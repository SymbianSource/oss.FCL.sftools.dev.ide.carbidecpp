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


package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 *
 */
public class TestCustomizerCommand implements ICustomizeComponentCommand {

	public static final String EXECUTED = "executed";
	public static final String UNDONE = "undone";
	public static final String REDONE = "redone";
	/**
	 */
	public TestCustomizerCommand(EObject instance) {
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#canExecute()
	 */
	public boolean canExecute() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#execute()
	 */
	public String execute() {
		return EXECUTED;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#undo()
	 */
	public String undo() {
		return UNDONE;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#redo()
	 */
	public String redo() {
		return REDONE;
	}

}
