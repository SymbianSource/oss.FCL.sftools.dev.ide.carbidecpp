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
/* START_USECASES: CU10 END_USECASES */

package com.nokia.carbide.cpp.uiq.components;

import java.util.ArrayList;
import java.util.List;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.properties.CompoundPropertySource;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.adapter.IImplementationDelegate;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.adapter.ITargetFeedbackListener;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.ui.views.properties.IPropertySource;

public class CQikContainerImplFactory implements IImplFactory {
	
	static class ImplFact implements IInitializer,
									IImplementationDelegate {
		
		private static final String DEF_COMPONENT_ID = "com.nokia.carbide.uiq.RowLayoutManager"; //$NON-NLS-1$
		
		private EObject eobject;
		
		public ImplFact(EObject componentInstance) {
			this.eobject = componentInstance;
		}
		
		/****** IInitializer ******/
		public void initialize(boolean isConfigured) {
			if(isConfigured) return;
			// add the default child
			IComponentInstance componentInstance = ModelUtils.getComponentInstance(getEObject());
			IDesignerDataModel dataModel = componentInstance.getDesignerDataModel();
			Check.checkContract(dataModel != null);
			IComponentSet componentSet = dataModel.getComponentSet();
			Check.checkContract(componentSet != null);
			IComponent component = componentSet.lookupComponent(DEF_COMPONENT_ID);
			Check.checkContract(component != null);
			EObject child = dataModel.createNewComponentInstance(component);
			Command command =
				dataModel.createAddNewComponentInstanceCommand(getEObject(), child, 0);
			if (command.canExecute()) {
				command.execute();
				IPropertySource instanceProperties = ModelUtils.getPropertySource(getEObject());
				IPropertySource childProperties = ModelUtils.getPropertySource(child);
				CompoundPropertySource layoutManagerProperty = (CompoundPropertySource)instanceProperties.getPropertyValue("layoutManager"); //$NON-NLS-1$
				layoutManagerProperty.setPropertyValue("referenceLayoutManager", childProperties.getPropertyValue("name")); //$NON-NLS-1$
			}
		}
		
		/****** IImplementationDelegate ******/
		public List<Class> getDelegateInterfaces() {
			List<Class> interfaces = new ArrayList<Class>();
			interfaces.add(ILayout.class);
			interfaces.add(ITargetFeedbackListener.class);
			return interfaces;
		}
		
		public List<EObject> getDelegates(Class interfaceType) {
			List<EObject> objects = new ArrayList<EObject>();
			String interfaceTypeCanonicalName = interfaceType.getCanonicalName();
			if ((interfaceTypeCanonicalName == ILayout.class.getCanonicalName()) || 
					(interfaceTypeCanonicalName == ITargetFeedbackListener.class.getCanonicalName())) {
				IComponentInstance componentInstance = ModelUtils.getComponentInstance(eobject);
				EObject[] children = componentInstance.getChildren();
				for (EObject child : children) {
					if (isLayoutManager(child))
						objects.add(child);
				}
			}
			return objects;
		}
		
		public EObject getEObject() {
			return eobject;
		}
		
		private boolean isLayoutManager(EObject object) {
			IDesignerDataModel model = ModelUtils.getModel(eobject);
			Check.checkState(model != null);
			String componentId = model.getComponentId(object);
			if (componentId != null) {
				IComponent component = model.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, DEF_COMPONENT_ID);
			}
			return false;
		}
	}
	
	public Object getImpl(EObject componentInstance) {
		return new ImplFact(componentInstance);
	}
}
