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
package com.nokia.carbide.cpp.internal.project.ui;

import com.nokia.carbide.cpp.internal.api.project.core.ProjectCorePluginUtility;
import com.nokia.carbide.cpp.internal.api.project.core.ResourceChangeListener;
import com.nokia.carbide.cpp.internal.project.ui.dialogs.MMPSelectionResolver;
import com.nokia.carbide.cpp.internal.project.ui.dialogs.UpdateProjectFilesQuery;
import com.nokia.carbide.cpp.internal.project.ui.preferences.PreferenceConstants;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.*;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ProjectUIPlugin extends AbstractUIPlugin implements IStartup {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.project.ui"; //$NON-NLS-1$

	// The shared instance
	private static ProjectUIPlugin plugin;
	

	/**
	 * The constructor
	 */
	public ProjectUIPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		if (keepProjectsInSync()) {
			ProjectCorePluginUtility.startProjectListener();
		}

		IPreferenceStore store = getPreferenceStore();
		if (store != null) {
			store.addPropertyChangeListener(new IPropertyChangeListener() {
				public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
					if (event.getProperty().equals(PreferenceConstants.PREF_KEEP_PROJECTS_IN_SYNC)) {
						if (keepProjectsInSync()) {
							ProjectCorePluginUtility.startProjectListener();
						} else {
							ProjectCorePluginUtility.stopProjectListener();
						}
					} else if (event.getProperty().equals(PreferenceConstants.PREF_ADDED_FILES_OPTION)) {
						ResourceChangeListener.setAddedUpdateOption(getAddFilesToProjectOption());
					} else if (event.getProperty().equals(PreferenceConstants.PREF_CHANGED_FILES_OPTION)) {
						ResourceChangeListener.setChangedUpdateOption(getChangedFilesInProjectOption());
					}
				}
			});
		}
		
		// set the dialogs for the listener
		ResourceChangeListener.setResolver(new MMPSelectionResolver(Messages.getString("SelectMMPsForNewSourceDialog.DialogMessage"))); //$NON-NLS-1$
		ResourceChangeListener.setUpdateQuery(new UpdateProjectFilesQuery());
		
		// set pref values
		ResourceChangeListener.setAddedUpdateOption(getAddFilesToProjectOption());
		ResourceChangeListener.setChangedUpdateOption(getChangedFilesInProjectOption());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ProjectUIPlugin getDefault() {
		return plugin;
	}

	public static String getUniqueId() {
		if (getDefault() == null) {
			return PLUGIN_ID;
		}
		return getDefault().getBundle().getSymbolicName();
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public void earlyStartup() {
	}

	public static void expandProject(IProject project) throws CoreException {
		// select and expand the project in the Project Explorer view
		IWorkbenchPage page = CUIPlugin.getActivePage();
		if (page != null && project.members().length > 0) {
			// Now findView is used instead of showView so that whichever view was shown before 
			// will be the one that will be shown.
			IWorkbenchPart part = page.findView(ProjectExplorer.VIEW_ID);
			if (part instanceof CommonNavigator) {
				CommonNavigator view = (CommonNavigator) part;
				view.getCommonViewer().refresh(ResourcesPlugin.getWorkspace().getRoot());
				view.selectReveal(new StructuredSelection(project));
				view.getCommonViewer().setExpandedState(project, true);
			}
		}
	}
	
	public static void projectCreated(final IProject project) {
		// expand the project root so the user can see the contents
		UIJob job = new UIJob(""){ //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) 
			{
				try {
					expandProject(project);
				} catch (CoreException e) {
					Logging.log(plugin, e.getStatus());
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}};
		job.setSystem(true);
		job.setRule(null); // no rule needed here - could just block important jobs
		job.schedule();

		// set the perspective to Carbide C/C++
		try {
			IWorkbench workbench = getDefault().getWorkbench();
			IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow == null) {
				IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
				activeWorkbenchWindow = windows[0];
			}
	
			final IPerspectiveDescriptor perspective = workbench.getPerspectiveRegistry().findPerspectiveWithId("com.nokia.carbide.cpp.CarbideCppPerspective"); //$NON-NLS-1$
			final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
			if (activePage != null) {
				job = new UIJob(""){ //$NON-NLS-1$
					public IStatus runInUIThread(IProgressMonitor monitor) {
						activePage.setPerspective(perspective);
						return Status.OK_STATUS;
					}};
				job.setSystem(true);
				job.setRule(null); // no rule needed here - could just block important jobs
				job.schedule();
			}
		} catch (IllegalStateException e) {
			// PlatformUI.getWorkbench() throws if running headless
		}
	}

	public static boolean keepProjectsInSync() {
		IPreferenceStore store = ProjectUIPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(PreferenceConstants.PREF_KEEP_PROJECTS_IN_SYNC);
	}

	public static void setKeepProjectsInSync(boolean keepInSync) {
		IPreferenceStore store = ProjectUIPlugin.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.PREF_KEEP_PROJECTS_IN_SYNC, keepInSync);
	}
	
	public static int getAddFilesToProjectOption() {
		IPreferenceStore store = ProjectUIPlugin.getDefault().getPreferenceStore();
		return store.getInt(PreferenceConstants.PREF_ADDED_FILES_OPTION);
	}

	public static void setAddFilesToProjectOption(int option) {
		IPreferenceStore store = ProjectUIPlugin.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.PREF_ADDED_FILES_OPTION, option);
	}

	public static int getChangedFilesInProjectOption() {
		IPreferenceStore store = ProjectUIPlugin.getDefault().getPreferenceStore();
		return store.getInt(PreferenceConstants.PREF_CHANGED_FILES_OPTION);
	}

	public static void setChangedFilesInProjectOption(int option) {
		IPreferenceStore store = ProjectUIPlugin.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.PREF_CHANGED_FILES_OPTION, option);
	}

	static public void log(IStatus status) {
		Logging.log(plugin, status);
	}

	static public void log(Throwable thr) {
		Logging.log(plugin, Logging.newStatus(plugin, thr));
	}

	static public void log(Throwable thr, String msg) {
		Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg, thr));
	}
}
