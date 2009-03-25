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

import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.templates.core.IBackEnd;
import com.nokia.sdt.sourcegen.templates.frontend.Node;
import com.nokia.sdt.sourcegen.templates.frontend.TextNode;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.Path;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This backend converts a Node tree into TextChunks then
 * runs them through a client-provided visitor to serialize
 * them to text in a context-sensitive way.
 * <p>
 * setFormatter() must be called before compilation.
 * 
 * 
 * @see Node
 * @see TextChunk
 * @see ITextChunkVisitor
 */
public class TextChunkBackEnd implements IBackEnd {
	static final Pattern wsPrefix = Pattern.compile("([ \t]*\n)(.*)", Pattern.MULTILINE); //$NON-NLS-1$
	static final Pattern wsSuffix = Pattern.compile("(.*?\n?)[ \t]*", Pattern.MULTILINE); //$NON-NLS-1$

	/** The generated chunks */
    List chunks;
    
    /** Our chunk formatter */
    ITextChunkVisitor formatter;

    /** Our variables for script expansion */
    Map dictionary;
    
    public TextChunkBackEnd(ITextChunkVisitor formatter) {
        super();
        this.formatter = formatter;
        this.dictionary = null;
    }

    /**
     * Specify how chunks are serialized.
     * @param formatter
     */
    public void setFormatter(ITextChunkVisitor formatter) {
        this.formatter = formatter;
    }

    /**
     * Specify a variable table for script expansion
     * @param dict a String -> String mapping
     */
    public void setDictionary(Map dict) {
        this.dictionary = dict;
    }
    
    public List getChunks() {
        return chunks;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.nokia.sdt.sourcegen.templates.IBackEnd#translate(com.nokia.sdt.sourcegen.templates.Node)
     */
    public void translate(Node nodes) {
        if (formatter == null)
            throw new IllegalStateException();

        //System.out.println("===============\nBackEnd\n==============="); //$NON-NLS-1$
        //new Dumper(System.out).dump(nodes);

        // the initial context is raw
        chunks = new RawBlockGenerator(this).generate(nodes);

        // remove whitespace:
        //	1) at start, if whitespace and a newline prefix (delete nl)
        //	2) at end of chunk list, if newline and whitespace suffix (keep nl)
        //	3) remove the whole chunk if it becomes blank via this process
        
        // N.B.: never delete an empty chunk that came in that way;
        // this can only mean the original text was empty itself.
        TextChunk prev = null;
        for (Iterator iter = chunks.iterator(); iter.hasNext();) {
            TextChunk chunk = (TextChunk) iter.next();
            if (chunk.text.length() > 0) {
	            if (prev == null) {
	            	Matcher wsMatcher = wsPrefix.matcher(chunk.text);
	            	if (wsMatcher.matches())
	            		chunk.text = wsMatcher.group(2);
	            }
	            if (!iter.hasNext()) {
	            	Matcher wsMatcher = wsSuffix.matcher(chunk.text);
	            	if (wsMatcher.matches())
	            		chunk.text = wsMatcher.group(1);
	            }
	            if (chunk.text.length() == 0) {
	            	iter.remove();
	            } else {
	            	prev = chunk;
	            }
            } else {
            	prev = chunk;
            }
        }
        
        // format the chunks
        boolean first = true;
        for (Iterator iter = chunks.iterator(); iter.hasNext();) {
            TextChunk chunk = (TextChunk) iter.next();
            chunk.accept(formatter, first, !iter.hasNext());
            first = false;
        }
        
    }

	protected void error(String key, Node block) {
        Message msg;
        String text = (block instanceof TextNode) ? ((TextNode) block).getText() : block.toString();
        msg = new Message(Message.ERROR, 
        		block != null && block.getRef() != null ? block.getRef() :  new MessageLocation(new Path("<unknown>")), //$NON-NLS-1$ 
                key, 
                MessageFormat.format(Messages.getString(key), new Object[] { text } ));
        MessageReporting.emitMessage(msg);
    }

	/**
	 * Lookup a variable value
	 * @param text
	 * @return
	 */
	public String lookup(String text) {
		if (dictionary == null)
			return text;
		return (String) dictionary.get(text);
	}
}
