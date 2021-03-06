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

import com.nokia.carbide.cpp.epoc.engine.model.IView;

public interface IDummyView extends IView<IDummyModel> {
	public void add(String text);
	public void remove(String text);
	public void change(String fromText, String toText);

}
