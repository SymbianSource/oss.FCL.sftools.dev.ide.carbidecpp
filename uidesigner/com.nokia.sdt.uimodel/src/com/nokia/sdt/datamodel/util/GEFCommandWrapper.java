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

import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.uidesigner.ui.command.SetValueCommand;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Wraps a GEF command with an EMF command.
 */
public class GEFCommandWrapper extends AbstractCommand {
	
	private org.eclipse.gef.commands.Command gefCommand;
	
	public GEFCommandWrapper(org.eclipse.gef.commands.Command gefCommand	 ) {
		Check.checkArg(gefCommand);
		this.gefCommand = gefCommand;
	}

	public void execute() {
		gefCommand.execute();
	}

	public void redo() {
		gefCommand.redo();
	}

	@Override
	public boolean canExecute() {
		return gefCommand.canExecute();
	}

	@Override
	public boolean canUndo() {
		return gefCommand.canUndo();
	}

	@Override
	public String getLabel() {
		return gefCommand.getLabel();
	}

	@Override
	public void undo() {
		gefCommand.undo();
	}
	
	/**
	 * Utility to return an EMF command wrapping the output of an
	 * ISetValueCommandExtender. Returns null if the target object
	 * does not implement ISetValueCommandExtender.
	 */
	public static Command wrapSetValueCommandExtender(EObject instance,
				Object propertyID, Object propertyValue) {
		Command result = null;
		IPropertySource ps = ModelUtils.getPropertySource(instance);
		ISetValueCommandExtender extender = 
			(ISetValueCommandExtender) EcoreUtil.getRegisteredAdapter(instance,
					ISetValueCommandExtender.class);
		if (extender != null && ps != null) {
			SetValueCommand gefCommand = new SetValueCommand();
			gefCommand.setTarget(ps);
			gefCommand.setPropertyId(propertyID);
			gefCommand.setPropertyValue(propertyValue);
			org.eclipse.gef.commands.Command extendedCommand = 
				extender.getExtendedCommand(propertyID.toString(), propertyValue, gefCommand);
			result = new GEFCommandWrapper(extendedCommand);
		}
		return result;
	}
}
