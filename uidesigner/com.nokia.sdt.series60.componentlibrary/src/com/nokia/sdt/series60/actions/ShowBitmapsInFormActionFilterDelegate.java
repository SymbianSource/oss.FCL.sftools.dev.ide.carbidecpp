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

import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.uidesigner.ui.command.ChangePropertyCommand;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class ShowBitmapsInFormActionFilterDelegate extends BaseComponentActionFilterDelegate {

	private static final String FORM_COMPONENT_ID = "com.nokia.sdt.series60.CAknForm"; //$NON-NLS-1$
	private static final String SHOW_BITMAPS_IN_FORM_PROPERTY = "EEikFormShowBitmaps"; //$NON-NLS-1$

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
		IPropertySource ps = ModelUtils.getPropertySource(getForm(selected));
		Boolean value = (Boolean) ps.getPropertyValue(SHOW_BITMAPS_IN_FORM_PROPERTY);
		if (value != null)
			action.setChecked(value.booleanValue());
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#executeActionCommand(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject, org.eclipse.gef.commands.CommandStack)
	 */
	@Override
	protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack) {
		EObject form = getForm(selected);
		IPropertySource ps = ModelUtils.getPropertySource(form);
		Boolean value = (Boolean) ps.getPropertyValue(SHOW_BITMAPS_IN_FORM_PROPERTY);
		ChangePropertyCommand command = new ChangePropertyCommand(
				form, SHOW_BITMAPS_IN_FORM_PROPERTY, 
				Boolean.valueOf(!value.booleanValue()), null);
		commandStack.execute(command);
	}

}
