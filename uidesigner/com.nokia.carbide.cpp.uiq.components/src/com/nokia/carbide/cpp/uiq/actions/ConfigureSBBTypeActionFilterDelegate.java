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
package com.nokia.carbide.cpp.uiq.actions;

import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;
import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.carbide.cpp.uiq.components.sbbCustomizer.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

public class ConfigureSBBTypeActionFilterDelegate extends BaseComponentActionFilterDelegate {

	private static final String SBB_ID = "com.nokia.carbide.uiq.SystemBuildingBlock"; //$NON-NLS-1$
	private static final String SBB_TYPE_PROPERTY_ID = "type"; //$NON-NLS-1$
	
	private EObject getSBB(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		if (instance == null)
			return null;
		if (instance.getComponentId().equals(SBB_ID))
			return object;
		
		EObject parent = instance.getParent();
		instance = ModelUtils.getComponentInstance(parent);
		if (instance == null)
			return null;
		if (instance.getComponentId().equals(SBB_ID))
			return parent;
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#isActionVisibleForSelectedObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isActionVisibleForSelectedObject(EObject selected) {
		return getSBB(selected) != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#updateActionForSelectedObject(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void updateActionForSelectedObject(IAction action, EObject selected) {
		action.setEnabled(getSBB(selected) != null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#executeActionCommand(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject, org.eclipse.gef.commands.CommandStack)
	 */
	@Override
	protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack) {
		EObject sbb = getSBB(selected);
		Check.checkState(sbb != null);

		IPropertySource ps = ModelUtils.getPropertySource(sbb);
		
		IComponentCustomizerUI customizerUI = (IComponentCustomizerUI) EcoreUtil.getRegisteredAdapter(sbb, IComponentCustomizerUI.class);
		Check.checkState(customizerUI != null);

		SBBLayoutEditorDialog dialog = new SBBLayoutEditorDialog(
				(Shell) this.editor.getAdapter(Shell.class),
				sbb);
		Object current = ps.getPropertyValue(SBB_TYPE_PROPERTY_ID);
		if (current != null)
			dialog.setValue(current.toString());
		else
			dialog.setValue(""); //$NON-NLS-1$
		
		int ret = dialog.open();
		if (ret == Dialog.OK) {

			String newValue = dialog.getValue();
			
			SBBCustomizerCommandFactory factory = new SBBCustomizerCommandFactory();
			factory.setValue(newValue);

			final String customizeLabel = Messages.getString("ConfigureSBBTypeActionFilterDelegate.SetSBBTypeCommand");  //$NON-NLS-1$
			final ICustomizeComponentCommand customizeCommand = factory.createCustomizeComponentCommand(sbb);
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
				ISetValueCommandExtender extender = 
					(ISetValueCommandExtender) EcoreUtil.getRegisteredAdapter(sbb, ISetValueCommandExtender.class);
				if (extender != null) {
					SBBCustomizerCommand sbbCommand = (SBBCustomizerCommand) customizeCommand;
					command = 
						extender.getExtendedCommand(
								SBBCustomizerCommand.TYPE_PROPERTY_ID, sbbCommand.getValue(), command);
				}		
				
				commandStack.execute(command);
			}
		}
	}

	
}
