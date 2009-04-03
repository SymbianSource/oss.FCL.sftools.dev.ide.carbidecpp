/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.updater;

import com.nokia.carbide.internal.api.updater.IProjectUpdateSession;
import com.nokia.carbide.internal.api.updater.IProjectUpdateSession.IStateListener;
import com.nokia.carbide.updater.CarbideUpdaterPlugin;
import com.nokia.carbide.updater.extension.*;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing the org.eclipse.ui.startup extension point
 * This class loads the projectUpdater and updateTrigger extensions, and checks the triggers
 * to see if a project update is required at startup.
 */
public class Startup implements IStartup {

	private static final String TRIGGER_EXTENSION_ID = CarbideUpdaterPlugin.PLUGIN_ID + ".updateTrigger"; //$NON-NLS-1$
	private static final String UPDATER_EXTENSION_ID = CarbideUpdaterPlugin.PLUGIN_ID + ".projectUpdater"; //$NON-NLS-1$
	private static final String SCANNER_EXTENSION_ID = CarbideUpdaterPlugin.PLUGIN_ID + ".updateProjectsScanner"; //$NON-NLS-1$
	private static final String REFACTORING_EXTENSION_ID = CarbideUpdaterPlugin.PLUGIN_ID + ".refactoringUpdater"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static Startup instance;
	
	private List<IUpdateTrigger> triggerExtensions;
	private boolean showProjectUpdateDialogOnStartup;
	private boolean showRefactoringDialogOnStartup;
	private List<IProjectUpdater> updaterExtensions;
	private List<IUpdateProjectsScanner> scannerExtensions;
	private List<IRefactoringUpdater> refactoringExtensions;
	private static boolean shuttingDown;
	
	private IProjectUpdateSession lastSession;
	
	public Startup() {
		super();
		instance = this;
	}

