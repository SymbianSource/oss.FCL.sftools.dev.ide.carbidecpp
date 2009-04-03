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
* Base class for tests that modify a stub devices.xml file.  Write the contents
* into a temporary directory so we don't dirty the workspace.
* These tests set up a file that is left behind for each test (to work with
* DevicesLoaderTest, which expects iteratively updated devices.xml).
*
*
*/
package com.nokia.carbide.cpp.sdk.core.test;

import com.nokia.cpp.internal.api.utils.core.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

import junit.framework.TestCase;
public abstract class BaseDeviceModifierTest extends TestCase {
	
	private String DEVICES_FILE = "devices.xml";
	protected File devicesFile;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File tempDir = FileUtils.getTemporaryDirectory();
		devicesFile = new File(tempDir, DEVICES_FILE);
		if (!devicesFile.exists()) {
			// clear out file
			FileOutputStream stream = new FileOutputStream(devicesFile);
			stream.close();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
