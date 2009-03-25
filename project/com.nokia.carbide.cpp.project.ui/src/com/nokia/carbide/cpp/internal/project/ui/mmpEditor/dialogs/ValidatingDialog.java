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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;

public abstract class ValidatingDialog extends TrayDialog {

	public ValidatingDialog(Shell shell) {
		super(shell);
	}

	public ValidatingDialog(IShellProvider parentShell) {
		super(parentShell);
	}
	
	/**
	 * Return IStatus with IStatus.OK for success
	 * IStatus.ERROR for validation failure to inhibit dialog closing.
	 * IStatus.CANCEL indicates a custom failure message has been displayed already
	 * IStatus.WARNING (or any other status) to have user confirm before closing
	 * All returned IStatus must have a user-displayable message.
	 * @return
	 */
	public abstract IStatus validate();
	
	/**
	 * Called when dialog has successfully validate and results
	 * can be captured from the GUI.
	 */
	protected abstract void captureResults();
	
	@Override
	protected void okPressed() {
		IStatus validateStatus = validate();
		switch (validateStatus.getSeverity()) {
		case IStatus.OK:
			captureResults();
			super.okPressed();
			break;
			
		case IStatus.ERROR:
			MessageDialog.openError(getShell(), Messages.getString("ValidationDialogTitle"), //$NON-NLS-1$
					validateStatus.getMessage());
			break;
			
		case IStatus.CANCEL:
			// indicates the derived class has already display a validation error.
			break;
			
		default:
			boolean ok = MessageDialog.openQuestion(getShell(), Messages.getString("ValidatingDialog.confirmationTitle"),  //$NON-NLS-1$
					validateStatus.getMessage());
			if (ok) {
				captureResults();
				super.okPressed();
			}
			break;
		}
	}
	
	protected IStatus makeStatus(int severity, String message) {
		// I don't see why we'd need a specific plugin ID here, rather than just identifying the product.
		return new Status(severity, Messages.getString("ValidatingDialog.pluginName"), 0, message, null); //$NON-NLS-1$
	}
}
