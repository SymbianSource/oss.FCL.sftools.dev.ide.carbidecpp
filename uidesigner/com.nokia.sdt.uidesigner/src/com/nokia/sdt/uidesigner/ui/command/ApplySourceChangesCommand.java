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
package com.nokia.sdt.uidesigner.ui.command;

import com.nokia.cpp.internal.api.utils.ui.NotifyWithBooleanPrefDialog;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.preferences.PreferenceConstants;
import com.nokia.sdt.uidesigner.ui.utils.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Shell;

import java.text.MessageFormat;

/**
 * Apply changes from source merging to the model.
 * 
 *
 */
public class ApplySourceChangesCommand extends DataModelCommandWrapper {
 
	private IDesignerDataModel model;
	private IDesignerDataModelEditor editor;

	public ApplySourceChangesCommand(IDesignerDataModelEditor editor, IDesignerDataModel model) {
		super();
		this.editor = editor;
		this.model = model;
	}
	
	
	@Override
	public void setDataModelCommand(Command command) {
		super.setDataModelCommand(command);
		
		setLabel(MessageFormat.format(Messages.getString("ApplySourceChangesCommand.CommandName"), //$NON-NLS-1$
				model.getModelSpecifier().getDisplayName()));
	}
	
	@Override
	public boolean canUndo() {
		return true; 
	}

	@Override
	public void undo() {
		acceptWarningAboutMissynchronizedSource();

		super.undo();

		editor.refreshFromModel();
	}
	
	private void acceptWarningAboutMissynchronizedSource() {
		NotifyWithBooleanPrefDialog dialog = new NotifyWithBooleanPrefDialog(
				(Shell) editor.getAdapter(Shell.class),
				Messages.getString("ApplySourceChangesCommand.NotifyDialogTitle"), //$NON-NLS-1$
				Messages.getString("ApplySourceChangesCommand.NotifyDialogText"), //$NON-NLS-1$
				UIModelPlugin.getDefault(),
				PreferenceConstants.P_PROMPT_BEFORE_UNDOING_SOURCE_SYNC);
		dialog.doNotify();
	}

	@Override
	public void redo() {
		super.redo();
		editor.refreshFromModel();
	}
	
	@Override
	public void execute() {
		super.execute();
		editor.refreshFromModel();
	}
}
