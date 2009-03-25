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

package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.displaymodel.adapter.ITargetFeedbackListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.GroupRequest;

public abstract class AbstractTargetFeedbackListener implements ITargetFeedbackListener {

	protected ITargetFeedbackFigure targetFeedbackFigure;
	protected final EObject object;

	public AbstractTargetFeedbackListener(EObject container) {
		this.object = container;
	}
	
	protected abstract ITargetFeedbackFigure createTargetFeedbackFigure();

	public IFigure beginTargetFeedback(GraphicalEditPart editPart) {
		if (targetFeedbackFigure == null)
			targetFeedbackFigure = createTargetFeedbackFigure();
		targetFeedbackFigure.setTargetEditPart(editPart);
		org.eclipse.swt.graphics.Rectangle r = editPart.getViewer().getControl().getBounds();
		targetFeedbackFigure.setBounds(new Rectangle(r.x, r.y, r.width, r.height));
		return targetFeedbackFigure;
	}

	public void mouseMoved(DropRequest dropRequest) {
		targetFeedbackFigure.setMouseLocation(dropRequest.getLocation());
		if (dropRequest instanceof GroupRequest)
			targetFeedbackFigure.setSourceEditParts(((GroupRequest) dropRequest).getEditParts());
		
		Request request = (Request) dropRequest;
		boolean executable = targetFeedbackFigure.isExecutable();
		if (!executable) {
			request.getExtendedData().put(CHAINED_COMMAND, UnexecutableCommand.INSTANCE);
			request.getExtendedData().remove(EDIT_PART_AFTER_INSERTION);
		}
		else {
			request.getExtendedData().remove(CHAINED_COMMAND);
			request.getExtendedData().put(EDIT_PART_AFTER_INSERTION, targetFeedbackFigure.getEditPartAfterInsertion());
		}
	}

	public EObject getEObject() {
		return object;
	}
}