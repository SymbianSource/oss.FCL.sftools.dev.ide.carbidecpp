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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.*;
import com.nokia.carbide.cdt.internal.api.builder.ui.ManageConfigurationsDialog;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.MMPModelFactory;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.texteditor.IDocumentProvider;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class MMPEditor extends CarbideFormEditor implements 
									ICarbideConfigurationChangedListener{

	private final MMPEditorContext editorContext;
	private OverviewPage overviewPage;
	private LibrariesPage librariesPage;
	private SourcesPage sourcesPage;
	private OptionsPage optionsPage;
	// true unless we degrade to text editor only 
	private boolean usingFormPages = true;
	
	public MMPEditor() {
		editorContext = new MMPEditorContext();
		initializeEditorContext(editorContext);
		CarbideBuilderPlugin.addBuildConfigChangedListener(this);
	}
	
	public void dispose() {		
		CarbideBuilderPlugin.removeBuildConfigChangedListener(this);
		super.dispose();
	}
	
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void addPages() {
		try {
			if (usingFormPages) {
				createOverviewPage();
				createSourcesPage();
				createLibrariesPage();
				createOptionsPage();
			}
			createTextEditorPage();

			applyActivePageProperty();
			refreshPages();

		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), Messages.MMPEditor_unableToOpenEditorErrorText,
					null, e.getStatus());
		}
	}
	
	@Override
	protected FormToolkit createToolkit(Display display) {
		FormToolkit result = super.createToolkit(display);
		// Bug 2811. Force borders to be drawn. In the XP theme table borders weren't drawn, 
		// but we always want them to have borders.
		result.setBorderStyle(SWT.NULL);
		return result;
	}

	private void createOverviewPage() throws PartInitException {
		overviewPage = new OverviewPage(editorContext);
		addPage(overviewPage);
	}
	
	private void createLibrariesPage() throws PartInitException {
		librariesPage = new LibrariesPage(editorContext);
		addPage(librariesPage);
	}
	
	private void createSourcesPage() throws PartInitException {
		sourcesPage = new SourcesPage(editorContext);
		addPage(sourcesPage);
	}
	
	private void createOptionsPage() throws PartInitException {
		optionsPage = new OptionsPage(editorContext);
		addPage(optionsPage);
	}	
	
	private void loadInput(IEditorInput editorInput) throws PartInitException {
		// assuming there's no previous model or connection to a document
		Check.checkState(editorContext.mmpModel == null);
		Check.checkState(editorContext.textDocument == null);
		
		IPath path = getInputLocation();
		if (!inputLocationExists()) {
			String fmt = Messages.MMPEditor_fileNotFoundMessage;
			// fix for Bug 5662 : cannot use path of non-existing file in exception message
			throw new PartInitException(MessageFormat.format(fmt, getEditorInput().getName()));
		}
		try {
			ICarbideBuildConfiguration buildConfig = null;
			IProject project = getProject();
			if (project != null) {
				// get the active ISymbianBuildContext
				ICarbideBuildManager buildManager = CarbideBuilderPlugin.getBuildManager();
				ICarbideProjectInfo cpi = buildManager.getProjectInfo(project);
				if (cpi != null) {
					buildConfig = cpi.getDefaultConfiguration();
				} 
				if (buildConfig == null) {
					String msg = null;
					if (buildManager.isCarbideProject(project)) {
						if (fixMissingBuildConfig(cpi)) {
							buildConfig = cpi.getDefaultConfiguration();
						} else {
							msg = Messages.MMPEditor_noBuildConfigError;
						}
							
					} else {
						// we'll open with just a text tab
						//msg = Messages.MMPEditor_badProjectMessage;
					}
					if (msg != null) {
						MessageDialog.openError(getSite().getShell(), 
								Messages.MMPEditor_dialogTitle, msg);
						throw new PartInitException(msg, null);
					}
				}
			}
			
			if (buildConfig != null) {
				editorContext.activeBuildConfig = buildConfig;
				editorContext.pathHelper = new MMPViewPathHelper(buildConfig);
				IDocumentProvider provider = CarbideTextEditor.documentProvider();
				provider.connect(editorInput);
				editorContext.project = project;
				editorContext.textDocument = provider.getDocument(editorInput);
				MMPModelFactory factory = new MMPModelFactory();
				editorContext.mmpModel = factory.createModel(path, editorContext.textDocument);
				editorContext.mmpModel.parse();
				
				initializeView();
				
				IFile file = getInputFile();
				if (file != null) {
					editorInputTracker = new TrackedResource(file);
					editorInputTracker.addListener(this);
				}
				
				updateProblemMarkers();
			} else {
				// degrade to a plain text editor; remove all but the text tab.
				usingFormPages = false;
			}
		}
		catch (PartInitException x) {
			throw x;
		}
		catch (CoreException x) {
			String fmt = Messages.MMPEditor_errorLoadingInputErrorText;
			Object params[] = {path.toOSString()};
			String msg = MessageFormat.format(fmt, params);
			throw new PartInitException(msg, x);
		}
	}
	
	private boolean fixMissingBuildConfig(ICarbideProjectInfo cpi) {
		boolean result = false;
		// Don't put up dialog if the workbench window is still not initialized
		Shell activeShell = WorkbenchUtils.getActiveShell();
		if (activeShell != null && activeShell.isVisible()) {
			boolean doDialog = MessageDialog.openQuestion(getSite().getShell(), Messages.MMPEditor_dialogTitle,
					Messages.MMPEditor_noBuildConfig_ChooseNowPrompt);
			if (doDialog) {
				ManageConfigurationsDialog dialog = new ManageConfigurationsDialog(
						getSite().getShell(), cpi);
				if (dialog.open() == Dialog.OK) {
					result = cpi.getDefaultConfiguration() != null;
				}
			}
		}
		return result;
	}
	
	
	private void initializeView() {
		if (editorContext.mmpView != null) {
			editorContext.mmpView.dispose();
			editorContext.mmpView = null;
		}
		MMPEditorViewConfiguration viewConfig = new MMPEditorViewConfiguration(
				editorContext.activeBuildConfig, getProject());
		editorContext.mmpView = editorContext.mmpModel.createView(viewConfig);						
	}
	
	@Override
	protected IView getView() {
		return editorContext.mmpView;
	}

	protected void refreshPages( ) {
		if (usingFormPages) {
			overviewPage.refresh();
			sourcesPage.refresh();
			optionsPage.refresh();
			librariesPage.refresh();
		}
	}
	
	public void operationHistoryChanged() {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				MMPEditorContributor contributor = (MMPEditorContributor) getEditorSite().getActionBarContributor();
				contributor.updateUndoRedo();
				editorDirtyStateChanged();
			}
		});
	}
			
	protected void revertView() {
		if (editorContext.mmpView == null) {
			return;
		}
		
		Exception caughtException = null;
		try {
			editorContext.mmpView.revert();
		} 
		catch (IllegalStateException x) {
			caughtException = x;		
		}
		catch (IllegalArgumentException x) {
			caughtException = x;
		}
		
		if (caughtException != null) {
			String msg = Messages.MMPEditor_errorRevertingMMPErrorText;
			editorContext.logAndDisplayError(msg, caughtException);			
		}
	}

	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		super.init(site, editorInput);
		setPartName(editorInput.getName());
		loadInput(editorInput);
	}


	@Override
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (adapter.equals(MMPEditorContext.class)) {
			result = editorContext;
		}
		else if (adapter.isInstance(this)) {
			result = this;
		}
		else if (adapter.equals(Shell.class)) {
			result = getSite() != null ? getSite().getShell() : null;
		}
		else if (adapter.equals(IResource.class)) {
			result = getInputFile();
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}
		return result;
	}
	
	@Override
	public IFormPage selectReveal(Object pageInput) {
		IFormPage result = super.selectReveal(pageInput);
		if (result == null && pageInput instanceof Control && usingFormPages) {
			Control control = (Control) pageInput;
			if (findControlInPage(overviewPage, control)) {
				return overviewPage;
			}
			else if (findControlInPage(sourcesPage, control)) {
				return sourcesPage;
			}
			else if (findControlInPage(librariesPage, control)) {
				return librariesPage;
			}
			else if (findControlInPage(optionsPage, control)) {
				return optionsPage;
			}
		}
		return result;
	}
	
	@Override
	protected void reloadRejected() {
		editorContext.mmpView.forceSynchronized();		
	}
	
	@Override
	protected Map<IPath, IDocument> getIncludedDocumentMap() {
		if (usingFormPages) {
			Map<IPath, IDocument> map = new HashMap<IPath, IDocument>(editorContext.mmpModel.getDocumentMap());
			map.remove(editorContext.mmpModel.getPath());
			return map;
		}
		else {
			return null;
		}
	}

	public void buildConfigurationChanged(final ICarbideBuildConfiguration currentConfig) {
		IProject project = getProject();
		if (project != null && editorContext != null) {
			ICarbideProjectInfo carbideProject = currentConfig.getCarbideProject();
			if (carbideProject.getProject() == project) {
				getEditorSite().getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						commitView();
						editorContext.activeBuildConfig = currentConfig;
						editorContext.pathHelper = new MMPViewPathHelper(currentConfig);
						initializeView();
						refreshPages();
					}
				});
			}
		}
	}
}
