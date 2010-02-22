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
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.builder.utils.Activator;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class ProjectCommandHandler extends AbstractHandler {

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
		
		if (cmd.getId().equals("com.nokia.carbide.cpp.builder.utils.commands.buildAllConfigs")) { //$NON-NLS-1$
			for (IProject project : selectedProjects) {
				buildAllConfigurationsForProject(project);
			}
		} else if (cmd.getId().equals("com.nokia.carbide.cpp.builder.utils.commands.freezeExports")) { //$NON-NLS-1$
			for (IProject project : selectedProjects) {
				freezeExports(project);
			}
		}
		
		return null;
	}

	/**
	 * If all selected items adapt to Carbide projects then returns a list of
	 * IProjects, otherwise returns an empty list.
	 * @param selection the selection
	 * @return list of projects which may be empty
	 */
	public static List<IProject> getProjects(ISelection selection) {
		
		List<IProject> selectedProjects = new ArrayList<IProject>(0);

		if (selection instanceof IStructuredSelection) {
			Iterator<?> iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				IProject project = null;
				Object selectionItem = iter.next();
				if (selectionItem instanceof ICElement) {
					ICProject cproject = ((ICElement)selectionItem).getCProject();
					if (cproject != null) {
						project = cproject.getProject();
					}
				} else if (selectionItem instanceof IResource) {
					project = ((IResource)selectionItem).getProject();
				} else if (selectionItem instanceof IAdaptable) {
					project = (IProject)((IAdaptable)selectionItem).getAdapter(IProject.class);
				}
				
				if (project != null) {
					if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
						selectedProjects.add(project);
					} else {
						selectedProjects.clear();
						return selectedProjects;
					}
				} else {
					selectedProjects.clear();
					return selectedProjects;
				}
			}
		} else if (selection instanceof ITextSelection) {
			IWorkbenchWindow window = Activator.getActiveWorkbenchWindow();
			IWorkbenchPage wpage = window.getActivePage();
			if (wpage != null) {
				// make sure it's not the disassembly (or DSF disassembly) view
				IWorkbenchPart part = wpage.getActivePart();
				if (part == null || !part.getTitle().toLowerCase().endsWith("disassembly")) { //$NON-NLS-1$
					IEditorPart ep = wpage.getActiveEditor();
					if (ep != null) {
						IEditorInput editorInput = ep.getEditorInput();
						if (editorInput instanceof IFileEditorInput) {
							IFile file = ((IFileEditorInput)editorInput).getFile();
							if (file != null) {
								IProject project = file.getProject();
								if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
									selectedProjects.add(project);
								}
							}
						}
					}
				}
			}
		}
		
		return selectedProjects;
	}

	private void buildAllConfigurationsForProject(final IProject project) {
		if (project == null) {
			return;
		}
		
		final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			final List<ICarbideBuildConfiguration> buildConfigList = cpi.getBuildConfigurations();

			final CarbideCommandLauncher launcher = new CarbideCommandLauncher(cpi.getProject(), new NullProgressMonitor(), null, cpi.getINFWorkingDirectory());
			launcher.showCommand(true);

    		try {
    			CarbideCPPBuilder.removeAllMarkers(cpi.getProject());
    		} catch (CoreException e) {
    			e.printStackTrace();
    		}

    		Job buildJob = new Job("Building all configurations for project " + project.getName()) { //$NON-NLS-1$
				protected IStatus run(IProgressMonitor monitor) {
					try {
						SubMonitor subMonitor = SubMonitor.convert(monitor, buildConfigList.size());
						
						for (final ICarbideBuildConfiguration currConfig : buildConfigList) {
							launcher.setErrorParserManager(cpi.getINFWorkingDirectory(), CarbideCPPBuilder.getParserIdArray(currConfig.getErrorParserId()));
							
							if (monitor.isCanceled()) {
								return new Status(IStatus.CANCEL, "Carbide.c++ builder utils plugin", IStatus.CANCEL, "Build Cancelled", null); 
							}
							// kick off a build for each configuration...
							CarbideCPPBuilder.invokeBuild(currConfig, launcher, subMonitor.newChild(1), false);
							
							if (monitor.isCanceled()) {
								return new Status(IStatus.CANCEL, "Carbide.c++ builder utils plugin", IStatus.CANCEL, "Build Cancelled", null); 
							}
						}
					} finally {
						monitor.done();
					}
					
					return new Status(IStatus.OK, "Carbide.c++ builder utils plugin", IStatus.OK, "Build Done", null); 
				}

				@Override
	    		public boolean belongsTo(Object family) {
	    			return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
	    		}
			};
			buildJob.setPriority(Job.BUILD);
			// set the workspace as the rule so that jobs will queue up (not run simultaneously)
			// since the builder keeps a list of the build components
			buildJob.setRule(project.getWorkspace().getRoot());
			buildJob.setUser(true);
			buildJob.schedule();
		}	
	}
	
	private void freezeExports(final IProject project) {
		if (project == null) {
			return;
		}
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		
		final ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
		Job buildJob = new Job("Freezing Configuration - " + defaultConfig.getDisplayString()) {
			protected IStatus run(IProgressMonitor monitor) {
				CarbideCPPBuilder.invokeFreeze(defaultConfig, monitor, true);
				Activator.refreshProjectAfterFreeze(project);
				return new Status(IStatus.OK, "Carbide.c++ builder utils plugin", IStatus.OK, "Freeze complete", null); 
			}

			@Override
    		public boolean belongsTo(Object family) {
    			return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
    		}
		};
		
		buildJob.setPriority(Job.BUILD);
		// set the workspace as the rule so that jobs will queue up (not run simultaneously)
		// since the builder keeps a list of the build components
		buildJob.setRule(project.getWorkspace().getRoot());
		buildJob.setUser(true);
		buildJob.schedule();
	}
}
