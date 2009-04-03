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

package com.nokia.sdt.uidesigner.ui;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Shell;

/**
 * Subclass of the GEF command stack that calls
 * {@link IDesignerDataModel#validateEdit(Object)} before
 * executing operations. 
 */
public class EditValidatingCommandStack extends CommandStack {
	
	private IDesignerDataModel dataModel;
	private Shell shell;

	public EditValidatingCommandStack() {
	}
	
	public void initialize(Shell shell, IDesignerDataModel model) {
		this.shell = shell;
		this.dataModel = model;
	}
	
	protected IStatus validateEdit() {
		IStatus result = Status.OK_STATUS;
		if (dataModel != null && !isDirty()) {
			result = dataModel.validateEdit(shell);
		}
		return result;
	}

	public void execute(org.eclipse.gef.commands.Command command) {	
		if (validateEdit().isOK()) {
			super.execute(command);
		}
	}
	
	public IStatus executeWithStatus(org.eclipse.gef.commands.Command command) {	
		IStatus result = validateEdit();
		if (result.isOK()) {
			super.execute(command);
		}
		return result;
	}
}