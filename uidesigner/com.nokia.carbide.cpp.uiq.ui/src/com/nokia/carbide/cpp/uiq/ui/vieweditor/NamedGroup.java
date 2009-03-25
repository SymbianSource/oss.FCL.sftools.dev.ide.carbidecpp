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
 * Class the represents the Named group instance in the local model.
 *
 */
public class NamedGroup extends CommandElementBase{
	private int children = 0;
	/**
	 * Class constructor
	 * @param name
	 */
	public NamedGroup(String name){
		this.setName(name);
	}
	/**
	 * Adds a child to the children counter
	 */
	public void addChildren(){
		children++;
	}
	/**
	 * Removes a child to the children counter
	 */
	public void removeChildren(){
		children--;
	}
	/**
	 * Returns the number of children
	 * @return
	 */
	public int getChildren(){
		return children;
	}
}
