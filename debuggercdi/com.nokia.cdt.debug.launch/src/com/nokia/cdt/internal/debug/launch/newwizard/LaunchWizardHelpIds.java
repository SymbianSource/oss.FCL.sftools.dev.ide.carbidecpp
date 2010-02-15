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
package com.nokia.cdt.internal.debug.launch.newwizard;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

// these are the help context id's for our launch wizard pages and dialogs

public class LaunchWizardHelpIds {
	/*
	 * Help context ID. Should be: PluginID + "." + <words without '.'>
	 */
	private static final String HelpID_Prefix = LaunchPlugin.getUniqueIdentifier() + ".launch2_"; //$NON-NLS-1$

	
	public static final String WIZARD_DIALOG_CHANGE_DEBUG_PROCESS = HelpID_Prefix + "wizard_dialog_change_debug_process"; //$NON-NLS-1$
	public static final String WIZARD_DIALOG_CHANGE_RUN_PROCESS = HelpID_Prefix + "wizard_dialog_change_run_process"; //$NON-NLS-1$
	public static final String WIZARD_DIALOG_CHANGE_CONNECTION = HelpID_Prefix + "wizard_dialog_change_connection"; //$NON-NLS-1$
	

	public static final String WIZARD_DIALOG_OTHER_SETTINGS = HelpID_Prefix + "wizard_dialog_other_settings"; //$NON-NLS-1$

}
