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

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.part.FileEditorInput;

import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.ui.NewSearchUI;

public class EditorOpener {

	private IEditorReference fReusedEditor;
	
	public IEditorPart open(IFileStore file, boolean activate) throws PartInitException {
		IWorkbenchPage wbPage= SearchPlugin.getActivePage();
		if (NewSearchUI.reuseEditor())
			return showWithReuse(file, wbPage, activate);
		return showWithoutReuse(file, wbPage, activate);
	}
	
	private void process(IFileStore fileStore) {

		String editorId = null;
		IEditorDescriptor des = getDefaultEditor(fileStore);
		if (des != null)
		{
			editorId = des.getId();
		}
		else
		{
			editorId = "org.eclipse.ui.DefaultTextEditor"; //$NON-NLS-1$
		}
		
		if ((fileStore == null)
			|| (!fileStore.fetchInfo().exists())
			|| (fileStore.fetchInfo().isDirectory()))
		{
			return;
		}

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, fileStore.toURI(), editorId, true);
		} catch (PartInitException e) {
			String msg =  NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, fileStore.getName());
			IDEWorkbenchPlugin.log(msg,e.getStatus());
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), IDEWorkbenchMessages.OpenLocalFileAction_title, msg);
		}
	}
	
	private IEditorPart showWithoutReuse(IFileStore file, IWorkbenchPage page, boolean activate) throws PartInitException {
		String editorId = getEditorID(file);

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		IFile[] ifiles = null;
		try {
			ifiles = root.findFilesForLocation(new Path(file.toLocalFile(EFS.NONE, null).getAbsolutePath()));
		} catch (CoreException e) {
		}

		IEditorInput input;
		
		if (ifiles != null && ifiles.length > 0)
			input= new FileEditorInput(ifiles[0]);
		else
			input = new FileStoreEditorInput(file);

		IEditorPart editor= null;
		if (ifiles != null && ifiles.length > 0) {
			editor = page.openEditor(input, editorId, activate);
		}

		try {
			if (editor == null)
				editor = IDE.openEditor(page, file.toURI(), editorId, true);
		} catch (PartInitException e) {
			String msg =  NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, file.getName());
			IDEWorkbenchPlugin.log(msg,e.getStatus());
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), IDEWorkbenchMessages.OpenLocalFileAction_title, msg);
		}

		if (editor == null)
			editor= page.openEditor(input, editorId, activate);

		return editor;
	}

	private IEditorPart showWithReuse(IFileStore file, IWorkbenchPage wbPage, boolean activate) throws PartInitException {
		String editorID= getEditorID(file);
		return showInEditor(wbPage, file, editorID, activate);
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

	private String getEditorID(IFileStore file) throws PartInitException {
		String editorId = null;
		IEditorDescriptor des = getDefaultEditor(file);
		if (des != null)
		{
			editorId = des.getId();
		}
		else
		{
			editorId = "org.eclipse.ui.DefaultTextEditor"; //$NON-NLS-1$
		}
		
		return editorId;
	}
	
	private IEditorPart showInEditor(IWorkbenchPage page, IFileStore file, String editorId, boolean activate) throws PartInitException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		IFile[] ifiles = null;
		try {
			ifiles = root.findFilesForLocation(new Path(file.toLocalFile(EFS.NONE, null).getAbsolutePath()));
		} catch (CoreException e) {
		}

		IEditorInput input;
		
		if (ifiles != null && ifiles.length > 0 && ifiles[0].exists())
			input= new FileEditorInput(ifiles[0]);
		else
			input = new FileStoreEditorInput(file);
		
		IEditorPart editor= page.findEditor(input);
		if (editor != null) {
			page.bringToTop(editor);
			if (activate) {
				page.activate(editor);
			}
			return editor;
		}
		IEditorReference reusedEditorRef= fReusedEditor;
		if (reusedEditorRef !=  null) {
			boolean isOpen= reusedEditorRef.getEditor(false) != null;
			boolean canBeReused= isOpen && !reusedEditorRef.isDirty() && !reusedEditorRef.isPinned();
			if (canBeReused) {
				boolean showsSameInputType= reusedEditorRef.getId().equals(editorId);
				if (!showsSameInputType) {
					page.closeEditors(new IEditorReference[] { reusedEditorRef }, false);
					fReusedEditor= null;
				} else {
					editor= reusedEditorRef.getEditor(true);
					if (editor instanceof IReusableEditor) {
						((IReusableEditor) editor).setInput(input);
						page.bringToTop(editor);
						if (activate) {
							page.activate(editor);
						}
						return editor;
					}
				}
			}
		}


		if (ifiles != null && ifiles.length > 0) {
			editor = page.openEditor(input, editorId, activate);
		}

		try {
			if (editor == null)
				editor = IDE.openEditor(page, file.toURI(), editorId, true);
		} catch (PartInitException e) {
			String msg =  NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, file.getName());
			IDEWorkbenchPlugin.log(msg,e.getStatus());
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), IDEWorkbenchMessages.OpenLocalFileAction_title, msg);
		}

		if (editor == null)
			editor= page.openEditor(input, editorId, activate);

		if (editor instanceof IReusableEditor) {
			IEditorReference reference= (IEditorReference) page.getReference(editor);
			fReusedEditor= reference;
		} else {
			fReusedEditor= null;
		}
		return editor;
	}

	
}
