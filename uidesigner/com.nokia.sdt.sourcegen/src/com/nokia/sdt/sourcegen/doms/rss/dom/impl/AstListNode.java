/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 
 *
 */
public class AstListNode extends AstNode implements IAstListNode {
	List<IAstNode> items;
	private Class baseType;
	private ITextSegmentGenerator textSegmentGenerator;
	
	/**
	 * Create a list with the given base type and generator
	 * @param baseType class from which all contained nodes must be derived
	 * @param generator object handling generating text segments
	 */
	public AstListNode(Class baseType, ITextSegmentGenerator generator) {
		Check.checkArg(baseType);
		Check.checkArg(IAstNode.class.isAssignableFrom(baseType));
		this.items = new LinkedList<IAstNode>();
		this.baseType = baseType;
		setTextSegmentGenerator(generator);
		dirty = false;
	}

	public void setTextSegmentGenerator(ITextSegmentGenerator generator) {
		Check.checkArg(generator);
		this.textSegmentGenerator = generator;
		dirty = true;
	}

	/**
	 * Create a list with the given base type and the
	 * given text segments 
	 * @param baseType class from which all contained nodes must be derived
	 * @param perItemSegments text segments added to all items but the last
	 * @param lastItemSegments text segments added to last item
	 */
	public AstListNode(Class baseType, final Object[] perItemSegments, final Object[] lastItemSegments) {
		this(baseType, new ITextSegmentGenerator() {

			public List getTextSegments(IAstListNode listNode) {
				List segments = new ArrayList();
				for (Iterator iter = listNode.iterator(); iter.hasNext();) {
					IAstNode node = (IAstNode) iter.next();
					segments.add(node);
					if (iter.hasNext())
						segments.addAll(Arrays.asList(perItemSegments));
					else
						segments.addAll(Arrays.asList(lastItemSegments));
				}	
				return segments;
			}
			
		});
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			IAstNode node = (IAstNode) iter.next();
			buffer.append('[');
			buffer.append(node.toString());
			buffer.append(']');
			if (iter.hasNext())
				buffer.append(',');
		}
		return buffer.toString();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
	 */
	public Object[] getTextSegments() {
		return textSegmentGenerator.getTextSegments(this).toArray();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#getBaseItemType()
	 */
	public Class getBaseItemType() {
		return baseType;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#addItem(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
	 */
	public void addItem(IAstNode node) {
		Check.checkArg(node != null && baseType.isInstance(node));
		Check.checkArg(!items.contains(node));
		items.add(node);
		node.setParent(this);
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#removeItem(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
	 */
	public void removeItem(IAstNode node) {
		Check.checkArg(node != null && baseType.isInstance(node));
		Check.checkArg(items.contains(node));
		node.setParent(null);
		items.remove(node);
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#insertItem(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode, com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
	 */
	public void insertItem(IAstNode after, IAstNode node) {
		Check.checkArg(node != null && baseType.isInstance(node));
        Check.checkArg(!items.contains(node));
        if (after == null)
            items.add(0, node);
        else {
            int idx = items.indexOf(after);
            Check.checkArg(idx >= 0);
            items.add(idx + 1, node);
        }
        node.setParent(this);
        dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#insertBeforeFileNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode, com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
	 */
	public void insertBeforeFileNode(IAstNode node, IAstNode before) {
		Check.checkArg(node != null && baseType.isInstance(node));
        Check.checkArg(!items.contains(node));
        Check.checkArg(before == null || items.contains(before));
        if (before == null) {
        	items.add(node);
        } else {
            int index = items.indexOf(before);
            Check.checkArg(index != -1);
            items.add(index, node);
        }
        node.setParent(this);
        dirty = true;
		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#containsItem(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
	 */
	public boolean containsItem(IAstNode node) {
		Check.checkArg(node != null && baseType.isInstance(node));
		return items.contains(node);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#getLastItem()
	 */
	public IAstNode getLastItem() {
		if (items.size() > 0)
			return items.get(items.size() - 1);
		else
			return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#clearItems()
	 */
	public void clearItems() {
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			IAstNode node = (IAstNode) iter.next();
			node.setParent(null);
		}
		items.clear();
		dirty = true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#listIterator()
	 */
	public ListIterator listIterator() {
		return items.listIterator();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#iterator()
	 */
	public Iterator iterator() {
		return items.iterator();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
	 */
	public IAstNode[] getChildren() {
		return (IAstNode[]) items.toArray((IAstNode[])Array.newInstance(baseType, items.size()));
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
	 */
	public IAstNode[] getReferencedNodes() {
		return getChildren();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#copyItems()
	 */
	public List copyItems() {
		return new ArrayList(items);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#getFileNodeIndex(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
	 */
	public int indexOf(IAstNode node) {
		return items.indexOf(node);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#get(int)
	 */
	public IAstNode get(int index) {
		return items.get(index);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode#size()
	 */
	public int size() {
		return items.size();
	}
}


