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
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cdt.internal.builder;

import java.io.File;
import java.util.*;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.cdtvariables.ICdtVariablesContributor;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CSourceEntry;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.extension.CBuildData;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.core.settings.model.extension.CFileData;
import org.eclipse.cdt.core.settings.model.extension.CFolderData;
import org.eclipse.cdt.core.settings.model.extension.CLanguageData;
import org.eclipse.cdt.core.settings.model.extension.CResourceData;
import org.eclipse.cdt.core.settings.model.extension.CTargetPlatformData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

/**
 * Class used to supply the new CDT 4.0 project model with the data it
 * needs for each build configuration.
 *
 */
public class BuildConfigurationData extends CConfigurationData {
	
	private ICarbideBuildConfiguration carbideBuildConfig;
	private CarbideRootFolderData rootFolderData;
	private CarbideTargetPlatformData targetPlatformData; 
	private CarbideBuildData buildData;
	private List<ICSourceEntry> sourceEntries;
	private List<ICSourceEntry> extraSourceEntries;
	private List<File> cacheFileSource;
	private long cacheTimestamp;
	private boolean forceRebuildCache;

	private final String CONFIG_DATA_CACHE = "configDataCache"; //$NON-NLS-1$
	private final String SOURCES_CACHE = "sourcesCache"; //$NON-NLS-1$
	private final String TIMESTAMP_CACHE = "timestampCache"; //$NON-NLS-1$
	private final String FILES_CACHE = "filesCache"; //$NON-NLS-1$
	private final String ENTRY_DELIMTER = ";"; //$NON-NLS-1$


	public BuildConfigurationData(ICarbideBuildConfiguration config){
		carbideBuildConfig = config;
		rootFolderData = new CarbideRootFolderData(carbideBuildConfig);
		targetPlatformData = new CarbideTargetPlatformData(carbideBuildConfig);
		buildData = new CarbideBuildData(carbideBuildConfig);
	}
	
	public ICarbideBuildConfiguration getConfiguration() {
		return carbideBuildConfig;
	}

