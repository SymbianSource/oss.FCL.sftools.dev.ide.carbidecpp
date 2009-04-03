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

package com.nokia.carbide.cpp.epoc.engine.tests.model.dummy;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;


public class DummyModel extends ModelBase implements IDummyModel {

	public DummyModel(IPath path, IDocument document) {
		super(path, document);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ModelBase#createView(com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel, com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewConfiguration)
	 */
	@Override
	protected IView createView(ModelBase model, IViewConfiguration configuration) {
		return new DummyView(model, configuration);
	}

}
