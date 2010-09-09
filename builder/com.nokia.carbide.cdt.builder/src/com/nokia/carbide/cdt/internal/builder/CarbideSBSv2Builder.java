/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cdt.internal.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;

public class CarbideSBSv2Builder implements ICarbideBuilder {

    private static final String CLEAN_CMD = "CLEAN"; //$NON-NLS-1$
    private static final String FREEZE_CMD = "FREEZE"; //$NON-NLS-1$
    private static final String REALLYCLEAN_CMD = "REALLYCLEAN"; //$NON-NLS-1$

    private static final String COMPONENT_ARG = "-p"; //$NON-NLS-1$
    private static final String COMPILE_ARG = "-c"; //$NON-NLS-1$
    private static final String STROUT_ARG = "-f -";
    
    public boolean buildAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		
		SubMonitor progress = SubMonitor.convert(monitor, 3);
		progress.setTaskName(CarbideBuildManagerUtils.getBuildLabel(buildConfig, null));
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		// build the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			if (!invokeSBSCommand(buildConfig, launcher, new ArrayList<String>(), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}

		// build the test components if there are any
		if (testMakMakePaths.size() > 0) {

			if (!invokeSBSCommand(buildConfig, launcher, new ArrayList<String>(), true)) {
				return false;
			}
			
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}
		
