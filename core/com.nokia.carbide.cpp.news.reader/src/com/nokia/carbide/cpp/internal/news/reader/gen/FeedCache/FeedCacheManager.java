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

package com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndEntry;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndFeed;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.impl.DateParser;

/**
 * A class to manage local cache of relevant feed information.
 *
 */
public class FeedCacheManager {

	// private data
	private FeedCacheType feedCache;
	private boolean cachedUpdated;
	private static ListenerList<IFeedCacheChangedlistener> listeners = new ListenerList<IFeedCacheChangedlistener>();

	/**
	 * The constructor.
	 */
	public FeedCacheManager () {
		cachedUpdated = false;
	}

	/**
	 * Add a listener to track changes to feed cache.
	 * @param listener - listener to be added
	 */
	public static void addFeedCacheChangedListener(IFeedCacheChangedlistener listener) {
		listeners.add(listener);
	}

	/**
	 * Create the default feed cache.
	 */
	public void createDefaultFeedCache() {
		feedCache = FeedCacheFactory.eINSTANCE.createFeedCacheType();
		FeedsType feeds = FeedCacheFactory.eINSTANCE.createFeedsType();
		EList<FeedType> feedsList = feeds.getFeed();
		FeedType feed = FeedCacheFactory.eINSTANCE.createFeedType();
		feedsList.add(feed);
		feedCache.setFeeds(feeds);
	}

	/**
	 * Clear the local feed cache.
	 */
	public void clearFeedCache() {
		createDefaultFeedCache();
	}

	/**
	 * Notify all the listeners about a change in feed cache.
	 */
	public static void fireFeedCacheChanged(boolean alertUser) {
		for (IFeedCacheChangedlistener l : listeners) {
			l.feedCacheChanged(alertUser);
		}
	}

	/**
	 * Return the cached feed information.
	 * @return cached feed information
	 */
	public FeedCacheType getFeedCache() {
		return feedCache;
	}

