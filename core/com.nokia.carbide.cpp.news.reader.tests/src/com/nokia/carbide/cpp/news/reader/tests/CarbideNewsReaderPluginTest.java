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

package com.nokia.carbide.cpp.news.reader.tests;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;

import junit.framework.TestCase;

/**
 * Test class for CarbideNewsReaderPlugin.
 *
 */
public class CarbideNewsReaderPluginTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCarbideNewsReaderPlugin() {
		assertNotNull(new CarbideNewsReaderPlugin());
	}

	public void testGetDefault() {
		assertNotNull(CarbideNewsReaderPlugin.getDefault());
	}

	public void testGetFeedManager() {
		assertNotNull(CarbideNewsReaderPlugin.getFeedManager());
	}

	public void testGetPrefsStore() {
		assertNotNull(CarbideNewsReaderPlugin.getPrefsStore());
	}

	public void testLaunchNewsPage() {
		try {
			CarbideNewsReaderPlugin.launchNewsPage();
		} catch (Exception e) {
			fail();
		}
	}

	public void testLoadFeeds() {
		try {
			CarbideNewsReaderPlugin.loadFeeds();
		} catch (Exception e) {
			fail();
		}
	}

	public void testUpdateFeeds() {
		try {
			CarbideNewsReaderPlugin.updateFeeds();
		} catch (Exception e) {
			fail();
		}
	}

}
