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
package com.nokia.carbide.cpp.sysdoc.internal.hover.core;

import java.util.Collection;
import java.util.Set;

import org.eclipse.cdt.core.parser.util.ObjectMap;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXIndexController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXIndexer;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.ComponentMapNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.NotAvailableSDLException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.preferences.PreferencesPageController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.JettyWebServerTest;

public class HoverManagerTest extends BaseTest {

	@Test
	public void testInitHover() throws Exception {
		HoverManager.getInstance().setStarted(false);
		HoverManager.getInstance().initHover();
		TestHelper.waitIndexingComplete(20000);
		assertTrue(HoverManager.getInstance().isEnabled());
	}

	@Test
	public void testAllSDLPlugins() throws Exception {
		System.gc();
		System.gc();

		Set<DevLibProperties> symbianPluginHelpsList = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		assertTrue("No Developer Library plug-in is installed ",
				!symbianPluginHelpsList.isEmpty());
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		Thread.sleep(1000);

		for (DevLibProperties devLibProps : symbianPluginHelpsList) {
			assertTrue(devLibProps.isValid());
			String label = devLibProps.getUserFriendlyName();
			store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, true);
			store
					.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING,
							false);
			store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, "Booo");
			store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, label);
			Thread.sleep(1000);
			PreferencesPageController.getInstance()
					.analyseRecentPropertyChanges();
			Logger.logDebug("Waiting indexing to be completed");
			TestHelper.waitIndexingComplete(20000);
			Logger.logDebug("Indexing is completed");
			assertTrue(HoverManager.getInstance().isEnabled());
			entityExist();
		}
	}

	@Test
	public void testNoSDLPlugins() throws Exception {
		System.gc();
		System.gc();
		HoverManager.getInstance().setEnabled(false);
		Set<DevLibProperties> symbianPluginHelpsList = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		symbianPluginHelpsList.clear();

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		Thread.sleep(1000);

		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, true);
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING, false);
		try {
			PreferencesPageController.getInstance()
					.analyseRecentPropertyChanges();
			TestHelper.waitIndexingComplete(20000);
		} catch (NotAvailableSDLException he) {
			assertTrue(!HoverManager.getInstance().isEnabled());
			DevLibPluginController.getInstance().probeDefaultDevLibPlugins();
			return;
		}
		fail("Not available Developer Library must have been thrown");
	}

	private void entityExist() throws Exception {
		InterXIndexer index = InterXIndexController.getInterXIndexer();
		int i = 0;
		ComponentMapNode componentNode = null;
		do {
			componentNode = index.getValueAt(i);
			if (componentNode == null) {
				break;
			}
			Collection<PathNode> values = componentNode.getComponentMap()
					.values();
			for (PathNode node : values) {
				travalPathNode(node);
			}
			i++;
		} while (componentNode != null);
	}

	private void travalPathNode(PathNode node) throws Exception {
		ObjectMap sub = node.getSub();
		if (sub == null) {
			checkPathNodePathExist(node);
			return;
		} else {
			int sanitySize = 1; // dont check all of it, but some ...
			for (int i = 0; i < sub.size() && i < sanitySize; i++) {
				PathNode n = (PathNode) sub.get(i);
				if (n == null) {
					checkPathNodePathExist(node);
					continue;
				}
				travalPathNode(node);
			}
		}
	}

	private void checkPathNodePathExist(PathNode node) throws Exception {

		String path = node.getPath();
		String url = URLHelper.getFullPath(path);
		Logger.logDebug("full url:" + url);
		String content = JettyWebServerTest.readWebPage(url, false);
		if (content.isEmpty()) {
			String msg = url + "does not exist";
			Logger.logDebug(msg);
			Logger.logWarn(msg);
			fail(msg);
		}
	}
}
