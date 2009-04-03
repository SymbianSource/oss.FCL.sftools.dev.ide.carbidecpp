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
 * This class is used to map a element from the domain model to an element
 * to be transfer in the DrapDrop operation. It only has the needed information
 * for locate the element in the local model.
 */
public class ElementTransfer {
	private String name;
	private String componentID;
	private String commandListName;
	
	/**
	 * Class constructor
	 * @param name
	 */
	public ElementTransfer(String name){
		this.name = name;
	}
	/**
	 * Returns the name of the element
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the component ID (command, named group, anonymous group or 
	 * command list ids)
	 * @param componentID
	 */
	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}
	/**
	 * Returns the component ID 
	 * @return
	 */
	public String getComponentID() {
		return componentID;
	}
	/**
	 * Sets the name of the command list parent of the element to be transfered
	 * @param commandList
	 */
	public void setCommandListName(String commandList) {
		this.commandListName = commandList;
	}
	/**
	 * Returns the name of the command list parent.
	 * @return
	 */
	public String getCommandListName() {
		return commandListName;
	}	
}
