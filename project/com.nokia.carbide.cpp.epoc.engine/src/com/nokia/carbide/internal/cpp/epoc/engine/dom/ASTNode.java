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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter.NodeRewriter;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTNode implements IASTNode {

	protected boolean dirty;
	private IASTNode parent;
	private Object id; // not part of equal-value or hash ???
	private ISourceRegion sourceRegion; // not part of equal-value or hash
	private IASTNode[] sourceNodes; // not part of equal-value or hash
	
	/**
	 * 
	 */
	public ASTNode() {
		this.dirty = false;
	}
	
	public ASTNode(ASTNode other) {
		sourceRegion = other.sourceRegion;
		dirty = other.dirty;
		id = other.id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//return getNewText() + " [hash="+hashCode()+"]";
		String text;
		if (sourceRegion != null) {
			text = sourceRegion + ":\n'" + getOriginalText() + "'"; //$NON-NLS-1$ //$NON-NLS-2$
			if (isDirtyTree())
				text += "\nbecomes '" + getNewText() + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
			text = "'" + getNewText() + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		return "("+getClass().getSimpleName() +")\n"+ text + " [hash="+hashCode()+"]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
	public void setId(Object id) {
		this.id = id;
		// does not dirty
	}

	public Object getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ASTNode))
			return false;
		ASTNode node = (ASTNode) obj;
		if (ObjectUtils.equals(node.sourceRegion, sourceRegion)) {
			if (equalValue((IASTNode) obj))
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTNode))
			return false;
		if (obj == this)
			return true;
		//ASTNode node = (ASTNode) obj;
		return true;
	}

	/**
	 * Compare equality, allowing for null.
	 * @param a
	 * @param b
	 * @return true if both are null or both are non-null and #equalValue()
	 */
	protected boolean equalValue(IASTNode a, IASTNode b) {
		if (a != null) {
			if (b != null)
				return a.equalValue(b);
			else
				return false;
		} else {
			if (b == null)
				return true;
			else
				return false;
		}
	}
	
	protected int hashCodeOr0(Object obj) {
		if (obj == null)
			return 0;
		return obj.hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 0;
		//return hashCodeOr0(document) ^ hashCodeOr0(location) ^ hashCodeOr0(region) ^ 0x30481833;
			//hashCodeOr0(document) ^ hashCodeOr0(location) ^ hashCodeOr0(region) ^ 0x30481833;
			// equals() will return the same hash code, but don't use this stuff
	}

	protected void unparent(IASTNode child) {
		if (child != null)
			child.setParent(null);
	}
	protected void parent(IASTNode child) {
		if (child == null)
			return;
		Check.checkArg(child.getParent() == null);
        child.setParent(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#isDirty()
	 */
	public boolean isDirty() {
		return dirty;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#setDirty(boolean)
	 */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	static class IsDirtyTreeVisitor implements IASTVisitor {

		boolean dirty;
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor#visit(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
		 */
		public int visit(IASTNode node) {
			dirty |= node.isDirty();
			if (dirty)
				return VISIT_ABORT;
			return VISIT_CHILDREN;
		}
	}

	static class SetDirtyTreeVisitor implements IASTVisitor {

		boolean dirty;
		
		public SetDirtyTreeVisitor(boolean dirty) {
			this.dirty = dirty;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor#visit(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
		 */
		public int visit(IASTNode node) {
			node.setDirty(dirty);
			return VISIT_CHILDREN;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#isDirtyTree()
	 */
	public boolean isDirtyTree() {
		IsDirtyTreeVisitor visitor = new IsDirtyTreeVisitor();
		accept(visitor);
		return visitor.dirty;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#setDirtyTree(boolean)
	 */
	public void setDirtyTree(boolean dirty) {
		SetDirtyTreeVisitor visitor = new SetDirtyTreeVisitor(dirty);
		accept(visitor);
	}
	
	static class HasDirtySourceTreeVisitor implements IASTVisitor {

		boolean dirty;
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor#visit(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
		 */
		public int visit(IASTNode node) {
			dirty |= node.hasDirtySource();
			if (dirty)
				return VISIT_ABORT;
			return VISIT_CHILDREN;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#hasDirtySource()
	 */
	public boolean hasDirtySource() {
		return dirty || sourceRegion == null;
	}
	
	public boolean hasDirtySourceTree() {
		HasDirtySourceTreeVisitor visitor = new HasDirtySourceTreeVisitor();
		accept(visitor);
		return visitor.dirty;
	}

	

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#accept(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor)
	 */
	public int accept(IASTVisitor visitor) {
		int ret = visitor.visit(this);
		if (ret == IASTVisitor.VISIT_ABORT)
			return ret;
		if (ret == IASTVisitor.VISIT_SIBLINGS)
			return ret;
		IASTNode[] kids = getChildren();
		for (IASTNode node : kids) {
			ret = node.accept(visitor);
			if (ret == IASTVisitor.VISIT_ABORT)
				return ret;
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getTranslationUnit()
	 */
	public IASTTranslationUnit getTranslationUnit() {
		IASTNode node = this;
		while (node != null && !(node instanceof IASTTranslationUnit)) {
			node = (IASTNode) node.getParent();
		}
		return (IASTTranslationUnit) node;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.dom.ast.IASTNode#getParent()
	 */
	public IASTNode getParent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.dom.ast.IASTNode#setParent(org.eclipse.cdt.core.dom.ast.IASTNode)
	 */
	public void setParent(IASTNode parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copySourceInfo(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public void copySourceInfo(IASTNode node) {
		if (node == null || node.getSourceRegion() == null) {
			sourceRegion = null;
		} else {
			sourceRegion = node.getSourceRegion().copy();
		}
	}
	
	protected void fireChanged() {
		IASTTranslationUnit tu = getTranslationUnit();
		if (tu != null) {
			tu.fireNodeChanged(this);
		}
	}
	
    /**
     */
    public static int parseIntValue(String val) {
        if (val.length() > 2 && val.startsWith("0b")) { //$NON-NLS-1$
            return Long.valueOf(val.substring(2), 2).intValue();
        } else if (val.length() > 2 && val.startsWith("0x")) { //$NON-NLS-1$
            return Long.valueOf(val.substring(2), 16).intValue();
        } else if (val.length() > 1 && val.startsWith("0")) { //$NON-NLS-1$
            return Long.valueOf(val.substring(1), 8).intValue();
        } else
            return Long.valueOf(val).intValue();
    }


	public String getNewText() {
		IRewriteHandler handler = new NodeRewriter();
		rewrite(handler);
		return handler.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getOriginalText()
	 */
	public String getOriginalText() {
		if (sourceRegion == null) {
			return null;
		}
		return sourceRegion.getText();
	}
	
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getMessageLocation()
	 */
	public MessageLocation getMessageLocation() {
		IASTNode node = this;
		while (node != null) {
			if (node.getSourceRegion() != null)
				break;
			node = node.getParent();
		}
		if (node == null) {
			return null;
		}
		return node.getSourceRegion().getMessageLocation();
	}
	
	public ISourceRegion getSourceRegion() {
		return sourceRegion;
	}
	
	public void setSourceRegion(ISourceRegion region) {
		this.sourceRegion = region;
	}

	public String getSourceReference() {
		IASTNode node = this;
		while (node != null) {
			if (node.getSourceRegion() != null)
				break;
			node = node.getParent();
		}
		if (node == null) {
			return Messages.getString("ASTNode.UnknownFileName"); //$NON-NLS-1$
		}
		return node.getSourceRegion().getSourceReference();
	}
	
	public IASTNode[] getSourceNodes() {
		return sourceNodes;
	}
	
	public void setSourceNodes(IASTNode[] nodes) {
		this.sourceNodes = nodes;
	}
}
