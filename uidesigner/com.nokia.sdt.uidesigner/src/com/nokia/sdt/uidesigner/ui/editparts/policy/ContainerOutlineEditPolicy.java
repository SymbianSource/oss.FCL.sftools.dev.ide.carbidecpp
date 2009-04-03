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
package com.nokia.sdt.uidesigner.ui.editparts.policy;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer.ELayoutCategory;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.command.*;
import com.nokia.sdt.uidesigner.ui.editparts.ModelContainerOutlineEditPart;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.draw2d.geometry.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.*;
import org.eclipse.gef.editpolicies.TreeContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import java.util.*;

public class ContainerOutlineEditPolicy extends TreeContainerEditPolicy {

	/**
	 * Command for moving children to a new parent
	 */
	protected Command getAddCommand(ChangeBoundsRequest request) {
		CompoundCommand command = new CompoundCommand();
		int newIndex = findIndexOfTreeItemAt(request.getLocation());
	
		EObject newParentModel = (EObject) getHost().getModel();
		for (Iterator iter = request.getEditParts().iterator(); iter.hasNext();) {
			
			EObject childModel = (EObject) ((EditPart) iter.next()).getModel();
			if (!EditorUtils.canContainChild(newParentModel, childModel)) {
				command.chain(UnexecutableCommand.INSTANCE);
				return command;
			}
			
			IDesignerEditor editor = ((ModelContainerOutlineEditPart) getHost()).getEditor();
			IDesignerDataModel dataModel = editor.getDataModel();
			command.add(new AddCommand(newParentModel, childModel, newIndex, dataModel, getHost(), editor));
			
			ILayoutContainer container = Adapters.getLayoutContainer(newParentModel);
			if (container != null && container.getLayoutCategory().equals(ELayoutCategory.ABSOLUTE) &&
					Adapters.getLayoutObject(childModel) != null) {
				Rectangle newBounds = sameAbsBoundsInNewParent(dataModel, childModel, newParentModel);
				command.add(new ChangeBoundsCommand(childModel, newBounds));
			}
			
		}
		return command;
	}
	
	private Rectangle sameAbsBoundsInNewParent(IDesignerDataModel dataModel, 
														EObject object, EObject newParent) {
		
		org.eclipse.swt.graphics.Rectangle bounds = Adapters.getLayoutObject(object).getBounds();
		
		// first get absolute location of object
		Point objectAbsLocation = EditorUtils.getAbsoluteLocation(object, dataModel);
		
		// then  get absolute location of new parent
		Point parentAbsLocation = EditorUtils.getAbsoluteLocation(newParent, dataModel);
		
		Dimension difference = objectAbsLocation.getDifference(parentAbsLocation);
		
		return new Rectangle(difference.width, difference.height, bounds.width, bounds.height);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ContainerEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		Object parent = getHost().getModel();
		if (parent instanceof EObject) {
			Rectangle constraint = new Rectangle(0, 0, -1, -1);
			request.getExtendedData().put(EditorUtils.CONSTRAINT, constraint);
			int index = findIndexOfTreeItemAt(request.getLocation());
			IDesignerEditor editor = ((ModelContainerOutlineEditPart) getHost()).getEditor();
			return new CreateCommand((EObject) parent, request, index,
					editor.getDataModel(), getHost(), editor);
		}
		return null;
	}

	/**
	 * Command for reordering children within the same parent
	 */
	protected Command getMoveChildrenCommand(ChangeBoundsRequest request) {
		List editParts = request.getEditParts();
		if (!EditorUtils.canReorderChildren((EObject) getHost().getModel(), getModelsFromParts(editParts)))
			return UnexecutableCommand.INSTANCE;
		
		CompoundCommand command = new CompoundCommand();
		int newIndexLoc = findIndexOfTreeItemAt(request.getLocation());
	
		List children = getHost().getChildren();
		for (Iterator iter = editParts.iterator(); iter.hasNext();) {
			EditPart childEditPart = (EditPart) iter.next();
			int newIndex = newIndexLoc;
			int oldIndex = children.indexOf(childEditPart);
			if ((oldIndex == newIndex) || ((oldIndex + 1) == newIndex)) {
				command.add(UnexecutableCommand.INSTANCE);
				return command;
			} 
			
			IDesignerEditor editor = ((ModelContainerOutlineEditPart) getHost()).getEditor();
			command.add(new AddCommand((EObject) getHost().getModel(), (EObject) childEditPart.getModel(), 
					newIndex, editor.getDataModel(), getHost(), editor));
		}
		return command;
	}

	private List<EObject> getModelsFromParts(List<EditPart> editParts) {
		List<EObject> models = new ArrayList<EObject>();
		for (EditPart part : editParts) {
			models.add((EObject) part.getModel());
		}
		return models;
	}

}
