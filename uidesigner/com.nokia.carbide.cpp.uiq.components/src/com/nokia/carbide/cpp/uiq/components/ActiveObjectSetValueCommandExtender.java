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

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;


public class ActiveObjectSetValueCommandExtender implements IImplFactory{
	
	class ActiveObjectExtender implements  ISetValueCommandExtender {
		
		private static final String CONTROL_COLLECTION = 
			"com.nokia.carbide.uiq.ControlCollection";	//$NON-NLS-1$
		private static final String PROGRESS_INFO = 
			"com.nokia.carbide.uiq.CEikProgressInfo";	//$NON-NLS-1$
		public static final String PROGRESS_INFO_PROPERTY = "progressInfo";	//$NON-NLS-1$
		public static final String ADD_PROGRESS_INFO_KEY = "addNewProgressInfo";	//$NON-NLS-1$
		
		private Object lastValue;

		
		private Command createLayoutControlCommand(EObject refObject, IComponent referenceComponent, EObject layoutParent, int insPos) {
			IComponent layoutComponent = CreationTool.getLayoutComponent(model, referenceComponent);
			EObject layoutObject = model.createNewComponentInstance(layoutComponent);
			Command addCommand = model.createAddNewComponentInstanceCommand(layoutParent, layoutObject, insPos);
			return addCommand.chain(new SetControlReferenceCommand(layoutObject, refObject));
		}
		
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
				
				ps.resetPropertyValue("progressInfoHidden");
				ps.setPropertyValue("progressInfoHidden", referenceName);
				
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
	
		public ActiveObjectExtender(EObject instance) {
			this.instance = instance;
			model = ModelUtils.getModel(instance);
			Check.checkState(model != null);
		}
		
		
		/********** ISetValueCommandExtender **********/
		public org.eclipse.gef.commands.Command getExtendedCommand(
				String propertyName, Object newValue,
				org.eclipse.gef.commands.Command command) {
				
			if ( propertyName.equals(PROGRESS_INFO_PROPERTY) ) {
				if(newValue instanceof Object && newValue.toString().equals(ADD_PROGRESS_INFO_KEY)){
					if (newValue.equals(lastValue))
						return command;
					lastValue = newValue;
					EObject controlcollection = getControlCollection();
	
					IComponentInstance containerInstance = ModelUtils.getComponentInstance(getEObject());				
					IDesignerDataModel dataModel = containerInstance.getDesignerDataModel();
					IComponent progressinfoicomponent = dataModel.getComponentSet().lookupComponent(PROGRESS_INFO);
					EObject  progressinfoeobject = dataModel.createNewComponentInstance(progressinfoicomponent);
					
					Command newcommand = dataModel.createAddNewComponentInstanceCommand(
							controlcollection, progressinfoeobject, IDesignerDataModel.AT_END);

					Check.checkState(newcommand.canExecute());
					newcommand.execute();
					
					Command aux = new SetControlReferenceCommand(getEObject(), progressinfoeobject);

					Check.checkState(aux.canExecute());
					aux.execute();

					newcommand.chain(aux);					
					
					DataModelCommandWrapper addNewComponentInstanceCommand = new DataModelCommandWrapper();
					addNewComponentInstanceCommand.setDataModelCommand(newcommand);					

					return command.chain(addNewComponentInstanceCommand);
				}
			} 
			return command;
		}
		
		private EObject getControlCollection() {
			if (controlCollection == null) {
				Object object = ComponentInstanceVisitor.preOrderTraversal(model.getRootContainers(), new Visitor() {
					public Object visit(IComponentInstance ci) {
						if (ci.getComponentId().equals(CONTROL_COLLECTION))
							return ci.getEObject();
						return null;
					}
				});
				controlCollection = ModelUtils.getEObject(object);
			}
			return controlCollection;
		}
		
		public EObject getEObject() {
			return instance;
		}

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new ActiveObjectExtender(instance);
	}

}
