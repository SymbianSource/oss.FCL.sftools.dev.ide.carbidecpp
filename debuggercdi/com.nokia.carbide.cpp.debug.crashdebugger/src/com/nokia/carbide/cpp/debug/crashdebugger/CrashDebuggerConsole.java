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
package com.nokia.carbide.cpp.debug.crashdebugger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.*;

public class CrashDebuggerConsole extends IOConsole {
	
	private IOConsoleOutputStream txStream;
	
	// Indicates whether the console's streams have been initialized
	private boolean initialized = false;
	
	public CrashDebuggerConsole(String consoleName) {
		super(consoleName, null);
	}
	
	protected void init() {
		// Called when console is added to the console view
		super.init();
		
		//	Ensure that initialization occurs in the ui thread
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}		
		display.asyncExec(new Runnable() {
			public void run() {
				initializeStreams();
			}
		});
	}
	
	/*
	 * Initialize thre streams of the console. Must be 
	 * called from the UI thread.
	 */
	private void initializeStreams() {
		if (!initialized) {
			txStream = newOutputStream();
			initialized = true;
		}
	}
	
	public IOConsoleOutputStream getOutputStream() {
		initializeStreams();
		return txStream;
	}
}
