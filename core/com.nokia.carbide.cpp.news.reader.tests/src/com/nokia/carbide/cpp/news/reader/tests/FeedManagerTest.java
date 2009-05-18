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

import java.util.Iterator;
import java.util.List;

import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndEntry;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndFeed;
import com.nokia.carbide.cpp.internal.news.reader.feed.FeedManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.IFeedCacheChangedlistener;

import junit.framework.TestCase;

/**
 * Test class for FeedManager.
 *
 */
public class FeedManagerTest extends TestCase implements IFeedCacheChangedlistener {

	private FeedManager feedManager;
	private boolean listenerFired;

	public void feedCacheChanged(boolean alertUser) {
		listenerFired = true;
	}

	protected void setUp() throws Exception {
		super.setUp();
		feedManager = new FeedManager();
		listenerFired = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFeedManager() {
		FeedManager feedManager = new FeedManager();
		assertNotNull(feedManager);
	}

	public void testIsCacheEmpty() {
		assertTrue(feedManager.isCacheEmpty());
		feedManager.loadFeeds();
		List<CarbideSyndFeed> newsFeeds = feedManager.getNewsFeeds();
		if (newsFeeds != null && newsFeeds.size() > 0) {
			assertFalse(feedManager.isCacheEmpty());
		}
	}

	public void testLoadFeeds() {
		try {
			feedManager.loadFeeds();
		} catch (Exception e) {
			fail();
		}
	}

	public void testGetNewsFeeds() {
		List<CarbideSyndFeed> newsFeeds = feedManager.getNewsFeeds();
		assertNotNull(newsFeeds);
		feedManager.loadFeeds();
		newsFeeds = feedManager.getNewsFeeds();
		if (newsFeeds != null && newsFeeds.size() > 0) {
			for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
				CarbideSyndFeed feed = iterator.next();
				assertNotNull(feed);
			}
		}
	}

	public void testGetUnreadEntriesCount() {
		assertTrue(feedManager.getUnreadEntriesCount() == 0);
		feedManager.loadFeeds();
		int actualCount = 0;
		List<CarbideSyndFeed> newsFeeds = feedManager.getNewsFeeds();
		if (newsFeeds != null && newsFeeds.size() > 0) {
			for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
				CarbideSyndFeed feed = iterator.next();
				actualCount += feed.getUnreadEntries().size();
			}
			assertTrue(feedManager.getUnreadEntriesCount() == actualCount);
		}
	}

	public void testGetResourceFeed() {
		CarbideSyndFeed resourceFeed = feedManager.getResourceFeed();
		if (resourceFeed != null && resourceFeed.getEntries().size() > 0) {
			for (Iterator<CarbideSyndEntry> iterator = resourceFeed.getEntries().iterator(); iterator.hasNext();) {
				CarbideSyndEntry entry = iterator.next();
				assertNotNull(entry);
			}
		}
	}

	public void testGetSubscribedNewsFeeds() {
		assertNotNull(feedManager.getSubscribedNewsFeeds());
		feedManager.loadFeeds();
		List<CarbideSyndFeed> newsFeeds = feedManager.getNewsFeeds();
		if (newsFeeds != null && newsFeeds.size() > 0) {
			List<CarbideSyndFeed> subscribedFeeds = feedManager.getSubscribedNewsFeeds();
			if (subscribedFeeds != null && subscribedFeeds.size() > 0) {
				for (Iterator<CarbideSyndFeed> iterator = subscribedFeeds.iterator(); iterator.hasNext();) {
					CarbideSyndFeed feed = iterator.next();
					assertTrue(feed.isSubscribed());
				}
			}
		}
	}

	public void testMarkEntryAsRead() {
		try {
			feedManager.loadFeeds();
			List<CarbideSyndFeed> newsFeeds = feedManager.getNewsFeeds();
			if (newsFeeds != null && newsFeeds.size() > 0) {
				CarbideSyndFeed feed = newsFeeds.get(0);
				List<CarbideSyndEntry> feedEntries = feed.getUnreadEntries();
				while (feedEntries != null && feedEntries.size() > 0) {
					int oldCount = feed.getUnreadEntries().size();
					CarbideSyndEntry feedEntry = feedEntries.get(0);
					feedManager.markEntryAsRead(feedEntries, feedEntry.getTitle());
					int newCount = feed.getUnreadEntries().size();
					assertTrue(newCount < oldCount);
					feedEntries = feed.getUnreadEntries();
				}
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testSaveFeeds() {
		try {
			feedManager.saveFeeds();
			feedManager.loadFeeds();
			feedManager.saveFeeds();
		} catch (Exception e) {
			fail();
		}
	}

	public void testUnreadEntriesCountChanged() {
		FeedCacheManager.addFeedCacheChangedListener(this);
		feedManager.unreadEntriesCountChanged();
		assertTrue(listenerFired);
		FeedCacheManager.removeFeedCacheChangedListener(this);
		listenerFired = false;
		feedManager.unreadEntriesCountChanged();
		assertFalse(listenerFired);
	}

	public void testUpdateFeeds() {
		try {
			feedManager.updateFeeds();
		} catch (Exception e) {
			fail();
		}
	}

}
