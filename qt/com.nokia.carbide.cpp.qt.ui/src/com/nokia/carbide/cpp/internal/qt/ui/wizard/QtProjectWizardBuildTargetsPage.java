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

public class QtProjectWizardBuildTargetsPage extends QtBuildTargetsPage {

	NewQtCppProjectWizard theWizard;
	
	public QtProjectWizardBuildTargetsPage(NewQtCppProjectWizard wizard) {
		super();
		
		theWizard = wizard;
	}

	protected boolean validatePage() {
		boolean valid = super.validatePage();
		if (valid) {
			checkPathWithSDKs(theWizard.getProjectPath());
		}
		return valid;
	}
}
