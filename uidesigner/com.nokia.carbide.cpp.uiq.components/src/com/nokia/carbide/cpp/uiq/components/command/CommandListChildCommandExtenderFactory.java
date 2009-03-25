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
package com.nokia.carbide.cpp.uiq.components.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.components.layoutComponents.UpdatePropertyCommand;
import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
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

import com.nokia.sdt.component.adapter.IImplFactory;

public class CommandListChildCommandExtenderFactory implements IImplFactory{

	class ChildExtender implements  IQueryContainment {

		private final static String CONTAINMENT_ERROR = 
			"A command list can't contain objects of type ''{0}''";
		private static final String COMMAND_LIST_CHILD_BASE_TYPE = "com.nokia.carbide.uiq.CommandListChildBase"; //$NON-NLS-1$
		
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

		public ChildExtender(EObject instance) {
			this.instance = instance;
			model = ModelUtils.getModel(instance);
			Check.checkState(model != null);
		}

		/********** IQueryContainment **********/
		public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
			if (! isCommandListChild(component)  ) { //$NON-NLS-1$
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

		public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
			return canContainComponent(child.getComponent(), statusHolder);
		}
		
		
		public boolean isValidComponentInPalette(IComponent component) {
			return isCommandListChild(component); //$NON-NLS-1$
		}
		

		private boolean isCommandListChild(IComponent component) {
				return ModelUtils.isOfType(component, COMMAND_LIST_CHILD_BASE_TYPE);
		}

		
		private IStatus getContainmentError(IComponent component) {
			return Logging.newStatus(null, IStatus.ERROR, 
				MessageFormat.format(CONTAINMENT_ERROR, new Object[] { component.getFriendlyName() }));
		}

		public EObject getEObject() {
			return instance;
		}

	}
	
	public Object getImpl(EObject instance) {
		return new ChildExtender(instance);
	}
	
}
