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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

/**
 * Interface to a portal page layer extension
 */
public interface IPortalPageLayer {

	/**
	 * Interface to an action/navigation bar for the portal portal page layer
	 */
	public interface IActionBar {
		
		/**
		 * Required title for the action bar
		 * @return String
		 */
		String getTitle();
		
		/**
		 * Actions shown in the action bar:
		 * Required: text and run() methods
		 * Optional: tool tip text
		 * Unused: image/check
		 * @return IAction
		 */
		IAction[] getActions();
		
		/**
		 * Action ids for actions that should be emphasized in the action bar (e.g., bold font)
		 * @return String[] or null
		 */
		String[] getHighlightedActionIds();

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
	 * Called to create the control for the portal page layer
	 * @param parent Composite
	 * @param part IEditorPart
	 * @return Control
	 */
	Control createControl(Composite parent, IEditorPart part);
	
	/**
	 * Called to initialize the portal page layer when shown for the first time
	 */
	void init();
	
	/**
	 * Return action bars for the portal page layer
	 * @param part IEditorPart
	 * @param updater IActionUIUpdater
	 * @return IActionBar[]
	 */
	IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater);
	
	/**
	 * Called to dispose internal resources of the portal page layer
	 */
	void dispose();
}
