/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.uiq.components.sbbCustomizer;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.properties.EnumPropertyTypeDescriptor;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;

import java.util.*;

/**
 * Get component-specific information about the system building block types it supports.
 * A component using the SBBCustomizerUIImplFactory or the SBBLayoutEditorFactory
 * must publish an attribute "sbb-type-enum-property" describing the enum type listing
 * the supported system building block types.
 *
 */
public class SBBLayoutData {

	private static final String SBB_STYLE_ENUM = "sbb-type-enum-property"; //$NON-NLS-1$

	/**
	 * @param component
	 * @return
	 */
	public static String[] getComponentSBBTypes(IComponent component) {
		if (component == null)
			return new String[0];
		
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		String styleEnum = attributes.getAttribute(SBB_STYLE_ENUM);
		Check.checkContract(styleEnum != null);
		
		ITypeDescriptor descriptor = component.getComponentSet().lookupTypeDescriptor(styleEnum);
		Check.checkContract(descriptor instanceof EnumPropertyTypeDescriptor);

		EnumPropertyTypeDescriptor enumType = (EnumPropertyTypeDescriptor) descriptor;
		Collection enums  = enumType.getChoiceOfValues();
		
		String[] styles = new String[enums.size()];
		int idx = 0;
		for (Iterator iter = enums.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			styles[idx++] = element.toString();
		}
		
		return styles;
	}
	
	public static String[] getInstanceSBBTypes(EObject eObject) {
		IComponentInstance instance = ModelUtils.getComponentInstance(eObject);
		return getComponentSBBTypes(instance.getComponent());
	}

	String[] orderedDisplayValues;
	HashMap<String, String> valueToDisplayMap;
	HashMap<String, String> displayToValueMap;
	
	public SBBLayoutData(String[] styles) {
		orderedDisplayValues = new String[styles.length];
		valueToDisplayMap = new HashMap<String, String>();
		displayToValueMap = new HashMap<String, String>();
		for (int i = 0; i < styles.length; i++) {
			orderedDisplayValues[i] = Messages.getString("SBBCustomizerComposite." + styles[i] + ".Caption"); //$NON-NLS-1$ $NON-NLS-2$
			valueToDisplayMap.put(styles[i], orderedDisplayValues[i]);
			displayToValueMap.put(orderedDisplayValues[i], styles[i]);
		}
	}

}
