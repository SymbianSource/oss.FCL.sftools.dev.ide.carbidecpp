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
package com.nokia.carbide.internal.updater;

import java.text.MessageFormat;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.internal.api.updater.IProjectUpdateSession;
import com.nokia.carbide.internal.updater.ui.ProjectUpdateDialog;
import com.nokia.carbide.updater.CarbideUpdaterPlugin;
import com.nokia.carbide.updater.extension.*;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.*;

/**
 * ProjectUpdateJob does updating work according
 * to the IProjectUpdateSession settings. It handles everything but
 * invoking the LTK refactoring dialog. Specifically, it:
 * - handles project-level UI and processing
 * - prompts for file-level updating, if desired
 * - gathers file-level update refactorings from extensions
 * - creates a separate WorkbenchJob (RefactoringJob) to run the
 *   LTK refactoring wizard and apply the refactorings.
 * The final LTK step is done in a separate job because it must run in the UI thread.
 */
public class ProjectUpdateJob extends WorkspaceJob {

	private final ProjectUpdateSession session;
	private final StatusBuilder statusBuilder = new StatusBuilder(CarbideUpdaterPlugin.getDefault());
	// This is the set of objects that will have project-level updates applied. They
	// need updating and the user selected them in the proposed list.
	private final List<IProject> selectedProjects = new ArrayList<IProject>();
	private final Map<IProject, String> projectCIndexers = new HashMap<IProject, String>();
	private List<Refactoring> refactorings = new ArrayList<Refactoring>();
	private IProgressMonitor monitor;
	
	private final String DIALOG_TITLE = Messages.getString("ProjectUpdateDialog.Title"); //$NON-NLS-1$
	
	private int totalWork = 100;
	private int completedWork = 0;
	
	private boolean doUpdateProjects = true;
	private boolean doRefactoring = true;
	
	// track actions taken within this job
	private boolean showedProjectUpdateDialog;
	private boolean showedConfirmFileDialog;
	private boolean updatedProjects;
	private boolean createdRefactoringJob;
	private boolean gatheredRefactorings;
	
	// use this scheduling rule to ensure two project update jobs
	// don't run at once
	class SchedulingRule implements ISchedulingRule {

		public boolean contains(ISchedulingRule rule) {
			return false;
		}

		public boolean isConflicting(ISchedulingRule rule) {
			return rule instanceof SchedulingRule && rule != this;
		}
	}
	
	public ProjectUpdateJob(ProjectUpdateSession session) {
		super(Messages.getString("ProjectUpdateJob.jobText")); //$NON-NLS-1$
		this.session = session;
		ArrayList<ISchedulingRule> rules = new ArrayList<ISchedulingRule>();
		rules.add(new SchedulingRule());
		// this is added because refactoring will need it later
		rules.add(ResourcesPlugin.getWorkspace().getRoot());
		for (IProject project : session.getUpdatableProjects()) {
			rules.add(project);
		}
		MultiRule mr = new MultiRule(rules.toArray(new ISchedulingRule[rules.size()]));
		setRule(mr);
		setUser(session.getUserInitated());
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) {
		this.monitor = monitor;
		IStatus result;
		try {
			monitor.beginTask(Messages.getString("ProjectUpdateJob.updatingProjectsTaskText"), totalWork); //$NON-NLS-1$
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
		
			if (session.getScanForProjects()) {
				session.setState(IProjectUpdateSession.State.SCANNING_PROJECTS);
				scanForProjects();
			} else {
				filterUpdatableProjects();
			}
			session.jobScanningProjectsComplete(this);
			worked(10);
			
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
		
			showProjectUpdateDialog();
			
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
			
			disableIndexers();
		
			if (doUpdateProjects) {
				doInitialWorkspaceScan();
				updateProjects();
			}
			worked(40);
			
			showErrorDialog();
	
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
		
			confirmUpdateFiles();
			if (doRefactoring) {
				session.setState(IProjectUpdateSession.State.GATHERING_REFACTORING);
				gatherRefactorings();
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
			
				worked(20);
				scheduleRefactorings();
				worked(30);
				
			} else {
				worked(50);
			}
			
			result = statusBuilder.createStatus(Messages.getString("UpdateProjectsJob.ProjectsNotUpdatedError"), null); //$NON-NLS-1$
			if (result == null) {
				result = Status.OK_STATUS;
			}
			
			if (!createdRefactoringJob) {
				if (gatheredRefactorings) {
					session.doneRefactorings();
				}
				restoreIndexers();
			}
			
			// Decide if the "all projects up to date" or "no refactorings needed"
			// dialogs should be shown.
			if (!showedProjectUpdateDialog && !updatedProjects && 
				!createdRefactoringJob && !session.getSilentIfEmpty()) {
				showEmptyDialog();
			} else if (doRefactoring && !createdRefactoringJob && 
					   showedConfirmFileDialog && !session.getSilentIfEmpty()) {
				showNoRefactoringsDialog();
			}
			
		} finally {
			monitor.worked(totalWork - completedWork);
			this.monitor = null;
			session.projectUpdateJobCompleted(this);
			session.setState(createdRefactoringJob? IProjectUpdateSession.State.APPLYING_REFACTORING :
				IProjectUpdateSession.State.COMPLETE);
		}
		return result;
	}
	
