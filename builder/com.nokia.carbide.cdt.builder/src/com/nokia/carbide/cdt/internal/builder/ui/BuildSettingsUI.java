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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * A composite for build settings that can be shared between workspace and project settings pages
 * Has three tabs: Common, SBSv1 and SBSv2 (which can be optionally disabled)
 */
public class BuildSettingsUI {
	
	private static final String UID = ".uid"; //$NON-NLS-1$

	private Shell shell;
	private TabFolder tabFolder;
	private boolean wantsSBSv2;
	private boolean projectSetting;
	
	// Common Tab
	private Button buildTestComponentsCheck;
	private Button useIncrementalBuilderCheck;
	private Button useConcurrentBuildingCheck;
	private Label concurrentBuildJobsLabel;
	private Spinner concurrentBuildJobsControl;
	private Group emulatorBuildOptionsGroup;
	private Button useBuiltInEnvVarsCheck;
	
	// SBS v1 Tab
	private Label cleanCmdv1Label;
	private Combo defaultCleanLevelv1Combo;
	private Button manageDependenciesCheck;
	private Button mmpChangedPromptCheck;
	private Composite defaultMMPActionComposite;
	private Label defaultMMPChangedActionLabel;
	private Combo defaultMMPChangedActionCombo;
	private Button dontCheckForExternalDependencies; // global setting only
	
	// SBS v2 Tab
	private Label cleanCmdv2Label;
	private Combo defaultCleanLevelv2Combo;
	private Button keepGoingCheck;
	private Button debugCheck;
	private Button overrideDefaultMakeEngineCheck;
	private Label makeEngineLabel;
	private Text makeEngineText;
	private Label extraArgsLabel;
	private Text  extraArgsText;
	private Label buildAliasLabel;     // project setting only
	private Text  buildAliasText; // project setting only
	
	public BuildSettingsUI(Shell shell, boolean wantsSBSv2, boolean projectSetting) {
		this.shell = shell;
		this.wantsSBSv2 = wantsSBSv2;
		this.projectSetting = projectSetting;
	}
	
	public Control createControl(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		tabFolder.setData(UID, "ConnectionSettingsPage"); //$NON-NLS-1$
		
		createCommonTabComposite();
		
		// for project settings we show either sbsv1 or sbsv2.  for workspace prefs
		// we potentially show both
		if ((projectSetting && !wantsSBSv2) || !projectSetting) {
			createSBSv1TabComposite();
		}
		
		if (wantsSBSv2) {
			createSBSv2TabComposite();
		}
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(tabFolder, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_PREFERENCE_PAGE);
		return tabFolder;
	}

	protected void createCommonTabComposite() {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(Messages.getString("BuildSettingsUI.CommonTabLabel")); //$NON-NLS-1$
		tabItem.setData(UID, "commonTab"); //$NON-NLS-1$

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout());
		content.setLayoutData(new GridData());
		tabItem.setControl(content);

		if (!projectSetting) {
			emulatorBuildOptionsGroup = new Group(content, SWT.NONE);
			emulatorBuildOptionsGroup.setText(Messages.getString("BuilderPreferencePage.EmulatorBuildGroup")); //$NON-NLS-1$
			emulatorBuildOptionsGroup.setLayout(new GridLayout());
			emulatorBuildOptionsGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			useBuiltInEnvVarsCheck = new Button(emulatorBuildOptionsGroup, SWT.CHECK);
			useBuiltInEnvVarsCheck.setText(Messages.getString("BuilderPreferencePage.EmulatorEnvOption")); //$NON-NLS-1$
			useBuiltInEnvVarsCheck.setToolTipText(Messages.getString("BuilderPreferencePage.EmulatorEnvOptionToolTip")); //$NON-NLS-1$
			useBuiltInEnvVarsCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}

