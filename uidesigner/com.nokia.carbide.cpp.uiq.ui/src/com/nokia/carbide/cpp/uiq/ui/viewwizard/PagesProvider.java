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
/* START_USECASES: CU1 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.viewwizard;

import com.nokia.carbide.internal.api.templatewizard.ui.IExtraPagesProvider;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import java.util.Arrays;
import java.util.List;

public class PagesProvider implements IExtraPagesProvider {
	
	private static ViewWizardManager wizardManager;
	
	public void setManager(IWizard wizard, ViewWizardManager manager) {
		if (wizardManager != null) {
			wizardManager.disposePages();
		}
		wizardManager = manager;
	}
	
	public List<IWizardDataPage> getPages(IWorkbenchWizard wizard) {
		return Arrays.asList(wizardManager.getPages());
	}

	public void init(IWorkbenchWizard wizard, IWorkbench workbench, IStructuredSelection selection) {
		setManager(wizard, new ViewWizardManager());
		wizardManager.createAdditionalPages(wizard, workbench, selection);
	}
}
