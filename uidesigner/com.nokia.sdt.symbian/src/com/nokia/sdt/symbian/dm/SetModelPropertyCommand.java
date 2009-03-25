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
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import java.util.*;

/**
 * Undoable command to set model-wide string properties. 
 * These properties don't map to user-level concepts, so the
 * client must provide undo labels.
 */
public class SetModelPropertyCommand extends org.eclipse.emf.common.command.AbstractCommand {

	private DesignerDataModel model;
	private String propertyID;
	private String newPropertyValue;
	private String oldPropertyValue;
	

	public SetModelPropertyCommand(DesignerDataModel model, 
			String propertyID, String propertyValue, String undoDisplayLabel) {
		this.model = model;
		this.propertyID = propertyID;
		this.newPropertyValue = propertyValue;
		if (undoDisplayLabel != null) {
			setLabel(undoDisplayLabel);
		}
	}

	public void execute() {
		model.setProperty(propertyID, newPropertyValue);
	}

	@Override
	protected boolean prepare() {
		oldPropertyValue = model.getProperty(propertyID);
		return true;
	}

	@Override
	public void undo() {
		model.setProperty(propertyID, oldPropertyValue);
	}

	public void redo() {
		execute();
	}

	@Override
	public Collection getAffectedObjects() {
		IDesignerDataModel dm = model;
		List<IDesignerDataModel> result = Collections.singletonList(dm);
		return result;
	}
}
