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


package com.nokia.carbide.uiq.test;

import com.nokia.sdt.component.symbian.visual.javascript.Colors;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;

abstract class VisualUtils {
	
	static Color colorFromString(String colorStr, ILookAndFeel laf) {
		if (colorStr == null || colorStr.equals(""))
			return null;

		Color result = null;
		String[] elements = colorStr.split(",");
		if (elements.length == 3) {
			boolean valid = true;
			int ints[] = new int[3];
			for (int i = 0; i < elements.length; i++) {
				try {
					ints[i] = Integer.parseInt(elements[i]);
				}
				catch (NumberFormatException e) {
					valid = false;
					break;
				}
			}
			if (valid)
				result = Colors.getColor(ints[0], ints[1], ints[2]);
		}
		else {
			result = laf.getColor(colorStr);
		}
		return result;
	}

	static Rectangle shrinkRect(Rectangle rect, int left, int top, int right, int bottom) {
		Rectangle r = new Rectangle(rect.x, rect.y, rect.width, rect.height);
		r.x += left; 
		r.width -= (left + right);
		r.y += top; 
		r.height -= (top + bottom);
		
		return r;
	}

}
