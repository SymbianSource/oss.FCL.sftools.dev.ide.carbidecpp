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
*/
package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.update.configuration.IConfiguredSite;
import org.eclipse.update.core.IFeature;
import org.eclipse.update.core.IFeatureReference;
import org.eclipse.update.core.ISite;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.core.VersionedIdentifier;
import org.eclipse.update.operations.OperationsManager;

/**
 * Installer to install features and plug-ins available at a supplied URL or directory.
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
public class DynamicFeatureInstaller {
	
	private URL featureURL;
	private IProgressMonitor progressMonitor;
	
	private static final String IGNORED_ID_S60_31_BUILD = "com.nokia.sdk.S60_31.build"; // deprecated, don't install
	
	/**
	 * Constructs an installer that can install all the features available in the
	 * supplied directory.  
	 * 
	 * @param featureDirectory String representing the directory under which the features and plugins 
	 *                         sub-directories exist.
	 * @param progressMonitor An implementation of IProgressMonitor, usually showing 
	 *                        the installation progress as a Progress Bar to the user.
	 *                        Can be null. 
	 * @throws MalformedURLException when the supplied featureDirectory does not correspond to a File URL
	 * @throws FileNotFoundException when the supplied featureDirectory does not correspond to a proper feature directory
	 */
	public DynamicFeatureInstaller(String featureDirectory, IProgressMonitor progressMonitor) throws MalformedURLException, FileNotFoundException {
		this(new File(featureDirectory), progressMonitor);
	}

	/**
	 * Constructs an installer that can install all the features available in the
	 * supplied directory.  
	 * 
	 * @param featureDirectory File representing the directory under which the features and plugins 
	 *                         sub-directories exist. 
	 * @param progressMonitor An implementation of IProgressMonitor, usually showing 
	 *                        the installation progress as a Progress Bar to the user.
	 *                        Can be null. 
	 * @throws MalformedURLException when the supplied featureDirectory does not correspond to a File URL.
	 * @throws FileNotFoundException when the supplied featureDirectory does not correspond to a proper feature directory
	 */
	public DynamicFeatureInstaller(File featureDirectory, IProgressMonitor progressMonitor) throws MalformedURLException, FileNotFoundException {
		this(featureDirectory.toURL(), progressMonitor);
		// Having a manifest does not require that you actually install anything. So errors should not be thrown.
		// You simply should not run the dynamic installer if the SDK has nothing to install.
		if (!featureDirectory.isDirectory()) {
			throw new FileNotFoundException(featureDirectory + " is not a directory.");
		}
		if (!(new File(featureDirectory, "features").isDirectory())) {
			throw new FileNotFoundException(featureDirectory + " does not contain \"features\" directory.");
		}
		if (!(new File(featureDirectory, "plugins").isDirectory())) {
			throw new FileNotFoundException(featureDirectory + " does not contain \"plugins\" directory.");
		}
	}

	/**
	 * Constructs an installer that can install all the features available at the
	 * supplied URL.  
	 * 
	 * @param featureDirectoryURL URL representing the directory under which the features and plugins 
	 *                            sub-directories exist. 
	 * @param progressMonitor An implementation of IProgressMonitor, usually showing 
	 *                        the installation progress as a Progress Bar to the user.
	 *                        Can be null. 
	 */
	private DynamicFeatureInstaller(URL featureDirectoryURL, IProgressMonitor progressMonitor) {
		this.featureURL = featureDirectoryURL;
		this.progressMonitor = progressMonitor;
	}

	/**
	 * Installs the features and plug-ins available to this Installer. This method performs the complete 
	 * installation operation. It checks for every feature found if it is already installed and if so, whether
	 * the installed version is atleast as new as the found feature. The found feature is installed only if this
	 * test fails.
	 * 
	 * @throws InstallationFailureException on installation failure due to any reason.
	 * @return true if installation results in a restart requirement, false otherwise.
	 */
	public boolean install() throws InstallationFailureException {
		try {
			OperationsManager.setInProgress(true);
			ISite remoteSite = SiteManager.getSite(featureURL, progressMonitor);
			IConfiguredSite[] configuredSites = SiteManager.getLocalSite().getCurrentConfiguration().getConfiguredSites();
			List<IFeatureReference> installedFeatureRefs = new ArrayList<IFeatureReference>();
			IConfiguredSite localSite = null;
			for (IConfiguredSite site : configuredSites) {
				installedFeatureRefs.addAll(Arrays.asList(site.getConfiguredFeatures()));
				if (localSite == null && site.isUpdatable()) {
					// TODO: If there are multiple sites should the user be presented a list to choose from?
					localSite = site;
				}
			}
			if (localSite == null)
				return false;
			
			boolean modified = false;
			for (IFeatureReference featureReference : remoteSite.getFeatureReferences()) {
				if (!isLatestInstalled(featureReference, installedFeatureRefs)) {
					//TODO need to verify whether to prompt user or not to determine what should be passed as VerificationListener
					IFeature feature = featureReference.getFeature(progressMonitor);
					if (!feature.getPrimaryPluginID().contains(IGNORED_ID_S60_31_BUILD)){
						IFeatureReference featureRef = localSite.install(feature, null, progressMonitor);
						localSite.configure(feature);
						if (featureRef != null){
							modified = true;
						}
					}
				}
			}
			if (modified) {
				//boolean restart = SiteManager.getLocalSite().save();
				// Trying to apply changes now thows many exceptions, so we'll just force user to restart.
				//OperationsManager.applyChangesNow();
				
				// Returning true here no matter what. On release builds for Carbide, the SiteManager
				// always returns false when saving for some strange reason, but not on debug builds.
				// This will only return true when we know something has been successfully installed
				// because the installer won't get kicked off if the latest feature file is the same as
				// what it's trying to install.
				return true;
			}
			return false;
		} catch (Throwable t) {
			throw new InstallationFailureException(t);
		} finally {
			OperationsManager.setInProgress(false);
		}
	}

	/**
	 * Checks for every new feature found if it is already installed and if so, whether
	 * the installed version is atleast as new as the found feature.
	 * @param newFeature <code>IFeatureReference</code> reference.
	 * @param installedFeatureList a list object contains <code>IFeatureReference</code> references.
	 * @return true, if the feature is newly installed, false otherwise.
	 */
	private boolean isLatestInstalled(IFeatureReference newFeature, List<IFeatureReference> installedFeatureList) throws CoreException {
		for (IFeatureReference installedFeature : installedFeatureList) {
			VersionedIdentifier oldId = installedFeature.getVersionedIdentifier();
			VersionedIdentifier newId = newFeature.getVersionedIdentifier();
			if (oldId.getIdentifier().equals(newId.getIdentifier()) && oldId.getVersion().isGreaterOrEqualTo(newId.getVersion())) {
				return true; 
			}
		}
		return false;
	}
}
