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

import com.nokia.carbide.cpp.internal.codescanner.config.CSCategory;
import com.nokia.carbide.cpp.internal.codescanner.config.CSRule;
import com.nokia.carbide.cpp.internal.codescanner.config.CSScript;
import com.nokia.carbide.cpp.internal.codescanner.config.CSSeverity;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSRulesSorter;

/**
 * Test cases for Class CSRulesSorter.
 *
 */
public class CSRulesSorterTest extends TestCase {

	private static CSRulesSorter rulesSorter;

	protected void setUp() throws Exception {
		super.setUp();
		if (rulesSorter == null) {
			rulesSorter = new CSRulesSorter();			
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSetSortingType() {
		rulesSorter.setSortingType(CSRulesSorter.SORT_BY_CATEGORY);
		assertEquals(CSRulesSorter.SORT_BY_CATEGORY, rulesSorter.getSortingType());
		rulesSorter.setSortingType(CSRulesSorter.SORT_BY_RULE);
		assertEquals(CSRulesSorter.SORT_BY_RULE, rulesSorter.getSortingType());
		rulesSorter.setSortingType(CSRulesSorter.SORT_BY_SEVERITY);
		assertEquals(CSRulesSorter.SORT_BY_SEVERITY, rulesSorter.getSortingType());
		rulesSorter.setSortingType(-1);
		assertEquals(CSRulesSorter.SORT_BY_RULE, rulesSorter.getSortingType());
		rulesSorter.setSortingType(100);
		assertEquals(CSRulesSorter.SORT_BY_RULE, rulesSorter.getSortingType());
	}

	public void testCompareViewerObjectObject() {
		CSRule rule1 = new CSRule(CSScript.script_accessArrayElementWithoutCheck, 
								  CSCategory.category_codereview, 
								  CSSeverity.severity_low,
								  true);
		CSRule rule2 = new CSRule(CSScript.script_accessArrayElementWithoutCheck,
								  CSCategory.category_codingstandards,
								  CSSeverity.severity_medium,
								  true);
		CSRule rule3 = new CSRule(CSScript.script_activestart,
								  CSCategory.category_codereview,
								  CSSeverity.severity_medium,
								  true);
		rulesSorter.setSortingType(CSRulesSorter.SORT_BY_CATEGORY);
		assertTrue(rulesSorter.compare(null, rule1, rule2) == -1);
		assertTrue(rulesSorter.compare(null, rule1, rule3) == 0);
		assertTrue(rulesSorter.compare(null, rule2, rule3) == 1);
		rulesSorter.setSortingType(CSRulesSorter.SORT_BY_RULE);
		assertTrue(rulesSorter.compare(null, rule1, rule3) == -1);
		assertTrue(rulesSorter.compare(null, rule1, rule2) == 0);
		assertTrue(rulesSorter.compare(null, rule3, rule2) == 1);
		rulesSorter.setSortingType(CSRulesSorter.SORT_BY_SEVERITY);
		assertTrue(rulesSorter.compare(null, rule2, rule1) == -1);
		assertTrue(rulesSorter.compare(null, rule2, rule3) == 0);
		assertTrue(rulesSorter.compare(null, rule1, rule3) == 1);
	}

}
