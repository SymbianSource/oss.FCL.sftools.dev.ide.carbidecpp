/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.cpp.internal.api.utils.core;

import com.nokia.cpp.utils.core.noexport.Messages;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Simple utility to replace variables in the form "${varname}"
 * or "$(varname)" in a string from a map of replacements, with
 * support for fallbacks for unknown variables.  
 *
 */
public class VariableSubstitutionEngine {

	private IMessageListener msgListener;
	private MessageLocation location;
	private boolean recursive;
	private Pattern thePattern = curlyBraces;
	private static final Pattern curlyBraces = Pattern.compile("(\\$\\{([^}]+)\\})");
	private static final Pattern parentheses = Pattern.compile("(\\$\\(([^)]+)\\))");
	private boolean replaceOnce;
	
	/**
	 * Create an engine which will report any errors against
	 * the given location to the given listener.  The default 
	 * variable token is '{'.
	 * @param msgListener
	 * @param location
	 */
	public VariableSubstitutionEngine(IMessageListener msgListener, MessageLocation location) {
		this.msgListener = msgListener;
		this.location = location;
		setVariableToken('{');
	}
	
	/**
	 * Set the token delimiting variables (either '{' or '(')
	 * @param token
	 */
	public void setVariableToken(char token) {
		if (token == '{') {
			thePattern = curlyBraces;
		}
		else if (token == '(') {
			thePattern = parentheses;
		} else {
			Check.checkArg(false);
		}
	}

	/**
	 * Substitute all instances of "${varname}" or "$(varname)"
	 * with values using the callback.
	 * 
	 * @param variables
	 * @param expr
	 * @return
	 * @see #setVariableToken(char)
	 */
	public String substitute(IVariableLookupCallback variableLookupCallback, String expr) {
		if (expr == null)
			return null;
		Check.checkArg(variableLookupCallback);
		StringBuilder buffer;
		// variables we've replaced, ever
		Set<String> replaces = new HashSet<String>();
		boolean replaced;
		do {
			// variables we've replaced in this line
			Set<String> lineReplaces = new HashSet<String>();
			buffer = new StringBuilder();
			replaced = false;
			Matcher matcher = thePattern.matcher(expr);
			int startPosition = 0;
			int prevPosition = 0;
			while (startPosition < expr.length() && matcher.find(startPosition)) {
				String key = matcher.group(2);
				if (recursive && replaces.contains(key) && !lineReplaces.contains(key) && replaceOnce) {
					if (msgListener != null) {
						msgListener.emitMessage(new Message(IMessage.ERROR,
							location,
							"SimpleSubstitutionEngine.InfiniteRecursion", //$NON-NLS-1$
							MessageFormat.format(
									Messages.getString("SimpleSubstitutionEngine.InfiniteRecursion"), //$NON-NLS-1$
									new Object[] { key })));
					}
					startPosition = matcher.end();
					continue;
				}
				Object value = variableLookupCallback.getValue(key);
				replaces.add(key);
				lineReplaces.add(key);
				if (value == null) {
					if (msgListener != null)
						msgListener.emitMessage(new Message(IMessage.ERROR,
							location,
							"SimpleSubstitutionEngine.UnknownVariable", //$NON-NLS-1$
							MessageFormat.format(
									Messages.getString("SimpleSubstitutionEngine.UnknownVariable"), //$NON-NLS-1$
									new Object[] { key })));
					startPosition = matcher.end();
					buffer.append(expr.substring(prevPosition, matcher.end()));
					prevPosition = matcher.end();
				} else {
					String valueString = value.toString();
					if (!recursive && thePattern.matcher(valueString).find()) {
						if (msgListener != null)
							msgListener.emitMessage(new Message(IMessage.ERROR,
								location,
								"SimpleSubstitutionEngine.InvalidSubstitution", //$NON-NLS-1$
								MessageFormat.format(
										Messages.getString("SimpleSubstitutionEngine.InvalidSubstitution"), //$NON-NLS-1$
										new Object[] { key, value })));
						return expr;
					}
					
					buffer.append(expr.substring(prevPosition, matcher.start()));
					buffer.append(valueString);
					prevPosition = matcher.end();
					replaced = true;
					
					// don't replace anymore
					int lastCharacterMatched = matcher.end();
					startPosition = lastCharacterMatched;
				}
			}
			buffer.append(expr.substring(prevPosition));
			expr = buffer.toString();
		} while (recursive && replaced);
		return expr;
	}

	/**
	 * Substitute all instances of "${varname}" or "$(varname)" 
	 * with values from the map.
	 * 
	 * @param variables
	 * @param expr
	 * @return
	 * @see #setVariableToken(char)
	 */
	public String substitute(final Map<?, ?> variables, String expr) {
		return substitute(new IVariableLookupCallback() {
			public Object getValue(String var) {
				return variables.get(var);
			}
		}, expr);
	}

	
	/**
	 * Allow recursive substitution of variables with values that
	 * contain ${...}
	 * @param enable
	 */
	public void allowRecursion(boolean enable) {
		this.recursive = enable;
	}
	
	
	/**
	 * Allow line replace to be checked
	 * @param enable
	 */	
	public void replaceOnce(boolean enable) {
		this.replaceOnce = enable;
	}

}
