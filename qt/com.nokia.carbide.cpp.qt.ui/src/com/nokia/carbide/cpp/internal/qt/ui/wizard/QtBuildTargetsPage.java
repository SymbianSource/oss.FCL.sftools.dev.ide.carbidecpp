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
package com.nokia.carbide.cpp.internal.qt.ui.wizard;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.nokia.carbide.cpp.internal.qt.core.QtConfigFilter;
import com.nokia.carbide.cpp.internal.qt.core.QtSDKFilter;
import com.nokia.carbide.cpp.project.ui.sharedui.NewProjectPage;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.ui.shared.BuildTargetsPage;
import com.nokia.cpp.internal.api.utils.core.HostOS;


public class QtBuildTargetsPage extends BuildTargetsPage {

	private Button qtFilterCheckbox;
	private QtSDKFilter sdkFilter;
	private QtConfigFilter configFilter;

	
	public QtBuildTargetsPage() {
		super();
		setHideFilterCheckbox();
		sdkFilter = new QtSDKFilter();
		configFilter = new QtConfigFilter();
		setTitle(Messages.QtBuildTargetsPage_title);
	}

    @Override
	protected void addOtherControls(Composite parent) {

		qtFilterCheckbox = new Button(parent, SWT.CHECK);
		qtFilterCheckbox.setText(Messages.QtBuildTargetsPage_filterCheckboxText);
		qtFilterCheckbox.setToolTipText(Messages.QtBuildTargetsPage_filterCheckboxToolTip);
		qtFilterCheckbox.setSelection(true); // default to checked
       	filteringContentProviderWrapper.setFilter(sdkFilter);
       	filteringContentProviderWrapper.setConfigFilter(configFilter);
       	qtFilterCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (qtFilterCheckbox.getSelection()) {
			       	filteringContentProviderWrapper.setFilter(sdkFilter);
			       	filteringContentProviderWrapper.setConfigFilter(configFilter);
				} else {
			       	filteringContentProviderWrapper.setFilter(null);
			       	filteringContentProviderWrapper.setConfigFilter(null);
				}
				viewer.refresh();
				setPageComplete(validatePage());
			}
        });
       	qtFilterCheckbox.setData(UID, "qtFilterCheckbox"); //$NON-NLS-1$
	}

	protected boolean validatePage() {
		if (viewer.getTree().getItemCount() == 0) {
			if (qtFilterCheckbox.getSelection())
				setErrorMessage(Messages.QtBuildTargetsPage_noQtConfigsError);
			return false;
		}
		
		if (HostOS.IS_WIN32){
			if ((viewer.getCheckedElements()).length > 1) {
				Object[] checkedElements = viewer.getCheckedElements();
				for (Object obj : checkedElements){
					TreeNode node = (TreeNode)obj;
					if (node.getValue() instanceof ISymbianBuildContext) {
						String epocRoot = ((ISymbianBuildContext)node.getValue()).getSDK().getEPOCROOT();
						IPath path = new Path(epocRoot);
						// This supports both the Qt project wizard and .pro file import wizard
						// So we need to check wizard page instances to figure out which wizard we are running
						String newProjectPageName = com.nokia.carbide.cpp.internal.project.ui.Messages.getString("NewProjectPage.Name");
						IWizardPage npwp = this.getWizard().getPage(newProjectPageName);
						if (npwp == null){
							newProjectPageName = com.nokia.carbide.cpp.internal.qt.ui.wizard.Messages.QtProFileSelectionPage_title;
							npwp = this.getWizard().getPage(newProjectPageName);
						}
						
						if (npwp != null){
							IPath projectLocation = null;
							if (npwp instanceof NewProjectPage){
								NewProjectPage npp = (NewProjectPage)this.getWizard().getStartingPage();
								projectLocation = npp.getLocationPath();
							}
							else if (npwp instanceof QtProFileSelectionPage){
								QtProFileSelectionPage qtpfsp = (QtProFileSelectionPage)this.getWizard().getStartingPage();
								projectLocation = new Path(qtpfsp.getProFilePath());
							}
							
							if (projectLocation != null){
								if (!projectLocation.getDevice().equalsIgnoreCase(path.getDevice())){
									setErrorMessage(Messages.QtBuildTargetsPage_mismatchedDriveSpec);
									return false;
								}
							}
						}
					}
				}
			}
		}
		
		return super.validatePage();
	}	
}
