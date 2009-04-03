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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.internal.builder.utils.Activator;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class BuildPKGCommandHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection == null) {
			return null;
		}

		final List<IFile> selectedFiles = getPKGFiles(selection);
		
		if (selectedFiles.size() < 1) {
			return null;
		}

		// Save all open editor windows before starting...
		WorkbenchUtils.saveOpenEditorsIfRequired();
		
		for (IFile file : selectedFiles) {
			doBuildSinglePKGFile(file);
		}

		return null;
	}

	/**
	 * If all selected files are pkg files, returns the list of IFile's
	 * of the selection, otherwise returns an empty list.
	 * @param selection the selection
	 * @return list of pkg files which may be empty
	 */
	public static List<IFile> getPKGFiles(ISelection selection) {
		
		List<IFile> selectedFiles = new ArrayList<IFile>(0);

		if (selection instanceof IStructuredSelection) {
			Iterator<?> iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				Object selectionItem = iter.next();
				if (selectionItem instanceof IFile) {
					IFile file = (IFile)selectionItem;
					if (file.getName().toLowerCase().endsWith(".pkg")) { //$NON-NLS-1$
						selectedFiles.add(file);
					} else {
						selectedFiles.clear();
						return selectedFiles;
					}
				} else {
					selectedFiles.clear();
					return selectedFiles;
				}
			}
		} else if (selection instanceof ITextSelection) {
			IWorkbenchWindow window = Activator.getActiveWorkbenchWindow();
			if (window != null) {
				IWorkbenchPage page = window.getActivePage();
				if (page != null) {
					IWorkbenchPart part = page.getActivePart();
					if (part instanceof IEditorPart) {
						IEditorPart epart = (IEditorPart) part;
						IResource resource = (IResource) epart.getEditorInput().getAdapter(IResource.class);
						if (resource instanceof IFile) {
							if (((IFile)resource).getName().toLowerCase().endsWith(".pkg")) { //$NON-NLS-1$
								selectedFiles.add((IFile)resource);
							}
						}
					}
				}
			}
		}
		
		return selectedFiles;
	}
	
	private void doBuildSinglePKGFile(IFile pkgFile) {

		final IProject project = pkgFile.getProject();
		
		try {
			CarbideCPPBuilder.removeAllMarkers(project);
		} catch (CoreException e){
			e.printStackTrace();
		}

		final IPath finalPKGPath = pkgFile.getLocation();;
		final String finalComponentName = finalPKGPath.lastSegment();

		Job buildJob = new Job("Building PKG file - " + finalComponentName) { //$NON-NLS-1$
			protected IStatus run(IProgressMonitor monitor){
				
				SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
				
				String projectName = project.getName();
		        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		        IPath workingDir = cpi.getINFWorkingDirectory();
				CarbideCommandLauncher bldMakeLauncher = new CarbideCommandLauncher(project, monitor, CarbideCPPBuilder.getParserIdArray(ICarbideBuildConfiguration.ERROR_PARSERS_SIS_BUILDER), workingDir);
				bldMakeLauncher.showCommand(true);
				String defaultConfigName = cpi.getDefaultBuildConfigName();
				ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
				ISymbianBuildContext context = SymbianBuildContext.getBuildContextFromDisplayName(defaultConfigName);
				
				bldMakeLauncher.startTimingStats();
				
				bldMakeLauncher.writeToConsole("\n***Building \"" + finalComponentName + "\" for project \"" + projectName + "\" and configuration \"" + context.getDisplayString() + "\".\n");
			
				CarbideCPPBuilder.invokeSISBuilder(finalPKGPath, defaultConfig, bldMakeLauncher, subMonitor, false);
				bldMakeLauncher.writeToConsole(bldMakeLauncher.getTimingStats());
				
				return new Status(IStatus.OK, "Carbide.c++ builder utils plugin", IStatus.OK, "Build Complete", null); 
			}

			@Override
    		public boolean belongsTo(Object family) {
    			return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
    		}
		};
		
		buildJob.setPriority(Job.BUILD);
		buildJob.setUser(true);
		buildJob.schedule();
	}
}
