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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.sourcegen.ISourceGenLocation;
import com.nokia.sdt.sourcegen.contributions.*;
import com.nokia.sdt.sourcegen.core.Messages;

import org.eclipse.core.runtime.*;

import java.util.*;

/**
 * A location in the "cpp" domain
 * 
 *
 */
public class CppLocation extends BaseLocation {
    protected CppDomain domain;

    // primary location
    private ISourceGenLocation sourceGenLocation;
    // other locations
	private List<ISourceGenLocation> sourceGenLocations;
	
    int state;
    ICppLocationSegment segment;
    
    CodeRange range;
    boolean cachedRange;
    
    private ISourceManipulator sfi_;

	/** contribution generation state: true if currently emitted
	 * contributions end at a new line (where indentation is required)
	 */
	public boolean insideLine;

    /**
     *  Create a location reference for the C/C++ domain. 
     *  <p>
     * @param domain resolved domain
     * @param parent the parent location -- must not be null
     * @param location original location
     * @param dir resolved directory
     * @param file resolved file
     * @param relPath resolved relative path to parent
     * @return an ILocation or null if the location is not valid
     *  (message reported) 
     */
    static public CppLocation createLocation(CppDomain domain,
            ILocation parent,
            ISourceGenLocation location, 
            String dir, String file, String relPath) throws SourceGenException {
        Check.checkArg(domain);
        Check.checkArg(parent);
        Check.checkArg(dir);
        Check.checkArg(file);
        Check.checkArg(relPath);
        CppLocationParser cppLocationParser = new CppLocationParser();
        List segments = cppLocationParser.parse(relPath, parent);
        if (segments == null) {
        	if (cppLocationParser.getError() != null) {
                //MessageReporting.emitMessage(cppLocationParser.getError().createMessage(location.getComponent().createMessageLocation()));
        		throw cppLocationParser.getError();
        	}
            return null;
        }

        // make a new location for each segment
        CppLocation loc = null;
        if (segments.size() > 0) {
            for (Iterator iter = segments.iterator(); iter.hasNext();) {
                ICppLocationSegment segment = (ICppLocationSegment) iter.next();
                loc = new CppLocation(domain, parent, location, dir, file, segment);
                parent = loc;
            }
        } else {
            loc = new CppLocation(domain, parent, location, dir, file, null);
        }
        return loc;
    }

    /**
     *  Create a location reference for the C/C++ domain. 
     *  <p>
     * @param domain resolved domain
     * @param dir resolved directory
     * @param file resolved file
     * @param fullPath resolved full path from root
     * @return an ILocation or null if the location is not valid
     *  (message reported) 
     */
    static public CppLocation createLocation(CppDomain domain,
    		String dir, String file, String fullPath) throws SourceGenException {
        Check.checkArg(domain);
        Check.checkArg(dir);
        Check.checkArg(file);
        
        ILocation parent = new RootLocation();
        
        CppLocationParser cppLocationParser = new CppLocationParser();
        List segments = cppLocationParser.parse(fullPath, parent);
        if (segments == null) {
            //throw new CoreException(cppLocationParser.getError().createStatus());
        	throw cppLocationParser.getError();
        }

        // make a new location for each segment
        CppLocation loc = null;
        if (segments.size() > 0) {
            for (Iterator iter = segments.iterator(); iter.hasNext();) {
                ICppLocationSegment segment = (ICppLocationSegment) iter.next();
                loc = new CppLocation(domain, parent, null, dir, file, segment);
                parent = loc;
            }
        } else {
            loc = new CppLocation(domain, parent, null, dir, file, null);
        }
        return loc;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof CppLocation) {
    		CppLocation other = (CppLocation) obj;
    		return (other.domain.equals(domain)
    				&& other.path.equals(path)
    				&& other.locationPath.equals(locationPath));
    	}
    	return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "<"+path+"@"+locationPath+"; state="+state+ ">"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getDomain()
     */
    public IDomain getDomain() {
        return domain;
    }


    private ISourceManipulator getSource() {
        if (sfi_ == null) {
            // all CppLocations are in one file
            sfi_ = domain.sourceManipulatorProvider.getSourceManipulator(getPath());
        }
        return sfi_;
    }
    
    public void addChildLocation(ILocation loc) {
        Check.checkState(loc.getParent() == this);
        if (childLocations == Collections.EMPTY_LIST)
        	childLocations = new ArrayList<ILocation>();
        childLocations.add(loc);
    }

