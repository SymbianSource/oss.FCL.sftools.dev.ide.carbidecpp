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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import java.util.*;

/**
 * This is a map whose keys are validated.
 *
 */
public abstract class ValidatingKeyMap<K, V> implements Map<K, V> {

	private Map<K, V> map;
	
	abstract public boolean isAllowedKey(K key);
	
	public ValidatingKeyMap(Map<K, V> realMap) {
		this.map = realMap;
	}
	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		map.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return new ValidatingSet<Map.Entry<K, V>>(map.entrySet()) {

			@Override
			public boolean isValidEntry(java.util.Map.Entry<K, V> entry) {
				return isAllowedKey(entry.getKey());
			}
		};
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(Object key) {
		if (!isAllowedKey((K)key))
			throw new IllegalArgumentException("Illegal key "+key+" for map"); //$NON-NLS-1$ //$NON-NLS-2$
		return map.get(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet() {
		return new ValidatingSet<K>(map.keySet()) {

			@Override
			public boolean isValidEntry(K entry) {
				return isAllowedKey(entry);
			}
		};
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		if (!isAllowedKey(key))
			throw new IllegalArgumentException("Illegal key "+key+" for map"); //$NON-NLS-1$ //$NON-NLS-2$
		return map.put(key, value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> t) {
		for (Map.Entry<? extends K, ? extends V> entry : t.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		return map.remove(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	public int size() {
		return map.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection<V> values() {
		return map.values();
	}
}
