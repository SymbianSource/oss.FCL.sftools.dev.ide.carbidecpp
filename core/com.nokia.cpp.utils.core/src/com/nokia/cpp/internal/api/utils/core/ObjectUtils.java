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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

import java.util.ArrayList;

/**
 * Utilities routines relying only on Object type
 */
public abstract class ObjectUtils {

	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 == obj2)
			return true;
		if (obj1 != null && obj2 != null)
			return obj1.equals(obj2);
		return false;
	}
	
	public static int findEqualObject(Object[] array, Object obj) {
		if (array == null || obj == null)
			return -1;
		int result = -1;
		for (int i = 0; i < array.length; i++) {
			Object curr = array[i];
			if (curr != null && curr.equals(obj)) {
				result = i;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Return an ArrayList with the passed array or elements
	 * as the initial contents. Differs from Arrays#asList() in that
	 * the list is not fixed sized.
	 * @param <T> type of list elements
	 * @param a An array or sequence of params of type T
	 */
	public static <T> ArrayList<T> asList(T... a) {
		ArrayList<T> result = new ArrayList<T>();
		for (T t : a) {
			result.add(t);
		}
		return result;
	}
	
	/**
	 * Utility to handle boilerplate code for locating the
	 * given interface on an object. It uses instanceof, 
	 * checks if the object implements IAdaptable, and uses
	 * the platform adapter manager.
	 */
	public static Object getAdapter(Object target, Class<?> interfaceType) {
		if (target == null) return null;
		Object result = null;
		if (interfaceType.isInstance(target)) {
			result = target;
		}
		else if (target instanceof IAdaptable) {
			result = ((IAdaptable)target).getAdapter(interfaceType);
		}
		if (result == null) {
			result = Platform.getAdapterManager().getAdapter(target, interfaceType);
		}
		return result;
	}
}
