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
* The activator class controls the plug-in life cycle
*
*/
package com.nokia.carbide.cpp.internal.qt.core;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerLoadedHook;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.trolltech.qtcppproject.QtNature;

public class QtCorePlugin extends Plugin implements ICarbideInstalledSDKChangeListener, ISDKManagerLoadedHook {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.qt.core"; //$NON-NLS-1$

	public static final String QT_PROJECT_NATURE_ID = QtNature.QT_NATURE_ID;

	// The shared instance
	private static QtCorePlugin plugin;
	
	/**
	 * The constructor
	 */
	public QtCorePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		SDKCorePlugin.getSDKManager().removeInstalledSdkChangeListener(this);
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static QtCorePlugin getDefault() {
		return plugin;
	}
	
	public static void addQtNature(IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] prevNatures = description.getNatureIds();
		for (String prevNature : prevNatures) {
			if (QtCorePlugin.QT_PROJECT_NATURE_ID.equals(prevNature)) {
				return;
			}
		}
		String[] newNatures = new String[prevNatures.length + 1];
		System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
		newNatures[prevNatures.length] = QtCorePlugin.QT_PROJECT_NATURE_ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, monitor);
	}

	private void scanForQtSDKs() {
		
		final String jobBaseText = "Checking for Qt installed in Symbian SDKs";
		Job job = new Job(jobBaseText) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager()
						.getSDKList();
				for (ISymbianSDK sdk : sdkList) {
					QtSDKUtils.addQtSDKForSymbianSDK(sdk, false);
				}

				return Status.OK_STATUS;
			}

		};
		job.schedule();
	}
	
	public void symbianSDKManagerLoaded() {
		scanForQtSDKs();
		SDKCorePlugin.getSDKManager().addInstalledSdkChangeListener(this);
	}

	public void installedSdkChanged(SDKChangeEventType eventType) {
		scanForQtSDKs();
	}

}
