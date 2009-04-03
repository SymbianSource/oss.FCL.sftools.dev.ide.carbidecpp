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
package com.nokia.carbide.cpp.sysdoc.internal.hover.view;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

public class DeveloperLibraryViewTest extends BaseTest {

	@Test
	public void testDeveloperLibraryView() throws PartInitException {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.showView(DeveloperLibraryView.VIEW_ID);
		URL url = null;
		try {
			url = new URL(HoverConstants.SDL_WEB_ADDRESS);
		} catch (MalformedURLException e) {
			Logger.logError(e);
		}
		DeveloperLibraryView.setURL(url);
	}
}
