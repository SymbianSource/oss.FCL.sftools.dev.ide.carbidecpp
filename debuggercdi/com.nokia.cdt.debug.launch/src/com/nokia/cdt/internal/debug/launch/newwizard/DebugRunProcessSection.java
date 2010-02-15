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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.cdt.internal.debug.launch.newwizard.LaunchWizardData.EExeSelection;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

/**
 * 
 */
public class DebugRunProcessSection extends AbstractLaunchWizardSection {

	public DebugRunProcessSection(LaunchWizardData data, UnifiedLaunchOptionsPage launchOptionsPage) {
		super(data, MessageFormat.format("{0} process", data.getModeLabel()), launchOptionsPage);
	}
	
	@Override
	public void createControl(Composite parent) {
		createSection(parent, 1);
	}

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
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(data.getProject());
		if (cpi != null) {
			data.setInstallPackage(!data.isSysTRKConnection());
			ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
			for (ISISBuilderInfo info : config.getSISBuilderInfoList()) {
				IPath sisPath = info.getSigningType() == ISISBuilderInfo.DONT_SIGN ? info.getUnsignedSISFullPath() : info.getSignedSISFullPath();
				data.setSisPath(sisPath.toOSString());
			}
		}
	}

	@Override
	protected AbstractLaunchSettingsDialog createChangeSettingsDialog(
			Shell shell, LaunchWizardData dialogData) {
		return new DebugRunProcessDialog(shell, dialogData);
	}
	
	@Override
	protected void refresh() {
		updateUI();
	}
	
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

		if (data.isInstallPackage() && (data.getSisPath() == null || data.getSisPath().length() == 0))
			status = error("Carbide must install a package to debug this project.");
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
			
			copyOrInstallMsg = getCopyOrInstallMsg();
			
			String runOrDebugProcessMessage = MessageFormat.format(mainFormat, copyOrInstallMsg, runOrLaunchMsg);
			descriptionLabel.setText(runOrDebugProcessMessage);
		} else {
			descriptionLabel.setText(status.getMessage() + "\n\n" +
					MessageFormat.format("Click the 'Change...' button to select another {0} method.",
							data.getModeLabel().toLowerCase()));
		}
	}

	private String getCopyOrInstallMsg() {
		if (data.isSysTRKConnection() || !data.isInstallPackage())
			return "copy files to the device";
		else
			return MessageFormat.format("install \"{0}\"", data.getSisPath());
	}

}
