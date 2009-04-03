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

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.nokia.cpp.utils.ui.noexport.Messages;

/**
 * Utility that wraps a JFace dialog to which notifies the user
 * of a situation.  The dialog has a checkbox indicating whether
 * to show the dialog again.
 * 
 * @see org.eclipse.jface.dialogs.MessageDialogWithToggle
 *
 */
public class NotifyWithBooleanPrefDialog  {
	
	protected String prefName;

	private IPreferenceStore preferences;

	private Shell parentShell;

	private String title;

	private String prompt;

	/**
	 * Create the dialog
	 * @param parentShell parent of the dialog, null for default
	 * @param title the dialog title, required
	 * @param prompt the prompt text, required
	 * @param plugin the plugin owning the preference, required
	 * @param prefName the name of the boolean preference, required
	 */
	public NotifyWithBooleanPrefDialog(Shell parentShell, 
			String title, String prompt,
			AbstractUIPlugin plugin, String prefName) {
		this.parentShell = parentShell;
		this.title = title;
		this.prompt = prompt;
		this.preferences = plugin.getPreferenceStore();
		this.prefName = prefName;
	}
	

	
	/**
	 * Main method to query for a setting.  If the preference setting 
	 * is at the skip value then the call returns immediately.
	 * <p> 
	 * If the user clicks OK with the "don't ask" checkbox checked
	 * then the preference setting is automatically updated.
	 */
	public void doNotify() {
		
		String value = preferences.contains(prefName)
					? preferences.getString(prefName)
					: null;
        if (value != null) {
        	if (value.equals(MessageDialogWithToggle.ALWAYS))
        		return;
        	// else, bogus, and go to dialog
        }

        MessageDialogWithToggle.openInformation(
			parentShell, title, prompt, Messages.getString("NotifyWithBooleanPrefDialog.DoNotNotifyAgain"), //$NON-NLS-1$
			false, preferences, prefName);
	}

}
