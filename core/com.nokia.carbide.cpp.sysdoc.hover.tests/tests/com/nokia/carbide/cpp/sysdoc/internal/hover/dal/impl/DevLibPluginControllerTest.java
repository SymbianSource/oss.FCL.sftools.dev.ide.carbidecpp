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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.impl;

import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;

public class DevLibPluginControllerTest extends TestCase {

	@Test
	public void testAvailableSDL() {
		Set<DevLibProperties> symbianPluginHelpsList = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		assertTrue("No Developer Library plug-in is installed ",
				!symbianPluginHelpsList.isEmpty());
	}

}
