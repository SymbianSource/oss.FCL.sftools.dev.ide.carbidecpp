/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import java.util.*;

public class RowLayoutTargetFeedbackFigure extends AbstractTargetFeedbackFigure {

	private Point insertionPoint = new Point(-1, -1);
	private int insertionLineLength;
	private boolean vertical;
	
	public RowLayoutTargetFeedbackFigure(boolean vertical) {
		this.vertical = vertical;
	}

	@Override
	protected void drawFeedback(Graphics graphics) {
		if (insertionPoint.x < 0 || insertionPoint.y < 0)
			return;
		IFigure targetFigure = getTargetEditPart().getFigure();
		Rectangle r = targetFigure.getBounds();
		Rectangle bounds = new Rectangle(insertionPoint.x, insertionPoint.y, r.width, r.height);
		if (vertical)
			bounds.width = insertionLineLength;
		else
			bounds.height = insertionLineLength;
		translateToFeedbackLayer(bounds);
		drawInsertionPoint(graphics, bounds);
	}

	private void drawInsertionPoint(Graphics graphics, Rectangle bounds) {
		graphics.setClip(getFeedbackLayer().getBounds());
		graphics.setBackgroundColor(ColorConstants.black);
		if (vertical) {
			graphics.fillRectangle(bounds.x, bounds.y - 2, 1, 7);
			graphics.fillRectangle(bounds.x + 1, bounds.y - 1, 1, 5);
			graphics.fillRectangle(bounds.x + bounds.width - 1, bounds.y - 2, 1, 7);
			graphics.fillRectangle(bounds.x + bounds.width - 2, bounds.y - 1, 1, 5);
			graphics.fillRectangle(bounds.x + 2, bounds.y, bounds.width - 4, 3);
		}
		else {
			graphics.fillRectangle(bounds.x - 2, bounds.y, 7, 1);
			graphics.fillRectangle(bounds.x - 1, bounds.y + 1, 5, 1);
			graphics.fillRectangle(bounds.x - 2, bounds.y + bounds.height - 1, 7, 1);
			graphics.fillRectangle(bounds.x - 1, bounds.y + bounds.height - 2, 5, 1);
			graphics.fillRectangle(bounds.x, bounds.y + 2, 3, bounds.height - 4);
		}
	}

	protected void setInsertMarkerLocation() {
		IFigure targetFigure = getTargetEditPart().getFigure();
		Point localPt = new Point(getMouseLocation());
		targetFigure.translateToRelative(localPt);
		IFigure figureUnderMouse = targetFigure.findFigureAt(localPt);
		for (int i = 1; i < 10; i++) {
			if (figureUnderMouse == null || !figureUnderMouse.getParent().equals(targetFigure)) {
				figureUnderMouse = targetFigure.findFigureAt(localPt.getTranslated(i, i));
			}
			else
				break;
		}
		setExecutable(true);
		insertionPoint.setLocation(-1, -1); // don't draw insertion point
		setEditPartAfterInsertion(null);
		if (figureUnderMouse == null)
			figureUnderMouse = targetFigure;
		if (!canMoveSourceFiguresBefore(figureUnderMouse)) {
			setExecutable(false);
		}
		else {
			EditPart partAfterInsertion = getChildOfContainerPartForFigure(figureUnderMouse);
			if (partAfterInsertion != null) {
				setEditPartAfterInsertion(partAfterInsertion);
				// draw insertion point before figureUnderMouse
				Rectangle r = figureUnderMouse.getBounds();
				if (vertical) {
					insertionPoint.setLocation(r.x, r.y);
					insertionLineLength = r.width;
				}
				else {
					insertionPoint.setLocation(r.x, r.y);
					insertionLineLength = r.height;
				}
			}
		}
	}
	
	protected boolean canMoveSourceFiguresBefore(IFigure figureUnderMouse) {
		Collection<IFigure> sourceFigures = getFiguresOfSourceEditParts();
		for (IFigure sourceFigure : sourceFigures) {
			if (figureIsSameOrNext(sourceFigure, figureUnderMouse))
				return false;
		}

		return true;
	}

	protected boolean figureIsSameOrNext(IFigure fig0, IFigure fig1) {
		// return fig0 == fig1 -or- fig1 is next figure after fig0
		if (fig0.equals(fig1))
			return true;
		
		// get the indices of fig0 and fig1 in the target edit part's child list
		List<GraphicalEditPart> children = getTargetEditPart().getChildren();
		int index0 = -1;
		int index1 = -1;
		int index = -1;
		for (GraphicalEditPart editPart : children) {
			if (index0 >= 0 && index1 >= 0)
				break;
			++index;
			if (fig0.equals(editPart.getFigure()))
				index0 = index;
			else if (fig1.equals(editPart.getFigure()))
				index1 = index;
		}
		
		return index0 > 0 && index1 > 0 && (index0 == index1 || index0 == (index1 - 1));
	}

	protected EditPart getChildOfContainerPartForFigure(IFigure figure) {
		List<GraphicalEditPart> children = getTargetEditPart().getChildren();
		for (GraphicalEditPart editPart : children) {
			if (figure.equals(editPart.getFigure()))
				return editPart;
		}
		
		return null;
	}
	
	protected EditPart getNextChildPart(EditPart childPart) {
		List<GraphicalEditPart> children = getTargetEditPart().getChildren();
		int index = children.indexOf(childPart);
		if (index + 1 < children.size())
			return children.get(index + 1);
		
		return null;
	}

	protected Collection getFiguresOfSourceEditParts() {
		Collection figures = new ArrayList();
		for (GraphicalEditPart editPart : getSourceEditParts()) {
			figures.add(editPart.getFigure());
		}
		
		return figures;
	}

	@Override
	public void setMouseLocation(Point point) {
		super.setMouseLocation(point);
		setInsertMarkerLocation();
	}
	
	public void setOrientation(boolean vertical) {
		this.vertical = vertical; 
	}
	
}