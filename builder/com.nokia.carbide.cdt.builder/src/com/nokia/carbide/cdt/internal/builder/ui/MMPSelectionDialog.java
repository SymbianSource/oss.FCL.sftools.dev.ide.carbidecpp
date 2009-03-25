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
package com.nokia.carbide.cdt.internal.builder.ui;

import java.util.List;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;


public class MMPSelectionDialog extends StatusDialog {

	private org.eclipse.swt.widgets.List selectionList;
	private List<String> mmpFiles;
	private int selectedItem;

	
	/**
	 * Create the dialog
	 * @param parent
	 * @param file
	 */
	public MMPSelectionDialog(Shell parent, List<String> mmpFiles) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.mmpFiles = mmpFiles;
	}

	/**
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Select MMP File");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_MMP_SELECTION_DIALOG);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout());
		
		selectionList = new org.eclipse.swt.widgets.List(composite, SWT.BORDER | SWT.SINGLE);
		selectionList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		for (String mmp : mmpFiles) {
			selectionList.add(mmp);
		}
		
		selectionList.select(0);
		
		applyDialogFont(composite);		

		return composite;
	}

	@Override
	protected void okPressed() {
		selectedItem = selectionList.getSelectionIndex();
		super.okPressed();
	}

	public int getselectedIndex() {
		return selectedItem;
	}
}
