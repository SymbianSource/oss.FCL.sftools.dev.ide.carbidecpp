/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

import com.nokia.sdt.uidesigner.events.BindDefaultEventAction;
import com.nokia.sdt.uidesigner.ui.actions.ShowEventsAction;
import com.nokia.sdt.uidesigner.ui.actions.ShowOutlineAction;
import com.nokia.sdt.uidesigner.ui.actions.ShowPropertiesAction;
import com.nokia.cpp.internal.api.utils.core.Check;

public class ViewEditorContextMenuProvider extends ContextMenuProvider {

	public static final String GROUP_COMPONENT = "com.nokia.sdt.uidesigner.component"; //$NON-NLS-1$

	/** The editor's action registry. */
	private ActionRegistry actionRegistry;
		
	/**
	 * Instantiate a new menu context provider for the specified EditPartViewer 
	 * and ActionRegistry.
	 * @param viewer	the editor's graphical viewer
	 * @param registry	the editor's action registry
	 * @throws IllegalArgumentException if registry is <tt>null</tt>. 
	 */
	public ViewEditorContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		Check.checkArg(registry);
		actionRegistry = registry;
	}


	/**
	 * Called when the context menu is about to show. Actions, 
	 * whose state is enabled, will appear in the context menu.
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void buildContextMenu(IMenuManager menu) {
		// Add standard action groups to the menu
		menu.add(new Separator(GEFActionConstants.GROUP_UNDO));
		menu.add(new Separator(GROUP_COMPONENT));
		menu.add(new Separator(GEFActionConstants.GROUP_COPY));
		menu.add(new Separator(GEFActionConstants.GROUP_EDIT));
		menu.add(new Separator(GEFActionConstants.GROUP_VIEW));
		menu.add(new Separator(GEFActionConstants.MB_ADDITIONS));

		// Add actions to the menu
		menu.appendToGroup(
				GEFActionConstants.GROUP_UNDO, // target group id
				getAction(ActionFactory.UNDO.getId())); // action to add
		menu.appendToGroup(
				GEFActionConstants.GROUP_UNDO, 
				getAction(ActionFactory.REDO.getId()));
		menu.appendToGroup(
				GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.CUT.getId()));
		menu.appendToGroup(
				GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.COPY.getId()));
		menu.appendToGroup(
				GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.PASTE.getId()));
		menu.appendToGroup(
				GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.DELETE.getId()));
	
		if (getViewer() instanceof GraphicalViewer) {
			menu.appendToGroup(GEFActionConstants.GROUP_VIEW,
					getAction(ShowOutlineAction.ID));
		}
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW,
				getAction(ShowEventsAction.ID));
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW,
				getAction(ShowPropertiesAction.ID));
		
		IAction bindEventAction = getAction(BindDefaultEventAction.ID);
		if (bindEventAction.isEnabled()) {
			menu.appendToGroup(GROUP_COMPONENT, bindEventAction);
		}

	}

	private IAction getAction(String actionId) {
		return actionRegistry.getAction(actionId);
	}
}
