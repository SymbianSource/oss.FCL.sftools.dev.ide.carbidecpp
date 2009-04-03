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

/** This class generates text by expanding expressions
 * inside a script block.  These expressions refer to
 * the parse-time state of the script.
 * 
 * 
 *
 */
public class ScriptExprBlockGenerator extends BlockGenerator {
    public ScriptExprBlockGenerator(TextChunkBackEnd backend) {
        super(backend);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.ShallowNodeVisitor#visitBlockNode(com.nokia.sdt.sourcegen.templates.frontend.BlockNode)
     */
    public void visitBlockNode(BlockNode node) {
        // an expression cannot contain any blocks
        backend.error("BackEnd.InvalidBlockSwitch", node); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.NodeVisitor#visitTextNode(com.nokia.sdt.sourcegen.templates.frontend.TextNode)
     */
    public void visitTextNode(TextNode node) {
        String value = (String) backend.lookup(node.getText());
        if (value == null) {
            backend.error("BackEnd.UnknownVariable", node); //$NON-NLS-1$
            chunks.add(new LiteralTextChunk(node.getText()));
        } else {
            chunks.add(new LiteralTextChunk(value.toString()));
        }
    }
}
