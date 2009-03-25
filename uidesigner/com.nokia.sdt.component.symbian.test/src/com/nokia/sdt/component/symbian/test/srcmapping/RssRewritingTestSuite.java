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
/**
 * 
 */
package com.nokia.sdt.component.symbian.test.srcmapping;

import com.nokia.sdt.sourcegen.doms.rss.parser.tests.*;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Bucket for several tests exposing issues with RSS parsing and rewriting.
 * 
 *
 */
public class RssRewritingTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Scattered tests for RSS rewriting");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestParserUtils.class);
		suite.addTestSuite(TestWhitespaceInclusion.class);
		suite.addTestSuite(SrcMappingTestUpdating.class);
		suite.addTestSuite(SrcMappingTestUpdatingDialog.class);
		suite.addTestSuite(SrcMappingTestUpdatingLocRls.class);
		suite.addTestSuite(SrcMappingTestDeleting.class);
		suite.addTestSuite(TestRewriting.class);
		suite.addTestSuite(TestSourceFormatter.class);
		suite.addTestSuite(TestSystemHeaders.class);
		suite.addTestSuite(TestSystemResources.class);
		//$JUnit-END$
		return suite;
	}

}
