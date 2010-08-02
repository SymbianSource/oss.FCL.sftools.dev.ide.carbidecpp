/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.extension;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * An action to show a preference page
 */
public class OpenPreferencePageAction extends Action {

	private String preferencePageId;

	public OpenPreferencePageAction(String text, String preferencePageId) {
		super(text);
		this.preferencePageId = preferencePageId;
	}

	@Override
	public void run() {
		Shell shell = WorkbenchUtils.getSafeShell();

		PreferenceDialog dialog = 
			PreferencesUtil.createPreferenceDialogOn(shell, preferencePageId, null, null);
		
		dialog.open();
	}
}
