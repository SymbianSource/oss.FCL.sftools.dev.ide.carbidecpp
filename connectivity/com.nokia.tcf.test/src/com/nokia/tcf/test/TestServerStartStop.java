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
package com.nokia.tcf.test;

import com.nokia.tcf.impl.TCAPIConnection;

import junit.framework.TestCase;

public class TestServerStartStop extends TestCase {

	public void testServerStartStop() {
		// for non-plugin junit test
		TCAPIConnection api2 = new TCAPIConnection();
		api2.nativeStartServer();
		
		// for non-plugin junit test
		api2.nativeStopServer();
	}
}
