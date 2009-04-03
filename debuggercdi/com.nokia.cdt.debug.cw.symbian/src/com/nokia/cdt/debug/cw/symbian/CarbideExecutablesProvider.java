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

package com.nokia.cdt.debug.cw.symbian;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.cdt.debug.core.executables.ExecutablesManager;
import org.eclipse.cdt.debug.core.executables.StandardExecutableProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;

public class CarbideExecutablesProvider extends StandardExecutableProvider implements IJobChangeListener {

	private ArrayList<Executable> executables = new ArrayList<Executable>();
	private ArrayList<Executable> activeExecutables = new ArrayList<Executable>();

	public CarbideExecutablesProvider() {
		super();
		Job.getJobManager().addJobChangeListener(this);		
	}

	public int getPriority() {
		return HIGH_PRIORITY;
	}

	public boolean executableExists(IPath exePath) {
		for (Executable executable : executables) {
			if (executable.getPath().equals(exePath))
				return true;
		}
		return false;
	}

	public Executable[] getExecutables(IProgressMonitor monitor) {

		synchronized (executables) {
			executables.clear();
			activeExecutables.clear();
			
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject[] projects = root.getProjects();

			monitor.beginTask("Checking Carbide Projects", projects.length);

			for (IProject project : projects) {

				if (monitor.isCanceled())
					break;

				try {
					if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
						ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
						if (cpi != null) {
							ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
							List<ICarbideBuildConfiguration> buildConfigList = cpi.getBuildConfigurations();
							for (ICarbideBuildConfiguration currConfig : buildConfigList) {
								if (monitor.isCanceled())
									break;

								for (IPath mmp : EpocEngineHelper.getMMPFilesForBuildConfiguration(currConfig)) {
									if (monitor.isCanceled())
										break;

									IPath hp = EpocEngineHelper.getHostPathForExecutable(currConfig, mmp);
									if (hp != null) {
										File hpFile = hp.toFile();
										if (hpFile.exists())
										{
											Executable exe = new Executable(new Path(hpFile.getCanonicalPath()), project, null);
											executables.add(exe);
											if (currConfig == defaultConfig)
												activeExecutables.add(exe);										
										}
									}
								}
							}
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				monitor.worked(1);
			}

			monitor.done();
		}
		return activeExecutables.toArray(new Executable[activeExecutables.size()]);
	}

	public void aboutToRun(IJobChangeEvent event) {}

	public void awake(IJobChangeEvent event) {}

	/**
	 * This is the lower case name of the build job for "Build Target Only". This is used here to avoid
	 * having a reference to com.nokia.cdt.carbide.builder.utils.
	 * @see com.nokia.carbide.cdt.build.utils.popup.actions.AbldCommandAction#runAbldActionOnProject(...)
	 */
	private static final String BUILD_TARGET_ONLY_JOB_NAME_LOWER = "performing targeted build for configuration";
	
	/**
	 * This is the lower case name of the build job for "Build Symbian Component". This is used here to avoid
	 * having a reference to com.nokia.cdt.carbide.builder.utils.
	 * @see com.nokia.carbide.cdt.build.utils.popup.actions.AbldCommandAction#doBuildSingleMMPComponent(...)
	 */
	private static final String BUILD_SYMBIAN_COMPONENT_JOB_NAME_LOWER = "building selected component";

	private static final String BUILD_ALL_CONFIGURATIONS_JOB_NAME_LOWER = "building all configurations for project";

	public void done(IJobChangeEvent event) {

		if (event.getJob().belongsTo(ResourcesPlugin.FAMILY_MANUAL_BUILD) ||
				event.getJob().getName().toLowerCase().startsWith(BUILD_TARGET_ONLY_JOB_NAME_LOWER) ||
				event.getJob().getName().toLowerCase().startsWith(BUILD_ALL_CONFIGURATIONS_JOB_NAME_LOWER) ||
				event.getJob().getName().toLowerCase().startsWith(BUILD_SYMBIAN_COMPONENT_JOB_NAME_LOWER)) {
				{
					ExecutablesManager.getExecutablesManager().scheduleRefresh(this, 1000);
				}
			}
		}

	public void running(IJobChangeEvent event) {}

	public void scheduled(IJobChangeEvent event) {}

	public void sleeping(IJobChangeEvent event) {}

}
