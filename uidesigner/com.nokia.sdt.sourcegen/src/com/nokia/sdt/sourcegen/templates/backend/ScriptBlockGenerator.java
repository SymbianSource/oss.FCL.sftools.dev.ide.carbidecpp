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

import com.nokia.sdt.sourcegen.templates.frontend.BlockNode;
import com.nokia.sdt.sourcegen.templates.frontend.TextNode;

import java.util.List;

public class ScriptBlockGenerator extends BlockGenerator {
    
    public ScriptBlockGenerator(TextChunkBackEnd backend) {
        super(backend);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.backend.BlockGenerator#generate(com.nokia.sdt.sourcegen.templates.frontend.Node)
     */
    public void visitBlockNode(BlockNode node) {
        List sub;
        if (node.getType() == BlockNode.BLOCK_EXPR) {
            sub = new ScriptExprBlockGenerator(backend).generate(node.getChildren());
        }
        else if (node.getType() == BlockNode.BLOCK_SCRIPT) {
            // redundant
            sub = new ScriptBlockGenerator(backend).generate(node.getChildren());
        }
        else if (node.getType() == BlockNode.BLOCK_RAW) {
            backend.error("BackEnd.InvalidBlockSwitch", node); //$NON-NLS-1$
            return;
        } else
            throw new IllegalStateException();
        
        chunks.addAll(sub);        
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.NodeVisitor#visitTextNode(com.nokia.sdt.sourcegen.templates.frontend.TextNode)
     */
    public void visitTextNode(TextNode node) {
        chunks.add(new LiteralTextChunk(node.getText()));
    }
}
