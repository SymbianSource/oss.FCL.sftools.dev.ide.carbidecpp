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

import com.nokia.cpp.internal.api.utils.core.StatusBuilder;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.utils.StatusHolder;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class TestQueryContainment implements IQueryContainment {

	private EObject instance;

	public TestQueryContainment(EObject componentInstance) {
		this.instance = componentInstance;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IQueryContainment#canContainComponent(com.nokia.sdt.component.IComponent, com.nokia.sdt.utils.StatusHolder)
	 */
	public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
		IComponentInstance componentInstance = 
			(IComponentInstance) EcoreUtil.getRegisteredAdapter(instance, IComponentInstance.class);
		boolean canContain = component.getId().equals(componentInstance.getComponent().getId());
		if (!canContain && (statusHolder != null)) {
			StatusBuilder builder = new StatusBuilder(PluginTest.getDefault());
			builder.add(IStatus.ERROR, "Test {0}", new Object[] { component.getId() });
			statusHolder.setStatus(builder.createStatus("", null));
		}
		return canContain;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IQueryContainment#canContainChild(com.nokia.sdt.displaymodel.adapter.ILayoutObject, com.nokia.sdt.utils.StatusHolder)
	 */
	public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
		// this is only testing the component version
		return canContainComponent(child.getComponent(), statusHolder);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IQueryContainment#canRemoveChild(com.nokia.sdt.datamodel.adapter.IComponentInstance)
	 */
	public boolean canRemoveChild(IComponentInstance child) {
		IComponentInstance componentInstance = 
			(IComponentInstance) EcoreUtil.getRegisteredAdapter(instance, IComponentInstance.class);
		return child.getComponentId().equals(componentInstance.getComponent().getId());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IQueryContainment#isValidComponentInPalette(com.nokia.sdt.component.IComponent)
	 */
	public boolean isValidComponentInPalette(IComponent component) {
		return canContainComponent(component, null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
	 */
	public EObject getEObject() {
		return instance;
	}

}
