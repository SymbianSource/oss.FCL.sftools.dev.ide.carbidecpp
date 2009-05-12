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

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.IncludeDirectoryDialog;

/**
 * An interface for testing the Include Directory dialog of the MMP Editor
 */
public class IncludeDirectoryDialogTester {

	/**
	 * Retrieves the "Include directory" combo widget of the Include Directory dialog.
	 * @param dialog - Include Directory dialog provided by caller
	 * @return combo widget if success, null otherwise
	 */
	public static Combo getIncludeDirectoryCombo(Dialog dialog) {
		if (dialog != null && dialog instanceof IncludeDirectoryDialog) {
			IncludeDirectoryDialog iDialog = (IncludeDirectoryDialog) dialog;
			return iDialog.getIncludeDirectoryCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "Browse" button widget of the Include Directory dialog.
	 * @param dialog - Include Directory dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getBrowseButton(Dialog dialog) {
		if (dialog != null && dialog instanceof IncludeDirectoryDialog) {
			IncludeDirectoryDialog iDialog = (IncludeDirectoryDialog) dialog;
			return iDialog.getBrowseButton();
		}
		return null;
	}

}
