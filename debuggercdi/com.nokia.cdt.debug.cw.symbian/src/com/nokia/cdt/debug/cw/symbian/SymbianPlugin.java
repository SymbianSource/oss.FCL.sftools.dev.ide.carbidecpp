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

package com.nokia.cdt.debug.cw.symbian;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.freescale.cdt.debug.cw.core.ui.console.LoggingConsole;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * The main plugin class to be used in the desktop.
 */
public class SymbianPlugin extends AbstractUIPlugin {
	// The shared instance.
	private static SymbianPlugin plugin;

	// Resource bundle.
	private ResourceBundle resourceBundle;

	private final String systemConsoleName = "Windows System Messages"; //$NON-NLS-1$
	private final String debugConsoleName = "Emulation Program Output"; //$NON-NLS-1$
	public static final String DebugTraceMessagesConsoleName = "Emulator Output"; //$NON-NLS-1$
	
	public static final String DebugTraceLaunchSetting = "DebugTraceLaunchSetting"; //$NON-NLS-1$

	public static final String PLUGIN_ID = "com.nokia.cdt.debug.cw.symbian" ; //$NON-NLS-1$

	// Preference constants
	public static final String Epoc_Root = PLUGIN_ID + ".Epoc_Root"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	public SymbianPlugin() {
		super();
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		// Force the the core FSL UI plugin to load; this ensures the UI registers
		// its callback into the core (non-UI) plugin before it's actually needed
		com.freescale.cdt.debug.cw.core.ui.CWDebugUIPlugin.getDefault();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		resourceBundle = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static SymbianPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = SymbianPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		try {
			if (resourceBundle == null)
				resourceBundle = ResourceBundle
						.getBundle("com.nokia.cdt.debug.cw.symbian.SymbianPluginResources"); //$NON-NLS-1$
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
		return resourceBundle;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(
				"com.nokia.cdt.debug.cw.symbian", path); //$NON-NLS-1$
	}

	public void openSystemConsole(boolean clearConsole) {
		openConsole(systemConsoleName, clearConsole);
	}

	public void openDebugConsole(boolean clearConsole) {
		openConsole(debugConsoleName, clearConsole);
	}
	
	public void openDebugTraceConsole(boolean clearConsole) {
		openConsole(DebugTraceMessagesConsoleName, clearConsole);
	}
	
	public void openConsole(String consoleName, boolean clearConsole) {
		// add it if necessary
		LoggingConsole console = null;
		boolean found = false;

		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (int i=0; i<consoles.length; i++) {
			if (consoleName.equals(consoles[i].getName())) {
				console = (LoggingConsole)consoles[i];
				found = true;
				break;
			}
		}
				
		if (!found) {
        	console = new LoggingConsole(consoleName);
        	ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{console});			
		}

		if (clearConsole && console != null) {
			console.clearConsole();
		}

		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
	}

	public static String getUniqueIdentifier() {
		if ( getDefault() == null ) {
			// If the default instance is not yet initialized,
			// return a static identifier. This identifier must
			// match the plugin id defined in plugin.xml
			return PLUGIN_ID;
		}
		return getDefault().getBundle().getSymbolicName();
	}

	public static void log(Throwable t) {
		Logging.log(plugin, Logging.newStatus(plugin, t));
	}
}
