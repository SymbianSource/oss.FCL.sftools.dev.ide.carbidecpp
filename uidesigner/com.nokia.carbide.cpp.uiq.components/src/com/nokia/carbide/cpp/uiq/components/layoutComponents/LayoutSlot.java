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


package com.nokia.carbide.cpp.uiq.components.layoutComponents;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;

import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;

public class LayoutSlot {
	
	private EObject slot;
	private int anchorId;
	private int numberOfRowsToFillIn;
	private int filledWidth;
	
	public EObject getSlot() {
		return slot;
	}
	
	public void setSlot(EObject slot) {
		this.slot = slot;
	}
	
	public int getAnchorId() {
		return anchorId;
	}
	
	public void setAnchorId(int anchorId) {
		this.anchorId = anchorId;
	}
	
	public int getNumberOfRowsToFillIn() {
		return numberOfRowsToFillIn;
	}
	
	public void setNumberOfRowsToFillIn(int numberOfRows) {
		this.numberOfRowsToFillIn = numberOfRows;
	}
	
	public int getFilledWidth() {
		return filledWidth;
	}

	public void setFilledWidth(int filledWidth) {
		this.filledWidth = filledWidth;
	}

	public ILayoutObject getSlotLO() {
		return ModelUtils.getLayoutObject(slot);
	}

	public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
		if (slot != null) {
			ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(slot, ILayout.class);
	    	if (layout != null)
				return layout.getPreferredSize(wHint, hHint, laf);
	    	
	    	IVisualAppearance visual = 
	    		(IVisualAppearance) EcoreUtil.getRegisteredAdapter(slot, IVisualAppearance.class);
	    	if (visual != null)
	    		return visual.getPreferredSize(wHint, hHint, laf);
		}
    	
		return null;
	}
}
