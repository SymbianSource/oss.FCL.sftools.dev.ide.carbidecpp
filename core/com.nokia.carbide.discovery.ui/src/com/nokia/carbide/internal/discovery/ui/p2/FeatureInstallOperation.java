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
package com.nokia.carbide.internal.discovery.ui.p2;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.InstallOperation;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.RepositoryTracker;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.IQueryable;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.discovery.ui.Messages;

public class FeatureInstallOperation implements IRunnableWithProgress {
	
	private Collection<URI> uris;
	private Collection<FeatureInfo> featureInfos;
	private boolean wantVersions;
	private ProvisioningUI provisioningUI;
	private Collection<IMetadataRepository> repositories;
	private Collection<IInstallableUnit> ius;
	private Collection<URI> urisUsed;

	public FeatureInstallOperation(Collection<URI> uris, Collection<FeatureInfo> featureInfos, boolean wantVersions) {
		this.uris = uris;
		this.featureInfos = featureInfos;
		this.wantVersions = wantVersions;
		provisioningUI = ProvisioningUI.getDefaultUI();
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			doInstall(monitor);
		} catch (OperationCanceledException e) {
			throw new InterruptedException();
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		}
	}

	public void doInstall(IProgressMonitor monitor) throws OperationCanceledException, CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, Messages.FeatureInstallOperation_ConfiguringTaskName, 100);
		getRepositories(subMonitor.newChild(30));
		findInstallableUnits(subMonitor.newChild(40));
		final InstallOperation operation = resolve(subMonitor.newChild(30));
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				IQueryable<IInstallableUnit> additions = operation.getProvisioningPlan().getAdditions();
				IQueryResult<IInstallableUnit> result = additions.query(QueryUtil.createIUGroupQuery(), new NullProgressMonitor());
				provisioningUI.openInstallWizard(result.toSet(), operation, null);
			}
		});
	}

	private void getRepositories(SubMonitor monitor) throws ProvisionException, OperationCanceledException {
		monitor.setWorkRemaining(uris.size());
		repositories = new ArrayList<IMetadataRepository>();
		ProvisioningSession session = provisioningUI.getSession();
		RepositoryTracker repositoryTracker = provisioningUI.getRepositoryTracker();
		IMetadataRepositoryManager manager = 
			(IMetadataRepositoryManager) session.getProvisioningAgent().getService(IMetadataRepositoryManager.SERVICE_NAME);
		for (URI uri : uris) {
			checkIfCanceled(monitor);
			repositoryTracker.addRepository(uri, null, session);
			repositories.add(manager.loadRepository(uri, monitor.newChild(1)));
		}
	}

	private void findInstallableUnits(SubMonitor monitor) throws ProvisionException {
		monitor.setWorkRemaining(repositories.size() * featureInfos.size());
		ius = new ArrayList<IInstallableUnit>();
		urisUsed = new HashSet<URI>();
		Set<FeatureInfo> remainingInfos = new HashSet<FeatureInfo>(featureInfos);
		for (IMetadataRepository repository : repositories) {
			checkIfCanceled(monitor);
			IQueryResult<IInstallableUnit> iusInRepository = repository.query(QueryUtil.createIUGroupQuery(), monitor.newChild(1));
			for (FeatureInfo featureInfo : new HashSet<FeatureInfo>(remainingInfos)) {
				String id = featureInfo.getId();
				IQuery<IInstallableUnit> iuQuery = wantVersions ?
					QueryUtil.createIUQuery(id, featureInfo.getVersion()) :
					QueryUtil.createLatestQuery(QueryUtil.createIUQuery(id));
				IQueryResult<IInstallableUnit> result = iusInRepository.query(iuQuery, monitor.newChild(1));
				if (!result.isEmpty()) {
					ius.add(result.iterator().next());
					urisUsed.add(repository.getLocation());
					remainingInfos.remove(featureInfo);
					if (remainingInfos.isEmpty())
						break;
				}
			}
		}

		if (!remainingInfos.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.FeatureInstallOperation_MissingFeaturesError);
			for (FeatureInfo featureInfo : remainingInfos) {
				sb.append(Messages.FeatureInstallOperation_IdLabel);
				sb.append(featureInfo.getId());
				if (wantVersions) {
					sb.append(Messages.FeatureInstallOperation_VersionLabel);
					sb.append(featureInfo.getVersion().toString());
				}
				sb.append("\n"); //$NON-NLS-1$
			}
			throw new ProvisionException(sb.toString());
		}
		monitor.done();
	}
	
	private InstallOperation resolve(SubMonitor monitor) throws CoreException {
		checkIfCanceled(monitor);
		URI[] uris = (URI[]) urisUsed.toArray(new URI[urisUsed.size()]);
		InstallOperation installOperation = provisioningUI.getInstallOperation(ius, uris);
		IStatus operationStatus = installOperation.resolveModal(monitor);
		if (operationStatus.getSeverity() > IStatus.WARNING) {
			throw new CoreException(operationStatus);
		}
		return installOperation;
	}

	private void checkIfCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}
}
