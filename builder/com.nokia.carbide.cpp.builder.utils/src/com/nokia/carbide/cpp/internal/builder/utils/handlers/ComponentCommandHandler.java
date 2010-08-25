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

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.CoreModelUtil;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.builder.utils.Activator;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class ComponentCommandHandler extends AbstractHandler {

	private static final int BUILD_ID = 1;
	private static final int CLEAN_ID = 2;
	private static final int FREEZE_ID = 4;

	public Object execute(ExecutionEvent event) throws ExecutionException {

		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection == null) {
			return null;
		}

		final List<IFile> selectedFiles = getFilesFromSelection(selection, true);
		
		if (selectedFiles.size() < 1) {
			return null;
		}

		// Save all open editor windows before starting...
		WorkbenchUtils.saveOpenEditorsIfRequired();
		
		Command cmd = event.getCommand();
		
		if (cmd.getId().equals("com.nokia.carbide.cpp.builder.utils.commands.buildSymbianComponent")) { //$NON-NLS-1$
			handleSelectedMMPComponentsAction(selectedFiles, BUILD_ID);
		} else if (cmd.getId().equals("com.nokia.carbide.cpp.builder.utils.commands.cleanSymbianComponent")) { //$NON-NLS-1$
			handleSelectedMMPComponentsAction(selectedFiles, CLEAN_ID);
		} else if (cmd.getId().equals("com.nokia.carbide.cpp.builder.utils.commands.freezeSymbianComponent")) { //$NON-NLS-1$
			handleSelectedMMPComponentsAction(selectedFiles, FREEZE_ID);
		}

		return null;
	}

	/**
	 * Returns whether or not component commands should be enable for the given selection
	 * @param selection the selection
	 * @return true if commands should be enabled, false otherwise
	 */
	public static boolean enabledForSelection(ISelection selection) {
		List<IFile> selectedFiles = getFilesFromSelection(selection, false);
		if (selectedFiles.size() > 0) {
			for (IFile file : selectedFiles) {
				if (!CarbideBuilderPlugin.getBuildManager().isCarbideProject(file.getProject())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private static List<IFile> getFilesFromSelection(ISelection selection, boolean lookupParentMMPs) {
		
		List<IFile> selectedFiles = new ArrayList<IFile>(0);

		if (selection.isEmpty() || selection instanceof ITextSelection) {
			// could be the MMP or make file editor is in focus
			IWorkbenchWindow window = Activator.getActiveWorkbenchWindow();
			if (window != null) {
				IWorkbenchPage page = window.getActivePage();
				if (page != null) {
					IWorkbenchPart activePart = page.getActivePart();
					if (activePart.getSite().getId().equals("com.nokia.carbide.cpp.mmpEditor.MMPEditor") || //$NON-NLS-1$
							activePart.getSite().getId().equals("org.eclipse.cdt.make.editor")) { //$NON-NLS-1$
						IEditorInput input = ((IEditorPart) activePart).getEditorInput();
						if (input instanceof FileEditorInput) {
							FileEditorInput editorInput = (FileEditorInput) input;
							selectedFiles.add(editorInput.getFile());
						}
					} else if (activePart instanceof IEditorPart) {
						// enable if there's a TU for the editor in focus
						IEditorPart epart = (IEditorPart) activePart;
						IResource resource = (IResource) epart.getEditorInput().getAdapter(IResource.class);
						ICElement element = CoreModel.getDefault().create(resource);
						if (element instanceof ITranslationUnit) {
							ITranslationUnit tu = (ITranslationUnit) element;
							if (tu.isSourceUnit() && tu.getResource() instanceof IFile) {
								IFile file = (IFile)tu.getResource();
								// this can be slow so we only do this when the command is invoked and not
								// to see if the command should be enabled
								if (lookupParentMMPs) {
									List<IPath> parentMMPs = EpocEngineHelper.getMMPsForSource(file.getProject(), file.getLocation());
									for (IPath mmp : parentMMPs) {
										IFile mmpFile = file.getProject().getFile(mmp);
										if (mmpFile != null && mmpFile.exists()) {
											if (!selectedFiles.contains(mmpFile)) {
												selectedFiles.add(mmpFile);
											}
										}
									}
								} else {
									selectedFiles.add(file);
								}
							}
						}
					}
				}
			}
		} else if (selection instanceof IStructuredSelection) {
			Iterator<?> iter = ((IStructuredSelection)selection).iterator();
			while (iter.hasNext()) {
				IFile file = null;
				Object selectionItem = iter.next();
				if (selectionItem instanceof IFile) {
					file = (IFile)selectionItem;
				} else if (selectionItem instanceof IAdaptable) {
					file = (IFile)((IAdaptable)selectionItem).getAdapter(IFile.class);
				}
				
				if (file != null) {
					String filename = file.getName();
					if (filename.toLowerCase().endsWith(".mmp") || filename.toLowerCase().endsWith(".mk")) { //$NON-NLS-1$ //$NON-NLS-2$
						selectedFiles.add(file);
					} else {
						// see if this is a source TU
						ITranslationUnit tu = CoreModelUtil.findTranslationUnit(file);
						if (tu != null && tu.isSourceUnit()) {
							// this can be slow so we only do this when the command is invoked and not
							// to see if the command should be enabled
							if (lookupParentMMPs) {
								List<IPath> parentMMPs = EpocEngineHelper.getMMPsForSource(file.getProject(), file.getLocation());
								for (IPath mmp : parentMMPs) {
									IFile mmpFile = file.getProject().getFile(mmp);
									if (mmpFile != null && mmpFile.exists()) {
										if (!selectedFiles.contains(mmpFile)) {
											selectedFiles.add(mmpFile);
										}
									}
								}
							} else {
								selectedFiles.add(file);
							}
						} else {
							selectedFiles.clear();
							return selectedFiles;
						}
					}
				} else {
					selectedFiles.clear();
					return selectedFiles;
				}
			}
		}
		
		return selectedFiles;
	}

	private void handleSelectedMMPComponentsAction(final List<IFile> selectedFiles, final int typeId) {
		
		if (selectedFiles.size() < 1) {
			return;
		}
		
		int actionType = 0;
		String jobName = ""; //$NON-NLS-1$
		
		switch(typeId) {
		case BUILD_ID:
			actionType = CarbideCPPBuilder.BUILD_COMPONENT_ACTION;
			jobName = "Building Selected Component(s)";
			break;
		case CLEAN_ID:
			actionType = CarbideCPPBuilder.CLEAN_COMPONENT_ACTION;
			jobName = "Cleaning Selected Component(s)";
			break;
		case FREEZE_ID:
			actionType = CarbideCPPBuilder.FREEZE_COMPONENT_ACTION;
			jobName = "Freezing Selected Component(s)";
			break;
		}
		
		final int finalActionType = actionType;

		Job buildJob = new Job(jobName) {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					SubMonitor subMonitor = SubMonitor.convert(monitor, selectedFiles.size());

					IProject lastProject = selectedFiles.get(0).getProject();
					ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(lastProject);
					String[] errorParserIds = cpi.getDefaultConfiguration().getErrorParserList();
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

					for (IFile file : selectedFiles) {
						IProject project = file.getProject();
						IPath componentPath = file.getLocation();
						
						if (project != null && componentPath != null) {
							
							final IPath finalComponentPath = componentPath;
							
							if (lastProject != project) {
					    		if (typeId == FREEZE_ID) {
									Activator.refreshProjectAfterFreeze(project);
					    		}

					    		lastProject = project;
								cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(lastProject);
								errorParserIds = cpi.getDefaultConfiguration().getErrorParserList();
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
								CarbideCPPBuilder.invokeSymbianComponenetAction(cpi.getDefaultConfiguration(), finalActionType, finalComponentPath, launcher, subMonitor.newChild(1), false);
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
				
		    		if (typeId == FREEZE_ID) {
						Activator.refreshProjectAfterFreeze(lastProject);
		    		}

				} finally {
					monitor.done();
				}
				return new Status(IStatus.OK, "Carbide.c++ builder utils plugin", IStatus.OK, "Complete", null); 
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
