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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CIncludePathEntry;
import org.eclipse.cdt.core.settings.model.CMacroEntry;
import org.eclipse.cdt.core.settings.model.CMacroFileEntry;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICLanguageSettingEntry;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.extension.CLanguageData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.core.runtime.content.IContentTypeSettings;
import org.eclipse.core.runtime.jobs.Job;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.IBSFPlatform;
import com.nokia.carbide.cpp.sdk.core.ISBVPlatform;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
/**
 * Part of the new CDT 4.0 project model requirements.  All this class
 * really does is provide path entries for this build configuration to CDT.
 * 
 */
public class CarbideLanguageData extends CLanguageData {

	private ICarbideBuildConfiguration carbideBuildConfig;
	private List<IContentType> fContentTypes = null;
	private static final String LANGUAGE_DATA_ID = "CarbideLanguageData"; //$NON-NLS-1$
	private static final String DOT = "."; //$NON-NLS-1$
	
	private List<ICLanguageSettingEntry> includeEntries;
	private List<ICLanguageSettingEntry> macroEntries;
	private List<File> cacheFileSource;
	private boolean lastUseMMPMacrosValue;
	private boolean forceRebuildCache = false;
	private long cacheTimestamp;
	
	private final String CONFIG_DATA_CACHE = "configDataCache"; //$NON-NLS-1$
	private final String INCLUDES_CACHE = "includesCache"; //$NON-NLS-1$
	private final String MACROS_CACHE = "macrosCache"; //$NON-NLS-1$
	private final String USE_MMP_MACROS_CACHE = "useMmpMacrosCache"; //$NON-NLS-1$
	private final String TIMESTAMP_CACHE = "timestampCache"; //$NON-NLS-1$
	private final String FILES_CACHE = "filesCache"; //$NON-NLS-1$

	private final String ENTRY_DELIMTER = ";"; //$NON-NLS-1$
	private final String LOCAL_MARKER = "[LOCAL]"; //$NON-NLS-1$

	public CarbideLanguageData(ICarbideBuildConfiguration config) {
		carbideBuildConfig = config;
	}
	
	@Override
	public ICLanguageSettingEntry[] getEntries(int kind) {

		// we want to synchronize access to this, but keep the persistence of the
		// cache out of the synchronized block.  otherwise we could have a deadlock
		// as the persistence of the cache is done by saving the project description,
		// which may call back into here.
		boolean cacheBuilt = false;

		synchronized (this) {
			if (kind == ICLanguageSettingEntry.INCLUDE_PATH || kind == ICLanguageSettingEntry.MACRO) {
				if (forceRebuildCache) {
					buildCache();
					forceRebuildCache = false;
					cacheBuilt = true;
				// see if we have the data cached or not.  if so, make sure the cache isn't stale
				} else if (includeEntries == null || macroEntries == null || cacheFileSource == null) {
					// try to load persisted cache
					loadCache();
					
					if (includeEntries == null || macroEntries == null || cacheFileSource == null) {
						// no persisted cache, so go ahead and create it
						buildCache();
						cacheBuilt = true;
					}
				}

				CarbideProjectInfo cpi = (CarbideProjectInfo)carbideBuildConfig.getCarbideProject();

				// see if the use mmp macro option has changed since the cache was built
				if (kind == ICLanguageSettingEntry.MACRO && lastUseMMPMacrosValue != cpi.shouldUseMMPMacros()) {
					buildCache();
					cacheBuilt = true;
				} else {
					// see if any of the files have changed since the cache was built
					for (File file : cacheFileSource) {
						if (!file.exists() || (file.lastModified() > cacheTimestamp)) {
							buildCache();
							cacheBuilt = true;
							break;
						}
					}
				}
			}
		}
		
		if (cacheBuilt) {
			persistCache();
		}

		switch(kind) {
		case ICLanguageSettingEntry.INCLUDE_PATH: {
			return includeEntries.toArray(new ICLanguageSettingEntry[includeEntries.size()]);
		}

		case ICLanguageSettingEntry.MACRO: {
			// we cache all but the SDK and compiler macros.  those are cached separately in SymbianBuildContext
			Set<ICLanguageSettingEntry> macros = new HashSet<ICLanguageSettingEntry>();
			macros.addAll(macroEntries);
			
			for (IDefine define : carbideBuildConfig.getBuildContext().getVariantHRHDefines()) {
				macros.add(new CMacroEntry(define.getNameAndArguments(), define.getExpansion(), 0));
			}

			for (IDefine define : carbideBuildConfig.getBuildContext().getCompilerPreincludeDefines()) {
				macros.add(new CMacroEntry(define.getNameAndArguments(), define.getExpansion(), 0));
			}
			
			return macros.toArray(new ICLanguageSettingEntry[macros.size()]);
		}
		
		case ICLanguageSettingEntry.MACRO_FILE: {
			CarbideProjectInfo cpi = (CarbideProjectInfo)carbideBuildConfig.getCarbideProject();
			String macrosFile = cpi.getMacrosFile();
			if (macrosFile != null && macrosFile.length() > 0) {
				ICLanguageSettingEntry[] macroFiles = new ICLanguageSettingEntry[1];
				macroFiles[0] = new CMacroFileEntry(new Path(macrosFile), 0);
				return macroFiles;
			}
			break;
		}

		case ICLanguageSettingEntry.OUTPUT_PATH: {
			break;
		}
		
		case ICLanguageSettingEntry.SOURCE_PATH: {
			break;
		}
		
		}

		return new ICLanguageSettingEntry[0];
	}
	
