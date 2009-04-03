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


package com.nokia.sdt.sourcegen;

import com.nokia.sdt.component.adapter.IComponentAdapter;

import java.util.List;


/**
 * This is the interface implemented by the sourcegen adapter
 * for a given component, whereby source is generated from 
 * component instances.
 * 
 * 
 */
public interface IComponentSourceGen extends IComponentAdapter {
    /** 
     * Generate the contributions that define the given location
     * @param context the generation context
     * @param locationId the identifier for the location
     */
	List generateLocation(IContributionContext context, String locationId);
    
    /** 
     * Generate the contributions for this component
     * @param context the generation context
     * @return list of IContribution 
     */
	List generate(IContributionContext context);

    /**
     * Get the source generation info gathered
     * @return info
     */
    IComponentSourceGenInfo getSourceGenInfo();
    
    /**
     * Trim the working set, releasing data not strictly
     * necessary for further operation.  (For our purposes,
     * this frees script generated during the XML->Javascript
     * translation.)
     */
    void trimWorkingSet();
}
