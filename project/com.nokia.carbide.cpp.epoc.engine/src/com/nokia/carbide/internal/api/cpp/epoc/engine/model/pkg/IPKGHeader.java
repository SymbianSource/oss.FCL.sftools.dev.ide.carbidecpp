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
package com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg;

import java.util.List;


/**
 * Interface representing the package header of a pkg file
 * @since 2.0
 */
public interface IPKGHeader extends IPKGStatement {

	/**
	 * Set the uid
	 * @param uid the uid string
	 */
	public void setUid(String uid);

	/**
	 * Get the uid
	 * @return uid string
	 */
	public String getUid();

	/**
	 * Get the version
	 * @return the modifiable version string list, never null
	 */
	public List<String> getVersion();

	/**
	 * Get the list of pkg header options
	 * @return list of modifiable option strings, never null
	 */
	public List<String> getOptions();

	/**
	 * @return a copy of this
	 */
	IPKGHeader copy();
}
