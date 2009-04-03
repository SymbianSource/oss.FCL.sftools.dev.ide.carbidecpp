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


package com.nokia.sdt.datamodel.adapter;

import org.eclipse.gef.commands.Command;

/**
 * An interface to allow components to extend a SetValueCommand with other undoable commands
 */
public interface ISetValueCommandExtender extends IModelAdapter {
	
	/**
	 * @param propertyName the name of the property for which to extend the set value command
	 * @param newValue the value to be set in the set value command
	 * @param command the Command to be extended
	 * @return the extended GEF command (should include original command or do something expected)
	 */
	public Command getExtendedCommand(String propertyName, Object newValue, Command command);
	
}
