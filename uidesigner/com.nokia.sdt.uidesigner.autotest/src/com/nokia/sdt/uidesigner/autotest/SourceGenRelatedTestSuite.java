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


import junit.framework.Test;
import junit.framework.TestSuite;

public class SourceGenRelatedTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.sdt.uidesigner.autotest");

        suite.addTest(com.nokia.sdt.component.symbian.test.sourcegen.AllTests.suite());
        suite.addTest(com.nokia.sdt.component.symbian.test.srcmapping.AllTests.suite());
		return suite;
	}

}
