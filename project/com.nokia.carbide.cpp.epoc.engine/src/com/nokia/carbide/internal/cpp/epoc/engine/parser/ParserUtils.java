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

package com.nokia.carbide.internal.cpp.epoc.engine.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentSourceLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.MultiDocumentSourceLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.Token;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import java.util.List;

/**
 * Utilities for setting up token spans.
 *
 */
public class ParserUtils {
 
    /**
	 * Try to get a IDocumentTokenLocation out of the location.
	 * @param token
	 * @return triple of document location, offset, and length
	 */
	public static Triple<IDocumentTokenLocation,Integer, Integer> resolveDocumentLocation(IToken token) {
		int offset = token.getOffset();
		int length = token.getLength();
		ITokenLocation location = token.getLocation();
		while (location != null) {
			
			if (location instanceof IDocumentTokenLocation)
				return new Triple<IDocumentTokenLocation, Integer, Integer>
					((IDocumentTokenLocation) location, offset, length);
			offset = location.getParentOffset();
			length = location.getParentLength();
			location = location.getParentLocation();
		}
		return new Triple<IDocumentTokenLocation, Integer, Integer>(null, offset, length);
	}

	public static void setSourceRangeFromTokenSpan(IASTNode node, Token start, Token end) {
    	if (start == null && end == null)
    		return;
    	
    	ASTToken astStart = (ASTToken) start;
    	ASTToken astEnd = (ASTToken) end;
    	
    	Triple<IDocumentTokenLocation, Integer, Integer> startInfo = resolveDocumentLocation(astStart.iToken);
    	Triple<IDocumentTokenLocation, Integer, Integer> endInfo = resolveDocumentLocation(astEnd.iToken);
    	
    	setSourceLocation(node, startInfo, endInfo);
    }
	
	private static void setSourceLocation(IASTNode node, Triple<IDocumentTokenLocation, Integer, Integer> startInfo, Triple<IDocumentTokenLocation, Integer, Integer> endInfo) {
    	IDocumentTokenLocation startLocation = startInfo.first;
    	IDocumentTokenLocation endLocation = endInfo.first;
    	if (startLocation != null && startLocation != endLocation) {
    		setDocumentSpanningLocation(node, startInfo, endInfo);
     	} else if (startLocation == null || endLocation == null) {
    		startLocation = endLocation = null;
    		node.setSourceRegion(null);
    		return;
    	}

    	node.setSourceRegion(ASTUtils.createDocumentSourceRegion( 
    			startLocation.getDocument(),
    			startLocation.getPath(),
    			new Region(startInfo.second,
    					endInfo.second + endInfo.third - startInfo.second)));
		
	}

	private static IDocumentSourceRegion createDocumentSourceRegion(Triple<IDocumentTokenLocation, Integer, Integer> startInfo) {
    	IDocumentTokenLocation startLocation = startInfo.first;
    	return ASTUtils.createDocumentSourceRegion(
    			startLocation.getDocument(), startLocation.getPath(), 
    			new Region(startInfo.second, startInfo.third));
		
	}

	private static void setDocumentSourceLocation(IASTNode node, Triple<IDocumentTokenLocation, Integer, Integer> startInfo) {
    	IDocumentTokenLocation startLocation = startInfo.first;
    	node.setSourceRegion(ASTUtils.createDocumentSourceRegion(
    			startLocation.getDocument(), startLocation.getPath(), 
    			new Region(startInfo.second, startInfo.third)));
		
	}

	private static void setDocumentSpanningLocation(IASTNode node, Triple<IDocumentTokenLocation,Integer,Integer> startInfo, Triple<IDocumentTokenLocation,Integer,Integer> endInfo) {
		int endOffset = startInfo.first.getDocument().getLength();
		
		IDocumentSourceRegion startRegion = new DocumentSourceLocation(
				startInfo.first.getDocument(), startInfo.first.getPath(),
				new Region(startInfo.second, endOffset - startInfo.second));
		
		IDocumentSourceRegion endRegion = new DocumentSourceLocation(
				endInfo.first.getDocument(), endInfo.first.getPath(),
				new Region(0, endInfo.third));
		
		IMultiDocumentSourceRegion bothRegions = new MultiDocumentSourceLocation();
		bothRegions.addSourceRegion(startRegion);
		bothRegions.addSourceRegion(endRegion);

		node.setSourceRegion(bothRegions.getCanonicalSourceRegion());
				
	}

