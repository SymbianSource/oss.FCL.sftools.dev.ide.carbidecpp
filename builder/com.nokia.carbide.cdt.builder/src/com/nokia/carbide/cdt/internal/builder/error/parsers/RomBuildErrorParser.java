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

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;

public class RomBuildErrorParser implements IErrorParser  {

	public RomBuildErrorParser() {
		dependenceGraphErrMsg = "";
		dependenceGraphErrLines = 0;
		msgSeverity = IMarkerGenerator.SEVERITY_ERROR_RESOURCE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IErrorParser#processLine(java.lang.String, org.eclipse.cdt.core.ErrorParserManager)
	 */
	public boolean processLine(String aLine, ErrorParserManager aErrorParserManager) {

		// Dependency graph error message is a multiline message.
		// so needs special handling
		// Generally this error msg starts with "Can't build dependence graph for" 
		// and ends with "is not in rom."
		if (dependenceGraphErrMsg.length() > 0)
		{
			dependenceGraphErrMsg += " ";
			dependenceGraphErrMsg += aLine;
			dependenceGraphErrLines++;
			
			// Generally the dependence graph error msg ends with "is not in rom."
			// As a safe check, the maximum number of possible number of lines 
			// allowed for dependence graph error msg is 5
			if (dependenceGraphErrMsg.endsWith("is not in rom.") || dependenceGraphErrLines==5) { //$NON-NLS-1$
				aErrorParserManager.generateMarker(null, -1, dependenceGraphErrMsg, msgSeverity, null);
				dependenceGraphErrMsg = "";
				dependenceGraphErrLines = 0;
				return true;
			}
			
			return false;
		}		

		// Known patterns.
		// (a)
		// "ERROR:"
		//
		// (b)
		// "WARNING:"
		//
		HashSet<String> lineErrorPrefixes = new HashSet<String>();		
		lineErrorPrefixes.add("ERROR:"); //$NON-NLS-1$
		lineErrorPrefixes.add("error:"); //$NON-NLS-1$

		HashSet<String> lineWarningPrefixes = new HashSet<String>();
		lineWarningPrefixes.add("WARNING:"); //$NON-NLS-1$
		lineWarningPrefixes.add("warning:"); //$NON-NLS-1$

		int msgSeverity = IMarkerGenerator.SEVERITY_ERROR_RESOURCE;
		if (checkForLineBeginnings(aLine, lineErrorPrefixes.toArray(new String[lineErrorPrefixes.size()]))) {
			dependenceGraphErrMsg = aLine.substring("ERROR:".length()).trim();
			if (!dependenceGraphErrMsg.startsWith("Can't build dependence graph for")) { //$NON-NLS-1$
				aErrorParserManager.generateMarker(null, -1, dependenceGraphErrMsg, msgSeverity, null);
				dependenceGraphErrMsg = "";
				return true;
			} else {
				dependenceGraphErrLines++;
				return false;
			}
		}

		msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
		if (checkForLineBeginnings(aLine, lineWarningPrefixes.toArray(new String[lineWarningPrefixes.size()]))) {
			aErrorParserManager.generateMarker(null, -1, aLine.substring("WARNING:".length()).trim(), msgSeverity, null); //$NON-NLS-1$
			return true;
		}
		return false;

	}

	protected boolean checkForLineBeginnings(String currLine, String[] matchLines) {

		boolean fileWideMessage = false;
		for (String testLine : matchLines) {
			if (currLine.startsWith(testLine)) {
				fileWideMessage = true;
				break;
			}
		}
		return fileWideMessage;
	}
	
	private String dependenceGraphErrMsg;
	private int dependenceGraphErrLines;
	private int msgSeverity;
	
}
