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
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * This location is meant only to be used as the root of the ILocation tree.
 * Contributions cannot be added at this location.
 * 
 *
 */
public class RootLocation extends BaseLocation {
    /**
     * 
     */
    public RootLocation() {
    	super(null, null, null, false);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getDomain()
     */
    public IDomain getDomain() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getSourceGenLocation()
     */
    public ISourceGenLocation getPrimarySourceGenLocation() {
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#addSourceGenLocation(com.nokia.sdt.sourcegen.ISourceGenLocation)
     */
    public void addSourceGenLocation(ISourceGenLocation sloc) {
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getSourceGenLocations()
     */
    public ISourceGenLocation[] getSourceGenLocations() {
    	return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getState()
     */
    public int getState() {
        return S_RESOLVED;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#setState(int)
     */
    public void setState(int state) {
        Check.checkState(false);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.ILocation#getIndentLevel()
     */
    public int getIndentLevel() {
        return 0;
    }
    
    public int compareTo(ILocation other) {
    	return UNCOMPARABLE;
    }
}