	/**
	 * Check whether the local cache is empty.
	 * @return true if local cache is empty; false otherwise
	 */
	public boolean isCacheEmpty() {
		if (feedCache == null) {
			return true;
		}
		if (feedCache.getFeeds().getFeed() == null || feedCache.getFeeds().getFeed().size() == 0) {
			return true;
		}
		if (feedCache.getFeeds().getFeed().size() == 1) {
			EList<FeedType> feedsList = feedCache.getFeeds().getFeed();
			FeedType feed = feedsList.get(0);
			if (feed.getTitle() == null && feed.getLink() == null) {
				return true;
			}
			if (feed.getEntries() == null || feed.getEntries().getEntry().size() == 0) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Check whether the local cache has been updated.
	 * @return true if local cache has been updated; false otherwise
	 */
	public boolean isCacheUpdated() {
		return cachedUpdated;
	}

	/**
	 * Check whether the local cache already contains a feed. 
	 * @param feed - feed to be examined.
	 * @return true if ocal cache already contains a feed; false otherwise
	 */
	public boolean isFeedInCache(CarbideSyndFeed feed) {
		if (feed == null || isCacheEmpty()) {
			return false;
		}

		List<FeedType> cachedFeeds = feedCache.getFeeds().getFeed();
		for (Iterator<FeedType> iterator = cachedFeeds.iterator(); iterator.hasNext();) {
			FeedType cachedFeed = iterator.next();
			if (cachedFeed == null) {
				continue;
			}
			if (cachedFeed.getTitle().equals(feed.getTitle()) && 
				cachedFeed.getLink().equals(feed.getLink())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Load cached feed information from a file.
	 * @param url - URL to the file
	 * @return true on success
	 */
	public boolean loadCacheFromFile(URL url) {
		if (url == null) {
			return false;
		}

		boolean success = true;
		try {
			feedCache = FeedCacheLoader.loadFeedCache(url);
			success = (feedCache != null);
		} catch (URISyntaxException eURI){
			CarbideNewsReaderPlugin.log(eURI);
			success = false;
		} catch (IOException eIO){
			CarbideNewsReaderPlugin.log(eIO);
			success = false;
		}
		return success;
	}

	/**
	 * Load feed information from local cache
	 * @param feedUrl - URL of the feed
	 * @return feed object on success; null otherwise
	 */
	public CarbideSyndFeed loadFeedFromCache(URL feedUrl) {
		if (feedUrl == null || isCacheEmpty()) {
			return null;
		}

		List<FeedType> cachedFeeds = feedCache.getFeeds().getFeed();
		for (Iterator<FeedType> fIterator = cachedFeeds.iterator(); fIterator.hasNext();) {
			FeedType cachedFeed = fIterator.next();
			if (cachedFeed.getLink().equals(feedUrl.toString())) {
				CarbideSyndFeed feed = new CarbideSyndFeed(new SyndFeedImpl());
				if (cachedFeed.getDescription() != null) {
					feed.setDescription(cachedFeed.getDescription());
				}
				feed.setLink(cachedFeed.getLink());
				if (cachedFeed.getPubDate() != null) {
					feed.setPublishedDate(DateParser.parseRFC822(cachedFeed.getPubDate()));
				}
				feed.setTitle(cachedFeed.getTitle());
				feed.setType(cachedFeed.getType());
				feed.setSubscribed(cachedFeed.isSubscribed());

				List<CarbideSyndEntry> feedEntries = new ArrayList<CarbideSyndEntry>();
				feed.setEntries(feedEntries);
				if (cachedFeed.getEntries() != null) {
					EList<EntryType> cachedEntries = cachedFeed.getEntries().getEntry();
					for (Iterator<EntryType> eIterator = cachedEntries.iterator(); eIterator.hasNext();) {
						EntryType cachedEntry = eIterator.next();
						CarbideSyndEntry feedEntry = new CarbideSyndEntry(new SyndEntryImpl());
						if (cachedEntry.getDescription() != null) {
							feedEntry.setDescription(cachedEntry.getDescription());
						}
						feedEntry.setLink(cachedEntry.getLink());
						if (cachedEntry.getPubDate() != null) {
							feedEntry.setPublishedDate(DateParser.parseRFC822(cachedEntry.getPubDate()));
						}
						feedEntry.setRead(cachedEntry.isRead());
						feedEntry.setTitle(cachedEntry.getTitle());
						feedEntries.add(feedEntry);
					}
				}
				
				return feed;
			}
		}	
		return null;
	}

	/**
	 * Remove a listener to track changes feed cache.
	 * @param listener - listener to be removed
	 */
	public static void removeFeedCacheChangedListener(IFeedCacheChangedlistener listener) {
		listeners.remove(listener);
	}

	/**
	 * Save cached feed information to a file.
	 * @param url - URL to the file
	 * @return true on success
	 */
	public boolean saveCacheToFile(URL url) {
		if (url == null) {
			return false;
		}

		boolean success = true;
		try {
			if (feedCache != null) {
				success = FeedCacheLoader.SaveFeedCache(feedCache, url);
			}
		} catch (URISyntaxException eURI){
			CarbideNewsReaderPlugin.log(eURI);
			success = false;
		} catch (IOException eIO){
			CarbideNewsReaderPlugin.log(eIO);
			success = false;
		}
		return success;
	}

	/**
	 * Save feed information to local cache.
	 * @param feed - feed to be written to local cache
	 */
	public void saveFeedToCache(CarbideSyndFeed feed) {
		if (feed == null) {
			return;
		}

		FeedType cachedFeed = FeedCacheFactory.eINSTANCE.createFeedType();
		if (feed.getDescription() != null) {
			cachedFeed.setDescription(feed.getDescription());
		}
		cachedFeed.setLink(feed.getLink());
		if (feed.getPublishedDate() != null) {
			cachedFeed.setPubDate(DateParser.formatRFC822(feed.getPublishedDate()));
		}
		cachedFeed.setSubscribed(feed.isSubscribed());
		cachedFeed.setTitle(feed.getTitle());
		cachedFeed.setType(feed.getType());

		EntriesType entries = FeedCacheFactory.eINSTANCE.createEntriesType();
		EList<EntryType> entryList = entries.getEntry();
		for (Iterator<CarbideSyndEntry> iterator = feed.getEntries().iterator(); iterator.hasNext();) {
			CarbideSyndEntry feedEntry = iterator.next();
			if (feedEntry == null) {
				continue;
			}
			EntryType cachedEntry = FeedCacheFactory.eINSTANCE.createEntryType();
			if (feedEntry.getDescription() != null) {
				cachedEntry.setDescription(feedEntry.getDescription());
			}
			cachedEntry.setLink(feedEntry.getLink());
			if (feedEntry.getPublishedDate() != null) {
				cachedEntry.setPubDate(DateParser.formatRFC822(feedEntry.getPublishedDate()));
			}
			else if (feed.getPublishedDate() != null) {
				cachedEntry.setPubDate(DateParser.formatRFC822(feed.getPublishedDate()));
			}
			cachedEntry.setRead(feedEntry.isRead());
			cachedEntry.setTitle(feedEntry.getTitle());
			entryList.add(cachedEntry);
		}
		cachedFeed.setEntries(entries);

		updateCachedFeed(cachedFeed);
	}

	/**
	 * Set the flag indicating whether the local cached has been updated.
	 * @param value - true/false
	 */
	public void setCacheUpdated(boolean value) {
		this.cachedUpdated = value;
	}

	/**
	 * Set the cached feed information
	 * @param feedCache - incoming cached feed information
	 */
	public void setFeedCache(FeedCacheType feedCache) {
		this.feedCache = feedCache;
	}

	/**
	 * Synchronize a feed with the local cache.
	 * @param feed - feed to be synchronized
	 */
	public void syncFeedWithCache(CarbideSyndFeed feed) {
		if (feed == null || isCacheEmpty()) {
			return;
		}

		List<FeedType> cachedFeeds = feedCache.getFeeds().getFeed();
		for (Iterator<FeedType> iterator = cachedFeeds.iterator(); iterator.hasNext();) {
			FeedType cachedFeed = iterator.next();
			if (cachedFeed.getLink().equals(feed.getLink())) {
				feed.setSubscribed(cachedFeed.isSubscribed());
				syncFeedEntriesWithCache(feed.getEntries(), cachedFeed.getEntries().getEntry());
				break;
			}
		}
	}

	/**
	 * Check whether a cached feed is out of sync with the incoming feed.
	 * @param cachedFeed - cached feed
	 * @param feed - feed to be synchronized with cache 
	 * @return
	 */
	private boolean isCachedFeedOutOfSync(FeedType cachedFeed, FeedType feed) {
		if (cachedFeed == null || feed == null) {
			return false;
		}

		List<EntryType> cachedEntries = cachedFeed.getEntries().getEntry();
		List<EntryType> feedEntries = feed.getEntries().getEntry();
		if (cachedEntries.size() != feedEntries.size()) {
			return true;
		}
		Iterator<EntryType> cIterator = cachedEntries.iterator();
		Iterator<EntryType> fIterator = feedEntries.iterator();
		while (cIterator.hasNext() && fIterator.hasNext()) {
			EntryType cachedEntry = cIterator.next();
			EntryType feedEntry = fIterator.next();
			if (!cachedEntry.getTitle().equals(feedEntry.getTitle())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Synchronize feed entries with locally cached feed entries.
	 * @param feedEntries - list of feed entries
	 * @param cachedEntries - locally cached feed entries
	 */
	private void syncFeedEntriesWithCache(List<CarbideSyndEntry> feedEntries, List<EntryType> cachedEntries) {
		if (feedEntries == null || cachedEntries == null) {
			return;
		}

		for (Iterator<CarbideSyndEntry> fIterator = feedEntries.iterator(); fIterator.hasNext();) {
			CarbideSyndEntry feedEntry = fIterator.next();
			for (Iterator<EntryType> cIterator = cachedEntries.iterator(); cIterator.hasNext();) {
				EntryType cachedEntry = cIterator.next();
				if (feedEntry.getTitle().equals(cachedEntry.getTitle())) {
					feedEntry.setRead(cachedEntry.isRead());
					break;
				}
			}
		}
	}

	/**
	 * Update a cached feed if it already exist, otherwise add it to local cache.
	 * @param newFeed - feed to be added/updated
	 */
	private void updateCachedFeed(FeedType newFeed) {
		if (newFeed == null) {
			return;
		}

		List<FeedType> cachedFeeds = feedCache.getFeeds().getFeed();
		if (isCacheEmpty()) {
			cachedFeeds.clear();
			cachedFeeds.add(newFeed);
			cachedUpdated = true;
		}
		else {
			int index = -1;
			for (Iterator<FeedType> iterator = cachedFeeds.iterator(); iterator.hasNext();) {
				FeedType oldFeed = iterator.next();
				if (oldFeed == null) {
					continue;
				}
				if (oldFeed.getTitle().equals(newFeed.getTitle()) &&
					oldFeed.getLink().equals(newFeed.getLink())) {
					cachedUpdated = isCachedFeedOutOfSync(oldFeed, newFeed);
					index = cachedFeeds.indexOf(oldFeed);
					cachedFeeds.set(index, newFeed);
				}
			}
			if (index == -1) {
				cachedFeeds.add(newFeed);
				cachedUpdated = true;
			}
		}
	}

}
