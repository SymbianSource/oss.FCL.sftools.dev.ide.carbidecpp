/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cdt.builder.test;

import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * Test class to simply load all the global Carbide preferences
 *
 */
public class TestCreateGlobalPrefs extends BaseTest {
	
	private static final String[] carbidePrefIds = { "com.nokia.carbide.cpp.sdk.ui.preferences.BuildPlatformFilterPage",
		"com.nokia.carbide.cpp.internal.sdk.ui.SDKPreferencePage",
		"com.nokia.carbide.cpp.internal.sdk.ui.SDKPreferencePage",
		"com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage",
		"com.nokia.carbide.cpp.preferences.ExtensionsPreferencesPage",
		"com.nokia.carbide.cpp.internal.builder.utils.ui.PreprocessPreferencePage",
		"com.nokia.carbide.cpp.internal.project.ui.preferences.CarbidePreferencePage",
		"com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage",
		"com.nokia.carbide.search.system.internal.ui.SearchPreferencePage"
	};
	
	@Override
	protected void setUp() throws Exception {
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	/**
	 * Iterte all the global eclipse pref IDs for Carbide prefs
	 * and try to create them. Any exception will require teasing
	 * the stack frame to find the offending preference that could not be loaded.
	 * @throws Exception
	 */
	public void testCreatePrefs() throws Exception {
		
		for (String id : carbidePrefIds){
			PreferencesUtil.createPreferenceDialogOn(WorkbenchUtils.getSafeShell(), id, null, null, 0);
		}
		
		// Deprecated - to be filtered
		//com.nokia.cdt.debug.cw.symbian.ui.GlobalSettings
		//com.nokia.carbide.cpp.logging.LoggingPreferencesPage
	}
}
