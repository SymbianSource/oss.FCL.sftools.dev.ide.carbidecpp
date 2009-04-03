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
/**
 * 
 */
package com.nokia.sdt.series60.actions;

import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.component.symbian.properties.ArrayPropertyDescriptor;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.series60.noexport.Messages;
import com.nokia.sdt.ui.IDialogCellEditorActivator;
import com.nokia.sdt.uidesigner.ui.command.ChangePropertyCommand;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;

/**
 * Provide a context item to edit an array property.  This only
 * works on components advertising the "edit-array-properties"
 * attribute.  Such properties must be an array property which 
 * have a CellEditor implementing IDialogCellEditorActivator.
 * 
 *
 */
public class EditArrayPropertyActionDelegate extends BaseComponentActionFilterDelegate {

	public static final String EDITABLE_ARRAY_PROPERTY = "editable-array-property"; //$NON-NLS-1$
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#isActionVisibleForSelectedObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isActionVisibleForSelectedObject(EObject selected) {
		return getEditableArrayPropertyDescriptor(selected) != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#updateActionForSelectedObject(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void updateActionForSelectedObject(IAction action, EObject selected) {
		ArrayPropertyDescriptor array = getEditableArrayPropertyDescriptor(selected);
		if (array != null) {
			action.setEnabled(true);
			action.setText(MessageFormat.format(
					Messages.getString("EditArrayPropertyActionDelegate.EditItemsLabel"), //$NON-NLS-1$
					new Object[] { array.getId() }));
		} else {
			action.setEnabled(false);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#executeActionCommand(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject, org.eclipse.gef.commands.CommandStack)
	 */
	@Override
	protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack) {
		ArrayPropertyDescriptor descriptor = getEditableArrayPropertyDescriptor(selected);
		Check.checkState(descriptor != null);

		CellEditor editor = descriptor.createPropertyEditor(getShell());
		Check.checkState(editor instanceof IDialogCellEditorActivator);
		
		IPropertySource ps = ModelUtils.getPropertySource(selected);

		// initialize
		editor.setValue(ps.getPropertyValue(descriptor.getId()));
		
		IDialogCellEditorActivator activator = (IDialogCellEditorActivator) editor;
		Object result = activator.invokeEditor(getShell());
		if (result != null) {
			ChangePropertyCommand command = new ChangePropertyCommand(
					selected, descriptor.getId().toString(), 
					result, null);
			commandStack.execute(command);
		}
	}

	/**
	 * @param selected
	 * @return
	 */
	private ArrayPropertyDescriptor getEditableArrayPropertyDescriptor(EObject selected) {
		IComponentInstance ci = ModelUtils.getComponentInstance(selected);
		if (ci == null)
			return null;
		if (ci.getComponent() == null)
			return null;
		
		IAttributes attributes = (IAttributes) ci.getComponent().getAdapter(IAttributes.class);
		String property = attributes.getAttribute(EDITABLE_ARRAY_PROPERTY);
		if (property == null || property.length() == 0)
			return null;
		
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(selected, IPropertySource.class);
		if (ps == null)
			return null;
		
		IPropertyDescriptor[] propertyDescriptors = ps.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			if (propertyDescriptors[i].getId().equals(property))
				return (ArrayPropertyDescriptor) propertyDescriptors[i];
		}
		return null;
	}

	
}
