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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.ui.SDKUIPlugin;
import com.nokia.carbide.internal.discovery.ui.p2.DynamicP2Installer;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class NewPluginChecker {

	private static final String SDK_FEATURE_SUBDIR = "epoc32/kit";  //$NON-NLS-1$
	
	public static void checkForNewlyInstalledPlugins(final IWorkbench workbench){
		
		if (WorkbenchUtils.isJUnitRunning()){
			return;
		}
		
		final List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		if (sdkList.size() == 0){
			return;
		}
		Job job = new Job("Checking installed SDKs for plugins") { 
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				boolean installed = false;
				SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
				Collection<String> sdkIdList = new ArrayList<String>();
				int worked = 100 / sdkList.size();
				for (ISymbianSDK sdk : sdkList) {
					if (!sdk.isEnabled()){
						continue; // Don't bother scanning SDKs that are not enabled
					}
					ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
					if (sbsv2BuildInfo != null) {
						if (sbsv2BuildInfo.isPreviouslyScanned() == false) {
							// XML was parsed, now try to run the feature installer
							sbsv2BuildInfo.setPreviouslyScanned(true);
							File featureDir = new File(sdk.getEPOCROOT() + SDK_FEATURE_SUBDIR);
							try {
								IStatus status = DynamicP2Installer.install(sdk.getUniqueId(), featureDir, subMonitor);
								if (status.isOK()) {
									sdkIdList.add(sdk.getUniqueId());
									installed = true;
								}
								else if (status.getSeverity() == IStatus.CANCEL) {
									// TODO installation canceled 
								}
							} catch (Exception e) {
								// Boog 8383: We should fail silently, since this will not break anything and may SDKs will not have any documentation
								// Otherwise, these errors will be logged every time this check is done (workspace is opened)
								// Originally, this was used to install MBS build support, but now is only used for SDK documentation
		//						ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.PLUGIN_ID, IStatus.ERROR, "Unable to install plug-ins dynamically.", e));
							} finally {
								subMonitor.worked(worked);
							}
						}
					}
				}
				
				if (installed) {
					// plugins from some SDK were installed
					doEclipseRestartDialog(workbench, sdkIdList);
				}
				
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	public static void doEclipseRestartDialog(final IWorkbench workbench, Collection<String> sdkIdList) {
		if (WorkbenchUtils.isJUnitRunning()){
			// Don't show the restart dialog if JUnit is running
			return;
		}
		String message = Messages.getString("NewPluginChecker.New_Plugins_Installed");
		for (Iterator<String> itr = sdkIdList.iterator(); itr.hasNext();) {
			String sdkId = itr.next();
			message += sdkId + "\n";
		}
		RemoteConnectionsActivator.getStatusDisplay().displayStatusWithAction(
			SDKUIPlugin.makeStatus(IStatus.INFO, message, null),
			Messages.getString("NewPluginChecker.Restart_Msg"),
			new Runnable() {
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							PlatformUI.getWorkbench().restart();
						}
					});
				}
			}
		);
	}
}
