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

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin.ILaunchCreationWizardFactory;
import com.nokia.cdt.internal.debug.launch.newwizard.LaunchWizard;
import com.nokia.cdt.internal.debug.launch.wizard.ILaunchCreationWizard;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchOptions;

public class PhoneLaunchShortcut extends AbstractSymbianLaunchShortcut {

	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.AbstractSymbianLaunchShortcut#isSupportedConfiguration(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	protected boolean isSupportedConfiguration(ILaunchConfiguration config)
			throws CoreException {
		return SettingsData.isAppTRKConfiguration(config) || SettingsData.isSysTRKConfiguration(config);
	}
	
	@Override
	protected void launchProject(IProject project, Executable executable, IPath defaultMMP, String mode) {
		LaunchPlugin.getDefault().launchProject(project, executable, defaultMMP, mode, 
																new ILaunchCreationWizardFactory() {
					public ILaunchCreationWizard createLaunchCreationWizard(LaunchOptions launchOptions) throws Exception {
						IConnectionTypeProvider provider = RemoteConnectionsActivator.getConnectionTypeProvider();
						IService trkService = provider.findServiceByID("com.nokia.carbide.trk.support.service.TRKService"); //$NON-NLS-1$
						LaunchWizard launchWizard = new LaunchWizard(launchOptions, trkService);
						return launchWizard;
					};
				});
	}
}