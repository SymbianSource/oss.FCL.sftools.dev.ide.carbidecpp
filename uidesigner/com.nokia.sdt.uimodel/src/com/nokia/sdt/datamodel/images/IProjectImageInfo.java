/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

/**
 * Generic interface to image information for a target project.
 * 
 *
 */
public interface IProjectImageInfo {
	public void addListener(IProjectImageInfoListener listener);
    
    public void removeListener(IProjectImageInfoListener listener);

	public void refresh();
    

}
