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
package com.nokia.sdt.emf.dm.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.nokia.sdt.emf.dm.tests");

		// The EMF-generated tests don't currently have
		// useful tests and will just generated warnings.
		// These are DmTests.suite(), dmAllTests.suite(),
		// DataModelAllTests.suite(), IPropertyValueTest,
		// IPropertyContainerTest, IDesignerDataTest

		//$JUnit-BEGIN$
		suite.addTestSuite(NewEmptyDataModelTest.class);
		suite.addTestSuite(NodeCopierTest.class);
		suite.addTestSuite(PropertyContainerCopierTest.class);
		suite.addTestSuite(ClipboardTests.class);
		suite.addTestSuite(FormatReadTests.class);
		suite.addTestSuite(NewRootDataModelTest.class);
		suite.addTestSuite(INodeTest.class);
		suite.addTestSuite(ILocalizedStringBundleTest.class);
		suite.addTestSuite(LocalizedStringTest.class);
		suite.addTestSuite(ILocalizedStringTableTest.class);
		suite.addTestSuite(RemoveChildInstancesCommandTest.class);
		suite.addTestSuite(FormatWriteTests.class);
		suite.addTestSuite(PropertyDataModelTest.class);
		//$JUnit-END$
		
		suite.addTestSuite(ComponentManifestTest.class);
		suite.addTestSuite(ReferencePropertyPromotionTest.class);

		return suite;
	}

}
