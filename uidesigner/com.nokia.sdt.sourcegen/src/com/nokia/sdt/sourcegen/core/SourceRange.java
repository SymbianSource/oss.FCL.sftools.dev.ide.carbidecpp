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
import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import org.eclipse.core.runtime.Path;

/**
 * 
 *
 */
public class SourceRange implements ISourceRange {

    private ISourceFile file;
    private ISourceFile endFile;
    private int offset;
    private int endOffset;

    public SourceRange(ISourceFile file, int offset, ISourceFile endFile, int endOffset) {
        init(file, offset, endFile, endOffset);
    }

    public SourceRange(ISourceFile file, int offset, int length) {
        init(file, offset, file, offset + length);
    }
    
    /**
     * @param range
     */
    public SourceRange(ISourceRange range) {
        init(range.getFile(), range.getOffset(), range.getEndFile(), range.getEndOffset());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "offs=" + offset + "-" + endOffset +  //$NON-NLS-1$ //$NON-NLS-2$
        "; line="+getLine()+"-"+getEndLine()+ //$NON-NLS-1$ //$NON-NLS-2$
        "; file="+file.getFileName()+(endFile!=file ? " endfile="+endFile.getFileName() : "") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
        + "\n'" + getText() + "'"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj == this)
    		return true;
    	if (obj instanceof SourceRange) {
    		SourceRange range = (SourceRange) obj;
    		return offset == range.offset
				&& endOffset == range.endOffset
				&& file.equals(range.file)
    			&& endFile.equals(range.endFile);
    	}
    	return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return offset + (endOffset << 16) ^ (file.hashCode() << 4) ^ (endFile.hashCode() << 2);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getFile()
     */
    public ISourceFile getFile() {
        return file;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getOffset()
     */
    public int getOffset() {
        return offset;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getLine()
     */
    public int getLine() {
        return file.getLineTable().getLineForOffset(offset);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getEndFile()
     */
    public ISourceFile getEndFile() {
        return endFile;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getEndOffset()
     */
    public int getEndOffset() {
        return endOffset;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getEndLine()
     */
    public int getEndLine() {
        return file.getLineTable().getLineForOffset(endOffset);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getText()
     */
    public String getText() {
        return getText(file.getText(), endFile.getText());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getText()
     */
    public String getText(char[] buffer, char[] endBuffer) {
        try {
            if (file.equals(endFile)) {
                return new String(buffer, offset, endOffset - offset);
            } else {
                return (new String(buffer, offset, buffer.length - offset))
                    + (new String(endBuffer, 0, endOffset));
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw e;
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#getLength()
     */
    public int getLength() {
        return endOffset - offset;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#update(ISourceFile, int, int, ISourceFile, int, int)
     */
    private void init(ISourceFile file, int offset, ISourceFile endFile, int endOffset) {
        Check.checkArg(file);
        
        // can't check this since it needs to change before new text written
        //Check.checkArg(endOffset <= endFile.getText().length);
        Check.checkArg(offset >= 0 && endOffset >= 0);
        if (file == endFile) {
            Check.checkArg(endOffset >= offset);
        }
        
        this.file = file;
        this.offset = offset;
        this.endFile = endFile;
        this.endOffset = endOffset;
    }

    /**
     * Count the number of complete lines contributed by the text
     * @param text
     * @return number of lines
     */
    public static int countLines(CharSequence text) {
        int lines = 0;
        for (int j = 0; j < text.length(); j++) {
            char c = text.charAt(j);
            if (c == '\n')
                lines++;
            else if (c == '\r') {
                lines++;
                if (j+1 < text.length() && text.charAt(j+1) == '\n')
                    j++;
            }
        }
        return lines;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#contains(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange)
     */
    public boolean contains(ISourceRange range) {
        if (!(range.getFile() == getFile() && range.getEndFile() == getEndFile()))
            return false;
        return offset <= range.getOffset() && endOffset >= range.getEndOffset();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.core.ISourceRange#intersects(com.nokia.sdt.sourcegen.core.ISourceRange)
     */
    public boolean intersects(ISourceRange range) {
        if (!(range.getFile() == getFile() && range.getEndFile() == getEndFile()))
            return false;
        if (range.getOffset() > offset)
        	return endOffset > range.getOffset();
       	else
        	return range.getEndOffset() > offset;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#setFile(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile)
     */
    public void setFile(ISourceFile file) {
        Check.checkArg(file);
        this.file = file;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#setEndFile(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile)
     */
    public void setEndFile(ISourceFile file) {
        Check.checkArg(file);
        this.endFile = file;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#setOffsetRange(int, int)
     */
    public void setOffsetRange(int offset, int endOffset) {
        Check.checkArg(offset >= 0 && endOffset >= 0);
        Check.checkArg(file != endFile || offset <= endOffset);
        this.offset = offset;
        this.endOffset = endOffset;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange#setLineRange(int, int)
     */
    public void setLineRange(int line, int endLine) {
        Check.checkArg(line >= 1 && endLine >= 1);
        Check.checkArg(file != endFile || line <= endLine);
        this.offset = file.getLineTable().getOffsetForLine(line);
        this.endOffset = file.getLineTable().getOffsetForLine(endLine+1);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.core.ISourceRange#createMessageLocation()
     */
    public MessageLocation createMessageLocation() {
    	return new MessageLocation(new Path(file.getFile().getAbsolutePath()), getLine(), 0);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.core.ISourceRange#compareTo(com.nokia.sdt.sourcegen.core.ISourceRange)
     */
    public int compareTo(ISourceRange other) {
    	Check.checkArg(file.equals(other.getFile()));
    	if (intersects(other))
    		return 0;
    	if (offset < other.getOffset())
    		return -1;
    	return 1;
    }
}
