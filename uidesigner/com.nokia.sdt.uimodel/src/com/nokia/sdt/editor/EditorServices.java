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


package com.nokia.sdt.editor;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.uimodel.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.sdt.utils.IRunnableWithProgressAndStatus;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.UITaskUtils;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

public class EditorServices {

    public interface EditorListener {
    	void editorAdded(IEditorPart editorPart);
    	void editorRemoved(IEditorPart editorPart);
	}

	// Use LinkedHashSet to maintain editor ordering on revert
	private static Collection<IEditorPart> openEditors = new LinkedHashSet<IEditorPart>();
	private static Collection listeners;
	private static boolean stiflingProgress;
	
	public static void addEditor(IEditorPart editorPart) {
        synchronized (openEditors) {
            openEditors.add(editorPart);
            fireEditorAdded(editorPart);
        }
	}
	
	public static void removeEditor(IEditorPart editorPart) {
        synchronized (openEditors) {
            openEditors.remove(editorPart);
            fireEditorRemoved(editorPart);
        }
    	WorkspaceContext.getContext().scheduleUnloadJob();
	}
	
	public static void saveAll(IProgressMonitor monitor) {
        synchronized (openEditors) {
            monitor.beginTask(Messages.getString("EditorServices.SavingAllModels"), //$NON-NLS-1$
                    openEditors.size());
            try {
        		for (Iterator iter = openEditors.iterator(); iter.hasNext();) {
        			IEditorPart editor = (IEditorPart) iter.next();
        			editor.doSave(new SubProgressMonitor(monitor, 1));
                    if (monitor.isCanceled())
                        break;
        		}
            } finally {
                monitor.done();
            }
        }
	}

    public static void saveAllModified(IProgressMonitor monitor) {
        synchronized (openEditors) {
            monitor.beginTask(Messages.getString("EditorServices.SavingAllModifiedModels"), //$NON-NLS-1$
                    openEditors.size());
            try {
                for (Iterator iter = openEditors.iterator(); iter.hasNext();) {
                    IEditorPart editor = (IEditorPart) iter.next();
                    if (editor.isDirty())
                        editor.doSave(new SubProgressMonitor(monitor, 1));
                    else
                        monitor.worked(1);
                    if (monitor.isCanceled())
                        break;
                }
            } finally {
                monitor.done();
            }
        }
    }
    

	public static void reloadAll(IProgressMonitor monitor) {
        // copy the editors list since opening/saving/reloading modifies it
		synchronized (openEditors) {
	        Collection editors = new ArrayList(openEditors);
	        monitor.beginTask(Messages.getString("EditorServices.ReloadingAllModels"), //$NON-NLS-1$
	                openEditors.size());
	        try {
	        	// force the list to be empty and re-add ones that work so broken designs are flushed
	        	openEditors.clear();
	            for (Iterator iter = editors.iterator(); iter.hasNext();) {
	                IDesignerDataModelEditor editor = (IDesignerDataModelEditor) iter.next();
	                editor.reload();
	                monitor.worked(1);
	                if (monitor.isCanceled())
	                    break;
	            }
	        } finally {
	            monitor.done();
	        }
		}
	}
	
	public static IEditorPart findEditor(IDesignerDataModel dataModel) {
		synchronized (openEditors) {
			for (Iterator iter = openEditors.iterator(); iter.hasNext();) {
				IDesignerDataModelEditor editor = (IDesignerDataModelEditor) iter.next();
				IDesignerDataModel dm = editor.getDataModel();
				if ((dm != null) && dm.getModelSpecifier().equals(dataModel.getModelSpecifier()))
					return (IEditorPart) editor.getAdapter(IEditorPart.class);
			}
			return null;
		}
		
	}
	
