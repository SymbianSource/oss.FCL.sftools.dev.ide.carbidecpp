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

public class CEikChoiceListInitializerFactory implements IImplFactory{

	public Object getImpl(EObject componentInstance) {
		return new CEikChoiceListInitializer(componentInstance);
	}

	static class CEikChoiceListInitializer implements IInitializer {
		
		protected IDesignerDataModel dataModel;
		protected IComponent ChoiceListItem;
		private EObject componentInstance;
		public static final String CHOICELISTITEM_ID = "com.nokia.carbide.uiq.ChoiceListItem"; //$NON-NLS-1$

		/**
		 * @param componentInstance
		 */
		public CEikChoiceListInitializer(EObject componentInstance) {
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
			ChoiceListItem = componentSet.lookupComponent(
					CHOICELISTITEM_ID);
			Check.checkState(ChoiceListItem != null);			

			EObject item = dataModel.createNewComponentInstance(
					ChoiceListItem);
			Check.checkState(item != null);
						
			if(!ModelUtils.getComponentInstance(componentInstance).getComponentId().contains("_Layout")){
				Command command = dataModel.createAddNewComponentInstanceCommand(
						componentInstance, item, IDesignerDataModel.AT_END);
				Check.checkState(command.canExecute());
				command.execute();
			}
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return componentInstance;
		}
		
	}	
	
}
