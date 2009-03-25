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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ITargetFeedbackListener;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.command.AddCommand;
import com.nokia.sdt.uidesigner.ui.command.CreateCommand;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.DropRequest;

public class ContainerOrderedLayoutEditPolicy extends OrderedLayoutEditPolicy {

	public ContainerOrderedLayoutEditPolicy() {
		defaultFeedback = new DefaultTargetFeedback(this);
	}

	private DefaultTargetFeedback defaultFeedback;
	private ITargetFeedbackListener targetFeedbackListener;
	private IFigure targetFeedback;;
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
		int insertionIndex = getInsertionIndexFromReference(null, getInsertionReference(request));
		CreateCommand command = new CreateCommand((EObject) getHost().getModel(), request,
				insertionIndex, editor.getDataModel(), getHost(), editor);
		
		return command.chain(getChainedCommandFromRequest(request));
	}

	private Command getChainedCommandFromRequest(Request request) {
		Command extra = 
			(Command) request.getExtendedData().get(ITargetFeedbackListener.CHAINED_COMMAND);
		if (extra == null)
			extra = new Command() {};
			
		return extra;
	}

	private int getInsertionIndexFromReference(EditPart child, EditPart after) {
		int newIndex = IDesignerDataModel.AT_END;
		if (after != null) {
		Object model = getHost().getModel();
			if (model instanceof EObject) {
				IComponentInstance ci = Adapters.getComponentInstance((EObject) model);
				EObject[] children = ci.getChildren();
				if (children != null) {
					for (int i = 0; i < children.length; i++) {
						if (children[i].equals(after.getModel())) {
							newIndex = i;
							break;
						}
					}
				}
			}
		}
		
		return newIndex;
	}
	
	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		if (!(child instanceof ModelObjectEditPart))
			throw new IllegalArgumentException();

		IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
		int insertionIndex = getInsertionIndexFromReference(child, after);
		return new AddCommand((EObject) getHost().getModel(),
				(EObject) child.getModel(), insertionIndex,
				((ModelObjectEditPart) child).getEditor().getDataModel(), getHost(), editor);
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		return createAddCommand(child, after);
	}

	@Override
	protected Command getAddCommand(Request request) {
		return super.getAddCommand(request).chain(getChainedCommandFromRequest(request));
	}

	@Override
	protected Command getMoveChildrenCommand(Request request) {
		return super.getMoveChildrenCommand(request).chain(getChainedCommandFromRequest(request));
	}

	@Override
	protected EditPart getInsertionReference(Request request) {
		return (EditPart) request.getExtendedData().get(ITargetFeedbackListener.EDIT_PART_AFTER_INSERTION);
	}
	
	@Override
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}

	@Override
	protected void decorateChild(EditPart child) {
	}

	@Override
	protected void decorateChildren() {
	}

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return null;
	}

	@Override
	public void showTargetFeedback(Request request) {
		if (EditorUtils.isRequestForTargetFeedback(request)) {
			if (targetFeedbackListener == null) {
				ILayoutContainer container = Adapters.getLayoutContainer((EObject) getHost().getModel());
				targetFeedbackListener = container.getTargetFeedbackListener();
				if (targetFeedbackListener == null)
					return;
				targetFeedback = targetFeedbackListener.beginTargetFeedback((GraphicalEditPart) getHost());
				if (targetFeedback != null)
					addFeedback(targetFeedback);
			}
			defaultFeedback.setTargetFigure(getHostFigure());
			addFeedback(defaultFeedback);
			Check.checkState(request instanceof DropRequest);
			DropRequest dropRequest = (DropRequest) request;
			if (targetFeedbackListener != null) {
				targetFeedbackListener.mouseMoved(dropRequest);
			}
		}
	}

	@Override
	public void eraseTargetFeedback(Request request) {
		if (EditorUtils.isRequestForTargetFeedback(request)) {
			defaultFeedback.setTargetFigure(null);
			if (getFeedbackLayer().getParent().equals(defaultFeedback))
				removeFeedback(defaultFeedback);
			if (targetFeedbackListener != null) {
				targetFeedbackListener.endTargetFeedback();
				targetFeedbackListener = null;
				if (targetFeedback != null)
					removeFeedback(targetFeedback);
				targetFeedback = null;
			}
		}
	}
}