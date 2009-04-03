/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.datamodel.images;

public interface IProjectImageInfoListener {
	/** Tells that the info has been detected to be dirty based on a change to
	 * a contributing file
	 * @param info
	 */
	void dirtyNotification(IProjectImageInfo info);
	/** 
	 * Tells that the info has been changed
	 * @param info
	 */
	void changed(IProjectImageInfo info);
}