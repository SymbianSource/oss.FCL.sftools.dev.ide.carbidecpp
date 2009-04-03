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


package com.nokia.sdt.series60.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Tuple;
import com.nokia.cpp.internal.api.utils.ui.ModelObjectComboBoxCellEditor;

/**
 * Creates editor for string list properties.
 * 
 */
public class MultiLineQueryTypeEditorFactory extends AbstractPropertyEditorFactory {
	
	private final static String EMultiDataFirstEdwin = "EMultiDataFirstEdwin"; //$NON-NLS-1$
	private final static String EMultiDataFirstSecEd = "EMultiDataFirstSecEd"; //$NON-NLS-1$
	private final static String EMultiDataFirstTimeEd = "EMultiDataFirstTimeEd"; //$NON-NLS-1$
	private final static String EMultiDataFirstDateEd = "EMultiDataFirstDateEd"; //$NON-NLS-1$
	private final static String EMultiDataFirstPhoneEd = "EMultiDataFirstPhoneEd"; //$NON-NLS-1$
	private final static String EMultiDataFirstNumEd = "EMultiDataFirstNumEd"; //$NON-NLS-1$
	private final static String EMultiDataFirstPinEd = "EMultiDataFirstPinEd"; //$NON-NLS-1$
	private final static String EMultiDataFirstIpEd = "EMultiDataFirstIpEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondEdwin = "EMultiDataSecondEdwin"; //$NON-NLS-1$
	private final static String EMultiDataSecondSecEd = "EMultiDataSecondSecEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondTimeEd = "EMultiDataSecondTimeEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondDateEd = "EMultiDataSecondDateEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondDurEd = "EMultiDataSecondDurEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondPhoneEd = "EMultiDataSecondPhoneEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondNumEd = "EMultiDataSecondNumEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondPinEd = "EMultiDataSecondPinEd"; //$NON-NLS-1$
	private final static String EMultiDataSecondIpEd = "EMultiDataSecondIpEd"; //$NON-NLS-1$
	
	private final static String[] firstTypes = {
		EMultiDataFirstEdwin,
		EMultiDataFirstSecEd,
		EMultiDataFirstTimeEd,
		EMultiDataFirstDateEd,
		EMultiDataFirstPhoneEd,
		EMultiDataFirstNumEd,
		EMultiDataFirstPinEd,
		EMultiDataFirstIpEd,
	};
	
	private final static String[] secondTypes = {
		EMultiDataSecondEdwin,
		EMultiDataSecondSecEd,
		EMultiDataSecondTimeEd,
		EMultiDataSecondDateEd,
		EMultiDataSecondDurEd,
		EMultiDataSecondPhoneEd,
		EMultiDataSecondNumEd,
		EMultiDataSecondPinEd,
		EMultiDataSecondIpEd,
	};
	
	private final static Tuple[] allowedTypes = {
		// EMultiDataFirstEdwin
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondEdwin),
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondSecEd),
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondTimeEd),
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondDateEd),
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondDurEd),
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondPhoneEd),
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondNumEd),
		new Tuple(EMultiDataFirstEdwin, EMultiDataSecondPinEd),
		
		// EMultiDataFirstSecEd
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondEdwin),
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondSecEd),
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondTimeEd),
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondDateEd),
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondDurEd),
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondPhoneEd),
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondNumEd),
		new Tuple(EMultiDataFirstSecEd, EMultiDataSecondPinEd),
		
		// EMultiDataFirstTimeEd
		new Tuple(EMultiDataFirstTimeEd, EMultiDataSecondTimeEd),
		new Tuple(EMultiDataFirstTimeEd, EMultiDataSecondDateEd),
		new Tuple(EMultiDataFirstTimeEd, EMultiDataSecondDurEd),

		// EMultiDataFirstDateEd
		new Tuple(EMultiDataFirstDateEd, EMultiDataSecondTimeEd),
		new Tuple(EMultiDataFirstDateEd, EMultiDataSecondDateEd),
		new Tuple(EMultiDataFirstDateEd, EMultiDataSecondDurEd),

		// EMultiDataFirstPhoneEd
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondEdwin),
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondSecEd),
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondTimeEd),
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondDateEd),
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondDurEd),
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondPhoneEd),
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondNumEd),
		new Tuple(EMultiDataFirstPhoneEd, EMultiDataSecondPinEd),
		
		// EMultiDataFirstNumEd
		new Tuple(EMultiDataFirstNumEd, EMultiDataSecondNumEd),
		
		// EMultiDataFirstPinEd
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondEdwin),
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondSecEd),
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondTimeEd),
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondDateEd),
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondDurEd),
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondPhoneEd),
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondNumEd),
		new Tuple(EMultiDataFirstPinEd, EMultiDataSecondPinEd),

		// EMultiDataFirstNumEd
		new Tuple(EMultiDataFirstIpEd, EMultiDataSecondIpEd),
	};
	
	private static final String TYPE1_PROPERTY_ID = "type";
	private static final String TYPE2_PROPERTY_ID = "type2";

	private static List<String> firstTypesList = new ArrayList(firstTypes.length);
	private static List<String> secondTypesList = new ArrayList(secondTypes.length);
	private static Set<Tuple> allowedTypesSet = new HashSet(allowedTypes.length);
	private static Map<String, List> propertyToTypesList = new HashMap(2);
	
	static {
		for (int i = 0; i < firstTypes.length; i++) {
			firstTypesList.add(firstTypes[i]);
		}
		
		for (int i = 0; i < secondTypes.length; i++) {
			secondTypesList.add(secondTypes[i]);
		}
		
		for (int i = 0; i < allowedTypes.length; i++) {
			allowedTypesSet.add(allowedTypes[i]);
		}
		
		propertyToTypesList.put(TYPE1_PROPERTY_ID, firstTypesList);
		propertyToTypesList.put(TYPE2_PROPERTY_ID, secondTypesList);
	}
	
	public MultiLineQueryTypeEditorFactory() {
		super();
	}
	
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {
		return new ModelObjectComboBoxCellEditor(parent, getValues(object, propertyId), new LabelProvider() {
			public String getText(Object element) {
				String string = "MultiLineQueryTypeEditorFactory."; //$NON-NLS-1$
				string += element;
				return Messages.getString(string);
			}
		});
	}

	private List getValues(EObject object, Object reqPropertyId) {
		Check.checkContract(reqPropertyId.equals(TYPE1_PROPERTY_ID) || 
								reqPropertyId.equals(TYPE2_PROPERTY_ID));
		
		List values = new ArrayList();
		IPropertySource ps = Utilities.getPropertySource(object);
		Object otherTypeProperty = getOtherTypeProperty(reqPropertyId);
		String curValue = (String) ps.getPropertyValue(otherTypeProperty);
		List list = propertyToTypesList.get(reqPropertyId);
		for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
			String item = iter.next();
			Tuple testTuple = makeTuple(reqPropertyId, item, curValue);
			if (allowedTypesSet.contains(testTuple))
				values.add(item);
		}
		
		return values;
	}

	private Tuple makeTuple(Object reqPropertyId, String itemToGet, String otherValue) {
		// order the tuple based on the property id to get
		if (reqPropertyId.equals(TYPE1_PROPERTY_ID))
			return new Tuple(itemToGet, otherValue);
		return new Tuple(otherValue, itemToGet);
	}

	private Object getOtherTypeProperty(Object propertyId) {
		if (propertyId.equals(TYPE1_PROPERTY_ID))
			return TYPE2_PROPERTY_ID;
		return TYPE1_PROPERTY_ID;
	}

}
