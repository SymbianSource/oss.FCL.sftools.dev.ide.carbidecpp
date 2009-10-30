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
package com.nokia.carbide.cpp.internal.project.ui.importWizards;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.cpp.internal.project.ui.importWizards.messages"; //$NON-NLS-1$

	public static String BldInfImportWizard_CreatingProjectJobName;

	public static String BldInfImportWizard_title;

	public static String BldInfSelectionPage_title;

	public static String BldInfSelectionPage_browseDialogTitle;

	public static String BldInfSelectionPage_noSpacesInPathError;

	public static String BldInfSelectionPage_invalidInfError;

	public static String BldInfSelectionPage_notInfError;
	
	public static String BldInfSelectionPage_badLocationError;

	public static String BldInfSelectionPage_description;

	public static String BldInfSelectionPage_infFileLabel;

	public static String BldInfSelectionPage_infFileToolTip;

	public static String BldInfSelectionPage_browseButtonLabel;

	public static String BldInfSelectionPage_browseButtonTooltip;

	public static String MMPSelectionPage_title;

	public static String MMPSelectionPage_deselectAllAButtonLabel;

	public static String MMPSelectionPage_deselectAllAButtonTooltip;

	public static String MMPSelectionPage_showFullPathsCheckboxLabel;

	public static String MMPSelectionPage_showFullPathsCheckboxTooltip;

	public static String MMPSelectionPage_sortByMMPNameCheckboxLabel;

	public static String MMPSelectionPage_sortByMMPNameCheckboxTooltip;

	public static String MMPSelectionPage_description;

	public static String MMPSelectionPage_selectAllAButtonLabel;

	public static String MMPSelectionPage_selectAllAButtonTooltip;
	
	public static String MMPSelectionPage_noMMPsFoundWarning;

	public static String MMPSelectionPage_noMMPsSelectedError;

	public static String ProjectPropertiesPage_noProjectSpecifiedError;

	public static String ProjectPropertiesPage_projectExistsError;
	
	public static String ProjectPropertiesPage_projectNameTooLong;

	public static String ProjectPropertiesPage_projectFileExistsError;
	
	public static String ProjectPropertiesPage_title;

	public static String ProjectPropertiesPage_description;

	public static String ProjectPropertiesPage_projectNameLabel;

	public static String ProjectPropertiesPage_projectNameTooltip;

	public static String ProjectPropertiesPage_rootDirectoryLabel;

	public static String ProjectPropertiesPage_rootDirectoryTooltip;

	public static String ProjectPropertiesPage_browseButtonLabel;

	public static String ProjectPropertiesPage_browseButtonTooltip;

	public static String ProjectPropertiesPage_helpText;

	public static String ProjectPropertiesPage_browseDialogTitle;
	
	public static String ProjectPropertiesPage_noDirectorySpecifiedError;

	public static String ProjectPropertiesPage_directoryDoesNotExitError;
	
	public static String ProjectPropertiesPage_directoryDoesNotContainRequiredFiles;
	
	public static String ProjectPropertiesPage_directoryDoesNotContainSourceFiles;

	public static String ProjectPropertiesPage_directoryTooLong;
	
	public static String ProjectPropertiesPage_directoryIsRoot;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
