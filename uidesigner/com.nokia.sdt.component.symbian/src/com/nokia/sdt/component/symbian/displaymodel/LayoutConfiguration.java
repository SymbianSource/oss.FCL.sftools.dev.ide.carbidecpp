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
/**
 * 
 */
package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.ui.skin.ISkin;

import org.eclipse.swt.graphics.Point;

class LayoutConfiguration implements IDisplayModel.LayoutAreaConfiguration {
	
	private ISkin skin;
	private ILookAndFeel laf;
	
	LayoutConfiguration(ISkin skin, ILookAndFeel laf) {
		this.skin = skin;
		this.laf = laf;
	}

	public String getID() {
		return skin.getID();
	}

	public String getDisplayName() {
		return skin.getDisplayName();
	}
	
	public Point getSize() {
		return skin.getSkinSize();
	}

	public ISkin getSkin() {
		return skin;
	}
	
	public ILookAndFeel getLookAndFeel() {
		return laf;
	}
	
	public boolean equals(Object o) {
		if (super.equals(o))
			return true;
		else if (o instanceof LayoutConfiguration)
			return ((LayoutConfiguration) o).getID().equals(getID());
		
		return false;
	}
	
	public String toString() {
		return skin.getID();
	}
}