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
package com.nokia.carbide.cpp.project.core;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.internal.core.model.CModelManager;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cpp.internal.api.project.core.ProjectCorePluginUtility;
import com.nokia.carbide.cpp.internal.project.core.Messages;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * The activator class controls the plug-in life cycle
 */
public class ProjectCorePlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.project.core"; //$NON-NLS-1$

	public static final String CARBIDE_PROJECT_ID = ProjectCorePlugin.getUniqueId() + ".carbidecppproject"; //$NON-NLS-1$

	private static final String PROJECT_FILE_NAME = ".project"; //$NON-NLS-1$
	private static final String CDT_PROJECT_FILE_NAME = ".cproject"; //$NON-NLS-1$
	private static final String OLD_CDT_PROJECT_FILE_NAME = ".cdtproject"; //$NON-NLS-1$
	private static final String CARBIDE_BUILD_SETTINGS_FILE_NAME = ".settings/.carbide_build_settings"; //$NON-NLS-1$
	private static final String CDT_CORE_PREFS_FILE_NAME = ".settings/org.eclipse.cdt.core.prefs"; //$NON-NLS-1$

	// The shared instance
	private static ProjectCorePlugin plugin;
	
	// internal utility class
	private static ProjectCorePluginUtility pluginUtility;
	
	
	/**
	 * The constructor
	 */
	public ProjectCorePlugin() {
		plugin = this;
		pluginUtility = new ProjectCorePluginUtility();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		pluginUtility.startup();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		pluginUtility.shutdown();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ProjectCorePlugin getDefault() {
		return plugin;
	}

	/**
	 * Gets the unique id for this plugin
	 * @return the unique id for this plugin
	 */
	public static String getUniqueId() {
		if (getDefault() == null) {
			return PLUGIN_ID;
		}
		return getDefault().getBundle().getSymbolicName();
	}
	
	/**
	 * Creates an Eclipse project with the given name and location.  Does some error checking first to make
	 * sure there's not already another open project at that location.  Deletes any project files that exist
	 * at that location as well.
	 * <p>
	 * This method is intended to be used to create a project prior to adding folders/files and setting up
	 * Carbide build settings.
	 * </p>
	 * 
	 * @param name the name of the project to create
	 * @param location the full file system path where the .project file should be created.  pass null to use the default location.
	 * @return the newly created IProject
	 * @throws CoreException
	 */
	public static IProject createProject(String name, String location) throws CoreException {
		IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(name);

		if (!projectHandle.exists()) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			
			IProjectDescription description = workspace.newProjectDescription(projectHandle.getName());
			
			if (location != null && !location.trim().equals("")) { //$NON-NLS-1$
				// don't set the location if it's in the workspace.  Eclipse doesn't
				// like this and will refuse to create the project. see Eclipse Bugzilla
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=76417
				IPath workspaceRoot = ResourcesPlugin.getWorkspace().getRoot().getLocation();
				if (workspaceRoot != null) {
					IPath defaultLocation = workspaceRoot.append(name);
					IPath locationPath = Path.fromPortableString(location);
					if (defaultLocation.toOSString().compareToIgnoreCase(locationPath.toOSString()) != 0) {
						description.setLocation(locationPath);
						description.setLocationURI(URIUtil.toURI(locationPath.toOSString()));
					}
				}
			}
			
			// see if there is already a .project file at this location.  if so, see if it's a project in the
			// workspace, opened or closed.  if so then we need to throw an error because we can't overwrite it.
			// if it's not a project in the workspace, ask the user if we should overwrite it or not.
			IPath projectLocation = location != null ? new Path(location) : workspace.getRoot().getLocation().append(projectHandle.getName());
			IContainer container = workspace.getRoot().getContainerForLocation(projectLocation);
			if (container != null && container.getType() == IResource.PROJECT) {
				// there is a project at this location already
				throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, 0, Messages.getString("ProjectCorePlugin.TheProject") + container.getName() + Messages.getString("ProjectCorePlugin.AlreadyExistsInDirectory") + projectLocation.toOSString(), null)); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
			// see if .project, .cproject files and .settings directories exist in the file
			// system.  if so then they are probably left over from an older project and we can
			// just overwrite them.  the check above would have found them if the project was
			// a part of the workspace, so it's not an active project.  maybe we should ask the
			// user but that may be more confusing.
			File projectFile = projectLocation.append(PROJECT_FILE_NAME).toFile();
			if (projectFile.exists()) {
				projectFile.delete();
			}
			File cdtProjectFile = projectLocation.append(CDT_PROJECT_FILE_NAME).toFile();
			if (cdtProjectFile.exists()) {
				cdtProjectFile.delete();
			}
			File oldCdtProjectFile = projectLocation.append(OLD_CDT_PROJECT_FILE_NAME).toFile();
			if (oldCdtProjectFile.exists()) {
				oldCdtProjectFile.delete();
			}
			File carbideBuildFile = projectLocation.append(CARBIDE_BUILD_SETTINGS_FILE_NAME).toFile();
			if (carbideBuildFile.exists()) {
				carbideBuildFile.delete();
			}
			File cdtCorePrefsFile = projectLocation.append(CDT_CORE_PREFS_FILE_NAME).toFile();
			if (cdtCorePrefsFile.exists()) {
				cdtCorePrefsFile.delete();
			}

			// create the Eclipse project
			IProgressMonitor monitor = new NullProgressMonitor();
			projectHandle.create(description, monitor);
			projectHandle.open(monitor);

		} else {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, 0, Messages.getString("ProjectCorePlugin.TheProject") + name + Messages.getString("ProjectCorePlugin.AlreadyExistsInWorkspace"), null)); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return projectHandle;
	}
	
	/**
	 * Takes a plain Eclipse project and turns it into a Carbide.c++ project.
	 * <p>
	 * This method is intended to be called after creating a project using {@link ProjectCorePlugin#createProject(String, String)}
	 * and adding all folders and files to it.  It sets up the project so it has the Carbide.c++ build nature and sets up all of
	 * the project settings for it.
	 * </p>
	 * 
	 * @param project the project handle returned from {@link ProjectCorePlugin#createProject(String, String)}
	 * @param projectRelativeBldInfPath the project relative path to the bld.inf file
	 * @param buildConfigs the list of ISymbianBuildContext's to be used as build configs for the project.  can be empty but not null.
	 * @param infComponentsList the list of mmp/makes files if a subset is to be built, otherwise an empty list (not null) if the entire bld.inf should be built.
	 * @param debugMMP not used since 1.3
	 * @param pkgMappings is the Map<ISymbianBuildContext, String> where build contexts are mapped to pkg file relative paths - can be null for none.
	 * The String value in pkgMappings is same as {@link ISISBuilderInfo#setPKGFile(String)}
	 * @param monitor progress monitor for this operation.
	 * @return the ICProject handle
	 * @throws CoreException
	 */
	public static ICProject postProjectCreatedActions(IProject project, String projectRelativeBldInfPath, 
			List<ISymbianBuildContext> buildConfigs, List<String> infComponentsList, String debugMMP, 
			Map<ISymbianBuildContext, String> pkgMappings, IProgressMonitor monitor) throws CoreException {

		if (project == null || !project.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Invalid project passed into postProjectCreatedActions", null)); //$NON-NLS-1$
		}
		
		// make sure there is at least one build config as CDT will not allow you to create
		// a project with no build configs
		if (buildConfigs.size() < 1) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, 0, "At least one build configuration required for postProjectCreatedActions", null)); //$NON-NLS-1$
		}

		// disable build automatically while creating the project
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDesc = workspace.getDescription();
		boolean autoBuilding = workspaceDesc.isAutoBuilding();
		workspaceDesc.setAutoBuilding(false);
		workspace.setDescription(workspaceDesc);

        // OK, let's create the project now.
        ICProject cProject = CoreModel.getDefault().create(project);
        
        monitor.worked(1);
        if (monitor.isCanceled()) {
        	return cProject;
        }

		try {
			// do this ourselves rather than calling CCorePlugin#createCProject as we need open to not
			// background refresh, otherwise we can get many resource deltas after the project is created
			// and the resource listener has no way of knowing that it shouldn't add these new files to
			// the mmp file(s).
			// don't use the real monitor here as these calls would change the task name
			IProgressMonitor nullMonitor = new NullProgressMonitor();
			CProjectNature.addCNature(project, nullMonitor);
			CCorePlugin.getDefault().convertProjectFromCtoCC(project, nullMonitor);

	        monitor.worked(1);
	        if (monitor.isCanceled()) {
	        	return cProject;
	        }

			// add our carbide builder nature
			CarbideBuilderPlugin.addBuildNature(project);
			
			monitor.worked(1);
	        if (monitor.isCanceled()) {
	        	return cProject;
	        }

			monitor.subTask(Messages.getString("ProjectCorePlugin.CreatingProjectSettingsTask")); //$NON-NLS-1$
	        
			// create the c project description
			ICProjectDescription projDes = CCorePlugin.getDefault().createProjectDescription(project, false, true);
			
			// setup the builder settings
			ProjectCorePluginUtility.setupBuilderSettings(projDes, projectRelativeBldInfPath, buildConfigs, infComponentsList, pkgMappings);

			CModelManager.getDefault().resetBinaryParser(project);

			monitor.worked(1);
            if (monitor.isCanceled()) {
            	return cProject;
            }

		} finally {
		}

        workspaceDesc.setAutoBuilding(autoBuilding);
		workspace.setDescription(workspaceDesc);
		
		return cProject;
	}

	public static void log(IStatus status) {
		Logging.log(plugin, status);
	}

	public static void log(Throwable thr) {
		Logging.log(plugin, Logging.newStatus(plugin, thr));
	}
}
