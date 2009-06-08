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

import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class ExportsPage extends BldInfEditorFormPage {

	private ExportSectionPart exports;
	private ExportSectionPart testExports;
	
	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public ExportsPage(BldInfEditorContext editorContext) {
		super(editorContext, BldInfEditorContext.EXPORTS_PAGE_ID, Messages.ExportsPage_Title);
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm form = managedForm.getForm();
		form.setText(Messages.ExportsPage_Title);
		Composite body = new RowLayoutComposite(form.getBody(), SWT.NONE, form);
		toolkit.paintBordersFor(body);
		toolkit.adapt(body);
	
		FormUtilities.addHelpContextToolbarItem(form.getForm(), 
				HelpContexts.EXPORTS_PAGE, 
				Messages.ExportsPage_HelpButtonText);

		exports = new ExportSectionPart(editorContext, EBldInfListSelector.EXPORTS, body, 
				toolkit, Section.TWISTIE | Section.DESCRIPTION |Section.TITLE_BAR | Section.EXPANDED,
				controlManager, false);
		exports.initialize(managedForm);
		exports.getSection().setText(Messages.ExportsPage_ExportsSection);
		exports.getSection().setDescription(Messages.ExportsPage_ExportsSectionDescription);

		testExports = new ExportSectionPart(editorContext, EBldInfListSelector.TEST_EXPORTS, body, 
				toolkit, Section.TWISTIE | Section.TITLE_BAR| Section.DESCRIPTION,
				controlManager, true);
		testExports.initialize(managedForm);
		testExports.getSection().setText(Messages.ExportsPage_TestExportsSection);
		testExports.getSection().setDescription(Messages.ExportsPage_TestExportsSectionDescription);

		setInputs();
	}
	
	private void setInputs() {
		exports.setFormInput(editorContext.bldInfView.getExports());	
		exports.getSection().setExpanded(true);

		testExports.setFormInput(editorContext.bldInfView.getTestExports());		
		// only expand the test section if it has items
		testExports.getSection().setExpanded(editorContext.bldInfView.getTestExports().size() > 0);
	}
	
	public void refresh() {
		super.refresh();
		if (getPartControl() != null) {
			setInputs();
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HelpContexts.EXPORTS_PAGE);
	}

	public SectionPart getExportsSection() {
		return exports;
	}

	public SectionPart getTestExportsSection() {
		return testExports;
	}

	public String getErrorMessage() {
		return getManagedForm().getForm().getMessage();
	}

}
