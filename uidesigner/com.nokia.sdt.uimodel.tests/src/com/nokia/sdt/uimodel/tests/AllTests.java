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
package com.nokia.sdt.uimodel.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.nokia.sdt.uimodel.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(ExtensionPropertiesTest.class);
		suite.addTestSuite(DisplayModelSmokeTest.class);
		suite.addTestSuite(LoaderRegistryTest.class);
		suite.addTestSuite(ComponentReferenceTest.class);
		suite.addTestSuite(NotificationTest.class);
		suite.addTestSuite(LoadedModelNotificationTest.class);
		suite.addTestSuite(TestVariableSubstitutionEngine.class);
		suite.addTestSuite(NodePathTest.class);
		suite.addTestSuite(ExtendWithEnumPropertyTest.class);
		suite.addTestSuite(EventsTest.class);
		suite.addTestSuite(ArrayComponentTest.class);
		suite.addTestSuite(SaveTest.class);
		suite.addTestSuite(WorkspaceMessageHandlerTest.class);
		//$JUnit-END$
		return suite;
	}

}
