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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import java.text.MessageFormat;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.undo.DocumentUndoManagerRegistry;
import org.eclipse.text.undo.IDocumentUndoManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension;
import org.eclipse.ui.texteditor.IDocumentProviderExtension3;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

public class CarbideTextEditor extends TextEditor {
	
	private static TextFileDocumentProvider documentProvider = new TextFileDocumentProvider();
	private final CarbideFormEditorContext editorContext;
	private long fModificationStamp = -1;

	public CarbideTextEditor(CarbideFormEditorContext editorContext) {
		this.editorContext = editorContext;
		setDocumentProvider(documentProvider);
	}

	/**
	 * Checks the state of the editor input.
	 */
	public void checkEditorInput() {
		safelySanityCheckState(getEditorInput());
	}

	/**
	 * Create action(s) specific to this editor.
	 */
	protected void createActions() {
		super.createActions();
		IAction saveAction = ActionFactory.SAVE.create(editorContext.editor.getSite().getWorkbenchWindow());
		saveAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.SAVE);
		setAction(ITextEditorActionConstants.SAVE, saveAction);
	}

	public static IDocumentProvider documentProvider() {
		return documentProvider;
	}
	
	public static IDocumentUndoManager getUndoManager(Object input) {
		IDocument document = documentProvider.getDocument(input);
		return DocumentUndoManagerRegistry.getDocumentUndoManager(document);
	}
	
	@Override
	protected void handleEditorInputChanged() {
		// We might reload the editor in response to this. That would dispose 
		// the current instance, so we need to do it async, as code on the call stack
		// isn't prepared for the instance to be disposed.
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				editorContext.editor.handleResourceChanged();
			}
		});
	}

	/**
	 * Handle case where editor input is out-of-sync with the file system.
	 */
	protected void handleOutofSync() {
		String title;
		String msg;
		Shell shell = getSite().getShell();

		final IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			close(false);
			return;
		}

		final IEditorInput input = getEditorInput();
		try {
			if (provider instanceof IDocumentProviderExtension) {
				IDocumentProviderExtension extension = (IDocumentProviderExtension) provider;
				extension.synchronize(input);
			} else {
				doSetInput(input);
			}
		} catch (CoreException x) {
			IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				title = Messages.CarbideTextEditor_syncErrorDialogTitle;
				msg = Messages.CarbideTextEditor_syncErrorDialogMessage;
				ErrorDialog.openError(shell, title, msg, x.getStatus());
			}
		}
	}

	/**
	 * This implementation uses CarbideFormEditor to track deleted resource. 
	 *
	 * @param progressMonitor the progress monitor to be used
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		final IEditorInput input = getEditorInput();

		IDocumentProvider provider = getDocumentProvider();
		final IEditorInput newInput;
		
		if ((input instanceof IURIEditorInput) && !(input instanceof IFileEditorInput)) {
			super.performSaveAs(progressMonitor);
		} else {
			SaveAsDialog dialog = new SaveAsDialog(shell);

			IFile original = (input instanceof IFileEditorInput) ? ((IFileEditorInput) input).getFile() : null;
			if (original != null)
				dialog.setOriginalFile(original);

			dialog.create();

			if (editorContext.editor.isResourceDeleted() && original != null) {
				String message= MessageFormat.format(Messages.CarbideTextEditor_warning_saveAs_deleted, original.getName());
				dialog.setErrorMessage(null);
				dialog.setMessage(message, IMessageProvider.WARNING);
			}
			else {
				dialog.setErrorMessage(null);
				dialog.setMessage(Messages.CarbideTextEditor_saveAs_message, IMessageProvider.WARNING);
			}

			if (dialog.open() == Window.CANCEL) {
				if (progressMonitor != null)
					progressMonitor.setCanceled(true);
				return;
			}
			
			IPath filePath= dialog.getResult();
			if (filePath == null) {
				if (progressMonitor != null)
					progressMonitor.setCanceled(true);
				return;
			}
			
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IFile file = workspace.getRoot().getFile(filePath);
			newInput = new FileEditorInput(file);

			if (provider == null) {
				return;
			}

			boolean success = false;
			try {

				provider.aboutToChange(newInput);
				provider.saveDocument(progressMonitor, newInput, provider.getDocument(input), true);
				success= true;

			} catch (CoreException e) {
				final IStatus status = e.getStatus();
				if (status == null || status.getSeverity() != IStatus.CANCEL) {
					String title = Messages.CarbideTextEditor_error_saveAs_title;
					String msg = MessageFormat.format(Messages.CarbideTextEditor_error_saveAs_message, e.getMessage());
					MessageDialog.openError(shell, title, msg);
				}
			} finally {
				provider.changed(newInput);
				if (success)
					setInput(newInput);
			}

			if (progressMonitor != null)
				progressMonitor.setCanceled(!success);
		}		
	}

	/**
	 * Checks the state of the given editor input.
	 * @param input the editor input whose state is to be checked
	 */
	protected void sanityCheckState(IEditorInput input) {
		IDocumentProvider p = getDocumentProvider();
		if (p == null)
			return;

		if (p instanceof IDocumentProviderExtension3) {
			IDocumentProviderExtension3 p3 = (IDocumentProviderExtension3) p;
			long stamp = p.getModificationStamp(input);
			if (stamp != fModificationStamp) {
				fModificationStamp = stamp;
				if (!p3.isSynchronized(input))
					handleOutofSync();
			}
		} else {
			if (fModificationStamp == -1)
				fModificationStamp = p.getSynchronizationStamp(input);

			long stamp = p.getModificationStamp(input);
			if (stamp != fModificationStamp) {
				fModificationStamp = stamp;
				if (stamp != p.getSynchronizationStamp(input))
					handleOutofSync();
			}
		}

		updateState(getEditorInput());
		updateStatusField(ITextEditorActionConstants.STATUS_CATEGORY_ELEMENT_STATE);
	}

	/**
	 * Eclipse's document and file buffer architecture monitor resource changes
	 * and will automatically reload documents when it thinks the in-memory document
	 * has no changes. In our usage we may have changes pending in the view while
	 * the document and file buffer are not yet modified.
	 * To defeat this auto-reloading behavior we mark the buffer as dirty. Then when
	 * a resource change is detected the reload is inhibited. No other UI changes occur, since
	 * more generally dirty state is handled at the document/editor level.
	 */
	public void setBufferDirty(boolean isDirty) {
		ITextFileBufferManager textFileBufferManager = FileBuffers.getTextFileBufferManager();
		IFile file = ResourceUtil.getFile(getEditorInput());
		if (file != null) {
			ITextFileBuffer textFileBuffer = textFileBufferManager.getTextFileBuffer(file.getFullPath(), LocationKind.IFILE);
			textFileBuffer.setDirty(isDirty);
		}
	}
}
