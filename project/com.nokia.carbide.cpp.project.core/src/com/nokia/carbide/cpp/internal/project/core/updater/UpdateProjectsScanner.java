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
package com.nokia.carbide.cpp.internal.project.core.updater;

import com.nokia.carbide.cdt.builder.DefaultViewConfiguration;
import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.IBldInfViewRunnable;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.project.core.Messages;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.updater.extension.IUpdateProjectsScanner;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ProjectFileResourceProxyVisitor;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.*;

public class UpdateProjectsScanner implements IUpdateProjectsScanner {
	
	private static class BldInfViewRunnable implements IBldInfViewRunnable {
		private final IWorkspaceRoot workspaceRoot;
		private final EpocEnginePathHelper pathHelper;
		private final IProject project;
		
		public BldInfViewRunnable(IWorkspaceRoot workspaceRoot, IProject project, EpocEnginePathHelper pathHelper) {
			super();
			this.workspaceRoot = workspaceRoot;
			this.project = project;
			this.pathHelper = pathHelper;
		}

		public Object failedLoad(CoreException exception) {
			return null;
		}

		public Object run(IBldInfView view) {
			List<IProject> linkedProjects = null;
			IMMPReference[] allMMPReferences = view.getAllMMPReferences();
			if (allMMPReferences.length > 1) {
				for (IMMPReference reference : allMMPReferences) {
					IPath path = reference.getPath();
					IPath absPath = pathHelper.convertToFilesystem(path);
					IFile[] files = workspaceRoot.findFilesForLocation(absPath);
					for (IFile file : files) {
						IProject mmpProject = file.getProject();
						if (!mmpProject.equals(project)) {
							if (linkedProjects == null) {
								linkedProjects = new ArrayList<IProject>();
								linkedProjects.add(project);
							}

							if (!linkedProjects.contains(mmpProject))
								linkedProjects.add(mmpProject);
						}
					}
				}
			}
			return linkedProjects;
		}
	}

	private static final String INF = "inf"; //$NON-NLS-1$
	private Map<IProject, IStatus> excludedProjects = new HashMap<IProject, IStatus>();

	public IStatus getProjectStatus(IProject project) {
		IStatus status = excludedProjects.get(project);
		if (status != null)
			return status;
		
		return Status.OK_STATUS;
	}

	public Collection<IProject> scanProjects(List<IProject> projectsToScan, IProgressMonitor monitor) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		monitor.beginTask(MessageFormat.format(Messages.getString("UpdateProjectsScanner.ScanningProjectsMessage"), new Object[0]), projectsToScan.size()); //$NON-NLS-1$
		for (IProject project : projectsToScan) {
			if (!excludedProjects.containsKey(project)) {
				checkLinkedFileProject(project, new SubProgressMonitor(monitor, 2));
			}
			if (!excludedProjects.containsKey(project)) {
				gatherAssociatedProjects(workspaceRoot, project, new SubProgressMonitor(monitor, 5));
			}
			monitor.worked(1);
		}
		monitor.done();
		
		return excludedProjects.keySet();
	}

	/**
	 * Check if project contains linked resources for exclusion
	 */
	private void checkLinkedFileProject(final IProject project, IProgressMonitor monitor) {
		try {
			project.accept(new IResourceProxyVisitor() {
				public boolean visit(IResourceProxy proxy) throws CoreException {
					if (excludedProjects.containsKey(project))
						return false;
					
					if (proxy.getType() == IResource.FILE) {
						if (proxy.requestResource().isLinked()) {
							String fileName = proxy.getName();
							String extension = FileUtils.getSafeFileExtension(new Path(fileName));
							if (extension.equalsIgnoreCase("cpp") || //$NON-NLS-1$
									extension.equalsIgnoreCase("rss")) { //$NON-NLS-1$
								// import from 1.0 w/o copying
								excludedProjects.put(project, createStatusForLinkedResourceProject(project, true));
								return false;
							}
						}
					}
					else if (proxy.getType() == IResource.FOLDER) {
						if (proxy.requestResource().isLinked()) {
							// imported from 1.1 w/o copying
							excludedProjects.put(project, createStatusForLinkedResourceProject(project, false));
							return false;
						}						
					}
					return true;
				}
			}, IResource.NONE);
		} catch (CoreException e) {
		}
		
	}

	/**
	 * Gather projects linked by same bld.inf file for exclusion 
	 */
	private void gatherAssociatedProjects(IWorkspaceRoot workspaceRoot, IProject project, IProgressMonitor monitor) {
		final EpocEnginePathHelper pathHelper = new EpocEnginePathHelper(project);
		
		ProjectFileResourceProxyVisitor visitor = new ProjectFileResourceProxyVisitor(INF, true);
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			project.accept(visitor, IResource.NONE);
		} catch (CoreException e) {}
		List<IPath> infFiles = visitor.getRequestedFiles();
		for (IPath path : infFiles) {
			IPath wsInfPath = pathHelper.convertToWorkspace(path);
			List<IProject> linkedProjects = (List<IProject>) EpocEnginePlugin.runWithBldInfView(wsInfPath, 
					new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
					new BldInfViewRunnable(workspaceRoot, project, pathHelper));
			if (linkedProjects != null) {
				for (IProject linkedProject : linkedProjects) {
					IStatus status = createStatusForLinkedProjects(linkedProject, linkedProjects);
					excludedProjects.put(linkedProject, status);
				}
			}
		}
	}

	private IStatus createStatusForLinkedProjects(IProject linkedProject, List<IProject> linkedProjects) {
		String message = 
			MessageFormat.format(
					Messages.getString("UpdateProjectsScanner.LinkedProjectsMessage"), //$NON-NLS-1$
					new Object[] { linkedProject.getName() });
		for (IProject project : linkedProjects) {
			if (!project.equals(linkedProject))
				message += MessageFormat.format(" ''{0}'' ", new Object[] { project.getName() }); //$NON-NLS-1$
		}
		
		return new Status(IStatus.ERROR, ProjectCorePlugin.PLUGIN_ID, 1, message, null);
	}

	private IStatus createStatusForLinkedResourceProject(IProject project, boolean isFile) {
		String fmt = isFile ? 
				Messages.getString("UpdateProjectScanner.LinkedFileProjectMessage") : //$NON-NLS-1$
				Messages.getString("UpdateProjectScanner.LinkedFolderProjectMessage"); //$NON-NLS-1$
					
		String message = 
			MessageFormat.format(fmt, new Object[] { project.getName() });
		return new Status(IStatus.ERROR, ProjectCorePlugin.PLUGIN_ID, 2, message, null);
	}

}
