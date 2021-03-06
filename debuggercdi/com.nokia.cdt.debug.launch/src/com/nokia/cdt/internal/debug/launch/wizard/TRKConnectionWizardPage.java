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
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2.IListener;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
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
	private String connectionId;
	private final String debugServiceId;
	
    
    public TRKConnectionWizardPage(ISummaryTextItemContainer summaryTextItemContainer,
    		String debugServiceId) {
        super(Messages.getString("TRKConnectionWizardPage.0")); //$NON-NLS-1$
		this.debugServiceId = debugServiceId;
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

		clientSiteUI = RemoteConnectionsActivator.getConnectionsManager().getClientSiteUI2(
				RemoteConnectionsActivator.getConnectionTypeProvider().
				findServiceByID(debugServiceId));
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
    
    public void updateConfiguration(ILaunchConfigurationWorkingCopy config) {
		if (connectionId != null) {
			config.setAttribute(RemoteConnectionsTRKHelper.CONNECTION_ATTRIBUTE, connectionId);
		}
    }

    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	IConnection connection = RemoteConnectionsActivator.getConnectionsManager().findConnection(connectionId);
    	if (!visible && connection != null) {
    		summaryTextItemContainer.putSummaryTextItem("Connection", //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", Messages.getString("TRKConnectionWizardPage.ConnectionSummaryLabel"), //$NON-NLS-1$ //$NON-NLS-2$
    						connection.getDisplayName()));
    	}
    }
    
    protected void validatePage() {
    	setErrorMessage(null);
    	setMessage(null);
    	setPageComplete(true);
		IStatus status = clientSiteUI.getSelectionStatus();
		if (!status.isOK()) {
			if (status.getSeverity() == IStatus.ERROR) {
				setErrorMessage(status.getMessage());
				setPageComplete(false);
			} else {
				setMessage(status.getMessage(), 
						status.getSeverity() == IStatus.WARNING ? WARNING : INFORMATION); 
			}
		}
		else {
			connectionId = clientSiteUI.getSelectedConnection();
		}
    }
    
    @Override
    public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.WIZARD_TRK_CONNECTION_PAGE);
    }
}