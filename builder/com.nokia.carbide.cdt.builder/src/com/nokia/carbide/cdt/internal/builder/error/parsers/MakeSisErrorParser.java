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

import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;

public class MakeSisErrorParser extends CarbideBaseErrorParser {
	
	public MakeSisErrorParser() {
	}

	public boolean processLine(String aLine, ErrorParserManager aErrorParserManager) {
		// Known patterns.
		// (a)
		// filename(lineno) : description
		//
		// (b)
		// filename(lineno) : Warning: description
		//
		// (c)
		// filename(lineno) : Error: description
		
		if (aLine.equalsIgnoreCase("error: invalid destination file")){
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			aErrorParserManager.generateMarker(null, -1, aLine, msgSeverity, null);
		}
		
		if (aLine.startsWith("Error : Cannot find file")) {
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			aErrorParserManager.generateMarker(null, -1, aLine, msgSeverity, null);
		}
		
		if (!setFirstColon(aLine)) {
			return false;
		}
		setDescription(aLine);
		
		if (aLine.contains(CarbideCPPBuilder.RESOLVED_PKG_PREFIX)){
			aLine = aLine.replaceAll(CarbideCPPBuilder.RESOLVED_PKG_PREFIX, "");
		}
		
		if (!setFileNameAndLineNumber(aLine)) {
			return false;
		}
		String[] extensionsToExclude = {".rss", ".def"};
		if (hasExcludedExtension(extensionsToExclude)) {
			return false;
		}
		setFile(aErrorParserManager);
		
		aErrorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, msgSeverity, null, externalFilePath);
		return true;
	}

	
	public void setDescription(String aLine) {
		// Get the iDescription
		msgDescription = aLine.substring(msgFirstColon + 1).trim();
		if (msgDescription.matches("(?i)warning.*")) {
			// Remove the warning.
			msgDescription = msgDescription.substring("warning".length()).trim(); //$NON-NLS-1$
			msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
		} else if (msgDescription.matches("(?i)error.*")) {
			// Remove the error.
			msgDescription = msgDescription.substring("error".length()).trim(); //$NON-NLS-1$
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_RESOURCE;
		}
		if (msgDescription.startsWith(":")) { //$NON-NLS-1$
			msgDescription = msgDescription.substring(1).trim(); //$NON-NLS-1$
		}
	}

}
