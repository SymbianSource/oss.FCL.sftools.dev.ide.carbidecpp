/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

/**
 * This location spans a range in a single document.
 *
 */
public interface IDocumentSourceRegion extends ISourceRegion {
    /**
     * Get filesystem file backing the source.
     * @see IResource#getLocation()
     * @return IPath or null for newly generated content
     */
    IPath getLocation();
    
    /**
     * Get workspace-relative file (or null)
     * @see IResource#getFullPath()
     * @return IPath or null for non-workspace file or newly generated content
     */
    IPath getFullPath();
    
	/**
	 * Get the project-relative path
	 * @return IPath or null for non-workspace file or newly generated content
	 */
	IPath getProjectRelativePath();

    /**
     * Get region of text covered in the document.
     * @return IRegion or null for newly generated content
     */
    IRegion getRegion();
    
    /**
     * Get the document backing this node.
     * @return IDocument or null for newly generated content
     */
    IDocument getDocument();

    /**
     * Set the document backing this node.
     */
    void setDocument(IDocument document);
    
    /**
     * Set the full path to the file backing this node.
     */
	void setLocation(IPath location);
	
    /**
     * Set the region in the document covering the sources.
     */
	void setRegion(IRegion region);
	
	/**
	 * Compare the receiver with the other location and return
	 * <0 if the receiver appears before the argument, 0 if they are
	 * the same, and >0 if the receiver appears after the argument.
	 * @param tokenRegion
	 * @return -1, 0, or 1
	 */
	int compareTo(IDocumentSourceRegion tokenRegion);

	/**
	 * Get a new location which has a start offset of 0. 
	 * @return
	 */
	IDocumentSourceRegion extendToStart();

}
