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
package com.nokia.sdt.uidesigner.ui;

import com.nokia.carbide.internal.api.updater.IProjectUpdateManager;
import com.nokia.carbide.updater.CarbideUpdaterPlugin;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.datamodel.images.IProjectImageInfo;
import com.nokia.sdt.datamodel.images.IProjectImageInfoListener;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.sourcegen.ISourceGenChangeListener;
import com.nokia.sdt.uidesigner.ui.command.ApplySourceChangesCommand;
import com.nokia.sdt.uidesigner.ui.utils.*;
import com.nokia.sdt.utils.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.ShowDialogWhenWorkbenchVisibleJob;
import com.nokia.cpp.internal.api.utils.ui.editor.ErrorPage;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.ResourceUtil;

import java.text.MessageFormat;
import java.util.*;

public abstract class AbstractDesignerDataModelEditor extends FormEditor 
			implements IDesignerDataModelEditor, ISourceGenChangeListener, IProjectImageInfoListener {

	final QualifiedName activePagePropertyName = new QualifiedName(UIDesignerPlugin.PLUGIN_ID, "activePage"); //$NON-NLS-1$
	
	protected List saveListeners;
	private IDesignerDataModel dataModel;
	private DesignerDataModelEditorResourceListener resourceListener;
	private EditValidatingCommandStack commandStack;
	// since FormEditor only notifies when page changes to FormPage,
	// we'll notify for embedded editors
	private ListenerList<IPageChangedListener> pageListeners;
	private IStatus openErrorStatus;

	public AbstractDesignerDataModelEditor() {
		commandStack = new EditValidatingCommandStack();
		pageListeners = new ListenerList<IPageChangedListener>();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (dataModel != null) {
			disableExternalChangeListeners();
			dataModel.dispose();
		}
		if (resourceListener != null)
			resourceListener.dispose();
		EditorServices.removeEditor(this);
	}
	
	public Object getAdapter(Class adapter) {
		if (adapter.isAssignableFrom(getClass()))
			return this;
		else if (adapter.equals(IResource.class) && dataModel != null) {
           return dataModel.getModelSpecifier().getPrimaryResource();
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void setInput(IEditorInput input) {
		super.setInputWithNotify(input);
		IFile file = ResourceUtil.getFile(input);
		setPartName(file.getName()); // set title of editor doc window
	}

	public void addSaveListener(SaveListener listener) {
		if (saveListeners == null)
			saveListeners = new ArrayList();
		
		if (!saveListeners.contains(listener))
			saveListeners.add(listener);
	}

	public void removeSaveListener(SaveListener listener) {
		if (saveListeners == null)
			return;
		
		saveListeners.remove(listener);
	}
	
    protected boolean fireQueryAboutToSave() {
    	if (saveListeners == null)
    		return true;
    	
    	for (Iterator iter = (new ArrayList(saveListeners)).iterator(); iter.hasNext();) {
    		SaveListener listener = (SaveListener) iter.next();
    		if (!listener.queryAboutToSave(this))
    			return false;
    	}
    	
    	return true;
    }
    
    protected void firePreSaveNotify(IProgressMonitor monitor) {
    	if (saveListeners == null)
    		return;
    	
    	for (Iterator iter = (new ArrayList(saveListeners)).iterator(); iter.hasNext();) {
    		SaveListener listener = (SaveListener) iter.next();
    		listener.preSaveNotify(this, monitor);
    	}
    }
    
    protected void firePostSaveNotify(IProgressMonitor monitor) {
		if (saveListeners == null)
			return;
		
		for (Iterator iter = (new ArrayList(saveListeners)).iterator(); iter.hasNext();) {
			SaveListener listener = (SaveListener) iter.next();
			listener.postSaveNotify(this, monitor);
		}
	}


	protected void enableExternalChangeListeners() {
		IProjectContext projectContext = dataModel != null ? dataModel.getProjectContext() : null;
		if (projectContext != null && projectContext.getSourceGenProvider() != null) {
			// list for changes to source
			projectContext.getSourceGenProvider().addSourceChangeListener(this);
			// scan for changes since last open
			projectContext.getSourceGenProvider().fireSourceChanged(true);
		}
		if (dataModel != null) {
			IProjectImageInfo projectImageInfo = ModelUtils.getProjectImageInfo(dataModel.getRoot());
			projectImageInfo.addListener(this);
		}
		
	}
	
	protected void disableExternalChangeListeners() {
		IProjectContext projectContext = dataModel.getProjectContext();
		if (projectContext != null && projectContext.getSourceGenProvider() != null) {
			projectContext.getSourceGenProvider().removeSourceChangeListener(this);
		}
		IProjectImageInfo projectImageInfo = ModelUtils.getProjectImageInfo(dataModel.getRoot());
		if (projectImageInfo != null)
			projectImageInfo.removeListener(this);
		
	}

	public void setListenToResourceChanges(boolean listen) {
		if (listen)
			resourceListener.postSaveNotify(this, null);
		else
			resourceListener.preSaveNotify(this, null);
	}

	public IDesignerDataModel getDataModel() {
		return dataModel;
	}
	
	@Override
	public boolean isDirty() {
        if (commandStack == null || !commandStack.isDirty())
        	return super.isDirty();
        
        return true;
	}

	public void applyModifiedSourceChanges(Command command) {
		ApplySourceChangesCommand gefCommand = 
			new ApplySourceChangesCommand(this, getListeningDataModel());
		gefCommand.setDataModelCommand(command);
		commandStack.execute(gefCommand);
	}

	public IDesignerDataModel getListeningDataModel() {
		if (commandStack != null)
			return dataModel;
		else
			return null;
	}
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		IStatus status = EditorServices.runWithProgressAndStatus(
				(Shell) getAdapter(Shell.class),
				new IRunnableWithProgressAndStatus() {
					public IStatus run(IProgressMonitor monitor) {
						return loadInput(monitor);
					}
				});
		if (status != null) {
			dataModel = null;
			openErrorStatus = status;
		}
	}
	
	protected IStatus preLoadInput(IProgressMonitor monitor) {
		return Status.OK_STATUS;
	}

	protected IStatus postLoadInput(IProgressMonitor monitor) {
		return Status.OK_STATUS;
	}
	
	/**
	 * Begins and ends the progress monitor and calls doLoadInput
	 * @param monitor
	 * @return
	 */
	protected IStatus loadInput(IProgressMonitor monitor) {
		monitor.beginTask(Strings.getString("AbstractDesignerDataModelEditor.LoadingDesignMessage"), 6); //$NON-NLS-1$
		
		IStatus status = preLoadInput(monitor); 
			
		if (status == null || status.isOK())
			status = doLoadInput(monitor);
		
		if (status == null || status.isOK()) {
			status = postLoadInput(monitor);
		
			Collection<IModelMessage> messages = ModelMessages.validateAndUpdateMarkers(dataModel);
			Shell shell = (Shell)getAdapter(Shell.class);
			Dialog loadProblemsDialog = EditorServices.createModelValidationMessagesDialog(shell, dataModel, messages);
			ShowDialogWhenWorkbenchVisibleJob.start(shell, loadProblemsDialog);

			monitor.worked(1);
		}
		
		monitor.done();
		return status;
	}

	/**
	 * Does the work of loading the input, but assumes the progress monitor has begun and does not end it
	 * @param monitor
	 * @return IStatus
	 */
	protected IStatus doLoadInput(IProgressMonitor monitor) {
		IFile file = ResourceUtil.getFile(getEditorInput());

		if ((file == null) || !file.exists()) {
			if (file != null && !file.getProject().isOpen())
				return EditorUtils.makeOrReportEditorOpenStatus(
						Strings.getString("AbstractDesignerDataModelEditor.LoadFailedProjectClosedError"), null); //$NON-NLS-1$
			return EditorUtils.makeOrReportEditorOpenStatus(
					Strings.getString("AbstractDesignerDataModelEditor.LoadFailedNoFileError"), null); //$NON-NLS-1$
		}

		WorkspaceContext wc = WorkspaceContext.getContext();
		IDesignerDataModelSpecifier modelSpecifier = wc.findSpecifierForResource(file);
		boolean neededUpdating = false;
		IProject project = file.getProject();
			
		// first we check if the project needs updating
		IProjectUpdateManager pum = CarbideUpdaterPlugin.getProjectUpdateManager();
		if (modelSpecifier == null && pum.projectNeedsUpdate(project, null)) {
			EditorUtils.tryToUpdateProject(project);
			modelSpecifier = wc.findSpecifierForResource(file);
			neededUpdating = true;
		}

		if (modelSpecifier == null) {
			String errorString = neededUpdating ?
					Strings.getString("AbstractDesignerDataModelEditor.LoadFailedNeedsUpdatingError") : //$NON-NLS-1$
						Strings.getString("AbstractDesignerDataModelEditor.LoadFailedNoSpecifierError"); //$NON-NLS-1$
			return EditorUtils.makeOrReportEditorOpenStatus(errorString, null);
		}
		
		monitor.worked(1);

		String failureMsg = modelSpecifier.isEditable();
		if (failureMsg != null)
			return EditorUtils.makeOrReportEditorOpenStatus(failureMsg, null); //$NON-NLS-1$
		
		StatusHolder holder = new StatusHolder();
		dataModel = EditorUtils.loadModel(modelSpecifier, holder);
		IStatus status = holder.getStatus();
		if (status != null && !status.isOK())
			return status;

		// now check if the model needs updating
		if (!neededUpdating && !dataModel.isModelUpToDate()) {
			boolean wantsUpdate = MessageDialog.openQuestion((Shell) getAdapter(Shell.class), 
				Strings.getString("AbstractDesignerDataModelEditor.LoadUpgradeQueryTitle"), //$NON-NLS-1$
				Strings.getString("AbstractDesignerDataModelEditor.LoadUpgradeQuery")); //$NON-NLS-1$
			if (wantsUpdate) {
				dataModel.dispose();
				EditorUtils.tryToUpgradeProjectDesigns(project);
				dataModel = EditorUtils.loadModel(modelSpecifier, holder);
				status = holder.getStatus();
				if (status != null && !status.isOK())
					return status;
			}
		}
			
		Check.checkState(dataModel != null);
		resourceListener = new DesignerDataModelEditorResourceListener(this);
		commandStack.initialize(getSite().getShell(), dataModel);
		EditorServices.addEditor(this);

		monitor.worked(1);

		enableExternalChangeListeners();
		
		monitor.worked(1);

		return Status.OK_STATUS;
	}


	private boolean checkProjectExists() {
		IProject project = null;
		IProjectContext projectContext = dataModel.getProjectContext();
		if (projectContext != null)
			project = projectContext.getProject();
		if (project == null) {
			String message = Strings.getString("AbstractDesignerDataModelEditor.NoProjectMessage"); //$NON-NLS-1$
			IStatus status = Logging.newStatus(UIDesignerPlugin.getDefault(), IStatus.ERROR, message );
			Logging.showErrorDialog(null, Strings.getString("AbstractDesignerDataModelEditor.SavingFailedError"), status); //$NON-NLS-1$
			return false;
		}
		return true;
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		if (!checkProjectExists())
			return;
		
		IStatus status = dataModel.validateEdit(getEditorSite().getShell());
		if (!status.isOK()) {
			return;
		}
		
		if (!fireQueryAboutToSave())
			return;
		
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

		try {
			dataModel.saveModel(monitor);
			if (commandStack != null)
				commandStack.markSaveLocation();
			Collection<IModelMessage> messages = ModelMessages.validateAndUpdateMarkers(dataModel);
			
			Shell shell = (Shell) getAdapter(Shell.class);
			//EditorServices.notifyIfDataModelSourceGenProblems(shell, dataModel);
			Dialog dialog = EditorServices.createModelValidationMessagesDialog(shell, dataModel, messages);
			dialog.open();
		} 
		catch (Exception e) {
			status = Logging.newStatus(UIDesignerPlugin.getDefault(), e);
			Logging.log(UIDesignerPlugin.getDefault(), status);
			Logging.showErrorDialog(null, Strings.getString("AbstractDesignerDataModelEditor.SavingFailedError"), status); //$NON-NLS-1$
		}
		finally {
			firePostSaveNotify(monitor);
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSaveAs() {
		// not allowed
	}

	protected void reloadDataModel() {
		IDesignerDataModelSpecifier spec = dataModel.getModelSpecifier();
		dataModel.dispose();
		LoadResult lr = spec.load();
		Check.checkState(lr != null);
		dataModel = lr.getModel();
		Check.checkState(dataModel != null);
	}
	
	protected void reloadPageData() {
		if (pages != null) {
			for (Object page : pages) {
				if (page instanceof IDesignerDataModelEditorPage) {
					((IDesignerDataModelEditorPage) page).reload();
				}
			}
		}
	}
	
	public void reload() {
		EditorServices.removeEditor(this);
		reloadDataModel();
		reloadPageData();
		flushCommandStack();
		editorDirtyStateChanged();
		enableExternalChangeListeners();
		EditorServices.addEditor(this);
	}

	protected void flushCommandStack() {
		commandStack.flush();
		commandStack.initialize(getSite().getShell(), dataModel);
	}
	
	protected void setCommandStack(EditValidatingCommandStack commandStack) {
		this.commandStack = commandStack;
	}
	
	public CommandStack getCommandStack() {
		return commandStack;
	}
	
	public FormEditor getFormEditor() {
		return this;
	}
	
	protected void initializeExtenders() throws CoreException {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = 
			extensionRegistry.getExtensionPoint(IDesignerDataModelEditorExtender.EXTENSION_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			Check.checkContract(elements.length == 1);
			IConfigurationElement element = elements[0];
			String filter = 
				element.getAttribute(IDesignerDataModelEditorExtender.EDITOR_ID_FILTER);
			if (filter == null || filter.length() == 0 || 
					getEditorSite().getId().matches(filter)) {
				initializeExtender(element);
			}
		}
		IResource resource = (IResource) getAdapter(IResource.class);
		if (resource != null) {		
			try {
				Object pageIndexObj = resource.getSessionProperty(activePagePropertyName);
				if (pageIndexObj instanceof Integer) {
					int index = ((Integer) pageIndexObj).intValue();
					int pageCount = getPageCount();
					if (index < pageCount)
						setActivePage(index);
				}
			} catch (CoreException e) {
				Logging.log(UIDesignerPlugin.getDefault(), e.getStatus());
			}	
		}

	}

	private void initializeExtender(IConfigurationElement element) throws CoreException {
		IDesignerDataModelEditorExtender editorExtenders = (IDesignerDataModelEditorExtender) 
			element.createExecutableExtension(IDesignerDataModelEditorExtender.EXTENDER);
		Check.checkContract(editorExtenders != null);
		editorExtenders.editorInitialized(this);
	}

	public void setTabTitle(int pageIndex, String title) {
		setPageText(pageIndex, title);
	}
	
	public void activatePage(int pageIndex) {
		if (getActivePage() != pageIndex)
			setActivePage(pageIndex);
	}

	public void addPageChangedListener(IPageChangedListener listener) {
		super.addPageChangedListener(listener);
		pageListeners.add(listener);
	}

	public void removePageChangedListener(IPageChangedListener listener) {
		super.removePageChangedListener(listener);
		pageListeners.remove(listener);
	}

	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		IFormPage formPage = getActivePageInstance();
		if (formPage == null) {
			Object page = pages.get(newPageIndex);
			firePageChanged(new PageChangedEvent(this, page));
		}
		IResource resource = (IResource) getAdapter(IResource.class);
		if (resource != null) {
			try {
				resource.setSessionProperty(activePagePropertyName, new Integer(newPageIndex));
			} catch (CoreException e) {
				Logging.log(UIDesignerPlugin.getDefault(), e.getStatus());
			}
		}
	}

	private void firePageChanged(final PageChangedEvent event) {
		for (IPageChangedListener l : pageListeners) {
			l.pageChanged(event);
		}
	}
	
	public void dirtyNotification(IProjectImageInfo info) {
		info.refresh();
	}
	
	public void changed(IProjectImageInfo info) {
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				EObject[] rootContainers = dataModel.getRootContainers();
				if (rootContainers != null && rootContainers.length > 0) {
					ILayoutObject object = Adapters.getLayoutObject(rootContainers[0]);
					if (object != null)
						object.forceRedraw();
				}
			}
		});
	}
	
	public boolean hasOpenError() {
		return openErrorStatus != null;
	}
	
	protected void addErrorPage() {
		ErrorPage errorPage = 
			new ErrorPage(this, "error", Strings.getString("AbstractDesignerDataModelEditor.ErrorTabTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		String error = 
			MessageFormat.format(Strings.getString("AbstractDesignerDataModelEditor.OpenErrorTitle"), new Object[] { getTitle() }); //$NON-NLS-1$
		errorPage.setTitleString(error);
		errorPage.setErrorString(openErrorStatus.getMessage());
		try {
			addPage(errorPage);
		} catch (PartInitException e) {
			Logging.log(UIDesignerPlugin.getDefault(), e.getStatus());
		}
	}
}