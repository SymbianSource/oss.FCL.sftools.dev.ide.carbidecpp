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
package com.nokia.sdt.component.symbian.actions;

import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.adapter.IDirectImageEdit;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.ui.IDialogCellEditorActivator;
import com.nokia.sdt.uidesigner.ui.command.ChangePropertyCommand;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import java.text.MessageFormat;

/**
 * Action filter delegate which identifies IDirectImageEdit support and
 * uses this to provide an "Edit '&lt;image&gt;' property" context menu item
 * 
 *
 */
public class EditImageActionFilterDelegate extends BaseComponentActionFilterDelegate {
	
	private IPropertyDescriptor getImagePropertyDescriptor(EObject instance, String propertyPath) {
		IPropertyDescriptor descriptor = null;
		if (propertyPath == null)
			return null;
		
		descriptor = ModelUtils.getPropertyPathDescriptor(instance, propertyPath, true);

		if (descriptor != null) {
			// ensure the descriptor has a direct editing interface
			CellEditor editor = descriptor.createPropertyEditor(shell);
			if (!(editor instanceof IDialogCellEditorActivator))
				descriptor = null;
			editor.dispose();
		}
		
		return descriptor;
	}
	
	private String getPrimaryImageProperty(EObject instance) {
		IDirectImageEdit directImageEdit = 
			(IDirectImageEdit) EcoreUtil.getRegisteredAdapter(instance, IDirectImageEdit.class);
		if (directImageEdit == null)
			return null;
		
		// Only honor the first image property
		String[] paths = directImageEdit.getPropertyPaths();
		if (paths == null || paths.length == 0)
			return null;

		return paths[0];
	}
	
	private IPropertyDescriptor getPropertyDescriptorForPrimaryProperty(EObject instance) {
		return getImagePropertyDescriptor(instance, getPrimaryImageProperty(instance));
	}
	
	@Override
	protected boolean isActionVisibleForSelectedObject(EObject instance) {
		return getImagePropertyDescriptor(instance, getPrimaryImageProperty(instance)) != null;
	}

	@Override
	protected void updateActionForSelectedObject(IAction action, EObject instance) {
		IPropertyDescriptor descriptor = getPropertyDescriptorForPrimaryProperty(instance);
		Check.checkState(descriptor != null);
		action.setText(MessageFormat.format(
				Messages.getString("EditImageActionDelegate.EditProperty"), //$NON-NLS-1$
				new Object[] { TextUtils.titleCaseSentence(descriptor.getDisplayName()) }));
	}

	@Override
	protected void executeActionCommand(IAction action, EObject instance, CommandStack commandStack) {
		String propertyPath = getPrimaryImageProperty(instance);
		IPropertyDescriptor descriptor = getImagePropertyDescriptor(instance, propertyPath);
		Check.checkState(descriptor != null);
		
		CellEditor cellEditor = descriptor.createPropertyEditor(shell);
		IDialogCellEditorActivator activator = (IDialogCellEditorActivator) cellEditor;
		NodePathLookupResult result = ModelUtils.readProperty(instance, propertyPath, true);
		cellEditor.setValue(result.result);
		Object newValue = activator.invokeEditor(shell);
		if (newValue != null) {
			ChangePropertyCommand command = 
				new ChangePropertyCommand(instance, 
						propertyPath, newValue, cellEditor.getValidator());
			commandStack.execute(command);
		}
	}
}