		buildTestComponentsCheck = new Button(content, SWT.CHECK);
		buildTestComponentsCheck.setText(Messages.getString("SharedPrefs.BuildTestCompsText")); //$NON-NLS-1$
		buildTestComponentsCheck.setToolTipText(Messages.getString("SharedPrefs.BuildTestCompsToolTip")); //$NON-NLS-1$
		buildTestComponentsCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		useConcurrentBuildingCheck = new Button(content, SWT.CHECK);
		useConcurrentBuildingCheck.setText(Messages.getString("SharedPrefs.UseConcurrentBuildingText")); //$NON-NLS-1$
		useConcurrentBuildingCheck.setToolTipText(Messages.getString("SharedPrefs.UseConcurrentBuildingToolTip")); //$NON-NLS-1$
		useConcurrentBuildingCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		useConcurrentBuildingCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setConcurrentBuildsEnabledState(true);
			}
		});

		concurrentBuildJobsLabel = new Label(content, SWT.NONE);
		concurrentBuildJobsLabel.setEnabled(useConcurrentBuildingCheck.getSelection());
		concurrentBuildJobsLabel.setText(Messages.getString("SharedPrefs.ConcurrentBuildJobsLabelText")); //$NON-NLS-1$
		concurrentBuildJobsLabel.setToolTipText(Messages.getString("SharedPrefs.ConcurrentBuildJobsToolTip")); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		concurrentBuildJobsLabel.setLayoutData(gd);

		concurrentBuildJobsControl = new Spinner(content, SWT.BORDER | SWT.READ_ONLY);
		concurrentBuildJobsControl.setValues(0, 2, 50, 0, 1, 4);
		concurrentBuildJobsControl.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		concurrentBuildJobsControl.setEnabled(useConcurrentBuildingCheck.getSelection());
		concurrentBuildJobsControl.setToolTipText(Messages.getString("SharedPrefs.ConcurrentBuildJobsToolTip")); //$NON-NLS-1$
		concurrentBuildJobsControl.setLayoutData(new GridData());
		
		useIncrementalBuilderCheck = new Button(content, SWT.CHECK);
		useIncrementalBuilderCheck.setText(Messages.getString("SharedPrefs.UseIncrementalBuilderText")); //$NON-NLS-1$
		useIncrementalBuilderCheck.setToolTipText(Messages.getString("SharedPrefs.UseIncrementalBuilderToolTip")); //$NON-NLS-1$
		useIncrementalBuilderCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}
	
	private void createSBSv1TabComposite() {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(Messages.getString("BuildSettingsUI.SBSv1TabLabel")); //$NON-NLS-1$
		tabItem.setToolTipText(Messages.getString("BuildSettingsUI.SBSv1TabToolTip")); //$NON-NLS-1$
		tabItem.setData(UID, "sBSv1Tab"); //$NON-NLS-1$

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		content.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		tabItem.setControl(content);

		cleanCmdv1Label = new Label(content, SWT.NONE);
		cleanCmdv1Label.setText(Messages.getString("SharedPrefs.CleanCommandLabelText")); //$NON-NLS-1$
		cleanCmdv1Label.setToolTipText(Messages.getString("SharedPrefs.CleanCommandLabelToolTip")); //$NON-NLS-1$
		cleanCmdv1Label.setLayoutData(new GridData());

		defaultCleanLevelv1Combo = new Combo(content, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		defaultCleanLevelv1Combo.add(Messages.getString("SharedPrefs.CleanLevel0")); //$NON-NLS-1$
		defaultCleanLevelv1Combo.add(Messages.getString("SharedPrefs.CleanLevel1")); //$NON-NLS-1$
		defaultCleanLevelv1Combo.add(Messages.getString("SharedPrefs.CleanLevel2")); //$NON-NLS-1$
		defaultCleanLevelv1Combo.setToolTipText(Messages.getString("SharedPrefs.CleanCommandLabelToolTip")); //$NON-NLS-1$
		defaultCleanLevelv1Combo.setLayoutData(new GridData());

		manageDependenciesCheck = new Button(content, SWT.CHECK);
		manageDependenciesCheck.setText(Messages.getString("SharedPrefs.ManageDepsText")); //$NON-NLS-1$
		manageDependenciesCheck.setToolTipText(Messages.getString("SharedPrefs.ManageDepsToolTip")); //$NON-NLS-1$
		manageDependenciesCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		mmpChangedPromptCheck = new Button(content, SWT.CHECK);
		mmpChangedPromptCheck.setText(Messages.getString("SharedPrefs.mmpChangedPromptButtonText")); //$NON-NLS-1$
		mmpChangedPromptCheck.setToolTipText(Messages.getString("SharedPrefs.mmpChangedPromptButtonToolTip")); //$NON-NLS-1$
		mmpChangedPromptCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		defaultMMPActionComposite = new Composite(content, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		defaultMMPActionComposite.setLayout(gridLayout);
		defaultMMPActionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		defaultMMPChangedActionLabel = new Label(defaultMMPActionComposite, SWT.NONE);
		defaultMMPChangedActionLabel.setText(Messages.getString("SharedPrefs.defaultMMPChangedActionLabelText")); //$NON-NLS-1$
		defaultMMPChangedActionLabel.setToolTipText(Messages.getString("SharedPrefs.defaultMMPChangedActionComboToolTip")); //$NON-NLS-1$
		defaultMMPChangedActionLabel.setLayoutData(new GridData());

		defaultMMPChangedActionCombo = new Combo(defaultMMPActionComposite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		defaultMMPChangedActionCombo.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		defaultMMPChangedActionCombo.add(Messages.getString("SharedPrefs.ActionNone")); //$NON-NLS-1$
		defaultMMPChangedActionCombo.add(Messages.getString("SharedPrefs.ActionLinkOnly")); //$NON-NLS-1$
		defaultMMPChangedActionCombo.add(Messages.getString("SharedPrefs.ActionCompileAndLink")); //$NON-NLS-1$
		defaultMMPChangedActionCombo.setToolTipText(Messages.getString("SharedPrefs.defaultMMPChangedActionComboToolTip")); //$NON-NLS-1$
		defaultMMPChangedActionCombo.setLayoutData(new GridData());
		
		if (!projectSetting){
			// Only a global setting
			dontCheckForExternalDependencies = new Button(content, SWT.CHECK);
			dontCheckForExternalDependencies.setText(Messages.getString("BuildSettingsUI.SharedPrefs.DontTrackDeps"));  //$NON-NLS-1$
			dontCheckForExternalDependencies.setToolTipText(Messages.getString("BuildSettingsUI.SharedPrefs.DontTrackDepsToolTip")); //$NON-NLS-1$
			dontCheckForExternalDependencies.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}
		
	}

	private void createSBSv2TabComposite() {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(Messages.getString("BuildSettingsUI.SBSv2TabLabel")); //$NON-NLS-1$
		tabItem.setToolTipText(Messages.getString("BuildSettingsUI.SBSv2TabToolTip")); //$NON-NLS-1$
		tabItem.setData(UID, "sBSv2Tab"); //$NON-NLS-1$

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		content.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		tabItem.setControl(content);

		cleanCmdv2Label = new Label(content, SWT.NONE);
		cleanCmdv2Label.setText(Messages.getString("SharedPrefs.CleanCommandLabelText")); //$NON-NLS-1$
		cleanCmdv2Label.setToolTipText(Messages.getString("SharedPrefs.CleanCommandLabelToolTip")); //$NON-NLS-1$
		cleanCmdv2Label.setLayoutData(new GridData());

		defaultCleanLevelv2Combo = new Combo(content, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		defaultCleanLevelv2Combo.add(Messages.getString("SharedPrefs_SBSv2.CleanLevel0")); //$NON-NLS-1$
		defaultCleanLevelv2Combo.add(Messages.getString("SharedPrefs_SBSv2.CleanLevel1")); //$NON-NLS-1$
		defaultCleanLevelv2Combo.setToolTipText(Messages.getString("SharedPrefs.CleanCommandLabelToolTip")); //$NON-NLS-1$
		defaultCleanLevelv2Combo.setLayoutData(new GridData());

		keepGoingCheck = new Button(content, SWT.CHECK);
		keepGoingCheck.setText(Messages.getString("BuildSettingsUI.KeepGoingLabel")); //$NON-NLS-1$
		keepGoingCheck.setToolTipText(Messages.getString("BuildSettingsUI.KeepGoingToolTip")); //$NON-NLS-1$
		keepGoingCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		debugCheck = new Button(content, SWT.CHECK);
		debugCheck.setText(Messages.getString("BuildSettingsUI.DebugLabel")); //$NON-NLS-1$
		debugCheck.setToolTipText(Messages.getString("BuildSettingsUI.DebugToolTip")); //$NON-NLS-1$
		debugCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		extraArgsLabel = new Label(content, SWT.NONE);
		extraArgsLabel.setText(Messages.getString("BuildSettingsUI.ExtraArgsText")); //$NON-NLS-1$
		extraArgsLabel.setToolTipText(Messages.getString("BuildSettingsUI.ExtraArgsLabelToolTip"));  //$NON-NLS-1$
		GridData argsGridData = new GridData();
		extraArgsLabel.setLayoutData(argsGridData);
		
		extraArgsText = new Text(content, SWT.BORDER);
		extraArgsText.setToolTipText(Messages.getString("BuildSettingsUI.ExtraArgsToolTipText")); //$NON-NLS-1$
		extraArgsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		if (projectSetting){
			buildAliasLabel = new Label(content, SWT.NONE);
			buildAliasLabel.setText(Messages.getString("BuildSettingsUI.AlliasAppendLabel")); //$NON-NLS-1$
			buildAliasLabel.setToolTipText(Messages.getString("BuildSettingsUI.AlliasAppendToolTipText"));  //$NON-NLS-1$
			GridData buildAliasGridData = new GridData();
			buildAliasLabel.setLayoutData(buildAliasGridData);
			
			buildAliasText = new Text(content, SWT.BORDER);
			buildAliasText.setToolTipText(Messages.getString("BuildSettingsUI.AlliasAppendToolTipText")); //$NON-NLS-1$
			buildAliasText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}
		overrideDefaultMakeEngineCheck = new Button(content, SWT.CHECK);
		overrideDefaultMakeEngineCheck.setText(Messages.getString("BuildSettingsUI.OverrideMakeEngineLabel")); //$NON-NLS-1$
		overrideDefaultMakeEngineCheck.setToolTipText(Messages.getString("BuildSettingsUI.OverrideMakeEngineToolTip")); //$NON-NLS-1$
		overrideDefaultMakeEngineCheck.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		overrideDefaultMakeEngineCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setOverriddenMakeEngineEnabledState(true);
			}
		});
		
		makeEngineLabel = new Label(content, SWT.NONE);
		makeEngineLabel.setText(Messages.getString("BuildSettingsUI.MakeEngineLabel")); //$NON-NLS-1$
		makeEngineLabel.setToolTipText(Messages.getString("BuildSettingsUI.MakeEngineToolTip")); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		makeEngineLabel.setLayoutData(gd);
		
		makeEngineText = new Text(content, SWT.BORDER);
		makeEngineText.setToolTipText(Messages.getString("BuildSettingsUI.MakeEngineToolTip")); //$NON-NLS-1$
		makeEngineText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
	}

	private void setConcurrentBuildsEnabledState(boolean enabled) {
		boolean usingConcurrentBuilding = useConcurrentBuildingCheck.getSelection();
		concurrentBuildJobsLabel.setEnabled(enabled && usingConcurrentBuilding);
		concurrentBuildJobsControl.setEnabled(enabled && usingConcurrentBuilding);
		if (enabled)
			concurrentBuildJobsControl.setFocus();
	}
	
	public void setBuildTestComponentsEnabledState(boolean enabled) {
		buildTestComponentsCheck.setEnabled(enabled);
	}
	
	public void setMMPChangedActionEnabledState(boolean enabled) {
		mmpChangedPromptCheck.setEnabled(enabled);
		defaultMMPActionComposite.setEnabled(enabled);
		defaultMMPChangedActionLabel.setEnabled(enabled);
		defaultMMPChangedActionCombo.setEnabled(enabled);
	}

	public void setEnabled(boolean enabled) {
		
		setBuildTestComponentsEnabledState(enabled);
		useIncrementalBuilderCheck.setEnabled(enabled);
		useConcurrentBuildingCheck.setEnabled(enabled);
		setConcurrentBuildsEnabledState(enabled);
		
		if (!projectSetting) {
			emulatorBuildOptionsGroup.setEnabled(enabled);
			useBuiltInEnvVarsCheck.setEnabled(enabled);
		}

		if ((projectSetting && !wantsSBSv2) || !projectSetting) {
			cleanCmdv1Label.setEnabled(enabled);
			defaultCleanLevelv1Combo.setEnabled(enabled);
			manageDependenciesCheck.setEnabled(enabled);
			setMMPChangedActionEnabledState(enabled);
		}
		
		if (wantsSBSv2) {
			cleanCmdv2Label.setEnabled(enabled);
			defaultCleanLevelv2Combo.setEnabled(enabled);
			keepGoingCheck.setEnabled(enabled);
			debugCheck.setEnabled(enabled);
			overrideDefaultMakeEngineCheck.setEnabled(enabled);
			setOverriddenMakeEngineEnabledState(enabled);
		}
	}

	private void setOverriddenMakeEngineEnabledState(boolean enabled) {
		boolean overridingDefaultMakeEngine = overrideDefaultMakeEngineCheck.getSelection();
		makeEngineLabel.setEnabled(enabled && overridingDefaultMakeEngine);
		makeEngineText.setEnabled(enabled && overridingDefaultMakeEngine);
		if (enabled)
			makeEngineText.setFocus();
	}
	
	public int getDefaultCleanLevelv1() {
		return defaultCleanLevelv1Combo.getSelectionIndex();
	}

	public void setDefaultCleanLevelv1(int defaultCleanLevel) {
		defaultCleanLevelv1Combo.select(defaultCleanLevel);
	}

	public boolean getBuildTestComponents() {
		return buildTestComponentsCheck.getSelection();
	}

	public void setBuildTestComponents(boolean buildTestComponents) {
		buildTestComponentsCheck.setSelection(buildTestComponents);
	}

	public boolean getManageDependencies() {
		return manageDependenciesCheck.getSelection();
	}

	public void setManageDependencies(boolean manageDependencies) {
		manageDependenciesCheck.setSelection(manageDependencies);
	}

	public boolean getUseConcurrentBuilding() {
		return useConcurrentBuildingCheck.getSelection();
	}

	public void setUseConcurrentBuilding(boolean useConcurrentBuilding) {
		useConcurrentBuildingCheck.setSelection(useConcurrentBuilding);
		setConcurrentBuildsEnabledState(useConcurrentBuilding);
	}

	public int getNumConcurrentBuildJobs() {
		return concurrentBuildJobsControl.getSelection();
	}

	public void setNumConcurrentBuildJobs(int numConcurrentBuildJobs) {
		concurrentBuildJobsControl.setSelection(numConcurrentBuildJobs);
	}

	public boolean getUseIncrementalBuilder() {
		return useIncrementalBuilderCheck.getSelection();
	}

	public void setUseIncrementalBuilder(boolean useIncrementalBuilder) {
		useIncrementalBuilderCheck.setSelection(useIncrementalBuilder);
	}

	public boolean getPromptForMMPChangedAction() {
		return mmpChangedPromptCheck.getSelection();
	}

	public void setPromptForMMPChangedAction(boolean promptForMMPChangedAction) {
		mmpChangedPromptCheck.setSelection(promptForMMPChangedAction);
	}

	public int getDefaultMMPChangedAction() {
		return defaultMMPChangedActionCombo.getSelectionIndex();
	}

	public void setDefaultMMPChangedAction(int defaultMMPChangedAction) {
		defaultMMPChangedActionCombo.select(defaultMMPChangedAction);
	}

	public boolean getUseBuiltInEnvVars() {
		return useBuiltInEnvVarsCheck.getSelection();
	}

	public void setUseBuiltInEnvVars(boolean useBuiltInEnvVars) {
		useBuiltInEnvVarsCheck.setSelection(useBuiltInEnvVars);
	}

	public int getDefaultCleanLevelv2() {
		return defaultCleanLevelv2Combo.getSelectionIndex();
	}

	public void setDefaultCleanLevelv2(int defaultCleanLevel) {
		defaultCleanLevelv2Combo.select(defaultCleanLevel);
	}

	public boolean getKeepGoing() {
		return keepGoingCheck.getSelection();
	}

	public void setKeepGoing(boolean keepGoing) {
		keepGoingCheck.setSelection(keepGoing);
	}

	public boolean getDebugMode() {
		return debugCheck.getSelection();
	}

	public void setDebugCheck(boolean debug) {
		debugCheck.setSelection(debug);
	}

	public boolean getOverrideDefaultMakeEngine() {
		return overrideDefaultMakeEngineCheck.getSelection();
	}

	public void setOverrideDefaultMakeEngine(boolean overrideDefaultMakeEngine) {
		overrideDefaultMakeEngineCheck.setSelection(overrideDefaultMakeEngine);
		setOverriddenMakeEngineEnabledState(overrideDefaultMakeEngine);
	}

	public String getMakeEngine() {
		return makeEngineText.getText();
	}

	public void setMakeEngineText(String makeEngine) {
		makeEngineText.setText(makeEngine);
	}

	public String getExtraSBSv2Args() {
		return extraArgsText.getText();
	}

	public void setExtraSBSv2Args(String args) {
		extraArgsText.setText(args);
	}
	
	public String getBuildAliasAppendText() {
		return buildAliasText.getText();
	}

	public void setBuildAliasAppendText(String args) {
		buildAliasText.setText(args);
	}
	
	public boolean getDontPromtTrackDeps(){
		if (!projectSetting){
			return dontCheckForExternalDependencies.getSelection();
		} else {
			return true;
		}
	}
	
	public void setDontPromtTrackDeps(boolean dontAsk){
		dontCheckForExternalDependencies.setSelection(dontAsk);
	}
	
}
