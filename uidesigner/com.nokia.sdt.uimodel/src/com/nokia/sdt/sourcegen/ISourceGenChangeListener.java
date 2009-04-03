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

package com.nokia.sdt.sourcegen;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.common.command.Command;

/**
 * Interface used to listen to changes made on generated
 * sources owned by a model.
 * 
 *
 */
public interface ISourceGenChangeListener {
	/**
	 * Specify which model to listen to, or null if not accepting commands.
	 */
	public IDesignerDataModel getListeningDataModel();
	
	/**
	 * Apply the detected changes to the model.
	 * @param dataModel the affected model
	 * @param command the commands to execute
	 */
	public void applyModifiedSourceChanges(Command command);
}
