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

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Font;

/**
 * 
 *
 */
public class NonLayoutObjectFigure extends Label {

	private Insets insets;
	private final static char BREAK_CHAR = '\n';
	
	public NonLayoutObjectFigure() {
		super();
		setOpaque(true); // non-transparent figure
		insets = new Insets(5, 5, 5, 5);
		setLabelAlignment(CENTER);
		setTextPlacement(SOUTH);
		setTextAlignment(CENTER);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#getInsets()
	 */
	public Insets getInsets() {
		return insets;
	}

	public void setText(String label) {
		super.setText(addLineBreaks(label));
		revalidate();
	}
	
	private String addLineBreaks(String label) {
		if (getFont() == null)
			return label;
		
		StringBuffer buf = new StringBuffer(label);
		Font f = getFont();
	
		// Search for widths just around 2X icon width
		int maxWidth = getIconBounds().width * 3;
		
		int lastBreak = 0;
		for (int i = 1 ; i < label.length(); i++) {
			int curWidth = FigureUtilities.getTextWidth(buf.substring(lastBreak, i), f);
			if (curWidth > maxWidth) {
				buf.insert(i, BREAK_CHAR);
				lastBreak = i + 1;
			}
		}
		
		return buf.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#addNotify()
	 */
	public void addNotify() {
		super.addNotify();
		setBackgroundColor(getParent().getBackgroundColor());
		recalcLineBreaks();
	}

	private void recalcLineBreaks() {
		StringBuffer newText = new StringBuffer();
		String curText = getText();
		for (int i = 0; i < curText.length(); i++) {
			char c = curText.charAt(i);
			if (c != BREAK_CHAR)
				newText.append(c);
		}
		setText(newText.toString());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
	 */
	protected boolean useLocalCoordinates() {
		return true;
	}

	public boolean pointInText(Point location) {
		return getTextBounds().contains(location);
	}
}
