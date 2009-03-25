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

package com.nokia.sdt.emf.dm;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class ComponentHelper {
	
	private IComponentSet componentSet;

	public static ComponentHelper getComponentHelper(EObject object) {
		ComponentHelper result = null;
		IDesignerData dd = null;
		
		Resource r = object.eResource();
		if (r != null) {
			EList roots = r.getContents();
			if (roots != null && roots.size() == 1) {
				dd = (IDesignerData) roots.get(0);
			}
		}
		
		if (dd != null)
			result = dd.getComponentHelper();
		return result;
	}
	
	public void setComponentSet(IComponentSet componentSet) {
		this.componentSet = componentSet;
	}
	
	public IComponentSet getComponentSet() {
		return componentSet;
	}
	
	public IComponent lookupComponent(String componentID) {
		IComponent result = null;
		if (componentSet != null)
			result = componentSet.lookupComponent(componentID);
		return result;
	}
}
