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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IMacroProvider;
import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.MacroTokenLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.Token;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class MacroExpandingTokenIterator implements Iterator<IToken> {
	private static final List<IToken> EMPTY_TOKENS = new ArrayList<IToken>();
	public static final IToken EMPTY_TOKEN = new Token(IToken.RAW, "", false, false); //$NON-NLS-1$
	static {
		EMPTY_TOKENS.add(EMPTY_TOKEN);
	}

	// all currently open macros
	Stack<IASTPreprocessorDefineStatement> macroStack;

	// all currently open macro locations
	Stack<IMacroTokenLocation> macroLocationStack;

	// all currently pushed stream pointers -- not including tokens
	Stack<ListIterator<IToken>> tokensStack;

	private IMacroProvider macroProvider;

	// current tokens iterator
	private ListIterator<IToken> tokensIter;

	private boolean frozen;

	private boolean expanding;
	
	private Set<IASTPreprocessorDefineStatement> expandedMacros;
	private List<IMacroTokenLocation> macroExpansionTokenLocations;

	public MacroExpandingTokenIterator(
			IMacroProvider macroProvider,
			List<IToken> tokens) {
		this.macroProvider = macroProvider;
		this.tokensStack = new Stack<ListIterator<IToken>>();
		this.macroStack = new Stack<IASTPreprocessorDefineStatement>();
		this.macroLocationStack = new Stack<IMacroTokenLocation>();
		this.tokensIter = tokens.listIterator();
		this.frozen = false;
		this.expanding = true;
		this.expandedMacros = new LinkedHashSet<IASTPreprocessorDefineStatement>();
		this.macroExpansionTokenLocations = new ArrayList<IMacroTokenLocation>();
	}
	
	
	/**
	 * Return the current position.
	 * @return
	 */
	public int mark() {
		Check.checkState(!frozen);
		frozen = true;
		return tokensIter.nextIndex();
	}
	
	/**
	 * Reset to an old position.
	 *
	 */
	public void release(int nextPosition) {
		Check.checkState(frozen && nextPosition <= tokensIter.nextIndex());
		this.frozen = false;
		while (tokensIter.nextIndex() != nextPosition)
			tokensIter.previous();
	}
	
	/**
	 * Control whether identifier tokens are expanded into macros.
	 * @param enabled
	 */
	public void enableMacroExpansion(boolean enabled) {
		this.expanding = enabled;
	}
	
	boolean macroExpansionTokenFollowsSpace = false;
	// used to ensure that a token after ## doesn't have a space
	private boolean nextTokenNoSpace = false;
	// used to ensure that a token after an empty macro expansion has a space
	private boolean nextTokenHasSpace = false;
	// last top-level token read when consuming a macro 
	private IToken lastToken;
	
	public boolean hasNext() {
		if (tokensIter != null && tokensIter.hasNext())
			return true;
		if (frozen)
			return false;
		for (Iterator<IToken> iter : tokensStack)
			if (iter.hasNext())
				return true;
		return false;
	}

	static class BadMacroSyntaxException extends Exception {

		public BadMacroSyntaxException(String string) {
			super(string);
		}
	}
	
	static interface IUnexpandableToken {}
	
	public IToken next() {
		while (true) {
			// no more tokens to examine?
			if (tokensIter == null)
				throw new NoSuchElementException();
	
			// done with current macro expansion?
			if (!tokensIter.hasNext()) {
				if (frozen)
					throw new NoSuchElementException();

				tokensIter = null;
				if (tokensStack.size() > 0) {
					/*IASTPreprocessorDefineStatement pop =*/ macroStack.pop();
					//System.out.println("1 popped " + pop.getMacroName().getValue());
					macroLocationStack.pop();
					tokensIter = tokensStack.pop();
				}
				continue;
			}
	
			// get another token
			IToken token = tokensIter.next();
				
			if (!frozen && expanding && token.getType() == IToken.IDENTIFIER
					&& !(token instanceof IUnexpandableToken)) {
				IASTPreprocessorDefineStatement define = lookupMacro(token.getText());
	
				if (define != null) {
					expandMacro(token, define);
					
					macroStack.push(define);
					//System.out.println("1 pushing " + define.getMacroName().getValue());
					continue;
				}
			}

			if (macroLocationStack.size() > 0) {
				// copy the token from the #define into its expanded context
				ITokenLocation location = macroLocationStack.peek();

				token = new Token(token.getType(), token.getText(),
						location, token.getOffset(), token.getLength(),
						nextTokenHasSpace || token.followsSpace(), 
						token.followsNewline());

				nextTokenHasSpace = isEmptyToken(token);
			}
			
			return token;
		}
	}

	/**
	 * @param token
	 * @return
	 */
	private boolean isEmptyToken(IToken token) {
		return token.getType() == IToken.RAW && token.getLength() == 0;
	}


	/**
	 * Expand a macro into the current stream.  'token' is the name of the
	 * macro.  Read the arguments from the current stream, and expand those
	 * arguments.  Then substitute the expanded arguments into the macro's body.
	 * Finally, push that stream of tokens as the expanded macro.
	 * @param token
	 * @param define
	 */
	private void expandMacro(IToken token,
			IASTPreprocessorDefineStatement define) {
		// if parentheses or arguments broken, recover before the macro name
		int recoveryIndex = tokensIter.nextIndex() - 1;
		try {
			//System.out.println("Expanding " + define.getMacroName().getValue());
			List<IToken> macroStream = null;
			
			macroStack.push(define);
			//System.out.println("2 pushed " + define.getMacroName().getValue());
			
			Pair<IToken, List<IToken>> info = expandMacroArgs(token, define);
			IToken endToken = info.first;
			macroStream = info.second;
			
			expandedMacros.add(define);
			
			//macroStack.push(define);
			//System.out.println("3 pushed " + define.getMacroName().getValue());
			//System.out.println("expanded macro to " + ASTFactory.createPreprocessorTokenStream(macroStream).getNewText());

			IMacroTokenLocation macroTokenLocation = createMacroTokenLocation(
					token, endToken, define);
			macroLocationStack.push(macroTokenLocation);
			tokensStack.push(tokensIter);
			tokensIter = macroStream.listIterator();
			macroExpansionTokenFollowsSpace |= token.followsSpace();
			nextTokenNoSpace = false;
			nextTokenHasSpace = false;
			macroExpansionTokenLocations.add(macroTokenLocation);
		} catch (BadMacroSyntaxException e) {
			// TODO: report error
			while (tokensIter.nextIndex() != recoveryIndex)
				tokensIter.previous();
		} finally {
			IASTPreprocessorDefineStatement pop = macroStack.pop();
			Check.checkState(define == pop);
			//System.out.println("3 popped " + define.getMacroName().getValue());
			//System.out.println("expanded " + define.getMacroName().getValue());
		}		
	}


	/**
	 * Read a macro's argument list, expand those arguments, and
	 * substitute those expansions into the body of the expanded macro stream.
	 * The token iterator should be after the macro name.
	 * @param define
	 * @return macro stream, or null if argument mismatch
	 */
	private Pair<IToken, List<IToken>> expandMacroArgs(IToken startToken, IASTPreprocessorDefineStatement define) throws BadMacroSyntaxException {
		if (define == null)
			return null;
		
		lastToken = startToken;
		
		Map<String, List<IToken>> argValues = new HashMap<String, List<IToken>>();
		
		if (define.getMacroArgs() != null) {
			readAndExpandMacroArguments(define, argValues);
		}
		
		// now go through the macro token stream and subsitute arguments
		List<IToken> expandedStream = new ArrayList<IToken>();
		
		//System.out.println("expanding stream");
		if (define.getMacroExpansion() != null) {
			
			boolean wasFrozen = frozen;
			frozen = true;
			//tokensStack.push(tokensIter);
			
			
			ListIterator<IToken> macroContentIterator = define.getMacroExpansion().getTokens().listIterator();
			
			IToken next = null;
			while (macroContentIterator.hasNext() || next != null) {
				IToken token = next != null ? next : macroContentIterator.next();
				next = null;
				if (token.getType() == IToken.IDENTIFIER
						&& argValues.containsKey(token.getText())) {
					//System.out.println("expanding argument " + token);
					List<IToken> argTokens = argValues.get(token.getText());
					for (IToken argToken : argTokens)
						addToken(expandedStream, argToken);
					continue;
				}
				if (token.getText().equals("#") && macroContentIterator.hasNext()) { //$NON-NLS-1$
					next = macroContentIterator.next();
					if (token.getText().equals("#")) { //$NON-NLS-1$
						// ignore these for now
						next = null;
						
						// but, let's actually do the behavior of removing spaces.
						nextTokenNoSpace = true;
						nextTokenHasSpace = false;
					} else {
						addToken(expandedStream, token);
					}	
				} else {
					addToken(expandedStream, token);
				}
			}

			//tokensIter = tokensStack.pop();
			
			frozen = wasFrozen;
		}

		return new Pair(lastToken, finalizeExpandedMacroStream(expandedStream, startToken.followsSpace()));
		
	}


	private void readAndExpandMacroArguments(
			IASTPreprocessorDefineStatement define,
			Map<String, List<IToken>> argValues) throws BadMacroSyntaxException {
		if (!matchToken("(")) //$NON-NLS-1$
			throw new BadMacroSyntaxException(Messages.getString("MacroExpandingTokenIterator.MissingOpenParenthesis")); //$NON-NLS-1$

		// shortcut for zero-args
		if (define.getMacroArgs().size() == 0) {
			if (!matchToken(")")) //$NON-NLS-1$
				throw new BadMacroSyntaxException(Messages.getString("MacroExpandingTokenIterator.MissingCloseParenthesis")); //$NON-NLS-1$
			return;
		}

		// read all the arguments
		
		for (IASTLiteralTextNode argName : define.getMacroArgs()) {
			List<IToken> argTokens = new ArrayList<IToken>();
			IToken token;
			int parenStack = 0;
			boolean varArgs = argName.equals("...") || argName.equals("__VA_ARGS__"); //$NON-NLS-1$ //$NON-NLS-2$
			while (tokensIter.hasNext()) {
				token = tokensIter.next();
				lastToken = token;
				if (token.getText().equals("(")) //$NON-NLS-1$
					parenStack++;
				else if (token.getText().equals(")")) { //$NON-NLS-1$
					if (parenStack == 0)
						break;
					parenStack--;
				} else if (parenStack == 0 && !varArgs && token.getText().equals(",")) //$NON-NLS-1$
					break;
				if (argTokens.size() == 0 && token.followsSpace()) {
					token = token.copy();
					token.setFollowsSpace(false);
				}
				argTokens.add(token);
			}
			if (parenStack != 0)
				throw new BadMacroSyntaxException(Messages.getString("MacroExpandingTokenIterator.UnbalancedParentheses")); //$NON-NLS-1$
			
			// expand the argument
			//System.out.println("expanding arg " + argName.getValue() + " with tokens " + ASTFactory.createPreprocessorTokenStream(argTokens).getNewText());
			MacroExpandingTokenIterator argIter = new MacroExpandingTokenIterator(macroProvider, argTokens);
			List<IToken> expandedArg = new ArrayList<IToken>();
			try {
				while (true) {
					IToken argToken = argIter.next();
					expandedArg.add(argToken);
				}
			} catch (NoSuchElementException e) {
			}
			//System.out.println("expanded arg " + argName.getValue() + " to " + ASTFactory.createPreprocessorTokenStream(expandedArg).getNewText());
			
			argValues.put(argName.getValue(), expandedArg);
		}
	}

	static class UnexpandableToken extends Token implements IUnexpandableToken {

		/**
		 * @param token
		 */
		public UnexpandableToken(IToken token) {
			super(token.getType(), token.getText(), token.getLocation(),
					token.getOffset(), token.getLength(), token.followsSpace(),
					token.followsNewline());
		}
		
	}

	private void addToken(List<IToken> expandedStream, IToken token) {
		if (token.getType() == IToken.IDENTIFIER && isExpandingMacro(token.getText())) {
			token = new UnexpandableToken(token.copy());
		}
		if ((nextTokenNoSpace && !isEmptyToken(token))) {
			if (token.followsSpace()) {
				token = token.copy();
				token.setFollowsSpace(false);
			}
			nextTokenNoSpace = false;
		}
		expandedStream.add(token);
	}

	private List<IToken> finalizeExpandedMacroStream(List<IToken> tokens, boolean followsSpace) {
		if (tokens.size() == 0) {
			// need to have SOMETHING to indicate a macro was here
			List<IToken> emptyMacroExpansion = new ArrayList<IToken>();
			IToken emptyToken = new Token(IToken.RAW, "", false, false); //$NON-NLS-1$
			emptyMacroExpansion.add(emptyToken);

			return emptyMacroExpansion;
		}
		if (tokens.get(0).followsSpace() != followsSpace && !isEmptyToken(tokens.get(0))) {
			IToken repl = tokens.get(0).copy();
			repl.setFollowsSpace(followsSpace);
			tokens.set(0, repl);
		}
		return tokens;
	}

	/**
	 * Match the next token and consume if matched.
	 * @param string
	 * @return true: matched and consumed; false: did not match and did not consume
	 */
	private boolean matchToken(String string) {
		if (!tokensIter.hasNext())
			return false;
		IToken token = tokensIter.next();
		lastToken = token;
		if (token.getText().equals(string))
			return true;
		tokensIter.previous();
		return false;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Tell whether the last read token is from a macro.
	 * 
	 * @return
	 */
	public boolean inMacro() {
		return macroStack.size() > 0;
	}

	/**
	 * Create a token location which refers to the position in a #define's token stream where an
	 * expanded token came from.
	 */
	private IMacroTokenLocation createMacroTokenLocation(
			IToken token, IToken endToken, 
			IASTPreprocessorDefineStatement define) {
		int parentOffset = macroLocationStack.size() > 0 ? (macroLocationStack
				.peek() != null ? macroLocationStack.peek().getParentOffset()
				: 0)
				: token.getOffset();
		ITokenLocation parentLocation = macroLocationStack.size() > 0 ? macroLocationStack
				.peek()
				: token.getLocation();
		// if this fails, need to implement more stuff
		Check.checkState(endToken.getLocation() == token.getLocation());
		IMacroTokenLocation macroTokenLocation = new MacroTokenLocation(
				define, parentOffset, endToken.getEndOffset() - token.getOffset(), 
				parentLocation);
		return macroTokenLocation;
	}
	
	/**
	 * See if the given identifier references a macro which is being expanded.
	 * @param name
	 * @return
	 */
	private boolean isExpandingMacro(String name) {
		IASTPreprocessorDefineStatement define = macroProvider.lookupMacro(name);
		if (define == null)
			return false;

		// don't re-expand macros
		if (macroStack.contains(define)) {
			return true;
		}

		return false;
	}
	/**
	 * See if the given identifier references a macro which can be expanded.
	 * We do not re-expand currently in-expansion macros.
	 * @param name
	 * @return
	 */
	private IASTPreprocessorDefineStatement lookupMacro(String name) {
		IASTPreprocessorDefineStatement define = macroProvider.lookupMacro(name);
		if (define == null)
			return null;

		// don't re-expand macros
		if (macroStack.contains(define)) {
			//System.out.println("Not re-expanding " + define.getMacroName().getValue() );
			return null;
		}

		return define;
	}

	/**
	 * Provide the set of expanded macros.
	 * @return set of macros that were expanded
	 */
	public Set<IASTPreprocessorDefineStatement> hitMacros() {
		return expandedMacros;
	}

	/**
	 * Get the locations where macros were expanded.
	 * @return
	 */
	public Collection<IMacroTokenLocation> getMacroExpansionTokenLocations() {
		return macroExpansionTokenLocations;
	}



}