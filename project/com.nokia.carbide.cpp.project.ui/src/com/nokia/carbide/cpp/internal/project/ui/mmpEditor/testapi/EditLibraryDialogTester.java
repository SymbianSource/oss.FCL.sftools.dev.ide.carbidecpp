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
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.EditLibraryDialog;

/**
 * An interface for testing the Edit Library dialog of the MMP Editor
 */
public class EditLibraryDialogTester {

	/**
	 * Retrieves the "Library name" string of the Choose Capabilities dialog.
	 * @param dialog - Edit Library dialog provided by caller
	 * @return string if success, null otherwise
	 */
	public static String getLibraryName(Dialog dialog) {
		if (dialog != null && dialog instanceof EditLibraryDialog) {
			EditLibraryDialog eDialog = (EditLibraryDialog) dialog;
			return eDialog.getLibraryName();
		}
		return null;
	}

	/**
	 * Retrieves the "Library name" text widget of the Choose Capabilities dialog.
	 * @param dialog - Edit Library dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getLibraryNameText(Dialog dialog) {
		if (dialog != null && dialog instanceof EditLibraryDialog) {
			EditLibraryDialog eDialog = (EditLibraryDialog) dialog;
			return eDialog.getLibraryNameText();
		}
		return null;
	}

}
