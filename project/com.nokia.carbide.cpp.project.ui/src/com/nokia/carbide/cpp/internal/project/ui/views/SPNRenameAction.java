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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.internal.ui.cview.CViewRenameAction;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.window.IShellProvider;

public class SPNRenameAction extends CViewRenameAction {

	private SymbianProjectNavigatorView view;
	
	public SPNRenameAction(IShellProvider shellProvider, SymbianProjectNavigatorView view) {
		super(shellProvider, view.getViewer());
		this.view = view;
	}

	@Override
	protected void runWithNewPath(IPath path, IResource resource) {
		super.runWithNewPath(path, resource);
		
		// refresh our view.  get the affected projects and refresh them.
		IProject project = resource.getProject();
		view.refreshProject(CoreModel.getDefault().create(project));
	}

	@Override
	public void run() {
		super.run();
		
		// refresh our view
		Set<IProject> projects = new HashSet<IProject>();
		for (Object o : getActionResources()) {
			if (o instanceof IResource) {
				projects.add(((IResource)o).getProject());
			}
		}
		
		for (IProject project : projects) {
			view.refreshProject(CoreModel.getDefault().create(project));
		}
	}

}
