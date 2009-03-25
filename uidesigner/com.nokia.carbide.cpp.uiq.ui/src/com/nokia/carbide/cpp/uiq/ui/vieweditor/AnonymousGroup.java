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
 * This class represents the AnonymousGroup in the local model.
 * It can be two types of AnonymousGroup:
 * 1. (0) The one that is direct child of the CommandList (when a command only has this
 * 	  anonymous group assigned).
 * 2. (1) The one that is child of a NamedGroup (when a command has both groups)
 * Only the AnonymousGroup type 1 can set a namedGroup, here we set the real name
 * because the name is the combined.
 *
 */
public class AnonymousGroup extends CommandElementBase{
	private int children = 0;
	private int type;
	private String nameNG = ""; //$NON-NLS-1$
	private String realName;
	
	/**
	 * Class constructor
	 * @param name
	 * @param type - 0: Parent command list, 1: Parent namedGroup
	 */
	public AnonymousGroup(String name, int type){
		this.setName(name); //combined name in case the type is 1
		this.realName = name;		
		this.type = type;
	}
	
	/**
	 * Returns the name to be displayed.
	 * @return name or realName according  to the type
	 */
	public String getLabelName(){
		if (type == 0){
			return this.getName();//name;
		}
		else{
			return realName;
		}
	}
	
	/**
	 * Sets the name of the named group parent for type 1.
	 * @param namedGroup
	 */
	public void setNGName(NamedGroup namedGroup){
		this.setNamedGroupName(namedGroup.getName());
		String newName = this.getName();
		int n = namedGroup.getName().length();
		int total = this.getName().length();
		newName = this.getName().substring(0, total - n);
		this.setRealName(newName);
	}
		
	/**
	 * Return the number of total children for the group
	 * @return children
	 */
	public int getChildren(){
		return children;
	}
	/**
	 * Add a child to the count
	 */
	public void addChildren(){
		children++;
	}
	/**
	 * Reduce the number of children
	 */
	public void removeChildren(){
		children--;
	}
	/**
	 * Returns the type of the group
	 * @return type
	 */	
	public int getType() {
		return type;
	}
	/**
	 * Set the name for the named Group
	 * @param nameNG
	 */
	public void setNamedGroupName(String nameNG) {
		this.nameNG = nameNG;		
	}
	/**
	 * Returns the name of the named group parent
	 * @return name
	 */
	public String getNamedGroupName() {
		return nameNG;		
	}

	/**
	 * Sets the real name of the group (type 1)
	 * @param name
	 */
	public void setRealName(String name) {
		this.realName = name;
	}
	
	/**
	 * Returns the real name of the group (type 1)
	 * @return realName
	 */
	public String getRealName() {		
		return this.realName;
	}
	
}
