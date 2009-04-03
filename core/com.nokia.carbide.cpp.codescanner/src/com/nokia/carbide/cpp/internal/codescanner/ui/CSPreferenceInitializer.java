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

package com.nokia.carbide.cpp.internal.codescanner.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigSettings;

/**
 * A class to to initialize default values of the CodeScanner preference page.
 */
public class CSPreferenceInitializer extends AbstractPreferenceInitializer {

	// private members
	private IPreferenceStore store;
	private CSConfigSettings defaultConfigSettings;

	/**
	 * Initialize default preference values.
	 */
	public void initializeDefaultPreferences() {
		store = CSPlugin.getCSPrefsStore();
		if (store == null) {
			return;
		}
		defaultConfigSettings = CSPlugin.getConfigManager().getDefaultConfig();
		if (defaultConfigSettings == null) {
			return;
		}
		store.setDefault(CSPreferenceConstants.PROJ_SETTINGS, false);
		CSGeneralTabPage.initializePreferenceValues();
		CSFileFiltersTabPage.initializePreferenceValues();
		CSRulesTabPage.initializePreferenceValues();
	}

}
