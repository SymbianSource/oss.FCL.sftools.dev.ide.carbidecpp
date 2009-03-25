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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionFilter;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Pair;

/**
 * 
 *
 */
abstract public class BaseLocation implements ILocation {
    /** Accept all contributions */
    public static final String FILTER_ACCEPT_ALL = "accept-all";  //$NON-NLS-1$

    /** Reject all contributions */
    public static final String FILTER_REJECT_ALL = "reject-all";  //$NON-NLS-1$

    /** Accept only unique prototypes in a class()  */
    public static final String FILTER_UNIQUE_PROTOTYPES = "unique-prototypes";  //$NON-NLS-1$

    /** Accept only unique base classes in a bases() (the default) */
    public static final String FILTER_UNIQUE_BASES = "unique-bases";  //$NON-NLS-1$
    
    /** Accept only unique files in #includes */
    public static final String FILTER_UNIQUE_INCLUDES = "unique-includes";  //$NON-NLS-1$


	protected ILocation parent; 
    protected List<ILocation> childLocations;
    /** full path */
    protected String locationPath;
    protected String dir;
    protected String file;
    protected IPath path;
    protected List contributions;
	protected boolean isOwned;
	protected IContributionFilter filter;

    /**
     * @param isOwned 
     * 
     */
    public BaseLocation(ILocation parent, 
            String dir, String file, boolean isOwned) {
    	this.parent = parent;
        this.childLocations = Collections.EMPTY_LIST;
        this.contributions = Collections.EMPTY_LIST;
        this.dir = dir;
        this.file = file;
        this.path = new Path(dir + "/" + file); //$NON-NLS-1$
        this.parent = parent;
        if (parent != null)
            parent.addChildLocation(this);
        this.isOwned = isOwned;
         
     }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getParent()
     */
    public ILocation getParent() {
        return parent;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#addChildLocation(com.nokia.sdt.sourcegen.contributions.ILocation)
     */
    public void addChildLocation(ILocation loc) {
        Check.checkState(loc.getParent() == this);
        if (childLocations == Collections.EMPTY_LIST)
        	childLocations = new ArrayList<ILocation>();
        childLocations.add(loc);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getChildren()
     */
    public List getChildren() {
        return Collections.unmodifiableList(childLocations);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getContributions()
     */
    public List getContributions() {
        return new ArrayList(contributions);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#addContribution(com.nokia.sdt.sourcegen.contributions.Contribution)
     */
    public void addContribution(IContribution contribution) {
        Check.checkArg(contribution.getLocation() == null
                || contribution.getLocation() == this);
        Check.checkArg(!contributions.contains(contribution));
        if (contributions == Collections.EMPTY_LIST)
        	contributions = new ArrayList<IContribution>();
        contributions.add(contribution);
        contribution.setLocation(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#removeContribution(com.nokia.sdt.sourcegen.contributions.IContribution)
     */
    public void removeContribution(IContribution contrib) {
        Check.checkArg(contrib.getLocation() == this);
        Check.checkArg(contributions.contains(contrib));
        contributions.remove(contrib);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getDir()
     */
    public String getDir() {
        return dir;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getFile()
     */
    public String getFile() {
        return file;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getPath()
     */
    public IPath getPath() {
        return path;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getPath()
     */
    public String getLocationPath() {
        return locationPath;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#isOwned()
     */
    public boolean isOwned() {
        return isOwned;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getContributionFilter()
     */
    public IContributionFilter getContributionFilter() {
        return filter;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#setContributionFilter(com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionFilter)
     */
    public void setContributionFilter(IContributionFilter filter) {
        this.filter = filter;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#filterContribution(com.nokia.sdt.sourcegen.contributions.IContribution)
     */
    public boolean filterContribution(IContribution contrib) {
        if (filter == null)
            return true;
        return filter.acceptContribution(contrib);
    }

	public void accept(ILocationVisitor visitor, boolean inOrder) {
		if (!visitor.enter(this))
			return;
		List<ILocation> childList;
		if (inOrder) {
			// make an ordered list which compares locations with
			// ranges first, and falls back to original ordering otherwise
			int index = 0;
			List<Pair<Integer, ILocation>> orderedList = new ArrayList<Pair<Integer,ILocation>>(childLocations.size());
			for (ILocation loc : childLocations) {
				orderedList.add(new Pair(index, loc));
			}
			Collections.sort(orderedList, new Comparator<Pair<Integer, ILocation>>() {

				public int compare(Pair<Integer, ILocation> o1, Pair<Integer, ILocation> o2) {
					if (o1.second.getClass() != o2.second.getClass())
						return o1.first - o2.first;
					int diff = o1.second.compareTo(o2.second);
					if (diff != UNCOMPARABLE)
						return diff;
					return o1.first - o2.first;
				}
				
			});
			childList = new ArrayList<ILocation>(childLocations.size());
			for (Pair<Integer, ILocation> info : orderedList) {
				childList.add(info.second);
			}
		} else {
			childList = childLocations;
		}
		
		for (ILocation location : childList) {
			if (!visitor.visit(location))
				break;
		}
		
		visitor.exit(this);
	}
    
}

