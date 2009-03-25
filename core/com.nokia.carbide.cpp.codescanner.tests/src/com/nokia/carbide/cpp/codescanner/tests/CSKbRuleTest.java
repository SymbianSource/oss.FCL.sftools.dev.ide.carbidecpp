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

import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbRule;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbRuleKeyword;

/**
 * Test cases for class CSKBaseRule.
 *
 */
public class CSKbRuleTest extends TestCase {

	private CSKbRule rule;
	
	protected void setUp() throws Exception {
		rule = new CSKbRule();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetDescription() throws Exception {
		assertNull(rule.getDescription());
		String description = "test";
		rule.setDescription(description);
		assertTrue(rule.getDescription().equals(description));
	}

	public void testGetFileTypes() throws Exception {
		assertNull(rule.getFileTypes());
		ArrayList<String> filetypes = new ArrayList<String>();
		filetypes.add("h");
		filetypes.add("cpp");
		rule.setFileTypes(filetypes);
		assertTrue(rule.getFileTypes().equals(filetypes));
	}

	public void testGetKeywords() throws Exception {
		assertNull(rule.getKeywords());
		ArrayList<CSKbRuleKeyword> keywords = new ArrayList<CSKbRuleKeyword>();
		keywords.add(new CSKbRuleKeyword("hello", "generic"));
		keywords.add(new CSKbRuleKeyword("CBase", "class"));
		rule.setKeywords(keywords);
		assertTrue(rule.getKeywords().equals(keywords));
	}

	public void testGetLink() throws Exception {
		assertNull(rule.getLink());
		String link = "http://www.nokia.com";
		rule.setLink(link);
		assertTrue(rule.getLink().equals(link));
	}

	public void testGetName() throws Exception {
		assertNull(rule.getName());
		String name = "myRule";
		rule.setName(name);
		assertTrue(rule.getName().equals(name));
	}

	public void testGetPlatforms() throws Exception {
		assertNull(rule.getPlatforms());
		ArrayList<Version> platform = new ArrayList<Version>();
		platform.add(new Version("5.0"));
		rule.setPlatforms(platform);
		assertTrue(rule.getPlatforms().equals(platform));
	}

	public void testGetPluginId() throws Exception {
		assertNull(rule.getPluginId());
		String pluginID = "com.nokia.carbide.cpp.codescanner";
		rule.setPluginId(pluginID);
		assertTrue(rule.getPluginId().equals(pluginID));
	}

	public void testGetSeverity() throws Exception {
		assertNull(rule.getSeverity());
		String severity = "low";
		rule.setSeverity(severity);
		assertTrue(rule.getSeverity().equals(severity));
	}

	public void testGetTitle() throws Exception {
		assertNull(rule.getTitle());
		String title = "my custom rule";
		rule.setTitle(title);
		assertTrue(rule.getTitle().equals(title));
	}

	public void testSetDescription() throws Exception {
		String description = "test";
		rule.setDescription(description);
		assertTrue(rule.getDescription().equals(description));
		rule.setDescription(null);
		assertNull(rule.getDescription());
	}

	public void testSetFileTypes() throws Exception {
		ArrayList<String> filetypes = new ArrayList<String>();
		filetypes.add("h");
		filetypes.add("cpp");
		rule.setFileTypes(filetypes);
		assertTrue(rule.getFileTypes().equals(filetypes));
		rule.setFileTypes(null);
		assertNull(rule.getFileTypes());
	}

	public void testSetKeywords() throws Exception {
		ArrayList<CSKbRuleKeyword> keywords = new ArrayList<CSKbRuleKeyword>();
		keywords.add(new CSKbRuleKeyword("hello", "generic"));
		keywords.add(new CSKbRuleKeyword("CBase", "class"));
		rule.setKeywords(keywords);
		assertTrue(rule.getKeywords().equals(keywords));
		rule.setKeywords(null);
		assertNull(rule.getKeywords());
	}

	public void testSetLink() throws Exception {
		String link = "http://www.nokia.com";
		rule.setLink(link);
		assertTrue(rule.getLink().equals(link));
		rule.setLink(null);
		assertNull(rule.getLink());
	}

	public void testSetName() throws Exception {
		String name = "myRule";
		rule.setName(name);
		assertTrue(rule.getName().equals(name));
		rule.setName(null);
		assertNull(rule.getName());
	}

	public void testSetPlatforms() throws Exception {
		ArrayList<Version> platforms = new ArrayList<Version>();
		platforms.add(new Version("5.0"));
		rule.setPlatforms(platforms);
		assertTrue(rule.getPlatforms().equals(platforms));
		rule.setPlatforms(null);
		assertNull(rule.getPlatforms());
	}

	public void testSetPluginId() throws Exception {
		String pluginID = "com.nokia.carbide.cpp.codescanner";
		rule.setPluginId(pluginID);
		assertTrue(rule.getPluginId().equals(pluginID));
		rule.setPluginId(null);
		assertNull(rule.getPluginId());
	}

	public void testSetSeverity() throws Exception {
		String severity = "low";
		rule.setSeverity(severity);
		assertTrue(rule.getSeverity().equals(severity));
		rule.setSeverity(null);
		assertNull(rule.getSeverity());
	}

	public void testSetTitle() throws Exception {
		String title = "my custom rule";
		rule.setTitle(title);
		assertTrue(rule.getTitle().equals(title));
		rule.setTitle(null);
		assertNull(rule.getTitle());
	}

}
