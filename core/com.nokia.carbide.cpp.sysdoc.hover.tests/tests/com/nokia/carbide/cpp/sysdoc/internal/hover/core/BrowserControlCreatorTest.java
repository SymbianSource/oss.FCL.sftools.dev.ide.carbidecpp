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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

import org.eclipse.jface.text.IInformationControl;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.view.BrowserInformationControl;

public class BrowserControlCreatorTest extends BaseTest {

	@Test
	public void testDoCreateInformationControl() throws Exception {
		IInformationControl iControl = createIInformationControl();
		assertNotNull(iControl);
	}

	public void testInputs() throws Exception {
		IInformationControl iControl = createIInformationControl();
		assertNotNull(iControl);

		BrowserInformationControl bic = null;
		if (iControl instanceof BrowserInformationControl) {
			bic = (BrowserInformationControl) iControl;
			String fqn = "LOCAL_C";
			checkBrowserInformationControl(bic, fqn);

			fqn = "myRubbishAPI::dfs";
			checkBrowserInformationControl(bic, fqn);

			fqn = "time_t";
			checkBrowserInformationControl(bic, fqn);

			fqn = "CConsoleBase::Write";
			checkBrowserInformationControl(bic, fqn);

			fqn = "CleanupStack::PushL";
			checkBrowserInformationControl(bic, fqn);
			bic.getInput();

			// wait
			fqn = "CleanupStack::PushL";
			FutureTask<DevLibContent> ft = TestHelper
					.createAsynchronousLookupFuture(fqn);
			bic.setInput(ft);

		} else {
			fail("Could not create custom browser information control");
		}
	}

	private IInformationControl createIInformationControl() throws Exception {
		TestHelper.waitIndexingComplete(20000);
		// we want to check also servlet and direct jar content reading
		HoverManager.setTestMode(false);
		Shell shell = Activator.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getShell();
		BrowserControlCreator browserControlCreator = new BrowserControlCreator();
		IInformationControl iControl = browserControlCreator
				.doCreateInformationControl(shell);
		return iControl;
	}

	private void checkBrowserInformationControl(BrowserInformationControl bic,
			String fqn) throws Exception, ExecutionException, TimeoutException {
		FutureTask<DevLibContent> ft = TestHelper.createAsynchronousLookup(fqn);
		bic.setInput(ft);
		Thread.sleep(1000);
	}
}
