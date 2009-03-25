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

package com.nokia.sdt.sourcegen.core;

import com.nokia.cpp.internal.api.utils.core.Check;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 
 *
 */
public class SourceFile implements ISourceFile {
    public static final String SYSTEM_DEFAULT_ENCODING = System.getProperty( "file.encoding" ); //$NON-NLS-1$

    protected File file;
    private String filename;
    private char[] text;
    private String charset;

    private boolean dirty;

    private LineTable lineTable;

	private long savedTime;

    /**
     * Create a sourcefile with empty text and the default system charset
     * @param file
     */
    public SourceFile(File file) {
        this(file, new char[0], null);
    }

    /**
     * Create a source file, using the base part as the filename
     * @param file the file path
     * @param text the text of the file (may not be null)
     * @param charset the character set (may be null to use system default)
     */
    public SourceFile(File file, char[] text, String charset) {
        int idx = file.getPath().lastIndexOf(System.getProperty("file.separator")); //$NON-NLS-1$
        init(file, file.getPath().substring(idx+1), text, charset);
    }
    
    /**
     * Create a source file
     * @param file the file
     * @param filename the file name
     * @param text the text of the file (may not be null)
     * @param charset the character set (may be null to use system default)
     */
    public SourceFile(File file, String filename, char[] text, String charset) {
        init(file, filename, text, charset);
    }

    private void init(File file, String filename, char[] text, String charset) {
        Check.checkArg(file);
        Check.checkArg(filename);
        
        try {
            this.file = file.getCanonicalFile();
        } catch (IOException e) {
            this.file = file;
        }
        this.filename = filename;
        Check.checkArg(text);
        this.text = text;
        if (charset != null) {
            Check.checkArg(Charset.isSupported(charset));
        }
        this.charset = charset;
        this.lineTable = null;
        this.dirty = false;
    }

    /**
     * Equality test on ISourceFile is File equality.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        
        if (!(obj instanceof SourceFile))
            return false;
        SourceFile other = (SourceFile) obj;
        return other.file.equals(file);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getFile().hashCode() ^ 1234;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "SourceFile { name="+filename + " filepath="+file + " }"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * @return Returns the file.
     */
    public File getFile() {
        return file;
    }
    
    /**
     * @param file The file to set.
     */
    public void setFile(File file) {
        this.file = file;
        this.filename = file.getName();
        
        dirty = true;
    }

    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#getFileName()
     */
    public String getFileName() {
        return filename;
    }

    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#setFileName(java.lang.String)
     */
    public void setFileName(String filename) {
        this.filename = filename;
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.core.ISourceFile#getLength()
     */
    public int getLength() {
    	return text != null ? text.length : 0;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#getText()
     */
    public char[] getText() {
        return text;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#setText(char[])
     */
    public void setText(char[] text) {
        this.text = text;
        lineTable = null;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#getCharset()
     */
    public String getCharset() {
        return charset;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#setCharset(java.lang.String)
     */
    public void setCharset(String charset) {
        this.charset = charset;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#isDirty()
     */
    public boolean isDirty() {
        return dirty;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#setDirty(boolean)
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile#getLineTable()
     */
    public LineTable getLineTable() {
        if (lineTable == null) {
            lineTable = new LineTable(text);
        }
        return lineTable;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.core.ISourceFile#getSavedTimestamp()
     */
    public long getSavedTimestamp() {
    	return savedTime;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.core.ISourceFile#setSavedTimestamp(long)
     */
    public void setSavedTimestamp(long time) {
    	this.savedTime = time;
    }

}
