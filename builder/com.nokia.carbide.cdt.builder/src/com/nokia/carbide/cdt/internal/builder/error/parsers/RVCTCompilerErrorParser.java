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

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.runtime.Path;

public class RVCTCompilerErrorParser extends CarbideBaseErrorParser {

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IErrorParser#processLine(java.lang.String, org.eclipse.cdt.core.ErrorParserManager)
	 */
	public boolean processLine(String aLine, ErrorParserManager aErrorParserManager) {
		// Known patterns.
		// (a)
		// <quote><filename><quote><comma><spaces>line<spaces><lineno>:<spaces>Warning:<spaces>description
		//
		// (b)
		// <quote><filename><quote><comma><spaces>line<spaces><lineno>:<spaces>Error:<spaces>description
		
		initialise();
		
		// check for other errors that don't have a common pattern
		// e.g. "no source": Error:  #5: cannot open source input file "\CarbideB18_workspace\BlackFlag\group\SRC\dbg_debug_menu.cpp": No such file or directory
		if (aLine.contains("No such file or directory") && !aLine.contains(" line ")){
			aErrorParserManager.generateMarker(aErrorParserManager.getProject(), 0, aLine, IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}
		
		
		// check if the tool is installed.  if not then give add a user friendly error to the problems view.
		if (aLine.toLowerCase().startsWith("'armcc.exe' is not recognized")) {
			aErrorParserManager.generateMarker(aErrorParserManager.getProject(), 0, "RVCT not found on the PATH.  Please check your RVCT installation.", IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}
		
		// Check for license.
		// TODO: This seems to follow a pattern of "Error: <code>: <message>:" So we might do a regexp check
		// in the future.
		if (aLine.toLowerCase().contains("cannot obtain license for compiler")){
			aErrorParserManager.generateMarker(aErrorParserManager.getProject(), 0, aLine, IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}
		
		
		if (!setFirstColon(aLine)) {
			return false;
		}
		setDescription(aLine);
		if (!setFileNameAndLineNumber(aLine)) {
			return false;
		}
		
		setFile(aErrorParserManager);
		aErrorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, msgSeverity, null, externalFilePath);
		return true;
	}
	
	
	public void setDescription(String aLine) {
		// Get the iDescription
		msgDescription = aLine.substring(msgFirstColon + 1).trim();
		if (msgDescription.matches("(?i)warning.*")) { //$NON-NLS-1$
			// Remove the warning.
			msgDescription = msgDescription.substring("warning".length()).trim(); //$NON-NLS-1$
			msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
		} else if (msgDescription.matches("(?i)error.*")) { //$NON-NLS-1$
			// Remove the error.
			msgDescription = msgDescription.substring("error".length()).trim(); //$NON-NLS-1$
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_RESOURCE;
		}
		if (msgDescription.startsWith(":")) { //$NON-NLS-1$
			msgDescription = msgDescription.substring(1).trim(); //$NON-NLS-1$
		}
	}

	
	protected boolean setFileNameAndLineNumber(String aLine) {
		// Get the first Substring, which must be in the form of
		// <quote><filename><quote><comma><spaces>line<spaces><lineno>
		String WHITE_SPACE = " ";
		String QUOTE = "\"";
		String firstSubstr = aLine.substring(0, msgFirstColon);
		if (firstSubstr != null) {
			try {
				String lineNumber = firstSubstr.substring(firstSubstr
						.lastIndexOf(WHITE_SPACE) + 1, msgFirstColon);
				try {
					msgLineNumber = Integer.parseInt(lineNumber);
				} catch (NumberFormatException e) {
					// Failed to get Line Number.
					return false;
				}
			} catch (StringIndexOutOfBoundsException e) {
				return false;
			}

			// Deal with filename
			msgFileName = firstSubstr.substring(QUOTE.length(), firstSubstr
					.indexOf("\"", QUOTE.length()));

			// The pattern is to general we have to guard:
			// Before making this pattern a marker we do one more check
			// The fileName that we extract __must__ look like a valid file
			// name.
			if (!Path.EMPTY.isValidPath(msgFileName)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

}
