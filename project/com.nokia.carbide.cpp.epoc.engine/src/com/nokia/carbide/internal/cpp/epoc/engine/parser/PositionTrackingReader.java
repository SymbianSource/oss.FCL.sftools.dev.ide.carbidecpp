/*
* Copyright (c) 2005-2009 Nokia Corporation and/or its subsidiary(-ies).
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

import java.io.IOException;
import java.io.Reader;

/**
 * This class implements a simple reader encapsulating java.io.Reader. It tracks
 * the offset, line, and column, unlike LineNumberReader or other Readers.
 * <p>
 * The class provides getOffset(), getLineNumber(), and getColumnNumber() functions to query
 * the state of the <i>next</i> character to read. Thus, after reading a
 * newline, the line number is incremented and the column number reset. (For
 * CR/LF sequences, the line and column do not change after the LF character of
 * the sequence is processed.)
 * 
 * 
 */
public class PositionTrackingReader extends Reader {

	/** Current character position (0-based) */
	int offset;
	
    /** Current line number (1-based) */
    int lineNumber;
    /** Current column (1-based) */
    int columnNumber;

    /** Encapsulated reader */
    Reader reader;
    boolean hadCR;
    private int savedLine;
    private int savedColumn;
    private boolean savedHadCR;

	private int savedOffset;
    
    public PositionTrackingReader(Reader reader) {
        this.reader = reader;
        this.offset = 0;
        this.lineNumber = 1;
        this.columnNumber = 1;
        this.hadCR = false;
    }
    
    /** Read a single character
     * @return -1 for end of stream, else 16-bit character 
     * @see java.io.Reader#read()
     */ 
    public int read() throws IOException {
        while (true) {
            int ch = reader.read();
            if (ch == -1)
                return ch;
            
            this.offset++;
            
            boolean ignoreLF = false;
            if (hadCR) {
                hadCR = false;
                if (ch == '\n') {
                    // got CR/LF, consume and ignore in line count
                    ignoreLF = true;
                    columnNumber--;
                }
            }
            // next column in current line
            columnNumber++;
            
            if (ch == '\r' || (ch == '\n' && !ignoreLF)) {
                // new EOL
                lineNumber++;
                columnNumber = 1;
                hadCR = (ch == '\r');
            }
            
            return ch;
        }
    }

    /** Read an array of characters
     * @param cbuf buffer to fill
     * @param off starting offset in cbuf
     * @param len maximum number of characters to read
     * @see java.io.Reader#read(char[],int,int)
     */ 
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (cbuf == null)
            throw new IllegalArgumentException();
        if (off < 0 || off + len > cbuf.length)
            throw new ArrayIndexOutOfBoundsException();
        
        int cnt = 0;
        int ch;
        while (len > 0 && (ch = read()) != -1) {
            cbuf[off++] = (char) ch;
            len--;
            cnt++;
        }
        return cnt;
    }

    /** Read an array of characters
     * @param cbuf buffer to fill
     * @see java.io.Reader#read(char[],int,int)
     */ 
    public int read(char[] cbuf) throws IOException {
        return read(cbuf, 0, cbuf.length);
    }

    /** Close reader
     * @see java.io.Reader#close()
     */ 
    public void close() throws IOException {
        reader.close();
    }
    
    /** Mark position to restore
     * @param readAheadLimit maximum number of characters that
     * can be read before losing position
     * @see java.io.Reader#mark(int)
     */ 
    public void mark(int readAheadLimit) throws IOException {
        reader.mark(readAheadLimit);
        savedOffset = offset;
        savedHadCR = hadCR;
        savedLine = lineNumber;
        savedColumn = columnNumber;
    }
    
    /** Reset position to last mark
     * @see java.io.Reader#reset()
     */ 
    public void reset() throws IOException {
        reader.reset();
        offset = savedOffset;
        hadCR = savedHadCR;
        lineNumber = savedLine;
        columnNumber = savedColumn;
    }
    
    public long skip(long n) throws IOException {
    	while (n > 0) {
    		read();
    		n--;
    	}
        return n;
    }

    /** Retrieve column number (1-based) */
    public int getColumnNumber() {
        return columnNumber;
    }
    

    /** Set column number (1-based), may not be <= 0 */
    public void setColumnNumber(int columnNumber) {
        if (columnNumber <= 0)
            throw new IllegalArgumentException();
        this.columnNumber = columnNumber;
    }
    

    /** Retrieve line number (1-based) */
    public int getLineNumber() {
        return lineNumber;
    }
    

    /** Set line number (1-based), may not be <= 0 */
    public void setLineNumber(int lineNumber) {
        if (lineNumber <= 0)
            throw new IllegalArgumentException();
        this.lineNumber = lineNumber;
    }

    /** Get offset (0-based) */
    public int getOffset() {
    	return offset;
    }
    
    /** Set offset (0-based), may not be negative */
    public void setOffset(int offset) {
    	this.offset = offset;
    }
    
    /** Read a line, stripping off terminating newlines.  Returns "" for a blank line and null for EOF. */
    public String readLine() throws IOException {
    	StringBuilder builder = new StringBuilder();
    	boolean any = false;
    	int ch;
    	while ((ch = read()) != -1) {
    		any = true;
    		if (ch == '\r') {
    			mark(1);
    			if (read() != '\n') {
    				reset();
    			}
    			break;
    		} else if (ch == '\n') {
    			break;
    		}
    		builder.append((char)ch);
    	}
    	if (any)
    		return builder.toString();
    	else
    		return null;
    }
}
