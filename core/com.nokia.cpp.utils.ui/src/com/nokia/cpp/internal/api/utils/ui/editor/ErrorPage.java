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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class ErrorPage extends FormPage {

	private String errorString;
	private String titleString;

	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public ErrorPage(String id, String title) {
		super(id, title);
	}

	/**
	 * Create the form page
	 * @param editor
	 * @param id
	 * @param title
	 */
	public ErrorPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}
	
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}
	
	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(titleString);
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Label separator = toolkit.createSeparator(body, SWT.HORIZONTAL);
		final GridData gd_separator = new GridData(SWT.FILL, SWT.CENTER, true, false);
		separator.setLayoutData(gd_separator);

		final Label errorTextLabel = toolkit.createLabel(body, errorString, SWT.NONE);
		final GridData gd_errorTextLabel = new GridData(SWT.FILL, SWT.FILL, true, true);
		errorTextLabel.setLayoutData(gd_errorTextLabel);
	}

}
