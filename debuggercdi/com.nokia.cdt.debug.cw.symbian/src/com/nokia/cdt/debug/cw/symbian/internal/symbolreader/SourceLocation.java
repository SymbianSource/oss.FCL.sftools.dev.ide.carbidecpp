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
package com.nokia.cdt.debug.cw.symbian.internal.symbolreader;

import com.nokia.cdt.debug.cw.symbian.symbolreader.ISourceLocation;

public class SourceLocation implements ISourceLocation {

	private String sourceFile;
	private int lineNumber;

	public SourceLocation(String sourceFile, int lineNumber) {
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.ISourceLocation#getSourceFile()
	 */
	public String getSourceFile() {
		return sourceFile;
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.ISourceLocation#getLineNumber()
	 */
	public int getLineNumber() {
		return lineNumber;
	}

}
