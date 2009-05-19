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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.cdt.make.core.makefile.ICommand;
import org.eclipse.cdt.make.core.makefile.IMacroDefinition;
import org.eclipse.cdt.make.core.makefile.IRule;
import org.eclipse.cdt.make.core.makefile.ITargetRule;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cdt.builder.BuilderPreferenceConstants;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultGNUMakefileViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultViewConfiguration;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.internal.builder.ui.MMPChangedActionDialog;
import com.nokia.carbide.cdt.internal.builder.ui.MMPChangedActionDialog.MMPChangedAction;
import com.nokia.carbide.cpp.epoc.engine.BldInfViewRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.MMPDataRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.model.IModel;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPLanguage;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPData;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;


public class CarbideSBSv1Builder implements ICarbideBuilder {
	
    private static final String EPOC_BUILD_DIR = "epoc32\\build"; //$NON-NLS-1$

    private static final String BUILD_CMD = "build"; //$NON-NLS-1$
    private static final String CLEAN_CMD = "clean"; //$NON-NLS-1$
    private static final String FREEZE_CMD = "freeze"; //$NON-NLS-1$
    private static final String REALLYCLEAN_CMD = "reallyclean"; //$NON-NLS-1$
    private static final String EXPORT_CMD = "export"; //$NON-NLS-1$
    private static final String LIBRARY_CMD = "library"; //$NON-NLS-1$
    private static final String MAKEFILE_CMD = "makefile"; //$NON-NLS-1$
    private static final String RESOURCE_CMD = "resource"; //$NON-NLS-1$
    private static final String TARGET_CMD = "target"; //$NON-NLS-1$
    private static final String FINAL_CMD = "final"; //$NON-NLS-1$
    private static final String TEST_CMD = "test"; //$NON-NLS-1$

    private static final IPath MAKE = new Path("make.exe"); //$NON-NLS-1$
    private static final IPath PERL = new Path("perl.exe"); //$NON-NLS-1$

	private static final String ABLD_BAT_NAME = "abld.bat"; //$NON-NLS-1$

	private static final String CARBIDE_MAKEFILE_TEXT = "# Managed by Carbide - do not modify"; //$NON-NLS-1$
    
	private static boolean areWeManagingTheMakeFiles = false;
	
	// workaround for bug #4728.  we don't want to pass -j when building resources
	// or the library stage (bug #4827)
	private static boolean removeMakeVariableOneTime = false;
	
	private static FileFilter objectCodeDirectoryFileFilter = new FileFilter() {

		public boolean accept(File file) {
			if (file.getName().endsWith(".d") || file.getName().endsWith(".dep") || file.getName().endsWith(".o") || file.getName().endsWith(".obj")) {
				return true;
			}
			return false;
		}
	};

	private static final String CARBIDE_CHANGES_FILE = "CARBIDE_CHANGES.TXT"; //$NON-NLS-1$

	private static final String FIXUP_DEPFILES_FILE = "fix_dep_file_paths.pl"; //$NON-NLS-1$

	
	public void preBuildStep(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher) {
		areWeManagingTheMakeFiles = shouldManageMakeFiles(buildConfig);

		// if variant BSF, let them know the other platforms that will be built as a result
		IBSFPlatform[] bsfPlatforms = buildConfig.getSDK().getBSFCatalog().getAdditionalBuiltPlatforms(buildConfig.getPlatformString());
		if (bsfPlatforms.length > 0) {
			String plats = "";
			for (IBSFPlatform plat : bsfPlatforms) {
				plats = ", " + plat.getName();
			}
			plats = plats.replaceFirst(", ", "");
			launcher.writeToConsole("\n***Additionally built platforms: " + plats + "\n");
		}
	}
	
	public void preCleanStep(ICarbideBuildConfiguration buildConfig) {
		areWeManagingTheMakeFiles = shouldManageMakeFiles(buildConfig);
	}