		return true;
	}
    
    /** Get the build-able configuration from the command line (i.e. build alias). This is passed after the sbs -c parameter */
    protected String getConfigName(ICarbideBuildConfiguration buildConfig, IPath componentPath) {
    	String buildAlias = ((ISBSv2BuildContext)buildConfig.getBuildContext()).getSBSv2Alias();
    	if (buildAlias == null){ 
    		// Just get the default target. This is a SBSv1 style configuration name...
    		buildAlias = buildConfig.getPlatformString().toLowerCase() + "_" + buildConfig.getTargetString().toLowerCase();
    	}
    	
    	if (buildAlias.contains(".") && componentPath != null &&
    		!EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), componentPath)){
    		
    		// This is a variant build, but the MMP is not a variant so just take the base alias.
    		buildAlias = buildAlias.split("\\.")[0];
    	}
    	
    	return buildAlias;
    }
    
	public boolean buildComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		String componentName = componentPath.lastSegment();

		SubMonitor progress = SubMonitor.convert(monitor, 1);
		progress.setTaskName(CarbideBuildManagerUtils.getBuildLabel(buildConfig, componentName));

		List<String> argsList = new ArrayList<String>();
		argsList.add(COMPONENT_ARG);
		argsList.add(componentPath.toOSString());
		
		if (!invokeSBSCommand(buildConfig, launcher, argsList, isTest)) {
			return false;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		launcher.writeToConsole("\n***Build Complete\n");

		return true;
	}

	public boolean buildComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 1 + normalMakMakePaths.size() + testMakMakePaths.size());
		progress.setTaskName(CarbideBuildManagerUtils.getBuildLabel(buildConfig, null));

		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		// build the normal components if there are any.
		if (normalMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();

			for (IPath path : normalMakMakePaths) {
				argsList.add(COMPONENT_ARG);
				argsList.add(path.lastSegment());
			}

			if (!invokeSBSCommand(buildConfig, launcher, argsList, false)) {
				return false;
			}
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}

		// build the test components if there are any.
		if (testMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();

			for (IPath path : testMakMakePaths) {
				argsList.add(COMPONENT_ARG);
				argsList.add(path.lastSegment());
			}

			if (!invokeSBSCommand(buildConfig, launcher, argsList, true)) {
				return false;
			}
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}

		return true;
	}

	public void cleanAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, 3);
		progress.setTaskName("Cleaning " + buildConfig.getDisplayString());

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
		
		CarbideProjectInfo cpi = (CarbideProjectInfo)buildConfig.getCarbideProject();

		int cleanLevel = cpi.getCleanLevel();
		String cleanCmd = REALLYCLEAN_CMD;
		if (0 == cleanLevel) {
			cleanCmd = CLEAN_CMD;
		}

		List<String> argsList = new ArrayList<String>();
		argsList.add(cleanCmd);

		// clean the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			if (!invokeSBSCommand(buildConfig, launcher, argsList, false)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		// clean the test components if there are any
		if (testMakMakePaths.size() > 0) {

			if (!invokeSBSCommand(buildConfig, launcher, argsList, true)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
	}

	public boolean cleanComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		String componentName = componentPath.lastSegment();
		
		SubMonitor progress = SubMonitor.convert(monitor, 2);
		progress.setTaskName("Cleaning " + componentName);

		int cleanLevel = buildConfig.getCarbideProject().getCleanLevel();
		String cleanCmd = REALLYCLEAN_CMD;
		if (0 == cleanLevel) {
			cleanCmd = CLEAN_CMD;
		}

		List<String> argsList = new ArrayList<String>();
		argsList.add(cleanCmd);
		argsList.add(COMPONENT_ARG);
		argsList.add(componentPath.toOSString());
		
		if (!invokeSBSCommand(buildConfig, launcher, argsList, isTest)) {
			return false;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		launcher.writeToConsole("\n***Clean Complete\n");

		return true;
	}

	public void cleanComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 1 + normalMakMakePaths.size() + testMakMakePaths.size());
		progress.setTaskName("Cleaning " + buildConfig.getDisplayString());

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		CarbideProjectInfo cpi = (CarbideProjectInfo)buildConfig.getCarbideProject();

		int cleanLevel = cpi.getCleanLevel();
		String cleanCmd = REALLYCLEAN_CMD;
		if (0 == cleanLevel) {
			cleanCmd = CLEAN_CMD;
		}

		// clean the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();
			argsList.add(cleanCmd);

			for (IPath path : normalMakMakePaths) {

				argsList.add(COMPONENT_ARG);
				argsList.add(path.lastSegment());
			}

			if (!invokeSBSCommand(buildConfig, launcher, argsList, false)) {
				return;
			}
			progress.worked(1);
			if (progress.isCanceled()) {
				return;
			}
		}
		
		// clean the test components if there are any
		if (testMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();
			argsList.add(cleanCmd);

			for (IPath path : testMakMakePaths) {

				argsList.add(COMPONENT_ARG);
				argsList.add(path.lastSegment());
			}

			if (!invokeSBSCommand(buildConfig, launcher, argsList, true)) {
				return;
			}
			progress.worked(1);
			if (progress.isCanceled()) {
				return;
			}
		}
	}

	public void freezeAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 3);
		progress.setTaskName("Freezing " + buildConfig.getDisplayString());

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
		
		List<String> argsList = new ArrayList<String>();
		argsList.add(FREEZE_CMD);

		// freeze the normal components if there are any
		if (normalMakMakePaths.size() > 0) {
			
			if (!invokeSBSCommand(buildConfig, launcher, argsList, false)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		// freeze the test components if there are any
		if (testMakMakePaths.size() > 0) {

			if (!invokeSBSCommand(buildConfig, launcher, argsList, true)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
	}

	public boolean freezeComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		List<String> argsList = new ArrayList<String>();
		argsList.add(FREEZE_CMD);
		argsList.add(COMPONENT_ARG);
		argsList.add(componentPath.toOSString());
		
		if (!invokeSBSCommand(buildConfig, launcher, argsList, isTest)) {
			return false;
		}
		
		monitor.worked(1);
		if (monitor.isCanceled()) {
			return false;
		}

		launcher.writeToConsole("\n***Freeze Complete\n");

		return true;
	}

	public void freezeComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, 1 + normalMakMakePaths.size() + testMakMakePaths.size());
		progress.setTaskName("Freezing " + buildConfig.getDisplayString());

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		// freeze the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();
			argsList.add(FREEZE_CMD);

			for (IPath path : normalMakMakePaths) {

				argsList.add(COMPONENT_ARG);
				argsList.add(path.lastSegment());
			}
			
			if (!invokeSBSCommand(buildConfig, launcher, argsList, false)) {
				return;
			}
			progress.worked(1);
			if (progress.isCanceled()) {
				return;
			}
		}

		// freeze the test components if there are any
		if (testMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();
			argsList.add(FREEZE_CMD);

			for (IPath path : testMakMakePaths) {

				argsList.add(COMPONENT_ARG);
				argsList.add(path.lastSegment());
			}
			
			if (!invokeSBSCommand(buildConfig, launcher, argsList, true)) {
				return;
			}
			progress.worked(1);
			if (progress.isCanceled()) {
				return;
			}
		}
	}

	public boolean generateAbldMakefileIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, IPath componentPath, boolean isTest, IProgressMonitor progress) {
		return true;
	}
	
	public boolean generateAbldMakefileIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, IPath componentPath, boolean isTest) {
		return true;
	}

	public IPath getMakefileDirectory(ICarbideBuildConfiguration buildConfig) {
		// the makefile's are build config specific but are generated every time no
		// matter what so we'll just put them in epoc32\build\projectname
		String projectName = buildConfig.getCarbideProject().getProject().getName();
		return new Path(buildConfig.getSDK().getEPOCROOT()).append("epoc32").append("build").append(projectName); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public String[] getResolvedEnvVars(ICarbideBuildConfiguration config) {
		return config.getEnvironmentVarsInfo().getResolvedEnvironmentVariables();
	}

	public boolean invokeAbldCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, String[] abldArgs) {

		// check for test and build args and strip them out
		boolean isTest = false;
		
		List<String> args = new ArrayList<String>();
		for (String arg : abldArgs) {
			if (arg.compareToIgnoreCase("test") == 0) { //$NON-NLS-1$
				isTest = true;
			} else if (arg.compareToIgnoreCase("build") == 0) { //$NON-NLS-1$
				// just remove it
			} else {
				args.add(arg);
			}
		}
		
		return invokeSBSCommand(buildConfig, launcher, args, isTest);
	}

	protected boolean invokeSBSCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, List<String> sbsArgs, boolean isTest) {
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IProject project = cpi.getProject();

		List<String> args = new ArrayList<String>();
		args.add("-b"); //$NON-NLS-1$
		args.add(cpi.getAbsoluteBldInfPath().toOSString());
		args.add("-c"); //$NON-NLS-1$
		IPath componentPath = null;
		if (sbsArgs.size() >= 2){
			componentPath =  new Path(sbsArgs.get(sbsArgs.indexOf("-p")+ 1));
			sbsArgs.remove(1);
			sbsArgs.add(componentPath.lastSegment());
		} 
		String configName = getConfigName(buildConfig, componentPath);
		
		if (isTest) {
			configName = configName + ".test"; //$NON-NLS-1$
		}
		
		args.add(configName);
		
		if (!sbsArgs.contains("-p") && configName.contains(".")){
			// normal build, we also need to add an extra -c param for non-variation build
			args.add("-c");
			args.add(configName.split("\\.")[0]);
		}
		
		//TODO this causes output to go to stdout, but only at the end of the build.  should we specify a logfile name and tail the file?
		args.add("-f"); //$NON-NLS-1$
		args.add("-"); //$NON-NLS-1$
		args.add("-m"); //$NON-NLS-1$
		args.add(getMakefileDirectory(buildConfig).append("makefile").toOSString()); //$NON-NLS-1$
		args.addAll(sbsArgs);

		if (cpi.useKeepGoing()) {
			args.add("-k"); //$NON-NLS-1$
		}
		
		if (cpi.useDebugMode()) {
			args.add("-d"); //$NON-NLS-1$
		}
		
		args.add("-j"); //$NON-NLS-1$
		if (buildConfig.getCarbideProject().isConcurrentBuildingEnabled()) {
			args.add(Integer.toString(cpi.concurrentBuildJobs()));
		} else {
			args.add(Integer.toString(1));
		}
		
		if (cpi.shouldOverrideMakeEngine()) {
			args.add("-e"); //$NON-NLS-1$
			args.add(cpi.makeEngineToUse());
		}
		
		// append extra sbsv2 arguments to the command
		if (cpi.extraSBSv2Args().trim().length() > 0){
			String[] extraArgs = cpi.extraSBSv2Args().split(" ");
			for (String arg : extraArgs){
				args.add(arg);
			}
		}
		
		launcher.setErrorParserManager(SBSv2Utils.getSBSBinDirectory(), buildConfig.getErrorParserList());

		launcher.writeToConsole("\n***Invoking sbs command\n");
		
		int retVal = launcher.executeCommand(SBSv2Utils.getSBSPath(), 
				args.toArray(new String[args.size()]), getResolvedEnvVars(buildConfig), cpi.getINFWorkingDirectory());
		if (retVal != 0) {
			launcher.writeToConsole("\n=== SBS command failed with error code " + retVal + " ===");
			launcher.writeToConsole("\n***Stopping. Check the Problems view or Console output for errors.\n");
   			CarbideBuilderPlugin.createCarbideProjectMarker(project, IMarker.SEVERITY_ERROR,  "sbs returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
			return false;
		}
		
		launcher.writeToConsole(launcher.getTimingStats());
		
		return true;
	}

	public boolean invokeBldmakeCommand(ICarbideBuildConfiguration buildConfig,
			CarbideCommandLauncher launcher, String[] bldmakeArgs) {
		// nothing to do
		return true;
	}

	public boolean needsBldmakeMakefileGeneration(
			ICarbideBuildConfiguration config) {
		return true;
	}

	public void preBuildStep(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher) {
	}

	public void preCleanStep(ICarbideBuildConfiguration buildConfig) {
	}

	public void compileFile(IPath file, ICarbideBuildConfiguration buildConfig, IPath fullMMPPath, CarbideCommandLauncher launcher, IProgressMonitor monitor) throws CoreException {
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IPath workingDirectory = cpi.getINFWorkingDirectory();
		
		String configName = getConfigName(buildConfig, fullMMPPath);
		
		String[] sbsArgs = new String[] {"--source-target=" + file.toOSString(), COMPILE_ARG, configName, COMPONENT_ARG, fullMMPPath.toFile().getName(), STROUT_ARG};
		launcher.setErrorParserManager(buildConfig.getCarbideProject().getINFWorkingDirectory(), buildConfig.getErrorParserList());
		
		int retVal = launcher.executeCommand(
				SBSv2Utils.getSBSPath(),
				sbsArgs, getResolvedEnvVars(buildConfig), workingDirectory);
		if (retVal != 0) {
			launcher.writeToConsole("\n=== make failed with error code " + retVal + " ===");
			launcher.writeToConsole("\n***Stopping. Check the Problems view or Console output for errors.\n");
   			CarbideBuilderPlugin.createCarbideProjectMarker(cpi.getProject(), IMarker.SEVERITY_ERROR,  "make returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
			return;
		}

		monitor.worked(1);
		if (monitor.isCanceled()) {
			return;
		}
	}
}