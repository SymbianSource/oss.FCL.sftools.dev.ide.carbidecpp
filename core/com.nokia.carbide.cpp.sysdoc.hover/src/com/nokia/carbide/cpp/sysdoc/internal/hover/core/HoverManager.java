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
package com.nokia.carbide.cpp.sysdoc.internal.hover.core;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.testing.TestableObject;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator.DevLiblocatorFactory;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator.IDevLibLocator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXIndexController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.sdk.SDKController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.preferences.PreferencesPageController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.IEmbeddedWebServer;
import com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.JettyWebServer;

/**
 * It is a manager class and an entry point to Hover plug-in. When Carbide
 * starts, startup class uses this class to start the hover plug-in. This class
 * also used to deactivate or activate the hover plug-in and holds global
 * services/instances used by the plug-in itself.
 * 
 */
final public class HoverManager {
	private static volatile HoverManager instance = new HoverManager();
	private volatile boolean enabled = false; // if plug-in enabled.
	private volatile boolean started = false; // to prevent multiple
	// initialising
	private static volatile boolean testMode = false;
	private volatile IDevLibLocator devLibLocator; // developer library locator
	// embedded web server to access directly developer libray plug-in (jar)
	// without extracting it...
	private volatile IEmbeddedWebServer webServer;
	// Active developer library properties
	private static volatile DevLibProperties activeDevLibProperties = null;

	private HoverManager() {
	}

	/**
	 * This is the method called by Activator or startup plug-in to initialise
	 * hover plug-in.
	 */
	public void initHover() {

		try {
			synchronized (this) {
				if (started) {
					return;
				}
				started = true; // mark it as already started...
				// start embedded web server for directly access to developer
				// library plug-in
				webServer = new JettyWebServer();
				webServer.startWebServer(Activator.PLUGIN_ID);
				// gather available SDK informations for binding resolution
				SDKController.getInstance().initSDKDirectories();
				// get preferences
				PreferencesPageController.getInstance()
						.initPreferenceController();
				if (PreferencesPageController.isDeActivatedHovering()) {
					setEnabled(false);
				} else {
					startIndex();
				}
			}
		} catch (Exception e) {
			haltHoveringService(e);
			return;
		}
		if (activeDevLibProperties != null && activeDevLibProperties.isValid()) {
			HoverManager.getInstance().setEnabled(true);
		}
	}

	public IEmbeddedWebServer getWebServer() {
		return webServer;
	}

	/**
	 * Halt hovering service. This method is generally called when an exception
	 * is thrown.
	 * 
	 * @param msg
	 *            : the reason why hover plug-in cancelled.
	 */
	public void haltHoveringService(final String msg) {
		HoverManager.getInstance().setEnabled(false);
		PreferencesPageController.setDeActivateHoverPlugin(true);

		// don't show error dialog here - fixes bug #10130
		// DialogHelper.displayErrorDialog(MessagesConstants.DEACTIVATED + msg);
	}

	/**
	 * Halt hovering service. This method is generally called when an exception
	 * is thrown.
	 */
	public void haltHoveringService() {
		haltHoveringService("");
	}

	/**
	 * Halt hovering service. This method is generally called when an exception
	 * is thrown.
	 * 
	 * @param e
	 *            Causing exception
	 */
	public void haltHoveringService(final Exception e) {
		Logger.logError(e);
		haltHoveringService(e.toString() + ". " + MessagesConstants.ERROR_VIEW);
	}

	/**
	 * Start indexing process of interchange file
	 */
	public void startIndex() {
		// Deactivated

		if (SDKController.getInstance().getAvailableAllSDKDirectories()
				.isEmpty()) {
			HoverManager.getInstance().haltHoveringService(
					MessagesConstants.NOT_AVAILABLE_SDK);
			return;
		}

		if (activeDevLibProperties == null || !activeDevLibProperties.isValid()) {
			HoverManager.getInstance().setEnabled(false);
			return;
		}
		startInterXIndexingExecutor();
	}

	/**
	 * This method prepares a Runnable to start ExecutorAgent
	 */
	private void startInterXIndexingExecutor() {
		HoverManager.getInstance().setEnabled(false);
		devLibLocator = DevLiblocatorFactory.createDevLibLocator();
		InterXIndexController.creatIndexer();
	}

	public static DevLibProperties getActiveDevLibProperties() {
		return activeDevLibProperties;
	}

	public static void setActiveDevLibProperties(
			DevLibProperties activeDevLibProperties) {
		HoverManager.activeDevLibProperties = activeDevLibProperties;
	}

	public IDevLibLocator getDevLibLocator() {
		return devLibLocator;
	}

	public void setDevLibLocator(IDevLibLocator devLibLocator) {
		this.devLibLocator = devLibLocator;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
		if (!enabled) {
			InterXIndexController.cancelIndexing();
		}
	}

	public static boolean isTestMode() {
		return testMode;
	}

	public static void setTestMode(boolean testMode) {
		HoverManager.testMode = testMode;
	}

	public static HoverManager getInstance() {
		return instance;
	}
	
	public static boolean isJunitRunning() {
		boolean result = false;
		TestableObject testableObject = PlatformUI.getTestableObject();
		if (testableObject != null) {
			result = testableObject.getTestHarness() != null;
		}
		return result;
	}
}
