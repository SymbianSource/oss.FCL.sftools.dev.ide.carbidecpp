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
package com.nokia.carbide.cpp.internal.project.ui.importWizards;

import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.internal.api.builder.ui.MMPSelectionUI;
import com.nokia.carbide.cdt.internal.api.builder.ui.MMPSelectionUI.FileInfo;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import java.util.*;

public class MMPSelectionPage extends WizardPage {
	
    private BldInfImportWizard theWizard;
	private MMPSelectionUI selectionUI;
    
    public MMPSelectionPage(BldInfImportWizard wizard) {
		super(Messages.MMPSelectionPage_title);
		setTitle(Messages.MMPSelectionPage_title);
		setDescription(Messages.MMPSelectionPage_description);
		theWizard = wizard;
	}

	public void createControl(Composite parent) {
		setPageComplete(false);
		setErrorMessage(null);
		setMessage(null);

		initializeDialogUnits(parent);
        
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createControls(composite);
        setControl(composite);
        
        composite.setData(".uid", "MMPSelectionPage"); //$NON-NLS-1$ //$NON-NLS-2$
		getControl().setData("MMPSelectionPage", this); //$NON-NLS-1$

		setPageComplete(validatePage());
	}
	
	private void createControls(Composite parent) {
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 1;

		selectionUI = new MMPSelectionUI(parent, SWT.NONE, getContainer());
		selectionUI.setLayoutData(gd);
		selectionUI.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				setPageComplete(validatePage());
			}
		});
	}

    private boolean validatePage() {
		setErrorMessage(null);
		setMessage(null);
		if (selectionUI.getSelection().isEmpty()) {
			setMessage(Messages.MMPSelectionPage_noMMPsFoundWarning, WARNING);
			// just a warning
			return true;
		}
		if (selectionUI.isUncheckedAll()) {
			setErrorMessage(Messages.MMPSelectionPage_noMMPsSelectedError);
			return false;
		}
		// if there are any new-style prj extensions, warn them that they won't be built
		// when not building the entire bld.inf.
		boolean hasProjectExtensions = selectionUI.hasProjectExtensions();
		if (hasProjectExtensions && !areAllMakMakeReferencesChecked()) {
			setMessage(Messages.MMPSelectionPage_prjExtensionsWarning, WARNING);
			return true;
		}
		return true;
    }

	@Override
	public void setVisible(boolean visible) {
		// this gets called just before the page goes in or out of view.  if it's
		// going into view then get the list of makemake refs for the bld.inf.  when
		// we do save the bld.inf file and list of build configs we parsed with so we
		// know if we need to re-parse again if either of these changes, e.g. the user
		// hits back and selects a different bld.inf or set of configs
		if (visible) {
			selectionUI.setBldInfFile(new Path(theWizard.getBldInfFile()), theWizard.getSelectedConfigs());
		}
		super.setVisible(visible);
	}
    
	public boolean areAllMakMakeReferencesChecked() {
		return selectionUI.isCheckedAll();
	}
	
	public List<String> getSelectedMakMakeReferences() {
		List<String> names = new ArrayList<String>();
		IStructuredSelection selection = (IStructuredSelection) selectionUI.getSelection();
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			FileInfo info = (FileInfo) iterator.next();
			if (info.isChecked()) {
				String name = info.getFileName();
				if (info.isTest())
					name += " " + ICarbideProjectInfo.TEST_COMPONENT_LABEL; //$NON-NLS-1$
				names.add(name);
			}
		}
		return names;
	}

	@Override
	public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl().getShell(), ProjectUIHelpIds.MMP_SELECTION_PAGE);
	}
}