	private void worked(int amount) {
		completedWork += amount;
		monitor.worked(amount);
	}
	
	private void scanForProjects() {
		monitor.subTask(Messages.getString("ProjectUpdateJob.scanningProjectsTasks")); //$NON-NLS-1$
		ProjectUpdateManager pum = ProjectUpdateManager.instance();
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = workspaceRoot.getProjects();
		for (IProject project : projects) {
			if (pum.projectNeedsUpdate(project, monitor)) {
				pum.addExclusivelyToSessionForUpdate(project, session);
			}		
		}
	}
	
	private void filterUpdatableProjects() {
		ProjectUpdateManager pum = ProjectUpdateManager.instance();
		for(IProject project : session.getUpdatableProjects()) {
			if (!pum.projectNeedsUpdate(project, monitor)) {
				session.removeUpdatableProject(project);
			}
		}
	}
	
	private void postTriggers(IUpdateTrigger.UpdateType type) {
		if (!session.shouldCallTriggers())
			return;
		boolean didTrigger = !Startup.workspaceIsShuttingDown();
		List<IUpdateTrigger> triggers = Startup.getTriggers();
		for (IUpdateTrigger trigger : triggers) {
			trigger.postTrigger(type, didTrigger);
		}
	}
	
	private void gatherRefactorings() {
		monitor.subTask(Messages.getString("UpdateAction.RefactoringsJobName")); //$NON-NLS-1$
		// Start with all the requested refactoring projects, and remove projects
		// that need updating
		List<IProject> refactoringProjects = ObjectUtils.asList(session.getRefactoringProjects());
		ProjectUpdateManager pum = ProjectUpdateManager.instance();
		for (Iterator<IProject> iter = refactoringProjects.iterator(); iter.hasNext();) {
			if (pum.projectNeedsUpdate(iter.next(), monitor)) {
				iter.remove();
			}
		}

		for (IProject project : refactoringProjects) {
			gatheredRefactorings = true;
			for (IRefactoringUpdater refactoringUpdater : Startup.getRefactoringUpdaters()) {
				Refactoring refactoring = refactoringUpdater.getRefactoring(project, monitor);
				if (refactoring != null)
					refactorings.add(refactoring);
			}
		}
	}
	
	private void scheduleRefactorings() {
		if (!session.getShowRefactoringDialog() || Startup.workspaceIsShuttingDown()) {
			doRefactoring = false;
			return;
		}
		
		postTriggers(IUpdateTrigger.UpdateType.FILE);
		if (!refactorings.isEmpty()) {
			RefactoringJob job = new RefactoringJob(getShell(), refactorings, 
					session, projectCIndexers);
			refactorings = null; // big data, forget the reference to it ASAP
			createdRefactoringJob = true;
			// this is the same rule the ltk code uses
			ISchedulingRule rule = ResourcesPlugin.getWorkspace().getRoot();
			job.setRule(rule);
			if (session.getUserInitated() || showedConfirmFileDialog) {
				job.setUser(true);
			}
			job.schedule();
		}
	}
	
