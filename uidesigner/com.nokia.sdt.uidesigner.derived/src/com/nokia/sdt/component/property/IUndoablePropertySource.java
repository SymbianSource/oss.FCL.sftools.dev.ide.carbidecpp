/*******************************************************************************
 * Copyright (C) 2006 Nokia Corporation. All rights reserved.
 * Made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.nokia.sdt.component.property;


public interface IUndoablePropertySource {
	
	/**
	 * Returns an opaque object that can be used to set this property source to a previous state.
	 * 
	 * @return Object
	 */
	public Object getUndoValue();
	
	/**
	 * Resets this property source to the state it had when getUndoValue() was called.
	 * 
	 * @param undoValue an Object
	 * @param preserveLocalizedStringKeys if true, localized string keys (macros) are
	 * preserved if they don't collide with existing keys. When false, or upon collision
	 * new keys are generated.
	 */
 	public void setFromUndoValue(Object undoValue, boolean preserveLocalizedStringKeys);
}
