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

import com.nokia.sdt.symbian.ui.noexport.Messages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class NoTabbingDetailsPage implements IDetailsPage {

	private Section uiDesignDetailsSection;
	private IManagedForm managedForm;
	private String description;

	/**
	 * Create the details page
	 */
	public NoTabbingDetailsPage() {
		// Create the details page
	}

	/**
	 * Initialize the details page
	 * @param form
	 */
	public void initialize(IManagedForm form) {
		managedForm = form;
	}

	/**
	 * Create contents of the details page
	 * @param parent
	 */
	public void createContents(Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		parent.setLayout(gridLayout);

		uiDesignDetailsSection = toolkit.createSection(parent,
				Section.DESCRIPTION | Section.EXPANDED | Section.TITLE_BAR);
		if (description != null) {
			uiDesignDetailsSection.setDescription(description);
		}
		uiDesignDetailsSection.setLayoutData(new GridData(GridData.FILL_BOTH));
		uiDesignDetailsSection.setText(Messages.getString("NoTabbingDetailsPage.designDetailsHeading")); //$NON-NLS-1$

		final Composite composite = toolkit.createComposite(uiDesignDetailsSection, SWT.NONE);
		toolkit.paintBordersFor(composite);
		uiDesignDetailsSection.setClient(composite);
	}
	
	public void setDescription(String description) {
		this.description = description;
		if (uiDesignDetailsSection != null) {
			uiDesignDetailsSection.setDescription(description);
		}
	}

	public void dispose() {
		// Dispose
	}

	public void setFocus() {
		// Set focus
	}

	private void update() {
		// Update
	}

	public boolean setFormInput(Object input) {
		return false;
	}

	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		update();
	}

	public void commit(boolean onSave) {
		// Commit
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isStale() {
		return false;
	}

	public void refresh() {
		update();
	}

}
