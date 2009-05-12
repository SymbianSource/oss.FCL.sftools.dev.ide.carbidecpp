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
import org.eclipse.jface.viewers.CheckboxTableViewer;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.LanguageSelectionDialog;

/**
 * An interface for testing the Language Selection dialog of the MMP Editor
 */
public class LanguageSelectionDialogTester {

	/**
	 * Retrieves the table viewer of the Language Selection dialog.
	 * @param dialog - Language Selection dialog provided by caller
	 * @return table viewer if success, null otherwise
	 */
	public static CheckboxTableViewer getTableViewer(Dialog dialog) {
		if (dialog != null && dialog instanceof LanguageSelectionDialog) {
			LanguageSelectionDialog lDialog = (LanguageSelectionDialog) dialog;
			return lDialog.getTableViewer();
		}
		return null;
	}

}
