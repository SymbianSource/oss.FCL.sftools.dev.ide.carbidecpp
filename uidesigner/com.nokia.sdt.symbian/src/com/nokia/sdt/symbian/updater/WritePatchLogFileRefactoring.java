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

import com.nokia.sdt.sourcegen.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.text.edits.ReplaceEdit;

import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.util.List;

/**
 * 
 *
 */
public class WritePatchLogFileRefactoring extends Refactoring {

	private List<DesignRefactoring> designRefactorings;
	private IProject project;

	/**
	 * @param designRefactorings
	 */
	public WritePatchLogFileRefactoring(IProject project, List<DesignRefactoring> designRefactorings) {
		this.project = project;
		this.designRefactorings = designRefactorings;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#getName()
	 */
	@Override
	public String getName() {
		return "Write patch log file";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		
		// Return a change which shows no context in the preview
		// but which is populated only at when the change is run.
		final CompositeChange changes = new CompositeChange(""); //$NON-NLS-1$  //"Write patch log");
		changes.markAsSynthetic();

		changes.add(new Change() {
			Change logTextChange = null;
			
			@Override
			public String getName() {
				return "Write patch log";  
			}

			@Override
			public void initializeValidationData(IProgressMonitor pm) {
			}

			@Override
			public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
				return new RefactoringStatus();
			}

			@Override
			public Change perform(IProgressMonitor pm) throws CoreException {
				// calculate and evaluate now
				for (DesignRefactoring refactoring : designRefactorings) {
					ISourceGenUpgradingProvider sourceGenUpgradingProvider = refactoring.getSourceGenUpgradingProvider();
					if (sourceGenUpgradingProvider != null)
						sourceGenUpgradingProvider.detectSkippedPatches();
				}
				logTextChange = createLogFileChanges();
				return logTextChange.perform(pm);
			}

			@Override
			public Object getModifiedElement() {
				return getLogFile();
			}
			
		});
		
		return changes;
	}
	
	private IFile getLogFile() {
		PatchContext patchContext = null;
		for (DesignRefactoring refactoring : designRefactorings) {
			patchContext = refactoring.getPatchContext();
			if (patchContext != null)
				break;
		}
		
		if (patchContext == null)
			return null;
		
		return project.getFile(patchContext.getLogFile());
	}
	
	/**
	 * Generates changes that write text describing the results of the patching.
	 * @return
	 */
	private Change createLogFileChanges() {
		PatchContext patchContext = null;
		for (DesignRefactoring refactoring : designRefactorings) {
			PatchContext context = refactoring.getPatchContext();
			if (context == null)
				continue;
			if (patchContext == null) {
				patchContext = new PatchContext(context.getLogFile());
			}
			patchContext.merge(context);
		}
		
		if (patchContext == null)
			return new NullChange();
		
		IFile logFile = project.getFile(patchContext.getLogFile());
		
		String writeLogDetailsFormat;
		if (logFile.exists()) 
			writeLogDetailsFormat = "Prepend source patching log to {0}";
		else
			writeLogDetailsFormat = "Write source patching log to {0}";
		
		String writeLogDetails = MessageFormat.format(writeLogDetailsFormat, 
				new Object [] { patchContext.getLogFile() });
		
		// The filebuffers APIs use a full path in the filesystem for non-existent files,
		// but there's no way to pass this information along!
		// See Eclipse bug 118199 for the original issue.  See bug 159875 for issues involving TextFileChange.
		
		/*
		//IFile fullPathLogFile = logFile.getWorkspace().getRoot().getFile(logFile.getLocation());
		//TextFileChange tfc = new TextFileChange(writeLogDetails, fullPathLogFile);
		TextFileChange tfc = new TextFileChange(writeLogDetails, logFile);
		*/
		
		TextFileChange tfc = new TextFileChange(writeLogDetails, logFile) {
			protected IDocument acquireDocument(IProgressMonitor pm) throws CoreException {
				getFile().refreshLocal(IResource.DEPTH_ZERO, pm);
				if (!getFile().exists()) {
					getFile().create(new ByteArrayInputStream(new byte[0]), false, pm);
				}
				return super.acquireDocument(pm);
			}
		};

		String logText = PatchLogWriter.createLogText(
				project.getName(), patchContext);
		tfc.setEdit(new ReplaceEdit(0, 0, logText));
		
		return tfc;
	}

}
