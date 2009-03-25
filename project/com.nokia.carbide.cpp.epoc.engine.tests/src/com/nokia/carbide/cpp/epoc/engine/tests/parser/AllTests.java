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

package com.nokia.carbide.cpp.epoc.engine.tests.parser;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.carbide.cpp.epoc.engine.tests.parser");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestPreParser.class);
		suite.addTest(TestSystemMMPs.suite());
		suite.addTestSuite(TestParsingSpeed.class);
		suite.addTestSuite(TestMMPParser.class);
		suite.addTestSuite(TestPositionTrackingReader.class);
		suite.addTestSuite(TestBSFParser.class);
		suite.addTestSuite(TestBldInfParser.class);
		suite.addTestSuite(TestASTParser.class);
		suite.addTestSuite(TestPKGParser.class);
		suite.addTestSuite(TestMMPParserProblems.class);
		//$JUnit-END$
		suite.addTest(TestSystemMMPs.createSuite());
		return suite;
	}

}