	/**
	 * Save the editor 
	 * @param editor a specific editor, or null for all open editors
	 * @return true for success, false for failure
	 */
	public static boolean saveEditor(final IEditorPart editor) {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				if (editor != null)
					editor.doSave(monitor);
				else
					EditorServices.saveAll(monitor);
			}
		};
		
		return UITaskUtils.runImmediately(runnable);
	}
	
	public synchronized static void addListener(EditorListener listener) {
		if (listeners == null)
			listeners = new ArrayList();
		
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public synchronized static void removeListener(EditorListener listener) {
		if (listeners == null)
			return;
		
		listeners.remove(listener);
	}
	
    private synchronized static void fireEditorAdded(IEditorPart editorPart) {
    	if (listeners == null)
    		return;
    	
    	for (Iterator iter = (new ArrayList(listeners)).iterator(); iter.hasNext();) {
    		EditorListener listener = (EditorListener) iter.next();
    		listener.editorAdded(editorPart);
    	}
    }
    
    private synchronized static void fireEditorRemoved(IEditorPart editorPart) {
		if (listeners == null)
			return;
		
		for (Iterator iter = (new ArrayList(listeners)).iterator(); iter.hasNext();) {
			EditorListener listener = (EditorListener) iter.next();
			listener.editorRemoved(editorPart);
		}
	}

	/**
	 * Get an array of currently open editors.
	 * @return array (copy) of editor parts 
	 */
	public synchronized static IEditorPart[] getEditors() {
		return (IEditorPart[]) openEditors.toArray(new IEditorPart[openEditors.size()]);
	}
	
	/**
	 * Return only the open editors with files in the given project
	 * @param project
	 */
	public synchronized static IDesignerDataModelEditor[] findEditorsForProject(IProject project) {
		if (project == null) return null;
		ArrayList<IDesignerDataModelEditor> result = new ArrayList<IDesignerDataModelEditor>();
		for (IEditorPart editorPart : openEditors) {
			if (editorPart instanceof IDesignerDataModelEditor) {
				IDesignerDataModelEditor ddme = (IDesignerDataModelEditor) editorPart;
				if (ddme.getDataModel() != null
						&& ddme.getDataModel().getProjectContext() != null
						&& project == ddme.getDataModel().getProjectContext().getProject()) {
					result.add(ddme);
				}
			}
		}
		return result.toArray(new IDesignerDataModelEditor[result.size()]);
	}
	
	public synchronized static IDesignerDataModelEditor findEditorForModel(IDesignerDataModelSpecifier modelSpecifier) {
		IDesignerDataModelEditor result = null;
		if (modelSpecifier != null) {
			for (IEditorPart editorPart : openEditors) {
				if (editorPart instanceof IDesignerDataModelEditor) {
					IDesignerDataModelEditor ddme = (IDesignerDataModelEditor) editorPart;
					if (modelSpecifier.equals(ddme.getDataModel().getModelSpecifier())) {
						result = ddme;
						break;
					}				
				}
			}
		}
		return result;
	}
	
	/**
	 * Utility to save, with user confirmation, any of the given models that have open, 
	 * dirty editors.
	 * The API takes a list so it can use the Eclipse API to present the list of
	 * dirty resources in a single dialog where appropriate. 
	 * @param models collection of models to save
	 * @return true if the save succeeded, false if the user canceled saving an affected editro
	 */
	public synchronized static boolean confirmSaveDirtyModels(
			Collection<IDesignerDataModelSpecifier> modelSpecifiers) {
		
		// make a list of all editors onto these models, and a list of resources
		// for dirty editors only
		ArrayList<IDesignerDataModelEditor> editors = new ArrayList<IDesignerDataModelEditor>();
		ArrayList<IResource> dirtyModelResources = new ArrayList<IResource>();
		for (IDesignerDataModelSpecifier specifier : modelSpecifiers) {
			IDesignerDataModelEditor ddme = findEditorForModel(specifier);
			if (ddme != null) {
				editors.add(ddme);
				if (ddme.isDirty()) {
					dirtyModelResources.add(specifier.getPrimaryResource());
				}
			}
		}
		boolean result = true;
		if (dirtyModelResources.size() > 0) {
			result = IDE.saveAllEditors(dirtyModelResources.toArray(new IResource[dirtyModelResources.size()]), true);
		}
		return result;
	}
			
	/**
	 * Utility to save a model, coordinating with any editor open
	 * on the model.
	 * @param model the model to save
	 * @param monitor progress monitor to use, can be null
	 * @return true if the save succeeded, false if the user canceled saving an affected editro
	 */
	public synchronized static void saveModels(Collection<IDesignerDataModel> models, 
			IProgressMonitor monitor) throws Exception {
				
		ArrayList<IDesignerDataModelEditor> editors = new ArrayList<IDesignerDataModelEditor>();
		for (IDesignerDataModel model : models) {
			IDesignerDataModelSpecifier specifier = model.getModelSpecifier();
			IDesignerDataModelEditor ddme = findEditorForModel(specifier);
			if (ddme != null) {
				editors.add(ddme);
				ddme.setListenToResourceChanges(false);
			}
		}
				
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
			
		Exception caughtException = null;
		for (IDesignerDataModel model : models) {
			try {
				model.saveModel(monitor);
				notifyIfDataModelSourceGenProblems(null, model);
			} catch (Exception x) {
				caughtException = x;
			}
			if (caughtException != null) {
				break;
			}
		}
		
		for (IDesignerDataModelEditor ddme : editors) {
			ddme.setListenToResourceChanges(true);
			ddme.reload();
		}
		
		if (caughtException != null) {
			throw caughtException;
		}
	}
	
	/**
	 * Tell whether any editors are open.  
	 * @return true: yup, false: no way
	 */
	public static boolean isAnyEditorOpen() {
		return openEditors.size() > 0;
	}
	
	/**
	 * Override the automatic detection of whether to show a progress dialog.
	 * @param stifle true: don't show progress
	 */
	public static void setStifleProgress(boolean stifle) {
		stiflingProgress = stifle;
	}
	
	/**
	 * Run a task, showing a progress dialog only when the workbench
	 * is up and no editors are open, when we presume that load times 
	 * will be much longer.
	 * @param shell
	 * @param runWithStatus
	 * @return null for success, else IStatus for error
	 */
	public static IStatus runWithProgressAndStatus(Shell shell, final IRunnableWithProgressAndStatus runWithStatus) {
		final IStatus status[] = { null };
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				status[0] = runWithStatus.run(monitor);
			}
		};
		
		try {
			// the first load is likely to take long, so show a dialog in this case
			if (!stiflingProgress
					&& !EditorServices.isAnyEditorOpen() 
					&& EditorServices.isEditorVisible(shell)) 
				new ProgressMonitorDialog(shell).run(false, false, runnable);
			else
				runnable.run(new NullProgressMonitor());

			if (status[0] == null || status[0] == Status.OK_STATUS)
				return null;
			else
				return status[0];
		} catch (Exception e) {
			return Logging.newStatus(UIModelPlugin.getDefault(), e);
		}
		
	}

	/**
	 * Check whether the model's sourcegen session has reported any problems.
	 * If so, show a dialog to alert the user.
	 * @param shell
	 * @param model
	 */
	public static void notifyIfDataModelSourceGenProblems(Shell shell, IDesignerDataModel model) {
		if (!Platform.isRunning())
			return;
		
		ISourceGenSession session = model.getSourceGenSession();
		if (session == null)
			return;
		
		Collection<IMessage> messages = session.getMessages();
		boolean needToShow = false;
		
		// ignore info messages
		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			IMessage message = (IMessage) iter.next();
			if (message.getSeverity() == IMessage.ERROR || message.getSeverity() == IMessage.WARNING) {
				needToShow = true;
				break;
			}
		}
		if (!needToShow)
			return;
		
		SourceGenProblemsDialog dialog = SourceGenProblemsDialog.create(shell, model.getModelSpecifier().getDisplayName(), session.getMessages());
		dialog.open();
	}
	
	/**
	 * Tell whether the editor is visible, i.e. whether the given shell
	 * is showing.  If null is passed, find whether any Eclipse window is visible.
	 * @param shell the shell to test, or null for any Eclipse shell
	 * @return
	 */
	public static boolean isEditorVisible(Shell shell) {
		if (shell != null)
			return shell.isVisible();

		if (!Platform.isRunning())
			return false;
		
		final IWorkbench workbench;
		try {
			workbench = PlatformUI.getWorkbench();
		} catch (IllegalStateException e) {
			return false;
		}
		
		final IWorkbenchWindow[] activeWorkbenchWindow = { null };
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				activeWorkbenchWindow[0] = workbench.getActiveWorkbenchWindow();
			}
		}); 
		if (activeWorkbenchWindow[0] == null)
			return false;
		
		shell = activeWorkbenchWindow[0].getShell();
		if (shell == null)
			return false;
		
		return shell.isVisible();
	}
	
	/**
	 * Create a dialog which reports model validation messages to the user.  
	 * @param shell the shell, or null
	 * @param model the model to mention
	 * @param messages list of messages; if empty, the dialog is still created but it will not be shown on Dialog#open().
	 * @return Dialog (never null)
	 */
	public static Dialog createModelValidationMessagesDialog(Shell shell, IDesignerDataModel model, Collection<IModelMessage> messages) {
		// make mostly dummy IStatuses so we can use the way-cool error dialog
		List<IStatus> statusList = new ArrayList();
		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			IModelMessage message = (IModelMessage) iter.next();
			String messageString = message.getMessage();
			String[] strings = messageString.split("\\n|\\r\\n");
			for (String string : strings) {
				statusList.add(new Status(message.getSeverity(), UIModelPlugin.PLUGIN_ID, IStatus.OK, 
						string, null));
			}
		}
		
		MultiStatus status = new MultiStatus(UIModelPlugin.PLUGIN_ID,
				IStatus.OK,
				(IStatus[]) statusList.toArray(new IStatus[statusList.size()]), 
				MessageFormat.format(
						Messages.getString("EditorServices.ValidationProblemsText"), //$NON-NLS-1$
						new Object[] { model.getModelSpecifier().getDisplayName() }),
				null);
		
		ErrorDialog dialog = new ErrorDialog(shell, Messages.getString("EditorServices.ValidationProblemsTitle"), null, status,  //$NON-NLS-1$
				IStatus.ERROR | IStatus.WARNING);
		return dialog;
	}
}
