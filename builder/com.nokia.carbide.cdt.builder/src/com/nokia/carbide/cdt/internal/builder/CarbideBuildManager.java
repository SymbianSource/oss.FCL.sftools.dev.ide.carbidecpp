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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.core.settings.model.extension.CFolderData;
import org.eclipse.cdt.core.settings.model.extension.CLanguageData;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.ICarbideBuildManager;
import com.nokia.carbide.cdt.builder.extension.ICarbidePrefsModifier;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.internal.api.builder.ui.CarbidePrefsModifier;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher.IResourceChangeHandler;

/**
 * This is a singleton class that allows you to get any Carbide.c++ project. You can get this instance by calling
 * CarbideBuildManager BuilderPlugin.getBuildManager().
 * 
 * @see BuilderPlugin, ICarbideProjectInfo
 *
 */
public class CarbideBuildManager implements ICarbideBuildManager, IResourceChangeListener, ICarbideInstalledSDKChangeListener {
	
	private static final String CONVERTED_SRC_MAPPINGS_2X_TO_3X = "convertedSrcMappings2xTo3x"; //$NON-NLS-1$
	
	private Map<IProject, ICarbideProjectInfo> projectInfoMap = new HashMap<IProject, ICarbideProjectInfo>();
	private MultiResourceChangeListenerDispatcher resourceChangedListener = new MultiResourceChangeListenerDispatcher();
	
	ICarbidePrefsModifier clientPrefsModifier;
	
	
	public CarbideBuildManager() {
		SDKCorePlugin.getSDKManager().addInstalledSdkChangeListener(this);
		
		if (clientPrefsModifier == null){
			clientPrefsModifier = new CarbidePrefsModifier();
		}
	}
	
