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
 
package com.nokia.carbide.internal.bugdatacollector.plugin;

import java.io.File;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class BugDataCollectorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.bugdatacollector"; //$NON-NLS-1$

	// The shared instance
	private static BugDataCollectorPlugin plugin;
	private static IPreferenceStore prefsStore;
	private String pluginInstallPath = ""; //$NON-NLS-1$
	
	/**
	 * The constructor
	 */
	public BugDataCollectorPlugin() {
		plugin = this;
	}
	
	/**
	 * Returns the path where this plugin is installed
	 * @return the path where this plugin is installed
	 */
	public static String getPluginInstallPath() {
		try {
			if (plugin.pluginInstallPath == "") { //$NON-NLS-1$
				 // URL to the plugin's root ("/")
				URL relativeURL = plugin.getBundle().getEntry("/"); //$NON-NLS-1$
				//	Converting into local path
				URL localURL = FileLocator.toFileURL(relativeURL);
				//	Getting install location in correct form
				File f = new File(localURL.getPath());
				plugin.pluginInstallPath = f.getAbsolutePath() + "\\"; //$NON-NLS-1$
			}
			return plugin.pluginInstallPath;
		} catch (Exception e) {
			return ""; //$NON-NLS-1$
		}
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
	public static BugDataCollectorPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the PreferenceStore where plugin preferences are stored
	 * 
	 * @return the PreferenceStore where plugin preferences are stored
	 */
	public static IPreferenceStore getPrefsStore(){
		if (prefsStore == null){
			prefsStore = getDefault().getPreferenceStore();
		}
		
		return prefsStore;
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
}
