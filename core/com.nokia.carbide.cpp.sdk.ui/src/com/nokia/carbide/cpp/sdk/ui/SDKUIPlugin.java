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
package com.nokia.carbide.cpp.sdk.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.api.sdk.ICarbideDevicesXMLChangeListener;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.api.sdk.ui.SBSv1PlatformFilterComposite;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.internal.sdk.ui.SDKUIPreferenceConstants;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.ui.QueryWithTristatePrefDialog;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * The activator class controls the plug-in life cycle
 */
public class SDKUIPlugin extends AbstractUIPlugin implements IStartup, ICarbideDevicesXMLChangeListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.sdk.ui";  //$NON-NLS-1$

	// The shared instance
	private static SDKUIPlugin plugin;
	

	/**
	 * The constructor
	 */
	public SDKUIPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (HostOS.IS_WIN32){
			((SDKManager)sdkMgr).setPlatformList(SBSv1PlatformFilterComposite.getPlatFilterPrefsStore());
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr != null && sdkMgr instanceof ISDKManagerInternal){
			ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
			sdkMgrInternal.removeDevicesXMLChangeListener(this);
		}
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SDKUIPlugin getDefault() {
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
	
	public void earlyStartup(){
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr != null && sdkMgr instanceof ISDKManagerInternal){
			ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
			sdkMgrInternal.addDevicesXMLChangeListener(this);
		}
		
	}

	public void devicesXMLOutOfSync() {
		
		if (WorkbenchUtils.isJUnitRunning()){
			// Don't show this dialog if JUnit is running
			return;
		}
		
		IPreferenceStore store = getDefault().getPreferenceStore();
		if (!store.getBoolean(SDKUIPreferenceConstants.LISTEN_FOR_DEVICES_XML_CHANGE)){
			return;
			
		}
		Shell shell;
		IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			shell = window.getShell();
		} else {
			return;
		}
		
		QueryWithTristatePrefDialog query = new QueryWithTristatePrefDialog(shell, "Devices.xml change detected.",
				"The current devices.xml content is not the same as the SDK list in Carbide.\n\nDo you want to scan for SDK changes now?",
				(AbstractUIPlugin)SDKUIPlugin.getDefault(), SDKUIPreferenceConstants.LISTEN_FOR_DEVICES_XML_CHANGE,
				false, QueryWithTristatePrefDialog.QUERY_YES_NO);
		
		if (query.doQuery()){
			ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
			sdkMgr.scanSDKs();
			
			// User selected yes, make sure pref doesn't change
			store.setValue(SDKUIPreferenceConstants.LISTEN_FOR_DEVICES_XML_CHANGE, true);
		}
		
	}
}
