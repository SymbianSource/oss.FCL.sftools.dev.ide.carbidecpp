/*******************************************************************************
 * Copyright (c) 2006 Wind River Systems and others.
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

import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider;
import com.nokia.carbide.search.system2.internal.ui.SearchMessages;



/**
 * @author markus.schorn@windriver.com
 */
public class FindInWorkspaceActionDelegate extends FindInRecentScopeActionDelegate {

	public FindInWorkspaceActionDelegate() {
		super(SearchMessages.FindInWorkspaceActionDelegate_text);
		setActionDefinitionId("com.nokia.carbide.search.system.ui.performTextSearchWorkspace"); //$NON-NLS-1$
	}

	protected ISearchQuery createQuery(TextSearchQueryProvider provider, String searchForString) throws CoreException {
		return provider.createQuery(searchForString);
	}
}
