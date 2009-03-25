/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.WorkbenchJob;

import com.nokia.carbide.internal.api.updater.IProjectUpdateSession;
import com.nokia.carbide.internal.updater.ui.RefactoringUpdaterWizard;
import com.nokia.carbide.updater.CarbideUpdaterPlugin;

/**
 * The job handles the final part of project updating, which is
 * to run the LTK refactoring wizard with refactorings generated
 * during ProjectUpdateJob. This is a WorkbenchJob running in the UI thread.
 */
class RefactoringJob extends WorkbenchJob {
	
	private final Shell shell;
	private List<Refactoring> refactorings;
	private final ProjectUpdateSession session;
	private final Map<IProject, String> savedIndexerSettings;

	RefactoringJob(Shell shell, List<Refactoring> refactorings, 
			ProjectUpdateSession session,
			Map<IProject, String> savedIndexerSettings) {
		super(Messages.getString("RefactoringJob.applyRefactoringJobText")); //$NON-NLS-1$
		this.shell = shell;
		this.refactorings = refactorings;
		this.session = session;
		this.savedIndexerSettings = savedIndexerSettings;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		RefactoringWizard wiz = new RefactoringUpdaterWizard(refactorings);
		refactorings = null; // big data, forget reference ASAP
		RefactoringWizardOpenOperation rwoop = new RefactoringWizardOpenOperation(wiz);
		try {
			rwoop.run(shell, Messages.getString("UpdateAction.FileUpdateOpName")); //$NON-NLS-1$
		} catch (InterruptedException e) {
		} finally {
			// it's vital that complete state is set
			try {
				session.doneRefactorings();
				restoreIndexers();
			} catch (Exception x) {
				CarbideUpdaterPlugin.log(x);
			}
			session.setState(IProjectUpdateSession.State.COMPLETE);
		}
		return Status.OK_STATUS;
	}
	
	/**
	 * The CDT indexer was disabled in ProjectUpdateJob. We re-enable after the refactorings
	 * have been applied.
	 */
	private void restoreIndexers() {
		for (IProject project : savedIndexerSettings.keySet()) {
			String indexerID = savedIndexerSettings.get(project);
			ProjectUpdateSession.enableCProjectIndexer(project, indexerID);
		}
	}
	
}