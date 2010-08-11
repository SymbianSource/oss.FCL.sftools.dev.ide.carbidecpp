/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.nokia.carbide.discovery.ui.Activator;

/**
 * Wizard for importing installed features from a file and install them
 */
public class ImportWizard extends Wizard implements IImportWizard {

	private ImportPage importPage;

	public ImportWizard() {
	}

	@Override
	public boolean performFinish() {
		// TODO read from file and install
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons\\install_wiz.gif")); //$NON-NLS-1$
		setWindowTitle("Import Installed Feature Configuration");
		importPage = new ImportPage();
		addPage(importPage);
	}

}
