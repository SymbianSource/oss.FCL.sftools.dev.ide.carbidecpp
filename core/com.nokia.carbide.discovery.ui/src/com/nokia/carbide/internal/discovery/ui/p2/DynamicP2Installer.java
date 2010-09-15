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
*/

package com.nokia.carbide.internal.discovery.ui.p2;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.equinox.internal.provisional.p2.director.IDirector;
import org.eclipse.equinox.internal.provisional.p2.director.ProfileChangeRequest;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;

/**
 * Installer to install features from a p2 repository at a supplied URL or directory.
 * 
 * The supplied directory should have as sub-directories features and plugins. These 
 * sub-directories in turn can contain any number of features and their corresponding 
 * plug-ins.
 * 
 * <p/>
 * e.g.
 * <p/>
 * <pre>
 * &lt;Feature Directory&gt;
 *               |
 *               | --- artifacts.jar
 *               | --- content.jar
 *               |
 *               ----&lt;features&gt;
 *                   |
 *                   |----&lt;feature folder name&gt;
 *                        |
 *                        |---features.xml file (contains list of feature jars)
 *               ----&lt;plugins&gt;
 *                        |
 *                        ---jar files;
 * </pre>
 */

@SuppressWarnings("restriction")
public class DynamicP2Installer {
	
	private IPath repositoryLocation;
	private ProvisioningUI provisioningUI;
	private IProvisioningAgent agent;
	private IProfile profile;
	
	/**
	 * Performs the install.
	 * @param monitor An implementation of IProgressMonitor, usually showing the 
	 * installation progress as a Progress Bar to the user.
	 */
	public static IStatus install(String sdkId, File repositoryDirectory, IProgressMonitor monitor) {
		try {
			DynamicP2Installer installer = new DynamicP2Installer(repositoryDirectory);
			installer.doInstall(monitor);
			RemoteConnectionsActivator.getStatusDisplay().displayStatus(
					Activator.makeStatus(IStatus.INFO, "Installed features from " + sdkId, null));
			return Status.OK_STATUS;
		} catch (CoreException e) {
			return e.getStatus();
		} catch (FileNotFoundException e) {
			return Activator.makeErrorStatus(null, e);
		}
	}

	private DynamicP2Installer(File repositoryDirectory) throws FileNotFoundException {
		checkRepository(repositoryDirectory);
		repositoryLocation = new Path(repositoryDirectory.getAbsolutePath());
		provisioningUI = ProvisioningUI.getDefaultUI();
		agent = provisioningUI.getSession().getProvisioningAgent();
		profile = getProfile();
	}
	
	private void checkRepository(File repositoryDirectory) throws FileNotFoundException {
		if (!repositoryDirectory.isDirectory()) {
			throw new FileNotFoundException(repositoryDirectory + " is not a directory.");
		}
		if (!(new File(repositoryDirectory, "features").isDirectory())) {
			throw new FileNotFoundException(repositoryDirectory + " missing \"features\" directory.");
		}
		if (!(new File(repositoryDirectory, "plugins").isDirectory())) {
			throw new FileNotFoundException(repositoryDirectory + " missing \"plugins\" directory.");
		}
		if (!(new File(repositoryDirectory, "artifacts.jar").exists()) && !(new File(repositoryDirectory, "artifacts.xml").exists())) {
			throw new FileNotFoundException(repositoryDirectory + " missing artifacts.jar.");
		}
		if (!(new File(repositoryDirectory, "content.jar").exists()) && !(new File(repositoryDirectory, "content.xml").exists())) {
			throw new FileNotFoundException(repositoryDirectory + " missing content.jar.");
		}
	}

	private void doInstall(IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Installing plugins from " + repositoryLocation.toOSString(), 100);
		URI uri = URIUtil.toURI(repositoryLocation);
		IMetadataRepositoryManager metadataRepoManager = 
			(IMetadataRepositoryManager) agent.getService(IMetadataRepositoryManager.SERVICE_NAME);
		IArtifactRepositoryManager artifactRepoManager = 
			(IArtifactRepositoryManager) agent.getService(IArtifactRepositoryManager.SERVICE_NAME);
		try {
			// add and load repository
			checkIfCanceled(subMonitor);
			metadataRepoManager.addRepository(uri);
			IMetadataRepository metadataRepository = metadataRepoManager.loadRepository(uri, null);
			artifactRepoManager.addRepository(uri);
			artifactRepoManager.loadRepository(uri, null);
			subMonitor.worked(10);
			
			// get IU from repository
			checkIfCanceled(subMonitor);
			IQueryResult<IInstallableUnit> units = metadataRepository.query(QueryUtil.createIUGroupQuery(), subMonitor);
			if (units.isEmpty())
				throw new CoreException(Activator.makeErrorStatus("Could not find installable unit", null));
			subMonitor.worked(10);
			
			// check if installed
			checkIfCanceled(subMonitor);
			IQueryResult<IInstallableUnit> result = profile.query(QueryUtil.createIUQuery(units.iterator().next()), subMonitor);
			if (!result.isEmpty())
				throw new CoreException(Activator.makeStatus(IStatus.CANCEL, null, null)); // already installed
			subMonitor.worked(10);
			
			// do provisioning operation
			checkIfCanceled(subMonitor);
			ProfileChangeRequest request = new ProfileChangeRequest(profile);
			request.addAll(units.toUnmodifiableSet());
			IDirector director = (IDirector) agent.getService(IDirector.SERVICE_NAME);
			IStatus status = director.provision(request, null, subMonitor);
			subMonitor.worked(70);
	
			if (!status.isOK())
				throw new CoreException(status);
		}
		finally {
			metadataRepoManager.removeRepository(uri);
			artifactRepoManager.removeRepository(uri);
			subMonitor.done();
		}
	}

	private IProfile getProfile() {
		IProfileRegistry profileRegistry = (IProfileRegistry) agent.getService(IProfileRegistry.SERVICE_NAME);
		return profileRegistry.getProfile(provisioningUI.getProfileId());
	}

	private void checkIfCanceled(IProgressMonitor monitor) throws CoreException {
		if (monitor.isCanceled()) {
			throw new CoreException(Activator.makeStatus(IStatus.CANCEL, null, null)); // installation canceled
		}
	}

}
