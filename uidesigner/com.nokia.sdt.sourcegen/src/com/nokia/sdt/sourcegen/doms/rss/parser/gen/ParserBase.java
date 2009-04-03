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

package com.nokia.sdt.sourcegen.doms.rss.parser.gen;

import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTextNode;
import com.nokia.sdt.sourcegen.doms.rss.parser.RssParser;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * 
 *
 */
public class ParserBase {
    SourceGenContext context;
    RssParser parser;
    //ITranslationUnit tu;
    
    public static MessageLocation makeSourceRef(Token tok) {
        if (tok instanceof RssToken) {
        	IPath fullPath = new Path(((RssToken)tok).file.getFile().getAbsolutePath());
            return new MessageLocation(fullPath, tok.beginLine, tok.beginColumn);
        } else
            return null;
    }

    // TODO:  we need to figure out how to handle macro expressions

    /**
     * Set the token range for the node.
     */
    public static void setSourceRange(IAstNode node, Token start, Token end) {
        RssToken rstart = (RssToken)start;
        RssToken rend = (RssToken)end;
        ISourceRange range = new SourceRange(rstart.file, rstart.offset, rend.file,
                                            rend.offset + rend.length);
        node.setSourceRange(range);
        node.setDirty(false);
    }

    /**
     * Set the token range for the node and include a trailing newline.
     */
    public static void setSourceRangePlusNewLine(IAstNode node, Token start, Token end) {
    	setSourceRange(node, start, end);
    	includeNewLineInSourceRange(node);
    }
    
    public static void includeNewLineInSourceRange(IAstNode node) {
    	ISourceRange range = node.getSourceRange(); 
       	char[] text = range.getFile().getText();
    	int eol = ParseUtils.includeTrailingComment(text, range.getEndOffset(), text.length, true);
    	node.getSourceRange().setOffsetRange(range.getOffset(), eol);
    }
    
    /**
     * Set token range to start after 'start' up to 'end'.
     * @param node
     * @param start
     * @param end
     */
    public static void setSourceRangeAfterStart(IAstNode node, Token start, Token end) {
        RssToken rstart = (RssToken)start;
        RssToken rend = (RssToken)end;
        
        char[] text = rstart.file.getText();
        int offset = rstart.offset + rstart.length;
        int endOffset = rstart.file == rend.file ? rend.offset : text.length;
        
        while (offset < endOffset && Character.isWhitespace(text[offset]))
        	offset++;
        ISourceRange range = new SourceRange(
            rstart.file, offset, 
            rend.file, rend.offset + rend.length);
        node.setSourceRange(range);
        node.setDirty(false);
    }

    /**
     *  Update the token range for 'node' to include 'inner',
     *  optionally containing the text for 'start' and 'end' 
     *  (if not null).
     */
    public static void setSourceRangeContaining(IAstNode node, Token start, IAstNode inner, Token end) {
        RssToken rstart = (RssToken)start;
        RssToken rend = (RssToken)end;
        if (start != null && end != null) {
            setSourceRange(node, start, end);
            return;
        }
        ISourceRange range;
        ISourceRange innerRange = inner.getExtendedRange();
        if (rend != null) {
            range = new SourceRange(
                innerRange.getFile(), innerRange.getOffset(), rend.file,
                rend.offset + rend.length);
        } else if (rstart != null) {
            range = new SourceRange(
                rstart.file, rstart.offset, innerRange.getEndFile(),
                innerRange.getEndOffset());
        } else {
            if (inner == node)
                range = innerRange;
            else
                range = new SourceRange(
                    innerRange.getFile(), innerRange.getOffset(), 
                    innerRange.getEndFile(), innerRange.getEndOffset());
        }
        node.setSourceRange(range);
        node.setDirty(false);
    }