	private void showProjectUpdateDialog() {
		if (!session.getShowProjectUpdateDialog() || Startup.workspaceIsShuttingDown()) {
			doUpdateProjects = false;
			return;
		}
		
		if (session.getUpdatableProjects().length == 0) {
			doUpdateProjects = false;
			postTriggers(IUpdateTrigger.UpdateType.PROJECT);
			return;
		}
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				List<IProject> projects = ObjectUtils.asList(session.getUpdatableProjects());
				String docString = ProjectUpdateManager.instance().getProjectUpdaterDocumentation(projects);
				ProjectUpdateDialog dialog = 
					new ProjectUpdateDialog(getShell(), projects, docString);
				int ret = dialog.open();
				if (ret == Window.OK) {
					selectedProjects.addAll(dialog.getCheckedProjects());
					doUpdateProjects = selectedProjects.size() > 0;
				}
				else if (ret == Window.CANCEL) {
					doUpdateProjects = false;
					doRefactoring = false;
				}
				else  {
					// Skip button
					doUpdateProjects = false;
				}
			}
		});
		showedProjectUpdateDialog = true;
		postTriggers(IUpdateTrigger.UpdateType.PROJECT);
	}
	
	private void disableIndexers() {
		for (IProject project : selectedProjects) {
			String prevIndexer = ProjectUpdateSession.disableCProjectIndexer(project);
			projectCIndexers.put(project, prevIndexer);
		}
	}
	
	private void restoreIndexers() {
		for (IProject project : selectedProjects) {
			String indexerID = projectCIndexers.get(project);
			ProjectUpdateSession.enableCProjectIndexer(project, indexerID);
		}
	}
	
	private void doInitialWorkspaceScan() {
		List<String> messages = new ArrayList();
		for (IUpdateProjectsScanner scanner : Startup.getScanners()) {
			Collection<IProject> excludedProjectsCollection = 
				scanner.scanProjects(selectedProjects, monitor);
			for (IProject project : excludedProjectsCollection) {
				if (selectedProjects.contains(project)) {
					IStatus projectStatus = scanner.getProjectStatus(project);
					messages.add(projectStatus.getMessage());
					selectedProjects.remove(project);
				}
			}
		}

		if (messages.isEmpty())
			return;
		
		final String title = Messages.getString("UpdateProjectsJob.WarningTitle"); //$NON-NLS-1$
		final String message = 
			MessageFormat.format(Messages.getString("UpdateProjectsJob.ProjectsNotUpdatedMessageFormat"),  //$NON-NLS-1$
					new Object[] { TextUtils.formatTabbedList(messages) });
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openWarning(null, title, message);
			}
		});
	}

	private void updateProjects() {
		for (IProjectUpdater updater : Startup.getUpdaters()) {
			for (IProject project : selectedProjects) {
				if (updater.needsUpdate(project, monitor)) {
					IStatus status = updater.update(project, monitor);
					updatedProjects = true;
					if (!status.isOK())
						statusBuilder.add(status);
				}
			}
		}
	}
	
	private void showEmptyDialog() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openInformation(getShell(), DIALOG_TITLE, 
						Messages.getString("UpdateAction.NoUpdateProjectsMessage")); //$NON-NLS-1$
			}
		});
	}
	
	private void showNoRefactoringsDialog() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openInformation(getShell(), DIALOG_TITLE, 
						Messages.getString("UpdateAction.NoRefactoringsMessage")); //$NON-NLS-1$
			}
		});
	}
	
	private void confirmUpdateFiles() {
		if (!doRefactoring)
			return;
		
		if (!session.getShowRefactoringDialog()) {
			doRefactoring = false;
			return;
		}
		// If there was no project update dialog then there's no need
		// to also show the file update confirmation dialog.
		if (!showedProjectUpdateDialog || !session.getConfirmFileUpdates() ||
			WorkbenchUtils.isJUnitRunning()) {
			return;
		}
		
		getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				doRefactoring = MessageDialog.openQuestion(
						getShell(), Messages.getString("UpdateAction.FileUpdatesConfirmTitle"),  //$NON-NLS-1$
									Messages.getString("UpdateAction.FileUpdatesConfirmText")); //$NON-NLS-1$
			}
			
		});
		showedConfirmFileDialog = true;
	}
	
	private void showErrorDialog() {
		if (statusBuilder.getErrorCount() > 0 ||
				statusBuilder.getWarningCount() > 0 ||
				statusBuilder.getInfoCount() > 0) {
				final IStatus status = statusBuilder.createStatus(Messages.getString("UpdateProjectsJob.ProjectsNotUpdatedError"), null); //$NON-NLS-1$
				statusBuilder.reset();
				
				getShell().getDisplay().syncExec(new Runnable() {
					public void run() {
						ErrorDialog.openError(Display.getDefault().getActiveShell(), 
								Messages.getString("UpdateAction.ErrorDialogTitle"), null, status); //$NON-NLS-1$
					}
				});
			}
	
	}
	
	
	private Shell getShell() {
		return WorkbenchUtils.getSafeShell();
	}
}
