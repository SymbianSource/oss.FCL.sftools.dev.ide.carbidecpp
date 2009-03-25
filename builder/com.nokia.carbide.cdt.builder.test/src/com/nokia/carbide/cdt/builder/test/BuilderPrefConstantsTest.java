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
* Tests the global builder preference options.
* 
* NOTE: THESE TESTS ARE TO INSURE THAT THE BUILDER GLOBAL PREFERENCES ARE INTACT
* AS THE PREF STORE CONSTANTS ARE PUBLIC API. IF A BUILDER PREF CONSTANT MUST BE
* REMOVED MAKE SURE IT IS DOCUMENTED IN THE API RELEASE NOTES.
*
*/
package com.nokia.carbide.cdt.builder.test;

import org.eclipse.jface.preference.IPreferenceStore;

import junit.framework.TestCase;

import com.nokia.carbide.cdt.builder.BuilderPreferenceConstants;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

public class BuilderPrefConstantsTest extends TestCase {
	
	
	protected void setUp() throws Exception {}

	protected void tearDown() throws Exception { super.tearDown(); 	}
	
	/**
	 * Test PREF_USE_BUILIN_X86_VARS
	 */
	public void testUseBuiltInX86VarsPref() throws Exception{
		// check default
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		boolean flag = store.getDefaultBoolean(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS);
		assertTrue("Default for use built-in x86 vars should be true.", flag);
		
		// check current setting read/write. Flip the switch
		flag = store.getBoolean(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS);
		store.setValue(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS, !flag);
		boolean newFlag = store.getBoolean(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS);
		assertEquals(!flag, newFlag);
		
		// set it back
		store.setValue(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS, flag);
		assertEquals(flag, store.getBoolean(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS));
	}
	
	/**
	 * Test PREF_BUILD_TEST_COMPS
	 */
	public void testBuildTestCompsPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		boolean flag = store.getDefaultBoolean(BuilderPreferenceConstants.PREF_BUILD_TEST_COMPS);
		assertTrue("Default for build test comps should be: true.", flag);
	}
	
	/**
	 * Test PREF_MANAGE_DEPENDENCIES
	 */
	public void testManageDependenciesPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		boolean flag = store.getDefaultBoolean(BuilderPreferenceConstants.PREF_MANAGE_DEPENDENCIES);
		assertTrue("Default for manage dependencies should be: true.", flag);
	}
	
	
	/**
	 * Test PREF_USE_CONCURRENT_BUILDING
	 */
	public void testUseConcurrentBuildsPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		boolean flag = store.getDefaultBoolean(BuilderPreferenceConstants.PREF_USE_CONCURRENT_BUILDING);
		assertTrue("Default for concurrency should be: true.", flag);	
	}
	
	
	
	/**
	 * Test PREF_CONCURRENT_BUILD_JOBS
	 */
	public void testNumberConcurrentBuildJobsPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		int numJobs = store.getDefaultInt(BuilderPreferenceConstants.PREF_CONCURRENT_BUILD_JOBS);
		assertEquals(4, numJobs);
		
		// TODO: Currently num build jobs is hardcoded to 4. Might use  Runtime.getRuntime().availableProcessors(); in future.
	}
	
	/**
	 * Test PREF_USE_INCREMENTAL_BUILDER (the default incremental builder)
	 */
	public void testUseIncrementalBuilderPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		boolean flag = store.getDefaultBoolean(BuilderPreferenceConstants.PREF_USE_INCREMENTAL_BUILDER);
		assertFalse("Default for use default incremental builder should be: false.", flag);
	}
	
	
	/**
	 * Test PREF_CLEAN_LEVEL
	 */
	public void testCleanLevelPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		int cleanIndex = store.getDefaultInt(BuilderPreferenceConstants.PREF_CLEAN_LEVEL);
		assertEquals(0, cleanIndex);
	}
	
	
	/**
	 * Test PREF_MMP_CHANGED_ACTION_PROMPT
	 */
	public void testMMPChangeActionPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		boolean flag = store.getDefaultBoolean(BuilderPreferenceConstants.PREF_MMP_CHANGED_ACTION_PROMPT);
		assertTrue("Default for MMP change action should be: true.", flag);
	}
	
	
	/**
	 * Test PREF_DEFAULT_MMP_CHANGED_ACTION (pop-up)
	 */
	public void testMMPChangeActionSelectionPref() throws Exception{
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		int action = store.getDefaultInt(BuilderPreferenceConstants.PREF_DEFAULT_MMP_CHANGED_ACTION);
		assertEquals(0, action);
	}
}
