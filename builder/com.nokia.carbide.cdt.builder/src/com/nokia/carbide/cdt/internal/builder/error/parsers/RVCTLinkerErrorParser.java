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
import org.eclipse.cdt.core.IMarkerGenerator;

public class RVCTLinkerErrorParser extends CarbideBaseErrorParser {

	public RVCTLinkerErrorParser() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IErrorParser#processLine(java.lang.String, org.eclipse.cdt.core.ErrorParserManager)
	 */
	public boolean processLine(String aLine, ErrorParserManager aErrorParserManager) {
		// Known patterns.
		// (a)
		// "Fatal error: L..."
		//
		// (b)
		// "Error: L..."
		//
		// (c)
		// "Warning: L..."


		initialise();
		
		HashSet<String> lineErrorPrefixes = new HashSet<String>();
		lineErrorPrefixes.add("Fatal error: L"); //$NON-NLS-1$
		lineErrorPrefixes.add("Error: L"); //$NON-NLS-1$

		HashSet<String> lineWarningPrefixes = new HashSet<String>();
		lineWarningPrefixes.add("Warning: L"); //$NON-NLS-1$

		msgSeverity = IMarkerGenerator.SEVERITY_ERROR_RESOURCE;
		if (checkForLineBeginnings(aLine, lineErrorPrefixes.toArray(new String[lineErrorPrefixes.size()]))) {
			aErrorParserManager.generateMarker(null, -1, aLine, msgSeverity, null);
			return true;
		}

		msgSeverity = IMarkerGenerator.SEVERITY_WARNING;
		if (checkForLineBeginnings(aLine, lineWarningPrefixes.toArray(new String[lineWarningPrefixes.size()]))) {
			aErrorParserManager.generateMarker(null, -1, aLine, msgSeverity, null);
			return true;
		}
		return false;

	}

	
	public void setDescription(String aLine) {
		// currently unused
	}

}
