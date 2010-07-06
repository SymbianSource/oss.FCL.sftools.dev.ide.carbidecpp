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
import org.eclipse.core.runtime.Path;

public class SBSv2ErrorParser extends CarbideBaseErrorParser {

	private final Pattern msgPattern = Pattern.compile("(.*):(\\d*):(\\d*):(.*)"); //$NON-NLS-1$

	public SBSv2ErrorParser() {
	}

	public boolean processLine(String line, ErrorParserManager errorParserManager) {

		initialise();
		
		if (line.startsWith("<info>"))
			return true; // just ignore info messages
		
		// full message detected
		if (findMessage(errorParserManager, line, "<error>", "</error>", IMarkerGenerator.SEVERITY_ERROR_BUILD)) 
			return true;
		if (findMessage(errorParserManager, line, "<warning>", "</warning>", IMarkerGenerator.SEVERITY_WARNING)) 
			return true;
		
		// some messages are split across multiple lines, so for now, at least show the first line (where <error>, etc. are)
		if (findMessage(errorParserManager, line, "<error>", "", IMarkerGenerator.SEVERITY_ERROR_BUILD)) 
			return true;
		if (findMessage(errorParserManager, line, "<warning>", "", IMarkerGenerator.SEVERITY_WARNING)) 
			return true;

		return false;
	}
	
	protected boolean findMessage(ErrorParserManager errorParserManager, String line, 
			String startStrip, String endStrip, int severity) {
		int idx = line.indexOf(startStrip);
		int endIdx = line.indexOf(endStrip);
		if (idx >= 0 && endIdx >= 0) {
			// strip the tags
			int descStart = idx + startStrip.length();
			int descEnd = line.length() - endStrip.length();
			
			String text = line.substring(descStart, descEnd);
			if (setFirstColon(text)) {
				if (setFileNameAndLineNumber(text) || setSBSv2FileNameAndLineNumber(text)) {
					setFile(errorParserManager);
					setDescription(text);
					errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, severity, null, externalFilePath);
					return true;
				}
			}
			msgFileName = ""; //$NON-NLS-1$
			msgDescription = text;
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, severity, null, externalFilePath);
			return true;
		}
		return false;
	}
	public void setDescription(String line) {
		// Get the iDescription
		msgDescription = line.substring(msgFirstColon + 1).trim();
		if (msgDescription.startsWith(":")) { //$NON-NLS-1$
			msgDescription = msgDescription.substring(1).trim();
		}
	}

	protected boolean setSBSv2FileNameAndLineNumber(String line) {
		// Get the first Substring, which must be in the form of
		// "fileName:line number:postion"
		String firstSubstr = line.substring(msgFirstColon + 1).trim();
		if (firstSubstr != null) {
			Matcher matcher = msgPattern.matcher(firstSubstr);
			if (matcher.matches()) {
				msgFileName = matcher.group(1);
				if (!Path.EMPTY.isValidPath(msgFileName)) {
					return false;
				}
				msgLineNumber = Integer.parseInt(matcher.group(2));
				return true;
			}
		}
		return false;
	}
}