    private CppLocation(CppDomain domain, ILocation parent, 
            ISourceGenLocation location, 
            String dir, String file, 
            ICppLocationSegment segment) {
    	super(parent, dir, file, 
    			location != null ? location.isOwned() : false);
    	Check.checkArg(domain);
    	this.domain = domain;
        this.sourceGenLocation = location;
        this.sourceGenLocations = null;
        if (parent != null && parent.getLocationPath() != null)
            this.locationPath = parent.getLocationPath();
        else
            this.locationPath = ""; //$NON-NLS-1$
        if (segment != null) {
            if (this.locationPath.length() > 0)
                this.locationPath += "/"; //$NON-NLS-1$
            this.locationPath += segment.getSegment();
        }
        this.segment = segment;
        this.state = S_UNKNOWN;
        this.range = null;
        this.filter = selectFilter(sourceGenLocation != null ? sourceGenLocation.getFilter() : null);
    }

    
    /**
     * Select the filtering function
     * @param filter
     * @return a filter, or null
     */
    private IContributionFilter selectFilter(String filter) {
        if (filter == null)
            return null;
        else if (filter.equals(FILTER_ACCEPT_ALL))
            return new IContributionFilter() {
                public boolean acceptContribution(IContribution contrib) {
                    return true;
                }

                public void reset() {
                } 
            };
        else if (filter.equals(FILTER_REJECT_ALL))
            return new IContributionFilter() {
                public boolean acceptContribution(IContribution contrib) {
                    return false;
                }

                public void reset() {
                }
            };
        else if (filter.equals(FILTER_UNIQUE_PROTOTYPES)) {
        	return new UniquePrototypeContributionFilter(this);
        } 
        else if (filter.equals(FILTER_UNIQUE_BASES)) {
        	return new UniqueBaseClassContributionFilter(this);
        } 
        else if (filter.equals(FILTER_UNIQUE_INCLUDES)) {
        	return new UniqueIncludesContributionFilter(this);
        } 
        else {
            Messages.emit(IStatus.ERROR,
                    sourceGenLocation.createMessageLocation(),
                    Messages.getString("CppLocation.UnknownFilter"), //$NON-NLS-1$
                    new Object[] { filter, locationPath });
            return null;
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getPrimarySourceGenLocation()
     */
    public ISourceGenLocation getPrimarySourceGenLocation() {
        return sourceGenLocation;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getSourceGenLocations()
     */
    public ISourceGenLocation[] getSourceGenLocations() {
    	if (sourceGenLocations == null)
    		return new ISourceGenLocation[] { sourceGenLocation };
    	return (ISourceGenLocation[]) sourceGenLocations.toArray(new ISourceGenLocation[sourceGenLocations.size()]);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#addSourceGenLocation(com.nokia.sdt.sourcegen.ISourceGenLocation)
     */
    public void addSourceGenLocation(ISourceGenLocation sloc) {
    	if (sourceGenLocation == sloc)
    		return;
    	
    	if (sourceGenLocations == null) {
    		sourceGenLocations = new ArrayList<ISourceGenLocation>(1);
    		sourceGenLocations.add(sourceGenLocation);
    		sourceGenLocations.add(sloc);
    	} else {
	    	for (Iterator iter = sourceGenLocations.iterator(); iter.hasNext();) {
				ISourceGenLocation s = (ISourceGenLocation) iter.next();
				if (s.getId().equals(sloc.getId())
						&& s.getComponentId().equals(sloc.getComponentId()))
					return;
			}
	    	sourceGenLocations.add(sloc);
    	}
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getState()
     */
    public int getState() {
        if (state == S_UNKNOWN) {
            // see if it resolves to anything in the tree
            cachedRange = false;
            //insertOffset = 0;
            
            // reset to force the next call to work
            range = null;
            range = getCodeRange();
            if (range != null) {
                state = S_RESOLVED;
            } else {
                state = S_MISSING;
            }
        }
        return state;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#setState(int)
     */
    public void setState(int state) {
        this.state = state;
        if (state == S_UNKNOWN || state == S_QUEUED) {
            cachedRange = false;
            range = null;
            sfi_ = null;
            //insertOffset = 0;
        }
    }

    /**
     * Find the code range containing the span of this location
     * in the persisted disk buffer (by using the parse tree for
     * a cache).
     * @return code range, or null if location not found
     */
    public CodeRange getCodeRange() {
        if (cachedRange)
            return this.range;
        
            
        // get situated relative to our parent
        if (!(parent instanceof CppLocation)) {
        	// we're the file, so we need to get the text and tree first
        	
            ISourceManipulator sfi = getSource();
            char[] text = sfi.getPersistedText();
            if (text == null) {
                try {
                    if (!sfi.exists())
                        return null;
                    sfi.load();
                } catch (CoreException e) {
                }
                text = sfi.getPersistedText();
                if (text == null)
                    return null;
            }

            if (sfi.getParseTree() != null) {
                range = new CodeRange(text, sfi.getParseTree(), sfi.getTranslationUnit(), 0, text.length);
                //insertOffset = text.length;	
            }
        } else if (parent instanceof CppLocation) {
        	// inherit text and range from parent
            range = ((CppLocation) parent).getCodeRange();
            //insertOffset = ((CppLocation) parent).getInsertOffset(); 
        } 
        
        if (range == null) {
            cachedRange = true;
            //insertOffset = 0;
            return null;
        }
        
        // now restrict our range to our location
        CodeRange parentRange = range;
        if (segment != null)
            range = segment.getChildRange(parentRange); 

        //insertOffset = seg.getInsertPosition(text, range);
        cachedRange = true;
        return range;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getIndentLevel()
     */
    public int getIndentLevel() {
        int level = 0;
        if (parent instanceof CppLocation)
            level = ((CppLocation) parent).getIndentLevel();
        
        if (segment == null)
            return level;
        
        if (segment.getType() == ICppLocationSegment.L_TO_FILE) {
            level = 0;
        } else if (segment.getType() != ICppLocationSegment.L_REGION
                && segment.getType() != ICppLocationSegment.L_BASES) {
            level++;
        }
        return level;
    }

    /**
     * Get the file-relative offset where new contributions should be inserted
     * into this location.
     * <p>
     * This should only be called if the state is known.
     * @return file offset
     */
    public int getInsertOffset() {
        // any text yet?
        char[] text = getSource().getPersistedText();
        if (text == null)
            return 0;
        
        // does our range exist yet?
        if (range == null) {
            // no, get the insert offset of the parent
            if (!(parent instanceof CppLocation))
                return text.length;
            CppLocation parentLoc = (CppLocation) parent;
            return parentLoc.getInsertOffset(text, segment);
        }
            
        // else, get the insert offset in our range
        Check.checkState(this.state != S_UNKNOWN && range != null);
        //if (!cachedRange)
        //    getCodeRange();
        if (segment == null)
            return text.length;
        
        return segment.getInsertPosition(text, range, null);
    }
 
    /**
     * Determine where to insert another location into this one
     * @param text
     * @param childSegment
     * @return file offset
     */
    private int getInsertOffset(char[] text, ICppLocationSegment childSegment) {
        if (segment == null || range == null)
            return getInsertOffset();
        return segment.getInsertPosition(text, range, childSegment);
    }

    /**
     * Get the comment indicating the region is not owned
     * @param 

     * @return string (never null)
     */
    public String getComment(boolean owned, boolean header) {
        if (segment == null)
            return ""; //$NON-NLS-1$
        return segment.getComment(this, owned, header);
    }

    /**
     * @return location for file created by this
     */
    public MessageLocation createMessageLocation() {
        IPath fullPath;
        if (domain.project != null) {
            fullPath = domain.project.getLocation().append(path);
        } else {
            fullPath = path;
        }
        int line = 0;
        int column = 0;
        // TODO: resolve offset to line/column
        //CodeRange range = getCodeRange();
        return new MessageLocation(fullPath, line, column); 
    }

    public ICppLocationSegment getSegment() {
        return segment;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#compareTo(com.nokia.sdt.sourcegen.contributions.ILocation)
     */
    public int compareTo(ILocation other) {
    	if (!(other instanceof CppLocation))
    		return UNCOMPARABLE;
    	CppLocation cother = (CppLocation) other;
    	CodeRange myRange = getCodeRange();
    	CodeRange otherRange = cother.getCodeRange();
    	if (myRange != null) {
    		if (otherRange != null) {
    			int diff = myRange.getOffset() - otherRange.getOffset();
    			if (diff != 0)
    				return diff;
    			return myRange.getEndOffset() - otherRange.getEndOffset();
    		}
    		return -1;
    	} else {
    		if (otherRange != null)
    			return 1;
    		return UNCOMPARABLE;
    	}
    }
}
