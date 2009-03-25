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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage a list of arguments, which quoted strings but not escapes
 * or parentheses.  This is what the makmake and bldmake tools do,  
 * Leading and trailing space is retained.
 *
 */
public class SimpleArgList {
	private List<String> argv;
	private String initial;
	private String terminal;

	public SimpleArgList(String text) {
		String trimmed = text.trim();
		this.initial = text.substring(0, text.indexOf(trimmed));
		this.terminal = text.substring(text.length() - this.initial.length() - trimmed.length());
		this.argv = splitArguments(trimmed);
		
	}
	
	public SimpleArgList(String initial, List<String> newArgs, String terminal) {
		Check.checkArg(initial);
		Check.checkArg(terminal);
		Check.checkArg(newArgs);
		this.initial = initial;
		this.terminal = terminal;
		this.argv = newArgs;
	}

	public SimpleArgList(String initial, IASTListNode<IASTLiteralTextNode> newArgs, String terminal) {
		Check.checkArg(initial);
		Check.checkArg(terminal);
		Check.checkArg(newArgs);
		this.initial = initial;
		this.terminal = terminal;
		this.argv = new ArrayList<String>();
		for (IASTLiteralTextNode node : newArgs)
			argv.add(node.getValue());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return initial + catenate(argv) + terminal;
	}
	
	private String catenate(List<String> argv) {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (String arg : argv) { 
			if (first)
				first = false;
			else
				buffer.append(' ');
			if (arg.matches(".*\\s.*")) { //$NON-NLS-1$
				buffer.append('"');
				buffer.append(arg);
				buffer.append('"');
			} else {
				buffer.append(arg);
			}
		}
		return buffer.toString();
	}

	/**
	 * Split a string into arguments.
	 */
	private List<String> splitArguments(String string) {
		List<String> args = new ArrayList<String>();
		StringBuilder current = new StringBuilder();
		int idx = 0;
		boolean quoted = false;
		while (idx < string.length()) {
			char ch = string.charAt(idx);
			if (ch == '"') {
				quoted = !quoted;
				current.append(ch);
				idx++;
			} else if (!quoted && Character.isWhitespace(ch)) {
				if (current.length() > 0) {
					// remove any quotes
					args.add(current.toString().replaceAll("\"", "")); //$NON-NLS-1$ //$NON-NLS-2$
					current.setLength(0);
					idx++;				}
				while (idx < string.length() && Character.isWhitespace(string.charAt(idx)))
					idx++;
			} else {
				current.append(ch);
				idx++;
			}
		}
		if (current.length() > 0)
			args.add(current.toString());
		return args;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.makefile.ICommand#getArgv()
	 */
	public List<String> getArgv() {
		return argv;
	}
	
}
