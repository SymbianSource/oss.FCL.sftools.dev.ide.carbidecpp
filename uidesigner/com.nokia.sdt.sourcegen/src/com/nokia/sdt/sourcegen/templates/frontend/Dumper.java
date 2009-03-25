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

package com.nokia.sdt.sourcegen.templates.frontend;

import java.io.PrintStream;

public class Dumper implements IDeepNodeVisitor {
    private PrintStream writer;
    private int indent;

    public Dumper(PrintStream writer) {
        this.writer = writer;
    }
    
    public void dump(Node nodes) {
        indent = 0;
        nodes.traverseDeep(this);
    }
    
    public void doIndent() {
        for (int i=0; i < indent; i++)
            writer.print('\t');
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.DeepNodeVisitor#visitBlockNode(com.nokia.sdt.sourcegen.templates.frontend.BlockNode, boolean)
     */
    public void visitBlockNode(BlockNode node, boolean open) {
        if (open) {
            doIndent();
            writer.print(node);
            writer.println(" ["); //$NON-NLS-1$
            indent++;
        }
        else {
            writer.println("]"); //$NON-NLS-1$
            indent--;
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.NodeVisitor#visitTextNode(com.nokia.sdt.sourcegen.templates.frontend.TextNode)
     */
    public void visitTextNode(TextNode node) {
        doIndent();
        writer.println(node);
    }
    
}