	public CFileData createFileData(IPath path, CFileData base) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Method not supported", null)); //$NON-NLS-1$
	}
	
	public CFileData createFileData(IPath path, CFolderData base, CLanguageData baseLangData) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Method not supported", null)); //$NON-NLS-1$
	}

	public CFolderData createFolderData(IPath path, CFolderData base) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Method not supported", null)); //$NON-NLS-1$
	}

	public String getDescription() {
		if (carbideBuildConfig instanceof CarbideBuildConfiguration) {
			if (((CarbideBuildConfiguration)carbideBuildConfig).valid() == false) {
				return CarbideBuildConfiguration.badSdkString();
			}
		}
		// our config name says it all
		return null;
	}

	public CResourceData[] getResourceDatas() {
		// we don't support file or folder specific settings.
		return new CResourceData[0];
	}

	public CFolderData getRootFolderData() {
		return rootFolderData;
	}

	public void removeResourceData(CResourceData data) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, 0, "Method not supported", null)); //$NON-NLS-1$
	}

	public void setDescription(String description) {
	}

	@Override
	public String getId() {
		return carbideBuildConfig.getDisplayString();
	}

	@Override
	public String getName() {
		return carbideBuildConfig.getDisplayString();
	}

	@Override
	public boolean isValid() {
		if (carbideBuildConfig != null) {
			return ((CarbideBuildConfiguration)carbideBuildConfig).valid();
		}
		return false;
	}

	public void setName(String name) {
	}

	public CTargetPlatformData getTargetPlatformData() {
		return targetPlatformData;
	}

	public ICSourceEntry[] getSourceEntries() {

		// we want to synchronize access to this, but keep the persistence of the
		// cache out of the synchronized block.  otherwise we could have a deadlock
		// as the persistence of the cache is done by saving the project description,
		// which may call back into here.
		boolean cacheBuilt = false;

		synchronized (this) {
			// see if we need to build (or rebuild) the cache
			if (sourceEntries == null || cacheFileSource == null) {
				// try to load persisted cache
				loadCache();
				
				if (sourceEntries == null || cacheFileSource == null) {
					// no persisted cache, so go ahead and create it
					buildCache();
					cacheBuilt = true;
				}
			} else if (forceRebuildCache) {
				buildCache();
				forceRebuildCache = false;
				cacheBuilt = true;
			} else {
				// rebuild if any of the files have changed since we built the cache last
				for (File file : cacheFileSource) {
					if (!file.exists() || (file.lastModified() > cacheTimestamp)) {
						buildCache();
						cacheBuilt = true;
						break;
					}
				}
			}
			
			// add any extra source entries not already accounted for
			if (extraSourceEntries != null) {
				for (ICSourceEntry sourceEntry : extraSourceEntries) {
					if (!sourceEntries.contains(sourceEntry)) {
						sourceEntries.add(sourceEntry);
					}
				}
			}
		}
		
		if (cacheBuilt) {
			persistCache();
		}
		
		// remove any source paths that don't currently exist.  this will force CDT to issue
		// a change event later if/when the resource does exist. that in turn will refresh
		// the CModel, making the source folders show up correctly.  see bug #4401 for more info.
		List<ICSourceEntry> liveSourceEntries = new ArrayList<ICSourceEntry>(sourceEntries);
		for (Iterator<ICSourceEntry> iter = liveSourceEntries.iterator(); iter.hasNext();) {
			IPath path = iter.next().getFullPath();
			if (path != null) {
				if (ResourcesPlugin.getWorkspace().getRoot().findMember(path) == null) {
					iter.remove();
				}
			}
		}

		return liveSourceEntries.toArray(new ICSourceEntry[liveSourceEntries.size()]);
	}

	private void buildCache() {
		sourceEntries = new ArrayList<ICSourceEntry>(0);

		// source entries are the decorated folder icons with the blue "c".
		ICarbideProjectInfo cpi = carbideBuildConfig.getCarbideProject();
		if (cpi == null) {
			return;
		}
		
		List<IPath> sourcePaths = EpocEngineHelper.getPreferredSourceRootsForProject(cpi);
		for (IPath srcPath : sourcePaths) {
			// if the project root is a source path then that's the only one we
			// want to set because anything else would be under the project root
			// and cause nested source paths.
			if (srcPath.segmentCount() == 1) {
				sourceEntries.clear();
				break;
			}
			sourceEntries.add(new CSourceEntry(srcPath, null, ICSettingEntry.VALUE_WORKSPACE_PATH));
		}

		// sort the list alphabetically
		Collections.sort(sourceEntries, new Comparator<ICSourceEntry>() {

			public int compare(ICSourceEntry o1, ICSourceEntry o2) {
				return o1.getName().compareTo(o2.getName());
			}
    		
    	});

		// get the list of files the engine references when parsing the bld.inf and mmp files
		Set<IPath> pathList = new HashSet<IPath>();
		EpocEngineHelper.addIncludedFilesFromBldInf(cpi, carbideBuildConfig, cpi.getAbsoluteBldInfPath(), pathList);

		// get the list of all mmp files selected for the build configuration
		// a null buildComponents list means all MMPs are included - so leave it null when indexing all files
		List<String> buildComponents = null;
		if (!EpocEngineHelper.getIndexAllPreference())
			buildComponents = carbideBuildConfig.getCarbideProject().isBuildingFromInf() ? null : carbideBuildConfig.getCarbideProject().getInfBuildComponents();

		for (IPath mmpPath : EpocEngineHelper.getMMPFilesForBuildConfiguration(carbideBuildConfig)) {
			if (buildComponents == null || TextUtils.listContainsIgnoreCase(buildComponents, mmpPath.lastSegment()))
				EpocEngineHelper.addIncludedFilesFromMMP(cpi, carbideBuildConfig, mmpPath, pathList);
		}

		cacheFileSource = new ArrayList<File>();
		for (IPath path : pathList) {
			cacheFileSource.add(path.toFile());
		}
		
		// save the time so we can test later if any of the bld.inf or mmp files have changed since we created
		// the cache.
		cacheTimestamp = System.currentTimeMillis();
	}
	
	private void persistCache() {
		// persist the cache between IDE launches.
		try {
			final ICarbideProjectInfo cpi = carbideBuildConfig.getCarbideProject();
			if (cpi == null) {
				return;
			}
			
			ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(cpi.getProject());
			if (projDes != null) {
				ICConfigurationDescription configDes = projDes.getConfigurationById(carbideBuildConfig.getDisplayString());
				if (configDes != null) {
					String sourcesCacheValue = "";
					for (ICSourceEntry src : sourceEntries) {
						sourcesCacheValue += src.getFullPath() + ENTRY_DELIMTER;
					}
					ICStorageElement storage = configDes.getStorage(CONFIG_DATA_CACHE, true);
					storage.setAttribute(SOURCES_CACHE, sourcesCacheValue);
					storage.setAttribute(TIMESTAMP_CACHE, Long.toString(cacheTimestamp));

					String filesCacheValue = "";
					for (File file : cacheFileSource) {
						filesCacheValue += file.getAbsolutePath() + ENTRY_DELIMTER;
					}
					storage.setAttribute(FILES_CACHE, filesCacheValue);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		}
	}

	private void loadCache() {
		CarbideProjectInfo cpi = (CarbideProjectInfo)carbideBuildConfig.getCarbideProject();
		IProject project = cpi.getProject();

		// try to load it from persisted data.  if it's there, we still need to check if that cache is stale or not.
		try {
			ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(project);
			if (projDes != null) {
				ICConfigurationDescription configDes = projDes.getConfigurationById(carbideBuildConfig.getDisplayString());
				if (configDes != null) {
					ICStorageElement storage = configDes.getStorage(CONFIG_DATA_CACHE, false);
					if (storage != null) {
						String sourceCacheValue = storage.getAttribute(SOURCES_CACHE);
						if (sourceCacheValue != null) {
							sourceEntries = new ArrayList<ICSourceEntry>(0);
							for (String src : sourceCacheValue.split(ENTRY_DELIMTER)) {
								sourceEntries.add(new CSourceEntry(src, null, ICSettingEntry.VALUE_WORKSPACE_PATH));
							}
						}

						String timestampString = storage.getAttribute(TIMESTAMP_CACHE);
						if (timestampString != null) {
							cacheTimestamp = Long.parseLong(timestampString);
						}
						
						String filesCacheValue = storage.getAttribute(FILES_CACHE);
						if (filesCacheValue != null) {
							cacheFileSource = new ArrayList<File>(0);
							for (String file : filesCacheValue.split(ENTRY_DELIMTER)) {
								cacheFileSource.add(new File(file));
							}
						}
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		}
	}

	public void setSourceEntries(ICSourceEntry[] entries) {
		extraSourceEntries = new ArrayList<ICSourceEntry>(Arrays.asList(entries));
	}

	public CBuildData getBuildData() {
		return buildData;
	}

	public ICdtVariablesContributor getBuildVariablesContributor() {
		// we don't use this
		return null;
	}
	
	public void forceRebuildCache() {
		forceRebuildCache = true;
	}

}
