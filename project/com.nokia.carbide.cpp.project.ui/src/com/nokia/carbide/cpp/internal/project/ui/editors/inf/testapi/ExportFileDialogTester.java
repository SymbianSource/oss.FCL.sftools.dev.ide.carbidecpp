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

import com.nokia.carbide.cpp.internal.project.ui.editors.inf.ExportFileDialog;

/**
 * An interface for testing the Export File dialog of the Bld.inf Editor
 */
public class ExportFileDialogTester {

	/**
	 * Retrieves the "Source Path" text widget of the Export File dialog.
	 * @param dialog - Export File dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getSourcePathText(Dialog dialog) {
		if (dialog != null && dialog instanceof ExportFileDialog) {
			ExportFileDialog eDialog = (ExportFileDialog) dialog;
			return eDialog.getSourcePathText();
		}
		return null;
	}

	/**
	 * Retrieves the "Browse" button widget of the Export File dialog.
	 * @param dialog - Export File dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getBrowseButton(Dialog dialog) {
		if (dialog != null && dialog instanceof ExportFileDialog) {
			ExportFileDialog eDialog = (ExportFileDialog) dialog;
			return eDialog.getBrowseButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Destination Path" text widget of the Export File dialog.
	 * @param dialog - Export File dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getDestinationPath(Dialog dialog) {
		if (dialog != null && dialog instanceof ExportFileDialog) {
			ExportFileDialog eDialog = (ExportFileDialog) dialog;
			return eDialog.getDestinationPath();
		}
		return null;
	}

	/**
	 * Retrieves the "Export is a zip file" button widget of the Export File dialog.
	 * @param dialog - Export File dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getIsArchiveButton(Dialog dialog) {
		if (dialog != null && dialog instanceof ExportFileDialog) {
			ExportFileDialog eDialog = (ExportFileDialog) dialog;
			return eDialog.getIsArchiveButton();
		}
		return null;
	}

}
