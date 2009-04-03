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


package com.nokia.sdt.uidesigner.ui.editparts.policy;

import com.nokia.sdt.uidesigner.ui.utils.DrawingUtils;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.*;
import org.eclipse.gef.editparts.LayerManager;

public class DefaultTargetFeedback extends Figure {
	
	private IFigure targetFigure;
	private EditPolicy editPolicy;
	
	public DefaultTargetFeedback(EditPolicy editPolicy) {
		this.editPolicy = editPolicy;
	}

	public void addNotify() {
		super.addNotify();
		setBounds(getParent().getBounds());
	}

	public void setTargetFigure(IFigure figure) {
		this.targetFigure = figure;
	}
	
	private IFigure getFeedbackLayer() {
		return LayerManager.Helper.find(editPolicy.getHost()).getLayer(LayerConstants.FEEDBACK_LAYER);
	}
	
	public void paintFigure(Graphics graphics) {
		if (targetFigure == null)
			return;
		graphics.setForegroundColor(ColorConstants.darkGray);
		Rectangle r = targetFigure.getBounds();
		Insets insets = targetFigure.getInsets();
		Rectangle useRect = new Rectangle(r.x + insets.left, 
				r.y + insets.top, 
				r.width - insets.right - insets.left - 1, 
				r.height - insets.bottom - insets.top - 1);
		targetFigure.translateToAbsolute(useRect);
		getFeedbackLayer().translateToRelative(useRect);
		
		for (int i = 0; i < 3; i++) {
			useRect.expand(1, 1);
			graphics.drawRectangle(useRect);
		}
		DrawingUtils.drawAlphaBlendedRectangle(graphics, useRect, 90, ColorConstants.lightGray);
	}
}

