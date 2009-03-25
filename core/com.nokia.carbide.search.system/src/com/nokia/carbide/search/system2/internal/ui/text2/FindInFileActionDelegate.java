/*******************************************************************************
 * Copyright (c) 2006, 2008 Wind River Systems, Nokia and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Markus Schorn - initial API and implementation 
 *******************************************************************************/

package com.nokia.carbide.search.system2.internal.ui.text2;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.ide.FileStoreEditorInput;

import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider;
import com.nokia.carbide.search.system2.internal.ui.SearchMessages;



public class FindInFileActionDelegate extends FindInRecentScopeActionDelegate {
	private IEditorPart fEditor= null;
	
	public FindInFileActionDelegate() {
		super(SearchMessages.FindInFileActionDelegate_text);
		setActionDefinitionId("com.nokia.carbide.search.system.ui.performTextSearchFile"); //$NON-NLS-1$
	}

	public void selectionChanged(IAction action, ISelection selection) {
		fEditor= null;
		IWorkbenchPage page= getWorkbenchPage();
		if (page != null) {
			IWorkbenchPart part= page.getActivePart();
			if (part instanceof IEditorPart) {
				IEditorPart editor= (IEditorPart) part;
				if (   (editor.getEditorInput() instanceof IFileEditorInput)
					|| (editor.getEditorInput() instanceof FileStoreEditorInput)) {
					fEditor= editor;
				}
			}
		}
		action.setEnabled(fEditor != null);
	}

	public void setActiveEditor(IAction action, IEditorPart editor) {
		if (   (editor != null)
			&& (   (editor.getEditorInput() instanceof IFileEditorInput)
				|| (editor.getEditorInput() instanceof FileStoreEditorInput)))
			{
			fEditor= editor;
		} else {
			fEditor= null;
		}
		super.setActiveEditor(action, editor);
	}

	
	private IFile getFile() {
		if (fEditor != null) {
			IEditorInput ei= fEditor.getEditorInput();
			if (ei instanceof IFileEditorInput) {
				return ((IFileEditorInput) ei).getFile();
			}
		}
		return null;
	}

	protected ISearchQuery createQuery(TextSearchQueryProvider provider, String searchForString) throws CoreException {
		return provider.createQuery(searchForString, new IResource[] { getFile() });
	}
	
}
