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
package com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;

/**
 * This class provides a common approach for URL related operations.
 */
final public class URLHelper {

	public static String getFullPath(String relPath) throws Exception {
		return HoverManager.getInstance().getDevLibLocator().getFullAddress(
				relPath).toString();
	}

	public static URL getMapperURL(String path) throws MalformedURLException {
		URL resource = null;
		String fullMapperPath = path
				+ HoverConstants.DEFAULT_INTERCHANGE_FILE_NAME;

		File f = new File(fullMapperPath);
		if (f.exists()) {
			resource = f.toURL();
		}
		return resource;
	}

	/**
	 * Check if resource specified exists by opening an input stream to the
	 * resource
	 * 
	 * @param urlStr
	 *            fully valid URL address
	 * @return true if URL points to an existing resource
	 */
	public static boolean validURL(String urlStr) {
		URL url;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			return false;
		}
		return validURL(url);
	}

	/**
	 * Check if resource specified exists by opening an input stream to the
	 * resource
	 * 
	 * @param url
	 *            URL address
	 * @return true if URL points to an existing resource
	 */

	public static boolean validURL(URL url) {
		URLConnection con = null;
		InputStream inputStream = null;
		try {
			con = url.openConnection();
			con.connect();
			inputStream = con.getInputStream();
		} catch (IOException e) {
			return false;
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
			return true;
		}

		return false;
	}

	/**
	 * Convert URL address to a standard portable path for a given URL address
	 * according to file system,
	 * 
	 * @param url
	 *            URL address which points a file system resource
	 * @return Full path of file system resource
	 */
	public static String toFileSystemPath(URL url) {
		if (url == null) {
			return null;
		}
		return toFileSystemPath(url.getFile());
	}

	/**
	 * Retrieve a standard portable path for a given address according to file
	 * system,
	 * 
	 * @param p
	 *            address which points a file system resource
	 * @return Full path of file system resource
	 */
	public static String toFileSystemPath(String p) {
		if (p == null) {
			return p;
		}
		IPath pathCurrentSDK = new Path(p);
		return pathCurrentSDK.toPortableString();
	}

	/**
	 * Append a relative path to a URL
	 * 
	 * @param parentURL
	 *            parent URL
	 * @param relPath
	 *            relative path will be added to parent URL
	 * @return Combination of parentURL and relative path
	 * @throws MalformedURLException
	 */
	public static URL append(URL parentURL, String relPath)
			throws MalformedURLException {

		String path = parentURL.getPath();
		if (!(path.endsWith("/") || path.endsWith("\\")
				|| relPath.startsWith("/") || relPath.startsWith("\\"))) {
			relPath = "/" + relPath;
		}
		String urlStr = path + relPath;
		URL url = new URL(parentURL.getProtocol(), parentURL.getHost(),
				parentURL.getPort(), urlStr);
		return url;
	}

	/**
	 * Check if given address refers to a jar file and has valid format
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isURLJAR(URL url) {
		String urlStr = url.toString().toLowerCase(Locale.ENGLISH);
		if (urlStr.startsWith("jar:")) {
			return true;
		}
		return false;
	}
}
