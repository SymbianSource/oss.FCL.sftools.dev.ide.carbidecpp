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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.cpp.internal.project.ui.editors.common.messages"; //$NON-NLS-1$

	public static String CarbideFormEditor_updatingDocError;

	public static String ChangeListValueOperation_modifyStatement;
	public static String CarbideFormEditor_reloadOnChangedFilePrompt;
	public static String CarbideFormEditor_reloadOnChangedFileDialogTitle;
	public static String CarbideFormEditor_yesButtonLabel;
	public static String CarbideFormEditor_noButtonLabel;
	public static String CarbideFormEditor_saveResourceTitle;
	public static String CarbideFormEditor_saveResourceMessage;
	public static String CarbideFormEditor_saveResourceLabel;
	public static String CarbideFormEditor_warning_derived_title;
	public static String CarbideFormEditor_warning_derived_message;
	public static String CarbideFormEditor_warning_derived_dontShowAgain;
	public static String CarbideFormEditorContext_errorDialogTitle;
	public static String CarbideFormEditorContext_commandErrorFormatText;
	public static String CarbideTextEditor_syncErrorDialogTitle;
	public static String CarbideTextEditor_syncErrorDialogMessage;
	public static String CarbideTextEditor_error_saveAs_title;
	public static String CarbideTextEditor_error_saveAs_message;
	public static String CarbideTextEditor_saveAs_message;
	public static String CarbideTextEditor_warning_saveAs_deleted;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
