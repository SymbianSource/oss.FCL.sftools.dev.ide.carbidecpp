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

package com.nokia.carbide.cpp.internal.news.reader.feed;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.service.datalocation.Location;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoConstants;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedInfoManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.FeedType;
import com.nokia.carbide.cpp.internal.news.reader.ui.NewsPreferenceConstants;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;

/**
 * A class to manage feeds for the Carbide.c++ news reader.
 *
 */
public class FeedManager {

	// public data
	public static final String FEED_CACHE_FILE_NAME = "carbide.c++.news.xml";
	public static final String PROPERTIES_FILE_LOCATION = "configuration/server.properties";

	// private data
	private FeedCacheManager feedCacheManager;
	private FeedInfoManager feedListingManager;
	private List<CarbideSyndFeed> newsFeeds;
	private CarbideSyndFeed resourceFeed;
	private Timer updateTimer;

	/**
	 * Private class to handle scheduled feeds updates.
	 *
	 */
	private class UpdateTimerTask extends TimerTask {
		/*
		 * (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		public void run(){
			CarbideNewsReaderPlugin.updateFeeds();
		}
	}

	/**
	 * The constructor.
	 */
	public FeedManager() {
		feedCacheManager = new FeedCacheManager();
		feedListingManager = new FeedInfoManager();
		newsFeeds = new ArrayList<CarbideSyndFeed>();
	}

	public boolean isCacheEmpty() {
		return feedCacheManager.isCacheEmpty();
	}

	/**
	 * Load feeds listed in the news feeds listing file.
	 */
	@SuppressWarnings("static-access")
	public void loadFeeds() {
		try {
			if (!loadFeedListing()) {
				return;
			}

			if (feedCacheManager.isCacheEmpty()) {
				loadFeedCache();
			}
			clearFeeds();
			updateCache();
			feedCacheManager.fireFeedCacheChanged(feedCacheManager.isCacheUpdated());
			feedCacheManager.setCacheUpdated(false);
		} catch (Exception e) {
			CarbideNewsReaderPlugin.log(e);
		}		
	}

	/**
	 * Retrieve all the currently available news feeds.
	 * @return list of feed objects
	 */
	public List<CarbideSyndFeed> getNewsFeeds() {
		validateFeeds();
		return newsFeeds;
	}

	/**
	 * Retrieve a property string from the news reader properties file.
	 * @param key - key used to identify the property string
	 * @return property string if found; null otherwise
	 */
	public String getProperty(String key) {
		try {
			Location installLocation = Platform.getInstallLocation();
			if (installLocation == null) {
				return null;
			}

			URL url = installLocation.getURL();
			if (url == null) {
				return null;
			}

			IPath path = new Path(url.getPath());
			path = path.append(PROPERTIES_FILE_LOCATION);
			File file = path.toFile();
			InputStream is = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(is);
			is.close();
			String result = (String)properties.get(key);
			return result;
		} catch (Exception e) {
			CarbideNewsReaderPlugin.log(e);
		}		

		return null;
	}

	/**
	 * Retrieve the resource feed.
	 * @return resource feed object
	 */
	public CarbideSyndFeed getResourceFeed() {
		validateFeeds();
		return resourceFeed;
	}

	public List<CarbideSyndFeed> getSubscribedNewsFeeds() {
		validateFeeds();
		List<CarbideSyndFeed> subscribedFeeds = new ArrayList<CarbideSyndFeed>();
		for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
			CarbideSyndFeed feed = iterator.next();
			if (feed.isSubscribed()) {
				subscribedFeeds.add(feed);
			}
		}
		return subscribedFeeds;
	}

