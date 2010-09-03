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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent;
import com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent.IPrerequisiteStatus;
import com.nokia.carbide.remoteconnections.internal.api.IStatusDisplay;
import com.nokia.carbide.remoteconnections.internal.api.IToggleServicesTestingListener;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.internal.ui.DeviceDiscoveryPrequisiteErrorDialog;
import com.nokia.carbide.remoteconnections.internal.ui.StatusDisplay;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * The activator class controls the plug-in life cycle
 */
public class RemoteConnectionsActivator extends AbstractUIPlugin {

	private final class WhenWorkbenchIsVisibleThread extends Thread {
		private Shell shell;
		private boolean visible;
		private final Runnable runnable;
		
		public WhenWorkbenchIsVisibleThread(Runnable runnable) {
			this.runnable = runnable;
			shell = WorkbenchUtils.getActiveShell();
		}
		
		public void run() {
			while (true) {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						if (shell != null && shell.isVisible()) {
							visible = true;
						}
					}
				});
				if (visible)
					break;
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					break;
				}
			}
			if (visible)
				runnable.run();
		}
	}
	
	public interface IDiscoveryAgentsLoadedListener {
		void agentsAreLoaded();
	}

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.remoteConnections"; //$NON-NLS-1$

	private static final String DISCOVERY_AGENT_EXTENSION = PLUGIN_ID + ".deviceDiscoveryAgent"; //$NON-NLS-1$

	// The shared instance
	private static RemoteConnectionsActivator plugin;

	private Collection<IDeviceDiscoveryAgent> discoveryAgents;
	private ListenerList<IDiscoveryAgentsLoadedListener> listeners;
	private ListenerList<IToggleServicesTestingListener> toggleServicesListeners;

	private static final String IGNORE_AGENT_LOAD_ERRORS_KEY = "ignoreAgentLoadErrors"; //$NON-NLS-1$
	private static final String AGENT_STATE_KEY_PREFIX = "agentState."; //$NON-NLS-1$
	private static final String SHOULD_TEST_SERVICES_PREF_ID = "shouldTestServices"; //$NON-NLS-1$

	/**
	 * The constructor
	 */
	public RemoteConnectionsActivator() {
		listeners = new ListenerList<IDiscoveryAgentsLoadedListener>();
		toggleServicesListeners = new ListenerList<IToggleServicesTestingListener>();
	}
 
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		Registry instance = Registry.instance();
		instance.loadExtensions();
		instance.loadConnections();
		
		new WhenWorkbenchIsVisibleThread(new Runnable() {
			public void run() {
				fireToggleServicesTestingListener(getShouldTestServices());

				if (!ignoreAgentLoadErrors())
					checkPrerequisites();
				
				loadAndStartDeviceDiscoveryAgents();
			}
		}).start();
	}

	private boolean ignoreAgentLoadErrors() {
		return getPreferenceStore().getBoolean(IGNORE_AGENT_LOAD_ERRORS_KEY);
	}

	private void checkPrerequisites() {
		if (WorkbenchUtils.isJUnitRunning())
			return;
		
		final Map<IDeviceDiscoveryAgent, IPrerequisiteStatus> agentToStatusMap = 
			new HashMap<IDeviceDiscoveryAgent, IPrerequisiteStatus>();
		
		// load the extensions just to check statuses
		Collection<IDeviceDiscoveryAgent> agents = new ArrayList<IDeviceDiscoveryAgent>();
		loadExtensions(DISCOVERY_AGENT_EXTENSION, null, agents, null);
		
		for (IDeviceDiscoveryAgent agent : agents) {
			IPrerequisiteStatus status = agent.getPrerequisiteStatus();
			if (!status.isOK()) {
				agentToStatusMap.put(agent, status);
			}
		}

		if (!agentToStatusMap.isEmpty()) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					DeviceDiscoveryPrequisiteErrorDialog dlg = new DeviceDiscoveryPrequisiteErrorDialog(WorkbenchUtils.getSafeShell());
					for (Entry<IDeviceDiscoveryAgent, IPrerequisiteStatus> entry : agentToStatusMap.entrySet()) {
						IDeviceDiscoveryAgent agent = entry.getKey();
						IPrerequisiteStatus status = entry.getValue();
						dlg.addAgentData(agent.getDisplayName(), status.getErrorText(), status.getURL());
					}
					dlg.open();
					if (dlg.isDontAskAgainChecked())
						storeIgnoreAgentLoadErrorsFlag();
				}
			});
		}	
	}

	public void stop(BundleContext context) throws Exception {
		stopDeviceDiscoveryAgents();
		Registry.instance().storeConnections();
		Registry.instance().disposeConnections();
		plugin = null;
		super.stop(context);
	}

	private void storeIgnoreAgentLoadErrorsFlag() {
		getPreferenceStore().setValue(IGNORE_AGENT_LOAD_ERRORS_KEY, true);
		try {
			new InstanceScope().getNode(PLUGIN_ID).flush();
		} catch (BackingStoreException e) {
			logError(e);
		}
	}
	
	private void storeAgentRunningStates() {
		IPreferenceStore preferenceStore = getPreferenceStore();
		for (IDeviceDiscoveryAgent agent : discoveryAgents) {
			String agentKey = AGENT_STATE_KEY_PREFIX + agent.getId();
			if (!preferenceStore.contains(agentKey))
				preferenceStore.setDefault(agentKey, true);
			preferenceStore.setValue(agentKey, agent.isRunning());
		}
		try {
			new InstanceScope().getNode(PLUGIN_ID).flush();
		} catch (BackingStoreException e) {
			logError(e);
		}
	}
	
	private boolean getStoredAgentRunningState(IDeviceDiscoveryAgent agent) {
		String agentKey = AGENT_STATE_KEY_PREFIX + agent.getId();
		if (!getPreferenceStore().contains(agentKey))
			return true;
		return getPreferenceStore().getBoolean(agentKey);
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
						IDeviceDiscoveryAgent discoveryAgent = (IDeviceDiscoveryAgent) toTest;
						if (getStoredAgentRunningState(discoveryAgent))
							discoveryAgent.start();
						return true;
					} catch (Throwable e) {
						// since we launch arbitrary code, catch any exception to prevent killing the view
						logError(e);
					}
				}
				return false;
			}
		});
		fireDiscoveryAgentsLoaded();
	}

	private void stopDeviceDiscoveryAgents() {
		storeAgentRunningStates();
		for (IDeviceDiscoveryAgent agent : discoveryAgents) {
			try {
				if (agent.isRunning())
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
	
	public static IStatusDisplay getStatusDisplay() {
		return new StatusDisplay();
	}

	public Collection<IDeviceDiscoveryAgent> getDiscoveryAgents() {
		if (discoveryAgents != null)
			return Collections.unmodifiableCollection(discoveryAgents);
		return Collections.emptySet();
	}

	public void addDiscoveryAgentsLoadedListener(IDiscoveryAgentsLoadedListener listener) {
		listeners.add(listener);
	}
	
	public void removeDiscoveryAgentsLoadedListener(IDiscoveryAgentsLoadedListener listener) {
		listeners.remove(listener);
	}
	
	private void fireDiscoveryAgentsLoaded() {
		for (IDiscoveryAgentsLoadedListener listener : listeners) {
			listener.agentsAreLoaded();
		}
	}
	
	public boolean getShouldTestServices() {
		IEclipsePreferences eclipsePreferences = new InstanceScope().getNode(PLUGIN_ID);
		return eclipsePreferences.getBoolean(SHOULD_TEST_SERVICES_PREF_ID, true);
	}
	
	public void setShouldTestServices(boolean shouldTest) {
		boolean currentState = getShouldTestServices();
		if (shouldTest == currentState)
			return;
		fireToggleServicesTestingListener(shouldTest);
		try {
			IEclipsePreferences eclipsePreferences = new InstanceScope().getNode(PLUGIN_ID);
			eclipsePreferences.putBoolean(SHOULD_TEST_SERVICES_PREF_ID, shouldTest);
			eclipsePreferences.flush();
		} catch (BackingStoreException e) {
			logError(e);
		}
	}

	public void addToggleServicesTestingListener(IToggleServicesTestingListener listener) {
		toggleServicesListeners.add(listener);
	}
	
	public void removeToggleServicesTestingListener(IToggleServicesTestingListener listener) {
		toggleServicesListeners.remove(listener);
	}
	
	private void fireToggleServicesTestingListener(boolean enabled) {
		for (IToggleServicesTestingListener listener : toggleServicesListeners) {
			listener.servicesTestingToggled(enabled);
		}
	}
	
}
