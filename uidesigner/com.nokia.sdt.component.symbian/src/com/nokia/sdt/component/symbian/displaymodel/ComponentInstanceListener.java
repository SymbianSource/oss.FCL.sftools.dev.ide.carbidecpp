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
package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/** 
 * This listener detects changes to the contents or hierarchy of instances 
 * owned by the display model and allows it to react to such changes.
 * This class has no special knowledge other than that parents contain children.
 * Subclasses may add additional checks.
 * 
 *
 */
public class ComponentInstanceListener implements 
						IComponentInstanceChildListener, IComponentInstancePropertyListener {
	
	protected DisplayModelBase displayModel;
	private IComponentInstance instance;
	
	/** This constructor implicitly adds itself to the instance */
	ComponentInstanceListener(DisplayModelBase displayModel, IComponentInstance instance) {
		this.displayModel = displayModel;
		this.instance = instance;
		displayModel.registerInstanceListener(instance, this);
		instance.addChildListener(this);
		instance.addPropertyListener(this);
		
		EObject[] children = instance.getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				IComponentInstance childInstance = (IComponentInstance)
				EcoreUtil.getRegisteredAdapter(children[i], IComponentInstance.class);
						Check.checkContract(childInstance != null);
					displayModel.addComponentInstanceListener(childInstance);
			}
		}
	}
	
	void disconnect() {
		instance.removeChildListener(this);
		instance.removePropertyListener(this);
	}
	
	void removed() {
		
		// first remove child listeners, then the listener for this instance
		EObject[] children = instance.getChildren();
		for (int i = 0; i < children.length; i++) {
			IComponentInstance childInstance = (IComponentInstance)
			EcoreUtil.getRegisteredAdapter(children[i], IComponentInstance.class);
			
			ComponentInstanceListener childListener = displayModel.listenerForInstance(childInstance);
			if (childListener != null) {
				childListener.removed();
			}
		}

		disconnect();
		displayModel.unregisterInstanceListener(instance);
	}

	public void childAdded(EObject parent, EObject child) {
		IComponentInstance childInstance = (IComponentInstance)
				EcoreUtil.getRegisteredAdapter(child, IComponentInstance.class);
		Check.checkContract(childInstance != null);
		
		displayModel.addComponentInstanceListener(childInstance);
		if (displayModel.isContainerIndirectionReference(parent))
			displayModel.refreshEditor();
	}

	public void childRemoved(EObject parent, EObject child) {
		IComponentInstance childInstance = (IComponentInstance)
			EcoreUtil.getRegisteredAdapter(child, IComponentInstance.class);
		Check.checkContract(childInstance != null);

		ComponentInstanceListener childListener = displayModel.listenerForInstance(childInstance);
		if (childListener != null) {
			childListener.removed();
		}
		if (displayModel.isContainerIndirectionReference(parent))
			displayModel.refreshEditor();
	}

	/** Handle any property changes of interest */
	public void propertyChanged(EObject componentInstance, Object propertyId) {
		if (propertyId.equals(displayModel.getContainerIndirectionRefPropertyName(componentInstance))) {
			displayModel.refreshEditor();
		}		
	}

	public void childrenReordered(EObject parent) {
		// nothing to do, same children exist
	}

}
