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
package com.nokia.carbide.cpp.internal.builder.utils.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class ABLDCommandHandler extends ProjectCommandHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection == null) {
			return null;
		}

		final List<IProject> selectedProjects = getProjects(selection);
		
		if (selectedProjects.size() < 1) {
			return null;
		}

		// Save all open editor windows before starting...
		WorkbenchUtils.saveOpenEditorsIfRequired();

		Command cmd = event.getCommand();
		
		for (IProject project : selectedProjects) {
			handleABLDCommand(project, cmd.getId());
		}
		
		return null;
	}

	public static List<IProject> getProjects(ISelection selection) {
		
		List<IProject> selectedProjects = new ArrayList<IProject>(0);

		// only for non-SBSv2 projects
		for (IProject project : ProjectCommandHandler.getProjects(selection)) {
			if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
				selectedProjects.add(project);
			}
		}

		return selectedProjects;
	}

	private void handleABLDCommand(final IProject project, final String cmdId) {
		if (project == null) {
			return;
		}

		final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {

			final ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();

			Job buildJob = new Job("Invoking abld command") {
				protected IStatus run(IProgressMonitor monitor) {
					CarbideCommandLauncher launcher = new CarbideCommandLauncher(cpi.getProject(), new NullProgressMonitor(), null, cpi.getINFWorkingDirectory());
					launcher.showCommand(true);
					launcher.startTimingStats();

					// make sure the makefiles are there and up-to-date
					if (!CarbideCPPBuilder.generateBldmakeMakefilesIfNecessary(buildConfig, launcher)) {
						return new Status(IStatus.ERROR, "Carbide.c++ builder utils plugin", IStatus.ERROR, "Failed to generate bldmake makefiles", null); 
					}
					
					if (!CarbideCPPBuilder.generateAbldMakefilesIfNecessary(buildConfig, launcher, new NullProgressMonitor())) {
						return new Status(IStatus.ERROR, "Carbide.c++ builder utils plugin", IStatus.ERROR, "Failed to generate mmp makefiles", null); 
					}

					boolean success = CarbideCPPBuilder.invokeAbldCommand(buildConfig, launcher, getAbldArgs(buildConfig, cmdId), true);

		    		if (success && !CarbideCPPBuilder.projectHasBuildErrors(cpi.getProject())) {
		    			launcher.writeToConsole("\n***Complete\n");
		    		} else {
		    			launcher.writeToConsole("\n***Errors were detected. See the Problems or Console view for details.\n");
		    		}

		    		launcher.writeToConsole(launcher.getTimingStats());
					return new Status(IStatus.OK, "Carbide.c++ builder utils plugin", IStatus.OK, "abld command complete", null); 
				}

				@Override
	    		public boolean belongsTo(Object family) {
	    			return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
	    		}
			};
			
			buildJob.setPriority(Job.BUILD);
			buildJob.setUser(true);
			// set a rule so that jobs will queue up (not run simultaneously)
			buildJob.setRule(cpi.getProject());
			buildJob.schedule();
		}
	}

	protected String[] getAbldArgs(ICarbideBuildConfiguration buildConfig, String cmdId) {
		if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTarget")) { //$NON-NLS-1$
			List<String> args = getABLDTargetArgs(buildConfig);
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldExport")) { //$NON-NLS-1$
			List<String> args = getABLDExportArgs(buildConfig);
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldCleanExport")) { //$NON-NLS-1$
			return new String[] {"cleanexport"}; //$NON-NLS-1$
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldResource")) { //$NON-NLS-1$
			List<String> args = getABLDResourceArgs(buildConfig);
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldFinal")) { //$NON-NLS-1$
			List<String> args = getABLDFinalArgs(buildConfig);
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTidy")) { //$NON-NLS-1$
			return new String[] {"tidy", buildConfig.getPlatformString(), buildConfig.getTargetString()}; //$NON-NLS-1$
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTestTarget")) { //$NON-NLS-1$
			List<String> args = getABLDTargetArgs(buildConfig);
			args.add(0, "test"); //$NON-NLS-1$
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTestExport")) { //$NON-NLS-1$
			List<String> args = getABLDExportArgs(buildConfig);
			args.add(0, "test"); //$NON-NLS-1$
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTestCleanExport")) { //$NON-NLS-1$
			return new String[] {"test", "cleanexport"}; //$NON-NLS-1$ //$NON-NLS-2$
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTestResource")) { //$NON-NLS-1$
			List<String> args = getABLDResourceArgs(buildConfig);
			args.add(0, "test"); //$NON-NLS-1$
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTestFinal")) { //$NON-NLS-1$
			List<String> args = getABLDFinalArgs(buildConfig);
			args.add(0, "test"); //$NON-NLS-1$
			return args.toArray(new String[args.size()]);
		} else if (cmdId.equals("com.nokia.carbide.cpp.builder.utils.commands.abldTestTidy")) { //$NON-NLS-1$
			return new String[] {"test", "tidy", buildConfig.getPlatformString(), buildConfig.getTargetString()}; //$NON-NLS-1$
		}
		
		return new String[0];
	}
	
	private List<String> getABLDTargetArgs(ICarbideBuildConfiguration buildConfig) {
		List<String> args = new ArrayList<String>();
		args.add("target"); //$NON-NLS-1$
		args.add(buildConfig.getPlatformString().toLowerCase());
		args.add(buildConfig.getTargetString().toLowerCase());
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldTargetArgs().split(" ")) { //$NON-NLS-1$
			args.add(arg);
		}
		
		return args;
	}

	private List<String> getABLDExportArgs(ICarbideBuildConfiguration buildConfig) {
		List<String> args = new ArrayList<String>();
		args.add("export"); //$NON-NLS-1$
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldExportArgs().split(" ")) { //$NON-NLS-1$
			args.add(arg);
		}
		
		return args;
	}

	private List<String> getABLDResourceArgs(ICarbideBuildConfiguration buildConfig) {
		List<String> args = new ArrayList<String>();
		args.add("resource"); //$NON-NLS-1$
		args.add(buildConfig.getPlatformString().toLowerCase());
		args.add(buildConfig.getTargetString().toLowerCase());
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldResourceArgs().split(" ")) { //$NON-NLS-1$
			args.add(arg);
		}
		
		return args;
	}

	private List<String> getABLDFinalArgs(ICarbideBuildConfiguration buildConfig) {
		List<String> args = new ArrayList<String>();
		args.add("final"); //$NON-NLS-1$
		args.add(buildConfig.getPlatformString().toLowerCase());
		args.add(buildConfig.getTargetString().toLowerCase());
		
		for (String arg : buildConfig.getBuildArgumentsInfo().getAbldFinalArgs().split(" ")) { //$NON-NLS-1$
			args.add(arg);
		}
		
		return args;
	}
}
