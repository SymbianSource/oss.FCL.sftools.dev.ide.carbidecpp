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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTLiteralTextNode extends ASTNode implements
		IASTLiteralTextNode {

	protected EStyle style;
	protected String value;
	
	// if style == PREPROCESSOR
	protected String trimmedValue;

	public ASTLiteralTextNode(EStyle style) {
		setStyle(style);
		setValue(""); //$NON-NLS-1$
		dirty = false;
	}
	
	public ASTLiteralTextNode(EStyle style, String value) {
		setStyle(style);
		setValue(value);
		dirty = false;
	}
	
	/**
	 * @param expression
	 */
	public ASTLiteralTextNode(ASTLiteralTextNode expression) {
		super(expression);
		this.style = expression.style;
		this.value = expression.value;
		this.trimmedValue = expression.trimmedValue;
		dirty = expression.dirty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTLiteralTextNode))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTLiteralTextNode node = (ASTLiteralTextNode) obj;
		return node.value.equals(value) && node.style.equals(style);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ value.hashCode() ^ style.hashCode() ^ 0x3948;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		Check.checkArg(value);
		if (!value.equals(this.value)) {
			String oldValue = this.value;
			this.value = value;
			// see if it's a string constant
			if (style == EStyle.PREPROCESSOR && value.length() >= 2 && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') { 
				this.trimmedValue = TextUtils.unescape(TextUtils.unquote(value, '"'));
			} else {
				this.trimmedValue = value;
			}
			fireLiteralTextChanged(oldValue, this.value);
			dirty = true;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression#getValue()
	 */
	public String getValue() {
		return trimmedValue;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression#setValue(int, int)
	 */
	public void setValue(int value, int radix) {
		String oldValue = this.value;
		if (radix == 16) {
			this.value = "0x" + Integer.toHexString(value); //$NON-NLS-1$
		} else if (radix == 8) {
 			this.value = "0" + Integer.toOctalString(value);  //$NON-NLS-1$
		} else if (radix == 2) {
			this.value = "0b" + Integer.toBinaryString(value); //$NON-NLS-1$
		} else {
			this.value = Integer.toString(value, radix);
		}
		this.trimmedValue = this.value;
		if (!this.value.equals(oldValue)) {
			fireLiteralTextChanged(oldValue, this.value);
			dirty = true;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode#setValue(int)
	 */
	public void setValue(int value) {
		setValue(value, 10);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return NO_CHILDREN;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTLiteralTextNode(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText(value);
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
        return ASTNode.parseIntValue(value);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getStringValue()
     */
    public String getStringValue() {
        return value;
    }
    
    protected void fireLiteralTextChanged(String oldT, String newT) {
		IASTTranslationUnit tu = getTranslationUnit();
		if (tu != null) {
			tu.fireLiteralTextNodeChanged(this, oldT, newT);
			tu.fireNodeChanged(this);
		}
	}

    /**
	 * @param style the style to set
	 */
	public void setStyle(EStyle style) {
		Check.checkArg(style);
		this.style = style;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode#getStyle()
	 */
	public EStyle getStyle() {
		return style;
	}
}
