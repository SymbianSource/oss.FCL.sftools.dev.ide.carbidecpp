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

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
/**
 * This class creates a command that executes the command to create a "new command"
 * and a "new command id", and sets the property command id in the new command.
 *
 */
public class AddCompoundInstanceCommand extends AbstractCommand {

	private IDesignerDataModelEditor editor;
	private String propertyID;
	private final String UI_COMMAND_LISTS_GROUP = UIQModelUtils.UIQ_COMMAND_LISTS_GROUP;
	private static final String COMMAND_PROPERTY_NAME = "name"; //$NON-NLS-1$
	private EObject newCommand;
	private EObject newCommandId;
	private EObject parent;
	private EObject parentGroup;
	private Command addCommand; 
	private Command addCommandID;
	private String  oldValue;
	private String nameCommand;
	
	
	public AddCompoundInstanceCommand(IDesignerDataModelEditor editor, 
			String propertyID, EObject newCommand,EObject newCommandId,EObject parent ,String undoDisplayLabel) {
		this.editor = editor;
		this.propertyID = propertyID;
		this.newCommand = newCommand;
		this.newCommandId = newCommandId;
		this.parent = parent;
		if (undoDisplayLabel != null) {
			setLabel(undoDisplayLabel);
		}
	}
	
	public EObject getNewInstance(){
		return newCommand;
	}
		
	public void execute() {
		prepare(); 
		addCommand = editor.getDataModel().createAddNewComponentInstanceCommand(
				parent, newCommand, IDesignerDataModel.AT_END); 
		addCommandID = editor.getDataModel().createAddNewComponentInstanceCommand(
				parentGroup, newCommandId, IDesignerDataModel.AT_END);
		
		addCommand.execute();
		addCommandID.execute();
		IPropertySource properties = ModelUtils.getPropertySource(newCommand);
		nameCommand = (String) properties.getPropertyValue(COMMAND_PROPERTY_NAME);
		properties.setPropertyValue(propertyID, ModelUtils.getPropertySource(newCommandId).
								getPropertyValue(COMMAND_PROPERTY_NAME));
		oldValue = (String) ModelUtils.getPropertySource(newCommandId).
		 						getPropertyValue(COMMAND_PROPERTY_NAME);
	}

	@Override
	protected boolean prepare() {		
		parentGroup  = UIConfigurationPageUtils.getObjectById(editor.getDataModel(),
				UI_COMMAND_LISTS_GROUP);			
		return true;
	}

	@Override
	public void undo() {		
		addCommand.undo();
		addCommandID.undo();
	}

	public void redo() {		
		addCommandID.redo();
		addCommand.redo();
		IComponentInstance oldCommand = ModelUtils.lookupReference(editor.getDataModel(), nameCommand);
		EObject commandObject = ModelUtils.getEObject(oldCommand);
		IPropertySource commandProperties = ModelUtils.getPropertySource(commandObject);
		commandProperties.setPropertyValue(propertyID, oldValue);
	}

	@Override
	public boolean canUndo(){		
		return true;
	}
	
}
