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

import java.util.ArrayList;
import java.util.List;

import com.nokia.carbide.cpp.internal.project.ui.wizards.NewSymbianOSCppProjectWizard;
import com.nokia.carbide.cpp.project.ui.sharedui.NewProjectPage;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;

public class NewQtCppProjectWizard extends NewSymbianOSCppProjectWizard {
	
	private static final String ID = "com.nokia.carbide.cpp.qt.ui.wizard.NewQtCppProjectWizard"; //$NON-NLS-1$
	private static final String QT_PROJECT_WIZARD_FEATURE = "QT_PROJECT_WIZARD"; //$NON-NLS-1$

	private QtModulesPage modulesPage;
	
	public NewQtCppProjectWizard() {
		super();
		setWindowTitle(Messages.NewQtCppProjectWizard_title);
	}

    @Override
	public List<IWizardDataPage> getPagesAfterTemplateChoice() {
		if (pagesAfterTemplateChoice == null) {
			setNeedsProgressMonitor(true);
			pagesAfterTemplateChoice = new ArrayList<IWizardDataPage>();

			newProjectPage = new NewProjectPage(Messages.NewQtCppProjectWizard_title, Messages.NewQtCppProjectWizard_desc);
			pagesAfterTemplateChoice.add(newProjectPage);
			
			buildTargetsPage = new QtProjectWizardBuildTargetsPage(this);
			pagesAfterTemplateChoice.add(buildTargetsPage);
			
			modulesPage = new QtModulesPage(this);
			pagesAfterTemplateChoice.add(modulesPage);

			notifyTemplateChanged();
		}
		return pagesAfterTemplateChoice;
	}

	@Override
	public String getWizardId() {
		return ID;
	}

	@Override
	public String getFeatureName() {
		return QT_PROJECT_WIZARD_FEATURE;
	}
}
