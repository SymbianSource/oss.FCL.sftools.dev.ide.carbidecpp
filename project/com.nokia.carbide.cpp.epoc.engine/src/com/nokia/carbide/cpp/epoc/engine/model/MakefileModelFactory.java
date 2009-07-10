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

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileOwnedModel;
import com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.MakefileModel;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

/**
 * This class is an internal implementation and will be moved to an internal package.
 * @deprecated Use {@link EpocEnginePlugin#getMakefileModelProvider()}
 */
public class MakefileModelFactory implements IModelFactory<IMakefileOwnedModel> {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IModelFactory#createModel(org.eclipse.core.runtime.IPath, org.eclipse.jface.text.IDocument)
	 */
	public IMakefileOwnedModel createModel(IPath path, IDocument document) {
		return new MakefileModel(path, document);
	}

}
