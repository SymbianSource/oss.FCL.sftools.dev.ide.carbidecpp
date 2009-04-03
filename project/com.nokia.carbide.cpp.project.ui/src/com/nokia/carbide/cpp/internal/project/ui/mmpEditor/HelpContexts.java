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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;

/**
 * Help context IDs for the mmp editor
 */
public abstract class HelpContexts {
	
	// common prefix for all contexts
	//     "com.nokia.carbide.cpp.project.ui"
	static final String PREFIX = ProjectUIPlugin.PLUGIN_ID;

	// MMP editor pages
	public static final String OVERVIEW_PAGE = PREFIX + ".MMPOverviewPage"; //$NON-NLS-1$
	public static final String OPTIONS_PAGE = PREFIX + ".MMPOptionsPage";  //$NON-NLS-1$
	public static final String SOURCES_PAGE = PREFIX + ".MMPSourcesPage"; //$NON-NLS-1$
	public static final String LIBRARIES_PAGE = PREFIX + ".MMPLibrariesPage"; //$NON-NLS-1$

	// MMP editor dialogs
	public static final String RESOURCE_BLOCK_DIALOG = PREFIX + ".MMPResourceBlockDialog"; //$NON-NLS-1$
	public static final String ADD_LIBRARY_DIALOG = PREFIX + ".MMPAddLibraryDialog"; //$NON-NLS-1$
	public static final String CAPABILITIES_DIALOG = PREFIX + ".MMPCapabilitiesDialog"; //$NON-NLS-1$
	public static final String EDIT_LIBRARY_DIALOG = PREFIX + ".MMPEditLibraryDialog"; //$NON-NLS-1$
	public static final String INCLUDE_DIRECTORY_DIALOG = PREFIX + ".MMPIncludeDirectoryDialog"; //$NON-NLS-1$
	public static final String MMPFILEPATH_DIALOG = PREFIX + ".MMPFilePathDialog"; //$NON-NLS-1$
	public static final String TOOL_OPTIONS_DIALOG = PREFIX + ".MMPToolOptionsDialog"; //$NON-NLS-1$
	public static final String LANGUAGES_DIALOG = PREFIX + ".MMPLanguagesDialog";//$NON-NLS-1$
	
}