    /**
     * Given a number of potentially null nodes, set the token
     * range for node to the widest span from the first to the
     * last node.
     * @param node
     * @param elements
     */
    public static void setSourceRangeSpanning(IAstNode node, IAstNode[] elements) {
        IAstNode from = null, to = null;
        for (int i = 0; i < elements.length; i++) {
            if ((from = elements[i]) != null)
                break;
        }
        for (int i = elements.length - 1; i >= 0; i--) {
            if ((to = elements[i]) != null)
                break;
        }
        Check.checkArg(from != null && to != null);
        
        ISourceRange fromRange = from.getExtendedRange();
        ISourceRange toRange = to.getExtendedRange();
        ISourceRange range = new SourceRange(
                fromRange.getFile(), fromRange.getOffset(), toRange.getEndFile(),
                toRange.getEndOffset());
        node.setSourceRange(range);
        node.setDirty(false);
    }
    
    /** Extend the node's range so it ends at the start of after
     * @param node
     * @param after
     */
    /*private void extendSourceRangeEnd(IAstNode node, IAstNode after) {
        ISourceRange nodeRange = node.getExtendedRange();
        ISourceRange nextRange = after.getSourceRange();
        if (nodeRange.getEndOffset() < nextRange.getOffset())
            node.setExtendedRange(new SourceRange(
                    nodeRange.getFile(), 
                    nodeRange.getOffset(), 
                    nextRange.getFile(),
                    nextRange.getOffset()));
    }*/

    /**
     * Set the extended range for the node.
     */
    public static void setExtendedSourceRange(IAstNode node, Token start, Token end) {
        RssToken rstart = (RssToken)start;
        RssToken rend = (RssToken)end;
        ISourceRange range = new SourceRange(rstart.file, rstart.offset, rend.file,
                                            rend.offset + rend.length);
        node.setSourceRange(range);
        node.setDirty(false);
    }

    /**
     *  Update the source range for 'node' so that it contains 'inner'
     *  and ends after 'start' (if not null) and before 'end' (if not null).
     */
    public static void setExtendedSourceRangeBracketed(IAstNode node, Token start, IAstNode inner, Token end) {
        RssToken rstart = (RssToken)start;
        RssToken rend = (RssToken)end;
        if (start != null && end != null) {
            setExtendedSourceRange(node, start, end);
            return;
        }
        ISourceRange range;
        ISourceRange innerRange = inner.getExtendedRange();
        if (rend != null) {
            range = new SourceRange(
                innerRange.getFile(), innerRange.getOffset(), rend.file,
                rend.offset);
        } else if (rstart != null) {
            range = new SourceRange(
                rstart.file, rstart.offset + rstart.length, innerRange.getEndFile(),
                innerRange.getEndOffset());
        } else {
            if (inner == node)
                range = innerRange;
            else
                range = new SourceRange(
                    innerRange.getFile(), innerRange.getOffset(), innerRange.getEndFile(),
                    innerRange.getEndOffset());
        }
        node.setExtendedRange(range);
        node.setDirty(false);
    }

    /**
     * Set the extended source range to include the leading
     * and trailing comments.  Leading comments include whitespace,
     * but if includeLeadingSpace, only if that
     * whitespace does not abut the line break (i.e. decl inside a line).
     * Otherwise, the leading text stops at whitespace.  This ensures that
     * indentation is not incorporated into the extended source range.
     * @param node
     * @param includeLeadingSpace include leading space? (IGNORED FOR NOW)
     * @param includeNewline include trailing newline in end of extended range?
     */
    public static void setExtendedSourceRangeFromComments(IAstNode node, boolean includeLeadingSpace, boolean includeNewline) {
        ISourceRange sourceRange = node.getSourceRange();
        RssToken rstart = (RssToken) null;
        RssToken rend = (RssToken) null;
        if (sourceRange != null) {
        	int boc = scanBackwardForComments(
	    			sourceRange.getFile(),
	    			sourceRange.getOffset(),
	    			(rstart != null && rstart.file == sourceRange.getFile() 
	    				? rstart.offset : 0),
	    				false);
        	setExtendedSourceRangeFrom(node, boc);

        	// don't look any further if ending at newline already
        	int eoc = sourceRange.getEndOffset();
        	char endCh = sourceRange.getFile().getText()[eoc-1];
        	if (endCh != '\r' && endCh != '\n') {
	        	eoc = scanForwardForComments(
	        			sourceRange.getFile(),
	        			sourceRange.getEndOffset(),
	        			(rend != null && rend.file == sourceRange.getFile() 
	    	    				? rend.offset : sourceRange.getFile().getText().length),
	    	    				includeNewline);
        	}
        	setExtendedSourceRangeTo(node, eoc);
        }

    }
    
