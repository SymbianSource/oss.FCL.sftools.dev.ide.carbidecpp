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
package com.nokia.cdt.internal.debug.launch;

import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin.ILaunchCreationWizardFactory;
import com.nokia.cdt.internal.debug.launch.wizard.AbstractLaunchWizard;
import com.nokia.cdt.internal.debug.launch.wizard.ILaunchCreationWizard;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchCreationWizard;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchCreationWizardInstance;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchOptions;

public class BoardLaunchShortcut extends AbstractSymbianLaunchShortcut {
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.AbstractSymbianLaunchShortcut#isSupportedConfiguration(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	protected boolean isSupportedConfiguration(ILaunchConfiguration config)
			throws CoreException {
		return SettingsData.isStopModeConfiguration(config);
	}
	
	@Override
	protected void launchProject(IProject project, Executable executable, IPath defaultMMP, String mode) {
		LaunchPlugin.getDefault().launchProject(project, executable, defaultMMP, mode, 
																new ILaunchCreationWizardFactory() {
			public ILaunchCreationWizard createLaunchCreationWizard(LaunchOptions launchOptions) throws Exception {
				LaunchCreationWizard creationWizard = 
					LaunchCreationWizardInstance.getInstance().create(
						launchOptions.project, 
						launchOptions.configurationName, 
						launchOptions.mmps,
						launchOptions.exes,
						launchOptions.defaultExecutable,
						launchOptions.isEmulation,
						launchOptions.emulatorOnly,
						launchOptions.mode);
				creationWizard.setCategoryId(AbstractLaunchWizard.BOARD_CATEGORY_ID);
				return creationWizard;
			}
		});
	}


}

