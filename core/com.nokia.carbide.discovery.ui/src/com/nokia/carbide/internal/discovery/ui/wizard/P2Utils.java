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
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.osgi.service.datalocation.Location;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;

public class P2Utils {
	
	public static Collection<URI> getKnownRepositories() {
		ProvisioningUI provisioningUI = ProvisioningUI.getDefaultUI();
		ProvisioningSession session = provisioningUI.getSession();
		URI[] uris = provisioningUI.getRepositoryTracker().getKnownRepositories(session);
		return Arrays.asList(uris);
	}

	public static Collection<FeatureInfo> getInstalledFeatures(IProgressMonitor monitor) {
		Set<FeatureInfo> infos = new HashSet<FeatureInfo>();
		ProvisioningUI provisioningUI = ProvisioningUI.getDefaultUI();
		ProvisioningSession session = provisioningUI.getSession();
		IProvisioningAgent agent = session.getProvisioningAgent();
		IProfileRegistry profileRegistry = (IProfileRegistry) agent.getService(IProfileRegistry.SERVICE_NAME);
		IProfile profile = null;
		if (profileRegistry != null) {
			profile = profileRegistry.getProfile(provisioningUI.getProfileId());
			if (profile != null) {
				IQueryResult<IInstallableUnit> result = 
					profile.query(QueryUtil.createIUGroupQuery(), monitor);
				for (Iterator<IInstallableUnit> iterator = result.iterator(); iterator.hasNext();) {
					infos.add(new FeatureInfo((IInstallableUnit) iterator.next()));
				}
			}
		}
		return infos;
	}
	
	public static void writeFeaturesToFile(File file) {
		if (!file.exists()) {
			Collection<FeatureInfo> initialFeatures = P2Utils.getInstalledFeatures(new NullProgressMonitor());
			ImportExportData data = new ImportExportData(false, Collections.<URI>emptyList(), initialFeatures);
			try {
				Streamer.writeToXML(new FileOutputStream(file), data);
			} catch (Exception e) {
				Activator.logError(MessageFormat.format(
						Messages.P2Utils_WriteInitialFeaturesFileError, file), e);
			}
		}
	}

	public static File getInitialFeaturesFile() {
		Location installLocation = Platform.getInstallLocation();
		URL url = installLocation.getURL();
		IPath path = new Path(url.getPath());
		path = path.append("configuration/initialFeatures.xml"); //$NON-NLS-1$
		File file = path.toFile();
		return file;
	}
}
