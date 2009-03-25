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


package com.nokia.sdt.uidesigner.ui.editparts;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

import com.nokia.sdt.uidesigner.ui.figure.NonLayoutObjectFigure;

/**
 * 
 *
 */
public class NonLayoutObjectCellEditorLocator implements CellEditorLocator {

	private NonLayoutObjectFigure figure;
	
	public NonLayoutObjectCellEditorLocator(NonLayoutObjectFigure figure) {
		this.figure = figure;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
	 */
	public void relocate(CellEditor celleditor) {
		Text text = (Text) celleditor.getControl(); /* @see DirectLabelEditManager.createCellEditorOn */
		Rectangle r = figure.getTextBounds().getCopy();
		figure.translateToAbsolute(r);
		int maxWidth = (figure.getIconBounds().width + figure.getInsets().right) * 3;
		text.setBounds(r.x, r.y, maxWidth, r.height * 2);
	}
}
