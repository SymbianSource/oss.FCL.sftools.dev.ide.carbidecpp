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
package com.nokia.cdt.internal.debug.launch;

import org.eclipse.debug.core.model.IDebugTarget;

public class ATFLaunchSupport {
	// Pass to IDebugTarget the outside world (e.g. test framework), it is dropped by Eclipse core debugger
	private static IDebugTarget debugTargetFromLaunchDelegate = null;
	
	/**
	 * Pass debug target from LaunchDelegate to outside world (e.g. test framework)
	 * We read and clear to save us the hassle of clearing before each usage.
	 */
	public static IDebugTarget getAndClearDebugTargetFromLaunchDelegate() {
		IDebugTarget result = debugTargetFromLaunchDelegate;
		debugTargetFromLaunchDelegate = null;
		return result;
	}

	/**
	 * Saves debug target from LaunchDelegate to outside world (e.g. test framework)
	 */
	public static void saveDebugTargetFromLaunchDelegate(IDebugTarget debugTarget) {
		debugTargetFromLaunchDelegate = debugTarget;
	}

}
