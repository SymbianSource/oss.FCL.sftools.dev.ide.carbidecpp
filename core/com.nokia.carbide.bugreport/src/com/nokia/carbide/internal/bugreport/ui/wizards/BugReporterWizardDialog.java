/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugreport.ui.wizards;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.dialogs.*;

import com.nokia.carbide.internal.bugreport.resources.Messages;

/**
 * Our own wizard dialog class, so that we can have Send Report button 
 * instead of Finish button in the wizard.
 *
 */
public class BugReporterWizardDialog extends WizardDialog{

	@Override
	public void create() {
		super.create();
		
		getButton(IDialogConstants.FINISH_ID).setText(Messages.getString("BugReporterWizardDialog.SendReport")); //$NON-NLS-1$
	}

	public BugReporterWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
	}
}
