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
package com.nokia.sdt.symbian.updater;

import com.nokia.carbide.updater.extension.IRefactoringUpdater;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Refactoring;

import java.util.*;

public class RefactoringUpdater implements IRefactoringUpdater {
	
	private static Map<IProject, Set<IDesignerDataModel>> checkedProjectInfo =
				new HashMap<IProject, Set<IDesignerDataModel>>();

	public Refactoring getRefactoring(IProject project, IProgressMonitor monitor) {
		ProjectUpdateRefactoring updateRefactoring = 
			new ProjectUpdateRefactoring(new ProjectRefactoringProcessor(project));
		try {
			if (updateRefactoring.isApplicable())
				return updateRefactoring;
		} catch (CoreException e) {
		}
		finally {
			addCheckedProject(project);
		}
		
		return null;
	}

	public boolean hasRefactorings(IProject project, IProgressMonitor monitor) {
		boolean result = false;
		if (!isCheckedProject(project)) {
			result = ProjectRefactoringProcessor.hasUpdates(project);
		}
		return result;
	}
	

	public void refactoringComplete(IProject project) {
		removeCheckedProject(project);
		// Refactoring updating is memory intensive, so be
		// proactive about releasing resources.
		IProjectContext context = WorkspaceContext.getContext().getExistingContextForProject(project);
		if (context != null) {
			context.unloadCachedModels();
		}
	}
	
	static void trackModel(IDesignerDataModel model) {
		IProject project = model.getModelSpecifier().getProjectContext().getProject();
		synchronized(checkedProjectInfo) {
			Set<IDesignerDataModel> models = checkedProjectInfo.get(project);
			Check.checkState(models != null);
			models.add(model);
		}
	}

	private void addCheckedProject(IProject project) {
		synchronized (checkedProjectInfo) {
			if (!checkedProjectInfo.containsKey(project)) {
				checkedProjectInfo.put(project, new HashSet<IDesignerDataModel>());
			}
		}
	}
	
	private boolean isCheckedProject(IProject project) {
		synchronized(checkedProjectInfo) {
			return checkedProjectInfo.containsKey(project);
		}
	}
	
	private static void removeCheckedProject(IProject project) {
		Set<IDesignerDataModel> models;
		synchronized(checkedProjectInfo) {
			models = checkedProjectInfo.remove(project);
		}
		if (models != null) {
			for (IDesignerDataModel model : models) {
				model.dispose();
			}
		}
	}
}
