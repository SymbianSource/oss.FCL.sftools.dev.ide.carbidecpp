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

import java.util.HashSet;
import java.util.List;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * A CheckboxTreeViewer configured to handle source
 * selection. Input should be set to the resource container,
 * e.g. a project.
 *
 */
public class SourceSelectionViewer extends CheckboxTreeViewer implements ICheckStateListener {
	
	static final String ECLIPSE_SETTINGS_FOLDER_PATH = ".settings"; //$NON-NLS-1$

	public SourceSelectionViewer(Composite parent, int style) {
		super(parent, style);
		setUseHashlookup(true);
		setContentProvider(new WorkbenchContentProvider());
		setLabelProvider(new WorkbenchLabelProvider());
		addFilter(new SourceSelectionViewerFilter());
		addCheckStateListener(this);
	}

	static class SourceSelectionViewerFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			boolean result = false;
			if (element instanceof IFile) {
				IFile file = (IFile) element;
				result = CoreModel.isValidSourceUnitName(file.getProject(), file.getName());
			} else if (element instanceof IContainer) {
				// filter out Eclipse settings folder
				IContainer container = (IContainer) element;
				IPath path = container.getProjectRelativePath();
				result = !path.toString().equalsIgnoreCase(ECLIPSE_SETTINGS_FOLDER_PATH);
			}
			return result;
		}
	}
	
	public ITreeContentProvider contentProvider() {
		return (ITreeContentProvider) getContentProvider();
	}

	public void checkStateChanged(CheckStateChangedEvent event) {
		Object element = event.getElement();
		ITreeContentProvider tcp = contentProvider();
		if (element instanceof IFile) {
			Object parent = tcp.getParent(element);
			if (parent instanceof IContainer) {
				updateContainerCheckState((IContainer)parent);
			}
			
		} else if (element instanceof IContainer) {
			setGrayed(element, false);
			updateChildrenCheckState((IContainer)element, event.getChecked());
		}
	}
	
	public void updateContainerCheckStates(List elements) {
		// this is brute force, potentially traversing the tree multiple times
		// either the tree must be updated bottom up, or we do brute force
		HashSet<IContainer> containers = new HashSet<IContainer>();
		ITreeContentProvider tcp = contentProvider();
		for (Object object : elements) {
			Object parent = tcp.getParent(object);
			if (parent instanceof IContainer) {
				containers.add((IContainer) parent);
			}
		}
		for (IContainer container : containers) {
			updateContainerCheckState(container);
		}
	}

	/**
	 * Update the checked state of the children of the container based
	 * on checking/unchecking of the container
	 * @return true if any non-container children
	 */
	private boolean updateChildrenCheckState(IContainer container, boolean checked) {
		boolean hasChildren = false;
		Object[] children = getFilteredChildren(container);
		for (Object child : children) {
			hasChildren |= (child instanceof IFile);
			if (setChecked(child, checked)) {
				if (child instanceof IContainer) {
					hasChildren |= updateChildrenCheckState((IContainer)child, checked);
				}
			}
		}
		return hasChildren;
	}

	/** Update check checked/grayed state of the container
	 * and its parents
	 */
	private void updateContainerCheckState(IContainer container) {
		ITreeContentProvider tcp = contentProvider();
		Object[] children = filter(tcp.getChildren(container));
		boolean anyChecked = false;
		boolean anyUnchecked = false;
		boolean anyGrayed = false;
		for (Object child : children) {
			boolean checked = getChecked(child);
			anyChecked |= checked;
			anyUnchecked |= !checked;
			anyGrayed |= getGrayed(child);
		}
		setGrayed(container, anyGrayed || (anyChecked && anyUnchecked));
		setChecked(container, anyChecked);
		Object containerParent = tcp.getParent(container);
		if (containerParent instanceof IContainer) {
			updateContainerCheckState((IContainer)containerParent);
		}
	}

	@Override
	public Object[] getFilteredChildren(Object parentElementOrTreePath) {
		return super.getFilteredChildren(parentElementOrTreePath);
	}
}
