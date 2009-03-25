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
package com.nokia.carbide.cdt.internal.builder.ui;

import com.nokia.carbide.cdt.builder.BuilderPreferenceConstants;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.*;


public class BuilderPreferencePage extends PreferencePage implements IWorkbenchPreferencePage { 
	
	private BuildSettingsUI buildSettingsUI;

	public BuilderPreferencePage() {
		super();
	}
	
	protected Control createContents(Composite parent) {
		buildSettingsUI = new BuildSettingsUI(parent.getShell(), SBSv2Utils.enableSBSv2Support(), false);
		Control control = buildSettingsUI.createControl(parent);
		
		buildSettingsUI.setDefaultCleanLevelv1(getCleanLevel());
		buildSettingsUI.setBuildTestComponents(isBuildingTestComps());
		buildSettingsUI.setManageDependencies(manageDependencies());
		buildSettingsUI.setUseIncrementalBuilder(useIncrementalBuilder());
		buildSettingsUI.setUseConcurrentBuilding(useConcurrentBuilding());
		buildSettingsUI.setNumConcurrentBuildJobs(concurrentBuildJobs());
		buildSettingsUI.setPromptForMMPChangedAction(promptForMMPChangedAction());
		buildSettingsUI.setDefaultMMPChangedAction(defaultMMPChangedAction());
		buildSettingsUI.setUseBuiltInEnvVars(useBuiltInX86Vars());
		
		if (SBSv2Utils.enableSBSv2Support()) {
			buildSettingsUI.setDefaultCleanLevelv2(getCleanLevelv2());
			buildSettingsUI.setKeepGoing(keepGoing());
			buildSettingsUI.setDebugCheck(debugMode());
			buildSettingsUI.setOverrideDefaultMakeEngine(overrideDefaultMakeEngine());
			buildSettingsUI.setMakeEngineText(makeEngine());
		}
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_PREFERENCE_PAGE);
		
		return control;
	}

	public void init(IWorkbench workbench) {
	}
	
	@Override
	public boolean performOk() {
		performApply();
		return super.performOk();
	}

	@Override
	protected void performApply() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		store.setValue(BuilderPreferenceConstants.PREF_CLEAN_LEVEL, buildSettingsUI.getDefaultCleanLevelv1());
		store.setValue(BuilderPreferenceConstants.PREF_BUILD_TEST_COMPS, buildSettingsUI.getBuildTestComponents());
		store.setValue(BuilderPreferenceConstants.PREF_MANAGE_DEPENDENCIES, buildSettingsUI.getManageDependencies());
		store.setValue(BuilderPreferenceConstants.PREF_USE_INCREMENTAL_BUILDER, buildSettingsUI.getUseIncrementalBuilder());
		store.setValue(BuilderPreferenceConstants.PREF_USE_CONCURRENT_BUILDING, buildSettingsUI.getUseConcurrentBuilding());
		store.setValue(BuilderPreferenceConstants.PREF_CONCURRENT_BUILD_JOBS, buildSettingsUI.getNumConcurrentBuildJobs());
		store.setValue(BuilderPreferenceConstants.PREF_MMP_CHANGED_ACTION_PROMPT, buildSettingsUI.getPromptForMMPChangedAction());
		store.setValue(BuilderPreferenceConstants.PREF_DEFAULT_MMP_CHANGED_ACTION, buildSettingsUI.getDefaultMMPChangedAction());
		store.setValue(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS, buildSettingsUI.getUseBuiltInEnvVars());
		
		if (SBSv2Utils.enableSBSv2Support()) {
			store.setValue(BuilderPreferenceConstants.PREF_CLEAN_LEVEL_V2, buildSettingsUI.getDefaultCleanLevelv2());
			store.setValue(BuilderPreferenceConstants.PREF_KEEP_GOING, buildSettingsUI.getKeepGoing());
			store.setValue(BuilderPreferenceConstants.PREF_DEBUG_MODE, buildSettingsUI.getDebugMode());
			store.setValue(BuilderPreferenceConstants.PREF_OVERRIDE_MAKE_ENGINE, buildSettingsUI.getOverrideDefaultMakeEngine());
			store.setValue(BuilderPreferenceConstants.PREF_MAKE_ENGINE, buildSettingsUI.getMakeEngine());
		}
	}
	
	protected void performDefaults() {
		buildSettingsUI.setDefaultCleanLevelv1(0);
		buildSettingsUI.setBuildTestComponents(true);
		buildSettingsUI.setManageDependencies(true);
		buildSettingsUI.setUseIncrementalBuilder(false);
		buildSettingsUI.setUseConcurrentBuilding(true);
		buildSettingsUI.setNumConcurrentBuildJobs(4);
		buildSettingsUI.setPromptForMMPChangedAction(true);
		buildSettingsUI.setDefaultMMPChangedAction(0);
		buildSettingsUI.setUseBuiltInEnvVars(true);

		if (SBSv2Utils.enableSBSv2Support()) {
			buildSettingsUI.setDefaultCleanLevelv2(0);
			buildSettingsUI.setKeepGoing(false);
			buildSettingsUI.setDebugCheck(false);
			buildSettingsUI.setOverrideDefaultMakeEngine(false);
			buildSettingsUI.setMakeEngineText("make"); //$NON-NLS-1$
		}
	}
	
	public static boolean useBuiltInX86Vars() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS);
	}

	public static boolean isBuildingTestComps() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_BUILD_TEST_COMPS);
	}

	public static boolean manageDependencies() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_MANAGE_DEPENDENCIES);
	}

	public static boolean useConcurrentBuilding() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_USE_CONCURRENT_BUILDING);
	}

	public static int concurrentBuildJobs() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getInt(BuilderPreferenceConstants.PREF_CONCURRENT_BUILD_JOBS);
	}

	public static boolean useIncrementalBuilder() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_USE_INCREMENTAL_BUILDER);
	}
	
	public static int getCleanLevel() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getInt(BuilderPreferenceConstants.PREF_CLEAN_LEVEL);
	}

	public static boolean promptForMMPChangedAction() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_MMP_CHANGED_ACTION_PROMPT);
	}

	public static int defaultMMPChangedAction() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getInt(BuilderPreferenceConstants.PREF_DEFAULT_MMP_CHANGED_ACTION);
	}
	
	public static int getCleanLevelv2() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getInt(BuilderPreferenceConstants.PREF_CLEAN_LEVEL_V2);
	}

	public static boolean keepGoing() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_KEEP_GOING);
	}
	
	public static boolean debugMode() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_DEBUG_MODE);
	}
	
	public static boolean overrideDefaultMakeEngine() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderPreferenceConstants.PREF_OVERRIDE_MAKE_ENGINE);
	}
	
	public static String makeEngine() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		return store.getString(BuilderPreferenceConstants.PREF_MAKE_ENGINE);
	}
	
}
