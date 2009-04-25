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
package com.nokia.carbide.cdt.internal.builder.error.parsers;

import java.util.regex.*;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.resources.IResource;

public class CarbideMakeErrorParser extends CarbideBaseErrorParser {

	private static Pattern MAKE_ERROR_PATTERN = Pattern.compile("make: \\*\\*\\* (.*)\\. Stop\\."); //$NON-NLS-1$
	private static Pattern MAKE_MSG_PATTERN = Pattern.compile("([^:]+):(\\d+): (.*)"); //$NON-NLS-1$
	private static Pattern UNRECOGNIZED_EXTERNAL_COMMAND = Pattern.compile("(.*) is not recognized as an internal or external command(.*)"); //$NON-NLS-1$
	private static Pattern BUG6415_ERROR_PATTERN = Pattern.compile("make(.*) \\*\\*\\* (.*) Error 87"); //$NON-NLS-1$
	
	private static String[] errorStrings = {"NO RULE TO MAKE TARGET", "THE PROCESS CANNOT ACCESS THE FILE"};
	private static String[] warningStrings = {"GIVEN MORE THAN ONCE IN THE SAME RULE"};
	private static String cantLaunchProcess = "CreateProcess((null)";
	private static String rvctNotInstalled = "RVCT0_0.H: No such file or directory";
	
	
	public boolean processLine(String line, ErrorParserManager eoParser) {
		
		IResource rsrc = eoParser.getProject();
		
		// check definite make messages
		Matcher matcher = MAKE_ERROR_PATTERN.matcher(line);
		if (matcher.matches()) {
			setDescription(matcher.group(1));
			eoParser.generateMarker(rsrc, msgLineNumber, line, msgSeverity, null);
			return true;
		}
		matcher = BUG6415_ERROR_PATTERN.matcher(line);
		if (matcher.matches()) {
			setDescription(matcher.group(1));
			String msg = " - possibly too many arguments.  If there are a lot of source files in a library, try breaking those out into multiple libraries.";
			msgLineNumber = -1;
			eoParser.generateMarker(rsrc, msgLineNumber, line + msg, msgSeverity, null);
			return true;
		}
		
		matcher = UNRECOGNIZED_EXTERNAL_COMMAND.matcher(line);
		if (matcher.matches()) {
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			msgLineNumber = -1;
			eoParser.generateMarker(rsrc, msgLineNumber, line, msgSeverity, null);
			return true;
		}
		
		// determine what makefile (if any) this is
		matcher = MAKE_MSG_PATTERN.matcher(line);
		if (matcher.matches()) {
			line = matcher.group(3);
			
			/* This won't help since makefiles are always outside the workspace
			msgFileName = matcher.group(1);
			msgLineNumber = Integer.parseInt(matcher.group(2));
			setFile(eoParser);
			if (msgIFile != null)
				rsrc = msgIFile;
			 */
		}
		
		// check for known make messages
		String upperLine = line.toUpperCase();
		
		for (String currErrorToCheck : errorStrings){
			if (upperLine.contains(currErrorToCheck)){
				setDescription(line);
				eoParser.generateMarker(rsrc, msgLineNumber, line, msgSeverity, null);
				return true;
			}
		}

		for (String currWarningToCheck : warningStrings){
			if (upperLine.contains(currWarningToCheck)){
				this.msgDescription = line;
				this.msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
				eoParser.generateMarker(rsrc, msgLineNumber, line, msgSeverity, null);
				return true;
			}
		}
		
		if (line.contains(cantLaunchProcess)){
			// create a specific error message for processes that cannot be launched
			// since they can be a bit vague from make
			this.msgDescription = "Cannot launch a process. Check the build console for process creation errors.";
			this.msgLineNumber = -1;
			this.msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			eoParser.generateMarker(rsrc, msgLineNumber, msgDescription, msgSeverity, null);
			return true;
		}
		
		if (line.contains(rvctNotInstalled)){
			// create a specific error message when rvct is not installed since the build tools don't
			this.msgDescription = "RVCT not installed or 'armcc' is not on PATH. Please check that your environment for the RVCT compiler is correct.";
			this.msgLineNumber = -1;
			this.msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			eoParser.generateMarker(rsrc, msgLineNumber, msgDescription, msgSeverity, null);
			return true;
		}
		
		return false;
	}
	
	public void setDescription(String line) {
		msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
		msgDescription = line;
	}
}
