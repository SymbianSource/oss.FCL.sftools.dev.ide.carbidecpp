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


package com.nokia.carbide.cpp.uiq.components.layoutComponents;

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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor;
import com.nokia.sdt.datamodel.adapter.IChildCommandExtender;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.StatusHolder;

public class ChildCommandExtenderFactory implements IImplFactory {
	
	class ChildExtender implements IChildCommandExtender, IQueryContainment, ISetValueCommandExtender {
		
		private static final String LAYOUT_CONTROL_TYPE = 
			"com.nokia.carbide.uiq.LayoutControlBase";	//$NON-NLS-1$
		private static final String CONTROL_COLLECTION_TYPE = 
			"com.nokia.carbide.uiq.ControlCollection";	//$NON-NLS-1$
		private static final String CONTROL_COLLECTION_ITEM_BASE_TYPE = 
			"com.nokia.carbide.uiq.ControlCollectionItemBase";	//$NON-NLS-1$
		private static final String LAYOUT_MANAGER_ITEM_BASE_TYPE = 
			"com.nokia.carbide.uiq.LayoutManager"; //$NON-NLS-1$
		public static final String LAYOUTMANAGER_PROPERTY = "layoutManager";	//$NON-NLS-1$
		public static final String TYPE_PROPERTY = "type";	//$NON-NLS-1$
		public static final String ISQIKCONTAINER_ATTR = "is-qikcontainer";	//$NON-NLS-1$
		public static final String ISQIKCONTAINERCONTENT_ATTR = "is-qikcontainer-content";	//$NON-NLS-1$
		public static final String ISLAYOUTMANAGER_ATTR = "is-layout-manager";	//$NON-NLS-1$
		public static final String LAYOUTMANAGERENUM_ATTR = "layout-manager-enum";	//$NON-NLS-1$
		public static final String TRUE_VALUE = "true";	//$NON-NLS-1$
		
		private IComponent lastValue;
	
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
		private IDesignerDataModel model;
		private EObject controlCollection;
	
		public ChildExtender(EObject instance) {
			this.instance = instance;
			model = ModelUtils.getModel(instance);
			Check.checkState(model != null);
		}
		
