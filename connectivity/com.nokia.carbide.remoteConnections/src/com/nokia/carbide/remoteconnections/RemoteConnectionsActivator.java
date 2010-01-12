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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * The activator class controls the plug-in life cycle
 */
public class RemoteConnectionsActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.remoteConnections"; //$NON-NLS-1$

	private static final String DISCOVERY_AGENT_EXTENSION = 
		PLUGIN_ID + ".deviceDiscoveryAgent"; //$NON-NLS-1$

	// The shared instance
	private static RemoteConnectionsActivator plugin;

	private Collection<IDeviceDiscoveryAgent> discoveryAgents;

	/**
	 * The constructor
	 */
	public RemoteConnectionsActivator() {
	}
 
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		Registry instance = Registry.instance();
		instance.loadExtensions();
		instance.loadConnections();
		loadAndStartDeviceDiscoveryAgents();
	}

	public void stop(BundleContext context) throws Exception {
		stopDeviceDiscoveryAgents();
		Registry.instance().storeConnections();
		Registry.instance().disposeConnections();
		plugin = null;
		super.stop(context);
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
	
	private void loadAndStartDeviceDiscoveryAgents() {
		String loadError = Messages.getString("RemoteConnectionsActivator.DiscoveryAgentLoadError"); //$NON-NLS-1$
		discoveryAgents = new ArrayList<IDeviceDiscoveryAgent>();
		loadExtensions(DISCOVERY_AGENT_EXTENSION, loadError, discoveryAgents, new IFilter() {
			public boolean select(Object toTest) {
				if (toTest instanceof IDeviceDiscoveryAgent) {
					try {
						((IDeviceDiscoveryAgent) toTest).start();
						return true;
					} catch (Throwable e) {
						// since we launch arbitrary code, catch any exception to prevent killing the view
						logError(e);
					}
				}
				return false;
			}
		});
		
	}

	private void stopDeviceDiscoveryAgents() {
		for (IDeviceDiscoveryAgent agent : discoveryAgents) {
			try {
				agent.stop();
			} catch (CoreException e) {
				logError(e);
			}
		}
		
	}

	public static void log(String errorStr, Throwable t) {
		RemoteConnectionsActivator p = RemoteConnectionsActivator.getDefault();
		String error = errorStr;
		if (t != null) {
			error += " : " + t.getLocalizedMessage(); //$NON-NLS-1$
		}
		Logging.log(p, Logging.newStatus(p, IStatus.ERROR, error));
		if (t instanceof CoreException)
			Logging.log(p, ((CoreException) t).getStatus());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void loadExtensions(String extensionId, String loadError, Collection<T> extensionObjects, IFilter filter) {
		IConfigurationElement[] elements = 
			Platform.getExtensionRegistry().getConfigurationElementsFor(extensionId);
		for (IConfigurationElement element : elements) {
			try {
				T extObject = (T) element.createExecutableExtension("class"); //$NON-NLS-1$
				if (filter == null || filter.select(extObject))
					extensionObjects.add(extObject);
			} 
			catch (CoreException e) {
				if (loadError != null)
					RemoteConnectionsActivator.log(loadError, e);
			}
		}
	}
}
