/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.editor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A simple RSS reader
 * @see http://www.rssboard.org/rss-specification
 */
public class SimpleRSSReader {
	
	public static class Rss {
		private List<Channel> channels = new ArrayList<Channel>();
		
		public List<Channel> getChannels() {
			return channels;
		}
		
		protected void addChannel(Channel channel) {
			channels.add(channel);
		}
	}
	
	public static class Container {
		private String title;
		private URL link;
		private String description;
		private Date pubDate;
		private List<String> categories = new ArrayList<String>();
		
		public String getTitle() {
			return title;
		}

		public URL getLink() {
			return link;
		}
		
		public String getDescription() {
			return description;
		}
		
		public Date getPubDate() {
			return pubDate;
		}
		
		public String[] getCategories() {
			return (String[]) categories.toArray(new String[categories.size()]);
		}
		
		public boolean isValid() {
			return title != null && link != null && description != null;
		}
		
		protected void setField(String element, String value) {
			if (RSSHandler.TITLE.equals(element))
				title = value;
			else if (RSSHandler.LINK.equals(element)) {
				try {
					link = new URL(value);
				} catch (MalformedURLException e) {
					// don't store malformed URLs
					e.printStackTrace();
				}
			}
			else if (RSSHandler.DESCRIPTION.equals(element) || RSSHandler.SUMMARY.equals(element))
				description = value;
			else if (RSSHandler.PUBDATE.equals(element)) {
				try {
					// FIXME parser needs writing!!
					pubDate = DateFormat.getInstance().parse(value);
				} catch (ParseException e) {
					// don't store malformed dates
//					e.printStackTrace();
				}
			}
			else if (RSSHandler.CATEGORY.equals(element))
				categories.add(value);
		}
	}
	
	public static class Channel extends Container {
		List<Item> items = new ArrayList<Item>();
		
		public List<Item> getItems() {
			return items;
		}
		
		protected void addItem(Item item) {
			items.add(item);
		}

		public boolean hasItems() {
			return !items.isEmpty();
		}
	}
	
	public static class Item extends Container {
		private Channel channel;
		
		public Item(Channel channel) {
			this.channel = channel;
		}
		
		public Channel getChannel() {
			return channel;
		}
	}
	
	private static class RSSHandler extends DefaultHandler {

		private static final String CHANNEL = "channel"; //$NON-NLS-1$
		private static final String TITLE = "title"; //$NON-NLS-1$
		private static final String LINK = "link"; //$NON-NLS-1$
		private static final String DESCRIPTION = "description"; //$NON-NLS-1$
		private static final String SUMMARY = "atom:summary"; //$NON-NLS-1$
		private static final String ITEM = "item"; //$NON-NLS-1$
		private static final String PUBDATE = "pubDate"; //$NON-NLS-1$
		private static final String CATEGORY = "category"; //$NON-NLS-1$
		private static final Set<String> charsElements = new HashSet<String>();
		static {
			charsElements.add(TITLE);
			charsElements.add(LINK);
			charsElements.add(DESCRIPTION);
			charsElements.add(SUMMARY);
			charsElements.add(PUBDATE);
			charsElements.add(CATEGORY);
		}

		private Channel curChannel;
		private Item curItem;
		private StringBuffer charsBuf;
		private Rss rss;

		public RSSHandler(Rss rss) {
			this.rss = rss;
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (charsElements.contains(qName)) {
				charsBuf = new StringBuffer();
			}
			if (CHANNEL.equals(qName)) {
				curChannel = new Channel();
			}
			else if (ITEM.equals(qName) && curChannel != null) {
				curItem = new Item(curChannel);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (CHANNEL.equals(qName)) {
				if (curChannel != null && curChannel.isValid() && curChannel.hasItems())
					rss.addChannel(curChannel);
				curChannel = null;
				curItem = null;
			}
			else if (ITEM.equals(qName)) {
				if (curChannel != null && curItem != null && curItem.isValid())
					curChannel.addItem(curItem);
				curItem = null;
			}
			else if (charsBuf != null && curChannel != null) {
				if (curItem != null)
					curItem.setField(qName, charsBuf.toString().trim());
				else
					curChannel.setField(qName, charsBuf.toString().trim());
				charsBuf = null;
			} 
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (charsBuf != null) {
				charsBuf.append(new String(ch, start, length));
			}
		}
		
	}

	
	public static Rss readRSS(URL url) throws SAXException, IOException, ParserConfigurationException {
		Rss rss = new Rss();
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream inputStream = url.openStream();
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(inputStream, new RSSHandler(rss));
		return rss;
	}
}
