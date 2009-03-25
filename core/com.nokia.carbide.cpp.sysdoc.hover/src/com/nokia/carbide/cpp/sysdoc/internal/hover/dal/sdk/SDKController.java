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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.sdk;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;

/**
 * This class provides utilities for discovering installed SDK, validation, and
 * holder of active current SDL.
 * 
 * SDK are used for resolving binding of a keyword, to check if API is part of
 * installed SDK. @see SDKAgent.isResourceInSDKDirectory method for further
 * information on API binding resolution.
 */
final public class SDKController {

	private static SDKController instance = new SDKController();

	private Set<URL> availableSDKList = new HashSet<URL>();
	private ISDKManager sdkMgr;
	private ICarbideInstalledSDKChangeListener sdkListChangeListener;

	private SDKController() {
		sdkMgr = SDKCorePlugin.getSDKManager();
	}

	/**
	 * SDKController class is singleton,
	 * 
	 * @return A instance of singleton SDKController.
	 */
	public static SDKController getInstance() {
		return instance;
	}

	/**
	 * Main entry method in the class, it probes installed SDKs, set initial SDL
	 * 
	 * @throws Exception
	 */
	public void initSDKDirectories() throws Exception {
		availableSDKList.clear();

		sdkListChangeListener = new ICarbideInstalledSDKChangeListener() {
			public void installedSdkChanged(SDKChangeEventType arg0) {
				Logger.logDebug("SDK has changed");
				try {
					probeAllAvailableSystemSDKDirectories();
				} catch (Exception e){
					
				}
			}
		};
		probeAllAvailableSystemSDKDirectories();
	}


	/**
	 * Get all available SDK directories which is both installed and custom SDKs
	 * 
	 * @return : Get all SDK directories.
	 */
	public Set<URL> getAvailableAllSDKDirectories() {
		return availableSDKList;
	}

	/**
	 * Gets SDK directories which has valid developer libraries. In a valid SDK,
	 * developer libraries (interchange file) should be under
	 * "developerlibrary/standard/" directory. Note, this method is future
	 * release where SDK includes a valid developer library
	 * 
	 * @return List of valid SDL directories and resources
	 */
	public List<URL> getValidSDKHelpRootDirectories() {
		List<URL> validSDKHelpDirs = new ArrayList<URL>();

		for (URL sdkDir : availableSDKList) {
			try {
				URL indexFile = checkSDKHelpDirIsValid(sdkDir);
				if (indexFile != null) {
					validSDKHelpDirs.add(indexFile);
				}
			} catch (Exception e) {

			}
		}
		return validSDKHelpDirs;
	}

	/**
	 * Check if an SDK directory has valid developer library in
	 * "developerlibrary/standard/" subdirectory.
	 * 
	 * @param sdkDir
	 *            : SDK directory
	 * @return URL of interchange file in SDL if valid otherwise return null.
	 */
	private URL checkSDKHelpDirIsValid(URL sdkDir) {
		URL mapperURL = null;
		try {
			mapperURL = URLHelper.getMapperURL(sdkDir.getPath()
					+ HoverConstants.DEFAULT_SDK_DOC_HOVER_DIR);
		} catch (MalformedURLException e) {
			return null;
		}
		return mapperURL;
	}

	/**
	 * Discovers all available installed SDKs which are used by Carbide.
	 * 
	 * @throws MalformedURLException
	 */
	public void probeAllAvailableSystemSDKDirectories() {
		sdkMgr.addInstalledSdkChangeListener(sdkListChangeListener);
		List<ISymbianSDK> sdkList = sdkMgr.getSDKList();

		for (ISymbianSDK currSDK : sdkList) {
			String epocRootStr = currSDK.getEPOCROOT();
			try {
				addDirToSDKDirectoryList(epocRootStr);
			} catch (Exception e){
				continue;
			}
		}	
			
		if (availableSDKList.isEmpty()){
			throw new HoverException(MessagesConstants.NOT_AVAILABLE_SDK);
		}		
	}

	/**
	 * An utility method to add a directory to SDK list
	 * 
	 * @param dir
	 * @throws MalformedURLException
	 */
	private void addDirToSDKDirectoryList(String dir)
			throws MalformedURLException {
		File f = new File(dir);
		URL url = f.toURL();
		addDirToSDKDirectoryList(url);
	}

	private void addDirToSDKDirectoryList(URL url) {
		availableSDKList.add(url);
		Logger.logDebug(" SDK DIR: " + url.toString());
	}

	/**
	 * Check if given file/resource is in the SDK directory
	 * 
	 * @param resources
	 *            : An array of resources. Each resource represents an header or
	 *            source file path
	 * @return true if any resource is in the SDK directory
	 * 
	 */
	public boolean isResourceInSDKDirectory(String... resources) {
		
		Set<URL> allSDKs = getAvailableAllSDKDirectories();

		if (allSDKs.isEmpty()){
			HoverManager.getInstance().haltHoveringService(MessagesConstants.NOT_AVAILABLE_SDK);			
			return false;
		}
		
		for (URL sdkDir : allSDKs) {
			for (String res : resources) {
				if (res == null)
					continue;
				String resPath = URLHelper.toFileSystemPath(res);
				String sdkPath = URLHelper.toFileSystemPath(sdkDir);

				if (resPath != null && resPath.contains(sdkPath)) {
					return true;
				}
			}
		}
		return false;
	}
}
