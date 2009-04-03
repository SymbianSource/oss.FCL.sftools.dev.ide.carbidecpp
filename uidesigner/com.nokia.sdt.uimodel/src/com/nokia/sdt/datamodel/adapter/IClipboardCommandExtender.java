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
import org.eclipse.emf.edit.domain.EditingDomain;

import java.util.List;

/**
 * An interface to allow components to extend clipboard commands 
 * (copy to clipboard and paste from clipboard) with other undoable commands
 * 
 * NOTE: These extenders must be on the components for the objects of the copy and paste operations
 */
public interface IClipboardCommandExtender extends IModelAdapter {
	
	/**
	 * @see IDesignerDataModel#createCopyComponentInstancesCommand(List)
	 * @param editingDomain EditingDomain
	 * @param command The original EMF command
	 * @see EditingDomain#getClipboard() and EditingDomain#setClipboard()
	 * <br>EditingDomain may be used to check and modify clipboard contents
	 * @return the extended EMF command (should include original command or do something expected)
	 */
	Command getExtendedCopyToClipboardCommand(EditingDomain editingDomain, Command command);

	/**
	 * @see IDesignerDataModel#createPasteComponentInstancesCommand(EObject)
	 * @param owner EObject
	 * @param objectToPaste EObject
	 * @param editingDomain EditingDomain
	 * @param command The original EMF command
	 * @see EditingDomain#getClipboard() and EditingDomain#setClipboard()
	 * <br>EditingDomain may be used to check and modify clipboard contents
	 * @return the extended EMF command (should include original command or do something expected)
	 */
	Command getExtendedPasteFromClipboardCommand(EObject owner, EditingDomain editingDomain, Command command);
	
}

