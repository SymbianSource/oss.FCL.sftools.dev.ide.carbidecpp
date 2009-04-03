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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.carbide.cpp.epoc.engine.tests.model");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestBldInfView2.class);
		suite.addTestSuite(TestBldInfViewBug4054.class);
		suite.addTestSuite(TestMMPView2.class);
		suite.addTestSuite(TestBldInfView.class);
		suite.addTestSuite(TestMMPView.class);
		suite.addTestSuite(TestStandaloneModelProvider.class);
		suite.addTestSuite(TestViewChanging.class);
		suite.addTestSuite(TestBldInfView3.class);
		suite.addTestSuite(TestPKGView.class);
		suite.addTestSuite(TestModelProvider.class);
		suite.addTestSuite(TestBSFView.class);
		suite.addTestSuite(TestModelsAndViews.class);
		suite.addTestSuite(ViewFilterTests.class);
		suite.addTestSuite(TestImageMakefileView.class);
		suite.addTestSuite(TestMMPView4.class);
		suite.addTestSuite(TestMakefileView.class);
		suite.addTestSuite(TestImageModel.class);
		suite.addTestSuite(TestBldInfView4.class);
		suite.addTestSuite(TestMMPView3.class);
		suite.addTestSuite(TestConditionalBlocks.class);
		suite.addTestSuite(TestMMPView5.class);
		suite.addTestSuite(TestViewDOMSynchronizer.class);
		suite.addTestSuite(TestViewDataCache.class);
		//$JUnit-END$
		return suite;
	}

}
