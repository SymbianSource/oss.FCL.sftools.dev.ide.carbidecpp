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

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.CoreModelUtil;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.builder.utils.Activator;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class CompileHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection == null) {
			return null;
		}

		final List<IFile> selectedFiles = getFilesToCompile(selection);
		
		if (selectedFiles.size() < 1) {
			return null;
		}

		// Save all open editor windows before starting...
		WorkbenchUtils.saveOpenEditorsIfRequired();
		
		Job buildJob = new Job("Compiling file(s)") {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					SubMonitor subMonitor = SubMonitor.convert(monitor, selectedFiles.size());

					IProject lastProject = selectedFiles.get(0).getProject();
					ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(lastProject);
					String[] errorParserIds = CarbideCPPBuilder.getParserIdArray(cpi.getDefaultConfiguration().getErrorParserId());
					IPath workingDirectory = cpi.getINFWorkingDirectory();

					// create the launcher for the first file's project.  if there are files selected for other projects, change the
					// launcher then and only then.  creating a new launcher clears the build console so we don't want to do that
					// for files of the same project.
					CarbideCommandLauncher launcher = new CarbideCommandLauncher(lastProject, monitor, errorParserIds, workingDirectory);
					launcher.showCommand(true);

					// clear the project markers
		    		try {
		    			CarbideCPPBuilder.removeAllMarkers(lastProject);
		    		} catch (CoreException e) {
		    			e.printStackTrace();
		    		}

		    		for (final IFile file : selectedFiles) {
						IProject project = file.getProject();
			        	final IPath path = file.getLocation();
						
						if (project != null && path != null) {
							if (lastProject != project && path != null) {
					    		lastProject = project;
								cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(lastProject);
								errorParserIds = CarbideCPPBuilder.getParserIdArray(cpi.getDefaultConfiguration().getErrorParserId());
								workingDirectory = cpi.getINFWorkingDirectory();
								launcher = new CarbideCommandLauncher(lastProject, monitor, errorParserIds, workingDirectory);
								launcher.showCommand(true);
								
								// only clear the markers once per project
					    		try {
					    			CarbideCPPBuilder.removeAllMarkers(lastProject);
					    		} catch (CoreException e) {
					    			e.printStackTrace();
					    		}
							}

							try {
								CarbideCPPBuilder.compileFile(path, cpi.getDefaultConfiguration(), CCorePlugin.getDefault().getConsole(), launcher, subMonitor.newChild(1), false);
								launcher.setErrorParserManager(workingDirectory, errorParserIds);
							} catch (CoreException e) {
								//TODO create a marker instead
								return e.getStatus();
							}
						}

				        if (monitor.isCanceled()) {
							return new Status(IStatus.CANCEL, "Carbide.c++ builder utils plugin", IStatus.CANCEL, "Cancelled", null); 
						}
					}
				} finally {
					monitor.done();
				}

				return new Status(IStatus.OK, "Carbide.c++ builder utils plugin", IStatus.OK, "Complete", null); 
			}
		};
		
		buildJob.setPriority(Job.BUILD);
		buildJob.setUser(true);
		buildJob.schedule();

		return null;
	}

	/**
	 * If all selected files are compilable, returns the list of IFile's
	 * of the selection, otherwise returns an empty list.
	 * @param selection the selection
	 * @return list of compilable files which may be empty
	 */
	public static List<IFile> getFilesToCompile(ISelection selection) {
		
		List<IFile> selectedFiles = new ArrayList<IFile>(0);

		if (selection instanceof IStructuredSelection) {
			Iterator<?> iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				Object selectionItem = iter.next();
				if (selectionItem instanceof IFile) {
					IFile file = (IFile)selectionItem;
					ITranslationUnit tu = CoreModelUtil.findTranslationUnit(file);
					if (tu != null && tu.isSourceUnit()) {
						selectedFiles.add(file);
					} else {
						selectedFiles.clear();
						return selectedFiles;
					}
				} else if (selectionItem instanceof ITranslationUnit) {
					ITranslationUnit tu = (ITranslationUnit) selectionItem;
					if (tu.isSourceUnit() && tu.getResource() instanceof IFile) {
						selectedFiles.add((IFile)tu.getResource());
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
						ICElement element = CoreModel.getDefault().create(resource);
						if (element instanceof ITranslationUnit) {
							ITranslationUnit tu = (ITranslationUnit) element;
							if (tu.isSourceUnit() && tu.getResource() instanceof IFile) {
								selectedFiles.add((IFile)tu.getResource());
							}
						}
					}
				}
			}
		}
		
		return selectedFiles;
	}
}
