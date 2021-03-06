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
package com.nokia.carbide.cpp.sysdoc.hover;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.preferences.PreferencesPageController;

/**
 * The activator class controls the plug-in life cycle
 */
public class ActivatorTest extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.sysdoc.hover.test";

	// The shared instance
	private static ActivatorTest plugin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		if (!HoverManager.getInstance().isStarted()) {
			HoverManager.getInstance().initHover();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		PreferencesPageController.getInstance().removePropertyChangeListener();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ActivatorTest getDefault() {
		return plugin;
	}
}
