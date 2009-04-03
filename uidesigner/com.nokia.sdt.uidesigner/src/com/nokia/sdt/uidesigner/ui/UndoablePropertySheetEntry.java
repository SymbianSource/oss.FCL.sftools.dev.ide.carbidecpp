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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.uidesigner.ui.command.SetValueCommand;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.PropertySourceWrapper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.*;

/**
 * 
 *
 */
public class UndoablePropertySheetEntry extends
		com.nokia.sdt.uidesigner.derived.ui.UndoablePropertySheetEntry {

	private UndoablePropertySheetEntry() {
	}

	public UndoablePropertySheetEntry(CommandStack stack) {
		super(stack);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.derived.ui.UndoablePropertySheetEntry#createChildEntry()
	 */
	@Override
	protected PropertySheetEntry createChildEntry() {
		return new UndoablePropertySheetEntry();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.derived.ui.UndoablePropertySheetEntry#getExtendedCommand(com.nokia.sdt.uidesigner.ui.command.SetValueCommand)
	 */
	@Override
	protected Command getExtendedCommand(
				com.nokia.sdt.uidesigner.derived.ui.UndoablePropertySheetEntry entry, 
							IPropertySource propertySource, Command command) {
		EObject object = null;
		if (propertySource instanceof PropertySourceWrapper) {
			object = ((PropertySourceWrapper) propertySource).getObject();
		}
		else if (propertySource instanceof IComponentInstance) {
			IComponentInstance instance = (IComponentInstance) propertySource;
			object = instance.getEObject();
		}
		if (object != null) {
			IComponentInstance instance = Adapters.getComponentInstance(object);
			IDesignerDataModel dataModel = instance.getDesignerDataModel();
			if (dataModel != null) {
				ISetValueCommandExtender extender = 
					(ISetValueCommandExtender) EcoreUtil.getRegisteredAdapter(object, 
							ISetValueCommandExtender.class);
				if (extender != null) {
					String propertyName = (String) entry.getDescriptor().getId();
					Object value = null;
					if (command instanceof SetValueCommand)
						value = ((SetValueCommand) command).getPropertyValue();
					return extender.getExtendedCommand(propertyName, value, command);
				}
			}
		}
		return null;
	}
	
	@Override
	protected void valueChanged(PropertySheetEntry child) {
		// ensure there is still a model object, otherwise do nothing
		// what can happen is that the property sheet has focus and so this is still alive
		// even after an object has been deleted (undo its creation)
		// the property sheet will try to apply pending values before focusing on a different object
		
		// find the top entry
		UndoablePropertySheetEntry topEntry = this;
		UndoablePropertySheetEntry parent = (UndoablePropertySheetEntry) getParent();
		while (parent != null) {
			topEntry = parent;
			parent = (UndoablePropertySheetEntry) parent.getParent();
		}
		// get the edit part from the values and make sure it still has a model (it is set to null when deleted)
		Object[] values = topEntry.getValues();
		if (values != null && values.length > 0 && values[0] instanceof EditPart) { // sanity check
			EditPart part = (EditPart) values[0];
			EObject object = (EObject) part.getModel();
			if (object != null)
				super.valueChanged(child);
		}
		else 
			// if the top object is not an edit part, then this is an embedded property editor (e.g., array editor
			// so it is very unlikely this object is not in the model - and that this is an undo operation.
			super.valueChanged(child);
	}
}
