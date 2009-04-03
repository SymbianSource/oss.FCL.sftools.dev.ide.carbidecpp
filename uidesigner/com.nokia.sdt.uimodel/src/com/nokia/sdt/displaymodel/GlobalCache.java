/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.displaymodel;

import com.nokia.cpp.internal.api.utils.core.CacheMap;


/**
 * A CacheMap instance is maintained as a service for
 * objects used within display models. It provides a means
 * for caching objects outside the scope of any single
 * display model. Whenever the count of DisplayModel instances
 * goes to zero all cached objects are disposed.
 */
public abstract class GlobalCache {

	private static CacheMap globalCache = new CacheMap();
	
	/**
	 * Get the global cache. 
	 * @return CacheMap, never null
	 */
	public static CacheMap getCache() {
		return globalCache;
	}
	
	/**
	 * Dispose the global cache.
	 */
	public static void disposeAll() {
		globalCache.disposeAll();
	}
	
}
