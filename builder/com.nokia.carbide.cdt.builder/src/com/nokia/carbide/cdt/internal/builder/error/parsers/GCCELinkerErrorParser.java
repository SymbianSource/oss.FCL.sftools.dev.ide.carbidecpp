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
* Handles GCCE specific error before invoking CDT GNU linker error parser
* 
*/
package com.nokia.carbide.cdt.internal.builder.error.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;

public class GCCELinkerErrorParser extends AbstractGCCEErrorParser {
	static String TOOLNAME = "arm-none-symbianelf-ld";
	static String parsersID = "org.eclipse.cdt.core.GLDErrorParser";
	static IErrorParser ldErrorParser = CCorePlugin.getDefault().getErrorParser(parsersID)[0];
	
	private static final Pattern rodataErrorPattern = Pattern.compile("(.*)\\(\\.rodata\\+.*\\): (.*)"); //$NON-NLS-1$;
	private static final Pattern errorPattern = Pattern.compile(TOOLNAME + "(\\.exe)?:\\s*(.*)");	//$NON-NLS-1$;
	
	public GCCELinkerErrorParser() {
		super(TOOLNAME);
	}
	
	public boolean processLine(String line, ErrorParserManager eoParser) {
		String lineLC = line.toLowerCase();
		Matcher ldMatcher;
		
		// check if the tool is installed.  if not then give add a user friendly error to the problems view.
		if (lineLC.startsWith("'arm-none-symbianelf-ld.exe' is not recognized")) {
			eoParser.generateMarker(eoParser.getProject(), 0, "GCCE not found on the PATH.  Please check your GCCE installation.", IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}
		
		if (ldErrorParser.processLine(line, eoParser)) {
			return true;
		}

		// pick up where CDT failed to pick up
		// consider porting to CDT later
		if (super.processLine(line, eoParser)) {
			return true;
		}
		
		ldMatcher = rodataErrorPattern.matcher(line);
		if (ldMatcher.matches()) {
			eoParser.generateMarker(eoParser.getProject(), 0, line, IMarkerGenerator.SEVERITY_ERROR_RESOURCE, null);
			return true;			
		}
		
		ldMatcher = errorPattern.matcher(line);
		if (ldMatcher.matches()) {
			eoParser.generateMarker(eoParser.getProject(), 0, ldMatcher.group(2), IMarkerGenerator.SEVERITY_ERROR_RESOURCE, null);
			return true;
		}

		return false;
	}

}
