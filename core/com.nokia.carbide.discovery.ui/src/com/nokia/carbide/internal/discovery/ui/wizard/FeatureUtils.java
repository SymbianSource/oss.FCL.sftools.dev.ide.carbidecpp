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

import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.ui.ProvisioningUI;

class FeatureUtils {
	
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

	public static void writeInfosToStream(Collection<FeatureInfo> featureInfos, OutputStream os) {
		// TODO Auto-generated method stub
		
	}
}
