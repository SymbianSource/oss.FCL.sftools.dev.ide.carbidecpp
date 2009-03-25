/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.updater.ui;

import com.nokia.carbide.internal.updater.CompositeRefactoring;
import com.nokia.carbide.internal.updater.Messages;
import com.nokia.carbide.updater.*;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.swt.widgets.Composite;

import java.util.List;

/**
 *
 */
public class RefactoringUpdaterWizard extends RefactoringWizard {

	private static final String DEFAULT_TITLE = 
		Messages.getString("RefactoringUpdaterWizard.DefaultTitle"); //$NON-NLS-1$

	private static final String HELP_CONTEXT_ID = 
		CarbideUpdaterPlugin.PLUGIN_ID + ".refactoringUpdaterWizard"; //$NON-NLS-1$

	public RefactoringUpdaterWizard(List<Refactoring> refactoringList) {
		super(new CompositeRefactoring(refactoringList, DEFAULT_TITLE), 
				RefactoringWizard.WIZARD_BASED_USER_INTERFACE);
		setWindowTitle(Messages.getString("RefactoringUpdaterWizard.WindowTitle")); //$NON-NLS-1$
		setDefaultPageTitle(DEFAULT_TITLE);
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		WorkbenchUtils.setHelpContextId(pageContainer, HELP_CONTEXT_ID);
	}

	@Override
	protected void addUserInputPages() {
		// none for now
	}

}
