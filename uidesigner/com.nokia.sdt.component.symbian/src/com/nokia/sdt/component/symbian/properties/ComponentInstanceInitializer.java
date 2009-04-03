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
package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

import java.util.Iterator;

	/**
	 * This adapter is installed when a component set
	 * is attached to a data model. As a subclass of
	 * EContentAdapter it receives EMF notifications for
	 * all objects in the model. 
	 * <p>
	 * Its job is to perform any initializations needed when
	 * objects are added
	 *
	 */
public class ComponentInstanceInitializer extends EContentAdapter {
	
	public ComponentInstanceInitializer(IDesignerDataModel model) {
		Resource[] resources = model.getResources();
		Check.checkArg(resources != null);
		for (Resource r : resources) {
			setTarget(r);
		}
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		int eventType = notification.getEventType();
		switch (eventType) {
		case Notification.ADD:
			checkObject(notification.getNewValue());
			break;
			
		case Notification.ADD_MANY:
			EList items = (EList) notification.getNewValue();
			for (Iterator iter = items.iterator(); iter.hasNext();) {
				checkObject(iter.next());
			}
			break;
		}
	}
	
	private void checkObject(Object obj) {
		if (obj instanceof EObject) {
			EObject eobj = (EObject) obj;
			IComponentInstance ci = ModelUtils.getComponentInstance(eobj);
			if (ci != null) {
				// if the instance has an IInitializer, call its initialize method
				IInitializer initializer = ModelUtils.getInitializerAdapter(eobj);
				if (initializer != null)
					initializer.initialize(ci.isConfigured());
			}
		}
	}
}
