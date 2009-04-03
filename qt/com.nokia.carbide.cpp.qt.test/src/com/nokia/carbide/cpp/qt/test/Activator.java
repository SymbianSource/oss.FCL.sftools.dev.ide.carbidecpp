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
* The activator class controls the plug-in life cycle
*
*/
package com.nokia.carbide.cpp.qt.test;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.trolltech.qtcppproject.QtProjectPlugin;

public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.qt.test";

	// The shared instance
	private static Activator plugin;
	
	private final String QT_VERSION_NAME = "4.4.4";
	
	private IPath QT_PATH = new Path("D:\\Qt\\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204");

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
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
	public static Activator getDefault() {
		return plugin;
	}

	public void setDefaultQtVersion() {
		if (QtProjectPlugin.getDefault().getDefaultQtVersion().length() == 0) {
			QtProjectPlugin.getDefault().addDefaultQtVersion(QT_VERSION_NAME, QT_PATH.append("bin"), QT_PATH.append("include"));
		}
	}
}
