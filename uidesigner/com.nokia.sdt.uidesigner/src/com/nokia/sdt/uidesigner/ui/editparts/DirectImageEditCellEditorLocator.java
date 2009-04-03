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
package com.nokia.sdt.uidesigner.ui.editparts;

import com.nokia.sdt.uidesigner.ui.figure.LayoutObjectFigure;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Control;

public class DirectImageEditCellEditorLocator implements CellEditorLocator {

    private LayoutObjectFigure figure;
    private Point mousePos;

    public DirectImageEditCellEditorLocator(LayoutObjectFigure figure, org.eclipse.swt.graphics.Rectangle bounds, Point mousePos) {
        this.figure = figure;
        this.mousePos = mousePos;
    }

    public void relocate(CellEditor celleditor) {
        // show the button right under the mouse
        Control ctl = celleditor.getControl();
        Rectangle r = new Rectangle(mousePos.x, mousePos.y, 24, 16);
        r.translate(figure.getLocation());
        figure.translateToAbsolute(r);
        ctl.setBounds(r.x - 12, r.y - 8, 24, 16);
    }

}
