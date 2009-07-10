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

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGOwnedModel;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.PKGModelHelper;
import com.nokia.carbide.internal.cpp.epoc.engine.model.pkg.PKGModel;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

/**
 * This class is an internal implementation and will be moved to an internal package.
 * @deprecated Use {@link PKGModelHelper#getPKGModelProvider()} when made into API
 */
public class PKGModelFactory  implements IModelFactory<IPKGOwnedModel> {

	public IPKGOwnedModel createModel(IPath path, IDocument document) {
		return new PKGModel(path, document);
	}

}
