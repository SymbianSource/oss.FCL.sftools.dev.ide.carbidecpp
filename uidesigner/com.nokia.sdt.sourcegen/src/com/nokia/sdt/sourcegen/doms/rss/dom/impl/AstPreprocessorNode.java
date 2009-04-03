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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorNode;

/**
 * 
 *
 */
abstract public class AstPreprocessorNode extends AstNode implements
        IAstPreprocessorNode {
    /**
     * 
     */
    public AstPreprocessorNode() {
        super();
    }
    

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#toString()
     */
    @Override
    public String toString() {
    	return getClass().getSimpleName() + ": '" + getNewText(null) + "'"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
