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

package com.nokia.carbide.cpp.epoc.engine.model;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVOwnedModel;
import com.nokia.carbide.internal.cpp.epoc.engine.model.sbv.SBVModel;

public class SBVModelFactory implements IModelFactory<ISBVOwnedModel> {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelFactory#createModel(org.eclipse.core.runtime.IPath, org.eclipse.jface.text.IDocument)
	 */
	public ISBVOwnedModel createModel(IPath path, IDocument document) {
		return new SBVModel(path, document);
	}

}
