/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

import java.util.Collection;
import java.util.List;

/**
 * An interface to allow components to extend AddNewComponentInstance, RemoveComponentInstances,
 * and MoveComponentInstance commands with other undoable commands
 */
public interface IChildCommandExtender extends IModelAdapter {
	
	/**
	 * @see IDesignerDataModel#createAddNewComponentInstanceCommand(EObject, EObject, int)
	 * @param owner EObject
	 * @param children Collection of children to be added (or pasted from clipboard)
	 * @param insertionPosition int
	 * @param command The original EMF command
	 * @return the extended EMF command (should include original command or do something expected)
	 */
	public Command getExtendedAddNewComponentInstanceCommand(
					EObject owner, Collection<EObject> children, int insertionPosition, Command command);

	/**
	 * @see IDesignerDataModel#createRemoveComponentInstancesCommand(List)
	 * @param objectsToRemove List
	 * @param command The original EMF command
	 * @return the extended EMF command (should include original command or do something expected)
	 */
	Command getExtendedRemoveComponentInstancesCommand(
					List<EObject> objectsToRemove, Command command);
	
	/**
	 * @see IDesignerDataModel#createMoveComponentInstanceCommand(EObject, EObject, int)
	 * @param targetObject EObject
	 * @param newOwner EObject
	 * @param insertionPosition int
	 * @param command The original EMF command
	 * @return the extended EMF command (should include original command or do something expected)
	 */
	Command getExtendedMoveComponentInstanceCommand(
			EObject targetObject, EObject newOwner, int insertionPosition, Command command);

}
