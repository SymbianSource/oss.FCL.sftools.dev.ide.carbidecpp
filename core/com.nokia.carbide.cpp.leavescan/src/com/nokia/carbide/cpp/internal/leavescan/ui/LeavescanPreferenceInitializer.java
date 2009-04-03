/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
*/
package com.nokia.carbide.cpp.internal.leavescan.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.leavescan.LeavescanPlugin;

/**
 * Class used to initialize default preference values.
 */
public class LeavescanPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = LeavescanPlugin.getLeaveScanPrefsStore();
		store.setDefault(LeavescanPreferenceConstants.LEAVESCAN_NOISY_OUTPUT, false);
		store.setDefault(LeavescanPreferenceConstants.LEAVESCAN_FOLDER, "");
	}

}
