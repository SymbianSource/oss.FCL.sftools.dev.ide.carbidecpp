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


/**
 * This interface is used to insert text into a location
 * 
 *
 */
public interface ITextInserter {
    ITextInserter NULL_INSERTER = new ITextInserter() {

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return "<NULL_INSERTER>"; //$NON-NLS-1$
        }
        
        public String getInsertText() {
            return ""; //$NON-NLS-1$
        } 
    };

    /** Get text to insert into the buffer  
     * @return new text
     */
    public String getInsertText();
}
