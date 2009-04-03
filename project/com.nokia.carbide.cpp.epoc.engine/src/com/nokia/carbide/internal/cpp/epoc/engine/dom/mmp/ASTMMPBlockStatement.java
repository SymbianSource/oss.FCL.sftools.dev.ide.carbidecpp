/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTMMPBlockStatement extends ASTMMPStatement implements
		IASTMMPBlockStatement {

	private IASTListNode<IASTMMPStatement> statements;

	/**
	 * @param keyword
	 */
	public ASTMMPBlockStatement(IASTListNode<IASTMMPStatement> statements) {
		super();
		setStatements(statements);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTMMPBlockStatement(ASTMMPBlockStatement statement) {
		super(statement);
		setStatements((IASTListNode<IASTMMPStatement>) statement.getStatements().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPBlockStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPBlockStatement node = (ASTMMPBlockStatement) obj;
		return node.statements.equalValue(statements);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ statements.hashCode() ^ 0x345343;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder#getList()
	 */
	public IASTListNode<IASTMMPStatement> getList() {
		return statements;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPBlockStatement#getStatements()
	 */
	public IASTListNode<IASTMMPStatement> getStatements() {
		return statements;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPBlockStatement#setStatements(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setStatements(IASTListNode<IASTMMPStatement> statements) {
		Check.checkArg(statements);
		unparent(this.statements);
		parent(statements);
		this.statements = statements;
		fireChanged();
		dirty = true;
	}
	
	protected void rewriteBlock(int level, IRewriteHandler handler) {
		//Check.checkState(level <= 4);
		//String indent = "\t\t\t\t".substring(0, level); //$NON-NLS-1$
		handler.indent(1);
		for (IASTMMPStatement statement : statements) {
			// only add indentation for new lines
			//if (statement.getRegion() == null)
			//	handler.emitText(indent);
			handler.emitNode(statement);
		}
		handler.indent(-1);
	}
	
	protected IASTNode[] makeChildListWith(IASTNode node) {
		IASTNode[] kids;
		if (node != null)
			kids = new IASTNode[] { statements, node };
		else
			kids = new IASTNode[] { statements };
		return kids;
	}

	protected IASTNode[] makeChildListWith(IASTNode node, IASTNode node2) {
		IASTNode[] kids;
		if (node != null && node2 != null)
			kids = new IASTNode[] { statements, node, node2 };
		else if (node != null)
			kids = new IASTNode[] { statements, node };
		else if (node2 != null)
			kids = new IASTNode[] { statements, node2 };
		else
			kids = new IASTNode[] { statements };
		return kids;
	}

	
}
