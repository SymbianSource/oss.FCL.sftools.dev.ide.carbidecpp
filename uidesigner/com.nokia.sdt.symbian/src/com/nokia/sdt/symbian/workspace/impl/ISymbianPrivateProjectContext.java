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
package com.nokia.sdt.symbian.workspace.impl;

import com.nokia.sdt.symbian.dm.DesignerDataModel;

public interface ISymbianPrivateProjectContext {

	// Enable handshaking between a newly loaded root
	// data model and the project context, to ensure
	// a current display lanaguage can be set
	void ensureCurrentLanguage(DesignerDataModel rootModel);
}
