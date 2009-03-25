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

package com.nokia.sdt.sourcegen.contributions;

import com.nokia.sdt.sourcegen.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import java.util.Collection;
import java.util.List;

/**
 * A domain describes the backend side of the contribution
 * engine, which handles integrating changes into source for
 * a given language.  Each domain may interface with a different
 * DOM or library, for instance.  A domain has its own way
 * of creating and interpreting ILocation strings as well.
 * 
 * 
 *
 */
public interface IDomain {
    /** Get the domain name */
    String getId();
    
    /** 
     * Find or create a location object for the given template location
     * and the expanded variables for that location.
     * This does not change source, it only makes a handle
     * for future source manipulation.
     * @param location the original location
     * @param dir the expanded directory
     * @param file the expanded file
     * @param fullpath a expanded slash-separated path ("a/b/c")
     * representing a "full path" from the file root
     * to the location in source where text will be written.
     * @return a location, which may be the same object as
     * a previous location whose path resolves to the same
     * semantic location.
     * @throws SourceGenException if the location is invalid
     */
    ILocation makeLocation(ISourceGenLocation location, String dir, String file, String fullpath) throws SourceGenException;

    /**
     * Find or create a location derived from another.
     * @param baseLocation existing base location
     * @param location original location
     * @param relPath an expanded slash-separated path ("a/b/c")
     * from the location representing a relative path from the baseLocation
     * to the location in source where text will be written.
     * @return a location, which may be the same object as
     * a previous location whose path resolves to the same
     * semantic location.
     * @throws SourceGenException if the location is invalid
    */
    ILocation makeLocation(ILocation baseLocation, ISourceGenLocation location, String relPath) throws SourceGenException;

    /**
     * Generate the source by loading target
     * files into memory (or creating new files in memory)
     * and applying the contributions in the list.
     * <p>
     * Changes are made to a SourceGenContext for the project.
     * @param contributions list of IContribution objects
     */
    void generateSource(List contributions) throws CoreException;

    /**
     * Get the list of project-relative files affected by the contributions
     * @param contributions list of IContribution objects
     */
    Collection<IPath> getGeneratedFiles(List contributions) throws CoreException;

    /**
     * Reset the domain for a new sourcegen session
     */
    void reset();

	/**
	 * Encode the location into a string uniquely identifying
	 * the domain and the location used to later look up the
	 * source for an event handler. 
	 * @param loc
	 * @return encoded string
	 */
	String encodeLocationToEventHandlerSymbol(ILocation loc);

	/**
	 * Decode an event handler symbol into the current real location
	 * in source.
	 * @param symbolInfo the domain-specific encoded event info
	 * @return SourceLocation
	 * @throws SourceGenException if symbol cannot be decoded 
	 */
	SourceLocation decodeEventHandlerSymbol(String symbolInfo) throws SourceGenException;
	
	/**
	 * Create a patch refactoring engine for the domain.
	 * @param patchContext 
	 * @return
	 */
	IDomainPatchRefactoringEngine createPatchRefactoringEngine(PatchContext patchContext);
	
}
