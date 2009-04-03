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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

/**
 * Specialization of ControlHandler that corresponds to a value
 * in IMMPView's list argument settings map
 *
 */
public class ListSettingTextHandler extends ControlHandler {

	private EMMPStatement statement;
	private IEditingContext editingContext;
	private MMPEditorContext editorContext;

	public ListSettingTextHandler(
			Control control,
			IEditingContext editingContext,
			EMMPStatement statement,
			MMPEditorContext editorContext) {
		super(control, null); 
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
		ArrayList<Object> newList = new ArrayList<Object>();
		String[] strings = splitText();
		if (strings != null) {
			for (String s : strings) {
				newList.add(s);
			}
		}
		
		ReplaceAllListValueOperation op = new ReplaceAllListValueOperation(
				editorContext.mmpView, editingContext,
				this, EMMPListSelector.CAPABILITIES, 
				newList);
		
		editorContext.executeOperation(op);
	}

	@Override
	protected void doRefresh() {
		List<String> list = editorContext.mmpView.getListArgumentSettings().get(statement);
		storeText(listToText(list));
	}
	
}
