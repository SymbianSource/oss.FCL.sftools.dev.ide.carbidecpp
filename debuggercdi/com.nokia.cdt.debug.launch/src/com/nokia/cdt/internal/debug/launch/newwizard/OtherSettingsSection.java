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

import com.nokia.cdt.internal.debug.launch.newwizard.LaunchOptionsData.EBuildBeforeLaunchOption;

/**
 * Present the "Build before debug" section with a short description.
 */
public class OtherSettingsSection extends AbstractLaunchWizardSection {

	/**
	 * 
	 */
	public OtherSettingsSection(LaunchOptionsData data) {
		super(data, "Other settings");
		
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
		
		String ifWorkspace = "";
		if (data.getBuildBeforeLaunch() == EBuildBeforeLaunchOption.USE_WORKSPACE_SETTING)
			ifWorkspace = " (workspace setting)";
		
		if (data.isCurrentBuildBeforeLaunch())
			msg = "Carbide will build the project before launch";
		else
			msg = "Carbide will not build the project before launch";
		
		descriptionLabel.setText(msg + ifWorkspace + ".");
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#createChangeSettingsDialog(org.eclipse.swt.widgets.Shell, com.nokia.cdt.internal.debug.launch.wizard2.LaunchOptionsData)
	 */
	@Override
	protected AbstractLaunchSettingsDialog createChangeSettingsDialog(
			Shell shell, LaunchOptionsData dialogData) {
		return new OtherSettingsDialog(shell, dialogData);
	}
	
	protected void refresh() {
		validate();
		updateUI();
	}
}
