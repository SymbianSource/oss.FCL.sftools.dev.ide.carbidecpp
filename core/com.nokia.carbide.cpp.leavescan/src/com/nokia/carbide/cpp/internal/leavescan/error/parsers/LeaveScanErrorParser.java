/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
*/
package com.nokia.carbide.cpp.internal.leavescan.error.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.resources.IFile;

public class LeaveScanErrorParser implements IErrorParser {

	private Pattern leaveScanErrorPattern = Pattern.compile("(.*)?(\\((\\d+)\\))\\s+:\\s+(.*)"); //$NON-NLS-1$;
	private Pattern leaveScanWarningPattern = Pattern.compile("(.*)?(\\((\\d+)\\))\\s+:\\s+Warning\\s+(.*)"); //$NON-NLS-1$;
	

	
	public LeaveScanErrorParser() {
	}

	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		// Known patterns.
		//
		// (a) warning
		// <file>(<line number>) : Warning - <message>
		// e.g..... 
		// C:\Symbian\UIQ3SDK\Examples\UIQ\QMyDirectory\Src\MyDirectoryAppUi.cpp(211) : Warning - CMyDirectoryAppUi::DoMergeCategoriesL appears to contain no leavers.
		//
		//
		// (b) error
		// <file>(<line number>) : <message>
		// e.g....
		// C:\Symbian\9.1\S60_3rd_MR\Examples\Basics\StaticDLL\CreateStaticDLL.cpp(40) : CMessenger::Construct Calls a function that can leave. QUALIFIED WITH-> // copy given string into own descriptor

		Matcher warningMatcher = leaveScanWarningPattern.matcher(line);
		if (warningMatcher.matches()) {
			String fileName = warningMatcher.group(1);
			IFile file = errorParserManager.findFileName(fileName);
			String lineNumberStr = warningMatcher.group(3);
			int lineNumber = Integer.parseInt(lineNumberStr);
			String msgDescription = warningMatcher.group(4);
			msgDescription = msgDescription.trim();
			errorParserManager.generateMarker(file, lineNumber,	msgDescription, IMarkerGenerator.SEVERITY_WARNING, null);
			return true;
		}
		
		Matcher errorMatcher = leaveScanErrorPattern.matcher(line);
		if (errorMatcher.matches()) {
			String fileName = errorMatcher.group(1);
			IFile file = errorParserManager.findFileName(fileName);
			String lineNumberStr = errorMatcher.group(3);
			int lineNumber = Integer.parseInt(lineNumberStr);
			String msgDescription = errorMatcher.group(4);
			msgDescription = msgDescription.trim();
			errorParserManager.generateMarker(file, lineNumber,	msgDescription, IMarkerGenerator.SEVERITY_ERROR_RESOURCE, null);
			return true;
		}
		
		// check for other process errors
		if (line.toLowerCase().contains("the system cannot find the path specified") ||
			line.toLowerCase().contains("is not supported in this release") ||
			line.toLowerCase().contains("is not recognized as an internal or external command")){
			errorParserManager.generateMarker(null, -1,	"Cannot run leavescan. Check that leavescan is on your PATH or go to the Carbide.c++ > Leavescan Preferences and set the Leavescan Directory. Leavescan.exe can be found at: http://www3.symbiandevnet.com/faq.nsf/0/f3765f69e4fb9baa80256a570051b952?OpenDocument", IMarkerGenerator.SEVERITY_ERROR_BUILD, null);

		}

		return false;
	}
	
}
