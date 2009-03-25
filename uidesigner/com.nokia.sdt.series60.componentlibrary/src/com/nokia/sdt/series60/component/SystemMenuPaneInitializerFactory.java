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

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;

/**
 * Add a dummy item to a system menu pane so users don't edit it.
 * 
 *
 */
public class SystemMenuPaneInitializerFactory implements IImplFactory {

	public static final String MENU_ITEM_NONE_COMMAND_ID = "NONE"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new SystemMenuPaneInitializer(componentInstance);
	}

	static class SystemMenuPaneInitializer extends BaseMenuPaneInitializer {

		/**
		 * @param componentInstance
		 * @param resourceName
		 */
		public SystemMenuPaneInitializer(EObject componentInstance) {
			super(componentInstance, null);
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
					Messages.getString("SystemMenuPaneInitializerFactory.UneditableMenuItemLabel"), //$NON-NLS-1$
					MENU_ITEM_NONE_COMMAND_ID);
		}
		
	}
}
