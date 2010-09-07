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

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.api.sdk.SDKCacheUtils;
import com.nokia.carbide.cpp.internal.sdk.core.model.AbstractSDKManager;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManagerRaptorOnly;
import com.nokia.cpp.internal.api.utils.core.HostOS;


/**
 * Main interface into the sdk.core plugin.
 * @see getSDKManager() to get the SDK information
 */
public class SDKCorePlugin extends Plugin {

	/** @since 3.0 */
	public static final boolean SUPPORTS_SBSV1_BUILDER = true;
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.sdk.core"; //$NON-NLS-1$

	// The shared instance
	private static SDKCorePlugin plugin;
	
	private static ISDKManager sdkManager;
	
	/**
	 * The constructor
	 */
	public SDKCorePlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		try {
			SDKCacheUtils.getCache().flushAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		plugin = null;
		super.stop(context);
	}
	
	public static String getPluginId(){
		return PLUGIN_ID;
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SDKCorePlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Get the instance of the ISDKManager
	 * @return ISDKManager instance
	 */
	public static ISDKManager getSDKManager() {
		if (sdkManager == null) {
			// TODO: SDKManagerRaptorOnly, currently only works on Linux
			// ... and SDKManager depends on Windows
			// Win32 can use this instead once ABLD support is dropped from Carbide
			if (HostOS.IS_WIN32)
				sdkManager = new SDKManager();
			else
				sdkManager = new SDKManagerRaptorOnly();
			
			((AbstractSDKManager)sdkManager).init();

		}
		return sdkManager;
	}
	
}
