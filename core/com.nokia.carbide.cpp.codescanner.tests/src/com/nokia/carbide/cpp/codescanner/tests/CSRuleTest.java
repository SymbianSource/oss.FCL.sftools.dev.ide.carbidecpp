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

/**
 * Test cases for Class CSRule.
 *
 */
public class CSRuleTest extends TestCase {
	
	private CSRule defaultRule;

	@Override
	protected void setUp() throws Exception {
		defaultRule = new CSRule(null, null, null, false);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCategory() throws Exception {
		if (defaultRule.getCategory() != null)
			fail();
		for (CSCategory category : CSCategory.values()) {
			CSRule rule = new CSRule(null, category, null, false);
			assertEquals(rule.getCategory(), category);
		}
	}

	public void testSetCategory() throws Exception {
		for (CSCategory category : CSCategory.values()) {
			defaultRule.setCategory(category);
			assertEquals(defaultRule.getCategory(), category);
		}
		defaultRule.setCategory(null);
		assertNull(defaultRule.getCategory());
	}

	public void testGetEnabled() throws Exception {
		CSRule firstRule = new CSRule(null, null, null, true);
		CSRule seconfRule = new CSRule(null, null, null, false);
		assertTrue(firstRule.getEnabled());
		assertFalse(seconfRule.getEnabled());
	}

	public void testSetEnabled() throws Exception {
		defaultRule.setEnabled(true);
		assertTrue(defaultRule.getEnabled());
		defaultRule.setEnabled(false);
		assertFalse(defaultRule.getEnabled());
	}

	public void testGetDetials() throws Exception {
		assertEquals(defaultRule.getDetails().length(), 0);
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				CSRule rule = new CSRule(script, null, null, false);
				assertTrue(rule.getDetails().length() > 0);
			}
		}
	}

	public void testGetScript() throws Exception {
		assertNull(defaultRule.getScript());
		for (CSScript script : CSScript.values()) {
			CSRule rule = new CSRule(script, null, null, false);
			assertEquals(rule.getScript(), script);
		}
	}

	public void testSetScript() throws Exception {
		for (CSScript script : CSScript.values()) {
			defaultRule.setScript(script);
			assertEquals(defaultRule.getScript(), script);
		}
		defaultRule.setScript(null);
		assertNull(defaultRule.getScript());
	}

	public void testGetSeverity() throws Exception {
		assertNull(defaultRule.getSeverity());
		for (CSSeverity severity : CSSeverity.values()) {
			CSRule rule = new CSRule(null, null, severity, false);
			assertEquals(rule.getSeverity(), severity);
		}
	}

	public void testSetSeverity() throws Exception {
		for (CSSeverity severity : CSSeverity.values()) {
			defaultRule.setSeverity(severity);
			assertEquals(defaultRule.getSeverity(), severity);
		}
		defaultRule.setSeverity(null);
		assertNull(defaultRule.getSeverity());
	}

}
