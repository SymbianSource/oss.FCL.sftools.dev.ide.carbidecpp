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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Item;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.internal.ui.util.ExceptionHandler;
import com.nokia.carbide.search.system.ui.NewSearchUI;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchResult;
import com.nokia.carbide.search.system.ui.text.Match;

/* package */ class ReplaceAction2 extends Action {
	
	private IWorkbenchSite fSite;
	private IFileStore[] fElements;
	private FileSearchPage fPage;
	
	private static class ItemIterator implements Iterator {
		private Item[] fArray;
		private int fNextPosition;
		ItemIterator(Item[] array) {
			fArray= array;
			fNextPosition= 0;
		}

		public boolean hasNext() {
			return fNextPosition < fArray.length;
		}

		public Object next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return fArray[fNextPosition++].getData();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public ReplaceAction2(FileSearchPage page, IFileStore[] elements) {
		Assert.isNotNull(page);
		fSite= page.getSite();
		if (elements != null)
			fElements= elements;
		else
			fElements= new IFileStore[0];
		fPage= page;
		
		setText(SearchMessages.ReplaceAction_label_all); 
		setEnabled(!(fElements.length == 0));
	}
	
	public ReplaceAction2(FileSearchPage page) {
		Assert.isNotNull(page);
		fSite= page.getSite();
		fPage= page;
		
		Item[] items= null;
		StructuredViewer viewer= fPage.getViewer();
		if (viewer instanceof TreeViewer) {
			items= ((TreeViewer)viewer).getTree().getItems();
		} else if (viewer instanceof TableViewer) {
			items= ((TableViewer)viewer).getTable().getItems();
		}
		fElements= collectFiles(new ItemIterator(items));
		
		setText(SearchMessages.ReplaceAction_label_all); 
		setEnabled(!(fElements.length == 0));
	}

	
	public ReplaceAction2(FileSearchPage page, IStructuredSelection selection) {
		fSite= page.getSite();
		fPage= page;
		setText(SearchMessages.ReplaceAction_label_selected); 
		fElements= collectFiles(selection.iterator());
		setEnabled(!(fElements.length == 0));
	}
	
	private IFileStore[] collectFiles(Iterator resources) {
		final Set files= new HashSet();
		final AbstractTextSearchResult result= fPage.getInput();
		if (result == null)
			return new IFileStore[0];
		while (resources.hasNext()) {
			IFileStore file = (IFileStore) resources.next();
			if (result.getMatchCount(file) > 0)
				files.add(file);
		}
		return (IFileStore[]) files.toArray(new IFileStore[files.size()]);
	}


	public void run() {
		IWorkspace workspace= ResourcesPlugin.getWorkspace();
		ISchedulingRule rule= workspace.getRuleFactory().modifyRule(workspace.getRoot());
		try { 
			Platform.getJobManager().beginRule(rule, null);
			if (validateResources((FileSearchQuery) fPage.getInput().getQuery())) {
				ReplaceDialog2 dialog= new ReplaceDialog2(fSite.getShell(), fElements, fPage);
				dialog.open();
			}
		} catch (OperationCanceledException e) {
		} finally {
			Platform.getJobManager().endRule(rule);
		}
	}
	
	private boolean validateResources(final FileSearchQuery operation) {
		IFile[] readOnlyFiles= getReadOnlyFiles();
		IStatus status= ResourcesPlugin.getWorkspace().validateEdit(readOnlyFiles, fSite.getShell());
		if (!status.isOK()) {
			if (status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog.openError(fSite.getShell(), SearchMessages.ReplaceAction2_error_validate_title, SearchMessages.ReplaceAction2_error_validate_message, status); 
			}
			return false;
		}

		final List outOfDateEntries= new ArrayList();
		for (int j= 0; j < fElements.length; j++) {
			IFileStore entry = fElements[j];
			Match[] markers= fPage.getDisplayedMatches(entry);
			for (int i= 0; i < markers.length; i++) {
				if (isOutOfDate((FileMatch)markers[i])) {
					outOfDateEntries.add(entry);
					break;
				}				
			}
		}
	
		final List outOfSyncEntries= new ArrayList();
//		for (int i= 0; i < fElements.length; i++) {
//			IFileStore entry = fElements[i];
//			if (isOutOfSync(entry)) {
//				outOfSyncEntries.add(entry);
//			}
//		}
		
		if (outOfDateEntries.size() > 0 || outOfSyncEntries.size() > 0) {
			if (askForResearch(outOfDateEntries, outOfSyncEntries)) {
				ProgressMonitorDialog pmd= new ProgressMonitorDialog(fSite.getShell());
				try {
					pmd.run(true, true, new WorkspaceModifyOperation(null) {
						protected void execute(IProgressMonitor monitor) throws CoreException {
							research(monitor, outOfDateEntries, operation);
						}
					});
					return true;
				} catch (InvocationTargetException e) {
					ExceptionHandler.handle(e, fSite.getShell(), SearchMessages.ReplaceAction_label, SearchMessages.ReplaceAction_research_error); 
				} catch (InterruptedException e) {
					// canceled
				}
			}
			return false;
		}
		return true;
	}

	private IFile[] getReadOnlyFiles() {
		Set readOnly= new HashSet();
		for (int i = 0; i < fElements.length; i++) {
			if (fElements[i].fetchInfo().getAttribute(EFS.ATTRIBUTE_READ_ONLY))
				readOnly.add(fElements[i]);
		}
		IFile[] readOnlyArray= new IFile[readOnly.size()];
		return (IFile[]) readOnly.toArray(readOnlyArray);
	}

	private void research(IProgressMonitor monitor, List outOfDateEntries, FileSearchQuery operation) throws CoreException {
		String message= SearchMessages.ReplaceAction2_statusMessage; 
		MultiStatus multiStatus= new MultiStatus(NewSearchUI.PLUGIN_ID, IStatus.OK, message, null);
		for (Iterator elements = outOfDateEntries.iterator(); elements.hasNext();) {
			IFile entry = (IFile) elements.next();
			IStatus status = research(operation, monitor, entry);
			if (status != null && !status.isOK()) {
				multiStatus.add(status);
			}
		}
		if (!multiStatus.isOK()) {
			throw new CoreException(multiStatus);
		}
	}

	private boolean askForResearch(List outOfDateEntries, List outOfSyncEntries) {
		SearchAgainConfirmationDialog dialog= new SearchAgainConfirmationDialog(fSite.getShell(), (ILabelProvider) fPage.getViewer().getLabelProvider(), outOfSyncEntries, outOfDateEntries);
		return dialog.open() == IDialogConstants.OK_ID;
	}
	
	private boolean isOutOfDate(FileMatch match) {
		
		if (match.getCreationTimeStamp() != match.getFile().fetchInfo().getLastModified())
			return true;
		ITextFileBufferManager bm= FileBuffers.getTextFileBufferManager();
		File file = null;
		try {
			file = match.getFile().toLocalFile(EFS.NONE, null);
		} catch (CoreException e) {
			return false;
		}
		ITextFileBuffer fb= bm.getTextFileBuffer(new Path(file.getAbsolutePath()), LocationKind.IFILE);
		if (fb != null && fb.isDirty())
			return true;
		return false;
	}

//	private boolean isOutOfSync(IFileStore entry) {
//		return !entry.isSynchronized(IResource.DEPTH_ZERO); 
//	}
		
	private IStatus research(FileSearchQuery operation, final IProgressMonitor monitor, IFile entry) {
		Match[] matches= fPage.getDisplayedMatches(entry);
		IStatus status= operation.searchInFile(getResult(), monitor, entry);

		// always remove old matches
		for (int i= 0; i < matches.length; i++) {
			getResult().removeMatch(matches[i]);
		}
		return status;
	}

	private AbstractTextSearchResult getResult() {
		return fPage.getInput();
	}
	
}