    /**
	 * Set the extended source ranges of the nodes, so they
	 * include what we expect to be comments in this style:
	 * <pre>
	 *   &lt;&lt; preceding comment(s) &gt;&gt;
	 *   &lt;&lt; node &gt;&gt; &lt;&lt;trailing comment&gt;&gt;
	 * </pre>
	 * The first array element starts after the first newline after 
	 * 'start', array elements are contiguous, and the last array element 
	 * ends at the last newline before 'end'.  Each element is
	 * extended to include its whole line, if possible.
	 * @param start start token, or null for beginning of file
	 * @param nodes
	 * @param end end token, or null for end of file
	 */
    public static void extendSourceRanges(Token start, IAstNode[] nodes, Token end) {
	    IAstNode prev = null;
	    int nl, boc;
	    RssToken rstart = (RssToken) start;
	    RssToken rend = (RssToken) end;
	    for (int i = 0; i < nodes.length; i++) {
	        IAstNode node = nodes[i];
	        ISourceRange sourceRange = node.getSourceRange();
	        if (sourceRange != null) {
	        	// scan backward for comments
	        	boc = scanBackwardForComments(
	        			sourceRange.getFile(),
	        			sourceRange.getOffset(),
	        			(rstart != null && rstart.file == sourceRange.getFile() 
	        				? rstart.offset : 0),
	        				false);
	        	setExtendedSourceRangeFrom(node, boc);
	        			
	        	
	            if (i + 1 < nodes.length && nodes[i + 1].getSourceRange() != null) {
	                nl = skipWhitespaceForward(sourceRange.getFile(),
	                        sourceRange.getEndOffset(),
	                        nodes[i + 1].getSourceRange().getOffset());
	                setExtendedSourceRangeTo(node, nl);
	            }
	            if (prev == null) { 
	                if (start != null) {
	                    nl = skipWhitespaceForward(rstart.file, rstart.offset + rstart.length,
	                            sourceRange.getOffset()
	                            );
	                    if (nl == sourceRange.getOffset())
	                        nl = rstart.offset + rstart.length;
	                    setExtendedSourceRangeFrom(node, nl);
	                } else {
	                    // this happens at start
	                    setExtendedSourceRangeFrom(node, 0);
	                }
	            } else {
	            	nl = skipWhitespaceForward(prev.getSourceRange().getFile(),
	                        prev.getSourceRange().getEndOffset(),
	                        sourceRange.getOffset());
	                setExtendedSourceRangeFrom(node, nl);
	                //extendSourceRangeEnd(prev, nodes[i]);
	            }
	            prev = node;
	        }
	    }
	    if (prev != null) {
	        if (end != null) {
	            nl = skipWhitespaceForward(prev.getSourceRange().getFile(), prev.getSourceRange().getEndOffset(), rend.offset);
	            setExtendedSourceRangeTo(prev, nl);
	        } else {
	            // this happens at end
	            nl = skipWhitespaceForward(prev.getSourceRange().getFile(), prev.getSourceRange().getEndOffset(), prev.getSourceRange().getFile().getText().length);
	            setExtendedSourceRangeTo(prev, nl);
	        }
	    }
	
	}

    public static  int scanBackwardForComments(ISourceFile file, int offset, int limit, boolean includeLeadingSpace) {
    	char[] text = file.getText();
    	return ParseUtils.includeLeadingComment(text, offset, limit, includeLeadingSpace);
	}

    public static  int scanForwardForComments(ISourceFile file, int offset, int limit, boolean includeNewline) {
    	char[] text = file.getText();
    	int eoc = ParseUtils.includeTrailingComment(text, offset, limit, includeNewline);
    	return eoc;
	}

    public static  void setExtendedSourceRangeFrom(IAstNode node, int start) {
        ISourceRange innerRange = node.getExtendedRange();
        // check needed for macro cases 
        if (start < innerRange.getEndOffset())
        	innerRange.setOffsetRange(start, innerRange.getEndOffset());
    }

