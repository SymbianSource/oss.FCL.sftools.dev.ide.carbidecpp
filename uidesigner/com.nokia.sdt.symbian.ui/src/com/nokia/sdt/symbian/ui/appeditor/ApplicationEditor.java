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
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.uidesigner.ui.AbstractDesignerDataModelEditor;
import com.nokia.sdt.uidesigner.ui.command.ApplySourceChangesCommand;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.gef.commands.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import java.text.MessageFormat;
import java.util.*;

public class ApplicationEditor extends AbstractDesignerDataModelEditor implements ISaveablePart2 {
	
	private AppEditorContext editorContext;
	private MultiPageContentOutlinePage	contentOutlinePage;
	private ApplicationEditorContributor applicationEditorContributor;
	
		// only set when we've ok'ed closing the editor and part is definitely
		// closing. see isSaveOnCloseNeeded
	private PreflightInfo closingEditorPreflightInfo;
	final QualifiedName activePagePropertyName = new QualifiedName(UIPlugin.getDefault().toString(), "activePage"); //$NON-NLS-1$
	static final String APPUI_PAGE_ID = "AppUi";	 //$NON-NLS-1$
	
	// record clean values for changes that require propagation to view designs
	private String originalComponentVersion;
	private SymbianModelUtils.LocalizationFileFormat originalLocalizationFormat;
	private Language originalDisplayLanguage;
	private Set<Language> originalLanguages;
	private List<IDesignerDataModelSpecifier> originalViews;
	private Collection originalLayoutAreaConfigs;
	
	// bit flags for changes affecting data outside the root model
	static private final int DISPLAY_LANGUAGE = 1;
	static private final int INCLUDED_LANGUAGES = 2;
	static private final int LOCALIZATION_FORMAT = 4;
	static private final int COMPONENT_VERSIONS = 8;
	static private final int REMOVED_DESIGN_REFERENCES = 16;
	static private final int LAYOUT_CONFIGURATIONS = 32;
	static private final int ALL_CHANGES = 
		DISPLAY_LANGUAGE|INCLUDED_LANGUAGES|LOCALIZATION_FORMAT|COMPONENT_VERSIONS|
		REMOVED_DESIGN_REFERENCES|LAYOUT_CONFIGURATIONS;

	private static final String HELP_CONTEXT_ID = 
		UIPlugin.PLUGIN_ID + ".applicationEditorContext"; //$NON-NLS-1$
	
