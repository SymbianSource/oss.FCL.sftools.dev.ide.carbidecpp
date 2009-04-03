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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

public class ViewPageInitializerFactory implements IImplFactory {

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new ViewPageInitializer(componentInstance);
	}

	static class ViewPageInitializer implements IInitializer {
		
		protected IDesignerDataModel dataModel;
		protected IComponent systemCQikContainerItemComponent;
		private EObject componentInstance;
		public static final String SYSTEM_CQIKCONTAINER_ITEM_COMPONENT_ID = "com.nokia.carbide.uiq.CQikContainer"; //$NON-NLS-1$

		/**
		 * @param componentInstance
		 */
		public ViewPageInitializer(EObject componentInstance) {
			this.componentInstance = componentInstance;
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IInitializer#initialize()
		 */
		public void initialize(boolean isConfigured) {			
			if (isConfigured) return;
			IPropertySource ps = ModelUtils.getPropertySource(componentInstance);
			
			IComponentInstance ci = ModelUtils.getComponentInstance(componentInstance);
			dataModel = ci.getDesignerDataModel();
			IComponentSet componentSet = dataModel.getComponentSet();
			systemCQikContainerItemComponent = componentSet.lookupComponent(
					SYSTEM_CQIKCONTAINER_ITEM_COMPONENT_ID);
			Check.checkState(systemCQikContainerItemComponent != null);			

			EObject containerItem = dataModel.createNewComponentInstance(
					systemCQikContainerItemComponent);
			Check.checkState(containerItem != null);

			Command command = dataModel.createAddNewComponentInstanceCommand(
					componentInstance, containerItem, IDesignerDataModel.AT_END);
			Check.checkState(command.canExecute());
			command.execute();			
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return componentInstance;
		}
		
	}
}
