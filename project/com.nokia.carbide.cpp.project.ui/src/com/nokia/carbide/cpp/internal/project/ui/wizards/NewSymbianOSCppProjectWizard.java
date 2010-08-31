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
package com.nokia.carbide.cpp.internal.project.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.api.sdk.ui.TemplateSDKsFilter;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.project.ui.sharedui.NewProjectPage;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.ui.shared.BuildTargetsPage;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.carbide.internal.api.templatewizard.ui.TemplateWizard;
import com.nokia.cpp.internal.api.utils.core.HostOS;

/**
 * Wizard for creating Symbian OS C++ project
 */
public class NewSymbianOSCppProjectWizard extends TemplateWizard {
	
	private static final String SOS_PROJECT_WIZARD_FEATURE = "SOS_PROJECT_WIZARD"; //$NON-NLS-1$
	private static final String ID = "com.nokia.carbide.cpp.project.ui.wizards.NewSymbianOSCppProjectWizard"; //$NON-NLS-1$
	protected List<IWizardDataPage> pagesAfterTemplateChoice;
	protected BuildTargetsPage buildTargetsPage;
	protected NewProjectPage newProjectPage;

	public NewSymbianOSCppProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
		setFilterCheckboxLabel(Messages.getString("NewSymbianOSCppProjectWizard.FilterCheckboxLabel")); //$NON-NLS-1$
		setTemplateFilter(new TemplateSDKsFilter());
		setWindowTitle(Messages.getString("NewSymbianOSCppProjectWizard.WindowTitle")); //$NON-NLS-1$
		
		if (HostOS.IS_WIN32){
			ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
			((SDKManager)sdkMgr).ensureSystemDrivesSynchronized();
			if (!((SDKManager)sdkMgr).checkDevicesXMLSynchronized()){
				if (sdkMgr instanceof ISDKManagerInternal){
					ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
					sdkMgrInternal.fireDevicesXMLChanged();
				}
				
			}
		}
	}

	@Override
    protected void initializeDefaultPageImageDescriptor() {
		setDefaultPageImageDescriptor(CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_NEW_CARBIDE_PROJECT_WIZARD_BANNER));
    }

    @Override
	public List<IWizardDataPage> getPagesAfterTemplateChoice() {
		if (pagesAfterTemplateChoice == null) {
			pagesAfterTemplateChoice = new ArrayList<IWizardDataPage>();
			String title = Messages.getString("NewSymbianOSCppProjectWizard.NewProjectPageTitle"); //$NON-NLS-1$
			String description = Messages.getString("NewSymbianOSCppProjectWizard.NewProjectPageDesc"); //$NON-NLS-1$
			newProjectPage = new NewProjectPage(title, description);
			pagesAfterTemplateChoice.add(newProjectPage);
			setNeedsProgressMonitor(true);
			buildTargetsPage = new ProjectWizardBuildTargetsPage(this);
			pagesAfterTemplateChoice.add(buildTargetsPage);
			notifyTemplateChanged();
		}
		return pagesAfterTemplateChoice;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getChooseTemplatePageTitle() {
		return Messages.getString("NewSymbianOSCppProjectWizard.ChooseTemplatePageTitle"); //$NON-NLS-1$
	}

	@Override
	public String getChooseTemplatePageDescription() {
		return null;
	}
	
	public IPath getProjectPath() {
		return newProjectPage.getLocationPath();
	}

	@Override
	public String getWizardId() {
		return ID;
	}

	@Override
	public void notifyTemplateChanged() {
		if (buildTargetsPage != null)
			buildTargetsPage.setSelectedTemplate(getSelectedTemplate());
	}

	@Override
	public ISchedulingRule getJobSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	@Override
	public String getProcessingTitle() {
		return Messages.getString("NewSymbianOSCppProjectWizard.ProcessTitle"); //$NON-NLS-1$
	}

    @Override
	public boolean performFinish() {
    	newProjectPage.saveDialogSettings();
		return super.performFinish();
	}

	@Override
	public String getFeatureName() {
		return SOS_PROJECT_WIZARD_FEATURE;
	}
}
