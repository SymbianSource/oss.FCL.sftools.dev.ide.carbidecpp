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
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class FooCodeImplFactoryStub implements IImplFactory {

	private static int gCounter = 0;
	
	class FooStub implements IFoo, IBar, IComponentInstancePropertyListener {
	
		private EObject instance;
		private int counter;
	
		public FooStub(EObject componentInstance) {
			this.instance = componentInstance;
			counter = ++gCounter;
		}
		
		public int doFoo() {
			return counter;
		}
		
		public int doBar() {
			return counter;
		}
		
		public void propertyChanged(EObject componentInstance, Object propertyId) {
			if (propertyId.equals("foo")) {
				IPropertySource properties = 
					(IPropertySource) EcoreUtil.getRegisteredAdapter(getEObject(), IPropertySource.class);
				properties.setPropertyValue("bar", properties.getPropertyValue("foo"));
			}
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new FooStub(componentInstance);
	}
}
