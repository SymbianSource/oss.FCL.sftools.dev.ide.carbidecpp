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
package com.nokia.carbide.cpp.uiq.components.choiceList;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IChildCommandExtender;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;


public class ChildCommandExtenderFactory implements IImplFactory{
	
	class ChildExtender implements IChildCommandExtender {

		private class SetControlReferenceCommand extends AbstractCommand {
			private EObject newObject;
			private EObject referenceObject;
			
			public SetControlReferenceCommand(EObject newObject, EObject referenceObject) {
				this.newObject = newObject;
				this.referenceObject = referenceObject;
			}

			public void execute() {
				IPropertySource ps = ModelUtils.getPropertySource(newObject);
				String referenceName = ModelUtils.getComponentInstance(referenceObject).getName();
				ps.setPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME, referenceName);
				ps.setPropertyValue(CreationTool.NAME_PROPERTY, 
						CreationTool.generateLayoutControlName(model, referenceName));
			}

			public void redo() {}
			
			@Override
			public boolean canUndo(){
				return true;
			}
			
			@Override
			public void undo() {
			}

			@Override
			protected boolean prepare() {
				return true;
			}
			
		}
		
		private EObject instance;
		private EObject choiceList;
		private EObject controlCollection;
		private IDesignerDataModel model;
		private static final String CHOICE_LIST_ITEM_TYPE = "com.nokia.carbide.uiq.ChoiceListItem"; //$NON-NLS-1$

		public ChildExtender(EObject instance) {
			this.instance = instance;
			model = ModelUtils.getModel(instance);
			Check.checkState(model != null);
		}
		/********** IChildCommandExtender **********/
		public Command getExtendedAddNewComponentInstanceCommand(
				EObject owner, Collection<EObject> children, int insertionPosition, Command command) {
			
			IComponentInstance ownerComponentInstance = ModelUtils.getComponentInstance(owner);
			if (ownerComponentInstance.getComponentId().equals("com.nokia.carbide.uiq.CEikChoiceList")) {
//				System.out.println("*******REJECTED********");	
				return command;
			}
			// check to see if we have any choice list item to handle
			boolean hasChoiceListItems = false;
			for (EObject child : children) 
				if (isChoiceListItem(child))
					hasChoiceListItems = true;

			if(hasChoiceListItems){
				CompoundCommand compoundCommand = new CompoundCommand(command.getLabel());
				for (EObject child : children) {
					if(isChoiceListItem(child)){
//						System.out.println("***child:" + child);
//						System.out.println("***Owner:" + owner);
						
						IPropertySource choiceListReferencePropertySource = ModelUtils.getPropertySource(owner);
						String choiceListName = choiceListReferencePropertySource.getPropertyValue("control").toString();
						IComponentInstance choiceList = ModelUtils.lookupReference(ModelUtils.getModel(owner), choiceListName);
						EObject parent = choiceList.getEObject();
						Command addCommand = 
							model.createAddNewComponentInstanceCommand(parent, child, insertionPosition);
						compoundCommand.append(addCommand);
					}
				}
				return compoundCommand.unwrap();
			}

			return command;	
		}

		public Command getExtendedRemoveComponentInstancesCommand(List<EObject> objectsToRemove, Command command) {
			// no need to override this command
			return command;
		}

		public Command getExtendedMoveComponentInstanceCommand(
				EObject targetObject, EObject newOwner, int insertionPosition, Command command) {
			if (isChoiceListItem(targetObject)) {
				// if moving a control collection item (from the control collection), 
				// create a layout component and add a reference
				IComponent component = ModelUtils.getComponentInstance(targetObject).getComponent();
				return createLayoutControlCommand(targetObject, component, newOwner, insertionPosition);
			}
			return command;
		}

		private Command createLayoutControlCommand(EObject refObject, IComponent referenceComponent, EObject layoutParent, int insPos) {
			IComponent layoutComponent = CreationTool.getLayoutComponent(model, referenceComponent);
			if (layoutComponent == null) {
				return null;
			}
			EObject layoutObject = model.createNewComponentInstance(layoutComponent);
			Command addCommand = model.createAddNewComponentInstanceCommand(layoutParent, layoutObject, insPos);
			return addCommand.chain(new SetControlReferenceCommand(layoutObject, refObject));
		}

		private boolean isChoiceListItem(EObject object) {
			String componentId = model.getComponentId(object);
			if (componentId != null) {
				IComponent component = model.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, CHOICE_LIST_ITEM_TYPE);
			}
			
			return false;
		}

		public EObject getEObject() {
			return instance;
		}

	}

		public Object getImpl(EObject instance) {
			return new ChildExtender(instance);
		}
}
