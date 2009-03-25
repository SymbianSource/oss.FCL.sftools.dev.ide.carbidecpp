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

package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;

import java.util.Arrays;
import java.util.List;

public class TestSetValueCommandExtender implements ISetValueCommandExtender {

	private EObject instance;

	public TestSetValueCommandExtender(EObject componentInstance) {
		this.instance = componentInstance;
	}

	public Command getExtendedCommand(String propertyName, Object newValue, Command setValueCommand) {
		if (propertyName.equals("foo"))
			return setValueCommand.chain(new Command() {
				private org.eclipse.emf.common.command.Command command;
				public boolean canExecute() {
					return true;
				}
				public void execute() {
					IComponentInstance instance = (IComponentInstance) 
						EcoreUtil.getRegisteredAdapter(getEObject(), IComponentInstance.class);
					IDesignerDataModel dataModel = instance.getDesignerDataModel();
					List<EObject> children = Arrays.asList(instance.getChildren());
					command = dataModel.createRemoveComponentInstancesCommand(children);
					if (command.canExecute())
						command.execute();
				}
	
				@Override
				public void redo() {
					command.redo();
				}
	
				@Override
				public void undo() {
					command.undo();
				}
			});
		else
			return setValueCommand;
	}
	
	public EObject getEObject() {
		return instance;
	}

}
