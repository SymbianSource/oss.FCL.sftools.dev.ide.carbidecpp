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
package com.nokia.sdt.utils.drawing;

import org.eclipse.swt.graphics.Point;

import java.util.HashMap;
import java.util.Map;

public class LineBreaker {
	public interface ITextMeasurer {
		Point getStringExtent(String text);
	}
	
	private ITextMeasurer textMeasurer;
	
	// current text, modified during operation
	private String para; 

	public LineBreaker(ITextMeasurer textMeasurer, String para) {
		this.textMeasurer = textMeasurer;
		this.para = para.trim();
	}

	/**
	 * Tell whether all the text has been consumed.
	 * @return
	 */
	public boolean empty() {
		return para.length() == 0;
	}

	/**
	 * Return the next line fitting within the given width.
	 * @param width
	 * @return next line, or null if empty; "" if too narrow
	 */
	public String getNextLine(int width) {
		// do a binary search which bisects the problem space into
		// two parts: lengths for which the text fits, and lengths for which
		// the text is too long.
		
		int min = 1;
		int max = para.length();
		int len = 1;
		String line = null;
		
		// cache metrics so we don't recalculate too much
		Map<String, Integer> segmentsToWidths = new HashMap<String, Integer>();
		
		while (min <= max) {
			len = (min + max) / 2;
			
			int lineWidth = getLineWidth(segmentsToWidths, len);
			if (lineWidth < width) {
				min = len + 1;
			} else if (lineWidth > width) {
				max = len - 1;
			} else
				break;
		}

		// actual fitting width is either len or len-1
		// (the binary search partitions the space into 'fits' and 'not fits')
		if (len > 1 && getLineWidth(segmentsToWidths, len) > width)
			len -= 1;
		
		// consume as much space as possible
		while (len < para.length() && Character.isWhitespace(para.charAt(len)))
			len++;
		
		int actualLen = len;

		// Ensure the line conforms to wrapping expectations:
		// if not truncating, stop on or after whitespace, else
		// stop one char short to give room for '...'
		if (actualLen < para.length()) {
			while (actualLen > 0 && !Character.isWhitespace(para.charAt(actualLen - 1)))
				actualLen--;
			
			// if there are no spaces, then just break in the middle of a word
			if (actualLen == 0)
				actualLen = len;
		}
		line = para.substring(0, actualLen);

		// update iterator
		para = para.substring(actualLen);

		return line;
	}

	/**
	 * Get the length of the prefix of the current text, caching results to avoid
	 * redundant text layout calculation.
	 * @param frc
	 * @param line
	 * @return
	 */
	private int getLineWidth(Map<String, Integer> stringsToWidths, int len) {
		String line = para.substring(0, len).trim();
		Integer width = stringsToWidths.get(line);
		if (width == null) {
			int lineWidth;
			if (line.length() == 0) {
				// another not-so-smart API that doesn't accept empty strings
				lineWidth = 0;
			} else {
				Point size = textMeasurer.getStringExtent(line);
				lineWidth = size.x;
			}
			width = new Integer(lineWidth);
			stringsToWidths.put(line, width);
		}
		return width.intValue();
	}
}