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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ASTListNode<T extends IASTNode> extends ASTNode implements IASTListNode<T>, IASTListHolder<T> {

	private List<T> list;
	private String separator;

	public ASTListNode() {
		this.list = new ArrayList<T>();
	}

	public ASTListNode(ASTListNode<T> other) {
		super(other);
		this.separator = other.separator;
		this.list = new ArrayList<T>();
		IASTNode[] kids = other.getChildren();
		for (IASTNode node : kids) {
			add((T) node.copy());
		}
		dirty = other.dirty;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTNode#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (T item : list) {
			if (first)
				first = false;
			else
				builder.append(',');
			builder.append('\'');
			builder.append(item.getNewText());
			builder.append('\'');
		}
		return builder.toString();
	}
	
	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTListNode))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTListNode node = (ASTListNode) obj;
		Iterator<IASTNode> nodeIter = node.list.iterator();
		Iterator<T> iter = list.iterator();
		while (nodeIter.hasNext() && iter.hasNext()) {
			if (!nodeIter.next().equalValue(iter.next()))
				return false;
		}
		return nodeIter.hasNext() == iter.hasNext();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ list.hashCode() ^ 0x103;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return list.toArray(NO_CHILDREN);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTListNode<T>(this);
	}

	/* (non-Javadoc)
	 * @see java.util.List#size()
	 */
	public int size() {
		return list.size();
	}

	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return list.contains(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#iterator()
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Iterator<T> iter = list.iterator();
			T last;
			int index = -1;
			
			public boolean hasNext() {
				return iter.hasNext();
			}

			public T next() {
				index++;
				last = iter.next();
				return last;
			}

			public void remove() {
				iter.remove();
				unparent(last);
				fireListNodeRemoved(last, index);
				dirty = true;
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return list.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray(T[])
	 */
	@SuppressWarnings("hiding") //$NON-NLS-1$
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	/* (non-Javadoc)
	 * @see java.util.List#add(E)
	 */
	public boolean add(T o) {
		Check.checkArg(o);
		boolean ret = list.add(o);
		if (ret) {
			parent(o);
			fireListNodeAdded(o, list.size() - 1);
			dirty = true;
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index >= 0) {
			unparent((IASTNode)o);
			list.remove(index);
			fireListNodeRemoved((T) o, index);
			dirty = true;
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends T> c) {
		boolean ret = false;
		for (T item : c) {
			ret |= add(item);
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean changed = false;
		for (T item : c) {
			Check.checkArg(item);
			list.add(index, item);
			parent(item);
			fireListNodeAdded(item, index);
			dirty = true;
			index++;
			changed = true;
		}
		return changed;
	}

	/* (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object item : c) {
			ret |= remove((T) item);
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for (Object o : list) {
			if (!c.contains(o)) {
				changed |= remove(o);
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#clear()
	 */
	public void clear() {
		for (int index = 0; index < list.size(); index++) {
			T item = list.get(index);
			unparent(item);
			fireListNodeRemoved(item, index);
			dirty = true;
		}
		list.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	public T get(int index) {
		return list.get(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#set(int, E)
	 */
	public T set(int index, T element) {
		Check.checkArg(element);
		T old = list.set(index, element);
		if (old != element) {
			unparent(old);
			parent(element);
			fireListNodeRemoved(old, index);
			fireListNodeAdded(old, index);
			dirty = true;
		}
		return old;
	}

	/* (non-Javadoc)
	 * @see java.util.List#add(int, E)
	 */
	public void add(int index, T element) {
		Check.checkArg(element);
		list.add(index, element);
		parent(element);
		fireListNodeAdded(element, index);
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	public T remove(int index) {
		T old = list.remove(index);
		unparent(old);
		fireListNodeRemoved(old, index);
		dirty = true;
		return old;
	}

	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		// use reference equality first
		for (ListIterator<T> listIter = list.listIterator(); listIter.hasNext(); ) {
			if (listIter.next() == o)
				return listIter.nextIndex() - 1;
		}
		return list.indexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		// use reference equality first
		for (ListIterator<T> listIter = list.listIterator(list.size()); listIter.hasPrevious(); ) {
			if (listIter.previous() == o)
				return listIter.previousIndex() + 1;
		}
		return list.lastIndexOf(o);
	}

	   /**
     * This iterator wraps another iterator.
     * If the "remove" call is made, it marks the owning
     * node dirty.
     *
     */
    protected class MyListIterator<E extends IASTNode> implements ListIterator<E> {

    	private ListIterator<E> iterator;
    	private E item;
    	private int index;
    	
		public MyListIterator(ListIterator<E> iterator) {
			Check.checkArg(iterator);
			this.iterator = iterator;
			this.index = -1;
		}
		public MyListIterator(ListIterator<E> iterator, int index) {
			Check.checkArg(iterator);
			this.iterator = iterator;
			this.index = index - 1;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			index++;
			item = iterator.next();
			return item;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			iterator.remove();
			unparent(item);
			fireListNodeRemoved(item, index);
			index--;
			dirty = true;
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#hasPrevious()
		 */
		public boolean hasPrevious() {
			return iterator.hasPrevious();
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#previous()
		 */
		public E previous() {
			--index;
			item = iterator.previous();
			return item;
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#nextIndex()
		 */
		public int nextIndex() {
			return iterator.nextIndex();
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#previousIndex()
		 */
		public int previousIndex() {
			return iterator.previousIndex();
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#set(E)
		 */
		public void set(E o) {
			Check.checkArg(o);
			iterator.set(o);
			unparent(item);
			parent(o);
			fireListNodeRemoved(item, index);
			fireListNodeAdded(o, index);
			dirty = true;
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#add(E)
		 */
		public void add(E o) {
			Check.checkArg(o);
			iterator.add(o);
			parent(o);
			fireListNodeAdded(o, index);
			index++;
			dirty = true;
		}
    }

	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<T> listIterator() {
		return new MyListIterator<T>(list.listIterator());
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<T> listIterator(int index) {
		return new MyListIterator<T>(list.listIterator(index), index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		// can't track changes
		throw new UnsupportedOperationException();
		//return list.subList(fromIndex, toIndex);
	}
	
	protected void fireListNodeAdded(IASTNode node, int index) {
		IASTTranslationUnit tu = getTranslationUnit();
		if (tu != null) {
			tu.fireListNodeAdded(this, node, index);
			tu.fireNodeChanged(this);
		}
	}
	protected void fireListNodeRemoved(IASTNode node, int index) {
		IASTTranslationUnit tu = getTranslationUnit();
		if (tu != null) {
			tu.fireListNodeRemoved(this, node, index);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		boolean first = true;
		for (T node : list) {
			if (first)
				first = false;
			else if (separator != null)
				handler.emitText(separator);
			handler.emitNode(node);
		}
	}
	
	public IASTNode[] getNodesContainedIn(ISourceRegion region) {
		if (region == null)
			return NO_CHILDREN;
		List<IASTNode> matches = new ArrayList<IASTNode>();
		for (IASTNode node : list) {
			if (node.getSourceRegion() == null)
				continue;
			if (region.contains(node.getSourceRegion())) {
				matches.add(node);
			}
		}
		return (IASTNode[]) matches.toArray(new IASTNode[matches.size()]);
	}
	
	/*
	public IASTNode[] getNodesContainedIn(IDocument document, IRegion region) {
		if (region == null)
			return NO_CHILDREN;
		List<IASTNode> matches = new ArrayList<IASTNode>();
		for (IASTNode node : list) {
			if (node.getDocument() != document)
				continue;
			IRegion inRegion = node.getRegion();
			if (inRegion != null
					&& region.getOffset() <= inRegion.getOffset()
					&& region.getOffset() + region.getLength() >= inRegion.getOffset() + inRegion.getLength()) {
				matches.add(node);
			}
		}
		return (IASTNode[]) matches.toArray(new IASTNode[matches.size()]);
	}
	
	public IASTNode[] getNodesContaining(IDocument document, IRegion region) {
		if (region == null)
			return NO_CHILDREN;
		List<IASTNode> matches = new ArrayList<IASTNode>();
		for (IASTNode node : list) {
			if (node.getDocument() != document)
				continue;
			IRegion inRegion = node.getRegion();
			if (inRegion != null 
					&& inRegion.getOffset() <= region.getOffset()
					&& inRegion.getOffset() + inRegion.getLength() >= region.getOffset() + region.getLength()) {
				matches.add(node);
			}
		}
		return (IASTNode[]) matches.toArray(new IASTNode[matches.size()]);
	}
*/
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode#insertNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public void insertNode(IASTNode after, IASTNode newNode) {
		if (after == null) {
			parent(newNode);
			list.add(0, (T) newNode);
			fireChanged();
			dirty = true;
			return;
		}
		for (ListIterator iter = list.listIterator(); iter.hasNext(); ) {
			if (iter.next() == after) {
				parent(newNode);
				iter.add(newNode);
				fireChanged();
				dirty = true;
				return;
			}
		}
		throw new IllegalArgumentException("cannot find "+after); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode#insertBeforeNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public void insertBeforeNode(IASTNode newNode, IASTNode before) {
		if (before == null) {
			parent(newNode);
			list.add((T) newNode);
			fireChanged();
			dirty = true;
			return;
		}
		for (ListIterator iter = list.listIterator(list.size()); iter.hasPrevious(); ) {
			if (iter.previous() == before) {
				parent(newNode);
				iter.add(newNode);
				fireChanged();
				dirty = true;
				return;
			}
		}
		throw new IllegalArgumentException("cannot find "+before); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode#insertNodeCopies(int, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode[])
	 */
	public IASTNode[] insertNodeCopies(int idx, IASTNode[] nodes) {
		IASTNode[] copies = new IASTNode[nodes.length];
		boolean anyNull = false;
		ListIterator iter = list.listIterator(idx);
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				IASTNode copy = nodes[i].copy();
				copies[i] = copy;
				parent(copy);
				iter.add(copy);
				fireChanged();
				dirty = true;
			} else {
				anyNull = true;
			}
		}
		if (anyNull) {
			int writeIdx = 0;
			for (int readIdx = 0; readIdx < copies.length; readIdx++) {
				if (copies[readIdx] != null) {
					copies[writeIdx++] = copies[readIdx];
				}
			}
			IASTNode[] nonNullCopies = new IASTNode[writeIdx];
			System.arraycopy(copies, 0, nonNullCopies, 0, writeIdx);
			return nonNullCopies;
		} else {
			return copies;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode#removeNodes(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode[])
	 */
	public int removeNodes(IASTNode[] nodes) {
		int firstIdx = -1;
		int idx = 0;
		while (idx < nodes.length && nodes[idx] == null)
			idx++;
		for (ListIterator iter = list.listIterator(); 
			idx < nodes.length && iter.hasNext(); ) {

			if (iter.next() == nodes[idx]) {
				if (firstIdx == -1)
					firstIdx = iter.nextIndex() - 1;
				iter.remove();
				unparent(nodes[idx]);
				fireChanged();
				dirty = true;
				idx++;
				while (idx < nodes.length && nodes[idx] == null)
					idx++;
			}
		}
		
		if (firstIdx == -1)
			if (idx < nodes.length)
				throw new IllegalArgumentException("cannot find first node " + nodes[0]); //$NON-NLS-1$
			else
				return list.size();
		return firstIdx;
	}

	public IASTListNode<T> getList() {
		return this;
	}
}
