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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.HelpContexts;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class CapabilitiesDialog extends TrayDialog {

	private Object[] checkedCapabilities;
	private CheckboxTableViewer capabilitiesViewer;
	
	static final String CAPABILITIES = Messages.getString("CapabilitiesDialog.capabilities1") + //$NON-NLS-1$
			Messages.getString("CapabilitiesDialog.capabilities2")+ //$NON-NLS-1$
			Messages.getString("CapabilitiesDialog.capabilities3"); //$NON-NLS-1$
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CapabilitiesDialog(Shell parentShell, Object[] initialCapabilities) {
		super(parentShell);
		this.checkedCapabilities = initialCapabilities;
		setHelpAvailable(true);
	}

	@Override
	protected void okPressed() {
		checkedCapabilities = capabilitiesViewer.getCheckedElements();
		super.okPressed();
	}
	
	public Object[] getCheckedCapabilities() {
		return checkedCapabilities;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		capabilitiesViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER);
		capabilitiesViewer.setContentProvider(new ArrayContentProvider());
		capabilitiesViewer.setSorter(new ViewerSorter());
		capabilitiesViewer.setLabelProvider(new LabelProvider());
		capabilitiesViewer.setInput(getCapabilities());
		Table table = capabilitiesViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		if (checkedCapabilities != null) {
			capabilitiesViewer.setCheckedElements(checkedCapabilities);
		}
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
		return new Point(369, 360);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("CapabilitiesDialog.dialogTitle")); //$NON-NLS-1$
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.CAPABILITIES_DIALOG);
	}
	
	String[] getCapabilities() {
		String temp = new String(CAPABILITIES);
		String[] result = temp.split(" "); //$NON-NLS-1$
		return result;
	}

}
