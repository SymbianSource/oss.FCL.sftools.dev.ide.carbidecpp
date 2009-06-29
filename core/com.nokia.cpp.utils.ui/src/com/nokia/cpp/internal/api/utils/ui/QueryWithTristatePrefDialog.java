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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * Utility that wraps a JFace dialog to which asks the user
 * a boolean question.  The dialog has a checkbox indicating whether
 * to remember the decision (yea or nay) for later.
 * <p>
 * This differs from QueryWithBooleanPrefDialog which only
 * remembers a decision if it was answered one way or the other.
 * 
 * @see org.eclipse.jface.dialogs.MessageDialogWithToggle
 * @see com.nokia.sdt.utils.ui.QueryWithBooleanPrefDialog
 *
 */
public class QueryWithTristatePrefDialog  {

	public static final int QUERY_YES_NO = 1;
	public static final int QUERY_OK_CANCEL = 2;
	
	protected String prefName;
	protected boolean valueToSkipDialog;
	protected boolean dontAskValue;

	private IPreferenceStore preferences;

	private Shell parentShell;

	private String title;

	private String prompt;

	private boolean initialSetting;

	private int type;

	/**
	 * Create the dialog
	 * @param parentShell parent of the dialog, null for default
	 * @param title the dialog title, required
	 * @param prompt the prompt text, required
	 * @param plugin the plugin owning the preference, required
	 * @param prefName the name of the boolean preference, required
	 * @param initialSetting the initial setting for the toggle
	 * @param type the query to perform (QUERY_YES_NO, QUERY_OK_CANCEL)
	 */
	public QueryWithTristatePrefDialog(Shell parentShell, 
			String title, String prompt,
			AbstractUIPlugin plugin, String prefName, 
			boolean initialSetting, int type) {
		Check.checkArg(type == QUERY_OK_CANCEL || type == QUERY_YES_NO);
		this.parentShell = parentShell;
		this.title = title;
		this.prompt = prompt;
		this.preferences = plugin.getPreferenceStore();
		this.prefName = prefName;
		this.initialSetting = initialSetting;
		this.type = type;
	}
	

	
	/**
	 * Main method to query for a setting.  If the preference setting 
	 * is at the "don't skip" value then the dialog is run. If the
	 * preference setting is at the skip value then the retained value 
	 * is always returned.<p>
	 * If the user clicks OK with the "don't ask" checkbox checked
	 * then the preference setting is automatically updated.
	 * @return true if the "always" pref is set or the positive value (yes/ok) 
	 * was selected, false if the "never" pref or the negative value (no/cancel)
	 * was selected.
	 */
	public boolean doQuery() {
		
		String value = preferences.contains(prefName)
					? preferences.getString(prefName)
					: null;
        if (value != null) {
        	if (value.equals(MessageDialogWithToggle.ALWAYS))
        		return true;
        	if (value.equals(MessageDialogWithToggle.NEVER))
        		return false;
        	// else, bogus, and go to dialog
        }

		MessageDialogWithToggle dialog;
		boolean confirmed = false;
		if (type == QUERY_YES_NO) {
			dialog = MessageDialogWithToggle.openYesNoQuestion(
				parentShell, title, prompt, "Don't ask to manage dependencies again.",
				initialSetting, preferences, 
				prefName);
			confirmed = dialog.getReturnCode() == IDialogConstants.YES_ID;
		}
		else if (type == QUERY_OK_CANCEL) {
			dialog = MessageDialogWithToggle.openYesNoQuestion(
					parentShell, title, prompt, null,
					initialSetting, preferences, 
					prefName);
			confirmed = dialog.getReturnCode() == IDialogConstants.OK_ID;
		}
		else
			Check.checkState(false);
		return confirmed;
	}

}
