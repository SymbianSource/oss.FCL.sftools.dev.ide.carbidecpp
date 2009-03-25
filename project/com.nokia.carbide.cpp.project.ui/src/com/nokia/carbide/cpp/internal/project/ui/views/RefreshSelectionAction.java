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

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;

import org.eclipse.cdt.core.model.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.SelectionListenerAction;

public class RefreshSelectionAction extends SelectionListenerAction {

	/**
	 * The id of this action.
	 */
	public static final String ID = ProjectUIPlugin.PLUGIN_ID + ".RefreshSelectionAction"; //$NON-NLS-1$

	private SymbianProjectNavigatorView view = null;
	
	
	protected RefreshSelectionAction(SymbianProjectNavigatorView view) {
		super(Messages.RefreshSelectionAction_RefreshText);
		this.view = view;
		setToolTipText(Messages.RefreshSelectionAction_RefreshToolTip);
		setId(ID);
	}

	@Override
	public void run() {
		IStructuredSelection selection = getStructuredSelection();
		if (selection.isEmpty()) {
			// refresh the entire view
			view.getViewer().refresh();
			return;
		}

		for (Object o : selection.toArray()) {
			if (o instanceof ISPNViewRefreshable) {
				((ISPNViewRefreshable)o).refresh();
				view.getViewer().refresh(o);
			} else if (o instanceof ICProject) {
				// this refreshes the viewer as well
				view.refreshProject((ICProject)o);
			} else {
				view.getViewer().refresh(o);
			}
		}
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		// enabled if selection is does not contain any IFile's.  everything
		// else that could be selected is IProject or SPNContainer's.
		if (!selection.isEmpty()) {
			for (Object o : selection.toArray()) {
				if (o instanceof IFile) {
					return false;
				}
				if (o instanceof ICElement && !(o instanceof IParent)) {
					// disable refresh for c elements that don't have children
					return false;
				}
			}
		}
		
		return true;
	}
}
