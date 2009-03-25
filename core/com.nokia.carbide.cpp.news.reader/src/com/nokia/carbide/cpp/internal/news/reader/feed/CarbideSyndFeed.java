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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * A class to handle a syndicated feed and its related attributes.
 *
 */
public class CarbideSyndFeed  {

	// private data
	private boolean subscribed;
	private boolean isNew;
	private SyndFeed syndFeed;
	private String type;
	private List<CarbideSyndEntry> entries;

	/**
	 * The constructor.
	 * @param feed - a syndicated feed
	 */
	public CarbideSyndFeed(SyndFeed feed) {
		syndFeed = feed;
		createEntries();
	}

	/**
	 * Get the description of a feed.
	 * @return description of a feed
	 */
	public String getDescription() {
		return syndFeed.getDescription();
	}

	/**
	 * Get the list of entries from a feed.
	 * @return list of entries from a feed
	 */
	public List<CarbideSyndEntry> getEntries() {
		return entries;
	}

	/**
	 * Get the link of a feed.
	 * @return link of a feed
	 */
	public String getLink() {
		return syndFeed.getLink();
	}

	/**
	 * Get the published date of a feed.
	 * @return published date of a feed
	 */
	public Date getPublishedDate() {
		return syndFeed.getPublishedDate();
	}

	/**
	 * Get the title of a feed.
	 * @return title of a feed
	 */
	public String getTitle() {
		return syndFeed.getTitle();
	}

	/**
	 * Get the type of a feed.
	 * @return type of a feed
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get the list of unread entries from a feed.
	 * @return list of unread entries from a feed
	 */
	public List<CarbideSyndEntry> getUnreadEntries() {
		List<CarbideSyndEntry> unreadEntries = new ArrayList<CarbideSyndEntry>();
		for (Iterator<CarbideSyndEntry> iterator = entries.iterator(); iterator.hasNext();) {
			CarbideSyndEntry entry = iterator.next();
			if (!entry.isRead()) {
				unreadEntries.add(entry);
			}
		}
		return unreadEntries;
	}

	/**
	 * Check whether a feed is new and currently not stored in local cache.
	 * @return true if a feed is new; false otherwise.
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * Get the subscription status of a feed.
	 * @return subscription status of a feed
	 */
	public boolean isSubscribed() {
		return subscribed;
	}

	/**
	 * Specify the description of a feed.
	 * @param description - description of a feed
	 */
	public void setDescription(String description) {
		syndFeed.setDescription(description);
	}

	/**
	 * Specify the list of entries from a feed.
	 * @param entryList - list of feed entries
	 */
	public void setEntries(List<CarbideSyndEntry> entryList) {
		this.entries = entryList;
	}

	/**
	 * Specify whether a feed is new.
	 * @param value - boolean indicating whether a feed is new.
	 */
	public void setIsNew(boolean value) {
		this.isNew = value;
	}

	/**
	 * Specify the link of a feed.
	 * @param link - link of a feed
	 */
	public void setLink(String link) {
		syndFeed.setLink(link);
	}

	/**
	 * Specify the published date of a feed.
	 * @param publishedDate - published date of a feed
	 */
	public void setPublishedDate(Date publishedDate) {
		syndFeed.setPublishedDate(publishedDate);
	}

	/**
	 * Specify the subscription status of a feed.
	 * @param subscribed - subscription status of a feed
	 */
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	/**
	 * Specify the title of a feed.
	 * @param title - title of a feed
	 */
	public void setTitle(String title) {
		syndFeed.setTitle(title);
	}

	/**
	 * Specify the type of a feed.
	 * @param type - type of a feed
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Create a list of feed entry objects.
	 */
	@SuppressWarnings("unchecked")
	private void createEntries() {
		entries = new ArrayList<CarbideSyndEntry>();
		if (syndFeed == null || syndFeed.getEntries() == null) {
			return;
		}

		List<SyndEntry> syndEntries = (List<SyndEntry>)syndFeed.getEntries();
		for (Iterator<SyndEntry> iterator = syndEntries.iterator(); iterator.hasNext();) {
			SyndEntry syndEntry = iterator.next();
			CarbideSyndEntry entry = createEntry(syndEntry);
			if (entry != null) {
				entries.add(entry);
			}
		}
		Collections.sort(entries);
	}

	/**
	 * Create a feed entry object from a syndicated feed entry.
	 * @param syndEntry - syndicated feed entry
	 * @return a feed entry object on success; null otherwise
	 */
	private CarbideSyndEntry createEntry(SyndEntry syndEntry) {
		if (syndEntry == null) {
			return null;
		}

		CarbideSyndEntry entry = new CarbideSyndEntry(syndEntry);
		return entry;
	}

}
