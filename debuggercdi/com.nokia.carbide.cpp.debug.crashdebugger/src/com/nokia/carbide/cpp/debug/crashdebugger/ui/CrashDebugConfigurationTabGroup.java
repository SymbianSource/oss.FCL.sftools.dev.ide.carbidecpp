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

import com.freescale.cdt.debug.cw.core.settings.ConnectionTypeInfo;
import com.freescale.cdt.debug.cw.core.settings.DebuggerCommonData;
import com.nokia.carbide.cpp.debug.crashdebugger.CrashDebugger;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.ui.RomLogFileTab;

import cwdbg.PreferenceConstants;

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
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// Call this to make sure options on those CDT common tabs such as "Common"
		// and "Source" have correct default.
		super.setDefaults(configuration);

		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID, CrashDebugger.DEBUGGER_ID);
		
		configuration.setAttribute(DebuggerCommonData.Host_App_Path, ""); //$NON-NLS-1$
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""); //$NON-NLS-1$
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, ""); //$NON-NLS-1$

		// set rom log file defaults.  do this for all launch types since it shouldn't hurt
		// and could be easy to miss some launch types that use this tab
		configuration.setAttribute(PreferenceConstants.J_PN_ParseRomLogFile, false);
		configuration.setAttribute(PreferenceConstants.J_PN_RomLogFilePath, ""); //$NON-NLS-1$
		configuration.setAttribute(PreferenceConstants.J_PN_SymbianKitEpoc32Dir, ""); //$NON-NLS-1$
		configuration.setAttribute(PreferenceConstants.J_PN_LogUnresolved, false);

		configuration.setAttribute( PreferenceConstants.J_PN_SupportOSView, true );
		configuration.setAttribute( PreferenceConstants.J_PN_IsSystemModeDebug, true );	
		
		SettingsData.setSerialConnTab(configuration, null);

		// Crash debugger: specify crash debugger protocol plugin.
		//
		// For crash debugger, we don't need a project. But don't set the name to "" as it would 
		// cause crasher in CDT if we tries to create CDebugTarget without a project.
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, "Any"); //$NON-NLS-1$
		
		ConnectionTypeInfo connTI = new ConnectionTypeInfo(
				"Carbide CrashDebugger", // Internal ID //$NON-NLS-1$
				"SymbianCrashDebuggerProtocol", // Display name. see SymbianCrashDebuggerPrefix.h on DE side. //$NON-NLS-1$
				SettingsData.spn_SerialComm); // Pref panel name

		DebuggerCommonData.setLaunchConfigConnSettings(
				configuration,
				connTI, 
				"SymbianCrashDebuggerProtocol", //$NON-NLS-1$
				""); // see SymbianCrashDebuggerPrefix.h on DE side //$NON-NLS-1$

		configuration.setAttribute(SettingsData.ATTR_originalName, configuration.getName());
	}
}
