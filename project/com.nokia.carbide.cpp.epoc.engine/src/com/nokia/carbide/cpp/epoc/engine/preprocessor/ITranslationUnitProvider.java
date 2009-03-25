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

package com.nokia.carbide.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;

import java.io.File;

/**
 * This provides cached preprocessor translation units for files.
 * <p>
 * The provider must detect changes to the IDocument hosted in a returned translation unit
 * and reflect those changes when asked for the file the next time.  The provider
 * does <i>not</i> have to persist the contents of the file on a document change, though.
 * <p>
 * The key used here is File (instead of IPath) since it has proper
 * case sensitivity.  Such Files should be absolute canonical filesystem paths.
 *
 */
public interface ITranslationUnitProvider {
	/** 
	 * Locate an existing TU, or load it and parse it.  Listen for future changes
	 * to the document contents and identity (via modelDocumentProvider) and to the file.  
	 * <p>
	 * This represents only the given file (no #includes are expanded).
	 * @param file the full path to the file
	 * @param modelDocumentProvider the provider of IDocument instances for the file.
	 * @return existing or new TU; return null only if the file does not exist.
	 * @since Carbide.c++ v1.3.  API break, needed since the previous implementation could not 
	 * distinguish documents in memory from those on disk.
	 */
	ITranslationUnit getTranslationUnit(File file, IModelDocumentProvider modelDocumentProvider);

}
