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
package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class DisplayObject extends AdapterImpl implements IDisplayObject {
	
	private IDisplayModel displayModel;
	private IComponentInstance instance;
	
	DisplayObject(IDisplayModel displayModel, IComponentInstance instance) {
		Check.checkArg(displayModel);
		Check.checkArg(instance);
		this.displayModel = displayModel;
		this.instance = instance;
	}
	
	public boolean isAdapterForType(Object type) {
		return IDisplayObject.class.equals(type);
	}

	public IStatus getStatus() {
		return null;
	}

	public boolean isRemovable() {
		boolean isNonRemovable = Utilities.booleanAttribute(displayModel, getEObject(), 
						CommonAttributes.IS_NOT_USER_REMOVABLE, false);
		
		boolean isTopLevelContentContainer = Utilities.booleanAttribute(displayModel, getEObject(), 
						CommonAttributes.IS_TOP_LEVEL_CONTENT_CONTAINER, false);
		
		boolean isRootContainer = displayModel.getRootContainer().equals(getEObject());
		
		IComponentEditor componentEditor = Utilities.getComponentEditor(getEObject());
		boolean isUserRemovable = componentEditor == null || componentEditor.isUserRemovable();
		
		return !isNonRemovable && !isTopLevelContentContainer && !isRootContainer && isUserRemovable;
	}

	public boolean isNonLayoutObject() {
		boolean result = Utilities.booleanAttribute(displayModel, getEObject(), 
				CommonAttributes.IS_NON_LAYOUT_OBJECT, false);
		return result;
	}

	public EObject getEObject() {
		return instance.getEObject();
	}

}
