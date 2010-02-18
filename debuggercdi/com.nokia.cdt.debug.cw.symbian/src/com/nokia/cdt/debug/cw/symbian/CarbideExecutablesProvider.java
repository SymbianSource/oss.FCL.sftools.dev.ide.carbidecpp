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

import org.eclipse.cdt.core.CCProjectNature;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.cdt.debug.core.executables.IProjectExecutablesProvider;
import org.eclipse.cdt.debug.core.executables.ISourceFileRemapping;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.cdt.debug.cw.symbian.ui.executables.SymbianSourceFileRemapping;

public class CarbideExecutablesProvider implements IProjectExecutablesProvider {

	List<String> supportedNatureIds = new ArrayList<String>();

	public CarbideExecutablesProvider() {
		supportedNatureIds.add(CProjectNature.C_NATURE_ID);
		supportedNatureIds.add(CCProjectNature.CC_NATURE_ID);
		supportedNatureIds.add(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID);
		supportedNatureIds.add(CarbideBuilderPlugin.CARBIDE_SBSV2_PROJECT_NATURE_ID);
	}
	
	public List<String> getProjectNatures() {
		return supportedNatureIds;
	}

	public List<Executable> getExecutables(IProject project, IProgressMonitor monitor) {
		List<Executable> executables = new ArrayList<Executable>();

		if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
				if (config != null) {
					List<IPath> mmps = EpocEngineHelper.getMMPFilesForBuildConfiguration(config);

					SubMonitor progress = SubMonitor.convert(monitor, mmps.size());

					for (IPath mmp : mmps) {
						if (progress.isCanceled()) {
							break;
						}
						
						progress.subTask("Parsing " + mmp.lastSegment());

	 					IPath hp = EpocEngineHelper.getHostPathForExecutable(config, mmp);
						if (hp != null) {
							File hpFile = hp.toFile();
							if (hpFile.exists()) {
								try {
									Executable exe = new Executable(new Path(hpFile.getCanonicalPath()),
											project, null, new ISourceFileRemapping[] {new SymbianSourceFileRemapping()});
									executables.add(exe);
								} catch (Exception e) {
								}
							}
						}
						
						progress.worked(1);
					}
				}
			}
		}
		
		return executables;
	}

	public IStatus removeExecutable(Executable executable, IProgressMonitor monitor) {
		try {
			executable.getPath().toFile().delete();
		} catch (Exception e) {
			return new Status(IStatus.WARNING, SymbianPlugin.PLUGIN_ID, "An error occured trying to delete " + executable.getPath().toOSString());
		}
		return Status.OK_STATUS;
	}
}
