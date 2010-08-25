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
package com.nokia.carbide.cpp.internal.project.ui.editors.inf;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.ICarbideBuildManager;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideConfigurationChangedListener;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.internal.api.builder.ui.ManageConfigurationsDialog;
import com.nokia.carbide.cpp.epoc.engine.model.BldInfModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideFormEditor;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideTextEditor;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;


public class BldInfEditor extends CarbideFormEditor implements 
									ICarbideConfigurationChangedListener {

	private BldInfEditorContext editorContext;
	private OverviewPage overviewPage;
	private ExportsPage exportsPage;

	// true unless we degrade to text editor only 
	private boolean usingFormPages = true;

	
	public BldInfEditor() {
		editorContext = new BldInfEditorContext();
		initializeEditorContext(editorContext);				
		CarbideBuilderPlugin.addBuildConfigChangedListener(this);
	}
	
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void addPages() {
		try {
			if (usingFormPages) {
				createOverviewPage();
				createExportsPage();
			}
			createTextEditorPage();
			
			applyActivePageProperty();
			refreshPages();
			
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), Messages.BldInfEditor_ErrorCreatingPage,
					null, e.getStatus());
		}
	}
	
	private void createOverviewPage() throws PartInitException {
		overviewPage = new OverviewPage(editorContext);
		addPage(overviewPage);
	}
	
	private void createExportsPage() throws PartInitException {
		exportsPage = new ExportsPage(editorContext);
		addPage(exportsPage);
	}
	
	private void loadInput(IEditorInput editorInput) throws PartInitException {
		// assuming there's no previous model or connection to a document
		Check.checkState(editorContext.bldInfModel == null);
		Check.checkState(editorContext.textDocument == null);
		
		IPath path = getInputLocation();

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
							msg = Messages.BldInfEditor_noBuildConfigError;
						}
							
					} else {
						// we'll open with just a text tab
						//msg = Messages.BldInfEditor_UnableToOpenProject;
					}
					if (msg != null) {
						MessageDialog.openError(getSite().getShell(), 
								Messages.BldInfEditor_BldInfEditor, msg);
						throw new PartInitException(msg, null);
					}
				}
			}
			
			if (buildConfig != null) {
				editorContext.activeBuildConfig = buildConfig;
				IDocumentProvider provider = CarbideTextEditor.documentProvider();
				provider.connect(editorInput);
				editorContext.project = project;
				editorContext.textDocument = provider.getDocument(editorInput);
				BldInfModelFactory factory = new BldInfModelFactory();
				editorContext.bldInfModel = factory.createModel(path, editorContext.textDocument);
				editorContext.bldInfModel.parse();
				
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
			String fmt = Messages.BldInfEditor_UnableToIntitialize;
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
			boolean doDialog = MessageDialog.openQuestion(getSite().getShell(), Messages.BldInfEditor_BldInfEditor,
					Messages.BldInfEditor_noBuildConfig_ChooseNowPrompt);
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
		if (editorContext.bldInfView != null) {
			editorContext.bldInfView.dispose();
			editorContext.bldInfView = null;
		}
		BldInfEditorViewConfiguration viewConfig = new BldInfEditorViewConfiguration(
				editorContext.activeBuildConfig.getBuildContext(), getProject());
		editorContext.bldInfView = editorContext.bldInfModel.createView(viewConfig);						
	}
	
	@Override
	protected IView getView() {
		return editorContext.bldInfView;
	}

	protected void refreshPages( ) {
		if (usingFormPages) {
			overviewPage.refresh();
			exportsPage.refresh();
		}
	}
	
	public void operationHistoryChanged() {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				BldInfEditorContributor contributor = (BldInfEditorContributor) getEditorSite().getActionBarContributor();
				contributor.updateUndoRedo();
				editorDirtyStateChanged();
			}
		});
	}
		
	protected void revertView() {
		if (editorContext.bldInfView == null) {
			return;
		}
		
		Exception caughtException = null;
		try {
			editorContext.bldInfView.revert();
		} 
		catch (IllegalStateException x) {
			caughtException = x;		
		}
		catch (IllegalArgumentException x) {
			caughtException = x;
		}
		
		if (caughtException != null) {
			String msg = Messages.BldInfEditor_ErrorReverting;
			editorContext.logAndDisplayError(msg, caughtException);			
		}
	}
	
	public void dispose() {
		CarbideBuilderPlugin.removeBuildConfigChangedListener(this);
		super.dispose();
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
		if (adapter.equals(BldInfEditorContext.class)) {
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
			else if (findControlInPage(exportsPage, control)) {
				return exportsPage;
			}
		}
		return result;
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
						initializeView();
						refreshPages();
					}
				});
			}
		}
	}

	@Override
	protected void reloadRejected() {
		// The user rejected reloading when the input file changed
		// externally. Tell the view to assume it's synchronized so
		// it can commit when the user saves.
		editorContext.bldInfView.forceSynchronized();		
	}
	
	@Override
	protected Map<IPath, IDocument> getIncludedDocumentMap() {
		if (usingFormPages) {
			Map<IPath, IDocument> map = new HashMap<IPath, IDocument>(editorContext.bldInfModel.getDocumentMap());
			map.remove(editorContext.bldInfModel.getPath());
			return map;
		}
		else {
			return null;
		}
	}

	public OverviewPage getOverviewPage() {
		return overviewPage;
	}

	public ExportsPage getExportsPage() {
		return exportsPage;
	}

	public IBldInfView getBldInfView() {
		return editorContext.bldInfView;
	}

}
