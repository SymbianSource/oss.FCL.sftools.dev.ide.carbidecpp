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
package com.nokia.carbide.cdt.builder.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.carbide.cdt.builder.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestBldInfViewPathHelper.class);
		suite.addTestSuite(ProjectPropertiesTest.class);
		suite.addTestSuite(TestMMPViewPathHelper.class);
		suite.addTestSuite(TestPKGViewPathHelper.class);
		suite.addTestSuite(testCarbideChangeListener.class);
		suite.addTestSuite(TestDefaultTranslationUnitProvider.class);
		suite.addTestSuite(TestImageMakefileViewPathHelper.class);
		suite.addTestSuite(TestEpocEnginePathHelper.class);
		suite.addTestSuite(TestProjectExportsGatherer.class);
		suite.addTestSuite(BuilderPrefConstantsTest.class);
		suite.addTestSuite(TestEnvironmentModifier.class);
		suite.addTestSuite(TestCarbideProjectSettingsModifier.class);
		suite.addTestSuite(TestEpocEngineHelper.class);
		suite.addTestSuite(SBSv2QueryTests.class);
		
		// error parser tests....
		suite.addTestSuite(com.nokia.carbide.cdt.builder.test.errorParsers.TestMakmakeErrorParser.class);
		suite.addTestSuite(com.nokia.carbide.cdt.builder.test.errorParsers.TestRVCTErrorParser.class);
		suite.addTestSuite(com.nokia.carbide.cdt.builder.test.errorParsers.TestGcceErrorParser.class);
		suite.addTestSuite(com.nokia.carbide.cdt.builder.test.errorParsers.TestSBSv2ErrorParser.class);
		suite.addTestSuite(com.nokia.carbide.cdt.builder.test.errorParsers.TestMakeErrorParser.class);
		
		
		//$JUnit-END$
		return suite;
	}

}
