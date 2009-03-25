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

package com.nokia.cdt.debug.cw.symbian;

import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;

import org.eclipse.cdt.debug.core.executables.ExecutablesManager;
import org.eclipse.cdt.debug.ui.importexecutable.AbstractImportExecutableWizard;
import org.eclipse.cdt.debug.ui.importexecutable.ImportExecutablePageOne;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;

import java.util.Arrays;
import java.util.List;

public class ImportSymbianExecutableWizard extends AbstractImportExecutableWizard {

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setDefaultPageImageDescriptor(CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_IMPORT_SOS_EXE_WIZARD_BANNER));
	}

	@Override
	public void addPages() {
		pageOne = new ImportExecutablePageOne(this);
		addPage(pageOne);
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		// this is done because AbstractImportExecutableWizard assumes a page2 exists
		// basically, this is the same as calling Wizard.getNextPage() and skipping 
		// AbstractImportExecutableWizard.getNextPage()
		List<IWizardPage> pages = Arrays.asList(getPages());
        int index = pages.indexOf(page);
        if (index == pages.size() - 1 || index == -1) {
			// last page or page not found
            return null;
		}
        return (IWizardPage) pages.get(index + 1);
	}

	@Override
	public String getPageOneTitle() {
		return Messages.getString("ImportSymbianExecutableWizard.13"); //$NON-NLS-1$
	}

	@Override
	public String getPageOneDescription() {
		return Messages.getString("ImportSymbianExecutableWizard.14"); //$NON-NLS-1$
	}

	@Override
	public String getExecutableListLabel() {
		return Messages.getString("ImportSymbianExecutableWizard.15"); //$NON-NLS-1$
	}

	@Override
	public void setupFileDialog(FileDialog dialog) {
		dialog.setText(Messages.getString("ImportSymbianExecutableWizard.16")); //$NON-NLS-1$
		dialog.setFilterExtensions(new String[] {"*.*"}); //$NON-NLS-1$
		dialog.setFilterNames(new String[] {Messages.getString("ImportSymbianExecutableWizard.18")}); //$NON-NLS-1$
	}

	@Override
	public boolean userSelectsBinaryParser() {
		return false;
	}

	@Override
	public String[] getDefaultBinaryParserIDs() {
		return new String[] {"com.nokia.cdt.debug.cw.symbian.SymbianE32", "org.eclipse.cdt.core.PE"}; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	public boolean supportsConfigurationType(ILaunchConfigurationType type) {
		return false;
	}

	@Override
	public boolean performFinish() {
		final String[] fileNames = pageOne.getSelectedExecutables();
		Job importJob = new Job("Import Executables") { //$NON-NLS-1$
			@Override
			public IStatus run(IProgressMonitor monitor) {
				ExecutablesManager.getExecutablesManager().importExecutables(fileNames, monitor);
				return Status.OK_STATUS;
			}
		};
		importJob.schedule();
		return true;
	}
}
