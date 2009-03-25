/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.EditorServices.EditorListener;
import com.nokia.sdt.editor.IDesignerDataModelEditor.SaveListener;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.UITaskUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The main plugin class to be used in the desktop (auto-generated)
 */
public class SourceGenPlugin extends AbstractUIPlugin implements SaveListener, EditorListener {
	//The shared instance.
	private static SourceGenPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;

	// Pattern matcher for files which might be written by sourcegen
	static final Pattern sourcePattern = Pattern.compile(
		".*\\.(c|cpp|h|hpp|ra|rss|rssi|loc|l[0-9]{2}|rls)$"); //$NON-NLS-1$


	/**
	 * The constructor.
	 */
	public SourceGenPlugin() {
		super();
		plugin = this;
		
		// attach to any open editors
		IEditorPart[] editors = EditorServices.getEditors();
		for (int i = 0; i < editors.length; i++) {
			editorAdded(editors[i]);
		}
		EditorServices.addListener(this);
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		EditorServices.addListener(this);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		EditorServices.removeListener(this);
		plugin = null;
		resourceBundle = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static SourceGenPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = SourceGenPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		try {
			if (resourceBundle == null)
				resourceBundle = ResourceBundle.getBundle("com.nokia.sdt.sourcegen.SourceGenPluginResources"); //$NON-NLS-1$
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
		return resourceBundle;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.nokia.sdt.sourcegen", path); //$NON-NLS-1$
	}

	/**
	 */
	public void log(Throwable t) {
		IStatus status = Logging.newSimpleStatus(0, t);
		Logging.log(this, status);
	}

    public IStatus createStatus(int severity, String message, Throwable t) {
        return Logging.newStatus(this, severity, message, t);
    }
    
	static interface IEditorPartFilter {
		public boolean accept(IEditorPart part);
	}
	
	/** 
	 * Save editors that appear to be source-related.
	 */
	public boolean saveAllSourceEditors(final IEditorPartFilter filter, boolean confirm) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final boolean finalConfirm = confirm;
		final boolean[] result = new boolean[1];
		result[0] = true;

		SafeRunner.run(new SafeRunnable() {
			public void run() {
				// Collect unsaved parts
				ArrayList dirtyParts = new ArrayList();
				ArrayList dirtyEditorsInput = new ArrayList();
				IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
				for (int i = 0; i < windows.length; i++) {
					IWorkbenchPage pages[] = windows[i].getPages();
					for (int j = 0; j < pages.length; j++) {
						IWorkbenchPage page = (IWorkbenchPage) pages[j];
						
                        ISaveablePart[] parts = page.getDirtyEditors();
                        
                        for (int k = 0; k < parts.length; k++) {
                            ISaveablePart part = parts[k];
                            
                            if (part instanceof IEditorPart) {
                            	IEditorPart editor = (IEditorPart)part;
                            	if (editor.isSaveOnCloseNeeded() && filter.accept(editor)) {
                                    if (!dirtyEditorsInput.contains(editor
                                            .getEditorInput())) {
                                        dirtyParts.add(editor);
                                        dirtyEditorsInput.add(editor
                                                .getEditorInput());
                                    }
                                }
                            }
                        }
   					}
				}
				
				if (dirtyParts.size() > 0) {
					IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
					if (window == null)
						window = windows[0];
					result[0] = confirmAndSaveAll(dirtyParts, finalConfirm, window);
				}
			}
		});
		return result[0];
	}

