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
package com.nokia.carbide.cpp.internal.project.ui.views;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.cpp.internal.project.ui.views.messages"; //$NON-NLS-1$

	public static String BuildGroup_BuildProject;

	public static String BuildGroup_CleanProject;

	public static String BuildGroup_RebuildProject;

	public static String CollapseAllAction_CollapseAllText;

	public static String OpenFileGroup_OpenWith;

	public static String OpenMBMMIFFileAction_OpenDescription;

	public static String OpenMBMMIFFileAction_OpenText;

	public static String OpenMBMMIFFileAction_OpenToolTip;

	public static String OpenNonWorkspaceFileAction_OpenActionText;

	public static String OpenNonWorkspaceFileAction_OpenActionToolTip;

	public static String RefreshSelectionAction_RefreshText;

	public static String RefreshSelectionAction_RefreshToolTip;

	public static String SPNViewContentProvider_DefFile;

	public static String SPNViewContentProvider_Documents;

	public static String SPNViewContentProvider_DoesNotExist;

	public static String SPNViewContentProvider_FetchingBldInfChildrenJob;

	public static String SPNViewContentProvider_FetchingMMPChildrenJob;

	public static String SPNViewContentProvider_ForText;

	public static String SPNViewContentProvider_Images;

	public static String SPNViewContentProvider_Includes;

	public static String SPNViewContentProvider_Resources;

	public static String SPNViewContentProvider_ScanningForIncludes;

	public static String SPNViewContentProvider_Sources;

	public static String SPNViewContentProvider_SourcePaths;

	public static String SPNViewContentProvider_UIDesigns;

	public static String SPNViewContentProvider_Aif;

	public static String SPNViewContentProvider_BldInfIncludes;

	public static String SymbianProjectNavigatorView_FilterText;

	public static String SymbianProjectNavigatorView_FilterToolTip;

	public static String SymbianProjectNavigatorView_SortMMPsText;

	public static String SymbianProjectNavigatorView_SortMMPsToolTip;

	public static String SymbianProjectNavigatorView_DisplaySourcePathsText;

	public static String SymbianProjectNavigatorView_DisplaySourcePathsToolTip;

	public static String MainActionGroup_NewMenu;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
