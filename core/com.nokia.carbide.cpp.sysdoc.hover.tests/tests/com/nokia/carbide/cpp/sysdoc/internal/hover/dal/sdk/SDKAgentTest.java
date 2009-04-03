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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.sdk;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;

public class SDKAgentTest extends BaseTest {

	@Ignore
	public void ignoreTestAvailableSDKList() throws MalformedURLException {
		SDKController.getInstance().probeAllAvailableSystemSDKDirectories();
		List<URL> validSDKHelpRootDirectories = SDKController.getInstance()
				.getValidSDKHelpRootDirectories();
		assertTrue("None of the installed SDK has any Developer Library",
				!validSDKHelpRootDirectories.isEmpty());
	}

	public void testIsResourceInSDKDirectory() {
		Set<URL> skds = SDKController.getInstance()
				.getAvailableAllSDKDirectories();

		Iterator<URL> it = skds.iterator();
		if (!it.hasNext()) {
			fail("No SDK is installed");
		}
		URL url = it.next();
		String sdkPath = URLHelper.toFileSystemPath(url);
		boolean validAPIDIr = SDKController.getInstance()
				.isResourceInSDKDirectory(new String[] { sdkPath });
		assertTrue(validAPIDIr);

		validAPIDIr = SDKController.getInstance().isResourceInSDKDirectory(
				new String[] { "" });
		assertFalse(validAPIDIr);

		skds.clear();
		validAPIDIr = SDKController.getInstance().isResourceInSDKDirectory(
				new String[] { sdkPath });
		assertFalse(validAPIDIr);

	}
}
