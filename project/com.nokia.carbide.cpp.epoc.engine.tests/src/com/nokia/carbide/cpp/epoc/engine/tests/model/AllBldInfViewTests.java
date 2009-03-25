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

public class AllBldInfViewTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.carbide.cpp.epoc.engine.tests.model");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestBldInfView.class);
		suite.addTestSuite(TestBldInfView2.class);
		suite.addTestSuite(TestBldInfView3.class);
		suite.addTestSuite(TestBldInfView4.class);
		suite.addTestSuite(TestBldInfViewBug4054.class);
		//$JUnit-END$
		return suite;
	}

}
