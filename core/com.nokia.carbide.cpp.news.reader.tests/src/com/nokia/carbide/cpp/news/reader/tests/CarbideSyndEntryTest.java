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

import java.util.Calendar;
import java.util.Date;

import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndEntry;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

import junit.framework.TestCase;

/**
 * Test class for CarbideSyndEntry.
 *
 */
public class CarbideSyndEntryTest extends TestCase {

	// private data
	private SyndEntry syndEntry;
	private CarbideSyndEntry cEntry;

	protected void setUp() throws Exception {
		super.setUp();
		syndEntry = new SyndEntryImpl();
		cEntry = new CarbideSyndEntry(syndEntry);
	}

	protected void tearDown() throws Exception {
		cEntry = null;
		syndEntry = null;
		super.tearDown();
	}

	public void testCarbideSyndEntry() {
		CarbideSyndEntry entry = new CarbideSyndEntry(syndEntry);
		assertNotNull(entry);
	}

	public void testCompareTo() {
		cEntry.setPublishedDate(Calendar.getInstance().getTime());
		SyndEntry syndEntry2 = new SyndEntryImpl();
		CarbideSyndEntry cEntry2 = new CarbideSyndEntry(syndEntry2);
		cEntry2.setPublishedDate(cEntry.getPublishedDate());
		assertTrue(cEntry.compareTo(cEntry2) == 0);
	}

	public void testGetDescription() {
		String description = "This is a syndicated news feed entry";
		assertNull(cEntry.getDescription());
		cEntry.setDescription(description);
		assertNotNull(cEntry.getDescription());
		assertTrue(cEntry.getDescription().equals(description));
	}

	public void testGetLink() {
		String link = "http://www.google.com";
		assertNull(cEntry.getLink());
		cEntry.setLink(link);
		assertNotNull(cEntry.getLink());
		assertTrue(cEntry.getLink().equals(link));
	}

	public void testGetPublishedDate() {
		Date now = Calendar.getInstance().getTime();
		assertNull(cEntry.getPublishedDate());
		cEntry.setPublishedDate(now);
		assertNotNull(cEntry.getPublishedDate());
		assertTrue(cEntry.getPublishedDate().equals(now));
	}

	public void testGetTitle() {
		String title = "Syndicate Entry";
		assertNull(cEntry.getTitle());
		cEntry.setDescription(title);
		assertNotNull(cEntry.getDescription());
		assertTrue(cEntry.getDescription().equals(title));
	}

	public void testIsRead() {
		assertFalse(cEntry.isRead());
		cEntry.setRead(true);
		assertTrue(cEntry.isRead());
	}

	public void testSetDescription() {
		String description = "This is a syndicated news feed entry";
		cEntry.setDescription(description);
		assertNotNull(cEntry.getDescription());
		assertTrue(cEntry.getDescription().equals(description));
		cEntry.setDescription(null);
		assertNull(cEntry.getDescription());
	}

	public void testSetLink() {
		String link = "http://www.google.com";
		cEntry.setLink(link);
		assertNotNull(cEntry.getLink());
		assertTrue(cEntry.getLink().equals(link));
		cEntry.setLink(null);
		assertNull(cEntry.getLink());
	}

	public void testSetPublishedDate() {
		Date now = Calendar.getInstance().getTime();
		cEntry.setPublishedDate(now);
		assertNotNull(cEntry.getPublishedDate());
		assertTrue(cEntry.getPublishedDate().equals(now));
		cEntry.setPublishedDate(null);
		assertNull(cEntry.getPublishedDate());
	}

	public void testSetRead() {
		cEntry.setRead(true);
		assertTrue(cEntry.isRead());
		cEntry.setRead(false);
		assertFalse(cEntry.isRead());
	}

	public void testSetTitle() {
		String title = "Syndicate Entry";
		cEntry.setDescription(title);
		assertNotNull(cEntry.getDescription());
		assertTrue(cEntry.getDescription().equals(title));
		cEntry.setDescription(null);
		assertNull(cEntry.getTitle());
	}

}
