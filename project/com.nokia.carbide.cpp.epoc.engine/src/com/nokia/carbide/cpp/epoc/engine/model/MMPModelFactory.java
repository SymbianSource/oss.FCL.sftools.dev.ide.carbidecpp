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

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.MMPModel;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

public class MMPModelFactory  implements IModelFactory<IMMPOwnedModel> {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelFactory#createModel(org.eclipse.core.runtime.IPath, org.eclipse.jface.text.IDocument)
	 */
	public IMMPOwnedModel createModel(IPath path, IDocument document) {
		return new MMPModel(path, document);
	}

}
