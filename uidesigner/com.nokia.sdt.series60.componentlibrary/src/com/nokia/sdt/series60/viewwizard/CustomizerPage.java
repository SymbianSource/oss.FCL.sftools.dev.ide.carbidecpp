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


import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public class CustomizerPage extends ViewWizardPageBase {

	public static final String PAGE_NAME = "Customizer"; //$NON-NLS-1$
	private IComponentCustomizerUI customizerUI;
	private Composite parent;
	private Composite curComposite;
	
	public CustomizerPage(ViewWizardManager manager) {
		super(PAGE_NAME, manager);
		setTitle(Messages.getString("CustomizerPage.PageTitle")); //$NON-NLS-1$
		setDescription(Messages.getString("CustomizerPage.PageDescription")); //$NON-NLS-1$
		
	}
	
	public void setCustomizerUI(IComponentCustomizerUI customizerUI) {
		this.customizerUI = customizerUI;
		if (parent == null)
			return;
		
		if (curComposite != null)
			curComposite.dispose();
		curComposite = null;
		if (customizerUI != null) {
			curComposite = customizerUI.getCustomizerComposite(parent);
			curComposite.setData(NAME_KEY, "customizerComposite");
			setControl(curComposite);
			setHelpContextId(ViewWizardManager.CUSTOMIZER_PAGE);
		}
		getWizard().getContainer().updateButtons();
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		this.parent = parent;
		if (customizerUI != null)
			curComposite = customizerUI.getCustomizerComposite(parent);
		else
			curComposite = new Composite(parent, SWT.DEFAULT);
		setControl(curComposite);
		setHelpContextId(ViewWizardManager.CUSTOMIZER_PAGE);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.wizard.WizardPageBase#leavingPage(java.lang.Object)
	 */
	protected void leavingPage(Object nextPage) {
		IComponentCustomizerCommandFactory commandFactory = null;
		if (customizerUI != null)
			commandFactory = customizerUI.getCommandFactory();

		getWizardManager().getDataStore().put(ViewWizardManager.CUSTOMIZER_COMMAND_FACTORY_KEY, commandFactory);
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.wizard.ViewWizardPageBase#enteringPage()
     */
    protected void enteringPage() {
    	super.enteringPage();
		Rectangle clientArea = curComposite.getParent().getClientArea();
		curComposite.setSize(curComposite.computeSize(clientArea.width, clientArea.height, true));
    }
    
    public void dispose() {
    	super.dispose();
		if (curComposite != null) {
			curComposite.dispose();
			curComposite = null;
		}
    }
}
