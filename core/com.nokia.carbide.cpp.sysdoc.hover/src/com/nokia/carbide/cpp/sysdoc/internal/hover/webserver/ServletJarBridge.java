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
package com.nokia.carbide.cpp.sysdoc.internal.hover.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.view.InputUpdater;

/**
 * Class used by servlet to access content of a developer library plug-in.
 */
public class ServletJarBridge {
	private URL devLibURL;
	// for junit tests
	private static boolean testMode = HoverManager.isTestMode();

	/**
	 * Transfer request to developer library plug-in.
	 * 
	 * @param req
	 *            Servlet request
	 * @param resp
	 *            Servlet response
	 * @throws IOException
	 */
	public void transfer(HttpServletRequest req, HttpServletResponse resp,
			URL devLibURL) throws IOException {

		if (testMode) {
			testMode(req, resp);
			return;
		}

		this.devLibURL = devLibURL;
		try {
			String url = getURL(req);
			if (url == null)
				return;

			// if not valid URL then
			if (!URLHelper.validURL(url)) {
				url = InputUpdater
						.getInitializationNotFoundResurce(
								"<i>" + url + "</i>" + "<br>"
										+ MessagesConstants.RESOURCE_NOT_FOUND
										+ "<br>").getURL().toString();
			}

			url = redirectIfCSSFile(url);

			if (url.toLowerCase(Locale.ENGLISH).startsWith("file:/")
					|| url.toLowerCase(Locale.ENGLISH).startsWith("jar:file:/")) {
				int i = url.indexOf('?');
				if (i != -1)
					url = url.substring(0, i);
			} else {
				throw new HoverException(
						MessagesConstants.DEV_LIB_IS_NOT_JAR_URL + ":" + url);
			}
			URLConnection con = openConnection(url, req, resp);
			resp.setContentType(con.getContentType());
			long maxAge = 0;
			try {
				// getExpiration() throws NullPointerException when URL is
				// jar:file:...
				long expiration = con.getExpiration();
				maxAge = (expiration - System.currentTimeMillis()) / 1000;
				if (maxAge < 0)
					maxAge = 0;
			} catch (Exception e) {
			}
			resp.setHeader("Cache-Control", "max-age=" + maxAge);

			InputStream is = null;
			try {
				is = con.getInputStream();
			} catch (IOException ioe) {
				Logger.logError(ioe);
			}
			OutputStream out = resp.getOutputStream();
			transferContent(is, out);
			out.flush();
			is.close();

		} catch (Exception e) {
			Logger.logError(e);
		}
	}

	public static void setTestMode(boolean testMode) {
		ServletJarBridge.testMode = testMode;
	}

	private void testMode(HttpServletRequest req, HttpServletResponse resp) {
		try {
			OutputStream out = resp.getOutputStream();
			InputStream is = new ByteArrayInputStream(
					HoverConstants.TEST_CONTENT.getBytes());
			transferContent(is, out);
			out.flush();
			is.close();
		} catch (Exception e) {

		}
	}

	/**
	 * Write the body to the response
	 */
	private void transferContent(InputStream inputStream, OutputStream out)
			throws IOException {
		try {
			BufferedInputStream dataStream = new BufferedInputStream(
					inputStream);

			byte[] buffer = new byte[4096];
			int len = 0;
			while (true) {
				len = dataStream.read(buffer); // Read file into the byte array
				if (len == -1)
					break;
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			Logger.logError(e);
		}
	}

	/**
	 * Gets content from the named url (this could be and eclipse defined url)
	 */
	private URLConnection openConnection(String url,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		URLConnection con = null;
		URL helpURL;

		helpURL = new URL(url);
		con = helpURL.openConnection();
		con.setAllowUserInteraction(false);
		con.setDoInput(true);
		con.connect();
		return con;
	}

	/**
	 * Extract relative path from request and construct a full URL address
	 * referring to resource in developer library
	 * 
	 * @param req
	 *            Servlet request
	 * @return full address referring to resource in developer library
	 */
	private String getURL(HttpServletRequest req) {
		String query = "";
		String pathInfo = req.getPathInfo();
		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
		String url = this.devLibURL.toString() + pathInfo + query;
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		return url;
	}

	/**
	 * If request pointing out original developer library CSS styles, then it is
	 * redirected specialised hover help styles,css
	 * 
	 * @param url
	 *            requested resource in developer library
	 * @return a redirected hover css resources if original developer library
	 *         resource is asked, otherwise return same resource
	 */
	private String redirectIfCSSFile(String url) {
		for (String css : HoverConstants.DEVLIB_CSS_FILE_NAMES) {
			if (url.endsWith(css)) {
				String interXRootDir = HoverManager.getInstance()
						.getDevLibLocator().getInterXRootDir();
				String urlHover = null;
				try {
					urlHover = URLHelper
							.append(
									devLibURL,
									interXRootDir
											+ HoverConstants.HOVER_CSS_PATH
											+ css).toString();
				} catch (MalformedURLException e) {
				}
				if (urlHover != null && URLHelper.validURL(urlHover)) {
					url = urlHover;
				} else {
					Logger.logWarn(MessagesConstants.NOT_AVAILABLE_HOVER_CSS);
				}
				return url;
			}
		}
		return url;
	}

}
