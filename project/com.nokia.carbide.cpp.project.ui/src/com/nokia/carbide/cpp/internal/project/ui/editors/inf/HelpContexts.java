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
package com.nokia.carbide.cpp.internal.project.ui.editors.inf;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;

/**
 * Help context IDs for the bld.inf editor
 */
public abstract class HelpContexts {
	
	// common prefix for all contexts
	//     "com.nokia.carbide.cpp.project.ui"
	static final String PREFIX = ProjectUIPlugin.PLUGIN_ID;

	// bld.inf editor pages
	public static final String OVERVIEW_PAGE = PREFIX + ".OverviewPage"; //$NON-NLS-1$
	public static final String EXPORTS_PAGE = PREFIX + ".ExportsPage"; //$NON-NLS-1$
	
	// dialogs
	public static final String EXPORTS_DIALOG = PREFIX + ".ExportsDialog"; //$NON-NLS-1$
	public static final String MMP_DIALOG = PREFIX + ".MMPDialog"; //$NON-NLS-1$
	public static final String MAKE_DIALOG = PREFIX + ".MakeDialog"; //$NON-NLS-1$
	
}
