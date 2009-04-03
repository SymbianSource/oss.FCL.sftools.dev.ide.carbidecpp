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
package com.nokia.carbide.cpp.internal.project.ui.images;

import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.ui.images.FileBasedImageContainerModelBase;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher.IResourceChangeHandler;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.progress.UIJob;

import java.util.ArrayList;
import java.util.List;

/**
 * Base for image container models that are hosted in editable files.  
 *
 */
public abstract class ModelFileBasedImageContainerModelBase extends FileBasedImageContainerModelBase implements IResourceChangeHandler {

	private IFile modelFile;
	private List<IPath> listenedPaths;

	/**
	 * 
	 */
	public ModelFileBasedImageContainerModelBase(IImageLoader imageLoader, IPath projectPath, IPath modelFile) {
		super(projectPath, imageLoader, modelFile);
		this.modelFile = modelPath != null ? FileUtils.convertFileToIFile(modelPath.toFile()) : null;
	}

	protected Job createModelFileEditingJob(Shell shell) {
		UIJob job = new UIJob(Messages.getString("ModelFileBasedImageContainerModelBase.EditingLabel") + modelPath.toOSString()) { //$NON-NLS-1$

			/* (non-Javadoc)
			 * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
			 */
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(Messages.getString("ModelFileBasedImageContainerModelBase.EditingLabel2"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
				
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
				if (activeWorkbenchWindow == null)
					activeWorkbenchWindow = workbench.getWorkbenchWindows()[0];
				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				IEditorRegistry registry = workbench.getEditorRegistry();
				
				IEditorDescriptor defaultEditor = registry.getDefaultEditor(modelPath.lastSegment());
				if (defaultEditor == null)
					return Status.CANCEL_STATUS;
				
				try {
					/*IEditorPart editor =*/ activePage.openEditor(new FileEditorInput(modelFile),
					        defaultEditor.getId(), true);
					
					// this might be cool but we should listen to the model file instead
					/*
					final Object waiter = new Object();
					
					activePage.addPartListener(new IPartListener() {

						public void partActivated(IWorkbenchPart part) {
						}

						public void partBroughtToTop(IWorkbenchPart part) {
						}

						public void partClosed(IWorkbenchPart part) {
							waiter.notify();
						}

						public void partDeactivated(IWorkbenchPart part) {
						}

						public void partOpened(IWorkbenchPart part) {
						}
						
					});
					
					try {
						waiter.wait();
					} catch (InterruptedException e) {
					}
					*/
					
					return Status.OK_STATUS;
					
				} catch (PartInitException e) {
					return e.getStatus();
				}		
			}
			
		};
		job.setUser(false);
		return job;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#createEditorProvider()
	 */
	public IImageContainerEditorProvider createEditorProvider() {
		if (modelFile == null)
			return null;
		
		return new IImageContainerEditorProvider() {

			public Job createEditingJob(Shell shell) {
				return createModelFileEditingJob(shell);
			}
			
		};
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.ImageContainerModelBase#addListener(com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModelListener)
	 */
	@Override
	public synchronized void addListener(IImageContainerModelListener listener) {
		if (modelPath != null) {
			if (listeners.size() == 0) {
				listenedPaths = new ArrayList<IPath>();
				IPath[] includedFiles = getModelIncludedFiles();
				for (IPath path : includedFiles) {
					IPath wsPath = FileUtils.convertToWorkspacePath(path);
					if (wsPath != null)
						listenedPaths.add(wsPath);
				}
				for (IPath path : listenedPaths) {
					EpocEnginePlugin.getMultiResourceChangeListenerDispatcher().addResource(
							path, this);
				}
			}
		}
		super.addListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.ImageContainerModelBase#removeListener(com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModelListener)
	 */
	@Override
	public synchronized void removeListener(
			IImageContainerModelListener listener) {
		super.removeListener(listener);
		if (modelPath != null) {
			if (listeners.size() == 0) {
				for (IPath path : listenedPaths) {
					EpocEnginePlugin.getMultiResourceChangeListenerDispatcher().removeResource(
							path, this);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.MultiResourceChangeListenerDispatcher.IResourceChangeHandler#resourceChanged(org.eclipse.core.runtime.IPath)
	 */
	public void resourceChanged(IPath workspacePath) {
		// tell listeners that we changed
		fireListeners();
	}

	/**
	 * Get the list of full paths to files that the model represents
	 * (including self)
	 * @return array, never null
	 */
	protected IPath[] getModelIncludedFiles() {
		List<IPath> paths = new ArrayList<IPath>();
		String fileExtension = FileUtils.getSafeFileExtension(modelPath);
		if (fileExtension.equalsIgnoreCase("mmp")) { //$NON-NLS-1$
			EpocEngineHelper.addIncludedFilesFromMMP(baseLocation, modelPath, paths);
		} else if (fileExtension.equalsIgnoreCase("inf")) { //$NON-NLS-1$
			EpocEngineHelper.addIncludedFilesFromBldInf(baseLocation, modelPath, paths);
		} else if (fileExtension.equalsIgnoreCase("mk")) { //$NON-NLS-1$
			// TODO: maybe later Makefile include paths
			paths.add(modelPath);
			//EpocEngineHelper.addIncludedFilesFromMakefile(projectPath, modelPath, paths);
		}
		return (IPath[]) paths.toArray(new IPath[paths.size()]);
	}

}