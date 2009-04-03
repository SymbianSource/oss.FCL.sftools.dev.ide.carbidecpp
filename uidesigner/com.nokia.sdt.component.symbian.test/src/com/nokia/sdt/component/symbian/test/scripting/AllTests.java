/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.test.scripting;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.sdt.component.symbian.test.scripting");
		//$JUnit-BEGIN$
		suite.addTestSuite(ScriptingEventsTest.class);
		suite.addTestSuite(ScriptingArraysGlobalTest.class);
		suite.addTestSuite(ScriptingPropertiesGlobalsTest.class);
		suite.addTestSuite(ScriptingDerivedGlobals.class);
		suite.addTestSuite(ScriptBasicTest.class);
		suite.addTestSuite(ScriptingPropertiesTest.class);
		suite.addTestSuite(ScriptingAttributesTest.class);
		suite.addTestSuite(ScriptingArraysTest.class);
		//$JUnit-END$
		return suite;
	}

}
