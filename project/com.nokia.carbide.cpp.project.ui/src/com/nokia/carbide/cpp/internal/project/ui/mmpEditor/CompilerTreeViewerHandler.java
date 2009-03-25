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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.AbstractTreeViewer;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.CompilerPresentationModel.PathElement;

/**
 * Specialization of TreeViewerHandler to resolve between presentation objects
 * used in the viewer and model objects from the mmp view
 */
public class CompilerTreeViewerHandler extends TreeViewerHandler {

	private final CompilerPresentationModel model;

	public CompilerTreeViewerHandler(CompilerPresentationModel model, AbstractTreeViewer viewer) {
		super(viewer, true);
		this.model = model;
	}

	@Override
	public void refresh() {
		model.refreshFromModel();
		super.refresh();
	}

	@Override
	public void refreshElement(Object element) {
		model.refreshFromModel();
		super.refreshElement(element);
	}

	@Override
	public List<Object> modelToViewerElements(List<Object> elements) {
		List<Object> result = new ArrayList<Object>();
		for (Object object : elements) {
			if (object instanceof IPath) {
				// TODO - need better way to choose between user & sys includes, in case they
				// both have the same path
				IPath path = (IPath) object;
				PathElement pe = model.userIncludes.findPathElementForIPath(path);
				if (pe == null) {
					pe = model.sysIncludes.findPathElementForIPath(path);
				}
				if (pe != null) {
					result.add(pe);
				} else {
					result.add(object);
				}
			} else {
				result.add(object);
			}
		}
		return result;
	}

	@Override
	public List<Object> viewerToModelElements(List<Object> elements) {
		List<Object> result = new ArrayList<Object>();
		for (Object object : elements) {
			if (object instanceof PathElement) {
				PathElement pe = (PathElement) object;
				result.add(pe.mmpViewPath);
			} else {
				result.add(object);
			}
		}
		return result;
	}
}
