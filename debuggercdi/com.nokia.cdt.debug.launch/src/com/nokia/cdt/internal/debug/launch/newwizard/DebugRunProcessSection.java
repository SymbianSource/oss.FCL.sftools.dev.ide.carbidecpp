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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.cdt.internal.debug.launch.newwizard.IDebugRunProcessWizardData.EExeSelection;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

/**
 * 
 */
public class DebugRunProcessSection extends AbstractLaunchWizardSection {
	
	private IDebugRunProcessWizardData debugRunProcessWizardData;

	public DebugRunProcessSection(IWizardData data, AbstractUnifiedLaunchOptionsPage launchOptionsPage) {
		super(data, MessageFormat.format(Messages.getString("DebugRunProcessSection.Title"), data.getModeLabel()), launchOptionsPage); //$NON-NLS-1$
		debugRunProcessWizardData = (IDebugRunProcessWizardData) data;
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
		debugRunProcessWizardData.setExeSelection(EExeSelection.USE_PROJECT_EXECUTABLE);
		if (debugRunProcessWizardData.getLaunchableExes().size() > 0)
			debugRunProcessWizardData.setExeSelectionPath(debugRunProcessWizardData.getLaunchableExes().get(0));
		else if (debugRunProcessWizardData.getDefaultExecutable() != null)
			debugRunProcessWizardData.setExeSelectionPath(debugRunProcessWizardData.getDefaultExecutable());
		if (Path.EMPTY.equals(debugRunProcessWizardData.getExeSelectionPath()))
			debugRunProcessWizardData.setExeSelection(EExeSelection.ATTACH_TO_PROCESS);
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(data.getProject());
		boolean hasSisInstall = false;
		if (cpi != null) {
			ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
			for (ISISBuilderInfo info : config.getSISBuilderInfoList()) {
				IPath sisPath = info.getSigningType() == ISISBuilderInfo.DONT_SIGN ? info.getUnsignedSISFullPath() : info.getSignedSISFullPath();
				debugRunProcessWizardData.setSisPath(sisPath.toOSString());
				if (info.isEnabled()) {
					hasSisInstall = true;
					break;
				}
			}
		}
		
		if (debugRunProcessWizardData.getExeSelection().equals(EExeSelection.ATTACH_TO_PROCESS)) {
			debugRunProcessWizardData.setInstallPackage(false);
		} else {
			Boolean detectedSysTrk = debugRunProcessWizardData.isSysTRKConnection(); // TRUE, FALSE, or null
			boolean isSysTrk = detectedSysTrk == Boolean.TRUE || (detectedSysTrk == null && debugRunProcessWizardData.isInternalLayout());
			debugRunProcessWizardData.setInstallPackage(hasSisInstall || !isSysTrk);
		}
		
	}

	@Override
	protected AbstractLaunchSettingsDialog createChangeSettingsDialog(
			Shell shell, IWizardData dialogData) {
		return new DebugRunProcessDialog(shell, dialogData);
	}
	
	@Override
	protected void refresh() {
		updateUI();
	}
	
	@Override
	protected void validate() {

		status = Status.OK_STATUS;
		
		switch (debugRunProcessWizardData.getExeSelection()) {
		case USE_PROJECT_EXECUTABLE:
			if (debugRunProcessWizardData.getExeSelectionPath() == null) 
				status = error(Messages.getString("DebugRunProcessSection.NoExesError"), //$NON-NLS-1$
						data.getModeLabel().toLowerCase());
			break;
		case USE_REMOTE_EXECUTABLE:
			if (debugRunProcessWizardData.getExeSelectionPath() == null)
				status = error(Messages.getString("DebugRunProcessSection.NoRemoteExeError"), //$NON-NLS-1$
						data.getModeLabel().toLowerCase());
			break;
		case ATTACH_TO_PROCESS:
			break;
		}

		if (debugRunProcessWizardData.isInstallPackage() && (debugRunProcessWizardData.getSisPath() == null || debugRunProcessWizardData.getSisPath().length() == 0))
			status = error(Messages.getString("DebugRunProcessSection.MustInstallError"),
					data.getModeLabel().toLowerCase()); //$NON-NLS-1$
	}

	@Override
	protected void updateUI() {
		
		validate();

		if (status.isOK()) {
			String mainFormat = Messages.getString("DebugRunProcessSection.MainFormat"); //$NON-NLS-1$
			String copyOrInstallMsg = ""; //$NON-NLS-1$
			String runOrLaunchMsg = ""; //$NON-NLS-1$
	
			switch (debugRunProcessWizardData.getExeSelection()) {
			case USE_PROJECT_EXECUTABLE:
				runOrLaunchMsg = MessageFormat.format(Messages.getString("DebugRunProcessSection.LaunchFormat"), 
						debugRunProcessWizardData.getExeSelectionPath().lastSegment()); //$NON-NLS-1$
				break;
			case USE_REMOTE_EXECUTABLE:
				runOrLaunchMsg = MessageFormat.format(Messages.getString("DebugRunProcessSection.LaunchFormat"), 
						PathUtils.convertPathToWindows(debugRunProcessWizardData.getExeSelectionPath().toString())); //$NON-NLS-1$
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
		if (debugRunProcessWizardData.requiresInstallPackage())
			return MessageFormat.format(Messages.getString("DebugRunProcessSection.InstallMsg"), debugRunProcessWizardData.getSisPath()); //$NON-NLS-1$
		else
			return Messages.getString("DebugRunProcessSection.CopyMsg"); //$NON-NLS-1$
	}

}
