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
import org.eclipse.cdt.internal.errorparsers.GCCErrorParser;

public class GCCEErrorParser extends GCCErrorParser {

	public boolean processLine(String line, ErrorParserManager eoParser) {
		String lineLC = line.toLowerCase();
		
		// check if the tool is installed.  if not then give add a user friendly error to the problems view.
		if (lineLC.startsWith("'arm-none-symbianelf-g++.exe' is not recognized")) {
			eoParser.generateMarker(eoParser.getProject(), 0, "GCCE not found on the PATH.  Please check your GCCE installation.", IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}
		
		// case for arm-none-symbianelf-ld:
		if (lineLC.contains("no such file: no such file or directory") || lineLC.contains("no such file or directory")) {
			eoParser.generateMarker(eoParser.getProject(), 0, line, IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}
		
		// fix bug #4541 (and #5618).  undefined reference linker errors.  note that they work correctly already for udeb
		// build because the error is reported with the source path and line #.  urel builds give an offset to
		// the text section.
		//Test2.cpp:(.text+0x1a4): undefined reference to `foo()' (UREL)
		//..\\src\/Test2.cpp:69: undefined reference to `foo()' (UDEB)
		if ((lineLC.contains("(.text") || lineLC.contains("(.rodata")) && lineLC.contains(": undefined reference to ")) {
			eoParser.generateMarker(eoParser.getProject(), 0, line, IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}

		// fix bug #6417. multiple definition linker errors.  note that they work correctly already for udeb
		// build because the error is reported with the source path and line #.  urel builds give an offset to
		// the text section.
		//b6417_2.cpp:(.text+0x88): multiple definition of `E32Main()'
		//\Symbian\9.2\S60_3rd_FP1\EPOC32\BUILD\Eclipse\rc1torc2workspace\b6417\group\B6417\GCCE\UREL\b6417.o:b6417.cpp:(.text+0x88): first defined here
		if ((lineLC.contains("(.text") || lineLC.contains("(.rodata")) &&
				(lineLC.contains(": multiple definition of ") || lineLC.contains(": first defined here"))) {
			eoParser.generateMarker(eoParser.getProject(), 0, line, IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			return true;
		}

		// the default gcce error parser
		return super.processLine(line, eoParser);
	}
	
}
