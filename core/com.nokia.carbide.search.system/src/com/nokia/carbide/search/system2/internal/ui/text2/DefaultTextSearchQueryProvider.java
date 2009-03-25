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

import org.eclipse.core.resources.IResource;

import org.eclipse.ui.IWorkingSet;

import com.nokia.carbide.search.system.internal.ui.text.FileSearchQuery;
import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.text.FileTextSearchScope;
import com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider;


public class DefaultTextSearchQueryProvider extends TextSearchQueryProvider {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider#createQuery(TextSearchInput)
	 */
	public ISearchQuery createQuery(TextSearchInput input) {
		FileTextSearchScope scope= input.getScope();
		String text= input.getSearchText();
		boolean regEx= input.isRegExSearch();
		boolean caseSensitive= input.isCaseSensitiveSearch();
		return new FileSearchQuery(text, regEx, caseSensitive, scope);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider#createQuery(java.lang.String)
	 */
	public ISearchQuery createQuery(String searchForString) {
		FileTextSearchScope scope= FileTextSearchScope.newWorkspaceScope(getPreviousFileNamePatterns(), false);
		return new FileSearchQuery(searchForString, false, true, scope);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider#createQuery(java.lang.String, org.eclipse.core.resources.IResource[])
	 */
	public ISearchQuery createQuery(String selectedText, IResource[] resources) {
		FileTextSearchScope scope= FileTextSearchScope.newSearchScope(resources, getPreviousFileNamePatterns(), false);
		return new FileSearchQuery(selectedText, false, true, scope);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider#createQuery(java.lang.String, org.eclipse.ui.IWorkingSet[])
	 */
	public ISearchQuery createQuery(String selectedText, IWorkingSet[] ws) {
		FileTextSearchScope scope= FileTextSearchScope.newSearchScope(ws, getPreviousFileNamePatterns(), false);
		return new FileSearchQuery(selectedText, false, true, scope);
	}
	
	private String[] getPreviousFileNamePatterns() {
		return new String[] { "*" }; //$NON-NLS-1$
	}
	
}
