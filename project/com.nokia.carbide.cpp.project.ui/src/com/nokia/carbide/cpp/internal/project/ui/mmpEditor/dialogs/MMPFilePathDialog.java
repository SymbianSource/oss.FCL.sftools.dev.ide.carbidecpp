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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import java.util.List;

/**
 * Dialog allowing the user to add or edit an MMP path
 * dialog via any of three methods: 
 * - choosing an eligible file from the project in a dropdown list
 * - browsing the file system for a file
 * - typing in a path
 */
public class MMPFilePathDialog extends ValidatingDialog {

	private ChooseFileComposite chooseFileComposite;
	private final IPath initialPath;
	private final EMMPPathContext pathContext;
	private final MMPViewPathHelper pathHelper;
	private final String[] fileFilters;
	private final List<IPath> eligibleProjectFiles;
	private final ICarbideBuildConfiguration buildConfiguration;
		
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param initialPath starting value, if editing an existing value. Can be null
	 * @param pathContext used for converting the result path to an MMP path
	 * @param pathHelper used for convert the result path to an MMP path
	 * @param eligibleProjectFiles used to populate the dropdown list
	 * @param fileFilters used for filtering files when browsing the file system
	 */
	public MMPFilePathDialog(Shell parentShell, IPath initialPath,
			EMMPPathContext pathContext, MMPViewPathHelper pathHelper,
			ICarbideBuildConfiguration buildConfiguration,
			List<IPath> eligibleProjectFiles, String fileFilters[]) {
		super(parentShell);
		this.initialPath = initialPath;
		this.pathContext = pathContext;
		this.pathHelper = pathHelper;
		this.buildConfiguration = buildConfiguration;
		this.eligibleProjectFiles = eligibleProjectFiles;
		this.fileFilters = fileFilters;	
		setHelpAvailable(true);
	}
	
	/**
	 * Returns the user entered path if OK was pressed.
	 */
	public IPath getResultPath() {
		return chooseFileComposite.getResultPath();
	}
	
	public IStatus validate() {
		IStatus result = chooseFileComposite.validate();
		if (!result.isOK()) {
			chooseFileComposite.setDefaultFocus();
		}
		return result;
	}

	@Override
	protected void captureResults() {
		chooseFileComposite.capturePath();
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout());

		chooseFileComposite = new ChooseFileComposite(container, SWT.NONE, 
				initialPath, pathContext, pathHelper, buildConfiguration,
				eligibleProjectFiles, fileFilters,
				Messages.getString("MMPFilePathDialog.filePathLabel")); //$NON-NLS-1$ 

		return container;
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
		return new Point(424, 192);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("MMPFilePathDialog.dialogTitle"));  //$NON-NLS-1$
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.MMPFILEPATH_DIALOG);
	}

	public Combo getFilePathCombo() {
		return chooseFileComposite.getPathViewerCombo();
	}

	public Button getBrowseButton() {
		return chooseFileComposite.getBrowseButton();
	}

}
