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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

import com.nokia.carbide.cpp.internal.codescanner.config.CSArgument;
import com.nokia.carbide.cpp.internal.codescanner.config.CSCategory;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigSettings;
import com.nokia.carbide.cpp.internal.codescanner.config.CSScript;
import com.nokia.carbide.cpp.internal.codescanner.config.CSSeverity;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodescannerConfigType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomrulesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SourcesType;

/**
 * Test cases for class CSConfigSettings.
 *
 */
public class CSConfigSettingsTest extends TestCase {

	private CSConfigSettings configSettings;
	
	private String INPUT_CONFIG_FILE = "data/cs_config.xml";
	private String OUTPUT_CONFIG_FILE = "data/new_cs_config.xml";
	

	@Override
	protected void setUp() throws Exception {
		this.configSettings = new CSConfigSettings();
		this.configSettings.loadDefaultConfig();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testLoadDefaultConfig() throws Exception {
		assertNotNull(this.configSettings.getConfig());
	}
	
	public void testLoadConfig() throws Exception {
		File configFile = loadPluginRelativeFile(INPUT_CONFIG_FILE);
		assertTrue(this.configSettings.loadConfig(configFile));
		assertFalse(this.configSettings.loadConfig(null));
	}

	public void testSaveConfig() throws Exception {
		File outputConfigFile = loadPluginRelativeFile(OUTPUT_CONFIG_FILE);
		assertTrue(this.configSettings.saveConfig(outputConfigFile));
		assertFalse(this.configSettings.saveConfig(null));
		if (outputConfigFile.exists()) {
			outputConfigFile.delete();
		}
	}

	public void testGetConfig() throws Exception {
		CodescannerConfigType config = this.configSettings.getConfig();
		assertNotNull(config);
	}

	public void testSetConfig() throws Exception {
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.setConfig(this.configSettings.getConfig());
		assertEquals(configSettings.getConfig(), this.configSettings.getConfig());
		configSettings.setConfig(null);
		assertNull(configSettings.getConfig());
	}

	public void testGetArguments() throws Exception {
		ArgumentsType arguments = this.configSettings.getArguments();
		assertNotNull(arguments);
	}

	public void testSetArguments() throws Exception {
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();
		ArgumentsType arguments = this.configSettings.getArguments();
		configSettings.setArguments(arguments);
		assertTrue(configSettings.getArguments().equals(arguments));
	}

	public void testGetArgument() throws Exception {
		assertNotNull(this.configSettings.getArgument(CSArgument.argument_input));
		assertNull(this.configSettings.getArgument(CSArgument.argument_lxr));
		assertNull(this.configSettings.getArgument(CSArgument.argument_lxrversion));
		assertNull(this.configSettings.getArgument(CSArgument.argument_outputformat));
		assertNull(this.configSettings.getArgument(CSArgument.argument_timestampedoutput));
		assertNull(this.configSettings.getArgument(CSArgument.argument_unknown));
	}

	public void testSetInputArguments() throws Exception {
		assertNotNull(this.configSettings.getArgument(CSArgument.argument_input));
		List<String> argumentList = new ArrayList<String>();
		argumentList.add("abc");
		argumentList.add("xyz");
		this.configSettings.setInputArguments(argumentList);
		assertTrue(this.configSettings.getArgument(CSArgument.argument_input).equals(argumentList));
	}

	public void testSetLxrArgument() throws Exception {
		this.configSettings.setLxrArgument(null);
		assertNull(this.configSettings.getArgument(CSArgument.argument_lxr));
		String argument = "abc";
		this.configSettings.setLxrArgument(argument);
		assertTrue(this.configSettings.getArgument(CSArgument.argument_lxr).equals(argument));
	}

	public void testSetLxrVersionArgument() throws Exception {
		this.configSettings.setLxrVersionArgument(null);
		assertNull(this.configSettings.getArgument(CSArgument.argument_lxrversion));
		String argument = "123";
		this.configSettings.setLxrVersionArgument(argument);
		assertTrue(this.configSettings.getArgument(CSArgument.argument_lxrversion).equals(argument));
	}

	public void testSetOutputFormatArgument() throws Exception {
		this.configSettings.setOutputFormatArgument(null);
		assertNull(this.configSettings.getArgument(CSArgument.argument_outputformat));
		String argument = "std|html";
		this.configSettings.setOutputFormatArgument(argument);
		assertTrue(this.configSettings.getArgument(CSArgument.argument_outputformat).equals(argument));
	}

	public void testSetTimeStampedOutputArgument() throws Exception {
		this.configSettings.setTimeStampedOutputArgument(null);
		assertNull(this.configSettings.getArgument(CSArgument.argument_timestampedoutput));
		String argument = "true";
		this.configSettings.setTimeStampedOutputArgument(argument);
		assertTrue(this.configSettings.getArgument(CSArgument.argument_timestampedoutput).equals(argument));
	}

	public void testGetCustomRules() throws Exception {
		CustomrulesType customRules = this.configSettings.getCustomRules();
		assertNotNull(customRules);
	}

	public void testSetCustomRules() throws Exception {
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();
		CustomrulesType customRules = this.configSettings.getCustomRules();
		configSettings.SetCustomRules(this.configSettings.getCustomRules());
		assertTrue(configSettings.getCustomRules().equals(customRules));
	}

	public void testGetCategories() throws Exception {
		CategoriesType categories = this.configSettings.getCategories();
		assertNotNull(categories);
	}

	public void testSetCategories() throws Exception {
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();
		CategoriesType oldCategories = this.configSettings.getCategories();
		configSettings.setCategories(this.configSettings.getCategories());
		assertTrue(configSettings.getCategories().equals(oldCategories));
	}

	public void testGetScripts() throws Exception {
		ScriptsType scripts = this.configSettings.getScripts();
		assertNotNull(scripts);
	}

	public void testSetScripts() throws Exception {
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();
		ScriptsType oldScripts = this.configSettings.getScripts();
		configSettings.setScripts(this.configSettings.getScripts());
		assertTrue(configSettings.getScripts().equals(oldScripts));
	}

	public void testGetSeverities() throws Exception {
		SeveritiesType severities = this.configSettings.getSeverities();
		assertNotNull(severities);
	}

	public void testSetSeverities() throws Exception {
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();
		SeveritiesType oldSeverities = this.configSettings.getSeverities();
		configSettings.setSeverities(this.configSettings.getSeverities());
		assertTrue(configSettings.getSeverities().equals(oldSeverities));
	}

	public void testGetSourceFilters() throws Exception {
		SourcesType sourceFilters = this.configSettings.getSourceFilters();
		assertNotNull(sourceFilters);
	}

	public void testSetSourceFilters() throws Exception {
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();
		SourcesType oldSourceFilters = this.configSettings.getSourceFilters();
		configSettings.setSourceFilters(this.configSettings.getSourceFilters());
		assertTrue(configSettings.getSourceFilters().equals(oldSourceFilters));
	}

	public void testGetScript() throws Exception {
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				assertTrue(this.configSettings.getScript(script) != null);
			}
		}
	}

	public void testGetScriptEnabled() throws Exception {
		for (CSScript script : CSScript.values()) {
			assertTrue(this.configSettings.getScriptEnabled(script));
		}
	}

	public void testSetScriptEnabled() throws Exception {
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				this.configSettings.setScriptEnabled(script, false);
				assertFalse(this.configSettings.getScriptEnabled(script));
			}
		}
	}

	public void testGetScriptCategory() throws Exception {
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				assertNotNull(this.configSettings.getScriptCategory(script));
			}
		}
	}

	public void testSetScriptCategory() throws Exception {
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				this.configSettings.setScriptCategory(script, "other");
				assertTrue(this.configSettings.getScriptCategory(script).equals("other"));
			}
		}
	}

	public void testGetScriptSeverity() throws Exception {
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				assertNotNull(this.configSettings.getScriptSeverity(script));
			}
		}
	}

	public void testSetScriptSeverity() throws Exception {
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				this.configSettings.setScriptSeverity(script, "low");
				assertTrue(this.configSettings.getScriptSeverity(script).equals("low"));
			}
		}
	}

	public void testGetScriptCClassIgnore() throws Exception {
		String result = "";
		result = this.configSettings.getScriptCClassIgnore();
		assertTrue(result.length() > 0);
	}

	public void testSetScriptCClassIgnore() throws Exception {
		this.configSettings.setScriptCClassIgnore("CBase|CMyClass");
		String result = "";
		result = this.configSettings.getScriptCClassIgnore();
		assertTrue(result.equals("CBase|CMyClass"));
	}

	public void testGetScriptForbiddenWords() throws Exception {
		String result = "";
		result = this.configSettings.getScriptForbiddenWords();
		assertTrue(result.length() > 0);
	}

	public void testSetScriptForbiddenWords() throws Exception {
		this.configSettings.setScriptForbiddenWords("secret");
		String result = "";
		result = this.configSettings.getScriptForbiddenWords();
		assertTrue(result.equals("secret"));
	}

	public void testGetScriptLFunctionIgnore() throws Exception {
		String result = "";
		result = this.configSettings.getScriptLFunctionIgnore();
		assertTrue(result.length() > 0);
	}

	public void testSetLFunctionCClassIgnore() throws Exception {
		this.configSettings.setScriptLFunctionIgnore("RunL|MyfuncL");
		String result = "";
		result = this.configSettings.getScriptLFunctionIgnore();
		assertTrue(result.equals("RunL|MyfuncL"));
	}

	public void testGetScriptLongLinesLength() throws Exception {
		int result = -1;
		result = this.configSettings.getScriptLongLinesLength();
		assertTrue(result != -1);
	}

	public void testSetScriptLongLinesLength() throws Exception {
		this.configSettings.setScriptLongLinesLength(80);
		int result = this.configSettings.getScriptLongLinesLength();
		assertEquals(result, 80);
	}
	
	public void testGetScriptOpenIgnore() throws Exception {
		String result = "";
		result = this.configSettings.getScriptOpenIgnore();
		assertTrue(result.length() > 0);
	}

	public void testSetScriptOpenIgnore() throws Exception {
		this.configSettings.setScriptOpenIgnore("MyClassA|MyClassB");
		String result = "";
		result = this.configSettings.getScriptOpenIgnore();
		assertTrue(result.equals("MyClassA|MyClassB"));
	}

	public void testGetScriptWorryingComments() throws Exception {
		String result = "";
		result = this.configSettings.getScriptWorryingComments();
		assertTrue(result.length() > 0);
	}

	public void testSetScriptWorryingComments() throws Exception {
		this.configSettings.setScriptWorryingComments("alert|crap");
		String result = "";
		result = this.configSettings.getScriptWorryingComments();
		assertTrue(result.equals("alert|crap"));
	}
	
	public void testGetCategoryEnabled() throws Exception {
		for (CSCategory category : CSCategory.values()) {
			if (!category.equals(CSCategory.category_unknown)) {
				assertTrue(this.configSettings.getCategoryEnabled(category));
			}
		}
	}

	public void testSetCategoryEnabled() throws Exception {
		for (CSCategory category : CSCategory.values()) {
			if (!category.equals(CSCategory.category_unknown)) {
				this.configSettings.setCategoryEnabled(category, false);
				assertFalse(this.configSettings.getCategoryEnabled(category));
			}
		}		
	}

	public void testGetSeverityEnabled() throws Exception {
		for (CSSeverity severity : CSSeverity.values()) {
			if (!severity.equals(CSSeverity.severity_unknown)) {
				assertTrue(this.configSettings.getSeverityEnabled(severity));
			}
		}
	}

	public void testSetSeverityEnabled() throws Exception {
		for (CSSeverity severity : CSSeverity.values()) {
			if (!severity.equals(CSSeverity.severity_unknown)) {
				this.configSettings.setSeverityEnabled(severity, false);
				assertFalse(this.configSettings.getSeverityEnabled(severity));
			}
		}		
	}

    private File loadPluginRelativeFile(String file) throws IOException {
    	Bundle bundle = TestsPlugin.getDefault().getBundle();
    	URL url = FileLocator.find(bundle, new Path("."), null);
        if (url == null)
            fail("could not make URL from bundle " + bundle + " and path " + file);
        url = FileLocator.resolve(url);
        assertEquals("file", url.getProtocol());
        return new File(url.getPath(), file);
    }

}
