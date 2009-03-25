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
package com.nokia.sdt.uidesigner.autotest;


import junit.framework.*;

public class WorkbenchTestSuite {
	
	public static Test suite()  {
		
		Test result = null;
		try {
			result = doSuite();
		} catch (final Exception x) {
			// We can't throw from here, so return
			// a test that will return this error
			return new Test() {

				public int countTestCases() {
					return 1;
				}

				public void run(TestResult result) {
					result.addError(this, x);
				}
				
				public String toString() {
					return "WorkbenchTestSuite suite() failure";
				}
			};
		}
		return result;
	}
		
	public static Test doSuite() {
		
		TestSuite suite = new TestSuite(
		"Test for com.nokia.sdt.uidesigner.autotest");
		suite.addTest(com.nokia.sdt.emf.dm.tests.AllTests.suite());
		suite.addTest(com.nokia.sdt.component.symbian.test.AllTests.suite());
		suite.addTest(com.nokia.sdt.uimodel.tests.AllTests.suite());
		suite.addTest(com.nokia.sdt.symbian.tests.AllTests.suite());
		suite.addTest(com.nokia.sdt.component.symbian.test.scripting.AllTests.suite());
		suite.addTest(com.nokia.sdt.component.symbian.test.srcmapping.AllTests.suite());
		suite.addTest(com.nokia.sdt.component.symbian.test.sourcegen.AllTests.suite());
	
        return suite;
	}

}
