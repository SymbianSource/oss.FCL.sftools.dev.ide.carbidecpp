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

package com.nokia.sdt.symbian.updater;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.workspace.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.ltk.core.refactoring.participants.*;
import org.eclipse.swt.widgets.Display;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class ProjectRefactoringProcessor extends RefactoringProcessor {

	static final QualifiedName IGNORE_PROJECT = 
		new QualifiedName(SymbianPlugin.PI_NAME, "IGNORE_AUTO_REFACTORING_UPDATING");

	private IProject project;
	
	//List<RefactoringParticipant> participants;
	
	private List<Refactoring> refactorings;
	private boolean isApplicable;
	private boolean checkedApplicable;

	public ProjectRefactoringProcessor(final IProject project) {
		this.project = project;
		//this.participants = new ArrayList<RefactoringParticipant>();
		this.refactorings = new ArrayList<Refactoring>();
		
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getElements()
	 */
	@Override
	public Object[] getElements() {
		return new Object[] { project };
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return "ProjectRefactoringProcessor"; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getProcessorName()
	 */
	@Override
	public String getProcessorName() {
		return "Project Updater"; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#isApplicable()
	 */
	@Override
	public boolean isApplicable() throws CoreException {
		if (checkedApplicable)
			return isApplicable;

		isApplicable = hasUpdates(project);
		checkedApplicable = true;
		
		return isApplicable;
	}
	
	public static boolean hasUpdates(IProject project) {
		try {
			if (project.getSessionProperty(IGNORE_PROJECT) != null)
				return false;
		} catch (CoreException e) {
			/*
			 * We should never get here.	
			 * Throws:
			 * CoreException if this method fails. Reasons include: 
			 * This resource does not exist.
			 * This resource is not local.
			 * This resource is a project that is not open. 
			*/
		}
		
		boolean hasUpdates = false;
		final IProjectContext context = WorkspaceContext.getContext().getContextForProject(project);
		if (context != null) {
			IDesignerDataModelSpecifier[] modelSpecifiers = context.getModelSpecifiers();
			for (IDesignerDataModelSpecifier spec : modelSpecifiers) {
			
				IRunWithModelAction action = new IRunWithModelAction() {
					public Object run(LoadResult lr) {
						boolean needsUpdate = false;
						IDesignerDataModel model = lr.getModel();
						if (model != null) {
							needsUpdate = !model.isModelUpToDate();
						}					
						return Boolean.valueOf(needsUpdate);
					}
				};
				
				Object modelNeedsUpdate = runWithLoadedModelNoSourceGen(spec, action);
				if (((Boolean)modelNeedsUpdate).booleanValue()) {
					hasUpdates = true;
					break;
				}
			}			
		}
		return hasUpdates;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {

		pm.beginTask(Messages.getString("ProjectRefactoringProcessor.CheckingDesigns"), 2); //$NON-NLS-1$
		
		final List<DesignRefactoring> designRefactorings = new ArrayList<DesignRefactoring>();
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				IProjectContext context = WorkspaceContext.getContext().getContextForProject(project);
				if (context == null)
					return;
				
				IDesignerDataModelSpecifier[] modelSpecifiers = context.getModelSpecifiers();
				for (IDesignerDataModelSpecifier spec : modelSpecifiers) {
					LoadResult lr = spec.load();
					if (lr.getModel() != null) {
						IDesignerDataModel model = lr.getModel();
						RefactoringUpdater.trackModel(model);
						DesignRefactoring designRefactoring = new DesignRefactoring(model, context);
						refactorings.add(designRefactoring);
						designRefactorings.add(designRefactoring);
					}
				}
			}
			
		});

		// add a refactoring to write the log file
		if (!refactorings.isEmpty())
			refactorings.add(new WritePatchLogFileRefactoring(project, designRefactorings));
		
		pm.worked(1);
		
		RefactoringStatus status = new RefactoringStatus();

		// check for build configurations, and fail if none in project
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		List<ICarbideBuildConfiguration> configurations = null;
		if (cpi != null)
			configurations = cpi.getBuildConfigurations();
		if (configurations == null || configurations.isEmpty()) {
			status.addFatalError(
					MessageFormat.format(
							Messages.getString("ProjectRefactoringProcessor.NoBuildConfigsError"),  //$NON-NLS-1$
							new Object[] { project.getName() }));
		}
		else {
			for (Refactoring refactoring : refactorings) {
				RefactoringStatus subStatus = refactoring.checkInitialConditions(pm);
				status.merge(subStatus);
			}
		}
		
		pm.worked(1);
		
		pm.done();
		
		return status;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException,
			OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		for (Refactoring refactoring : refactorings) {
			RefactoringStatus subStatus = refactoring.checkFinalConditions(pm);
			status.merge(subStatus);
		}
		return status;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		String fmt = Messages.getString("ProjectRefactoringProcessor.ProjectChanges"); //$NON-NLS-1$
		String name = MessageFormat.format(fmt, new Object[] { project.getName() });
		CompositeChange change = new CompositeChange(name);
		
		for (Refactoring refactoring : refactorings) {
			Change subChange = refactoring.createChange(pm);
			change.add(subChange);
		}
		
		return change;
		
	}
	


	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#loadParticipants(org.eclipse.ltk.core.refactoring.RefactoringStatus, org.eclipse.ltk.core.refactoring.participants.SharableParticipants)
	 */
	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		return new RefactoringParticipant[0];
	}
	
	private static Object runWithLoadedModelNoSourceGen(final IDesignerDataModelSpecifier specifier, 
			final IDesignerDataModelSpecifier.IRunWithModelAction action) {
		final Object[] runResult = new Object[1];
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				runResult[0] = specifier.runWithLoadedModelNoSourceGen(action);
			}
			
		});
		return runResult[0];
	}
}