		/********** IChildCommandExtender **********/
		public Command getExtendedAddNewComponentInstanceCommand(
				EObject owner, Collection<EObject> children, int insertionPosition, Command command) {
			EObject controlCollection = getControlCollection();
			if (controlCollection == null)
				return command;
			
			// check to see if we have any control collection, layout manager and/or layout control items to handle
			boolean hasControlCollectionItems = false;
			boolean hasLayoutManagerItems = false;
			boolean hasLayoutControlItems = false;
			boolean hasQikContainerItems = false;
			for (EObject child : children) {
				if (isControlCollectionItem(child))
					hasControlCollectionItems = true;
				if (isLayoutManagerItem(child))
					hasLayoutManagerItems = true;
				if (isLayoutControlItem(child))
					hasLayoutControlItems = true;
				if (isQikContainerItem(child)) {
					hasQikContainerItems = true;
				}
			}
			
			// if so, create a compound command to handle each separately
			if (hasControlCollectionItems) {
				CompoundCommand compoundCommand = new CompoundCommand(command.getLabel());
				for (EObject child : children) {
					if (ModelUtils.getComponentInstance(child) != null)
						continue;
					
					boolean isControlCollectionItem = isControlCollectionItem(child);
					EObject parent = isControlCollectionItem ? controlCollection : owner;;
					Command addCommand = 
						model.createAddNewComponentInstanceCommand(parent, child, 
								isControlCollectionItem ? IDesignerDataModel.AT_END : insertionPosition);
					if (isControlCollectionItem) {
						String componentId = model.getComponentId(child);
						IComponent component = model.getComponentSet().lookupComponent(componentId);
						addCommand = addCommand.chain(createLayoutControlCommand(child, component, owner, insertionPosition));
					}
					compoundCommand.append(addCommand);
				}
				return compoundCommand.unwrap();
			}
			if (hasLayoutManagerItems) {
				List<EObject> layoutManagersToRemove = new ArrayList<EObject>();
				IComponentInstance instance = ModelUtils.getComponentInstance(getEObject());
				EObject[] existingChildren = instance.getChildren();
				for (EObject existingChild : existingChildren) {
					boolean isExistingChildALayoutManagerItem = isLayoutManagerItem(existingChild);
					if (isExistingChildALayoutManagerItem)
						layoutManagersToRemove.add(existingChild);
				}
				Command removeCommand = 
					model.createRemoveComponentInstancesCommand(layoutManagersToRemove);
				
				UpdatePropertyCommand updatePropertyCommand = new UpdatePropertyCommand(getEObject(), instance, model, 
						LAYOUTMANAGER_PROPERTY, "referenceLayoutManager"); //$NON-NLS-1$
				
				if (removeCommand != null)
					return command.chain(removeCommand.chain(updatePropertyCommand));
			}
			if (hasLayoutControlItems) {
				for (EObject childObject : children) {
					IComponentInstance childCI = ModelUtils.getComponentInstance(childObject);
					if (childCI != null) {
						StatusHolder statusHolder = new StatusHolder();
						boolean canContainChild = canContainChild(childCI, statusHolder);
						if (!canContainChild)
							return UnexecutableCommand.INSTANCE;
					}
				}
			}
			if (hasQikContainerItems) {
				for (EObject childObject : children) {
					if (isQikContainerItem(childObject) && 
							!UiqUtils.canGroupElementContainQikContainer(getEObject(), childObject)) {
						return UnexecutableCommand.INSTANCE;
					}
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
				// create a layout component and add a reference, as long as, there's no
				// reference to it
				IComponentInstance targetCI = ModelUtils.getComponentInstance(targetObject);
				StatusHolder statusHolder = new StatusHolder();
				if (canContainChild(targetCI, statusHolder)) {
					IComponent component = targetCI.getComponent();
					return createLayoutControlCommand(targetObject, component, newOwner, insertionPosition);
				}
			}
			return command;
		}
		
		/********** IQueryContainment **********/
		public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
			if (! (ModelUtils.booleanAttribute(component, ISQIKCONTAINERCONTENT_ATTR) || isValidLayoutManager(component)) ) {
				statusHolder.setStatus(getAttributeError(component));
				return false;
			}
			if (ModelUtils.isOfType(component, LAYOUT_CONTROL_TYPE)) {
				CreationTool creationTool = (CreationTool) component.getAdapter(CreationTool.class);
				EObject layoutOrContainer = UiqUtils.findViewLayoutOrContainer(instance);
				if (layoutOrContainer != null && creationTool != null) {
					final String controlRefValue = creationTool.getControlRefValue();
					Object object = ComponentInstanceVisitor.preOrderTraversal(layoutOrContainer, new Visitor() {
						public Object visit(IComponentInstance ci) {
							EObject curObject = ci.getEObject();
							if (ModelUtils.isInstanceOf(curObject, LAYOUT_CONTROL_TYPE)) {
								IPropertySource ps = ModelUtils.getPropertySource(curObject);
								Object propertyValue = ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
								if (controlRefValue.equals(propertyValue))
									return curObject;
							}
							return null;
						}
					});
					if (object != null) {
						statusHolder.setStatus(getReferenceExistsInLayoutError());
						return false;
					}
				}
			}
			return true;
		}
		
		public boolean canContainChild(final IComponentInstance child, StatusHolder statusHolder) {
			IComponent childComponent = child.getComponent();
			if (! (ModelUtils.booleanAttribute(childComponent, ISQIKCONTAINERCONTENT_ATTR) || isValidLayoutManager(childComponent)) ) {
				if (statusHolder != null) {
					statusHolder.setStatus(getAttributeError(childComponent));
				}
				return false;
			}
			if (ModelUtils.isOfType(childComponent, LAYOUT_CONTROL_TYPE)) {
				EObject layoutOrContainer = UiqUtils.findViewLayoutOrContainer(instance);
				if (layoutOrContainer != null) {
					IPropertySource ps = ModelUtils.getPropertySource(child.getEObject());
					if (ps instanceof IPropertySource) {
						Object pv = ps.getPropertyValue("control");  //$NON-NLS-1$
						final String controlRefValue = pv.toString();
						Object object = ComponentInstanceVisitor.preOrderTraversal(layoutOrContainer, new Visitor() {
							public Object visit(IComponentInstance ci) {
								if (ci == child)
									return null; // don't return this object
								EObject curObject = ci.getEObject();
								if (ModelUtils.isInstanceOf(curObject, LAYOUT_CONTROL_TYPE)) {
									IPropertySource ps = ModelUtils.getPropertySource(curObject);
									Object propertyValue = ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
									if (controlRefValue.equals(propertyValue))
										return curObject;
								}
								return null;
							}
						});
						if (object != null) {
							if (statusHolder != null)
								statusHolder.setStatus(getReferenceExistsInLayoutError());
							return false;
						}
					}
				}
			}
			if (ModelUtils.isOfType(childComponent, CONTROL_COLLECTION_ITEM_BASE_TYPE)) {
				EObject layoutOrContainer = UiqUtils.findViewLayoutOrContainer(instance);
				if (layoutOrContainer != null) {
					final String controlRefValue = child.getName();
					Object object = ComponentInstanceVisitor.preOrderTraversal(layoutOrContainer, new Visitor() {
						public Object visit(IComponentInstance ci) {
							EObject curObject = ci.getEObject();
							if (ModelUtils.isInstanceOf(curObject, LAYOUT_CONTROL_TYPE)) {
								IPropertySource ps = ModelUtils.getPropertySource(curObject);
								Object propertyValue = ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
								if (controlRefValue.equals(propertyValue))
									return curObject;
							}
							return null;
						}
					});
					if (object != null) {
						if (statusHolder != null)
							statusHolder.setStatus(getReferenceExistsInLayoutError());
						return false;
					}
				}
			}
			return true;
		}
		
