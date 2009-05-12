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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlManager;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

public class KernelSectionPart extends SectionPart {

	private final MMPEditorContext editorContext;
	private final ControlManager controlManager;
	private Button asspExportsButton;
	private Button asspAbiButton;
	
	/**
	 * Create the SectionPart
	 * @param parent
	 * @param toolkit
	 * @param style
	 */
	public KernelSectionPart(Composite parent, FormToolkit toolkit, int style, 
			MMPEditorContext editorContext, ControlManager controlManager) {
		super(parent, toolkit, style);
		this.editorContext = editorContext;
		this.controlManager = controlManager;
		createClient(getSection(), toolkit);
	}

	/**
	 * Fill the section
	 */
	private void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.KernelSectionPart_kernelSectionTitle);
		Composite container = toolkit.createComposite(section);
		//
		section.setClient(container);

		asspAbiButton = toolkit.createButton(container, Messages.KernelSectionPart_asspAbiButton, SWT.CHECK);
		asspAbiButton.setToolTipText(Messages.KernelSectionPart_asspABITooltip);
		asspAbiButton.setBounds(10, 10, 100, 23);

		asspExportsButton = toolkit.createButton(container, Messages.KernelSectionPart_asspAbiExports, SWT.CHECK);
		asspExportsButton.setToolTipText(Messages.KernelSectionPart_asspExportsTooltip);
		asspExportsButton.setBounds(10, 33, 100, 23);
		
		hookControls();
		refresh();
	}
	
	private void hookControls() {
		controlManager.add(new FlagSettingHandler(asspAbiButton,
				new FormEditorEditingContext(editorContext.editor, asspAbiButton),
				EMMPStatement.ASSPABI, editorContext));		
		controlManager.add(new FlagSettingHandler(asspExportsButton,
				new FormEditorEditingContext(editorContext.editor, asspAbiButton),
				EMMPStatement.ASSPEXPORTS, editorContext));		
	}

	@Override
	public void refresh() {
		super.refresh();
		// auto-expand kernel section if any option is set
		boolean anyKernelOptionSet = false;
		if (editorContext.mmpView.getFlags().contains(EMMPStatement.ASSPABI)) {
			anyKernelOptionSet |= true;
		}
		if (editorContext.mmpView.getFlags().contains(EMMPStatement.ASSPEXPORTS)) {
			anyKernelOptionSet |= true;
		}
		if (anyKernelOptionSet) {
			getSection().setExpanded(true);
		}
	}

	public Button getAsspExportsButton() {
		return asspExportsButton;
	}

	public Button getAsspAbiButton() {
		return asspAbiButton;
	}

}
