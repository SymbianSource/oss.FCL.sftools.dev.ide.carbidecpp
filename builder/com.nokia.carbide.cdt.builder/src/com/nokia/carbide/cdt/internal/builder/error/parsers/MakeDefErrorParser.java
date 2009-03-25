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

public class MakeDefErrorParser extends CarbideBaseErrorParser {
		
	private String iMessagePrefix = null;
	private int    iPrefixableMessagesLeft;
	
	public MakeDefErrorParser() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IErrorParser#processLine(java.lang.String, org.eclipse.cdt.core.ErrorParserManager)
	 */
	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		// Known patterns.
		// (a)
		// <space><space>filename(lineno)<space>:<space>description
		//
		// (b)
		// filename(lineno) : Warning: description
		//
		// (c)
		// filename(lineno) : Error: description

		/*
		 * Check for the "header" messages that preceed a number of specific
		 * messages, e.g. "MAKEDEF WARNING: 2 export(s) not yet Frozen:" and
		 * save this header message for use as a prefix to the specific messages
		 * that follow the header.
		 */
		final String MAKEDEF_WARNING_STR = "MAKEDEF WARNING:"; //$NON-NLS-1$
		if (line.startsWith(MAKEDEF_WARNING_STR)) {
			int lAfterNum = line.indexOf("export(s) not yet Frozen");
			if (lAfterNum != -1) {
				int lBeforeNum = MAKEDEF_WARNING_STR.length();
				iPrefixableMessagesLeft = Integer.parseInt(line.substring(lBeforeNum, lAfterNum).trim());
				iMessagePrefix = line;
			}
			return false;
		}
		
		final String MAKEDEF_ERROR_STR = "MAKEDEF ERROR:"; //$NON-NLS-1$
		if (line.startsWith(MAKEDEF_ERROR_STR)) {
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			// Here we only parse one line at at time so the next line is lost, but it generally lists some missing export.
			errorParserManager.generateMarker(null, -1, line + " See the console for more information.", msgSeverity, null);
			return true;
		}

		// this is a two-line error.  we only get them one at a time though and can't peek at the next line.
		// we could cache this and try to handle it with the second line, but there's really no pattern to it
		// other than the text itself.  if we pattern the exact text then we may as well just hard code it here.
		final String EFREEZE__CANT_APPEND_ERROR_STR = "EFREEZE ERROR: Can't append "; //$NON-NLS-1$
		if (line.startsWith(EFREEZE__CANT_APPEND_ERROR_STR)) {
			String errStr = line + " as file is not writeable.  Check source code control system."; //$NON-NLS-1$
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			errorParserManager.generateMarker(null, -1, errStr, msgSeverity, null);
			 
			return true;
		}

		final String EFREEZE_ERROR_STR = "EFREEZE ERROR:"; //$NON-NLS-1$
		if (line.startsWith(EFREEZE_ERROR_STR)) {
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			errorParserManager.generateMarker(errorParserManager.getProject(), -1, line, msgSeverity, null);
			return true;
		}

		final String NO_DEF_ERROR_STR = "could not open "; //$NON-NLS-1$
		if (line.startsWith(NO_DEF_ERROR_STR)) {
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			errorParserManager.generateMarker(errorParserManager.getProject(), -1, line, msgSeverity, null);
			return true;
		}

		if (!setFirstColon(line)) {
			return false;
		}
		setDescription(line);
		if (!setFileNameAndLineNumber(line)) {
			return false;
		}
		String[] extensionsToExclude = {".pkg", ".rss"};
		if (hasExcludedExtension(extensionsToExclude)) {
			return false;
		}
		
		// see if it's an error
		if (msgDescription.toLowerCase().indexOf("error") >= 0) {
			msgSeverity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
		}

		/* If there is a prefix messages to append, append it */
		if (iPrefixableMessagesLeft > 0) {
			--iPrefixableMessagesLeft;
			assert iMessagePrefix != null;
			msgDescription = iMessagePrefix + " " + msgDescription;
			//The following is a defect fix. The severity should be set to WARNING
			//even if the name of the export has "error" in it. 
			msgSeverity = IMarkerGenerator.SEVERITY_WARNING; 
			if (iPrefixableMessagesLeft == 0) { iMessagePrefix = null; }
		}

		setFile(errorParserManager);
		errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, msgSeverity, null, externalFilePath);
		return true;
	}
	
	
	public void setDescription(String aLine) {
		// Get the iDescription
		msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
		msgDescription = aLine.substring(msgFirstColon + 1).trim();

		if (msgDescription.startsWith(":")) { //$NON-NLS-1$
			msgDescription = msgDescription.substring(1).trim(); //$NON-NLS-1$
		}
	}

}
