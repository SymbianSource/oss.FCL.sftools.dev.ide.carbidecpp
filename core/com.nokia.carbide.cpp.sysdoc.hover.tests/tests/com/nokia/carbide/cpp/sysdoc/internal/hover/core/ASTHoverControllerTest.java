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

import java.util.List;
import java.util.concurrent.Future;

import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.swt.widgets.Display;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;

public class ASTHoverControllerTest extends BaseTest {

	@Test
	public void testASTAndAPIBindings() throws Exception {
		TestHelper.waitIndexingComplete(20000);
		Logger.logDebug("start resolving");
		ITranslationUnit tUnit = TestHelper.getTranslationUnit();
		ASTHoverController astHoverController = new ASTHoverController();
		checkHoverObject(astHoverController, tUnit, "LOCAL_D", 565, 7, false);
		checkHoverObject(astHoverController, tUnit, "CConsoleBase", 573, 12,
				false);
		checkHoverObject(astHoverController, tUnit, "New", 1235, 3, false);
		// not Symbian API
		checkHoverObject(astHoverController, tUnit, null, 1, 34, true);
		checkHoverObject(astHoverController, tUnit, null, 797, 4, true);
		checkHoverObject(astHoverController, tUnit, null, 412, 17, true);
	}

	private void checkHoverObject(ASTHoverController astHoverController,
			ITranslationUnit tUnit, String fqn, int offset, int len,
			boolean isNull) throws Exception {

		Object hoverInfo = astHoverController.getASTHoverInfo(tUnit, offset,
				len);
		checkHoverObject(hoverInfo, fqn, isNull);
	}

	public static void checkHoverObject(Object hoverInfo, String fqn,
			boolean isNull) throws Exception {

		if (isNull) {
			assertNull(hoverInfo);
			return;
		} else {
			assertNotNull(hoverInfo);
		}

		if (!(hoverInfo instanceof Future)) {
			fail("Unknown object. Future <DevLibContent> was expected");
		}

		Future<DevLibContent> fDevContent = (Future<DevLibContent>) hoverInfo;
		Display display = Activator.getDefault().getWorkbench().getDisplay();
		int sleep = 100;
		DevLibContent devLibContent;
		while (true) {

			if (fDevContent.isDone()) {
				devLibContent = fDevContent.get();
				break;
			}

			if (fDevContent.isCancelled()) {
				fail("Can not obtain Future <DevContent>.");
			}

			display.readAndDispatch();
			Thread.sleep(sleep);
		}

		String fullURL = null;

		List<PathNode> pathAsNodesList = devLibContent.getPathAsNodesList();
		if (pathAsNodesList == null) {
			fullURL = devLibContent.getURL().toString();
		}
		if (pathAsNodesList.size() == 1) {
			fullURL = URLHelper.getFullPath(pathAsNodesList.get(0).getPath());
		}
		if (pathAsNodesList.size() > 1) {
			fullURL = URLHelper.getFullPath(pathAsNodesList.get(0).getPath());
		}

		assertTrue(fullURL.toLowerCase().contains(fqn.toLowerCase()));

	}

}
