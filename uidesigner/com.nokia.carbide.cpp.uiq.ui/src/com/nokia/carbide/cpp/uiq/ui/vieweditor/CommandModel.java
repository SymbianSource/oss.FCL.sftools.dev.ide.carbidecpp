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
/* START_USECASES: CU6 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;
/**
 * This class represents the Command instance in the local model
 *
 */
public class CommandModel extends CommandElementBase{
	private String commandID = ""; //$NON-NLS-1$
	
	/**
	 * Class constructor
	 * @param name
	 * @param parent - Command list parent
	 */
	public CommandModel (String name,CommandList parent){
		this.setName(name);
		this.setParent(parent);
	}
	/**
	 * Sets the command id for the local model
	 * @param commandID
	 */
	public void setCommandID(String commandID) {
		this.commandID = commandID;
	}

	/**
	 * Returns the command id assigned to this command
	 * @return
	 */
	public String getCommandID() {
		return commandID;
	}	
}
