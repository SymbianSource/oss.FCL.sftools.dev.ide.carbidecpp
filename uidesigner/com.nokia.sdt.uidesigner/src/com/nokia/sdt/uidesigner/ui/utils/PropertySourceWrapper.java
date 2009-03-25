/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui.utils;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.uidesigner.ui.command.ChangePropertyCommand;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource2;

public class PropertySourceWrapper implements IPropertySource2 {
	
	private EObject object;
	private IDesignerDataModel model;
	private String name;
	
	public PropertySourceWrapper(EObject object) {
		super();
		Check.checkArg(object);
		this.object = object;
		model = Adapters.getComponentInstance(object).getDesignerDataModel();
		name = Adapters.getComponentInstance(object).getName();
	}
	
	private IPropertySource2 getPropertySource() {
		return Adapters.getPropertySource2(getObject());
	}
	
	public EObject getObject() {
		IDesignerDataModel designerDataModel = 
			Adapters.getComponentInstance(object).getDesignerDataModel();
		if (designerDataModel == null) {
			if (model != null) {
				EObject o = model.findByNameProperty(name);
				// don't replace object unless not null
				if (o != null) 
					object = o;
			}
		}
		
		return object;
	}

	public Object getEditableValue() {
		return getPropertySource().getEditableValue();
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return getPropertySource().getPropertyDescriptors();
	}

	public Object getPropertyValue(Object id) {
		return getPropertySource().getPropertyValue(id);
	}

	public boolean isPropertyResettable(Object id) {
		return getPropertySource().isPropertyResettable(id);
	}

	public boolean isPropertySet(Object id) {
		return getPropertySource().isPropertySet(id);
	}

	public void resetPropertyValue(Object id) {
		getPropertySource().resetPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		getPropertySource().setPropertyValue(id, value);
		if (ChangePropertyCommand.NAME_PROPERTY.equals(id) && (value instanceof String))
			name = (String) value;
	}
}
