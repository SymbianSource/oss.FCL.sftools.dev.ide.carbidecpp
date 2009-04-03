/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation, Nokia and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Juerg Billeter, juergbi@ethz.ch - 47136 Search view should show match objects
 *     Ulrich Etter, etteru@ethz.ch - 47136 Search view should show match objects
 *     Roman Fuchs, fuchsro@ethz.ch - 47136 Search view should show match objects
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.ui.text;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.nokia.carbide.search.system.core.text.TextSearchEngine;
import com.nokia.carbide.search.system.core.text.TextSearchMatchAccess;
import com.nokia.carbide.search.system.core.text.TextSearchRequestor;
import com.nokia.carbide.search.system.internal.core.text.PatternConstructor;
import com.nokia.carbide.search.system.internal.ui.Messages;
import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.ISearchResult;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchResult;
import com.nokia.carbide.search.system.ui.text.FileTextSearchScope;
import com.nokia.carbide.search.system.ui.text.Match;




public class FileSearchQuery implements ISearchQuery {
	
	private final static class TextSearchResultCollector extends TextSearchRequestor {
		
		private final AbstractTextSearchResult fResult;
		private final boolean fIsFileSearchOnly;
		private final boolean fSearchInBinaries;
		private ArrayList fCachedMatches;
		
		private TextSearchResultCollector(AbstractTextSearchResult result, boolean isFileSearchOnly, boolean searchInBinaries) {
			fResult= result;
			fIsFileSearchOnly= isFileSearchOnly;
			fSearchInBinaries= searchInBinaries;
			
		}
		
		public boolean acceptFile(IFileStore file) throws CoreException {
			if (fIsFileSearchOnly) {
				fResult.addMatch(new FileMatch(file, 0, 0));
			}
			flushMatches();
			return true;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.carbide.search.system.core.text.TextSearchRequestor#reportBinaryFile(org.eclipse.core.resources.IFile)
		 */
		public boolean reportBinaryFile(IFile file) {
			return fSearchInBinaries;
		}

		public boolean acceptPatternMatch(TextSearchMatchAccess matchRequestor) throws CoreException {
			fCachedMatches.add(new FileMatch(matchRequestor.getFile(), matchRequestor.getMatchOffset(), matchRequestor.getMatchLength()));
			return true;
		}

		public void beginReporting() {
			fCachedMatches= new ArrayList();
		}
		
		public void endReporting() {
			flushMatches();
			fCachedMatches= null;
		}

		private void flushMatches() {
			if (!fCachedMatches.isEmpty()) {
				fResult.addMatches((Match[]) fCachedMatches.toArray(new Match[fCachedMatches.size()]));
				fCachedMatches.clear();
			}
		}
	}
	
	private final FileTextSearchScope fScope;
	private final String fSearchText;
	private final boolean fIsRegEx;
	private final boolean fIsCaseSensitive;
	
	private FileSearchResult fResult;
	
	public FileSearchQuery(String searchText, boolean isRegEx, boolean isCaseSensitive, FileTextSearchScope scope) {
		fSearchText= searchText;
		fIsRegEx= isRegEx;
		fIsCaseSensitive= isCaseSensitive;
		fScope= scope;
	}
	
	public FileTextSearchScope getSearchScope() {
		return fScope;
	}
	
	public boolean canRunInBackground() {
		return true;
	}

	public IStatus run(final IProgressMonitor monitor) {
		AbstractTextSearchResult textResult= (AbstractTextSearchResult) getSearchResult();
		textResult.removeAll();
		
		Pattern searchPattern= getSearchPattern();
		boolean isFileSearchOnly= searchPattern.pattern().length() == 0;
		boolean searchInBinaries= !isScopeAllFileTypes();
		
		TextSearchResultCollector collector= new TextSearchResultCollector(textResult, isFileSearchOnly, searchInBinaries);
		return TextSearchEngine.create().search(fScope, collector, searchPattern, monitor);
	}
	
	private boolean isScopeAllFileTypes() {
		String[] fileNamePatterns= fScope.getFileNamePatterns();
		if (fileNamePatterns == null)
			return true;
		for (int i= 0; i < fileNamePatterns.length; i++) {
			if ("*".equals(fileNamePatterns[i])) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}
	

	public String getLabel() {
		return SearchMessages.FileSearchQuery_label; 
	}
	
	public String getSearchString() {
		return fSearchText;
	}
	
	public String getResultLabel(int nMatches) {
		String searchString= getSearchString();
		if (searchString.length() > 0) {
			// text search
			if (isScopeAllFileTypes()) {
				// search all file extensions
				if (nMatches == 1) {
					Object[] args= { searchString, fScope.getDescription() };
					return Messages.format(SearchMessages.FileSearchQuery_singularLabel, args);
				}
				Object[] args= { searchString, new Integer(nMatches), fScope.getDescription() };
				return Messages.format(SearchMessages.FileSearchQuery_pluralPattern, args); 
			}
			// search selected file extensions
			if (nMatches == 1) {
				Object[] args= { searchString, fScope.getFilterDescription(), fScope.getDescription() };
				return Messages.format(SearchMessages.FileSearchQuery_singularPatternWithFileExt, args);
			}
			Object[] args= { searchString, new Integer(nMatches), fScope.getFilterDescription(), fScope.getDescription() };
			return Messages.format(SearchMessages.FileSearchQuery_pluralPatternWithFileExt, args);
		}
		// file search
		if (nMatches == 1) {
			Object[] args= { fScope.getFilterDescription(), fScope.getDescription() };
			return Messages.format(SearchMessages.FileSearchQuery_singularLabel_fileNameSearch, args); 
		}
		Object[] args= { fScope.getFilterDescription(), new Integer(nMatches), fScope.getDescription() };
		return Messages.format(SearchMessages.FileSearchQuery_pluralPattern_fileNameSearch, args); 
	}

	/**
	 * @param result all result are added to this search result
	 * @param monitor the progress monitor to use
	 * @param file the file to search in
	 * @return returns the status of the operation
	 */
	public IStatus searchInFile(final AbstractTextSearchResult result, final IProgressMonitor monitor, IFile file) {
		FileTextSearchScope scope= FileTextSearchScope.newSearchScope(new IResource[] { file }, new String[] { "*" }, true); //$NON-NLS-1$
		
		Pattern searchPattern= getSearchPattern();
		boolean isFileSearchOnly= searchPattern.pattern().length() == 0;
		TextSearchResultCollector collector= new TextSearchResultCollector(result, isFileSearchOnly, true);
		
		return TextSearchEngine.create().search(scope, collector, searchPattern, monitor);
	}
	
	protected Pattern getSearchPattern() {
		return PatternConstructor.createPattern(fSearchText, fIsCaseSensitive, fIsRegEx);
	}
	
	public boolean isRegexSearch() {
		return fIsRegEx;
	}
	
	public boolean isCaseSensitive() {
		return fIsCaseSensitive;
	}

	public boolean canRerun() {
		return true;
	}

	public ISearchResult getSearchResult() {
		if (fResult == null) {
			fResult= new FileSearchResult(this);
			new SearchResultUpdater(fResult);
		}
		return fResult;
	}
}
