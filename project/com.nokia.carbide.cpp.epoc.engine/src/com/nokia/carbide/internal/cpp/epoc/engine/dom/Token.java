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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.List;


public class Token implements IToken {

	private int type;
	private String text;
	private int offset;
	private int length;
	private boolean followsSpace;
	private boolean followsNewline;
	private ITokenLocation location;

	public Token(int type, String text, ITokenLocation location,
			int offset, int length, boolean followsSpace, boolean followsNewline) {
		Check.checkArg(type >= 0 && type < IToken.LAST);
		this.type = type;
		this.text = text;
		this.location = location;
		this.offset = offset;
		this.length = length;
		this.followsSpace = followsSpace;
		this.followsNewline = followsNewline;
	}
	
	public Token(int type, String text, boolean followsSpace, boolean followsNewline) {
		this(type, text, null, 0, 0, followsSpace, followsNewline);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IToken))
			return false;
		IToken token = (IToken) obj;
		return token.getType() == type
			&& token.getText().equals(text)
			&& token.getOffset() == offset
			&& token.followsSpace() == followsSpace
			&& token.followsNewline() == followsNewline;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (type << 24) ^ (text.hashCode() << 4)
			^ (offset << 16) ^ (followsSpace ? 256 : 0) ^ 
			(followsNewline ? 128 : 0);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (type == EOF)
			return "<EOF>"; //$NON-NLS-1$
		else if (type == EOL)
			return "<EOL>"; //$NON-NLS-1$
		else
			return text;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#getType()
	 */
	public int getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#getText()
	 */
	public String getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#getContent()
	 */
	public String getContent() {
		if (type != STRING)
			return text;
		else
			return TextUtils.unescape(TextUtils.unquote(text, '"'));
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#followsSpace()
	 */
	public boolean followsSpace() {
		return followsSpace;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#setFollowsSpace(boolean)
	 */
	public void setFollowsSpace(boolean followsSpace) {
		this.followsSpace = followsSpace;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#followsNewline()
	 */
	public boolean followsNewline() {
		return followsNewline;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#getLocation()
	 */
	public ITokenLocation getLocation() {
		return location;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#getOffset()
	 */
	public int getOffset() {
		return offset;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#getLength()
	 */
	public int getLength() {
		return length;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#getEndOffset()
	 */
	public int getEndOffset() {
		return offset + length;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken#setLocationAndOffset(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation, int)
	 */
	public void setLocationAndOffset(ITokenLocation location, int offset) {
		this.location = location;
		this.offset = offset;
	}

	/**
	 * Get catenated text of the tokens
	 * @param tokens
	 * @return
	 */
	public static String getTokenText(IToken[] tokens) {
		if (tokens.length == 1)
			return tokens[0].getText();
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (IToken token : tokens) {
			if (first)
				first = false;
			else if (token.followsSpace())
				builder.append(' ');
			builder.append(token.getText());
		}
		return builder.toString();
	}

	/**
	 * Get catenated text of the tokens
	 * @param tokens
	 * @return
	 */
	public static String getTokenText(List<IToken> tokens) {
		if (tokens.size() == 1)
			return tokens.get(0).getText();
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (IToken token : tokens) {
			if (first)
				first = false;
			else if (token.followsSpace())
				builder.append(' ');
			builder.append(token.getText());
		}
		return builder.toString();
	}

	public IToken copy() {
		return new Token(type, text, location, offset, length, followsSpace, followsNewline);
	}
}
