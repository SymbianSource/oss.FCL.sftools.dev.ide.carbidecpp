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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import java.text.MessageFormat;

import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.*;

/**
 * This is a document change which mirrors changes into the SourceGenContext-managed
 * ISourceManipulators.
 * 
 *
 */
public class SourceGenDocumentChange extends DocumentChange {

	private ISourceManipulatorProvider sourceManipulatorProvider;
	private IPath path;

	public SourceGenDocumentChange(IProject project, ISourceManipulatorProvider sourceManipulatorProvider, IPath path, IDocument document) {
		super(getLogicalName(project, path), document);
		this.sourceManipulatorProvider = sourceManipulatorProvider;
		this.path = path;
	}

	private static String getLogicalName(IProject project, IPath path) {
		String logicalName;
		if (project != null)
			logicalName = new Path(project.getName()).append(path).toString();
		else
			logicalName = path.toString();
		return MessageFormat.format(Messages.getString("SourceGenDocumentChange.DocumentChangeLabel"), new Object[] { logicalName }); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.TextChange#perform(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		/*Change change =*/ super.perform(pm);

		// persist the changes to our own buffer
		ISourceManipulator manipulator = sourceManipulatorProvider.getSourceManipulator(path);
		Check.checkState(manipulator != null);
		if (!manipulator.isLoaded()) {
			manipulator.load();
		}
		ITextReplacer replacer = manipulator.getTextReplacer();
		Check.checkState(replacer != null);
		char[] persistedText = manipulator.getPersistedText();
		int currentLength = persistedText == null ? 0 : persistedText.length;
		replacer.replaceText(0, currentLength, ((IDocument)getModifiedElement()).get());
		
		// do not allow undo
		return new NullChange();
	}
	
}
