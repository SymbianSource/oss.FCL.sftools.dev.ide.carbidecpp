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
package com.nokia.carbide.internal.discovery.ui.extension;

import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.carbide.internal.discovery.ui.editor.SimpleRSSReader;
import com.nokia.carbide.internal.discovery.ui.editor.SimpleRSSReader.Channel;
import com.nokia.carbide.internal.discovery.ui.editor.SimpleRSSReader.Item;
import com.nokia.carbide.internal.discovery.ui.editor.SimpleRSSReader.Rss;

public abstract class AbstractRSSPortalPageLayer extends AbstractBrowserPortalPageLayer {

	private static final List<Pattern> EXCLUDE_TAG_PATTERNS = new ArrayList<Pattern>();

	private static final String[] EXCLUDE_TAGS = { 	
		"object", //$NON-NLS-1$
		"param", //$NON-NLS-1$
		"embed", //$NON-NLS-1$
		"script", //$NON-NLS-1$
		"img" //$NON-NLS-1$
	};
	
	private static final String[] EXCLUDE_FORMAT_TAGS = {
		"b", //$NON-NLS-1$
		"i", //$NON-NLS-1$
		"em", //$NON-NLS-1$
		"strong", //$NON-NLS-1$
		"tt", //$NON-NLS-1$
		"s", //$NON-NLS-1$
		"strike", //$NON-NLS-1$
		"p" //$NON-NLS-1$
	};
	
	static {
		for (String tagString : EXCLUDE_TAGS) {
			StringBuilder sb = new StringBuilder();
			sb.append('<');
			sb.append(tagString);
			sb.append(" .*</"); //$NON-NLS-1$
			sb.append(tagString);
			sb.append('>');
			EXCLUDE_TAG_PATTERNS.add(Pattern.compile(sb.toString(),
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE));
		}
		for (String tagString : EXCLUDE_TAGS) {
			StringBuilder sb = new StringBuilder();
			sb.append('<');
			sb.append(tagString);
			sb.append(" .*/>"); //$NON-NLS-1$
			EXCLUDE_TAG_PATTERNS.add(Pattern.compile(sb.toString(),
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE));
		}
		for (String formatTag : EXCLUDE_FORMAT_TAGS) {
			StringBuilder sb = new StringBuilder();
			sb.append("</?"); //$NON-NLS-1$
			sb.append(formatTag);
			sb.append('>');
			EXCLUDE_TAG_PATTERNS.add(Pattern.compile(sb.toString(),
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE));
			
		}
	}
	
	private static final String HTML_BODY_HEADER = "<html><head><title></title><style type=\"text/css\">div.item {font-family : sans-serif; font-size : 12px; margin-bottom : 16px;} div.itemBody {padding-top : 3px; padding-bottom : 3px;} div.itemInfo {background-color : #EEEEEE; color : #333333;} div.feedflare {display: none;} a.itemTitle {font-size : 12px; font-weight : bold;} a.markItemRead {font-size : 10px; color : #333333;}</style></head><body>"; //$NON-NLS-1$
	private static final String HTML_BODY_FOOTER = "</body></html>"; //$NON-NLS-1$
	private static final int MAX_ELEM_LEN = 256;
	
	private Rss rss;

	@Override
	public void init() {
		Activator.runInUIThreadWhenProxyDataSet(browser, new Runnable() {
			@Override
			public void run() {
				actionBar.hookBrowser();
				readRSS();
				actionBar.update();
				browser.setFocus();
			}
		});
	}

	protected void readRSS() {
		URL url = getURL();
		if (url != null) {
			try {
				rss = SimpleRSSReader.readRSS(url);
				displayRSS();
			} catch (Exception e) {
				Activator.logError(MessageFormat.format(Messages.AbstractRSSPortalPageLayer_RSSReadError, url), e);
			}
		}
	}
	
	protected void displayRSS() {
		StringBuffer buf = new StringBuffer();
		buf.append(HTML_BODY_HEADER);
		for (Channel channel : rss.getChannels()) {
			buf.append("<div class=\"channel\"><a class=\"channelName\" href=\""); //$NON-NLS-1$
			buf.append(channel.getLink().toString());
			buf.append("\">"); //$NON-NLS-1$
			buf.append(clean(channel.getTitle()));
			buf.append("</a>"); //$NON-NLS-1$
			buf.append("<div class=\"channelBody\">"); //$NON-NLS-1$
			buf.append(clean(channel.getDescription()));
			buf.append("</div><br>"); //$NON-NLS-1$
			buf.append("<ul>"); //$NON-NLS-1$
			for (Item item : channel.getItems()) {
				buf.append("<li><div class=\"item\"><a class=\"itemTitle\" href=\""); //$NON-NLS-1$
				buf.append(item.getLink().toString());
				buf.append("\">"); //$NON-NLS-1$
				buf.append(clean(item.getTitle()));
				buf.append("</a>"); //$NON-NLS-1$
				buf.append("<div class=\"itemBody\">"); //$NON-NLS-1$
				Date date = item.getPubDate();
				if (date != null) {
					String dateString = DateFormat.getDateTimeInstance().format(date);
					buf.append(dateString);
					buf.append("<br>");
				}
				buf.append(clean(item.getDescription()));
				buf.append("</div></li>"); //$NON-NLS-1$
			}
			buf.append("</ul>"); //$NON-NLS-1$
		}
		buf.append(HTML_BODY_FOOTER);
		browser.setText(buf.toString());
	}

	private String clean(String s) {
		String output = s;
		for (Pattern pattern : EXCLUDE_TAG_PATTERNS) {
			output = pattern.matcher(output).replaceAll(""); //$NON-NLS-1$
		}
		output = output.length() > MAX_ELEM_LEN ? output.substring(0, MAX_ELEM_LEN) : output;
		return output;
	}

	@Override
	public void dispose() {
	}

	@Override
	protected Set<IAction> makeActions() {
		Set<IAction> actions = new LinkedHashSet<IAction>();
		for (IAction action : super.makeActions()) {
			if (action.getText().equals(Messages.AbstractBrowserPortalPage_RefreshLabel)) {
				IAction a = new Action(Messages.AbstractBrowserPortalPage_RefreshLabel) {
					@Override
					public void run() {
						if (browser != null) {
							if (browserHasURL())
								browser.refresh();
							else
								readRSS();
							actionBar.update();
						}
					}
					
					@Override
					public boolean isEnabled() {
						return browser != null ? !actionBar.isLoading() : false;
					}
				};
				actions.add(a);
			}
			else
				actions.add(action);
		}

		actions.add(new Action(Messages.AbstractRSSPortalPageLayer_ReturnToFeedLabel) {
			@Override
			public void run() {
				displayRSS();
				actionBar.update();
			}

			@Override
			public boolean isEnabled() {
				return browserHasURL();
			}
		});
		
		return actions;
	}

}