	public static void setSourceRangeFromTokenSpanUpTo(IASTNode node, Token start, Token end) {
    	if (start == null && end == null)
    		return;
    	
    	try {
	    	ASTToken astStart = (ASTToken) start;
	    	ASTToken astEnd = (ASTToken) end;
	    	
	    	Triple<IDocumentTokenLocation, Integer, Integer> startInfo = resolveDocumentLocation(astStart.iToken);
	    	Triple<IDocumentTokenLocation, Integer, Integer> endInfo = resolveDocumentLocation(astEnd.iToken);
	    	if (endInfo.first != null) {
	    		endInfo.third = 0;
	    	}
	    	
	    	setSourceLocation(node, startInfo, endInfo);
    	} catch (ClassCastException e) {
    		// oops, this must be EOF
    		
    	}
    }

    /**
     * Set a list node's source range to be the union of its contents,
     * or if empty, the exclusive space between start and end. 
     * @param start
     * @param node
     * @param end
     */
    public static void setSourceRangeForListNode(Token start, IASTListNode<? extends IASTNode> node, Token end) {
    	if (node.size() > 0) {
    		ISourceRegion region = ASTUtils.getEnclosingRegion((IASTNode[]) node.toArray(new IASTNode[node.size()]));
    		node.setSourceRegion(region);
    		return;
    	}
    	
    	if (start == null && end == null)
    		return;
    	if (start == null || end == null) {
	    	if (start != null)
	    		end = start;
	    	else
	    		start = end;
    	}
    	
    	setSourceRangeExclusive(node, ((ASTToken)start).iToken, ((ASTToken)end).iToken);
    }
    
    
    /**
     * Make sure the children of a list consume all the space between 
     * the elements, aligned to lines.
     * @param start
     * @param node
     * @param end
     */
    public static void setAdjacentSourceRangesForListElements(IASTListNode<? extends IASTNode> node) {
    	if (node.size() == 0)
    		return;

    	IDocumentSourceRegion headRegion = node.getSourceRegion().getExclusiveHeadRegion();
    	
    	for (IASTNode child : node) {
    		//int endOffset = child.getRegion().getOffset() + child.getRegion().getLength();
    		//child.setRegion(new Region(offset, endOffset - offset));
    		//offset = endOffset;
    		child.setSourceRegion(ASTUtils.getEnclosingRegion(headRegion, child.getSourceRegion()));
    		headRegion = child.getSourceRegion().getExclusiveTailRegion();
    	}
    }
    
    /**
     * Set the source range to include start and end.
     * @param node
     * @param start
     * @param end
     */
    public static void setSourceRange(IASTNode node, IToken start, IToken end) {
    	Triple<IDocumentTokenLocation, Integer, Integer> startInfo = resolveDocumentLocation(start);
    	Triple<IDocumentTokenLocation, Integer, Integer> endInfo = resolveDocumentLocation(end);
    	
    	setSourceLocation(node, startInfo, endInfo);
    }
    
    /**
     * Set the source range to lie between start and end, not including them.
     * @param node
     * @param start
     * @param end
     */
    public static void setSourceRangeExclusive(IASTNode node, IToken start, IToken end) {
    	Triple<IDocumentTokenLocation, Integer, Integer> startInfo = resolveDocumentLocation(start);
    	Triple<IDocumentTokenLocation, Integer, Integer> endInfo = resolveDocumentLocation(end);
    	
    	if (startInfo.first != null) {
    		startInfo.second += startInfo.third;
    		startInfo.third = 0;
    	}
    	if (endInfo.first != null) {
    		endInfo.third = 0;
    	}
    	setSourceLocation(node, startInfo, endInfo);
    }

	/**
	 * Copy the source range from the node.
	 * @param node
	 * @param start
	 */
	public static void copySourceInfo(IASTNode node, IASTNode start) {
		if (start == null)
			return;
		
		node.copySourceInfo(start);
	}
	
	/**
	 * Set the source range from the start and end nodes.
	 * @param node
	 * @param start
	 * @param end
	 */
	public static void setSourceRangeFromNodes(IASTNode node, IASTNode start, IASTNode end) {
		if (start == null || end == null)
			return;
		
		if (start == end) {
			copySourceInfo(node, start);
			return;
		}
		
		node.setSourceRegion(ASTUtils.getEnclosingRegion(start.getSourceRegion(), end.getSourceRegion()));
	}


	/**
	 * Set the source range as empty after the given node.
	 * @param node
	 * @param start
	 * @param end
	 */
	public static void setSourceRangeAfterNode(IASTNode node, IASTNode end) {
		if (end == null)
			return;
		
		if (end.getSourceRegion() == null)
			return;
		
		node.setSourceRegion(end.getSourceRegion().getExclusiveTailRegion());
	}

