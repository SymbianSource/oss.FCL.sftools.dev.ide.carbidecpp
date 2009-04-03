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

import java.util.List;
import java.util.Set;

/**
 * Abstract the method by which we get contributions for an instance
 * (mainly for testing)
 * 
 *
 */
public interface IContributionGatherer {
    /** Generate the contributions for this frame and children */
    List generate(IContributionContext frame);

    /** Generate the contributions that generate this location */
    List generateLocation(IContributionContext frame, String locationId);
    
    /** Get the dead location list */
    Set<ILocation> getDeadLocations();
}
