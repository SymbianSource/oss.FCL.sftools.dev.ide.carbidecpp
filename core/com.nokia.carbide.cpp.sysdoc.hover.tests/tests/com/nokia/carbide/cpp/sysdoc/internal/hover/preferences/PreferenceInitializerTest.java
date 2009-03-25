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

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;

public class PreferenceInitializerTest extends BaseTest {

	@Test
	public void testMyDefaults() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		Boolean autoMode = store
				.getDefaultBoolean(HoverConstants.PREFERENCE_AUTO_DEVLIB_SELECTION);
		assertTrue(autoMode);

		Boolean deActivate = store
				.getDefaultBoolean(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING);

		// If there is no developer library available, then deactivate hovering
		// is true as default
		String defaultDevLib = PreferencesPageController.getInstance()
				.probeDefaultDevLibLocation();
		if (defaultDevLib != null) {
			assertTrue(!deActivate);
		} else {
			assertTrue(deActivate);
		}

		String firstSDL = store
				.getDefaultString(HoverConstants.PREFERENCE_DEV_LIB_LOC);
		assertTrue(!firstSDL.isEmpty());
	}

	@Test
	public void testNoDevLibDefaults() {
		DevLibPluginController.getInstance().getDevLibPropertiesSet().clear();
		PreferenceInitializer prefInit = new PreferenceInitializer();
		prefInit.initializeDefaultPreferences();
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		Boolean deActivate = store
				.getDefaultBoolean(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING);
		assertTrue(deActivate);
		DevLibPluginController.getInstance().probeDefaultDevLibPlugins();
	}
}
