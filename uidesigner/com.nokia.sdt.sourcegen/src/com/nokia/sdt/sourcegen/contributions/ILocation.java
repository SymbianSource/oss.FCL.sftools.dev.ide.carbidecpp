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

import com.nokia.sdt.sourcegen.ISourceGenLocation;
import com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionFilter;

import org.eclipse.core.runtime.IPath;

import java.util.List;

/**
 * This represents the location of a contribution.  
 * It is an instance of a domain-specific class which knows how
 * to interpret a location's contents.
 * <p>
 * ILocations are stored within a tree to track the
 * project-wide state of contributions' locations.
 * <p>
 * When it is instantiated
 * as an ILocation, necessary changes are made to source in
 * accordance with the attributes of this interface to prepare
 * for source generation.  
 * 
 *
 */
public interface ILocation {
    /** We do not know anything about this location yet */ 
    static final int S_UNKNOWN = 0;
    /** This location is known to resolve to actual source; 
     * we have a node in the parse tree and a range of pertinent
     * text for it
     */
    static final int S_RESOLVED = 1;
    /** This location refers to a missing node in the source and the
     * memory buffer. 
     */
    static final int S_MISSING = 2;
    /** This location's contributions have been generated but not
     * written to the memory buffer.
     */
    static final int S_QUEUED = 3;
    /** This location exists but is going to be regenerated
     * due to being owned.
     */
    static final int S_TO_REPLACE = 4;
    /** This location has been deleted and will be regenerated.
     * This differs from S_QUEUED in that "do not modify"
     * comments should not be emitted with this variant.
     */
    static final int S_DELETED_QUEUED = 5;
    /** This location has been regenerated to the memory buffer
     * but does not exist on disk or the parse tree. 
     */
    static final int S_PENDING = 6;
    /** This location should be deleted entirely.
     */
    static final int S_DELETE = 7;
    
    static final int UNCOMPARABLE = 999;
    
    /**
     * Get the primary/first sourcegen location
     */
    ISourceGenLocation getPrimarySourceGenLocation();
    
    /**
     * Get all the sourcegen locations (including primary) that map to this
     */
    ISourceGenLocation[] getSourceGenLocations();
    
    /**
     * Add a sourcegen location that generates this (duplicates ignored)
     */
    void addSourceGenLocation(ISourceGenLocation sloc);
    
    /**
     * Get the parent location
     */
    ILocation getParent();
    
    /**
     * Get child locations contained within this one
     * @return list of ILocation (unmodifiable)
     */
    List getChildren();

    /**
     * Add a child location
     */
    void addChildLocation(ILocation loc);

    /**
     * Get any contributions directly attached to this location
     * @return list of IContribution (unmodifiable)
     */
    List getContributions();

    /**
     * Add a contribution for this location
     * <p>
     * Sets the contribution's location to this
     */
    void addContribution(IContribution contribution);

    /**
     * Remove this contribution from the location
     * <p>
     * Sets the contribution's location to null
     * @param contrib
     */
    void removeContribution(IContribution contrib);

    /** 
     * Get the domain handler that owns this location
     */
    IDomain getDomain();

    /**
     * Get the directory specifier
     */
    String getDir();

    /**
     * Get the file specifier
     */
    String getFile();
    
    /**
     * Get the project-relative IPath 
     */
    IPath getPath();
    
    /**
     * Get the full path (inside the directory and file)
     */
    String getLocationPath();

    /**
     * Tell whether this location is "owned" or not.
     * <p>
     * If owned, its contents are cleared before applying contributions. This
     * location is the root of the area to clean. This and all its children will
     * be removed if existing. When creating the location, its contents are
     * emitted surrounded by a comment indicating the user should not modify the
     * source.
     * <p>
     * Otherwise, if not owned, the location is created and its
     * contributions applied only when it doesn't exist.  No comment
     * is emitted.  
     */
    boolean isOwned();    

    /** Get the state of the location, indicating how this
     * location corresponds to source on disk and in memory 
     * @return state
     * @see #S_UNKNOWN
     * @see #S_RESOLVED
     * @see #S_MISSING
     * @see #S_PENDING
     */
    public int getState();
    
    /**
     * Set the state of the location
     * @see #S_UNKNOWN
     * @see #S_RESOLVED
     * @see #S_MISSING
     * @see #S_PENDING
     */
    void setState(int state);
    
    /**
     * Get the indentation level implied by the location
     */
    int getIndentLevel();

    
    /**
     * Get the filter, if any, that validates contributions added to this location.
     */
    public IContributionFilter getContributionFilter();

    /**
     * Set the filter for contributions added to this location.
     * @param filter filter or null (meaning default behavior based on
     * ownership)
     */
    public void setContributionFilter(IContributionFilter filter);

    /**
     * Tell whether the contribution should be accepted.
     * If the current filter is null, the default behavior for the
     * location is:  (1) if owned, accept; (2) if not owned 
     * and the location previously existed, reject; else accept
     * @param contrib
     * @return true: accept, false: reject
     */
    public boolean filterContribution(IContribution contrib);
    
    /**
     * Visit this location and its children in order.
     * @param visitor
     * @param inOrder if true, order nodes in actual source order (leaving new nodes at the end), else, present raw #getChildren() order
     */
    void accept(ILocationVisitor visitor, boolean inOrder);

    /**
     * Compare two locations for purposes of determining ordering.
     * @param other
     * @return -1 for this &lt; other, 0 for this == other, 1 for this &gt; other, or #UNCOMPARABLE for uncomparable
     */
	int compareTo(ILocation other);
}