		public boolean canRemoveChild(IComponentInstance child) {
			if(ModelUtils.booleanAttribute(child.getComponent(), ISQIKCONTAINERCONTENT_ATTR))
				return true;
			else {
				IComponentInstance parent = ModelUtils.getComponentInstance(child.getParent());
				return countChildrenByRequiredAttribute(parent.getChildren(), ISLAYOUTMANAGER_ATTR) > 1;
			}
		}
		
		public boolean isValidComponentInPalette(IComponent component) {
			return ModelUtils.booleanAttribute(component, ISQIKCONTAINERCONTENT_ATTR) || 
				(ModelUtils.readAttribute(component, LAYOUTMANAGERENUM_ATTR) != null && 
						ModelUtils.readAttribute(component, LAYOUTMANAGERENUM_ATTR).length() > 0);
		}
		
		/********** ISetValueCommandExtender **********/
		public org.eclipse.gef.commands.Command getExtendedCommand(
				String propertyName, Object newValue,
				org.eclipse.gef.commands.Command command) {
			if (propertyName.equals(LAYOUTMANAGER_PROPERTY)) {
				if (newValue instanceof String) {	
					IComponentInstance containerInstance = ModelUtils.getComponentInstance(getEObject());
					IPropertySource containerProperties = (IPropertySource) EcoreUtil.getRegisteredAdapter(getEObject(), IPropertySource.class);
					
					// Set containerInstance.layoutManagerProperty.typeProperty
					IPropertySource  layoutManagerProperty = (IPropertySource )containerProperties.getPropertyValue(LAYOUTMANAGER_PROPERTY);
					layoutManagerProperty.setPropertyValue(TYPE_PROPERTY, (String)newValue);
					
					// Create containerInstance.layoutManagerInstance
					IDesignerDataModel dataModel = containerInstance.getDesignerDataModel();
					IComponentSet componentSet = dataModel.getComponentSet();
					IComponent layoutManagerComponent = componentSet.lookupComponent(
							(String)newValue);
					Check.checkState(layoutManagerComponent != null);			

					EObject newLayoutManager = dataModel.createNewComponentInstance(
							layoutManagerComponent);
					Check.checkState(newLayoutManager != null);
					//IComponentInstance newLayoutManagerInstance = ModelUtils.getComponentInstance(newLayoutManager);
					Command command2 = dataModel.createAddNewComponentInstanceCommand(
							getEObject(), newLayoutManager, IDesignerDataModel.IN_FRONT);
					Check.checkState(command2.canExecute());
					command2.execute();
				}
				if (newValue instanceof IComponent) {
					IComponent layoutManagerComponent = (IComponent)newValue;
					if (lastValue == null) {
						EObject[] containerChildren = ModelUtils.getComponentInstance(this.instance).getChildren();
						for (int i=0; i<containerChildren.length; i++) {
							String attribute = ModelUtils.readAttribute(containerChildren[0], ISLAYOUTMANAGER_ATTR);
							if (attribute != null && attribute.length() > 0) {
								IComponentInstance currentLayoutManager = ModelUtils.getComponentInstance(containerChildren[0]);
								
								if (((IComponent)newValue).getId().equals(currentLayoutManager.getComponentId())) {
									return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
								}
							}
						}
					}
					if (newValue.equals(lastValue))
						return null;
					this.lastValue = (IComponent)newValue;
					IComponentInstance containerInstance = ModelUtils.getComponentInstance(getEObject());
					IPropertySource containerProperties = (IPropertySource) EcoreUtil.getRegisteredAdapter(getEObject(), IPropertySource.class);
					
					// Set containerInstance.layoutManagerProperty.typeProperty
					IPropertySource  layoutManagerProperty = (IPropertySource )containerProperties.getPropertyValue(LAYOUTMANAGER_PROPERTY);
					layoutManagerProperty.setPropertyValue(TYPE_PROPERTY, layoutManagerComponent.getId());
					
					// Create containerInstance.layoutManagerInstance
					IDesignerDataModel dataModel = containerInstance.getDesignerDataModel();
					Check.checkState(layoutManagerComponent != null);			

					EObject newLayoutManager = dataModel.createNewComponentInstance(
							layoutManagerComponent);
					Check.checkState(newLayoutManager != null);
					//IComponentInstance newLayoutManagerInstance = ModelUtils.getComponentInstance(newLayoutManager);
					Command command2 = dataModel.createAddNewComponentInstanceCommand(
							getEObject(), newLayoutManager, IDesignerDataModel.IN_FRONT);
					
					DataModelCommandWrapper addNewComponentInstanceCommand = new DataModelCommandWrapper();
					addNewComponentInstanceCommand.setDataModelCommand(command2);
					
					return command.chain(addNewComponentInstanceCommand);
				} else {
					return null;
				}
			} 
			return command;
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

		private boolean isControlCollectionItem(EObject eobject) {
			String componentId = model.getComponentId(eobject);
			if (componentId != null) {
				IComponent component = model.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, CONTROL_COLLECTION_ITEM_BASE_TYPE);
			}
			return false;
		}
		
