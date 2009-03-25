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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX;

import java.net.URL;

import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;

public class InterXAnalyserTest extends BaseTest {

	@Test
	public void testIsValid() {
		URL url = null;
		try {
			url = new URL(HoverConstants.SDL_WEB_ADDRESS);
		} catch (Exception e) {

		}
		assertFalse(InterXAnalyser.isValidInterXFile(url));
		InterXProperties anInterXProps = TestHelper.getAnInterXProps();
		assertTrue(InterXAnalyser.isValidInterXFile(anInterXProps
				.getInterXURL()));
	}

}
