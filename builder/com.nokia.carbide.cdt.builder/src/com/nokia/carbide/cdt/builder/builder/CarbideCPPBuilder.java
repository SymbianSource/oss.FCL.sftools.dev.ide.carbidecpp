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
package com.nokia.carbide.cdt.builder.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.ICModelMarker;
import org.eclipse.cdt.core.resources.IConsole;
import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultViewConfiguration;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cdt.builder.PKGViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.IROMBuilderInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cdt.internal.builder.CarbideSBSv1Builder;
import com.nokia.carbide.cdt.internal.builder.CarbideSBSv2Builder;
import com.nokia.carbide.cdt.internal.builder.ICarbideBuilder;
import com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage;
import com.nokia.carbide.cdt.internal.builder.ui.MMPSelectionDialog;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.MMPDataRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.PKGViewRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPLanguage;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPData;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.internal.x86build.X86BuildPlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.EPKGLanguage;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGEmbeddedSISFile;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGHeader;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGInstallFile;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.PKGModelHelper;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * Main interface for invoking different build stages.
 *
 */
public class CarbideCPPBuilder extends IncrementalProjectBuilder {
	
	//TODO do we need to pipe output of compiles to files so we get then errors/warnings in order?
	
	public static final String BUILDER_ID = "com.nokia.carbide.cdt.builder.carbideCPPBuilder"; //$NON-NLS-1$

	public static final int BUILD_COMPONENT_ACTION = 0;
	public static final int CLEAN_COMPONENT_ACTION = 1;
	public static final int FREEZE_COMPONENT_ACTION = 2;
	
    public static final String RESOLVED_PKG_PREFIX = "_resolved"; //$NON-NLS-1$
    
    private static final boolean SHOW_ENV_VARS = true;
    
    private static final String PKG_SYMBOL_EPOCROOT = "$(EPOCROOT)"; //$NON-NLS-1$
    private static final String PKG_SYMBOL_PLATFORM = "$(PLATFORM)"; //$NON-NLS-1$
    private static final String PKG_SYMBOL_TARGET = "$(TARGET)"; //$NON-NLS-1$
    
    private static final String DEFAULT_KEY_NAME = "key-gen.key"; //$NON-NLS-1$
    private static final String DEFAULT_CERT_NAME = "cert-gen.cer"; //$NON-NLS-1$
    private static final String DEAULT_PASSWORD = "DefaultPassword"; //$NON-NLS-1$
    private static final String MAKEKEYS_EXE = "makekeys.exe"; //$NON-NLS-1$
    private static final String MAKESIS_EXE = "makesis.exe"; //$NON-NLS-1$
    
    private static final String SIGNSIS_EXE = "signsis.exe"; //$NON-NLS-1$
    
	private static List<IPath> normalMakMakePaths = Collections.synchronizedList(new ArrayList<IPath>());
	private static List<IPath> testMakMakePaths = Collections.synchronizedList(new ArrayList<IPath>());
	
	private static ICarbideBuilder v1Builder = new CarbideSBSv1Builder();
	private static ICarbideBuilder v2Builder = new CarbideSBSv2Builder();
	
	private static ICarbideBuilder getBuilder(IProject project) {
		if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
			return v2Builder;
		} else {
			return v1Builder;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("unchecked")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {

		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		
		IProject currentProject = getProject();
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(currentProject);
		if (cpi != null) {
			if (cpi.incrementalBuilderEnabled()) {
				// the callOnEmptyDelta builder attribute is set, so we get called
				// not matter what, even with an empty delta.  this means that when the
				// incremental build option is disabled, we still get called.  when it's
				// enabled, we just check the delta.  if null, we need to build.  otherwise
				// see what's changed and decide from that.
				IResourceDelta delta = getDelta(currentProject);
				if (delta != null) {
					// something in the workspace has changed.  see if a rebuild is needed.
					if (!shouldRebuild(delta)) {

						// also rebuild if the project has errors
						boolean hasErrors = false;
				    	try {
							for (IMarker currMarker : getProject().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE)){
								if (currMarker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR) == IMarker.SEVERITY_ERROR){
									hasErrors = true;
									break;
								}
							}
							
						} catch (CoreException e){
							e.printStackTrace();
						}
						
						if (!hasErrors) {
							return null;
						}
					}
				}
			}
			
			ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
			if (defaultConfig != null) {
				CarbideCommandLauncher launcher = new CarbideCommandLauncher(currentProject, monitor, getParserIdArray(defaultConfig.getErrorParserId()), cpi.getINFWorkingDirectory());
				launcher.showCommand(true);
				invokeBuild(defaultConfig, launcher, subMonitor.newChild(1), true);
			} else {
	   			CarbideBuilderPlugin.createCarbideProjectMarker(currentProject, IMarker.SEVERITY_ERROR,  "Project has no configurations.", IMarker.PRIORITY_HIGH);
			}
		}
		monitor.done();

