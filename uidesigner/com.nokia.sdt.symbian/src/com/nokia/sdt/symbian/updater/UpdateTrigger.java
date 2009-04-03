/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.symbian.updater;

import com.nokia.carbide.updater.extension.IUpdateTrigger;
import com.nokia.sdt.symbian.SymbianPlugin;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * Class implementing the com.nokia.carbide.updater.updateTrigger extension point
 * 
 * This implementation will check that the refactoring dialog was shown at startup once
 */
public class UpdateTrigger implements IUpdateTrigger {

	// use the version of the UI Designer model updating to as the trigger property
	private static final String TRIGGER_PROPERTY = "1.1.0"; //$NON-NLS-1$
	private static final String LOCAL_KEY = ".updateTrigger"; //$NON-NLS-1$
	private static final QualifiedName key = new QualifiedName(SymbianPlugin.PI_NAME, LOCAL_KEY);

	public boolean workspaceNeedsUpdate(UpdateType type) {
		if (type == UpdateType.PROJECT)
			return false;
		
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		try {
			String string = workspaceRoot.getPersistentProperty(key);
			if (string != null && string.equals(TRIGGER_PROPERTY)) {
				return false;
			}
		} 
		catch (CoreException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	public void postTrigger(UpdateType type, boolean triggered) {
		if (triggered && type == UpdateType.FILE)
			writeProperty();
	}
	
	public static void writeProperty() {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		try {
			workspaceRoot.setPersistentProperty(key, TRIGGER_PROPERTY);
		} 
		catch (CoreException e) {
			System.out.println(e.getMessage());
		}
	}

}
