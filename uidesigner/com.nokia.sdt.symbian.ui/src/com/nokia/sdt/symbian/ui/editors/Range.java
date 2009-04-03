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
/**
 * 
 */
package com.nokia.sdt.symbian.ui.editors;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Range {
	public int minimum, maximum;
	public String separatorText;
	public Range(int min, int max, String separator) {
		this.minimum = min;
		this.maximum = max;
		this.separatorText = separator;
	}
	public String toString() {
		return minimum + separatorText + maximum;
	}
	
	static public Range parseRange(String time) throws ParseException {
		Pattern pattern = Pattern.compile("(-?\\d+)\\s*(\\D+?)\\s*(-?\\d+)"); //$NON-NLS-1$
		Matcher matcher = pattern.matcher(time);
		if (matcher.matches()) {
			int minimum = Integer.parseInt(matcher.group(1)); 
			int maximum = Integer.parseInt(matcher.group(3));
			if (minimum > maximum)
				return new Range(maximum, minimum, matcher.group(2));
			else
				return new Range(minimum, maximum, matcher.group(2));
		} else {
			throw new ParseException(Messages.getString("RangeEditorFactory.InvalidRangeSpecification"), 0); //$NON-NLS-1$
		}
	}
}