	public static void setSourceRangeForTokenStream(IASTNode node, IASTPreprocessorTokenStream stream) {
		List<IToken> tokens = stream.getTokens();
		if (tokens.size() == 0)
			return;
		IToken start = tokens.get(0);
		IToken end = tokens.get(tokens.size() - 1);
		setSourceRange(node, start, end);
	}

	/**
	 * Extend the source region to include another node following the current node.
	 * @param node
	 * @param tail
	 */
	public static void extendSourceRangeToInclude(IASTNode node, IASTNode tail) {
		if (tail.getSourceRegion() == null)
			return;
		node.setSourceRegion(ASTUtils.getSerialRegion(new ISourceRegion[] { node.getSourceRegion(), tail.getSourceRegion() }));
	}
	
	public static void extendSourceRangeToInclude(IASTNode node, Token token) {
		IToken itoken = ((ASTToken) token).iToken;
    	Triple<IDocumentTokenLocation, Integer, Integer> startInfo = resolveDocumentLocation(itoken);
    	IDocumentSourceRegion tokenRegion = createDocumentSourceRegion(startInfo);
    	IDocumentSourceRegion headRegion = node.getSourceRegion().getInclusiveHeadRegion();
    	IDocumentSourceRegion tailRegion = node.getSourceRegion().getInclusiveTailRegion();
		if (tokenRegion.getDocument() == headRegion.getDocument() 
				&& tokenRegion.compareTo(headRegion) < 0) {
    		// prepend token
    		headRegion.setRegion(new Region(
    				tokenRegion.getRegion().getOffset(),
    				headRegion.getRegion().getOffset() + headRegion.getRegion().getLength()
    				- tokenRegion.getRegion().getOffset()));
		} else if (tokenRegion.getDocument() == tailRegion.getDocument()
				&& tokenRegion.compareTo(tailRegion) >= 0) {
    		// append token
    		tailRegion.setRegion(new Region(
    				tailRegion.getRegion().getOffset(),
    				tokenRegion.getRegion().getOffset() + tokenRegion.getRegion().getLength()
    				- tailRegion.getRegion().getOffset()));
    	} else {
    		// append
    		node.setSourceRegion(ASTUtils.getSerialRegion(new ISourceRegion[] { node.getSourceRegion(), tokenRegion }));
    	}
	}

	/**
	 * @param node
	 * @param end
	 */
	public static void setSourceRangeFromTokenAfter(IASTNode node, Token end) {
		IToken itoken = ((ASTToken) end).iToken;
    	Triple<IDocumentTokenLocation, Integer, Integer> startInfo = resolveDocumentLocation(itoken);
    	startInfo.second += startInfo.third;
    	startInfo.third = 0;
    	setDocumentSourceLocation(node, startInfo);
	}

	/**
	 * @param node
	 */
	public static void expandSourceRangeToLine(IASTNode node) {
		IDocumentSourceRegion endRegion = node.getSourceRegion().getInclusiveTailRegion();
    	IRegion region = endRegion.getRegion();
    	IDocument document = endRegion.getDocument();
    	try {
    		IRegion startLine = document.getLineInformationOfOffset(region.getOffset());
    		IRegion endLine;
    		if (region.getLength() > 0)
    			endLine = document.getLineInformationOfOffset(region.getOffset() + region.getLength() - 1);
    		else
    			endLine = startLine;
    		String delim = document.getLineDelimiter(document.getLineOfOffset(endLine.getOffset()));
    		IRegion lineRegion = new Region(startLine.getOffset(), 
    				endLine.getOffset() + endLine.getLength() - startLine.getOffset()
    				+ (delim != null ? delim.length() : 0));
    		endRegion.setRegion(lineRegion);
    	} catch (BadLocationException e) {
    		throw new IllegalStateException(e);
    	}		
	}

	public static MessageLocation getMessageLocationFromToken(Token start) {
    	if (start == null)
    		return null;
    	
    	ASTToken astStart = (ASTToken) start;
    	
    	Triple<IDocumentTokenLocation, Integer, Integer> startInfo = resolveDocumentLocation(astStart.iToken);
    	
    	IDocumentTokenLocation startLocation = startInfo.first;
    	if (startLocation == null) 
    		return null;

    	IDocument document = startLocation.getDocument();
    	int line;
		try {
			line = document.getLineOfOffset(startInfo.second) + 1;
		} catch (BadLocationException e) {
			line = 0;
		}
    	return new MessageLocation(startLocation.getPath(), line, 0);
    }

