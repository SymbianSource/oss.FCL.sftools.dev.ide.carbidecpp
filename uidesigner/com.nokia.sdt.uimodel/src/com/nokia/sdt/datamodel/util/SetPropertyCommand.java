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
package com.nokia.sdt.datamodel.util;

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.component.property.IUndoablePropertySource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.uimodel.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AbstractOverrideableCommand;
import org.eclipse.ui.views.properties.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

	/**
	 * EMF command to set a property. 
	 *
	 */
public class SetPropertyCommand extends AbstractOverrideableCommand {
	
	private IDesignerDataModel model;
	private String instanceName;
	private String propertyPath;
	
	private Object propertyValue;
	
	// for managing deferred prepare()
	private boolean prepared;	
	
	private Object undoValue;
	private boolean resetOnUndo;
	private IUndoablePropertySource undoablePropertySource;
	
	// This is to allow commands that should be executed, but where undo is a no-op.
	// Useful where a command is needed for a CompoundCommand, but should not undo when the
	// compound command is undone.
	private boolean ignoreUndoRedo;
	
	public SetPropertyCommand(EObject instance,
					String propertyID,
					Object propertyValue) {
		super(null);
		Check.checkArg(instance);
		IComponentInstance ci = ModelUtils.getComponentInstance(instance);
		this.model = ci.getDesignerDataModel();
		this.instanceName = ci.getName();
		this.propertyPath = propertyID;
		this.propertyValue = propertyValue;

		if (propertyValue instanceof IPropertySource)
			this.propertyValue = ((IPropertySource) propertyValue)
					.getEditableValue();
	}

	public SetPropertyCommand(IPropertySource propertySource,
			String propertyID,
			Object propertyValue) {
		super(null);
		Check.checkContract(propertySource instanceof IPropertyInformation);
		EObject instance = ((IPropertyInformation) propertySource).getPropertyOwner(propertyID);
		IComponentInstance ci = ModelUtils.getComponentInstance(instance);
		this.model = ci.getDesignerDataModel();
		this.instanceName = ci.getName();
		this.propertyPath = ((IPropertyInformation) propertySource).getPropertyPath(propertyID);
		if (this.propertyPath == null)
			this.propertyPath = propertyID;
		this.propertyValue = propertyValue;
		
		if (propertyValue instanceof IPropertySource)
			this.propertyValue = ((IPropertySource) propertyValue)
					.getEditableValue();
	}
	
	public EObject getInstance() {
		return model.findByNameProperty(instanceName);
	}
	
	public IPropertySource getPropertySource() {
		EObject instance = getInstance();
		NodePathLookupResult result = ModelUtils.readProperty(instance, propertyPath, false);
		return result.properties;
	}
	
	public String getPropertyID() {
		return ModelUtils.getPropertyNameFromPropertyPath(propertyPath);
	}
	
	public void ignoreUndoRedo() {
		ignoreUndoRedo = true;
	}
	
	/**
	 * Allow updating the property value after the command
	 * has been created.
	 */
	public void updatePropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void doExecute() {
		Check.checkState(getOverride() == null);
		
		prepare(); // lazily prepare() if we got an EObject instead of IPropertySource
		// doPrepare may have installed an override command. Since
		// it was installed after doExecute was called we must manually
		// execute it.
		if (getOverride() != null) {
			getOverride().execute();
		} else {
			IPropertySource propertySource = getPropertySource();
			Check.checkState(propertySource != null);
			
			// checking if property is resettable must be done both before
			// and after setting the property, so we do it here instead of in
			// prepare().
			String propertyID = getPropertyID();
			boolean wasPropertySet = propertySource.isPropertySet(propertyID);
			propertySource.setPropertyValue(propertyID, propertyValue);
			if (propertySource instanceof IPropertySource2) {
				resetOnUndo = !wasPropertySet
				&& ((IPropertySource2) propertySource)
				.isPropertyResettable(propertyID);
			}
			else {
				resetOnUndo = !wasPropertySet
				&& propertySource.isPropertySet(propertyID);
			}
			if (resetOnUndo)
				undoValue = null;
			if (propertyID.equals(model.getNamePropertyId()))
				instanceName = propertyValue.toString();
		}
	}
	
	

	@Override
	protected boolean prepare() {
		if (prepared) return true;
		
		// If the target object implements ISetValueCommand extender then
		// get its command and let if override this one.
		EObject eobject = getInstance();
		String propertyID = getPropertyID();
		if (eobject != null) {
			Command overrideCmd = GEFCommandWrapper.wrapSetValueCommandExtender(
					eobject, propertyID, propertyValue);
			if (overrideCmd != null) {
				if (!overrideCmd.canExecute()) {
					return false;
				}
				setOverride(overrideCmd);
			}
		}
		
		IPropertySource propertySource = getPropertySource();
		
		// update the label
		IPropertyDescriptor pd = null;
		IPropertyDescriptor pds[] = propertySource.getPropertyDescriptors();
		for (int i = 0; i < pds.length; i++) {
			if (pds[i].getId().equals(propertyID)) {
				pd = pds[i];
				break;
			}
		}
		Check.checkArg(pd);
		Object params[] = {pd};
		setLabel(MessageFormat.format(Messages.getString("SetPropertyCommand.commandLabel"), params)); //$NON-NLS-1$
		
		if (getOverride() == null) {
			// now get the undo value
			undoValue = propertySource.getPropertyValue(propertyID);
			if ((undoValue instanceof IUndoablePropertySource)) {
				undoablePropertySource = (IUndoablePropertySource) undoValue;
				undoValue = ((IUndoablePropertySource) undoValue).getUndoValue();
			}
		}
		prepared = true;
		return true;
	}
		
	@Override
	public void doUndo() {
		if (ignoreUndoRedo) return;
		
		String propertyID = getPropertyID();
		IPropertySource propertySource = getPropertySource();
		if (resetOnUndo) {
			propertySource.resetPropertyValue(propertyID);
		}
		else {
			if (undoablePropertySource != null) {
				undoablePropertySource.setFromUndoValue(undoValue, true);
				propertySource.setPropertyValue(propertyID, undoablePropertySource);
			}
			else {
				propertySource.setPropertyValue(propertyID, undoValue);
			}
		}
		if (propertyID.equals(model.getNamePropertyId()))
			instanceName = undoValue.toString();
	}

	public void doRedo() {
		if (ignoreUndoRedo) return;
		doExecute();
	}

	@Override
	public Collection doGetAffectedObjects() {
		Collection result = new ArrayList();
		EObject instance = getInstance();
		if (instance != null) {
			result.add(instance);
		}
		return result;
	}
}
