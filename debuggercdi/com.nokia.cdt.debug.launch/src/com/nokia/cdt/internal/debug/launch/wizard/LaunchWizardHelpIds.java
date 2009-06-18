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
package com.nokia.cdt.internal.debug.launch.wizard;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

// these are the help context id's for our launch wizard pages and dialogs

public class LaunchWizardHelpIds {
	/*
	 * Help context ID. Should be: PluginID + "." + <words without '.'>
	 */
	private static final String HelpID_Prefix = LaunchPlugin.getUniqueIdentifier() + "."; //$NON-NLS-1$
	
	public static final String WIZARD_BINARY_SELECTION_PAGE = HelpID_Prefix + "wizard_binary_selection_page"; //$NON-NLS-1$

	public static final String CATEGORY_SELECTION_PAGE = HelpID_Prefix + "category_selection_page"; //$NON-NLS-1$

	public static final String WIZARD_SELECTION_PAGE = HelpID_Prefix + "wizard_selection_page"; //$NON-NLS-1$

	public static final String WIZARD_SUMMARY_PAGE = HelpID_Prefix + "wizard_summary_page"; //$NON-NLS-1$

	public static final String WIZARD_SOPHIA_PAGE = HelpID_Prefix + "wizard_sophia_page"; //$NON-NLS-1$

	public static final String WIZARD_TRACE32_PAGE = HelpID_Prefix + "wizard_trace32_page"; //$NON-NLS-1$

	public static final String WIZARD_STOP_MODE_ROM_IMG_PAGE = HelpID_Prefix + "wizard_stop_mode_rom_img_page"; //$NON-NLS-1$

	public static final String WIZARD_TRK_CONNECTION_PAGE = HelpID_Prefix + "wizard_trk_connection_page"; //$NON-NLS-1$
	

	public static final String WIZARD_TRK_SIS_SELECTION_PAGE = HelpID_Prefix + "wizard_trk_sis_selection_page"; //$NON-NLS-1$

	public static final String WIZARD_ROM_IMAGE_PAGE = HelpID_Prefix + "wizard_rom_image_page"; //$NON-NLS-1$

	public static final String GET_SIS_DIALOG = HelpID_Prefix + "get_sis_info_dialog"; //$NON-NLS-1$

	public static final String BUILD_OPTIONS_SELECTION_PAGE = HelpID_Prefix + "build_options_selection_page"; //$NON-NLS-1$
}
