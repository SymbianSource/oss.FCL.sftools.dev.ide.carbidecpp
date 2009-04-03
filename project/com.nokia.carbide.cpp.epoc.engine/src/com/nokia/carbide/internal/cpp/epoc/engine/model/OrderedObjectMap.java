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
package com.nokia.carbide.internal.cpp.epoc.engine.model;

import java.util.AbstractMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class OrderedObjectMap<K,V> extends AbstractMap<K,V> {

	IdentityHashMap<K, V> identityMap;
	LinkedList<K> ordering;
	
	public OrderedObjectMap() {
		this.identityMap = new IdentityHashMap<K, V>();
		this.ordering = new LinkedList<K>();
	}
	
	@Override
	public Set entrySet() {
		return identityMap.entrySet();
	}

	@Override
	public V put(K key, V value) {
		V orig = identityMap.put(key, value);
		if (orig == null) {
			ordering.add(key);
		}
		return orig;
	}
	
	@Override
	public V remove(Object key) {
		ordering.remove(key);
		return super.remove(key);
	}
	
	/**
	 * This method returns an iterator over the entries in
	 * insertion order.
	 * @return
	 */
	public Iterator<Map.Entry<K, V>> orderedIterator() {
		return new Iterator<Entry<K,V>>() {
			Iterator<K> listIterator = ordering.iterator();
			K lastKey = null;
			public boolean hasNext() {
				return listIterator.hasNext();
			}

			public java.util.Map.Entry<K, V> next() {
				lastKey = listIterator.next();
				Entry<K,V> entry = new Entry<K,V>() {

					public K getKey() {
						return lastKey;
					}

					public V getValue() {
						return identityMap.get(lastKey);
					}

					public V setValue(V value) {
						return identityMap.put(lastKey, value);
					}
					
				};
				return entry;
			}

			public void remove() {
				listIterator.remove();
				identityMap.remove(lastKey);
			}
			
		};
	}

	public Iterator<K> orderedKeyIterator() {
		return ordering.iterator();
	}
}
