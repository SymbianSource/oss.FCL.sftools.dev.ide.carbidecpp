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

import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;

public class DirtyTrackingArrayList<V> extends ArrayList<V> {
	private boolean dirty;
	
	public DirtyTrackingArrayList() {
		super();
		dirty = true;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, V element) {
		super.add(index, element);
		dirty = true;
	}
	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(V o) {
		if (super.add(o)) {
			dirty = true;
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#remove(int)
	 */
	@Override
	public V remove(int index) {
		V old = super.remove(index);
		if (old != null)
			dirty = true;
		return old;
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		if (super.remove(o)) {
			dirty = true;
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#set(int, java.lang.Object)
	 */
	@Override
	public V set(int index, V element) {
		V old = super.set(index, element);
		if (!ObjectUtils.equals(old, element))
			dirty = true;
		return old;
	}
	
}
