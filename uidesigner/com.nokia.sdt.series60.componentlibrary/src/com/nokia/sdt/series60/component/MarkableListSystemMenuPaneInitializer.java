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

package com.nokia.sdt.series60.component;

import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;

class MarkableListSystemMenuPaneInitializer extends BaseMenuPaneInitializer {

	/**
	 * @param componentInstance
	 * @param resourceName
	 */
	public MarkableListSystemMenuPaneInitializer(EObject componentInstance) {
		super(componentInstance, MarkableListSystemMenuPaneInitializerFactory.AVKON_MARKABLE_LIST_RESOURCE_ID);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.component.BaseMenuPaneInitializer#addStockMenuItems()
	 */
	@Override
	protected void addStockMenuItems() {
		// on an undo, don't re-add the same stock item
		if (ModelUtils.findFirstComponentInstance(getEObject(), SYSTEM_MENU_ITEM_COMPONENT_ID, true) != null)
			return;
		
		addMenuItem(
				Messages.getString("MarkableListSystemMenuPaneInitializerFactory.MarkLabel"), //$NON-NLS-1$
				"EAknCmdMark"); //$NON-NLS-1$
		addMenuItem(
				Messages.getString("MarkableListSystemMenuPaneInitializerFactory.UnmarkLabel"), //$NON-NLS-1$
				"EAknCmdUnmark"); //$NON-NLS-1$
		addMenuItem(
				Messages.getString("MarkableListSystemMenuPaneInitializerFactory.MarkAllLabel"), //$NON-NLS-1$
				"EAknMarkAll"); //$NON-NLS-1$
		addMenuItem(
				Messages.getString("MarkableListSystemMenuPaneInitializerFactory.UnmarkAllLabel"), //$NON-NLS-1$
				"EAknUnmarkAll"); //$NON-NLS-1$

	}
}