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

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXProperties;

/**
 * A base develop library locator for {@link IDevLibLocator} interface. It gets location information from a {@link DevLibProperties}
 */
public class BaseDevLibLocator implements IDevLibLocator {
	protected DevLibProperties devLibProperties;

	/**
	 * This base class utilise DevLibProperties to obtain location informations.
	 * 
	 * @param devLibProperties
	 */
	public BaseDevLibLocator(DevLibProperties devLibProperties) {
		this.devLibProperties = devLibProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator.IDevLibLocator
	 * #getFullAddress(java.lang.String)
	 */
	public URL getFullAddress(String relAddress) throws MalformedURLException {
		String address = HoverManager.getInstance().getWebServer()
				.getRootPath()
				+ devLibProperties.getInterXRootDir() + relAddress;
		URL fullPath = new URL(address);// URLHelper.coninicalPath(devLibRes,
										// relAddress);
		return fullPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator.IDevLibLocator
	 * #getInterXProperties()
	 */
	public InterXProperties getInterXProperties() {
		return devLibProperties.getInterXProperties();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator.IDevLibLocator
	 * #getInterXRootDir()
	 */
	public String getInterXRootDir() {
		return devLibProperties.getInterXRootDir();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator.IDevLibLocator
	 * #getDevLibURL()
	 */
	public URL getDevLibURL() {
		return devLibProperties.getDevLibURL();
	}
}
