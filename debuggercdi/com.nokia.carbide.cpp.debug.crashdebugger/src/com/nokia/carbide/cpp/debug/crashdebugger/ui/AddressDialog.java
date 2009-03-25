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
package com.nokia.carbide.cpp.debug.crashdebugger.ui;
import java.math.BigInteger;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.debug.crashdebugger.HelpIds;

public class AddressDialog extends StatusDialog {

	private Text address;
	private String enteredAddress = ""; //$NON-NLS-1$

	public AddressDialog(Shell parent) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		setTitle(Messages.getString("AddressDialog.1")); //$NON-NLS-1$
	}

	/**
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, HelpIds.ADDRESS_DIALOG);
	}		

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite= (Composite) super.createDialogArea(parent);
		
		GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 2;
		
		final Label addressLabel = new Label(composite, SWT.NONE);
		addressLabel.setText(Messages.getString("AddressDialog.2")); //$NON-NLS-1$
		GridData data = new GridData();
		data.horizontalSpan = 1;
		addressLabel.setLayoutData(data);
		addressLabel.setToolTipText(Messages.getString("AddressDialog.3")); //$NON-NLS-1$

		address = new Text(composite, SWT.SINGLE | SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		address.setLayoutData(data);
		address.setToolTipText(Messages.getString("AddressDialog.4")); //$NON-NLS-1$
		address.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkValues();
			}
		});

		applyDialogFont(composite);		

		return composite;
	}

	public void create() {
		super.create();
		checkValues();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getInitialSize()
	 */
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		size.x *= 2;
		
		return size;
	}

	protected void okPressed() {
		enteredAddress = address.getText().trim();
		if (enteredAddress.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
			enteredAddress = enteredAddress.substring(2, enteredAddress.length());
		}
		super.okPressed();
	}

	protected void checkValues() {
		StatusInfo status= new StatusInfo();
		status.setOK();
		
		String addr = address.getText().trim();
		if (addr.length() > 0) {
			boolean goodValue = false;
			if (addr.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
				addr = addr.substring(2, addr.length());
			}
			if (addr.length() <= 8) {
				try {
					// if this doesn't throw an exception then we
					// have a good hex value
					BigInteger ignore = new BigInteger(addr, 16);
					goodValue = true;
				} catch (NumberFormatException e) {
				}
			}
			if (!goodValue) {
				status.setError(Messages.getString("AddressDialog.7")); //$NON-NLS-1$
			}
		}
		else {
			status.setError(Messages.getString("AddressDialog.8")); //$NON-NLS-1$
		}

		updateStatus(status);		
	}

	public String getAddress() {
		return enteredAddress;
	}
}
