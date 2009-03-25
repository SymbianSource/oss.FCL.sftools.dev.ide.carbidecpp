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
import java.util.Set;

import junit.framework.TestCase;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator.BaseDevLibLocator;

public class DevLibLocatorTest extends TestCase {
	BaseDevLibLocator devLibLocator;

	public void testProbeResourceForInterXFile() throws MalformedURLException {
		Set<DevLibProperties> symbianPluginHelpsList = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		assertTrue("No available Developer Library in plug-in directory",
				!symbianPluginHelpsList.isEmpty());

		// devLibLocator= new JarDevLibLocator(symbianPluginHelpsList.get(0)) ;
		// devLibLocator= new BaseDevLibLocator(new URL(url));
	}

}
