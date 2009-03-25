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
/* START_USECASES: CU5 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerDataModelEditorExtender;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.commands.*;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

public class UIQViewEditorExtender implements IDesignerDataModelEditorExtender {

	private final static int DESIGNER_EDITOR_PAGE_INDEX = 0; 
	private FormEditor formEditor;
	
	//private DesignerEditorPage designPage;
	private UIConfigurationsPage uiConfigsPage;
	private CommandsPage commandsPage;

	public void editorInitialized(IDesignerDataModelEditor editor) throws CoreException {
		if (!SymbianModelUtils.getModelSDK(editor.getDataModel()).equals(SDKType.UIQ))
			return;
		
		formEditor = editor.getFormEditor();
		
		//createDesignPage();
		createUIConfigsPage(editor);
		createCommandsPage(editor);
//		formEditor.addPage(new TestEditorPage(editor, "testEditorPage", "Model")); 
		
		final IDesignerDataModelEditor dmEditor = editor;
		
		dmEditor.getCommandStack().addCommandStackEventListener(new CommandStackEventListener() {
			public void stackChanged(CommandStackEvent event) {
				switch (event.getDetail()) {
				case CommandStack.PRE_EXECUTE:
				case CommandStack.PRE_UNDO:
				case CommandStack.PRE_REDO:
					if (!(event.getCommand() instanceof EditingContextCommand)) {
						dmEditor.activatePage(DESIGNER_EDITOR_PAGE_INDEX);
					}
					break;
				default:
					break;
				}
				
			}
		});
	}

	/*private void createDesignPage() throws PartInitException {
		designPage = new DesignerEditorPage(editorContext);
		formEditor.addPage(designPage);
	}*/

	private void createUIConfigsPage(IDesignerDataModelEditor editor) throws PartInitException {
		uiConfigsPage = new UIConfigurationsPage(editor);
		formEditor.addPage(uiConfigsPage);
	}

	private void createCommandsPage(IDesignerDataModelEditor editor) throws PartInitException {
		commandsPage = new CommandsPage(editor);
		formEditor.addPage(commandsPage);
	}


}
