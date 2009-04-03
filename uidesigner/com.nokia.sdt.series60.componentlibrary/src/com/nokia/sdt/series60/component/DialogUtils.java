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


package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class DialogUtils {
	
	private final static String NO_DATA = Messages.getString("DialogUtils.NoData"); //$NON-NLS-1$
	
    /**
     * Draw the "No data" string in the dialog.
     * The gc must be preconfigured with a background color.
     * @param gc
     * @param laf
     */
	public static void drawDialogNoData(GC gc, ILookAndFeel laf) {
	    Rectangle r = laf.getRectangle(Series60LAF.CONTENT_PANE_BOUNDS);
        gc.setBackground(laf.getColor("EEikColorWindowBackground")); //$NON-NLS-1$
        gc.fillRectangle(new Rectangle(0, 0, r.width, r.height));
		gc.setFont(laf.getFont("NormalFont"));
		Point extent = gc.stringExtent(NO_DATA);
		gc.setForeground(laf.getColor("EEikColorDialogText"));
		gc.drawString(NO_DATA, (r.width - extent.x) / 2, r.height / 3, true);
	}
	
	public static boolean hasDialogChildren(IComponentInstance dialogInstance) {
		EObject[] children = dialogInstance.getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if (!Utilities.isNonLayoutObject(children[i]))
					return true;
			}
		}
		
		return false;
	}
}
