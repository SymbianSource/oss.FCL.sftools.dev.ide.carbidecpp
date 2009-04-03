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
package com.nokia.carbide.cpp.internal.project.ui.dialogs;

import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;

import org.eclipse.core.resources.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

import java.util.ArrayList;
import java.util.List;

public class UpdateProjectFilesQueryDialog extends TrayDialog {
	
	private Table changeList;
	private Button rememberButton;
	private boolean rememberDecision = false;
	private IProject project;
	private IFile file;
	private List<String> changeDetails = new ArrayList<String>(0);
	
	
	public UpdateProjectFilesQueryDialog(Shell shell, IProject project, IFile file, List<String> details) {
		super(shell);
		this.project = project;
		this.file = file;
		this.changeDetails.addAll(details);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		setHelpAvailable(true);
	}

	/*
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("UpdateProjectFilesQueryDialog.DialogTitle")); //$NON-NLS-1$
		newShell.setImage(CarbideUIPlugin.getSharedImages().getImage(ICarbideSharedImages.IMG_CARBIDE_C_ICON_16_16));
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, ProjectUIHelpIds.UPDATE_FILES_QUERY_DIALOG);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);

		final Label resourcesChangedLabel = new Label(container, SWT.NONE);
		resourcesChangedLabel.setText(Messages.getString("UpdateProjectFilesQueryDialog.ChangesDetectedLabelText") + project.getName()); //$NON-NLS-1$
		
		changeList = new Table(container, SWT.HIDE_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.widthHint = 300;
        gd.heightHint = 4 * changeList.getItemHeight();
        changeList.setLayoutData(gd);
        for (String change : changeDetails) {
            TableItem newItem = new TableItem(changeList, SWT.NONE);
            newItem.setText(change);
        }

		final Label queryLabel = new Label(container, SWT.NONE);
		queryLabel.setText(Messages.getString("UpdateProjectFilesQueryDialog.UpdateQuertText1") + file.getFullPath() + Messages.getString("UpdateProjectFilesQueryDialog.UpdateQuertText2")); //$NON-NLS-1$ //$NON-NLS-2$
		
        rememberButton = new Button(container, SWT.CHECK);
        rememberButton.setText(Messages.getString("UpdateProjectFilesQueryDialog.RememberMyDecisionText")); //$NON-NLS-1$
        rememberButton.setToolTipText(Messages.getString("UpdateProjectFilesQueryDialog.RememberMyDecisionToolTip")); //$NON-NLS-1$
        
        return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.YES_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.NO_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(600, 256);
	}

	@Override
	protected void okPressed() {
		rememberDecision = rememberButton.getSelection();
		super.okPressed();
	}
	
	@Override
	protected void cancelPressed() {
		rememberDecision = rememberButton.getSelection();
		super.cancelPressed();
	}

	public boolean rememberDecision() {
		return rememberDecision;
	}
}
