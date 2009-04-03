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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, false);
		store.setDefault(HoverConstants.PREFERENCE_AUTO_DEVLIB_SELECTION, true);

		String defaultDevLib = PreferencesPageController.getInstance()
				.probeDefaultDevLibLocation();
		if (defaultDevLib != null) {
			store.setDefault(HoverConstants.PREFERENCE_DEV_LIB_LOC,
					defaultDevLib);
		} else {
			store.setDefault(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING,
					true);			
		}
	}
}
