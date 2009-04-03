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

package com.nokia.carbide.cpp.codescanner.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.nokia.carbide.cpp.codescanner.tests");

		//$JUnit-BEGIN$
		suite.addTestSuite(CSBuilderTest.class);
		suite.addTestSuite(CSCategoryTest.class);
		suite.addTestSuite(CSScriptTest.class);
		suite.addTestSuite(CSSeverityTest.class);
		suite.addTestSuite(CSRuleTest.class);
		suite.addTestSuite(CSConfigManagerTest.class);
		suite.addTestSuite(CSConfigSettingsTest.class);
		suite.addTestSuite(CSErrorParserTest.class);
		suite.addTestSuite(CSPluginTest.class);
		suite.addTestSuite(CSProjectSettingsTest.class);
		suite.addTestSuite(CSRulesSorterTest.class);
		suite.addTestSuite(CSFileFilterTest.class);
		suite.addTestSuite(CSKbRuleTest.class);
		suite.addTestSuite(CSKbRuleKeywordTest.class);
		suite.addTestSuite(CSKbManagerTest.class);
		//$JUnit-END$

		return suite;
	}

}
