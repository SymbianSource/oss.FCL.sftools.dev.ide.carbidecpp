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
import org.eclipse.ui.forms.widgets.ColumnLayoutData;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.FormUtilities;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class LibrariesPage extends MMPEditorFormPage {

	private LibrarySectionPart mainLibraries;
	private LibrarySectionPart staticLibraries;
	private LibrarySectionPart asspLibraries;
	private LibrarySectionPart win32Libraries;
	private LibrarySectionPart debugLibraries;
	
	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public LibrariesPage(MMPEditorContext editorContext) {
		super(editorContext, MMPEditorContext.LIBRARIES_PAGE_ID, Messages.LibrariesPage_librariesPageTitle);
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm form = managedForm.getForm();
		form.setText(Messages.LibrariesPage_librariesFormTitle);
		Composite body = form.getBody();
		AlternativeColumnLayout cl = new AlternativeColumnLayout();
		body.setLayout(cl);
		toolkit.paintBordersFor(body);
	
		FormUtilities.addHelpContextToolbarItem(form.getForm(), 
				HelpContexts.LIBRARIES_PAGE, 
				Messages.helpTooltip);

		mainLibraries = new LibrarySectionPart(editorContext, EMMPListSelector.MAIN_LIBRARIES, body, 
				toolkit, Section.TWISTIE | Section.DESCRIPTION |Section.TITLE_BAR | Section.EXPANDED,
				controlManager);
		mainLibraries.initialize(managedForm);
		mainLibraries.getSection().setText(Messages.LibrariesPage_mainLibsSectionTitle);
		mainLibraries.getSection().setDescription(Messages.LibrariesPage_mainLibsDescription);
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.horizontalAlignment = ColumnLayoutData.LEFT;
		mainLibraries.getSection().setLayoutData(cld);

		staticLibraries = new LibrarySectionPart(editorContext, EMMPListSelector.STATIC_LIBRARIES, body, 
				toolkit, Section.TWISTIE | Section.TITLE_BAR| Section.DESCRIPTION,
				controlManager);
		staticLibraries.initialize(managedForm);
		staticLibraries.getSection().setText(Messages.LibrariesPage_staticLibsTitle);
		staticLibraries.getSection().setDescription(Messages.LibrariesPage_staticLibsDescription);
		cld = new ColumnLayoutData();
		cld.horizontalAlignment = ColumnLayoutData.LEFT;
		staticLibraries.getSection().setLayoutData(cld);

		debugLibraries = new LibrarySectionPart(editorContext, EMMPListSelector.DEBUG_LIBRARIES, body, 
				toolkit, Section.TWISTIE | Section.TITLE_BAR| Section.DESCRIPTION,
				controlManager);
		debugLibraries.initialize(managedForm);
		debugLibraries.getSection().setText(Messages.LibrariesPage_debugLibsTitle);
		debugLibraries.getSection().setDescription(Messages.LibrariesPage_debugLibsDescription);
		cld = new ColumnLayoutData();
		cld.horizontalAlignment = ColumnLayoutData.LEFT;
		debugLibraries.getSection().setLayoutData(cld);

		win32Libraries = new LibrarySectionPart(editorContext, EMMPListSelector.WIN32_LIBRARIES, body, 
				toolkit, Section.TWISTIE | Section.TITLE_BAR| Section.DESCRIPTION,
				controlManager);
		win32Libraries.initialize(managedForm);
		win32Libraries.getSection().setText(Messages.LibrariesPage_win32LibsTitle);
		win32Libraries.getSection().setDescription(Messages.LibrariesPage_win32LibsDescription);
		cld = new ColumnLayoutData();
		cld.horizontalAlignment = ColumnLayoutData.LEFT;
		win32Libraries.getSection().setLayoutData(cld);
	
		asspLibraries = new LibrarySectionPart(editorContext, EMMPListSelector.ASSP_LIBRARIES, body, 
				toolkit, Section.TWISTIE | Section.TITLE_BAR| Section.DESCRIPTION,
				controlManager);
		asspLibraries.initialize(managedForm);
		asspLibraries.getSection().setText(Messages.LibrariesPage_asspLibsTitle);
		asspLibraries.getSection().setDescription(Messages.LibrariesPage_asspLibsDescription);
		cld = new ColumnLayoutData();
		cld.horizontalAlignment = ColumnLayoutData.LEFT;
		asspLibraries.getSection().setLayoutData(cld);
		
		setInputs();
	}
	
	private void setInputs() {
		mainLibraries.setFormInput(editorContext.mmpView.getLibraries());	
		staticLibraries.setFormInput(editorContext.mmpView.getStaticLibraries());
		debugLibraries.setFormInput(editorContext.mmpView.getDebugLibraries());
		win32Libraries.setFormInput(editorContext.mmpView.getWin32Libraries());
		asspLibraries.setFormInput(editorContext.mmpView.getASSPLibraries());
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HelpContexts.LIBRARIES_PAGE);
	}

	public void refresh() {
		super.refresh();
		if (getPartControl() != null) {
			setInputs();
		}
	}

	public LibrarySectionPart getMainLibrariesSection() {
		return mainLibraries;
	}

	public LibrarySectionPart getStaticLibrariesSection() {
		return staticLibraries;
	}

	public LibrarySectionPart getASSPLibrariesSection() {
		return asspLibraries;
	}

	public LibrarySectionPart getWin32LibrariesSection() {
		return win32Libraries;
	}

	public LibrarySectionPart getDebugLibrariesSection() {
		return debugLibraries;
	}

	public String getErrorMesaage() {
		return getManagedForm().getForm().getMessage();
	}

}
