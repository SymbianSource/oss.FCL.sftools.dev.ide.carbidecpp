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
package com.nokia.sdt.component.symbian.scripting;

import org.mozilla.javascript.*;

import java.util.*;

public abstract class ScriptingUtils {
	/**
	 * Unwrap an object, if possible.
	 */
	public static Object unwrap(Object obj) {
		if (obj instanceof Wrapper)
			return ((Wrapper) obj).unwrap();
		else
			return obj;
	}
	
	/**
	 * Unwrap an array or collection provided from Javascript code into a Java array
	 * @param obj 
	 * @param allowSingleton true: if obj is not an array or collection, return singleton
	 * @return an array of the objects, as native Java objects if possible,
	 * or null if the obj doesn't look like an array
	 */
	public static Object[] unwrapArray(Object obj, boolean allowSingleton) {
		Object[] params = null;
        if (obj instanceof NativeArray) {
        	NativeArray array = (NativeArray) obj;
        	params = new Object[(int) array.getLength()];
        	for (int i = 0; i < params.length; i++) {
				params[i] = unwrap(array.get(i, array));
			}
        } else if (obj instanceof NativeJavaArray) {
        	Object[] theArgs = (Object[]) unwrap((NativeJavaArray) obj);
        	params = new Object[theArgs.length];
        	System.arraycopy(theArgs, 0, params, 0, theArgs.length);
        } else if (obj instanceof Collection) {
        	params = ((Collection)obj).toArray();
        	for (int i = 0; i < params.length; i++) {
				params[i] = unwrap(params[i]);
			}
        } else if (allowSingleton) {
        	params = new Object[] { unwrap(obj) };
        }
        return params;
	}
	
	/**
	 * Unwrap an array or collection provided from Javascript code
	 * into a Collection.
	 * @param obj
	 * @param allowSingleton true: if obj is not an array or collection, return singleton
	 * @return an collection of the objects, as native Java objects if possible,
	 * or null if the obj doesn't look like a collection
	 */
	public static Collection unwrapCollection(Object obj, boolean allowSingleton) {
		Collection params = null;
        if (obj instanceof NativeArray) {
        	NativeArray array = (NativeArray) obj;
        	params = new ArrayList();
        	for (int i = 0; i < array.getLength(); i++) {
				params.add(unwrap(array.get(i, array)));
			}
        } else if (obj instanceof NativeJavaArray) {
        	Object[] theArgs = (Object[]) unwrap((NativeJavaArray) obj);
        	params = Arrays.asList(theArgs);
        } else if (obj instanceof Collection) {
        	params = new ArrayList();
        	for (Iterator iter = ((Collection)obj).iterator(); iter.hasNext();) {
				Object o = (Object) iter.next();
				params.add(unwrap(o));
			}
        } else if (allowSingleton) {
        	params = new ArrayList();
        	params.add(unwrap(obj));
        }
        return params;
	}	
}
