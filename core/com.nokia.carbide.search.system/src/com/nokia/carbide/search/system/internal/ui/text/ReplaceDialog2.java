/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation, Nokia and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.ui.text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.contentassist.SubjectControlContentAssistant;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.GlobalBuildAction;
import org.eclipse.ui.contentassist.ContentAssistHandler;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import com.ibm.icu.text.MessageFormat;
import com.nokia.carbide.search.system.internal.core.text.PatternConstructor;
import com.nokia.carbide.search.system.internal.ui.ISearchHelpContextIds;
import com.nokia.carbide.search.system.internal.ui.Messages;
import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.internal.ui.util.ExceptionHandler;
import com.nokia.carbide.search.system.internal.ui.util.ExtendedDialogWindow;
import com.nokia.carbide.search.system.ui.NewSearchUI;
import com.nokia.carbide.search.system.ui.text.Match;
import com.nokia.carbide.search.system2.internal.ui.InternalSearchUI;
import com.nokia.carbide.search.system2.internal.ui.text.PositionTracker;

class ReplaceDialog2 extends ExtendedDialogWindow {
		
	private abstract static class ReplaceOperation implements IRunnableWithProgress {

		public void run(IProgressMonitor monitor) throws InvocationTargetException {
			try {
				doReplace(monitor);
			} catch (BadLocationException e) {
				throw new InvocationTargetException(e);
			} catch (CoreException e) {
				throw new InvocationTargetException(e);
			} catch (IOException e) {
				throw new InvocationTargetException(e);
			}
		}
		
		protected abstract void doReplace(IProgressMonitor pm) throws BadLocationException, CoreException, IOException;
	}
	
	private static final String SETTINGS_GROUP= "ReplaceDialog2"; //$NON-NLS-1$
	private static final String SETTINGS_REPLACE_WITH= "replace_with"; //$NON-NLS-1$
	
	// various widget related constants
	private static final int REPLACE= IDialogConstants.CLIENT_ID + 1;
	private static final int REPLACE_ALL_IN_FILE= IDialogConstants.CLIENT_ID + 2;
	private static final int REPLACE_ALL= IDialogConstants.CLIENT_ID + 3;
	private static final int SKIP= IDialogConstants.CLIENT_ID + 4;
	private static final int SKIP_FILE= IDialogConstants.CLIENT_ID + 5;
	private static final int SKIP_ALL= IDialogConstants.CLIENT_ID + 6;
	
	// Widgets
	private Combo fTextField;
	private Button fReplaceWithRegex;
	private Button fReplaceButton;
	private Button fReplaceAllInFileButton;
	private Button fReplaceAllButton;
	private Button fSkipButton;
	private Button fSkipFileButton;

	
	private List fMarkers;
	private boolean fSkipReadonly= false;
	
	// reuse editors stuff
	private IReusableEditor fEditor;
	private FileSearchPage fPage;
	private ContentAssistHandler fReplaceContentAssistHandler;
	private Label fStatusLabel;

	private boolean fSaved= false;

