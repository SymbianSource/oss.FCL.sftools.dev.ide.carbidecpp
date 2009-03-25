/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IValidateContainment;
import com.nokia.cpp.internal.api.utils.core.Check;

class ChangeQueryChildCommand extends Command {
	private org.eclipse.emf.common.command.Command removeChildCommand;
	private org.eclipse.emf.common.command.Command createChildCommand;
	private EObject queryInstance;
	
	// Note, can't use compound command, because create command would fail canExecute
	// until remove command has executed, since queryInstance can only contain a single child
	public ChangeQueryChildCommand(EObject queryInstance, Object newTypeProperty,
											Map<Object, String> typeToComponentIdMap, 
											String defaultComponentId, int index) {
		this.queryInstance = queryInstance;
		IComponentInstance componentInstance = Utilities.getComponentInstance(queryInstance);
		IDesignerDataModel dataModel = componentInstance.getDesignerDataModel();
		EObject[] children = componentInstance.getChildren();
		Check.checkState((children != null) && (children.length > 0));
		EObject currentChild = children[index];
		IComponentInstance currentChildInstance = Utilities.getComponentInstance(currentChild);
		String currentChildId = currentChildInstance.getComponentId();
		String newComponentId = newTypeProperty != null ? 
				typeToComponentIdMap.get(newTypeProperty) : defaultComponentId;
		if (!currentChildId.equals(newComponentId)) {
			IComponentSet componentSet = dataModel.getComponentSet();
			IComponent newComponent = componentSet.lookupComponent(newComponentId);
			EObject newChild = dataModel.createNewComponentInstance(newComponent);
			createChildCommand = dataModel.createAddNewComponentInstanceCommand(queryInstance, newChild, index);
			List<EObject> currentChildList = Collections.singletonList(currentChild);
			removeChildCommand = dataModel.createRemoveComponentInstancesCommand(currentChildList);
		}
	}
	
	public boolean canExecute() {
		return (removeChildCommand == null) || removeChildCommand.canExecute();
	}

	private void enableContainmentValidation(boolean enable) {
		IValidateContainment validator = 
			(IValidateContainment) EcoreUtil.getRegisteredAdapter(queryInstance, IValidateContainment.class);
		if (validator != null)
			validator.setEnabled(enable);
	}

	public void execute() {
		if ((removeChildCommand != null) && (createChildCommand != null)) {
			enableContainmentValidation(false);
			removeChildCommand.execute();
			if (createChildCommand.canExecute())
				createChildCommand.execute();
			enableContainmentValidation(true);
		}
	}

	public void redo() {
		if ((removeChildCommand != null) && (createChildCommand != null)) {
			enableContainmentValidation(false);
			removeChildCommand.redo();
			createChildCommand.redo();
			enableContainmentValidation(true);
		}
	}

	public void undo() {
		if ((removeChildCommand != null) && (createChildCommand != null)) {
			enableContainmentValidation(false);
			// note reverse order for undo
			createChildCommand.undo();
			removeChildCommand.undo();
			enableContainmentValidation(true);
		}
	}
}