	/**
	 * Put up a dialog showing possible sources that will be
	 * modified and allow the user to select items to save,
	 * and save the selected items.
	 * <p>
	 * This is a ripoff of the Eclipse EditorManager#saveAll()
	 * method, which is internal.
	 * @param dirtyParts
	 * @return true: saving confirmed, false: save canceled
	 */
	private boolean confirmAndSaveAll(List dirtyParts, boolean confirm, IWorkbenchWindow window) {
    	// If the editor list is empty return.
        if (dirtyParts.isEmpty())
        	return true;
        
        // Convert the list into an element collection.
        AdaptableList input = new AdaptableList(dirtyParts);

        ListSelectionDialog dlg = new ListSelectionDialog(
                window.getShell(), input,
                new BaseWorkbenchContentProvider(),
                new WorkbenchPartLabelProvider(), 
                Messages.getString("SourceGenPlugin.SelectResources")); //$NON-NLS-1$

        dlg.setInitialSelections(dirtyParts
                .toArray(new Object[dirtyParts.size()]));
        dlg.setTitle(Messages.getString("SourceGenPlugin.SaveSourcesTitle")); //$NON-NLS-1$
        int result = dlg.open();

        if (result == IDialogConstants.CANCEL_ID)
            return false;

        final List savingDirtyParts = Arrays.asList(dlg.getResult());

        final boolean[] saveSucceeded = new boolean[1];
        
		saveSucceeded[0] = true;
        UITaskUtils.runImmediately(new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask(Messages.getString("SourceGenPlugin.SavingEditors"), savingDirtyParts.size()); //$NON-NLS-1$
		        for (Iterator iter = savingDirtyParts.iterator(); iter.hasNext();) {
		        	if (monitor.isCanceled())
		        		saveSucceeded[0] = false;
		        	IEditorPart editor = (IEditorPart) iter.next();
		        	editor.doSave(monitor);
		        	monitor.worked(1);
				}
				monitor.done();
			}
        	
        });
        
        return saveSucceeded[0];
	}

	/**
	 * Check that the workspace is synchronized and in a 
	 * sane state to save sources.
	 * @return true: everything is up-to-date, false: cancel the save
	 */
	private boolean checkSynchronizedWorkspace(IDesignerDataModel dataModel) {
		// ask to save any source editors
		if (dataModel.getProjectContext() == null)
			return true;
		final IProject theProject = dataModel.getProjectContext().getProject();
		if (theProject == null)
			return true;
		
		IEditorPartFilter filter = new IEditorPartFilter() {

			public boolean accept(IEditorPart part) {
				// only look at stuff in this project
				IResource rsrc = (IResource) part.getEditorInput().getAdapter(IResource.class);
				if (rsrc != null) {
					IProject project = rsrc.getProject();
					if (project == null || !project.equals(theProject))
						return false;
				}
				String name = part.getEditorInput().getName();
				return sourcePattern.matcher(name).matches();
			}
		};
		if (!saveAllSourceEditors(filter, true))
			return false;
		
		// check for changes made outside workspace
		IProject project = null;
		if (dataModel.getProjectContext() != null
				&& (project = dataModel.getProjectContext().getProject()) != null) {
			
			final List<IPath> unsynchronizedResources = new ArrayList<IPath>();
			
			IResourceProxyVisitor visitor = new IResourceProxyVisitor() {
				public boolean visit(IResourceProxy resourceProxy) throws CoreException {
					String name = resourceProxy.getName();
					boolean looksLikeSource = sourcePattern.matcher(name).matches();
					if (looksLikeSource || resourceProxy.getType() == IResource.FOLDER) {
						IResource resource = resourceProxy.requestResource();
						if (resource != null && !resource.isSynchronized(IResource.DEPTH_ZERO))
							unsynchronizedResources.add(resource.getProjectRelativePath());
					}
					return resourceProxy.getType() == IResource.FOLDER ||
						resourceProxy.getType() == IResource.PROJECT;
				}
			};
			
			try {
				project.accept(visitor, 0);
				if (unsynchronizedResources.size() == 0)
					return true;
			} catch (CoreException e1) {
				SourceGenPlugin.getDefault().log(e1);
				// fall through, something bad happened
			}
			
			Shell shell = null;
			boolean doSync = MessageDialog.openConfirm(shell, 
					Messages.getString("SourceGenProvider.ProjectOutOfSync"), //$NON-NLS-1$
					MessageFormat.format(
							Messages.getString("SourceGenProvider.ProjectOutOfSyncMessage"), //$NON-NLS-1$
							new Object[] { project.getName(), 
								TextUtils.formatTabbedList(unsynchronizedResources) }));
			if (doSync) {
				try {
					project.refreshLocal(IResource.DEPTH_INFINITE, null);
					return true;
				} catch (CoreException e) {
					SourceGenPlugin.getDefault().log(e);
					Logging.showErrorDialog(Messages.getString("SourceGenProvider.Error"),  //$NON-NLS-1$
							null, e.getStatus());
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see com.nokia.sdt.ui.IDesignerEditorBase.SaveListener#queryAboutToSave(com.nokia.sdt.ui.IDesignerEditorBase)
	 */
	public boolean queryAboutToSave(IDesignerDataModelEditor editor) {
		return checkSynchronizedWorkspace(editor.getDataModel());
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.ui.IDesignerEditorBase.SaveListener#preSaveNotify(com.nokia.sdt.ui.IDesignerEditorBase)
	 */
	public void preSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor) {
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.ui.IDesignerEditorBase.SaveListener#postSaveNotify(com.nokia.sdt.ui.IDesignerEditorBase)
	 */
	public void postSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor) {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
	 */
	public void editorAdded(IEditorPart part) {
		IDesignerDataModelEditor editor = (IDesignerDataModelEditor) part.getAdapter(IDesignerDataModelEditor.class);
		if (editor != null)
			editor.addSaveListener(SourceGenPlugin.this);
	}

	public void editorRemoved(IEditorPart part) {
		IDesignerDataModelEditor editor = (IDesignerDataModelEditor) part.getAdapter(IDesignerDataModelEditor.class);
		if (editor != null)
			editor.removeSaveListener(SourceGenPlugin.this);
	}
}
