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

package com.nokia.carbide.cpp.epoc.engine.model;

import org.eclipse.jface.text.IDocument;

import java.io.File;

/**
 * This interface is used to look up documents for files. 
 * <p>
 * The provider must detect changes to any IDocument returned 
 * and reflect those changes when asked for the file the next time.  The provider
 * does <i>not</i> have to persist the contents of the file on a document change, though.
 * <p>
 * The key used here is File (instead of IPath) since it has proper
 * case sensitivity.  Such Files should be absolute canonical filesystem paths.
 * @since Carbide.c++ 1.3
 */
public interface IModelDocumentProvider {
	/** 
	 * Locate an existing document or load it.  Listen for future changes
	 * to the document and to the file.  
	 * @param file full pathed file
	 * @return IDocument instance; return null only if the file does not exist. 
	 */
	IDocument getDocument(File file);
}
