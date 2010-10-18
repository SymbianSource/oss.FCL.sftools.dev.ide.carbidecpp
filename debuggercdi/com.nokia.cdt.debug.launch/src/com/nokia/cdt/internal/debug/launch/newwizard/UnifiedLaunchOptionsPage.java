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

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cdt.internal.debug.launch.newwizard.LaunchWizardData.EExeSelection;

/**
 * This page presents three sections:
 * <p>
 * Connection to use:  container for the Remote Connection selection UI, plus a label
 * explaining how to handle the case of no connections defined.
 * <p>
 * Debug process: section explaining how the launch will happen, with a combo
 * allowing selecting different process to launch, and a button allowing more
 * in-depth configuration.
 * <p>
 * Other settings: section with (currently only) the build-before-debug preference for this
 * launch configuration.
 * <p>
 * Each section is validated separately and editable with its own dialog.  Changes in
 * the dialog are not applied until the dialog is accepted.
 */
public class UnifiedLaunchOptionsPage extends AbstractUnifiedLaunchOptionsPage {

	public UnifiedLaunchOptionsPage(LaunchWizardData data) {
		super(Messages.getString("UnifiedLaunchOptionsPage.Title"), data); //$NON-NLS-1$
		setDescription(Messages.getString("UnifiedLaunchOptionsPage.Desc")); //$NON-NLS-1$
		
		addSection(new ConnectToDeviceSection(data, this));
		addSection(new DebugRunProcessSection(data, this));
		addSection(new OtherSettingsSection(data, this));
	}

	protected IStatus getStatus() {
		return checkBuildProducts();
	}

	private IStatus checkBuildProducts() {
		if (!data.isCurrentBuildBeforeLaunch()) {
			// check sis files
			String sisPath = data.getSisPath();
			if (data.isInstallPackage() && 
					sisPath != null && !new File(sisPath).exists()) {
				return new Status(IStatus.WARNING, LaunchPlugin.PLUGIN_ID,
						Messages.getString("UnifiedLaunchOptionsPage.SISFileMissingWarning")); //$NON-NLS-1$
			}
			// check launch file
			if (data.getExeSelection().equals(EExeSelection.USE_PROJECT_EXECUTABLE) && 
					!data.getExePath().toFile().exists()) {
				return new Status(IStatus.WARNING, LaunchPlugin.PLUGIN_ID,
						Messages.getString("UnifiedLaunchOptionsPage.ExeFileMissingWarning")); //$NON-NLS-1$
			}
		}
		
		return Status.OK_STATUS;
	}
}
