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

import java.util.ArrayList;

import org.osgi.framework.Version;

import junit.framework.TestCase;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigSettings;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomrulesType;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbManager;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbRule;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbRuleKeyword;

public class CSKbManagerTest extends TestCase {

	private static CSKbManager kBaseRuleManager;
	protected void setUp() throws Exception {
		if (kBaseRuleManager == null) {
			kBaseRuleManager = new CSKbManager();
		}
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAdd() throws Exception {
		CSKbRule rule = new CSKbRule();
		kBaseRuleManager.clearRules();
		kBaseRuleManager.addRule(rule);
		assertNotNull(kBaseRuleManager.getRules());
	}

	public void testAddAll() throws Exception {
		ArrayList<CSKbRule> rules = new ArrayList<CSKbRule>();
		rules.add(new CSKbRule());
		kBaseRuleManager.clearRules();
		kBaseRuleManager.addRules(rules);
		assertNotNull(kBaseRuleManager.getRules());
	}

	public void testClear() throws Exception {
		CSKbRule rule = new CSKbRule();
		kBaseRuleManager.addRule(rule);
		assertNotNull(kBaseRuleManager.getRules());
		kBaseRuleManager.clearRules();
		assertNull(kBaseRuleManager.getRules());
	}

	public void testGetPluginRules() throws Exception {
		String pluginID = "com.nokia.carbide.cpp.codescanner";
		CSKbRule rule = new CSKbRule();
		rule.setPluginId(pluginID);
		kBaseRuleManager.clearRules();
		kBaseRuleManager.addRule(rule);
		assertNotNull(kBaseRuleManager.getPluginRules(pluginID));
	}

	public void testGetRules() throws Exception {
		kBaseRuleManager.clearRules();
		assertNull(kBaseRuleManager.getRules());
		CSKbRule rule = new CSKbRule();
		kBaseRuleManager.addRule(rule);
		assertNotNull(kBaseRuleManager.getRules());
	}

	public void testRemove() throws Exception {
		String name = "myRule";
		CSKbRule rule = new CSKbRule();
		rule.setName(name);
		kBaseRuleManager.clearRules();
		kBaseRuleManager.addRule(rule);
		assertNotNull(kBaseRuleManager.getRules());
		kBaseRuleManager.removeRule(name);
		assertNull(kBaseRuleManager.getRules());
	}

	public void testRemoveAll() throws Exception {
		ArrayList<CSKbRule> rules = new ArrayList<CSKbRule>();
		rules.add(new CSKbRule());
		kBaseRuleManager.clearRules();
		kBaseRuleManager.addRules(rules);
		assertNotNull(kBaseRuleManager.getRules());
		kBaseRuleManager.removeRules(rules);
		assertNull(kBaseRuleManager.getRules());
	}

	public void testRemoveAllPluginRules() throws Exception {
		String pluginID = "com.nokia.carbide.cpp.codescanner";
		CSKbRule rule = new CSKbRule();
		rule.setPluginId(pluginID);
		kBaseRuleManager.clearRules();
		kBaseRuleManager.addRule(rule);
		assertNotNull(kBaseRuleManager.getPluginRules(pluginID));
		kBaseRuleManager.removeAllPluginRules(pluginID);
		assertNull(kBaseRuleManager.getRules());
	}

	public void testSetRules() throws Exception {
		ArrayList<CSKbRule> rules = new ArrayList<CSKbRule>();
		rules.add(new CSKbRule());
		kBaseRuleManager.clearRules();
		kBaseRuleManager.setRules(rules);
		assertNotNull(kBaseRuleManager.getRules());
	}

	public void testAddKBaseRulesToConfigSettings() throws Exception {
		ArrayList<String> filetypes = new ArrayList<String>();
		filetypes.add("cpp");
		ArrayList<CSKbRuleKeyword> keywords = new ArrayList<CSKbRuleKeyword>();
		keywords.add(new CSKbRuleKeyword("myKeyword", "generic"));
		ArrayList<Version> platforms = new ArrayList<Version>();
		platforms.add(new Version("5.0"));
		CSKbRule rule = new CSKbRule();
		rule.setName("myRule");
		rule.setFileTypes(filetypes);
		rule.setKeywords(keywords);
		rule.setSeverity("low");
		rule.setTitle("this is my rule");
		rule.setDescription("Detailed description of my rule");
		rule.setLink(null);
		rule.setPlatforms(platforms);
		rule.setPluginId("com.nokia.carbide.cpp.codescanner");
		kBaseRuleManager.clearRules();
		kBaseRuleManager.addRule(rule);
		CSConfigSettings configSettings = CSPlugin.getConfigManager().getDefaultConfig();
		kBaseRuleManager.addKBaseRulesToConfigSettings(null, configSettings);
		CustomrulesType customRules = configSettings.getCustomRules();
		assertTrue(customRules.getCustomrule().size() == 0);
	}

}
