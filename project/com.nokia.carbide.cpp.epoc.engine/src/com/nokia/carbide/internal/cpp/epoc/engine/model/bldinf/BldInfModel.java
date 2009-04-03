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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;


public class BldInfModel extends ModelBase<IBldInfView> implements IBldInfOwnedModel {

	public BldInfModel(IPath path, IDocument document) {
		super(path, document);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase#createView(com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase, com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration)
	 */
	@Override
	protected IBldInfView createView(ModelBase model, IViewConfiguration configuration) {
		return new BldInfView(model, 
				ParserFactory.createBldInfParser(), 
				configuration);
	}

}
