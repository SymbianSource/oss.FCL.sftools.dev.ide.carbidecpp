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
package com.nokia.cdt.internal.debug.launch.wizard;

import com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2.IListener;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import java.text.MessageFormat;

public class TRKConnectionWizardPage extends WizardPage {
    
	private final ISummaryTextItemContainer summaryTextItemContainer;
	private IClientServiceSiteUI2 clientSiteUI;
	private String connection;
	
    
    public TRKConnectionWizardPage(ISummaryTextItemContainer summaryTextItemContainer) {
        super(Messages.getString("TRKConnectionWizardPage.0")); //$NON-NLS-1$
		Check.checkArg(summaryTextItemContainer);
		this.summaryTextItemContainer = summaryTextItemContainer;
        setPageComplete(false);
        setTitle(Messages.getString("TRKConnectionWizardPage.0")); //$NON-NLS-1$
        setDescription(Messages.getString("TRKConnectionWizardPage.1")); //$NON-NLS-1$
    }
    
	/*
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        composite.setLayout(layout);

		clientSiteUI = RemoteConnectionsActivator.getConnectionsManager().getClientSiteUI2(LaunchPlugin.getTRKService());
		clientSiteUI.createComposite(composite);
		clientSiteUI.addListener(new IListener() {
			public void connectionSelected() {
				validatePage();
			}
		});

		setControl(composite);
        Dialog.applyDialogFont(parent);
        validatePage();
    }
    
    void updateConfiguration(ILaunchConfigurationWorkingCopy config) {
		if (connection != null) {
			config.setAttribute(RemoteConnectionsTRKHelper.CONNECTION_ATTRIBUTE, connection);
		}
    }

    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if (!visible && connection != null) {
    		summaryTextItemContainer.putSummaryTextItem("Connection", //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", Messages.getString("TRKConnectionWizardPage.ConnectionSummaryLabel"), //$NON-NLS-1$ //$NON-NLS-2$
    						clientSiteUI.getConnectionDisplayName(connection)));
    	}
    }
    
    protected void validatePage() {
    	setErrorMessage(null);
    	setPageComplete(true);
		connection = clientSiteUI.getSelectedConnection();
		if (connection == null) {
			setErrorMessage(Messages.getString("TRKConnectionWizardPage.NoConnectionError")); //$NON-NLS-1$
			setPageComplete(false);
		}
    }
    
    @Override
    public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.WIZARD_TRK_CONNECTION_PAGE);
    }
}