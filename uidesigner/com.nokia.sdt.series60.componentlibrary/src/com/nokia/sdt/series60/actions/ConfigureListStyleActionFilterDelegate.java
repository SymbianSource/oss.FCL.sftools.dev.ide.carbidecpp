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

import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;
import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.series60.component.listCustomizer.ListCustomizerCommandFactory;
import com.nokia.sdt.series60.component.listCustomizer.ListLayoutEditorDialog;
import com.nokia.sdt.series60.noexport.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class ConfigureListStyleActionFilterDelegate extends BaseComponentActionFilterDelegate {

	private static final String LIST_BOX_ID_PREFIX = "com.nokia.sdt.series60.CAknList"; //$NON-NLS-1$
	private static final String LIST_BOX_STYLE_PROPERTY_ID = "style"; //$NON-NLS-1$
	
	private EObject getListbox(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		if (instance == null)
			return null;
		if (instance.getComponentId().startsWith(LIST_BOX_ID_PREFIX))
			return object;
		
		EObject parent = instance.getParent();
		instance = ModelUtils.getComponentInstance(parent);
		if (instance == null)
			return null;
		if (instance.getComponentId().startsWith(LIST_BOX_ID_PREFIX))
			return parent;
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#isActionVisibleForSelectedObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isActionVisibleForSelectedObject(EObject selected) {
		return getListbox(selected) != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#updateActionForSelectedObject(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void updateActionForSelectedObject(IAction action, EObject selected) {
		action.setEnabled(getListbox(selected) != null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#executeActionCommand(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject, org.eclipse.gef.commands.CommandStack)
	 */
	@Override
	protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack) {
		EObject listBox = getListbox(selected);
		Check.checkState(listBox != null);

		IPropertySource ps = ModelUtils.getPropertySource(listBox);
		
		IComponentCustomizerUI customizerUI = (IComponentCustomizerUI) EcoreUtil.getRegisteredAdapter(listBox, IComponentCustomizerUI.class);
		Check.checkState(customizerUI != null);

		ListLayoutEditorDialog dialog = new ListLayoutEditorDialog(
				(Shell) this.editor.getAdapter(Shell.class),
				listBox);
		Object current = ps.getPropertyValue(LIST_BOX_STYLE_PROPERTY_ID);
		if (current != null)
			dialog.setValue(current.toString());
		else
			dialog.setValue("");
		
		int ret = dialog.open();
		if (ret == Dialog.OK) {

			String newValue = dialog.getValue();
			
			ListCustomizerCommandFactory factory = new ListCustomizerCommandFactory();
			factory.setValue(newValue);

			final String customizeLabel = Messages.getString("ConfigureListStyleActionFilterDelegate.SetListStyleCommand"); //$NON-NLS-1$
			final ICustomizeComponentCommand customizeCommand = factory.createCustomizeComponentCommand(listBox);
			if (customizeCommand != null && customizeCommand.canExecute()) {
				Command command = new Command() {
					@Override
					public boolean canExecute() {
						return customizeCommand.canExecute();
					}
					@Override
					public boolean canUndo() {
						return true;
					}
					@Override
					public void execute() {
						customizeCommand.execute();
					}
					@Override
					public void undo() {
						customizeCommand.undo();
					}
					@Override
					public String getLabel() {
						return customizeLabel;
					}
				};
				commandStack.execute(command);
			}
		}
	}

	
}
