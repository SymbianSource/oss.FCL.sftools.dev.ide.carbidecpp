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

package com.nokia.carbide.cpp.epoc.engine.model;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import java.util.Map;

/**
 * This manages a single file's contents and provides "views" onto its contents
 * and resolves changes to such views to rewrite the contents.
 * <p>
 * This model may be modified by the owner.
 *
 */
public interface IOwnedModel<View> extends IModel<View> {
	/**
	 * Get the document representing the main file text.  A model is
	 * initially created with an empty document.
	 * @return main document
	 */
	IDocument getDocument();
	
	/**
	 * Set the model's main document.  This is only allowed when no views
	 * are attached.
	 */
	void setDocument(IDocument document);
	
	/** 
	 * Look up a document by full/absolute path.
	 * @param IPath the absolute path to the file as used in the DOM or
	 * IModel#getPath()
	 * @return IDocument or null for unknown file
	 */
	IDocument getDocument(IPath path);

	/** 
	 * Look up a document by full/absolute path.  
	 * This is only allowed to change existing documents when no views
	 * are attached.
	 * @param IPath the absolute path to the file as used in the DOM or
	 * IModel#getPath()
	 * @param document the document for the path
	 */
	void setDocument(IPath path, IDocument document);

	/**
	 * Get the map of used documents.
	 * @return map of absolute path (IPath) to document (IDocument)
	 */
	Map<IPath, IDocument> getDocumentMap();
	
	/**
	 * Parse the model from its document to create the preprocessor DOM.  
	 * <p>
	 * This is only valid when the document is set.
	 * <p>
	 * If any changes are detected and any views are open, they are all marked
	 * unsynchronized.
	 * @return load results (never null)
	 */
	IModelLoadResults parse();
	
	/**
	 * Set the model provider.  Called by a model provider only.
	 * @param modelProvider the provider that owns the model.
	 */
	void setModelProvider(IModelProvider<IOwnedModel<View>, IModel<View>> modelProvider);
	
	/**
	 * Dispose the model.
	 * <p>Throws IllegalStateException unless views are disposed.
	 */
	void dispose();
}
