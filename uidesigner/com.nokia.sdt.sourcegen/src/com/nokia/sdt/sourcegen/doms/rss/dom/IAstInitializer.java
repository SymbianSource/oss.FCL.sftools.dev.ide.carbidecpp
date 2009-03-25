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

package com.nokia.sdt.sourcegen.doms.rss.dom;

/**
 * Base interface for an initializer.
 * 
 * This contains a source range that includes the "=" sign.
 * (though it needn't necessary <i>start</i> at '=').
 * 
 * 
 *
 */
public interface IAstInitializer extends IAstNode {
    public static final IAstInitializer EMPTY_ARRAY[] = new IAstInitializer[0];
}
