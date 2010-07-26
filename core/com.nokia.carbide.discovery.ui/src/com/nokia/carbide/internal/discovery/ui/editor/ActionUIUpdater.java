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

import java.util.ArrayList;
import java.util.List;

import com.nokia.carbide.internal.discovery.ui.extension.IPortalExtension.IActionUIUpdater;

class ActionUIUpdater implements IActionUIUpdater {
	
	private List<TaskBar> taskBars;
	
	public ActionUIUpdater() {
		taskBars = new ArrayList<TaskBar>();
	}

	void addTaskBar(TaskBar taskBar) {
		taskBars.add(taskBar);
	}
	
	@Override
	public void update(String actionId) {
		for (TaskBar taskBar : taskBars) {
			taskBar.updateActionUI(actionId);
		}
	}

	@Override
	public void updateAll() {
		for (TaskBar taskBar : taskBars) {
			taskBar.updateAllActionsUI();
		}
	}

}
