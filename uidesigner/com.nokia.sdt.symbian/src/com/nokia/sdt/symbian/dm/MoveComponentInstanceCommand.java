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
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.impl.NodeCopier;
import com.nokia.sdt.emf.dm.impl.NodeCopier.IFilter;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.Collection;

public class MoveComponentInstanceCommand extends AbstractCommand {
	
	private String nodeToMoveName;
	private String newParentName;
	private String moveBeforeSiblingName;
	private String oldParentName;
	private int oldPosition;
	private int newPosition;
	private NodeCopier copier;
	private INode resultNode;
	private boolean preserveLocalizedStringKeys;
	private boolean sameParent;
	private IDesignerDataModel dataModel;
	
	public MoveComponentInstanceCommand(INode nodeToMove, INode newParent, int newPosition) {
		Check.checkArg(nodeToMove);
		Check.checkArg(newParent);
		this.dataModel = nodeToMove.getDesignerData().getDesignerDataModel();
		this.nodeToMoveName = nodeToMove.getName();
		this.newParentName = newParent.getName();
		EObject oldContainer = nodeToMove.eContainer();
		if (oldContainer instanceof INode) {
			INode oldParent = (INode) oldContainer;
			oldParentName = oldParent.getName();
			this.sameParent = oldParentName.equals(newParentName);
			setMoveBeforeSiblingName(newParent, newPosition);
		}
		// If we're moving in the same model we can preserve localized string keys. Across
		// models we can't.
		preserveLocalizedStringKeys = nodeToMove.getDesignerData() == newParent.getDesignerData();
	}

	protected boolean prepare() {
		if (oldParentName == null)
			return false;
		
		INode nodeToMove = getNodeFromName(nodeToMoveName);
		copier = new NodeCopier(nodeToMove, new IFilter() {
			public boolean test(INode node) {
				IComponentEditor componentEditor = ModelUtils.getComponentEditor(node);
				return (componentEditor == null) || !componentEditor.isTemporaryObject();
			}
		});
		return true;
	}

	public void execute() {
		INode nodeToMove = getNodeFromName(nodeToMoveName);
		if (nodeToMove != null) {
			INode oldParent = getNodeFromName(oldParentName);
			oldPosition = oldParent.getChildren().indexOf(nodeToMove);
			newPosition = getNewPosition();
			if (sameParent) {
				if (oldPosition < newPosition)
					newPosition--;
			
				oldParent.getChildren().move(newPosition, oldPosition);
				resultNode = nodeToMove;
			}
			else {
				if (oldParent.getChildren().remove(nodeToMove)) {
					INode newParent = getNodeFromName(newParentName);
					if (newParent != null)
						resultNode = NodeCopier.copyNode(copier.getCopy(), newParent, newPosition,
								preserveLocalizedStringKeys);
				}
			}
		}
	}

	public boolean canUndo() {
		INode oldParent = getNodeFromName(oldParentName);
		return oldParent != null && resultNode != null;
	}

	public Collection getAffectedObjects() {
		Collection result = new ArrayList();
		INode nodeToMove = getNodeFromName(nodeToMoveName);
		if (nodeToMove != null) {
			result.add(nodeToMove);
		}
		if ((resultNode != null) && (resultNode != nodeToMove)) {
			result.add(resultNode);
		}
		return result;
	}

	public Collection getResult() {
		Collection result = new ArrayList();
		if (resultNode != null) {
			result.add(resultNode);
		}
		return result;
	}

	public void undo() {
		if (resultNode != null) {
			INode newParent = getNodeFromName(newParentName);
			if (sameParent) {
				newPosition = newParent.getChildren().indexOf(resultNode);
				newParent.getChildren().move(oldPosition, newPosition);
			}
			else {
				if (newParent.getChildren().remove(resultNode)) {
					INode oldParent = getNodeFromName(oldParentName);
					INode nodeToMove = NodeCopier.copyNode(copier.getCopy(), oldParent, 
							oldPosition, preserveLocalizedStringKeys);
					nodeToMoveName = nodeToMove.getName();
				}
			}
			resultNode = null;
		}
	}

	public void redo() {
		execute();
	}
	
	private INode getNodeFromName(String name) {
		return (INode) dataModel.findByNameProperty(name);
	}

	private void setMoveBeforeSiblingName(INode parent, int newPosition) {
		if (newPosition < 0)
			return;
		EList children = parent.getChildren();
		if (newPosition >= children.size())
			return;
		INode moveBeforeSibling = (INode) children.get(newPosition);
		if (moveBeforeSibling != null)
			moveBeforeSiblingName = moveBeforeSibling.getName();
	}

	private int getNewPosition() {
		INode parent = getNodeFromName(newParentName);
		INode moveBeforeSibling = getNodeFromName(moveBeforeSiblingName);
		if (moveBeforeSibling != null)
			return parent.getChildren().indexOf(moveBeforeSibling);
		
		return parent.getChildren().size();
	}

}
