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


package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstanceChildListener;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class ChildListenerCodeImplFactoryStub implements IImplFactory {

	class ChildListenerStub implements IComponentInstanceChildListener {
	
		private EObject instance;
	
		public ChildListenerStub(EObject instance) {
			this.instance = instance;
		}
		
		public void childAdded(EObject parent, EObject child) {
			IComponentInstance childInstance = Utilities.getComponentInstance(child);
			if (childInstance.getComponentId().equals("com.nokia.examples.codeComp")) {
				IPropertySource ps = Utilities.getPropertySource(child);
				ps.setPropertyValue("test", "added");
			}
		}
		
		public void childRemoved(EObject parent, EObject child) {
			if (parent.equals(instance)) {
				IPropertySource ps = Utilities.getPropertySource(parent);
				ps.setPropertyValue("test", "removed");
			}
		}
		
		public void childrenReordered(EObject parent) {
			if (parent.equals(instance)) {
				IPropertySource ps = Utilities.getPropertySource(parent);
				ps.setPropertyValue("test", "reordered");
			}
		}
		
		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new ChildListenerStub(instance);
	}
}
