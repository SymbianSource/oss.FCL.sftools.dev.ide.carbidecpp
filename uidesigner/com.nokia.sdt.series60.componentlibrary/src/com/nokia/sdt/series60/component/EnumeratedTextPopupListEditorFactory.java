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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.ui.editors.AbstractArrayPropertyEditorFactory;
import com.nokia.sdt.symbian.ui.editors.Messages;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Creates editor for enumerated string list properties.
 * Each item contains the setting item text, which is shown outside editing,
 * the popup text, which is shown in the popup, and a number, which is
 * the value.
 * <p>
 * TODO: make this work property when the incoming list is not 
 * ISequencePropertySource   
 *
 */
public class EnumeratedTextPopupListEditorFactory extends AbstractArrayPropertyEditorFactory {

	private final static String SETTING_ITEM_TEXT_PROPERTY = "settingText"; //$NON-NLS-1$
	private final static String BINARY_POPUP_COMPONENT = "com.nokia.sdt.series60.CAknBinaryPopupSettingItem";
	
	static final char QUOTE_CHAR = '"';
	/**
	 * Return the info string for the given component.
	 * @param object
	 * @return
	 */
	protected String getInfoString(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		String infoString = Messages.getString("EnumeratedTextPopupListEditorFactory.EnumeratedStringDialogInfoMessage"); //$NON-NLS-1$
		if (instance.getComponentId().indexOf("CAknBinaryPopupSettingItem") >= 0) { //$NON-NLS-1$
			infoString = Messages.getString("EnumeratedTextPopupListEditorFactory.BinaryPopupEnumeratedStringDialogInfoMessage"); //$NON-NLS-1$
		} 
		return infoString;
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
		// this factory is used for CAknEnumeratedTextPopupSettingItem and 
		// CAknBinaryPopupSettingItem. The latter is pre-initialized with a two item array
		return ModelUtils.isInstanceOf(object, BINARY_POPUP_COMPONENT);
	}
}
