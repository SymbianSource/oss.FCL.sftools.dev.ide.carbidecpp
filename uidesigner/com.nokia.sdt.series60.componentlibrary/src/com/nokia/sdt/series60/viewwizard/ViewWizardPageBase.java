/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.viewwizard;


import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.sdt.series60.component.Series60ComponentPlugin;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.PlatformUI;

import java.util.Collections;
import java.util.Map;

abstract class ViewWizardPageBase extends WizardPage implements IWizardDataPage {
	public static final String NAME_KEY = ".uid"; //$NON-NLS-1$
	
	private ViewWizardManager wizardManager;

	public ViewWizardPageBase(String pageName, ViewWizardManager wizardManager) {
		super(pageName);
		this.wizardManager = wizardManager;
	}
	
	protected ViewWizardManager getWizardManager() {
		return wizardManager;
	}

	protected void enteringPage() {
		wizardManager.setCurrentPage(this);
	}
	
	protected void leavingPage(Object nextPage) {}

    /**
     * The <code>WizardPage</code> implementation of this method 
     * declared on <code>DialogPage</code> updates the container
     * if this is the current page.
     */
    public void setWarningMessage(String newMessage) {
        super.setMessage(newMessage, IMessageProvider.WARNING);
        if (isCurrentPage()) {
            getContainer().updateMessage();
        }
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardPage#getNextPage()
	 */
	public IWizardPage getNextPage() {
		return wizardManager.getNextPage(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardPage#getPreviousPage()
	 */
	public IWizardPage getPreviousPage() {
		return wizardManager.getPreviousPage(this);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			enteringPage();
		else
			leavingPage(getNextPage());
	}

	public Map<String, Object> getPageValues() {
		return Collections.EMPTY_MAP;
	}
	
	protected void setHelpContextId(String pageId) {
		String helpContextId = Series60ComponentPlugin.ID + "." + pageId;
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), helpContextId );
	}
}

