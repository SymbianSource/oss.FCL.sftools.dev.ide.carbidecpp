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
* Handles GCCE specific error before invoking CDT GNU assembler error parser
* 
*/
package com.nokia.carbide.cdt.internal.builder.error.parsers;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;

public class GCCEAssemblerErrorParser extends AbstractGCCEErrorParser {
	static String parsersID = "org.eclipse.cdt.core.GASErrorParser";
	static IErrorParser asErrorParser = CCorePlugin.getDefault().getErrorParser(parsersID)[0];
	
	public GCCEAssemblerErrorParser() {
		super("arm-none-symbianelf-as");		//$NON-NLS-1$
	}
	
	public boolean processLine(String line, ErrorParserManager eoParser) {
		String lineLC = line.toLowerCase();
		// check if the tool is installed.  if not then give add a user friendly error to the problems view.
		if (lineLC.startsWith("'arm-none-symbianelf-as.exe' is not recognized")) {
			eoParser.generateMarker(eoParser.getProject(), 0, "GCCE not found on the PATH.  Please check your GCCE installation.", IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}
	
		if (asErrorParser.processLine(line, eoParser)) {
			return true;
		}
		
		// pick up where CDT failed to pick up
		// consider porting to CDT later
		if (super.processLine(line, eoParser)) {
			return true;
		}
		
		return false;
	}

}
