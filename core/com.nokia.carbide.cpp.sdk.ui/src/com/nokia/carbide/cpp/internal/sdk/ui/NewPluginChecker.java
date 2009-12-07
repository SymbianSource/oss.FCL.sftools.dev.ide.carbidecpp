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
package com.nokia.carbide.cpp.internal.sdk.ui;

import com.nokia.carbide.cpp.internal.sdk.core.model.DynamicFeatureInstaller;
import com.nokia.carbide.cpp.internal.sdk.core.model.InstallationFailureException;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.ui.SDKUIPlugin;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

@SuppressWarnings("restriction")
public class NewPluginChecker {

	private static final String SDK_FEATURE_SUBDIR = "epoc32/kit/feature";  //$NON-NLS-1$
	
	public static void checkForNewlyInstalledPlugins(final IWorkbench workbench){
		
		if (WorkbenchUtils.isJUnitRunning()){
			return;
		}
		
		final List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		Job job = new Job("Checking installed SDKs for plugins") { 
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				boolean installed = false;
				boolean oneSDKWasScanned = false;
				for (ISymbianSDK sdk : sdkList) {
					
					if (sdk.isPreviouslyScanned() == false){
						oneSDKWasScanned = true;
						// XML was parsed, now try to run the feature installer
						try {
							sdk.setPreviouslyScanned(true);
							String eclipsePluginsPath = sdk.getEPOCROOT() + SDK_FEATURE_SUBDIR;
							DynamicFeatureInstaller installer = new DynamicFeatureInstaller(new File(eclipsePluginsPath), null);
							if (installer.install()) {
								installed = true;
							}
	// Boog 8383: We should fail silently, since this will not break anything and may SDKs will not have any documentation
	// Otherwise, these errors will be logged every time this check is done (workspace is opened)
	// Originally, this was used to install MBS build support, but now is only used for SDK documentation
						} catch (MalformedURLException e) {
	//						ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, "Unable to install plug-ins dynamically.", e));
						} catch (FileNotFoundException e) {
	//						ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, "Unable to install plug-ins dynamically.", e));
						} catch (InstallationFailureException e) {
	//						ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, "Unable to install plug-ins dynamically.", e));
						}
					}
					
				}
				
				if (oneSDKWasScanned) {
					SDKCorePlugin.getSDKManager().updateCarbideSDKCache();
				}
				if (installed) {
					// plugins from some SDK were installed
					doEclipseRestartDialog(workbench);
				}
				
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	public static void doEclipseRestartDialog(final IWorkbench workbench) {
		if (WorkbenchUtils.isJUnitRunning()){
			// Don't show the restart dialog if JUnit is running
			return;
		}
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					Shell shell = window.getShell();
					// Possible Enhancement: Set the SDKs that have new features so they can be displayed to the user
					RestartIDEDialog.show(shell,
							Messages.getString("NewPluginChecker.New_Plugins_Installed") + //$NON-NLS-1$
					Messages.getString("NewPluginChecker.Restart_Msg")); //$NON-NLS-1$
				} else {
					ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.WARNING, SDKUIPlugin.PLUGIN_ID, IStatus.WARNING, Messages.getString("NewPluginChecker.Restart_Error"), null)); //$NON-NLS-1$
				}
			}
		});
	}
}
