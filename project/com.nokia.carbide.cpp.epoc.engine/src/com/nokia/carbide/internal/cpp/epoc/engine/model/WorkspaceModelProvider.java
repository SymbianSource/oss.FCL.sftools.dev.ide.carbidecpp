/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IModelFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher.IResourceChangeHandler;
import com.nokia.sdt.utils.WorkspaceFileTracker;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the model provider implementation that understands workspace
 * semantics.  All paths are workspace-relative.
 *
 */
public class WorkspaceModelProvider<Model, SharedModel> extends ModelProviderBase implements IResourceChangeHandler {

	private WorkspaceFileTracker fileHandler;
	private Map<IPath, Long> externalFileModDateMap;
	
	public WorkspaceModelProvider(IModelFactory modelFactory) {
		super(modelFactory);
		Check.checkState(Platform.isRunning());
		this.fileHandler = new WorkspaceFileTracker();
		this.externalFileModDateMap = new HashMap<IPath, Long>();
		
	}

	/**
	 * @param path
	 * @return
	 */
	private File getAbsoluteFileForPath(IPath path) {
		if (path.segmentCount() <= 1)
			return null;
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		if (resource == null || resource.getLocation() == null)
			return null;
		// get the real file if a linked resource
		File file;
		IPath rawLocation = resource.getRawLocation();
		if (rawLocation != null)
			file = rawLocation.toFile();
		else
			file = resource.getLocation().toFile();
		return file;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#getCanonicalPath(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected IPath getFullPath(IPath path) {
		File file;
		
		// oops, clients aren't following the "workspace-relative path" rule all the
		// time; this seems like a good way to tell the difference.
		if (path.getDevice() != null) {
			file = new File(path.toOSString());
		} else {
			file = getAbsoluteFileForPath(path);
			if (file == null)
				return path;
		}
		try {
			return new Path(file.getCanonicalPath());
		} catch (IOException e) {
			EpocEnginePlugin.log(e);
			return new Path(file.getAbsolutePath());
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#loadDocument(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected String loadStorage(IPath fullPath) throws CoreException {
		File file = fullPath.toFile();
		if (file != null && file.exists()) {
			externalFileModDateMap.put(fullPath, file.lastModified());
			char[] text = fileHandler.loadFileText(file);
			return new String(text);
		} else {
			externalFileModDateMap.remove(fullPath);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#saveDocument(org.eclipse.core.runtime.IPath, org.eclipse.jface.text.IDocument)
	 */
	@Override
	protected void saveStorage(IPath fullPath, final String text)
			throws CoreException {
		final File file = fullPath.toFile();
		if (file != null) {
			// get current contents and ignore save if nothing changed
			try {
				char[] currentChars = fileHandler.loadFileText(file);
				if (Arrays.equals(currentChars, text.toCharArray())) {
					return;
				}
			} catch (CoreException e) {
				// ignore
			}

			final char[] chars = text.toCharArray();
			
        	// back up file if outside workspace
			IPath wsPath = FileUtils.convertToWorkspacePath(fullPath, false);
			if (wsPath == null) {
	        	File backup = new Path(file.getAbsolutePath()).removeFileExtension().addFileExtension("bak").toFile(); //$NON-NLS-1$
	        	backup.delete();

	        	// copy it rather than rename it, so the original is always there.  otherwise saveFileText will fail since the original
	        	// resource would be missing
	        	if (file.exists()) {
		        	try {
						FileUtils.copyFile(file, backup);
					} catch (IOException e) {
						EpocEnginePlugin.log(e);
					}
	        	}
	        	
				fileHandler.saveFileText(file, null, chars);
				externalFileModDateMap.put(fullPath, file.lastModified());
			} else {
				// we can't be sure we have rights to modify the workspace
				// (e.g. might be triggered through a resource listener), so do this in a job
				//
				// also, due to boog 4636, a resource which exists in multiple
				// projects may be saved under a different scheduling rule;
				// we need to trap this and try again in a job if necessary.
				
				boolean retryInJob = false;
				if (ResourcesPlugin.getWorkspace().isTreeLocked()) {
					retryInJob = true;
				} else {
					try {
						fileHandler.saveFileText(file, null, chars);
					} catch (IllegalArgumentException e) {
						// too bad there is not a more specific error :(
						retryInJob = true;
					}
				}
				
				if (retryInJob) {
					WorkspaceJob job = new WorkspaceJob(Messages.getString("WorkspaceModelProvider.SavingModelFile.TaskLabel")) { //$NON-NLS-1$
	
						@Override
						public IStatus runInWorkspace(IProgressMonitor monitor)
								throws CoreException {
							fileHandler.saveFileText(file, null, chars);
							return Status.OK_STATUS;
						}
					};
					// note: because character sets might be changed, the rule is really the project
					job.setRule(ResourcesPlugin.getWorkspace().getRoot().getProject(wsPath.segment(0)));
					job.setPriority(WorkspaceJob.INTERACTIVE);
					job.schedule();
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#startTrackingStorage(org.eclipse.core.runtime.IPath)
	 */
	protected void startTrackingStorage(IPath fullPath) {
		// the path is canonical already
		IPath wsPath = FileUtils.convertToWorkspacePath(fullPath, false);
		if (wsPath == null) {
			externalFileModDateMap.put(fullPath, fullPath.toFile().lastModified());
		} else {
			externalFileModDateMap.remove(fullPath);
			EpocEnginePlugin.getMultiResourceChangeListenerDispatcher().addResource(wsPath, this);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#stopTrackingStorage(org.eclipse.core.runtime.IPath)
	 */
	protected void stopTrackingStorage(IPath fullPath) {
		// remove stragglers if they were originally external and now workspace files
		externalFileModDateMap.remove(fullPath);
		IPath wsPath = FileUtils.convertToWorkspacePath(fullPath, false);
		if (wsPath != null) {
			EpocEnginePlugin.getMultiResourceChangeListenerDispatcher().removeResource(wsPath, this);
		}
	}
	
	@Override
	protected boolean hasTrackedStorageChanged(IPath fullPath) {
		Long prevTime = externalFileModDateMap.get(fullPath);
		if (prevTime == null) {
			IFile wsFile = FileUtils.convertFileToIFile(fullPath.toFile());
			// boog 4045: Eclipse may still give us a "workspace file" for something no
			// longer visible in the workspace (e.g. due to its project being deleted)! 
			// But we can tell if the IFile doesn't "exist".
			if (wsFile == null || !wsFile.exists()) {
				// we don't even know about it: e.g., cleanly registered file
				return true;
			}
			// if a workspace file, we're already tracking it
			return false;
		} else {
			// originally an external file (tho it may possibly now be a workspace file)
			if (prevTime.longValue() != fullPath.toFile().lastModified()) {
				return true;
			}
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.cpp.internal.api.utils.core.*
	 */
	public void resourceChanged(IPath workspacePath) {
		fireStorageChanged(getFullPath(workspacePath));
	}
}
