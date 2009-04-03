/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.settings.ui;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.*;

public class OnDeviceConnectionAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		// Ensure the remote connections view is visible
        IWorkbenchPage page = window.getActivePage();
        if (page != null) {
            try {
                page.showView("com.nokia.carbide.remoteconnections.view.ConnectionsView"); //$NON-NLS-1$
            } 
            catch (PartInitException e) {
            	RemoteConnectionsActivator.logError(e);
            }
        }
		
		// Launch the new connection wizard
		SettingsWizard wizard = new SettingsWizard();
		wizard.open(window.getShell());
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
