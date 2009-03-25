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

package com.nokia.sdt.sourcegen.templates.frontend;


import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.templates.core.IFrontEnd;
import com.nokia.sdt.utils.MessageReporting;

import java.util.Iterator;
import java.util.Stack;

/**
 * Parser for template language.
 * This generates Node trees corresponding to the format
 * of the text passed in.
 * <p>
 * For example, if the expression delimiters are ${ and },
 * then this text:
 * <p>
 * <pre>
 *      hello ${name}
 * </pre>
 * <p>
 * will generate:
 * <p>
 * <pre>
 *      TextNode("hello", ExprBlockNode("name") )
 * </pre>
 * 
 * 
 *
 */
public class FrontEnd extends Tokenizer implements IFrontEnd {

    /** the current stack of open blocks (Token) */
    private Stack openNodes;

    /** The last token read.  Any parse routine is expected to leave
     * the next unconsumed token in this spot.
     */
    private Token token;
    
    /** If true, emit only one TextNode at a time,
     * otherwise, emit separate blocks for whitespace, newlines,
     * and other text
     */
    private boolean collapseTextBlocks;
    
    /**
     * Create a frontend with default behavior:
     * <p>
     * No raw escapes<p>
     * Expression: ${ ... }<p>
     * Script: &lt;% ... %&gt;
     * @param collapseTextBlocks true: reduce sequences of Text Nodes into one
     */
    public FrontEnd(boolean collapseTextBlocks) {
        super();
        this.collapseTextBlocks = collapseTextBlocks;
    }

     /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.IFrontEnd#parse()
     */
    public Node parse() {
        openNodes = new java.util.Stack();
        
        token = readToken();
        Node nodes = parseList();
        
        if (!openNodes.empty()) {
            nodeStackError("FrontEnd.UnterminatedBlock", openNodes.peek()); //$NON-NLS-1$
        }
        
        if (token != null) {
            error(token.isCloseDelimiter() ?
                    "FrontEnd.UnexpectedClosingDelimiter" //$NON-NLS-1$
                    : "FrontEnd.UnexpectedTrailingText"); //$NON-NLS-1$
        }
        return nodes;
    }

    /*
    private boolean inRawBlock() {
        return !openNodes.empty() && 
            ((Token)openNodes.peek()).isOpenDelimiter() &&
            ((Token)openNodes.peek()).value == DELIM_RAW;
    }

    private boolean inExprBlock() {
        return !openNodes.empty() && 
            ((Token)openNodes.peek()).isOpenDelimiter() &&
            ((Token)openNodes.peek()).value == DELIM_EXPR;
    }
    */
    
    private boolean inScriptBlock() {
        return !openNodes.empty() && 
            ((Token)openNodes.peek()).isOpenDelimiter() &&
            ((Token)openNodes.peek()).value == DELIM_SCRIPT;
    }

    /**
     * Parse a block:
     * 
     * block := delim.open list delim.close 
     */
    private BlockNode parseBlock() {
        if (token == null || token.type != Token.DELIM)
            throw new IllegalArgumentException();
        
        switch (token.value) {
        case -Tokenizer.DELIM_RAW:
        case -Tokenizer.DELIM_EXPR:
        case -Tokenizer.DELIM_SCRIPT:
            error("FrontEnd.UnexpectedClosingDelimiter"); //$NON-NLS-1$
            break;
        }

        // check for redundant blocks
        if (!openNodes.empty()) {
            Token parent = (Token) openNodes.peek();
            if (parent.value == token.value)
                warning("FrontEnd.RedundantBlocks"); //$NON-NLS-1$
        }
        
        // note we're in an open block
        openNodes.push(token);
        
        // get the contained nodes
        token = readToken();
        Node list = parseList();
        
        // try to close the block
        if (openNodes.empty())
            throw new AssertionError("block stack underflow"); //$NON-NLS-1$
        
        Token open = (Token) openNodes.peek();
        
        // make sure the current token is the closing one
        if (token == null)
            nodeStackError("FrontEnd.UnterminatedBlock", open); //$NON-NLS-1$
        else if (token.type != Token.DELIM)
            nodeStackError("FrontEnd.UnexpectedToken"); //$NON-NLS-1$
        else if (token.value != -open.value)
            nodeStackError("FrontEnd.MismatchedClosingDelimiter", open); //$NON-NLS-1$

        openNodes.pop();
 
        // prime next token for the caller
        token = readToken();
        
        // construct the block
        switch (open.value) {
        case Tokenizer.DELIM_RAW:
            return new BlockNode(BlockNode.BLOCK_RAW, list);
        case Tokenizer.DELIM_EXPR:
            return new BlockNode(BlockNode.BLOCK_EXPR, list);
        case Tokenizer.DELIM_SCRIPT:
            return new BlockNode(BlockNode.BLOCK_SCRIPT, list);
        }
        
        throw new AssertionError("unknown block type " + token); //$NON-NLS-1$
    }


