/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.viewwizard;

import com.nokia.sdt.symbian.cproject.CProjectUtils;
import com.nokia.sdt.symbian.workspace.SymbianProjectUtils;
import com.nokia.sdt.symbian.workspace.SymbianProjectUtils.ES60ProjectType;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import java.util.*;

public class ProjectsComboWidget {

	static final String NONE_TEXT = Messages.getString("ProjectsComboWidget.NO_PROJECT"); //$NON-NLS-1$
	Map displayNameToProject = new HashMap();
	IStructuredSelection selectedResources;
	Combo combo;
	
	public ProjectsComboWidget(Composite parent, int style, IStructuredSelection selectedResources) {
		combo = new Combo(parent, style);
		this.selectedResources = selectedResources;
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (isEligibleProject(project))
				displayNameToProject.put(getDisplayName(project), project);
		}
		
		if (!displayNameToProject.isEmpty()) {
			String[] displayNames = getDisplayNames();
			combo.setItems(displayNames);
			combo.select(getDefaultProjectIndex(displayNames));
		}
		else {
			combo.setItems(new String[] { NONE_TEXT } );
			combo.setEnabled(false);
		}
	}

	private boolean isEligibleProject(IProject project) {
		ES60ProjectType s60projectType = SymbianProjectUtils.getS60ProjectType(project);
		return s60projectType != ES60ProjectType.NotS60;
	}

	private String getDisplayName(IProject project) {
		return project.getName();
	}
	
	private String[] getDisplayNames() {
		Collection displayNames = displayNameToProject.keySet();
		return (String[]) displayNames.toArray(new String[displayNames.size()]);
	}
	
	private String getDefaultProjectDisplayName(IStructuredSelection selection) {
		if (selection == null)
			return null;
		
		ICElement celem = CProjectUtils.getInitialCElement(selection);
        if (celem != null) {
            ICProject cproject = celem.getCProject();
            if (cproject != null) {
            	return getDisplayName(cproject.getProject());
            }
        }
		
		return null;
	}
	
    public IProject getProjectFromDisplayName(String displayName) {
		Check.checkContract(displayNameToProject.containsKey(displayName));
		return (IProject) displayNameToProject.get(displayName);
	}

	private int getDefaultProjectIndex(String[] displayNames) {
		String defaultDisplayName = getDefaultProjectDisplayName(selectedResources);
		for (int i = 0; i < displayNames.length; i++) {
			if (displayNames[i].equals(defaultDisplayName))
				return i;
		}
		
		return -1;
	}
	
	public boolean isValid() {
    	return !displayNameToProject.isEmpty() && (combo.getSelectionIndex() >= 0);
	}
	
	public String getErrorString() {
    	if (displayNameToProject.isEmpty()) {
    		return Messages.getString("ProjectsComboWidget.NoEligibleProjectsError"); //$NON-NLS-1$
    	}
        if (combo.getSelectionIndex() < 0) {
            return Messages.getString("ProjectsComboWidget.SelectProjectPrompt"); //$NON-NLS-1$
        }
		
        return null;
	}

	/**
	 * @return the Combo control
	 */
	public Combo getControl() {
		return combo;
	}

	/**
	 * @return the text of the combo
	 */
	public String getText() {
		return combo.getText();
	}
}