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
package com.nokia.carbide.cpp.internal.sdk.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import com.nokia.carbide.cpp.sdk.ui.SDKUIPlugin;

/**
 * Class used to initialize default preference values.
 */
public class SDKUIPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = SDKUIPlugin.getDefault().getPreferenceStore();
		store.setDefault(SDKUIPreferenceConstants.ENABLE_BSF_SCANNER, false);
		store.setDefault(SDKUIPreferenceConstants.LISTEN_FOR_DEVICES_XML_CHANGE, true);
		store.setDefault(SDKUIPreferenceConstants.SCAN_FOR_NEW_PLUGINS, false);
		
		store.setDefault(SDKUIPreferenceConstants.PLAT_EKA2_WINSCW, true);
		store.setDefault(SDKUIPreferenceConstants.PLAT_EKA2_GCCE, true);
		store.setDefault(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5, true);
		store.setDefault(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV2, false);
		store.setDefault(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV1, false);
	}

}
