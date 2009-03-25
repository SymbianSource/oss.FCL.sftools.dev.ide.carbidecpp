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

package com.nokia.carbide.internal.cpp.epoc.engine;

import java.util.*;

/**
 * A list implementation that tracks changes.
 * A real list provides the actual API, but the ordered
 * list of changes can be tracked as well.
 *
 */
public class ChangeTrackingList<T> implements List<T> {

	/** Abstract base of recorded changes */
	public abstract static class Change<T>  {

		protected T o;
		Change(T o) {
			this.o = o;
		}
		
		/**
		 * Get the affected item.
		 * @return
		 */
		public T get() {
			return o;
		}
		
		/**
		 * Execute the change on another list.
		 */
		abstract public void execute(List<T> list);
	}
	
	/**
	 * An item was added at the end.
	 *
	 * @param <T>
	 */
	public static class Add<T> extends Change<T> {
		Add(T o) {
			super(o);
		}
		public void execute(List list) {
			list.add(o);
		}
	}
	
	/**
	 * An item was added at a given position.
	 *
	 * @param <T>
	 */
	public static class AddAt<T> extends Change<T> {
		private int idx;
		AddAt(int idx, T o) {
			super(o);
			this.idx = idx;
		}
		public int getIndex() {
			return idx;
		}
		public void execute(List list) {
			list.add(idx, o);
		}
	}

	/**
	 * An item was removed.
	 *
	 * @param <T>
	 */
	public static class Remove<T> extends Change<T> {
		Remove(T o) {
			super(o);
		}
		public void execute(List list) {
			list.remove(o);
		}
	}
	
	/**
	 * An item was set.
	 */
	public static class Set<T> extends Change<T> {
		private int idx;
		Set(int idx, T o) {
			super(o);
			this.idx = idx;
		}
		public int getIndex() {
			return idx;
		}
		public void execute(List list) {
			list.set(idx, o);
		}
	}
	
	private List<T> list;
	private List<Change<T>> changes;

	/**
	 * 
	 */
	public ChangeTrackingList() {
		this.list = new ArrayList<T>();
		this.changes = new ArrayList<Change<T>>();
	}
	
	public ChangeTrackingList(List<T> nodes) {
		this.list = new ArrayList<T>(nodes);
		this.changes = new ArrayList<Change<T>>();
	}

	/**
	 * Tell if any changes were detected on the list.
	 * @return
	 */
	public boolean isChanged() {
		return changes.size() > 0;
	}
	
	/**
	 * Get the list of changes made to the list
	 * @return
	 */
	public List<Change<T>> getChanges() {
		return changes;
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(T o) {
		changes.add(new Add(o));
		return list.add(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, T element) {
		changes.add(new AddAt(index, element));
		list.add(index, element);
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends T> c) {
		boolean changed = false;
		for (T node : c)
			changed |= add(node);
		return changed;
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		for (T node : c) {
			changes.add(new AddAt(index, node));
			index++;
		}
		return list.addAll(index, c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#clear()
	 */
	public void clear() {
		for (T node : list) {
			changes.add(new Remove(node));
		}
		list.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return list.contains(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	public T get(int index) {
		return list.get(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.List#iterator()
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Iterator<T> iter = list.iterator();
			T last = null;
			
			public boolean hasNext() {
				return iter.hasNext();
			}

			public T next() {
				last = iter.next();
				return last;
			}

			public void remove() {
				iter.remove();
				changes.add(new Remove(last));
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	
	private class MyListIterator implements ListIterator<T> {
		ListIterator<T> iter;
		T last;
		int lastIndex = 0;
		public MyListIterator() {
			iter = list.listIterator();
			last = null;
			lastIndex = 0;
		}
		
		public MyListIterator(int index) {
			iter = list.listIterator(index);
			last = null;
			lastIndex = index;
		}
		
		public void add(T o) {
			iter.add(o);
			changes.add(new Add(o));
			last = null;
		}
	
		public boolean hasNext() {
			return iter.hasNext();
		}
	
		public boolean hasPrevious() {
			return iter.hasPrevious();
		}
	
		public T next() {
			lastIndex = iter.nextIndex();
			last = iter.next();
			return last;
		}
	
		public int nextIndex() {
			return iter.nextIndex();
		}
	
		public T previous() {
			lastIndex = iter.previousIndex();
			last = iter.previous();
			return last;
		}
	
		public int previousIndex() {
			return iter.previousIndex();
		}
	
		public void remove() {
			iter.remove();
			changes.add(new Remove(last));
			last = null;
		}
	
		public void set(T o) {
			iter.set(o);
			changes.add(new Set(lastIndex, o));
			last = o;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<T> listIterator() {
		return new MyListIterator();
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<T> listIterator(int index) {
		return new MyListIterator(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		if (list.remove(o)) {
			changes.add(new Remove(o));
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	public T remove(int index) {
		T item = list.remove(index);
		if (item != null) {
			changes.add(new Remove(item));
		}
		return item;
	}

	/* (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object o : c) {
			if (list.remove(o)) {
				changes.add(new Remove(o));
				changed = true;
			}
		}
		return changed;
	}

	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for (Object o : list) {
			if (!c.contains(o)) {
				changes.add(new Remove(o));
				list.remove(o);
				changed = true;
			}
		}
		return changed;
	}

	/* (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public T set(int index, T element) {
		T existing = list.set(index, element);
		if (existing == element)
			return existing;
		changes.add(new Set(index, element));
		return existing;
	}

	/* (non-Javadoc)
	 * @see java.util.List#size()
	 */
	public int size() {
		return list.size();
	}

	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
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

}
