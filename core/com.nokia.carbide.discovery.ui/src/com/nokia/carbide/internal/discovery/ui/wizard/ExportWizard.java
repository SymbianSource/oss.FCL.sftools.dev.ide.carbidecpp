/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.discovery.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;

/**
 * Wizard for exporting installed features to a file
 */
public class ExportWizard extends Wizard implements IExportWizard {

	private ExportPage exportPage;

	public ExportWizard() {
	}

	public boolean performFinish() {
		Collection<FeatureInfo> featureInfos = exportPage.getFeatureInfos();
		File file = exportPage.getFile();
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			ImportExportData data = new ImportExportData(false, P2Utils.getKnownRepositories(), featureInfos);
			Streamer.writeToXML(os, data);
		} catch (Exception e) {
			MessageDialog.openError(getShell(), Messages.ExportWizard_ErrorTitle, 
					MessageFormat.format(Messages.ExportWizard_WriteFileError, e.getMessage()));
		}
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons\\install_wiz.gif")); //$NON-NLS-1$
		setWindowTitle(Messages.ExportWizard_Title);
		exportPage = new ExportPage(getFilteredFeatureIds());
		addPage(exportPage);
	}

	private Collection<String> getFilteredFeatureIds() {
		Collection<FeatureInfo> installedFeatures = null;
		Location installLocation = Platform.getInstallLocation();
		URL url = installLocation.getURL();
		IPath path = new Path(url.getPath());
		path = path.append("configuration/installedFeatures.xml"); //$NON-NLS-1$
		File file = path.toFile();
		if (!file.exists()) {
			installedFeatures = P2Utils.getInstalledFeatures(new NullProgressMonitor());
			ImportExportData data = new ImportExportData(false, Collections.<URI>emptyList(), installedFeatures);
			try {
				Streamer.writeToXML(new FileOutputStream(file), data);
			} catch (Exception e) {
				Activator.logError("Could not write installed features file: " + file, e);
			}
		}
		else {
			try {
				ImportExportData data = Streamer.readFromXML(new FileInputStream(file));
				installedFeatures = data.getFeatureInfos();
			} catch (Exception e) {
				Activator.logError("Could not read installed features file: " + file, e);
			}
		}
		
		Collection<String> filteredIds = new ArrayList<String>();
		if (installedFeatures != null) {
			for (FeatureInfo featureInfo : installedFeatures) {
				filteredIds.add(featureInfo.getId());
			}
		}
		return filteredIds;
	}

}
