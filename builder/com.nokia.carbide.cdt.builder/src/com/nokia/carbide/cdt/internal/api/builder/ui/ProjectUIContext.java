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
package com.nokia.carbide.cdt.internal.api.builder.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

public class ProjectUIContext {

	private List<IProject> context = new ArrayList<IProject>();
	
	public ProjectUIContext(IWorkbenchWindow window) {
	}

	public boolean checkProjectContext(ISelection selection) {
		List<IProject> existingContext = context;
		context = CarbideBuilderPlugin.getProjectsFromSelection(selection);

		if (context.isEmpty()) {
			IProject project = CarbideBuilderPlugin.getProjectInContext();
			if (project != null)
				context.add(project);
		}
		
		return !context.equals(existingContext);
	}

	public ArrayList<IProject> getBuildableProjects() {
		ArrayList<IProject> result = new ArrayList<IProject>();
		for (Iterator iter = context.iterator(); iter.hasNext();) {
			IProject project = (IProject) iter.next();
			try {
				if (project.hasNature(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID))
					result.add(project);
			} catch (CoreException e) {
			}
		}
		return result;
	}

	public List<IProject> getProjects() {
		return context;
	}
	
	public IProject getFirstProject() {
		if (context.isEmpty())
			return null;
		return context.get(0);
	}
}