    /**
     * Parse a list of nodes:
     * 
     * list := node list | nil
     * @return new node
     */
    private Node parseList() {
        Node head = null, tail = null;
        
        if (token == null) {
            head = tail = new TextNode(getCurrentRef(), ""); //$NON-NLS-1$
            return head;
        }
        
        while (token != null) {
            Node next;
            
            if (isMatchingCloseDelimiter(token))
                break;
                
            if (isVisibleOpenDelimiter(token)) {
                next = parseBlock();
            } else {
                // catenate successive text nodes
                if (collapseTextBlocks && tail != null 
                        && tail instanceof TextNode) 
                {
                    ((TextNode)tail).text += token.text;
                    token = readToken();
                    continue;
                } else {
                    next = new TextNode(token.ref, token.text);
                }
                token = readToken();
            }
            
            if (head == null)
                head = next;
            else
                tail.setNext(next);
            tail = next;
        }
        
        return head;
    }



    /**
     * Tell whether an open delimiter is "visible" in the current context. I.e.
     * inside scripts, we don't open new blocks, since these are used as
     * script-level programming constructs.
     * 
     * @param token
     *            the token to test
     * @return true if this counts as an opening delimiter
     */
    private boolean isVisibleOpenDelimiter(Token token) {
        if (!token.isOpenDelimiter())
            return false;
        // we ignore delimiters inside script blocks,
        // except for another script delimiter, which
        // is redundant
        return !inScriptBlock() || token.value != DELIM_RAW;
    }

    /**
     * Tell whether an close delimiter matches an expected
     * closing delimiter.  
     * 
     * @param token
     *            the token to test
     * @return true if this counts as an closing delimiter
     */
    private boolean isMatchingCloseDelimiter(Token token) {
        if (!token.isCloseDelimiter())
            return false;
        return openNodes.empty() ? false : 
            ((Token) openNodes.peek()).value == -token.value;
    }

    protected void emit(Message msg) {
        MessageReporting.emitMessage(msg);
    }


    private Message makeMessage(int severity, Token token, String key) {
        Message msg;
        msg = new Message(severity, token != null ? token.ref : getCurrentRef(), 
                key, Messages.getString(key));
        return msg;
    }

    private Message makeMessage(int severity, Token token, String key, Object arg) {
        Message msg;
        msg = new Message(severity, token != null ? token.ref : getCurrentRef(), 
                key, Messages.getString(key), arg);
        return msg;
    }

    protected void error(String key) {
        Message msg;
        msg = makeMessage(Message.ERROR, token, key);
        if (token != null)
            msg.text += makeMessage(Message.INFO, null, "FrontEnd.AtTokenX", token); //$NON-NLS-1$
        emit(msg);
    }

    protected void warning(String key) {
        Message msg;
        msg = makeMessage(Message.WARNING, token, key);
        if (token != null)
            msg.text += makeMessage(Message.INFO, null, "FrontEnd.AtTokenX", token); //$NON-NLS-1$
        emit(msg);
    }
    
    /**
     * Report an error and dump the node stack
     * @param key the message id
     */
    private void nodeStackError(String key) {
        Message msg;
        msg = makeMessage(Message.ERROR, token, key);
        if (token != null)
            msg.text += makeMessage(Message.INFO, null, "FrontEnd.AtTokenX", token); //$NON-NLS-1$
        msg.text += dumpOpenNodeStack();
        emit(msg);
    }

    private void nodeStackError(String key, Object arg) {
        Message msg;
        msg = makeMessage(Message.ERROR, token, key, arg);
        if (token != null)
            msg.text += makeMessage(Message.INFO, null, "FrontEnd.AtTokenX", token); //$NON-NLS-1$
        msg.text += dumpOpenNodeStack();
        emit(msg);
    }

    /**
     * @return printable representation of open nodes
     */
    private String dumpOpenNodeStack() {
        StringBuffer buffer = new StringBuffer();
        if (!openNodes.empty()) {
            buffer.append(Messages.getString("FrontEnd.OpenBlocksHeader")); //$NON-NLS-1$
            for (Iterator iter = openNodes.iterator(); iter.hasNext();) {
                Token element = (Token) iter.next();
                buffer.append("\t"); //$NON-NLS-1$
                buffer.append(element.ref);
                buffer.append("\n"); //$NON-NLS-1$
            }
        }
        return buffer.toString();
    }
 
}
