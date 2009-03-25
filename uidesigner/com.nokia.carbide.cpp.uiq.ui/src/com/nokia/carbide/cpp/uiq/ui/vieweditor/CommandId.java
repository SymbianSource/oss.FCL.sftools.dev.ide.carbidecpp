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
 * This class represents the Command Id in the local model.
 *
 */
public class CommandId {
	private String name;
	private String commandId;
	private String commandEvent;
	private int references = 0;
	
	/**
	 * Class constructor
	 * @param name
	 */
	public CommandId(String name){
		this.setName(name);		
	}
	/**
	 * Sets the name for the command id
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;		
	}
	/**
	 * Returns the name of the command id
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the property commandid
	 * @param commandId
	 */
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	/**
	 * Returns the property command id
	 * @return
	 */
	public String getCommandId() {
		return commandId;
	}
	/**
	 * Sets the command Event property
	 * @param commandEvent
	 */
	public void setCommandEvent(String commandEvent) {
		this.commandEvent = commandEvent;
	}
	/**
	 * Returns the command Event property
	 * @return
	 */
	public String getCommandEvent() {
		return commandEvent;
	}
	
	/**
	 * Add a reference for the commandID
	 * @param adding
	 */
	public void refreshReference(int adding){
		references = references + adding;
	}
	/**
	 * Returns the number of references.
	 * @return number of references
	 */
	public int getReference(){
		return references;
	}

}
