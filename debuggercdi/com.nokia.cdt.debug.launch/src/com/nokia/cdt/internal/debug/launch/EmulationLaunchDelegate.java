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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.cdt.launch.internal.ui.LaunchUIPlugin;
import org.eclipse.cdt.utils.pty.PTY;
import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.cdt.utils.spawner.ProcessFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

import com.freescale.cdt.debug.cw.DebuggerLog;
import com.freescale.cdt.debug.cw.core.cdi.ISessionListener;
import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.freescale.cdt.debug.cw.core.settings.DebuggerCommonData;
import com.freescale.cdt.debug.cw.core.ui.console.LoggingConsole;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.debug.cw.symbian.SymbianPlugin;

import cwdbg.PreferenceConstants;

public class EmulationLaunchDelegate extends NokiaAbstractLaunchDelegate {

	private IPath verifyHostApp(ILaunchConfiguration config) throws CoreException {
		String hostApp = config.getAttribute(DebuggerCommonData.Host_App_Path, (String)null);
		if (hostApp.length() > 0)
		{
			Path hostAppPath = new Path(hostApp);
			if (hostAppPath == null || hostAppPath.isEmpty()) {
				return null;
			}
			if (!hostAppPath.toFile().exists()) {
				abort(
						"File does not exist",
						new FileNotFoundException(
								LaunchMessages.getFormattedString(
																	"AbstractCLaunchDelegate.PROGRAM_PATH_not_found", hostAppPath.toOSString())), //$NON-NLS-1$
						ICDTLaunchConfigurationConstants.ERR_PROGRAM_NOT_EXIST);
			}
			return hostAppPath;
		}
		return null;
	}

	public void launch(
			ILaunchConfiguration 	config, 
			String 					mode, 
			ILaunch 				launch, 
			IProgressMonitor monitor) throws CoreException 
	{
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
        	addBeingLaunched(config); // indicating the LC is being launched
        	
			monitor.worked(1);
			IPath exePath = verifyProgramPath(config);
			ICProject project = verifyCProject(config);
			IBinaryObject exeFile = verifyBinary(project, exePath);
			String arguments[] = getProgramArgumentsArray(config);
			verifyHostApp(config);

			// See comment for this method for more.
            SettingsData.setInternalPreferences(config, SettingsData.LaunchConfig_Emulator);
            			
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
							if (config.getAttribute(PreferenceConstants.J_PN_ViewSystemMessage, false)) {
								SymbianPlugin.getDefault().openSystemConsole(true);
							}

							if (config.getAttribute(PreferenceConstants.J_PN_ViewProcessOutput, false)) {
								SymbianPlugin.getDefault().openDebugConsole(true);
							}

							if (config.getAttribute(SymbianPlugin.DebugTraceLaunchSetting, false)) {
								openDebugTraceConsole(cwDebugSession);
							}

							config = synchronizeWithProjectAccessPaths(project, config);
							
							File wd = getWorkingDirectory(config);
							long t = System.currentTimeMillis();
							cwDebugSession.launchExecutable(launch, config, exeFile, otherExecutables, arguments, wd, getEnvironmentAsProperty(config), monitor, project, renderTargetLabel(debugConfig), true);
							t = System.currentTimeMillis() - t;
//							System.out.println("launchExecutable returns in : " + t);
							DebuggerLog.log("launchExecutable returns in : " + t);
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
				// run mode
				File wd = getWorkingDirectory(config);
				if (wd == null) {
					wd = new File(System.getProperty("user.home", ".")); //$NON-NLS-1$ //$NON-NLS-2$
				}
				ArrayList<String> command = new ArrayList<String>(1 + arguments.length);

		    	// run the host app if specified 
		    	String hostAppPath = config.getAttribute(DebuggerCommonData.Host_App_Path, ""); //$NON-NLS-1$
		    	if (hostAppPath.length() > 0)
			    	command.add(hostAppPath);
		    	else
			    	command.add(exePath.toOSString());
		    	
				command.addAll(Arrays.asList(arguments));
				String[] commandArray = (String[]) command.toArray(new String[command.size()]);
				boolean usePty = config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_USE_TERMINAL,
						ICDTLaunchConfigurationConstants.USE_TERMINAL_DEFAULT);
				monitor.worked(5);
				Process process = exec(commandArray, getEnvironment(config), wd, usePty);
				monitor.worked(3);
				IProcess proc = DebugPlugin.newProcess(launch, process, renderProcessLabel(commandArray[0]));
				