	public boolean isCarbideProject(IProject project) {
		boolean carbideProject = false;
		
		try {
			if (project != null && project.isOpen() && project.hasNature(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID)) {
				carbideProject = true;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return carbideProject;
	}
	
	public boolean isCoronaProject(IProject project) {
		boolean coronaProject = false;
		
		try {
			if (project.isOpen() && project.hasNature(CarbideBuilderPlugin.CORONA_PROJECT_NATURE_ID)) {
				coronaProject = true;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return coronaProject;
	}
	
	public boolean isCarbideSBSv2Project(IProject project) {
		boolean carbideSBSv2Project = false;
		
		try {
			if (project != null && project.isOpen() && project.hasNature(CarbideBuilderPlugin.CARBIDE_SBSV2_PROJECT_NATURE_ID)) {
				carbideSBSv2Project = true;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return carbideSBSv2Project;
	}

	public ICarbideProjectInfo getProjectInfo(IProject project) {
		synchronized (projectInfoMap) {
			ICarbideProjectInfo info = projectInfoMap.get(project);
			if (info != null) {
				return info;
			}
		}

		// we haven't loaded info for this project yet.  load and it if it's
		// valid, add it to the map and return it.  otherwise return null.
		
		// The lock on projectInfoMap must be released and reacquired around the
		// creation of CarbideProjectInfo because workspace resources will be
		// accessed. Those resources may be locked by another thread, so deadlock 
		// may occur.
		if (isCarbideProject(project)) {
			CarbideProjectInfo newInfo = new CarbideProjectInfo(project);
			
			ICarbideProjectInfo info = null;
			
			synchronized (projectInfoMap) {
				// Since we released and reacquired the lock we need to
				// check if someone else added the project already.
				info = projectInfoMap.get(project);
				if (info == null) {
					// projectRelativeBldInfPath will not be null if the project info is valid
					if (newInfo.getProjectRelativeBldInfPath() != null) {
						projectInfoMap.put(project, newInfo);
						addProjectListener(newInfo);
						info = newInfo;
					}
				}
			}
			if (info != null && info.getBuildConfigurations().size() == 0){
				CarbideBuilderPlugin.createCarbideProjectMarker(project, IMarker.SEVERITY_ERROR, "No configurations can be found for this project. Please add a configuration by choosing Project > Properties > Carbide Build Configurations > Manage...", IMarker.PRIORITY_HIGH);
			}
			
			validateProjectAndSetProjectMarker(info);
			
			return info;
		}

		return null;
	}
	
	public ICarbideProjectModifier createProjectInfo(ICProjectDescription projDes) {
		synchronized (projectInfoMap) {
			IProject project = projDes.getProject();
			assert(projectInfoMap.get(project) == null);
			
			return new CarbideProjectModifier(projDes);
		}
	}

	public ICarbideProjectModifier getProjectModifier(IProject project) {
		ICarbideProjectInfo info = getProjectInfo(project);
		if (info != null) {
			return new CarbideProjectModifier((CarbideProjectInfo)info);
		}
		return null;
	}

	public void setProjectInfo(ICarbideProjectInfo newInfo) {
		synchronized (projectInfoMap) {
			IProject project = newInfo.getProject();
//			ICarbideProjectInfo oldInfo = projectInfoMap.get(project);
//			assert(oldInfo != null);
			
			projectInfoMap.put(project, newInfo);
			addProjectListener(newInfo);
		}
	}
	
	public void resourceChanged(IResourceChangeEvent event) {
		// remove projects from map when they are closed or deleted
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE || event.getType() == IResourceChangeEvent.PRE_DELETE) {
			projectInfoMap.remove(event.getResource());
		}
	}
	
	public void validateProjectAndSetProjectMarker(ICarbideProjectInfo projectInfo) {
		if (projectInfo != null) {
			List<ICarbideBuildConfiguration> configs = projectInfo.getBuildConfigurations();
			for (ICarbideBuildConfiguration config : configs) {
				if (config instanceof CarbideBuildConfiguration) {
					((CarbideBuildConfiguration)config).validateAndSetProjectMarker();
				}
			}
		}
	}
	
	private class ResourceChangeHandler implements IResourceChangeHandler {
		
		private ICarbideProjectInfo cpi;
		// lock for the next two booleans
		private Object refreshingLock;
		// if true, currently running a refresh job
		private boolean refreshingCondition;
		// if true, re-refreshing was delayed due to a job running
		private boolean refreshingDelayed;
		
		public ResourceChangeHandler(ICarbideProjectInfo cpi) {
			this.cpi = cpi;
			refreshingLock = new Object();
			refreshingCondition = false;
			refreshingDelayed = false;
		}
		
		public void resourceChanged(final IPath workspacePath) {
			//System.out.println("Queueing refresh job due to change on " + workspacePath);
			queueRefreshingJob();
		}

		/**
		 * 
		 */
		private void queueRefreshingJob() {
			final IProject project = cpi.getProject();
			
			if (!project.isAccessible()) {
				return;
			}
			
			// avoid queueing multiple jobs on the same project at the same time
			synchronized (refreshingLock) {
				if (refreshingCondition) {
					refreshingDelayed = true;
					return;
				}
				refreshingDelayed = false;
				refreshingCondition = true;
			}
			
			// when the bld.inf or an mmp or any of their includes changes we want CDT to re-read the
			// paths and symbols info.  setting the project description is the only way to get CDT to
			// call BuildConfigurationData#getSourceEntries and CarbideLanguageData#getEntries.  that's
			// where the cache is stored and will be reset for each build config if necessary
			WorkspaceJob refreshDataJob = new WorkspaceJob("Refreshing configuration data") {
				public IStatus runInWorkspace(IProgressMonitor monitor) {
					try {
						try {
							ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(project);
							if (projDes != null) {
								CCorePlugin.getDefault().setProjectDescription(project, projDes, true, null);
							}
						} catch (CoreException e) {
							e.printStackTrace();
							CarbideBuilderPlugin.log(e);
						}
					} finally {
						// now we can refresh again; we need to re-refresh if another 
						// resource changed while we were refreshing since we have no
						// idea if its change happened before or after the refresh job
						// read it
						synchronized (refreshingLock) {
							refreshingCondition = false;
							if (refreshingDelayed) {
								queueRefreshingJob();
							}
						}
					}
					return new Status(IStatus.OK, CarbideBuilderPlugin.PLUGIN_ID, IStatus.OK, null, null); 
				}
			};
			// schedule on workspace:  (1) this job triggers EPOC engine model reading, which
			// must be scheduled if models are also being written at the same time -- which is
			// highly likely given that this job is in response to a resource change! 
			// (Actual test case: importing images in UIQ UI Designer projects can touch
			// bld.inf and then PKG models in succession.)
			// (2) CDT needs the workspace to be scheduled, versus the project, for some reason.
			refreshDataJob.setRule(project.getWorkspace().getRoot());
			refreshDataJob.setPriority(Job.SHORT);
			refreshDataJob.schedule();
			
			// reset the list of files we're listening to as whatever changed could have affected
			// the list of files.
			listenForReferencedFileChanges(cpi, this);
		}
	}

	private void addProjectListener(ICarbideProjectInfo cpi) {
		final IProject project = cpi.getProject();
		
		// get the list of files the engine references when parsing the bld.inf file
		listenForReferencedFileChanges(cpi, new ResourceChangeHandler(cpi));

		// remove all the resource listeners once the project is closed or deleted
		IResourceChangeListener projectDeleteListener = new IResourceChangeListener() {

			public void resourceChanged(IResourceChangeEvent event) {
				// see if a project was closed or deleted
				if (event.getType() == IResourceChangeEvent.PRE_CLOSE || event.getType() == IResourceChangeEvent.PRE_DELETE) {
					if (project.equals(event.getResource())) {
						resourceChangedListener.removeAllForPrefix(new Path(project.getName()));
						ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
					}
				}
			}
			
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(projectDeleteListener);
		
	}
	
	private void listenForReferencedFileChanges(ICarbideProjectInfo cpi, ResourceChangeHandler handler) {
		// reset the listener
		resourceChangedListener.removeAllForPrefix(new Path(cpi.getProject().getName()));
		
		// calculate the list for each build config so the engine has a context with which to
		// expand macros, locate includes, etc.  we're using a set to avoid duplicates.
		Set<IPath> pathList = new HashSet<IPath>();
		for (ICarbideBuildConfiguration config : cpi.getBuildConfigurations()) {

			// force a rebuild of the CarbideLanguageData cache
			// TODO PERFORMANCE EJS: why??? We end up forcing a cache rebuild even when you just switch configurations...
			CLanguageData languageData = null;
			ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(config.getCarbideProject().getProject());
			if (projDes != null) {
				ICConfigurationDescription configDes = projDes.getConfigurationById(config.getDisplayString());
				if (configDes != null) {
					CConfigurationData configData = configDes.getConfigurationData();
					if (configData != null && configData instanceof BuildConfigurationData) {
						CFolderData rootFolderData = ((BuildConfigurationData)configData).getRootFolderData();
						if (rootFolderData != null) {
							CLanguageData[] languageDatas = rootFolderData.getLanguageDatas();
							if (languageDatas != null && languageDatas.length > 0) {
								// there's only one for Carbide anyway
								languageData = languageDatas[0];
								if (languageData instanceof CarbideLanguageData)
									((CarbideLanguageData)languageData).forceRebuildCache();
							}
						}
					}
				}
			}

			EpocEngineHelper.addIncludedFilesFromBldInf(cpi, config, cpi.getAbsoluteBldInfPath(), pathList);

			
			// get the list of all mmp files selected for the build configuration
			// a null buildComponents list means all MMPs are included - so leave it null when indexing all files
			List<String> buildComponents = null;
			if (!EpocEngineHelper.getIndexAllPreference())
				buildComponents = config.getCarbideProject().isBuildingFromInf() ? null : config.getCarbideProject().getInfBuildComponents();

			for (IPath mmpPath : EpocEngineHelper.getMMPFilesForBuildConfiguration(config)) {
				if (buildComponents == null || TextUtils.listContainsIgnoreCase(buildComponents, mmpPath.lastSegment()))
					EpocEngineHelper.addIncludedFilesFromMMP(cpi, config, mmpPath, pathList);
			}
		}
		
		// now listen for changes to these files (the ones in the workspace at least)
		for (IPath path : pathList) {
			IPath wsPath = FileUtils.convertToWorkspacePath(path);
			if (wsPath != null) {
				resourceChangedListener.addResource(wsPath, handler);
			}
		}
	}

	public void installedSdkChanged(SDKChangeEventType eventType) {
		
		if (eventType == SDKChangeEventType.eSDKScanned){
			// once per workspace, convert existing Carbide 2.x source location mappings to 3.x
			if (!CarbideBuilderPlugin.getDefault().getPreferenceStore().getBoolean(CONVERTED_SRC_MAPPINGS_2X_TO_3X)) {
				try {
					CarbideBuildManagerUtils.convertSourceMappings2xTo3x();
				} catch (Exception e) {
				}
				CarbideBuilderPlugin.getDefault().getPreferenceStore().setValue(CONVERTED_SRC_MAPPINGS_2X_TO_3X, true);
			}

			// TODO: This is causing deadlocks with the indexer and generally when other project info is being retrieved
			// Need to consider what this is actually doing and why it even needs to be here.
			
//			synchronized(projectInfoMap){
//				for (IProject currPrj : projectInfoMap.keySet()){
//					try {
//					ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(currPrj);
//					if (projDes != null) {
//						CCorePlugin.getDefault().setProjectDescription(currPrj, projDes, true, null);
//					}
//					} catch (CoreException e) {
//						e.printStackTrace();
//						CarbideBuilderPlugin.log(e);
//					}
//				}
//			}
		}
	}
	
	public ICarbidePrefsModifier getPrefsModifier(){
		return clientPrefsModifier;
	}

}
