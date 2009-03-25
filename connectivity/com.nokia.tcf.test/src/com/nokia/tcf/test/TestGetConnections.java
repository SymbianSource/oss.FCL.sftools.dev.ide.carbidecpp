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

import com.nokia.tcf.api.ITCAPIConnection;
import com.nokia.tcf.api.ITCConnection;
import com.nokia.tcf.api.TCFClassFactory;

import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * Test the GetConnections API
 */
public class TestGetConnections extends TestCase {

	@Override
	public TestResult run() {
		return super.run();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetConnections() {
//		TCFClassFactory factory = new TCFClassFactory();
		ITCAPIConnection api = TCFClassFactory.createITCAPIConnection();
		ITCConnection[] connections = api.getConnections();
		assertTrue(" should be null", connections == null);
	}
}
