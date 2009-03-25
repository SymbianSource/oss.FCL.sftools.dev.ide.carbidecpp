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
package com.nokia.carbide.cpp.internal.project.ui.views;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.actions.ActionGroup;

/**
 * This is the action group for all the view actions.
 * It delegates to several subgroups for most of the actions.
 * 
 * @see OpenFileGroup
 * 
 */
public abstract class SPNViewActionGroup extends ActionGroup {

	protected SymbianProjectNavigatorView view;
	
	public SPNViewActionGroup(SymbianProjectNavigatorView view) {
		this.view = view;
		makeActions();
	}
	
	protected ImageDescriptor getImageDescriptor(String relativePath) {
		String iconPath = "icons/"; //$NON-NLS-1$
		try {
			URL installURL = CUIPlugin.getDefault().getBundle().getEntry("/"); //$NON-NLS-1$
			URL url = new URL(installURL, iconPath + relativePath);
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException e) {
			// should not happen
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}	

	/**
	 * Returns the view.
	 */
	public SymbianProjectNavigatorView getView() {
		return view;
	}
	
	/**
 	 * Handles a key pressed event by invoking the appropriate action.
	 * Does nothing by default.
 	 */
	public void handleKeyPressed(KeyEvent event) {
	}

	/**
	 * Handles a key released event by invoking the appropriate action.
	 * Does nothing by default.
	 */
	public void handleKeyReleased(KeyEvent event) {
	}

	/**
	 * Makes the actions contained in this action group.
	 */
	protected abstract void makeActions();
	
	/**
	 * Called when the context menu is about to open.
	 * Override to add your own context dependent menu contributions.
	 */
	public abstract void fillContextMenu(IMenuManager menu);

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 */
	public abstract void fillActionBars(IActionBars actionBars);

	public abstract void updateActionBars();

	/**
	 * Runs the default action in the group.
	 * Does nothing by default.
	 * 
	 * @param selection the current selection
	 */
	public void runDefaultAction(IStructuredSelection selection) {
	}

	public void restoreFilterAndSorterState(IMemento memento) {
	}
	
	public void saveFilterAndSorterState(IMemento memento) {
	}

}
