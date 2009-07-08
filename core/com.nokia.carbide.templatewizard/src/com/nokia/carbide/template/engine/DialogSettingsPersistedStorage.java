/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.template.engine;


import org.eclipse.jface.dialogs.IDialogSettings;

/**
 * This class is a default implementation of {@link IPersistedSettingStorage} that
 * uses IDialogSettings to store its contents as strings.
 * @since 2.1
 *
 */
public class DialogSettingsPersistedStorage implements
		IPersistedSettingStorage {
	private final IDialogSettings dialogSettings;

	/**
	 * @param dialogSettings
	 */
	public DialogSettingsPersistedStorage(IDialogSettings dialogSettings) {
		this.dialogSettings = dialogSettings;
	}
	
	/**
	 * Override this method if you want a setting to be modified from the template field id.
	 * @param key incoming template field id
	 * @return key for settings storage
	 */
	protected String transformKey(String key) {
		return key;
	}

	public String read(String key) {
		return dialogSettings.get(transformKey(key));
	}

	public void write(String key, String value) {
		dialogSettings.put(transformKey(key), value);
	}
}
