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

import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.FilteredPreferenceDialog;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceDialog;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;

public class PreferencePageViewTest {
	static PreferencePageView page = null;
	static FilteredPreferenceDialog pref = null;

	@BeforeClass
	public static void initPreference() throws Exception {
		HoverManager.setTestMode(true);
		TestHelper.waitIndexingComplete(20000);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();

		pref = WorkbenchPreferenceDialog.createDialogOn(shell,
				PreferencePageView.PREFERENCE_ID);
		if (pref != null) {
			Display.getCurrent().asyncExec(new Runnable() {
				@Override
				public void run() {
					pref.open();
				}
			});
		} else {
			Assert.fail("can not open preference page");
		}
		Thread.sleep(2000);
		IPreferencePage pageI = pref.getCurrentPage();
		if (pageI instanceof PreferencePageView) {
			page = (PreferencePageView) pageI;
		} else {
			Assert.fail("Can not access preference page");
		}
	}

	@AfterClass
	public static void finishPreferenceTest() {
		pref.close();
	}

	@Test
	public void testDeactivateEnabled() throws Exception {
		IPreferenceStore store = page.getPreferenceStore();
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, true);
		// indirect way, modify state of wizard controls in preference page
		page.loadPreferences();
		page.performApply();
		Assert.assertFalse(HoverManager.getInstance().isEnabled());
	}

	@Test
	public void testSelectDevLib() throws Exception {
		HoverManager.setTestMode(true);
		IPreferenceStore store = page.getPreferenceStore();
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, false);
		DevLibPluginController.getInstance().probeDefaultDevLibPlugins();
		Set<DevLibProperties> devLibPropertiesSet = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		Iterator<DevLibProperties> it = devLibPropertiesSet.iterator();
		if (!it.hasNext()) {
			Assert.fail(" No Developer library available");
		}
		String devLib = it.next().getUserFriendlyName();
		// to trigger property change listener
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, "beeee");
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, devLib);
		// indirect way, modify state of wizard controls in preference page
		page.loadPreferences();
		page.performApply();
		TestHelper.waitIndexingComplete(20000);
		Assert.assertTrue(HoverManager.getInstance().isEnabled());
	}

	// Test previously selected developer library is not available anymore
	@Test
	public void testPreviouslySelectedDevLibDeleted() throws Exception {
		HoverManager.setTestMode(true);
		IPreferenceStore store = page.getPreferenceStore();
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, false);
		DevLibPluginController.getInstance().probeDefaultDevLibPlugins();
		Set<DevLibProperties> devLibPropertiesSet = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		Iterator<DevLibProperties> it = devLibPropertiesSet.iterator();
		if (!it.hasNext()) {
			Assert.fail(" No Developer library available");
		}
		DevLibProperties devLib = it.next();
		devLibPropertiesSet.remove(devLib);
		it = devLibPropertiesSet.iterator();
		if (!it.hasNext()) {
			Assert
					.fail("There must be more than one Developer library for this test!");
		}
		devLib = it.next();
		String devLibName = devLib.getUserFriendlyName();
		// to trigger property change listener
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, "beeee");
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, devLibName);
		// indirect way, modify state of wizard controls in preference page
		page.loadPreferences();
		page.performApply();
		TestHelper.waitIndexingComplete(20000);		
		Assert.assertTrue(HoverManager.getInstance().isEnabled());
	}

	@Test
	public void testDefault() throws Exception {
		IPreferenceStore store = page.getPreferenceStore();
		page.performDefaults();
		page.storePrefences();
		Assert.assertTrue(store
				.getBoolean(HoverConstants.PREFERENCE_AUTO_DEVLIB_SELECTION));
	}

	@Test
	public void testPerformHelp() throws Exception {
		page.performHelp();
	}
}
