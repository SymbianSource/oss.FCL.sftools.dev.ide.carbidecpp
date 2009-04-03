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

/**
 * A pair of objects.
 *
 * @param <T>
 * @param <U>
 */
public class Pair<T,U> {
	public T first;
	public U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof Pair) {
			return ObjectUtils.equals(((Pair)obj).first, first)
			&& ObjectUtils.equals(((Pair)obj).second, second); 
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return ((first != null) ? first.hashCode() : 0)
		^ (((second != null) ? second.hashCode() : 0) << 1);
	}

	@Override
	public String toString() {
		return "< " + first + " / " + second + " >"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
