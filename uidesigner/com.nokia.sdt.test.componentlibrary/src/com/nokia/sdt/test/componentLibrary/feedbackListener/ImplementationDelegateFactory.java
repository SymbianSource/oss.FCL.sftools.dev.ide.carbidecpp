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
package com.nokia.sdt.test.componentLibrary.feedbackListener;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.adapter.ITargetFeedbackListener;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.StatusHolder;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

import java.util.*;

public class ImplementationDelegateFactory implements IImplFactory {

	private final static Class[] DELEGATE_CLASSES = new Class[] {
		IQueryContainment.class, ILayout.class, ITargetFeedbackListener.class
	};

	private class ContainmentAndDelegateImpl implements IImplementationDelegate, IQueryContainment {
		private final EObject object;

		private ContainmentAndDelegateImpl(EObject object) {
			this.object = object;
		}
		
		public List<Class> getDelegateInterfaces() {
			return Arrays.asList(DELEGATE_CLASSES);
		}

		public List<EObject> getDelegates(Class interfaceType) {
			if (!isApplicableInterface(interfaceType))
				return null;
			
			// return first layout manager child
			IComponentInstance ci = Utilities.getComponentInstance(getEObject());
			EObject[] children = ci.getChildren();
			if (children != null) {
				for (EObject child : children) {
					IComponentInstance childInst = Utilities.getComponentInstance(child);
					if (childInst.getComponentId().equals("com.nokia.test.feedbackLayoutManager"))
						return Collections.singletonList(child);
				}
			}
			return null;
		}

		private boolean isApplicableInterface(Class interfaceType) {
			for (Class cls : DELEGATE_CLASSES) {
				if (interfaceType.isAssignableFrom(cls))
					return true;
			}
			return false;
		}

		public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
			return canContainComponent(child.getComponent(), statusHolder);
		}

		public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
			boolean isLayoutManager = component.getId().equals("com.nokia.test.feedbackLayoutManager");
			if (!isLayoutManager)
				statusHolder.setStatus(Logging.newStatus(null, IStatus.ERROR, "This container must have a layout manager."));
			return isLayoutManager;
		}

		public boolean canRemoveChild(IComponentInstance child) {
			return false;
		}

		public boolean isValidComponentInPalette(IComponent component) {
			return component.getId().equals("com.nokia.test.feedbackLayoutManager");
		}

		public EObject getEObject() {
			return object;
		}
	}

	public Object getImpl(EObject object) {
		return new ContainmentAndDelegateImpl(object);
	}

}
