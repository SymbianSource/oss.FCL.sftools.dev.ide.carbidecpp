/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentTokenLocation;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;


public class DocumentTokenLocation implements IDocumentTokenLocation {

	private IDocument document;
	private IPath path;

	public DocumentTokenLocation(IDocument document, IPath path) {
		this.document = document;
		this.path = path;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IDocumentTokenLocation))
			return false;
		IDocumentTokenLocation location = (IDocumentTokenLocation) obj;
		return location.getDocument().equals(document)
		&& location.getPath().equals(path);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentTokenLocation#getPath()
	 */
	public IPath getPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentTokenLocation#getDocument()
	 */
	public IDocument getDocument() {
		return document;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation#get(int, int)
	 */
	public String get(int offset, int length) {
		try {
			return document.get(offset, length);
		} catch (BadLocationException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation#getParentLocation()
	 */
	public ITokenLocation getParentLocation() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation#getParentOffset()
	 */
	public int getParentOffset() {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation#getParentLength()
	 */
	public int getParentLength() {
		return 0;
	}
}
