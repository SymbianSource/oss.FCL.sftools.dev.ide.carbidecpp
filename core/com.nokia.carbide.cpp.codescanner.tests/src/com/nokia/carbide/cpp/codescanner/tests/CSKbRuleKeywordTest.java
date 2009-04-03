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

package com.nokia.carbide.cpp.codescanner.tests;

import junit.framework.TestCase;

import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbRuleKeyword;

/**
 * Test cases for class CSKBaseRuleKeyword.
 *
 */
public class CSKbRuleKeywordTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetContent() throws Exception {
		CSKbRuleKeyword keyword1 = new CSKbRuleKeyword(null, null);
		assertNull(keyword1.getContent());
		String content = "hello";
		CSKbRuleKeyword keyword2 = new CSKbRuleKeyword(content, null);
		assertEquals(keyword2.getContent(), content);
	}

	public void testGetType() throws Exception {
		CSKbRuleKeyword keyword1 = new CSKbRuleKeyword(null, null);
		assertNull(keyword1.getType());
		String type = "generic";
		CSKbRuleKeyword keyword2 = new CSKbRuleKeyword(null, type);
		assertEquals(keyword2.getType(), type);
	}

	public void testSetContent() throws Exception {
		CSKbRuleKeyword keyword = new CSKbRuleKeyword(null, null);
		String content = "hello";
		keyword.setContent(content);
		assertEquals(keyword.getContent(), content);
		keyword.setContent(null);
		assertNull(keyword.getContent());
	}

	public void testSetType() throws Exception {
		CSKbRuleKeyword keyword = new CSKbRuleKeyword(null, null);
		String type = "generic";
		keyword.setType(type);
		assertEquals(keyword.getType(), type);
		keyword.setType(null);
		assertNull(keyword.getType());
	}

}
