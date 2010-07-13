/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.extension;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

/**
 * Interface to a portal page extension
 */
public interface IPortalPage {

	/**
	 * Interface to an action/navigation bar for the portal page
	 */
	public interface IActionBar {
		
		/**
		 * Optional title for the action bar
		 * @return String
		 */
		String getTitle();
		
		/**
		 * Actions shown in the action bar:
		 * Required: text and run() methods
		 * Optional: tool tip text
		 * Unused: image
		 * @return IAction
		 */
		IAction[] getActions();

	}
	
	/**
	 * Interface allowing the UI for provided actions of an IActionBar to be updated (e.g., enabled state)
	 */
	public interface IActionUIUpdater {
		
		/**
		 * Update the UI for a specific action by id
		 * @param actionId
		 */
		void update(String actionId);
		
		/**
		 * Update the UI for all actions in the IActionBar
		 */
		void updateAll();
		
	}

	/**
	 * Required title text used by main navigation bar
	 * @return String
	 */
	String getText();
	
	/**
	 * Required image descriptor used by main navigation bar
	 * @return ImageDescriptor
	 */
	ImageDescriptor getImageDescriptor();
	
	/**
	 * Called to create the control for the page
	 * @param parent Composite
	 * @param part IEditorPart
	 * @return Control
	 */
	Control createControl(Composite parent, IEditorPart part);
	
	/**
	 * Called to initialize the page when shown for the first time
	 */
	void init();
	
	/**
	 * Return action bars for the page (can't be null)
	 * @param part IEditorPart
	 * @param updater IActionUIUpdater
	 * @return IActionBar[]
	 */
	IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater);
	
	/**
	 * Called to dispose internal resources of the page
	 */
	void dispose();
}
