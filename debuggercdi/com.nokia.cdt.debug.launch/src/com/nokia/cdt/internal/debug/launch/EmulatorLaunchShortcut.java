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
import org.eclipse.core.runtime.IPath;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin.ILaunchCreationWizardFactory;
import com.nokia.cdt.internal.debug.launch.wizard.ILaunchCreationWizard;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchCreationWizardInstance;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchOptions;

public class EmulatorLaunchShortcut extends AbstractSymbianLaunchShortcut {
	
	@Override
	protected void launchProject(IProject project, Executable executable, IPath defaultMMP, String mode) {
		LaunchPlugin.getDefault().launchProject(project, executable, defaultMMP, mode, 
																new ILaunchCreationWizardFactory() {
			public ILaunchCreationWizard createLaunchCreationWizard(LaunchOptions launchOptions) throws Exception {
				return LaunchCreationWizardInstance.getInstance().create(
						launchOptions.project, 
						launchOptions.configurationName, 
						launchOptions.mmps,
						launchOptions.exes,
						launchOptions.defaultExecutable,
						launchOptions.isEmulation,
						launchOptions.emulatorOnly,
						launchOptions.mode);			
			}
		});
	}

}

