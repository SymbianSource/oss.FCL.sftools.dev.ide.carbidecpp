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

import com.nokia.carbide.cpp.internal.api.sdk.ui.TemplateSDKsFilter;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.sharedui.ChooseProjectPage;
import com.nokia.carbide.cpp.internal.project.ui.sharedui.ClassNameAndLocationPage;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.carbide.internal.api.templatewizard.ui.TemplateWizard;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.viewers.IStructuredSelection;

import java.util.List;

/**
 * Wizard for creating Symbian OS C++ class
 */
public class NewSymbianOSCppClassWizard extends TemplateWizard {
	
	private static final String ID = "com.nokia.carbide.cpp.project.ui.wizards.NewSymbianOSCppClassWizard"; 
	private ChooseProjectPage chooseProjectPage;
	private ClassNameAndLocationPage chooseClassLocationPage;
	
	public NewSymbianOSCppClassWizard() {
		super();
		setFilterCheckboxLabel(Messages.getString("NewSymbianOSCppClassWizard.FilterCheckboxLabel")); //$NON-NLS-1$
		setTemplateFilter(new TemplateSDKsFilter());
		setWindowTitle(Messages.getString("NewSymbianOSCppClassWizard.WizardTitle")); //$NON-NLS-1$
	}

    @Override
    protected void initializeDefaultPageImageDescriptor() {
		setDefaultPageImageDescriptor(
				CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_NEW_SOS_CLASS_WIZARD_BANNER));
    }
    
    @Override
	public void addPages() {
		String title = Messages.getString("NewSymbianOSCppClassWizard.ChooseProjectPageTitle"); //$NON-NLS-1$
		String description = Messages.getString("NewSymbianOSCppClassWizard.ChooseProjectPageDesc"); //$NON-NLS-1$
		chooseProjectPage = new ChooseProjectPage(title, description);
		
		title = Messages.getString("NewSymbianOSCppClassWizard.ChooseClassLocationTitle"); //$NON-NLS-1$
		description = Messages.getString("NewSymbianOSCppClassWizard.ChooseClassLocationDesc"); //$NON-NLS-1$
		chooseClassLocationPage = new ClassNameAndLocationPage(title, description);
		
		IStructuredSelection selection = getSelection();
		if (!selection.isEmpty()) {
			Object object = selection.getFirstElement();
			if (object instanceof ICProject)
				object = ((ICProject) object).getProject();
			if (object instanceof IProject)
				chooseProjectPage.setInitialSelection((IProject) object);
		}
		addPage(chooseProjectPage);
		addPage(chooseClassLocationPage);
		super.addPages();
	}

	@Override
	public List<IWizardDataPage> getPagesAfterTemplateChoice() {
		return null;
	}

	@Override
	public String getChooseTemplatePageTitle() {
		return Messages.getString("NewSymbianOSCppClassWizard.ChooseTemplatePageTitle"); //$NON-NLS-1$
	}

	@Override
	public String getChooseTemplatePageDescription() {
		return null;
	}

	@Override
	public String getWizardId() {
		return ID;
	}

	@Override
	public ISchedulingRule getJobSchedulingRule() {
		if (chooseProjectPage == null)
			return null;
		
		return chooseProjectPage.getProject().getWorkspace().getRoot();
	}

	@Override
	public String getProcessingTitle() {
		return Messages.getString("NewSymbianOSCppClassWizard.ProcessTitle"); //$NON-NLS-1$
	}
}
