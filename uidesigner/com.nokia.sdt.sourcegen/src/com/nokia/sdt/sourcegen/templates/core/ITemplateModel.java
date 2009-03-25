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

package com.nokia.sdt.sourcegen.templates.core;

/**
 * This interface is used to access data from templates.
 * 
 * 
 *
 */
public interface ITemplateModel {
    /** Look up the value indicated by key, possibly null.
     * Use hasKey(String) to check for key existence.
     *  
     * @param key a model-specific name of a datum
     * @return value of datum
     */
    public Object getValue(Object key);
    
    /** Check for the existence of a key.
     * 
     * @param key
     * @return true if key exists.
     */
    public boolean hasKey(Object key);
}
