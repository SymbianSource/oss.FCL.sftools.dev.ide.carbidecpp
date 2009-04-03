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
package com.nokia.carbide.cdt.builder;


/**
 * This file defines constants for the Carbide.c++ builder global preferences. The preference store can be
 * retrieved with a call to:
 * <br><br>
 * <code>IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();</code>
 * </br></br>
 *  and then using the appropriate API to read/write settings.
 *  <br></br>
 *  CAUTION: Preference values change from release to release so use these sparingly. As well,
 *  writing preference store data may have functional impact on other areas that read these settings.
 */
public class BuilderPreferenceConstants {
	
	/**
	 * Boolean setting for "Use built-in Nokia x86 environment variables for WINSCW builds".
	 */
	public final static String PREF_USE_BUILIN_X86_VARS = "useBuiltInX86Vars"; //$NON-NLS-1$
	
	/**
	 * Boolean "Build test components when building from bld.inf".
	 */
	public final static String PREF_BUILD_TEST_COMPS = "buildTestComponents"; //$NON-NLS-1$
	
	/**
	 * Boolean setting for "Manage dependency tracking".
	 */
	public final static String PREF_MANAGE_DEPENDENCIES = "manageDependencies"; //$NON-NLS-1$

	/**
	 * Boolean setting for "Run builds concurrently".
	 */
	public final static String PREF_USE_CONCURRENT_BUILDING = "useConcurrentBuilding"; //$NON-NLS-1$

	/**
	 * Integer setting for number of "Build Jobs"
	 */
	public final static String PREF_CONCURRENT_BUILD_JOBS = "concurrentBuildJobs"; //$NON-NLS-1$

	/**
	 * Boolean setting for "Use default incremental builder"
	 */
	public final static String PREF_USE_INCREMENTAL_BUILDER = "useIncrementalBuilder"; //$NON-NLS-1$
	
	/**
	 * Integer setting for "Clean Level". Zero based index of menu item selected.
	 */
	public final static String PREF_CLEAN_LEVEL = "cleanLevel"; //$NON-NLS-1$
	
	/**
	 * Boolean setting for "Show a dialog to modify build actions when modified MMPs are detected"
	 */
	public final static String PREF_MMP_CHANGED_ACTION_PROMPT = "mmpChangedActionPrompt"; //$NON-NLS-1$
	
	/**
	 * Integer setting for "Default action to take when mmp files are modified". Zero based index of menu item selected.
	 */
	public final static String PREF_DEFAULT_MMP_CHANGED_ACTION = "defaultMMPChangedAction"; //$NON-NLS-1$

	/**
	 * Integer setting for "Clean Level" for SBSv2. Zero based index of menu item selected.
	 * @since 2.0
	 */
	public final static String PREF_CLEAN_LEVEL_V2 = "cleanLevelv2"; //$NON-NLS-1$

	/**
	 * Boolean setting for "Keep going"
	 * @since 2.0
	 */
	public final static String PREF_KEEP_GOING = "keepGoing"; //$NON-NLS-1$

	/**
	 * Boolean setting for "Debug mode"
	 * @since 2.0
	 */
	public final static String PREF_DEBUG_MODE = "debugMode"; //$NON-NLS-1$

	/**
	 * Boolean setting for "Override make engine"
	 * @since 2.0
	 */
	public final static String PREF_OVERRIDE_MAKE_ENGINE = "overrideMakeEngine"; //$NON-NLS-1$

	/**
	 * String setting for "Make engine"
	 * @since 2.0
	 */
	public final static String PREF_MAKE_ENGINE = "makeEngine"; //$NON-NLS-1$
}
