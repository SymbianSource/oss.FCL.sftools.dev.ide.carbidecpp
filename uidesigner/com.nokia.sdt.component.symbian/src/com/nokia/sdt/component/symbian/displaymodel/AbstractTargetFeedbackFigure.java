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


import com.nokia.sdt.uidesigner.ui.figure.LocalCoordinatesFigure;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.gef.*;
import org.eclipse.gef.editparts.LayerManager;

import java.util.Collections;
import java.util.List;

public abstract class AbstractTargetFeedbackFigure extends Figure implements ITargetFeedbackFigure {

	private GraphicalEditPart targetEditPart;
	private Point mouseLocation;
	private EditPart editPartAfterInsertion;
	private boolean executable;
	private List<GraphicalEditPart> sourceEditParts;

	public void setTargetEditPart(GraphicalEditPart editPart) {
		targetEditPart = editPart;
		editPartAfterInsertion = null;
		sourceEditParts = Collections.EMPTY_LIST;
	}

	public void setMouseLocation(Point point) {
		this.mouseLocation = point;
	}

	public EditPart getEditPartAfterInsertion() {
		return editPartAfterInsertion;
	}

	public void setSourceEditParts(List editParts) {
		sourceEditParts = editParts;
	}

	public boolean isExecutable() {
		return executable;
	}

	protected void setExecutable(boolean executable) {
		this.executable = executable;
	}

	protected GraphicalEditPart getTargetEditPart() {
		return targetEditPart;
	}

	protected Point getMouseLocation() {
		return mouseLocation;
	}

	protected List<GraphicalEditPart> getSourceEditParts() {
		return sourceEditParts;
	}

	protected void setEditPartAfterInsertion(EditPart editPartAfterInsertion) {
		this.editPartAfterInsertion = editPartAfterInsertion;
	}
	
	protected IFigure getFeedbackLayer() {
		return LayerManager.Helper.find(targetEditPart).getLayer(LayerConstants.FEEDBACK_LAYER);
	}
	
	protected void translateToFeedbackLayer(Translatable t) {
		IFigure anchor = getTargetEditPart().getContentPane();
		anchor.translateToAbsolute(t);
		getFeedbackLayer().translateToRelative(t);
		if (anchor instanceof LocalCoordinatesFigure) {
			anchor.translateToParent(t);
		}
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		drawFeedback(graphics);
	}

	protected abstract void drawFeedback(Graphics graphics); 
}