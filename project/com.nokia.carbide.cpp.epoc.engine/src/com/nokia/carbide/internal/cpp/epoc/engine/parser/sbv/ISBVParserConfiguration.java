/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.sbv;


/**
 * Extend parser configuration to tell which statements and kinds are recognized, e.g.,
 * according to the current SDK.
 * <p>
 * All the calls are passed a canonical upper-case keyword.
 *
 */
public interface ISBVParserConfiguration {
	/** Unknown or illegal for this SDK */
	int UNKNOWN_STATEMENT = 0;
	/** A statement which appears alone on a line */ 
	int FLAG_STATEMENT = 1;
	/** A statement taking a single argument */ 
	int SINGLE_ARGUMENT_STATEMENT = 2;
	/** A statement with a list of arguments, all alike */ 
	int LIST_ARGUMENT_STATEMENT = 3;
	
	/** Not returned here, but in EMMPStatement */
	int AIF_STATEMENT = 4;
	/** Not returned here, but in EMMPStatement */
	int OPTION_STATEMENT = 5;
	/** Not returned here, but in EMMPStatement */
	int START_BLOCK_STATEMENT = 6;
	/** Not returned here, but in EMMPStatement */
	int UID_STATEMENT = 7;
	/** Not returned here, but in EMMPStatement */
	int BITMAP_SOURCE_STATEMENT = 8;

	/** Is AIF supported? */
	boolean isAifStatementRecognized();
	/** Is SOURCE (for START BITMAP) supported? */
	boolean isBitmapSourceStatementRecognized();
	/** Is OPTION supported? */
	boolean isOptionStatementRecognized();
	/** Is START ... END supported? */
	boolean isStartBlockStatementRecognized();
	/** Is UID supported? */
	boolean isUidStatementRecognized();

	/** Classify this generic statement */
	int categorizeStatement(String keyword);
}
