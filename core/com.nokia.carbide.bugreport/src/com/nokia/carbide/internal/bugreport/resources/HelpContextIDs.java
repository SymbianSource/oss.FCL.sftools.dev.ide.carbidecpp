/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugreport.resources;

import com.nokia.carbide.internal.bugreport.plugin.BugReporterPlugin;

/**
 * IDs for context sensitive help.
 */
public class HelpContextIDs {

	/**
	 * ID for product selection wizard page
	 */
    public static final String PRODUCT_SELECTION_PAGE = 
    	BugReporterPlugin.PLUGIN_ID + ".ProductSelectionPageContext"; //$NON-NLS-1$

    public static final String BUG_DESCRIPTION_PAGE = 
    	BugReporterPlugin.PLUGIN_ID + ".BugDescriptionPageContext"; //$NON-NLS-1$
    
    public static final String SUMMARY_PAGE = 
    	BugReporterPlugin.PLUGIN_ID + ".SummaryPageContext"; //$NON-NLS-1$
    
}

