/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.series60.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.uidesigner.ui.command.ChangePropertyCommand;


public class ToggleChoiceListButtonActionFilterDelegate extends BaseComponentActionFilterDelegate {

	private static final String FORM_COMPONENT_ID = "com.nokia.sdt.series60.CAknChoiceList"; //$NON-NLS-1$
	private String SHOW_AS_BUTTON_IN_FORM_PROPERTY = "showAsButton.showAsButtonValue"; //$NON-NLS-1$

	private EObject getForm(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		if (instance == null)
			return null;
		if (instance.getComponentId().equals(FORM_COMPONENT_ID))
			return object;
		
		EObject parent = instance.getParent();
		instance = ModelUtils.getComponentInstance(parent);
		if (instance == null)
			return null;
		if (instance.getComponentId().equals(FORM_COMPONENT_ID))
			return parent;
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#isActionVisibleForSelectedObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isActionVisibleForSelectedObject(EObject selected) {
		return getForm(selected) != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#updateActionForSelectedObject(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void updateActionForSelectedObject(IAction action, EObject selected) {
		Boolean b = (Boolean) ModelUtils.readProperty(selected, SHOW_AS_BUTTON_IN_FORM_PROPERTY, true).result;
		if (b){
			action.setText(Messages.getString("ChoiceList.Show_ChoiceList_As_Popup"));
		} else {
			action.setText(Messages.getString("ChoiceList.Show_ChoiceList_As_Button"));
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#executeActionCommand(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject, org.eclipse.gef.commands.CommandStack)
	 */
	@Override
	protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack) {
		EObject form = getForm(selected);
		Boolean b = (Boolean) ModelUtils.readProperty(selected, SHOW_AS_BUTTON_IN_FORM_PROPERTY, true).result;
		ChangePropertyCommand command = new ChangePropertyCommand(
				form, SHOW_AS_BUTTON_IN_FORM_PROPERTY, 
				Boolean.valueOf(!b), null);
		commandStack.execute(command);
	}

}
