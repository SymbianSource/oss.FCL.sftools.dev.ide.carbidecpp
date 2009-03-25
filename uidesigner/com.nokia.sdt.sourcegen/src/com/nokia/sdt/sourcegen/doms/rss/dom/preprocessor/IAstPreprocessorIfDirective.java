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

package com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor;

/**
 * An #if directive.
 * <p> 
 * Like other preprocessor nodes, this is an atomic node.
 * It does not have any explicit structure with respect to
 * matching #else, #endif nodes except by ordering 
 * and arrangement in the owning translation unit.
 * <p>
 * The #isTaken() flag tells whether, in the original parse of
 * source, the "true" branch of the conditional was taken.
 * The value of this flag implies nothing about the nodes
 * "contained" between this directive and any matching #else or
 * #endif.    
 * 
 * 
 *
 */
public interface IAstPreprocessorIfDirective extends IAstPreprocessorTestDirective {
    /** Tell whether the branch was taken in the current tree */
    public boolean isTaken();
    
    /** Tell whether the #else is taken in the current tree */
    public void setTaken(boolean taken);
}
