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

import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;

public class ViewsFormPage extends FormPage {
	
	private ViewMasterDetailsBlock masterDetailsBlock;
	private AppEditorContext editorContext;

	private static final String HELP_CONTEXT_ID = 
		UIPlugin.PLUGIN_ID + "." + "appEditorViewsFormPageContext"; //$NON-NLS-1$
	
	public ViewsFormPage(AppEditorContext editorContext) {
		super(editorContext.getFormEditor(), AppEditorContext.VIEWS_PAGE_ID, Messages.getString("ViewsFormPage.pageTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		this.editorContext = editorContext;
		masterDetailsBlock = new ViewMasterDetailsBlock(this, editorContext);
	}

	protected void createFormContent(IManagedForm managedForm) {
		masterDetailsBlock.createContent(managedForm);
		masterDetailsBlock.initialDisplay();
	}
	
	@Override
	public boolean selectReveal(Object object) {
		return masterDetailsBlock.selectReveal(object);
	}

	@Override
	public void dispose() {
		if (masterDetailsBlock != null) {
			masterDetailsBlock.dispose();
		}
		super.dispose();
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (adapter.getClass().equals(AppEditorContext.class)) {
			result = editorContext;
		}
		else {
			result = super.getAdapter(adapter);
		}
		return result;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HELP_CONTEXT_ID);
	}
}
