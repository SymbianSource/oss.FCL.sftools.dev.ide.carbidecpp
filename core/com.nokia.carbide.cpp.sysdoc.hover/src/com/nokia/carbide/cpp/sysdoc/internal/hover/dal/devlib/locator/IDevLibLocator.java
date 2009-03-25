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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator;

import java.net.MalformedURLException;
import java.net.URL;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXProperties;

/**
 * 
 * An locator interface for developer library to extract address related
 * information
 * 
 */

public interface IDevLibLocator {
	/**
	 * 
	 * @return full interchange file properties of developer library
	 */
	InterXProperties getInterXProperties();

	/**
	 * 
	 * @return full URL of developer library
	 */
	URL getDevLibURL();

	/**
	 * Given a relative address to developer address, return full address
	 * 
	 * @param relAddress
	 *            relative address
	 * @return Full URL address of relative addres
	 * @throws MalformedURLException
	 */
	URL getFullAddress(String relAddress) throws MalformedURLException;

	/**
	 * 
	 * @return relative root address of interchange file in developer library
	 */
	String getInterXRootDir();
}
