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

package com.nokia.carbide.trk.support.connection;

import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;

import java.util.Map;

public class TCFConnection extends TCPIPConnection {

	public TCFConnection(IConnectionType connectionType, Map<String, String> settings) {
		super(connectionType, settings);
	}

	
}
