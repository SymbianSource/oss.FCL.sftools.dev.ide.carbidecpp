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

package com.nokia.carbide.templatewizard.symbian.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.carbide.templatewizard.symbian.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(TemplateUtilsTest.class);
		suite.addTestSuite(TestVariableSubstitutionEngine.class);
		suite.addTestSuite(LiveTemplatesValidationTest.class);
		//$JUnit-END$
		return suite;
	}

}