	public static void propagateRangesUpward(IASTNode node) {
		ISourceRegion region = node.getSourceRegion();
		IASTNode[] kids = node.getChildren();
		if (kids.length > 0) {
			ISourceRegion[] theRegions = new ISourceRegion[kids.length + 1];
			int idx = 0;
			if (region != null) {
				theRegions[idx++] = region;
			}
			for (IASTNode kid : kids) {
				propagateRangesUpward(kid);
				ISourceRegion kidRegion = kid.getSourceRegion();
				if (kidRegion != null) {
					theRegions[idx++] = kidRegion;
				}
			}
			if (idx < theRegions.length) {
				ISourceRegion[] subRegions = new ISourceRegion[idx];
				System.arraycopy(theRegions, 0, subRegions, 0, idx);
				theRegions = subRegions;
			}
			node.setSourceRegion(ASTUtils.getSerialRegion(theRegions));
		}
	}

	/**
	 * Take a range of ITokens and return a combined token, or self
	 * @param firstToken first token considered part of the token
	 * @param lastToken last (inclusive) token considered part of the token
	 * @param text gathered text, which may differ from token content
	 * @return firstToken or new IToken with source range from firstToken...lastToken
	 */
	public static IToken getCombinedToken(IToken firstToken, IToken lastToken,
			String text) {
		Check.checkArg(firstToken != null && lastToken != null);
		
		ITokenLocation firstLocation = firstToken.getLocation();
		ITokenLocation lastLocation = lastToken.getLocation();
		int firstOffset = firstToken.getOffset();
		int lastEndOffset = lastToken.getEndOffset();
		if (firstLocation != lastLocation) {
			while (firstLocation instanceof IMacroTokenLocation) {
				firstOffset = firstLocation.getParentOffset();
				firstLocation = firstLocation.getParentLocation();
			}
			while (lastLocation instanceof IMacroTokenLocation) {
				lastEndOffset = lastLocation.getParentOffset() + lastLocation.getParentLength();
				lastLocation = lastLocation.getParentLocation();
			}
			Check.checkArg(firstLocation == lastLocation);
		}
	    
	    IToken combined;
	    if (firstToken != lastToken)
	    	combined = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(firstToken.getType(),
	    		text,
	    		firstLocation, firstOffset, lastEndOffset - firstOffset,
	    		firstToken.followsSpace(), firstToken.followsNewline());
	    else
	    	combined = firstToken;
		return combined;
	}

	public static ISourceRegion getFlattenedTokenLocation(IToken token, ITokenLocation location) {
		int offset = token != null ? token.getOffset() : 0;
		int length = token != null ? token.getLength() : 0;
		while (location != null) {
			if (location instanceof IDocumentTokenLocation)
				return ASTUtils.createDocumentSourceRegion(
						((IDocumentTokenLocation) location).getDocument(),
						((IDocumentTokenLocation) location).getPath(), 
		    			new Region(offset, length));
			offset = location.getParentOffset();
			length = location.getParentLength();
			location = location.getParentLocation();
		}
		return null;
	}

	public static String getLiteralTextSpanning(Token start, Token end) {
    	if (start == null && end == null)
    		return null;
    	
    	boolean first = true;
    	ASTToken astStart = (ASTToken) start;
    	ASTToken astEnd = (ASTToken) end;
    	StringBuilder builder = new StringBuilder();
    	while (true) {
    		if (first)
    			first = false;
    		else if (astStart.followsSpace)
    			builder.append(' ');
    		builder.append(astStart.getTokenText());
    		if (astStart != astEnd)
    			astStart = (ASTToken) astStart.next;
    		else
    			break;
    	}
    	
    	String text = builder.toString();
    	//text = text.replaceAll("\"", ""); //$NON-NLS-1$ //$NON-NLS-2$
    	return text;
    }
    
	public static String getLiteralTextSpanningUpTo(Token start, Token end) {
    	if (start == null && end == null)
    		return null;
    	StringBuilder builder = new StringBuilder();
    	boolean first = true;
    	while (start != end) {
    		ASTToken astStart = (ASTToken) start;
    		if (first)
    			first = false;
    		else if (astStart.followsSpace)
    			builder.append(' ');
    		builder.append(astStart.getTokenText());
    		start = start.next;
    	}
    	String text = builder.toString();
    	text = text.replaceAll("\"", ""); //$NON-NLS-1$ //$NON-NLS-2$
    	return text;
		
    }
}
