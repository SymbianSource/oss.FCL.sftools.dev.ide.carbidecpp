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
package com.nokia.cpp.internal.api.utils.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator which manages two other iterators in sequence.
 *
 */
public class ChainedIterator<Element> implements Iterator<Element> {

	private Iterator<Element> first;
	private Iterator<Element> second;

	/**
	 * 
	 */
	public ChainedIterator(Iterator<Element> first, Iterator<Element> second) {
		this.first = first;
		this.second = second;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		if (first != null) {
			if (first.hasNext())
				return true;
			first = null;
		}
		if (second != null) {
			if (second.hasNext())
				return true;
			second = null;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Element next() {
		if (first != null)
			return first.next();
		else if (second != null)
			return second.next();
		throw new NoSuchElementException();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		if (first != null)
			first.remove();
		else if (second != null)
			second.remove();
		else
			throw new UnsupportedOperationException();
	}

}
