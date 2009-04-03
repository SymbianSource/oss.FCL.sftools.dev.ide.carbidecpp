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



import java.io.File;


/** 
 * A basic source or header file.  One instance exists per
 * unique file on disk.
 * 
 * 
 */
public interface ISourceFile {
    /** Get the file */
    public File getFile();
    
    /** Set the file (this implicitly updates result from getFileName() ) */
    public void setFile(File file);
    
    /** Get the file name (e.g. from #include) */
    public String getFileName();

    /** Set the file name (e.g. from #include) */
    public void setFileName(String file);

    /**
     * Get the length
     */
    public int getLength();
    
    /** Get the file text */
    public char[] getText();

    /** Set the file text */
    public void setText(char[] text);

    /** Get the file encoding (IANA) 
     * @see java.nio.charset.Charset
     */
    public String getCharset();

    /** Set the file encoding (IANA) 
     * @see java.nio.charset.Charset
     */
    public void setCharset(String charset);
    
    /** Get the dirty state */
    public boolean isDirty();
    
    /** Set the dirty state */
    public void setDirty(boolean dirty);

    /** Get the line table */
    public LineTable getLineTable();
    
    /**
     * Get time when last saved.
     */
    public long getSavedTimestamp();
    
    /**
     * Set time when last saved.
     */
    public void setSavedTimestamp(long time);

}
