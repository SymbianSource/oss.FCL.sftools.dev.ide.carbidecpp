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

import java.util.Map;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.ChangeSingleSettingOperation;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

/**
 * Specialization of ControlHandler that corresponds to a value
 * in IMMPView's single argument settings map.
 * Populates the control from the setting and creates operations
 * to modify the mmp view when the control values is changed.
 */
public class SingleSettingTextHandler extends ControlHandler {
	
	private EMMPStatement statement;
	private IEditingContext editingContext;
	private MMPEditorContext editorContext;

	public SingleSettingTextHandler(Control control,
			IEditingContext editingContext,
			IValidator validator,
			EMMPStatement statement,
			MMPEditorContext editorContext) {
		super(control, validator);
		this.statement = statement;
		this.editorContext = editorContext;
		this.editingContext = editingContext;
		
		addListener(new ControlHandlerAdapter() {
			@Override
			public void valueModified(Control control) {
				makeOperation(control);
			}
		});
	}
	
	public SingleSettingTextHandler(StructuredViewer viewer,
			IEditingContext editingContext,
			EMMPStatement statement,
			MMPEditorContext editorContext,
			boolean caseSensitive) {
		super(viewer, caseSensitive);
		this.statement = statement;
		this.editorContext = editorContext;
		this.editingContext = editingContext;
		
		addListener(new ControlHandlerAdapter() {
			@Override
			public void valueModified(Control control) {
				makeOperation(control);
			}
		});
	}
	
	private void makeOperation(Control control) {
		ChangeSingleSettingOperation op = new ChangeSingleSettingOperation(
				editorContext.mmpView, editingContext,
				control, statement, getControlText(control));
		editorContext.executeOperation(op);
	}
	
	public void refresh() {
		Map<EMMPStatement, String> sas = editorContext.mmpView.getSingleArgumentSettings();
		String value = sas.get(statement);
		storeText(value);
	}
}
