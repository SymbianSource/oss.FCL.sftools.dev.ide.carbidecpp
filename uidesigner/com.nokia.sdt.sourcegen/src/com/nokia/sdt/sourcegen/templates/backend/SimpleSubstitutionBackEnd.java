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

package com.nokia.sdt.sourcegen.templates.backend;

import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.templates.core.IBackEnd;
import com.nokia.sdt.sourcegen.templates.frontend.*;
import com.nokia.sdt.utils.*;

import java.io.*;
import java.util.Map;
import java.util.Stack;

/**
 * This is a simple backend, which merely traverses the Node tree, treating
 * expression nodes as variable names and emitting their values, and emitting
 * raw text as itself. Script nodes are not allowed.
 * <p>
 * Since this backend combines traversal and emission, it
 * must keep an explicit node stack and a "handler" to know
 * how to interpret text nodes (i.e. text nodes inside a raw
 * block node vs. text nodes inside an expression block node).
 * 
 * 
 * 
 */
public class SimpleSubstitutionBackEnd implements IBackEnd, IDeepNodeVisitor {
    /** Our dictionary of variables */
    protected Map variables;

    /** Our destination */
    private Writer output;

    /** Our intermediate destination */
    private StringWriter writer;

    /** Message listener */
    private IMessageListener messageListener;
    
    public SimpleSubstitutionBackEnd() {
        super();
    }

    public SimpleSubstitutionBackEnd(Writer writer, Map variables) {
        super();
        setWriter(writer);
        setVariables(variables);
    }

    public void setWriter(Writer writer) {
        if (writer == null)
            throw new IllegalArgumentException();
        this.output = writer;
        this.writer = new StringWriter();
    }

    public void setVariables(Map variables) {
        if (variables == null)
            throw new IllegalArgumentException();
        this.variables = variables;
    }

    public void setMessageListener(IMessageListener messageListener) {
    	this.messageListener = messageListener;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.nokia.sdt.sourcegen.templates.IBackEnd#translate(com.nokia.sdt.sourcegen.templates.Node)
     */
    public void translate(Node nodes) {
        if (variables == null || writer == null)
            throw new IllegalStateException();
        if (nodes == null)
            throw new IllegalArgumentException();

        //System.out.println("===============\nBackEnd\n==============="); //$NON-NLS-1$
        //new Dumper(System.out).dump(nodes);

        handlerStack = new java.util.Stack();
        textNodeHandler = rawHandler;
        nodes.traverseDeep(this);

        try {
            output.write(writer.toString());
        } catch (IOException e) {
            MessageReporting.emitMessage(new Message(Message.ERROR, null,
                    "FrontEnd.IOError", e.getLocalizedMessage())); //$NON-NLS-1$
        }
    }

    protected void error(String key, Node node) {
    	Message msg = new Message(Message.ERROR, node.getRef(), key, Messages.getString(key));
    	if (messageListener != null)
    		messageListener.emitMessage(msg);
    	else
    		MessageReporting.emitMessage(msg);
    }

    protected void error(String key, Node node, Object arg) {
    	Message msg = new Message(Message.ERROR, node.getRef(), key, Messages.getString(key),
                arg);
    	if (messageListener != null)
    		messageListener.emitMessage(msg);
    	else
    		MessageReporting.emitMessage(msg);
    }


    /** Interface for handling text nodes. */
    interface TextHandler {
        public void handle(TextNode node);
    }

    TextHandler rawHandler = new TextHandler() {
        public void handle(TextNode node) {
            writer.write(node.getText());
        }
    };

    TextHandler exprHandler = new TextHandler() {
        public void handle(TextNode node) {
            Object value = variables.get(node.getText());
            if (value == null) {
                error("BackEnd.UnknownVariable", node, node.getText()); //$NON-NLS-1$
                writer.write("?{" + node.getText() + "}"); //$NON-NLS-1$ //$NON-NLS-2$
            } else
                writer.write(value.toString());
        }
    };

    /** Current handler during descent. */
    TextHandler textNodeHandler = rawHandler;

    /** Track the last textNodeHandler */
    private Stack handlerStack;

    /*
     * (non-Javadoc)
     * 
     * @see com.nokia.sdt.sourcegen.templates.frontend.DeepNodeVisitor#visitScriptBlockNode(com.nokia.sdt.sourcegen.templates.frontend.ScriptBlockNode,
     *      boolean)
     */
    public void visitBlockNode(BlockNode node, boolean open) {
        if (open) {
            handlerStack.push(textNodeHandler);
            switch (node.getType()) {
            case BlockNode.BLOCK_RAW:
                textNodeHandler = rawHandler;
                break;
            case BlockNode.BLOCK_EXPR:
                textNodeHandler = exprHandler;
                break;
            case BlockNode.BLOCK_SCRIPT:
                throw new IllegalStateException("no script blocks expected"); //$NON-NLS-1$
            }
        } else {
            textNodeHandler = (TextHandler) handlerStack.pop();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nokia.sdt.sourcegen.templates.frontend.NodeVisitor#visitTextNode(com.nokia.sdt.sourcegen.templates.frontend.TextNode)
     */
    public void visitTextNode(TextNode node) {
        textNodeHandler.handle(node);
    }
}
