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

public class SBSv2ErrorParser extends CarbideBaseErrorParser {

	private static final Pattern warningPattern = Pattern.compile("<warning>(.*)</warning>"); //$NON-NLS-1$
	private static final Pattern errorPattern = Pattern.compile("<error>(.*)</error>"); //$NON-NLS-1$
	private static final Pattern infoPattern = Pattern.compile("<info>(.*)</info>"); //$NON-NLS-1$

	public SBSv2ErrorParser() {
	}

	public boolean processLine(String line, ErrorParserManager errorParserManager) {

		initialise();
		
		Matcher matcher = infoPattern.matcher(line);
		if (matcher.matches()) {
			return true; // just ignore info messages
		}
		matcher = warningPattern.matcher(line);
		if (matcher.matches()) {
			// strip the tags
			String text = line.substring("<warning>".length(), line.length() - "</warning>".length()); //$NON-NLS-1$ //$NON-NLS-2$
			if (setFirstColon(text)) {
				if (setFileNameAndLineNumber(text)) {
					setFile(errorParserManager);
					setDescription(text);
					errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, IMarkerGenerator.SEVERITY_WARNING, null, externalFilePath);
					return true;
				}
			}
			msgFileName = ""; //$NON-NLS-1$
			msgDescription = matcher.group(1);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, IMarkerGenerator.SEVERITY_WARNING, null, externalFilePath);
			return true;
		}
		
		matcher = errorPattern.matcher(line);
		if (matcher.matches()) {
			// strip the tags
			String text = line.substring("<error>".length(), line.length() - "</error>".length()); //$NON-NLS-1$ //$NON-NLS-2$
			if (setFirstColon(text)) {
				if (setFileNameAndLineNumber(text)) {
					setFile(errorParserManager);
					setDescription(text);
					errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, externalFilePath);
					return true;
				}
			}
			msgFileName = ""; //$NON-NLS-1$
			msgDescription = matcher.group(1);
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, IMarkerGenerator.SEVERITY_ERROR_BUILD, null, externalFilePath);
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

}
