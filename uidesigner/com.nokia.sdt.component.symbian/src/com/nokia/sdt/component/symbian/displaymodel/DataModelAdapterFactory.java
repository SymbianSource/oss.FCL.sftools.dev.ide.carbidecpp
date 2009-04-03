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
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IValidateContainment;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class DataModelAdapterFactory extends AdapterFactoryImpl {
	
	private IDisplayModel displayModel;
	
	DataModelAdapterFactory(IDisplayModel displayModel) {
		Check.checkArg(displayModel);
		this.displayModel = displayModel;
	}

	protected Adapter createAdapter(Notifier target, Object type) {
		if (target instanceof EObject) {
			IComponentInstance ci = (IComponentInstance) EcoreUtil.getRegisteredAdapter((EObject)target, IComponentInstance.class);
			if (ci != null) {
				IAttributes attr = Utilities.getAttributes(displayModel, (EObject)target);		
				if (IDisplayObject.class.equals(type)) {
					return new DisplayObject(displayModel, ci);
				}
				
				if (((Class) type).isAssignableFrom(ILayoutContainer.class) || 
							IValidateContainment.class.equals(type)) {
					if (attr != null && attr.getBooleanAttribute(CommonAttributes.IS_LAYOUT_CONTAINER, false)) {
						return (Adapter) displayModel.createLayoutContainer(ci);
					}
				}
				
				if (IContainer.class.equals(type) || IValidateContainment.class.equals(type)) {
					if (attr != null && attr.getBooleanAttribute(CommonAttributes.IS_OUTLINE_CONTAINER, false)) {
						return (Adapter) displayModel.createContainer(ci);
					}
				}
				
				if (ILayoutObject.class.equals(type)) {
					if (attr != null && (attr.getBooleanAttribute(CommonAttributes.IS_LAYOUT_OBJECT, false) ||
							attr.getBooleanAttribute(CommonAttributes.IS_TRANSIENT_OBJECT, false))) {
						return (Adapter) displayModel.createLayoutObject(ci);
					}
				}
			}
		}
		
		return null;
	}
	
	@Override
	protected void associate(Adapter adapter, Notifier target) {
		if (adapter != null && adapter.isAdapterForType(ILayoutContainer.class)) {
			String s = Utilities.getStringAttribute((EObject) target, 
				CommonAttributes.LAYOUT_CONTAINER_INDIRECTION_REFERENCE_PROPERTY);
			if (s != null)
				return; // don't associate if delegating ILayoutContainer adapter
		}
		super.associate(adapter, target);
	}

	public boolean isFactoryForType(Object type) {
		return type.equals(IDisplayObject.class) ||
			type.equals(ILayoutContainer.class) ||
			type.equals(IContainer.class) ||
			type.equals(ILayoutObject.class);
	}
}
