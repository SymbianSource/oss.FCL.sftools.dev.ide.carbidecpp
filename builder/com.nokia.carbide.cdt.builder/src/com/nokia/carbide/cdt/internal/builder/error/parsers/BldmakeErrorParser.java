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

/**
 * Class to parse errors/warnigns from bldmake.pl
 */
public class BldmakeErrorParser extends CarbideBaseErrorParser {
	
	private String[] errorBeginnings = new String[] {
		"ERROR: ", //$NON-NLS-1$ - from e32env.pm, e32plat.pm, e32variant.pm
		"EPOCROOT environment variable must be capitalised", //$NON-NLS-1$ - from e32env.pm
		"Could not open:", //$NON-NLS-1$ - from e32variant.pm
		"BLDMAKE ERROR: ", //$NON-NLS-1$ - from bldmake.pl
		"Unknown option: ", //$NON-NLS-1$ - from options.pm
	};
	
	private String[] warningBeginnings = new String[] {
		"WARNING: ", //$NON-NLS-1$ - from e32env.pm
		"BLDMAKE WARNING: ", //$NON-NLS-1$ - from bldmake.pl
	};

	private boolean inFatalErrorsList = false;
	private boolean inWarningsList = false;
	
	
	public BldmakeErrorParser() {
		initialise();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IErrorParser#processLine(java.lang.String, org.eclipse.cdt.core.ErrorParserManager)
	 */
	public boolean processLine(String line, ErrorParserManager errorParserManager) {

		for (String err : errorBeginnings) {
			if (line.startsWith(err)) {
				errorParserManager.generateExternalMarker(null, -1, line, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, null);
				return true;
			}
		}
		
		for (String err : warningBeginnings) {
			if (line.startsWith(err)) {
				errorParserManager.generateExternalMarker(null, -1, line, IMarkerGenerator.SEVERITY_WARNING, null, null);
				return true;
			}
		}

		// now check for fatal errors and warnings.  these can be single or queued up in a list
		if (line.indexOf(" FATAL ERROR(S):") > 0) { //$NON-NLS-1$
			inFatalErrorsList = true;
			return true;
		}
		
		if (inFatalErrorsList) {
			if (generateMarkerForLine(line, IMarkerGenerator.SEVERITY_ERROR_BUILD, errorParserManager)) {
				return true;
			} else {
				inFatalErrorsList = false;
			}
		}

		if (line.indexOf("BLD.INF WARNING(S):") > 0) { //$NON-NLS-1$
			inWarningsList = true;
			return true;
		}
		
		if (inWarningsList) {
			if (generateMarkerForLine(line, IMarkerGenerator.SEVERITY_WARNING, errorParserManager)) {
				return true;
			} else {
				inWarningsList = false;
			}
		}

		return false;
	}
	
	private boolean generateMarkerForLine(String line, int markerType, ErrorParserManager errorParserManager) {
		// it would look like filepath(lineNum) : errStr
		if (!setFirstColon(line)) {
			return false;
		}
		if (!setFileNameAndLineNumber(line)) {
			return false;	
		}
		msgSeverity = markerType;
		setDescription(line);
		setFile(errorParserManager);
		errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, msgSeverity, null, externalFilePath);
		return true;
	}

	@Override
	public void setDescription(String line) {
		msgDescription = (msgSeverity == IMarkerGenerator.SEVERITY_WARNING ? "BLD.INF WARNING: " : "BLD.INF FATAL ERROR: ") //$NON-NLS-1$ //$NON-NLS-2$
			+ line.substring(msgFirstColon + 1).trim();
	}
}
