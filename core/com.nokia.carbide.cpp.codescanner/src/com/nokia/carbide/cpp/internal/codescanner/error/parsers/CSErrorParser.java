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

package com.nokia.carbide.cpp.internal.codescanner.error.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.resources.IFile;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;

/**
 * A class to process and generate markers for CodeScanner errors and warnings.
 */
public class CSErrorParser implements IErrorParser {

	// IDs defined in plugin.xml
	public static final String CS_ERROR_PARSER_ID = CSPlugin.PLUGIN_ID + ".CSErrorParser";

	private Pattern CSInfoPattern = Pattern.compile("(.*)?(\\((\\d+)\\))\\s+:\\s+info:\\s+(.*)"); //$NON-NLS-1$;
	private Pattern CSWarningPattern = Pattern.compile("(.*)?(\\((\\d+)\\))\\s+:\\s+warning:\\s+(.*)"); //$NON-NLS-1$;
	private Pattern CSErrorPattern = Pattern.compile("(.*)?(\\((\\d+)\\))\\s+:\\s+error:\\s+(.*)"); //$NON-NLS-1$;

	/**
	 * Constructor for CSErrorParser.
	 */
	public CSErrorParser() {
	}

	/**
	 * Process a line of output from CodeScanner.
	 * 
	 * @param line a line of output from CodeScanner
	 * @param errorParserManager the class that manages various aspects of error parsing
	 */
	public boolean processLine(String line, ErrorParserManager errorParserManager) {
		// Known CodeScanner error/warning patterns.
		//
		// (a) info
		// <file>(<line number>) : info: <message>
		// e.g....
		// C:\Examples\CreateStaticDLL.cpp(40) : info: Commented-out code
		//
		// (b) warning
		// <file>(<line number>) : warning: <message>
		// e.g..... 
		// C:\Examples\MyDirectoryAppUi.cpp(211) : warning: use of double semicolon
		//
		// (c) error
		// <file>(<line number>) : error: <message>
		// e.g....
		// C:\Examples\CreateStaticDLL.cpp(40) : error: exporting inline functions

		// check whether this line is an info message and if it is,
		// generate an info marker
		Matcher infoMatcher = CSInfoPattern.matcher(line);
		if (infoMatcher.matches()) {
			String fileName = infoMatcher.group(1);
			IFile file = errorParserManager.findFileName(fileName);
			String lineNumberStr = infoMatcher.group(3);
			int lineNumber = Integer.parseInt(lineNumberStr);
			String msgDescription = infoMatcher.group(4);
			msgDescription.trim();
			errorParserManager.generateMarker(file, lineNumber,	msgDescription, IMarkerGenerator.SEVERITY_INFO, null);
			return true;
		}
		
		// check whether this line is a warning message and if it is,
		// generate a warning marker
		Matcher warningMatcher = CSWarningPattern.matcher(line);
		if (warningMatcher.matches()) {
			String fileName = warningMatcher.group(1);
			IFile file = errorParserManager.findFileName(fileName);
			String lineNumberStr = warningMatcher.group(3);
			int lineNumber = Integer.parseInt(lineNumberStr);
			String msgDescription = warningMatcher.group(4);
			msgDescription.trim();
			errorParserManager.generateMarker(file, lineNumber,	msgDescription, IMarkerGenerator.SEVERITY_WARNING, null);
			return true;
		}
		
		// check whether this line is an error message and if it is,
		// generate an error marker
		Matcher errorMatcher = CSErrorPattern.matcher(line);
		if (errorMatcher.matches()) {
			String fileName = errorMatcher.group(1);
			IFile file = errorParserManager.findFileName(fileName);
			String lineNumberStr = errorMatcher.group(3);
			int lineNumber = Integer.parseInt(lineNumberStr);
			String msgDescription = errorMatcher.group(4);
			msgDescription.trim();
			errorParserManager.generateMarker(file, lineNumber,	msgDescription, IMarkerGenerator.SEVERITY_ERROR_RESOURCE, null);
			return true;
		}
		
		// check for other process errors
		if (line.toLowerCase().contains("the system cannot find the path specified") ||
			line.toLowerCase().contains("is not supported in this release") ||
			line.toLowerCase().contains("is not recognized as an internal or external command")){
			errorParserManager.generateMarker(null, -1,	Messages.getString("ErrorParser.ProecessError"), IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
		}

		return false;
	}

}
