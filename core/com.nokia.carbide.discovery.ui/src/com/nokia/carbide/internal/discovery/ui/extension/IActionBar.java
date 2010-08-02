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