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
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cdt.builder.extension.IEnvironmentModifier;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideConfigurationChangedListener;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectPropertyChangedListener;
import com.nokia.carbide.cdt.internal.builder.BuildConfigurationData;
import com.nokia.carbide.cdt.internal.builder.CarbideBuildManager;
import com.nokia.carbide.cdt.internal.builder.CarbideCPPBuilderNature;
import com.nokia.carbide.cdt.internal.builder.CarbideSBSv2BuilderNature;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.cdt.core.model.*;
import org.eclipse.cdt.core.settings.model.CProjectDescriptionEvent;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionListener;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.make.core.IMakeBuilderInfo;
import org.eclipse.cdt.make.internal.core.BuildInfoFactory;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.*;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import java.net.URL;
import java.util.*;

/**
 * The activator class controls the plug-in life cycle
 */
public class CarbideBuilderPlugin extends AbstractUIPlugin implements ICProjectDescriptionListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cdt.builder"; //$NON-NLS-1$
	public static final String CARBIDE_BUILDER_EXTENSION_ID = "com.nokia.carbide.cdt.builder.carbideCPPBuilder"; //$NON-NLS-1$

	/**
	 * ID of the Carbide.c++ 1.2 project nature
	 */
	public static final String CARBIDE_PROJECT_NATURE_ID = "com.nokia.carbide.cdt.builder.carbideCPPBuilderNature";

	/**
	 * ID of the Carbide.c++ 1.1/1.0 project nature
	 */
	public static final String CORONA_PROJECT_NATURE_ID = "com.symbian.cdt.core.symbiannature";

	/**
	 * ID of the Carbide.c++ SBSv2 builder project nature
	 * @since 2.0
	 */
	public static final String CARBIDE_SBSV2_PROJECT_NATURE_ID = "com.nokia.carbide.cdt.builder.carbideSBSv2BuilderNature";

	public static final QualifiedName LINKED_PROJECT_ROOT_DIRECTORY = new QualifiedName(PLUGIN_ID, "LINKED_PROJECT_ROOT_DIRECTORY"); //$NON-NLS-1$

	/**
	 * Qualified named for SBSv2 projects.  This is set as a project session property by project
	 * creation wizards for SBSv2 projects so the proper nature will get added.
	 * @since 2.0
	 */
	public static final QualifiedName SBSV2_PROJECT = new QualifiedName(PLUGIN_ID, "SBSV2_PROJECT"); //$NON-NLS-1$

	// The shared instance
	private static CarbideBuilderPlugin plugin;
	
	private static CarbideBuildManager buildManager;
	
    public static final String CARBIDE_PROJECT_MARKER = "com.nokia.carbide.cdt.builder.CarbideBuilderMarkers";  //$NON-NLS-1$

	private static ListenerList<ICarbideConfigurationChangedListener> listeners = new ListenerList<ICarbideConfigurationChangedListener>();

	private static ListenerList<ICarbideProjectPropertyChangedListener> ppListeners = new ListenerList<ICarbideProjectPropertyChangedListener>();
	
	private static List<IEnvironmentModifier> envModifiers = null;
	private static IPath x86BuildDirectoryPath;
	
	/**
	 * The constructor
	 */
	public CarbideBuilderPlugin() {
		plugin = this;
	}
	
	public static void addBuildConfigChangedListener(ICarbideConfigurationChangedListener listener) {
		listeners.add(listener);
	}
	
	public static void removeBuildConfigChangedListener(ICarbideConfigurationChangedListener listener) {
		listeners.remove(listener);
	}
	
	public static void fireBuildConfigChangedChanged(ICarbideBuildConfiguration config) {
		for (ICarbideConfigurationChangedListener l : listeners) {
			l.buildConfigurationChanged(config);
		}
	}

	public static void addProjectPropertyChangedListener(ICarbideProjectPropertyChangedListener listener) {
		ppListeners.add(listener);
	}

	public static void removeProjectPropertyChangedListener(ICarbideProjectPropertyChangedListener listener) {
		ppListeners.remove(listener);
	}
	
	public static void fireProjectPropertyChanged(ICarbideProjectInfo cpi) {
		for (ICarbideProjectPropertyChangedListener l : ppListeners) {
			l.projectPropertyChanged(cpi);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		CoreModel.getDefault().getProjectDescriptionManager().addCProjectDescriptionListener(this, CProjectDescriptionEvent.APPLIED);
		
		Location installLocation = Platform.getInstallLocation();
		URL installURL = installLocation.getURL();
		IPath installPath = new Path(installURL.getFile());

		x86BuildDirectoryPath = installPath.append("x86Build"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		if (buildManager != null){
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(buildManager);
		}
		CoreModel.getDefault().getProjectDescriptionManager().removeCProjectDescriptionListener(this);
		
		super.stop(context);
	}

	/**
	 * A utility function.
	 * Try getting an IProject from the current perspective.  The logic used to determine
	 * what project to use is as follows:
	 * 
	 * 		- get the current selection - it doesn't matter which view.  if non-empty then try to
	 * 		  get the owner project of the selected item
	 * 
	 * 		- see if either the Project Explorer or the SPN view is open.  get the selection from
	 * 		  one of those views and get the owning project of the selected item.  if both views are
	 *		  open then default to the Project Explorer view selection.
	 *
	 * @return the project for ths current context, or null
	 */
	public static IProject getProjectInContext() {
		IWorkbenchPage page = CUIPlugin.getActivePage();
		if (page != null) {
			List<IProject> projects = getProjectsFromSelection(page.getSelection());
			
			if (projects.size() > 0) {
				// take the first one I guess..
				return projects.get(0);
			}

			for (IViewReference view : page.getViewReferences()) {
				if (view.getId().equals(ProjectExplorer.VIEW_ID)) {
					IViewPart viewpart = view.getView(false);
					if (viewpart != null && page.isPartVisible(viewpart)) {					
						ISelectionProvider selectionProvider = viewpart.getViewSite().getSelectionProvider();
						ISelection selection = selectionProvider != null ? selectionProvider.getSelection() : null;
						if (selection instanceof IStructuredSelection) {
							projects = getProjectsFromSelection((IStructuredSelection)selection);
							if (projects.size() > 0) {
								// take the first one I guess..
								return projects.get(0);
							}
						}
					}
				}
			}

			for (IViewReference view : page.getViewReferences()) {
				if (view.getId().equals("com.nokia.carbide.cpp.project.ui.views.SymbianProjectNavigatorView")) {
					IViewPart spnviewpart = view.getView(false);
					if (spnviewpart != null && page.isPartVisible(spnviewpart)) {					
						ISelection selection = spnviewpart.getViewSite().getSelectionProvider().getSelection();
						if (selection instanceof IStructuredSelection) {
							projects = getProjectsFromSelection((IStructuredSelection)selection);
							if (projects.size() > 0) {
								// take the first one I guess..
								return projects.get(0);
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * A utility function.  Gets the owning project(s) of the selected object(s) if any
	 * @param selection the current selection
	 * @return a list of projects - may be empty
	 */
	public static List<IProject> getProjectsFromSelection(ISelection selection) {
		List<IProject> projects = new ArrayList<IProject>();

		if (selection != null && !selection.isEmpty()) {
			if (selection instanceof ITextSelection) {
				
				IWorkbenchWindow activeWindow = getDefault().getWorkbench().getActiveWorkbenchWindow();;				
				IWorkbenchPage wpage = activeWindow.getActivePage();
				if (wpage != null) {
					IEditorPart ep = wpage.getActiveEditor();
					if (ep != null) {
						IEditorInput editorInput = ep.getEditorInput();
						if (editorInput instanceof IFileEditorInput) {
							IFile file = ((IFileEditorInput)editorInput).getFile();
							if (file != null) {
								projects.add(file.getProject());
							}
						}
					}
				}
			} else if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				
				for (Iterator iter = structuredSelection.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {

						if (element instanceof ICProject) {
							projects.add(((ICProject)element).getProject());
						} else if (element instanceof IResource) {
							projects.add(((IResource)element).getProject());
						} else if (element instanceof ICElement) {
							ICElement unit = (ICElement) element;

							// Get parent of the Element until we reach the owner project.
							while (unit != null && ! (unit instanceof ICProject))
								unit = unit.getParent();
							
							if (unit != null) {
								projects.add(((ICProject)unit).getProject());
							}
						} else if (element instanceof IAdaptable) {
							Object adapter = ((IAdaptable)element).getAdapter(IResource.class);
							if (adapter != null && adapter instanceof IResource) {
								projects.add(((IResource)adapter).getProject());
							} else {
								adapter = ((IAdaptable)element).getAdapter(ICProject.class);
								if (adapter != null && adapter instanceof ICProject) {
									projects.add(((ICProject)adapter).getProject());
								}
							}
						}
					}
				}
			}
		}
		return projects;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CarbideBuilderPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.nokia.carbide.cdt.builder", path);
	}
	
	public static IMakeBuilderInfo createBuildInfo(Preferences prefs, String builderID, boolean useDefaults) {
		return BuildInfoFactory.create(prefs, builderID, useDefaults);
	}
    
	public static void createCarbideProjectMarker(final IProject project, final int markerSeverity, final String message, final int markerPriority ){
		if (project == null){
			return;
		}
		
		WorkspaceJob job = new WorkspaceJob("Create problem marker") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				IMarker marker = project.createMarker(CARBIDE_PROJECT_MARKER);
				marker.setAttribute(IMarker.SEVERITY, markerSeverity);
				marker.setAttribute(IMarker.MESSAGE, message);
				marker.setAttribute(IMarker.PRIORITY, markerPriority);
				return Status.OK_STATUS;
			}
			
		};
		job.setRule(project);
		job.schedule();
	}
	
	public static IMakeBuilderInfo createBuildInfo(IProject project, String builderID) throws CoreException {
		return BuildInfoFactory.create(project, builderID);
	}
	
	public static String getCarbideBuilderExtensionID(){
		return CARBIDE_BUILDER_EXTENSION_ID;
	}
	
	public static void addBuildNature(IProject project) throws CoreException {
		boolean sbsv2Project = false;
		
		Object property = project.getSessionProperty(SBSV2_PROJECT);
		if (property != null && property instanceof Boolean) {
			sbsv2Project = ((Boolean)property).booleanValue();
		}
		
		if (sbsv2Project) {
			CarbideSBSv2BuilderNature.addNature(project, null);			
		} else {
			CarbideCPPBuilderNature.addNature(project, null);
		}
	}
	
	public static IPath getProjectRoot(IProject project) {
		// if the project was a linked import then it will have the root directory
		// set as a project property.  check for this first.  otherwise just use the
		// project location

/*		
	Removed for now as we're not supporting "linked" projects because there are far too many issues
	right now with linked resources.
		try {
			String linkedRoot = project.getPersistentProperty(LINKED_PROJECT_ROOT_DIRECTORY);
			if (linkedRoot != null && linkedRoot != "") {
				return new Path(linkedRoot);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
*/		
		return project.getLocation();
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

		
	public static ICarbideBuildManager getBuildManager() {
		
		if (buildManager == null){
			buildManager = new CarbideBuildManager();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(buildManager);
		}
		return buildManager;
	}

	public void handleEvent(CProjectDescriptionEvent event) {
		// make sure this is a Carbide project
		IProject project = event.getProject();
		if (project != null && CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
			// see if the active config changed.  make sure there was an old config though
			if (event.getOldCProjectDescription() != null) {
				ICConfigurationDescription newConfig = event.getNewCProjectDescription().getActiveConfiguration();
				ICConfigurationDescription oldConfig = event.getOldCProjectDescription().getActiveConfiguration();
				if (newConfig == null || oldConfig == null || !newConfig.getId().equals(oldConfig.getId())) {
					CConfigurationData data = newConfig.getConfigurationData();
					if (data instanceof BuildConfigurationData) {
						ICarbideBuildConfiguration buildConfig = ((BuildConfigurationData)data).getConfiguration();
						// notify any listeners
						CarbideBuilderPlugin.fireBuildConfigChangedChanged(buildConfig);
					}
				}
			}
		}
	}
	
	public static List<IEnvironmentModifier> getEnvironmentModifierExtensions() {
		if (envModifiers == null) {
			envModifiers = new ArrayList<IEnvironmentModifier>();

			IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(PLUGIN_ID + ".environmentModifier"); //$NON-NLS-1$
			IExtension[] extensions = extensionPoint.getExtensions();
			
			for (int i = 0; i < extensions.length; i++) {
				IExtension extension = extensions[i];
				IConfigurationElement[] elements = extension.getConfigurationElements();
				Check.checkContract(elements.length == 1);
				IConfigurationElement element = elements[0];
				
				boolean failed = false;
				try {
					Object extObject = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (extObject instanceof IEnvironmentModifier) {
						envModifiers.add((IEnvironmentModifier)extObject);
					} else {
						failed = true;
					}
				} 
				catch (CoreException e) {
					failed = true;
				}
				
				if (failed) {
					CarbideBuilderPlugin.log(Logging.newStatus(CarbideBuilderPlugin.getDefault(), 
							IStatus.ERROR,
							"Unable to load environmentModifier extension from " + extension.getContributor().getName()));
				}
			}
		}
		
		return envModifiers;
	}

	public static IPath getX86BuildDirectoryPath() {
		return x86BuildDirectoryPath;
	}
}
