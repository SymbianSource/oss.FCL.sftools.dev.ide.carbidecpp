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

package com.nokia.carbide.cpp.internal.news.reader.editor;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndEntry;
import com.nokia.carbide.cpp.internal.news.reader.feed.CarbideSyndFeed;

/**
 * Label provider for the Carbide.c++ news page.
 *
 */
public class NewsPageLabelProvider extends LabelProvider implements
		ITableColorProvider, ITableFontProvider {

	// private content
	private int labelColorID;

	/**
	 * The constructor.
	 * @param colorID - constant for the matching standard color
	 */
	public NewsPageLabelProvider(int colorID) {
		labelColorID = colorID;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableColorProvider#getBackground(java.lang.Object, int)
	 */
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableColorProvider#getForeground(java.lang.Object, int)
	 */
	public Color getForeground(Object element, int columnIndex) {
		Color color = Display.getDefault().getSystemColor(labelColorID);
		if (element instanceof CarbideSyndFeed) {
			CarbideSyndFeed feed = (CarbideSyndFeed)element;
			if (feed.isNew()) {
				color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
				feed.setIsNew(false);
			}
		}
		return color;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableFontProvider#getFont(java.lang.Object, int)
	 */
	public Font getFont(Object element, int columnIndex) {
		Font font = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
		return font;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof CarbideSyndFeed) {
			CarbideSyndFeed feed = (CarbideSyndFeed)element;
			String text = "";
			int count = feed.getUnreadEntries().size();
			if (feed.getTitle().startsWith(NewsPage.NEWS_SUMMARY_TITLE)) {
				text = NewsPage.NEWS_SUMMARY_TITLE + " (" + count + ")";
			}
			else {
				text = feed.getTitle() + " (" + count + ")";
			}
			return text;
		}
		else if (element instanceof CarbideSyndEntry) {
			CarbideSyndEntry entry = (CarbideSyndEntry)element;
			return entry.getTitle();
		}
		else {
			return super.getText(element);
		}
	}

}
