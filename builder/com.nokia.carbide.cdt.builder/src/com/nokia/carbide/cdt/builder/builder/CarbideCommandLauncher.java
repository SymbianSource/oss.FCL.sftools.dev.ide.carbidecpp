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
package com.nokia.carbide.cdt.builder.builder;

import java.io.IOException;
import java.util.Properties;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.CommandLauncher;
import org.eclipse.cdt.core.ConsoleOutputStream;
import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.cdt.core.ProblemMarkerInfo;
import org.eclipse.cdt.core.model.ICModelMarker;
import org.eclipse.cdt.core.resources.IConsole;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

/**
 * A utility class to handle windows process execution. This utility class handles all the execution, 
 * error processing, and console output.
 */
public class CarbideCommandLauncher extends CommandLauncher implements IMarkerGenerator {

	protected IProgressMonitor monitor;
	protected IConsole console;
	protected ErrorParserManager stdoutStream;
	protected ErrorParserManager stderrStream;
	protected ConsoleOutputStream consoleOutStream;
	protected ConsoleOutputStream consoleErrorStream;
	protected ConsoleOutputStream consoleInfoStream;
	protected IProject project;
	protected String[] errorParserIds;
	protected IMarkerGenerator markerGen;
	
	/**
	 * Location of tool execution
	 */
	IPath workingDir;
	
	protected long startTime=0;
	private static String osShell;
	
	/**
	 * Create an instance of the CarbideCommandLauncher with error parsing
	 * @param carbideProjectInfo - A valid ICarbideProjectInfo object interface
	 * @param monitor - The IProgressMonitor
	 * @param console - The console where output streams are written.
	 * @param errorParserIds - The list of error parser IDs used to parse the output streams. Can be null or empty array if no error parsing is required.
	 * @param workingDir - Location of program execution. Used init the ErrorParserManager
	 * @deprecated don't pass in a console if you want to to get colored info/output/error streams.  use {@link #CarbideCommandLauncher(IProject, IProgressMonitor, String[], IPath)} instead,
	 * or pass in <code>CUIPlugin.getDefault().getConsoleManager().getConsole(project)</code>
	 */
	public CarbideCommandLauncher(IProject project, 
								  IProgressMonitor monitor, 
								  IConsole console, 
								  String[] errorParserIds, 
								  IPath workingDir) {
		super();
		this.project = project;
		this.monitor = monitor;
		this.console = console;
		this.workingDir = workingDir;
		if (errorParserIds != null){
			this.errorParserIds = new String[errorParserIds.length];
		} else {
			this.errorParserIds = new String[0];
		}
		this.errorParserIds = errorParserIds;

		init();
	}
	
	/**
	 * Create an instance of the CarbideCommandLauncher with error parsing
	 * @param carbideProjectInfo - A valid ICarbideProjectInfo object interface
	 * @param monitor - The IProgressMonitor
	 * @param errorParserIds - The list of error parser IDs used to parse the output streams. Can be null or empty array if no error parsing is required.
	 * @param workingDir - Location of program execution. Used init the ErrorParserManager
	 */
	public CarbideCommandLauncher(IProject project, 
								  IProgressMonitor monitor, 
								  String[] errorParserIds, 
								  IPath workingDir) {
		super();
		this.project = project;
		this.monitor = monitor;
		this.console = CUIPlugin.getDefault().getConsoleManager().getConsole(project);
		this.workingDir = workingDir;
		if (errorParserIds != null){
			this.errorParserIds = new String[errorParserIds.length];
		} else {
			this.errorParserIds = new String[0];
		}
		this.errorParserIds = errorParserIds;

		init();
	}

