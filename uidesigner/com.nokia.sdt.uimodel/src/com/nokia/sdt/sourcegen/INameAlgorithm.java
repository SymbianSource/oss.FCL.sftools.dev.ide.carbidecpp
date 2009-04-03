/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;

/**
 * This interface captures behaviors provided by the
 * "name algorithm" used in mapEnum elements.<p>
 * These are registered as extensions for the com.nokia.sdt.sourcegen.nameAlgorithm
 * extension point.
 * 
 *
 */
public interface INameAlgorithm {
    final static String NAME_ALGORITHM_EXTENSION_POINT = "com.nokia.sdt.sourcegen.nameAlgorithm"; //$NON-NLS-1$

	/** Generate the name for an enum declaration; 
     * this will be cleaned up and uniquified (if necessary) afterwards
     * @param instance the instance with which to associate the enumerator
     * @param containerName the name of the top-level container to include in the enum
     * @param instanceName the name of the instance (possibly modified)
     * @param propertyId the base property which owns the enumerator (NOT a path)
     */
    public String getEnumDeclarationName(IComponentInstance instance, String containerName, String instanceName, String propertyId);
    
    /** Generate the name for an enumerator; 
     * this will be cleaned up and uniquified (if necessary) afterwards 
     * @param instance the instance with which to associate the enumerator
     * @param containerName the name of the top-level container to include in the enum
     * @param instanceName the name of the instance (possibly modified)
     * @param propertyId the base property which owns the enumerator (NOT a path)
     */
    public String getEnumeratorName(IComponentInstance instance, String containerName, String instanceName, String propertyId);

    /**
     * Generate the initial value for an enumerator (e.g. "0x6000").  If this
     * returns <code>null</code>, then {@link #getEnumeratorValue(IComponentInstance, String)} is used 
     * instead.
     * @param instance the instance with which to associate the enumerator
     * @param propertyId the base property which owns the enumerator (NOT a path)
     * @return value of enumerator (literal, symbol, expression, or <code>null</code>)
     */
    public String getInitialEnumeratorValue(IComponentInstance instance, String propertyId);
    
    /**
     * Generate the value for an enumerator (e.g. "0x6000"), either an exact
     * value, a reference to a previous enumerator, or <code>null</code> for the
     * default value.
     * @param instance the instance with which to associate the enumerator
     * @param propertyId the base property which owns the enumerator (NOT a path)
     * @return value of enumerator (literal, symbol, expression, or <code>null</code>)
     */
    public String getEnumeratorValue(IComponentInstance instance, String propertyId);
}
