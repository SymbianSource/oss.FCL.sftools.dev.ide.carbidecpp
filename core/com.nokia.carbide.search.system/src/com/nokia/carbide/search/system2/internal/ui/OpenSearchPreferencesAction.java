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

import org.eclipse.swt.widgets.Shell;

import org.eclipse.jface.action.Action;

import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.internal.ui.SearchPreferencePage;


/**
 * Opens the search preferences dialog
 */
public class OpenSearchPreferencesAction extends Action {
	public OpenSearchPreferencesAction() {
		super(SearchMessages.OpenSearchPreferencesAction_label); 
		setToolTipText(SearchMessages.OpenSearchPreferencesAction_tooltip); 
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.OPEN_PREFERENCES_ACTION);
	}

	/* (non-Javadoc)
	 * Method declared on Action.
	 */
	public void run() {
		Shell shell= SearchPlugin.getActiveWorkbenchShell();
		PreferencesUtil.createPreferenceDialogOn(shell, SearchPreferencePage.PAGE_ID, null, null).open();
	}

}