	protected ReplaceDialog2(Shell parentShell, IFileStore[] entries, FileSearchPage page) {
		super(parentShell);
		Assert.isNotNull(entries);
		Assert.isNotNull(page.getInput());
		fPage= page;
		fMarkers= new ArrayList();
		initializeMarkers(entries);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
	 */
	protected IDialogSettings getDialogBoundsSettings() {
		return SearchPlugin.getDefault().getDialogSettingsSection("DialogBounds_ReplaceDialog2"); //$NON-NLS-1$
	}
		
	private FileSearchQuery getQuery() {
		return (FileSearchQuery) fPage.getInput().getQuery();
	}
		
	private void initializeMarkers(IFileStore[] entries) {
		for (int j= 0; j < entries.length; j++) {
			IFileStore entry = entries[j];
			Match[] matches= fPage.getDisplayedMatches(entry);
			for (int i= 0; i < matches.length; i++) {
				fMarkers.add(matches[i]);
			}
		}
	}
	
	// widget related stuff -----------------------------------------------------------
	public void create() {
		super.create();
		Shell shell= getShell();
		shell.setText(getDialogTitle());
		gotoCurrentMarker();
		enableButtons();
		
		if (!canReplace()) {
			statusMessage(true, MessageFormat.format(SearchMessages.ReplaceDialog2_nomatches_error, new String[] { getQuery().getSearchString() }));
		}
		
	}
		
	public int open() {
		boolean wasAutobuild = false;
		try {
			wasAutobuild= disableAutobuild();
		} catch (CoreException e) {
			ExceptionHandler.handle(e, getShell(), getDialogTitle(), SearchMessages.ReplaceDialog2_error_disableAutobuild); 
		}
		try {
			return super.open();
		} finally {
			if (wasAutobuild)
				try {
					restoreAutobuild();
				} catch (CoreException e1) {
					ExceptionHandler.handle(e1, getShell(), getDialogTitle(), SearchMessages.ReplaceDialog2_error_restoreAutobuild); 
					return CANCEL;
				}
		}
	}	

	private void restoreAutobuild() throws CoreException {
		// this is only called if autobuild was on before.
		IWorkspace workspace= ResourcesPlugin.getWorkspace();
		IWorkspaceDescription description = workspace.getDescription();
		description.setAutoBuilding(true);
		workspace.setDescription(description);

		if (fSaved) {
			new GlobalBuildAction(fPage.getSite().getWorkbenchWindow(), IncrementalProjectBuilder.INCREMENTAL_BUILD).run();
		}
	}

	private boolean disableAutobuild() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		boolean autobuild = workspace.isAutoBuilding();
		if (autobuild) {
			IWorkspaceDescription description = workspace.getDescription();
			description.setAutoBuilding(false);
			workspace.setDescription(description);
		}
		return autobuild;
	}

