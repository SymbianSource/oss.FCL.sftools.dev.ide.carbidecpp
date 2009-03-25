/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.cpp.internal.api.utils.core;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import java.io.File;
import java.net.URI;

/**
 * A reference to a location in a file.  It contains a source,
 * a line, and a column (the latter both 1-based).
 * <p>
 * The source is presumably a file, but you can use
 * whatever is needed.  The source should implement
 * toString() in an appropriate manner for reporting
 * the source reference as a string. 
 *
 */
public class MessageLocation {
    /** The full path of the offending resource */
	private URI uri;
    /** The line number where this node starts (1-based) */
	private int lineNumber;
    /** The character offset of the column where the node starts (1-based) */ 
	private int columnNumber;

    /**
     * Create a new source reference
     * @param project the project (may not be null)
     * @param line line (1-based)
     * @param column column (1-based)
     */
    public MessageLocation(IProject project, int line, int column) {
        if (project == null)
        	throw new IllegalArgumentException();
        if (project.getLocationURI() != null)
        	throw new IllegalArgumentException();
        this.uri = project.getLocationURI();
        lineNumber = line;
        columnNumber = column;
    }
    
    /**
     * Create a new source reference
     * @param fullpath the full filesystem path to the offending resource (may not be null)
     * @param line line (1-based)
     * @param column column (1-based)
     */
    public MessageLocation(IPath fullpath, int line, int column) {
        if (fullpath == null)
        	throw new IllegalArgumentException();
        this.uri = URIUtil.toURI(fullpath);
        lineNumber = line;
        columnNumber = column;
    }
    
    /**
     * Create a new source reference
     * @param uri the uri to the offending resource (may not be null)
     * @param line line (1-based)
     * @param column column (1-based)
     */
    public MessageLocation(URI uri, int line, int column) {
        if (uri == null)
        	throw new IllegalArgumentException();
        this.uri = uri;
        lineNumber = line;
        columnNumber = column;
    }
    
    /**
     * @param path the full filesystem path to the offending resource (may not be null)
    */
    public MessageLocation(IPath path) {
        this(path, 0, 0);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof MessageLocation))
            return false;
        MessageLocation other = (MessageLocation) obj;
        return uri.equals(other.uri)
        && lineNumber == other.lineNumber
        && columnNumber == other.columnNumber;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return uri.hashCode()
        ^ (lineNumber << 16)
        ^ (columnNumber << 8)
        ^ 0x18283782;
    }
     
    public int getColumnNumber() {
        return columnNumber;
    }
    
    public int getLineNumber() {
        return lineNumber;
    }
    
    /** 
     * Return full path in filesystem 
     */
    public IPath getLocation() {
        return URIUtil.toPath(uri);
    }
    
    /**
	 * @return the URI for the message
	 */
	public URI getURI() {
		return uri;
	}
    
    /**
     * Get the workspace-relative path, or <code>null</code> if
     * not resolvable to the workspace
     */
    public IPath getPath() {
    	return FileUtils.convertToWorkspacePath(getLocation());
    }
    
    public String toString() {
    	IPath path = getPath();
    	if (path == null)
    		path = getLocation();
        return path+":"+lineNumber+":"+columnNumber;     //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public String toShortString() {
        return new File(uri.getPath()).getName()+":"+lineNumber;     //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Set the full path
     */
    public void setLocation(IPath newPath) {
        this.uri = URIUtil.toURI(newPath);
    }
    
}
