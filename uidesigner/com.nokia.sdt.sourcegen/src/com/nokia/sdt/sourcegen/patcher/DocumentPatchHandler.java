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

package com.nokia.sdt.sourcegen.patcher;

import com.nokia.sdt.sourcegen.ISourceGenPatch;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.jface.text.*;
import org.eclipse.ltk.core.refactoring.DocumentChange;
import org.eclipse.ltk.core.refactoring.TextEditChangeGroup;
import org.eclipse.text.edits.*;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * This implements IPatchHandler by generating instructions that
 * will modify an IDocument (retrieved by getChanges()).<p>
 * A document needn't be an entire file.  <p>
 * Also, more than one patch handler may be created for the same
 * document, but the changes generated need to be manually merged
 * under a single DocumentChange object. (I think)
 * 
 *
 */
public class DocumentPatchHandler extends BaseSourceGenPatchHandler {
	private IDocument document;
	private DocumentChange documentChange;
	private MultiTextEdit textChanges;
	private Map<ISourceGenPatch, TextEditChangeGroup> patchesToTextChangeGroups;
	
	public DocumentPatchHandler(IDocument document, String logicalName) {
		this.document = document;
		this.documentChange = new DocumentChange(logicalName, document);
		this.textChanges = new MultiTextEdit();
		this.documentChange.setEdit(textChanges);
		this.patchesToTextChangeGroups = new HashMap<ISourceGenPatch, TextEditChangeGroup>();
	
	}

	public void setDocumentChange(DocumentChange change) {
		this.documentChange = change;
		this.documentChange.setEdit(textChanges);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#hasLine(int)
	 */
	public boolean hasLine(int index) {
		return index < document.getNumberOfLines();
	}
	
	public String getLine(int index) {
		try {
			IRegion info = document.getLineInformation(index);
			return document.get(info.getOffset(), info.getLength());
		} catch (BadLocationException e) {
			return null;
		}
	}
	
	public int getEndLineNumber() {
		return document.getNumberOfLines();
	}

	public int replaceLines(ISourceGenPatch patch, int fromIndex, int toIndex, String[] lines) {
		int startOffset = 0;
		int endOffset = 0;
		try {
			IRegion startLine = document.getLineInformation(fromIndex);
			startOffset = startLine.getOffset();
			if (toIndex > fromIndex) {
				IRegion endLine = document.getLineInformation(toIndex);
				endOffset = endLine.getOffset();
			} else {
				endOffset = startOffset;
			}
		} catch (BadLocationException e) {
			Check.failedArg(e);
			return fromIndex;
		}
		
		TextEdit edit;
		if (lines != null) {
			// lines[] must already have newlines
			StringBuffer catenated = new StringBuffer();
			for (String line : lines) {
				catenated.append(line);
			}
			edit = new ReplaceEdit(startOffset, endOffset - startOffset,
					catenated.toString());
			
		} else {
			edit = new DeleteEdit(startOffset, endOffset - startOffset);
		}
		
		// make a group for each patch with its name
		String description = patch.getDescription();
		if (description == null) {
			description = MessageFormat.format("Patch to {0}", 
					new Object[] { patch.getLocationPath() });
		}
		if (patch.isConflicting()) {
			description = description + " [CONFLICT]" ;
		}
		
		TextEditGroup group = new TextEditGroup(description);
		group.addTextEdit(edit);
		TextEditChangeGroup textEditChangeGroup = new TextEditChangeGroup(documentChange, group);
		documentChange.addTextEditChangeGroup(textEditChangeGroup);
		
		patchesToTextChangeGroups.put(patch, textEditChangeGroup);
		
		// and add the change to the document too
		((MultiTextEdit) documentChange.getEdit()).addChild(edit);
		
		return toIndex;
	}
	
	/**
	 * Get the sum total of the changes to the document.
	 * @return
	 */
	public DocumentChange getChanges() {
		return documentChange;
	}
	
	public Map<ISourceGenPatch, TextEditChangeGroup> getPatchToTextEditMap() {
		return patchesToTextChangeGroups;
	}
}
