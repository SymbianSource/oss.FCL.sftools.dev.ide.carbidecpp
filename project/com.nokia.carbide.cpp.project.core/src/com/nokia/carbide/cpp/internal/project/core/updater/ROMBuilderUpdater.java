/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.internal.project.core.updater;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.IROMBuilderInfo;
import com.nokia.carbide.cpp.internal.project.core.Messages;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.updater.extension.IProjectUpdater;

/**
 * Class implementing the com.nokia.carbide.updater.projectUpdater extension point
 */
public class ROMBuilderUpdater implements IProjectUpdater {

	private static final String ROM_BUILD_NATURE_ID = "com.nokia.carbide.cdt.rombuilder.carbideRomBuildNature"; //$NON-NLS-1$
	private static final String ROM_BUILDER_ID = "com.nokia.carbide.cdt.rombuilder.carbideRomBuilder"; //$NON-NLS-1$
	private static final String ROM_BUILDER_PLUGIN_ID = "com.nokia.carbide.cdt.rombuilder";
	private static final String ROM_BUILD_COMMAND_LINE = "rombuildcommandline"; //$NON-NLS-1$
	private static final String ROM_BUILD_WORKING_DIR = "rombuildworkingdir"; //$NON-NLS-1$

	private IProject project;

	
	public ROMBuilderUpdater() {
		super();
	}

	public String getDocumentation() {
		return 
		"<div><h4>" +  //$NON-NLS-1$
		Messages.getString("ROMBuilderUpdater.Doc1") +  //$NON-NLS-1$
		Messages.getString("ROMBuilderUpdater.Doc2") +  //$NON-NLS-1$
		"</h4></div>"; //$NON-NLS-1$
	}

	public String getUpdateLabel() {
		return Messages.getString("ROMBuilderUpdater.Label"); //$NON-NLS-1$
	}

	public boolean needsUpdate(IProject project, IProgressMonitor monitor) {
		IProjectDescription description = null;
		try {
			description = project.getDescription();
		} 
		catch (CoreException e) {
		}

		if (description != null && description.hasNature(ROM_BUILD_NATURE_ID)) {
			return true;
		}
		return false;
	}
	
	public IStatus update(IProject project, IProgressMonitor monitor) {
		this.project = project;
		
		try {
			monitor.beginTask(MessageFormat.format(Messages.getString("ProjectUpdater.TaskName"), //$NON-NLS-1$
					new Object[] { project.getName() }), 3);
			return doUpdateProject(new SubProgressMonitor(monitor, 3));
		} finally {
			monitor.done();
		}
	}
	
	private IStatus doUpdateProject(IProgressMonitor monitor) {
		try {
			// remove the nature
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			List<String> newNatures = new ArrayList<String>(Arrays.asList(prevNatures));
			newNatures.remove(ROM_BUILD_NATURE_ID);
			description.setNatureIds((String[]) newNatures.toArray(new String[newNatures.size()]));
			project.setDescription(description, monitor);

			monitor.worked(1);
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;

			// remove the builder
			ICommand[] commands = description.getBuildSpec();
			for (int i = 0; i < commands.length; ++i) {
				if (commands[i].getBuilderName().equals(ROM_BUILDER_ID)) {
					ICommand[] newCommands = new ICommand[commands.length - 1];
					System.arraycopy(commands, 0, newCommands, 0, i);
					System.arraycopy(commands, i + 1, newCommands, i,
							commands.length - i - 1);
					description.setBuildSpec(newCommands);
					project.setDescription(description, monitor);
					break;
				}
			}

			monitor.worked(1);
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;

			// convert the prefs, if any
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				
				String cmdLine = getAndRemoveRomBuildCommandLine();
				String workingDir = getAndRemoveRomBuildWorkingDir();

				for (ICarbideBuildConfiguration config : cpi.getBuildConfigurations()) {
					// don't add it for emulator configs
					if (!config.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						IROMBuilderInfo info = config.getROMBuildInfo();

						if (cmdLine != null && cmdLine.trim().length() > 0) {
							info.setCommandLine(cmdLine);
						}
						
						if (workingDir != null && workingDir.trim().length() > 0) {
							info.setWorkingDirectory(workingDir);
						}
					}
				}
			}
			
			monitor.worked(1);

		} catch (CoreException e) {
			e.printStackTrace();
		}

		return Status.OK_STATUS;
	}
	
	private String getAndRemoveRomBuildCommandLine() {
		try {
			ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(project);
			if (projDes != null) {
				ICStorageElement storage = projDes.getStorage(ROM_BUILDER_PLUGIN_ID, false);
				if (storage != null) {
					String value = storage.getAttribute(ROM_BUILD_COMMAND_LINE);
					storage.removeAttribute(ROM_BUILD_COMMAND_LINE);
					return value;
				}
			}
		}
		catch (CoreException e) {
			e.printStackTrace();
			ProjectCorePlugin.log(e);
		}
		
		return null;
	}
	
	private String getAndRemoveRomBuildWorkingDir() {
		try {
			ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(project);
			if (projDes != null) {
				ICStorageElement storage = projDes.getStorage(ROM_BUILDER_PLUGIN_ID, false);
				if (storage != null) {
					String value = storage.getAttribute(ROM_BUILD_WORKING_DIR);
					storage.removeAttribute(ROM_BUILD_WORKING_DIR);
					return value;
				}
			}
		}
		catch (CoreException e) {
			e.printStackTrace();
			ProjectCorePlugin.log(e);
		}
		
		return null;
	}
}

