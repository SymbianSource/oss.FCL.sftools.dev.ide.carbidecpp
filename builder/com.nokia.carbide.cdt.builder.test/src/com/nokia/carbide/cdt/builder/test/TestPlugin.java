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
* The main plugin class to be used in the desktop.
* 
*/
package com.nokia.carbide.cdt.builder.test;

import com.nokia.carbide.cpp.sdk.core.*;

import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class TestPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static TestPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public TestPlugin() {
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
	public static TestPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.nokia.carbide.cdt.builder.test", path);
	}
	
	/**
	 * Get build configurations for the first non-empty SDK we find
	 * @return
	 */
	public static List<ISymbianBuildContext> getUsableBuildConfigs() {
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()) {
			List<ISymbianBuildContext> contexts = sdk.getUnfilteredBuildConfigurations();
			if (contexts.size() > 0)
				return contexts;
		}
		TestCase.fail("No installed SDKs provide build configurations");
		return Collections.EMPTY_LIST;
	}

}
