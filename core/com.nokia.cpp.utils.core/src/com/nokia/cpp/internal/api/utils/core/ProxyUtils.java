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

package com.nokia.cpp.internal.api.utils.core;

import com.nokia.cpp.utils.core.noexport.UtilsCorePlugin;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.net.URI;

public class ProxyUtils {

	/**
	 * Get a reference to the proxy service
	 * @return IProxyService
	 */
	public static IProxyService getProxyService() {
		BundleContext bc = UtilsCorePlugin.getDefault().getBundle().getBundleContext();
		ServiceReference ref = bc.getServiceReference(IProxyService.class.getName());
		if (ref != null)
			return (IProxyService) bc.getService(ref);
			
		return null;
	}
	
	
	/**
	 * Get proxy data for this URI. Takes into consideration native vs manual proxy setting
	 * as well as hosts for which no proxy is required.
	 * Returns null if no proxy data is set or needed for this URI.
	 * @param uri URI
	 * @return IProxyData
	 */
	public static IProxyData getProxyData(URI uri) {
		IProxyService proxyService = getProxyService();
		if (proxyService != null) {
			IProxyData[] proxyData = proxyService.select(uri);
			if (proxyData.length > 0)
				return proxyData[0];
		}
		
		return  null;
	}
}
