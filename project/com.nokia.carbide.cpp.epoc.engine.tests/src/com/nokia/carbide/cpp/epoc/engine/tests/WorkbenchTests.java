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

package com.nokia.carbide.cpp.epoc.engine.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This runs tests that need the workbench.
 * It should be run as a JUnit Plugin Test.
 *
 */
public class WorkbenchTests {
	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.carbide.cpp.epoc.engine.tests (workbench only)");
		suite.addTest(com.nokia.carbide.cpp.epoc.engine.tests.workspace.AllTests.suite());
		return suite;
	}

}
