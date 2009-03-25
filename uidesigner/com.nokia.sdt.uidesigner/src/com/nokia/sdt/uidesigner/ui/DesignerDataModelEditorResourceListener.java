/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.UITaskUtils;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerDataModelEditor.SaveListener;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.FileEditorInput;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

public class DesignerDataModelEditorResourceListener implements IResourceChangeListener, SaveListener {
	private IEditorPart editorPart;
	private boolean isSaving;
	private ResourceDeltaVisitor visitor;
	private ActivationListener activationListener;
	private long savedTimeStamp;
	
	class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		public boolean visit(IResourceDelta delta) {
			// if it's not our file, accept
			if ((delta == null) || !getFile().equals(delta.getResource()))
				return true;
	
			if (delta.getKind() == IResourceDelta.REMOVED) { // the file was removed or renamed
				if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0)
					handleFileRenamed(delta.getMovedToPath());
				else
					handleFileRemoved(false);
			} 
			else if ((delta.getKind() == IResourceDelta.CHANGED) &&
					((delta.getFlags() & IResourceDelta.CONTENT) != 0)) { // the file was modified
				handleFileChanged();
			}
			return false;
		}
	}
	
	class ActivationListener implements IPartListener, IWindowListener {
		
		public void partActivated(IWorkbenchPart part) {
			handleActivation(part);
		}

		public void partClosed(IWorkbenchPart part) {
			if (part.equals(getEditorPart())) {
				getEditorPart().getSite().getPage().removePartListener(activationListener);
				getEditorPart().getSite().getWorkbenchWindow().getWorkbench().removeWindowListener(activationListener);
				activationListener = null;
			}
		}

		public void windowActivated(IWorkbenchWindow window) {
			handleActivation(null);
		}
		
		private void handleActivation(IWorkbenchPart part) {
			if ((part == null) || part.equals(getEditorPart())) {
				handlePossibleTimeStampChanged(getTimeStamp(getFile()));
			}
		}

		public void partDeactivated(IWorkbenchPart part) {}
		public void partOpened(IWorkbenchPart part) {}
		public void partBroughtToTop(IWorkbenchPart part) {}
		public void windowDeactivated(IWorkbenchWindow window) {}
		public void windowClosed(IWorkbenchWindow window) {}
		public void windowOpened(IWorkbenchWindow window) {}
	}
		
	public DesignerDataModelEditorResourceListener(IEditorPart editorPart) {
		this.editorPart = editorPart;
		visitor = new ResourceDeltaVisitor();
		IFile file = getFile();
		savedTimeStamp = getTimeStamp(file);
		file.getWorkspace().addResourceChangeListener(this);
		getEditorPart().getSite().getPage().addPartListener(activationListener = new ActivationListener());
		getEditorPart().getSite().getWorkbenchWindow().getWorkbench().addWindowListener(activationListener);
		IDesignerDataModelEditor editor = getDesignerEditor();
		if (editor != null) {
			editor.addSaveListener(this);
		}
	}
	
	protected void handlePossibleTimeStampChanged(long newTimeStamp) {
		if (savedTimeStamp != newTimeStamp) {
			if (newTimeStamp == 0)
				handleFileRemoved(true);
			else
				handleFileChanged();
			savedTimeStamp = newTimeStamp;
		}
	}

	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		try {
			if (delta != null)
				delta.accept(visitor);
		} 
		catch (CoreException e) {
			IStatus status = Logging.newStatus(UIDesignerPlugin.getDefault(), e);
			Logging.log(UIDesignerPlugin.getDefault(), status);
			Logging.showErrorDialog(null, Strings.getString("DesignerEditorResourceListener.FileError"), status); //$NON-NLS-1$
		}
	}
	

	protected void handleFileRenamed(IPath newPath) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(newPath);
		final FileEditorInput fileEditorInput = new FileEditorInput(file);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				getDesignerEditor().setInput(fileEditorInput);
			}
		});
	}

	protected void handleFileRemoved(final boolean alwaysAsk) {
		synchronized (this) {
			if (isSaving)
				return;
			
			// This can safely be set this here, because the only possible outcome
			// is saving or closing the editor. In either case, no other questions need be asked.
			isSaving = true; 
		}
		
		WorkspaceJob wsJob = new WorkspaceJob("") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						boolean hasProject = false;
						IFile file = getFile();
						if (file != null) {
							IProject project = file.getProject();
							hasProject = (project != null) && project.exists();
						}
						if (hasProject && (alwaysAsk || getEditorPart().isDirty())) {
							String messageFmt = Strings.getString("DesignerEditorResourceListener.DeletedQuestion"); //$NON-NLS-1$
							String message = MessageFormat.format(messageFmt, new Object[] { getEditorPart().getTitle() } );
							if (MessageDialog.openQuestion(getEditorPart().getSite().getShell(),  
									Strings.getString("DesignerEditorResourceListener.DeletedTitle"), message)) { //$NON-NLS-1$
								saveEditor();
							}
							else
								closeEditor();
						}
						else
							closeEditor();
					}
				});
				
				return Status.OK_STATUS;
			}
		};
		wsJob.setRule(getFile().getProject());
		wsJob.setPriority(WorkspaceContext.QUERY_SAVE_CHANGES_ON_DELETE_PRIORITY);
		wsJob.setUser(false);
		wsJob.setSystem(true);
		wsJob.schedule();
	}
	
	protected void handleFileChanged() {
		if (fileHasSavedTimeStamp())
			return;
		
		synchronized (this) {
			if (isSaving)
				return;
		}
		
		getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (hasExistingInput() && askWantsLoad())
					reloadEditor();
			}
		});
	}
	
	private boolean fileHasSavedTimeStamp() {
		// checking for late notifications from Eclipse
		return getTimeStamp(getFile()) == savedTimeStamp;
	}

	protected boolean hasExistingInput() {
		return getEditorPart().getEditorInput().exists();
	}

	private boolean askWantsLoad() {
		String messageFmt = Strings.getString("DesignerEditorResourceListener.ChangedQuestion"); //$NON-NLS-1$
		String message = MessageFormat.format(messageFmt, new Object[] { getEditorPart().getTitle() } );
		if (getEditorPart().isDirty())
			message += Strings.getString("DesignerEditorResourceListener.ChangedQuestionUnsaved"); //$NON-NLS-1$
		return MessageDialog.openQuestion(getEditorPart().getSite().getShell(),  
				Strings.getString("DesignerEditorResourceListener.ChangedTitle"), message); //$NON-NLS-1$
	}
	
	private IFile getFile() {
		return ResourceUtil.getFile(getEditorPart().getEditorInput());
	}
	
	private long getTimeStamp(IFile file) {
		// be extra careful since this can be called after a file is deleted
		return (file != null && file.exists() && file.getRawLocation() != null) 
			? file.getRawLocation().toFile().lastModified() : 0;
	}
	
	private void closeEditor() {
		// flag really means "confirm" not "save"
		getEditorPart().getSite().getPage().closeEditor(getEditorPart(), false); 
	}
	
	protected void saveEditor() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
            	getEditorPart().doSave(monitor);
            	reloadEditor();
			}
		};
		
		UITaskUtils.runImmediately(runnable);
	}

	private void reloadEditor() {
		getDesignerEditor().reload();
	}
	
	private IEditorPart getEditorPart() {
		return editorPart;
	}
	
	private IDesignerDataModelEditor getDesignerEditor() {
		return (IDesignerDataModelEditor) getEditorPart().getAdapter(IDesignerDataModelEditor.class);
	}
	
	private Display getDisplay() {
		return getEditorPart().getSite().getShell().getDisplay();
	}

	public boolean queryAboutToSave(IDesignerDataModelEditor editor) {
		return true; // no veto
	}
	
	public void preSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor) {
		synchronized (this) {
			isSaving = true;
		}
	}

	public void postSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor) {
		synchronized (this) {
			savedTimeStamp = getTimeStamp(getFile());
			isSaving = false;
		}
	}

	public void dispose() {
		getFile().getWorkspace().removeResourceChangeListener(this);
		getDesignerEditor().removeSaveListener(this);
	}
}
