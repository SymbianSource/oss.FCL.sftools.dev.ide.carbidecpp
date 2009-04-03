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

/**
 * A node which is literal text.  
 */
public interface IASTLiteralTextNode extends IASTNode {
	/** This enum determines how strings are interpreted. */
	enum EStyle {
		/** The text is uninterpreted. */
		RAW,
		/** The text is treated like a preprocessor string literal if quoted. */
		PREPROCESSOR
	}
	
	/** Get the style (immutable) 
	 * @return EStyle */
	EStyle getStyle();
	
	/** Set the text for the expression. */
	void setValue(String value);
	
	/** Get the text for the expression.  This is trimmed text with any comments removed.  */
	String getValue();
	
	/** Set the value to the formatted literal string in the given radix. */
	void setValue(int value, int radix);
	
	/** Set the value to the formatted literal string in decimal. */
	void setValue(int value);

    /** Get value as integer 
     * @throws NumberFormatException
     */
    public int getIntValue();

    /** Get value as floating point
     * @throws NumberFormatException
     */
    public double getFloatValue();
    
    /** Get character value (first char of string value) 
     */
    public char getCharValue();

    /** Get string value.  If the original node was quoted, this string is still quoted.
     */
    public String getStringValue();
	
    /**
	 * Must implement to compare all variant fields (including
	 * document, path, region).
	 * @see Object#equals(java.lang.Object)
	 */
	boolean equals(Object obj);
	
	/**
	 * Must implement to satisfy requirement that if a.equals(b),
	 * then a.hashCode() == b.hashCode().
	 * @see Object#hashCode()
	 */
	int hashCode();
	
}
