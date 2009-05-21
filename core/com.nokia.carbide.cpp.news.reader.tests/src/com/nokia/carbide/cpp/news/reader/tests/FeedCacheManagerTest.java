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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndEntry;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndFeed;
import com.nokia.carbide.cpp.internal.news.reader.feed.FeedManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheFactory;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedType;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.IFeedCacheChangedlistener;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

import junit.framework.TestCase;

public class FeedCacheManagerTest extends TestCase implements IFeedCacheChangedlistener {

	private FeedCacheManager feedCacheManager;
	private boolean alert;
	private boolean listenerFired;

	public void feedCacheChanged(boolean alertUser) {
		alert = alertUser;
		listenerFired = true;
	}

	private File getDefaultFeedCacheFile() {
		String location = System.getProperty("user.home") + File.separator + 
						  FeedManager.FEED_CACHE_FILE_NAME;
		File file = new File(location);
		return file;
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		feedCacheManager = new FeedCacheManager();
		alert = false;
		listenerFired = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFeedCacheManager() {
		FeedCacheManager cacheManager = new FeedCacheManager();
		assertNotNull(cacheManager);
	}

	@SuppressWarnings("static-access")
	public void testFeedCacheChangedListener() {
		feedCacheManager.addFeedCacheChangedListener(this);
		feedCacheManager.fireFeedCacheChanged(true);
		assertTrue(alert);
		assertTrue(listenerFired);
		feedCacheManager.fireFeedCacheChanged(false);
		assertFalse(alert);
		assertTrue(listenerFired);
		feedCacheManager.removeFeedCacheChangedListener(this);
		alert = false;
		listenerFired = false;
		feedCacheManager.fireFeedCacheChanged(true);
		assertFalse(alert);
		assertFalse(listenerFired);
	}

	public void testCreateDefaultFeedCache() {
		feedCacheManager.createDefaultFeedCache();
		FeedCacheType feedCache = feedCacheManager.getFeedCache();
		assertNotNull(feedCache);
		assertNotNull(feedCache.getFeeds());
	}

	public void testClearFeedCache() {
		feedCacheManager.clearFeedCache();
		FeedCacheType feedCache = feedCacheManager.getFeedCache();
		assertNotNull(feedCache);
		assertNotNull(feedCache.getFeeds());
	}

	public void testGetFeedCache() {
		assertNull(feedCacheManager.getFeedCache());
		feedCacheManager.createDefaultFeedCache();
		assertNotNull(feedCacheManager.getFeedCache());
	}

	public void testIsCacheEmpty() {
		assertTrue(feedCacheManager.isCacheEmpty());
		feedCacheManager.createDefaultFeedCache();
		assertTrue(feedCacheManager.isCacheEmpty());
		feedCacheManager.clearFeedCache();
		assertTrue(feedCacheManager.isCacheEmpty());
	}

	public void testIsCacheUpdated() {
		assertFalse(feedCacheManager.isCacheUpdated());
		feedCacheManager.setCacheUpdated(true);
		assertTrue(feedCacheManager.isCacheUpdated());
		feedCacheManager.setCacheUpdated(false);
		assertFalse(feedCacheManager.isCacheUpdated());
	}

	public void testLoadCacheFromFile() {
		assertFalse(feedCacheManager.loadCacheFromFile(null));
		try {
			File file = getDefaultFeedCacheFile();
			URL url = file.toURL();
			if (feedCacheManager.loadCacheFromFile(url)) {
				assertNotNull(feedCacheManager.getFeedCache());
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testIsFeedInCache() {
		assertFalse(feedCacheManager.isFeedInCache(null));
		try {
			File file = getDefaultFeedCacheFile();
			URL url = file.toURL();
			if (feedCacheManager.loadCacheFromFile(url)) {
				if (!feedCacheManager.isCacheEmpty()) {
					List<FeedType> feeds = feedCacheManager.getFeedCache().getFeeds().getFeed();
					for (Iterator<FeedType> iterator = feeds.iterator(); iterator.hasNext();) {
						FeedType feed = iterator.next();
						URL feedUrl = new URL(feed.getLink());
						if (feedUrl != null) {
							CarbideSyndFeed syndFeed = feedCacheManager.loadFeedFromCache(feedUrl);
							assertTrue(feedCacheManager.isFeedInCache(syndFeed));
						}
					}
				}
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testLoadFeedFromCache() {
		assertNull(feedCacheManager.loadFeedFromCache(null));
		try {
			File file = getDefaultFeedCacheFile();
			URL url = file.toURL();
			if (feedCacheManager.loadCacheFromFile(url)) {
				if (!feedCacheManager.isCacheEmpty()) {
					List<FeedType> feeds = feedCacheManager.getFeedCache().getFeeds().getFeed();
					for (Iterator<FeedType> iterator = feeds.iterator(); iterator.hasNext();) {
						FeedType feed = iterator.next();
						URL feedUrl = new URL(feed.getLink());
						if (feedUrl != null) {
							assertNotNull(feedCacheManager.loadFeedFromCache(feedUrl));
						}
					}
				}
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testSaveCacheToFile() {
		assertFalse(feedCacheManager.saveCacheToFile(null));
		try {
			File file = getDefaultFeedCacheFile();
			URL url = file.toURL();
			if (feedCacheManager.loadCacheFromFile(url)) {
				assertTrue(feedCacheManager.saveCacheToFile(url));
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testSaveFeedToCache() {
		try {
			feedCacheManager.setFeedCache(null);
			feedCacheManager.saveFeedToCache(null);
			assertNull(feedCacheManager.getFeedCache());
			File file = getDefaultFeedCacheFile();
			URL url = file.toURL();
			if (feedCacheManager.loadCacheFromFile(url)) {
				SyndFeed syndFeed = new SyndFeedImpl();
				CarbideSyndFeed feed = new CarbideSyndFeed(syndFeed);
				List<CarbideSyndEntry> entries = new ArrayList<CarbideSyndEntry>();
				SyndEntry syndEntry = new SyndEntryImpl();
				CarbideSyndEntry entry = new CarbideSyndEntry(syndEntry);
				entries.add(entry);
				feed.setEntries(entries);
				feed.setDescription("A feed for testing feed cache manager.");
				feed.setTitle("Test feed");
				feed.setLink("http://www.nokia.com");
				feedCacheManager.saveFeedToCache(feed);
				assertTrue(feedCacheManager.isFeedInCache(feed));
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testSetCacheUpdated() {
		feedCacheManager.setCacheUpdated(true);
		assertTrue(feedCacheManager.isCacheUpdated());
		feedCacheManager.setCacheUpdated(false);
		assertFalse(feedCacheManager.isCacheUpdated());
	}

	public void testSetFeedCache() {
		feedCacheManager.setFeedCache(null);
		assertNull(feedCacheManager.getFeedCache());
		FeedCacheType feedCache = FeedCacheFactory.eINSTANCE.createFeedCacheType();
		feedCacheManager.setFeedCache(feedCache);
		assertNotNull(feedCacheManager.getFeedCache());
		assertTrue(feedCacheManager.getFeedCache().equals(feedCache));
	}

}
