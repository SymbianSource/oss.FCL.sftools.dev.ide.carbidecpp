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
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.ResourceBlockDialog;

/**
 * An interface for testing the Resource Block dialog of the MMP Editor
 */
public class ResourceBlockDialogTester {

	/**
	 * Retrieves the "RSS file path" combo widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return combo widget if success, null otherwise
	 */
	public static Combo getRSSFilePathCombo(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getRSSFilePathCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "Browse" button widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getBrowseButton(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getBrowseButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Target file name" text widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getTargetFileNameText(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getTargetFileNameText();
		}
		return null;
	}

	/**
	 * Retrieves the "Target file path" combo widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return combo widget if success, null otherwise
	 */
	public static Combo getTargetFilePathCombo(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getTargetFilePathCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "UID 2" text widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getUID2Text(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getUID2Text();
		}
		return null;
	}

	/**
	 * Retrieves the "UID 3" text widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getUID3Text(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getUID3Text();
		}
		return null;
	}

	/**
	 * Retrieves the "Header files only" button widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getOnlyGenerateHeaderButton(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getOnlyGenerateHeaderButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Generate header file" button widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getGenerateHeaderButton(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getGenerateHeaderButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Don't generate header file" button widget of the Resource Block dialog.
	 * @param dialog - Resource Block dialog provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getDontGenerateHeaderButton(Dialog dialog) {
		if (dialog != null && dialog instanceof ResourceBlockDialog) {
			ResourceBlockDialog rDialog = (ResourceBlockDialog) dialog;
			return rDialog.getDontGenerateHeaderButton();
		}
		return null;
	}

}
