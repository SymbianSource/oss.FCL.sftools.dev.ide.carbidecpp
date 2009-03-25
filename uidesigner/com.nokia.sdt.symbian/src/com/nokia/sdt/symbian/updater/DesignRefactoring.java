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

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.IComponentManifest;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.sourcegen.ISourceGenComponentVersionProvider;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.sourcegen.ISourceGenUpgradingProvider;
import com.nokia.sdt.sourcegen.PatchContext;
import com.nokia.sdt.sourcegen.SourceGenUpdatingRefactoring;
import com.nokia.sdt.symbian.dm.ComponentManifestSourceGenVersionProvider;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.workspace.IProjectContext;

/**
 * Stub refactoring for .uidesign update
 * 
 *
 */
public class DesignRefactoring extends Refactoring {

	private String designName;
	private IDesignerDataModel model;
	private IProjectContext projectContext;
	private ISourceGenSession session;
	private boolean alreadyGotSources;
	private SourceGenUpdatingRefactoring sourceGenRefactoring;
	private boolean designNeedsUpdate;
	
	/**
	 * 
	 */
	public DesignRefactoring(IDesignerDataModel model, IProjectContext context) {
		this.model = model;
		this.designName = model.getModelSpecifier().getDisplayName();
		this.projectContext = context;
		this.designNeedsUpdate = false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
	 */
	@Override
	public String getName() {
		String pattern = "Update design ''{0}'' to new version";
		return MessageFormat.format(pattern, new Object[] { designName });
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		pm.beginTask("Gathering design update information...", 2);
		
		// check for out-of-date components
		IDesignerData data = ((DesignerDataModel) model).getDesignerData();
		IComponentManifest componentManifest = data.getComponentManifest();
		if (componentManifest.getComponentDeltas(data) == null) {
			return new RefactoringStatus();
		}

		// mark design as needing update
		designNeedsUpdate = true;
		
		// this can be called multiple times
		if (!alreadyGotSources) {
			alreadyGotSources = true;
	
			final CoreException excs[] = new CoreException[] { null };
			// generate sources so we can formulate sourcegen patches
			Display.getDefault().syncExec(new Runnable() {
	
				public void run() {
					try {
						session = projectContext.getSourceGenProvider().createSourceGenSession(model, model.getModelSpecifier());
						
						// get mapping info
						session.loadFromSource();

						// generate sources and get the patch information only
						session.setUpgradingMode(true);
						
						session.generateSource(new SubProgressMonitor(pm, 1));
					} catch (CoreException e) {
						excs[0] = e;
					}
				}
				
			});
			if (excs[0] != null) {
				return RefactoringStatus.create(excs[0].getStatus()); 
			}
			
			ISourceGenComponentVersionProvider componentVersionProvider = new ComponentManifestSourceGenVersionProvider(model);
			
			sourceGenRefactoring = new SourceGenUpdatingRefactoring(session, componentVersionProvider);

		}
		
		RefactoringStatus status = new RefactoringStatus();
		
		if (sourceGenRefactoring != null)
			status.merge(sourceGenRefactoring.checkInitialConditions(pm));
		
		pm.worked(1);
		pm.done();
		
		return status;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		
		if (sourceGenRefactoring != null)
			status.merge(sourceGenRefactoring.checkFinalConditions(pm));
		
		return status;
	}

	/**
	 * A "change" that commits sources to disk -- non-optional
	 * 
	 *
	 */
	class SaveSourcesAndModelChange extends Change {

		SaveSourcesAndModelChange() {
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ltk.core.refactoring.Change#isEnabled()
		 */
		@Override
		public boolean isEnabled() {
			return true;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ltk.core.refactoring.Change#setEnabled(boolean)
		 */
		@Override
		public void setEnabled(boolean enabled) {
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ltk.core.refactoring.Change#getName()
		 */
		@Override
		public String getName() {
			return "Save design with current version format";
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public void initializeValidationData(IProgressMonitor pm) {
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
			return new RefactoringStatus();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public Change perform(IProgressMonitor pm) throws CoreException {
			if (session != null) {
				// now save any patched sources
				session.getSourceGenProvider().saveGeneratedSources(pm);
				
				// revert to normal operation
				session.setUpgradingMode(false);
			}
			
			// and save model based on patched sources
			try {
				model.saveModel(pm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// not undoable
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
		 */
		@Override
		public Object getModifiedElement() {
			return null;
		}
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		CompositeChange change = new CompositeChange(getName());
		
		if (designNeedsUpdate && sourceGenRefactoring != null) {
			change.add(sourceGenRefactoring.createChange(pm));
		}

		// add hidden change to the end
		CompositeChange finalChanges = new CompositeChange(""); //$NON-NLS-1$ //"Finish up");
		finalChanges.markAsSynthetic();
		SaveSourcesAndModelChange saveSourcesChange = new SaveSourcesAndModelChange();
		finalChanges.add(saveSourcesChange);
		change.add(finalChanges);
		
		return change;
	}

	/**
	 * @return
	 */
	public PatchContext getPatchContext() {
		if (sourceGenRefactoring != null)
			return sourceGenRefactoring.getPatchContext();
		return null;
	}

	/**
	 * @return
	 */
	public ISourceGenUpgradingProvider getSourceGenUpgradingProvider() {
		if (sourceGenRefactoring != null)
			return sourceGenRefactoring.getSourceGenUpgradingProvider();
		return null;
	}

}