	public boolean buildComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		areWeManagingTheMakeFiles = shouldManageMakeFiles(buildConfig);

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return false;
		}
		
		String componentName = componentPath.removeFileExtension().lastSegment();
		String buildPlatform = "";
		if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
				 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), componentPath)){
			buildPlatform = buildConfig.getPlatformString().toLowerCase();
		} else {
			buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
		}
		
		// need to run individual build steps when managing makefiles or doing concurrent builds
		if (areWeManagingTheMakeFiles || buildConfig.getCarbideProject().isConcurrentBuildingEnabled()) {
			
			SubMonitor progress = SubMonitor.convert(monitor, 7);
			progress.setTaskName("Building " + componentName);

			// run abld export or test export
			List<String> args = new ArrayList<String>();
			if (isTest) {
				args.add(TEST_CMD);
			}
			args.add(EXPORT_CMD);
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldExportArgs().split(" ")) {
				args.add(arg);
			}
			
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}
			
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			if (!runMMPChangeCheck(buildConfig, componentPath, isTest, launcher)) {
				return false;
			}
			
			// run abld makefile platform for each component to be built if needed
			if (!generateAbldMakefileIfNecessary(buildConfig, launcher, componentPath, isTest, progress)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld library platform
			args.clear();
			if (isTest) {
				args.add(TEST_CMD);
			}
			args.add(LIBRARY_CMD);
			args.add(buildPlatform);
			args.add(componentName);
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldLibraryArgs().split(" ")) {
				args.add(arg);
			}

			removeMakeVariableOneTime = true;
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld resource platform target
			args.clear();
			if (isTest) {
				args.add(TEST_CMD);
			}
			args.add(RESOURCE_CMD);
			args.add(buildPlatform);
			args.add(buildConfig.getTargetString().toLowerCase());
			args.add(componentName);
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldResourceArgs().split(" ")) {
				args.add(arg);
			}

			removeMakeVariableOneTime = true;
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld target platform target
			args.clear();
			if (isTest) {
				args.add(TEST_CMD);
			}
			args.add(TARGET_CMD);
			args.add(buildPlatform);
			args.add(buildConfig.getTargetString().toLowerCase());
			args.add(componentName);

			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldTargetArgs().split(" ")) {
				args.add(arg);
			}

			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}
			
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld final platform target
			args.clear();
			if (isTest) {
				args.add(TEST_CMD);
			}
			args.add(FINAL_CMD);
			args.add(buildPlatform);
			args.add(buildConfig.getTargetString().toLowerCase());
			args.add(componentName);
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFinalArgs().split(" ")) {
				args.add(arg);
			}

			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
			
		} else {
			SubMonitor progress = SubMonitor.convert(monitor, 1);
			progress.setTaskName("Building " + componentName);

			List<String> argsList = new ArrayList<String>();
			if (isTest) {
				argsList.add(TEST_CMD);
			}
			argsList.add(BUILD_CMD);
			argsList.add(buildPlatform);
			argsList.add(buildConfig.getTargetString().toLowerCase());
			argsList.add(componentName);
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldBuildArgs().split(" ")) {
				argsList.add(arg);
			}
			
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}
		
		launcher.writeToConsole("\n***Build Complete\n");

		return true;
	}
	
	public boolean cleanComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		areWeManagingTheMakeFiles = shouldManageMakeFiles(buildConfig);

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return false;
		}

		String componentName = componentPath.removeFileExtension().lastSegment();
		String buildPlatform = "";
		if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
				 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), componentPath)){
			buildPlatform = buildConfig.getPlatformString().toLowerCase();
		} else {
			buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
		}
		
		SubMonitor progress = SubMonitor.convert(monitor, 2);
		progress.setTaskName("Cleaning " + componentName);

		// run abld makefile platform for each component to be built if needed
		if (!generateAbldMakefileIfNecessary(buildConfig, launcher, componentPath, isTest, progress)) {
			return false;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		int cleanLevel = buildConfig.getCarbideProject().getCleanLevel();
		String abldCleanCmd = REALLYCLEAN_CMD;
		if (0 == cleanLevel) {
			abldCleanCmd = CLEAN_CMD;
		}

		List<String> argsList = new ArrayList<String>();
		if (isTest) {
			argsList.add(TEST_CMD);
		}
		argsList.add(abldCleanCmd);
		argsList.add(buildPlatform);
		argsList.add(buildConfig.getTargetString().toLowerCase());
		argsList.add(componentName);
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldCleanArgs().split(" ")) {
			argsList.add(arg);
		}
		
		if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
			return false;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		launcher.writeToConsole("\n***Clean Complete\n");

		return true;
	}
	
	public boolean freezeComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		areWeManagingTheMakeFiles = shouldManageMakeFiles(buildConfig);

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return false;
		}

		String componentName = componentPath.removeFileExtension().lastSegment();
		String buildPlatform = "";
		if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
				 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), componentPath)){
			buildPlatform = buildConfig.getPlatformString().toLowerCase();
		} else {
			buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
		}
		
		// run abld makefile platform for each component to be built if needed
		if (!generateAbldMakefileIfNecessary(buildConfig, launcher, componentPath, isTest, monitor)) {
			return false;
		}

		monitor.worked(1);
		if (monitor.isCanceled()) {
			return false;
		}

		List<String> argsList = new ArrayList<String>();
		if (isTest) {
			argsList.add(TEST_CMD);
		}
		argsList.add(FREEZE_CMD);
		argsList.add(buildPlatform);
		argsList.add(componentName);
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFreezeArgs().split(" ")) {
			argsList.add(arg);
		}
		
		if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
			return false;
		}
		
		monitor.worked(1);
		if (monitor.isCanceled()) {
			return false;
		}

		launcher.writeToConsole("\n***Freeze Complete\n");

		return true;
	}
	
	public void compileFile(IPath file, ICarbideBuildConfiguration buildConfig, IPath fullMMPPath, CarbideCommandLauncher launcher, IProgressMonitor monitor) throws CoreException {

		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		
		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return;
		}

		monitor.worked(1);
		if (monitor.isCanceled()) {
			return;
		}

		List<ISymbianBuildContext> buildConfigList = new ArrayList<ISymbianBuildContext>(1);
		buildConfigList.add(buildConfig);

		List<IPath> normalMakMakePaths = new ArrayList<IPath>();
		List<IPath> testMakMakePaths = new ArrayList<IPath>();

		EpocEngineHelper.getMakMakeFiles(cpi.getAbsoluteBldInfPath(), buildConfigList, normalMakMakePaths, testMakMakePaths, new NullProgressMonitor());

		monitor.worked(1);
		if (monitor.isCanceled()) {
			return;
		}

		boolean found = false;
		boolean isTest = false;
		
		// see if we can find the component
		for (IPath path : normalMakMakePaths) {
			if (path.equals(fullMMPPath)) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			// try the test components
			for (IPath path : testMakMakePaths) {
				if (path.equals(fullMMPPath)) {
					found = true;
					isTest = true;
					break;
				}
			}
		}

		if (!found) {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Unable to find mmp file " + fullMMPPath.toOSString(), null)); //$NON-NLS-1$
		}

		areWeManagingTheMakeFiles = shouldManageMakeFiles(buildConfig);
		
		if (!runMMPChangeCheck(buildConfig, fullMMPPath, isTest, launcher)) {
			return;
		}

		if (!generateAbldMakefileIfNecessary(buildConfig, launcher, fullMMPPath, isTest, monitor)) {
			return;
		}

		monitor.worked(1);
		if (monitor.isCanceled()) {
			return;
		}

		List<IPath> objectFilePaths = new ArrayList<IPath>();

		File makeFile = getMakefileForMMP(buildConfig, fullMMPPath);
		
		if (FileUtils.getSafeFileExtension(file).toLowerCase().compareTo("rss") == 0) {
			// rss files can be compiled for multiple languages, e.g. .R01, .R02, etc.
			EpocEnginePathHelper helper = new EpocEnginePathHelper(cpi.getProject());
			objectFilePaths = getMakeRulesForResource(buildConfig, helper.convertFilesystemToWorkspace(fullMMPPath), helper.convertPathToView(file));
		} else {
			// assume source file (or .s file or .cia file).  we need to get the path to the output file
			// that matches the one in the makefile.  we could normally do this without parsing the makefile
			// itself, but there are cases where we can't determine if the path with be absolute or relative
			// to the bld.inf directory.  for example, my tests on EKA1 kits show that it's relative except for
			// WINSCW builds.  For EKA1, they are always absolute, except in the UIQ3 SDK where they are similar
			// to EKA1.  since this is a little iffy, we'll get the real path from the makefile itself so we know
			// we're right.  this will be a little slower but...

			IPath makefilePath = new Path(makeFile.getAbsolutePath());

			IModelProvider modelProvider = EpocEnginePlugin.getMakefileModelProvider();
			IModel model = modelProvider.getSharedModel(makefilePath);
			if (model == null) {
				launcher.writeToConsole("\nERROR: Unable to create model for " + makefilePath.toOSString() + "\n");
				return;
			}
			
			IMakefileView view = (IMakefileView)model.createView(new DefaultGNUMakefileViewConfiguration(buildConfig.getCarbideProject(), new AcceptedNodesViewFilter()));
			if (view == null) {
				launcher.writeToConsole("\nERROR: Unable to create view for " + makefilePath.toOSString() + "\n");
				return;
			}

			IMacroDefinition[] epocBldMacros = view.getAllMacroDefinitions("EPOCBLD");
			if (epocBldMacros.length != 1) {
				launcher.writeToConsole("\nERROR: Unable to locate EPOCBLD macro in " + makefilePath.toOSString() + "\n");
				return;
			}

			view.dispose();
			modelProvider.releaseSharedModel(model);

			IPath objectDir = null;
			if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
				 EpocEngineHelper.hasFeatureVariantKeyword(cpi, fullMMPPath)){
				// if symbian binary variation, then the object file dir will be in sub-directory with <md5>/udeb/<obj>
				// The platform can only be a variant if the MMP file has FEATUREVARIANT keyword && The platform is ARMV5-based.
				String MD5Name = EpocEngineHelper.getMD5HashForBinaryVariant(buildConfig, fullMMPPath);
				if (MD5Name != null && MD5Name.length() > 0){
					objectDir = new Path(epocBldMacros[0].getValue().toString()).append(MD5Name).append(buildConfig.getTargetString());
				}
			} 
			
			if (objectDir == null){
				objectDir = new Path(epocBldMacros[0].getValue().toString()).append(buildConfig.getTargetString());
			}
			
			IPath path = objectDir.append(file.removeFileExtension().lastSegment() + ".o").removeTrailingSeparator();
			
			if (FileUtils.getSafeFileExtension(file).toLowerCase().compareTo("cia") == 0) {
				// .cia objects have an _ appended to them
				path = objectDir.append(file.removeFileExtension().lastSegment() + "_.o").removeTrailingSeparator();
			}

			objectFilePaths.add(path);
		}
		
		IPath workingDirectory = cpi.getINFWorkingDirectory();

		for (IPath objectPath : objectFilePaths) {
			// verify object file directory. the make target cannot build unless the output folder exists
			File objDir = objectPath.removeLastSegments(1).toFile();
			if (!objDir.exists()) {
				objDir.mkdirs();
			}

			// delete the object file if it exists so make will actually compile it
			IPath objectToDelete = objectPath;
			if (objectToDelete.toOSString().startsWith("..")) {
				// relative paths are relative to the bld.inf directory
				objectToDelete = workingDirectory.append(objectToDelete);
			} else if (objectToDelete.isAbsolute() && objectToDelete.getDevice() == null) {
				objectToDelete = objectToDelete.setDevice(workingDirectory.getDevice());
			}

			try {
				File outputFile = objectToDelete.toFile().getCanonicalFile();
				if (outputFile.exists()) {
					outputFile.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
				CarbideBuilderPlugin.log(e);
			}
			
			// strip off the drive to match the makefile rules
			String[] makeArgs = new String[] {"-f", makeFile.getAbsolutePath(), objectPath.setDevice(null).toOSString()};

			launcher.setErrorParserManager(buildConfig.getCarbideProject().getINFWorkingDirectory(), buildConfig.getErrorParserList());

			int retVal = launcher.executeCommand(MAKE, makeArgs, getResolvedEnvVars(buildConfig), workingDirectory);
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
	
	protected List<IPath> getMakeRulesForResource(final ICarbideBuildConfiguration buildConfig, final IPath workspaceRelativeMMPPath, final IPath projectRelativeResourcePath) {
		
		final List<IPath> rules = new ArrayList<IPath>();
		
		EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
				new DefaultMMPViewConfiguration(buildConfig.getCarbideProject().getProject(), buildConfig, new AcceptedNodesViewFilter()), 
				new MMPDataRunnableAdapter() {

				public Object run(IMMPData mmpData) {
					// read the project-wide target path
					String targetPath = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
					if (targetPath != null) {
						// make sure it doesn't start with a "\" but ends with one
						IPath targetP = new Path(targetPath).makeRelative().addTrailingSeparator();
						targetPath = targetP.toOSString();
					} else {
						// for EKA1 just leave empty.  for EKA2 use sys\bin\
						if (buildConfig.getSDK().getOSVersion().getMajor() > 8) {
							targetPath = "sys\\bin\\"; //$NON-NLS-1$
						} else {
							targetPath = ""; //$NON-NLS-1$
						}
					}

					String dataZDir = buildConfig.getSDK().getReleaseRoot().removeLastSegments(1).toOSString() + "\\Data\\z\\"; //$NON-NLS-1$

					IPath rezPath = null;
					List<EMMPLanguage> languages = null;
					
					// check the user resources
					List<IPath> userResources = mmpData.getUserResources();
					for (IPath userRes : userResources) {
						if (userRes.equals(projectRelativeResourcePath)) {
							if (buildConfig.getSDK().isEKA1()) {
								rezPath = new Path(dataZDir + targetPath + userRes.removeFileExtension().lastSegment());
							} else {
								rezPath = new Path(dataZDir).removeLastSegments(1).append(userRes.removeFileExtension().lastSegment());
							}
							break;
						}
					}
					
					if (rezPath == null) {
						// check the system resources
						List<IPath> systemResources = mmpData.getSystemResources();
						for (IPath systemRes : systemResources) {
							if (systemRes.equals(projectRelativeResourcePath)) {
								// target path for system resources is \z\system\data\
								rezPath = new Path(dataZDir + "system\\data\\" + systemRes.removeFileExtension().lastSegment());
								break;
							}
						}
					}

					if (rezPath == null) {
						// check the resource blocks
						List<IMMPResource> resourceBlocks = mmpData.getResourceBlocks();
						for (IMMPResource resourceBlock : resourceBlocks) {
							if (resourceBlock.getSource().equals(projectRelativeResourcePath)) {
								IPath resPath = resourceBlock.getTargetPath();
								if (resPath == null) {
									// if not specified in the resource block then get the global
									// target path
									String targetP = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
									if (targetP != null) {
										resPath = new Path(targetP);
									}
								}
								if (resPath != null) {
									resPath = resPath.makeRelative().addTrailingSeparator();
									String filename = resourceBlock.getTargetFile();
									if (filename == null) {
										filename = resourceBlock.getSource().removeFileExtension().lastSegment();
									} else {
										filename = new Path(filename).removeFileExtension().toOSString();
									}
									rezPath = new Path(dataZDir + resPath.toOSString() + filename);
									
									languages = resourceBlock.getLanguages();
									if (languages.size() == 0)
										languages = null;

									break;
								}
							}
						}
					}

					if (rezPath != null) {
						if (languages == null) {
							languages = mmpData.getLanguages();
							if (languages.size() == 0) {
								// default is non-localize
								languages = Collections.singletonList(EMMPLanguage.SC_NonLocalized);
							}
						}

						for (EMMPLanguage language : languages) {
							String extension = "R" + language.getCodeString(); //$NON-NLS-1$
							rules.add(rezPath.addFileExtension(extension));
						}
					}
					return null;
				}
		});
		
		return rules;
	}

	protected boolean shouldManageMakeFiles(ICarbideBuildConfiguration buildConfig) {
		// check the project pref to see if we should manage the make files
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		if (cpi.areMakefilesManaged()) {
			// if it's an EKA1 SDK then we need to check if our change is there.  if not
			// then we need to ask them if we can make the change.
			final ISymbianSDK sdk = buildConfig.getSDK();
			if (sdk.isEKA1()) {
				// check to see if the CARBIDE_CHANGES.TXT file is there
				if (!sdk.getToolsPath().append(CARBIDE_CHANGES_FILE).toFile().exists()) {

					// trick to get flag back from runnable
					final List<Boolean> shouldUpdate = new ArrayList<Boolean>(0);

					// ask the user if we can update their sdk
					Display.getDefault().syncExec(new Runnable() {

						public void run() {
							// ask the user if they want to update now
							if (MessageDialog.openQuestion(WorkbenchUtils.getSafeShell(),
									"SDK Update Required",
									"In order for Carbide to manage dependencies and improve build times, we need to make a minor change to one of the files in your SDK.  This will not affect builds outside of Carbide in any way.  Would you like to make this change?")) {
								shouldUpdate.add(Boolean.TRUE);
							}
						}
					});

					if (shouldUpdate.size() == 1 && shouldUpdate.get(0).booleanValue()) {
						if (!updateMakDepsFile(sdk)) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	protected boolean updateMakDepsFile(ISymbianSDK sdk) {
		// add the following to the top of the the Deps_GenDependsL subroutine in makdeps.pm
		String change = "\r\n\t# Carbide.c++ change.  See CARBIDE_CHANGES.TXT for more details.\r\n\tif ($ENV{CARBIDE_NO_DEPENDENCIES}) {\r\n\t\treturn;\r\n\t}\r\n";

		boolean updated = false;
		try {
			File mdFile = sdk.getToolsPath().append("makdeps.pm").toFile();
			RandomAccessFile makDepsFile = new RandomAccessFile(mdFile, "rw");
			
			// back up the file first
			File backupFile = sdk.getToolsPath().append("makdeps.pmbak").toFile();
			if (backupFile.createNewFile()) {
				FileUtils.copyFile(mdFile, backupFile);
			}
			
			// now scan for the line we're interested in
			String line = makDepsFile.readLine();
			while (line != null) {
				if (line.startsWith("sub Deps_GenDependsL")) {
					// now insert our text
					long fp = makDepsFile.getFilePointer();
					byte[] bytes = new byte[(int)(makDepsFile.length() - fp)];
					makDepsFile.readFully(bytes);
					makDepsFile.seek(fp);
					
					String newText = new String(bytes);
					newText = change + newText;
					
					makDepsFile.write(newText.getBytes());
					updated = true;
					break;
				}
				line = makDepsFile.readLine();
			}

			makDepsFile.close();

		} catch (Exception e) {
			CarbideBuilderPlugin.log(e);
			e.printStackTrace();
		}
		
		if (!updated) {
			return false;
		}
		
		// now create the CARBIDE_CHANGES.TXT file
		File ourFile = sdk.getToolsPath().append(CARBIDE_CHANGES_FILE).toFile();
		try {
			if (!ourFile.createNewFile()) {
				return false;
			}
			
			// now write the content
			String content = "In order to improve build performance, we need to tell the Symbian build tools not to generate dependency\r\n" +
							 "information for sources and resources in the make files.  The dependency information will still be generated and\r\n" +
							 "tracked, but will be done on a per-file basis.  In order to do this, we needed to add a few lines to the makdeps.pm\r\n" +
							 "file.  This change will not affect command line builds.  The previous file was backed up and named makdeps.pmbak.\r\n";

			FileWriter writer = new FileWriter(ourFile);
			writer.write(content);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			CarbideBuilderPlugin.log(e);
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean buildAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		//TODO: do we really want to bail if abld returns an error?  is this the keepgoing flag?  see what the command line builds do.
		// they bail on an individual build step, but keep building the other steps even if there's an error and keepgoing is not specified
		
		
		// need to run individual build steps when managing makefiles or doing concurrent builds
		if (!areWeManagingTheMakeFiles && !buildConfig.getCarbideProject().isConcurrentBuildingEnabled()) {
			// not using our dependency stuff.  just call abld [test] build.  note that we need
			// to do this because the makefile step needs to happen even if the mmp hasn't changed
			// because the dependency info is in the makefiles.
			
			SubMonitor progress = SubMonitor.convert(monitor, 3);
			progress.setTaskName("Building " + buildConfig.getDisplayString());

			if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
				return false;
			}
			
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld build for normal components if there are any
			if (normalMakMakePaths.size() > 0) {

				List<String> argsList = new ArrayList<String>();
				argsList.add(BUILD_CMD);
				argsList.add(buildConfig.getPlatformString().toLowerCase());
				argsList.add(buildConfig.getTargetString().toLowerCase());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldBuildArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
			
			// run abld build for test components if there are any
			if (buildConfig.getCarbideProject().isBuildingTestComps()) {
				if (testMakMakePaths.size() > 0) {
	
					List<String> argsList = new ArrayList<String>();
					argsList.add(TEST_CMD);
					argsList.add(BUILD_CMD);
					argsList.add(buildConfig.getPlatformString().toLowerCase());
					argsList.add(buildConfig.getTargetString().toLowerCase());
					
					for (String arg : buildConfig.getBuildArgumentsInfo().getAbldBuildArgs().split(" ")) {
						argsList.add(arg);
					}
					
					if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
						return false;
					}
				}
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			return true;
		}
		
		// figure out exactly how much work we have to do
		int unitsOfWork = 3;
		if (normalMakMakePaths.size() > 0) {
			unitsOfWork += 4;
		}
		if (buildConfig.getCarbideProject().isBuildingTestComps()) {
			unitsOfWork += 1;
		}
		if (testMakMakePaths.size() > 0) {
			unitsOfWork += 4;
		}
		
		SubMonitor progress = SubMonitor.convert(monitor, unitsOfWork);
		progress.setTaskName("Building " + buildConfig.getDisplayString());

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return false;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		// run abld export even if there are no mmps
		List<String> args = new ArrayList<String>();
		args.add(EXPORT_CMD);
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldExportArgs().split(" ")) {
			args.add(arg);
		}
		
		if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
			return false;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		if (!runMMPChangeCheck(buildConfig, normalMakMakePaths, testMakMakePaths, launcher)) {
			return false;
		}
		
		// run abld makefile platform for each component to be built if needed
		if (!CarbideCPPBuilder.generateAbldMakefilesIfNecessary(buildConfig, launcher, false, progress)) {
			return false;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		// build the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			// run abld library platform
			args.clear();
			args.add(LIBRARY_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldLibraryArgs().split(" ")) {
				args.add(arg);
			}
			
			removeMakeVariableOneTime = true;
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld resource platform target
			args.clear();
			args.add(RESOURCE_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			args.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldResourceArgs().split(" ")) {
				args.add(arg);
			}
			
			removeMakeVariableOneTime = true;
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld target platform target
			args.clear();
			args.add(TARGET_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			args.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldTargetArgs().split(" ")) {
				args.add(arg);
			}
			
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}
			
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld final platform target
			args.clear();
			args.add(FINAL_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			args.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFinalArgs().split(" ")) {
				args.add(arg);
			}
			
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}

		// run abld test export even if there are no mmps
		// only do this if building test components is checked.  note we do this
		// even if there are no test mmps.  there could still be test exports.
		if (buildConfig.getCarbideProject().isBuildingTestComps()) {
			args.clear();
			args.add(TEST_CMD);
			args.add(EXPORT_CMD);
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldExportArgs().split(" ")) {
				args.add(arg);
			}
			
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}

		// build the test components if there are any
		if (testMakMakePaths.size() > 0) {

			// run abld library platform
			args.clear();
			args.add(TEST_CMD);
			args.add(LIBRARY_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldLibraryArgs().split(" ")) {
				args.add(arg);
			}
			
			removeMakeVariableOneTime = true;
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld resource platform target
			args.clear();
			args.add(TEST_CMD);
			args.add(RESOURCE_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			args.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldResourceArgs().split(" ")) {
				args.add(arg);
			}
			
			removeMakeVariableOneTime = true;
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld target platform target
			args.clear();
			args.add(TEST_CMD);
			args.add(TARGET_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			args.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldTargetArgs().split(" ")) {
				args.add(arg);
			}
			
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}
			
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld final platform target
			args.clear();
			args.add(TEST_CMD);
			args.add(FINAL_CMD);
			args.add(buildConfig.getPlatformString().toLowerCase());
			args.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFinalArgs().split(" ")) {
				args.add(arg);
			}
			
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, args.toArray(new String[args.size()]), false)) {
				return false;
			}

			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean buildComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		// need to run individual build steps when managing makefiles or doing concurrent builds
		if (!areWeManagingTheMakeFiles && !buildConfig.getCarbideProject().isConcurrentBuildingEnabled()) {
			// not using our dependency stuff.  just call abld [test] build.  note that we need
			// to do this because the makefile step needs to happen even if the mmp hasn't changed
			// because the dependency info is in the makefiles.
			
			SubMonitor progress = SubMonitor.convert(monitor, 1 + normalMakMakePaths.size() + testMakMakePaths.size());
			progress.setTaskName("Building " + buildConfig.getDisplayString());

			if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
				return false;
			}
			
			progress.worked(1);
			if (progress.isCanceled()) {
				return false;
			}

			// run abld build platform for each component
			for (IPath path : normalMakMakePaths) {

				List<String> argsList = new ArrayList<String>();
				argsList.add(BUILD_CMD);
				argsList.add(buildConfig.getPlatformString().toLowerCase());
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldBuildArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}

			// run abld build platform target for each test component
			for (IPath path : testMakMakePaths) {

				List<String> argsList = new ArrayList<String>();
				argsList.add(TEST_CMD);
				argsList.add(BUILD_CMD);
				argsList.add(buildConfig.getPlatformString().toLowerCase());
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldBuildArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}
			
			// in case there are no components selected, we still need to do an export
			if (normalMakMakePaths.size() == 0) {
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, new String[]{EXPORT_CMD}, false)) {
					return false;
				}
			}

			if (testMakMakePaths.size() == 0) {
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, new String[]{TEST_CMD, EXPORT_CMD}, false)) {
					return false;
				}
			}

			return true;
		}
		
		SubMonitor progress = SubMonitor.convert(monitor, 4 + (normalMakMakePaths.size()*4) + (testMakMakePaths.size()*4));
		progress.setTaskName("Building " + buildConfig.getDisplayString());

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return false;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		// run abld export even if there are no mmps - note, this is not mmp specific
		List<String> argsList = new ArrayList<String>();
		argsList.add(EXPORT_CMD);
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldExportArgs().split(" ")) {
			argsList.add(arg);
		}
		
		if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
			return false;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		if (!runMMPChangeCheck(buildConfig, normalMakMakePaths, testMakMakePaths, launcher)) {
			return false;
		}
		
		// run abld makefile platform for each component to be built if needed
		if (!CarbideCPPBuilder.generateAbldMakefilesIfNecessary(buildConfig, launcher, false, progress)) {
			return false;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}
		
		// build the normal components if there are any. do each abld step for all components.
		if (normalMakMakePaths.size() > 0) {

			// run abld library platform for each component
			for (IPath path : normalMakMakePaths) {
				
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(LIBRARY_CMD);
				argsList.add(buildPlatform);
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldLibraryArgs().split(" ")) {
					argsList.add(arg);
				}
				
				removeMakeVariableOneTime = true;
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}

			// run abld resource platform target for each component
			for (IPath path : normalMakMakePaths) {
				
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(RESOURCE_CMD);
				argsList.add(buildPlatform);
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldResourceArgs().split(" ")) {
					argsList.add(arg);
				}
				
				removeMakeVariableOneTime = true;
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}

			// run abld target platform target for each component
			for (IPath path : normalMakMakePaths) {
				
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(TARGET_CMD);
				argsList.add(buildPlatform);
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldTargetArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}
			
			// run abld final platform target for each component
			for (IPath path : normalMakMakePaths) {
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(FINAL_CMD);
				argsList.add(buildPlatform);
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFinalArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}
		}

		// run abld test export even if there are no mmps - note, this is not mmp specific
		argsList.clear();
		argsList.add(TEST_CMD);
		argsList.add(EXPORT_CMD);
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldExportArgs().split(" ")) {
			argsList.add(arg);
		}
		
		if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
			return false;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return false;
		}

		// build the test components if there are any. do each abld step for all components.
		if (testMakMakePaths.size() > 0) {

			// run abld library platform for each component
			for (IPath path : testMakMakePaths) {
				
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(TEST_CMD);
				argsList.add(LIBRARY_CMD);
				argsList.add(buildPlatform);
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldLibraryArgs().split(" ")) {
					argsList.add(arg);
				}

				removeMakeVariableOneTime = true;
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}

			// run abld resource platform target for each component
			for (IPath path : testMakMakePaths) {
				
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(TEST_CMD);
				argsList.add(RESOURCE_CMD);
				argsList.add(buildPlatform);
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldResourceArgs().split(" ")) {
					argsList.add(arg);
				}

				removeMakeVariableOneTime = true;
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}

			// run abld target platform target for each component
			for (IPath path : testMakMakePaths) {
				
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(TEST_CMD);
				argsList.add(TARGET_CMD);
				argsList.add(buildPlatform);
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldTargetArgs().split(" ")) {
					argsList.add(arg);
				}

				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}

			// run abld final platform target for each component
			for (IPath path : testMakMakePaths) {
				
				String buildPlatform = "";
				if ( buildConfig.getPlatformString().startsWith(ISymbianBuildContext.ARMV5_PLATFORM) &&
					 EpocEngineHelper.hasFeatureVariantKeyword(buildConfig.getCarbideProject(), path)) {
					
					buildPlatform = buildConfig.getPlatformString().toLowerCase();
					
				} else {
					buildPlatform = buildConfig.getBasePlatformForVariation().toLowerCase();
				}
				
				argsList.clear();
				argsList.add(TEST_CMD);
				argsList.add(FINAL_CMD);
				argsList.add(buildPlatform);
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFinalArgs().split(" ")) {
					argsList.add(arg);
				}

				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return false;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return false;
				}
			}
		}
		
		return true;
	}

	public void cleanAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 5);
		progress.setTaskName("Cleaning " + buildConfig.getDisplayString());

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
		
		if (!CarbideCPPBuilder.generateAbldMakefilesIfNecessary(buildConfig, launcher, false, progress)) {
			return;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
		
		CarbideProjectInfo cpi = (CarbideProjectInfo)buildConfig.getCarbideProject();

		int cleanLevel = cpi.getCleanLevel();
		String abldCleanCmd = REALLYCLEAN_CMD;
		if (0 == cleanLevel) {
			abldCleanCmd = CLEAN_CMD;
		}
		
		// clean the normal components if there are any
		if (normalMakMakePaths.size() > 0) {
			
			List<String> argsList = new ArrayList<String>();
			argsList.add(abldCleanCmd);
			argsList.add(buildConfig.getPlatformString().toLowerCase());
			argsList.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldCleanArgs().split(" ")) {
				argsList.add(arg);
			}
			
				// run abld clean/reallyclean
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		// clean the test components if there are any
		if (testMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();
			argsList.add(TEST_CMD);
			argsList.add(abldCleanCmd);
			argsList.add(buildConfig.getPlatformString().toLowerCase());
			argsList.add(buildConfig.getTargetString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldCleanArgs().split(" ")) {
				argsList.add(arg);
			}
			
			// run abld test clean/reallyclean
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		if (2 == cleanLevel) {
			// clean level 2 so we need to run bldmake clean as well

			List<String> argsList = new ArrayList<String>();
			argsList.add(CLEAN_CMD);
			argsList.add(buildConfig.getPlatformString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getBldmakeCleanArgs().split(" ")) {
				argsList.add(arg);
			}
			
			CarbideCPPBuilder.invokeBldmakeCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false);
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
	}
	
	public void cleanComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 3 + normalMakMakePaths.size() + testMakMakePaths.size());
		progress.setTaskName("Cleaning " + buildConfig.getDisplayString());

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		if (!CarbideCPPBuilder.generateAbldMakefilesIfNecessary(buildConfig, launcher, false, progress)) {
			return;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		CarbideProjectInfo cpi = (CarbideProjectInfo)buildConfig.getCarbideProject();

		int cleanLevel = cpi.getCleanLevel();
		String abldCleanCmd = REALLYCLEAN_CMD;
		if (0 == cleanLevel) {
			abldCleanCmd = CLEAN_CMD;
		}

		// clean the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			// run abld clean/reallyclean for each component
			for (IPath path : normalMakMakePaths) {

				List<String> argsList = new ArrayList<String>();
				argsList.add(abldCleanCmd);
				argsList.add(buildConfig.getPlatformString().toLowerCase());
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldCleanArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return;
				}
			}
		}
		
		// clean the test components if there are any
		if (testMakMakePaths.size() > 0) {

			// run abld test clean/reallyclean for each component
			for (IPath path : testMakMakePaths) {

				List<String> argsList = new ArrayList<String>();
				argsList.add(TEST_CMD);
				argsList.add(abldCleanCmd);
				argsList.add(buildConfig.getPlatformString().toLowerCase());
				argsList.add(buildConfig.getTargetString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldCleanArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return;
				}
			}
		}

		if (2 == cleanLevel) {
			// clean level 2 so we need to run bldmake clean as well

			List<String> argsList = new ArrayList<String>();
			argsList.add(CLEAN_CMD);
			argsList.add(buildConfig.getPlatformString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getBldmakeCleanArgs().split(" ")) {
				argsList.add(arg);
			}
			
			CarbideCPPBuilder.invokeBldmakeCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false);
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
	}

	public void freezeAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 4);
		progress.setTaskName("Freezing " + buildConfig.getDisplayString());

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
		
		if (!CarbideCPPBuilder.generateAbldMakefilesIfNecessary(buildConfig, launcher, false, progress)) {
			return;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
		
		// freeze the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();
			argsList.add(FREEZE_CMD);
			argsList.add(buildConfig.getPlatformString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFreezeArgs().split(" ")) {
				argsList.add(arg);
			}
			
			// run abld freeze
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		// freeze the test components if there are any
		if (testMakMakePaths.size() > 0) {

			List<String> argsList = new ArrayList<String>();
			argsList.add(TEST_CMD);
			argsList.add(FREEZE_CMD);
			argsList.add(buildConfig.getPlatformString().toLowerCase());
			
			for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFreezeArgs().split(" ")) {
				argsList.add(arg);
			}
			
			// run abld test freeze
			if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
				return;
			}
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}
	}
	
	public void freezeComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 2 + normalMakMakePaths.size() + testMakMakePaths.size());
		progress.setTaskName("Freezing " + buildConfig.getDisplayString());

		if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
			return;
		}

		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		if (!CarbideCPPBuilder.generateAbldMakefilesIfNecessary(buildConfig, launcher, false, progress)) {
			return;
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		// freeze the normal components if there are any
		if (normalMakMakePaths.size() > 0) {

			// run abld freeze for each component
			for (IPath path : normalMakMakePaths) {

				List<String> argsList = new ArrayList<String>();
				argsList.add(FREEZE_CMD);
				argsList.add(buildConfig.getPlatformString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFreezeArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return;
				}
			}
		}

		// freeze the test components if there are any
		if (testMakMakePaths.size() > 0) {

			// run abld test freeze for each component
			for (IPath path : testMakMakePaths) {

				List<String> argsList = new ArrayList<String>();
				argsList.add(TEST_CMD);
				argsList.add(FREEZE_CMD);
				argsList.add(buildConfig.getPlatformString().toLowerCase());
				argsList.add(path.removeFileExtension().lastSegment());
				
				for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFreezeArgs().split(" ")) {
					argsList.add(arg);
				}
				
				if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
					return;
				}
				progress.worked(1);
				if (progress.isCanceled()) {
					return;
				}
			}
		}
	}

	protected boolean runMMPChangeCheck(final ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, final CarbideCommandLauncher launcher) {

		// ignore this for Qt projects since the mmp will be regenerated before each build.
		final IProject project = buildConfig.getCarbideProject().getProject();
		try {
			if (project.hasNature(QtCorePlugin.QT_PROJECT_NATURE_ID)) {
				return true;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		// we used to ignore this for emulator builds, thinking everything of interest was in the .uid.cpp file.  well, a lot of
		// the things are, but not all. see bug #5504 for more details.

		if (!buildConfig.getCarbideProject().promptForMMPChangedAction() && buildConfig.getCarbideProject().defaultMMPChangedAction() == ICarbideProjectInfo.ACTION_NONE) {
			// don't need to do anything
			return true;
		}
		
		final List<MMPChangedAction> mmpList = new ArrayList<MMPChangedAction>();
		
		int defaultAction = 0;
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			defaultAction = cpi.defaultMMPChangedAction();
		}
		
		if (hasMMPMakefileChanged(buildConfig, componentPath)) {
			mmpList.add(new MMPChangedAction(componentPath, defaultAction, isTest));
		}

		return mmpsChanged(buildConfig, mmpList, launcher);
	}
	
	protected boolean runMMPChangeCheck(final ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, final CarbideCommandLauncher launcher) {

		// ignore this for Qt projects since the mmp will be regenerated before each build.
		final IProject project = buildConfig.getCarbideProject().getProject();
		try {
			if (project.hasNature(QtCorePlugin.QT_PROJECT_NATURE_ID)) {
				return true;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		// we used to ignore this for emulator builds, thinking everything of interest was in the .uid.cpp file.  well, a lot of
		// the things are, but not all. see bug #5504 for more details.

		if (!buildConfig.getCarbideProject().promptForMMPChangedAction() && buildConfig.getCarbideProject().defaultMMPChangedAction() == ICarbideProjectInfo.ACTION_NONE) {
			// don't need to do anything
			return true;
		}
		
		
		final List<MMPChangedAction> mmpList = new ArrayList<MMPChangedAction>();
		
		int defaultAction = 0;
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			defaultAction = cpi.defaultMMPChangedAction();
		}
		
		// check all mmps
		for (IPath path : normalMakMakePaths) {
			if (hasMMPMakefileChanged(buildConfig, path)) {
				mmpList.add(new MMPChangedAction(path, defaultAction, false));
			}
		}
		
		for (IPath path : testMakMakePaths) {
			if (hasMMPMakefileChanged(buildConfig, path)) {
				mmpList.add(new MMPChangedAction(path, defaultAction, true));
			}
		}

		return mmpsChanged(buildConfig, mmpList, launcher);
	}

	protected boolean mmpsChanged(final ICarbideBuildConfiguration buildConfig, final List<MMPChangedAction> mmpList, final CarbideCommandLauncher launcher) {
		if (mmpList.size() < 1) {
			// no changed mmps
			return true;
		}

		if (!buildConfig.getCarbideProject().promptForMMPChangedAction()) {
			// just apply the default action to each mmp
			switch(buildConfig.getCarbideProject().defaultMMPChangedAction()) {
			case ICarbideProjectInfo.ACTION_NONE:
				// do nothing
				break;
			case ICarbideProjectInfo.ACTION_LINK_ONLY:
				for (MMPChangedAction action : mmpList) {
					// force re-link
					forceRelink(buildConfig, action.fullMMPPath);
				}
				break;
			case ICarbideProjectInfo.ACTION_COMPILE_AND_LINK:
				for (MMPChangedAction action : mmpList) {
					// force re-compile
					forceRecompile(buildConfig, action.fullMMPPath, action.isTest, launcher);
				}
				break;
			}
			
			return true;
		}

		// run in the UI thread
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null) {
			IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
			window = windows[0];
		}

		final Shell shell = window.getShell();

		final IProject project = buildConfig.getCarbideProject().getProject();

		final MMPChangedActionDialog dlg = new MMPChangedActionDialog(shell, project, mmpList);

		// trick to get return value
		final List<Boolean> retVal = new ArrayList<Boolean>(1);
		
		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				if (Dialog.OK == dlg.open()) {
					// check the don't bug me option
					if (dlg.dontAskAgain()) {
						// uncheck the project or workspace setting
						CarbideProjectModifier cpm = (CarbideProjectModifier)CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
						if (cpm != null) {
							// if there's only one mmp then change the default action to what they selected.
							// ideally when "don't ask me again" is checked we'd remember their decision, but
							// since they can decide different actions for different mmp files, we can't really
							// do it.  so only remember their decision when there's one mmp file.
							boolean saveAction = (mmpList.size() == 1);
							if (cpm.overrideWorkspaceBuildSettingsProjectValue()) {
								cpm.writeProjectSetting(CarbideProjectInfo.PROMPT_FOR_MMP_CHANGED_ACTION, "false");
								if (saveAction) {
									cpm.writeProjectSetting(CarbideProjectInfo.DEFAULT_MMP_CHANGED_ACTION, Integer.toString(mmpList.get(0).mmpAction));
								}
								cpm.saveChanges();
							} else {
								IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
								store.setValue(BuilderPreferenceConstants.PREF_MMP_CHANGED_ACTION_PROMPT, false);
								if (saveAction) {
									store.setValue(BuilderPreferenceConstants.PREF_DEFAULT_MMP_CHANGED_ACTION, mmpList.get(0).mmpAction);
								}
							}
						}
					}
					
					for (MMPChangedAction action : mmpList) {
						if (action.mmpAction == 1) {
							// force re-link
							forceRelink(buildConfig, action.fullMMPPath);
						} else if (action.mmpAction == 2) {
							// force re-compile or clean
							forceRecompile(buildConfig, action.fullMMPPath, action.isTest, launcher);
						}
					}
					// need to return true
					retVal.add(Boolean.TRUE);
				} else {
					// need to return false
					retVal.add(Boolean.FALSE);
				}
			}
		});
		
		return retVal.get(0).booleanValue();
		
	}
	
	protected void forceRecompile(ICarbideBuildConfiguration buildConfig, IPath mmpPath, boolean isTest, CarbideCommandLauncher launcher) {
		// we could delete the entire object code directory and any .rsc files generated by
		// the mmp, but it's easier to just clean the component
		String componentName = mmpPath.removeFileExtension().lastSegment();
		
		launcher.writeToConsole("\n***Cleaning component \"" + componentName + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");

		// only do a basic clean since we just want the object code and output files removed, nothing else
		List<String> argsList = new ArrayList<String>();
		if (isTest) {
			argsList.add(TEST_CMD);
		}
		argsList.add(CLEAN_CMD);
		argsList.add(buildConfig.getPlatformString().toLowerCase());
		argsList.add(buildConfig.getTargetString().toLowerCase());
		argsList.add(componentName);
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldCleanArgs().split(" ")) {
			argsList.add(arg);
		}
		
		if (!CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, argsList.toArray(new String[argsList.size()]), false)) {
			return;
		}
		
		launcher.writeToConsole("\n***Clean Complete\n");
	}
	
	protected void forceRelink(ICarbideBuildConfiguration buildConfig, IPath mmpPath) {
		// just want to re-link but not compile anything.  we need to delete some linker
		// dependency.  the most reliable is the output of the build.
		IPath outputPath = EpocEngineHelper.getHostPathForExecutable(buildConfig, mmpPath);
		if (outputPath != null) {
			File outputFile = outputPath.toFile();
			if (outputFile.exists()) {
				outputFile.delete();
			}
		}
	}

	public boolean invokeBldmakeCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, String[] bldmakeArgs) {
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IProject project = cpi.getProject();

		List<String> args = new ArrayList<String>(bldmakeArgs.length + 2);
		args.addAll(Arrays.asList(bldmakeArgs));
		args.add(0, "bldmake.pl"); //$NON-NLS-1$
		args.add(0, "-S"); //$NON-NLS-1$

		bldmakeArgs = args.toArray(new String[args.size()]);

		launcher.setErrorParserManager(buildConfig.getCarbideProject().getINFWorkingDirectory(), CarbideCPPBuilder.getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_BLDMAKE_MAKE));

		launcher.writeToConsole("\n***Invoking bldmake command\n");
		
		int retVal = launcher.executeCommand(PERL, bldmakeArgs, getResolvedEnvVars(buildConfig), cpi.getINFWorkingDirectory());
		if (retVal != 0) {
			launcher.writeToConsole("\n=== BLDMAKE Command failed with error code " + retVal + " ===");
			launcher.writeToConsole("\n***Stopping. Check the Problems view or Console output for errors.\n");
   			CarbideBuilderPlugin.createCarbideProjectMarker(project, IMarker.SEVERITY_ERROR,  "bldmake returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
			return false;
		}
		
		launcher.writeToConsole(launcher.getTimingStats());

		return true;
	}

	public boolean invokeAbldCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, String[] abldArgs) {
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IProject project = cpi.getProject();

		List<String> args = new ArrayList<String>(abldArgs.length + 2);
		args.addAll(Arrays.asList(abldArgs));
		args.add(0, launcher.getWorkingDirectory().setDevice(null).addTrailingSeparator().toOSString());
		args.add(0, "ABLD.PL"); //$NON-NLS-1$
		args.add(0, "-S"); //$NON-NLS-1$

		abldArgs = args.toArray(new String[args.size()]);

		launcher.setErrorParserManager(buildConfig.getCarbideProject().getINFWorkingDirectory(), buildConfig.getErrorParserList());

		launcher.writeToConsole("\n***Invoking abld command\n");
		
		int retVal = launcher.executeCommand(PERL, abldArgs, getResolvedEnvVars(buildConfig), cpi.getINFWorkingDirectory());
		if (retVal != 0) {
			launcher.writeToConsole("\n=== ABLD Command failed with error code " + retVal + " ===");
			launcher.writeToConsole("\n***Stopping. Check the Problems view or Console output for errors.\n");
   			CarbideBuilderPlugin.createCarbideProjectMarker(project, IMarker.SEVERITY_ERROR,  "abld returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
			return false;
		}
		
		launcher.writeToConsole(launcher.getTimingStats());

		return true;
	}
		
	public String [] getResolvedEnvVars(ICarbideBuildConfiguration config) {

		String[] vars = config.getEnvironmentVarsInfo().getResolvedEnvironmentVariables();
		
		if (removeMakeVariableOneTime) {
			removeMakeVariableOneTime = false;
			
			List<String> newVars = new ArrayList<String>();
			newVars.addAll(Arrays.asList(vars));

			// start at the end as it's probably there
			for (int i=newVars.size()-1; i>=0; i--) {
				String var = newVars.get(i);
				if (var.startsWith("MAKE=make -j")) {
					newVars.remove(var);
					break;
				}
			}
			
			vars = newVars.toArray(new String[newVars.size()]);
		}
		
		return vars;
	}
	
	public boolean needsBldmakeMakefileGeneration(ICarbideBuildConfiguration config) {
		ICarbideProjectInfo cpi = config.getCarbideProject();
		IPath bldInfPath = cpi.getAbsoluteBldInfPath();
		IPath bldInfDir = bldInfPath.removeLastSegments(1);

		if (!bldInfDir.toFile().exists()) {
			return true; // try to generate and let the build process flag the error
		}
		
		if (!bldInfDir.append(ABLD_BAT_NAME).toFile().exists()) {
			return true; // abld.bat does not exist, generate it
		}
		
		// check for the makefiles dir...		
		IPath prjBuildPath = getMakefileDirectory(config);
		if (!prjBuildPath.toFile().exists()) {
			return true;  // no build dir
		} else {
			// there should always be EXPORT.MAKE and EXPORTTEST.MAKE, PLATFORM.PM, and then {plat}.MAKE and {plat}TEST.MAKE, where
			// {plat} is the platform string, e.g. WINSCW, ARMV5.  if one of these doesn't exist then we need to run
			// bldmake bldfiles platform.  if they all exist, we need to get the oldest time stamp.  if that time stamp is
			// older than the bld.inf or any of its includes then we need to regenerate the makefiles.
			File makeFile = prjBuildPath.append("EXPORT.MAKE").toFile();
			if (!makeFile.exists()) {
				return true;
			}
			long oldestMakefileTimestamp = makeFile.lastModified();
			
			makeFile = prjBuildPath.append("EXPORTTEST.MAKE").toFile();
			if (!makeFile.exists()) {
				return true;
			}
			if (makeFile.lastModified() < oldestMakefileTimestamp) {
				oldestMakefileTimestamp = makeFile.lastModified();
			}

			makeFile = prjBuildPath.append("PLATFORM.PM").toFile();
			if (!makeFile.exists()) {
				return true;
			}
			if (makeFile.lastModified() < oldestMakefileTimestamp) {
				oldestMakefileTimestamp = makeFile.lastModified();
			}

			makeFile = prjBuildPath.append(config.getPlatformString() + ".MAKE").toFile();
			if (!makeFile.exists()) {
				return true;
			}
			if (makeFile.lastModified() < oldestMakefileTimestamp) {
				oldestMakefileTimestamp = makeFile.lastModified();
			}

			makeFile = prjBuildPath.append(config.getPlatformString() + "TEST.MAKE").toFile();
			if (!makeFile.exists()) {
				return true;
			}
			if (makeFile.lastModified() < oldestMakefileTimestamp) {
				oldestMakefileTimestamp = makeFile.lastModified();
			}
			
			// we need to check the variant hrh files as well
			File prefixFile = config.getSDK().getPrefixFile();
			if (prefixFile != null && prefixFile.lastModified() > oldestMakefileTimestamp) {
				return true;
			}

			for (File file : config.getPrefixFileIncludes()) {
				if (file.lastModified() > oldestMakefileTimestamp) {
					return true;
				}
			}

			// all make files exist.  now make sure the oldest of them is newer than the bld.inf or any of its includes
			final long finalOldestMakefileTimestamp = oldestMakefileTimestamp;
			Boolean regenerate = (Boolean)EpocEnginePlugin.runWithBldInfView(bldInfPath,
					new DefaultViewConfiguration(config, bldInfPath, new AcceptedNodesViewFilter()), 
					new BldInfViewRunnableAdapter() {
						public Object run(IBldInfView view) {
							for (IPath file : view.getReferencedFiles()) {
								if (file.toFile().lastModified() > finalOldestMakefileTimestamp) {
									return Boolean.TRUE;
								}
							}
							return Boolean.FALSE;
						}
				});
			
			return regenerate.booleanValue();
		}
	}
	
	public boolean generateAbldMakefileIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, IPath componentPath, boolean isTest) {
		return generateAbldMakefileIfNecessary(config, launcher, componentPath, isTest, new NullProgressMonitor()); 
	}

	
	public boolean generateAbldMakefileIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, IPath componentPath, boolean isTest, IProgressMonitor progress) {

		// generate the makefile if necessary
		if (needsAbldMakefileGeneration(config, componentPath)) {
			ICarbideProjectInfo cpi = config.getCarbideProject();
			IPath workingDir = cpi.getINFWorkingDirectory();

			launcher.setErrorParserManager(workingDir, CarbideCPPBuilder.getParserIdArray(config.getErrorParserId()));
			launcher.writeToConsole("\n***Generating abld makefile.\n");

			// delete the *.uid.cpp file if it exists so it gets regenerated.  makmake won't regenerate it if only an mmp include
			// file has changed.  see bug #4590 for details.
			if (config.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
				File uidFile = new Path(getMakefileForMMP(config, componentPath).getAbsolutePath()).removeFileExtension().addFileExtension("UID.CPP").toFile();
				if (uidFile.exists()) {
					uidFile.delete();
				}
			}
			
			List<String> abldArgs = new ArrayList<String>();
			abldArgs.add(0, "ABLD.PL"); //$NON-NLS-1$
			abldArgs.add(0, "-S"); //$NON-NLS-1$
			abldArgs.add(launcher.getWorkingDirectory().setDevice(null).addTrailingSeparator().toOSString());
			
			if (isTest) {
				abldArgs.add(TEST_CMD); //$NON-NLS-1$
			}
			
			abldArgs.add(MAKEFILE_CMD); //$NON-NLS-1$
			abldArgs.add(config.getPlatformString().toLowerCase());
			abldArgs.add(componentPath.removeFileExtension().lastSegment());
			
			for (String arg : config.getBuildArgumentsInfo().getAbldMakefileArgs().split(" ")) {
				abldArgs.add(arg);
			}
			
			launcher.writeToConsole("\n***Invoking abld command\n");
			
			int retVal = launcher.executeCommand(PERL, abldArgs.toArray(new String[abldArgs.size()]), getResolvedEnvVars(config), workingDir);
			launcher.writeToConsole(launcher.getTimingStats());
			
			if (retVal != 0){
				launcher.writeToConsole("\n***Abld returned with exit value = " + retVal);
				launcher.writeToConsole("\n***Stopping.\n");
				return false;
			}
			
			// now make our changes to the generated makefile
			if (areWeManagingTheMakeFiles) {
				try {
					updateMakefile(config, componentPath, progress);
				} catch (CoreException e) {
					CarbideBuilderPlugin.log(e);
					e.printStackTrace();
					//TODO is this enough?
				}
			}
		}
		
		return true;
	}

	protected boolean needsAbldMakefileGeneration(ICarbideBuildConfiguration config, IPath componentPath) {
		// if this is an extension makefile then we always do the makefile step.
		if (isExtensionMakefile(componentPath)) {
			return true;
		}
		
		ICarbideProjectInfo cpi = config.getCarbideProject();

		File makefile = getMakefileForMMP(config, componentPath);
		if (!makefile.exists()) {
			return true;
		}
		
		final long makefileTimestamp = makefile.lastModified();
		
		// we need to check the variant hrh files as well
		File prefixFile = config.getSDK().getPrefixFile();
		if (prefixFile != null && prefixFile.lastModified() > makefileTimestamp) {
			return true;
		}

		for (File file : config.getPrefixFileIncludes()) {
			if (file.lastModified() > makefileTimestamp) {
				return true;
			}
		}

		// see if the makefile is newer than the mmp and all of its includes
		Boolean regenerate = (Boolean)EpocEnginePlugin.runWithMMPData(componentPath,
				new DefaultMMPViewConfiguration(cpi.getProject(), config, new AcceptedNodesViewFilter()), 
				new MMPDataRunnableAdapter() {
					public Object run(IMMPData data) {
						for (IPath path : data.getReferencedFiles()) {
							if (path.toFile().lastModified() > makefileTimestamp) {
								return Boolean.TRUE;
							}
						}
						return Boolean.FALSE;
					}
			});
		
		if (regenerate.booleanValue()) {
			return true;
		}
		
		// now check to see if our makefile changes are there
		if (areWeManagingTheMakeFiles && !makeFileHasOurChanges(makefile)) {
			// if they are not then the user must have been building from the command line.  this means that
			// any dependency files that exist could be stale so we need to delete them.  we also need to
			// remove any object code since the corresponding dependency info will not be available.
			try {
				cleanupObjectCodeDirectory(new Path(makefile.getAbsolutePath()).removeLastSegments(1));
			} catch (Exception e) {
				CarbideBuilderPlugin.log(e);
	    		e.printStackTrace();
			}
			return true;
		}
		
		return false;
	}
	
	protected boolean hasMMPMakefileChanged(ICarbideBuildConfiguration config, IPath componentPath) {

		if (isExtensionMakefile(componentPath)) {
			return false;
		}
		
		ICarbideProjectInfo cpi = config.getCarbideProject();

		// only return true when it exists and the mmp or any of its includes is newer
		File makefile = getMakefileForMMP(config, componentPath);
		if (!makefile.exists()) {
			return false;
		}
		
		final long makefileTimestamp = makefile.lastModified();
		
		// we need to check the variant hrh files as well
		File prefixFile = config.getSDK().getPrefixFile();
		if (prefixFile != null && prefixFile.lastModified() > makefileTimestamp) {
			return true;
		}
		
		for (File file : config.getPrefixFileIncludes()) {
			if (file.lastModified() > makefileTimestamp) {
				return true;
			}
		}

		// see if the makefile is newer than the mmp and all of its includes
		Boolean regenerate = (Boolean)EpocEnginePlugin.runWithMMPData(componentPath,
				new DefaultMMPViewConfiguration(cpi.getProject(), config, new AcceptedNodesViewFilter()), 
				new MMPDataRunnableAdapter() {
					public Object run(IMMPData data) {
						for (IPath path : data.getReferencedFiles()) {
							if (path.toFile().lastModified() > makefileTimestamp) {
								return Boolean.TRUE;
							}
						}
						return Boolean.FALSE;
					}
			});
		
		return regenerate.booleanValue();
	}

	protected boolean makeFileHasOurChanges(File makefile) {
		// check the first line of our file for the CARBIDE_MAKEFILE_TEXT comment
		try {
			String text = new String(FileUtils.readFileContents(makefile, null));
			if (text.startsWith(CARBIDE_MAKEFILE_TEXT)) {
				return true;
			}
		} catch (CoreException e) {
		}
		return false;
	}

	protected void cleanupObjectCodeDirectory(IPath makefileDir) {
		// delete any .d, .dep, .o and .obj files in the makefile directory
		File files[] = makefileDir.toFile().listFiles(objectCodeDirectoryFileFilter);
		
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
		
		// now delete any .d or .dep files in the udeb/urel directories
		files = makefileDir.append("udeb").toFile().listFiles(objectCodeDirectoryFileFilter);
		
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
		
		files = makefileDir.append("urel").toFile().listFiles(objectCodeDirectoryFileFilter);
		
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
	}
	
	public File getMakefileForMMP(ICarbideBuildConfiguration config, IPath componentPath) {
		// create a path to the directory where the make files live
		IPath makefilePath = new Path(config.getSDK().getEPOCROOT()).append(EPOC_BUILD_DIR);
		
		// in EKA1 kits, you append the path to the mmp file to the epoc32 build directory.  in EKA2, they
		// changed it to be the path to the bld.inf file. (see bldmake.pl sub CreatePlatMak())
		if (config.getSDK().isEKA1()) {
			makefilePath = makefilePath.append(componentPath.removeLastSegments(1).setDevice(null));
		} else {
			makefilePath = makefilePath.append(config.getCarbideProject().getINFWorkingDirectory().setDevice(null));
		}
		
		// each mmp file has its own directory
		String mmpName = componentPath.removeFileExtension().lastSegment().toUpperCase();
		makefilePath = makefilePath.append(mmpName);

		// each platform has its own directory
		String platformName = "";
		if (EpocEngineHelper.hasFeatureVariantKeyword(config.getCarbideProject(), componentPath)){
			platformName = config.getPlatformString().toUpperCase();
		} else {
			platformName = config.getBasePlatformForVariation();
		}
		
		makefilePath = makefilePath.append(config.getBasePlatformForVariation().toUpperCase());

		// and the makefile has the form MMPNAME.PLATFORM
		makefilePath = makefilePath.append(mmpName + "." + platformName);
		
		if (!makefilePath.toFile().exists()){
			makefilePath = makefilePath.removeLastSegments(1);
			makefilePath = makefilePath.append(mmpName + "." + platformName + ".DEFAULT");
		}
		
		return makefilePath.toFile();
	}
	
	protected void updateMakefile(ICarbideBuildConfiguration config, IPath componentPath, IProgressMonitor progress) throws CoreException {
		// ignore extension makefiles.
		if (isExtensionMakefile(componentPath)) {
			return;
		}
		
		if (progress.isCanceled()) {
			return;
		}
		
		File makefile = getMakefileForMMP(config, componentPath);
		if (!makefile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Makefile " + makefile.getAbsolutePath() + " doesn't exist.", null)); //$NON-NLS-1$
		}

		IPath makefilePath = new Path(makefile.getAbsolutePath());

		IModelProvider modelProvider = EpocEnginePlugin.getMakefileModelProvider();
		IModel model = modelProvider.getSharedModel(makefilePath);
		if (model == null) {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Unable to create model for makefile " + makefile.getAbsolutePath(), null)); //$NON-NLS-1$
		}
		
		IMakefileView view = (IMakefileView)model.createView(new DefaultGNUMakefileViewConfiguration(config.getCarbideProject(), new AcceptedNodesViewFilter()));
		if (view == null) {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Unable to create view for makefile " + makefile.getAbsolutePath(), null)); //$NON-NLS-1$
		}

		// copy the fix_dep_file_paths.pl file to the makefile directory
		IPath makefileDirPath = makefilePath.removeLastSegments(1).addTrailingSeparator();
		createFixupDepsFile(makefileDirPath);

		// insert our tag string - note, this will be at the top of the file
		view.insertText(null, CARBIDE_MAKEFILE_TEXT + view.getEOL());

		// insert the includes after the macros so we can use them.
		IMacroDefinition[] lastMacro = view.getAllMacroDefinitions("EPOCASSPLINKUREL");
		if (lastMacro.length != 1) {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Unable to include dependencies in makefile " + makefile.getAbsolutePath(), null)); //$NON-NLS-1$
		}

		// .d for everything but WINSCW
		String dependencyFileExt = "d";
		if (config.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
			dependencyFileExt = "dep";
		}

		IPath udebDirPath = makefilePath.removeLastSegments(1).append("udeb").addTrailingSeparator();
		IPath urelDirPath = makefilePath.removeLastSegments(1).append("urel").addTrailingSeparator();
		
		view.insertText(lastMacro[0], "-include " + urelDirPath + "*." + dependencyFileExt + view.getEOL());
		view.insertText(lastMacro[0], "-include " + udebDirPath + "*." + dependencyFileExt + view.getEOL());
		view.insertText(lastMacro[0], "-include " + makefileDirPath + "*.d" + view.getEOL());
		view.insertText(lastMacro[0], view.getEOL() + "# Include dependency files" + view.getEOL());
		
		view.insertText(lastMacro[0], ".DELETE_ON_ERROR:" + view.getEOL());
		view.insertText(lastMacro[0], view.getEOL() + "# Delete targets if/when updating them fails." + view.getEOL());

		// now add the -MD switch to the compiler args
		final String platform = config.getPlatformString();
		if (platform.equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
			// append the -MD and -gccdep switches to the CWFLAGS macro
			IMacroDefinition[] macros = view.getAllMacroDefinitions("CWFLAGS");
			if (macros.length < 1) {
				throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "CWFLAGS macro not found in makefile", null)); //$NON-NLS-1$
			}
			IMacroDefinition macro = macros[macros.length - 1];
			// add the switches before to the end of the macro.  toString may add  a line delimiter to the end so strip that off
			String macroText = macro.toString();
			if (macroText.endsWith("\n")) {
				macroText = macroText.substring(0, macroText.length() - 1);
			}
			macroText = macroText + " -MD -gccdep" + view.getEOL();
			view.replaceDirective(macro, macroText);
			
		} else if (platform.equals(ISymbianBuildContext.GCCE_PLATFORM)) {
			// append the -MD switch to the CCFLAGS macro.  there may be more than one so take the last one
			IMacroDefinition[] macros = view.getAllMacroDefinitions("CCFLAGS");
			if (macros.length < 1) {
				throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "CCFLAGS macro not found in makefile", null)); //$NON-NLS-1$
			}
			IMacroDefinition macro = macros[macros.length - 1];
			// add the switches before to the end of the macro.  toString may add  a line delimiter to the end so strip that off
			String macroText = macro.toString();
			if (macroText.endsWith("\n")) {
				macroText = macroText.substring(0, macroText.length() - 1);
			}
			macroText = macroText + " -MD" + view.getEOL();
			view.replaceDirective(macro, macroText);
			
		} else if (platform.equals(ISymbianBuildContext.ARMI_PLATFORM) ||
				platform.equals(ISymbianBuildContext.ARM4_PLATFORM) ||
				platform.equals(ISymbianBuildContext.THUMB_PLATFORM)) {

			// append the -MD switch to the GCCFLAGS macro
			IMacroDefinition[] macros = view.getAllMacroDefinitions("GCCFLAGS");
			if (macros.length < 1) {
				throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "GCCFLAGS macro not found in makefile", null)); //$NON-NLS-1$
			}
			IMacroDefinition macro = macros[macros.length - 1];
			// add the switches before to the end of the macro.  toString may add  a line delimiter to the end so strip that off
			String macroText = macro.toString();
			if (macroText.endsWith("\n")) {
				macroText = macroText.substring(0, macroText.length() - 1);
			}
			macroText = macroText + " -MD" + view.getEOL();
			view.replaceDirective(macro, macroText);
			
		} else {
			// assuming some version of RVCT
			IMacroDefinition[] macros = view.getAllMacroDefinitions("ARMCCFLAGS");
			if (macros.length > 0) {
				IMacroDefinition macro = macros[macros.length - 1];
				// add the switches before to the end of the macro.  toString may add  a line delimiter to the end so strip that off
				String macroText = macro.toString();
				if (macroText.endsWith("\n")) {
					macroText = macroText.substring(0, macroText.length() - 1);
				}
				macroText = macroText + " --md" + view.getEOL();
				view.replaceDirective(macro, macroText);
			} else {
				// newer kits (it looks like 9.4 and later) use different make variables
				macros = view.getAllMacroDefinitions("CCFLAGS");
				if (macros.length > 0) {
					// there may be more than one CCFLAGS macro so take the last one
					IMacroDefinition macro = macros[macros.length - 1];
					// add the switches before to the end of the macro.  toString may add  a line delimiter to the end so strip that off
					String macroText = macro.toString();
					if (macroText.endsWith("\n")) {
						macroText = macroText.substring(0, macroText.length() - 1);
					}
					macroText = macroText + " --md" + view.getEOL();
					view.replaceDirective(macro, macroText);
				} else {
					throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Compiler flags macro (ARMCCFLAGS/CCFLAGS) not found in makefile", null)); //$NON-NLS-1$
				}
			}
		}

		final String dep_file_paths_perl_script = "\"" + makefileDirPath.append(FIXUP_DEPFILES_FILE).toOSString() + "\"";
		
		// now add preprocess commands to the resource file rules.  if ALT_PRE env variable is set then
		// use scpp.exe, otherwise use cpp.exe.  pipe the output to a dependency file in the makefile directory.
		String cpp = "cpp.exe";
		for (String var : getResolvedEnvVars(config)) {
			if (var.compareTo("ALT_PRE") == 0 || var.startsWith("ALT_PRE=")) {
				cpp = "rcpp.exe";
				break;
			}
		}
		
		for (ICommand resourceCmd : view.findCommandsInvoking("perl -S epocrc.pl")) {
			
			Object parent = resourceCmd.getParent();
			if (parent instanceof ITargetRule) {
				ITargetRule rule = (ITargetRule)parent;

				// need to get a new copy of the rule.  when we make changes, they are committed and
				// the makefile is re-parsed so the line number get updated in case any lines were added
				// or removed.  if we use the "stale" copy we'd be making the changes to the wrong lines!
				rule = view.findRuleForTarget(rule.getTarget().toString(), true);
				if (rule == null) {
					throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Resource rule not found in makefile", null)); //$NON-NLS-1$
				}

				// figure out the dependency file name
				String depFileName = rule.getTarget().toString();
				int lastdelimiter = depFileName.lastIndexOf("\\");
				if (lastdelimiter < 0) {
					throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Failed trying to build cpp command", null)); //$NON-NLS-1$
				}
				depFileName = depFileName.substring(lastdelimiter) + ".d";

				// now update the rule so it uses our dependency macro
				String changedRule = rule.toString();

				// now replace the $(DEPEND) with $(DEPENDdepfilename)
				changedRule = changedRule.replaceFirst("DEPEND", "DEPEND" + depFileName) + view.getEOL();

				// fix up line endings.  IDirective#toString uses \n whereas the rest of the makefile
				// uses \r\n\.  CDT could change though so check the line endings before changing them.
				changedRule = changedRule.replaceAll("(?<!\r)\n", view.getEOL());
	
				// now replace the old rule with our new one
				view.replaceDirective(rule, changedRule);

				// calculate the full path to the dependency file
				String depFilePath = makefileDirPath.append(depFileName).toOSString();

				// now create a dependency on the .rsc to the .rsc.d
				String newRule = view.getEOL() + "# Create the dependency on the resource dependency file" + view.getEOL();
				newRule = newRule + rule.getTarget().toString() + ": " + depFilePath + view.getEOL();
				
				// now create the rule to create the .rsc.d file
				newRule = newRule + view.getEOL() + "# Create the rule to generate the actual dependency file" + view.getEOL();
				newRule = newRule + depFilePath + ":" + view.getEOL();
				
				// now add the cpp command to our new rule
				newRule = newRule + "\t" + cpp + " -undef -M -nostdinc ";

				// add the compiler prefix file if any
				IPath compilerPrefix = config.getCompilerPrefixFile();
				if (compilerPrefix != null) {
					newRule = newRule + "-include \"" + compilerPrefix.toOSString() + "\" "; 
				}
				
				// get the includes and any resource macros from the actual call to epocrc.pl
				String rcompCmd = resourceCmd.toString();
				int firstInclude = rcompCmd.indexOf("-I");
				int dashU = rcompCmd.lastIndexOf("-u");
				int dashO = rcompCmd.lastIndexOf("-o$@");
				if (firstInclude < 0 || dashU < 0 || dashO < 0) {
					throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Failed trying to build cpp command", null)); //$NON-NLS-1$
				}
				
				// strip off everything before the first -I and everything after the .rss path
				rcompCmd = rcompCmd.substring(firstInclude, dashO);
				
				// strip off any uid switches after the rss file - see bug #5196
				int uidSwitch = rcompCmd.indexOf("-uid");
				if (uidSwitch >= 0) {
					rcompCmd = rcompCmd.substring(0, uidSwitch);
				}
				
				newRule += rcompCmd;				
				
				// find the macros we need to pass, and replace the -u switch with them
				String macros = null;

				if (platform.equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
					// the macros are listed in the CWDEFS macro
					IMacroDefinition[] defs = view.getAllMacroDefinitions("CWDEFS");
					if (defs.length != 1) {
						throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "CWDEFS macro not found in makefile", null)); //$NON-NLS-1$
					}

					// need to expand macros here
					macros = defs[0].getValue().toString();
					macros = view.expandAllMacrosInString(macros);

					// remove quotes, and change -d to -D
					macros = macros.replaceAll("\"", "");
					macros = macros.replaceAll("-d ", "-D");

				} else if (platform.equals(ISymbianBuildContext.GCCE_PLATFORM)) {
					// the macros are listed in the CCDEFS macro
					IMacroDefinition[] defs = view.getAllMacroDefinitions("CCDEFS");
					if (defs.length != 1) {
						throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "CCDEFS macro not found in makefile", null)); //$NON-NLS-1$
					}
					
					// need to expand macros here
					macros = defs[0].getValue().toString();
					macros = view.expandAllMacrosInString(macros);

				} else if (platform.equals(ISymbianBuildContext.ARMI_PLATFORM) ||
						platform.equals(ISymbianBuildContext.ARM4_PLATFORM) ||
						platform.equals(ISymbianBuildContext.THUMB_PLATFORM)) {
					// the macros are listed in the GCCDEFS macro
					IMacroDefinition[] defs = view.getAllMacroDefinitions("GCCDEFS");
					if (defs.length != 1) {
						throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "GCCDEFS macro not found in makefile", null)); //$NON-NLS-1$
					}

					// need to expand macros here
					macros = defs[0].getValue().toString();
					macros = view.expandAllMacrosInString(macros);

				} else {
					// assuming some version of RVCT
					// the macros are listed in the ARMCCDEFS macro
					IMacroDefinition[] defs = view.getAllMacroDefinitions("ARMCCDEFS");
					if (defs.length != 1) {
						// newer kits (it looks like 9.4 and later) use different make variables
						defs = view.getAllMacroDefinitions("CCDEFS");
						if (defs.length != 1) {
							throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Compiler defines macro (ARMCCDEFS/CCDEFS) not found in makefile", null)); //$NON-NLS-1$
						}
					}

					// need to expand macros here
					macros = defs[0].getValue().toString();
					macros = view.expandAllMacrosInString(macros);

				}

				if (macros != null) {
					// now strip off any macros that were'nt resolved
					macros = macros.replaceAll("\\$\\(.*?\\)", "");

					// cleanup the __PRODUCT_INCLUDE__ macro if necessary
					macros = macros.replaceFirst("\\\\\"", "\"<" + makefilePath.getDevice());
					macros = macros.replaceFirst("\\\\\"", ">\"");
					macros = macros.replaceAll("\\\\", "\\\\\\\\");

					newRule = newRule.replaceFirst("-u ", macros);
				}

				// add the compiler prefix file if any
				ISBVPlatform sbvPlatform = config.getSDK().getSBVCatalog().findPlatform(config.getPlatformString());
				File sdkPrefix = config.getSDK().getPrefixFile();
				if (sbvPlatform != null){
					// might be an alternate HRH file to use
					IPath varVarHRH = sbvPlatform.getBuildVariantHRHFile();
					if (!varVarHRH.toFile().equals(sdkPrefix) && varVarHRH.toFile().exists()){
						sdkPrefix = varVarHRH.toFile();
					} 
				}
				
				if (sdkPrefix != null && sdkPrefix.exists()) {
					newRule = newRule + "-include \"" + sdkPrefix.getAbsolutePath() + "\" "; 
				}

				
				newRule += " -o " + depFilePath + view.getEOL();
				
				// for some reason cpp.exe doesn't like paths without the drive letter when working on a subst'ed drive.
				newRule = newRule.replaceAll("\"\\\\", "\"" + makefilePath.getDevice() + "\\\\");
				
				// now add the call to cleanup the dependency file
				newRule = newRule + "\tperl -S " + dep_file_paths_perl_script + " " + depFilePath + " cpp" + view.getEOL() + view.getEOL();

				view.insertTextBefore(newRule, rule);
			}
		}

		// we need to cleanup the dependency files
		IRule[] rules = view.getMakefile().getRules();
		if (rules != null && rules.length > 0) {
			for (IRule rule : rules) {
				// some old kits like S60 1.2 used .obj for winscw builds
				if (rule.getTarget().toString().endsWith(".o") || rule.getTarget().toString().endsWith(".obj")) {
					// .s files will not have dependency files
					ICommand[] cmds = rule.getCommands();
					if (cmds.length > 0) {
						String cmd = cmds[0].toString().toLowerCase();
						if (cmd.endsWith(".s") || cmd.endsWith(".s\n")) {
							continue;
						}
					}
					
					// need to get a new copy of the rule.  when we make changes, they are committed and
					// the makefile is reparsed so the line number get updated in case any lines were added
					// or removed.  if we use the "stale" copy we'd be making the changes to the wrong lines!
					rule = view.findRuleForTarget(rule.getTarget().toString(), true);
					if (rule == null) {
						throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Rule not found in makefile", null)); //$NON-NLS-1$
					}

					String newRule = rule.toString();

					IPath destPath = new Path(rule.getTarget().toString()).removeFileExtension().addFileExtension(dependencyFileExt);
					String sourcePath = config.getCarbideProject().getINFWorkingDirectory().addTrailingSeparator().toOSString() + destPath.lastSegment();

					// RVCT and GCC98 generate the dependency files in the bld.inf directory.  create a command to move
					// them to the object code directory in the object file rules.  note that we do this from within the
					// makefile rather than outside of it for two reasons:
					// 1) There could be source files with the same name in different mmp files, so when building from
					//    the bld.inf you could wind up overwriting dependency files.
					// 2) This will keep the makefiles we alter working from the command line as well (if the user does abld target)
					//
					// The Symbian GCC Improvement project updated the GCC for EKA1 kits to GCC 3.0.  This behaves much like GCCE.  Rather than
					// running gcc to get the version (which could be tricky), we can just check for the folder
					// \epoc32\gcc\lib\gcc-lib\arm-epoc-pe\3.0-psion-98r2.  If it exists, we'll assume GCC 3.0 and treat it like GCCE.
					boolean isGCC30 = false;
					if (config.getSDK().getToolsPath().removeLastSegments(1).append("gcc\\lib\\gcc-lib\\arm-epoc-pe\\3.0-psion-98r2").toFile().exists()) {
						isGCC30 = true;
					}
					
					boolean moveDepFile = false;
					if (!platform.equals(ISymbianBuildContext.EMULATOR_PLATFORM) &&
							!platform.equals(ISymbianBuildContext.GCCE_PLATFORM)) {

						if (platform.equals(ISymbianBuildContext.ARM4_PLATFORM) ||
							platform.equals(ISymbianBuildContext.ARMI_PLATFORM) ||
							platform.equals(ISymbianBuildContext.THUMB_PLATFORM)) {
							if (!isGCC30) {
								moveDepFile = true;
							}
						} else {
							// some form of ARMV5|6
							moveDepFile = true;
						}

					}
					
					if (moveDepFile) {
						newRule = newRule + "\tperl -S ecopyfile.pl " + sourcePath + " " + destPath.toOSString() + view.getEOL();
						newRule = newRule + "\tdel " + sourcePath + view.getEOL() + view.getEOL();
					}
					
					String plat = "";
					if (platform.equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						plat = "winscw";
					} else if (platform.equals(ISymbianBuildContext.GCCE_PLATFORM)) {
						plat = "gcce";
					} else if (platform.equals(ISymbianBuildContext.ARM4_PLATFORM) ||
							platform.equals(ISymbianBuildContext.ARMI_PLATFORM) ||
							platform.equals(ISymbianBuildContext.THUMB_PLATFORM)) {
						if (isGCC30) {
							plat = "gcce";
						} else {
							plat = "gcc98";
						}
					} else {
						// assume some version of rvct.  no need to do anything since the dep files are generated correctly
					}
					if (plat.length() > 0) {
						newRule = newRule + "\tperl -S " + dep_file_paths_perl_script + " " + destPath.toOSString() + " " + plat + view.getEOL() + view.getEOL();
					}

					// fix up line endings.  IDirective#toString uses \n whereas the rest of the makefile
					// uses \r\n\.  CDT could change though so check the line endings before changing them.
					newRule = newRule.replaceAll("(?<!\r)\n", view.getEOL());

					// now replace the old rule with our new one
					view.replaceDirective(rule, newRule);
					
					// fix for bug #7748.  we may need to update the listing rule as well since it could generate
					// a dependency file
					if (!platform.equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						// only non-WINSCW platforms are affected
						rule = view.findRuleForTarget(new Path(rule.getTarget().toString()).removeFileExtension().addFileExtension("lis").toOSString(), true);
						if (rule != null) {
							newRule = rule.toString();

							if (moveDepFile) {
								newRule = newRule + "\tperl -S ecopyfile.pl " + sourcePath + " " + destPath.toOSString() + view.getEOL();
								newRule = newRule + "\tdel " + sourcePath + view.getEOL() + view.getEOL();
							}

							if (plat.length() > 0) {
								newRule = newRule + "\tperl -S " + dep_file_paths_perl_script + " " + destPath.toOSString() + " " + plat + view.getEOL() + view.getEOL();
							}

							// fix up line endings.  IDirective#toString uses \n whereas the rest of the makefile
							// uses \r\n\.  CDT could change though so check the line endings before changing them.
							newRule = newRule.replaceAll("(?<!\r)\n", view.getEOL());

							// now replace the old rule with our new one
							view.replaceDirective(rule, newRule);
						}
					}
				}
			}
		}
		
		// armcc will not generated an output file when -E is passed (preprocess) along with --md (see bug #4873)
		// find all rules for .pre files and expand the macros and remove --md
		rules = view.getMakefile().getRules();
		if (rules != null && rules.length > 0) {
			for (IRule rule : rules) {
				if (rule.getTarget().toString().endsWith(".pre")) {
					// sanity check that it's a preprocess command
					// some kits will use a PREPROCESSOR_OPTION macro which is in the #included compilation config file, so expand the
					// macros first.
					String expandedRule = view.expandAllMacrosInString(rule.toString());
					
					if (expandedRule.indexOf("-E ") > 0) {
						// need to get a new copy of the rule.  when we make changes, they are committed and
						// the makefile is re-parsed so the line number get updated in case any lines were added
						// or removed.  if we use the "stale" copy we'd be making the changes to the wrong lines!
						rule = view.findRuleForTarget(rule.getTarget().toString(), true);
						if (rule == null) {
							throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Rule not found in makefile", null)); //$NON-NLS-1$
						}

						String newRule = expandedRule.replaceFirst("--md ", "");

						// fix up line endings.  IDirective#toString uses \n whereas the rest of the makefile
						// uses \r\n\.  CDT could change though so check the line endings before changing them.
						newRule = newRule.replaceAll("(?<!\r)\n", view.getEOL());

						// now replace the old rule with our new one
						view.replaceDirective(rule, newRule);
					}
				}
			}
		}

		// append the default rule.  this is the catch-all rule for anything it doesn't find a rule for elsewhere in the
		// makefile.  this basically tells make to not complain when a source or header doesn't exist, but execute the
		// commands for the rule in question and let the tool (like a compiler) handle the error.
		view.appendText(".DEFAULT: ;" + view.getEOL());
		
		// now commit the changes and release the file
		while (true) {
			try {
				view.commit();
				break;
			} catch (IllegalStateException e) {
				if (!view.merge()) {
					view.revert();
				}
			}
		}

		view.dispose();
		modelProvider.releaseSharedModel(model);
	}
	
	private void createFixupDepsFile(IPath directory) throws CoreException {
		File file = directory.append(FIXUP_DEPFILES_FILE).toFile();
		try {
			if (!file.exists() && !file.createNewFile()) {
				throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Unable to create fix_dep_file_paths.pl in " + directory.toOSString(), null)); //$NON-NLS-1$
			}
			
	        BufferedInputStream in = new BufferedInputStream(FileLocator.openStream(CarbideBuilderPlugin.getDefault().getBundle(), new Path("data").append(FIXUP_DEPFILES_FILE), false)); //$NON-NLS-1$
	        FileOutputStream out = new FileOutputStream(file);

            byte[] buf = new byte[1024];
            int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

	        in.close();
	        out.close();
			
		} catch (IOException e) {
			CarbideBuilderPlugin.log(e);
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Unable to create fix_dep_file_paths.pl contents in " + directory.toOSString(), null)); //$NON-NLS-1$
		}
	}
	
	protected boolean isExtensionMakefile(IPath componenetPath) {
		// mmp files will always have the mmp extension, even if they have a different extension
		// in the bld.inf file
		if (FileUtils.getSafeFileExtension(componenetPath).compareToIgnoreCase("mmp") == 0) {
			return false;
		}
		return true;
	}

	public IPath getMakefileDirectory(ICarbideBuildConfiguration config) {
		return new Path(config.getSDK().getEPOCROOT()).append(EPOC_BUILD_DIR).append(config.getCarbideProject().getAbsoluteBldInfPath().removeLastSegments(1).setDevice(null));
	}
}
