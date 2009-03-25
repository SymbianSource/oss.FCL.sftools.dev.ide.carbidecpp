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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

/**
 * A helper base class for an operation that changes a setting from
 * an old value to a new value.
 *
 * @param <Type>
 */
abstract class AbstractMultiImageEditorOperation<Type> extends MultiImageEditorOperation {

	/**
	 * 
	 */
	protected Type oldValue;
	protected Type newValue;

	public AbstractMultiImageEditorOperation(MultiImageEditorContextBase editorContext, String label, Type oldValue, Type newValue) {
		super(label, editorContext);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	protected abstract void update(Type value);
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.ImageEditorOperation#doesAnything()
	 */
	@Override
	protected boolean doesAnything() {
		return !ObjectUtils.equals(oldValue, newValue);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.ImageEditorOperation#redo()
	 */
	@Override
	protected void redo() {
		update(newValue);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.ImageEditorOperation#undo()
	 */
	@Override
	protected void undo() {
		update(oldValue);
	}
	
}