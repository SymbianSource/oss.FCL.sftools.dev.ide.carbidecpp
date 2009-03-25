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

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

public class RCOMPErrorParser extends CarbideBaseErrorParser {

	private Pattern cppFileLineWarningPattern;
	private Pattern cppFileLinePattern;
	private Pattern cppFileLineInfoPattern;
	
	public RCOMPErrorParser() {
		// match file, line, possible column, and warning
		cppFileLineWarningPattern = Pattern.compile("(.+):(\\d+):(?:(\\d+:)?)\\s+warning:\\s+(.*)"); //$NON-NLS-1$
		// match file, line, possible column, and error
		cppFileLinePattern = Pattern.compile("(.+):(\\d+):(?:(\\d+:)?)\\s+(.*)"); //$NON-NLS-1$
//		 match file, line, possible column, and info
		cppFileLineInfoPattern = Pattern.compile("(.+):(\\d+):(?:(\\d+:)?)\\s+note:(.*)"); //$NON-NLS-1$
	}

	private boolean processPreprocessorLine(String line, ErrorParserManager errorParserManager) {
		// Known patterns:
		//
		// (a)
		// %s:%d: warning: %s
		//
		// (b)
		// %s:%d:%d: warning: %s
		//
		// (c)
		// %s:%d: %s
		//
		// (d)
		// %s:%d:%d: %s
		//
		// (e)
		// * cpp failed

		initialise();

		Matcher cppMatcher = cppFileLineWarningPattern.matcher(line);
		if (cppMatcher.matches()) {
			msgFileName = cppMatcher.group(1);
			msgLineNumber = Integer.parseInt(cppMatcher.group(2));
			msgDescription = cppMatcher.group(4);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber,	msgDescription, IMarkerGenerator.SEVERITY_WARNING, null, externalFilePath);
			return true;
		}

		cppMatcher = cppFileLineInfoPattern.matcher(line);
		if (cppMatcher.matches()) {
			msgFileName = cppMatcher.group(1);
			msgLineNumber = Integer.parseInt(cppMatcher.group(2));
			msgDescription = cppMatcher.group(4);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, IMarkerGenerator.SEVERITY_INFO, null, externalFilePath);
			return true;
		}
		
		cppMatcher = cppFileLinePattern.matcher(line);
		if (cppMatcher.matches()) {
			msgFileName = cppMatcher.group(1);
			msgLineNumber = Integer.parseInt(cppMatcher.group(2));
			msgDescription = cppMatcher.group(4);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, externalFilePath);
			return true;
		}

		if (line.startsWith("* cpp failed") || line.startsWith("* BMCONV failed") 
			|| line.startsWith("* mifconv.exe failed") || line.startsWith("* epocaif.pl failed") 
			|| line.toUpperCase().startsWith("FAILED TO UPDATE FILE") ) {
			errorParserManager.generateExternalMarker(null, -1, line, IStatus.ERROR, null, externalFilePath);
			return true;
		}

		return false;
	}
	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		// Known patterns.
		// (a)
		// filename(lineno) : iDescription
		//
		// (b)
		// filename(lineno) : Warning: (WarningNumber) iDescription
		//
		// (c)
		// filename(lineno) : Error: (ErrorNumber) iDescription
		//
		// (d)
		// cannot open %s for writing (where %s is the asbolute path to the .rsc
		// file)
		//
		// (e)
		// Failed to write UIDs to %s (where %s is the asbolute path to the .rsc
		// file)
		//
		// (f)
		// * RCOMP failed - deleting output files
		//
		// (g)
		// ERROR: %s (where %s could be anything really)
		//
		// (h) C Pre-Processor
		// Filename:lineNumber: iDescription
		//
		// (i) C Pre-Processor failing
		// * cpp failed
		
		initialise();
		
		if (processPreprocessorLine(line, errorParserManager))
			return true;
		
		HashSet<String> linePrefixes = new HashSet<String>();
		linePrefixes.add("cannot open "); //$NON-NLS-1$
		linePrefixes.add("Failed to write UIDs to "); //$NON-NLS-1$
		linePrefixes.add("* RCOMP failed -"); //$NON-NLS-1$
		linePrefixes.add("ERROR: "); //$NON-NLS-1$
		linePrefixes.add("* cpp failed"); //$NON-NLS-1$

		if (checkForLineBeginnings(line, linePrefixes.toArray(new String[linePrefixes.size()]))) {
			errorParserManager.generateMarker(null, -1, line, msgSeverity, null);
			return true;
		}

		// check for MBM build failure.  This string is part of the help text spewed out
		// for an invalid command line.  Same in Symbian 7.0s and 9.2, so assumed static.
		if (line.indexOf("epocmbm [-h headerfile]") >= 0) { //$NON-NLS-1$
			errorParserManager.generateMarker(null, -1,
					"epocmbm build failed (perhaps due to an empty image list or invalid color format specification): please check the Console",
					IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}

		if (!setFirstColon(line)) {
			return false;
		}
		setDescription(line);
		if (!setFileNameAndLineNumber(line) && !setDetailsFromCppOutput(line)) {
			return false;	
		}
		String[] extensionsToExclude = {".pkg", ".def", ".defi"}; //$NON-NLS-1$ //$NON-NLS-2$ 
		if (hasExcludedExtension(extensionsToExclude)) {
			return false;
		}
		setFile(errorParserManager);
		errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, msgSeverity, null, externalFilePath);
		return true;
	}

	/**
	 * Rcomp calls the C PreProcessor so it is possible that cpp will fail. This
	 * method attempts to get the line number, file name and description
	 * 
	 * @param line
	 *            a String containing console output
	 * @return true if this was a cpp error, false otherwise
	 */
	private boolean setDetailsFromCppOutput(String line) {
		Pattern lPln = Pattern.compile("^([^:]+):(\\d+): (.*)$"); //$NON-NLS-1$ 
		Matcher lMatch = lPln.matcher(line);
		if (lMatch.matches() && lMatch.groupCount() == 3) {
			msgFileName = lMatch.group(1);
			if (!Path.EMPTY.isValidPath(msgFileName)) {
				return false;
			}
			try {
				msgLineNumber = Integer.parseInt(lMatch.group(2));
			} catch (NumberFormatException e) {
				return false;
			}
			msgDescription = lMatch.group(3);
		} else {
			return false;
		}
		return true;
	}

	
	public void setDescription(String line) {
		// Get the iDescription
		msgDescription = line.substring(msgFirstColon + 1).trim();
		if (msgDescription.matches("(?i)warning.*")) { //$NON-NLS-1$ 
			// Remove the warning.
			msgDescription = msgDescription.substring("warning".length()).trim(); //$NON-NLS-1$
			msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
		} else if (msgDescription.matches("(?i)error.*")) {
			// Remove the error.
			msgDescription = msgDescription.substring("error".length()).trim(); //$NON-NLS-1$
		}
		if (msgDescription.startsWith(":")) { //$NON-NLS-1$
			msgDescription = msgDescription.substring(1).trim();
		}
	}

}
