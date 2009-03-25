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


package com.nokia.sdt.uidesigner.ui.figure;

import org.eclipse.draw2d.*;

/**
 * 
 *
 */
public class LocalCoordinatesFigure extends Figure {

	public LocalCoordinatesFigure() {
		super();
		addMouseMotionListener(new MouseMotionListener.Stub() {

			public void mouseEntered(MouseEvent me) {
				IFigure parent = getParent();
				while (parent instanceof LocalCoordinatesFigure) {
					parent.setToolTip(null);
					parent = parent.getParent();
				}
			}

			public void mouseExited(MouseEvent me) {
				setToolTip(null);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
	 */
	protected boolean useLocalCoordinates() {
		return true;
	}

	
}