				// set the command line attribute so it shows up in the process
				// information property page
				String cmdLine = ""; //$NON-NLS-1$
				for (int i=0; i<commandArray.length; i++) {
					cmdLine += commandArray[i];
					if (i != commandArray.length - 1) {
						cmdLine += " "; //$NON-NLS-1$
					}
				}
				proc.setAttribute(IProcess.ATTR_CMDLINE, cmdLine);
			}
		} catch (CoreException e) {
        	if (! monitor.isCanceled()) // don't throw on user cancellation
        		throw e;
		} finally {
			monitor.done();
            removeBeingLaunched(config);
		}
		}	// end of synchronized
	}

	/**
	 * Performs a runtime exec on the given command line in the context of the
	 * specified working directory, and returns the resulting process. If the
	 * current runtime does not support the specification of a working
	 * directory, the status handler for error code
	 * <code>ERR_WORKING_DIRECTORY_NOT_SUPPORTED</code> is queried to see if
	 * the exec should be re-executed without specifying a working directory.
	 * 
	 * @param cmdLine
	 *            the command line
	 * @param workingDirectory
	 *            the working directory, or <code>null</code>
	 * @return the resulting process or <code>null</code> if the exec is
	 *         cancelled
	 * @see Runtime
	 */
	protected Process exec(String[] cmdLine, String[] environ, File workingDirectory, boolean usePty)
			throws CoreException {
		Process p = null;
		try {
			if (workingDirectory == null) {
				p = ProcessFactory.getFactory().exec(cmdLine, environ);
			} else {
				if (usePty && PTY.isSupported()) {
					p = ProcessFactory.getFactory().exec(cmdLine, environ, workingDirectory, new PTY());
				} else {
					p = ProcessFactory.getFactory().exec(cmdLine, environ, workingDirectory);
				}
			}
		} catch (IOException e) {
			if (p != null) {
				p.destroy();
			}
			abort(LaunchMessages.getString("LocalRunLaunchDelegate.Error_starting_process"), e, //$NON-NLS-1$
					ICDTLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
		} catch (NoSuchMethodError e) {
			//attempting launches on 1.2.* - no ability to set working
			// directory

			IStatus status = new Status(IStatus.ERROR, LaunchUIPlugin.getUniqueIdentifier(),
					ICDTLaunchConfigurationConstants.ERR_WORKING_DIRECTORY_NOT_SUPPORTED, LaunchMessages
							.getString("LocalRunLaunchDelegate.Does_not_support_working_dir"), //$NON-NLS-1$
					e);
			IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(status);

			if (handler != null) {
				Object result = handler.handleStatus(status, this);
				if (result instanceof Boolean && ((Boolean) result).booleanValue()) {
					p = exec(cmdLine, environ, null, usePty);
				}
			}
		}
		return p;
	}
	
	@Override
	protected String getCPUString() {
		return "x86"; //$NON-NLS-1$
	}

	/**
	 * Input stream at the tail of a file, used to capture the contents of the epocwind.out file.
	 */
	public class FileTailInputStream extends InputStream {

		private RandomAccessFile file;
		private long tail;

		public FileTailInputStream(File file) throws IOException {
			super();
			this.file = new RandomAccessFile(file, "r"); //$NON-NLS-1$
			
			// save the original file length when the input stream is created
			tail = file.length();

			// start at the tail of the file
			this.file.seek(tail);
		}
		
		public int read() throws IOException {
			checkTail();
			byte[] b = new byte[1];
			int len = file.read(b, 0, 1);
			if (len < 0) {
				return len;
			}
			return b[0];
		}

		public int read(byte[] b) throws IOException {
			checkTail();
			return file.read(b, 0, b.length);
		}

		public int read(byte[] b, int off, int len) throws IOException {
			checkTail();
			return file.read(b, off, len);
		}

		public void close() throws IOException {
			file.close();
		}

		private void checkTail() throws IOException {
			// this checks to see if the file was cleared at
			// some point after we created the input stream.  if
			// so we want to start at the beginning of the file.
			if (file.length() < tail) {
				tail = 0;
				file.seek(0);
			}
		}
	}

	// file monitoring thread
	class ReadThread extends Thread implements Runnable, ISessionListener {
		
		private InputStream in = null;
		private OutputStream out = null;
		private boolean keepListening = true;

		public ReadThread(InputStream in, OutputStream out) {
			this.in = in;
			this.out = out;
			setName("epocwind monitor thread"); //$NON-NLS-1$
		}
		
		public void run() {
			
			byte[] buffer = new byte[1024];
			
			try {
				while (keepListening) {
					int bytesRead = in.read(buffer);
					if (bytesRead > 0) {
						out.write(buffer, 0, bytesRead);
					}
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
					}
				}
			} catch (IOException e) {
				LaunchUIPlugin.log(e);
			} finally {
				// close the input stream (the file).  leave the output stream open
				// for future debug sessions.
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		public void sessionEnded() {
			keepListening = false;
		}
	}

	public void openDebugTraceConsole(Session session) {
		final String consoleName = SymbianPlugin.DebugTraceMessagesConsoleName;
		
		// add it if necessary
		LoggingConsole console = null;
		boolean found = false;

		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (int i=0; i<consoles.length; i++) {
			if (consoleName.equals(consoles[i].getName())) {
				console = (LoggingConsole)consoles[i];
				found = true;
				break;
			}
		}
				
		if (!found) {
        	console = new LoggingConsole(consoleName);
        	ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{console});			
		}

		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
		
		// clear the console and get an output stream for it
		console.clearConsole();
		IOConsoleOutputStream consoleOut = console.getLoggingStream();
		
		// locate the file
		File traceFile = null;
		String value = EnvironmentReader.getEnvVar("TEMP"); //$NON-NLS-1$
		if (value != null) {
			try {
				traceFile = new File(value + "\\epocwind.out").getCanonicalFile(); //$NON-NLS-1$
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (traceFile != null) {
			// create it if it doesn't already exist.  the emulator would create it but
			// hasn't been launched yet and we need to create an input stream from the file now.
			if (!traceFile.exists()) {
				try {
					traceFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				// create the monitor thread and kick it off
				FileTailInputStream inputStream = new FileTailInputStream(traceFile);
				ReadThread rt = new ReadThread(new BufferedInputStream(inputStream), consoleOut);
				session.addListener(rt);
				rt.start();
			} catch (IOException e) {
			}
		} else {
			try {
				consoleOut.write("Unable to open %TEMP%\\epocwind.out"); //$NON-NLS-1$
				consoleOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
