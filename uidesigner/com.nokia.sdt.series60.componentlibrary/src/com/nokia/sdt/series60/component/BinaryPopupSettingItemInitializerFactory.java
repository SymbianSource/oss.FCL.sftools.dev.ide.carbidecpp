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
package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Factory for the IInitializer implementation for the
 * CAknBinaryPopupSettingItem component
 *
 */
public class BinaryPopupSettingItemInitializerFactory implements IImplFactory {
	
	static final String ITEMS_PROPERTY = "items"; //$NON-NLS-1$
	static final String SETTINGS_TEXT_PROPERTY = "settingText"; //$NON-NLS-1$
	static final String ITEMS_TEXT_PROPERTY = "popupText"; //$NON-NLS-1$
	static final String VALUE_PROPERTY = "value"; //$NON-NLS-1$

	public Object getImpl(EObject componentInstance) {
		return new Initializer(componentInstance);
	}
	
	static class Initializer implements IInitializer {
		
		private EObject instance;
	
		public Initializer(EObject instance) {
			this.instance = instance;
		}
		
		public void initialize(boolean isConfigured) {
			if (isConfigured) return;
			IPropertySource ps = ModelUtils.getPropertySource(instance);
			Object pv = ps.getPropertyValue(ITEMS_PROPERTY);
			if (pv instanceof ISequencePropertySource) {
				ISequencePropertySource sps = (ISequencePropertySource) pv;
				IPropertySource item0 = sps.addCompoundProperty(0);
				item0.setPropertyValue(SETTINGS_TEXT_PROPERTY, Messages.getString("BinaryPopupSettingItemInitializerFactory.InitialOnSettingsText")); //$NON-NLS-1$
				item0.setPropertyValue(ITEMS_TEXT_PROPERTY, Messages.getString("BinaryPopupSettingItemInitializerFactory.InitialOnPopupText")); //$NON-NLS-1$
				item0.setPropertyValue(VALUE_PROPERTY, Integer.valueOf(1));
	
				IPropertySource item1 = sps.addCompoundProperty(1);
				item1.setPropertyValue(SETTINGS_TEXT_PROPERTY, Messages.getString("BinaryPopupSettingItemInitializerFactory.InitialOffSettingsText")); //$NON-NLS-1$
				item1.setPropertyValue(ITEMS_TEXT_PROPERTY, Messages.getString("BinaryPopupSettingItemInitializerFactory.InitialOffPopupText")); //$NON-NLS-1$
				item1.setPropertyValue(VALUE_PROPERTY, Integer.valueOf(0));
			}
		}

		public EObject getEObject() {
			return instance;
		}
	}
}
