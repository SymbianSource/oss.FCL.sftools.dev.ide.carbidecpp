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

package com.nokia.carbide.cpp.internal.news.reader.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;

/**
 * A class to to initialize default values of the Carbide.c++ news preference page.
 */
public class NewsPreferenceInitializer extends AbstractPreferenceInitializer {

	// private members
	private IPreferenceStore store;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		store = CarbideNewsReaderPlugin.getPrefsStore();
		if (store == null) {
			return;
		}

		store.setDefault(NewsPreferenceConstants.LAUNCH_AT_STARTUP, true);
		store.setDefault(NewsPreferenceConstants.UPDATE_INTERVAL, 6);
	}

}
