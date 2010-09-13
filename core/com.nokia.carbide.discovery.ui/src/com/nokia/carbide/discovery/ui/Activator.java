/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.discovery.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.cpp.internal.api.utils.core.ProxyUtils;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.discovery.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private static final String PROPERTY_PROXYPORT = "network.proxy_port"; //$NON-NLS-1$
	private static final String PROPERTY_PROXYHOST = "network.proxy_host"; //$NON-NLS-1$

	private boolean proxyDataAvailable;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		plugin = this;
		super.start(context);
		Job j = new Job(Messages.Activator_GetProxyInfoJobTitle) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IProxyData proxyData = ProxyUtils.getProxyData(new URI("http://www.yahoo.com")); //$NON-NLS-1$
					if (proxyData != null) {
						System.setProperty(PROPERTY_PROXYHOST, proxyData.getHost());
						System.setProperty(PROPERTY_PROXYPORT, String.valueOf(proxyData.getPort()));
					}
				} catch (URISyntaxException e) {
				} //$NON-NLS-1$
				setProxyDataAvailable();
				return Status.OK_STATUS;
			}
		};
		j.setSystem(true);
		j.setUser(false);
		j.schedule();
	}

	private synchronized void setProxyDataAvailable() {
		proxyDataAvailable = true;
	}
	
	public synchronized boolean isProxyDataAvailable() {
		return proxyDataAvailable;
	}

	public static void runInUIThreadWhenProxyDataSet(final Control control, final Runnable r) {
		Job j = new Job("") { //$NON-NLS-1$
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				setBusyCursor(control, true);
				Activator activator = getDefault();
				while (!activator.isProxyDataAvailable()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
				setBusyCursor(control, false);
				Display.getDefault().asyncExec(r);
				return Status.OK_STATUS;
			}
		};
		j.setSystem(true);
		j.setUser(false);
		j.schedule();
	}
	
	public static void setBusyCursor(final Control control, final boolean isBusy) {
		if (control == null)
			return;
		final Display display = control.getDisplay();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				control.setCursor(isBusy ? display.getSystemCursor(SWT.CURSOR_WAIT) : null);
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
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
	 * Log to error log
	 * @param message
	 * @param t
	 */
	public static void logError(String message, Throwable t) {
		getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, t));
	}

	/**
	 * Get a value from the server.properties file
	 * @param key
	 * @return
	 */
	public static String getFromServerProperties(String key) {
		Location installLocation = Platform.getInstallLocation();
		URL url = installLocation.getURL();
		IPath path = new Path(url.getPath());
		path = path.append("configuration/server.properties"); //$NON-NLS-1$
		File file = path.toFile();
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream(file);
			properties.load(is);
			is.close();
		} catch (IOException e) {
			String message = 
				MessageFormat.format(Messages.Activator_MissingConfigURLError, key);
			Activator.logError(message, e);
		}
		return (String) properties.get(key);
	}

    public static IStatus makeErrorStatus(String message, Throwable t) {
    	return makeStatus(IStatus.ERROR, message, t);
    }

    public static IStatus makeStatus(int severity, String message, Throwable t) {
    	return new Status(severity, PLUGIN_ID, message, t);
    }
}
