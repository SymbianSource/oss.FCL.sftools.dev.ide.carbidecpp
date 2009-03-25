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
package com.nokia.carbide.cpp.debug.crashdebugger;

import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.NokiaAbstractLaunchDelegate;

public class CrashDebuggerLaunchDelegate extends NokiaAbstractLaunchDelegate {

	protected String getPluginID() {
		return CrashDebuggerPlugin.getUniqueIdentifier();
	}

	public void launch(ILaunchConfiguration config, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		// See comment at definition of the "mutex" for why this "synchronized".
		synchronized (Session.sessionStartStopMutex()) {

			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}
			monitor
					.beginTask(
							LaunchMessages
									.getString("LocalRunLaunchDelegate.Launching_Local_C_Application"), 10); //$NON-NLS-1$
			// check for cancellation
			if (monitor.isCanceled()) {
				return;
			}

			// See comment for this method for more.
            SettingsData.setInternalPreferences(config, SettingsData.LaunchConfig_CrashDebugger);
            			
			CrashDebuggerSession session = null;
			
			try {
	        	addBeingLaunched(config); // indicating the LC is being launched
	        	
				monitor.worked(1);

				// set the default source locator if required
				setDefaultSourceLocator(launch, config);

				// same handling, whether "mode" is "Debug" or "Run"
				//

				// Crash debugger connection
				ICDebugConfiguration debugConfig = getDebugConfig(config);

				// Set up crash debugger session.
				session = (CrashDebuggerSession) debugConfig
						.createDebugger().createDebuggerSession(launch, null, // IBinaryObject,
								// "null" means a DeviceCommandSession is
								// needed.
								new SubProgressMonitor(monitor, 8));

				if (session != null)
					doAdditionalSessionSetup(session);

				if (mode.equals(ILaunchManager.DEBUG_MODE)) {
					if (session != null) {
						// Open the console if not yet.
						OpenCrashDebuggerConsole();
					}
				}
			} catch (CoreException e) {
	        	if (! monitor.isCanceled()) // don't throw on user cancellation
					throw e;
			} finally {
				monitor.done();
	            removeBeingLaunched(config);
			}
		}
	}

	private void OpenCrashDebuggerConsole() {

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (window != null) {
					IWorkbenchPage page = window.getActivePage();
					if (page != null) {
						try {
							page
									.showView("com.nokia.carbide.cpp.debug.crashdebugger.ui.CrashDebuggerView");
						} catch (PartInitException e) {
						}
					}
				}
			}
		});		
	}
}
