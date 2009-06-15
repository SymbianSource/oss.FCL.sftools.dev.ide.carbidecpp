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
import org.eclipse.core.runtime.Path;

public class RCOMPErrorParser extends CarbideBaseErrorParser {

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
		// cannot open %s for writing (where %s is the absolute path to the .rsc
		// file)
		//
		// (e)
		// Failed to write UIDs to %s (where %s is the absolute path to the .rsc
		// file)
		//
		// (f)
		// * RCOMP failed - deleting output files
		//
		// (g)
		// ERROR: %s (where %s could be anything really)
		//
		// (h) C Pre-Processor failing
		// * cpp failed
		//
		// (i)
		// Failed to open %s
		//
		// (j)
		// RCOMP failed with code %d
		//
		
		// Warning pattern
		// (a)
		// atof may have failed for %s
		
		initialise();
		
		// some error
		HashSet<String> linePrefixes = new HashSet<String>();
		linePrefixes.add("cannot open "); //$NON-NLS-1$
		linePrefixes.add("Failed to write UIDs to "); //$NON-NLS-1$
		linePrefixes.add("* RCOMP failed -"); //$NON-NLS-1$
		linePrefixes.add("ERROR: "); //$NON-NLS-1$
		linePrefixes.add("* cpp failed"); //$NON-NLS-1$
		linePrefixes.add("Failed to open "); //$NON-NLS-1$
		linePrefixes.add("RCOMP failed with code "); //$NON-NLS-1$

		if (checkForLineBeginnings(line, linePrefixes.toArray(new String[linePrefixes.size()]))) {
			errorParserManager.generateMarker(null, -1, line, msgSeverity, null);
			return true;
		}
		
		// some warning
		HashSet<String> warningLinePrefixes = new HashSet<String>();
		warningLinePrefixes.add("atof may have failed for "); //$NON-NLS-1$
		
		if (checkForLineBeginnings(line, warningLinePrefixes.toArray(new String[warningLinePrefixes.size()]))) {
			errorParserManager.generateMarker(null, -1, line, IMarkerGenerator.SEVERITY_WARNING, null);
			return true;
		}

		// check for MBM build failure.  This string is part of the help text spewed out
		// for an invalid command line.  Same in Symbian 7.0s and 9.2, so assumed static.
		if (line.indexOf("epocmbm [-h headerfile]") >= 0) { //$NON-NLS-1$
			errorParserManager.generateMarker(null, -1,
					"epocmbm build failed (perhaps due to an empty image list or invalid color format specification): please check the Console", //$NON-NLS-1$
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
		String[] extensionsToExclude = {".pkg", ".def", ".defi", ".asm", ".c" ,".c++", ".cc", ".cia", ".cpp", ".cxx", "h", ".hh", ".hpp", ".hxx", ".s"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ 
		if (hasExcludedExtension(extensionsToExclude)) {
			return false;
		}
		
		if (msgDescription.toLowerCase().contains("warning: ")){
			msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
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
		} else if (msgDescription.matches("(?i)error.*")) { //$NON-NLS-1$
			// Remove the error.
			msgDescription = msgDescription.substring("error".length()).trim(); //$NON-NLS-1$
		}
		if (msgDescription.startsWith(":")) { //$NON-NLS-1$
			msgDescription = msgDescription.substring(1).trim();
		}
	}

}
