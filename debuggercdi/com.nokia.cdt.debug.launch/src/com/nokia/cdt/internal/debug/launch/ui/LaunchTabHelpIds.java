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

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

// these are the help context id's for our launch configuration tabs

public class LaunchTabHelpIds {
	/*
	 * Help context ID. Should be: PluginID + "." + <words without '.'>
	 */
	private static final String HelpID_Prefix = LaunchPlugin.getUniqueIdentifier() + "."; //$NON-NLS-1$
	
	public static final String ALL_EXECUTABLES = HelpID_Prefix + "all_executables"; //$NON-NLS-1$

	public static final String CONTEXT_MAIN = HelpID_Prefix + "context_main"; //$NON-NLS-1$

	public static final String EMULATION_MAIN = HelpID_Prefix + "emulation_main"; //$NON-NLS-1$

	public static final String EMULATION_DEBUGGER = HelpID_Prefix + "emulation_debugger"; //$NON-NLS-1$

	public static final String EMULATION_EXCEPTIONS = HelpID_Prefix + "emulation_exceptions"; //$NON-NLS-1$

	public static final String RUN_MODE_MAIN = HelpID_Prefix + "runmode_main"; //$NON-NLS-1$

	public static final String ATTACH_MAIN = HelpID_Prefix + "attach_main"; //$NON-NLS-1$
	
	public static final String ATTACH_CHOOSE_PROCESS = HelpID_Prefix + "attach_choose_process"; //$NON-NLS-1$

	public static final String RUN_MODE_DEBUGGER = HelpID_Prefix+ "runmode_debugger"; //$NON-NLS-1$

	public static final String RUN_MODE_CONNECTION = HelpID_Prefix + "runmode_connection"; //$NON-NLS-1$

	public static final String RUN_MODE_INSTALLATION = HelpID_Prefix + "runmode_installation"; //$NON-NLS-1$

	public static final String RUN_MODE_FILE_TRANSFER = HelpID_Prefix + "runmode_filetransfer"; //$NON-NLS-1$

	public static final String RUN_MODE_FILE_TRANSFER_DIALOG = HelpID_Prefix+ "runmode_filetransferDialog"; //$NON-NLS-1$
	
	public static final String STOP_MODE_MAIN = HelpID_Prefix + "stopmode_main"; //$NON-NLS-1$
	
	public static final String STOP_MODE_DEBUGGER = HelpID_Prefix + "stopmode_debugger"; //$NON-NLS-1$
	
	public static final String STOP_MODE_EXCEPTIONS = HelpID_Prefix + "stopmode_exceptions"; //$NON-NLS-1$
	
	public static final String STOP_MODE_T32_CONNECTION = HelpID_Prefix + "stopmode_t32connection"; //$NON-NLS-1$
	
	public static final String STOP_MODE_SOPHIA_CONNECTION = HelpID_Prefix + "stopmode_sophiaconnection"; //$NON-NLS-1$
	
	public static final String STOP_MODE_ROM_IMAGE = HelpID_Prefix + "stopmode_romimage"; //$NON-NLS-1$
	
	public static final String RUN_MODE_ROM_LOG_FILE = HelpID_Prefix + "runmode_romlogfile"; //$NON-NLS-1$

	public static final String PARTIAL_UPGRADE_ALERT_DIALOG = HelpID_Prefix + "partial_upgrade_alert_dialog"; //$NON-NLS-1$
	
}
