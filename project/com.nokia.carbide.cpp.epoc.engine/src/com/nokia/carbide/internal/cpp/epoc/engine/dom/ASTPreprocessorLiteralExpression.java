/*
* Copyright (c) 2005-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorLiteralExpression;


public class ASTPreprocessorLiteralExpression extends ASTLiteralTextNode
        implements IASTPreprocessorLiteralExpression {

	private final static String FALSE = "false"; //$NON-NLS-1$ 
	private final static String TRUE = "true"; //$NON-NLS-1$
	
	/**
     * @param kind
     * @param value
     */
    public ASTPreprocessorLiteralExpression(String value) {
    	super(EStyle.PREPROCESSOR, value);
    	dirty = false;
    }

    /**
	 * @param expression
	 */
	public ASTPreprocessorLiteralExpression(ASTPreprocessorLiteralExpression expression) {
		super(expression);
		setValue(expression.getValue());
		dirty = expression.dirty;
	}

	/* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.internal.ASTLiteralTextExpression#copy()
     */
    public IASTNode copy() {
    	return new ASTPreprocessorLiteralExpression(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorLiteralExpression#getBooleanValue()
     */
    public boolean getBooleanValue() {
    	if (value.equals(TRUE))
    		return true;
    	if (value.equals(FALSE))
    		return false;

    	// else get integer and compare against 0
    	int val = 0;
        try {
        	val = parseIntValue(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(value);
        }

        return val != 0;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorLiteralExpression#setValue(boolean)
     */
    public void setValue(boolean value) {
    	setValue(value ? TRUE : FALSE);
    }
    
}
