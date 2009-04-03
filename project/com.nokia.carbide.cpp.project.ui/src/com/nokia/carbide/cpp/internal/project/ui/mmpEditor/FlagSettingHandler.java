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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.ChangeFlagSettingOperation;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

/**
 * Specialization of ControlHandler that corresponds to a value
 * in IMMPView's flag settings set
 *
 */

public class FlagSettingHandler extends ControlHandler {
	
	private EMMPStatement statement;
	private IEditingContext editingContext;
	private MMPEditorContext editorContext;

	public FlagSettingHandler(Control control,
			IEditingContext editingContext,
			EMMPStatement statement,
			MMPEditorContext editorContext) {
		super(control, null); 
		this.statement = statement;
		this.editorContext = editorContext;
		this.editingContext = editingContext;
		
		addListener(new ControlHandlerAdapter() {
			@Override
			public void controlSelected(Control control) {
				makeOperation(control);
			}
		});
	}
	
	public FlagSettingHandler(StructuredViewer viewer,
			IEditingContext editingContext,
			EMMPStatement statement,
			MMPEditorContext editorContext) {
		super(viewer, false);
		this.statement = statement;
		this.editorContext = editorContext;
		this.editingContext = editingContext;
		
		addListener(new ControlHandlerAdapter() {
			@Override
			public void controlSelected(Control control) {
				makeOperation(control);
			}
		});
	}
	
	private void makeOperation(Control control) {
		boolean newValue = false;
		Object value = ControlHandler.getControlValue(control);
		if (value != null) {
			newValue = Boolean.valueOf(value.toString());
		}
		ChangeFlagSettingOperation op = new ChangeFlagSettingOperation(
				editorContext.mmpView, editingContext,
				control, statement, newValue);
		editorContext.executeOperation(op);
	}
	
	public void refresh() {
		boolean value = editorContext.mmpView.getFlags().contains(statement);
		setValue(value);
	}
}
