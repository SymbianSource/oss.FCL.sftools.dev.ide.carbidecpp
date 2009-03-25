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

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.FormUtilities;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class SourcesPage extends MMPEditorFormPage {
	
	private SourcesSectionPart sourcesSection;
	private ResourcesSectionPart resourcesSection;
	private MissingSourcesSectionPart missingSourcesSectionPart;

	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public SourcesPage(MMPEditorContext editorContext) {
		super(editorContext, MMPEditorContext.SOURCES_PAGE_ID, Messages.SourcesPage_sourcesPageTitle);
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HelpContexts.SOURCES_PAGE);
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(Messages.SourcesPage_sourcesFormTitle);
		Composite body = form.getBody();
		body.setLayout(new FormLayout());
		toolkit.paintBordersFor(body);
		
		FormUtilities.addHelpContextToolbarItem(form.getForm(), 
				HelpContexts.SOURCES_PAGE, 
				Messages.helpTooltip);
		sourcesSection = new SourcesSectionPart(body, toolkit, Section.DESCRIPTION | Section.TITLE_BAR);
		final Section section = sourcesSection.getSection();
					
		resourcesSection = new ResourcesSectionPart(body, toolkit, Section.DESCRIPTION | Section.TITLE_BAR);
		final Section resourcesSection1 = resourcesSection.getSection();
		final FormData formData_1 = new FormData();
		formData_1.left = new FormAttachment(100, -331);
		formData_1.right = new FormAttachment(100, -5);
		formData_1.top = new FormAttachment(0, 5);
		resourcesSection1.setLayoutData(formData_1);
		
		final FormData formData = new FormData();
		formData.right = new FormAttachment(resourcesSection1, -5, SWT.LEFT);
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		section.setLayoutData(formData);

		missingSourcesSectionPart = new MissingSourcesSectionPart(body, toolkit, 
				Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR, this);
		Section missingSourcesSection1;
		missingSourcesSection1 = missingSourcesSectionPart.getSection();
		formData_1.bottom = new FormAttachment(missingSourcesSection1, 0, SWT.BOTTOM);
		formData.bottom = new FormAttachment(missingSourcesSection1, -5, SWT.TOP);
		final FormData formData_2 = new FormData();
		formData_2.right = new FormAttachment(resourcesSection1, -5, SWT.LEFT);
		formData_2.bottom = new FormAttachment(100, -3);
		formData_2.left = new FormAttachment(0, 5);
		missingSourcesSection1.setLayoutData(formData_2);
	
		initSections();
		
		// The form seems to cache a preferred size early on, before all our content is added.
		// That inhibits the scroll bars from enabling properly. Forcing the layout size cache 
		// to be flushed here forces the preferred size to be recalculated.
		form.reflow(true);
	}
	
	private void initSections() {
		sourcesSection.initialize(editorContext, controlManager);
		resourcesSection.initialize(editorContext, controlManager);
		missingSourcesSectionPart.initialize(getManagedForm(), editorContext, controlManager);
	}

	public void refresh() {
		super.refresh();
		if (getPartControl() != null) {
			sourcesSection.refresh();
			resourcesSection.refresh();
			List<IPath> paths = sourcesSection.getMissingFilePaths();
			missingSourcesSectionPart.setMissingFilePaths(paths);
			missingSourcesSectionPart.refresh();
			missingSourcesSectionPart.getSection().setExpanded(paths.size() > 0);
		}
	}

}