		return null;
	}
	
	protected boolean shouldRebuild(IResourceDelta delta) {
		// check for any changed files.  note that even if files are removed or added, they won't
		// affect the build unless the bld.inf or mmp files change.  those files will be picked up
		// by this check, so no need to worry about checking for added and removed resources.
		// I thought about maybe checking by file extension, but there's really no way for us to know the
		// comprehensive list.  note that they could change the input to a hlp file, any random
		// header file with any extension, even .txt files that are included in a pkg file.  so
		// at least for now we'll leave it as rebuilding when any file in the workspace changes.
		for (IResourceDelta child : delta.getAffectedChildren(IResourceDelta.CHANGED)) {
			IResource resource = child.getResource();
			if (resource != null) {
				if (resource.getType() == IResource.FILE) {
					// ignore changes to the .cproject and .project files
					if (!resource.getName().equals(".project") && !resource.getName().equals(".cproject")) {
						return true;
					}
				}
			}
			if (shouldRebuild(child)) {
				return true;
			}
		}
		return false;
	}
	
	protected void clean(IProgressMonitor monitor) throws CoreException {

		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		
		IProject currentProject = getProject();
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(currentProject);
		if (cpi != null) {
			ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
			if (defaultConfig != null) {
				invokeClean(defaultConfig, subMonitor.newChild(1), true);
			} else {
	   			CarbideBuilderPlugin.createCarbideProjectMarker(currentProject, IMarker.SEVERITY_ERROR,  "Project has no configurations.", IMarker.PRIORITY_HIGH);
			}
		}
		
		monitor.done();
	}
	
	/**
	 * Invokes a build for the given build configuration.  Output is piped to the given console and progress is reported
	 * using the given monitor.  Make files are created/updated if necessary.
	 * @param buildConfig the build configuration to build
	 * @param launcher the Carbide launcher
	 * @param monitor the progress monitor to use to report progress
	 * @param clearMarkers true if project markers should be cleared before action is taken
	 */
	public static void invokeBuild(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, IProgressMonitor monitor, boolean clearMarkers) {
		
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		
		if (clearMarkers) {
    		try {
    			removeAllMarkers(cpi.getProject());
    		} catch (CoreException e) {
    			e.printStackTrace();
    		}
        }

		calculateComponentLists(buildConfig, launcher);

		launcher.startTimingStats();

		launcher.writeToConsole("\n***Building project \"" + cpi.getProject().getName() + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");
		
		getBuilder(cpi.getProject()).preBuildStep(buildConfig, launcher);
		
		runPreBuildChecks(buildConfig, launcher);

		if (SHOW_ENV_VARS){ 
			launcher.writeToConsole("\n***Printing environment variables modified from default:\n");
			String[] modEnvVars = getModifiedEnvVars(buildConfig);
			for (String envVar : modEnvVars){
				launcher.writeToConsole(envVar + "\n");
			}
			launcher.writeToConsole("\n");	
			launcher.writeToConsole("Working Directory: " + cpi.getINFWorkingDirectory() + "\n\n");	
		}

		boolean success = true;
		if (cpi.isBuildingFromInf()) {
			success = buildAllComponents(buildConfig, launcher, monitor);
		} else {
			success = buildComponentSubset(buildConfig, launcher, monitor);
		}

		// error codes are not always returned from abld.pl and the like.
		// check for error markers on the project as well in case the error
		// parsers picked anything up.
		if (success && !projectHasBuildErrors(cpi.getProject())) {
			launcher.writeToConsole("\n***Build Complete\n");

			// build any sis files
			SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
			invokeSISBuilder(buildConfig, launcher, subMonitor.newChild(1));

			// build ROM if necessary
			subMonitor = SubMonitor.convert(monitor, 1);
			invokeROMBuilder(buildConfig, launcher, subMonitor.newChild(1));

		} else {
			launcher.writeToConsole("\n***Errors were detected in build. See the Problems or Console view for details.\n");
		}
		
		launcher.writeToConsole(launcher.getTimingStats());

		// only refresh if the auto refresh workspace pref is turned off
        if (!ResourcesPlugin.getPlugin().getPluginPreferences().getBoolean(ResourcesPlugin.PREF_AUTO_REFRESH)) {
    		// refresh the file system to pick up any newly created files under the project
    		try {
    			cpi.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
    		} catch (CoreException e) {
    			// no file system refresh
    			e.printStackTrace();
    		}
        }
	}
	
	/**
	 * Invokes a clean for the given build configuration.  Output is piped to the given console and progress is reported
	 * using the given monitor.  Make files are created/updated if necessary.
	 * @param buildConfig the build configuration to clean
	 * @param monitor the progress monitor to use to report progress
	 * @param clearMarkers true if project markers should be cleared before action is taken
	 */
	public static void invokeClean(ICarbideBuildConfiguration buildConfig, IProgressMonitor monitor, boolean clearMarkers) {
		
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IProject project = cpi.getProject();
		
		if (clearMarkers) {
    		try {
    			removeAllMarkers(project);
    		} catch (CoreException e) {
    			e.printStackTrace();
    		}
        }

		getBuilder(cpi.getProject()).preCleanStep(buildConfig);

		CarbideCommandLauncher launcher = new CarbideCommandLauncher(project, monitor, getParserIdArray(buildConfig.getErrorParserId()), cpi.getINFWorkingDirectory());
		launcher.showCommand(true);

		calculateComponentLists(buildConfig, launcher);
		
		launcher.startTimingStats();

		launcher.writeToConsole("\n***Cleaning project \"" + project.getName() + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");

		if (cpi.isBuildingFromInf()) {
			cleanAllComponents(buildConfig, launcher, monitor);
		} else {
			cleanComponentSubset(buildConfig, launcher, monitor);
		}
		
		launcher.writeToConsole("\n***Clean Complete\n");
		
		launcher.writeToConsole(launcher.getTimingStats());

		// only refresh if the auto refresh workspace pref is turned off
        if (!ResourcesPlugin.getPlugin().getPluginPreferences().getBoolean(ResourcesPlugin.PREF_AUTO_REFRESH)) {
    		// refresh the file system to pick up any newly created files under the project
    		try {
    			cpi.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
    		} catch (CoreException e) {
    			// no file system refresh
    			e.printStackTrace();
    		}
        }
	}

	/**
	 * Invokes a freeze for the given build configuration.  Output is piped to the given console and progress is reported
	 * using the given monitor.  Make files are created/updated if necessary.
	 * @param buildConfig the build configuration to freeze
	 * @param monitor the progress monitor to use to report progress
	 * @param clearMarkers true if project markers should be cleared before action is taken
	 */
	public static void invokeFreeze(ICarbideBuildConfiguration buildConfig, IProgressMonitor monitor, boolean clearMarkers) {
		
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IProject project = cpi.getProject();
		
		CarbideCommandLauncher launcher = new CarbideCommandLauncher(project, monitor, getParserIdArray(buildConfig.getErrorParserId()), cpi.getINFWorkingDirectory());
		launcher.showCommand(true);

		// make sure the project is built
		launcher.writeToConsole("\n***Bringing project up to date before freezing\n");
		invokeBuild(buildConfig, launcher, monitor, clearMarkers);
		
		launcher.startTimingStats();
		launcher.writeToConsole("\n***Freezing project \"" + project.getName() + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");
		
		if (cpi.isBuildingFromInf()) {
			freezeAllComponents(buildConfig, launcher, monitor);
		} else {
			freezeComponentSubset(buildConfig, launcher, monitor);
		}
		
		launcher.writeToConsole("\n***Freeze Complete\n");
		
		launcher.writeToConsole(launcher.getTimingStats());
	}

	/**
	 * Invoke the given action on the given Symbian mmp/make file for the given build configuration.
	 * @param buildConfig the build configuration to act upon
	 * @param action the action to perform, see {@link #BUILD_COMPONENT_ACTION}, {@link #CLEAN_COMPONENT_ACTION}, {@link #FREEZE_COMPONENT_ACTION}
	 * @param componentPath the full path of the mmp or make file
     * @param launcher - The object to use for the process execution
	 * @param monitor the progress monitor to report progress to
	 * @param clearMarkers true if project markers should be cleared before action is taken
	 * @return true if successful, false otherwise
	 * @throws CoreException
	 */
	public static boolean invokeSymbianComponenetAction(ICarbideBuildConfiguration buildConfig, int action, IPath componentPath, CarbideCommandLauncher launcher, IProgressMonitor monitor, boolean clearMarkers) throws CoreException {
		
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();		
		List<ISymbianBuildContext> buildConfigList = new ArrayList<ISymbianBuildContext>(1);
		buildConfigList.add(buildConfig);
		
		if (clearMarkers) {
    		try {
    			removeAllMarkers(cpi.getProject());
    		} catch (CoreException e) {
    			e.printStackTrace();
    		}
        }

		List<IPath> normalMakMakePaths = new ArrayList<IPath>();
		List<IPath> testMakMakePaths = new ArrayList<IPath>();

		// get the list of mmp/make files for this build configuration.  note that we're not calling calculateComponentLists here
		// because then the caller would only be able to build components that are build built by a full build.  but that's kind of
		// limiting.  they may have build test components disabled but just want to verify that one test mmp still builds.
		EpocEngineHelper.getMakMakeFiles(cpi.getAbsoluteBldInfPath(), buildConfigList, normalMakMakePaths, testMakMakePaths, new NullProgressMonitor());

		boolean found = false;
		boolean isTest = false;
		
		// see if we can find the component
		for (IPath path : normalMakMakePaths) {
			if (path.equals(componentPath)) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			// try the test components
			for (IPath path : testMakMakePaths) {
				if (path.equals(componentPath)) {
					found = true;
					isTest = true;
					break;
				}
			}
		}
		
		if (!found) {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Component " + componentPath.toOSString() + " not found.", null)); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		launcher.startTimingStats();

		String componentName = componentPath.removeFileExtension().lastSegment();

		boolean result = false;
		if (action == BUILD_COMPONENT_ACTION) {
			runPreBuildChecks(buildConfig, launcher);

			launcher.writeToConsole("\n***Building component \"" + componentName + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");
			
			result = getBuilder(cpi.getProject()).buildComponent(buildConfig, componentPath, isTest, launcher, monitor);

		} else if (action == CLEAN_COMPONENT_ACTION) {
			
			launcher.writeToConsole("\n***Cleaning component \"" + componentName + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");
		
			result = getBuilder(cpi.getProject()).cleanComponent(buildConfig, componentPath, isTest, launcher, monitor);

		} else if (action == FREEZE_COMPONENT_ACTION) {
			
			SubMonitor progress = SubMonitor.convert(monitor, 3);
			progress.setTaskName("Freezing " + componentName);

			// make sure the component is built
			launcher.writeToConsole("\n***Bringing component up to date before freezing\n");
			boolean success = invokeSymbianComponenetAction(buildConfig, BUILD_COMPONENT_ACTION, componentPath, launcher, progress.newChild(1), false);
			
			if (success) {
				launcher.writeToConsole("\n***Freezing component \"" + componentName + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");
				result = getBuilder(cpi.getProject()).freezeComponent(buildConfig, componentPath, isTest, launcher, progress);
			}
		}

		return result;
	}

	/**
	 * Invokes a compile of the the given file for the given build configuration.  Output is piped to the given console and
	 * progress is reported using the given monitor.  Make files are created/updated if necessary.
	 * @param file the absolute file system path to the file to be compiled (source, resource)
	 * @param buildConfig the build configuration to build
	 * @param console the console to pipe build output to
     * @param launcher - The object to use for the process execution
	 * @param monitor the progress monitor to use to report progress
	 * @param clearMarkers whether or not to clear project markers before compiling
	 * @throws CoreException
	 */
	public static void compileFile(IPath file, ICarbideBuildConfiguration buildConfig, IConsole console, CarbideCommandLauncher launcher, IProgressMonitor monitor, boolean clearMarkers) throws CoreException {
		
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		
		if (clearMarkers) {
    		try {
    			removeAllMarkers(cpi.getProject());
    		} catch (CoreException e) {
    			e.printStackTrace();
    		}
        }
		
		final SubMonitor progress = SubMonitor.convert(monitor, 5);
		progress.setTaskName("Compiling " + file.lastSegment());

		final List<IPath> parentMMPs = EpocEngineHelper.getMMPsForSource(cpi.getProject(), file);
		if (parentMMPs.isEmpty()) {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Unable to find parent MMP for file " + file.toOSString(), null)); //$NON-NLS-1$
		}
		
		progress.worked(1);
		if (progress.isCanceled()) {
			return;
		}

		final EpocEnginePathHelper helper = new EpocEnginePathHelper(cpi.getProject());
		final List<IPath> tempPath = new ArrayList<IPath>();
		tempPath.add(helper.convertToFilesystem(parentMMPs.get(0)));

		// if there is more than one mmp that uses this file, ask the user which one to use
		if (parentMMPs.size() > 1) {
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					List<String> mmps = new ArrayList<String>();
					for (IPath mmp : parentMMPs) {
						mmps.add(mmp.lastSegment());
					}
					
					MMPSelectionDialog dlg = new MMPSelectionDialog(WorkbenchUtils.getSafeShell(), mmps);
					if (Window.OK == dlg.open()) {
						tempPath.set(0, helper.convertToFilesystem(parentMMPs.get(dlg.getselectedIndex())));
					} else {
						progress.setCanceled(true);
					}
				}
			});
		}
		
		if (progress.isCanceled()) {
			return;
		}

		IPath fullMMPPath = tempPath.get(0);

		launcher.writeToConsole("\n***Compiling file \"" + file.toOSString() + "\" for configuration \"" + buildConfig.getDisplayString() + "\"\n");
		
		runPreBuildChecks(buildConfig, launcher);

		getBuilder(cpi.getProject()).compileFile(file, buildConfig, fullMMPPath, launcher, monitor);

		launcher.writeToConsole("\n***Compile Complete\n");
		
		launcher.writeToConsole(launcher.getTimingStats());
	}

	protected static List<IPath> getMakeRulesForResource(final ICarbideBuildConfiguration buildConfig, final IPath workspaceRelativeMMPPath, final IPath projectRelativeResourcePath) {
		
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

	protected static void runPreBuildChecks(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher) {
		if (buildConfig.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM) && BuilderPreferencePage.useBuiltInX86Vars()) {
			X86BuildPlugin.checkForUpdates();
		}
	}
	
	protected static void calculateComponentLists(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher) {
		// this could potentially take some time, and this information is needed in a
		// lot of different places, so just do it once and keep it cached for each user
		// invoked builder operation.  clear it at the start of each operation.
		normalMakMakePaths.clear();
		testMakMakePaths.clear();

		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();		
		List<ISymbianBuildContext> buildConfigList = new ArrayList<ISymbianBuildContext>(1);
		buildConfigList.add(buildConfig);

		// get the list of mmp/make files for this build configuration
		EpocEngineHelper.getMakMakeFiles(cpi.getAbsoluteBldInfPath(), buildConfigList, normalMakMakePaths, testMakMakePaths, new NullProgressMonitor());

		if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(cpi.getProject())) {
			// add any named extensions
	    	List<IExtension> normalNamedExtensionsList = new ArrayList<IExtension>();
			List<IExtension> testNamedExtensionsList = new ArrayList<IExtension>();
			EpocEngineHelper.getNamedExtensions(cpi.getAbsoluteBldInfPath(), buildConfigList,
					normalNamedExtensionsList, testNamedExtensionsList, new NullProgressMonitor());
			
	    	for (IExtension extension : normalNamedExtensionsList) {
	    		normalMakMakePaths.add(new Path(extension.getName()));
	    	}
	    	for (IExtension extension : testNamedExtensionsList) {
	    		testMakMakePaths.add(new Path(extension.getName()));
	    	}
		}

    	// if we're not supposed to build test components then clear the list
		if (cpi.isBuildingFromInf() && !cpi.isBuildingTestComps()) {
			testMakMakePaths.clear();
		}

		if (!cpi.isBuildingFromInf()) {
			// issue a warning that extensions will not be built if they're building a subset of components
			boolean buildingSubset = false;
			
			// building a subset so we need to figure out which ones to check
			List<String> normalComponentsToBeBuilt = cpi.getNormalInfBuildComponents();
			for (Iterator<IPath> iter = normalMakMakePaths.iterator(); iter.hasNext();) {
				if (!normalComponentsToBeBuilt.contains(iter.next().lastSegment())) {
					iter.remove();
					buildingSubset = true;
				}
			}

			List<String> testComponentsToBeBuilt = cpi.getTestInfBuildComponents();
			for (Iterator<IPath> iter = testMakMakePaths.iterator(); iter.hasNext();) {
				if (!testComponentsToBeBuilt.contains(iter.next().lastSegment())) {
					iter.remove();
					buildingSubset = true;
				}
			}
			
			if (buildingSubset && EpocEngineHelper.hasUnnamedExtensions(cpi.getAbsoluteBldInfPath(), buildConfigList, new NullProgressMonitor())) {
				String warningText = "WARNING: PRJ_EXTENSIONS and PRJ_TESTEXTENSIONS will be excluded from the build because you've selected to build a subset of the bld.inf, and there is no way to specify unnamed components.";
				if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(cpi.getProject())) {
					warningText = warningText + "  If you name the extensions then you can select them to be built from the UI.";
				}
				launcher.writeToConsole(warningText + "\n");
	   			CarbideBuilderPlugin.createCarbideProjectMarker(cpi.getProject(), IMarker.SEVERITY_WARNING, warningText, IMarker.PRIORITY_LOW);
			}
		}
	}
	
	protected static boolean buildAllComponents(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		return getBuilder(buildConfig.getCarbideProject().getProject()).buildAllComponents(buildConfig, normalMakMakePaths, testMakMakePaths, launcher, monitor);
	}
	
	protected static boolean buildComponentSubset(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		return getBuilder(buildConfig.getCarbideProject().getProject()).buildComponentSubset(buildConfig, normalMakMakePaths, testMakMakePaths, launcher, monitor);
	}

	protected static void cleanAllComponents(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		getBuilder(buildConfig.getCarbideProject().getProject()).cleanAllComponents(buildConfig, normalMakMakePaths, testMakMakePaths, launcher, monitor);
	}
	
	protected static void cleanComponentSubset(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		getBuilder(buildConfig.getCarbideProject().getProject()).cleanComponentSubset(buildConfig, normalMakMakePaths, testMakMakePaths, launcher, monitor);
	}

	protected static void freezeAllComponents(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		getBuilder(buildConfig.getCarbideProject().getProject()).freezeAllComponents(buildConfig, normalMakMakePaths, testMakMakePaths, launcher, monitor);
	}
	
	protected static void freezeComponentSubset(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, IProgressMonitor monitor) {
		getBuilder(buildConfig.getCarbideProject().getProject()).freezeComponentSubset(buildConfig, normalMakMakePaths, testMakMakePaths, launcher, monitor);
	}

	/**
	 * Call bldmake with the given arguments
	 * @param buildConfig the build configuration context
	 * @param launcher the command launcher
	 * @param bldmakeArgs array of String arguments to be passed to bldmake
	 * @param removeMarkers project markers will be removed when true
	 * @return true if operation was successful, false otherwise
	 */
	public static boolean invokeBldmakeCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, String[] bldmakeArgs, boolean removeMarkers) {
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IProject project = cpi.getProject();

        if (removeMarkers) {
    		try {
    			removeAllMarkers(project);
    		} catch (CoreException e) {
    			e.printStackTrace();
    		}
        }
        
        return getBuilder(project).invokeBldmakeCommand(buildConfig, launcher, bldmakeArgs);
	}

	/**
	 * Call abld with the given arguments
	 * @param buildConfig the build configuration context
	 * @param launcher the command launcher
	 * @param abldArgs array of String arguments to be passed to abld
	 * @param removeMarkers project markers will be removed when true
	 * @return true if operation was successful, false otherwise
	 */
	public static boolean invokeAbldCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, String[] abldArgs, boolean removeMarkers) {
		ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		IProject project = cpi.getProject();

        if (removeMarkers) {
    		try {
    			removeAllMarkers(project);
    		} catch (CoreException e) {
    			e.printStackTrace();
    		}
        }

        return getBuilder(project).invokeAbldCommand(buildConfig, launcher, abldArgs);
	}
		
	/**
	 * Get the array of resolved environment variables. This the entire list of variables that should be
	 * used during a build for the given configuration
	 * @param config - The config whose environment variables you want.
	 * @return An array of environment variables of format &lt;var&gt;=&lt;value&gt;
	 */
	public static String [] getResolvedEnvVars(ICarbideBuildConfiguration config) {
		if (config == null){
			return new String[0];
		}
		
		return getBuilder(config.getCarbideProject().getProject()).getResolvedEnvVars(config);
	}
	
	/**
	 * Get the array of environment variables that are modified from their default values.
	 * @param config - Config you are building for.
	 * @return An array of environment variables of format &lt;var&gt;=&lt;value&gt;
	 */
	public static String[] getModifiedEnvVars(ICarbideBuildConfiguration config){
		return config.getEnvironmentVarsInfo().getModifiedEnvironmentVariables();
	}
	
	/**
	 * Get the environement variables exactly as they come from the system.
	 * @return An array of environment variables of format &lt;var&gt;=&lt;value&gt;
	 */
	public static String [] getRawEnvVars(){
		return EnvironmentReader.getRawEnvVars();
	}
		
	/**
	 * Generates the bldmake makefiles if necessary.
	 * If abld.bat or any makefiles don't exist, or if the bld.inf or any of its includes
	 * is newer than any of the makefiles, then generates the makefiles by running
	 * 'bldmake bldfiles platform'.
	 * @param config the build configuration context
	 * @param launcher the Carbide launcher
	 * @return false if makefile generation was necessary but failed, true otherwise
	 */
	public static boolean generateBldmakeMakefilesIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher) {

		if (needsBldmakeMakefileGeneration(config)) {
			
			List<String> argsList = new ArrayList<String>();
			argsList.add("bldfiles");
			argsList.add(config.getBasePlatformForVariation().toLowerCase());
			
			for (String arg : config.getBuildArgumentsInfo().getBldmakeBldFilesArgs().split(" ")) {
				argsList.add(arg);
			}
			
			if (!invokeBldmakeCommand(config, launcher, argsList.toArray(new String[argsList.size()]), false)) {
				return false;
			}
		} else {
			launcher.writeToConsole("***Makefile structures up to date. Skipping 'bldmake bldfiles'\n"); 
		}

		return true;
	}

	/**
	 * Check to see if abld.bat and the bldmake makefiles exists, or if they are stale.
	 * If abld.bat or any makefiles don't exist, or if the bld.inf or any of its includes
	 * is newer than any of the makefiles, then returns true.
	 * @param config - The build configuration to check the makefiles for
	 * @return true if makefiles need to be regenerated (bldmake bldfiles platform)
	 */
	protected static boolean needsBldmakeMakefileGeneration(ICarbideBuildConfiguration config) {
		return getBuilder(config.getCarbideProject().getProject()).needsBldmakeMakefileGeneration(config);
	}
	
	/**
	 * Generates the abld makefiles if necessary.
	 * Loops through the mmp files to be built for the given build configuration and generates the makefile
	 * for the mmp if:
	 * 	 1) the makefile for the mmp does not exist
	 *   2) if the mmp or any of its includes is newer than the makefile
	 *   3) the makefile does not have the necessary Carbide changes
	 *   
	 *   The command used will be 'abld [test] makefile platform mmpname'
	 * @param config the build configuration context
	 * @param launcher the Carbide launcher
	 * @return false if any makefile generation was necessary but failed, true otherwise
	 */
	public static boolean generateAbldMakefilesIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher) {
		return generateAbldMakefilesIfNecessary(config, launcher, true);
	}

	/**
	 * Generates the abld makefiles if necessary.
	 * Loops through the mmp files to be built for the given build configuration and generates the makefile
	 * for the mmp if:
	 * 	 1) the makefile for the mmp does not exist
	 *   2) if the mmp or any of its includes is newer than the makefile
	 *   3) the makefile does not have the necessary Carbide changes
	 *   
	 *   The command used will be 'abld [test] makefile platform mmpname'
	 * @param config the build configuration context
	 * @param launcher the Carbide launcher
	 * @param calculateComponentLists whether or not to calculate the list of makmake components
	 * @return false if any makefile generation was necessary but failed, true otherwise
	 * @since 2.0
	 * @deprecated Use {@link #generateAbldMakeFileIfNecessary(generateAbldMakefilesIfNecessary(ICarbideBuildConfiguration, CarbideCommandLauncher, boolean, IProgressMonitor)} instead
	 */
	public static boolean generateAbldMakefilesIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, boolean calculateComponentLists) {
		return generateAbldMakefilesIfNecessary(config, launcher, calculateComponentLists, new NullProgressMonitor());
	}

	/**
	 * Generates the abld makefiles if necessary.
	 * Loops through the mmp files to be built for the given build configuration and generates the makefile
	 * for the mmp if:
	 * 	 1) the makefile for the mmp does not exist
	 *   2) if the mmp or any of its includes is newer than the makefile
	 *   3) the makefile does not have the necessary Carbide changes
	 *   
	 *   The command used will be 'abld [test] makefile platform mmpname'
	 * @param config the build configuration context
	 * @param launcher the Carbide launcher
	 * @param calculateComponentLists whether or not to calculate the list of makmake components
	 * @param progress monitor to allow user to cancel
	 * @return false if any makefile generation was necessary but failed, true otherwise
	 * @since 2.0
	 */
	public static boolean generateAbldMakefilesIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, boolean calculateComponentLists, IProgressMonitor progress) {

		if (calculateComponentLists) {
			calculateComponentLists(config, launcher);
		}

		// generate the makefiles if necessary
		for (IPath path : normalMakMakePaths) {
			if (!generateAbldMakefileIfNecessary(config, launcher, path, false, progress)) {
				return false;
			}
		}
		
		for (IPath path : testMakMakePaths) {
			if (!generateAbldMakefileIfNecessary(config, launcher, path, true, progress)) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Generates the abld makefile if necessary.
	 * Generates the makefile for the given mmp file if:
	 * 	 1) the makefile for the mmp does not exist
	 *   2) if the mmp or any of its includes is newer than the makefile
	 *   3) the makefile does not have the necessary Carbide changes
	 *   
	 *   The command used will be 'abld [test] makefile platform mmpname'
	 * @param config the build configuration context
	 * @param launcher the Carbide launcher
	 * @param componentPath the absolute file system path of the component
	 * @param isTest true for test components, false otherwise
	 * @return false if any makefile generation was necessary but failed, true otherwise
	 */
	protected static boolean generateAbldMakefileIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, IPath componentPath, boolean isTest, IProgressMonitor progress) {
		return getBuilder(config.getCarbideProject().getProject()).generateAbldMakefileIfNecessary(config, launcher, componentPath, isTest, progress);
	}
	
