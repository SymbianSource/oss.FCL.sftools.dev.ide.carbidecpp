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

public class FullTestSuite {
	
	public static Test suite()  {
		
		Test result = null;
		try {
			result = doSuite();
		} catch (final Exception x) {
			// We can't throw from here, so return
			// a test that will return this error
			return new TestSuite() {
				
				public int testCount() {
					return 1;
				}
				
				public Test testAt(int i) {
					
					return new Test() {
						public int countTestCases() {
							return 1;
						}
						
						public void run(TestResult result) {
							result.addError(this, x);
						}
					};
				}

				public String toString() {
					return "FullTestSuite suite() failure";
				}
			};
		}
		return result;
	}
		
	public static Test doSuite() {
		
		TestSuite suite = new TestSuite(
		"Test for com.nokia.sdt.uidesigner.autotest");
		// reference project tests
		suite.addTest(com.nokia.sdt.referenceprojects.test.AllTests.suite());
		// add the WorkbenchTestSuite tests
		suite.addTest(WorkbenchTestSuite.suite());
		
        return suite;
	}

}