    public static  void setExtendedSourceRangeTo(IAstNode node, int end) {
        ISourceRange innerRange = node.getExtendedRange();
        if (end > innerRange.getEndOffset())
        	innerRange.setOffsetRange(innerRange.getOffset(), end);
    }

    public static void setSourceRangeFrom(IAstNode node, int start) {
    	setExtendedSourceRangeFrom(node, start);
        ISourceRange innerRange = node.getSourceRange();
        // check needed for macro cases 
        if (start < innerRange.getEndOffset())
        	innerRange.setOffsetRange(start, innerRange.getEndOffset());
    }

    public static void setSourceRangeTo(IAstNode node, int end) {
    	setExtendedSourceRangeTo(node, end);
        ISourceRange innerRange = node.getSourceRange();
        if (end > innerRange.getEndOffset())
        	innerRange.setOffsetRange(innerRange.getOffset(), end);
    }

    /**
     * Skip whitespace, including comments, to find the first newline 
     * after 'start' but before 'offset' and return the offset after 
     * the newline.  If no newline found, return offset.
     * @param start
     * @param offset
     */
    public static  int skipWhitespaceForward(ISourceFile file, int start, int offset) {
    	char[] text = file.getText();
    	int nl = ParseUtils.includeTrailingComment(text, start, offset, true);
    	return nl;
    	//int nl = ParseUtils.skipWhitespaceForward(text, start, offset);
    	//nl = ParseUtils.skipIfNewLine(text, nl);
    	//return nl;/
    	/*
        int nl = start;
        while (nl < offset) {
            if (text[nl] == '\r') {
                if (nl + 1 <= offset && text[nl+1] == '\n')
                    return nl + 2;
                else 
                    return nl + 1;
            }
            else if (text[nl] == '\n')
                return nl + 1;
            nl++;
        }
        return offset;*/
    }

    /**
     * Find the first newline before 'end' but after
     * 'endOffset' and return the offset of the newline.
     * @param end
     * @param endOffset
     */
    /*private int findNewlineBefore(ISourceFile file, int end, int endOffset) {
        int nl = end;
        char[] text = file.getText();
        while (nl > endOffset) {
            nl--;
            if (text[nl] == '\r' || text[nl] == '\n')
                return nl;
        }
        return endOffset;
    }*/

    /**
     * Find the source range for a list of child nodes 
     * delineated by start and end tokens, but not including
     * those tokens.
     * @param start the open brace
     * @param end the close brace
     */
    public static void setListSourceRange(IAstNode node, Token start, Token end) {
    	Check.checkArg(start);
    	Check.checkArg(end);
    	IAstNode[] kids = node.getChildren();
    	
    	
        RssToken rstart = (RssToken) start;
        RssToken rend = (RssToken) end;
    	int boc;
    	
    	ISourceFile file = node.getParent().getSourceRange().getFile();
    	int lower = rstart.offset + rstart.length;
    	if (kids.length > 0)
    		boc = scanBackwardForComments(
    			file,
    			kids[0].getSourceRange().getOffset(),
    			lower,
    			false);
    	else
    		boc = lower;

    	// encapsulate children
    	if (kids.length > 0 && kids[0].getExtendedRange() != null) {
    		int firstChildOffset = kids[0].getExtendedRange().getOffset();
	    	if (firstChildOffset < boc) {
	    		Check.checkState(firstChildOffset >= lower);
	    		boc = firstChildOffset;
	    	}
    	}
    	
    	int eoc;
    	int upper = rend.offset;
    	if (kids.length > 0)
    		eoc = scanForwardForComments(
    			file,
    			kids[kids.length-1].getSourceRange().getEndOffset(),
    			upper,
    			false);
    	else {
    		if (rend != null)
    			eoc = lower;	// no kids, so nothing included in source range (not even spaces)
    		else
    			eoc = upper;
    	}
    	
    	node.setSourceRange(new SourceRange(file, boc, eoc - boc)); 

    	int bol;
    	int eol;
    	
    	char[] text = file.getText();
    	bol = ParseUtils.includeLeadingComment(
				text, 
				boc,
				lower,
				false);
		setExtendedSourceRangeFrom(node, bol);
    	
    	for (int i = 0; i < kids.length; i++) {
			 bol = ParseUtils.includeLeadingComment(
					text, 
					kids[i].getExtendedRange().getOffset(),
					i > 0 ? kids[i - 1].getExtendedRange().getEndOffset() :
						node.getSourceRange().getOffset(),
						false);
			setExtendedSourceRangeFrom(kids[i], bol);
			eol = ParseUtils.includeTrailingComment(
						text, 
						kids[i].getExtendedRange().getOffset(),
						i+1 < kids.length ? kids[i + 1].getExtendedRange().getOffset() :
							node.getSourceRange().getEndOffset(),
							false);
			setExtendedSourceRangeTo(kids[i], eol);
		} 
    }

