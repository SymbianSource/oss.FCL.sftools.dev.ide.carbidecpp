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
package com.nokia.sdt.component.symbian.componentAttachment;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentAttachment;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;

import java.util.Iterator;
import java.util.List;

public class ComponentAttachmentNotifier extends EContentAdapter {
	private IDesignerDataModelListener listener = null;
	
	public ComponentAttachmentNotifier(final IDesignerDataModel model) {
		Resource[] resources = model.getResources();
		Check.checkArg(resources != null);
		for (Resource r : resources) {
			setTarget(r);
		}
		listener = new DesignerDataModelAdapter() {
			@Override
			public void dataModelInitialized(IDesignerDataModel initializedModel) {
				if (!initializedModel.equals(model))
					return;
				
				// notify objects in the model
				EObject[] containers = model.getRootContainers();
				for (int i = 0; i < containers.length; i++) {
					notifyObjectCreated(containers[i]);
				}
				DesignerDataModelNotifier.instance().removeListener(listener);
				listener = null;
			}
		};
		DesignerDataModelNotifier.instance().addListener(listener);
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		int eventType = notification.getEventType();
		List items;
		switch (eventType) {
		case Notification.ADD:
			notifyObjectCreated(notification.getNewValue());
			break;
			
		case Notification.ADD_MANY:
			items = (List) notification.getNewValue();
			for (Iterator iter = items.iterator(); iter.hasNext();) {
				notifyObjectCreated(iter.next());
			}
			break;
			
		case Notification.REMOVE:
			notifyObjectDeleted(notification.getOldValue());
			break;
			
		case Notification.REMOVE_MANY:
			items = (List) notification.getOldValue();
			for (Iterator iter = items.iterator(); iter.hasNext();) {
				notifyObjectDeleted(iter.next());
			}
			break;
		}
	}
	
	private void notifyObjectCreated(Object obj) {
		if (obj instanceof EObject) {
			EObject eobj = (EObject) obj;
			IComponentInstance ci = ModelUtils.getComponentInstance(eobj);
			if (ci != null) {
				// notify object
				IComponent component = ci.getComponent();
				if (component != null) {
					IComponentAttachment attachment = 
						(IComponentAttachment) component.getAdapter(IComponentAttachment.class);
					if (attachment != null) {
						attachment.objectCreated(eobj);
					}
				}
				// then notify children
				EObject[] children = ci.getChildren();
				if (children != null) {
					for (int i = 0; i < children.length; i++) {
						EObject childObj = children[i];
						notifyObjectCreated(childObj);
					}
				}
			}
		}
	}

	private void notifyObjectDeleted(Object obj) {
		if (obj instanceof EObject) {
			EObject eobj = (EObject) obj;
			IComponentInstance ci = ModelUtils.getComponentInstance(eobj);
			if (ci != null) {
				// notify children
				EObject[] children = ci.getChildren();
				if (children != null) {
					for (int i = 0; i < children.length; i++) {
						EObject childObj = children[i];
						notifyObjectDeleted(childObj);
					}
				}
				// then notify object
				IComponent component = ci.getComponent();
				if (component != null) {
					IComponentAttachment attachment = 
						(IComponentAttachment) component.getAdapter(IComponentAttachment.class);
					if (attachment != null) {
						attachment.objectDeleted(eobj);
					}
				}
			}
		}
	}
}
