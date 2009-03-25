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
package com.nokia.carbide.cpp.uiq.components.slot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor;
import com.nokia.sdt.datamodel.adapter.IChildCommandExtender;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.StatusHolder;


public class ItemSlotChildCommandExtenderFactory implements IImplFactory {

	class ChildExtender implements IChildCommandExtender, IQueryContainment {
		
		private static final String CONTROL_COLLECTION_TYPE = 
			"com.nokia.carbide.uiq.ControlCollection"; //$NON-NLS-1$
		private static final String CONTROL_COLLECTION_ITEM_BASE_TYPE = 
			"com.nokia.carbide.uiq.ControlCollectionItemBase"; //$NON-NLS-1$
		private static final String LAYOUT_CONTROL_TYPE = 
			"com.nokia.carbide.uiq.LayoutControlBase";	//$NON-NLS-1$
		public static final String ISSLOTCONTENT_ATTR = "is-slot-content";	//$NON-NLS-1$
		
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
		private EObject controlCollection;
		private IDesignerDataModel model;
		
		public ChildExtender(EObject instance) {
			this.instance = instance;
			model = ModelUtils.getModel(instance);
			Check.checkState(model != null);
		}
		
		/********** IChildCommandExtender **********/
		public Command getExtendedAddNewComponentInstanceCommand(EObject owner,
				Collection<EObject> children, int insertionPosition, Command command) {
			EObject controlCollection = getControlCollection();
			if (controlCollection == null)
				return command;
			
			// check to see if we have any control collection and/or layout control items to handle
			boolean hasControlCollectionItems = false;
			boolean hasLayoutControlItems = false;
			for (EObject child : children) {
				if (isControlCollectionItem(child))
					hasControlCollectionItems = true;
				if (isLayoutControlItem(child))
					hasLayoutControlItems = true;
			}
			
			// if so, create a compound command to handle each separately
			if (hasControlCollectionItems) {
				CompoundCommand compoundCommand = new CompoundCommand(command.getLabel());
				for (EObject child : children) {
					if (ModelUtils.getComponentInstance(child) != null) {
						continue;
					}
					boolean isControlCollectionItem = isControlCollectionItem(child);
					// control collection items are added to the control collection
					EObject parent = isControlCollectionItem ? controlCollection : owner;
					Command addCommand = 
						model.createAddNewComponentInstanceCommand(parent, child, insertionPosition);
					if (isControlCollectionItem) {
						String componentId = model.getComponentId(child);
						IComponent component = model.getComponentSet().lookupComponent(componentId);
						addCommand = addCommand.chain(createLayoutControlCommand(child, component, owner, insertionPosition));
					}
					compoundCommand.append(addCommand);
				}
				return compoundCommand.unwrap();
			}
			if (hasLayoutControlItems) {
				for (EObject childObject : children) {
					IComponentInstance childCI = ModelUtils.getComponentInstance(childObject);
					if (childCI != null && !UiqUtils.canGroupElementContainChild(getEObject(), childCI)) {
						return UnexecutableCommand.INSTANCE;
					}
				}
				Command removeCommand = getRemovePreviousChildrenCommand();
				if (removeCommand != null) {
					return command.chain(removeCommand);
				}
			}
			
			return command;
		}
		
		public Command getExtendedRemoveComponentInstancesCommand(List<EObject> objectsToRemove, Command command) {
			// no need to override this command
			return command;
		}
		
		public Command getExtendedMoveComponentInstanceCommand(
				EObject targetObject, EObject newOwner, int insertionPosition, Command command) {
			if (isControlCollectionItem(targetObject)) {
				// if moving a control collection item (from the control collection), 
				// create a layout component and add a reference
				IComponent component = ModelUtils.getComponentInstance(targetObject).getComponent();
				return createLayoutControlCommand(targetObject, component, newOwner, insertionPosition);
			}
			if (isLayoutControlItem(targetObject)) {
				Command removeCommand = getRemovePreviousChildrenCommand();
				if (removeCommand != null) {
					return command.chain(removeCommand);
				}
			}
			return command;
		}
		