	public ApplicationEditor() {
	}

	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		super.init(site, editorInput);
		applicationEditorContributor = 
			(ApplicationEditorContributor) getEditorSite().getActionBarContributor();
		
	}
	
	ApplicationEditorContributor getContributor() {
		return applicationEditorContributor;
	}
	
	private void commandStackChanged() {
		getContributor().updateUndoRedo();
		editorDirtyStateChanged();
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (adapter.isInstance(this)) {
			result = this;
		}
		else if (adapter.equals(AppEditorContext.class)) {
			result = editorContext;
		}
		else if (adapter.equals(Shell.class)) {
			result = getSite() != null ? getSite().getShell() : null;
		}
		else if (adapter.equals(ApplicationEditorContributor.class)) {
			result = getContributor();
		}
		else {
			IDesignerEditor appUiPage = getAppUiPage();
			if (adapter.equals(IDesignerEditor.class)) {
				return appUiPage;
			}
			else if (appUiPage != null) {
				result = appUiPage.getAdapter(adapter);
				if (result instanceof IContentOutlinePage) {
					IContentOutlinePage outlinePage = (IContentOutlinePage) result;
					MultiPageContentOutlinePage mpcOutlinePage = getContentOutlinePage(outlinePage, appUiPage);
					if (appUiPage.equals(getSelectedPage()))
						mpcOutlinePage.activatePage(appUiPage);
					result = mpcOutlinePage;
				}
			}
		}
		
		if (result == null) {
			result = super.getAdapter(adapter);
		}
		return result;
	}
	
	private IDesignerEditor getAppUiPage() {
		if (editorContext == null)
			return null;
		
		int appUiPageIndex = editorContext.getAppUiPageIndex();
		if (pages != null && appUiPageIndex >= 0) {
			return (IDesignerEditor) pages.get(appUiPageIndex);
		}
		
		return null;
	}

	protected IStatus preLoadInput(IProgressMonitor monitor) {
		FeatureUseTrackerPlugin.getFeatureUseProxy().startUsingFeature(FeatureUseTrackerConsts.CARBIDE_UI_DESIGNER);
		IFile file = ResourceUtil.getFile(getEditorInput());
		WorkspaceContext wc = WorkspaceContext.getContext();
		IDesignerDataModelSpecifier modelSpecifier = wc.findSpecifierForResource(file);

		if (modelSpecifier != null && modelSpecifier.isUIDesign()) {
			return EditorUtils.makeOrReportEditorOpenStatus(
					Messages.getString("ApplicationEditor.notValidAppModelError"), null); //$NON-NLS-1$
		}
		
		return Status.OK_STATUS;
	}
		
	protected IStatus postLoadInput(IProgressMonitor monitor) {
		hookCommandStack(getDataModel());
		editorContext = new AppEditorContext(this);
		recordPropagatedValues();
		EditorServices.addEditor(this);	
		initializeModelListeners();
		
		monitor.worked(1);
			
		return Status.OK_STATUS;
	}
	
	private void hookCommandStack(IDesignerDataModel dataModel) {
		CommandStackEventListener listener = new CommandStackEventListener() {
			public void stackChanged(CommandStackEvent event) {
				switch (event.getDetail()) {
				case CommandStack.POST_EXECUTE:
				case CommandStack.POST_REDO:
				case CommandStack.POST_UNDO:
					commandStackChanged();
					break;
				}
			}
		};
		getCommandStack().addCommandStackEventListener(listener);
	}
	
	private void initializeModelListeners() {
		ILocalizedStringBundle bundle = null;
		DesignerDataModel model = (DesignerDataModel) getDataModel();
		DesignerDataModel modelImpl = (DesignerDataModel) model;
		IDesignerData designerData = modelImpl.getDesignerData();
		if (designerData != null) {
			bundle = designerData.getStringBundle();
		}
		Check.checkArg(bundle != null);

		bundle.addListener( new ILocalizedStringBundle.IListener() {
			public void stringTableAdded(Language language) {
			}

			public void stringTableRemoved(Language language) {
			}

			public void defaultLanguageChanged(Language newDefaultLanguage) {
				IDesignerEditor appUiPage = getAppUiPage();
				if (appUiPage != null) {
					appUiPage.refreshFromModel();
				}
			}
		});
	}

	@Override
	protected void addPages() {
		if (hasOpenError()) {
			addErrorPage();
			return;
		}
		try {
			initializeExtenders();
			initializePageActionBarContributors();
			originalLayoutAreaConfigs = getLayoutAreaConfigurations();
		} 
		catch (CoreException e) {
			ErrorDialog.openError(getSite().getShell(), Messages.getString("ApplicationEditor.pageCreateError"), //$NON-NLS-1$
					null, e.getStatus());
			Logging.log(UIPlugin.getDefault(), e.getStatus());
		}
	}

	private void initializePageActionBarContributors() {
		for (Iterator iter = pages.iterator(); iter.hasNext();) {
			Object page = iter.next();
			if (page instanceof IEditorPart) {
				IEditorPart editorPart = (IEditorPart) page;
				IEditorActionBarContributor contributor = 
					(IEditorActionBarContributor) editorPart.getAdapter(IEditorActionBarContributor.class);
				if (contributor != null) {
					contributor.init(getEditorSite().getActionBars(), getSite().getPage());
					getContributor().addPageContributor(editorPart, contributor);
				}
			}
		}
	}

	private Collection getLayoutAreaConfigurations() {
		// adapt pages to IDisplayModel. 
		// We'll assume only a single page is adaptable to it, and use that display model
		for (Iterator iter = pages.iterator(); iter.hasNext();) {
			Object page = iter.next();
			if (page instanceof IAdaptable) {
				Object adapter = ((IAdaptable) page).getAdapter(IDisplayModel.class);
				if (adapter != null) {
					return ((IDisplayModel) adapter).getLayoutAreaConfigurations();
				}
			}
		}
		
		return null;
	}
	
	public MultiPageContentOutlinePage getContentOutlinePage(IContentOutlinePage subPage, Object reference) {
		if (contentOutlinePage == null) {
			contentOutlinePage = new MultiPageContentOutlinePage(this);
			contentOutlinePage.addSubPage(subPage, reference);
		}
		return contentOutlinePage;
	}

	public Object getSelectedPage() {
		int index = getActivePage();
		if (index < 0)
			return null;
		
		return pages.get(index);
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		FeatureUseTrackerPlugin.getFeatureUseProxy().stopUsingFeature(FeatureUseTrackerConsts.CARBIDE_UI_DESIGNER);
		super.dispose();
	}
		
	/**
	 * Prior to saving, check for changes that have to
	 * be propagated to view models and prompt the user
	 * accordingly.
	 * @return true if ok to proceed with save
	 *
	 */
	
	private static class PreflightInfo {
		boolean isCloseRequest;
		int changeFlags;
		boolean preSaveOtherEditorsNeeded;
		boolean postSaveOtherEditorsNeeded;
		boolean reloadOtherEditorsNeeded;
		boolean reloadThis;
		String dialogMessage;
	}
	
	/**
	 * Fill in the preflight information, calculating what we need to do
	 * before saving
	 * @param isCloseRequest is user closing the editor?
	 */
	private PreflightInfo calcPreflightInfo(boolean isCloseRequest) {
		
		PreflightInfo pi = new PreflightInfo();
		pi.isCloseRequest = isCloseRequest;
		pi.changeFlags = calcPropagatedChangeFlags();
		
		int totalEditors = 0;
		int dirtyEditors = 0;
		IProject project = getDataModel().getProjectContext().getProject();
		IDesignerDataModelEditor[] editors = EditorServices.findEditorsForProject(project);
		for (IDesignerDataModelEditor ddme : editors) {
			if (ddme != this) {
				++totalEditors;
				if (ddme.isDirty()) {
					++dirtyEditors;
				}
			}
		}
		
		// changes requiring synching view designs, including save & reload
		// of open editors
		String fmt = null;
		if ((pi.changeFlags & (COMPONENT_VERSIONS|INCLUDED_LANGUAGES|LOCALIZATION_FORMAT|REMOVED_DESIGN_REFERENCES|LAYOUT_CONFIGURATIONS))!= 0) {
			if (dirtyEditors > 0) {
				if (isCloseRequest) {
					fmt = Messages.getString("ApplicationEditor.closeEditorSaveOtherEditors"); //$NON-NLS-1$
				} else {
					fmt = Messages.getString("ApplicationEditor.saveOtherEditors"); //$NON-NLS-1$
				}
				pi.preSaveOtherEditorsNeeded = true;
			}
			else  {
				if (isCloseRequest) {
					fmt = Messages.getString("ApplicationEditor.closeEditorReloadOtherEditors"); //$NON-NLS-1$
				} else {
					fmt = Messages.getString("ApplicationEditor.reloadOtherEditors"); //$NON-NLS-1$
				}
			} 
			pi.reloadOtherEditorsNeeded = true;
			pi.reloadThis = !isCloseRequest && (pi.changeFlags & COMPONENT_VERSIONS) != 0;
			pi.postSaveOtherEditorsNeeded = (pi.changeFlags & LOCALIZATION_FORMAT) != 0;
		}
		if (fmt != null) {
			Object params[] = { getDataModel().getModelSpecifier().getDisplayName() };
			pi.dialogMessage = MessageFormat.format(fmt, params);
		}
		return pi;
	}
	
	/**
	 * Get the list of designs whose references will be removed.
	 * @return list
	 */
	private List<IDesignerDataModelSpecifier> getRemovedDesigns() {
		List<IDesignerDataModelSpecifier> removedDesigns = new ArrayList<IDesignerDataModelSpecifier>();
		List<IDesignerDataModelSpecifier> referencedDesigns = SymbianModelUtils.getSpecifiersForReferencedDesigns(getDataModel());
		for (Iterator iter = originalViews.iterator(); iter.hasNext();) {
			IDesignerDataModelSpecifier dmSpec = (IDesignerDataModelSpecifier) iter.next();
			if (!referencedDesigns.contains(dmSpec))
				removedDesigns.add(dmSpec);
		}
		return removedDesigns;
	}

	/**
	 * Pose any dialog implied by the preflight info, return true
	 * if it's ok to continue, false to cancel.
	 */
	private boolean queryPreflightInfo(PreflightInfo pi) {
		boolean result = true;
		if (pi.dialogMessage != null) {
			result = MessageDialog.openQuestion(getEditorSite().getShell(), 
					Messages.getString("ApplicationEditor.confirmAppChangesDialogTitle"), pi.dialogMessage);			 //$NON-NLS-1$
		}
		return result;
	}
	
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		
		PreflightInfo pi = closingEditorPreflightInfo;
		if (pi == null) {
			pi = calcPreflightInfo(false);
			if (!queryPreflightInfo(pi)) {
				return;
			}
		}
		
		if (pi.preSaveOtherEditorsNeeded) {
			saveOpenEditors(monitor);
		}

		// close to-be-removed designs' editors 
		closeUnreferencedDesignEditors(monitor);

		// ensure the source gen provider knows about changes to design references
		IProjectContext context = getDataModel().getProjectContext();
		if (context != null) {
			context.getSourceGenProvider().updateProjectInfo(getDataModel());
		}

		if (fireQueryAboutToSave()) {
			firePreSaveNotify(monitor);
			
			// Changes are committed when the user switches the
			// active page, but changes buffered in the current page
			// are not committed automatically when saving.
			IFormPage activePage = getActivePageInstance();
			if (activePage != null) {
				IManagedForm managedForm = activePage.getManagedForm();
				if (managedForm != null) {
					managedForm.commit(true);
				}
			}
						
			IDesignerDataModel model = getDataModel();
			try {
				model.saveModel(monitor);
				
				getCommandStack().markSaveLocation();
				editorDirtyStateChanged();
			}
			catch (Exception x) {
				Check.reportFailure(Messages.getString("ApplicationEditor.genericSaveFailureError"), x); //$NON-NLS-1$
			}
			firePostSaveNotify(monitor);
			
			applyPropagatedChanges(pi, monitor);
			recordPropagatedValues();
			
			Collection<IModelMessage> messages = ModelMessages.validateAndUpdateMarkers(model);
			
			EditorUtils.saveCurrentLayout((IDesignerEditor) getAdapter(IDesignerEditor.class));
			
			Dialog dialog = EditorServices.createModelValidationMessagesDialog((Shell)getAdapter(Shell.class), getDataModel(), messages);
			dialog.open();
			
			if (pi.reloadThis)
				reload();
		}
	}

	private void saveOpenEditors(IProgressMonitor monitor) {
		// save all editors for the same project (excluding this one)
		IProjectContext pc = getDataModel().getProjectContext();
		IEditorPart[] editors = EditorServices.getEditors();
		for (int i = 0; i < editors.length; i++) {
			IDesignerDataModelEditor ddme = (IDesignerDataModelEditor) editors[i];
			if (ddme.getDataModel().getProjectContext() == pc &&
				(ddme != this)) {
				
				ddme.doSave(monitor);
			}
		}		
	}

	private void reloadOpenEditors() {
		// invalidate the cached model
		getDataModel().getModelSpecifier().modelSaved(getDataModel());
		// reload all editors for the same project (excluding this one)
		IProjectContext pc = getDataModel().getProjectContext();
		IEditorPart[] editors = EditorServices.getEditors();
		for (int i = 0; i < editors.length; i++) {
			IDesignerDataModelEditor ddme = (IDesignerDataModelEditor) editors[i];
			if (ddme.getDataModel().getProjectContext() == pc && ddme != this) {
				ddme.reload();
			}
		}		
	}
	
	private void refreshOpenEditors() {
		Language displayLanguage = getStringBundle().getDefaultLanguage();
		IProjectContext pc = getDataModel().getProjectContext();
		IEditorPart[] editors = EditorServices.getEditors();
		for (int i = 0; i < editors.length; i++) {
			IDesignerDataModelEditor ddme = (IDesignerDataModelEditor) editors[i];
			if (ddme.getDataModel().getProjectContext() == pc &&
				(ddme != this)) {
				
				DesignerDataModel dm = (DesignerDataModel) ddme.getDataModel();
				ILocalizedStringBundle bundle = dm.getDesignerData().getStringBundle();
				bundle.setDefaultLanguage(displayLanguage);
				
				ddme.refreshFromModel();
			}
		}				
	}

	/**
	 * Close any editors whose design references are removed.
	 * @param monitor
	 */
	private void closeUnreferencedDesignEditors(IProgressMonitor monitor) {
		List<IDesignerDataModelSpecifier> removedDesigns = getRemovedDesigns();
		for (Iterator iter = removedDesigns.iterator(); iter.hasNext();) {
			IDesignerDataModelSpecifier dmSpec = (IDesignerDataModelSpecifier) iter.next();
			IDesignerDataModelEditor editor = EditorServices.findEditorForModel(dmSpec);
			if (editor != null)
				editor.close(false);	// already saved in the presave phase
		}
	}
	
	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		/*
		 * setActivePage(0); IDE.gotoMarker(getEditor(0), marker);
		 */
	}

	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		updateActionBarContributor(newPageIndex);
	}
	
	protected void updateActionBarContributor(int pageIndex) {
		IEditorPart editor = getEditor(pageIndex);
		if (editor == null)
			editor = this;
		getContributor().setActiveEditor(editor);
		super.updateActionBarContributor(pageIndex);
	}

	public void reload() {
		// close the editor without saving
		getSite().getPage().closeEditor(this, false);
		try {
			// open the editor again
			IEditorInput input = getEditorInput();
			if (input.exists())
				getSite().getPage().openEditor(input, "com.nokia.sdt.symbian.ui.appeditor.ApplicationEditor"); //$NON-NLS-1$
		}
		catch (PartInitException pe) {
			IStatus status = Logging.newStatus(UIPlugin.getDefault(), pe);
			Logging.log(UIPlugin.getDefault(), status);
		}
	}

	private ILocalizedStringBundle getStringBundle() {
		DesignerDataModel dm = (DesignerDataModel) getDataModel();
		return dm.getDesignerData().getStringBundle();
	}
	
	/**
	 * Determine which propagatable data has changed
	 */
	private int calcPropagatedChangeFlags() {
		int result = 0;
		
		IDesignerDataModel model = getDataModel();
		// if any views were imported then we synch everything. We could calc
		// synch flags per-view, but we don't
		List<IDesignerDataModelSpecifier> referencedViews;
		referencedViews = SymbianModelUtils.getSpecifiersForReferencedDesigns(model);
		referencedViews.removeAll(originalViews);
		if (!referencedViews.isEmpty()) {
			result = ALL_CHANGES;
		} 
		else {
			Language currLanguageValue = getStringBundle().getDefaultLanguage();
			if (!originalDisplayLanguage.equals(currLanguageValue)) {
				result |= DISPLAY_LANGUAGE;
			}
			
			if (!ObjectUtils.equals(originalLocalizationFormat, SymbianModelUtils.getLocalizationFormat(model))) {
				result |= LOCALIZATION_FORMAT;
			}
			
			if (!ObjectUtils.equals(originalComponentVersion, SymbianModelUtils.getSDKVersion(model))) {
				result |= COMPONENT_VERSIONS;
			}
			
			Set<Language> currLanguages = getBundleLanguages();
			if (!currLanguages.equals(originalLanguages)) {
				result |= INCLUDED_LANGUAGES;
			}
			
			List<IDesignerDataModelSpecifier> removedDesigns = getRemovedDesigns();
			if (removedDesigns.size() > 0) {
				result |= REMOVED_DESIGN_REFERENCES;
			}
			
			// no display model for legacy application designs
			Collection layoutAreaConfigs = getLayoutAreaConfigurations();
			if (layoutAreaConfigs != null && 
					!layoutAreaConfigs.equals(originalLayoutAreaConfigs)) {
				result |= LAYOUT_CONFIGURATIONS;
			}
		}	
		
		return result;
	}
	
	private void recordPropagatedValues() {
		IDesignerDataModel model = getDataModel();
		originalComponentVersion = SymbianModelUtils.getSDKVersion(model);

		originalLocalizationFormat = SymbianModelUtils.getLocalizationFormat(model);

		IProjectContext pc = model.getProjectContext();
		ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
		originalDisplayLanguage = spc.getCurrentLanguage(); 
		editorContext.setDisplayLanguage(originalDisplayLanguage);
		
		originalLanguages = getBundleLanguages();
		originalViews = SymbianModelUtils.getSpecifiersForReferencedDesigns(model);
		
		originalLayoutAreaConfigs = getLayoutAreaConfigurations();
	}
	
	private Set<Language> getBundleLanguages() {
		Set<Language> result = new HashSet<Language>();
		List tables = getStringBundle().getLocalizedStringTables();
		for (Iterator iter = tables.iterator(); iter.hasNext();) {
			ILocalizedStringTable table = (ILocalizedStringTable) iter.next();
			result.add(table.getLanguage());
		}
		return result;
	}
	
	private void applyPropagatedChanges(PreflightInfo pi, IProgressMonitor monitor) {
	
		IDesignerDataModel rootModel = getDataModel();
		
		// turn off sourcegen updates while touching multiple models
		boolean oldEnableFlag = false;
		ISourceGenProvider provider = rootModel.getProjectContext().getSourceGenProvider();
		oldEnableFlag = provider.enableSourceGenChangedListeners(false);
		
		if ((pi.changeFlags & DISPLAY_LANGUAGE)!= 0) {
			ISymbianProjectContext spc = editorContext.getSymbianProjectContext();
			spc.setCurrentLanguage(editorContext.getDisplayLanguage());
		}
		
		// a change of only the display language does not trigger save/reload
		if (pi.changeFlags != 0 && pi.changeFlags != DISPLAY_LANGUAGE) {
			SynchViewDesignsAction action = new SynchViewDesignsAction(
					rootModel,
					(pi.changeFlags & COMPONENT_VERSIONS) != 0,
					(pi.changeFlags & INCLUDED_LANGUAGES) != 0,
					getEditorSite().getShell());
			
			action.synchDesigns(SymbianModelUtils.getSpecifiersForReferencedDesigns(rootModel));
		}
		
		if (pi.reloadOtherEditorsNeeded) {
			reloadOpenEditors();
		}
		else {
			// Any change in the application model requires the open
			// editors in the same project refresh. This encompasses
			// a change to the display language or a change to the root model
			// such as status pane changes. If we're reloading the editors
			// then they will of course redraw. Otherwise we tell them to
			// refresh everything.
			refreshOpenEditors();
		}
		
		if (pi.postSaveOtherEditorsNeeded) {
			saveOpenEditors(monitor);
		}
		
		// reenable sourcegen updates
		provider.enableSourceGenChangedListeners(oldEnableFlag);
	}
	
	/**
	 * Because of the app editor's synchronize on save behavior we need to customize
	 * the close->ask save changes flow. If the user says "yes" to the built-in "Save changes"
	 * query, the editor is definitely going to close. But our "confirm application changes" dialog
	 * has a cancel. If they cancel there the changes would be thrown away
	 */
	public int promptToSaveOnClose() {
		if (!isDirty()) {
			return NO;
		}
		PreflightInfo pi = calcPreflightInfo(true);
		if (pi.changeFlags == 0 || pi.dialogMessage == null) {
			return DEFAULT;
		}
		
		String title = Messages.getString("ApplicationEditor.saveDialogTitle"); //$NON-NLS-1$
		String labels[];
		if (pi.preSaveOtherEditorsNeeded) {
			labels = new String[]{Messages.getString("ApplicationEditor.saveAllButton"), Messages.getString("ApplicationEditor.dontSaveButton"), Messages.getString("ApplicationEditor.cancelButton")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		else {
			labels = new String[]{Messages.getString("ApplicationEditor.saveButton"),  //$NON-NLS-1$
						Messages.getString("ApplicationEditor.dontSaveButton"),  //$NON-NLS-1$
						Messages.getString("ApplicationEditor.cancelButton")};  //$NON-NLS-1$
		}
		MessageDialog dialog = new MessageDialog(getEditorSite().getShell(), title,
				null, pi.dialogMessage, MessageDialog.QUESTION, labels, YES);
		int result = dialog.open();
		if (result == YES) {
			// set after user approves actions, so we don't requery from doSave
			closingEditorPreflightInfo = pi;
		}
		return result;
	}
	
	public void refreshFromModel() {
		// nothing to do
	}

	public void disposeOutlinePage() {
		contentOutlinePage = null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenChangeListener#applyModifiedSourceChanges(org.eclipse.emf.common.command.Command)
	 */
	public void applyModifiedSourceChanges(org.eclipse.emf.common.command.Command command) {
		ApplySourceChangesCommand gefCommand = new ApplySourceChangesCommand(this, getListeningDataModel());
		gefCommand.setDataModelCommand(command);
		editorContext.addAndExecuteCommand(gefCommand);
	}

	@Override
	protected void createPages() {
		super.createPages();
		WorkbenchUtils.setHelpContextId(getContainer(), HELP_CONTEXT_ID);
	}

	private void setPartName(IEditorInput input) {
		// Since the file name is always the same, we include the
		// project name into the title.
		IFile file = ResourceUtil.getFile(input);
		String projectName = file.getProject().getName();
		if (!TextUtils.isEmpty(projectName)) {
			Object params[] = {projectName, file.getName()};
			String title = MessageFormat.format(Messages.getString("AppEditor.title"), params); //$NON-NLS-1$
			setPartName(title);
		} else {
			setPartName(file.getName());
		}
	}

	public void setInput(IEditorInput input) {
		super.setInputWithNotify(input);
		setPartName(input);
	}

	public void activatePage(int pageIndex) {
		setActivePage(pageIndex);
		pageChange(pageIndex);
	}
}
