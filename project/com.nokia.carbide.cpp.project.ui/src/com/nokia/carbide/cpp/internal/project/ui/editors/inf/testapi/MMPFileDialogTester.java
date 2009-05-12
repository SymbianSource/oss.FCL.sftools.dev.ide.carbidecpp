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

package com.nokia.carbide.cpp.internal.project.ui.editors.inf.testapi;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.project.ui.editors.inf.MMPFileDialog;

/**
 * An interface for testing the MMP File dialog of the Bld.inf Editor
 */
public class MMPFileDialogTester {

	/**
	 * Retrieves the "File Path" text widget of the MMP File dialog.
	 * @param dialog - MMP File dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getFilePathText(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFileDialog) {
			MMPFileDialog mDialog = (MMPFileDialog) dialog;
			return mDialog.getFilePathText();
		}
		return null;
	}

	/**
	 * Retrieves the "Browse" button widget of the MMP File dialog.
	 * @param dialog - MMP File dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getBrowseButton(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFileDialog) {
			MMPFileDialog mDialog = (MMPFileDialog) dialog;
			return mDialog.getBrowseButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Tidy" button widget of the MMP File dialog.
	 * @param dialog - MMP File dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getTidyButton(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFileDialog) {
			MMPFileDialog mDialog = (MMPFileDialog) dialog;
			return mDialog.getTidyButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Build as ARM" button widget of the MMP File dialog.
	 * @param dialog - MMP File dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getBuildAsARMButton(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFileDialog) {
			MMPFileDialog mDialog = (MMPFileDialog) dialog;
			return mDialog.getBuildAsARMButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Manual" button widget of the MMP File dialog.
	 * @param dialog - MMP File dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getManualButton(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFileDialog) {
			MMPFileDialog mDialog = (MMPFileDialog) dialog;
			return mDialog.getManualButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Support" button widget of the MMP File dialog.
	 * @param dialog - MMP File dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getSupportButton(Dialog dialog) {
		if (dialog != null && dialog instanceof MMPFileDialog) {
			MMPFileDialog mDialog = (MMPFileDialog) dialog;
			return mDialog.getSupportButton();
		}
		return null;
	}

}
