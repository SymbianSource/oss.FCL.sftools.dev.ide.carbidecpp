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

package com.nokia.carbide.cpp.internal.news.reader.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.IFeedCacheChangedlistener;
import com.nokia.carbide.cpp.internal.news.reader.ui.NewsPreferenceConstants;

/**
 * An editor for hosting Carbide.c++ news page.
 */
public class NewsEditor extends FormEditor implements INewsEditor,
		IFeedCacheChangedlistener {

	// The ID of this editor
	public static final String EDITOR_ID = "com.nokia.carbide.cpp.news.reader.NewsEditor";

	// private contents
	private static NewsEditorInput editorInput;
	private NewsPage newsPage;

	/**
	 * The Constructor.
	 */
	public NewsEditor() {
		FeedCacheManager.addFeedCacheChangedListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			addNewsPage();
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error Creating Page", null, e.getStatus());
		}
	}

	/**
	 * Add the Carbide.c++ news page.
	 * @throws PartInitException
	 */
	private void addNewsPage() throws PartInitException {
		newsPage = new NewsPage(this);
		addPage(newsPage);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
	 */
	public void dispose() {
		FeedCacheManager.removeFeedCacheChangedListener(this);
		super.dispose();
	}

	/**
	 * Refresh contents of the Carbide.c++ news page associated with this editor.
	 */
	public void doRefresh() {
		getNewsPage().refresh();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
	}

	/**
	 * Perform the necessary updates in response to local feed cache changes.
	 */
	public void feedCacheChanged(boolean alertUser) {
		// perform update in UI thread
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					doRefresh();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(INewsEditor.class))
			return this;
		return super.getAdapter(adapter);
	}

	/**
	 * Return the shared editor input.
	 */
	public static NewsEditorInput getDefaultInput() {
		if (editorInput == null) {
			editorInput = new NewsEditorInput();
		}
		return editorInput;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.carbide.cpp.news.reader.editor.INewsEditor#getNewsPage()
	 */
	public NewsPage getNewsPage() {
		return newsPage;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Launch the Carbide.c++ news page during startup?
	 * @return true if Carbide.c++ news page should be launched during startup; false otherwise
	 */
	public static boolean isLaunchedAtStartup() {
		IPreferenceStore store = CarbideNewsReaderPlugin.getPrefsStore();
		boolean launchAtStartup = store.getBoolean(NewsPreferenceConstants.LAUNCH_AT_STARTUP);
		return launchAtStartup;
	}

	/**
	 * Open the Carbide.c++ news page in the editor area.
	 */
	public static void openEditor() {
		try {
			NewsEditorInput input = getDefaultInput();
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			if (workbenchWindow != null) {
				IWorkbenchPage activePage = workbenchWindow.getActivePage();
				if (activePage != null) {
					activePage.openEditor(input, EDITOR_ID);
				}
			}
		} catch (Exception e) {
			CarbideNewsReaderPlugin.log(e);
		}
	}

}
