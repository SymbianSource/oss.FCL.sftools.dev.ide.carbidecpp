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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibHelper.DevLibSourceEnum;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;

public class DevLibHelperTester extends BaseTest {

	@Test
	public void testDevLibHelper() throws MalformedURLException {
		DevLibProperties activeDevLibLoc = null;
		DevLibSourceEnum devLibSource = DevLibHelper
				.findDevLibSourceType(activeDevLibLoc);
		assertNull(devLibSource);

		Set<DevLibProperties> devLibPropertiesSet = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();

		Iterator<DevLibProperties> it = devLibPropertiesSet.iterator();
		if (!it.hasNext()) {
			fail("No developer library available");
		}

		activeDevLibLoc = it.next();
		devLibSource = DevLibHelper.findDevLibSourceType(activeDevLibLoc);
		assertTrue(devLibSource.equals(DevLibSourceEnum.JAR));

		activeDevLibLoc = new DevLibProperties(
				new URL("http://www.symbian.com"));
		devLibSource = DevLibHelper.findDevLibSourceType(activeDevLibLoc);
		assertNull(devLibSource);
	}

	public void testDevLibContainsCSS() {
		DevLibPluginController.getInstance().probeDefaultDevLibPlugins();
		Set<DevLibProperties> devLibPropertiesSet = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();

		if (devLibPropertiesSet.isEmpty()) {
			fail("No developer library available");
		}
		Iterator<DevLibProperties> it = devLibPropertiesSet.iterator();
		while (it.hasNext()) {
			DevLibProperties devLib = it.next();
			String msg = existDevLibHasCSS(devLib);
			assertNull(msg, msg);
		}
	}

	private String existDevLibHasCSS(DevLibProperties devLib) {
		String interXRootDir = devLib.getInterXRootDir();
		for (String css : HoverConstants.DEVLIB_CSS_FILE_NAMES) {
			String urlHover = null;
			try {
				urlHover = URLHelper.append(devLib.getDevLibURL(),
						interXRootDir + HoverConstants.HOVER_CSS_PATH + css)
						.toString();
			} catch (MalformedURLException e) {
				return css + " CSS file not found in developer library "
						+ devLib.getUserFriendlyName();
			}
			if (urlHover != null && !URLHelper.validURL(urlHover)) {
				return css + " CSS file not found in developer library "
						+ devLib.getUserFriendlyName();
			}
		}
		return null;
	}
}
