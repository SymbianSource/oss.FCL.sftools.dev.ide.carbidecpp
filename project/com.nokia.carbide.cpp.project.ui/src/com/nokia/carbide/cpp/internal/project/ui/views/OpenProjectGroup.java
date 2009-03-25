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
package com.nokia.carbide.cpp.internal.project.ui.views;

import java.util.Iterator;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.BuildAction;
import org.eclipse.ui.actions.CloseResourceAction;
import org.eclipse.ui.actions.CloseUnrelatedProjectsAction;
import org.eclipse.ui.actions.OpenResourceAction;
import org.eclipse.ui.ide.IDEActionFactory;

/**
 * This is the action group for actions such as Refresh Local, and Open/Close Project.
 */
public class OpenProjectGroup extends SPNViewActionGroup {

    private BuildAction buildAction;
    private BuildAction cleanAction;
	private OpenResourceAction openProjectAction;
	private CloseResourceAction closeProjectAction;
    private CloseUnrelatedProjectsAction closeUnrelatedProjectsAction;
	private RefreshSelectionAction refreshAction;

	// Menu tags for the build
	final String BUILD_GROUP_MARKER = "buildGroup"; //$NON-NLS-1$
	final String BUILD_GROUP_MARKER_END = "end-buildGroup"; //$NON-NLS-1$

	
	public OpenProjectGroup(SymbianProjectNavigatorView view) {
		super(view);
	}

	public void fillActionBars(IActionBars actionBars) {
        actionBars.setGlobalActionHandler(IDEActionFactory.BUILD_PROJECT.getId(), buildAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.OPEN_PROJECT.getId(), openProjectAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT.getId(), closeProjectAction);
        actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), refreshAction);
        actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_UNRELATED_PROJECTS.getId(), closeUnrelatedProjectsAction);
	}

	/**
	 * Adds the open project, close project and refresh resource actions to the
	 * context menu.
	 * <p>
	 * refresh-no closed project selected
	 * </p>
	 * <p>
	 * Both the open project and close project action may be on the menu at the
	 * same time.
	 * </p>
	 * <p>
	 * No disabled action should be on the context menu.
	 * </p>
	 * 
	 * @param menu
	 *            context menu to add actions to
	 */
	public void fillContextMenu(IMenuManager menu) {
        IStructuredSelection selection = (IStructuredSelection) getContext()
        .getSelection();
		boolean isProjectSelection = true;
		boolean hasOpenProjects = false;
		boolean hasClosedProjects = false;
		boolean hasBuilder = true; // false if any project is closed or does not have builder 
		Iterator resources = selection.iterator();
		
		while (resources.hasNext()
		        && (!hasOpenProjects || !hasClosedProjects || hasBuilder || isProjectSelection)) {
		    Object next = resources.next();
		    IProject project = null;
		
		    if (next instanceof IProject) {
				project = (IProject) next;
			} else if (next instanceof IAdaptable) {
				project = (IProject) ((IAdaptable) next)
		                .getAdapter(IProject.class);
			}
		
		    if (project == null) {
		        isProjectSelection = false;
		        continue;
		    }
		    if (project.isOpen()) {
		        hasOpenProjects = true;
		        if (hasBuilder && !hasBuilder(project)) {
					hasBuilder = false;
				}
		    } else {
		        hasClosedProjects = true;
		        hasBuilder = false;
		    }
		}
		
		if (!selection.isEmpty() && isProjectSelection
		        && !ResourcesPlugin.getWorkspace().isAutoBuilding()
		        && hasBuilder) {
		    // Allow manual incremental build only if auto build is off.
		    buildAction.selectionChanged(selection);
		    menu.add(buildAction);
			cleanAction.selectionChanged(selection);
			menu.add(cleanAction);
		}
		
		if (!hasClosedProjects) {
		    refreshAction.selectionChanged(selection);
		    menu.add(refreshAction);
		}
		if (isProjectSelection) {
		    if (hasClosedProjects) {
		        openProjectAction.selectionChanged(selection);
		        menu.add(openProjectAction);
		    }
		    if (hasOpenProjects) {
		        closeProjectAction.selectionChanged(selection);
		        menu.add(closeProjectAction);
		        closeUnrelatedProjectsAction.selectionChanged(selection);
		        menu.add(closeUnrelatedProjectsAction);
		    }
		}
		menu.add(new Separator());
		menu.add(new GroupMarker(BUILD_GROUP_MARKER));
		menu.add(new GroupMarker(BUILD_GROUP_MARKER_END));
	}

	/**
	 * Handles a key pressed event by invoking the appropriate action.
	 */
	public void handleKeyPressed(KeyEvent event) {
		if (event.keyCode == SWT.F5 && event.stateMask == 0) {
			if (refreshAction.isEnabled()) {
				refreshAction.run();
			}
			// Swallow the event
			event.doit = false;
		}
	}

    /**
     * Returns whether there are builders configured on the given project.
     *
     * @return <code>true</code> if it has builders,
     *   <code>false</code> if not, or if this could not be determined
     */
    boolean hasBuilder(IProject project) {
        try {
            ICommand[] commands = project.getDescription().getBuildSpec();
            if (commands.length > 0) {
				return true;
			}
        } catch (CoreException e) {
            // Cannot determine if project has builders. Project is closed 
            // or does not exist. Fall through to return false.
        }
        return false;
    }

    protected void makeActions() {
    	IWorkbenchPartSite site = getView().getSite();
		IWorkspace workspace = CUIPlugin.getWorkspace();

        buildAction = new BuildAction(site, IncrementalProjectBuilder.INCREMENTAL_BUILD);
		cleanAction = new BuildAction(site, IncrementalProjectBuilder.CLEAN_BUILD);
		cleanAction.setText(Messages.BuildGroup_CleanProject);
		openProjectAction = new OpenResourceAction(site);
		workspace.addResourceChangeListener(openProjectAction, IResourceChangeEvent.POST_CHANGE);
		closeProjectAction = new CloseResourceAction(site);
		workspace.addResourceChangeListener(closeProjectAction, IResourceChangeEvent.POST_CHANGE);
        closeUnrelatedProjectsAction = new CloseUnrelatedProjectsAction(site);
		workspace.addResourceChangeListener(closeUnrelatedProjectsAction, IResourceChangeEvent.POST_CHANGE);
		refreshAction = new RefreshSelectionAction(view);
		refreshAction.setDisabledImageDescriptor(getImageDescriptor("dlcl16/refresh_nav.gif"));//$NON-NLS-1$
		refreshAction.setImageDescriptor(getImageDescriptor("elcl16/refresh_nav.gif"));//$NON-NLS-1$
	}

	public void updateActionBars() {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        buildAction.selectionChanged(selection);
        cleanAction.selectionChanged(selection);
		openProjectAction.selectionChanged(selection);
		closeProjectAction.selectionChanged(selection);
        closeUnrelatedProjectsAction.selectionChanged(selection);
		refreshAction.selectionChanged(selection);
	}

	public void dispose() {
		IWorkspace workspace = CUIPlugin.getWorkspace();
		workspace.removeResourceChangeListener(closeProjectAction);
		workspace.removeResourceChangeListener(openProjectAction);
		workspace.removeResourceChangeListener(closeUnrelatedProjectsAction);
	}

}
