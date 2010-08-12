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

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
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
		final ImportExportData data = importPage.getData();
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					P2Utils.doInstall(data.getURIs(), data.getFeatureInfos(), data.getWantsVersions(), monitor);
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				}
			}
		};
		
		try {
			getContainer().run(true, true, runnable);
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", 
					MessageFormat.format("Could not install due to error: {0}", e.getLocalizedMessage()));
		}
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons\\install_wiz.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
		setWindowTitle("Import Installed Feature Configuration");
		importPage = new ImportPage();
		addPage(importPage);
	}

}
