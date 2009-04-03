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


package com.nokia.sdt.uidesigner.ui.editparts.policy;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.editor.ICreationTool;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.command.AddCommand;
import com.nokia.sdt.uidesigner.ui.command.CreateCommand;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;


public class ContentsNonLayoutEditPolicy extends FlowLayoutEditPolicy {

	private final GraphicalEditPart part;

	public ContentsNonLayoutEditPolicy(GraphicalEditPart part) {
		this.part = part;
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		return createMoveChildCommand(child, after);
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
		EObject parent = (EObject) part.getModel();
		return new AddCommand(parent, (EObject) child.getModel(), 
				findIndexForObject(parent, after), editor.getDataModel(), getHost(), editor);
	}
	
	private int toAbsIndex(int nonLayoutObjectIndex) {
		if (nonLayoutObjectIndex < 0)
			return nonLayoutObjectIndex;
		
		IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
		IComponentInstance parentInstance = Adapters.getComponentInstance((EObject) part.getModel());
		EObject[] children = parentInstance.getChildren();
		int curNLOIndex = -1;
		for (int i = 0; i < children.length; i++) {
			if (EditorUtils.isNonLayoutObject(children[i], editor.getDataModel())) {
				++curNLOIndex;
				if (curNLOIndex == nonLayoutObjectIndex)
					return i;
			}
		}
		
		return -1;
	}
	
	private int findIndexForObject(EObject parent, EditPart after) {
		if (after == null)
			return -1;
		
		IComponentInstance parentInstance = Adapters.getComponentInstance(parent);
		EObject[] children = parentInstance.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i].equals(after.getModel()))
				return i;
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		// return a command that can add a component to a root component 
		IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
		// don't allow layout objects to be added here
		ICreationTool tool = (ICreationTool) request.getNewObjectType();
		if (!EditorUtils.isNonLayoutComponent(tool))
			return UnexecutableCommand.INSTANCE;
		
		return new CreateCommand((EObject) part.getModel(), request, 
						toAbsIndex(getFeedbackIndexFor(request)),
							editor.getDataModel(), getHost(), editor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
	 */
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
	 */
	protected EditPolicy createChildEditPolicy(EditPart child) {
		NonResizableEditPolicy editPolicy = new NonResizableEditPolicy();
		editPolicy.setDragAllowed(true);
		
		return editPolicy;
	}
}