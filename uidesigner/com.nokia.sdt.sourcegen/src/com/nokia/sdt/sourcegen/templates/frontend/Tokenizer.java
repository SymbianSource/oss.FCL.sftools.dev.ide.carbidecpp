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


import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.sourcegen.core.LineTable;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Lexical analyzer for template language.
 * <p>
 * This scans text for use of delimiters in three classes (raw, 
 * expression, and script), which are user-configurable.<p>
 * Delimiters must occur in pairs in order to be recognized.<p>
 * Delimiters may be escaped using '\' characters, which are
 * stripped out ('\\' provides a raw '\').<p>
 * All text is treated the same regardless of semantic
 * context (i.e. no provisions are made for quoted text or the like).
 * 
 * 
 */
public class Tokenizer {
    IMessageLocationFactory msgLocFactory;
    IMessageListener msgListener;
    
   /* Our delimiters: for each pair, both must be null or non-null */
    private String rawOpen = null, rawClose = null;
    private String exprOpen = "${", exprClose = "}"; //$NON-NLS-1$ //$NON-NLS-2$
    private String scriptOpen = "<%", scriptClose = "%>"; //$NON-NLS-1$ //$NON-NLS-2$

    private String text;
    private int textIdx;

    private LineTable lineTable;

    private int startLine;

    private int startColumn;

    private Pattern anyDelimRegex;

    // mapping of groups in anyDelimRegex to their delimiter (DELIM_...)
    private int delimArray[] = new int[7];

    private IPath messagePath;

    public static final int DELIM_RAW = 1;

    public static final int DELIM_EXPR = 2;

    public static final int DELIM_SCRIPT = 3;

    public Tokenizer() {
    	this.msgListener = new IMessageListener() {
			public boolean isHandlingMessage(IMessage msg) {
				return true;
			}
			public void emitMessage(IMessage msg) {
				MessageReporting.emitMessage(msg);
			}
    		
    	};
        this.msgLocFactory = new IMessageLocationFactory() {

            public MessageLocation createLocation(int line, int offset) {
                return new MessageLocation(messagePath, line, offset);
            }};
        syncParseTables();
    }

    public Tokenizer(IMessageLocationFactory factory, String text,
            int startLine, int startColumn) {
        this();
        setMessageLocationFactory(factory);
        setSource(text, startLine, startColumn);
    }

    /**
     * @param factory
     */
    public void setMessageLocationFactory(IMessageLocationFactory factory) {
        Check.checkArg(factory);
        this.msgLocFactory = factory;
    }

    public void setMessageListener(IMessageListener listener) {
    	this.msgListener = listener;
    }
    
    public Tokenizer(IMessageLocationFactory factory, String text) {
        this(factory, text, 1, 1);
    }

    public void setSource(String text,
            int startLine, int startColumn) {
        Check.checkArg(text);
        this.text = text;
        this.textIdx = 0;
        this.lineTable = new LineTable(this.text.toCharArray());
        this.startLine = startLine;
        this.startColumn = startColumn;
    }
    
    public void setSource( String text) {
        setSource(text, 1, 1);
    }
  
    public void setSource(final String sourceName, String text,
            int startLine, int startColumn) {
        setSource(text, startLine, startColumn);
        messagePath = new Path(sourceName);
    }
    
    public void setSource(String sourceName, String text) {
        setSource(sourceName, text, 1, 1);
    }
 
   /** Set escapes used to delineate raw text
     * 
     * @param open the opening delimiter (null if raw text disallowed)
     * @param close the closing delimiter (null if raw text disallowed) 
     */ 
    public void setRawEscapes(String open, String close) {
        // both must be null or not
        if ((open == null) != (close == null))
            throw new IllegalArgumentException();
        
        this.rawOpen = open;
        this.rawClose = close;
    
        syncParseTables();
    }

    /** Set escapes used to delineate expressions
     * 
     * @param open the opening delimiter (null if expressions disallowed)
     * @param close the closing delimiter (null if expression disallowed) 
     */ 
    public void setExprEscapes(String open, String close) {
        // both must be null or not
        if ((open == null) != (close == null))
            throw new IllegalArgumentException();
        
        this.exprOpen = open;
        this.exprClose = close;

        syncParseTables();
    }

    /** Set escapes used to delineate script code
     * 
     * @param open the opening delimiter (null if script disallowed)
     * @param close the closing delimiter (null if script disallowed) 
     */ 
    public void setScriptEscapes(String open, String close) {
        // both must be null or not
        if ((open == null) != (close == null))
            throw new IllegalArgumentException();
        
        this.scriptOpen = open;
        this.scriptClose = close;

        syncParseTables();
    }

    /** Set up variables used to optimize scanning */
    private void syncParseTables() {
        anyDelimRegex = null;
        
        String delimRx = ""; //$NON-NLS-1$
        int delimIdx = 1;
        
        if (rawOpen != null) {
            delimRx += "(" + TextUtils.regexEscape(rawOpen) + ")|(" + TextUtils.regexEscape(rawClose) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            delimArray[delimIdx++] = DELIM_RAW;
            delimArray[delimIdx++] = -DELIM_RAW;
        }
        if (exprOpen != null) {
            if (delimRx.length() > 0)
                delimRx += "|"; //$NON-NLS-1$
            delimRx += "(" + TextUtils.regexEscape(exprOpen) + ")|(" + TextUtils.regexEscape(exprClose) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            delimArray[delimIdx++] = DELIM_EXPR;
            delimArray[delimIdx++] = -DELIM_EXPR;
        }
        if (scriptOpen != null) {
            if (delimRx.length() > 0)
                delimRx += "|"; //$NON-NLS-1$
            delimRx += "(" + TextUtils.regexEscape(scriptOpen) + ")|(" + TextUtils.regexEscape(scriptClose) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            delimArray[delimIdx++] = DELIM_SCRIPT;
            delimArray[delimIdx++] = -DELIM_SCRIPT;
        }
        
        anyDelimRegex = Pattern.compile(delimRx);
        
        // check ambiguity of delimiters
        String[] all = { rawOpen, rawClose, exprOpen, exprClose, scriptOpen, scriptClose };
        for (int i = 0; i < all.length; i++) {
            String a = all[i];
            if (a != null) {
                for (int j = i+1; j < all.length; j++) {
                    String b = all[j];
                    if (b != null) {
                        if (a.startsWith(b) || b.startsWith(a))
                            throw new IllegalStateException("ambiguous delimiters"); //$NON-NLS-1$
                    }
                }
            }
        }
    }
    
    protected void error(String key, Object arg) {
    	if (msgListener != null)
    		msgListener.emitMessage(
                new Message(Message.ERROR,
                getCurrentRef(), key, Messages.getString(key), arg)); 
    }
    
    public MessageLocation getCurrentRef() {
        if (msgLocFactory == null || lineTable == null)
            throw new IllegalStateException();
        int curLine = lineTable.getLineForOffset(textIdx);
        int curColumn = lineTable.getColumnForOffset(textIdx);
        if (curLine == 1) {
            return msgLocFactory.createLocation(startLine + curLine - 1, 
                    startColumn + curColumn - 1);
        } else {
            return msgLocFactory.createLocation(startLine + curLine - 1, 
                    curColumn);
        }
    }
    
    public Token readToken() {
        Check.checkState(text != null);
        
        // check for EOF
        if (textIdx >= text.length())
            return null;
        
        Token tok = new Token(getCurrentRef());

        // assume it's plain text
        tok.type = Token.TEXT;

        // check for delimiter at current position
        Matcher m;
        m = anyDelimRegex.matcher(text.substring(textIdx));
        if (m.lookingAt()) {
            // delimiter is here
            tok.type = Token.DELIM;
            int group = 0;
            for (group = 1; group <= 6; group++)
                if (m.start(group) != -1)
                    break;
            Check.checkState(group < 6);
            tok.value = delimArray[group];
            tok.text = m.group(group);
            textIdx += tok.text.length();
            return tok;
        }

        // else it's normal text
        
        StringBuffer buffer = new StringBuffer();
        
        // current span
        int startIndex = textIdx;
        int endIndex = textIdx;

        m = anyDelimRegex.matcher(text);
        int probableEndIndex = text.length();
        if (m.find(startIndex))
            probableEndIndex = m.start();
        
        // read and continue looking until EOS or next delimiter
        while (endIndex < text.length()) {
            // look for escapes, which prevents interpreting a
            // delimiter
            if (text.charAt(endIndex) == '\\') {
                buffer.append(text.toCharArray(), startIndex, endIndex - startIndex);
                endIndex++;
                startIndex = endIndex;
                if (endIndex < text.length()) {
                    endIndex++;
                }
            }
            else {
                if (endIndex == probableEndIndex)
                    break;
                endIndex++;
            }            
            
            if (endIndex > probableEndIndex) {
                // skipped a delimiter; reset
                probableEndIndex = text.length();
                if (m.find(endIndex))
                    probableEndIndex = m.start();
            }
        }
        
        // plain text
        if (startIndex < endIndex) {
            buffer.append(text.toCharArray(), startIndex, endIndex - startIndex);
        }
        textIdx = endIndex;
        tok.text = buffer.toString();
        tok.type = Token.TEXT;
        return tok;
    }


    /**
     * 
     */
    public void clearEscapes() {
        setExprEscapes(null, null);
        setRawEscapes(null, null);
        setScriptEscapes(null, null);
    }

    
}
