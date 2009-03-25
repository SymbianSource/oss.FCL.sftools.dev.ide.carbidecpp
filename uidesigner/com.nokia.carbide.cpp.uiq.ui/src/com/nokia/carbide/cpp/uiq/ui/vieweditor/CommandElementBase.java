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
 * This is the Parent Class for the classes AnonymousGroup,
 * NamedGroup and CommandModel.
 * It only has the gets and sets for the common properties:
 * name, parent, NG parent and AG parent
 * AG = AnonymousGroup
 * NG = NamedGroup
 *
 */
public class CommandElementBase {
	private String name;
	private CommandList parent;
	private NamedGroup parentNG;
	private AnonymousGroup parentAG;
	
	/**
	 * Sets the name for the element
	 * @param name
	 */
	public void setName(String name) {
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
	 * Sets the command list parent.
	 * @param parent
	 */
	public void setParent(CommandList parent) {
		this.parent = parent;
	}
	/**
	 * Returns the command list parent.
	 * @return parent
	 */
	public CommandList getParent() {
		return parent;
	}
	/**
	 * Sets the NG parent 
	 * @param parentNG
	 */
	public void setNGParent(NamedGroup parentNG) {
		this.parentNG = parentNG;
	}
	/**
	 * Returns the NG parent
	 * @return parentNG
	 */
	public NamedGroup getNGParent() {
		return parentNG;
	}
	/**
	 * Sets the AG parent
	 * @param parentAG
	 */
	public void setAGParent(AnonymousGroup parentAG) {
		this.parentAG = parentAG;
	}
	/**
	 * Returns the AG parent.
	 * @return parentAG
	 */
	public AnonymousGroup getAGParent() {
		return parentAG;
	}
}
