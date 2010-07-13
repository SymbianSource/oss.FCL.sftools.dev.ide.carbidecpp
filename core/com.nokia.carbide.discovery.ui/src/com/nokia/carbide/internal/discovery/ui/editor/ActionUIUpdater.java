/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.editor;

import com.nokia.carbide.internal.discovery.ui.extension.IPortalPage.IActionUIUpdater;

class ActionUIUpdater implements IActionUIUpdater {
	
	private TaskBar taskBar;

	void setTaskBar(TaskBar taskBar) {
		this.taskBar = taskBar;
	}
	
	@Override
	public void update(String actionId) {
		if (taskBar != null)
			taskBar.updateActionUI(actionId);
	}

	@Override
	public void updateAll() {
		if (taskBar != null)
			taskBar.updateAllActionsUI();
	}

}
