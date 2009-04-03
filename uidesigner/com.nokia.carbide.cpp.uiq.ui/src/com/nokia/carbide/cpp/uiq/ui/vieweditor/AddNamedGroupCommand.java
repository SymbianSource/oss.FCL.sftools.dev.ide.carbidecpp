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
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
/**
 * This class executes all the commands that are necessary to get the promotion of a command
 * to a new named group. It sets its properties (group references), this are the ones corresponding
 * to the command that is been deleted (promoted).
 *
 */
public class AddNamedGroupCommand extends AbstractCommand {

	private IDesignerDataModelEditor editor;
	
	private EObject newInstance;
	private EObject parent;
	private Command addCommand; 
	private List<Command> deleteCommands;
	public  Hashtable<String, String> propertyValue;
	private static final String COMMAND_PROPERTY_NAME = "name"; //$NON-NLS-1$
	private String nameInstance = "";
	//normal
	public AddNamedGroupCommand(IDesignerDataModelEditor editor, List<Command> deleteCommands,
			Hashtable<String, String> propertyValue, EObject newInstance,EObject parent ,String undoDisplayLabel) {
		this.propertyValue = propertyValue;
		this.editor = editor;
		this.newInstance = newInstance;
		this.parent = parent;
		if (deleteCommands !=null){
			this.deleteCommands = deleteCommands;
		}
		if (undoDisplayLabel != null) {
			setLabel(undoDisplayLabel);
		}
	}

	public EObject getNewInstance(){
		return newInstance;
	}
	
	public void execute() {
		prepare(); 
		addCommand = editor.getDataModel().createAddNewComponentInstanceCommand(
				parent, newInstance, IDesignerDataModel.AT_END); 
		addCommand.execute();
		
		IPropertySource properties = ModelUtils.getPropertySource(newInstance);
		nameInstance = (String) properties.getPropertyValue(COMMAND_PROPERTY_NAME);
		Object[] allProperties = propertyValue.keySet().toArray();
		for (int i = 0; i < allProperties.length; i++){
			properties.setPropertyValue(allProperties[i],propertyValue.get(allProperties[i]));
		}		
	}

	@Override
	protected boolean prepare() {		
		//executes deletions
		if (deleteCommands !=null){
			for(Command command : deleteCommands){
				command.execute();
			}
		}
		return true;
	}

	@Override
	public void undo() {		
		if (deleteCommands !=null){
			int total = deleteCommands.size();
			for (int i = total-1; i >= 0 ; i --){
				Command command = deleteCommands.get(i);
				command.undo();
			}
		}
		addCommand.undo();		
	}

	public void redo() {		
		if (deleteCommands !=null){
			for(Command command : deleteCommands){
				command.redo();
			}
		}
		addCommand.redo();
		IComponentInstance oldCommand = ModelUtils.lookupReference(editor.getDataModel(), nameInstance);
		EObject commandObject = ModelUtils.getEObject(oldCommand);
		IPropertySource commandProperties = ModelUtils.getPropertySource(commandObject);
		Object[] allProperties = propertyValue.keySet().toArray();
		for (int i = 0; i < allProperties.length; i++){
			commandProperties.setPropertyValue(allProperties[i],propertyValue.get(allProperties[i]));
		}
	}

	@Override
	public boolean canUndo(){		
		return true;
	}
}