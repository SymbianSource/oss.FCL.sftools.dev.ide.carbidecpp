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


package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IInitializer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class InitializerCodeImplFactoryStub implements IImplFactory {

	class InitializerStub implements IInitializer {
	
		private EObject instance;
	
		public InitializerStub(EObject instance) {
			this.instance = instance;
		}
		
		public void initialize(boolean isConfigured) {
			IPropertySource propertySource = 
				(IPropertySource) EcoreUtil.getRegisteredAdapter(getEObject(), IPropertySource.class);
			propertySource.setPropertyValue("foo", new Integer(-1));
		}

		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new InitializerStub(instance);
	}
}
