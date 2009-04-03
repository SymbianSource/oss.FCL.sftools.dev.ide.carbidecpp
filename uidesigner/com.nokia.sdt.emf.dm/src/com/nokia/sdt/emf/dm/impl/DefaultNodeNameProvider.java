/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.emf.dm.INodeNameProvider;

public class DefaultNodeNameProvider implements INodeNameProvider {

	public String getInstanceNameRoot(IComponent component) {
		return component.getInstanceNameRoot();
	}

}
