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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.FormUtilities;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class OptionsPage extends MMPEditorFormPage {
	
	private LinkerSectionPart linkerSectionPart;
	private RuntimeSectionPart runtimeSectionPart;
	private CompilerSectionPart compilerSectionPart;
	private KernelSectionPart kernelSectionPart;

	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public OptionsPage(MMPEditorContext editorContext) {
		super(editorContext, MMPEditorContext.OPTIONS_PAGE_ID, Messages.OptionsPage_optionsPageTitle);
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HelpContexts.OPTIONS_PAGE);
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(Messages.OptionsPage_optionsFormTitle);
		Composite body = form.getBody();
		body.setLayout(new AlternativeColumnLayout());
		toolkit.paintBordersFor(body);
	
		FormUtilities.addHelpContextToolbarItem(form.getForm(), 
				HelpContexts.OPTIONS_PAGE, 
				Messages.helpTooltip);

		runtimeSectionPart = new RuntimeSectionPart(body, toolkit, 
				Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED, 
				editorContext, controlManager);
		final Section section = runtimeSectionPart.getSection();
		toolkit.paintBordersFor(section);
		runtimeSectionPart.initialize(managedForm);

		compilerSectionPart = new CompilerSectionPart(body, toolkit, 
				Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED,
				editorContext, controlManager);
		final Section section_1 = compilerSectionPart.getSection();
		toolkit.paintBordersFor(section_1);
		compilerSectionPart.initialize(managedForm);
		
		linkerSectionPart = new LinkerSectionPart(body, toolkit, 
				Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED, 
				editorContext, controlManager);
		final Section section_2 = linkerSectionPart.getSection();
		toolkit.paintBordersFor(section_2);
		linkerSectionPart.initialize(managedForm);
		
		kernelSectionPart = new KernelSectionPart(body, toolkit, 
				Section.TWISTIE | Section.TITLE_BAR, 
				editorContext, controlManager);
		toolkit.paintBordersFor(kernelSectionPart.getSection());
		kernelSectionPart.initialize(managedForm);
	}
	
	void addInclude(EMMPListSelector includeType) {
		if (includeType == EMMPListSelector.USER_INCLUDES) {
			compilerSectionPart.getModel().userIncludes.doAdd();
		} else if (includeType == EMMPListSelector.SYS_INCLUDES) {
			compilerSectionPart.getModel().sysIncludes.doAdd();
		}
	}

	public void refresh() {
		super.refresh();
		if (getPartControl() != null) {
			runtimeSectionPart.refresh();
			compilerSectionPart.refresh();
			linkerSectionPart.refresh();
			kernelSectionPart.refresh();
		}
	}

	public LinkerSectionPart getLinkerSection() {
		return linkerSectionPart;
	}

	public RuntimeSectionPart getRuntimeSection() {
		return runtimeSectionPart;
	}

	public CompilerSectionPart getCompilerSection() {
		return compilerSectionPart;
	}

	public KernelSectionPart getKernelSection() {
		return kernelSectionPart;
	}

}