    /**
     * Find the source ranges for the top-level file nodes.
     * <p> 
     * We need to take care not to consume the file header comment
     * so it won't disappear between saves.
     * 
     * We'll just assume it is present, even if it happens to be
     * Doxygen for the first item in the file (better safe than sorry).
     */
    public static void setFileNodesSourceRange(IAstSourceFile asFile) {
    	if (asFile.isReadOnly())
    		return;
    	
    	IAstListNode node = asFile.getFileNodeList();
    	IAstNode[] kids = node.getChildren();
    	
    	
    	
    	ISourceFile file = asFile.getSourceFile();
    	int lower = 0;
    	int boc = 0;
    	
    	while (true) {
    		boc = scanForwardForComments(file, lower, file.getLength(), true);
    		if (boc == lower)
    			break;
    		lower = boc;
    	}
    	
    	/*
    	if (kids.length > 0)
    		boc = scanBackwardForComments(
    			file,
    			kids[0].getSourceRange().getOffset(),
    			lower,
    			false);
    	else
    		boc = lower;
    		*/
    	
    	// encapsulate children
    	if (kids.length > 0 && kids[0].getExtendedRange() != null) {
    		int firstChildOffset = kids[0].getExtendedRange().getOffset();
	    	if (firstChildOffset < boc) {
	    		if (!(kids[0] instanceof IAstPreprocessorTextNode)) {
		    		// sorry, you don't own this comment
		    		//Check.checkState(firstChildOffset >= lower);
		    		setExtendedSourceRangeFrom(kids[0], boc);
		    		setSourceRangeFrom(kids[0], boc);
		    	} else {
		    		boc = firstChildOffset;
		    	}
	    	}
    	}
    	
    	int upper = file.getLength();
    	
    	node.setSourceRange(new SourceRange(file, boc, upper - boc));
    	
    	int bol;
    	int eol;
    	
    	char[] text = file.getText();
    	/*bol = ParseUtils.includeLeadingComment(
				text, 
				boc,
				lower,
				false);
		setExtendedSourceRangeFrom(node, bol);
    		*/
    	
    	for (int i = 0; i < kids.length; i++) {
			 bol = ParseUtils.includeLeadingComment(
					text, 
					kids[i].getExtendedRange().getOffset(),
					i > 0 ? kids[i - 1].getExtendedRange().getEndOffset() :
						node.getSourceRange().getOffset(),
						false);
			setExtendedSourceRangeFrom(kids[i], bol);
			eol = ParseUtils.includeTrailingComment(
						text, 
						kids[i].getExtendedRange().getOffset(),
						i+1 < kids.length ? kids[i + 1].getExtendedRange().getOffset() :
							node.getSourceRange().getEndOffset(),
							false);
			setExtendedSourceRangeTo(kids[i], eol);
		} 

    }


    public static void extendSourceRangeWithLeadingWhitespace(Token start, IAstNode node) {
    	ISourceRange range = node.getExtendedRange();
    	char[] text = range.getFile().getText();
    	RssToken rstart = (RssToken) start;
    	int pos = ParseUtils.includeLeadingComment(
				text, 
				range.getOffset(),
				rstart.offset + rstart.length,
				false);
		setExtendedSourceRangeFrom(node, pos);
    	
    }
}
