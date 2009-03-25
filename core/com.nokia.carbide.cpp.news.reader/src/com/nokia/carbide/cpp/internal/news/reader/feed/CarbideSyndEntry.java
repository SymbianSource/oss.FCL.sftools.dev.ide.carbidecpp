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

import java.util.Date;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * A class to handle a syndicated feed entry and its related attributes.
 *
 */
public class CarbideSyndEntry implements Comparable<CarbideSyndEntry> {

	// private data
	private boolean read;
	private SyndEntry syndEntry;

	/**
	 * The constructor.
	 * @param entry - a syndicated feed entry
	 */
	public CarbideSyndEntry(SyndEntry entry) {
		syndEntry = entry;
		read = false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CarbideSyndEntry compareEntry) {
		Date date1 = getPublishedDate();
		Date date2 = compareEntry.getPublishedDate();
		if (date1 == null || date2 == null) {
			return 0;
		}
		if (date1.before(date2)) {
			return 1;
		}
		else if (date1.equals(date2)) {
			return 0;
		}
		else { // date1.after(date2)
			return -1;
		}
	}

	/**
	 * Get the description of a feed entry if available.
	 * @return description of a feed entry as string if available; null otherwise
	 */
	public String getDescription() {
		if (syndEntry.getDescription() != null) {
			return syndEntry.getDescription().getValue();
		}
		return null;
	}

	/**
	 * Get the link of a feed entry.
	 * @return link of a feed entry
	 */
	public String getLink() {
		return syndEntry.getLink();
	}

	/**
	 * Get the published date of a feed entry if available.
	 * @return published date of a feed entry if available
	 */
	public Date getPublishedDate() {
		return syndEntry.getPublishedDate();
	}

	/**
	 * Get the title of a feed entry. 
	 * @return title of a feed entry
	 */
	public String getTitle() {
		return syndEntry.getTitle();
	}

	/**
	 * Get the read/unread status of a feed entry.
	 * @return true if a feed entry had been read, false otherwise
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * Specify the description of a feed entry.
	 * @param description - description of a feed entry
	 */
	public void setDescription(String description) {
		SyndContent content = new SyndContentImpl();
		content.setValue(description);
		syndEntry.setDescription(content);
	}

	/**
	 * Specify the link of a feed entry.
	 * @param link - link of a feed entry
	 */
	public void setLink(String link) {
		syndEntry.setLink(link);
	}

	/**
	 * Specify the published date of a feed entry.
	 * @param publishedDate - published date of a feed entry
	 */
	public void setPublishedDate(Date publishedDate) {
		syndEntry.setPublishedDate(publishedDate);
	}

	/**
	 * Specify the read/unread status of a feed entry.
	 * @param read - read/unread status
	 */
	public void setRead(boolean read) {
		this.read = read;
	}

	/**
	 * Specify the title of a feed entry.
	 * @param title - title of a feed entry
	 */
	public void setTitle(String title) {
		syndEntry.setTitle(title);
	}

}
