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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IMarkerGenerator;

public class DLLToolErrorParser extends CarbideBaseErrorParser {

	private static final Pattern dllToolErrorPattern = Pattern.compile("(.*)?: (undefined reference to(:)? .*)"); //$NON-NLS-1$;
	private static final Pattern dllToolSyntaxErrorPattern = Pattern.compile("dlltool: Syntax (.*)"); //$NON-NLS-1$;
	
	public DLLToolErrorParser() {
	}

	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		// Known patterns.
		// (a)
		// %s:<file>: undefined reference to '<ref>'
		//

		initialise();

		Matcher dllToolMatcher = dllToolErrorPattern.matcher(line);
		if (dllToolMatcher.matches()) {
			msgFileName = "";
			msgDescription = dllToolMatcher.group(2).trim();
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber,	msgDescription, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, externalFilePath);
			return true;
		}
		dllToolMatcher = dllToolSyntaxErrorPattern.matcher(line);
		if (dllToolMatcher.matches()) {
			msgFileName = "";
			msgDescription = dllToolMatcher.group(1).trim();
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber,	msgDescription, IMarkerGenerator.SEVERITY_ERROR_RESOURCE, null, externalFilePath);
			return true;
		}

		return false;
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