	/**
	 * Create an instance of the CarbideCommandLauncher. No error parsing is done.
	 * @param carbideProjectInfo - A valid IProject object interface
	 * @param monitor - The IProgressMonitor
	 * @param console - The console where output streams are written.
	 * @deprecated use {@link #CarbideCommandLauncher(IProject, IProgressMonitor, String[], IPath)} instead
	 */
	public CarbideCommandLauncher(IProject project, 
								  IProgressMonitor monitor, 
								  IConsole console ) {
		super();
		this.project = project;
		this.monitor = monitor;
		this.console = console;
		this.workingDir = project.getFullPath(); // used for error parsers, which will not be used with the constructor
		this.errorParserIds = new String[0]; // this constructor does not use error parsers.

		init();
	}
	
	
	/**
	 * Initialize variables. Called by the constructor
	 *
	 */
	public void init(){
		try {
			console.start(project);
			consoleOutStream = console.getOutputStream();
			consoleErrorStream = console.getErrorStream();
			consoleInfoStream = console.getInfoStream();
			
			if (errorParserIds == null){
				errorParserIds = new String[0];
			} 
			
			stdoutStream = new ErrorParserManager(project, workingDir, this, errorParserIds);
			stdoutStream.setOutputStream(consoleOutStream);

			stderrStream = new ErrorParserManager(project, workingDir, this, errorParserIds);
			stderrStream.setOutputStream(consoleErrorStream);
		} catch (Exception e){
			CarbideBuilderPlugin.log(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets up the core ErrorParserManager
	 * @param workingDir - Location where file paths will be computed from (e.g. relative paths)
	 * @param parserIDs - The ID of plugin.xml parser extension IDs to be called on the process execution for stderr and stdout
	 */
	public void setErrorParserManager(IPath workingDir, String[] parserIDs) {
		String[] ids = parserIDs == null ? errorParserIds : parserIDs;
		
		stdoutStream = new ErrorParserManager(project, workingDir, this, ids);
		stdoutStream.setOutputStream(consoleOutStream);

		stderrStream = new ErrorParserManager(project, workingDir, this, ids);
		stderrStream.setOutputStream(consoleErrorStream);
	}
	
	/**
	 * Get the output stream for the console
	 * @return ConcoleOutputStream object
	 */
	public ConsoleOutputStream getConsoleOutputStream(){
		return consoleOutStream;
	}
	
	/** Write a message to the console */
	public void writeToConsole(String msg){
		try {
			consoleInfoStream.write(msg.getBytes());
		} catch (IOException e){
			CarbideBuilderPlugin.log(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes a single command. Note that multiple calls of the same instance of this object
	 * to executeCommand should be followed by setErrorParserManager so that the console output
	 * stream is re-opened
	 * @param command - The tool to call
	 * @param args - argument array that 'command' consumes
	 * @param env - Full list of environment variables
	 * @param workingDir - The current working directory the command is to be invoked in.
	 * @return 0 (zero) on success. returns the actual tool return status
	 */
	public int executeCommand(IPath command, String[] args, String[] env, IPath workingDir){
		int exitValue = -1;
		String errMsg;
		try {
			
			Process proc = execute(command, args, env, workingDir);
			
			if (proc != null) {
				try {
					// Close the input of the process since we will never write to it
					proc.getOutputStream().close();
				} catch (IOException e) {
					CarbideBuilderPlugin.log(e);
					e.printStackTrace();
				}
				
				if (waitAndRead(stdoutStream.getOutputStream(), stderrStream.getOutputStream(),
						new SubProgressMonitor(monitor,
								IProgressMonitor.UNKNOWN)) != CommandLauncher.OK) {
					errMsg = getErrorMessage();
				}
				
				exitValue = proc.exitValue();
				
			} else {
				errMsg = getErrorMessage();
				consoleErrorStream.write(errMsg.getBytes());
			}
		
		} catch (IOException ioe){
			CarbideBuilderPlugin.log(ioe);
			ioe.printStackTrace();
		}
		
		try {
			stdoutStream.close();
			stderrStream.close();
        } catch (IOException e1) {
            //logIOException(e1);
        }
		
		stdoutStream.reportProblems();
		stderrStream.reportProblems();
		
		System.out.flush();
		
		return exitValue;
	}
	
	/**
	 * Sets the current system time as the starting time.
	 * @see getTimingStats()
	 */
	public void startTimingStats(){
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Get a formatted string of total time since timer was started.
	 * @return A formatted string with time in seconds. If time is 0 and empty string is returned.
	 */
	public String getTimingStats(){
		if (startTime != 0){
			long millisec = (System.currentTimeMillis() - startTime);
			long minutes = millisec / 1000 / 60;
			long seconds = (millisec / 1000) % 60;
			if (minutes > 0){
				return "\nTotal Time: " + minutes + " min, " + seconds + " sec\n";
			} else {
				return "\nTotal Time: " + seconds + " sec\n";
			}
			
		} else {
			return "";
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.cdt.core.IMarkerGenerator#addMarker(org.eclipse.core.resources.IResource, int, java.lang.String, int, java.lang.String)
	 */
    public void addMarker(IResource file, int lineNumber, String errorDesc, int severity, String errorVar) {
    	ProblemMarkerInfo info = new ProblemMarkerInfo(file, lineNumber, errorDesc, severity, errorVar);
    	addMarker(info);
    }

	public void addMarker(ProblemMarkerInfo problemMarkerInfo) {

		try {
			IResource markerResource = problemMarkerInfo.file ;
			if (markerResource == null)  {
				markerResource = stdoutStream.getProject();
			}
			IMarker[] cur = markerResource.findMarkers(ICModelMarker.C_MODEL_PROBLEM_MARKER, false, IResource.DEPTH_ONE);

			/*
			 * Try to find matching markers and don't put in duplicates
			 */
			if ((cur != null) && (cur.length > 0)) {
				for (int i = 0; i < cur.length; i++) {
					int line = cur[i].getAttribute(IMarker.LINE_NUMBER, -1);
					int sev = ((Integer) cur[i].getAttribute(IMarker.SEVERITY)).intValue();
					String mesg = (String) cur[i].getAttribute(IMarker.MESSAGE);
					if (line == problemMarkerInfo.lineNumber && sev == mapMarkerSeverity(problemMarkerInfo.severity) && mesg.equals(problemMarkerInfo.description)) {
						return;
					}
				}
			}

			// need to pause briefly between marker creations so the creation timestamps
			// are unique and sorting the problems view by creation time works properly.
			try {
				Thread.sleep(20);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			IMarker marker = markerResource.createMarker(ICModelMarker.C_MODEL_PROBLEM_MARKER);
			marker.setAttribute(IMarker.MESSAGE, problemMarkerInfo.description);
			marker.setAttribute(IMarker.SEVERITY, mapMarkerSeverity(problemMarkerInfo.severity));
			marker.setAttribute(IMarker.LINE_NUMBER, problemMarkerInfo.lineNumber);
			marker.setAttribute(IMarker.CHAR_START, -1);
			marker.setAttribute(IMarker.CHAR_END, -1);
			if (problemMarkerInfo.variableName != null) {
				marker.setAttribute(ICModelMarker.C_MODEL_MARKER_VARIABLE, problemMarkerInfo.variableName);
			}
			if (problemMarkerInfo.externalPath != null) {
				// try to make it absolute if not already
				IPath absolutePath = problemMarkerInfo.externalPath;
				if (!absolutePath.isAbsolute()) {
					IPath projectPath = project.getLocation();
					if (projectPath != null) {
						absolutePath = projectPath.append(absolutePath);
					}
				}
				// now canonicalize it
				try {
					absolutePath = new Path(absolutePath.toFile().getCanonicalPath());
				} catch (IOException e) {
					CarbideBuilderPlugin.log(e);
					e.printStackTrace();
				}
				marker.setAttribute(ICModelMarker.C_MODEL_MARKER_EXTERNAL_LOCATION, absolutePath.toOSString());
			}
		}
		catch (CoreException e) {
			CCorePlugin.log(e.getStatus());
		}

	}
	
	int mapMarkerSeverity(int severity) {

		switch (severity) {
			case SEVERITY_ERROR_BUILD :
			case SEVERITY_ERROR_RESOURCE :
				return IMarker.SEVERITY_ERROR;
			case SEVERITY_INFO :
				return IMarker.SEVERITY_INFO;
			case SEVERITY_WARNING :
				return IMarker.SEVERITY_WARNING;
		}
		return IMarker.SEVERITY_ERROR;
	}
	
	/**
	 * Get the location of cmd.exe by querying the COMSPEC variable.
	 * @return The full path to cmd.exe
	 * @deprecated don't use cmd.exe for calling executeCommand.  just use the process
	 * you want to call, e.g. abld.bat.  There have been intermittent problems canceling
	 * the cmd.exe process.
	 */
	 public static IPath getCmdExeLocation() {
	        if (osShell==null)
	        {
	    		Properties env=EnvironmentReader.getEnvVars();
	    		osShell=env.getProperty("COMSPEC", "cmd.exe");
	        }
	        return new Path(osShell);        
	 }
	 
	 /**
	  * Returns the current working directory
	 * @since 2.0
	 */
	public IPath getWorkingDirectory() {
		 return workingDir;
	 }
}
