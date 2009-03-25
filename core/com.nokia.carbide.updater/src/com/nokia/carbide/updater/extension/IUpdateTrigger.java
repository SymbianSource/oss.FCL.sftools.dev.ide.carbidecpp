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


package com.nokia.carbide.updater.extension;

/**
 * Interface implemented by extensions to the com.nokia.carbide.updater.updateTrigger
 * extension point.
 * 
 * Implementors should do a very quick scan of the workspace to determine if something
 * needs updating. No updating should be done by this extension.
 *
 */
public interface IUpdateTrigger {
	enum UpdateType {
		PROJECT,
		FILE
	}
	
	/**
	 * Does a quick check of the workspace, and returns true if something needs updating
	 * Is called separately for project and file updating
	 * @param type the type of trigger, either project or file
	 * @return boolean
	 */
	boolean workspaceNeedsUpdate(UpdateType type);
	
	/**
	 * This is called after triggering to allow any necessary clean up, or write any metadata.
	 * Advised which type of trigger and whether triggered or not.
	 * @param type the type of trigger, either project or file
	 * @param triggered whether it triggered
	 */
	void postTrigger(UpdateType type, boolean triggered);
}