		/********** IQueryContainment **********/
		public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
			if (!ModelUtils.booleanAttribute(child.getComponent(), ISSLOTCONTENT_ATTR)) {
				if (statusHolder != null) {
					statusHolder.setStatus(getAttributeError(child.getComponent()));
				}
				return false;
			}
			if (!UiqUtils.canGroupElementContainChild(getEObject(), child)) {
				if (statusHolder != null) {
					statusHolder.setStatus(getReferenceExistsInLayoutError());
				}
				return false;
			}
			return true;
		}
		
		public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
			if (!ModelUtils.booleanAttribute(component, ISSLOTCONTENT_ATTR)) {
				statusHolder.setStatus(getAttributeError(component));
				return false;
			}
			if (!UiqUtils.canGroupElementContainComponent(getEObject(), component)) {
				statusHolder.setStatus(getReferenceExistsInLayoutError());
				return false;
			}
			return true;
		}
		
		public boolean canRemoveChild(IComponentInstance child) {
			if(ModelUtils.booleanAttribute(child.getComponent(), ISSLOTCONTENT_ATTR))
				return true;
			else {
				return false;
			}
		}

		public boolean isValidComponentInPalette(IComponent component) {
			return ModelUtils.booleanAttribute(component, ISSLOTCONTENT_ATTR);
		}
		
		private Command createLayoutControlCommand(EObject refObject, IComponent referenceComponent, EObject layoutParent, int insPos) {
			IComponent layoutComponent = CreationTool.getLayoutComponent(model, referenceComponent);
			EObject layoutObject = model.createNewComponentInstance(layoutComponent);
			Command addCommand = model.createAddNewComponentInstanceCommand(layoutParent, layoutObject, insPos);
			return addCommand.chain(new SetControlReferenceCommand(layoutObject, refObject));
		}
		
		private boolean isControlCollectionItem(EObject object) {
			String componentId = model.getComponentId(object);
			if (componentId != null) {
				IComponent component = model.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, CONTROL_COLLECTION_ITEM_BASE_TYPE);
			}
			
			return false;
		}
		
		private boolean isLayoutControlItem(EObject eobject) {
			String componentId = model.getComponentId(eobject);
			if (componentId != null) {
				IComponent component = model.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, LAYOUT_CONTROL_TYPE);
			}
			return false;
		}
		
		private EObject getControlCollection() {
			if (controlCollection == null) {
				Object object = ComponentInstanceVisitor.preOrderTraversal(model.getRootContainers(), new Visitor() {
					public Object visit(IComponentInstance ci) {
						if (ci.getComponentId().equals(CONTROL_COLLECTION_TYPE))
							return ci.getEObject();
						return null;
					}
				});
				controlCollection = ModelUtils.getEObject(object);
			}
			return controlCollection;
		}
		
		private IStatus getAttributeError(IComponent component) {
			return Logging.newStatus(null, IStatus.ERROR,
					MessageFormat.format(Messages.getString("ItemSlotChildCommandExtenderFactory.AttributeError"), //$NON-NLS-1$
							new Object[] { component.getFriendlyName() }));
		}
		
		private IStatus getReferenceExistsInLayoutError() {
			return Logging.newStatus(null, IStatus.ERROR, Messages.getString("ItemSlotChildCommandExtenderFactory.7")); //$NON-NLS-1$
		}
		
		private Command getRemovePreviousChildrenCommand() {
			Command removeCommand = null;
			List<EObject> referencesToRemove = new ArrayList<EObject>();
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			EObject[] existingChildren = ci.getChildren();
			for (EObject existingChild : existingChildren) {
				referencesToRemove.add(existingChild);
			}
			if(!referencesToRemove.isEmpty()) {
				removeCommand = model.createRemoveComponentInstancesCommand(referencesToRemove);
			}
			return removeCommand;
		}
		
		public EObject getEObject() {
			return instance;
		}
	}
	
	public Object getImpl(EObject instance) {
		return new ChildExtender(instance);
	}
}
