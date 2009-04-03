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

package com.nokia.carbide.cpp.internal.codescanner;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.ProblemMarkerInfo;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cpp.internal.codescanner.markers.CSMarker;

/**
 * A class to handle CodeScanner commandline tool execution, error processing
 * and generating CodeScanner specific markers.
 * 
 */
public class CSCommandLauncher extends CarbideCommandLauncher {

	
	private Pattern CSMessagePattern = Pattern.compile("(\\w+):\\s+(\\w+):\\s+(\\w+):\\s+(.*)"); //$NON-NLS-1$;

	/**
	 * Create an instance of the CSCommandLauncher with error parsing
	 * @param project - a valid IProject object interface
	 * @param monitor - a valid IProgressMonitor object interface
	 * @param errorParserIds - the list of error parser IDs used to parse the 
	 * 						   output streams, can be null or empyt array if 
	 * 						   no error parsing is required
	 * @param workingDir - location of program execution, used to initialize 
	 * 					   the ErrorParserManager
	 */
	public CSCommandLauncher(IProject project,
							 IProgressMonitor monitor,
							 String[] errorParserIds,
							 IPath workingDir) {
		super(project, monitor, errorParserIds, workingDir);
	}

	/**
	 * Generate a CodeScanner specific marker
	 * @param problemMarkerInfo - a valid ProblemMarkerInfo object
	 */
	public void addMarker(ProblemMarkerInfo problemMarkerInfo) {
		try {
			IResource markerResource = problemMarkerInfo.file ;
			if (markerResource == null)  {
				markerResource = stdoutStream.getProject();
			}
			if (markerResource != null) {
				IMarker[] cur = markerResource.findMarkers(CSMarker.CS_PROBLEM_MARKER, false, IResource.DEPTH_ONE);

				// try to find matching markers and don't put in duplicates
				if ((cur != null) && (cur.length > 0)) {
					for (int i = 0; i < cur.length; i++) {
						int line = cur[i].getAttribute(IMarker.LINE_NUMBER, -1);
						int sev = ((Integer) cur[i].getAttribute(IMarker.SEVERITY)).intValue();
						String mesg = (String) cur[i].getAttribute(IMarker.MESSAGE);
						if (line == problemMarkerInfo.lineNumber && sev == mapCSMarkerSeverity(problemMarkerInfo.severity) && mesg.equals(problemMarkerInfo.description)) {
							return;
						}
					}
				}

				// create the marker and set its attributes
				IMarker marker = markerResource.createMarker(CSMarker.CS_PROBLEM_MARKER);
				if (marker != null) {
					String message = getCSMessage(problemMarkerInfo.description);
					String ruleName = getCSRuleName(problemMarkerInfo.description);
					marker.setAttribute(IMarker.MESSAGE, message);
					marker.setAttribute(IMarker.SEVERITY, mapCSMarkerSeverity(problemMarkerInfo.severity));
					marker.setAttribute(IMarker.LINE_NUMBER, problemMarkerInfo.lineNumber);
					marker.setAttribute(IMarker.CHAR_START, -1);
					marker.setAttribute(IMarker.CHAR_END, -1);
					marker.setAttribute(CSMarker.CS_MARKER_RULE_NAME, ruleName);
					if (problemMarkerInfo.variableName != null) {
						marker.setAttribute(CSMarker.CS_MARKER_VARIABLE, problemMarkerInfo.variableName);
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
							e.printStackTrace();
						}
						marker.setAttribute(CSMarker.CS_MARKER_EXTERNAL_LOCATION, absolutePath.toOSString());
					}
				}
			}
		}
		catch (CoreException e) {
			CCorePlugin.log(e.getStatus());
			e.printStackTrace();
		}
	}

	/**
	 * Map an IMarkerGenerator severity to an IMarker severity
	 * @param severity - an IMarkerGenerator severity
	 * @return an IMarker severity
	 */
	protected int mapCSMarkerSeverity(int severity) {

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
	 * Reconstruct error/warning message from CodeScanner commandline tool 
	 * to get rid of the name of the rule.
	 * @param message - error/warning message generated by CodeScanner
	 * @return error/warning message without the name of the rule
	 */
	private String getCSMessage(String message) {
		String csMessage = message;
		Matcher messageMatcher = CSMessagePattern.matcher(message);
		if (messageMatcher.matches()) {
			csMessage = messageMatcher.group(2) + ": " + 
						messageMatcher.group(3) + ": " + 
						messageMatcher.group(4);
		}
		return csMessage;
	}

	/**
	 * Retrieve the name of the rule embedded in error/warning message
	 * from CodeScanner commandline tool.
	 * @param message - error/warning message generated by CodeScanner
	 * @return name of the rule if found, null otherwise
	 */
	private String getCSRuleName(String message) {
		String ruleName = null;
		Matcher messageMatcher = CSMessagePattern.matcher(message);
		if (messageMatcher.matches()) {
			ruleName = messageMatcher.group(1);
		}
		return ruleName;
	}

}
