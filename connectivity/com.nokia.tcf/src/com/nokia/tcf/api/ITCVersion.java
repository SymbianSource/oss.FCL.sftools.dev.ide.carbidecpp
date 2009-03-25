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
/**
 * 
 */
package com.nokia.tcf.api;

/**
 * An object representing a version of some connection entity.
 */
public interface ITCVersion {

	/**
	 * Return the version in string format
	 * 
	 * @return
	 */
	public String getVersionString();
	
	/**
	 * Set the version from a string
	 * 
	 * @param inVersion
	 */
	public void setVersionString(String inVersion);
}
