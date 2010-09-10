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
package com.nokia.carbide.cpp.internal.qt.ui.wizard;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.cpp.internal.qt.ui.wizard.messages"; //$NON-NLS-1$

	public static String NewQtCppProjectWizard_title;

	public static String NewQtCppProjectWizard_desc;

	public static String QtProFileImportWizard_CreatingProjectJobName;

	public static String QtProFileImportWizard_title;

	public static String QtProFileSelectionPage_title;

	public static String QtProFileSelectionPage_browseDialogTitle;

	public static String QtProFileSelectionPage_noSpacesInPathError;
	
	public static String QtProFileSelectionPage_selectAProFileInfo;

	public static String QtProFileSelectionPage_invalidProError;

	public static String QtProFileSelectionPage_notProError;
	
	public static String QtProFileSelectionPage_badLocationError;

	public static String QtProFileSelectionPage_description;

	public static String QtProFileSelectionPage_proFileLabel;

	public static String QtProFileSelectionPage_proFileToolTip;

	public static String QtProFileSelectionPage_browseButtonLabel;

	public static String QtProFileSelectionPage_browseButtonTooltip;

	public static String QtProFileSelectionPage_projectExistsError;
	
	public static String QtProFileSelectionPage_projectNameTooLong;

	public static String QtProFileSelectionPage_projectFileExistsError;
	
	public static String QtProFileSelectionPage_directoryTooLong;

	public static String QtModulesPage_QtModulesPageTitle;
	
	public static String QtModulesPage_QtModulesPageDescription;
	
	public static String QtModulesPage_groupText;

	public static String QtModulesPage_groupToolTip;

	public static String QtModulesPage_coreModuleText;
	
	public static String QtModulesPage_coreModuleToolTip;
	
	public static String QtModulesPage_guiModuleText;
	
	public static String QtModulesPage_guiModuleToolTip;
	
	public static String QtModulesPage_sqlModuleText;
	
	public static String QtModulesPage_sqlModuleToolTip;
	
	public static String QtModulesPage_xmlModuleText;
	
	public static String QtModulesPage_xmlModuleToolTip;
	
	public static String QtModulesPage_xmlPatternsModuleText;
	
	public static String QtModulesPage_xmlPatternsModuleToolTip;
	
	public static String QtModulesPage_networkModuleText;
	
	public static String QtModulesPage_networkModuleToolTip;
	
	public static String QtModulesPage_svgModuleText;
	
	public static String QtModulesPage_svgModuleToolTip;
	
	public static String QtModulesPage_openglModuleText;
	
	public static String QtModulesPage_openglModuleToolTip;
	
	public static String QtModulesPage_webkitModuleText;
	
	public static String QtModulesPage_webkitModuleToolTip;
	
	public static String QtModulesPage_scriptModuleText;
	
	public static String QtModulesPage_scriptModuleToolTip;
	
	public static String QtModulesPage_phononModuleText;
	
	public static String QtModulesPage_phononModuleToolTip;
	
	public static String QtBuildTargetsPage_title;

	public static String QtBuildTargetsPage_filterCheckboxText;

	public static String QtBuildTargetsPage_filterCheckboxToolTip;

	public static String QtBuildTargetsPage_noQtConfigsError;
	
	public static String PerspectiveSwitchDialog_Title;
	
	public static String PerspectiveSwitchDialog_Query;
	
	public static String PerspectiveSwitchDialog_RememberDecisionText;
	
	public static String QtBuildTargetsPage_mismatchedDriveSpec;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
