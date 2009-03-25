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
*
* Description: 
*
*/

package com.nokia.carbide.cpp.epoc.engine.tests;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import junit.textui.TestRunner;

public class WorkbenchTestSuiteRunnerApplication implements IApplication {

	public Object start(IApplicationContext context) throws Exception {
		TestRunner runner = new TestRunner();
		runner.startTest(WorkbenchTests.suite());
		return EXIT_OK;
	}

	public void stop() {
	}


}
