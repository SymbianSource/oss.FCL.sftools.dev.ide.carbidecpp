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

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collections;

public class ClipboardCommandExtenderFactory implements IImplFactory {

	public class ClipboardExtender implements IClipboardCommandExtender {

		private static final String CONTROL_COLLECTION_TYPE = 
			"com.nokia.carbide.uiq.ControlCollection";	//$NON-NLS-1$
		private final EObject instance;

		public ClipboardExtender(EObject instance) {
			this.instance = instance;
		}
		
		private class AssociateReferenceObjectCommand extends AbstractCommand {
			
			private EObject objectToCopy;
			private EditingDomain editingDomain;
			private String objectName;
			private EObject referenceObject;
			
			public AssociateReferenceObjectCommand(EObject objectToCopy, EditingDomain editingDomain) {
				this.objectToCopy = objectToCopy;
				this.editingDomain = editingDomain;
				this.objectName = getObjectName(objectToCopy);
			}

			@Override
			protected boolean prepare() {
				String referenceName = getReferenceName(objectToCopy);
				Check.checkState(referenceName != null);
				IComponentInstance ci = ModelUtils.getComponentInstance(objectToCopy);
				referenceObject = ci.getDesignerDataModel().findByNameProperty(referenceName);
				return referenceObject != null && editingDomain instanceof IEnhancedClipboardProvider;
			}
			
			public void execute() {
				IEnhancedClipboardProvider enhClip = (IEnhancedClipboardProvider) editingDomain;
				enhClip.copyExtraObject(objectName, referenceObject);
			}

			public void redo() {
				// unused
			}
			
			public void undo() {
				// unused
			}

		}
		
		private class PasteAssociatedReferenceObjectCommand extends AbstractCommand {
			
			private IDesignerDataModel model;
			private EObject copiedObject;
			private EditingDomain editingDomain;
			private String objectName;
			private String referenceName;
			private boolean didCopy;
			
			public PasteAssociatedReferenceObjectCommand(IDesignerDataModel model, EObject copiedObject, EditingDomain editingDomain) {
				this.model = model;
				this.copiedObject = copiedObject;
				this.editingDomain = editingDomain;
				this.objectName = getObjectName(copiedObject);
			}

			@Override
			protected boolean prepare() {
				this.referenceName = getReferenceName(copiedObject);
				return objectName != null && editingDomain instanceof IEnhancedClipboardProvider;
			}
			
			public void execute() {
				EObject controlCollection = getControlCollection(model);
				EObject existingRefObj = findChildByName(controlCollection, referenceName);
				if (existingRefObj == null) {
					IEnhancedClipboardProvider enhClip = (IEnhancedClipboardProvider) editingDomain;
					enhClip.pasteExtraObject(objectName, controlCollection, IDesignerDataModel.AT_END);
					didCopy = true;
				}
			}

			public void redo() {
				if (didCopy) {
					EObject controlCollection = getControlCollection(model);
					IEnhancedClipboardProvider enhClip = (IEnhancedClipboardProvider) editingDomain;
					enhClip.pasteExtraObject(objectName, controlCollection, IDesignerDataModel.AT_END);
				}
			}
			
			public void undo() {
				if (didCopy) {
					EObject referenceObject = model.findByNameProperty(referenceName);
					Command command = model.createRemoveComponentInstancesCommand(Collections.singletonList(referenceObject));
					if (command.canExecute())
						command.execute();
				}
			}

		}

		private class RenameLayoutControlsCommand extends AbstractCommand {

			private IDesignerDataModel model;
			private EObject copiedObject;
			private String referenceName;
			
			public RenameLayoutControlsCommand(IDesignerDataModel model, EObject copiedObject) {
				this.model = model;
				this.copiedObject = copiedObject;
			}

			@Override
			protected boolean prepare() {
				referenceName = getReferenceName(copiedObject);
				return referenceName != null;
			}
			
			public void execute() {
				IComponentInstance ci = ModelUtils.getComponentInstance(copiedObject);
				EObject objectInModel = model.findByNameProperty(ci.getName());
				ci = ModelUtils.getComponentInstance(objectInModel);
				ComponentInstanceVisitor.preOrderTraversal(ci.getRootContainer(), 
						new ComponentInstanceVisitor.Visitor() {
							public Object visit(IComponentInstance ci) {
								EObject object = ci.getEObject();
								String refNameToCheck = getReferenceName(object);
								// if it has the reference, but the name does not conform, rename it
								if (referenceName.equals(refNameToCheck) && !ci.getName().startsWith(referenceName)) {
									String name = CreationTool.generateLayoutControlName(model, referenceName);
									IPropertySource ps = ModelUtils.getPropertySource(object);
									ps.setPropertyValue(CreationTool.NAME_PROPERTY, name);
								}
								return null;
							}
				});
			}

			public void redo() {
				execute();
			}
			
			public void undo() {
				// unused
			}

		}

		public Command getExtendedCopyToClipboardCommand(EditingDomain editingDomain, Command command) {
			return command.chain(new AssociateReferenceObjectCommand(instance, editingDomain));
		}

		public EObject findChildByName(EObject parent, String referenceName) {
			IComponentInstance ci = ModelUtils.getComponentInstance(parent);
			EObject[] children = ci.getChildren();
			if (children == null)
				return null;
			for (EObject object : children) {
				if (getObjectName(object).equals(referenceName))
					return object;
			}
			
			return null;
		}
		
		private String getReferenceName(EObject layoutControl) {
			IPropertySource ps = ModelUtils.getPropertySource(layoutControl);
			Object propertyValue = ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
			if (propertyValue != null)
				return propertyValue.toString();
			
			return null;
		}

		public Command getExtendedPasteFromClipboardCommand(EObject owner, EditingDomain editingDomain, Command command) {
			IComponentInstance ownerInstance = ModelUtils.getComponentInstance(owner);
			EObject rootContainer = ownerInstance.getRootContainer();
			if (rootContainer.equals(owner))
				return command;
			
			if (!canContainChild(owner, instance))
				return command.chain(UnexecutableCommand.INSTANCE);

			IDesignerDataModel model = ownerInstance.getDesignerDataModel();
			// ensure we paste the control collection object before the reference
			CompoundCommand compoundCommand = new CompoundCommand();
			PasteAssociatedReferenceObjectCommand pasteAssociatedReferenceObjectCommand = 
				new PasteAssociatedReferenceObjectCommand(model, instance, editingDomain);
			compoundCommand.append(pasteAssociatedReferenceObjectCommand);
			compoundCommand.append(command);
			compoundCommand.append(new RenameLayoutControlsCommand(model, instance));
			return compoundCommand;
		}
		
		private boolean canContainChild(EObject owner, EObject instance2) {
			IComponentInstance ci = ModelUtils.getComponentInstance(instance);
			return UiqUtils.canGroupElementContainChild(owner, ci);
		}

		private String getObjectName(EObject object) {
			IComponentInstance ci = ModelUtils.getComponentInstance(object);
			return ci.getName();
		}
		
		private EObject getControlCollection(IDesignerDataModel model) {
			Object object = ComponentInstanceVisitor.preOrderTraversal(model.getRootContainers(), new Visitor() {
				public Object visit(IComponentInstance ci) {
					if (ci.getComponentId().equals(CONTROL_COLLECTION_TYPE))
						return ci.getEObject();
					return null;
				}
			});
			return ModelUtils.getEObject(object);
		}
		
		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new ClipboardExtender(instance);
	}

}
