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
 * Class to parse errors from abld, makmake and friends (but not bldmake).
 */
public class MakmakeErrorParser extends CarbideBaseErrorParser {

	private static final String MMP_ERROR = "FATAL ERROR: "; //$NON-NLS-1$
	private static final String MMP_WARNING = "WARNING: "; //$NON-NLS-1$

	private String[] errorBeginnings = new String[] {
		"ERROR: ", //$NON-NLS-1$ - from e32env.pm, e32plat.pm, e32variant.pm, makmake.pl, mmp.pm
		"EPOCROOT environment variable must be capitalised", //$NON-NLS-1$ - from e32env.pm
		"Could not open:", //$NON-NLS-1$ - from e32variant.pm
		"MAKMAKE ERROR: ", //$NON-NLS-1$ - from makmake.pl
		MMP_ERROR, // - from mmp.pm
		"ABLD ERROR: ", //$NON-NLS-1$ - from abld.pl
		"INTERNAL ERROR: ", //$NON-NLS-1$ - from trgtype.pm
		"Unknown option: ", //$NON-NLS-1$ - from options.pm
		"This project does not support ", //$NON-NLS-1$ - from abld.pl
	};
		
	private String[] warningBeginnings = new String[] {
		MMP_WARNING, // - from e32env.pm, makmake.pl, mmp.pm
		"MIGRATION_NOTE: ", //$NON-NLS-1$ - from mmp.pm, trgtype.pm
	};
	
	public MakmakeErrorParser() {
		initialise();
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IErrorParser#processLine(java.lang.String, org.eclipse.cdt.core.ErrorParserManager)
	 */
	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		for (String err : errorBeginnings) {
			if (line.startsWith(err)) {
				// mmp.pm messages can be in the form "FATAL ERROR: filePath(lineNum) : errStr"
				if (line.startsWith(MMP_ERROR)) {
					line = line.substring(MMP_ERROR.length());
					if (setFirstColon(line)) {
						if (setFileNameAndLineNumber(line)) {
							msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
							setDescription(line);
							setFile(errorParserManager);
							errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, msgSeverity, null, externalFilePath);
							return true;
						}
					}
				}
				// not in that form, just log the line as the error
				errorParserManager.generateExternalMarker(null, -1, line, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, null);
				return true;
			}
		}
		
		for (String err : warningBeginnings) {
			if (line.startsWith(err)) {
				// mmp.pm messages can be in the form "WARNING: filePath(lineNum) : errStr"
				if (line.startsWith(MMP_WARNING)) {
					line = line.substring(MMP_WARNING.length());
					if (setFirstColon(line)) {
						if (setFileNameAndLineNumber(line)) {
							msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
							setDescription(line);
							setFile(errorParserManager);
							errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, msgSeverity, null, externalFilePath);
							return true;
						}
					}
				}
				// not in that form, just log the line as the warning
				errorParserManager.generateExternalMarker(null, -1, line, IMarkerGenerator.SEVERITY_WARNING, null, null);
				return true;
			}
		}
		return false;
	}


	public void setDescription(String line) {
		msgDescription = line.substring(msgFirstColon + 1).trim();

		if (msgDescription.startsWith(":")) { //$NON-NLS-1$
			msgDescription = msgDescription.substring(1).trim();
		}
	}
}
