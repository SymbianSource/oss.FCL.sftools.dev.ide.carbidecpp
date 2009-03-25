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
package com.nokia.cpp.internal.api.utils.ui.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.utils.ui.noexport.Messages;
import com.nokia.cpp.utils.ui.noexport.UtilsUIPlugin;

public class FormEditorEditingContext extends ComposeableEditingContext {
	
	private FormEditor formEditor;
	private Object inputObject;
	
	public FormEditorEditingContext(IEditingContext outerContext, FormEditor editor, Object inputObject) {
		super(outerContext);
		Check.checkArg(editor);
		this.formEditor = editor;
		this.inputObject = inputObject;
	}
	
	public FormEditorEditingContext(FormEditor editor, Object inputObject) {
		super(null);
		Check.checkArg(editor);
		this.formEditor = editor;
		this.inputObject = inputObject;
	}

	public IStatus showSelf() {
		IStatus result = null;
		IFormPage page = formEditor.selectReveal(inputObject);
		if (page != null) {
			if (formEditor.getActivePageInstance() != page) {
				formEditor.setActivePage(page.getId());
			}
			if (inputObject instanceof Control) {
				Control ctl = (Control) inputObject;
				ctl.setFocus();
			}
		}
		else {
			String msg = Messages.getString("FormEditorEditingContext.cantShowUndo"); //$NON-NLS-1$
			result = Logging.newSimpleStatus(UtilsUIPlugin.getDefault(), IStatus.WARNING, msg, null);
		}
		return result;
	}

	public Shell getShell() {
		return formEditor.getSite().getShell();
	}
}