		private boolean isLayoutManagerItem(EObject object) {
			String componentId = model.getComponentId(object);
			if (componentId != null) {
				IComponent component = model.getComponentSet().lookupComponent(componentId);
				return ModelUtils.isOfType(component, LAYOUT_MANAGER_ITEM_BASE_TYPE);
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
		
		private boolean isQikContainerItem(EObject eobject) {
			String isQikContainer = ModelUtils.readAttribute(eobject, ISQIKCONTAINER_ATTR);
			return Boolean.parseBoolean(isQikContainer);
		}
		
		private Command createLayoutControlCommand(EObject refObject, IComponent referenceComponent, EObject layoutParent, int insPos) {
			IComponent layoutComponent = CreationTool.getLayoutComponent(model, referenceComponent);
			EObject layoutObject = model.createNewComponentInstance(layoutComponent);
			Command addCommand = model.createAddNewComponentInstanceCommand(layoutParent, layoutObject, insPos);
			return addCommand.chain(new SetControlReferenceCommand(layoutObject, refObject));
		}
		
		private int countChildrenByRequiredAttribute(EObject[] children, String requiredAttribute) {
			int count = 0;
			if (children != null) {
				for (EObject child : children) {
					String attribute = ModelUtils.readAttribute(child, requiredAttribute);
					if (attribute != null && attribute.length() > 0)
						count++;
				}
			}
			return count;
		}
		
		private boolean isValidLayoutManager(IComponent component) {
			String isLayoutManager = ModelUtils.readAttribute(component, ISLAYOUTMANAGER_ATTR);
			if (isLayoutManager != null && isLayoutManager.equals(TRUE_VALUE)) {
				IComponentInstance ci = ModelUtils.getComponentInstance(instance);
				if (ci != null) {
					EObject[] children = ci.getChildren();
					if (children != null) {
						return countChildrenByRequiredAttribute(children, ISLAYOUTMANAGER_ATTR) < 2;
					}
				}
			}
			return false;
		}
		
		private IStatus getAttributeError(IComponent component) {
			return Logging.newStatus(null, IStatus.ERROR,
					MessageFormat.format(Messages.getString("ChildCommandExtenderFactory.AttributeError"),	//$NON-NLS-1$
						new Object[] { component.getFriendlyName() }));
		}

		private IStatus getReferenceExistsInLayoutError() {
			return Logging.newStatus(null, IStatus.ERROR,
					Messages.getString("ChildCommandExtenderFactory.RefExistsInLayoutError"));	//$NON-NLS-1$
		}
		
		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new ChildExtender(instance);
	}
}
