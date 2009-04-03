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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeRewriter implements IRewriteHandler {
	private static final Pattern NEWLINE_OR_LINE_PATTERN = Pattern.compile("(.+)|(\r\n|\r|\n)"); //$NON-NLS-1$

	protected StringBuilder buffer = new StringBuilder();
	private String newLine = null;
	private static Pattern NEWLINE_PATTERN = Pattern.compile("(\r\n|\r|\n)"); //$NON-NLS-1$
	
	private StringBuilder indent;
	protected boolean needIndent;
	
	public NodeRewriter() {
		super();
		indent = new StringBuilder();
		needIndent = true;
	}

	protected void setEOL(String eol) {
		newLine = eol;
	}
	
	public String toString() {
		return buffer.toString();
	}
	
	/**
	 * Emit text generated either from scratch or from existing clean text.
	 * This tracks the indentation state so that new text can be properly
	 * indented.
	 * @param text text
	 * @param reinterpretNewlines if true, then this is new text, and replace
	 * any newlines with the current proper newline sequence; else, emit
	 * newlines as they exist
	 */
	protected void emitText(String text, boolean reinterpretNewlines) {
		if (newLine == null) {
			Matcher matcher = NEWLINE_PATTERN.matcher(text);
			if (matcher.matches()) {
				newLine = matcher.group(1);
			}
		}
		
		if (needIndent) {
			buffer.append(indent);
			needIndent = false;
		}

		Matcher matcher = NEWLINE_OR_LINE_PATTERN.matcher(text);
		while (matcher.find()) {
			String eol = matcher.group(2);
			if (eol != null) {
				if (reinterpretNewlines) {
					ensureNewline();
					buffer.append(newLine);
				} else {
					buffer.append(eol);
				}
				needIndent = true;
			} else {
				buffer.append(matcher.group(1));
			}
		}
	}

	public void emitText(String text) {
		emitText(text, true);
	}
	
	public void emitSpace() {
		buffer.append(' ');
	}

	private void ensureNewline() {
		if (newLine == null) {
			newLine = "\n"; //$NON-NLS-1$
		}
	}
	public void emitNewline() {
		if (buffer.length() > 0 && buffer.charAt(buffer.length() - 1) == '\\')
			buffer.append(' ');
		ensureNewline();
		buffer.append(newLine);
		needIndent = true;
	}
	
	protected void emitNewlineIfMissing() {
		int pos = buffer.length();
		while (--pos >= 0) {
			char ch = buffer.charAt(buffer.length() - 1);
			if (ch == '\r' || ch == '\n') {
				break;
			} else if (Character.isWhitespace(ch)) {
				continue;
			} else {
				ensureNewline();
				buffer.append(newLine);
				needIndent = true;
				break;
			}
		}
	}

	public void emitWrappingHint() {
	}

	public void emitNode(IASTNode node) {
		node.rewrite(this);
	}

	public void emitSpaceIfMissing() {
		int pos = buffer.length();
		while (--pos >= 0) {
			char ch = buffer.charAt(buffer.length() - 1);
			if (ch == ' ' || ch == '\t') {
				break;
			} else {
				buffer.append(' ');
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler#indent(int)
	 */
	public void indent(int amount) {
		if (amount < 0) {
			indent.setLength(Math.max(0, indent.length() + amount));
		} else {
			while (amount > 0) {
				indent.append('\t');
				amount--;
			}
		}
	}
}