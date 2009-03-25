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
package com.nokia.cdt.internal.debug.launch.wizard;

/**
 * An interface to allow adding summary text items by unique key
 */
public interface ISummaryTextItemContainer {

	/**
	 * Add a summary bullet item with a unique key - this allows replacement if something changes.<br> 
	 * To remove a value set value to <code>null</code>.
	 * @param key String
	 * @param summaryItem String
	 */
	void putSummaryTextItem(String key, String summaryItem);

}
