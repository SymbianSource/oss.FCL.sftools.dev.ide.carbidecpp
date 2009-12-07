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
* Handles GCCE specific error before invoking CDT error parser
* 
*/
package com.nokia.carbide.cdt.internal.builder.error.parsers;

import java.util.regex.Matcher;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.cdt.core.errorparsers.AbstractErrorParser;
import org.eclipse.cdt.core.errorparsers.ErrorPattern;

/*
 * AbstractErrorParser/ErrorPattern are going to public API in CDT 5.1
 */
abstract public class AbstractGCCEErrorParser extends AbstractErrorParser {
	private String toolNameString;
	private static final ErrorPattern[] patterns = {
		/*
		 *	int groupFileName,
		 *	int groupLineNum,
		 *	int groupDesc,
		 *	int groupVarName,
		 *	int severity
		 */
			// arm-none-symbianelf-g++: \test\test.cpp: No such file or directory
			new ErrorPattern("(.*):\\s*(.*):\\s*(.*(?:[Nn]o such file:\\s*)?[Nn]o such file or directory)", 2, 0, 3, 0, IMarkerGenerator.SEVERITY_ERROR_RESOURCE),	//$NON-NLS-1$
			// arm-none-symbianelf-g++: no input files
			new ErrorPattern("(.*):\\s*(.*[Nn]o input files)", 0, 0, 2, 0, IMarkerGenerator.SEVERITY_ERROR_RESOURCE),	//$NON-NLS-1$
			//arm-none-symbianelf-g++: warning: `-x c++' after last input file has no effect
			//arm-none-symbianelf-g++: error: some message
			new ErrorPattern("(.*):\\s*(.*[(?:[Ww]arning)(?:[Ee]rror)]):\\s*(.*)", 0, 0, 3, 0, IMarkerGenerator.SEVERITY_INFO) {	//$NON-NLS-1$

				@Override
				public int getSeverity(Matcher matcher) {
					String warningGroup = matcher.group(2);
					if (warningGroup != null && warningGroup.indexOf("arning") >= 0) //$NON-NLS-1$
						return IMarkerGenerator.SEVERITY_WARNING;
					
					return IMarkerGenerator.SEVERITY_ERROR_RESOURCE;
				}

			}
	};
	
	protected AbstractGCCEErrorParser(String toolNameString) {
		super(patterns);
		this.toolNameString = toolNameString;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.cdt.internal.errorparsers.AbstractErrorParser#processLine(java.lang.String, org.eclipse.cdt.core.ErrorParserManager)
	 * 
	 * Only pick up message that matches the tool name pattern
	 * 
	 */
	public boolean processLine(String line, ErrorParserManager eoParser) {
		if (line.startsWith(toolNameString)) {
			return super.processLine(line, eoParser);
		}
		return false;
	}
}
