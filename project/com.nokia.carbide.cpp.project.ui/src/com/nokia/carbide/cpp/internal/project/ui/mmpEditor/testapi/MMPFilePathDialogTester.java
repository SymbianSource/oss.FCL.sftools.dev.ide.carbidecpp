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

package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.testapi;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.MMPFilePathDialog;

/**
 * An interface for testing the MMP FilePath dialog of the MMP Editor
 */
public class MMPFilePathDialogTester {

	/**
	 * Retrieves the "File path" combo widget of the MMP FilePath dialog.
	 * @param dialog - MMP FilePath dialog provided by caller
	 * @return combo widget if success, null otherwise
	 */
	public static Combo getFilePathCombo(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFilePathDialog) {
			MMPFilePathDialog mDialog = (MMPFilePathDialog) dialog;
			return mDialog.getFilePathCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "browse" button widget of the MMP FilePath dialog.
	 * @param dialog - MMP FilePath dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getBrowseButton(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFilePathDialog) {
			MMPFilePathDialog mDialog = (MMPFilePathDialog) dialog;
			return mDialog.getBrowseButton();
		}
		return null;
	}

}
