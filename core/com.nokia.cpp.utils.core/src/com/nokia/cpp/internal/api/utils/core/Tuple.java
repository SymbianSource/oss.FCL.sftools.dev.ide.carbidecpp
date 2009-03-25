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
 * A tuple of zero or more items.
 *
 */
public class Tuple {
	private Object[] args;

	public Tuple(Object... args) {
		this.args = args;
	}

	public Object get(int index) {
		return args[index];
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Tuple))
			return false;
		Object[] otherArgs = ((Tuple) obj).args;
		if (args.length != otherArgs.length)
			return false;
		for (int i = 0; i < otherArgs.length; i++) {
			if (!ObjectUtils.equals(args[i], otherArgs[i]))
				return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashCode = 0x12345678;
		for (int i = 0; i < args.length; i++) {
			hashCode ^= (args[i] != null ? args[i].hashCode() : 0);
		}
		return hashCode;
	}
}
