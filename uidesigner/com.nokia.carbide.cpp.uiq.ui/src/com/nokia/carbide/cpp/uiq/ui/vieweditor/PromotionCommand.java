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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
/**
 * This class encapsulates the commands required to promote a command and adding a
 * new command / named group. It receives an AddNamedGroupCommand instance, and a Command
 * instance, this can be an AddCompoundInstanceCommand (to add a new command after the promotion)
 * or an AddNamedGroupCommand instance (to add a new named group after the promotion). 
 *
 */
public class PromotionCommand extends AbstractCommand {

	private IDesignerDataModelEditor editor;
	private AddNamedGroupCommand commandNamed;
	private Command addCommand;
	public final String COMMAND_PROPERTY_NAMED_GROUP = "namedGroup"; //$NON-NLS-1$
	private String nameNamed = "";
	private String nameCommand = "";

	public PromotionCommand(IDesignerDataModelEditor editor, 
			AddNamedGroupCommand commandNamed,Command addCommand,String undoDisplayLabel) {
		this.editor = editor;
		this.commandNamed = commandNamed;
		this.addCommand = addCommand;
		if (undoDisplayLabel != null) {
			setLabel(undoDisplayLabel);
		}
	}
	
	public void execute() {
		prepare();
		commandNamed.execute();
		addCommand.execute();
		EObject newNamed = commandNamed.getNewInstance();
		EObject newCommand = null;
		if (addCommand instanceof AddCompoundInstanceCommand){
			newCommand = ((AddCompoundInstanceCommand) addCommand).getNewInstance();
		}
		else if (addCommand instanceof AddNamedGroupCommand){
			newCommand = ((AddNamedGroupCommand) addCommand).getNewInstance();
		}
		IPropertySource propertiesCommand = ModelUtils.getPropertySource(newCommand);
		IPropertySource propertiesNamed = ModelUtils.getPropertySource(newNamed);
		nameCommand = (String)propertiesCommand.getPropertyValue("name");
		nameNamed = (String)propertiesNamed.getPropertyValue("name");
		propertiesCommand.setPropertyValue(COMMAND_PROPERTY_NAMED_GROUP,
				nameNamed);
	}
	
	
	@Override
	protected boolean prepare() {					
		return true;
	}

	@Override
	public void undo() {		
		addCommand.undo();
		commandNamed.undo();				
	}

	public void redo() {		
		commandNamed.redo();
		addCommand.redo();	
		IComponentInstance oldCommand = ModelUtils.lookupReference(editor.getDataModel(), nameCommand);
		IPropertySource properties = ModelUtils.getPropertySource(
									ModelUtils.getEObject(oldCommand));
		properties.setPropertyValue(COMMAND_PROPERTY_NAMED_GROUP, nameNamed);
	}

	@Override
	public boolean canUndo(){		
		return true;
	}
	
}

