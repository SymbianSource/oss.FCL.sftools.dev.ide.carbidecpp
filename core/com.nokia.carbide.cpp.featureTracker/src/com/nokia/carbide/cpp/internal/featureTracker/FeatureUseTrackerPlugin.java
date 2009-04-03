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

package com.nokia.carbide.cpp.internal.featureTracker;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.api.featureTracker.IFeatureUseTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class FeatureUseTrackerPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.featureTracker";

	// The shared instance
	private static FeatureUseTrackerPlugin plugin;
	
	private static IFeatureUseTracker featureUseProxy;
	
	/**
	 * The constructor
	 */
	public FeatureUseTrackerPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static FeatureUseTrackerPlugin getDefault() {
		return plugin;
	}
	
	public static IFeatureUseTracker getFeatureUseProxy()
	{
		if ( featureUseProxy == null ){
			featureUseProxy = new FeatureUseTrackerProxy();
		}
		
		return featureUseProxy;
	}

}
