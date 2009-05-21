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
*/
package com.nokia.carbide.cpp.sdk.core;

import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This is a catalog of all the .VAR (Symbian Binary Variation) files detected for a given SDK.
 *
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ISBVCatalog {
	/**
	 * Get any problems detected while parsing the BSF files.
	 * @return array of messages, never null
	 */
	IMessage[] getMessages();
	
	/** 
	 * Get the array of SBV platforms detected.  Each corresponds to
	 * a *.var file.  This does not include the built-in platforms.
	 * @return array, never null
	 */
	ISBVPlatform[] getPlatforms();
	
	
	/**
	 * Find a platform with the given name.  
	 * @param name platform name, case doesn't matter
	 * @return platform or null
	 */
	ISBVPlatform findPlatform(String platform);

}