	/**
	 * Return the total number of unread entries from subscribed news feeds.
	 * @return total number of unread entries from subscribed news feeds
	 */
	public int getUnreadEntriesCount() {
		int count = 0;
		for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
			CarbideSyndFeed feed = iterator.next();
			if (feed.isSubscribed()) {
				count += feed.getUnreadEntries().size();
			}
		}
		return count;
	}

	/**
	 * Remove an entry from a feed.
	 * @param feed - feed object in question
	 * @param entryTitle - title of the entry to be removed
	 */
	public void removeNewsFeedEntry(List<CarbideSyndEntry> entries, String entryTitle) {
		if (entries == null || entryTitle == null) {
			return;
		}

		for (Iterator<CarbideSyndEntry> iterator = entries.iterator(); iterator.hasNext();) {
			CarbideSyndEntry entry = iterator.next();
			String title = entry.getTitle();
			title = title.replaceAll("\n", "");
			if (title.equals(entryTitle)) {
				entry.setRead(true);
				break;
			}
		}
		
	}

	/**
	 * Reset the update timer.
	 */
	public void resetUpdateTimer() {
		if (updateTimer == null) {
			updateTimer = new Timer();
		}
		long updateInterval = getUpdateInterval();
		UpdateTimerTask updateTask = new UpdateTimerTask();
		updateTimer.schedule(updateTask, updateInterval);
	}

	/**
	 * Save relevant feed information to local cache.
	 */
	public void saveFeeds() {
		feedCacheManager.clearFeedCache();
		for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
			CarbideSyndFeed feed = iterator.next();
			feedCacheManager.saveFeedToCache(feed);
		}
		feedCacheManager.saveFeedToCache(resourceFeed);
		saveFeedCache();
	}

	/**
	 * Perform any necessary actions when the total number of unread entries from 
	 * subscribed news feeds has changed.
	 */
	@SuppressWarnings("static-access")
	public void unreadEntriesCountChanged() {
		feedCacheManager.fireFeedCacheChanged(false);
	}

	/**
	 * Update all news and resource feeds.
	 */
	@SuppressWarnings("static-access")
	public void updateFeeds() {
		try {
			if (!loadFeedListing()) {
				return;
			}

			clearFeeds();
			updateCache();
			feedCacheManager.fireFeedCacheChanged(feedCacheManager.isCacheUpdated());
			feedCacheManager.setCacheUpdated(false);
		} catch (Exception e) {
			CarbideNewsReaderPlugin.log(e);
		}		
	}

	/**
	 * Add a news feed. The news reader can have multiple news feeds.
	 * @param feed - incoming news feed
	 * @return true on success
	 */
	private boolean addNewsFeed(CarbideSyndFeed feed) {
		if (feed == null) {
			return false;
		}

		if (feed != null ) {
			boolean feedExist = false;
			for (Iterator<CarbideSyndFeed> iterator = newsFeeds.iterator(); iterator.hasNext();) {
				CarbideSyndFeed aFeed = iterator.next();
				if (aFeed.getTitle().equals(feed.getTitle()) &&
					aFeed.getLink().equals(feed.getLink())) {
					int index = newsFeeds.indexOf(aFeed);
					newsFeeds.set(index, feed);
					feedExist = true;
				}
			}

			if (!feedExist) {
				feed.setType(FeedInfoConstants.FEED_TYPE_NEWS);
				newsFeeds.add(feed);
			}
			return true;
		}
		return false;
	}

	/**
	 * Add a resource feed. The news reader can only have one resource feed.
	 * @param feed - incoming resource feed
	 * @return true on success
	 */
	private boolean addResourceFeed(CarbideSyndFeed feed) {
		if (feed == null) {
			return false;
		}

		resourceFeed = feed;
		if (resourceFeed != null) {
			resourceFeed.setType(FeedInfoConstants.FEED_TYPE_RESOURCE);
			return true;
		}
		return false;
	}

	/**
	 * Clear all news and resource feeds.
	 */
	private void clearFeeds() {
		newsFeeds.clear();
		resourceFeed = null;
	}

	/**
	 * Feed feed content from its server.
	 * @param feedUrl - URL of the feed
	 * @return feed object on success; null otherwise
	 */
	private CarbideSyndFeed fetchFeed(URL feedUrl) throws Exception {
		if (feedUrl == null) {
			return null;
		}

		FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
		FeedFetcher fetcher = new HttpURLFeedFetcher(feedInfoCache);
		SyndFeed sFeed = fetcher.retrieveFeed(feedUrl);
		CarbideSyndFeed feed = new CarbideSyndFeed(sFeed);
		if (feed != null) {
			feed.setSubscribed(true);
			feed.setLink(feedUrl.toString());
			return feed;
		}
		return null;
	}

	/**
	 * Get the default local feeds cache file.
	 * @return default local feeds cache
	 */
	private File getDefaultFeedCacheFile() {
		String location = System.getProperty("user.home") + File.separator + 
						  FEED_CACHE_FILE_NAME;
		File file = new File(location);
		return file;
	}

	/**
	 * Get the update interval from news reader preferences.
	 * @return update interval
	 */
	private long getUpdateInterval() {
		IPreferenceStore store = CarbideNewsReaderPlugin.getPrefsStore();
		long interval = store.getLong(NewsPreferenceConstants.UPDATE_INTERVAL) * 1000 * 60 * 60;
		return interval;
	}

	/**
	 * Load feed information from local cache.
	 */
	private void loadFeedCache() throws Exception {
		File file = getDefaultFeedCacheFile();
		URL url = file.toURL();
		if (file.exists()) {
			if (url != null) {
				feedCacheManager.loadCacheFromFile(url);
			}
		}
		else {
			file.createNewFile();
			feedCacheManager.createDefaultFeedCache();
			feedCacheManager.saveCacheToFile(url);
		}
	}

	/**
	 * Load feed information from feed listing file. 
	 * @return true on success; false otherwise
	 */
	private boolean loadFeedListing() throws Exception {
		URL url = feedListingManager.getFeedInfoFileURL();
		if (url != null) {
			return feedListingManager.loadFeedInfo(url);
		}
		return false;
	}

	/**
	 * Save feed information to local cache.
	 */
	private void saveFeedCache() {
		try {
			File file = getDefaultFeedCacheFile();
			URL url = file.toURL();
			if (file.exists()) {
				if (url != null) {
					feedCacheManager.saveCacheToFile(url);
				}
			}
			else {
				file.createNewFile();
				feedCacheManager.saveCacheToFile(url);
			}
		} catch (Exception e) {
			CarbideNewsReaderPlugin.log(e);
		}		
	}

	/**
	 * Update local cache of feeds.
	 */
	private void updateCache() throws Exception {
		List<FeedType> feedInfoList = feedListingManager.getFeedInfo().getFeeds().getFeed();
		for (Iterator<FeedType> iterator = feedInfoList.iterator(); iterator.hasNext();) {
			FeedType feedInfo = iterator.next();
			URL feedUrl = new URL(feedInfo.getUrl());
			if (feedUrl != null) {
				CarbideSyndFeed feed = fetchFeed(feedUrl);
				if (feed != null) {
					if (feedCacheManager.isFeedInCache(feed)) {
						feedCacheManager.syncFeedWithCache(feed);
					}
					else {
						feed.setIsNew(true);
					}
					feedCacheManager.saveFeedToCache(feed);

					if (FeedInfoManager.isNewsFeed(feedInfo)) {
						if (!addNewsFeed(feed)) {
							break;
						}
					}
					else
					if (FeedInfoManager.isResourceFeed(feedInfo)) {
						if (!addResourceFeed(feed)) {
							break;
						}
					}
				}
			}
		}
		// reset the update timer after each cache update
		resetUpdateTimer();
	}

	/**
	 * Check news and resource feeds to make sure they are valid.
	 * Reload feeds from cache if necessary.
	 */
	private void validateFeeds() {
		if (newsFeeds == null || newsFeeds.size() == 0 || resourceFeed == null) {
			try {
				if (!loadFeedListing()) {
					return;
				}

				loadFeedCache();
				List<FeedType> feedInfoList = feedListingManager.getFeedInfo().getFeeds().getFeed();
				for (Iterator<FeedType> iterator = feedInfoList.iterator(); iterator.hasNext();) {
					FeedType feedInfo = iterator.next();
					URL feedUrl = new URL(feedInfo.getUrl());
					CarbideSyndFeed feed = feedCacheManager.loadFeedFromCache(feedUrl);
					if (feed != null) {
						if (FeedInfoManager.isNewsFeed(feedInfo)) {
							if (!addNewsFeed(feed)) {
								break;
							}
						}
						else
						if (FeedInfoManager.isResourceFeed(feedInfo)) {
							if (!addResourceFeed(feed)) {
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				CarbideNewsReaderPlugin.log(e);
			}		
		}
	}

}
