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

package com.nokia.carbide.cpp.epoc.engine.model.makefile;

import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manage a list of arguments, which honors parenthesized groups and
 * quoted strings.  Leading and trailing space is retained.
 *
 */
public class ArgList {
	private static final Pattern CONTINUATION = Pattern.compile("(.*)(\\s+\\\\(\r\n|\r|\n)\\s*)"); //$NON-NLS-1$
	private List<String> argv;
	private String initial;
	private String terminal;

	public ArgList(String text) {
		String trimmed = text.trim();
		this.initial = text.substring(0, text.indexOf(trimmed));
		this.terminal = text.substring(text.length() - this.initial.length() - trimmed.length());
		this.argv = splitArguments(trimmed);
		
	}
	
	public ArgList(String initial, List<String> newArgs, String terminal) {
		Check.checkArg(initial);
		Check.checkArg(terminal);
		Check.checkArg(newArgs);
		this.initial = initial;
		this.terminal = terminal;
		this.argv = newArgs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString(" "); //$NON-NLS-1$
	}
	
	private String catenate(List<String> argv, String separator) {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (String arg : argv) { 
			if (first)
				first = false;
			else
				buffer.append(separator);
			
			// detect spaces in command line arguments, but also handle
			// the fact that continuation lines are considered arguments too
			Matcher matcher = CONTINUATION.matcher(arg);
			if (matcher.matches()) {
				arg = matcher.group(1);
				if (arg.matches(".*\\s.*")) { //$NON-NLS-1$
					buffer.append('"');
					buffer.append(arg);
					buffer.append('"');
				} else {
					buffer.append(arg);
				}
				buffer.append(matcher.group(2));
			} else {
				if (arg.matches(".*\\s.*")) { //$NON-NLS-1$
					buffer.append('"');
					buffer.append(arg);
					buffer.append('"');
				} else {
					buffer.append(arg);
				}
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
		int parenStack = 0;
		boolean quoted = false;
		while (idx < string.length()) {
			char ch = string.charAt(idx);
			if (!quoted && ch == '(') {
				parenStack++;
				current.append(ch);
				idx++;
			} else if (!quoted && ch == ')' && parenStack > 0) {
				parenStack--;
				current.append(ch);
				idx++;
			} else if (ch == '"') {
				quoted = !quoted;
				current.append(ch);
				idx++;
			} else if (ch == '\\') {
				current.append(ch);
				idx++;
				if (idx < string.length()) {
					current.append(string.charAt(idx++));
				}
			} else if (!quoted && parenStack == 0 && Character.isWhitespace(ch)) {
				if (current.length() > 0) {
					// remove any quotes
					args.add(current.toString().replaceAll("\"", "")); //$NON-NLS-1$ //$NON-NLS-2$
					current.setLength(0);
					idx++;				
				}
				while (idx < string.length() && Character.isWhitespace(string.charAt(idx)))
					idx++;
			} else {
				current.append(ch);
				idx++;
			}
		}
		if (current.length() > 0) {
			// remove any quotes
			args.add(current.toString().replaceAll("\"", "")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return args;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.makefile.ICommand#getArgv()
	 */
	public List<String> getArgv() {
		return argv;
	}

	/**
	 * @param string
	 * @return
	 */
	public String toString(String separator) {
		return initial + catenate(argv, separator) + terminal;
	}
	
}
