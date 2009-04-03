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


package com.nokia.carbide.cpp.uiq.components.controlCollection;

import com.nokia.carbide.cpp.uiq.components.controlCollection.ControlCollectionAdapterFactory.CreationTool;
import com.nokia.carbide.cpp.uiq.components.layoutComponents.ComponentLibrary;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

public class ChildCommandExtenderFactory implements IImplFactory {
	
	class ChildExtender implements IChildCommandExtender {
	
		private EObject instance;
		private IDesignerDataModel model;
	
		public ChildExtender(EObject instance) {
			this.instance = instance;
			model = ModelUtils.getModel(instance);
			Check.checkState(model != null);
		}
		
		public Command getExtendedAddNewComponentInstanceCommand(
				EObject owner, Collection<EObject> children, int insertionPosition, Command command) {
			// no need to override this command
//			System.out.println("ControlCollection.ChildCommandExtenderFactory.getExtendedAddNewComponentInstanceCommand");
			return command;
		}
		
		public Command getExtendedMoveComponentInstanceCommand(
				EObject targetObject, EObject newOwner, int insertionPosition, Command command) {
			// no need to override this command
//			System.out.println("ControlCollection.ChildCommandExtenderFactory.getExtendedMoveComponentInstanceCommand");
			return command;
		}

		public Command getExtendedRemoveComponentInstancesCommand(
				List<EObject> objectsToRemove, Command command) {
			// for each control collection object removed, remove all layout controls referencing it
			final List<EObject> additionalObjectsToRemove = new ArrayList();
			for (EObject object : objectsToRemove) {
				IComponentInstance compInstance = ModelUtils.getComponentInstance(object);
				if (compInstance.getParent().equals(instance)) {
					final String refName = compInstance.getName();
					ComponentInstanceVisitor.preOrderTraversal(model.getRootContainers(), new Visitor() {
						public Object visit(IComponentInstance ci) {
							EObject curObject = ci.getEObject();
							if (ModelUtils.isInstanceOf(curObject, ComponentLibrary.LAYOUT_CONTROL_BASE)) {
								IPropertySource ps = ModelUtils.getPropertySource(curObject);
								String curRefName = (String) ps.getPropertyValue(CreationTool.REFERENCE_PROPERTY_NAME);
								if (curRefName.equals(refName)) {
									additionalObjectsToRemove.add(curObject);
								}
							}
							return null;
						}
					});
				}
			}
			if (!additionalObjectsToRemove.isEmpty()) {
				command = model.createRemoveComponentInstancesCommand(additionalObjectsToRemove).chain(command);
			}
			return command;
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
