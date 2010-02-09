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

import java.text.MessageFormat;

import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.nokia.cdt.internal.debug.launch.newwizard.LaunchOptionsData.EExeSelection;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

/**
 * 
 */
public class DebugRunProcessSection extends AbstractLaunchWizardSection {

	public DebugRunProcessSection(LaunchOptionsData data) {
		super(data, MessageFormat.format("{0} process", data.getModeLabel()));
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.IWizardSection#createComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		createSection(parent, 1);
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#dispose()
	 */
	@Override
	protected void dispose() {
		
	}
	
	@Override
	public void initializeSettings() {
		data.setExeSelection(EExeSelection.USE_PROJECT_EXECUTABLE);
		if (data.getExes().size() > 0)
			data.setExeSelectionPath(data.getExes().get(0));
		else if (data.getDefaultExecutable() != null)
			data.setExeSelectionPath(data.getDefaultExecutable());
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#createChangeSettingsDialog(org.eclipse.swt.widgets.Shell, com.nokia.cdt.internal.debug.launch.wizard2.LaunchOptionsData)
	 */
	@Override
	protected AbstractLaunchSettingsDialog createChangeSettingsDialog(
			Shell shell, LaunchOptionsData dialogData) {
		return new DebugRunProcessDialog(shell, dialogData);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#refresh()
	 */
	@Override
	protected void refresh() {
		updateUI();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#validate()
	 */
	@Override
	protected void validate() {

		status = Status.OK_STATUS;
		
		switch (data.getExeSelection()) {
		case USE_PROJECT_EXECUTABLE:
			if (data.getExeSelectionPath() == null) 
				status = error("This project does not build any executables.",
						data.getModeLabel().toLowerCase());
			break;
		case USE_REMOTE_EXECUTABLE:
			if (data.getExeSelectionPath() == null)
				status = error("No remote executable is selected.",
						data.getModeLabel().toLowerCase());
			break;
		case ATTACH_TO_PROCESS:
			break;
		}
		
		// TODO: package
	}
	
	@Override
	protected void updateUI() {
		
		validate();

		if (status.isOK()) {
			String mainFormat = "Carbide will {0} and {1}.";
			String copyOrInstallMsg = "";
			String runOrLaunchMsg = "";
	
			switch (data.getExeSelection()) {
			case USE_PROJECT_EXECUTABLE:
				runOrLaunchMsg = "launch '" + data.getExeSelectionPath().lastSegment() + "'";
				break;
			case USE_REMOTE_EXECUTABLE:
				runOrLaunchMsg = "launch '" + PathUtils.convertPathToWindows(data.getExeSelectionPath()) + "'";
				break;
			case ATTACH_TO_PROCESS:
				runOrLaunchMsg = "attach to a process selected at launch time";
				break;
			}
			
			copyOrInstallMsg = "copy files to the device";
			
			String runOrDebugProcessMessage = MessageFormat.format(mainFormat, copyOrInstallMsg, runOrLaunchMsg);
			descriptionLabel.setText(runOrDebugProcessMessage);
		} else {
			descriptionLabel.setText(status.getMessage() + "\n\n" +
					MessageFormat.format("Click the 'Change...' button to select another {0} method.",
							data.getModeLabel().toLowerCase()));
		}
	}

}
