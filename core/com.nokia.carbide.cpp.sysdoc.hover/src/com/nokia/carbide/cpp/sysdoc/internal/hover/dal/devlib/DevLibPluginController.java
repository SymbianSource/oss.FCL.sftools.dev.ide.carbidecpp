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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXAnalyser;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;

/**
 * 
 * A controller class used for Developer Libraries plug-in for discovering and
 * validating. When the hover plug-in starts, it discovers installed developer
 * library plug-in by using this class. This class filters the plug-in filenames
 * of Carbide plug-in directory with "com.symbian.help.developer_library"
 * pattern in order to find out developer libraries.
 * 
 */
final public class DevLibPluginController {
	private static final DevLibPluginController instance = new DevLibPluginController();
	private Set<DevLibProperties> devLibPropertiesSet = new TreeSet<DevLibProperties>(
			new DevLibComparator());

	/**
	 * A singleton class
	 */
	private DevLibPluginController() {
		probeDefaultDevLibPlugins();
	}

	/**
	 * Get singleton instance
	 * 
	 * @return return singleton instance
	 */
	public static DevLibPluginController getInstance() {
		return instance;
	}

	/**
	 * Find out if any Developer Library plug-in is installed
	 * 
	 * @return true if SDL installed
	 */

	public boolean isDevLibPluginsAvailable() {
		Set<DevLibProperties> devLibsProps = getDevLibPropertiesSet();
		if (devLibsProps.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Find out if a Developer Library plug-in contains an interchange file
	 * which is in different location for public (sdk) and partner (standard)
	 * distributions.
	 * 
	 * @param parentDir
	 *            A root developer library path
	 * @return discovered interchange file's relative path. If no interchange
	 *         available, an HoverInitializationException exception is thrown
	 *         and hover plug-in is then will be deactivated
	 * 
	 * 
	 */
	private static String findOutInterXRelativePath(URL parentDir) {

		String[] defaultInterchangeFilePathsInJar = getDefaultInterchangeFilePathsInJar();
		for (String interchangeFiles : defaultInterchangeFilePathsInJar) {
			URL url;
			try {
				url = URLHelper.append(parentDir, interchangeFiles);
			} catch (MalformedURLException e1) {
				continue;
			}
			if (URLHelper.validURL(url)) {
				return interchangeFiles;
			}
		}
		throw new HoverException(
				"Can not locate any interchange file in extracted plugin directory.");
	}

	/**
	 * Probes set of installed Developer Library plug-ins. It checks Carbide
	 * plug-in directories and filters the jar plug-ins which matches
	 * "com.symbian.help.developer_library" pattern.
	 * 
	 * @return List of installed path
	 */
	public void probeDefaultDevLibPlugins() {

		Location installLocation = Platform.getInstallLocation();
		String path = installLocation.getURL().getPath();
		File ff = new File(path);
		Logger.logDebug("installation path" + ff.getAbsolutePath());
		File f = new File(path, "plugins");
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name
						.startsWith(HoverConstants.DeveloperLibraryPluginPrefix)) {
					return true;
				}
				return false;
			}
		};

		File[] list = f.listFiles(filter);
		for (File file : list) {
			try {
				// convert it to valid jar URL format
				String jarURL = "jar:" + file.toURL().toString() + "!/";
				URL url = new URL(jarURL);
				DevLibProperties devLibProp = extractDevLibProperties(url);
				if (devLibProp.isValid()) {
					devLibPropertiesSet.add(devLibProp);
				} else {
					Logger.logWarn("Developer library is not valid"
							+ url.toString());
				}
			} catch (MalformedURLException e) {
				continue;
			}
		}
	}

	/**
	 * Extract properties from a given URL which points out an developer library
	 * plug-in
	 * 
	 * @param devLibURL
	 *            Developer library plug-in url
	 * @return properties of given developer library plug-in
	 */
	private DevLibProperties extractDevLibProperties(URL devLibURL) {
		DevLibProperties devLibProp = new DevLibProperties(devLibURL);
		// until we are sure it is a valid dev lib, we mark it is invalid.
		devLibProp.setValid(false);
		// make sure it is a valid jar url
		if (!URLHelper.isURLJAR(devLibURL)) {
			return devLibProp;
		}

		// get relative path of interX file
		String interXFileRelPath = null;
		try {
			interXFileRelPath = findOutInterXRelativePath(devLibURL);
		} catch (Exception e) {
			return devLibProp;
		}

		// construct full address of interX file
		URL interchangeURL = null;
		try {
			interchangeURL = URLHelper.append(devLibURL, interXFileRelPath);
		} catch (MalformedURLException e) {
			return devLibProp;
		}
		// now lets get properties of interX file
		InterXProperties interXProperties;
		try {
			interXProperties = InterXAnalyser
					.getHeaderAttributes(interchangeURL);
		} catch (IOException e) {
			return devLibProp;
		}

		String interXRootDir = "";
		int pos = interXFileRelPath.indexOf("/");
		if (pos != -1) {
			interXRootDir = interXFileRelPath.substring(0, pos + 1);
		}

		devLibProp.setInterXRootDir(interXRootDir);
		devLibProp.setInterXProperties(interXProperties);
		// make sure developer library is valid
		devLibProp.setValid(interXProperties.isValid());
		return devLibProp;
	}

	/**
	 * Get set of installed Developer Library plug-ins
	 * 
	 * @return Set of installed developer libraries plug-in
	 */
	public Set<DevLibProperties> getDevLibPropertiesSet() {
		return devLibPropertiesSet;
	}


	/**
	 * Default interchange files in SDL libraries. It can be either in "sdk"
	 * (public distribution) nor "standard" (partner). It can be also in root
	 * for backward compatibility
	 * 
	 * @return A list of path of interchange files.
	 */
	public static String[] getDefaultInterchangeFilePathsInJar() {
		String indexFiles[] = new String[HoverConstants.JAR_HOVER_HELP_ROOT_DIR.length];

		for (int i = 0; i < HoverConstants.JAR_HOVER_HELP_ROOT_DIR.length; i++) {
			indexFiles[i] = getDefaultInterXFilePath(HoverConstants.JAR_HOVER_HELP_ROOT_DIR[i]);
		}
		return indexFiles;
	}

	private static String getDefaultInterXFilePath(String path) {
		return path + HoverConstants.DEFAULT_INTERCHANGE_FILE_NAME;
	}

	/**
	 * Among all installed developer libraries, find the one of which name is
	 * equal to provided user friendly name. This is generally called from
	 * preference page initialiser where previous developer library preference
	 * (user friendly name, label) is associated with a developer library
	 * 
	 * @param userFriendlyDevLibName
	 *            : Label used to display developer library in preference page
	 * @return developer library properties of which user friendly name is
	 *         provided. If not found, return null
	 */
	public DevLibProperties findDevLibPropFromLabel(
			String userFriendlyDevLibName) {
		DevLibProperties devLibProp = null;
		Set<DevLibProperties> symbianPluginHelpSet = getDevLibPropertiesSet();
		Iterator<DevLibProperties> iterator = symbianPluginHelpSet.iterator();
		while (iterator.hasNext()) {
			devLibProp = iterator.next();
			if (devLibProp.getUserFriendlyName().equals(userFriendlyDevLibName)) {
				return devLibProp;
			}
		}
		return null;
	}
}
