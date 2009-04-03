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

import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

/**
 * This is the basis of describing a location in source.
 *
 */
public interface ISourceRegion {
	/**
	 * Get human-readable source reference for the node.
	 */
	String getSourceReference();
	
	/**
	 * Get a MessageLocation for the node.
	 */
	MessageLocation getMessageLocation();

	/**
	 * Tell if the receiver matches the given document and contains the 
	 * given source range.
	 * @param document
	 * @param region
	 * @return true this node has source, matches the document, and its region
	 * contains region
	 */
	//boolean contains(IDocument document, IRegion region);

	/**
	 * Tell if the receiver contains the given location.
	 * @param region
	 * @return flag
	 */
	boolean contains(ISourceRegion region);

	/**
	 * Copy the source location
	 * @return new source location
	 */
	ISourceRegion copy();

	/**
	 * Tell if the receiver equals the given location.
	 * @param location
	 * @return flag
	 */
	boolean equals(Object location);
	
	/**
	 * Implemented for maps.
	 */
	int hashCode();

	/**
	 * Get the text spanned by the location.
	 * @return String
	 */
	String getText();
	
	/**
	 * Get an canonical location for the span of locations in this
	 * node which collapses adjacent locations and deletes empty
	 * locations.  The #equals(), #copy(), #hashCode(), and #contains()
	 * routines expect canonical locations.
	 * @return self, or a IDocumentSourceLocation, or
	 * another IMultiDocumentSourceLocation
	 */
	ISourceRegion getCanonicalSourceRegion();
	
	/**
	 * Get a location pointing at emptiness after this location.
	 * This is a copy of information and modifications will be ignored.
	 * @return an empty IDocumentSourceLocation 
	 */
	IDocumentSourceRegion getExclusiveTailRegion();

	/**
	 * Get a location pointing at emptiness before this location.
	 * This is a copy of information and modifications will be ignored.
	 * @return an empty IDocumentSourceLocation 
	 */
	IDocumentSourceRegion getExclusiveHeadRegion();
	
	/**
	 * Get a location pointing to content at the end of the location.
	 * Changes to this will affect the receiver.
	 * @return a contained IDocumentSourceLocation 
	 */
	IDocumentSourceRegion getInclusiveTailRegion();

	/**
	 * Get a location pointing at content at the start of this location.
	 * Changes to this will affect the receiver.
	 * @return an empty IDocumentSourceLocation 
	 */
	IDocumentSourceRegion getInclusiveHeadRegion();
	
	/**
	 * Validate the location
	 */
	void validate() throws IllegalStateException;
	
	/**
	 * Visit the locations contained in this location
	 * @param visitor
	 * @return
	 */
	int accept(ISourceRegionVisitor visitor);

	/**
	 * Get the first document referenced by the location.
	 * @return IDocument
	 */
	IDocument getFirstDocument();

	/**
	 * Get the first location referenced by the location.
	 * @return IDocument
	 */
	IPath getFirstLocation();

	/**
	 * Tell if the location is empty (spans no text)
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Return a location based on the current location with the
	 * argument removed from its range. 
	 * @param region range to remove
	 * @return new location or self if location does not intersect or an empty location
	 */
	ISourceRegion getRegionWithout(ISourceRegion region);
	
	/**
	 * Get the document source locations contained directly or indirectly by this location.
	 */
	IDocumentSourceRegion[] getDocumentSourceRegions();
}
