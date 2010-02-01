/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.remoteconnections.discovery.pccs;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.remoteConnections.discovery.pccs"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private static final String SYMSEE_LAYOUT_PLUGIN = "com.nokia.carbide.internal.doc.user"; //$NON-NLS-1$
	private static final String AGENT_NAME = Messages.Activator_Agent_Name;
	private static final String PCSuiteWikiLocation = Messages.Activator_PCSuite_Location;
	private static final String PCCSLocation = Messages.Activator_PCCS_Location;

	/**
	 * The constructor
	 */
	public Activator() {
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
	public static Activator getDefault() {
		return plugin;
	}

	public static boolean isSymSEELayout() {
		if (Platform.getBundle(SYMSEE_LAYOUT_PLUGIN) != null)
			return true;
		return false;

	}
	public static String getDisplayName() {
		return AGENT_NAME;
	}
	public static String getLoadErrorURL() {
		if (isSymSEELayout()) {
			return PCCSLocation;
		} else {
			return PCSuiteWikiLocation;
		}
	}
}
