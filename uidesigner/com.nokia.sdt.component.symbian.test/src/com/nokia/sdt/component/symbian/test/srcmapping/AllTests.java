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
package com.nokia.sdt.component.symbian.test.srcmapping;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.sdt.component.symbian.test SourceMapping");
		//$JUnit-BEGIN$
		suite.addTestSuite(SrcMappingTestChoices.class);
		suite.addTestSuite(SrcMappingTestArrays.class);
		suite.addTestSuite(SrcMappingTestUpdatingDialog.class);
		suite.addTestSuite(SrcMappingTestUpdatingLLinks.class);
		suite.addTestSuite(SrcMappingTestS60Components.class);
		suite.addTestSuite(SrcMappingTestReferences.class);
		suite.addTestSuite(SrcMappingTestStandaloneResources.class);
		suite.addTestSuite(SrcMappingTestUpdatingResources.class);
		suite.addTestSuite(SrcMappingTestSimple.class);
		suite.addTestSuite(SrcMappingTestDeleting.class);
		suite.addTestSuite(SrcMappingTestCBA.class);
		suite.addTestSuite(SrcMappingTestResources.class);
		suite.addTestSuite(SrcMappingTestUpdatingLocRls.class);
		suite.addTestSuite(SrcMappingTestMultipleResources.class);
		suite.addTestSuite(SrcMappingTestUpdating.class);
		suite.addTestSuite(SrcMappingTestBitmasks.class);
		suite.addTest(RssRewritingTestSuite.suite());
		suite.addTestSuite(SrcMappingTestUniqueEnums.class);
		suite.addTestSuite(SrcMappingTestBasic.class);
		suite.addTestSuite(SrcMappingTestRemoveRsrcs.class);
		suite.addTestSuite(SrcMappingTestEnums.class);
		suite.addTestSuite(SrcMappingTestMultipleFiles.class);
		suite.addTestSuite(SrcMappingTestRsrcIds.class);
		//$JUnit-END$
		return suite;
	}

}
