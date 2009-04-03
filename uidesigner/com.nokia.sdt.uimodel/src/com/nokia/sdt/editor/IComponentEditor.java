/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.editor;

import com.nokia.sdt.datamodel.adapter.IModelAdapter;

import org.eclipse.core.runtime.IAdaptable;

/**
 * 
 * Interface for components with an edit mode
 */
public interface IComponentEditor extends IModelAdapter, IAdaptable {

	/**
	 * Advise the component editor that it is now editing
	 * @param editor the <code>IDesignerEditor</code>
	 */
	void beginEditing(IDesignerEditor editor);
	
	/**
	 * Advise the component editor that it is no longer editing
	 */
	void endEditing();
	
	/**
	 * @return true if object is temporary byproduct of editing operation
	 */
	boolean isTemporaryObject();
	
	/**
	 * @return true if the user can remove this object
	 */
	boolean isUserRemovable();
}
