/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system2.internal.ui;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;

import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.ISearchResult;
import com.nokia.carbide.search.system.ui.NewSearchUI;




/**
 * Invoke the resource creation wizard selection Wizard.
 * This action will retarget to the active view.
 */
class ShowSearchHistoryDialogAction extends Action {
	private SearchView fSearchView;


	/*
	 *	Create a new instance of this class
	 */
	public ShowSearchHistoryDialogAction(SearchView searchView) {
		super(SearchMessages.ShowSearchesAction_label); 
		setToolTipText(SearchMessages.ShowSearchesAction_tooltip); 
		fSearchView= searchView;
	}
	 
	public void run() {
		ISearchQuery[] queries= NewSearchUI.getQueries();

		ArrayList input= new ArrayList();
		for (int j= 0; j < queries.length; j++) {
			ISearchResult search= queries[j].getSearchResult();
			input.add(search);
		}
		
		SearchHistorySelectionDialog dlg= new SearchHistorySelectionDialog(SearchPlugin.getActiveWorkbenchShell(),input);
		
		ISearchResult current= fSearchView.getCurrentSearchResult();
		if (current != null) {
			Object[] selected= new Object[1];
			selected[0]= current;
			dlg.setInitialSelections(selected);
		}
		if (dlg.open() == Window.OK) {
			Object[] result= dlg.getResult();
			if (result != null && result.length == 1) {
				ISearchResult searchResult= (ISearchResult) result[0];
				InternalSearchUI.getInstance().showSearchResult(fSearchView, searchResult, dlg.isOpenInNewView());
			}
		}

	}
}
