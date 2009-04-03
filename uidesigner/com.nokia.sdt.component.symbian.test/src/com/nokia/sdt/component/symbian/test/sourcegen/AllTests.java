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

package com.nokia.sdt.component.symbian.test.sourcegen;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.sdt.component.symbian.test.sourcegen");
		//$JUnit-BEGIN$
		suite.addTestSuite(SourceGenInlineTest.class);
		suite.addTestSuite(SourceGenLocationsTest.class);
		suite.addTestSuite(SourceGenTemplateGroupsTest.class);
		suite.addTestSuite(SourceGenTemplatesTest.class);
		suite.addTestSuite(TestJSExpressionFormatter.class);
		suite.addTestSuite(SourceGenTemplatesRel2Test.class);
		//$JUnit-END$
		return suite;
	}

}
