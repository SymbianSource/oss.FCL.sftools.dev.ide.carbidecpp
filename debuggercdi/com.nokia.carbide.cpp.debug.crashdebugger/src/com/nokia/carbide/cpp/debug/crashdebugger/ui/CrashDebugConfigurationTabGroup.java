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
package com.nokia.carbide.cpp.debug.crashdebugger.ui;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTabGroup;

import com.nokia.carbide.cpp.debug.crashdebugger.CrashDebugger;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.ui.RomLogFileTab;

public class CrashDebugConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {

		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
			new CrashDebugConnectionTab(),
			new RomLogFileTab()
			// no Common tab, though some options there are useful, but some surely cause problem to us.
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

		config.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID, CrashDebugger.DEBUGGER_ID);
		SettingsData.setDefaults(
					config, 
					SettingsData.LaunchConfig_CrashDebugger, 
					null /* no project needed for CD */);
	}
}
