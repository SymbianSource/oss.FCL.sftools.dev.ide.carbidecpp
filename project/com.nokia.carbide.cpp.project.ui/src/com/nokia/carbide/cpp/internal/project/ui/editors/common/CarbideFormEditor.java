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

import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.ControlVisitor;
import com.nokia.cpp.internal.api.utils.ui.editor.EditingContextOperation;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;
import com.nokia.sdt.utils.WorkspaceFileTracker;

import org.eclipse.core.commands.operations.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.search.ui.text.ISearchEditorAccess;
import org.eclipse.search.ui.text.Match;
import org.eclipse.swt.widgets.*;
import org.eclipse.text.undo.IDocumentUndoManager;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.progress.WorkbenchJob;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import java.io.File;
import java.text.MessageFormat;
import java.util.Map;

public abstract class CarbideFormEditor extends FormEditor 
										implements IGotoMarker, TrackedResource.IListener {

	protected CarbideFormEditorContext baseEditorContext;
	protected TrackedResource editorInputTracker;
	protected CarbideTextEditor textEditorPage;
	protected int textEditorPageIndex;
	private IEditorPart lastActivePage;
	private ActivationListener activationListener;
	private boolean resourceDeleted;
	
	// top of the undo stack at the last save, null if never saved
	private IUndoableOperation saveOperationMarker;
	private IOperationHistoryListener opHistoryListener;
	boolean committingView;
	
	final QualifiedName activePagePropertyName = new QualifiedName(ProjectUIPlugin.getDefault().toString(), "activePage"); //$NON-NLS-1$

	/**
	 * A class to track activation of this editor.
	 * 
	 */
	class ActivationListener implements IPartListener {
		public void partActivated(IWorkbenchPart part) {
			if (part != null && part.equals(CarbideFormEditor.this)) {
				textEditorPage.checkEditorInput();
				if (resourceDeleted) {
					handleResourceDeleted();
				}
			}
		}
		
		public void partDeactivated(IWorkbenchPart part) {}
		public void partBroughtToTop(IWorkbenchPart part) {}
		public void partOpened(IWorkbenchPart part) {}
		public void partClosed(IWorkbenchPart part) {}
	}
	
	public CarbideFormEditor() {
		super();
	}
	
	/**
	 * Initialize basic fields of CarbideFormEditorContext. Subclasses
	 * may be used an extended type with additional fields.
	 * @param baseEditorContext
	 */
	protected void initializeEditorContext(CarbideFormEditorContext baseEditorContext) {
		this.baseEditorContext = baseEditorContext;
		baseEditorContext.editor = this;
		baseEditorContext.operationHistory = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();		
		baseEditorContext.modelOperationContext = new ObjectUndoContext(new ContextKey("MODEL")); //$NON-NLS-1$
		baseEditorContext.allContext = new ObjectUndoContext(new ContextKey("ALL")); //$NON-NLS-1$
		baseEditorContext.allContext.addMatch(baseEditorContext.modelOperationContext);

		opHistoryListener = new OperationHistoryListener(this);
		baseEditorContext.operationHistory.addOperationHistoryListener(opHistoryListener);	
	}
	
	// helper for IUndoContext: doesn't compare equal with equal keys as a String would, 
	// but lets us see the context easily in the debugger
	static class ContextKey {
		String s;
		ContextKey(String s) {
			this.s = s;
		}
		public String toString() {
			return s;
		}
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		super.init(site, editorInput);
		activationListener = new ActivationListener();
		getSite().getPage().addPartListener(activationListener);
		resourceDeleted = false;
	}
	
	public void dispose() {		
		super.dispose();
		baseEditorContext.operationHistory.removeOperationHistoryListener(opHistoryListener);
		if (editorInputTracker != null) {
			editorInputTracker.removeListener(this);
		}
		
		if (baseEditorContext != null) {
			baseEditorContext.dispose();
			baseEditorContext = null;
		}
		if (activationListener != null) {
			getSite().getPage().removePartListener(activationListener);
			activationListener = null;
		}
	}
	
	public abstract void operationHistoryChanged();
		
	protected abstract IView getView();
	/**
	 * Revert the view state to that of the text document
	 */
	protected abstract void revertView();
	
	protected abstract void refreshPages();
	
	/**
	 * Called when the file has changed outside the editor, the editor
	 * is dirty, but the user choose not to reload the editor.
	 *
	 */
	protected abstract void reloadRejected();

	/** Get the IDocument instances loaded in the model which are not the main document.
	 * For now, this editor only directly deals with the primary document. 
	 * @return collection of IDocuments for the current model
	 */
	protected abstract Map<IPath, IDocument> getIncludedDocumentMap();
	
	public CarbideFormEditorContext getBaseEditorContext() {
		return baseEditorContext;
	}
	
	protected void createTextEditorPage() throws PartInitException {
		textEditorPage = new CarbideTextEditor(baseEditorContext);
		textEditorPageIndex = addPage(textEditorPage, getEditorInput());
		setPageText(textEditorPageIndex, textEditorPage.getTitle());
		
		// Get the IUndoContext used by the text document, and configure
		// our editors undo context to match it. This unifies the undo stacks
		IDocumentUndoManager undoManager = CarbideTextEditor.getUndoManager(getEditorInput());
		baseEditorContext.textEditorContext = undoManager.getUndoContext();
		baseEditorContext.allContext.addMatch(baseEditorContext.textEditorContext);
		baseEditorContext.modelOperationContext.addMatch(baseEditorContext.textEditorContext);
	}
	
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		for (int i = 0; i < getPageCount(); i++) {
			IEditorPart part = getEditor(i);
			if (part instanceof IFormPart) {
				((IFormPart)part).setFormInput(input);
			}
		}
	}
	
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		setActivePageProperty(Integer.valueOf(newPageIndex));
		handlePageChanged(newPageIndex);
	}
	
	@Override
	public boolean isDirty() {
		if (baseEditorContext == null) {
			return false;
		}
		boolean result = false;
		IUndoableOperation[] history = baseEditorContext.operationHistory.getUndoHistory(
				baseEditorContext.allContext);
		if (history != null && history.length > 0) {
			if (saveOperationMarker != null) {
				int index = -1;
				for (int i = 0; i < history.length; i++) {
					if (history[i] == saveOperationMarker) {
						index = i;
						break;
					}
				}
				result = index != history.length - 1;
			} else {
				result = true;
			}
		} else if (saveOperationMarker != null) {
			// there's no undoable operations, but we have a reference to the last saved operation. The
			// user must have undone everything.
			result = true;
		}
		// mark the buffer dirty to inhibit auto-reload. See comments on setBufferDirty
		textEditorPage.setBufferDirty(result);
		return result;
	}
	
	protected void applyActivePageProperty() {
		IFile file = getInputFile();
		if (file != null) {		
			try {
				Object pageIndexObj = file.getSessionProperty(activePagePropertyName);
				if (pageIndexObj instanceof Integer) {
					int index = ((Integer)pageIndexObj).intValue();
					setActivePage(index);
					handlePageChanged(index);
				}
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}	
		}
	}
	
	protected void setActivePageProperty(Integer value) {
		IFile file = getInputFile();
		if (file != null) {
			try {
				file.setSessionProperty(activePagePropertyName, value);
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}
		}
	}

	/** 
	 * Every command initiated from a form page
	 * has a form editing context. Commands from the
	 * text editor page will not. Form editing commands 
	 * restore their own UI context. For the text editor we
	 * handle that here.
	 */
	public void respondToTextEditingUndoRedo(IUndoableOperation op) {
		if (op.hasContext(baseEditorContext.textEditorContext) &&
			!(EditingContextOperation.getEditingContextForOperation(op) instanceof FormEditorEditingContext) &&
			!baseEditorContext.viewCommitTextOps.contains(op)) {
			if (getActiveEditor() != textEditorPage) {
				getSite().getShell().getDisplay().syncExec(new Runnable() {
					public void run() {
						revertView();
						setActivePage(textEditorPageIndex);
					}
				});
			}
		}
	}

	public void resourceChanged(TrackedResource resource) {
		enableInputTracking(false);
		WorkbenchJob job = new WorkbenchJob("") { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				handleResourceChanged();
				enableInputTracking(true);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	void handleResourceChanged() {
		// The file has changed. If this editor is clean then simply
		// reload, otherwise ask the user if they want to reload first.
		boolean doReload;
		if (isDirty()) {
			String fmt = Messages.CarbideFormEditor_reloadOnChangedFilePrompt;
			String msg = MessageFormat.format(fmt, getEditorInput().getName());
			String btnLabels[] = {Messages.CarbideFormEditor_yesButtonLabel, Messages.CarbideFormEditor_noButtonLabel};
			MessageDialog dialog = new MessageDialog(this.getSite().getShell(), 
					Messages.CarbideFormEditor_reloadOnChangedFileDialogTitle, null, msg, MessageDialog.QUESTION, btnLabels, 1);
			doReload = Dialog.OK == dialog.open();
		} else {
			doReload = true;
		}
		
		if (doReload) {
			reload();
		} else {
			reloadRejected();
		}
	}
	
	public void resourceDeleted(TrackedResource resource) {
		WorkbenchJob job = new WorkbenchJob("") { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				// if the editor is dirty, instead of closing the editor 
				// immediately, we set a flag and wait for the editor 
				// to be activated before handling this
				if (isDirty()) {
					resourceDeleted = true;
				}
				else {
					getSite().getPage().closeEditor(CarbideFormEditor.this, false);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	public boolean isResourceDeleted() {
		return resourceDeleted;
	}

	/**
	 * If the file has been deleted, and the editor is dirty,
	 * offer to save the file or close the editor.
	 */
	protected void handleResourceDeleted() {
		// offer user the choice of saving the file or closing the editor
		boolean doSaveAs = false;
		if (isDirty()) {
			MessageDialog dialog = new MessageDialog (
					getSite().getShell(),
					Messages.CarbideFormEditor_saveResourceTitle,
					null,
					Messages.CarbideFormEditor_saveResourceMessage,
					MessageDialog.INFORMATION,
					new String[] {Messages.CarbideFormEditor_saveResourceLabel, IDialogConstants.CLOSE_LABEL},
					0);
			if (dialog.open() == 0) {
				doSaveAs = true;
				resourceDeleted = false;
			}
		}

		// save the file or close the editor at the next reasonable opportunity
		final boolean saveAs = doSaveAs;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (saveAs)
					doSaveAs();
				else
					getSite().getPage().closeEditor(CarbideFormEditor.this, false);
			}
		});
	}

	public void resourceMoved(TrackedResource resource, IPath oldPath) {
	}
	
	public boolean enableInputTracking(boolean enabled) {
		boolean result = false;
		// we only track if the input is an IFile
		if (editorInputTracker != null) {
			result = editorInputTracker.enableTracking(enabled);
		}
		return result;
	}

	public void reload() {
		String editorID = getEditorSite().getId();
		// close the editor without saving
		getSite().getPage().closeEditor(this, false);
		
		try {
			// open the editor again
			IEditorInput input = getEditorInput();
			if (input.exists())
				getSite().getPage().openEditor(input, editorID);
		}
		catch (PartInitException x) {
			ProjectUIPlugin.log(x);
		}
	}
	
	protected void commitView() {
		try {
			committingView = true;
			IView view = getView();
			if (view != null) {
				view.commit();
			}
		} finally {
			committingView = false;
		}	
	}
	
	/**
	 * Update the text document from the in-memory model
	 * Committing the mmp view will create undoable text
	 * actions. We don't want those to appear in the UI
	 * as actions the user can undo, but we do need to 
	 * preserve them. Undoing one or more GUI operations
	 * will not restore the text to the prior state. But
	 * text operations will refer to offsets from that 
	 * prior state. So each time we commit the mmp text
	 * we attach the text operations to the topmost GUI
	 * operation on the undo stack.
	 * If there is no GUI operation on the stack then
	 * we don't need to worry about invalidating textual
	 * undo, so in that case we can remove the text operations
	 * entirely.
	 */
	protected void updateTextDocument() {
		IDocumentUndoManager undoManager = CarbideTextEditor.getUndoManager(getEditorInput());
		// record previous top of the undo stack, could be be null
		IUndoableOperation prevUndoOp = baseEditorContext.operationHistory.getUndoOperation(baseEditorContext.allContext);
		if (prevUndoOp == null) {
			// no changes to commit
			return;
		}
		Exception caughtException = null;
		try {
			undoManager.beginCompoundChange();
			commitView();
			textEditorPage.selectAndReveal(0, 0);
		}
		catch (IllegalStateException x) {
			caughtException = x;		
		}
		catch (IllegalArgumentException x) {
			caughtException = x;
		}
		
		undoManager.endCompoundChange();
		undoManager.commit();
		
		if (caughtException != null) {
			String fmt = Messages.CarbideFormEditor_updatingDocError;
			String msg = MessageFormat.format(fmt, getEditorInput().getName());
			baseEditorContext.logAndDisplayError(msg, caughtException);		
		}
		
		// record any undo operations added by updating the mmp text
		IUndoableOperation[] undoHistory = baseEditorContext.operationHistory.getUndoHistory(baseEditorContext.allContext);
		for (int i = undoHistory.length - 1; i >= 0; --i) {
			IUndoableOperation op = undoHistory[i];
			if (op == prevUndoOp) {
				break;
			}
			// These operations remain in the history, but we will transparent
			// undo/redo them along with the desired user operations. To
			// make this fiction work well we set the label to the nearest
			// real operation
			if (op instanceof AbstractOperation) {
				AbstractOperation aop = (AbstractOperation) op;
				aop.setLabel(prevUndoOp.getLabel());
			}
			baseEditorContext.viewCommitTextOps.add(op);
		}
		// after we've fiddled with the operation history double-check that
		// the text document's undo manager is flushed.
//		undoManager.commit();		
	}
	
	public void doSave(IProgressMonitor monitor) {
		// handle case where file has already been removed from workspace when trying to save
		// fix for bug 4947 and 4699
		if (resourceDeleted) {
			doSaveAs();
			return;
		}
		
		if (getActiveEditor() != textEditorPage) {
			updateTextDocument();
		}
		
		enableInputTracking(false);
		
		textEditorPage.doSave(monitor);
		
		// bug 3725 - commit current undo so a new contiguous text change will dirty the document
		IDocumentUndoManager undoManager = CarbideTextEditor.getUndoManager(getEditorInput());
		undoManager.commit();
		
		// iterate other documents used by model and save them as well
		// TODO: so far, these changes cannot be undone directly
		WorkspaceFileTracker fileTracker = new WorkspaceFileTracker();
		Map<IPath, IDocument> map = getIncludedDocumentMap();
		if (map != null) {
			for (Map.Entry<IPath, IDocument> entry : map.entrySet()) {
				try {
					// bug 5058: don't save unmodified include files
					File file = entry.getKey().toFile();
					String text = entry.getValue().get();
					String contents = new String(fileTracker.loadFileText(file));
					if (contents.equals(text))
						continue;
					
					try {
						fileTracker.saveFileText(file, null, text.toCharArray());
					} catch (CoreException e) {
						String fmt = "Failed to save #include file ''{0}''";
						String msg = MessageFormat.format(fmt, entry.getKey().toOSString());
						baseEditorContext.logAndDisplayError(msg, e);		
					}

				} catch (CoreException e) {
					String fmt = "Failed to reload #include file ''{0}''";
					String msg = MessageFormat.format(fmt, entry.getKey().toOSString());
					baseEditorContext.logAndDisplayError(msg, e);	
				}
			}
		}
		
		enableInputTracking(true);
		IUndoableOperation undoOp = baseEditorContext.operationHistory.getUndoOperation(baseEditorContext.allContext);
		saveOperationMarker = undoOp;
		editorDirtyStateChanged();
		
		// If we're on the text editor page and the user has manually fixed
		// problems then the problem markers should update now. We have to revert
		// the view to get the up-to-date messages
		if (getActiveEditor() == textEditorPage) {
			revertView();
		} else {
			// committing the view invalidates cached objects, so we must
			// refresh the UI
			refreshPages();
		}
		updateProblemMarkers();		
	}
	
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = textEditorPage;
		editor.doSaveAs();
		IEditorInput input = editor.getEditorInput();
		setInput(input);
		setActivePageProperty(getActivePage());
		// TODO: this is a late fix in 1.2. The possible scenarios are:
		// a) saved to same project
		// b) saved to different Carbide project
		// c) saved to a non-Carbide project
		// d) was not in a Carbide project, but now is in a Carbide project
		// Because we have just the text page in when not in a Carbide project
		// c and d require a reload or other major editor reconfiguration. 
		// Scenarios a and b do not, but require more changes than are safe right now.
		// So for now we always reload.
		reload();
	}
	
	protected void updateProblemMarkers() {
		IFile file = getInputFile();
		if (file != null) {
			try {
				file.deleteMarkers(CarbideFormEditorMarker.FORMEDITOR_PROBLEM_MARKER, true, IResource.DEPTH_ZERO);
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}
			IView view = getView();
			if (view != null) {
				IMessage[] messages = view.getMessages();
				IPath modelPath = file.getLocation();
				for (IMessage message : messages) {
					// TODO: at a later point we should add markers to the #included files too, once we determine the proper way to clear them. 
					if (message.getLocation() != null && message.getLocation().equals(modelPath))
						message.createMarker(file, CarbideFormEditorMarker.FORMEDITOR_PROBLEM_MARKER);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(textEditorPageIndex);
		IDE.gotoMarker(textEditorPage, marker);
	}

	public boolean isSaveAsAllowed() {
		return true;
	}
	
	protected boolean findControlInPage(IFormPage page, final Control control) {
		Control partControl = page.getPartControl();
		if (partControl == control) {
			return true;
		} else if (partControl instanceof Composite) {
			Object visitResult = ControlVisitor.visitChildren((Composite)partControl, 
					new ControlVisitor.Visitor() {
						public Object visit(Control c) {
							return c == control? Boolean.TRUE : null;
						}
			});
			return visitResult != null && ((Boolean)visitResult).booleanValue();
		}
		return false;
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object result = null;
		// fix for Bug 6173
		if (adapter.equals(ISearchEditorAccess.class)) {
			result = new ISearchEditorAccess() {
				public IDocument getDocument(Match match) {
					IDocument document = null;
					if (textEditorPage != null) {
						document = textEditorPage.getDocumentProvider().getDocument(textEditorPage.getEditorInput());
					}
					return document;
				}
				
				public IAnnotationModel getAnnotationModel(Match match) {
					IAnnotationModel annoModel = null;
					if (textEditorPage != null) {
						annoModel = textEditorPage.getDocumentProvider().getAnnotationModel(textEditorPage.getEditorInput());
					}
					return annoModel;
				}
			};
		}
		if (result == null) {
			result = super.getAdapter(adapter);
		}
		return result;
	}
	
	public IFile getInputFile() {
		IFile result = ResourceUtil.getFile(getEditorInput());
		return result;
	}
	
	public IPath getInputLocation() {
		IPath result = null;
		IEditorInput input = getEditorInput();
		IResource r = ResourceUtil.getResource(input);
		if (r != null) {
			result = r.getLocation();
		} else if (input instanceof IStorageEditorInput) {
			IStorageEditorInput storageInput = (IStorageEditorInput) input;
			try {
				result = storageInput.getStorage().getFullPath();
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}
		}
		return result;
	}
	
	public boolean inputLocationExists() {
		boolean result = true;
		IFile inputFile = getInputFile();
		if (inputFile != null) {
			result = inputFile.exists();
		} else {
			IEditorInput input = getEditorInput();
			if (input instanceof IStorageEditorInput) {
				IStorageEditorInput storageInput = (IStorageEditorInput) input;
				try {
					result = storageInput.getStorage() != null;
				} catch (CoreException x) {
				}
			}
		}
		return result;
	}

	public IProject getProject() {
		IProject result = null;
		IFile file = getInputFile();
		if (file != null) {
			result = file.getProject();
		}
		return result;
	}
	
	/**
	 * Confirm editing should be allowed. If needed, interacts with the
	 * Team VCS provider to check that file can be modified.
	 * @return true if ok to proceed with changes.
	 */
	public boolean preflightEdit() {
		IFile fileInput = getInputFile();
		if (fileInput == null) {
			return true;
		}
		Shell shell = getEditorSite().getShell();
		// If files are modified as a result of a version control
		// operation then our resource listener will pick it up.
		IStatus status = FileUtils.validateEdit(fileInput, shell);
		if (status.isOK()) {
			// check to see if the file is derived
			if (fileInput.isDerived()) {
				final String warnKey = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_WARN_IF_INPUT_DERIVED;
				IPreferenceStore store = EditorsUI.getPreferenceStore();
				if (!store.getBoolean(warnKey))
					return true;
				
				MessageDialogWithToggle toggleDialog = MessageDialogWithToggle.openYesNoQuestion(
						getSite().getShell(),
						Messages.CarbideFormEditor_warning_derived_title,
						Messages.CarbideFormEditor_warning_derived_message,
						Messages.CarbideFormEditor_warning_derived_dontShowAgain,
						false,
						null,
						null);
				
				store.setValue(warnKey, !toggleDialog.getToggleState());
				
				return toggleDialog.getReturnCode() == IDialogConstants.YES_ID;
			}
		}
		
		return status.isOK();
	}

	@Override
	protected void updateActionBarContributor(int pageIndex) {
		IEditorPart activeEditor = (IEditorPart) pages.get(pageIndex);
		IEditorActionBarContributor contributor = getEditorSite().getActionBarContributor();
		if (contributor instanceof MultiPageEditorActionBarContributor) {
			MultiPageEditorActionBarContributor mpeac = (MultiPageEditorActionBarContributor) contributor;
			mpeac.setActivePage(activeEditor);
		}
	}
	
	protected void handlePageChanged(int index) {
		// IEditorPart activeEditor = getEditor(index);
		IEditorPart activeEditor = (IEditorPart) pages.get(index);
		// transitioning to text page?
		if (activeEditor == textEditorPage) {
			if (lastActivePage != textEditorPage)
				updateTextDocument();
		}
		// transitioning from text page?
		else if (lastActivePage == textEditorPage) {
			IDocumentUndoManager undoManager = CarbideTextEditor.getUndoManager(getEditorInput());
			undoManager.commit();
			revertView();
			refreshPages();
			undoManager.commit();
		}
		lastActivePage = activeEditor;
		MultiPageEditorActionBarContributor contributor = (MultiPageEditorActionBarContributor) getEditorSite().getActionBarContributor();
		contributor.setActivePage(activeEditor);
	}

	public CarbideTextEditor getTextEditorPage() {
		setActivePage(textEditorPageIndex);
		return textEditorPage;
	}

}