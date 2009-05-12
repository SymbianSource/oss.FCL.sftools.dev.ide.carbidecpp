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

package com.nokia.carbide.cpp.internal.project.ui.editors.inf.testapi;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.project.ui.wizards.MMPWizard;
import com.nokia.carbide.cpp.internal.project.ui.wizards.MMPWizardPage;

/**
 * An interface for testing the MMP wizard of the Bld.inf Editor
 */
public class MMPWizardTester {

	/**
	 * Retrieves the wizard page of the MMP wizard.
	 * @param wizard - MMP wizard provided by caller
	 * @return wizard page if success, null otherwise
	 */
	public static WizardPage getMMPWizardPage(Wizard wizard) {
		if (wizard != null && wizard instanceof MMPWizard) {
			MMPWizard mWizard = (MMPWizard) wizard;
			return mWizard.getMMPWizardPage();
		}
		return null;
	}

	/**
	 * Retrieves the "Folder" text widget of the MMP wizard page.
	 * @param page - MMP wizard provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getFolderText(WizardPage page) {
		if (page != null && page instanceof MMPWizardPage) {
			MMPWizardPage mPage = (MMPWizardPage) page;
			return mPage.getContainerText();
		}
		return null;
	}

	/**
	 * Retrieves the "File name" text widget of the MMP wizard page.
	 * @param page - MMP wizard provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getFileNameText(WizardPage page) {
		if (page != null && page instanceof MMPWizardPage) {
			MMPWizardPage mPage = (MMPWizardPage) page;
			return mPage.getFileNameText();
		}
		return null;
	}

	/**
	 * Retrieves the "Browse" button widget of the MMP wizard page.
	 * @param page - MMP wizard provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getBrowseButton(WizardPage page) {
		if (page != null && page instanceof MMPWizardPage) {
			MMPWizardPage mPage = (MMPWizardPage) page;
			return mPage.getBrowseButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Test MMP" button widget of the MMP wizard page.
	 * @param page - MMP wizard provided by caller
	 * @return button widget if success, null otherwise
	 */
	public static Button getTestMMPButton(WizardPage page) {
		if (page != null && page instanceof MMPWizardPage) {
			MMPWizardPage mPage = (MMPWizardPage) page;
			return mPage.getTestMMPButton();
		}
		return null;
	}

}
