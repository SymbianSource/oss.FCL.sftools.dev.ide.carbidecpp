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

import com.nokia.sdt.sourcegen.templates.frontend.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This generator converts a Node tree to Chunks by traversing
 * the node tree and using the BlockNode type to create a
 * corresponding TextChunk type.
 *  
 * 
 * @see Node
 * @see TextChunk
 */
public abstract class BlockGenerator implements IShallowNodeVisitor {
    protected TextChunkBackEnd backend;
    List chunks;
    
    public BlockGenerator(TextChunkBackEnd backend) {
        this.backend = backend;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.javascript.IBlockGenerator#generate(com.nokia.sdt.sourcegen.templates.frontend.Node)
     */
    public List generate(Node node) {
        chunks = new ArrayList();
        node.traverseShallow(this);
        return chunks;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.ShallowNodeVisitor#visitBlockNode(com.nokia.sdt.sourcegen.templates.frontend.BlockNode)
     */
    public void visitBlockNode(BlockNode node) {
        List sub;
        if (node.getType() == BlockNode.BLOCK_EXPR) {
            sub = new ExprBlockGenerator(backend).generate(node.getChildren());
        }
        else if (node.getType() == BlockNode.BLOCK_SCRIPT) {
            sub = new ScriptBlockGenerator(backend).generate(node.getChildren());
        }
        else if (node.getType() == BlockNode.BLOCK_RAW) {
            sub = new RawBlockGenerator(backend).generate(node.getChildren());
        } else
            throw new IllegalStateException();
        
        chunks.addAll(sub);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.NodeVisitor#visitTextNode(com.nokia.sdt.sourcegen.templates.frontend.TextNode)
     */
    abstract public void visitTextNode(TextNode node);
}
