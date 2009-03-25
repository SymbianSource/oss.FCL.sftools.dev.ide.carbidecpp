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
package com.nokia.sdt.component.symbian.actions;

import com.nokia.sdt.component.symbian.builder.UserComponentProjectNature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

import java.util.*;

public class SelectCustomComponentProjectsAction implements IWorkbenchWindowActionDelegate {
    private IWorkbenchWindow workbenchWindow;
    IWorkbenchPart workbenchPart;
    IEditorPart editor;
    
    public void init(IWorkbenchWindow window) {
        workbenchWindow = window;
    }
    
    public void dispose() {

    }

	/**
	 * Put up a dialog showing projects in workspace and allow
	 * user to select which of these is searched for custom components.
	 */
	private void selectProjects() {
		
		IProject[] projectArray = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<IProject> projects = new ArrayList(Arrays.asList(projectArray));
		
		for (Iterator iter = projects.iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			if (!project.isOpen())
				iter.remove();
		}
		
    	// If the editor list is empty, return.
        if (projects.isEmpty())
        	return;
        
        // Find the custom component projects
        List<IProject> customComponentProjects = new ArrayList<IProject>();
        for (Iterator iter = projects.iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			if (UserComponentProjectNature.hasNature(project))
				customComponentProjects.add(project);
		}
        
        // Convert the list into an element collection.
        AdaptableList input = new AdaptableList(projects);

        ListSelectionDialog dlg = new ListSelectionDialog(
                workbenchWindow.getShell(), input,
                new BaseWorkbenchContentProvider(),
                new LabelProvider() {

					public String getText(Object element) {
						return ((IProject) element).getName();
					}
                }, 
                com.nokia.sdt.component.symbian.actions.Messages.getString("SelectCustomComponentProjectsAction.DialogMessage"));  //$NON-NLS-1$

        dlg.setInitialSelections(customComponentProjects.toArray());
        dlg.setTitle(com.nokia.sdt.component.symbian.actions.Messages.getString("SelectCustomComponentProjectsAction.DialogTitle")); //$NON-NLS-1$
        
        int result = dlg.open();
        if (result == IDialogConstants.CANCEL_ID)
            return;

        final List newCustomComponentProjects = Arrays.asList(dlg.getResult());
        
        // turn off excluded projects
        for (Iterator iter = customComponentProjects.iterator(); iter
				.hasNext();) {
			IProject project = (IProject) iter.next();
			if (!newCustomComponentProjects.contains(project))
				UserComponentProjectNature.setNature(project, false);
		}
        
        // turn on included projects (redundantly)
        for (Iterator iter = newCustomComponentProjects.iterator(); iter
				.hasNext();) {
			IProject project = (IProject) iter.next();
			UserComponentProjectNature.setNature(project, true);
		}
	}

    public void run(IAction action) {
    	selectProjects();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(true);
    }
}
