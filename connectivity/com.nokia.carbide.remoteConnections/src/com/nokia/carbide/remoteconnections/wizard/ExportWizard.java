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


package com.nokia.carbide.remoteconnections.wizard;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.internal.registry.Writer;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import java.io.*;
import java.util.List;

/**
 * Wizard for exporting connections to a file
 */
public class ExportWizard extends Wizard implements IExportWizard {

	private ExportPage exportPage;

	public ExportWizard() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		List<IConnection> connections = exportPage.getSelectedConnections();
		File file = exportPage.getFile();
		try {
			OutputStream os = new FileOutputStream(file);
			Writer.writeToXML(os, connections);
		} catch (Exception e) {
			Plugin p = RemoteConnectionsActivator.getDefault();
			Logging.log(p, Logging.newSimpleStatus(p, IStatus.ERROR, 
					Messages.getString("ExportWizard.WriteFileError"), e)); //$NON-NLS-1$
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("ExportWizard.ExportTitle")); //$NON-NLS-1$
		exportPage = new ExportPage();
		addPage(exportPage);
	}

}
