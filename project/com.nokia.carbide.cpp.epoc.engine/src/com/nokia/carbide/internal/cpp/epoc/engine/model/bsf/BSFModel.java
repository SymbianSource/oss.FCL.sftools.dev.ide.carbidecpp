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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bsf;

import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bsf.IBSFOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bsf.IBSFView;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;


public class BSFModel extends ModelBase<IBSFView> implements IBSFOwnedModel {

	public BSFModel(IPath path, IDocument document) {
		super(path, document);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase#createView(com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase, com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration)
	 */
	@Override
	protected IBSFView createView(ModelBase model, IViewConfiguration configuration) {
		return new BSFView(model, configuration);
	}

}
