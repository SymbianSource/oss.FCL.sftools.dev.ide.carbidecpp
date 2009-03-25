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

import java.util.*;

import org.eclipse.jface.viewers.*;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

/**
 * abstract ITreeContentProvider for tree viewers used in the MMP editor.
 * Provides the model needed to drive the presentation, and is intended to be
 * a thin layer that adds simple tree abstraction and common API for
 * adding, editing, and removing elements.
 *
 */
public abstract class TreePresentationModel implements ITreeContentProvider {

	private final Root root = new Root();
	private AbstractTreeViewer viewer;
	private ITreeNode[] nodes;
	
	interface ITreeNode {
		String getDisplayName();
		String getImageKey();
		Object[] getChildren();
		boolean hasChildren();
		int countChildren();
		boolean isChild(Object element);
		boolean canAdd();
		boolean canEdit(Object element);
		boolean canRemove(Object element);
		void doAdd();
		void doEdit(Object element);
		void doRemove(Object element);
		
		/**
		 * Refresh from the model. If there's no caching or mapping of
		 * model to presentation objects then this can be a no-op.
		 */
		void refreshFromModel();
	}
	
	static abstract class BaseTreeNode implements ITreeNode {
		String displayText;
		protected BaseTreeNode(String displayText) {
			this.displayText = displayText;
		}
		abstract Collection getChildCollection();
		
		public Object[] getChildren() {
			return getChildCollection().toArray();
		}
		
		public boolean hasChildren() {
			Collection l = getChildCollection();
			return l != null && l.size() > 0;
		}
		
		public int countChildren() {
			Collection l = getChildCollection();
			return l != null? l.size() : 0;
		}
		
		public boolean isChild(Object element) {
			return getChildCollection().contains(element);
		}
		
		public boolean canAdd() {
			return true;
		}
		
		public boolean canEdit(Object element) {
			return isChild(element);
		}
		
		public boolean canRemove(Object element) {
			return isChild(element);
		}
		
		public String getDisplayName() {
			return displayText;
		}
		
		public void refreshFromModel() {
		}
	}
	
	public class Root implements ITreeNode {
		public boolean canAdd() {
			return false;
		}
		public boolean canEdit(Object element) {
			return false;
		}
		public boolean canRemove(Object element) {
			return false;
		}
		public int countChildren() {
			return nodes.length;
		}
		public void doAdd() {
		}
		public void doEdit(Object element) {
		}
		public void doRemove(Object element) {
		}
		public Object[] getChildren() {
			return nodes;
		}
		public String getDisplayName() {
			return ""; //$NON-NLS-1$
		}
		public String getImageKey() {
			return null;
		}
		public boolean hasChildren() {
			return true;
		}
		public boolean isChild(Object element) {
			return ObjectUtils.findEqualObject(nodes, element) >= 0;
		}
		public void refreshFromModel() {
		}
	}
	
	protected TreePresentationModel() {
	}
	
	protected void initializeNodes(ITreeNode[] nodes) {
		this.nodes = nodes;
	}
	
	public void dispose() {
		viewer = null;
	}
	
	public Root getRoot() {
		return root;
	}
	
	public AbstractTreeViewer getViewer() {
		return viewer;
	}
	
	public Object[] getContainers() {
		return nodes;
	}

	public Object[] getElements(Object inputElement) {
		Object[] result = null;
		if (inputElement instanceof Root) {
			result = nodes;
		}
		return result;
	}

	public Object[] getChildren(Object parentElement) {
		Object[] result = null;
		if (parentElement instanceof ITreeNode) {
			result = ((ITreeNode)parentElement).getChildren();
		}
		return result;
	}
	
	private ITreeNode findElementParent(ITreeNode node, Object element) {
		ITreeNode result = null;
		if (node.isChild(element)) {
			result = node;
		} else {
			Object[] children = node.getChildren();
			for (Object child : children) {
				if (child instanceof ITreeNode) {
					ITreeNode foundNode = findElementParent((ITreeNode)child, element);
					if (foundNode != null) {
						result = foundNode;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public static class TreeObjectRef {
		public ITreeNode parent;
		public Object object;
	}
	
	/**
	 * Map a tree viewer selection to the nearest ITreeNodes and their children.
	 */
	List<TreeObjectRef> mapTreeViewerSelection(ITreeSelection selection) {
		List<TreeObjectRef> result = null; 
		TreePath[] paths = selection.getPaths();
		for (TreePath path : paths) {
			TreeObjectRef ts = new TreeObjectRef();
			if (path.getSegmentCount() >= 2) {
				Object object = path.getSegment(path.getSegmentCount()-2);
				if (object instanceof ITreeNode) {
					ts.parent = (ITreeNode) object;
				}
			} else {
				ts.parent = getRoot();
			}
			ts.object = path.getLastSegment();
			if (result == null) {
				result = new ArrayList<TreeObjectRef>(); 
			}
			result.add(ts);
		}
		return result;
	}
	
	/**
	 * Create a TreePath (used with TreeViewer), which is the full
	 * path to an element, based on just the immediate parent and
	 * an optional child.
	 * @param node the parent node
	 * @param element the child, or null for a path to the node only
	 */
	public TreePath getTreePath(ITreeNode node, Object element) {
		List<Object> segments = new ArrayList<Object>();
		ITreeNode curr = node;
		while (curr != null) {
			segments.add(0, curr);
			curr = getParent(curr);
		}
		if (element != null) {
			segments.add(element);
		}
		return new TreePath(segments.toArray());
	}

	public ITreeNode getParent(Object element) {
		ITreeNode result = null;
		if (element != null) {
			for (ITreeNode root : nodes) {
				ITreeNode foundNode = findElementParent(root, element);
				if (foundNode != null) {
					result = foundNode;
					break;
				}
			}
		}
		return result;
	}
	
	public boolean hasChildren(Object element) {
		boolean result = false;
		if (element instanceof ITreeNode) {
			ITreeNode c = (ITreeNode) element;
			result = c.hasChildren();
		}
		return result;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		Check.checkArg(viewer instanceof AbstractTreeViewer);
		this.viewer = (AbstractTreeViewer) viewer;
	}
	
	void refreshViewerElement(Object element) {
		TreeViewerHandler handler = (TreeViewerHandler) ControlHandler.getHandlerForControl(viewer.getControl());
		handler.refreshElement(element);
	}
	
	public void refreshFromModel() {
		for (ITreeNode node : nodes) {
			node.refreshFromModel();
		}
	}
}
