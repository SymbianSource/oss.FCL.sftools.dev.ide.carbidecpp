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

import com.nokia.carbide.cpp.epoc.engine.model.makefile.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;

public class MakefileView extends MakefileViewBase implements IMakefileView {

	public MakefileView(ModelBase model, IMakefileViewConfiguration viewConfiguration) {
		super(model, viewConfiguration);
	}
}
