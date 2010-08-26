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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
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
		exportPage = new ExportPage(getInitialFeatureIds());
		addPage(exportPage);
	}

	private Collection<String> getInitialFeatureIds() {
		// make sure it exists if not written already
		File file = P2Utils.getInitialFeaturesFile();
		if (!file.exists()) {
			P2Utils.writeFeaturesToFile(file);
		}
		
		Collection<FeatureInfo> installedFeatures = null;
		try {
			ImportExportData data = Streamer.readFromXML(new FileInputStream(file));
			installedFeatures = data.getFeatureInfos();
		} catch (Exception e) {
			Activator.logError(Messages.ExportWizard_ReadInstalledFeaturesError + file, e);
		}
		
		Collection<String> featureIds = new ArrayList<String>();
		if (installedFeatures != null) {
			for (FeatureInfo featureInfo : installedFeatures) {
				featureIds.add(featureInfo.getId());
			}
		}
		return featureIds;
	}

}
