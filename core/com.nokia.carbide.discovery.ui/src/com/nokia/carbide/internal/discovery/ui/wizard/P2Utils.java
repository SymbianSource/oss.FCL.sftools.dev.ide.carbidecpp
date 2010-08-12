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

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.Version;
import org.eclipse.equinox.p2.operations.InstallOperation;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.RepositoryTracker;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.swt.widgets.Display;

class P2Utils {
	
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

	public static void doInstall(Collection<URI> uris, Collection<FeatureInfo> featureInfos, 
			boolean wantVersions, IProgressMonitor monitor) throws OperationCanceledException, CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Configuring install operation", 100);
		Collection<IMetadataRepository> repositories = 
			getRepositories(uris, wantVersions, subMonitor.newChild(30));
		final Collection<IInstallableUnit> ius = 
			findInstallableUnits(repositories, featureInfos, wantVersions, subMonitor.newChild(40));
		final InstallOperation operation = resolve(ius, (URI[]) uris.toArray(new URI[uris.size()]), subMonitor.newChild(30));
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				ProvisioningUI.getDefaultUI().openInstallWizard(ius, operation, null);
			}
		});
	}

	private static Collection<IMetadataRepository> getRepositories(Collection<URI> uris, 
			boolean wantVersions, SubMonitor monitor) throws ProvisionException, OperationCanceledException {
		monitor.setWorkRemaining(uris.size());
		List<IMetadataRepository> repositories = new ArrayList<IMetadataRepository>();
		ProvisioningUI provisioningUI = ProvisioningUI.getDefaultUI();
		ProvisioningSession session = provisioningUI.getSession();
		RepositoryTracker repositoryTracker = provisioningUI.getRepositoryTracker();
		IMetadataRepositoryManager manager = 
			(IMetadataRepositoryManager) session.getProvisioningAgent().getService(IMetadataRepositoryManager.SERVICE_NAME);
		for (URI uri : uris) {
			checkIfCanceled(monitor);
			repositoryTracker.addRepository(uri, null, session);
			repositories.add(manager.loadRepository(uri, monitor.newChild(1)));
		}

		return repositories;
	}

	private static Collection<IInstallableUnit> findInstallableUnits(Collection<IMetadataRepository> repositories, 
			Collection<FeatureInfo> featureInfos, boolean wantVersions, SubMonitor monitor) throws ProvisionException {
		monitor.setWorkRemaining(repositories.size());
		List<IInstallableUnit> ius = new ArrayList<IInstallableUnit>();
		Set<FeatureInfo> remainingInfos = new HashSet<FeatureInfo>(featureInfos);
		for (IMetadataRepository repository : repositories) {
			checkIfCanceled(monitor);
			IQueryResult<IInstallableUnit> result = repository.query(QueryUtil.createIUGroupQuery(), monitor.newChild(1));
			for (Iterator<IInstallableUnit> iter = result.iterator(); iter.hasNext();) {
				IInstallableUnit iu = iter.next();
				String id = iu.getId();
				Version version = iu.getVersion();
				FeatureInfo featureInfo = findInfo(remainingInfos, id, wantVersions ? version : null);
				if (featureInfo != null) {
					ius.add(iu);
					remainingInfos.remove(featureInfo);
				}
			}
		}
		if (!remainingInfos.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Install failed. Could not find some features: \n");
			for (FeatureInfo featureInfo : remainingInfos) {
				sb.append("id=");
				sb.append(featureInfo.getId());
				if (wantVersions) {
					sb.append("version=");
					sb.append(featureInfo.getVersion().toString());
				}
				sb.append("\n");
			}
			throw new ProvisionException(sb.toString());
		}
		return ius;
	}
	
	private static FeatureInfo findInfo(Collection<FeatureInfo> featureInfos, String id, Version version) {
		for (FeatureInfo featureInfo : featureInfos) {
			if (featureInfo.getId().equals(id) && 
					(version == null || version.toString().equals(featureInfo.getVersion().toString())))
				return featureInfo;
		}
		return null;
	}
	
	private static InstallOperation resolve(Collection<IInstallableUnit> ius, URI[] repositories, 
			SubMonitor monitor) throws CoreException {
		checkIfCanceled(monitor);
		ProvisioningUI provisioningUI = ProvisioningUI.getDefaultUI();
		InstallOperation installOperation = provisioningUI.getInstallOperation(ius, repositories);
		IStatus operationStatus = installOperation.resolveModal(monitor);
		if (operationStatus.getSeverity() > IStatus.WARNING) {
			throw new CoreException(operationStatus);
		}
		return installOperation;
	}

	private static void checkIfCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}
}
