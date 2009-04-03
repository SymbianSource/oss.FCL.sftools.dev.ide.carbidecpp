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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis;

import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;

/**
 * This class runs runnable either in asyncronous or synchronous mode. In junit
 * test cases, runnable called in asynchronous mode.
 */
public class ExecutorAgent {

	public static void run(Runnable runnable) {
		Display display = Activator.getDefault().getWorkbench().getDisplay();
		if (HoverManager.isTestMode()) {
			display.syncExec(runnable);
		} else {
			display.asyncExec(runnable);
		}
	}
}
