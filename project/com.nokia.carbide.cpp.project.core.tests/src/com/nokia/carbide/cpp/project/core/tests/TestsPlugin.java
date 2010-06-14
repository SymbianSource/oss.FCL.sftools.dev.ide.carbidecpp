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
package com.nokia.carbide.cpp.project.core.tests;

import com.nokia.carbide.cpp.sdk.core.*;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

/**
 * The activator class controls the plug-in life cycle
 */
public class TestsPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.project.core.tests";

	// The shared instance
	private static TestsPlugin plugin;
	
	/**
	 * The constructor
	 */
	public TestsPlugin() {
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
	public static TestsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Get build configurations for the first non-empty SDK we find
	 * @return
	 */
	public static List<ISymbianBuildContext> getUsableBuildConfigs() {
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()) {
			List<ISymbianBuildContext> contexts = sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER).getAllBuildConfigurations();
			if (contexts.size() > 0)
				return contexts.subList(0, Math.min(contexts.size(), 8));
		}
		TestCase.fail("No installed SDKs provide build configurations");
		return Collections.emptyList();
	}


}
