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
package com.nokia.carbide.cpp.internal.project.core.updater;

import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.updater.extension.IUpdateTrigger.UpdateType;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractUpdateTrigger {

	private static final String INTRO_VIEW = "org.eclipse.ui.internal.introview"; //$NON-NLS-1$
	protected static final String CARBIDE_PERSPECTIVE_ID = "com.nokia.carbide.cpp.CarbideCppPerspective"; //$NON-NLS-1$

	private QualifiedName key;

	protected abstract String getLocalKey();
	protected abstract String getTriggerProperty();
	protected abstract void postTriggerAction();
	
	public void writeProperty() {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		try {
			if (key == null)
				key = new QualifiedName(ProjectCorePlugin.PLUGIN_ID, getLocalKey());
			workspaceRoot.setPersistentProperty(key, getTriggerProperty());
		} 
		catch (CoreException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean workspaceNeedsUpdate(UpdateType type) {
		if (type == UpdateType.FILE)
			return false;
		
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		try {
			if (key == null)
				key = new QualifiedName(ProjectCorePlugin.PLUGIN_ID, getLocalKey());
			String string = workspaceRoot.getPersistentProperty(key);
			if (string != null && string.equals(getTriggerProperty())) {
				return false;
			}
		} 
		catch (CoreException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	public void postTrigger(UpdateType type, boolean triggered) {
		if (triggered && type == UpdateType.PROJECT) {
			writeProperty();
			postTriggerAction();
		}
	}


	protected boolean isIntroViewActive(IWorkbenchPage page) {
		IWorkbenchPart activePart = page.getActivePart();
		String id = activePart.getSite().getId();
		return INTRO_VIEW.equals(id);
	}

}
