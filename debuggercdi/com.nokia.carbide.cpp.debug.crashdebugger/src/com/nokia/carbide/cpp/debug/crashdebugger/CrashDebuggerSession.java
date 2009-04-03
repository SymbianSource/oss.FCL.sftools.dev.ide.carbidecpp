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

import org.eclipse.debug.core.ILaunch;

import com.freescale.cdt.debug.cw.CWException;
import com.freescale.cdt.debug.cw.core.Debugger;
import com.freescale.cdt.debug.cw.core.cdi.DeviceCommandSession;
import com.freescale.cdt.debug.cw.core.cdi.ISessionOutputReceiver;
import com.freescale.cdt.debug.cw.core.cdi.model.Target;
import com.freescale.cdt.debug.cw.core.cdi.model.TargetConfiguration;

import cwdbg.ConnectionClient;
import cwdbg.ConnectionContext;
import cwdbg.Machine;

public class CrashDebuggerSession extends DeviceCommandSession {

	TargetConfiguration4CrashDebugger m_targetConfiguration = null;

	public CrashDebuggerSession(Debugger debugger, ILaunch launch,
			ConnectionContext context, ConnectionClient client,
			Machine cwMachine, ISessionOutputReceiver receiver)
			throws CWException {

		super(debugger, launch, context, client, cwMachine, receiver);

		m_targetConfiguration = new TargetConfiguration4CrashDebugger(null);
	}

	public TargetConfiguration getTargetConfiguration() {
		return m_targetConfiguration;
	}

	// override parent to create our special CDI targets.
	// ......................... 01/24/07
	protected Target createCDITarget(ILaunch launch,
			cwdbg.Target cwLaunchTarget, cwdbg.SymWorld cwLaunchSymWorld) {
		return new CrashDebuggerTarget(this, launch, cwLaunchTarget,
				cwLaunchSymWorld);
	}

	public void debuggingStopped(Target target) {
		// This will remove the target from the session.
		exitTarget(target);

		if (m_targets.size() == 0) {
			// Shut down actions in the CD console.
			CrashDebugger.sessionEnded();

			// call parent to shut down Crash debugger remote connection and
			// then Corba connection.
			super.debuggingStopped(null);
		}
	}
}
