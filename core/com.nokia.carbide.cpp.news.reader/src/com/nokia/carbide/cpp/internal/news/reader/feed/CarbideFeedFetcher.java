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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.core.net.proxy.IProxyData;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.fetcher.impl.SyndFeedInfo;
import com.sun.syndication.io.FeedException;

/**
 * A class to retrieve feed via HTTP connection. 
 *
 */
public class CarbideFeedFetcher extends HttpURLFeedFetcher {

	/**
	 * Constructor to enable CarbideFeedFetcher without caching feeds.
	 */
	public CarbideFeedFetcher() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sun.syndication.fetcher.impl.HttpURLFeedFetcher#retrieveFeed(java.net.URL)
	 */
	public SyndFeed retrieveFeed(URL feedUrl) throws IllegalArgumentException, IOException, FeedException, FetcherException {
		IProxyData data = CarbideNewsReaderPlugin.getProxyData(feedUrl);
		if (data != null) {
			System.setProperty("http.proxyHost", data.getHost());
			System.setProperty("http.proxyPort", Integer.toString(data.getPort()));
		}
		return super.retrieveFeed(feedUrl);
	}

	/**
	 * Constructor to enable CarbideFeedFetcher to cache feeds
	 * @param feedCache - an instance of the FeedFetcherCache interface
	 */
	public CarbideFeedFetcher(FeedFetcherCache feedInfoCache) {
		super(feedInfoCache);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sun.syndication.fetcher.impl.HttpURLFeedFetcher#setRequestHeaders(java.net.URLConnection, com.sun.syndication.fetcher.impl.SyndFeedInfo)
	 */
	protected void setRequestHeaders(URLConnection connection, SyndFeedInfo syndFeedInfo) {
		super.setRequestHeaders(connection, syndFeedInfo);
		// specify acceptable content types
		connection.setRequestProperty("Accept", "*/xml");
	}

}
