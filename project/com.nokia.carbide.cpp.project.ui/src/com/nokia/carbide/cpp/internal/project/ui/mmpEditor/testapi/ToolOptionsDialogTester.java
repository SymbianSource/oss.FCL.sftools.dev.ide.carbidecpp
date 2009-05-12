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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.ToolOptionsDialog;

/**
 * An interface for testing the Tool Options dialog of the MMP Editor
 */
public class ToolOptionsDialogTester {

	/**
	 * Retrieves the "Tool chain" combo widget of the Tool Options dialog.
	 * @param dialog - Tool Options dialog provided by caller
	 * @return combo widget if success, null otherwise
	 */
	public static Combo getToolChainCombo(Dialog dialog) {
		if (dialog != null && dialog instanceof ToolOptionsDialog) {
			ToolOptionsDialog tDialog = (ToolOptionsDialog) dialog;
			return tDialog.getToolChainCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "Options" text widget of the Tool Options dialog.
	 * @param dialog - Tool Options dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getOptionsText(Dialog dialog) {
		if (dialog != null && dialog instanceof ToolOptionsDialog) {
			ToolOptionsDialog tDialog = (ToolOptionsDialog) dialog;
			return tDialog.getOptionsText();
		}
		return null;
	}

}
