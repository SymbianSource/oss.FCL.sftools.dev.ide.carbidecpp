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
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerDataModelEditorExtender;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

public class UIQApplicationEditorExtender implements IDesignerDataModelEditorExtender {

	private FormEditor formEditor;
	
	private OverviewPage overviewPage;
	private ViewsFormPage viewsPage;
	private LanguagesPage languagesPage;

	private AppEditorContext editorContext;

	public void editorInitialized(IDesignerDataModelEditor editor) throws CoreException {
		if (!SymbianModelUtils.getModelSDK(editor.getDataModel()).equals(SDKType.UIQ))
			return;
		
		formEditor = editor.getFormEditor();
		editorContext = (AppEditorContext) editor.getAdapter(AppEditorContext.class);
		
		createOverviewPage();
		createViewsPage();
		createLanguagesPage();
//		formEditor.addPage(new TestEditorPage(editor, "testEditorPage", "Model")); 
	}

	private void createOverviewPage() throws PartInitException {
		overviewPage = new OverviewPage(editorContext);
		formEditor.addPage(overviewPage);
	}

	private void createViewsPage() throws PartInitException {
		viewsPage = new ViewsFormPage(editorContext);
		formEditor.addPage(viewsPage);
	}

	private void createLanguagesPage() throws PartInitException {
		languagesPage = new LanguagesPage(editorContext);
		formEditor.addPage(languagesPage);
	}


}
