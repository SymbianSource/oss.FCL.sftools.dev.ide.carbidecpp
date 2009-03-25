/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.uiq.components;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.StatusHolder;

public class ListBoxLayoutGroupImplFactory implements IImplFactory {
	
	public Object getImpl(EObject componentInstance) {
		return new ListBoxLayoutGroupImplementations(componentInstance);
	}
	
	class ListBoxLayoutGroupImplementations implements IQueryContainment, IInitializer {
		
		private static final String LISTBOX_LAYOUT_TYPE = "com.nokia.carbide.uiq.ListBoxLayout"; //$NON-NLS-1$
		
		private EObject eobjectCI;
		
		public ListBoxLayoutGroupImplementations(EObject eobject) {
			this.eobjectCI = eobject;
		}

		public EObject getEObject() {
			return this.eobjectCI;
		}

		/********** IQueryContainment **********/
		public boolean canContainChild(IComponentInstance child,
				StatusHolder statusHolder) {
			return canContainComponent(child.getComponent(), statusHolder);
		}

		public boolean canContainComponent(IComponent component,
				StatusHolder statusHolder) {
			if (!ModelUtils.isOfType(component, LISTBOX_LAYOUT_TYPE)) {
				if (statusHolder != null) {
					statusHolder.setStatus(getContainmentError(component));
				}
				return false;
			}
			return true;
		}

		public boolean canRemoveChild(IComponentInstance child) {
			return true;
		}

		public boolean isValidComponentInPalette(IComponent component) {
			return canContainComponent(component, null);
		}
		
		/********** IInitializer **********/
		public void initialize(boolean isConfigured) {
			if (isConfigured) {
				return;
			}
			IDesignerDataModel ddm = ModelUtils.getModel(getEObject());
			IComponentSet cs = ddm.getComponentSet();
			IComponent listboxLayoutComponent = cs.lookupComponent(LISTBOX_LAYOUT_TYPE);
			Check.checkState(listboxLayoutComponent != null);
			EObject listboxLayout = ddm.createNewComponentInstance(listboxLayoutComponent);
			Check.checkState(listboxLayout != null);
			Command command = ddm.createAddNewComponentInstanceCommand(getEObject(), listboxLayout, IDesignerDataModel.AT_END);
			if (command.canExecute()) {
				command.execute();
			}
		}

		private IStatus getContainmentError(IComponent component) {
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			return Logging.newStatus(null, IStatus.ERROR, MessageFormat.format(
					Messages.getString("CQikListBoxImplFactory.ContainmentError"), //$NON-NLS-1$
					ci.getComponent().getFriendlyName(), component.getFriendlyName()));
		}
	}
}
