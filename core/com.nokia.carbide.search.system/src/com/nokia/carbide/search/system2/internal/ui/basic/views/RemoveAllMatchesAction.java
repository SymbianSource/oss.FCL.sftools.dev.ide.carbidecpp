/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system2.internal.ui.basic.views;

import org.eclipse.jface.action.Action;

import com.nokia.carbide.search.system.internal.ui.SearchPluginImages;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchResult;
import com.nokia.carbide.search.system.ui.text.AbstractTextSearchViewPage;
import com.nokia.carbide.search.system2.internal.ui.SearchMessages;



public class RemoveAllMatchesAction extends Action {
	AbstractTextSearchViewPage fPage;

	public RemoveAllMatchesAction(AbstractTextSearchViewPage page) {
		super(SearchMessages.RemoveAllMatchesAction_label); 
		SearchPluginImages.setImageDescriptors(this, SearchPluginImages.T_LCL, SearchPluginImages.IMG_LCL_SEARCH_REM_ALL);		
		setToolTipText(SearchMessages.RemoveAllMatchesAction_tooltip); 
		fPage= page;
	}
	
	public void run() {
		AbstractTextSearchResult search= fPage.getInput();
		if (search != null)
			search.removeAll();
	}
}
