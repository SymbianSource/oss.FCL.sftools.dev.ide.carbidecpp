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

public class MWLDErrorParser extends CarbideBaseErrorParser {

	private static final Pattern linkerErrorPattern = Pattern.compile("mwldsym2.exe: (.*)"); //$NON-NLS-1$;
	private static final Pattern linkerSkipErrorPattern = Pattern.compile("mwldsym2.exe: referenced from(.*)"); //$NON-NLS-1$;
	private static final Pattern linkerWarningPattern = Pattern.compile("mwldsym2.exe: warning: (.*)"); //$NON-NLS-1$;

	private static final Pattern compilerErrorPattern = Pattern.compile("mwccsym2.exe: (.*)"); //$NON-NLS-1$;
	private static final Pattern compilerWarningPattern = Pattern.compile("mwccsym2.exe: warning: (.*)"); //$NON-NLS-1$;

	public MWLDErrorParser() {
	}

	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		// Known patterns.
		// (a)
		// mwldsym2.exe: <error summary>: <full error>
		//
		// (b)
		// mwldsym2.exe: (referenced from *) (in <file>:<line>)
		//
		// (c)
		// mwldsym2.exe: warning <message>
		//

		initialise();
		
		Matcher cppMatcher = linkerWarningPattern.matcher(line);
		if (cppMatcher.matches()) {
			msgFileName = "";
			//msgLineNumber = 0;
			msgDescription = cppMatcher.group(1);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber,	msgDescription, IMarkerGenerator.SEVERITY_WARNING, null, externalFilePath);
			return true;
		}
		
		cppMatcher = linkerSkipErrorPattern.matcher(line);
		if (cppMatcher.matches()) {
			return false; // skip this patter, the ordering of the messages is too confusing. Bug 3253
		}
		
		cppMatcher = linkerErrorPattern.matcher(line);
		if (cppMatcher.matches()) {
			msgFileName = "";
			//msgLineNumber = 0;
			msgDescription = cppMatcher.group(1);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber,	msgDescription, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, externalFilePath);
			return true;
		}
		
		// catch any compiler errors too!
		cppMatcher = compilerWarningPattern.matcher(line);
		if (cppMatcher.matches()) {
			msgFileName = "";
			//msgLineNumber = 0;
			msgDescription = cppMatcher.group(1);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber,	msgDescription, IMarkerGenerator.SEVERITY_WARNING, null, externalFilePath);
			return true;
		}

		cppMatcher = compilerErrorPattern.matcher(line);
		if (cppMatcher.matches()) {
			msgFileName = "";
			//msgLineNumber = 0;
			msgDescription = cppMatcher.group(1);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber,	msgDescription, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, externalFilePath);
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
