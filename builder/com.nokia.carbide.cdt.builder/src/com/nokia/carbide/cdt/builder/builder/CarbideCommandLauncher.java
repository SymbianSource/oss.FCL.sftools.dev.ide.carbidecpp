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

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

import org.eclipse.cdt.core.*;
import org.eclipse.cdt.core.model.ICModelMarker;
import org.eclipse.cdt.core.resources.IConsole;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.internal.resources.MarkerInfo;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.IOException;
import java.util.*;

/**
 * A utility class to handle windows process execution. This utility class handles all the execution, 
 * error processing, and console output.
 */
public class CarbideCommandLauncher extends CommandLauncher implements IMarkerGenerator {

	public class MarkerCacheElement {
		private int line;
		private int severity;
		private String message;
		
		public MarkerCacheElement(IMarker marker) throws CoreException {
			line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
			severity = ((Integer) marker.getAttribute(IMarker.SEVERITY)).intValue();
			message = (String) marker.getAttribute(IMarker.MESSAGE);
		}

		public MarkerCacheElement(ProblemMarkerInfo problemMarkerInfo) {
			line = problemMarkerInfo.lineNumber;
			severity = mapMarkerSeverity(problemMarkerInfo.severity);
			message = problemMarkerInfo.description;
		}

		@Override
		public int hashCode() {
			final int prime = 31; 
			int result = 1;
			result = prime * result + line;
			result = prime * result + severity;
			result = prime * result
					+ ((message == null) ? 0 : message.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof MarkerCacheElement))
				return false;
			MarkerCacheElement other = (MarkerCacheElement) obj;
			if (line != other.line)
				return false;
			if (severity != other.severity)
				return false;
			if (message == null) {
				if (other.message != null)
					return false;
			} 
			else if (!message.equals(other.message))
				return false;
			return true;
		}
	}

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
	private Map<IResource, Set<MarkerCacheElement>> markerCache;
	private long markerCreationTime;
	
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
		markerCache = null;
		markerCreationTime = System.currentTimeMillis();
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
		if (markerCache == null)
			markerCache = new HashMap<IResource, Set<MarkerCacheElement>>();
		
		try {
			IResource markerResource = problemMarkerInfo.file ;
			if (markerResource == null)  {
				markerResource = stdoutStream.getProject();
			}
			
			Set<MarkerCacheElement> cacheElements = markerCache.get(markerResource);
			if (cacheElements == null) {
				cacheElements = new HashSet<MarkerCacheElement>();
				markerCache.put(markerResource, cacheElements);
				IMarker[] cur = markerResource.findMarkers(ICModelMarker.C_MODEL_PROBLEM_MARKER, false, IResource.DEPTH_ONE);
				for (IMarker marker : cur) {
					cacheElements.add(new MarkerCacheElement(marker));
				}
			}
			
			if (!cacheElements.add(new MarkerCacheElement(problemMarkerInfo)))
				return;

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
			setUniqueCreationTime(marker);
		}
		catch (CoreException e) {
			CCorePlugin.log(e.getStatus());
		}

	}
	
	private void setUniqueCreationTime(IMarker marker) {
		// This is using internal platform APIs to avoid putting a delay into every marker creation
		// to ensure each marker gets a unique creation time (for sorting in the problems view).
		// The total delay could be significant when there are many problem markers
		IResource resource = marker.getResource();
		Workspace workspace = (Workspace) resource.getWorkspace();
		MarkerInfo markerInfo = workspace.getMarkerManager().findMarkerInfo(resource, marker.getId());
		markerInfo.setCreationTime(markerCreationTime++);
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