	protected Control createPageArea(Composite parent) {
		initializeDialogUnits(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, ISearchHelpContextIds.REPLACE_DIALOG);
		Composite result= new Composite(parent, SWT.NULL);
		GridLayout layout= new GridLayout();
		result.setLayout(layout);
		layout.numColumns= 2;
		
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		
		initializeDialogUnits(result);
		
		FileSearchQuery query= getQuery();
		
		Label label= new Label(result, SWT.NONE);
		label.setText(SearchMessages.ReplaceDialog_replace_label); 
		Text clabel= new Text(result, SWT.BORDER | SWT.READ_ONLY);
		clabel.setText(query.getSearchString());
		GridData gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint= convertWidthInCharsToPixels(50);
		clabel.setLayoutData(gd);
		
		
		label= new Label(result, SWT.NONE);
		label.setText(SearchMessages.ReplaceDialog_with_label); 
		fTextField= new Combo(result, SWT.DROP_DOWN);
		gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint= convertWidthInCharsToPixels(50);
		fTextField.setLayoutData(gd);
		fTextField.setFocus();
		
		IDialogSettings settings= SearchPlugin.getDefault().getDialogSettings().getSection(SETTINGS_GROUP);
		if (settings != null) {
			String[] previousReplaceWith= settings.getArray(SETTINGS_REPLACE_WITH);
			if (previousReplaceWith != null) {
				fTextField.setItems(previousReplaceWith);
				fTextField.select(0);
			}
		}
		
		new Label(result, SWT.NONE);
		fReplaceWithRegex= new Button(result, SWT.CHECK);
		fReplaceWithRegex.setText(SearchMessages.ReplaceDialog_isRegex_label);
		fReplaceWithRegex.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setContentAssistsEnablement(fReplaceWithRegex.getSelection());
			}
		});
		if (query.isRegexSearch()) {
			fReplaceWithRegex.setSelection(true);
		} else {
			fReplaceWithRegex.setSelection(false);
			fReplaceWithRegex.setEnabled(false);
		}
	
		
		fStatusLabel= new Label(result, SWT.NULL);
		gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalAlignment= SWT.BOTTOM;
		gd.horizontalSpan= 2;
		fStatusLabel.setLayoutData(gd);

		setContentAssistsEnablement(fReplaceWithRegex.getSelection());
		
		applyDialogFont(result);
		return result;
	}
	
	protected Control createButtonBar(Composite parent) {
		Composite composite= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.numColumns= 0;   // createActionButton increments 
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		fReplaceButton= createActionButton(composite, REPLACE, SearchMessages.ReplaceDialog_replace, true); 
		fReplaceAllInFileButton= createActionButton(composite, REPLACE_ALL_IN_FILE, SearchMessages.ReplaceDialog_replaceAllInFile, false); 

		Label filler= new Label(composite, SWT.NONE);
		filler.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		
		fReplaceAllButton= createActionButton(composite, REPLACE_ALL, SearchMessages.ReplaceDialog_replaceAll, false); 
		fSkipButton= createActionButton(composite, SKIP, SearchMessages.ReplaceDialog_skip, false); 
		fSkipFileButton= createActionButton(composite, SKIP_FILE, SearchMessages.ReplaceDialog_skipFile, false); 

		filler= new Label(composite, SWT.NONE);
		filler.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		super.createButtonsForButtonBar(composite);  // cancel button

		layout.numColumns= 4;   // createActionButton increments 
		
		return composite;
	}
		
	private void enableButtons() {
		fSkipButton.setEnabled(hasNextMarker());
		fSkipFileButton.setEnabled(hasNextFile());
		fReplaceButton.setEnabled(canReplace());
		fReplaceAllInFileButton.setEnabled(canReplace());
		fReplaceAllButton.setEnabled(canReplace());
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.CANCEL_ID) {
			super.buttonPressed(buttonId);
			return;
		}
		
		
		final String replaceText= fTextField.getText();
		statusMessage(false, ""); //$NON-NLS-1$
		try {
			switch (buttonId) {
				case SKIP :
					skip();
					break;
				case SKIP_FILE :
					skipFile();
					break;
				case REPLACE :
					run(new ReplaceOperation() {
						protected void doReplace(IProgressMonitor pm) throws BadLocationException, CoreException {
							replace(pm, replaceText);
						}
					}, (IFileStore) getCurrentMarker().getElement());
					gotoCurrentMarker();
					break;
				case REPLACE_ALL_IN_FILE :
					run(new ReplaceOperation() {
						protected void doReplace(IProgressMonitor pm) throws BadLocationException, CoreException {
							replaceInFile(pm, replaceText);
						}
					}, (IFileStore) getCurrentMarker().getElement());
					gotoCurrentMarker();
					break;
				case REPLACE_ALL :
					run(new ReplaceOperation() {
						protected void doReplace(IProgressMonitor pm) throws BadLocationException, CoreException {
							replaceAll(pm, replaceText);
						}
					}, null);
					gotoCurrentMarker();
					break;
				default : {
				}
			}
		} catch (InvocationTargetException e) {
			Throwable targetException= e.getTargetException();
			if (targetException instanceof PatternSyntaxException) {
				String format= SearchMessages.ReplaceDialog2_regexError_format; 
				String message= MessageFormat.format(format, new Object[] { targetException.getLocalizedMessage() });
				statusMessage(true, message);
			} else {
				String message= Messages.format(SearchMessages.ReplaceDialog_error_unable_to_replace, ((IFileStore)getCurrentMarker().getElement()).getName()); 
				ExceptionHandler.handle(e, getParentShell(), getDialogTitle(), message);
			}
		} catch (InterruptedException e) {
			// means operation canceled
		} finally {
			if (!canReplace())
				close();
			else {
				enableButtons();
			}
		}
	}
	
	private void run(ReplaceOperation operation, IFileStore resource) throws InvocationTargetException, InterruptedException {
//		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
//		ISchedulingRule rule= ruleFactory.modifyRule(resource);
		
		PlatformUI.getWorkbench().getProgressService().runInUI(this, operation, null);	
	}
	
	private Match getCurrentMarker() {
		return (Match)fMarkers.get(0);
	}
	
	private void replace(IProgressMonitor pm, String replacementText) throws BadLocationException, CoreException {
		Match marker= getCurrentMarker();
		pm.beginTask(SearchMessages.ReplaceDialog_task_replace, 10); 
		replaceInFile(pm, (IFileStore) marker.getElement(), replacementText, new Match[]{marker});
	}
	
	private Path getFullPath(IFileStore fileStore) {
		try {
			File file = fileStore.toLocalFile(EFS.NONE, null);
			return new Path(file.getAbsolutePath());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private void replaceInFile(IProgressMonitor pm, String replacementText) throws BadLocationException, CoreException {
		Match firstMarker= getCurrentMarker();
		Match[] markers= collectMarkers((IFileStore)firstMarker.getElement());
		pm.beginTask(Messages.format(SearchMessages.ReplaceDialog_task_replaceInFile, getFullPath((IFileStore)firstMarker.getElement())).toString(), 4); 
		replaceInFile(pm, (IFileStore) firstMarker.getElement(), replacementText, markers);
	}
	
	private void replaceAll(IProgressMonitor pm, String replacementText) throws BadLocationException, CoreException {
		int resourceCount= countResources();
		pm.beginTask(SearchMessages.ReplaceDialog_task_replace_replaceAll, resourceCount); 
		try {
			while (fMarkers.size() > 0) {
				replaceInFile(new SubProgressMonitor(pm, 1, 0), replacementText);
			}
		} finally {
			pm.done();
		}
	}
	
	private void replaceInFile(final IProgressMonitor pm, final IFileStore file, final String replacementText, final Match[] markers) throws BadLocationException, CoreException {
		if (pm.isCanceled())
			throw new OperationCanceledException();
		doReplaceInFile(pm, file, replacementText, markers);
	}
	
	private void doReplaceInFile(IProgressMonitor pm, IFileStore file, String replacementText, final Match[] markers) throws BadLocationException, CoreException {
		Pattern pattern= null;
		FileSearchQuery query= getQuery();
		if (query.isRegexSearch()) {
			pattern= createReplacePattern(query);
		}
		try {
			if (file.fetchInfo().getAttribute(EFS.ATTRIBUTE_READ_ONLY)) {
				if (fSkipReadonly) {
					skipFile();
					return;
				}
				int rc= askForSkip(file);
				switch (rc) {
					case CANCEL :
						throw new OperationCanceledException();
					case SKIP_FILE :
						skipFile();
						return;
					case SKIP_ALL :
						fSkipReadonly= true;
						skipFile();
						return;
				}
			}
			ITextFileBufferManager bm= FileBuffers.getTextFileBufferManager();
			try {
				bm.connect(getFullPath(file), LocationKind.IFILE, new SubProgressMonitor(pm, 1));
				ITextFileBuffer fb= bm.getTextFileBuffer(getFullPath(file), LocationKind.IFILE);
				boolean wasDirty= fb.isDirty();
				IDocument doc= fb.getDocument();
				for (int i= 0; i < markers.length; i++) {
					PositionTracker tracker= InternalSearchUI.getInstance().getPositionTracker();
					Match match= markers[i];
					int offset= match.getOffset();
					int length= match.getLength();
					Position currentPosition= tracker.getCurrentPosition(match);
					if (currentPosition != null) {
						offset= currentPosition.offset;
						length= currentPosition.length;
					}
					String originalText= doc.get(offset, length);
					String replacementString= computeReplacementString(pattern, originalText, replacementText);
					doc.replace(offset, length, replacementString);
					fMarkers.remove(match);
					fPage.getInput().removeMatch(match);
				}
				if (!wasDirty) {
					fb.commit(new SubProgressMonitor(pm, 1), true);
					fSaved= true;
				}
			} finally {
				bm.disconnect(getFullPath(file), LocationKind.IFILE, new SubProgressMonitor(pm, 1));
			}
		} finally {
			pm.done();
		}
	}
	
	private Pattern createReplacePattern(FileSearchQuery query) {
		return PatternConstructor.createPattern(query.getSearchString(), true, true, query.isCaseSensitive(), false);
	}

	private String computeReplacementString(Pattern pattern, String originalText, String replacementText) {
		if (pattern != null) {
			try {
				return pattern.matcher(originalText).replaceFirst(replacementText);
			} catch (IndexOutOfBoundsException ex) {
				throw new PatternSyntaxException(ex.getLocalizedMessage(), replacementText, -1);
			}
		}
		return replacementText;
	}

	private int askForSkip(final IFileStore file) {
		
		String message= Messages.format(SearchMessages.ReadOnlyDialog_message, getFullPath(file).toString()); 
		String[] buttonLabels= null;
		boolean showSkip= countResources() > 1;
		if (showSkip) {
			String skipLabel= SearchMessages.ReadOnlyDialog_skipFile; 
			String skipAllLabel= SearchMessages.ReadOnlyDialog_skipAll; 
			buttonLabels= new String[]{skipLabel, skipAllLabel, IDialogConstants.CANCEL_LABEL};
		} else {
			buttonLabels= new String[]{IDialogConstants.CANCEL_LABEL};
			
		}
		
		MessageDialog msd= new MessageDialog(getShell(), getShell().getText(), null, message, MessageDialog.ERROR, buttonLabels, 0);
		int rc= msd.open();
		switch (rc) {
			case 0 :
				return showSkip ? SKIP_FILE : CANCEL;
			case 1 :
				return SKIP_ALL;
			default :
				return CANCEL;
		}
	}
		
	private String getDialogTitle() {
		return SearchMessages.ReplaceDialog_dialog_title; 
	}
	
	private void skip() {
		fMarkers.remove(0);
		Assert.isTrue(fMarkers.size() > 0);
		gotoCurrentMarker();
	}
	
	private void skipFile() {
		Match currentMarker= getCurrentMarker();
		if (currentMarker == null)
			return;
		IFileStore currentFile= (IFileStore) currentMarker.getElement();
		while (fMarkers.size() > 0 && getCurrentMarker().getElement().equals(currentFile))
			fMarkers.remove(0);
		gotoCurrentMarker();
	}
	
	private void gotoCurrentMarker() {
		if (fMarkers.size() > 0) {
			Match marker= getCurrentMarker();
			Control focusControl= getShell().getDisplay().getFocusControl();
			try {
				selectEntry(marker);
				ITextEditor editor= null;
				if (NewSearchUI.reuseEditor())
					editor= openEditorReuse(marker);
				else
					editor= openEditorNoReuse(marker);
				Position p= InternalSearchUI.getInstance().getPositionTracker().getCurrentPosition(marker);
				if (p != null)
					editor.selectAndReveal(p.getOffset(), p.getLength());
				else
				editor.selectAndReveal(marker.getOffset(), marker.getLength());
				if (focusControl != null && !focusControl.isDisposed())
					focusControl.setFocus();
			} catch (PartInitException e) {
				String message= Messages.format(SearchMessages.ReplaceDialog_error_unable_to_open_text_editor, ((IFileStore)marker.getElement()).getName()); 
				MessageDialog.openError(getParentShell(), getDialogTitle(), message);
			}
		}
	}
	
	private void selectEntry(Match marker) {
		ISelection sel= fPage.getViewer().getSelection();
		if (!(sel instanceof IStructuredSelection))
			return;
		IStructuredSelection ss= (IStructuredSelection) sel;
		IFileStore file= (IFileStore) marker.getElement();
		if (ss.size() == 1 && file.equals(ss.getFirstElement()))
			return;
		fPage.getViewer().setSelection(new StructuredSelection(marker.getElement()));
	}

	// opening editors ------------------------------------------
	private ITextEditor openEditorNoReuse(Match marker) throws PartInitException {
		IFileStore file= (IFileStore) marker.getElement();
		IWorkbenchPage activePage= SearchPlugin.getActivePage();
		if (activePage == null)
			return null;
		ITextEditor textEditor= showOpenTextEditor(activePage, file);
		if (textEditor != null)
			return textEditor;
		return openNewTextEditor(file, activePage);
	}
	
	private ITextEditor openNewTextEditor(IFileStore file, IWorkbenchPage activePage) throws PartInitException {
		IEditorDescriptor desc= getDefaultEditor(file);
		if (desc != null) {
			String editorID= desc.getId();
			IEditorPart editor;
			if (desc.isInternal()) {
				editor= activePage.openEditor(new FileStoreEditorInput(file), editorID);
				if (editor instanceof ITextEditor) {
					if (editor instanceof IReusableEditor)
						fEditor= (IReusableEditor) editor;
					return (ITextEditor)editor;
				}
				activePage.closeEditor(editor, false);
			}
		}
		IEditorPart editor= activePage.openEditor(new FileStoreEditorInput(file), "org.eclipse.ui.DefaultTextEditor"); //$NON-NLS-1$
		return (ITextEditor)editor;
	}
	private IEditorDescriptor getDefaultEditor(IFileStore fileStore)
	{
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		IFile[] ifiles = null;
		try {
			ifiles = root.findFilesForLocation(new Path(fileStore.toLocalFile(EFS.NONE, null).getAbsolutePath()));
		} catch (CoreException e) {
		}

		if (ifiles == null || ifiles.length == 0) {
			return PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fileStore.getName());
		} else {
			return IDE.getDefaultEditor(ifiles[0]);
		}
	}

	private ITextEditor openEditorReuse(Match marker) throws PartInitException {
		IWorkbenchPage page= SearchPlugin.getActivePage();
		IFileStore file= (IFileStore) marker.getElement();
		if (page == null)
			return null;

		ITextEditor textEditor= showOpenTextEditor(page, file);
		if (textEditor != null)
			return textEditor;

		String editorId= null;
		IEditorDescriptor desc= getDefaultEditor(file);
		if (desc != null && desc.isInternal())
			editorId= desc.getId();

		boolean isOpen= isEditorOpen(page, fEditor);

		boolean canBeReused= isOpen && !fEditor.isDirty() && !isPinned(fEditor);
		boolean showsSameInputType= fEditor != null && (editorId == null || fEditor.getSite().getId().equals(editorId));

		if (canBeReused) {
			if (showsSameInputType) {
				fEditor.setInput(new FileStoreEditorInput(file));
				page.bringToTop(fEditor);
				return (ITextEditor) fEditor;
			}
			page.closeEditor(fEditor, false);
			fEditor= null;
		}
		return openNewTextEditor(file, page);
	}

	private boolean isEditorOpen(IWorkbenchPage page, IEditorPart editor) {
		if (editor != null) {
			IEditorReference[] parts= page.getEditorReferences();
			int i= 0;
			for (int j = 0; j < parts.length; j++) {
				if (editor == parts[i++].getEditor(false))
					return true;
			}
		}
		return false;
	}

	private ITextEditor showOpenTextEditor(IWorkbenchPage page, IFileStore file) {
		IEditorPart editor= page.findEditor(new FileStoreEditorInput(file));
		if (editor instanceof ITextEditor) {
			page.bringToTop(editor);
			return (ITextEditor) editor;
		}
		return null;
	}

	private boolean isPinned(IEditorPart editor) {
		if (editor == null)
			return false;
		
		IEditorReference[] editorRefs= editor.getEditorSite().getPage().getEditorReferences();
		int i= 0;
		while (i < editorRefs.length) {
			if (editor.equals(editorRefs[i].getEditor(false)))
				return editorRefs[i].isPinned();
			i++;
		}
		return false;
	}
	
	// resource related  -------------------------------------------------------------
	/**
	 * @return the number of resources referred to in fMarkers
	 */
	private int countResources() {
		IFileStore r= null;
		int count= 0;
		for (Iterator elements= fMarkers.iterator(); elements.hasNext(); ) {
			Match element= (Match)elements.next();
			if (!element.getElement().equals(r)) {
				count++;
				r= (IFileStore) element.getElement();
			}
		}
		return count;
	}
	
	private Match[] collectMarkers(IFileStore resource) {
		List matching= new ArrayList();
		for (int i= 0; i < fMarkers.size(); i++) {
			Match marker= (Match)fMarkers.get(i);
			if (!resource.equals(marker.getElement()))
				break;
			matching.add(marker);
		}
		Match[] markers= new Match[matching.size()];
		return (Match[])matching.toArray(markers);
	}
	
	
	// some queries -------------------------------------------------------------
	private boolean hasNextMarker() {
		return fMarkers.size() > 1;
	}
	
	private boolean hasNextFile() {
		if (!hasNextMarker())
			return false;
		IFileStore currentFile= (IFileStore) getCurrentMarker().getElement();
		for (int i= 0; i < fMarkers.size(); i++) {
			if (!((Match)fMarkers.get(i)).getElement().equals(currentFile))
				return true;
		}
		return false;
	}
	
	private boolean canReplace() {
		return fMarkers.size() > 0;
	}
	
	public static SubjectControlContentAssistant createContentAssistant(boolean isFind) {
		final SubjectControlContentAssistant contentAssistant= new SubjectControlContentAssistant();
		
		contentAssistant.setRestoreCompletionProposalSize(SearchPlugin.getDefault().getDialogSettings());
		
		IContentAssistProcessor processor= new RegExContentAssistProcessor(isFind);
		contentAssistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		
		contentAssistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		contentAssistant.setInformationControlCreator(new IInformationControlCreator() {
			/*
			 * @see org.eclipse.jface.text.IInformationControlCreator#createInformationControl(org.eclipse.swt.widgets.Shell)
			 */
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent);
			}});
		
		return contentAssistant;
	}
	
	private void setContentAssistsEnablement(boolean enable) {
		if (enable) {
			if (fReplaceContentAssistHandler == null) {
				fReplaceContentAssistHandler= ContentAssistHandler.createHandlerForCombo(fTextField, createContentAssistant(false));
			}
			fReplaceContentAssistHandler.setEnabled(true);
			
		} else {
			if (fReplaceContentAssistHandler == null)
				return;
			fReplaceContentAssistHandler.setEnabled(false);
		}
	}

	private void statusMessage(boolean error, String message) {
		fStatusLabel.setText(message);
	
		if (error)
			fStatusLabel.setForeground(JFaceColors.getErrorText(fStatusLabel.getDisplay()));
		else
			fStatusLabel.setForeground(null);
	
		if (error)
			getShell().getDisplay().beep();
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	public boolean close() {
		String[] items= fTextField.getItems();
		ArrayList history= new ArrayList();
		history.add(fTextField.getText());
		int historySize= Math.min(items.length, 6);
		for (int i= 0; i < historySize; i++) {
			String curr= items[i];
			if (!history.contains(curr)) {
				history.add(curr);
			}
		}
		IDialogSettings settings= SearchPlugin.getDefault().getDialogSettings().addNewSection(SETTINGS_GROUP);
		settings.put(SETTINGS_REPLACE_WITH, (String[]) history.toArray(new String[history.size()]));
		return super.close();
	}


}
