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

package com.nokia.carbide.cpp.internal.news.reader;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.news.reader.editor.NewsEditor;
import com.nokia.carbide.cpp.internal.news.reader.feed.FeedManager;
import com.nokia.carbide.cpp.internal.news.reader.ui.NewsControlContribution;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * The activator class controls the plug-in life cycle.
 */
public class CarbideNewsReaderPlugin extends AbstractUIPlugin implements IStartup {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.news.reader";

	// The shared instance
	private static CarbideNewsReaderPlugin plugin;

	private static FeedManager feedManager;

	private static IPreferenceStore prefsStore;

	/**
	 * The constructor.
	 */
	public CarbideNewsReaderPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup(){
		if (NewsEditor.isLaunchedAtStartup()) {
			launchNewsPage();
		}
		loadFeeds();
	}

	/**
	 * Returns the shared instance.
	 * @return the shared instance
	 */
	public static CarbideNewsReaderPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the shared feed manager.
	 * @return the shared feed manager
	 */
	public static FeedManager getFeedManager() {
		if (feedManager == null) {
			feedManager = new FeedManager();
		}
		return feedManager;
	}

	/**
	 * Returns the full path of this plugin.
	 * @return IPath object representing full path of this plugin
	 */
	public static IPath getPluginInstallLocation() {
		try {
			URL pluginURL = FileLocator.find(getDefault().getBundle(), new Path("/"), null);
			pluginURL = FileLocator.resolve(pluginURL);
			String pluginFilePath = pluginURL.getFile();
			Path pluginPath = new Path(pluginFilePath);
			return pluginPath;
		} catch (IOException e) {
			log(e);
		}
		return null;
	}

	/**
	 * Returns the shared preference store of this plugin.
	 * @return the shared preference store of this plugin
	 */
	public static IPreferenceStore getPrefsStore(){
		if (prefsStore == null){
			prefsStore = getDefault().getPreferenceStore();
		}
		return prefsStore;
	}

	/**
	 * Launch the Carbide.c++ news page.
	 */
	public static void launchNewsPage() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					NewsEditor.openEditor();
					NewsControlContribution control = NewsControlContribution.getDefault();
					if (control != null) {
						control.update();
					}
				}
			}
		});
	}

	/**
	 * Load feeds in a non-UI thread.
	 */
	public static void loadFeeds() {
		String message = Messages.Plugin_LoadFeeds_JobMessage;
		Job job = new Job(message) {
			protected IStatus run(IProgressMonitor monitor) {
				getFeedManager().loadFeeds();
				return new Status(IStatus.OK, PLUGIN_ID, Messages.Plugin_LoadFeeds_JobFinishedMessage);
			}
		};
		job.schedule();
	}

	/**
	 * Log an error or exception.
	 * @param e - object associated with the error or exception
	 */
	static public void log(Throwable e) {
		Logging.log(plugin, Logging.newStatus(plugin, e));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		getFeedManager().saveFeeds();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Update feeds in a non-UI thread.
	 */
	public static void updateFeeds() {
		String message = Messages.Plugin_UpdateFeeds_JobMessage;
		Job job = new Job(message) {
			protected IStatus run(IProgressMonitor monitor) {
				getFeedManager().saveFeeds();
				getFeedManager().updateFeeds();
				return new Status(IStatus.OK, PLUGIN_ID, Messages.Plugin_UpdateFeeds_JobFinishedMessage);
			}
		};
		job.schedule();
	}

}
