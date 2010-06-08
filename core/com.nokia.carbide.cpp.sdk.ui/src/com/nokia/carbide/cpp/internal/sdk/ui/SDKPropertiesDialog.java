/*
* Copyright (c) 2009-2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.cpp.internal.sdk.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SDKPropertiesDialog extends TrayDialog {
	
	ISymbianSDK sdk;
	private Table propsTable;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public SDKPropertiesDialog(Shell parentShell, ISymbianSDK sdk) {
		super(parentShell);
		this.sdk = sdk;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.getString("SDKPropertiesDialog.SDK_Properties_For") + sdk.getUniqueId()); //$NON-NLS-1$
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		final GridLayout gridLayout = new GridLayout();
		container.setLayout(gridLayout);

		final Label sdkPropertiesLabel = new Label(container, SWT.NONE);
		sdkPropertiesLabel.setText(Messages.getString("SDKPropertiesDialog.Available_SDK_Properties")); //$NON-NLS-1$
		
		propsTable = new Table(container, SWT.BORDER);
		propsTable.setLinesVisible(true);
		propsTable.setHeaderVisible(true);
		final GridData sdkPropTable = new GridData(GridData.FILL, GridData.FILL, true, true);
		sdkPropTable.widthHint = 300;
		propsTable.setLayoutData(sdkPropTable);
		
		final TableColumn sdkPropCol1 = new TableColumn(propsTable, SWT.NONE);
		sdkPropCol1.setWidth(111);
		sdkPropCol1.setText(Messages.getString("SDKPropertiesDialog.Property")); //$NON-NLS-1$

		final TableColumn sdkPropCol2 = new TableColumn(propsTable, SWT.NONE);
		sdkPropCol2.setWidth(287);
		sdkPropCol2.setText(Messages.getString("SDKPropertiesDialog.Value")); //$NON-NLS-1$
		
		// OS Version at Row 1.
		TableItem itemOSVersion = new TableItem(propsTable, SWT.NONE);
		itemOSVersion.setText(new String[] {Messages.getString("SDKPropertiesDialog.OS_Version"),  //$NON-NLS-2$
											sdk.getOSVersion().toString() + sdk.getSDKOSBranch()}); //$NON-NLS-1$
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, SDKUIHelpIds.SDK_PROPERTIES_DIALOG);
		
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
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(418, 375);
	}

}
