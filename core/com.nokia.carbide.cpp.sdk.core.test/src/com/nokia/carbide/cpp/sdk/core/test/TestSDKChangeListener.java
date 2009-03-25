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
package com.nokia.carbide.cpp.sdk.core.test;

import junit.framework.TestCase;

import com.nokia.carbide.cpp.sdk.core.*;

/**
 * Tests the ICarbideInstalledSDKChangeListener class
 */
public class TestSDKChangeListener extends TestCase {
		
	protected ICarbideInstalledSDKChangeListener listener;
			
	// First thing we have to do is actually create a project in a workspace...
	protected void setUp() throws Exception {
		
		listener = new ICarbideInstalledSDKChangeListener() {
			public void installedSdkChanged(SDKChangeEventType eventType) {
				System.out.println("Rescan can event detected for test of ICarbideInstalledSDKChangeListener");
				assertTrue(eventType == SDKChangeEventType.eSDKScanned);
			}
		};
			
		SDKCorePlugin.getSDKManager().addInstalledSdkChangeListener(listener);	
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		SDKCorePlugin.getSDKManager().removeInstalledSdkChangeListener(listener);
	}
	
	// Here we just rescan the sdks and the listener will perform the test
	public void testCarbideConfigurationChangedListener() throws Exception{
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		sdkMgr.scanSDKs();
	}
	
}
