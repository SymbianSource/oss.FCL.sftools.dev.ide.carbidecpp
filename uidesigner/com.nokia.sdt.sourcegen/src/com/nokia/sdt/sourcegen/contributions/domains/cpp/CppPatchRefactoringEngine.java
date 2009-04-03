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

import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.contributions.*;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.patcher.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.*;
import org.eclipse.ltk.core.refactoring.*;

import java.text.MessageFormat;
import java.util.*;

/**
 * 
 *
 */
public class CppPatchRefactoringEngine implements IDomainPatchRefactoringEngine {

	private CppDomain domain;
	private PatchContext patchContext;
	private ISourceManipulatorProvider sourceManipulatorProvider;
	
	private Map<ILocation, SourceGenDocumentLineRangePatchHandler> locationsToPatchHandlers;
	private Map<IPath, DocumentPatchHandler> pathsToDocumentPatchHandlers;
	private Map<ISourceGenPatch, TextEditChangeGroup> patchesToTextChangeGroups;
	private IProject project;

	/**
	 * 
	 */
	public CppPatchRefactoringEngine(CppDomain domain, PatchContext context, IProject project, ISourceManipulatorProvider sourceManipulatorProvider) {
		Check.checkArg(sourceManipulatorProvider);
		this.project = project;
		this.domain = domain;
		this.sourceManipulatorProvider = sourceManipulatorProvider;
		this.patchContext = context;
		
		locationsToPatchHandlers = new HashMap<ILocation, SourceGenDocumentLineRangePatchHandler>();
		pathsToDocumentPatchHandlers = new HashMap<IPath, DocumentPatchHandler>();
		patchesToTextChangeGroups = new HashMap<ISourceGenPatch, TextEditChangeGroup>();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IDomain#applyPatches(com.nokia.sdt.sourcegen.contributions.PatchContext)
	 */
	public void applyPatches(List<SourceGenPatch> patches) {
		// ordered list
		Map<IPath, List<ISourceGenPatch>> pathsToPatches = new HashMap<IPath, List<ISourceGenPatch>>();
		
		// go through the patches and organize by file
		for (SourceGenPatch patch : patches) {
			ILocation location = patch.getLocation();
			List<ISourceGenPatch> filePatches = pathsToPatches.get(location.getPath());
			if (filePatches == null) {
				filePatches = new ArrayList<ISourceGenPatch>();
				pathsToPatches.put(location.getPath(), filePatches);
			}
			filePatches.add(patch);
		}

		for (Iterator iter = pathsToPatches.keySet().iterator(); iter.hasNext();) {
			IPath path = (IPath) iter.next();
			List<ISourceGenPatch> filePatches = pathsToPatches.get(path);
			getFilePatchChanges(path, filePatches);
		}
	}
	
	static class SourceLocateException extends Exception {
		public SourceLocateException(String string) {
			super(string);
		}
	}

	private CodeRange getLocationSourceRange(ILocation location) throws SourceLocateException {
		if (!(location instanceof CppLocation))
			throw new SourceLocateException(Messages.getString("CppPatchEngine.UnknownSourceType")); //$NON-NLS-1$
		
		if (location.getState() != ILocation.S_RESOLVED)
			throw new SourceLocateException(Messages.getString("CppPatchEngine.SourceLocNotFound")); //$NON-NLS-1$
		
		CodeRange range = ((CppLocation) location).getCodeRange();
		if (range == null)
			throw new SourceLocateException(Messages.getString("CppPatchEngine.SourceLocNotFound")); //$NON-NLS-1$

		return range;
	}

	/**
	 * Get the range of lines that make up the location
	 * @param location
	 * @return pair of [start line, end line)
	 */
	private Pair<Integer, Integer> getLocationLines(IDocument document, ILocation location) throws SourceLocateException {
		CodeRange range = getLocationSourceRange(location);
		int startLine = 0;
		int endLine = 0;
		try {
			startLine = document.getLineOfOffset(range.getOffset());
			endLine = document.getLineOfOffset(range.getEndOffset());
			return new Pair<Integer, Integer>(startLine, endLine);
		} catch (BadLocationException e) {
			throw new SourceLocateException(e.getLocalizedMessage());
		}
	}


	/**
	 * Get Change objects for patches to the file.
	 * @param results 
	 * @param path project-relative path
	 * @param patches the patches for this file
	 */
	private void getFilePatchChanges(IPath path, List<ISourceGenPatch> patches) {
		// get the current lines for each of the locations
		final ISourceManipulator manipulator = sourceManipulatorProvider.getSourceManipulator(path);
		if (manipulator == null) {
			// um, goodbye all
			patchContext.failAll(patches, Messages.getString("CppPatchEngine.CannotLocateFile")); //$NON-NLS-1$
			return;
		}

		// get file contents into memory
		if (!manipulator.isLoaded()) {
			try {
				manipulator.load();
			} catch (CoreException e) {
				patchContext.failAll(patches, e.getLocalizedMessage());
				return;
			}
		}
		
		// get a patcher for the file as a whole
		char[] fileText = manipulator.getPersistedText();
		
		// make a shadow document, and listen for the patch being applied,
		// and mirror changes in our own source manipulator (yuck!)
		IDocument document = new Document(new String(fileText));

		// make the document handler
		DocumentPatchHandler documentPatchHandler = new DocumentPatchHandler(
				document, 
				MessageFormat.format(
						Messages.getString("CppPatchRefactoringEngine.DocumentChangesDescription"), //$NON-NLS-1$
						new Object[] { path }));
		documentPatchHandler.setDocumentChange(new SourceGenDocumentChange(project, sourceManipulatorProvider, path, document));
		
		// record changes for design as a whole
		pathsToDocumentPatchHandlers.put(path, documentPatchHandler);
		
		// now apply all the patches against the lines
		for (Iterator iterator = patches.iterator(); iterator.hasNext();) {
			SourceGenPatch patch = (SourceGenPatch) iterator.next();
			ILocation location = patch.getLocation();
			
			SourceGenDocumentLineRangePatchHandler handler = locationsToPatchHandlers.get(location);
			if (handler == null) {
				Pair<Integer, Integer> range; 
				try {
					range = getLocationLines(document, location);
					handler = new SourceGenDocumentLineRangePatchHandler(domain, documentPatchHandler, range.first, range.second);
					locationsToPatchHandlers.put(location, handler);
				} catch (SourceLocateException e) {
					patch.markConflicting(e.getLocalizedMessage());
					patchContext.getFailedPatches().add(patch);
					iterator.remove();
					continue;
				}
			}
			
			try {
				// apply the changes to the handler
				Patcher patcher = new Patcher(patch);
				if (patcher.apply(handler)) {
					// yay!
					patchContext.getApplicablePatches().add(patch);
				} else {
					// failed patch: insert details
					notateFailedPatch(patch, handler);
					patch.markConflicting(Messages.getString("CppPatchEngine.PatchDoesNotMatch")); //$NON-NLS-1$
					patchContext.getFailedPatches().add(patch);
					iterator.remove();
				}
			} catch (PatchException e) {
				patch.markConflicting(e.getLocalizedMessage());
				patchContext.getFailedPatches().add(patch);
				iterator.remove();
			}
			
		}
		
	}

	/**
	 * Add a notation near the expected patch location 
	 * @param patch
	 * @param handler 
	 */
	private void notateFailedPatch(SourceGenPatch patch, IPatchHandler handler) {
		String errorLineFormat = 
			Messages.getString("CppPatchRefactoringEngine.FailedPatchError"); //$NON-NLS-1$
		String errorLine = MessageFormat.format(errorLineFormat, 
				new Object[] { patch.getDescription(), patchContext.getLogFile() });
		handler.replaceLines(patch, 0, 0, new String[] { errorLine });
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IDomainPatchRefactoringEngine#getChanges()
	 */
	public Change getChanges() {
		Check.checkContract(pathsToDocumentPatchHandlers != null);

		CompositeChange changes = new CompositeChange(""); ////$NON-NLS-1$ //"C++ source and header changes");
		changes.markAsSynthetic();
		
		patchesToTextChangeGroups.clear();
		
		for (IPath path : pathsToDocumentPatchHandlers.keySet()) {
			DocumentPatchHandler handler = pathsToDocumentPatchHandlers.get(path);
			changes.add(handler.getChanges());
			for (Map.Entry<ISourceGenPatch, TextEditChangeGroup> entry : handler.getPatchToTextEditMap().entrySet())
				patchesToTextChangeGroups.put(entry.getKey(), entry.getValue());
		}
		
		pathsToDocumentPatchHandlers = null;
		return changes;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IDomainPatchRefactoringEngine#getPatchToTextEditMap()
	 */
	public Map getPatchToTextEditMap() {
		return patchesToTextChangeGroups;
	}
}
