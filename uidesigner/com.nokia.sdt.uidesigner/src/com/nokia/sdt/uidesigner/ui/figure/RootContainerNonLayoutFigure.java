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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 
 *
 */
public class RootContainerNonLayoutFigure extends LocalCoordinatesFigure {
	private Insets insets;
	
	public RootContainerNonLayoutFigure() {
		insets = new Insets(20, 20, 20, 20);
		setBackgroundColor(ColorConstants.listBackground);
		setOpaque(true);
		FlowLayout layout = new FlowLayout(true);
		layout.setMajorSpacing(20);
		layout.setMinorSpacing(10);
		layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
		setLayoutManager(layout);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.IFigure#setBounds(org.eclipse.draw2d.geometry.Rectangle)
	 */
	public void setBounds(Rectangle rect) {
		if (!getBounds().equals(rect))
			revalidate();
		super.setBounds(rect);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#getInsets()
	 */
	public Insets getInsets() {
		return insets;
	}
}
