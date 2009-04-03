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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndEntry;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndFeed;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

import junit.framework.TestCase;

/**
 * Test class for CarbideSyndFeed.
 *
 */
public class CarbideSyndFeedTest extends TestCase {

	// private data
	private SyndFeed syndFeed;
	private CarbideSyndFeed cFeed;

	protected void setUp() throws Exception {
		super.setUp();
		syndFeed = new SyndFeedImpl();
		cFeed = new CarbideSyndFeed(syndFeed);
	}

	protected void tearDown() throws Exception {
		cFeed = null;
		syndFeed = null;
		super.tearDown();
	}

	public void testCarbideSyndFeed() {
		CarbideSyndFeed feed = new CarbideSyndFeed(syndFeed);
		assertNotNull(feed);
	}

	public void testGetDescription() {
		String description = "This is a syndicated news feed";
		assertNull(cFeed.getDescription());
		cFeed.setDescription(description);
		assertNotNull(cFeed.getDescription());
		assertTrue(cFeed.getDescription().equals(description));
	}

	public void testGetEntries() {
		assertNotNull(cFeed.getEntries());
		assertTrue(cFeed.getEntries().size() == 0);
		cFeed.getEntries().add(new CarbideSyndEntry(new SyndEntryImpl()));
		assertTrue(cFeed.getEntries().size() > 0);
	}

	public void testGetLink() {
		String link = "http://www.google.com";
		assertNull(cFeed.getLink());
		cFeed.setLink(link);
		assertNotNull(cFeed.getLink());
		assertTrue(cFeed.getLink().equals(link));
	}

	public void testGetPublishedDate() {
		Date now = Calendar.getInstance().getTime();
		assertNull(cFeed.getPublishedDate());
		cFeed.setPublishedDate(now);
		assertNotNull(cFeed.getPublishedDate());
		assertTrue(cFeed.getPublishedDate().equals(now));
	}

	public void testGetTitle() {
		String title = "Syndicate Feed";
		assertNull(cFeed.getTitle());
		cFeed.setTitle(title);
		assertNotNull(cFeed.getTitle());
		assertTrue(cFeed.getTitle().equals(title));
	}

	public void testGetType() {
		String type = "news";
		assertNull(cFeed.getType());
		cFeed.setType(type);
		assertNotNull(cFeed.getType());
		assertTrue(cFeed.getType().equals(type));
	}

	public void testGetUnreadEntries() {
		assertNotNull(cFeed.getUnreadEntries());
		assertTrue(cFeed.getUnreadEntries().size() == 0);
		CarbideSyndEntry entry = new CarbideSyndEntry(new SyndEntryImpl());
		entry.setRead(false);
		cFeed.getEntries().add(entry);
		assertTrue(cFeed.getUnreadEntries().size() > 0);
		cFeed.getEntries().remove(entry);
		assertTrue(cFeed.getUnreadEntries().size() == 0);
	}

	public void testIsNew() {
		cFeed.setIsNew(true);
		assertTrue(cFeed.isNew());
		cFeed.setIsNew(false);
		assertFalse(cFeed.isNew());
	}

	public void testIsSubscribed() {
		cFeed.setSubscribed(true);
		assertTrue(cFeed.isSubscribed());
		cFeed.setSubscribed(false);
		assertFalse(cFeed.isSubscribed());
	}

	public void testSetDescription() {
		String description = "This is a syndicated news feed";
		cFeed.setDescription(description);
		assertNotNull(cFeed.getDescription());
		assertTrue(cFeed.getDescription().equals(description));
		cFeed.setDescription(null);
		assertNull(cFeed.getDescription());
	}

	public void testSetEntries() {
		List<CarbideSyndEntry> entries = new ArrayList<CarbideSyndEntry>();
		cFeed.setEntries(entries);
		assertNotNull(cFeed.getEntries());
		assertTrue(cFeed.getEntries().equals(entries));
		assertTrue(cFeed.getEntries().size() == 0);
		cFeed.setEntries(null);
		assertNull(cFeed.getEntries());
		entries.add(new CarbideSyndEntry(new SyndEntryImpl()));
		cFeed.setEntries(entries);
		assertTrue(cFeed.getEntries().equals(entries));
		assertTrue(cFeed.getEntries().size() > 0);
	}

	public void testSetLink() {
		String link = "http://www.google.com";
		cFeed.setLink(link);
		assertNotNull(cFeed.getLink());
		assertTrue(cFeed.getLink().equals(link));
		cFeed.setLink(null);
		assertNull(cFeed.getLink());
	}

	public void testSetPublishedDate() {
		Date now = Calendar.getInstance().getTime();
		cFeed.setPublishedDate(now);
		assertNotNull(cFeed.getPublishedDate());
		assertTrue(cFeed.getPublishedDate().equals(now));
		cFeed.setPublishedDate(null);
		assertNull(cFeed.getPublishedDate());
	}

	public void testSetTitle() {
		String title = "Syndicate Feed";
		cFeed.setTitle(title);
		assertNotNull(cFeed.getTitle());
		assertTrue(cFeed.getTitle().equals(title));
		cFeed.setTitle(null);
		assertNull(cFeed.getTitle());
	}

	public void testSetType() {
		String newsType = "news";
		String resourceType = "resource";
		cFeed.setType(newsType);
		assertNotNull(cFeed.getType());
		assertTrue(cFeed.getType().equals(newsType));
		cFeed.setType(resourceType);
		assertNotNull(cFeed.getType());
		assertTrue(cFeed.getType().equals(resourceType));
		cFeed.setType(null);
		assertNull(cFeed.getType());
	}

}
