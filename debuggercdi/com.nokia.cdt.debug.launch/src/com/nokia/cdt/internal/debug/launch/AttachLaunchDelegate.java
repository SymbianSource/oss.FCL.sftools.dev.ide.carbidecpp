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
package com.nokia.cdt.internal.debug.launch;

import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.swt.widgets.Display;

import com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper;
import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.freescale.cdt.debug.cw.core.os.OSProcess;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.debug.cw.symbian.SymbianPlugin;
import com.nokia.cdt.internal.debug.launch.ui.ChooseProcessDialog;

import cwdbg.PreferenceConstants;

public class AttachLaunchDelegate extends TRKLaunchDelegate {

	private OSProcess attachTarget;

	@Override
	public void launch(ILaunchConfiguration config, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		// See comment at definition of the "mutex" for why this "synchronized".
		synchronized(Session.sessionStartStopMutex()) {

			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}

	        monitor.beginTask(LaunchMessages.getString("LocalRunLaunchDelegate.Launching_Local_C_Application"), 10); //$NON-NLS-1$
			// check for cancellation
			if (monitor.isCanceled()) {
				return;
			}

	        if (!RemoteConnectionsTRKHelper.configUsesConnectionAttribute(config)) {
	        	config = RemoteConnectionsTRKHelper.attemptUpdateLaunchConfiguration(config.getWorkingCopy());
	        }
	        
	        final IConnection connection = RemoteConnectionsTRKHelper.getConnectionFromConfig(config);
			if (connection == null) {
				IStatus status = new Status(Status.ERROR, LaunchPlugin.PLUGIN_ID, 
					LaunchMessages.getString("TRKLaunchDelegate.NoConnectionErrorMsg")); //$NON-NLS-1$
				throw new DebugException(status);
			}
			connection.useConnection(true);

			try {
	        	addBeingLaunched(config); // indicating the LC is being launched
	        	
				monitor.worked(1);
				IPath exePath = verifyProgramPath(config);
				ICProject project = verifyCProject(config);
				IBinaryObject exeFile = verifyBinary(project, exePath);

				// See comment for this method for more.
	            SettingsData.setInternalPreferences(config, SettingsData.LaunchConfig_AppTRK);
	            			
				// set the default source locator if required
				setDefaultSourceLocator(launch, config);

				if (mode.equals(ILaunchManager.DEBUG_MODE)) {
					// debug mode
					ICDebugConfiguration debugConfig = getDebugConfig(config);
					ICDISession dsession = null;
	                String debugMode = config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
									ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN);
	                if (debugMode.equals(ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN)) {
	                    dsession = debugConfig.createDebugger().createDebuggerSession(launch, exeFile,
										new SubProgressMonitor(monitor, 8));

						assert(dsession instanceof Session);
						Session cwDebugSession = (Session)dsession;

						doAdditionalSessionSetup(cwDebugSession);
						
						IPath[] otherExecutables = getOtherExecutables(project, new Path(""), config, monitor);
						{
							try {
								monitor.worked(1);

								// if enabled in the prefs, show the console view(s)
								if (config.getAttribute(PreferenceConstants.J_PN_ViewUnframedData, false)) {
									SymbianPlugin.getDefault().openUnframedDataConsole(true);
								}

								if (config.getAttribute(PreferenceConstants.J_PN_ViewCommMessages, false)) {
									SymbianPlugin.getDefault().openTRKCommLogConsole(true);
								}

								config = synchronizeWithProjectAccessPaths(project, config);

								// Connect to device
								cwDebugSession.connectRemote(monitor);

								// get processes on the device
								OSProcess[] processesOnTarget = cwDebugSession.getTargetProcesses();
								
								// Ask user to choose a process
								String defaultProcessName = exeFile.getPath().removeFileExtension().lastSegment();
								OSProcess attachTarget = chooseProcessTarget(processesOnTarget, defaultProcessName);
								
								if (attachTarget == null) {
									this.cancel(LaunchMessages.getString("LocalAttachLaunchDelegate.No_Process_ID_selected"), 0); //$NON-NLS-1$
								} else {
									String processName = attachTarget.parseProcess().getProcessName();
									
									for (IPath oExePath : otherExecutables) {								
										if (oExePath.lastSegment().startsWith(processName))
											exePath = oExePath;									
									}
									
									exeFile = verifyBinary(project, exePath);

									cwDebugSession.attachToProcess(
											attachTarget, launch, config,
											exeFile, otherExecutables, monitor,
											project,
											getTargetLabel(exeFile.getName()));
								}

							} catch (CoreException e) {
								Session session = (Session)dsession;
								session.cleanupAfterLaunchFailure();
								throw e;
							} catch (Exception e) {
								Session session = (Session)dsession;
								session.debuggingStopped(null);
								this.abort(e.getLocalizedMessage(), null, 0);
							}
						}
					}
				} else if (mode.equals(ILaunchManager.RUN_MODE)) {
					// run mode not supported for attach
				}
			} catch (CoreException e) {
	      		connection.useConnection(false);
	        	if (! monitor.isCanceled()) // don't throw on user cancellation
					throw e;
			} finally {
				monitor.done();
	            removeBeingLaunched(config);
			}
		} // end of synchronized.
	}

	private OSProcess chooseProcessTarget(final OSProcess[] processesOnTarget, final String defaultProcessName) {
		attachTarget = null;

		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				ChooseProcessDialog dialog = new ChooseProcessDialog(
						processesOnTarget, defaultProcessName, Display.getDefault().getActiveShell());
				int dialogResult = dialog.open();

				if (dialogResult == org.eclipse.jface.dialogs.Dialog.OK) {
					attachTarget = dialog.getSelectedProcess();
				}
			}
		});

		return attachTarget;
	}
}
