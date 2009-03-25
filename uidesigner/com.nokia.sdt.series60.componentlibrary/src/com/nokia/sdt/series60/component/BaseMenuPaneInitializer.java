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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.series60.component.menu.MenuEditManager;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Helper class for menu pane initializers.  Provides routines to
 * add uneditable items so rendering shows something familiar.
 * <p>
 * You need to implement #addStockMenuItems() to call
 * #addMenuItem() to add the stock items.
 * 
 *
 */
public abstract class BaseMenuPaneInitializer implements IInitializer {

	public static final String SYSTEM_MENU_ITEM_COMPONENT_ID = "com.nokia.sdt.series60.SystemMenuItem"; //$NON-NLS-1$
	public static final String MENU_ITEM_TEXT_PROPERTY_ID = "text"; //$NON-NLS-1$
	public static final String MENU_ITEM_COMMAND_PROPERTY_ID = "command"; //$NON-NLS-1$
	public static final String SYSTEM_MENU_PANE_RESOURCE_NAME = "systemResourceName"; //$NON-NLS-1$

	protected EObject componentInstance;
	private Object resourceName;
	protected IDesignerDataModel dataModel;
	protected IComponent systemMenuItemComponent;

	/**
	 * @param componentInstance
	 */
	public BaseMenuPaneInitializer(EObject componentInstance,
			String resourceName) {
		this.componentInstance = componentInstance;
		this.resourceName = resourceName;
	}

	protected abstract void addStockMenuItems();
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IInitializer#initialize()
	 */
	public void initialize(boolean isConfigured) {
		if (isConfigured) return;
		IPropertySource ps = ModelUtils.getPropertySource(componentInstance);

		if (resourceName != null)
			ps.setPropertyValue(SYSTEM_MENU_PANE_RESOURCE_NAME, resourceName);
		
		IComponentInstance ci = ModelUtils.getComponentInstance(componentInstance);
		dataModel = ci.getDesignerDataModel();
		IComponentSet componentSet = dataModel.getComponentSet();
		systemMenuItemComponent = componentSet.lookupComponent(
				SYSTEM_MENU_ITEM_COMPONENT_ID);
		Check.checkState(systemMenuItemComponent != null);
		
		addStockMenuItems();
	}

	/**
	 * @param dataModel 
	 * @param systemMenuItemComponent
	 * @param string
	 * @param string2
	 * @return
	 */
	protected void addMenuItem(String label, String commandId) {
		EObject menuItem = dataModel.createNewComponentInstance(
				systemMenuItemComponent);
		Check.checkState(menuItem != null);

		Command command = dataModel.createAddNewComponentInstanceCommand(
				componentInstance, menuItem, IDesignerDataModel.AT_END);
		Check.checkState(command.canExecute());
		command.execute();
		
		IPropertySource ps = ModelUtils.getPropertySource(menuItem);
		
		ps.setPropertyValue(MENU_ITEM_TEXT_PROPERTY_ID, label);
		ps.setPropertyValue(MENU_ITEM_COMMAND_PROPERTY_ID, commandId);
		
		MenuEditManager.assignNewName(dataModel, menuItem);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
	 */
	public EObject getEObject() {
		return componentInstance;
	}
}
