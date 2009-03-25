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
package com.nokia.sdt.sourcegen.doms.rss;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generator for unique RSS NAME values
 * 
 *
 */
public class UniqueRssNameGenerator implements Iterator {
	private char[] letters;
	private int count;

	private final static int LIMIT = 26*26*26*26;
	
	public UniqueRssNameGenerator(String base) {
        
        Pattern patt = Pattern.compile("([^A-Z])"); //$NON-NLS-1$
        Matcher match = patt.matcher(base.toUpperCase());
        String rssName = match.replaceAll(""); //$NON-NLS-1$
        
        this.letters = new char[4];
        for (int i = 0; i < 4; i++) {
            if (rssName.length() <= i)
                break;
            letters[i] = rssName.charAt(i);
        }
        for (int i = rssName.length(); i < 4; i++) {
            letters[i] = 'A';
        }
        
        this.count = 0;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return count < LIMIT;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
        char[] munged = new char[4];
        int tens = 1;
        int place = 3;
        while (tens < LIMIT) {
            munged[place] = (char) ((letters[place] - 'A' + (count / tens) % 26 ) % 26 + 'A');
            place--;
            tens *= 26;
        }
        count++;
        return new String(munged);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
