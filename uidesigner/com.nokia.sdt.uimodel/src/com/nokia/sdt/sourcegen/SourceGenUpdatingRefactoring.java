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

package com.nokia.sdt.sourcegen;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.*;
import org.eclipse.ltk.core.refactoring.*;

/**
 * Refactoring for the sourcegen patch updating task.
 * <p>
 * At the moment I'm assuming that some other participant, like a
 * DesignerRefactoring, is actually converting
 * the project and issuing ISourceGenSession#generateSources() to get
 * the initial modified files (e.g. with updated owned sections from 
 * new components) into memory.
 * <p>
 * This participant, then, shadows those buffers into IDocuments upon
 * which the patches are generated, previewed, and applied.  Any changes
 * to the IDocuments -- e.g. from applying the Changes -- is reflected
 * back to the ISourceGenSession's modified file buffers.  
 * <p>
 * The driver -- e.g. the update manager -- is expected to finish
 * the operation by issuing ISourceGenProvider#saveGeneratedSources(). 
 * But when it saves the design, it shouldn't have sourcegen enabled,
 * otherwise it obliterates these changes. 
 * 
 *
 */
public class SourceGenUpdatingRefactoring extends Refactoring {

	private ISourceGenSession session;
	private ISourceGenComponentVersionProvider versionProvider;
	private ISourceGenUpgradingProvider upgradingProvider;
	
	public SourceGenUpdatingRefactoring(ISourceGenSession session, 
			ISourceGenComponentVersionProvider versionProvider) {
		this.session = session;
		this.versionProvider = versionProvider;
	}

	@Override
	public String getName() {
		return com.nokia.sdt.uimodel.Messages.getString("SourceGenUpdatingRefactoringParticipant.SourceUpdatingParticipantName"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		// the session must have been saved before this point
    	upgradingProvider = session.getSourceGenUpgradingProvider(versionProvider);
    	Check.checkState(upgradingProvider != null);
		
		return upgradingProvider.getValidationStatus();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		// the session must have been saved before this point
		return upgradingProvider.getValidationStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		Check.checkContract(upgradingProvider != null);
		Change changes = upgradingProvider.getChanges();
		return changes;
	}
	
	public PatchContext getPatchContext() {
		return upgradingProvider.getPatchContext();
	}

	public ISourceGenUpgradingProvider getSourceGenUpgradingProvider() {
		return upgradingProvider;
	}
};
