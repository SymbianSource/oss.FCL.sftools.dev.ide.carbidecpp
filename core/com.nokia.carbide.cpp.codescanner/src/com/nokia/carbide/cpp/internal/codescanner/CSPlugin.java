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

package com.nokia.carbide.cpp.internal.codescanner;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigManager;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbManager;

/**
 * This activator class controls the plug-in life cycle
 */
public class CSPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.codescanner";
	
	// The shared instance
	private static CSPlugin plugin;
	
	private static CSConfigManager configManager;

	private static CSKbManager kbManager;

	private static IPreferenceStore prefsStore;


	/**
	 * The constructor
	 */
	public CSPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		getCSPrefsStore();
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
	 */
	public static CSPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Returns the shared preference store of this plugin
	 */
	public static IPreferenceStore getCSPrefsStore(){
		if (prefsStore == null){
			prefsStore = getDefault().getPreferenceStore();
		}
		
		return prefsStore;
	}
	
	/**
	 * Returns the shared configuration manager.
	 */
	public static CSConfigManager getConfigManager() {
		if (configManager == null) {
			configManager = new CSConfigManager();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(configManager);	
		}
		return configManager;
	}

	/**
	 * Returns the shared knowledge base rule manager.
	 */
	public static CSKbManager getKbManager() {
		if (kbManager == null) {
			kbManager = new CSKbManager();
		}
		return kbManager;
	}

	/**
	 * Returns the full path of the tools folder in this plugin.
	 * @throws Exception
	 */
	public static String getPluginInstallLocation() throws Exception {
		String location = "";
		try {
			URL pluginURL = FileLocator.find(getDefault().getBundle(), new Path("/"), null);
			pluginURL = FileLocator.resolve(pluginURL);
			String pluginFilePath = pluginURL.getFile();
			Path pluginPath = new Path(pluginFilePath);
			location = pluginPath.toOSString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return location;
	}

}
