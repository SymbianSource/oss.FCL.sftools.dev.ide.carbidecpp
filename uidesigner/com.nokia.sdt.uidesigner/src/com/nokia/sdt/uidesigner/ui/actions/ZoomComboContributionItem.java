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

package com.nokia.sdt.uidesigner.ui.actions;

import org.eclipse.ui.IPartService;

public class ZoomComboContributionItem extends
		org.eclipse.gef.ui.actions.ZoomComboContributionItem {

	private boolean shouldShow = true;

	public ZoomComboContributionItem(IPartService partService) {
		super(partService);
	}

	public ZoomComboContributionItem(IPartService partService, String initString) {
		super(partService, initString);
	}

	public ZoomComboContributionItem(IPartService partService, String[] initStrings) {
		super(partService, initStrings);
	}

    public void setShouldBeVisible(boolean visible) {
    	this.shouldShow = visible;
    	setVisible(visible);
    }
    
    public void setVisible(boolean visible) {
    	super.setVisible(visible && shouldShow);
    }
}
