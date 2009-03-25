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

import com.nokia.sdt.sourcegen.core.ISourceFile;

/**
 * Implement this interface and pass it to IAstNode#getCurrentText()
 * to track the progress of source updating.
 * 
 * 
 *
 */
public interface ISourceUpdatingMonitor {
    public void usingOriginalTextForNode(IAstNode node);
    public void rewritingTextForNode(IAstNode node);
    public void modifyingFileContents(ISourceFile file);
}
