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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

/**
 * 
 *
 */
public class LayoutObjectCellEditorLocator implements CellEditorLocator {

	private Figure figure;
	private org.eclipse.swt.graphics.Rectangle labelBounds;
	
	public LayoutObjectCellEditorLocator(Figure figure, org.eclipse.swt.graphics.Rectangle labelBounds) {
		this.figure = figure;
		this.labelBounds = labelBounds;
	}
	
	public void setMinDimension(Dimension dimension) {
		if (dimension.height > labelBounds.height)
			labelBounds.height = dimension.height;
		if (dimension.width > labelBounds.width)
			labelBounds.width = dimension.width;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
	 */
	public void relocate(CellEditor celleditor) {
		Text text = (Text) celleditor.getControl(); /* @see DirectLabelEditManager.createCellEditorOn */
		Rectangle r = new Rectangle(labelBounds);
		r.translate(figure.getLocation());
		figure.translateToAbsolute(r);
		text.setBounds(r.x + 2, r.y + 2, r.width, r.height);
	}
}
