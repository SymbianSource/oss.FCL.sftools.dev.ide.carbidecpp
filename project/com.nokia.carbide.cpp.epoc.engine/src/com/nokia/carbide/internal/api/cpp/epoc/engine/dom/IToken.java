/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;


public interface IToken {
	/** end of file */
	int EOF = 0;
	/** a single punctuation character */
	int PUNC = 1;
	/** character sequence starting with a number and followed
	 * by alphanumeric characters, including '.' and 'e+' or 'e-'
	 */
	int NUMBER = 2;
	/** character sequence starting with a letter or '_' and followed
	 * by alphanumeric characters including '_' */
	int IDENTIFIER = 3;
	/** a string literal expressed as "..." */
	int STRING = 4;
	/** raw text, uninterpreted */
	int RAW = 5;
	/** meta token for entering an IConditionalBlock */
	int BLOCK_ENTER = 6;
	/** meta token for exiting an IConditionalBlock */
	int BLOCK_EXIT = 7;
	/** meta token for moving to an #else or #elif in an IConditionalBlock */
	int BLOCK_SWITCH = 8;
	/** end of line */
	int EOL = 9;
	/** a character literal expressed as '...' */
	int CHAR = 10;
	/** meta token for moving to a new #include file */
	int FILE_SWITCH = 11;
	/** last type, not legal */
	int LAST = 12;

	/** Get the type */
	int getType();
	
	/** Get the raw token text */
	String getText();
	
	/** Get the interpreted content of the token 
	 * (e.g. for STRING, an unescaped char sequence without quotes) */
	String getContent();
	
	/** does this follow a space (or comment)? */
	boolean followsSpace();

	/** set follows space */
	void setFollowsSpace(boolean followsSpace);
	
	/** does this follow a newline? */
	boolean followsNewline();

	/** Get the location of the token */
	ITokenLocation getLocation();
	
	/** get the character offset of raw text within the location */
	int getOffset();
	
	/** get the length of raw text */
	int getLength();

	/** get start offset plus length */
	int getEndOffset();

	/** Copy the token contents */
	IToken copy();
}
