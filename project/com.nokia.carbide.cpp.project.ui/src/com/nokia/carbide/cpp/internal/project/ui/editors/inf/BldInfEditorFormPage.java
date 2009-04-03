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
package com.nokia.carbide.cpp.internal.project.ui.editors.inf;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlManager;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.FormPage;

public class BldInfEditorFormPage extends FormPage {

	protected final BldInfEditorContext editorContext;
	protected final ControlManager controlManager = new ControlManager();

	public BldInfEditorFormPage(BldInfEditorContext editorContext, String id, String title) {
		super(editorContext.editor, id, title);
		this.editorContext = editorContext;
		controlManager.enableStatusLineValidationMessages(editorContext.getStatusLineManager());
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
        controlManager.enableFormValidationMessages(getManagedForm().getForm().getForm());
        
        // provide initial population of page
		refresh();
		controlManager.validate();
	}

	void refresh() {
		controlManager.refresh();
	}
}
