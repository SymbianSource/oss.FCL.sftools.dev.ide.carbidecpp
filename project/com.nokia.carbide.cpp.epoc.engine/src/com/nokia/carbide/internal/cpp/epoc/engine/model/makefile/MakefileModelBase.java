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

package com.nokia.carbide.internal.cpp.epoc.engine.model.makefile;

import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

public abstract class MakefileModelBase<View extends IView> extends ModelBase<View> {

	public MakefileModelBase(IPath path, IDocument document) {
		super(path, document);
	}

}