	public void earlyStartup() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWorkbenchListener(new IWorkbenchListener() {
			public void postShutdown(IWorkbench workbench) {}

			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				shuttingDown = true;
				return true;
			}
		});
		loadTriggers();
		for (IUpdateTrigger updateTrigger : triggerExtensions) {
			if (updateTrigger.workspaceNeedsUpdate(IUpdateTrigger.UpdateType.PROJECT)) {
				showProjectUpdateDialogOnStartup = true; // don't break
			}
			if (updateTrigger.workspaceNeedsUpdate(IUpdateTrigger.UpdateType.FILE)) {
				showRefactoringDialogOnStartup = true; // don't break
			}
		}
		
		if (showProjectUpdateDialogOnStartup || showRefactoringDialogOnStartup) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					RunRunnableWhenWorkbenchVisibleJob.start(new Runnable() {
						public void run() {
							IProjectUpdateSession session = CarbideUpdaterPlugin.getProjectUpdateManager().createSession();
							// Although the user didn't initiate this, if a trigger fired
							// let's give more feedback about what's going on.
							session.setUserInitiated(true);
							session.setScanForProjects(true);
							session.setScanForProjects(true);
							session.addAllWorkspaceRefactoringProjects();
							session.setConfirmFileUpdates(true);
							session.setSilentIfEmpty(true);
							session.setShouldCallTriggers(true);
							session.updateProjects();
						}
					});
				}
			});
		}
		
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				
				IResourceDelta delta = event.getDelta();
				if (delta != null) {
					try {
						delta.accept(new IResourceDeltaVisitor() {
							public boolean visit(IResourceDelta delta) throws CoreException {
								IResource resource = delta.getResource();
								if (resource == null)
									return false;
								
								if (resource.getType() == IResource.PROJECT) {
									IProject project = resource.getProject();
									if (projectWasAdded(delta) || projectWasOpened(delta, project)) {
										updateProject(project);
									}
									return false;
								}
								else if (resource.getType() == IResource.ROOT)
									return true;
								
								return false;
							}
						});
					} catch (CoreException e) {
					}
				}
			}
			
			private boolean projectWasAdded(IResourceDelta delta) {
				boolean added = delta.getKind() == IResourceDelta.ADDED;
				int flags = delta.getFlags();
				return added && (flags & IResourceDelta.MOVED_FROM) == 0;
			}
			
			private boolean projectWasOpened(IResourceDelta delta, IProject project) {
				boolean openChanged = delta.getKind() == IResourceDelta.CHANGED && 
									(delta.getFlags() & IResourceDelta.OPEN) != 0;
				
				return openChanged && project.isOpen();
			}
			
			private void updateProject(IProject project) {
				// The project updater slows down unit tests when it kicks
				// in as test projects are created. At the moment we have no
				// tests that rely on this resource listener firing, so just
				// disable it for JUnit.
				if (WorkbenchUtils.isJUnitRunning()) {
					return;
				}
				
				ProjectUpdateManager pum = ProjectUpdateManager.instance();
				boolean needNewSession = true;
				if (lastSession != null) {
					try {
						pum.addExclusivelyToSessionForUpdate(project, lastSession);
						pum.addExclusivelyToSessionForRefactoring(project, lastSession);
						needNewSession = false;
					}
					catch (IllegalStateException x) {
						lastSession = null;
					}
				}
				if (needNewSession) {
					IProjectUpdateSession session = pum.createSession();
					pum.addExclusivelyToSessionForUpdate(project, session);
					pum.addExclusivelyToSessionForRefactoring(project, session);
					session.updateProjects(3000);
					lastSession = session;
					lastSession.addStateListener(new IStateListener() {
						public void stateChanged(IProjectUpdateSession session) {
							if (session.getState() == IProjectUpdateSession.State.COMPLETE &&
								session == lastSession) {
								lastSession = null;
							}
						}
					});
				}
			}
		});
	}
	
	private List loadExtensions(Class cls, String id, String name) {
		List<Object> exts = new ArrayList<Object>();
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(id);
		IExtension[] extensions = extensionPoint.getExtensions();
		
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			Check.checkContract(elements.length == 1);
			IConfigurationElement element = elements[0];
			try {
				Object extObject = element.createExecutableExtension(CLASS);
				exts.add(extObject);
			} 
			catch (CoreException e) {
				CarbideUpdaterPlugin p = CarbideUpdaterPlugin.getDefault();
				String error = MessageFormat.format(Messages.getString("Startup.ExtensionLoadError"), new Object[] { name }); //$NON-NLS-1$
				Logging.log(p, Logging.newStatus(p, IStatus.ERROR, error));
			}
		}
		
		return exts;
	}
	
	@SuppressWarnings("unchecked")
	private void loadTriggers() {
		triggerExtensions = loadExtensions(IUpdateTrigger.class, TRIGGER_EXTENSION_ID, Messages.getString("Startup.UpdateTriggerName")); //$NON-NLS-1$
	}
	
	@SuppressWarnings("unchecked")
	private void loadUpdaters() {
		updaterExtensions= loadExtensions(IProjectUpdater.class, UPDATER_EXTENSION_ID, Messages.getString("Startup.ProjectUpdaterName")); //$NON-NLS-1$
	}
	
	@SuppressWarnings("unchecked")
	private void loadScanners() {
		scannerExtensions = loadExtensions(IUpdateProjectsScanner.class, SCANNER_EXTENSION_ID, Messages.getString("Startup.UpdateProjectsScannerName")); //$NON-NLS-1$
	}
	
	@SuppressWarnings("unchecked")
	private void loadRefactorings() {
		refactoringExtensions = loadExtensions(IUpdateProjectsScanner.class, REFACTORING_EXTENSION_ID, Messages.getString("Startup.RefactoringUpdaterName")); //$NON-NLS-1$
	}
	
	public static List<IUpdateTrigger> getTriggers() {
		if (instance == null)
			instance = new Startup();
		
		if (instance.triggerExtensions == null)
			instance.loadTriggers();
		
		return instance.triggerExtensions;
	}
	
	public static List<IProjectUpdater> getUpdaters() {
		if (instance == null)
			instance = new Startup();
		
		if (instance.updaterExtensions == null)
			instance.loadUpdaters();
		
		return instance.updaterExtensions;
	}
	
	public static List<IUpdateProjectsScanner> getScanners() {
		if (instance == null)
			instance = new Startup();
		
		if (instance.scannerExtensions == null)
			instance.loadScanners();
		
		return instance.scannerExtensions;
	}
	
	public static List<IRefactoringUpdater> getRefactoringUpdaters() {
		if (instance == null)
			instance = new Startup();
		
		if (instance.refactoringExtensions == null)
			instance.loadRefactorings();
		
		return instance.refactoringExtensions;
	}
	
	public static boolean workspaceIsShuttingDown() {
		return shuttingDown;
	}

}
