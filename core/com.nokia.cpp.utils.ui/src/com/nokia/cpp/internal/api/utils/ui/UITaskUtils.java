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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.nokia.cpp.utils.ui.noexport.UtilsUIPlugin;

import java.lang.reflect.InvocationTargetException;

/**
 * This class provides utilities to perform various long-running 
 * operations in a UI-friendly way.
 * <p>
 * At the time of writing the primary issue is that the data model and sourcegen
 * operations must be performed on the UI thread, and at the same time provide
 * progress information to avoid unresponsiveness.
 * 
 */
public class UITaskUtils {

	private UITaskUtils() {
	}

	/**
	 * Run this task immediately and do not return until it is
	 * finished.
	 * @param runnable
	 * @return true for succeess, false for failure 
	 */
	public static boolean runImmediately(IRunnableWithProgress runnable) {
		return runImmediately(runnable, ResourcesPlugin.getWorkspace().getRoot());
	}
	
	/**
	 * Run this task immediately and do not return until it is
	 * finished.  Provide the scheudling rule.
	 * @param runnable
	 * @param rule
	 * @return true for succeess, false for failure 
	 */
	public static boolean runImmediately(IRunnableWithProgress runnable, ISchedulingRule rule) {
		try {
			// claim the operation depends on the entire workspace
			PlatformUI.getWorkbench().getProgressService().runInUI(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow(), 
					runnable, 
					rule);
			return true;
		} catch (InvocationTargetException e) {
			UtilsUIPlugin.log(e);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}
}
