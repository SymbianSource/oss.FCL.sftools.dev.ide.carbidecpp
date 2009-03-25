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
package com.nokia.carbide.cpp.sysdoc.internal.hover.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.nokia.carbide.cpp.sysdoc.hover.dependencies.Activator;


/**
 * Provides utilities (such as opening an URL in an external browser) for
 * BrowserInformationControl class.
 */
final public class BrowserHelper {

	private static BrowserHelper instance = new BrowserHelper();
	private final IWorkbenchBrowserSupport browserSupport;

	private BrowserHelper() {
		browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
	}

	public static BrowserHelper getInstance() {
		return instance;
	}

	/**
	 * Open address in an external browser in eclipse 
	 * @param address which will be launched
	 */
	private void openExternalBrowser(final String address) {
		IWebBrowser browser = null;
		String myId = address;
		try {
			URL url = new URL(address);
			browser = browserSupport.createBrowser(myId);
			browser.openURL(url);
		} catch (Exception e) {
			Activator.log(e, "Can not open external browser:"+address);
			return;
		}
	}

	/**
	 * Open address in an external browser in eclipse  and dispose popup browser
	 * @param url: address to be opened in external browser
	 * @param shell of the browser 
	 */
	public void doOpenExternalBrowser(String url, Shell shell, boolean keepShell) {
		BrowserHelper.getInstance().openExternalBrowser(url);
		if (!keepShell){
			shell.dispose();
		}
	}


	/**
	 * SWT browser kicks of with a "about:blank" page. We dont want to do
	 * anything if the browser points out this address
	 * 
	 * @param loc
	 *            : address currently the browser pointing out
	 * @return true if it is "about:blank" page
	 */
	private boolean isAboutPage(String loc) {
		if (loc.equals("about:blank")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param loc
	 *            address which the browser currently pointing at
	 * @return true if the loc is not found resource page
	 */
	protected boolean isNotFoundPage(String loc) {
		if (loc.matches(".*notFound[/\\\\]notFound\\.html$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * If address is an about or waiting pages. 
	 * @param lastURL
	 * @return
	 */
	public boolean isAboutOrHoverAuxPage(String lastURL) {
		if (isAboutPage(lastURL)
				|| lastURL.matches(".*waiting[/\\\\]init\\.html$")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check if a referred page does exist on local system
	 * 
	 * @param loc address to be checked if it is locally exist
	 * @return true if loc (address) exist in local system
	 */
	private boolean checkURLLocallyExist(String loc) {

		// HTTP protocol... we are not interested in non-local pages
		if (loc.startsWith("http")) {
			return true;
		}

		// not interested aux pages
		if (BrowserHelper.getInstance().isAboutOrHoverAuxPage(loc)
				|| BrowserHelper.getInstance().isNotFoundPage(loc)) {
			return true;
		}

		// first of all, check, the location is it stands (without protocol
		// info) exist on local system.
		File f = new File(loc);
		if (f.exists()) {
			return true;
		}

		URI uri;
		try {
			uri = new URI(loc);
		} catch (URISyntaxException e) {
			return false;
		}
		try {
			String protocol = uri.toURL().getProtocol();
			// yeah, we are interested with only local files
			if (protocol.equals("file")) {
				String path = uri.toURL().getPath();
				f = new File(path);
				if (!f.exists()) {
					return false;
				}
			}
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}		
}
