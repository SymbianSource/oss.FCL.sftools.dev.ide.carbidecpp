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

package com.nokia.carbide.cpp.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.ui.SharedImages;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * The activator class controls the plug-in life cycle
 */
public class CarbideUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.ui";

	// The shared instance
	private static CarbideUIPlugin plugin;
	
	private static ICarbideSharedImages sharedImages;
	
	/**
	 * The constructor
	 */
	public CarbideUIPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
	public static CarbideUIPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Provides access to images published by this plugin.
	 * @return
	 */
	public static ICarbideSharedImages getSharedImages() {
		if (sharedImages == null) {
			sharedImages = new SharedImages();
		}
		return sharedImages;
	}
	
	static public void log(IStatus status) {
		Logging.log(plugin, status);
	}
	
	static public void log(Throwable thr) {
		Logging.log(plugin, Logging.newStatus(plugin, thr));
	}
	
	static public void log(Throwable thr, String msg) {
		Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg, thr));
	}
}
