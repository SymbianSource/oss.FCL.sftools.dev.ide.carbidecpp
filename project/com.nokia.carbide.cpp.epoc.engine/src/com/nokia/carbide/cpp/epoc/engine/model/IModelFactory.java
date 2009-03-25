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

/**
 * Factory interface for creating models. 
 *
 */
public interface IModelFactory<Model extends IOwnedModel> {
	/**
	 * Create a model
	 * @param path full path used to identify the model
	 * @param document the document containing the model content
	 * @return new model
	 */
	Model createModel(IPath path, IDocument document);
}
