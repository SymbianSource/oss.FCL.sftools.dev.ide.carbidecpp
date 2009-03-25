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

import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;

public class PreferencesPageControllerTest extends TestCase {

	@Test
	public void testDeactivatedProptery() throws Exception {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean preVal = store
				.getBoolean(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING);
		if (preVal) {
			store
					.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING,
							false);
		}
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, true);
		Thread.sleep(1000);
		assertTrue(!PreferencesPageController.getInstance()
				.getChangedProperties().isEmpty());
		PreferencesPageController.getInstance().analyseRecentPropertyChanges();
		assertTrue(!HoverManager.getInstance().isEnabled());
	}

	@Test
	public void testDevLibChanged() throws Exception {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String preVal = store.getString(HoverConstants.PREFERENCE_DEV_LIB_LOC);
		Set<DevLibProperties> devLibPropertiesSet = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		Iterator<DevLibProperties> iterator = devLibPropertiesSet.iterator();
		String newVal = preVal;
		while (iterator.hasNext()) {
			DevLibProperties devLibProp = iterator.next();
			if (devLibProp.getUserFriendlyName().equals(preVal)) {
				continue;
			}
			newVal = devLibProp.getUserFriendlyName();
			break;
		}
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, newVal);
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, false);
		Thread.sleep(1000);
		PreferencesPageController.getInstance().analyseRecentPropertyChanges();
		Thread.sleep(1000);
		assertTrue(PreferencesPageController.getInstance()
				.getChangedProperties().isEmpty());
		assertTrue(HoverManager.getActiveDevLibProperties()
				.getUserFriendlyName().equals(newVal));
	}

	public void testDevLibInvalidChanged() throws Exception {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, "Beee");
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, "Booo");
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, false);
		Thread.sleep(1000);
		HoverManager.setActiveDevLibProperties(null);
		PreferencesPageController.getInstance().analyseRecentPropertyChanges();
		Thread.sleep(1000);
		assertTrue(PreferencesPageController.getInstance()
				.getChangedProperties().isEmpty());
		assertNotNull(HoverManager.getActiveDevLibProperties());
	}

	@Test
	public void testChangedProptery() throws Exception {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean preVal = store
				.getBoolean(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING);
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, !preVal);
		Thread.sleep(1000);
		assertTrue(!PreferencesPageController.getInstance()
				.getChangedProperties().isEmpty());
	}
}
