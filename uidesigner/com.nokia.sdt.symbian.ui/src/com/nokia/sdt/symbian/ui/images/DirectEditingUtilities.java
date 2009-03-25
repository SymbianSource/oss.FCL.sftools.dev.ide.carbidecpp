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
package com.nokia.sdt.symbian.ui.images;

import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.ui.IDialogCellEditorActivator;
import com.nokia.sdt.uidesigner.ui.command.ChangePropertyCommand;
import com.nokia.sdt.uidesigner.ui.editparts.LayoutObjectCellEditorLocator;
import com.nokia.sdt.uidesigner.ui.editparts.LayoutObjectLabelEditManager;
import com.nokia.sdt.uidesigner.ui.figure.LayoutObjectFigure;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * Support for invoking direct edit on components.
 * 
 *
 */
public abstract class DirectEditingUtilities {

	/**
	 * Create a cell editor activator to edit the given image property
	 * @param shell
	 * @param instance
	 * @param propertyPath
	 * @return cell editor activator or null
	 */
	public static IDialogCellEditorActivator getCellEditorActivator(Shell shell, EObject instance, String propertyPath) {

		// We only get this far if direct image editing is available and a property is available
	//	IDirectImageEdit directImageEdit = 
	//		(IDirectImageEdit) EcoreUtil.getRegisteredAdapter(instance, IDirectImageEdit.class);
	//	Check.checkState(directImageEdit != null);
		
		IPropertyDescriptor desc = ModelUtils.getPropertyPathDescriptor(instance, propertyPath, true);
		Check.checkState(desc != null);
		
		// ensure this has a direct editing interface
		CellEditor editor = desc.createPropertyEditor(shell);
		if (editor instanceof IDialogCellEditorActivator) {
			return (IDialogCellEditorActivator) editor;
		} else {
			editor.dispose();
			return null;
		}
	}

	/**
	 * Edit the given image property in a dialog and return the command.
	 * @param shell
	 * @param instance
	 * @param propertyPath
	 * @return command, or null if no change
	 */
	public static Command editImageProperty(Shell shell, EObject instance, String propertyPath) {
		IDialogCellEditorActivator activator = getCellEditorActivator(shell, instance, propertyPath);
		if (activator == null)
			return null;
		
		NodePathLookupResult result = ModelUtils.readProperty(instance, propertyPath, true);
		
		CellEditor cellEditor = (CellEditor) activator;
		cellEditor.setValue(result.result);
		
		Object newValue = activator.invokeEditor(shell);
		if (newValue != null) {
			ChangePropertyCommand command = 
				new ChangePropertyCommand(instance, 
						propertyPath, newValue, cellEditor.getValidator());
			return command;
		}
		return null;
	}

	/**
	 * Edit the label property.
	 * TODO: make this return a command.  It works now, but it's not obvious.
	 * @param editor
	 * @param editPart
	 * @param instance
	 * @param propertyPath
	 */
	static public void editLabelProperty(IDesignerEditor editor, GraphicalEditPart editPart, EObject instance, String propertyPath) {
		Check.checkArg(editor);
		Check.checkArg(editPart);
		Check.checkArg(instance);
		IDirectLabelEdit directEdit = 
			(IDirectLabelEdit) EcoreUtil.getRegisteredAdapter(instance, IDirectLabelEdit.class);
		Check.checkState(directEdit != null);
	
		LayoutObjectFigure figure = (LayoutObjectFigure) editPart.getFigure();
	    org.eclipse.swt.graphics.Rectangle bounds = directEdit.getVisualBounds(propertyPath, editor.getDisplayModel().getLookAndFeel());
		LayoutObjectCellEditorLocator layoutObjectCellEditorLocator = 
					new LayoutObjectCellEditorLocator(figure, bounds);
		LayoutObjectLabelEditManager lastLabelEditManager = new LayoutObjectLabelEditManager(
				editPart, 
				TextCellEditor.class, 
				layoutObjectCellEditorLocator, propertyPath, editor);
		lastLabelEditManager.setInitialKeyEvent(null);
		lastLabelEditManager.show();
	}
	

}
