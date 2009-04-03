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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentSourceLocation;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import java.util.ArrayList;
import java.util.List;


public class ConditionalBlock implements IConditionalBlock {

	private List<IASTNode> nodes;
	private List<IConditionalBlock> children;
	protected IConditionalBlock parent;
	private IASTNode end;
	private IASTNode start;
	private IASTPreprocessorStatement condition;
	private ISourceRegion span;
	private ISourceRegion fullSpan;
	private int ifDepth = -1;

	public ConditionalBlock() {
		this.condition = null;
		this.nodes = new ArrayList<IASTNode>();
		this.children = new ArrayList<IConditionalBlock>();
		this.parent = null;
	}
	
	/**
	 * Create a conditional block for the entire file.
	 * @param region 
	 * 
	 */
	public ConditionalBlock(ISourceRegion region) {
		this();
		if (region != null) {
			this.span = region.copy();
			this.fullSpan = region.copy();
		}
	}
	/**
	 * Create a conditional block for the entire file.
	 * @param region 
	 * 
	 */
	public ConditionalBlock(IDocument document, IPath location) {
		this();
		if (document != null) {
			IRegion region = new Region(0, document.getLength());
			this.span = new DocumentSourceLocation(document, location, region);
			this.fullSpan = new DocumentSourceLocation(document, location, region);
		}
	}
	
	/**
	 * 
	 */
	public ConditionalBlock(IASTPreprocessorStatement condition) {
		this();
		this.condition = condition;
	}
	
	/**
	 * @param children 
	 * @param condition2
	 * @param nodes2
	 */
	public ConditionalBlock(IASTPreprocessorStatement condition, 
			IASTNode start, IASTNode[] nodes, IASTNode end,
			IConditionalBlock[] children) {
		this(condition);
		this.start = start;
		this.end = end;
		if (nodes != null) {
			for (IASTNode node : nodes)
				this.nodes.add(node);
		}
		if (children != null) { 
			for (IConditionalBlock child : children) {
				this.children.add(child);
				child.setParent(this);
			}
		}
	}

