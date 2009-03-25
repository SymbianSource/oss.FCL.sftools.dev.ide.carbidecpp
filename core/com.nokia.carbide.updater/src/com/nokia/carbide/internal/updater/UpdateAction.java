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

package com.nokia.carbide.internal.updater;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import com.nokia.carbide.internal.api.updater.IProjectUpdateSession;

public class UpdateAction implements IWorkbenchWindowActionDelegate {
	
	public UpdateAction() {
	}

	public void dispose() {
	}
	
	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		ProjectUpdateManager pum = ProjectUpdateManager.instance();
		IProjectUpdateSession session = pum.createSession();
		session.setUserInitiated(true);
		session.setScanForProjects(true);
		pum.addAllRefactoringProjectsToSession(session);
		session.setConfirmFileUpdates(true);
		session.setSilentIfEmpty(false);
		session.updateProjects();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
