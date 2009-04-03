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


package com.nokia.sdt.series60.component;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.ui.editors.AbstractArrayPropertyEditorFactory;
import com.nokia.sdt.symbian.ui.editors.Messages;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Creates editor for choice list string properties.
 * Each item contains the menu item text
 *
 */
public class ChoiceListTextEditorFactory extends AbstractArrayPropertyEditorFactory {

	private final static String SETTING_ITEM_TEXT_PROPERTY = "itemText"; //$NON-NLS-1$
	private final static String CHOICE_LIST_COMPONENT = "com.nokia.sdt.series60.CAknChoiceList";
	
	static final char QUOTE_CHAR = '"';
	/**
	 * Return the info string for the given component.
	 * @param object
	 * @return
	 */
	protected String getInfoString(EObject object) {
		//IComponentInstance instance = ModelUtils.getComponentInstance(object);
		//String infoString = Messages.getString("ChoiceListEditorFactory.EnumeratedStringDialogInfoMessage"); //$NON-NLS-1$
		//if (instance.getComponentId().indexOf("CAknChoiceList") >= 0) { //$NON-NLS-1$
		//	infoString = Messages.getString("ChoiceListEditorFactory.BinaryPopupEnumeratedStringDialogInfoMessage"); //$NON-NLS-1$
		//} 
		//return infoString;
		return "";
	}
			
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditorValidator(org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public ICellEditorValidator createCellEditorValidator(EObject object, String propertyPath) {
		return null;
	}

	@Override
	public ILabelProvider createElementLabelProvider(EObject object, Object propertyID) {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				String result = null;
				if (element instanceof IPropertySource) {
					IPropertySource ps = (IPropertySource) element;
					result = ps.getPropertyValue(SETTING_ITEM_TEXT_PROPERTY).toString();
				} else {
					result = super.getText(element);
				}
				if (result != null) {
					result = TextUtils.escape(result, QUOTE_CHAR);
				}
				return result;
			}
		};
	}
	
	@Override
	protected boolean isFixedLengthArrayProperty(EObject object, Object propertyID) {
		// this factory is used for CAknChoiceList
		return false;
	}
}
