/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugdatacollector.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.internal.bugdatacollector.plugin.BugDataCollectorPlugin;

/**
 * Initializes preferences to empty
 *
 */
public class BugDataCollectorPreferenceInitializer  extends AbstractPreferenceInitializer {
	public void initializeDefaultPreferences() {
		IPreferenceStore store = BugDataCollectorPlugin.getPrefsStore();
		store.setDefault(BugDataCollectorPreferenceConstants.BR_PASSWORD, ""); //$NON-NLS-1$
		store.setDefault(BugDataCollectorPreferenceConstants.BR_USERNAME, ""); //$NON-NLS-1$
		store.setDefault(BugDataCollectorPreferenceConstants.BR_SEND_SDK_INFO, false); //$NON-NLS-1$
		store.setDefault(BugDataCollectorPreferenceConstants.BR_SEND_DIAGNOSTIC_LOG, false); //$NON-NLS-1$
	}
}
