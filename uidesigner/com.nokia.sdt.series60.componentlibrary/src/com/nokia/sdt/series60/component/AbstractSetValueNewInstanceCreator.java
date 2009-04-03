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
package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.NewComponentReferenceParameter;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;

import java.text.MessageFormat;

/**
 * Abstract implementation of ISetValueCommandExtender used with
 * component reference properties support new instance creation.
 * This provides a basic framework for handling creating new
 * instances and assigning to existing instances. 
 * Can be used as a base implementation, where the subclass just needs
 * to override to indicate which component instances to create and optionally
 * initialize newly created instances.
 */
public abstract class AbstractSetValueNewInstanceCreator implements ISetValueCommandExtender {
	
	private EObject instance;
	private IDesignerDataModel model;
	
	protected AbstractSetValueNewInstanceCreator(EObject instance) {
		this.instance = instance;
		IComponentInstance ci = ModelUtils.getComponentInstance(instance);
		Check.checkArg(ci != null);
		model = ci.getDesignerDataModel();
		Check.checkArg(model != null);
	}

	public EObject getEObject() {
		return instance;
	}
	
	protected IDesignerDataModel getModel() {
		return model;
	}

	/**
	 * A default implementation. It looks for the a NewComponentReferenceParameter as a signal
	 * that commands to create and initialize a new component instance must be created.
	 * For other cases it calls getSetExistingInstanceCommand to return an appropriate command.
	 */
	public org.eclipse.gef.commands.Command getExtendedCommand(String propertyID, Object newValue,
											org.eclipse.gef.commands.Command command) {
		org.eclipse.gef.commands.Command result = null;
		if (newValue instanceof NewComponentReferenceParameter) {
			NewComponentReferenceParameter param = (NewComponentReferenceParameter) newValue;
			Command emfCommand = makeSetNewInstanceCommand(propertyID, param.getCreationKey());
			DataModelCommandWrapper wrapper = new DataModelCommandWrapper();
			wrapper.setDataModelCommand(emfCommand);
			result = wrapper;
			
		} else {
			result = getSetExistingInstanceCommand(propertyID, command);
		}
		return result;
	}
	
	/**
	 * This method returns the component ID of the instance to be created for a given
	 * property and creation key.
	 */
	protected abstract String getNewComponentID(String propertyID, String creationKey);
	

	/**
	 * This method returns the command that adds the new instance to the model. It requires
	 * knowledge of the parent and insertion position of the new instance.
	 * @param componentID
	 */
	protected abstract Command makeAddComponentCommand(String propertyID, String creationKey, EObject instance);
	
	/**
	 * This method creates the commands to initialize the newly created component instance.
	 * Any commands needed are appended to the given compound command.
	 */
	protected void addInitializeComponentCommands(String propertyID, String creationKey, 
					EObject instance, CompoundCommand cc) {
	}
	
	/**
	 * This method returns a command to set the target property value to the
	 * name of the newly created instance. It generally won't need to be overriden.
	 */
	protected Command makeSetReferencePropertyCommand(String propertyID, String creationKey, EObject object) {
		SetPropertyCommand result = new SetComponentReferencePropertyCommand(instance, propertyID, object);
		return result;
	}
	
	/**
	 * Since we are preparing a set of commands in advance of adding the new
	 * instance we don't know the instance name yet. This command
	 * allows us to set the name, getting it just as its needed.
	 */
	static class SetComponentReferencePropertyCommand extends SetPropertyCommand {
		
		private EObject referencedObject;

		public SetComponentReferencePropertyCommand(EObject instance, String propertyID, EObject referencedObject) {
			super(instance, propertyID, null);
			this.referencedObject = referencedObject;
		}
		
		@Override
		public void doExecute() {
			IComponentInstance ci = ModelUtils.getComponentInstance(referencedObject);
			updatePropertyValue(ci.getName());
			super.doExecute();
		}
	}

	/**
	 * This method returns a command to handle all actions necessary to create, initialize, and set the property
	 * value for a new instance. Each phase is separated into methods that can be separately
	 * overriden if desired.
	 */
	protected Command makeSetNewInstanceCommand(String propertyID, String creationKey) {
		Command result = null;
		// Step 1: get the component ID for the new instance
		String componentID = getNewComponentID(propertyID, creationKey);
		if (componentID != null) {
			IComponent component = model.getComponentSet().lookupComponent(componentID);
			if (component != null) {
				CompoundCommand cc = new CompoundCommand(0);
				
				// Step 2: create the new component instance
				EObject object = model.createNewComponentInstance(component);
				
				// Step 3: get the command to add the new instance
				Command addCommand = makeAddComponentCommand(propertyID, creationKey, object);
				Check.checkState(addCommand != null);
				cc.append(addCommand);
				
				// Step 4: get the command to initialize the new instance. This step is optional
				addInitializeComponentCommands(propertyID, creationKey, object, cc);
				
				// Step 5: get the command to set the target property to the new instance
				Command setPropertyCommand = makeSetReferencePropertyCommand(propertyID, creationKey, object);
				Check.checkState(setPropertyCommand != null);
				cc.append(setPropertyCommand);
				
				result = cc;
				
			} else {
				String fmt = Messages.getString("AbstractSetValueNewInstanceCreator.unavailableComponent"); //$NON-NLS-1$
				Object params[] = {this.getClass().getName(), componentID, propertyID, creationKey};
				Series60ComponentPlugin.log(null, MessageFormat.format(fmt, params));			
			}
			
		} else {
			String fmt = Messages.getString("AbstractSetValueNewInstanceCreator.noComponentSpecified"); //$NON-NLS-1$
			Object params[] = {this.getClass().getName(), propertyID, creationKey};
			Series60ComponentPlugin.log(null, MessageFormat.format(fmt, params));
		}
		return result;
	}
	


	/**
	 * This method returns the GEF command that sets the value for an existing instance.
	 * The default version just returns the default command provided to the ISetValueCommandExtender and
	 * generally should not need to be overriden.
	 * @param propertyID the target property
	 * @param setValueCommand the default command, as created by the property sheet entry
	 */
	protected org.eclipse.gef.commands.Command getSetExistingInstanceCommand(
			String propertyID, org.eclipse.gef.commands.Command command) {
		return command;
	}

}
