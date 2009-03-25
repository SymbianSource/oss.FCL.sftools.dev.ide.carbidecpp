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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Utility to query the user with an OK/Cancel dialog
 * that is tied to a boolean preference. The preference
 * controls whether the dialog is shown or skipped. <p>
 * A checkbox can be set in the dialog to enable the preference.<p>
 * If the checkbox is set, only one response to the dialog
 * means it will be suppressed for the future. 
 *
 */
public class QueryWithBooleanPrefDialog  {

	private MessageDialogWithToggle dialog;
	
	protected String prefName;
	protected boolean valueToSkipDialog;
	protected boolean dontAskValue;

	private IPreferenceStore preferences;

	/**
	 * Create the dialog
	 * @param parentShell parent of the dialog, null for default
	 * @param title the dialog title, required
	 * @param prompt the prompt text, required
	 * @param plugin the plugin owning the preference, required
	 * @param prefName the name of the boolean preference, required
	 * @param valueToSkipDialog the boolean preference value which corresponds to
	 * skipping the dialog.
	 */
	public QueryWithBooleanPrefDialog(Shell parentShell, 
			String title, String prompt,
			AbstractUIPlugin plugin, String prefName, boolean valueToSkipDialog) {
		preferences = plugin.getPreferenceStore();
		dialog = new MessageDialogWithToggle(parentShell, title, 
				null, prompt, MessageDialog.INFORMATION,
				new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0,
				null,
				preferences.getBoolean(prefName) == valueToSkipDialog);
		this.prefName = prefName;
		this.valueToSkipDialog = valueToSkipDialog;
	}

	
	/**
	 * Main method to query for OK/Cancel. If the preference setting 
	 * is at the "don't skip" value then the dialog is run. If the
	 * preference setting is at the skip value then true is always
	 * returned.<p>
	 * If the user clicks OK with the "don't ask" checkbox checked
	 * then the preference setting is automatically updated.
	 */
	public boolean doQuery() {
		boolean result = false;
		boolean prefValue = preferences.getBoolean(prefName);
		if (prefValue != valueToSkipDialog) {
			result = (dialog.open() == IDialogConstants.OK_ID);
			if (dialog.getToggleState()) {
				preferences.setValue(prefName, valueToSkipDialog);
			}
		}
		else {
			result = true;
		}
		return result;
	}

}
