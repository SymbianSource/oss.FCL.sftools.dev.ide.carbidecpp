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
package com.nokia.carbide.cpp.epoc.engine.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class TestsPlugin extends Plugin {

	//The shared instance.
	private static TestsPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public TestsPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static TestsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Get some build configurations for the first non-empty SDK we find.
	 * @return a list of contexts, no more than 8.
	 */
	public static List<ISymbianBuildContext> getUsableBuildConfigs() {
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()) {
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
			ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
			List<ISymbianBuildContext> contexts = new ArrayList<ISymbianBuildContext>();
			contexts.addAll(sbsv1BuildInfo.getAllBuildConfigurations());
			contexts.addAll(sbsv2BuildInfo.getAllBuildConfigurations());
			if (contexts.size() > 0) {
				return contexts.subList(0, Math.min(contexts.size(), 8));
			}
		}
		TestCase.fail("No installed SDKs provide build configurations");
		return Collections.emptyList();
	}

}
