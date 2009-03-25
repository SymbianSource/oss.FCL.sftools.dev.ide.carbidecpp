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

import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.ChangeStringValueOperation;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.IMMPStringValueProvider;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

public class StringSettingTextHandler extends ControlHandler {
	
	private IEditingContext editingContext;
	private MMPEditorContext editorContext;
	private final IMMPStringValueProvider stringValueProvider;

	public StringSettingTextHandler(Control control, 
			IEditingContext editingContext,
			IValidator validator, 
			IMMPStringValueProvider stringValueProvider,
			MMPEditorContext editorContext) {
		super(control, validator);
		this.stringValueProvider = stringValueProvider;
		this.editingContext = editingContext;
		this.editorContext = editorContext;
		
		addListener(new ControlHandlerAdapter() {
			@Override
			public void valueModified(Control control) {
				makeOperation(control);
			}
		});
	}
	
	private void makeOperation(Control control) {
		ChangeStringValueOperation op = new ChangeStringValueOperation(
				editorContext.mmpView, editingContext,
				control, stringValueProvider, getControlText(control));
		editorContext.executeOperation(op);
	}

	@Override
	protected void doRefresh() {
		String value = stringValueProvider.fetchString(editorContext.mmpView);
		storeText(value);
	}
}
