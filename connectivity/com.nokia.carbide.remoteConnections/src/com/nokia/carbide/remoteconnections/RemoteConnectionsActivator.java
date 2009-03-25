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
package com.nokia.carbide.remoteconnections;

import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class RemoteConnectionsActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.remoteConnections"; //$NON-NLS-1$

	// The shared instance
	private static RemoteConnectionsActivator plugin;
	
	// A service tracker for the proxy service
	private ServiceTracker proxyServiceTracker;
	
	/**
	 * The constructor
	 */
	public RemoteConnectionsActivator() {
	}

 
 	/**
	 * Returns IProxyService.
	 */
	public IProxyService getProxyService() {
		return (IProxyService) proxyServiceTracker.getService();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		Registry instance = Registry.instance();
		instance.loadExtensions();
		instance.loadConnections();
		this.proxyServiceTracker = new ServiceTracker(context, IProxyService.class.getName(), null);
		this.proxyServiceTracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Registry.instance().storeConnections();
		Registry.instance().disposeConnections();
		plugin = null;
		super.stop(context);
		this.proxyServiceTracker.close();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RemoteConnectionsActivator getDefault() {
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
	 * Return the implementation of IConnectionsManager
	 * @return IConnectionsManager
	 */
	public static IConnectionsManager getConnectionsManager() {
		return Registry.instance();
	}
	
	/**
	 * Return the implementation of IConnectionTypeProvider
	 * @return IConnectionTypeProvider
	 */
	public static IConnectionTypeProvider getConnectionTypeProvider() {
		return Registry.instance();
	}

	public static void logError(Throwable t) {
		Logging.log(plugin, Logging.newSimpleStatus(0, t));
	}
	
	public static void logMessage(String message, int type) {
		Logging.log(plugin, Logging.newStatus(plugin, type, message));
	}

	public static void setHelp(Control control, String id) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, PLUGIN_ID + id);		 //$NON-NLS-1$
	}
}
