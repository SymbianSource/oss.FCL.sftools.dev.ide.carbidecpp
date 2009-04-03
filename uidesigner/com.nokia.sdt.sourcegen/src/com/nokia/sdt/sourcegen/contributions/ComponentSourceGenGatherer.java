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

import com.nokia.sdt.sourcegen.IContributionContext;

import java.util.*;

/**
 * 
 *
 */
public class ComponentSourceGenGatherer implements IContributionGatherer {

	private Set<ILocation> deadLocations;

    public ComponentSourceGenGatherer() {
    	this.deadLocations = new HashSet<ILocation>();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContributionGatherer#generate(com.nokia.sdt.sourcegen.contributions.StackFrame)
     */
    public List generate(IContributionContext frame) {
        return frame.getComponentSourceGen().generate(frame);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContributionGatherer#generateLocation(com.nokia.sdt.datamodel.adapter.IComponentInstance, java.lang.String, java.lang.String)
     */
    public List generateLocation(IContributionContext frame, String locationId) {
        return frame.getComponentSourceGen().generateLocation(frame, locationId);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContributionGatherer#getDeadLocations()
     */
    public Set<ILocation> getDeadLocations() {
    	return deadLocations;
    }
}
