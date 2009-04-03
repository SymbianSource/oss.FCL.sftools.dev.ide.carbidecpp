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


package com.nokia.sdt.test.componentLibrary.editorPages;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerDataModelEditorExtender;

import org.eclipse.core.runtime.CoreException;

/**
 *
 */
public class TestEditorPageProvider implements IDesignerDataModelEditorExtender {

	public void editorInitialized(IDesignerDataModelEditor editor) throws CoreException {
		IDesignerDataModel dataModel = editor.getDataModel();
		assert(dataModel != null);
		String vendor = dataModel.getProperty("com.nokia.sdt.component.symbian.vendor");
		if (vendor.equals("com.nokia.series60")) {
			TestEditorPage testEditorPage = new TestEditorPage(editor, 
				"com.nokia.sdt.test.componentLibrary.TestEditorPage", "Model");
			editor.getFormEditor().addPage(1, testEditorPage);
		}
	}

}