	/**
	 * @param children 
	 * @param condition2
	 * @param nodes2
	 */
	public ConditionalBlock(IASTPreprocessorStatement condition, 
			IASTNode[] nodes, 
			IConditionalBlock[] children) {
		this(condition, null, nodes, null, children);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "region" + (condition!= null?": "+condition:"") + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		 " = " + (getSpan()!= null ? span : "<no span>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/* (non-Javadoc)
	 * @see src.view.IViewRegion#getCondition()
	 */
	public IASTPreprocessorStatement getCondition() {
		return condition;
	}
	
	/* (non-Javadoc)
	 * @see src.view.IViewRegion#addChild(src.view.IViewRegion)
	 */
	public void addChild(IConditionalBlock block) {
		children.add(block);
		block.setParent(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#removeChild(com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion)
	 */
	public void removeChild(IConditionalBlock block) {
		children.remove(block);
		block.setParent(null);
	}

	/* (non-Javadoc)
	 * @see src.view.IViewRegion#getChildren()
	 */
	public IConditionalBlock[] getChildren() {
		return (IConditionalBlock[]) children.toArray(new IConditionalBlock[children.size()]);
	}

	/* (non-Javadoc)
	 * @see src.view.IViewRegion#getNodes()
	 */
	public IASTNode[] getNodes() {
		return (IASTNode[]) nodes.toArray(new IASTNode[nodes.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#addNode(src.dom.IASTNode)
	 */
	public void addNode(IASTNode node) {
		nodes.add(node);
	}
	/* (non-Javadoc)
	 * @see src.view.IViewRegion#getParent()
	 */
	public IConditionalBlock getParent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see src.view.IViewRegion#setParent(src.view.IViewRegion)
	 */
	public void setParent(IConditionalBlock block) {
		// cannot change hierarchy -- if this is ever allowed, rethink #getIfDepth()
		Check.checkArg(parent == null || block == null);
		this.parent = block;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#reset()
	 */
	public void clear() {
		this.condition = null;
		this.nodes.clear();
		this.children.clear();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#getStartNode()
	 */
	public IASTNode getStartNode() {
		return start;
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#getEndNode()
	 */
	public IASTNode getEndNode() {
		return end;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#getStartNode()
	 */
	public void setStartNode(IASTNode start) {
		this.start = start;
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#getEndNode()
	 */
	public void setEndNode(IASTNode end) {
		this.end = end;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#accept(com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegionVisitor)
	 */
	public int accept(IConditionalBlockVisitor visitor) {
		int ret = visitor.visit(this);
		if (ret == IConditionalBlockVisitor.VISIT_ABORT)
			return ret;
		if (ret == IConditionalBlockVisitor.VISIT_SIBLINGS)
			return ret;
		for (IConditionalBlock block : children) {
			ret = block.accept(visitor);
			if (ret == IConditionalBlockVisitor.VISIT_ABORT)
				return ret;
		}
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#clearNodes()
	 */
	public void clearNodes() {
		nodes.clear();
	}
	
	/**
	 * Get the unfiltered list of regions contributing the block.
	 * This includes the ranges of child nodes and child blocks.
	 * @param includeBookends
	 * @return
	 */
	private ISourceRegion[] getNodeSourceRegionArray(boolean includeBookends) {
		List<ISourceRegion> regions = new ArrayList<ISourceRegion>(
				2 + (nodes != null ? nodes.size() : 0));
		ISourceRegion encStart = start != null ? start.getSourceRegion() : null;
		ISourceRegion encEnd = end != null ? end.getSourceRegion() : null;
		if (!includeBookends) {
			encStart = encStart != null ? encStart.getExclusiveTailRegion() : null;
			encEnd = encEnd != null ? encEnd.getExclusiveHeadRegion() : null;
		}
		if (encStart != null) {
			regions.add(encStart);
		}
		
		for (IConditionalBlock block : children) {
			regions.add(block.getFullSpan());
		}
		for (IASTNode node : nodes) {
			ISourceRegion sourceRegion = node.getSourceRegion();
			if (sourceRegion != null)
				regions.add(sourceRegion);
		}
		
		if (encEnd != null) {
			regions.add(encEnd);
		}
		
		return (ISourceRegion[]) regions.toArray(new ISourceRegion[regions.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock#getSpan()
	 */
	public ISourceRegion getSpan() {
		if (span == null) {
			ISourceRegion[] nodeArr = getNodeSourceRegionArray(false);
			span = ASTUtils.getEnclosingRegion(nodeArr);
			Check.checkState(span != null);
		}
		return span;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock#getFullSpan()
	 */
	public ISourceRegion getFullSpan() {
		if (fullSpan == null) {
			ISourceRegion[] nodeArr = getNodeSourceRegionArray(true);
			fullSpan = ASTUtils.getEnclosingRegion(nodeArr);
			Check.checkState(fullSpan != null);
		}
		return fullSpan;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IViewRegion#containsRegion(org.eclipse.jface.text.IRegion)
	 */
	public boolean containsRegion(ISourceRegion region) {
		return getSpan().contains(region);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock#isContainedInRegion(org.eclipse.jface.text.IRegion)
	 */
	public boolean isContainedInRegion(ISourceRegion region) {
		return region.contains(getFullSpan());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock#contains(com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock)
	 */
	public boolean contains(IConditionalBlock block) {
		if (block == this)
			return true;
		for (IConditionalBlock kid : children) {
			if (kid.contains(block))
				return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock#getDocument()
	 */
	public IDocument getFirstDocument() {
		ISourceRegion span = getSpan();
		return span.getFirstDocument();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock#getIfDepth()
	 */
	public int getIfDepth() {
		if (ifDepth == -1) {
			// calculate once, assuming the hierarchy doesn't change
			if (parent != null)
				ifDepth = parent.getIfDepth();
			else
				ifDepth = 0;
		}
		return ifDepth;
	}
}
