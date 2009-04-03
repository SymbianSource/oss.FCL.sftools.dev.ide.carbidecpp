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

package com.nokia.sdt.sourcegen.templates.backend;

import com.nokia.sdt.sourcegen.templates.frontend.TextNode;

public class RawBlockGenerator extends BlockGenerator {
 
    public RawBlockGenerator(TextChunkBackEnd backend) {
        super(backend);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.NodeVisitor#visitTextNode(com.nokia.sdt.sourcegen.templates.frontend.TextNode)
     */
    public void visitTextNode(TextNode node) {
        chunks.add(new RawTextChunk(node.getText()));

        // mirror newlines in script for readability
        //if (node.getText().indexOf("\n") != -1 || node.getText().indexOf("\r") != -1) //$NON-NLS-1$ //$NON-NLS-2$
        //    chunks.add(new ScriptTextChunk("\n")); //$NON-NLS-1$
    }
}