	private void buildCache() {
		
		// cache the includes
		includeEntries = new ArrayList<ICLanguageSettingEntry>(0);
		
		CarbideProjectInfo cpi = (CarbideProjectInfo)carbideBuildConfig.getCarbideProject();
		IProject project = cpi.getProject();
		ISymbianSDK sdk = carbideBuildConfig.getSDK();
		ISymbianBuildContext context = carbideBuildConfig.getBuildContext();

		// add platform includes first
		IBSFPlatform platform = null;
		ISBVPlatform sbvPlat = null;
		if (context instanceof ISBSv1BuildContext) {
			// SBSv1 only
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
			platform = sbsv1BuildInfo.getBSFCatalog().findPlatform(carbideBuildConfig.getPlatformString());
			sbvPlat = sbsv1BuildInfo.getSBVCatalog().findPlatform(carbideBuildConfig.getPlatformString());
		} 
		if (platform != null) {
			IPath[] systemIncludePaths = platform.getSystemIncludePaths();
			for (IPath path : systemIncludePaths) {
				includeEntries.add(new CIncludePathEntry(path, 0));
			}
		}
		else if (sbvPlat != null){
			
			LinkedHashMap<IPath, String> platPaths = sbvPlat.getBuildIncludePaths();
			Set<IPath> set = platPaths.keySet();
			for (IPath path : set) {
				String pathType = platPaths.get(path);
				if (pathType.equalsIgnoreCase(ISBVView.INCLUDE_FLAG_PREPEND) || pathType.equalsIgnoreCase(ISBVView.INCLUDE_FLAG_SET)){
					includeEntries.add(new CIncludePathEntry(path, 0));
				}
			}
		}
		
		// get the user and system includes
		List<File> userIncludes = new ArrayList<File>();
		List<File> systemIncludes = new ArrayList<File>();
		EpocEngineHelper.getProjectIncludePaths(cpi, carbideBuildConfig, userIncludes, systemIncludes);
		IPath projectPath = project.getLocation();
		
		for (File inc : userIncludes) {
			// convert the absolute path to project relative if possible
			IPath incPath = new Path(inc.toString());
			IPath projRelIncPath = FileUtils.removePrefixFromPath(projectPath, incPath);
			if (projRelIncPath != null) {
				includeEntries.add(new CIncludePathEntry(projectPath.append(projRelIncPath), ICSettingEntry.LOCAL));
			} else {
				includeEntries.add(new CIncludePathEntry(incPath, ICSettingEntry.LOCAL));
			}
		}
		
		if (context instanceof ISBSv2BuildContext) {
			// SBSv2 only system includes
			if (((ISBSv2BuildContext)context).getSystemIncludes() != null){
			
				for (IPath incPath : ((ISBSv2BuildContext)context).getSystemIncludes()) {
					IPath projRelIncPath = FileUtils.removePrefixFromPath(projectPath, incPath);
					if (projRelIncPath != null) {
						includeEntries.add(new CIncludePathEntry(projectPath.append(projRelIncPath), 0));
					} else {
						includeEntries.add(new CIncludePathEntry(incPath, 0));
					}
	
					// remove duplicate system includes
					File inc = incPath.toFile();
					if (systemIncludes.contains(inc)) {
						systemIncludes.remove(inc);
					}
				}
			}
		}
		
		for (File inc : systemIncludes) {
			// convert the absolute path to project relative if possible
			IPath incPath = new Path(inc.toString());
			IPath projRelIncPath = FileUtils.removePrefixFromPath(projectPath, incPath);
			if (projRelIncPath != null) {
				includeEntries.add(new CIncludePathEntry(projectPath.append(projRelIncPath), 0));
			} else {
				includeEntries.add(new CIncludePathEntry(incPath, 0));
			}
		}
		
		// add OEM dir
		File oemDir = null;
		if (sdk.getIncludePath() != null){
			oemDir = sdk.getIncludePath().append("oem").toFile();
		}
		if (oemDir != null && oemDir.exists()) {
			includeEntries.add(new CIncludePathEntry(new Path(oemDir.getAbsolutePath()), 0));
		}
		
		// cache the macros
		macroEntries = new ArrayList<ICLanguageSettingEntry>(0);

		lastUseMMPMacrosValue = cpi.shouldUseMMPMacros();
		
		// get the list of all mmp files selected for the build configuration
		// a null buildComponents list means all MMPs are included - so leave it null when indexing all files
		List<String> buildComponents = null;
		if (!EpocEngineHelper.getIndexAllPreference())
			buildComponents = carbideBuildConfig.getCarbideProject().isBuildingFromInf() ? null : carbideBuildConfig.getCarbideProject().getInfBuildComponents();
		
		List<IPath> mmpFiles = null;
		List<IDefine> projectDefines = new ArrayList<IDefine>();
		if (buildComponents != null){
			// Get the list of mmp files we are building so we only get those macros
			List<IPath> tmpMMPFiles = EpocEngineHelper.getMMPFilesForBuildConfiguration(carbideBuildConfig);
			mmpFiles = new ArrayList<IPath>();
			for (IPath mmp : tmpMMPFiles){
				if (TextUtils.listContainsIgnoreCase(buildComponents, mmp.lastSegment())){
					mmpFiles.add(mmp);
				}
			}
		} 
		
		projectDefines = EpocEngineHelper.getGlobalDefinesForConfiguration(carbideBuildConfig, mmpFiles);
		
		// now create the path entries for the macros
		for (IDefine define : projectDefines) {
			macroEntries.add(new CMacroEntry(define.getNameAndArguments(), define.getExpansion(), 0));
		}
		
		// get the list of files the engine references when parsing the bld.inf and mmp files
		Set<IPath> pathList = new HashSet<IPath>();
		EpocEngineHelper.addIncludedFilesFromBldInf(cpi, carbideBuildConfig, cpi.getAbsoluteBldInfPath(), pathList);

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
			ICarbideProjectInfo carbideProject = carbideBuildConfig.getCarbideProject();
			if (carbideProject == null)
				return;
			final IProject project = carbideProject.getProject();
			if (project == null)
				return;
			ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(project);
			if (projDes != null) {
				ICConfigurationDescription configDes = projDes.getConfigurationById(carbideBuildConfig.getBuildContext().getConfigurationID());
				if (configDes != null) {
					String includesCacheValue = "";
					for (ICLanguageSettingEntry inc : includeEntries) {
						String incString = inc.getValue();
						if ((inc.getFlags() & ICSettingEntry.LOCAL) != 0) {
							incString += LOCAL_MARKER;
						}
						includesCacheValue += incString + ENTRY_DELIMTER;
					}
					ICStorageElement storage = configDes.getStorage(CONFIG_DATA_CACHE, true);
					storage.setAttribute(INCLUDES_CACHE, includesCacheValue);
					
					String macrosCacheValue = "";
					for (ICLanguageSettingEntry macro : macroEntries) {
						String macroString = macro.getName();
						String value = macro.getValue();
						if (value != null && value.length() > 0) {
							macroString = macroString + "=" + value;
						}
						macrosCacheValue += macroString + ENTRY_DELIMTER;
					}
					storage.setAttribute(MACROS_CACHE, macrosCacheValue);
					storage.setAttribute(USE_MMP_MACROS_CACHE, lastUseMMPMacrosValue ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
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
				ICConfigurationDescription configDes = projDes.getConfigurationById(carbideBuildConfig.getBuildContext().getConfigurationID());
				if (configDes != null) {
					ICStorageElement storage = configDes.getStorage(CONFIG_DATA_CACHE, false);
					if (storage != null) {
						String includesCacheValue = storage.getAttribute(INCLUDES_CACHE);
						if (includesCacheValue != null) {
							includeEntries = new ArrayList<ICLanguageSettingEntry>(0);
							for (String inc : includesCacheValue.split(ENTRY_DELIMTER)) {
								if (inc.endsWith(LOCAL_MARKER)) {
									includeEntries.add(new CIncludePathEntry(inc.substring(0, inc.length() - LOCAL_MARKER.length()), ICSettingEntry.LOCAL));
								} else {
									includeEntries.add(new CIncludePathEntry(inc, 0));
								}
							}
						}

						String macrosCacheValue = storage.getAttribute(MACROS_CACHE);
						if (macrosCacheValue != null) {
							macroEntries = new ArrayList<ICLanguageSettingEntry>(0);
							for (String macro : macrosCacheValue.split(ENTRY_DELIMTER)) {
								String[] result = macro.split("="); //$NON-NLS-1$
								if (result.length == 2) {
									macroEntries.add(new CMacroEntry(result[0], result[1], 0));
								} else {
									macroEntries.add(new CMacroEntry(macro, "", 0)); //$NON-NLS-1$
								}
							}
						}

						String mmpMacrosString = storage.getAttribute(USE_MMP_MACROS_CACHE);
						if (mmpMacrosString != null) {
							lastUseMMPMacrosValue = (mmpMacrosString.compareToIgnoreCase("true") == 0); //$NON-NLS-1$
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

	@Override
	public String getLanguageId() {
		return null;
	}

	@Override
	public String[] getSourceContentTypeIds() {
		return CoreModel.getRegistedContentTypeIds();
	}

	@Override
	public String[] getSourceExtensions() {
		Set<String> exts = new HashSet<String>();
		
		for (IContentType ctype : getRegistedContentTypes()) {
			try {
				IProject project = carbideBuildConfig.getCarbideProject().getProject();
				IContentTypeSettings setting = ctype.getSettings(CCorePlugin.usesProjectSpecificContentTypes(project) ? new ProjectScope(project) : null);
				for (String spec : setting.getFileSpecs(IContentType.FILE_EXTENSION_SPEC)) {
					exts.add(spec);
				}
			} catch (CoreException e) {
				e.printStackTrace();
				CarbideBuilderPlugin.log(e);
			}
		}

		return exts.toArray(new String[exts.size()]);
	}

	private List<IContentType> getRegistedContentTypes() {
		if (fContentTypes == null) {
			fContentTypes = new ArrayList<IContentType>();

			IContentTypeManager manager = Platform.getContentTypeManager();
			for (String id : CoreModel.getRegistedContentTypeIds()) {
				fContentTypes.add(manager.getContentType(id));
			}
		}
		return fContentTypes;
	}
	
	public void forceRebuildCache() {
		// only force rebuild if there was something loaded already -- 
		// we get here on startup too when first hooking up resource listeners
		if (includeEntries != null && macroEntries != null && cacheFileSource != null) {
			forceRebuildCache = true;
		}
	}

	@Override
	public int getSupportedEntryKinds() {
		return ICLanguageSettingEntry.INCLUDE_PATH |
			ICLanguageSettingEntry.MACRO |
			ICLanguageSettingEntry.OUTPUT_PATH |
			ICLanguageSettingEntry.SOURCE_PATH;
	}

	@Override
	public void setEntries(int kind, ICLanguageSettingEntry[] entries) {
		// not used
	}

	@Override
	public void setLanguageId(String id) {
	}

	@Override
	public void setSourceContentTypeIds(String[] ids) {
	}

	@Override
	public void setSourceExtensions(String[] exts) {
	}

	@Override
	public String getId() {
		// plugin id + sdk id + plat id + target id + type id
		return CarbideBuilderPlugin.PLUGIN_ID + DOT +
			carbideBuildConfig.getSDK().getUniqueId() + DOT +
			carbideBuildConfig.getPlatformString() + DOT +
			carbideBuildConfig.getTargetString() + DOT +
			LANGUAGE_DATA_ID;
	}

	@Override
	public String getName() {
		return "Carbide.c++"; //$NON-NLS-1$
	}

	@Override
	public boolean isValid() {
		return (carbideBuildConfig != null);
	}

	@Override
	public String toString() {
		return "Language Data for " + carbideBuildConfig.getCarbideProject().getProject().getName() + " - " + carbideBuildConfig.getDisplayString(); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
