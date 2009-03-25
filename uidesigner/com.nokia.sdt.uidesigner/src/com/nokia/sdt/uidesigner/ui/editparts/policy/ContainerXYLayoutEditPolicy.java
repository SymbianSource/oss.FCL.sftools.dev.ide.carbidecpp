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
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.command.*;
import com.nokia.sdt.uidesigner.ui.editparts.LayoutObjectEditPart;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class ContainerXYLayoutEditPolicy extends XYLayoutEditPolicy {
	private DefaultTargetFeedback feedback;
	
	public ContainerXYLayoutEditPolicy(XYLayout layout) {
		Check.checkArg(layout);
		setXyLayout(layout);
		feedback = new DefaultTargetFeedback(this);
	}

	protected Command createAddCommand(EditPart child, Object constraint) {
		if (!(child instanceof ModelObjectEditPart))
			throw new IllegalArgumentException();

		IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
		Command cmd = new AddCommand((EObject) getHost().getModel(),
				(EObject) child.getModel(), IDesignerDataModel.AT_END,
				((ModelObjectEditPart) child).getEditor().getDataModel(), getHost(), editor);

		ChangeBoundsRequest request = new ChangeBoundsRequest();
		request.setEditParts(child);
		Command moveCmd = createChangeConstraintCommand(request, child,
				constraint);

		return cmd.chain(moveCmd);
	}

	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest,
	 *      org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		if (child instanceof LayoutObjectEditPart
				&& constraint instanceof Rectangle) {
			// return a command that can move and/or resize a visual component
			return new ChangeBoundsCommand((EObject) child.getModel(),
					(Rectangle) constraint);
		}
		return super.createChangeConstraintCommand(request, child, constraint);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		request.getExtendedData().put(EditorUtils.CONSTRAINT, constraint);
		IDesignerEditor editor = ((ModelObjectEditPart) getHost()).getEditor();
		return new CreateCommand((EObject) getHost().getModel(), request, IDesignerDataModel.AT_END,
				editor.getDataModel(), getHost(), editor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
	 */
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}

	/**
	 * Retrieves the child's current constraint from the
	 * <code>LayoutManager</code>.
	 * 
	 * @param child
	 *            the child
	 * @return the current constraint
	 */
	protected Rectangle getCurrentConstraintFor(GraphicalEditPart child) {
		IFigure fig = child.getContentPane();
		return fig.getBounds();
	}

	protected void decorateChild(EditPart child) {
	}

	protected void decorateChildren() {
	}

	protected EditPolicy createChildEditPolicy(EditPart child) {
		return null;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#showTargetFeedback(org.eclipse.gef.Request)
	 */
	public void showTargetFeedback(Request request) {
		if (EditorUtils.isRequestForTargetFeedback(request)) {
			if ((request instanceof CreateRequest) && ((CreateRequest) request).getSize() != null)
				super.showTargetFeedback(request);
			feedback.setTargetFigure(getHostFigure());
			addFeedback(feedback);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#eraseTargetFeedback(org.eclipse.gef.Request)
	 */
	public void eraseTargetFeedback(Request request) {
		if (EditorUtils.isRequestForTargetFeedback(request)) {
			if ((request instanceof CreateRequest) && ((CreateRequest) request).getSize() != null)
				super.eraseTargetFeedback(request);
			feedback.setTargetFigure(null);
			if (getFeedbackLayer().equals(feedback.getParent()))
				removeFeedback(feedback);
		}
	}
}