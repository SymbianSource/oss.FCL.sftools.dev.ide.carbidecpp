/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class ASTTranslationUnit extends ASTNode implements IASTTranslationUnit {

	private IASTListNode<IASTTopLevelNode> nodes;
	private List<ITranslationUnitListener> listeners = new ArrayList<ITranslationUnitListener>();
	private IDocument mainDocument;
	private IPath mainLocation;

	public ASTTranslationUnit(IASTListNode<? extends IASTTopLevelNode> nodes) {
		setNodes((IASTListNode<IASTTopLevelNode>)nodes);
		dirty = false;
	}
	
	public ASTTranslationUnit(ASTTranslationUnit unit) {
		super(unit);
		setMainDocument(unit.getMainDocument());
		setMainLocation(unit.getMainLocation());
		setNodes((IASTListNode<IASTTopLevelNode>) unit.getNodes().copy());
		dirty = unit.dirty;
		
		// set up ordering again
		IASTTopLevelNode prev = null;
		for (IASTTopLevelNode node : nodes) {
			if (prev != null) {
				prev.setNext(node);
			}
			prev = node;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTTranslationUnit))
			return false;
		if (!super.equalValue(obj))
			return false;

		// note: the doc and location are not part of the value
		ASTTranslationUnit node = (ASTTranslationUnit) obj;
		return node.nodes.equalValue(nodes);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// note: the doc and location are not part of this
		return super.hashCode() ^ nodes.hashCode() ^ 0x1211;
	}

	public IDocument getMainDocument() {
		return mainDocument;
	}
	
	public IPath getMainLocation() {
		return mainLocation;
	}
	
	public void setMainDocument(IDocument document) {
		this.mainDocument = document;
		fireChanged();
		dirty = true;
	}

	public void setMainLocation(IPath location) {
		this.mainLocation = location;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#getNodes()
	 */
	public IASTListNode<IASTTopLevelNode> getNodes() {
		return nodes;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#setNodes(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setNodes(IASTListNode<IASTTopLevelNode> nodes) {
		Check.checkArg(nodes);
		unparent(this.nodes);
		parent(nodes);
		this.nodes = nodes;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#addListener(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener)
	 */
	public void addListener(ITranslationUnitListener listener) {
		Check.checkArg(listener);
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#removeListener(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener)
	 */
	public void removeListener(ITranslationUnitListener listener) {
		listeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#fireNodeChanged(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public void fireNodeChanged(IASTNode node) {
		for (ITranslationUnitListener listener : listeners) {
			listener.changedNode(node);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#fireLiteralTextExpressionChanged(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression, java.lang.String, java.lang.String)
	 */
	public void fireLiteralTextNodeChanged(IASTLiteralTextNode node, String oldText, String newText) {
		for (ITranslationUnitListener listener : listeners) {
			listener.changedNode(node);
			//listener.changedLiteralTextNode(node, oldText, newText);
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#fireListNodeAdded(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, int)
	 */
	public void fireListNodeAdded(IASTListNode<? extends IASTNode> list, IASTNode node, int index) {
		for (ITranslationUnitListener listener : listeners) {
			listener.changedNode(list);
			//listener.addedListItem(list, node, index);
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit#fireListNodeRemoved(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, int)
	 */
	public void fireListNodeRemoved(IASTListNode<? extends IASTNode> list, IASTNode node,
			int index) {
		for (ITranslationUnitListener listener : listeners) {
			listener.changedNode(list);
			//listener.removedListItem(list, node, index);
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { nodes };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTTranslationUnit(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(nodes);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPTranslationUnit#findFirstStatementOfType(java.lang.Class)
	 */
	public IASTStatement findFirstStatementOfType(final Class<? extends IASTStatement> klazz) {
		final IASTStatement[] stmt = new IASTStatement[1];
		accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				if (klazz.isInstance(node)) {
					stmt[0] = (IASTStatement) node;
					return VISIT_ABORT;
				}
				return VISIT_CHILDREN;
			}
		});
		return stmt[0];
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPTranslationUnit#findStatementsOfType(java.lang.Class)
	 */
	public IASTStatement[] findStatementsOfType(final Class<? extends IASTStatement> klazz) {
		final List<IASTStatement> statements = new ArrayList<IASTStatement>();
		accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				if (klazz.isInstance(node)) {
					statements.add((IASTStatement) node);
				}
				return VISIT_CHILDREN;
			}
		});
		return (IASTStatement[]) statements.toArray(new IASTStatement[statements.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPTranslationUnit#findFirstStatementOfType(java.lang.String)
	 */
	public IASTStatement findFirstStatementOfType(String keywordPattern) {
		final Pattern pattern = Pattern.compile(keywordPattern);
		final IASTStatement[] stmt = new IASTStatement[1];
		accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				if (node instanceof IASTStatement) {
					String keywordName = ((IASTStatement) node).getKeywordName();
					if (keywordName != null && pattern.matcher(keywordName.toUpperCase()).matches()) {
						stmt[0] = (IASTStatement) node;
						return VISIT_ABORT;
					}
				}
				return VISIT_CHILDREN;
			}
		});
		return stmt[0];
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPTranslationUnit#findStatementsOfType(java.lang.String)
	 */
	public IASTStatement[] findStatementsOfType(String keywordPattern) {
		final Pattern pattern = Pattern.compile(keywordPattern);
		final List<IASTStatement> statements = new ArrayList<IASTStatement>();
		accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				if (node instanceof IASTStatement) {
					String keywordName = ((IASTStatement) node).getKeywordName();
					if (keywordName != null && pattern.matcher(keywordName.toUpperCase()).matches()) {
						statements.add((IASTStatement) node);
					}
				}
				return VISIT_CHILDREN;
			}
		});
		return (IASTStatement[]) statements.toArray(new IASTStatement[statements.size()]);
	}
}
