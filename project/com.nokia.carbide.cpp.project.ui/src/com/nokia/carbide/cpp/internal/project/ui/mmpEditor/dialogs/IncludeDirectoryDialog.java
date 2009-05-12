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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs;

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.HelpContexts;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class IncludeDirectoryDialog extends ValidatingDialog {

	private final IProject project;
	private final EMMPPathContext pathContext;
	private final MMPViewPathHelper pathHelper;
	private IPath initialPath;
	private ChooseDirectoryComposite chooseDirectoryComposite;
	private final ICarbideBuildConfiguration buildConfiguration;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public IncludeDirectoryDialog(Shell parentShell, IProject project, 
			EMMPPathContext pathContext, MMPViewPathHelper pathHelper,
			ICarbideBuildConfiguration buildConfiguration) {
		super(parentShell);
		this.project = project;
		this.pathContext = pathContext;
		this.pathHelper = pathHelper;
		this.buildConfiguration = buildConfiguration;
		setHelpAvailable(true);
	}
	
	public void setInitialPath(IPath path) {
		initialPath = path;
	}
	
	public IStatus validate() {
		IStatus result = chooseDirectoryComposite.validate();
		if (!result.isOK()) {
			chooseDirectoryComposite.setDefaultFocus();
		}
		return result;
	}
	
	/**
	 * Result is returned as an MMP path
	 * @return
	 */
	public IPath getResult() {
		return chooseDirectoryComposite.getResult();
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout());

		chooseDirectoryComposite = new ChooseDirectoryComposite(container, SWT.NONE, 
				project, initialPath, pathContext, pathHelper, buildConfiguration);
				
		return container;
	}
	
	@Override
	protected void captureResults() {
		chooseDirectoryComposite.capturePath();
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return super.getInitialSize();
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("IncludeDirectoryDialog.dialogTitle")); //$NON-NLS-1$
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.INCLUDE_DIRECTORY_DIALOG);
	}

	public Combo getIncludeDirectoryCombo() {
		return chooseDirectoryComposite.getPathViewerCombo();
	}

	public Button getBrowseButton() {
		return chooseDirectoryComposite.getBrowseButton();
	}

}
