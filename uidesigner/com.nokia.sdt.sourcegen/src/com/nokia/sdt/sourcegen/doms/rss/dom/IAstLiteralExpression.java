/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.dom;

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression;

/**
 * A literal value
 * 
 * 
 *
 */
public interface IAstLiteralExpression extends IAstExpression, IAstPreprocessorExpression {
    public static final int K_INTEGER = 1;
    public static final int K_FLOAT = 2;
    public static final int K_CHAR = 3;
    public static final int K_STRING = 4;
    public static final int K_LAST = 4;
    
    /** Get the kind of literal (K_....) */
    public int getKind();
    
    /** Set the literal kind (K_...) */
    public void setKind(int kind);
    
    /** Get the value (never null) */
    public String getValue();
    
    /** Set the value (cannot be null) 
     *
     * @throws IllegalArgumentException if value is not valid for kind
     */
    public void setValue(String value);
    
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

    /** Get string value
     */
    public String getStringValue();

}
