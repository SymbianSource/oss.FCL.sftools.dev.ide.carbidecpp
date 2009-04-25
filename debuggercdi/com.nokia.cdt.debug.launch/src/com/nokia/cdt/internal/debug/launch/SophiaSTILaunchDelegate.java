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

import java.io.File;

import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.freescale.cdt.debug.cw.CWException;
import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.freescale.cdt.debug.cw.core.cdi.model.Target;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.debug.cw.symbian.SymbianPlugin;

import cwdbg.PreferenceConstants;

public class SophiaSTILaunchDelegate extends NokiaAbstractLaunchDelegate {

	public void launch(ILaunchConfiguration config, 
					   String mode,
					   ILaunch launch, 
					   IProgressMonitor monitor) throws CoreException {
		

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
	        try {
	            monitor.worked(1);
	            IPath exePath = verifyProgramPath(config);
	            ICProject project = verifyCProject(config);
	            IBinaryObject exeFile = verifyBinary(project, exePath);
	            String arguments[] = getProgramArgumentsArray(config);

				// See comment for this method for more.
	            SettingsData.setInternalPreferences(config, SettingsData.LaunchConfig_SophiaSTI);
	            			
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
						
						IPath[] otherExecutables = getOtherExecutables(project, exePath, config, monitor);
						{
							try {
								monitor.worked(1);
								
								// if enabled in the prefs, show the console view(s)
								if (config.getAttribute(SettingsData.spn_SophiaSTIConn_LogOption, false)) {
									SymbianPlugin.getDefault().openSTICommLogConsole(true);
								}
								if (config.getAttribute(PreferenceConstants.J_PN_LogUnresolved, false)) {
									SymbianPlugin.getDefault().openSymbianRomLogConsole(true);
								}
								
								config = synchronizeWithProjectAccessPaths(project, config);
								
								File wd = getWorkingDirectory(config);
								Target target = cwDebugSession.launchExecutable(launch, config, exeFile, otherExecutables, arguments, wd, getEnvironmentAsProperty(config), monitor, project, getTargetLabel(exeFile.getName()), true);
								ATFLaunchSupport.saveDebugTargetFromLaunchDelegate(target.getCoreModelTarget());

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
	            }
	            else if (mode.equals(ILaunchManager.RUN_MODE)) {
	                // Run the program.
	            	// Connect to DE, download and launch the program, close debug session.
	                ICDebugConfiguration debugConfig = getDebugConfig(config);
	                ICDISession dsession = null;
	                
	                // Set up communication with DE.
	                dsession = debugConfig.createDebugger().createDebuggerSession(launch, exeFile,
	                            new SubProgressMonitor(monitor, 8));

	                // Launch the program through the DE.
	                Session cwDebugSession = (Session)dsession;
					cwDebugSession.launchExecutable(
											launch, 
											config, 
											exeFile, 
											new IPath[0], 
											arguments, 
											null, 
											getEnvironmentAsProperty(config), 
											monitor, 
											project, 
											"",  //$NON-NLS-1$
											false  /* run instead of debug */);
					
					// The above call would throw exception on error. So it must have succeeded 
					// if control gets here. Show success message in a dialog.
		    		//
		    		// get the name of the executable that's launched instead of, say, the DLL name if
		    		// exeFile is a DLL.
		    		final String exeLaunched = config.getAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, exeFile.toString());
					Display display = Display.getCurrent();
					if (display == null) {
						display = Display.getDefault();
					}
					
					display.syncExec(new Runnable() {
						public void run() {
							MessageDialog.openInformation(
								CUIPlugin.getActiveWorkbenchShell(),
								LaunchMessages.getString("CarbideCPPLaunchDelegate.DebuggerName"),  //$NON-NLS-1$
								LaunchMessages.getString("TRKLaunchDelegate.runSucceed") + //$NON-NLS-1$
							           "\n\t\"" + exeLaunched + "\""); //$NON-NLS-1$ //$NON-NLS-2$
						}
					});
	            }
	        } catch (CWException e) {
	        	if (! monitor.isCanceled()) // don't throw on user cancellation
	        		e.printStackTrace();
			} catch (CoreException e) {
	        	if (! monitor.isCanceled()) // don't throw on user cancellation
	        		throw e;
			} finally {
	            monitor.done();
	        }
		} // end of synchronized.
	}
}