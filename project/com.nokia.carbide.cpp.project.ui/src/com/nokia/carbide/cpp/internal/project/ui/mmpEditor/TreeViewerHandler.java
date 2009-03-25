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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;

import org.eclipse.jface.viewers.AbstractTreeViewer;

/**
 * A ControlHandler that refreshes and expands the tree. When
 * a tree is naively refreshed the prior expansion state is lost.
 * Currently it just refreshes the whole tree as items
 * are added and removed, but it could be enhanced for
 * more selective updating.
 */
public class TreeViewerHandler extends ControlHandler {

	private int expandLevel = AbstractTreeViewer.ALL_LEVELS;

	public TreeViewerHandler(AbstractTreeViewer viewer, boolean caseSensitive) {
		super(viewer, caseSensitive);
	}
	
	public void setExpandLevel(int expandLevel) {
		this.expandLevel = expandLevel;
	}
	
	AbstractTreeViewer treeViewer() {
		return (AbstractTreeViewer) getViewer();
	}


	@Override
	public void refresh() {
		super.refresh();
		treeViewer().expandToLevel(expandLevel);
	}
	
	public void refreshElement(Object element) {
		AbstractTreeViewer tv = treeViewer();
		tv.refresh(element);
		tv.expandToLevel(expandLevel);	
	}
}
