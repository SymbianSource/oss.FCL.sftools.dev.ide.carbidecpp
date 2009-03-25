/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.customizer;

/**
 * 
 *
 */
public interface ICustomizeComponentCommand {

	/**
	 * @return whether the command can execute
	 */
	boolean canExecute();
	
	/**
	 * @return error string or <code>null</code> if success
	 */
	String execute();
	
	/**
	 * @return error string or <code>null</code> if success
	 */
	String undo();
	
	/**
	 * @return error string or <code>null</code> if success
	 */
	String redo();

}