/**
 * Get the array of parser ID's (extension ID from plugin.xml) that will be used to parse the output
 * of a given process invoked by the CarbideCommandLauncher. If you don't know which parser to use
 * you can use them all by passed ICarbideBuildeConfiguration.ERROR_PARSERS_ALL
 * @param id - The id to use to get the parsers.
 * @return An array of parser id's to iterate through to find matches in the stdout and stderrr
 * @see CarbideCommandLauncher, ICarbideBuildConfiguration.ERROR_PARSERS*
 */
public static String[] getParserIdArray(int id) {
	String[] parserIds = new String[0];

	switch (id) {
		case ICarbideBuildConfiguration.ERROR_PARSERS_WINSCW:
	
			parserIds = new String[] {
		        "com.nokia.carbide.cdt.builder.MakmakeErrorParser",
		        "com.nokia.carbide.cdt.builder.SBSv2ErrorParser",
		        "com.nokia.carbide.cdt.builder.CarbideMakeErrorParser",
		        "com.nokia.carbide.cdt.builder.RCOMPErrorParser",
		        "org.eclipse.cdt.core.GCCErrorParser",				// for cpp message from RCOMP
		        "com.nokia.carbide.cdt.builder.MWLDErrorParser",
		        "com.nokia.carbide.cdt.builder.MWCCErrorParser",
		        "com.nokia.carbide.cdt.builder.MakeDefErrorParser"
		        };
		 
			break;
	
		case ICarbideBuildConfiguration.ERROR_PARSERS_ARMVx:
			parserIds = new String[] {
			        "com.nokia.carbide.cdt.builder.MakmakeErrorParser",
			        "com.nokia.carbide.cdt.builder.SBSv2ErrorParser",
			        "com.nokia.carbide.cdt.builder.CarbideMakeErrorParser",
			        "com.nokia.carbide.cdt.builder.RVCTCompilerErrorParser",
			        "com.nokia.carbide.cdt.builder.RVCTLinkerErrorParser",
			        "com.nokia.carbide.cdt.builder.MakeDefErrorParser",
			        "com.nokia.carbide.cdt.builder.RCOMPErrorParser",
			        "org.eclipse.cdt.core.GCCErrorParser",				// for cpp message from RCOMP
			        "com.nokia.carbide.cdt.builder.Elf2E32ErrorParser"
			        };
			break;
	
		case ICarbideBuildConfiguration.ERROR_PARSERS_GCCE:
			parserIds = new String[] {
			        "com.nokia.carbide.cdt.builder.MakmakeErrorParser",
			        "com.nokia.carbide.cdt.builder.SBSv2ErrorParser",
			        "com.nokia.carbide.cdt.builder.CarbideMakeErrorParser",
			        "com.nokia.carbide.cdt.builder.RCOMPErrorParser",
			        "com.nokia.carbide.cdt.builder.GCCECompilerErrorParser",		// also handles cpp message from RCOMP
			        "com.nokia.carbide.cdt.builder.GCCEAssemblerErrorParser",
			        "com.nokia.carbide.cdt.builder.GCCELinkerErrorParser",
			        "com.nokia.carbide.cdt.builder.MakeDefErrorParser",
			        "com.nokia.carbide.cdt.builder.Elf2E32ErrorParser"
			        };
			break;
		
		case ICarbideBuildConfiguration.ERROR_PARSERS_ARM_EKA1:
			parserIds = new String[] {
			        "com.nokia.carbide.cdt.builder.MakmakeErrorParser",
			        "com.nokia.carbide.cdt.builder.SBSv2ErrorParser",
			        "com.nokia.carbide.cdt.builder.CarbideMakeErrorParser",
			        "com.nokia.carbide.cdt.builder.RCOMPErrorParser",
			        "org.eclipse.cdt.core.GCCErrorParser",
			        "org.eclipse.cdt.core.GLDErrorParser",
			        "com.nokia.carbide.cdt.builder.MakeDefErrorParser",
			        "com.nokia.carbide.cdt.builder.DLLToolErrorParser"
			        };
			break;
		
		case ICarbideBuildConfiguration.ERROR_PARSERS_SIS_BUILDER:
			parserIds = new String[] {
			        "com.nokia.carbide.cdt.builder.MakeSisErrorParser"
			        };
			break;
		
		case ICarbideBuildConfiguration.ERROR_PARSERS_BLDMAKE_MAKE:
			parserIds = new String[] {
			        "com.nokia.carbide.cdt.builder.BldmakeErrorParser",
			        "com.nokia.carbide.cdt.builder.SBSv2ErrorParser"
			        };
			break;
		
		case ICarbideBuildConfiguration.ERROR_PARSERS_ROM_BUILDER:
			parserIds = new String[] {
			        "com.nokia.carbide.cdt.builder.RomBuildErrorParser"
			        };
		break;
	
		case ICarbideBuildConfiguration.ERROR_PARSERS_ALL:
		default:
		
			parserIds = new String[] {
		        	"com.nokia.carbide.cdt.builder.BldmakeErrorParser",
			        "com.nokia.carbide.cdt.builder.MakeSisErrorParser",
			        "com.nokia.carbide.cdt.builder.MakmakeErrorParser",
			        "com.nokia.carbide.cdt.builder.SBSv2ErrorParser",
			        "com.nokia.carbide.cdt.builder.CarbideMakeErrorParser",
			        "com.nokia.carbide.cdt.builder.RVCTCompilerErrorParser",
			        "com.nokia.carbide.cdt.builder.RVCTLinkerErrorParser",
			        "com.nokia.carbide.cdt.builder.RCOMPErrorParser",
			        "org.eclipse.cdt.core.GCCErrorParser",				// for cpp message from RCOMP
			        "com.nokia.carbide.cdt.builder.MWLDErrorParser",
			        "com.nokia.carbide.cdt.builder.MakeDefErrorParser",
			        "com.nokia.carbide.cdt.builder.Elf2E32ErrorParser",
			        "com.nokia.carbide.cdt.builder.RomBuildErrorParser"
			        };
		
			break;
		}
	
		return parserIds;
	}

	/**
	 * Remove all the C/C++ markers for the current project.
	 * @param currProject - Project to remove markers for.
	 * @throws CoreException
	 */
	 public static void removeAllMarkers(IProject currProject) throws CoreException {
		IWorkspace workspace = currProject.getWorkspace();

		// remove all markers
		IMarker[] markers = currProject.findMarkers(ICModelMarker.C_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		if (markers != null) {
			workspace.deleteMarkers(markers);
		}
		
		markers = currProject.findMarkers(CarbideBuilderPlugin.CARBIDE_PROJECT_MARKER, true, IResource.DEPTH_INFINITE);
		if (markers != null) {
			workspace.deleteMarkers(markers);
		}
	}

	/**
	 * Invoke the SIS builder for all pkg files for the given build configuration
	 * @param config - The current configuration from where to get the settings from
	 * @param cmdLauncher - The object to use for the process execution
	 * @param monitor - An IProgressMonitor
	 */   
	public static void invokeSISBuilder(ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor) {

		int enabledSisInfos = 0;
		List<ISISBuilderInfo> sisList = config.getSISBuilderInfoList();
		for (ISISBuilderInfo sisInfo : sisList) {
			if (sisInfo.isEnabled()) {
				enabledSisInfos++;
			}
		}

		if (enabledSisInfos < 1) {
			return;
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor, enabledSisInfos);

		for (ISISBuilderInfo sisInfo : sisList) {
			
			if (!sisInfo.isEnabled()) {
				continue;
			}
			
			buildSisFile(sisInfo, config, cmdLauncher, subMonitor.newChild(1), false);
		}
		
    	monitor.done();
	}

    /**
     * Invoke the SIS builder for either EKA1 or EKA2 projects. Depending on the os version will
     * determine if makesis or makeis/signsis will get called
     * @param pkgPath - Full path to the PKG file to be used to generate the SIS file
     * @param config - The current configuration from where to get the settings for
     * @param cmdLauncher - The object to use for the process execution
     * @param monitor - An IProgressMonitor
     * @param createOutputFromPKGFileName - When true, only create output file name based on PKG file name, otherwise check the SIS build settings.
     */   
    public static void invokeSISBuilder(IPath pkgPath, ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor, boolean createOutputFromPKGFileName) {
		int sisInfosForPkg = 0;
		List<ISISBuilderInfo> sisList = config.getSISBuilderInfoList();
		for (ISISBuilderInfo sisInfo : sisList) {
			// ignoring enabled flag on purpose
			if (sisInfo.getPKGFullPath().equals(pkgPath)) {
				sisInfosForPkg++;
			}
		}

		if (sisInfosForPkg < 1) {
   			cmdLauncher.writeToConsole("No SIS Builder info found for pkg file.");
			return;
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor, sisInfosForPkg);

		for (ISISBuilderInfo sisInfo : sisList) {
			
			// ignoring enabled flag on purpose
			if (!sisInfo.getPKGFullPath().equals(pkgPath)) {
				continue;
			}
			
			buildSisFile(sisInfo, config, cmdLauncher, subMonitor.newChild(1), createOutputFromPKGFileName);
		}
		
    	monitor.done();
    }
    
    private static void buildSisFile(ISISBuilderInfo sisInfo, ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor, boolean createOutputFromPKGFileName) {
		IPath pkgPath = sisInfo.getPKGFullPath();
    	if (pkgPath == null) {
   			cmdLauncher.writeToConsole("PKG file does not exist.  Skipping...");
    		return;
    	} else if (!pkgPath.toFile().exists()) {
   			cmdLauncher.writeToConsole("PKG file" + pkgPath.toOSString() + " does not exist.  Skipping...");
    		return;
    	}
    	
    	// see if we need to rebuild the sis file
		boolean shouldBuild = false;
		
		String sisName = createOutputFromPKGFileName ? pkgPath.toOSString().substring(0, pkgPath.toOSString().lastIndexOf(".")) + ".sis" : sisInfo.getUnsignedSISFullPath().toOSString();
		File sisFile = new File(sisName);
		if (!sisFile.exists()) {
			// no sis file - need to build
			shouldBuild = true;
		} else {
			long sisFileTimestamp = sisFile.lastModified();
			
			// see if the pkg file is newer than the sis
			if (pkgPath.toFile().lastModified() > sisFileTimestamp) {
				shouldBuild = true;
			} else {
				// get the list of files from the pkg file and check for their existence
				for (IPath path : EpocEngineHelper.getFilesInPKG(pkgPath, config, sisInfo)) {
					File file = path.toFile();
					if (!file.exists()) {
						shouldBuild = true;
						break;
					}
				}
			}
		}
		
		if (!shouldBuild) {
			// see if they have changed any sis builder settings since the
			// last build
	    	if (sisInfo instanceof SISBuilderInfo2) {
	    		SISBuilderInfo2 info = (SISBuilderInfo2)sisInfo;
	    		if (info.hasSisChanges()) {
	    			shouldBuild = true;
	    		}
	    	}
		}
    	
		// check to see if any of the files listed in the pkg have changed since the sis file
		// was last built.  remember the list of files that have changed in case we're building
		// a partial upgrade
   		boolean creatingPU = false;
    	boolean createPartialUpgradeEnabled = false;
    	if (sisInfo instanceof SISBuilderInfo2) {
    		createPartialUpgradeEnabled = ((SISBuilderInfo2)sisInfo).isPartialUpgrade();
    	}
		final List<File> modifiedFiles = new ArrayList<File>();

		if (!shouldBuild) {
	    	long sisFileTimestamp = sisFile.lastModified();
			for (IPath path : EpocEngineHelper.getFilesInPKG(pkgPath, config, sisInfo)) {
				if (path.toFile().lastModified() > sisFileTimestamp) {
					// file has changed since the sis was built
					shouldBuild = true;
					if (createPartialUpgradeEnabled) {
						modifiedFiles.add(path.toFile());
					} else {
						break;
					}
				}
			}
		}
		
    	SubMonitor subMonitor = SubMonitor.convert(monitor, 1);

    	if (shouldBuild) {
	    	if (sisInfo instanceof SISBuilderInfo2) {
	    		SISBuilderInfo2 info = (SISBuilderInfo2)sisInfo;
	    		info.setHasSisChanges(false);
	    	}

			subMonitor.setTaskName("Building sis file");
			
			// put temp _resolved.pkg file in epoc32/build tree
	    	IPath buildDirPath = getBuilder(config.getCarbideProject().getProject()).getMakefileDirectory(config);
	    	String prefix = RESOLVED_PKG_PREFIX;

			IPath tmpPKGPath = buildDirPath.append(prefix + pkgPath.lastSegment());

			IPath resolvedPKGPath = resolvePKGFile(pkgPath, config, tmpPKGPath);
			
			List<String> args = new ArrayList<String>();

			// add the search location if specified
			String searchDir = sisInfo.getContentSearchLocation();
			if (searchDir != null && searchDir.length() > 0) {
				args.add("-d" + searchDir);
			}

			args.add(resolvedPKGPath.toOSString());
			args.add(sisName);

			IPath makeSisPath = config.getSDK().getToolsPath().append(MAKESIS_EXE);

			cmdLauncher.writeToConsole("\n***Invoking " + MAKESIS_EXE + " ....\n");
			cmdLauncher.setErrorParserManager(pkgPath.removeLastSegments(1), getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_SIS_BUILDER));
	   		int retVal = cmdLauncher.executeCommand(makeSisPath, args.toArray(new String[args.size()]), getResolvedEnvVars(config), pkgPath.removeLastSegments(1));
	   		
	   		subMonitor.worked(1);
	   		if (subMonitor.isCanceled()) {
	   			return;
	   		}
	   		
	   		if (retVal != 0){
	   			cmdLauncher.writeToConsole("***Non-Zero Status: " + MAKESIS_EXE + " returned with exit value = " + retVal);
	   			CarbideBuilderPlugin.createCarbideProjectMarker(config.getCarbideProject().getProject(), IMarker.SEVERITY_ERROR,  MAKESIS_EXE + " returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
	   			return;
	   		}
	   		
	   		// now create the partial upgrade sis file if necessary
			if (createPartialUpgradeEnabled && modifiedFiles.size() > 0) {
				creatingPU = true;
				
				if (!createPartialUpgradeSis(sisInfo, config, cmdLauncher, subMonitor, createOutputFromPKGFileName, modifiedFiles)) {
					return;
				}
			} else {
				// delete the partial upgrade files and links if they exist since they are now obsolete
				IFile tempPkgFileLink = getTempPkgIFile(pkgPath, getTempPkgBuildTreePath(sisInfo, config), config);
				if (tempPkgFileLink.exists()) {
					try {
						tempPkgFileLink.getRawLocation().toFile().delete();
						tempPkgFileLink.delete(0, null);
					} catch (CoreException e) {
						e.printStackTrace();
						CarbideBuilderPlugin.log(e);
					}
				}

				IPath tempPkgBuildTreePath = getTempPkgBuildTreePath(sisInfo, config);
				IFile tempSisFileLink = getTempSisIFile(pkgPath, getPUSisPath(sisInfo, tempPkgBuildTreePath, createOutputFromPKGFileName, false), config);
				if (tempSisFileLink.exists()) {
					try {
						tempSisFileLink.getRawLocation().toFile().delete();
						tempSisFileLink.delete(0, null);
					} catch (CoreException e) {
						e.printStackTrace();
						CarbideBuilderPlugin.log(e);
					}
				}
			}
		} else {
	   		subMonitor.worked(1);
	   		if (subMonitor.isCanceled()) {
	   			return;
	   		}

	   		cmdLauncher.writeToConsole("\nSIS file " + sisFile.getAbsolutePath() + " already up to date.  Skipping...");
		}
    	
    	buildSisxFile(sisInfo, config, cmdLauncher, monitor, createOutputFromPKGFileName, sisFile, creatingPU);
		
   		cmdLauncher.writeToConsole("\n***SIS Creation Complete\n");
    }
    
    private static void buildSisxFile(ISISBuilderInfo sisInfo, ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor, boolean createOutputFromPKGFileName, File sisFile, boolean creatingPu) {
    	if (sisInfo.getSigningType() == ISISBuilderInfo.DONT_SIGN) {
    		return;
    	}

    	boolean shouldBuild = false;

		IPath pkgPath = sisInfo.getPKGFullPath();

		String sisxName = sisInfo.getSignedSISFullPath().toOSString();
		if (createOutputFromPKGFileName){
			sisxName = pkgPath.toOSString().substring(0, pkgPath.toOSString().lastIndexOf(".")) + ".sisx";
		}

		File sisxFile = new File(sisxName);
		if (!sisxFile.exists()) {
			// no sisx file - need to build
			shouldBuild = true;
		} else {
			// see if the sis file is newer than the sisx
			if (sisFile.lastModified() > sisxFile.lastModified()) {
				shouldBuild = true;
			}
		}
		
		if (!shouldBuild) {
			// see if they have changed any sisx builder settings since the
			// last build
	    	if (sisInfo instanceof SISBuilderInfo2) {
	    		SISBuilderInfo2 info = (SISBuilderInfo2)sisInfo;
	    		if (info.hasSisxChanges()) {
	    			shouldBuild = true;
	    		}
	    	}
		}

    	SubMonitor subMonitor = SubMonitor.convert(monitor, 2);

    	if (shouldBuild) {
	    	if (sisInfo instanceof SISBuilderInfo2) {
	    		SISBuilderInfo2 info = (SISBuilderInfo2)sisInfo;
	    		info.setHasSisxChanges(false);
	    	}

   	    	IPath buildDirPath = getBuilder(config.getCarbideProject().getProject()).getMakefileDirectory(config);

   	    	int signingMethod = sisInfo.getSigningType();
	   		if (signingMethod != ISISBuilderInfo.DONT_SIGN) {
	   			
				String password = sisInfo.getPassword();

		   		if (signingMethod == ISISBuilderInfo.SELF_SIGN) {

		   	    	// call makekeys
					cmdLauncher.writeToConsole("\n...No key/cert defined. Generating dummy key/cert for self-signing (" + DEFAULT_KEY_NAME + "/" + DEFAULT_CERT_NAME + ")...\n");
					if (password.length() == 0){
						password = DEAULT_PASSWORD;
						cmdLauncher.writeToConsole("No passphrase defined. Using: \"" + DEAULT_PASSWORD + "\"\n");
					}
					
					cmdLauncher.writeToConsole("\n***Invoking makekeys....\n");

					IPath makekeys = config.getSDK().getToolsPath().append(MAKEKEYS_EXE);
					List<String> makekeysArgList = new ArrayList<String>();
					makekeysArgList.add("-cert");
					makekeysArgList.add("-password");
					makekeysArgList.add(password);
					makekeysArgList.add("-len");
					makekeysArgList.add("2048");
					makekeysArgList.add("-dname");
					makekeysArgList.add("\"CN=JoeBloggs OR=Acme\"");
					makekeysArgList.add(DEFAULT_KEY_NAME);
					makekeysArgList.add(DEFAULT_CERT_NAME);
					String[] makeKeysArgs = 
						(String[]) makekeysArgList.toArray(new String[makekeysArgList.size()]);
					
					// set the working directory to the epoc32/build directory so the temp file get generated there
	 				subMonitor.setTaskName("Invoking makekeys...");
	 				cmdLauncher.setErrorParserManager(pkgPath.removeLastSegments(1), getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_SIS_BUILDER));
	 				int retVal = cmdLauncher.executeCommand(makekeys, makeKeysArgs, getResolvedEnvVars(config), buildDirPath);
	 				
	 				if (retVal != 0){
	 		   			cmdLauncher.writeToConsole("***Non-Zero Status: " + MAKEKEYS_EXE + " returned with exit value = " + retVal);
	 		   			CarbideBuilderPlugin.createCarbideProjectMarker(config.getCarbideProject().getProject(), IMarker.SEVERITY_ERROR,  MAKEKEYS_EXE + " returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
	 		   			return;
	 		   		}

	 		   		subMonitor.worked(1);
	 		   		if (subMonitor.isCanceled()) {
	 		   			return;
	 		   		}
		   		}

				// call signsis...
				cmdLauncher.writeToConsole("\n***Invoking " + SIGNSIS_EXE + "....\n");

				IPath signsis = config.getSDK().getToolsPath().append(SIGNSIS_EXE);
				
				List<String> signSISArgList = new ArrayList<String>();
				
				if (sisInfo.getAdditionalOptions().length() > 0){
					String additionalOpts = sisInfo.getAdditionalOptions();
					String[] addOptsArray = additionalOpts.split(" ");
					if (addOptsArray.length > 0){
						for (String currOpt : addOptsArray) {
							signSISArgList.add(currOpt);
						}
					}
				}
				
				signSISArgList.add("-s");
				
				if (createOutputFromPKGFileName){
					String sisName = pkgPath.toOSString().substring(0, pkgPath.toOSString().lastIndexOf(".")) + ".sis";
					signSISArgList.add(sisName);
					signSISArgList.add(sisName + "x");
				} else {
					signSISArgList.add(sisInfo.getUnsignedSISFullPath().toOSString());
					signSISArgList.add(sisInfo.getSignedSISFullPath().toOSString());
				}
				
		   		if (signingMethod == ISISBuilderInfo.SELF_SIGN) {
					signSISArgList.add(buildDirPath.append(DEFAULT_CERT_NAME).toOSString());
					signSISArgList.add(buildDirPath.append(DEFAULT_KEY_NAME).toOSString());
				} else {
					signSISArgList.add("\"" + sisInfo.getCertificateFullPath().toOSString() + "\"");
					signSISArgList.add("\"" + sisInfo.getKeyFullPath().toOSString() + "\"");
				}
				
				signSISArgList.add(password);
				
				String[] signSisArgs = (String[]) signSISArgList.toArray(new String[signSISArgList.size()]);

				subMonitor.setTaskName("Signing sisfile...");
				cmdLauncher.setErrorParserManager(pkgPath.removeLastSegments(1), getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_SIS_BUILDER));
				int retVal = cmdLauncher.executeCommand(signsis, signSisArgs, getResolvedEnvVars(config), pkgPath.removeLastSegments(1));
					
				if (retVal != 0) {
		   			cmdLauncher.writeToConsole("***Non-Zero Status: " + SIGNSIS_EXE + " returned with exit value = " + retVal);
		   			CarbideBuilderPlugin.createCarbideProjectMarker(config.getCarbideProject().getProject(), IMarker.SEVERITY_ERROR,  SIGNSIS_EXE + " returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
		   			return;
		   		}

		   		// now create the partial upgrade sisx file if necessary
		    	if (creatingPu) {
					if (!createPartialUpgradeSisx(sisInfo, config, cmdLauncher, subMonitor, createOutputFromPKGFileName)) {
						return;
					}
		    	} else {
					// delete the partial upgrade file and link if they exist since they are now obsolete
					IPath tempPkgBuildTreePath = getTempPkgBuildTreePath(sisInfo, config);
					IFile tempSisxFileLink = getTempSisIFile(pkgPath, getPUSisPath(sisInfo, tempPkgBuildTreePath, createOutputFromPKGFileName, true), config);
					if (tempSisxFileLink.exists()) {
						try {
							tempSisxFileLink.getRawLocation().toFile().delete();
							tempSisxFileLink.delete(0, null);
						} catch (CoreException e) {
							e.printStackTrace();
							CarbideBuilderPlugin.log(e);
						}
					}
		    	}
	   		}

	   		subMonitor.worked(1);
	   		if (subMonitor.isCanceled()) {
	   			return;
	   		}
		} else {
	   		subMonitor.worked(2);
	   		if (subMonitor.isCanceled()) {
	   			return;
	   		}

	   		cmdLauncher.writeToConsole("\nSISX file " + sisxFile.getAbsolutePath() + " already up to date.  Skipping...");
		}
    }
    
	private static boolean createPartialUpgradeSis(final ISISBuilderInfo sisInfo, final ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor, boolean createOutputFromPKGFileName, final List<File> modifiedFiles) {
		final IPath pkgPath = sisInfo.getPKGFullPath();

		// make a copy of the pkg file and put it in the epoc32 build tree
    	IPath tempPkgBuildTreePath = getTempPkgBuildTreePath(sisInfo, config);
		File tempPkgFile = tempPkgBuildTreePath.toFile();
		
		try {
			if (!tempPkgFile.exists()) {
				tempPkgFile.createNewFile();
			}
			FileUtils.copyFile(pkgPath.toFile(), tempPkgFile);
		} catch (IOException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
			return false;
		}

		// update the temp pkg file by setting the PU flag and removing any files that have not been modified
		PKGModelHelper.runWithPKGView(tempPkgBuildTreePath,
				new DefaultViewConfiguration(config.getCarbideProject(), config), 
				new PKGViewRunnableAdapter() {

				public Object run(IPKGView view) {
					PKGViewPathHelper helper = new PKGViewPathHelper(view, config);
					if (sisInfo != null) {
						helper.setSISBuilderInfo(sisInfo);
					}
					
					// we moved the file so we need to set the main directory for the parser so it picks up
					// relative paths correctly.
					helper.setMainDirectory(pkgPath.removeLastSegments(1));

					// set the PU flag in the package header
					IPKGHeader header = view.getPackageHeader();
					if (header != null) {
						String puFlag = "TYPE=PU";  //$NON-NLS-1$
						List<String> options = header.getOptions();
						if (!options.contains(puFlag)) {
							options.add(puFlag);
						}
						view.setPackageHeader(header);
					}
					
					// remove any files that haven't changed
					for (IPKGInstallFile file : view.getAllInstallFiles()) {
						Map<EPKGLanguage, IPath> sourceFiles = file.getSourceFiles();
						
						// with multiple launguages, we need to keep the install file if any one of
						// the language variant files has changed
						boolean anyVariantHasChanged = false;
						for (EPKGLanguage language : sourceFiles.keySet()) {
							IPath path = helper.getAbsolutePathFromViewPath(sourceFiles.get(language));
							if (path != null) {
								if (modifiedFiles.contains(path.toFile())) {
									anyVariantHasChanged = true;
									break;
								}
							}
						}
						
						if (!anyVariantHasChanged) {
							view.removeInstallFile(file);
						}
					}
					
					// remove any embedded sis files that haven't changed
					for (IPKGEmbeddedSISFile file : view.getAllEmbeddedSISFiles()) {
						Map<EPKGLanguage, IPath> sourceFiles = file.getSourceFiles();
						
						// with multiple launguages, we need to keep the embedded sis file if any one of
						// the language variant files has changed
						boolean anyVariantHasChanged = false;
						for (EPKGLanguage language : sourceFiles.keySet()) {
							IPath path = helper.getAbsolutePathFromViewPath(sourceFiles.get(language));
							if (path != null) {
								if (modifiedFiles.contains(path.toFile())) {
									anyVariantHasChanged = true;
									break;
								}
							}
						}
						
						if (!anyVariantHasChanged) {
							view.removeEmbeddedSISFile(file);
						}
					}

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

					return null;
				}
		});

		tempPkgBuildTreePath = resolvePKGFile(tempPkgBuildTreePath, config, tempPkgBuildTreePath);

		// create link to temp pkg file and mark it as derived.
		IFile tempPkgFileLink = getTempPkgIFile(pkgPath, tempPkgBuildTreePath, config);
		if (!tempPkgFileLink.exists()) {
			try {
				tempPkgFileLink.createLink(tempPkgBuildTreePath, 0, null);
				tempPkgFileLink.setDerived(true);
			} catch (CoreException e) {
				e.printStackTrace();
				CarbideBuilderPlugin.log(e);
				return false;
			}
		}

		// change the sis name and location as well
		IPath puSisPath = getPUSisPath(sisInfo, tempPkgBuildTreePath, createOutputFromPKGFileName, false);
		
		List<String> args = new ArrayList<String>();

		// add the search location if specified
		String searchDir = sisInfo.getContentSearchLocation();
		if (searchDir != null && searchDir.length() > 0) {
			args.add("-d" + searchDir);
		}

		args.add(tempPkgBuildTreePath.toOSString());
		args.add(puSisPath.toOSString());

		cmdLauncher.writeToConsole("\n***Invoking " + MAKESIS_EXE + " for partial upgrade....\n");
		cmdLauncher.setErrorParserManager(pkgPath.removeLastSegments(1), getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_SIS_BUILDER));

		IPath makeSisPath = config.getSDK().getToolsPath().append(MAKESIS_EXE);
		int retVal = cmdLauncher.executeCommand(makeSisPath, args.toArray(new String[args.size()]), getResolvedEnvVars(config), pkgPath.removeLastSegments(1));
		
    	SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		subMonitor.setTaskName("Building partial upgrade sis file");

		subMonitor.worked(1);
		if (subMonitor.isCanceled()) {
			return false;
		}
		
		if (retVal != 0){
			cmdLauncher.writeToConsole("***Non-Zero Status: " + MAKESIS_EXE + " returned with exit value = " + retVal);
			CarbideBuilderPlugin.createCarbideProjectMarker(config.getCarbideProject().getProject(), IMarker.SEVERITY_ERROR,  MAKESIS_EXE + " returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
			return false;
		} else {
			// create link to sis file
			IFile tempSisFile = getTempSisIFile(pkgPath, puSisPath, config);
			if (!tempSisFile.exists()) {
				try {
					tempSisFile.createLink(puSisPath, 0, null);
				} catch (CoreException e) {
					e.printStackTrace();
					CarbideBuilderPlugin.log(e);
					return false;
				}
			}
		}

		return true;
	}
	
	private static boolean createPartialUpgradeSisx(final ISISBuilderInfo sisInfo, final ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor, boolean createOutputFromPKGFileName) {
    	if (sisInfo.getSigningType() == ISISBuilderInfo.DONT_SIGN) {
    		return true;
    	}

    	String sisxName = "PU_" + sisInfo.getSignedSISFullPath().lastSegment();
		if (createOutputFromPKGFileName) {
			String pkgName = sisInfo.getPKGFullPath().lastSegment();
			sisxName = pkgName.substring(0, pkgName.lastIndexOf(".")) + ".sisx";
		}

    	IPath buildDirPath = getBuilder(config.getCarbideProject().getProject()).getMakefileDirectory(config);
		IPath puSisxPath = buildDirPath.append(sisxName);

    	SubMonitor subMonitor = SubMonitor.convert(monitor, 2);

		// call signsis...
		cmdLauncher.writeToConsole("\n***Invoking " + SIGNSIS_EXE + " for partial upgrade....\n");

		IPath signsis = config.getSDK().getToolsPath().append(SIGNSIS_EXE);
		
		List<String> signSISArgList = new ArrayList<String>();
		
		if (sisInfo.getAdditionalOptions().length() > 0){
			String additionalOpts = sisInfo.getAdditionalOptions();
			String[] addOptsArray = additionalOpts.split(" ");
			if (addOptsArray.length > 0){
				for (String currOpt : addOptsArray) {
					signSISArgList.add(currOpt);
				}
			}
		}
		
		signSISArgList.add("-s");
		signSISArgList.add(getPUSisPath(sisInfo, getTempPkgBuildTreePath(sisInfo, config), createOutputFromPKGFileName, false).toOSString());
		signSISArgList.add(puSisxPath.toOSString());
		
		String password = sisInfo.getPassword();

		if (sisInfo.getSigningType() == ISISBuilderInfo.SELF_SIGN) {
			signSISArgList.add(buildDirPath.append(DEFAULT_CERT_NAME).toOSString());
			signSISArgList.add(buildDirPath.append(DEFAULT_KEY_NAME).toOSString());
			
			if (password.length() == 0){
				password = DEAULT_PASSWORD;
				cmdLauncher.writeToConsole("No passphrase defined. Using: \"" + DEAULT_PASSWORD + "\"\n");
			}
		} else {
			signSISArgList.add("\"" + sisInfo.getCertificateFullPath().toOSString() + "\"");
			signSISArgList.add("\"" + sisInfo.getKeyFullPath().toOSString() + "\"");
		}
		
		signSISArgList.add(password);
		
		String[] signSisArgs = (String[]) signSISArgList.toArray(new String[signSISArgList.size()]);

		subMonitor.setTaskName("Signing partial upgrade sisfile...");
		IPath pkgPath = sisInfo.getPKGFullPath();
		cmdLauncher.setErrorParserManager(pkgPath.removeLastSegments(1), getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_SIS_BUILDER));
		int retVal = cmdLauncher.executeCommand(signsis, signSisArgs, getResolvedEnvVars(config), pkgPath.removeLastSegments(1));
			
		if (retVal != 0){
   			cmdLauncher.writeToConsole("***Non-Zero Status: " + SIGNSIS_EXE + " returned with exit value = " + retVal);
   			CarbideBuilderPlugin.createCarbideProjectMarker(config.getCarbideProject().getProject(), IMarker.SEVERITY_ERROR,  SIGNSIS_EXE + " returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
   			return false;
   		} else {
			// create link to sisx file
			IFile tempSisxFile = getTempSisIFile(pkgPath, puSisxPath, config);
			if (!tempSisxFile.exists()) {
				try {
					tempSisxFile.createLink(puSisxPath, 0, null);
				} catch (CoreException e) {
					e.printStackTrace();
					CarbideBuilderPlugin.log(e);
					return false;
				}
			}
   		}

   		subMonitor.worked(1);

   		return true;
	}

	private static IPath getTempPkgBuildTreePath(ISISBuilderInfo info, ICarbideBuildConfiguration config) {
    	IPath buildDirPath = getBuilder(config.getCarbideProject().getProject()).getMakefileDirectory(config);
    	return buildDirPath.append("PU_" + info.getPKGFullPath().lastSegment());
	}

	private static IPath getPUSisPath(ISISBuilderInfo sisInfo, IPath tempPkgBuildTreePath, boolean createOutputFromPKGFileName, boolean sisx) {
		IPath puSisPath = tempPkgBuildTreePath.removeLastSegments(1);
		if (createOutputFromPKGFileName) {
			String name = tempPkgBuildTreePath.lastSegment();
			if (sisx) {
				puSisPath = puSisPath.append(name.substring(0, name.lastIndexOf(".")) + ".sisx");
			} else {
				puSisPath = puSisPath.append(name.substring(0, name.lastIndexOf(".")) + ".sis");
			}
		} else {
			if (sisx) {
				puSisPath = puSisPath.append("PU_" + sisInfo.getSignedSISFullPath().lastSegment());
			} else {
				puSisPath = puSisPath.append("PU_" + sisInfo.getUnsignedSISFullPath().lastSegment());
			}
		}
		
		return puSisPath;
	}

	private static IFile getTempPkgIFile(IPath pkgPath, IPath tempPkgBuildTreePath, ICarbideBuildConfiguration config) {
		// create the file is in the same directory as the real pkg file.  if the pkg file is
		// not under the project then create the file in the root of the project.
		IPath tempPkgPath = null;
		IProject project = config.getCarbideProject().getProject();
		IPath projectRootPath = project.getLocation();
		if (projectRootPath != null && projectRootPath.isPrefixOf(pkgPath)) {
			IPath projectPathToPkg = pkgPath.removeFirstSegments(projectRootPath.segmentCount()).setDevice(null);
			tempPkgPath = projectPathToPkg.removeLastSegments(1).append(tempPkgBuildTreePath.lastSegment());
		} else {
			tempPkgPath = new Path(tempPkgBuildTreePath.lastSegment());
		}
		
		return project.getFile(tempPkgPath);
	}
	
	private static IFile getTempSisIFile(IPath pkgPath, IPath puSisPath, ICarbideBuildConfiguration config) {
		// create the file is in the same directory as the real pkg file.  if the pkg file is
		// not under the project then create the file in the root of the project.

		IPath tempSisPath = null;
		IProject project = config.getCarbideProject().getProject();
		IPath projectRootPath = project.getLocation();
		if (projectRootPath != null && projectRootPath.isPrefixOf(pkgPath)) {
			IPath projectPathToPkg = pkgPath.removeFirstSegments(projectRootPath.segmentCount()).setDevice(null);
			tempSisPath = projectPathToPkg.removeLastSegments(1).append(puSisPath.lastSegment());
		} else {
			tempSisPath = new Path(puSisPath.lastSegment());
		}
		
		return project.getFile(tempSisPath);
	}

	/**
     * Given PKG file to be built, check to see if it has supported macros and if so replace them so
     * the PKG file will contain current build context values.
     * @param pkgFile - The PKG file input
     * @param context - The sdk/plat/target currently building for
     * @param tempPKGFileName - The name of the PKG file to generate. If null or empty string one will be generated for you
     * @return IPath - If macros don't exist, the input path is returned others the new file
     * that is created, fully resolved is returned.
     */
    public static IPath resolvePKGFile(IPath pkgFile, ISymbianBuildContext context, IPath tempPKGFileName){
    	try {
    		String charset = null;
    		IFile iFile = FileUtils.convertFileToIFile(pkgFile.toFile());
    		if (iFile != null) {
    			charset = iFile.getCharset();
    		}
    		char[] pkgFileBuf = FileUtils.readFileContents(pkgFile.toFile(), charset);
    		String pkgFileStr = new String(pkgFileBuf);
    		
    		if (pkgFileStr.contains(PKG_SYMBOL_EPOCROOT) || 
    			pkgFileStr.contains(PKG_SYMBOL_PLATFORM) ||
    			pkgFileStr.contains(PKG_SYMBOL_TARGET) ) {
    				// need to create a new PKG file, resolved...
    				pkgFileStr = pkgFileStr.replace(PKG_SYMBOL_EPOCROOT, context.getSDK().getEPOCROOT());
    				pkgFileStr = pkgFileStr.replace(PKG_SYMBOL_PLATFORM, context.getPlatformString());
    				pkgFileStr = pkgFileStr.replace(PKG_SYMBOL_TARGET, context.getTargetString());
    				
    				IPath tmpPKGPath = pkgFile.removeLastSegments(1);
    				if (tempPKGFileName == null){
    					tmpPKGPath = tmpPKGPath.append(RESOLVED_PKG_PREFIX + pkgFile.lastSegment());
    				} else {
    					tmpPKGPath = tempPKGFileName;
    				}
    				
    	    		FileUtils.writeFileContents(tmpPKGPath.toFile(), pkgFileStr.toCharArray(), charset);
    	    		return new Path(tmpPKGPath.toString());	
    		} 
    	} catch (CoreException e){
			CarbideBuilderPlugin.log(e);
    		e.printStackTrace();
    	} 
    	
    	return pkgFile;
    }

    /**
     * Given a sis/sisx path, returns the absolute file system path of the associated
     * partial upgrade sis/sisx file if any, otherwise null.  note that the file may or
     * may not exist.
     * @param config the build configuration
     * @param sisPath the absolute file system path to the normal sis/sisx file
     * @return absolute path to PU sis/sisx file, or null
     * @since 2.0
     */
    public static IPath getPartialUpgradeSisPath(ICarbideBuildConfiguration config, IPath sisPath) {
    	for (ISISBuilderInfo info : config.getSISBuilderInfoList()) {
    		if (info instanceof SISBuilderInfo2) {
    			SISBuilderInfo2 info2 = (SISBuilderInfo2)info;
    			if (info2.isPartialUpgrade()) {
    	    		if (info2.getFinalSISFullPath().equals(sisPath)) {
    	    			return getPUSisPath(info, getTempPkgBuildTreePath(info, config), false, info.getSigningType() != ISISBuilderInfo.DONT_SIGN);
    	    		}
    			}
    		}
    	}
    	return null;
    }
    
	/**
	 * Invoke the ROM builder for the given build configuration
	 * @param config - The current configuration from where to get the settings from
	 * @param cmdLauncher - The object to use for the process execution
	 * @param monitor - An IProgressMonitor
	 */   
	public static void invokeROMBuilder(ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor) {

		IROMBuilderInfo info = config.getROMBuildInfo();
		if (info != null) {
			String commandLine = info.getCommandLine().trim();
			if (commandLine.length() > 0) {

				monitor.setTaskName("Building ROM Image");

				IPath workingDir = config.getCarbideProject().getINFWorkingDirectory();
				String workingDirString = info.getWorkingDirectory().trim();
				if (workingDirString.length() > 0) {
					workingDir = new Path(workingDirString);
				}
				
				cmdLauncher.writeToConsole("\n***Building ROM Image ....\n");
				List<String> args = tokenizeArgsWithQuotes(commandLine);
				args.add(0, "/c");

				cmdLauncher.setErrorParserManager(workingDir, getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_ROM_BUILDER));
				int retVal = cmdLauncher.executeCommand(CarbideCommandLauncher.getCmdExeLocation(), args.toArray(new String[args.size()]), getResolvedEnvVars(config), workingDir);
		   		if (retVal != 0){
		   			cmdLauncher.writeToConsole("***Non-Zero Status: Specified rom build command returned with exit value = " + retVal);  //$NON-NLS-1$
		   			CarbideBuilderPlugin.createCarbideProjectMarker(config.getCarbideProject().getProject(), IMarker.SEVERITY_ERROR,  "Specified rom build command returned with exit value = " + retVal, IMarker.PRIORITY_LOW); //$NON-NLS-1$
		   			cmdLauncher.writeToConsole("\nRom build failed\n");			   			
		   		} else { 
		   			cmdLauncher.writeToConsole("\nRom build completed\n");	//$NON-NLS-1$
		   		}
				cmdLauncher.writeToConsole(cmdLauncher.getTimingStats());					

				
		   		monitor.worked(1);
		   		if (monitor.isCanceled()) {
		   			return;
		   		}
			}
		}
		
    	monitor.done();
	}

	 /**
	  * Returns a list of arguments as strings.  The given string is basically split at spaces, but not if the space(s)
	  * is contained in quotes.  Arguments are typically quoted if they contain spaces.
	  * @param line
	  * @return
	  */
	private static List<String> tokenizeArgsWithQuotes(String line) {
		List<String> allTokens = new ArrayList<String>();

		int tokenStart = 0;
		boolean inQuotes = false;

		for (int index = 0; index < line.length() - 1; index++) {
			char c = line.charAt(index);
			if (c == '"') {
				inQuotes = !inQuotes;
			} else if (c == ' ' && !inQuotes) {
				allTokens.add(line.substring(tokenStart, index).replace("\"", ""));
				tokenStart = index + 1;
			}
		}
			
		// add the last token
		allTokens.add(line.substring(tokenStart, line.length()));

		return allTokens;
	}

	/**
	 * This method performs a build for a given Carbide build configuration.
	 * @param config - The Carbide configuration to build
	 * @param monitor - A progress monitor so user can cancel build (can be null)
	 * @param console - Where to pipe the output. If null, a new CConole will be created and existing console cleared.
	 * @param buildKind - FULL_BUILD, else incremental build assumed.
	 * 
	 * @deprecated use {@link #invokeBuild(ICarbideBuildConfiguration, IConsole, IProgressMonitor)} instead
	 */
	public static void callAbldBuildForConfiguration(ICarbideBuildConfiguration config, IProgressMonitor monitor, IConsole console, int buildKind, boolean clearMarkers){
		
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		
		if (console == null){
			console = CCorePlugin.getDefault().getConsole();
		}

		CarbideCommandLauncher launcher = new CarbideCommandLauncher(config.getCarbideProject().getProject(), monitor, console, getParserIdArray(config.getErrorParserId()), config.getCarbideProject().getINFWorkingDirectory());
		launcher.showCommand(true);

		invokeBuild(config, launcher, monitor, clearMarkers);
	}

	/**
	 * Invoke bldmake bldfiles on the current bld.inf and SDK.
	 * @param config - Config to generate makefiles for.
	 * @param cmdLauncher - The process launcher
	 * @param monitor - The progress monitor
	 * @param console - The console to write the messages to.
	 * @param env - The array of environment variables to be used for the process
	 * @param workingDir - The full path to the bld.inf file to be used as the current working directory
	 * 
	 * @deprecated use {@link #generateBldmakeMakefilesIfNecessary(ICarbideBuildConfiguration, CarbideCommandLauncher, IConsole, IProgressMonitor, boolean)} instead
	 */
	public static boolean invokeBldmakeBldFiles(ICarbideBuildConfiguration config, CarbideCommandLauncher cmdLauncher, IProgressMonitor monitor, IConsole console, String[] env, IPath workingDir ){
		return generateBldmakeMakefilesIfNecessary(config, cmdLauncher);
	}

	/**
	 * Check to see if abld.bat and the SDK's platform makefile exists. If either don't exist, re-generate makefiles
	 * This also tests to make sure the makefile target is OLDER than the bld.inf file.
	 * @param bldInfDir - The working dir of the bld.inf file (should not contain 'bld.inf')
	 * @param defaultConfig - The ISymbianBuildConfiguration to be built.
	 * @return true if makefiles need to be regenerated
	 * 
	 * @deprecated use {@link #needsBldmakeMakefileGeneration(ICarbideBuildConfiguration)}
	 */
	public static boolean projectNeedsMakefileGeneration(IPath bldInfDir, ICarbideBuildConfiguration defaultConfig){
		return needsBldmakeMakefileGeneration(defaultConfig);
	}

    /**
     * Checks the Problems view for any error markers.
     * @param project - IProject to check for problem markers
     * @return true if at least one error marker exists on the project
     */
    public static boolean projectHasBuildErrors(IProject project){
    	try {
			IMarker [] errorMarkers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			for (IMarker currMarker : errorMarkers){
				if (currMarker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR) == IMarker.SEVERITY_ERROR){
		    	   	if (currMarker.isSubtypeOf(CarbideBuilderPlugin.CARBIDE_PROJECT_MARKER) ||
		    	   		currMarker.isSubtypeOf(ICModelMarker.C_MODEL_PROBLEM_MARKER)) {
		        		return true;
		        	}
				}
			}
			
		} catch (CoreException e){
			e.printStackTrace();
		}
		
		return false;
    }
    
    public static IPath getBuilderMakefileDir(ICarbideBuildConfiguration config){
    	return getBuilder(config.getCarbideProject().getProject()).getMakefileDirectory(config);
    }
}
