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

package com.nokia.sdt.series60.component.listCustomizer;

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
 * Get component-specific information about the list box styles it supports.
 * A component using the ListCustomizerUIImplFactory or the ListLayoutEditorFactory
 * must publish an attribute "list-style-enum-property" describing the enum type listing
 * the supported list box styles.
 * 
 *
 */
public class ListLayoutData {

	private static final String LIST_STYLE_ENUM = "list-style-enum-property"; //$NON-NLS-1$

	/**
	 * @param component
	 * @return
	 */
	public static String[] getComponentListBoxStyles(IComponent component) {
		if (component == null)
			return new String[0];
		
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		String styleEnum = attributes.getAttribute(LIST_STYLE_ENUM);
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
	
	public static String[] getInstanceListBoxStyles(EObject eObject) {
		IComponentInstance instance = ModelUtils.getComponentInstance(eObject);
		return getComponentListBoxStyles(instance.getComponent());
	}

	String[] orderedDisplayValues;
	HashMap<String, String> valueToDisplayMap;
	HashMap<String, String> displayToValueMap;
	
	public ListLayoutData(String[] styles) {
		orderedDisplayValues = new String[styles.length];
		valueToDisplayMap = new HashMap<String, String>();
		displayToValueMap = new HashMap<String, String>();
		for (int i = 0; i < styles.length; i++) {
			orderedDisplayValues[i] = Messages.getString("ListCustomizerComposite." + styles[i] + ".Caption"); //$NON-NLS-1$ $NON-NLS-2$
			valueToDisplayMap.put(styles[i], orderedDisplayValues[i]);
			displayToValueMap.put(orderedDisplayValues[i], styles[i]);
		}
	}

}
