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

/**
 * This interface is used to inject text into a location.
 * 
 * 
 *
 */
public interface ISourceMerger {
    /** Get the location this applies to */
    ILocation getLocation();
    
    /** 
     * Merge text into the location.
     * This may be called several times, which inserts text
     * consecutively with no breaks.
     * @param text
     */
    void mergeText(String text);
    
    /**
     * Commit the merged text into the location.
     * This may modify the formatting of the text.
     * The merger and all other mergers are invalidated.
     */
    void commit();
}
