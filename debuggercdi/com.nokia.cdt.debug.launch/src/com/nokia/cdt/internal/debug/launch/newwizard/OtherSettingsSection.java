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
*
*/

package com.nokia.cdt.internal.debug.launch.newwizard;

import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.nokia.cdt.internal.debug.launch.newwizard.LaunchWizardData.EBuildBeforeLaunchOption;

/**
 * Present the "Build before debug" section with a short description.
 */
public class OtherSettingsSection extends AbstractLaunchWizardSection {

	/**
	 * 
	 */
	public OtherSettingsSection(LaunchWizardData data, AbstractUnifiedLaunchOptionsPage launchOptionsPage) {
		super(data, Messages.getString("OtherSettingsSection.Title"), launchOptionsPage); //$NON-NLS-1$
		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.IWizardSection#createComposite(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		createSection(parent, 2);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#dispose()
	 */
	@Override
	protected void dispose() {
	}
	
	public void initializeSettings() {
		data.setBuildBeforeLaunchOption(EBuildBeforeLaunchOption.USE_WORKSPACE_SETTING);
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#validate()
	 */
	@Override
	protected void validate() {
		status = Status.OK_STATUS;
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#updateUI()
	 */
	@Override
	protected void updateUI() {
		String msg;
		
		String ifWorkspace = ""; //$NON-NLS-1$
		if (data.getBuildBeforeLaunch() == EBuildBeforeLaunchOption.USE_WORKSPACE_SETTING)
			ifWorkspace = Messages.getString("OtherSettingsSection.WorkspaceLabel"); //$NON-NLS-1$
		
		if (data.isCurrentBuildBeforeLaunch())
			msg = Messages.getString("OtherSettingsSection.BuildBeforeLabel"); //$NON-NLS-1$
		else
			msg = Messages.getString("OtherSettingsSection.NoBuildBeforeLabel"); //$NON-NLS-1$
		
		descriptionLabel.setText(msg + ifWorkspace + "."); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#createChangeSettingsDialog(org.eclipse.swt.widgets.Shell, com.nokia.cdt.internal.debug.launch.wizard2.LaunchOptionsData)
	 */
	@Override
	protected AbstractLaunchSettingsDialog createChangeSettingsDialog(
			Shell shell, LaunchWizardData dialogData) {
		return new OtherSettingsDialog(shell, dialogData);
	}
	
	protected void refresh() {
		validate();
		updateUI();
	}
}
