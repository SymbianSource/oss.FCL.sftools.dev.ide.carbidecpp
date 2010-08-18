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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;

/**
 * Wizard for importing installed features from a file and install them
 */
public class ImportWizard extends Wizard implements IImportWizard {

	private ImportPage importPage;

	public ImportWizard() {
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new FeatureInstallOperation(
					importPage.getURIs(), importPage.getFeatureInfos(), importPage.getWantsOriginalVersions()));
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof CoreException) {
				IStatus status = ((CoreException) cause).getStatus();
				if (allInstalledStatus(status)) {
					MessageDialog.openWarning(getShell(), Messages.ImportWizard_AllInstalledTitle, Messages.ImportWizard_AllInstalledMessage);
				}
				else {
					ErrorDialog.openError(getShell(), Messages.ImportWizard_ErrorTitle, null, status);
				}
			} else {
				MessageDialog.openError(getShell(), Messages.ImportWizard_ErrorTitle, 
						MessageFormat.format(Messages.ImportWizard_InstallErrorSimple, cause.getMessage()));
			}
		} catch (InterruptedException e) {
		}
		return true;
	}

	private boolean allInstalledStatus(IStatus status) {
		boolean isOpFailed = false;
		IStatus[] statusChildren = status.getChildren();
		if (statusChildren != null && statusChildren.length > 0) {
			isOpFailed = statusChildren[0].getCode() == 10050;
			if (isOpFailed) {
				for (int i = 1; i < statusChildren.length; i++) {
					if (statusChildren[i].getCode() != 10005)
						isOpFailed = false;
				}
			}
		}
		return isOpFailed;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons\\install_wiz.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.ImportWizard_Title);
		importPage = new ImportPage();
		addPage(importPage);
	}

}
