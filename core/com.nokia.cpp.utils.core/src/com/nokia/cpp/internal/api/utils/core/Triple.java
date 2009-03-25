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
 * A triple of objects.
 *
 * @param <T>
 * @param <U>
 * @param <V>
 */
public class Triple<T,U,V> {
	public T first;
	public U second;
	public V third;

	public Triple(T first, U second, V third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof Triple) {
			return ObjectUtils.equals(((Triple)obj).first, first)
			&& ObjectUtils.equals(((Triple)obj).second, second) 
			&& ObjectUtils.equals(((Triple)obj).third, third); 
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return ((first != null) ? first.hashCode() : 0)
		^ (((second != null) ? second.hashCode() : 0) << 1)
		^ (((third != null) ? third.hashCode() : 0) << 2);
	}
	
}
