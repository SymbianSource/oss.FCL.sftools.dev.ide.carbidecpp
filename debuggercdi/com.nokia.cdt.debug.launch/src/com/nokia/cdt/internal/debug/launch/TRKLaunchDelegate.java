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
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.freescale.cdt.debug.cw.CWException;
import com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper;
import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.freescale.cdt.debug.cw.core.cdi.model.Target;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.debug.cw.symbian.SymbianPlugin;
import com.nokia.cdt.internal.debug.launch.ui.PartialUpgradeAlertDialog;

import cwdbg.PreferenceConstants;

public class TRKLaunchDelegate extends NokiaAbstractLaunchDelegate {

	private static final String DONT_ASK_ABOUT_PARTIAL_UPGRADE_OPTION = "DONT_ASK_ABOUT_PARTIAL_UPGRADE_OPTION"; //$NON-NLS-1$
	private static final int LARGE_SIS_THRESHOLD = 250 * 1024; // 250K
	
	protected Session cwDebugSession;
	
	
	public void launch(
			ILaunchConfiguration 	config, 
			String 					mode, 
			ILaunch 				launch, 
			IProgressMonitor monitor) throws CoreException 
	{
	// See comment at definition of the "mutex" for why this "synchronized".		
	synchronized(Session.sessionStartStopMutex()) {

		cwDebugSession = null;

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
        
        final IConnection connection = RemoteConnectionsTRKHelper.ensureConnectionFromConfig(config);
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
            String arguments[] = getProgramArgumentsArray(config);

            // See comment for this method for more.
            SettingsData.setInternalPreferences(config, SettingsData.LaunchConfig_AppTRK);
            
            // set the partial upgrade pref value
            ILaunchConfigurationWorkingCopy workingCopy = config.getWorkingCopy();
            workingCopy.setAttribute(PreferenceConstants.J_PN_PUSisFileHostPath, ""); //$NON-NLS-1$
            
            String sisHostPath = config.getAttribute(PreferenceConstants.J_PN_SisFileHostPath, ""); //$NON-NLS-1$
            if (sisHostPath.length() > 0) {
            	// app trk launch - see if there's a partial upgrade sis file
            	IPath sisPath = new Path(sisHostPath);
            	String buildConfigName = config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, ""); //$NON-NLS-1$
				if (buildConfigName.length() > 0) {
			        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project.getProject());
			        if (cpi != null) {
			        	for (ICarbideBuildConfiguration buildConfig : cpi.getBuildConfigurations()) {
			        		if (buildConfig.getDisplayString().equals(buildConfigName)) {
								IPath puSisPath = CarbideCPPBuilder.getPartialUpgradeSisPath(buildConfig, sisPath);
								if (puSisPath != null && puSisPath.toFile().exists()) {
									workingCopy.setAttribute(PreferenceConstants.J_PN_PUSisFileHostPath, puSisPath.toOSString());
								}
								break;
			        		}
			        	}
			        }
				}
			}
            
            // save the changes
            workingCopy.doSave();
                        
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
					cwDebugSession = (Session)dsession;

					doAdditionalSessionSetup(cwDebugSession);
					
					IPath[] otherExecutables = getOtherExecutables(project, exePath, config, monitor);					
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
                cwDebugSession = (Session)dsession;
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
						connection.useConnection(false);
						MessageDialog.openInformation(
							CUIPlugin.getActiveWorkbenchShell(),
							LaunchMessages.getString("CarbideCPPLaunchDelegate.DebuggerName"),  //$NON-NLS-1$
							LaunchMessages.getString("TRKLaunchDelegate.runSucceed") + //$NON-NLS-1$
						           "\n\t\"" + exeLaunched + "\""); //$NON-NLS-1$ //$NON-NLS-2$
					}
				});
            }
        } catch (CWException e) {
       		connection.useConnection(false);
        	if (! monitor.isCanceled()) // don't throw on user cancellation
        		e.printStackTrace();
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

	@Override
	public boolean buildForLaunch(final ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		// for app trk, check to see if we need to ask the user if they want to enable
		// partial upgrade builds
        String sisHostPath = configuration.getAttribute(PreferenceConstants.J_PN_SisFileHostPath, ""); //$NON-NLS-1$
        if (sisHostPath.length() > 0) {
        	// app trk launch - see if the partial upgrade option is enabled
        	IPath sisPath = new Path(sisHostPath);
        	String buildConfigName = configuration.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, ""); //$NON-NLS-1$
			if (buildConfigName.length() > 0) {
	            final ICProject project = verifyCProject(configuration);
		        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project.getProject());
		        if (cpi != null) {
		        	for (ICarbideBuildConfiguration buildConfig : cpi.getBuildConfigurations()) {
		        		if (buildConfig.getDisplayString().equals(buildConfigName)) {
							if (null == CarbideCPPBuilder.getPartialUpgradeSisPath(buildConfig, sisPath)) {
								if (!configuration.getAttribute(DONT_ASK_ABOUT_PARTIAL_UPGRADE_OPTION, false)) {
									// get the size of the sis file if it exists
									File sisFile = sisPath.toFile();
									if (sisFile.exists() && sisFile.length() > LARGE_SIS_THRESHOLD) {
										// it's larger than the threshold so present dialog.
										
										Display.getDefault().syncExec(new Runnable() {

											public void run() {
												PartialUpgradeAlertDialog dlg = new PartialUpgradeAlertDialog(CUIPlugin.getActiveWorkbenchShell(), project.getProject());
												dlg.open();

												// if they check the option then remember it
												if (dlg.dontAskAgain()) {
										            try {
										            	ILaunchConfigurationWorkingCopy workingCopy = configuration.getWorkingCopy();
														workingCopy.setAttribute(DONT_ASK_ABOUT_PARTIAL_UPGRADE_OPTION, true);

														// save the changes
											            workingCopy.doSave();
													} catch (CoreException e) {
														e.printStackTrace();
														LaunchPlugin.log(e);
													}
												}
											}
										});
									}
								}
							}
							break;
		        		}
		        	}
		        }
			}
		}
		
		
		return super.buildForLaunch(configuration, mode, monitor);
	}
}
