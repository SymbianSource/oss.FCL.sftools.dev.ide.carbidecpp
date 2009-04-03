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
package com.nokia.carbide.updater;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.internal.api.updater.IProjectUpdateManager;
import com.nokia.carbide.internal.updater.ProjectUpdateManager;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * The activator class controls the plug-in life cycle
 */
public class CarbideUpdaterPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.updater"; //$NON-NLS-1$

	// The shared instance
	private static CarbideUpdaterPlugin plugin;
	
	/**
	 * The constructor
	 */
	public CarbideUpdaterPlugin() {
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
	public static CarbideUpdaterPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Returns the singleton IProjectUpdateManager, used to
	 * initiate project updating sessions.
	 */
	public static IProjectUpdateManager getProjectUpdateManager() {
		return ProjectUpdateManager.instance();
	}

	public static void log(IStatus status) {
		Logging.log(plugin, status);
	}

	static public void log(Throwable thr) {
		Logging.log(plugin, Logging.newStatus(plugin, thr));
	}
}
