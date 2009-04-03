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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstLiteralExpression extends AstExpression implements
        IAstLiteralExpression {

    protected int kind;
    protected String value;

    public AstLiteralExpression(int kind, String value) {
        super();
        setKind(kind);
        setValue(value);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return NO_CHILDREN;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        switch (kind) {
        case K_CHAR:
            return new Object[] { AstLiteralExpression.quoteForRss(value, '\'') }; //$NON-NLS-1$ //$NON-NLS-2$
        case K_STRING:
            return new Object[] { AstLiteralExpression.quoteForRss(value, '"') }; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return new Object[] { value };
    }

    /**
     * @param ch
     */
    static void escapeCharacter(StringBuffer buff, char ch) {
        buff.append("<0x"); //$NON-NLS-1$
        String hexVal = Integer.toHexString(ch);
        if (hexVal.length() < 4)
            hexVal = "0000".substring(hexVal.length())+hexVal; //$NON-NLS-1$
        buff.append(hexVal);
        buff.append('>');
        
    }

    /**
     * Escape a string 'val' whereever 'escapeMe' appears;
     * also escape non-ASCII characters into the RSS-style
     */
    static public String quoteForRss(String val, char quote) {
        StringBuffer buff = new StringBuffer();
        boolean startedQuotes = false;
        for (int i = 0; i < val.length(); i++) {
            char ch = val.charAt(i);
            String chVal = null;
            if (ch == quote) { 
                chVal = "\\" + ch; //$NON-NLS-1$
            }
            else if (ch == '\\') {
            	chVal = ("\\\\"); //$NON-NLS-1$
            }
            else if (ch < 32 || isLineOrParaSeparator(ch)) {
                switch (ch) {
                case '\t':
                    chVal = ("\\t"); //$NON-NLS-1$
                    break;
                case '\n':
                    chVal = ("\\n"); //$NON-NLS-1$
                    break;
                case '\r':
                    chVal = ("\\r"); //$NON-NLS-1$
                    break;
                case '\f':
                    chVal = ("\\f"); //$NON-NLS-1$
                    break;
                case '\b':
                    chVal = ("\\b"); //$NON-NLS-1$
                    break;
                default:
                    if (startedQuotes)
                        buff.append(quote);
                    escapeCharacter(buff, ch);
                    if (startedQuotes)
                        buff.append(quote);
                    break;
                }
            } else /*if (ch < 127)*/ {
                chVal = "" + ch; //$NON-NLS-1$
            } /* else {
                if (startedQuotes)
                    buff.append(quote);
                escapeCharacter(buff, ch);
                if (startedQuotes)
                    buff.append(quote);
            }*/
            if (chVal != null) {
                if (!startedQuotes) {
                    buff.append(quote);
                    startedQuotes = true;
                }
                buff.append(chVal);
            }
        }
        if (startedQuotes)
            buff.append(quote);
        if (buff.length() == 0) {
        	buff.append(quote);
        	buff.append(quote);
        }
        return buff.toString();
    }

    private static boolean isLineOrParaSeparator(char ch) {
		int type = Character.getType(ch);
		return ch > 127 && 
			(type == Character.PARAGRAPH_SEPARATOR || type == Character.LINE_SEPARATOR);
	}


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getKind()
     */
    public int getKind() {
        return kind;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#setKind(int)
     */
    public void setKind(int kind) {
        Check.checkArg(kind >= 0 && kind <= K_LAST);
        if (this.kind != kind) {
        	this.kind = kind;
        	dirty = true;
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getValue()
     */
    public String getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#setValue(java.lang.String)
     */
    public void setValue(String value) {
        Check.checkArg(value);
        
        // validate the argument 
        try {
            switch (kind) {
            case K_INTEGER:
                parseIntValue(value);
                break;
            case K_FLOAT:
                Double.valueOf(value);
                break;
            case K_CHAR:
                Check.checkArg(value.length() == 1);
                break;
            }
        } catch (NumberFormatException e) {
            Check.failedArg(e);
        }
        if (this.value == null || !this.value.equals(value)) {
        	dirty = true;
        }
        this.value = value;
    }

    /**
     */
    public static int parseIntValue(String val) {
        if (val.length() > 2 && val.startsWith("0x")) { //$NON-NLS-1$
            return Long.valueOf(val.substring(2), 16).intValue();
        } else if (val.length() > 1 && val.startsWith("0")) { //$NON-NLS-1$
            return Long.valueOf(val.substring(2), 8).intValue();
        } else
            return Long.valueOf(val).intValue();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getCharValue()
     */
    public char getCharValue() {
        return value.length() > 0 ? value.charAt(0) : 0;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getFloatValue()
     */
    public double getFloatValue() {
        return Double.valueOf(value).doubleValue();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getIntValue()
     */
    public int getIntValue() {
        return Integer.valueOf(value).intValue();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getStringValue()
     */
    public String getStringValue() {
        return value;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#simplify()
     */
    public IAstExpression simplify() {
        String newValue = null;
        switch (kind) {
        case K_INTEGER:
            int i = parseIntValue(value);
            newValue = i + ""; //$NON-NLS-1$
            break;
        case K_FLOAT:
            Double d = Double.valueOf(value);
            newValue = d.toString();
            break;
        }
        if (newValue != null && !newValue.equals(value)) {
            IAstLiteralExpression lit = new AstLiteralExpression(kind, newValue);
            lit.setParent(getParent());
            return lit;
        }
        else
            return this;
    }
    
     /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#equalValue(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public boolean equalValue(IAstExpression expr) {
        return (expr instanceof IAstLiteralExpression)
        && ((IAstLiteralExpression) expr).getKind() == getKind()
        && ((IAstLiteralExpression) expr).getValue().equals(getValue());
    }
}
