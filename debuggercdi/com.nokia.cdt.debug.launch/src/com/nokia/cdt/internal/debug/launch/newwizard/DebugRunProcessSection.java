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
		super(data, MessageFormat.format(Messages.getString("DebugRunProcessSection.Title"), data.getModeLabel()), launchOptionsPage); //$NON-NLS-1$
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
				status = error(Messages.getString("DebugRunProcessSection.NoExesError"), //$NON-NLS-1$
						data.getModeLabel().toLowerCase());
			break;
		case USE_REMOTE_EXECUTABLE:
			if (data.getExeSelectionPath() == null)
				status = error(Messages.getString("DebugRunProcessSection.NoRemoteExeError"), //$NON-NLS-1$
						data.getModeLabel().toLowerCase());
			break;
		case ATTACH_TO_PROCESS:
			break;
		}

		if (data.isInstallPackage() && (data.getSisPath() == null || data.getSisPath().length() == 0))
			status = error(Messages.getString("DebugRunProcessSection.MustInstallError")); //$NON-NLS-1$
	}

	@Override
	protected void updateUI() {
		
		validate();

		if (status.isOK()) {
			String mainFormat = Messages.getString("DebugRunProcessSection.MainFormat"); //$NON-NLS-1$
			String copyOrInstallMsg = ""; //$NON-NLS-1$
			String runOrLaunchMsg = ""; //$NON-NLS-1$
	
			switch (data.getExeSelection()) {
			case USE_PROJECT_EXECUTABLE:
				runOrLaunchMsg = MessageFormat.format(Messages.getString("DebugRunProcessSection.LaunchFormat"), data.getExeSelectionPath().lastSegment()); //$NON-NLS-1$
				break;
			case USE_REMOTE_EXECUTABLE:
				runOrLaunchMsg = MessageFormat.format(Messages.getString("DebugRunProcessSection.LaunchFormat"), PathUtils.convertPathToWindows(data.getExeSelectionPath())); //$NON-NLS-1$
				break;
			case ATTACH_TO_PROCESS:
				runOrLaunchMsg = Messages.getString("DebugRunProcessSection.AttachMsg"); //$NON-NLS-1$
				break;
			}
			
			copyOrInstallMsg = getCopyOrInstallMsg();
			
			String runOrDebugProcessMessage = MessageFormat.format(mainFormat, copyOrInstallMsg, runOrLaunchMsg);
			descriptionLabel.setText(runOrDebugProcessMessage);
		} else {
			descriptionLabel.setText(status.getMessage() + "\n\n" + //$NON-NLS-1$
					MessageFormat.format(Messages.getString("DebugRunProcessSection.ChangeMsg"), //$NON-NLS-1$
							data.getModeLabel().toLowerCase()));
		}
	}

	private String getCopyOrInstallMsg() {
		if (data.isSysTRKConnection() || !data.isInstallPackage())
			return Messages.getString("DebugRunProcessSection.CopyMsg"); //$NON-NLS-1$
		else
			return MessageFormat.format(Messages.getString("DebugRunProcessSection.InstallMsg"), data.getSisPath()); //$NON-NLS-1$
	}

}
