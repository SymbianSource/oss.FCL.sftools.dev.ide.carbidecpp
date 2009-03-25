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

public class Elf2E32ErrorParser extends CarbideBaseErrorParser {

	public Elf2E32ErrorParser() {
	}

	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		// Known patterns.
		// (a)
		// elf2e32 : <error code>: <full error>
		//

		initialise();

		String lowerLine = line.toLowerCase();
		
		if (lowerLine.startsWith("elf2e32:") || lowerLine.startsWith("elf2e32 :")) {
			int type = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			if (lowerLine.indexOf("warning:") > 0) {
				type = IMarkerGenerator.SEVERITY_WARNING;
			}
			
			msgFileName = "";
			msgDescription = line;
			setFile(errorParserManager);
			errorParserManager.generateExternalMarker(msgIFile, msgLineNumber, msgDescription, type, null, externalFilePath);
			return true;
		}

		return false;
	}
	
	public void setDescription(String line) {
		//  currently unused
	}

}
