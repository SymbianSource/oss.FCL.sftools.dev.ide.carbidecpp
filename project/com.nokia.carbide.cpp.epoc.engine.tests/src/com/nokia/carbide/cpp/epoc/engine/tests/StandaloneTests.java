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
 * This is the core set of tests which don't need a workbench to run (read: fast).
 * Run as a JUnit Test.
 *
 */
public class StandaloneTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.carbide.cpp.epoc.engine.tests");
		suite.addTest(com.nokia.carbide.cpp.epoc.engine.tests.dom.AllTests.suite());
		suite.addTest(com.nokia.carbide.cpp.epoc.engine.tests.parser.AllTests.suite());
		suite.addTest(com.nokia.carbide.cpp.epoc.engine.tests.preprocessor.AllTests.suite());
		suite.addTest(com.nokia.carbide.cpp.epoc.engine.tests.model.AllTests.suite());
		suite.addTest(com.nokia.carbide.cpp.epoc.engine.tests.misc.AllTests.suite());
		suite.addTest(com.nokia.carbide.cpp.epoc.engine.tests.conversion.AllTests.suite());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}

}
