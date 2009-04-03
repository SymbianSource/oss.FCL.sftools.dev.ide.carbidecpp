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

package com.nokia.sdt.symbian;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.ISourceGenSession;

/**
 * This acts as a bridge between the generic sourcegen session
 * and the Symbian-specific knowledge shared between the source
 * gen provider and the data model implementation.
 * 
 * 
 *
 */
public interface ISymbianSourceGenSession extends ISourceGenSession {
    /** loc/lxx format file */
	public final int FORMAT_LOC = 1;
	/** rls format file */
	public final int FORMAT_RLS = 2;

	/** Get the name of the primary resource generated for the given instance.
     * <p>
     * This is the first unnamed resource for a component.  Use
     * global functions from script or SourceGenContext#getGeneratedInstanceResource(instance, rsrcId)
     * for more details.
     * @param instance the instance to query
     * @return the instance name or null if the instance provides
     * no resources or provides an unnamed resource
     */
    String getGeneratedResource(IComponentInstance instance);
    
    /** 
     * Get the RSS filename base for this component.  This has
     * no extension or directory.
     * <p>
     * @return filename base, i.e. "MyApp"
     */
    String getResourceFileNameBase();
}
