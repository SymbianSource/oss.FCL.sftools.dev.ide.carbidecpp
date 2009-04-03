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
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigManager;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbManager;

/**
 * Test cases for class CSPlugin.
 *
 */
public class CSPluginTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCSPlugin() {
		CSPlugin plugin = new CSPlugin();
		assertNotNull(plugin);
	}

	public void testGetDefault() {
		CSPlugin plugin = CSPlugin.getDefault();
		assertNotNull(plugin);
	}

	public void testGetCSPrefsStore() {
		IPreferenceStore prefStore = CSPlugin.getCSPrefsStore();
		assertNotNull(prefStore);
	}

	public void testGetConfigManager() {
		CSConfigManager configManager = CSPlugin.getConfigManager();
		assertNotNull(configManager);
	}

	public void testGetKBaseRuleManager() {
		CSKbManager kbaseRuleManager = CSPlugin.getKbManager();
		assertNotNull(kbaseRuleManager);
	}
}
