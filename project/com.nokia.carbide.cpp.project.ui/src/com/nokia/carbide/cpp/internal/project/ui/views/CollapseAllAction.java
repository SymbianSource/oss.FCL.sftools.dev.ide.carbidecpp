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

import org.eclipse.jface.action.Action;

/**
 * Collapse all nodes.
 */
class CollapseAllAction extends Action {
	
	private SymbianProjectNavigatorView view;
	
	CollapseAllAction(SymbianProjectNavigatorView part) {
		super(Messages.CollapseAllAction_CollapseAllText);
		setDescription(Messages.CollapseAllAction_CollapseAllText);
		setToolTipText(Messages.CollapseAllAction_CollapseAllText);
		setImageDescriptor(ProjectUIPlugin.getImageDescriptor("icons/collapseall.gif")); //$NON-NLS-1$
		view = part;
	}
 
	public void run() { 
		view.collapseAll();
	}
}
