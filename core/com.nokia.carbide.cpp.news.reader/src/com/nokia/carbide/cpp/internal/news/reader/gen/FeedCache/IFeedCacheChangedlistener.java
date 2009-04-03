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


package com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache;

/**
 * Interface that clients implement to listen for changes to local feed cache.
 *
 */
public interface IFeedCacheChangedlistener {

	/**
	 * Receive an event that the local feed cache has changed.
	 * @param alertUser - controls whether to alert user of change.
	 */
	void feedCacheChanged(boolean alertUser);

}
