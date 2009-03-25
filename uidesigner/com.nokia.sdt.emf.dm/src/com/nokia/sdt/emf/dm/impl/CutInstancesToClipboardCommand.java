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


package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.EMFEditPlugin;

import java.util.List;

/**
 * This command is a compound command composed of a SetInstancesToClipboardCommand
 * and a RemoveChildInstancesCommand.
 */
public class CutInstancesToClipboardCommand extends CompoundCommand implements Command {

	protected static final String LABEL = 
		EMFEditPlugin.INSTANCE.getString("_UI_CutToClipboardCommand_label");
	protected static final String DESCRIPTION = 
		EMFEditPlugin.INSTANCE.getString("_UI_CutToClipboardCommand_description");

	public CutInstancesToClipboardCommand(IDesignerDataModel model, List objectsToCut) {
		append(model.createCopyComponentInstancesCommand(objectsToCut));
		append(model.createRemoveComponentInstancesCommand(objectsToCut));
	}
}
