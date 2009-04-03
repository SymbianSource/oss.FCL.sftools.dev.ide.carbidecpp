/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.cpp.epoc.engine.model.pkg;

import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGOwnedModel;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGView;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

public class PKGModel extends ModelBase<IPKGView> implements IPKGOwnedModel {

	public PKGModel(IPath path, IDocument document) {
		super(path, document);
	}

	@Override
	protected IPKGView createView(ModelBase model,
			IViewConfiguration configuration) {
		return new PKGView(model, configuration);
	}

}
