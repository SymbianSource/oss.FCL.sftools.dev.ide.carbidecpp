/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.cdt.internal.debug.launch.ui;

import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.*;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

public class T32StopModeConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new StopModeMainTab(),				
				new Trace32ConnectionTab(),
				new StopModeDebuggerTab(),
				new SymbianExceptionsTab(),
				new RomImageTab(),	
				new ExecutablesTab(false),
				new SourceLookupTab(),
				new CommonTab() 
			};
			setTabs(tabs);
	}
	
	
	/**
	 * Set default for all preferences related to this launch configuration.
	 * 
	 * @see ILaunchConfigurationTabGroup#setDefaults(ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		// Call this to make sure options on those CDT common tabs such as "Common"
		// and "Source" have correct default.
		super.setDefaults(config);

		// Get the current selected project in C project view.
		IProject project = LaunchPlugin.getSelectedProject();

		SettingsData.setDefaults(config, SettingsData.LaunchConfig_Trace32, project);
	}
}
