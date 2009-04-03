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

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration;
import org.eclipse.debug.core.ILaunch;

import com.freescale.cdt.debug.cw.core.cdi.DeviceCommandSession;
import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.freescale.cdt.debug.cw.core.cdi.model.Target;

import cwdbg.SymWorld;

public class CrashDebuggerTarget extends Target {

	CrashDebuggerSession m_cdSession = null;
	
	public CrashDebuggerTarget(Session session, ILaunch launch,
			cwdbg.Target cwLaunchTarget, SymWorld cwLaunchSymWorld) {
		super(session, launch, cwLaunchTarget, cwLaunchSymWorld);
		
		m_cdSession = (CrashDebuggerSession)session;
		assert(m_cdSession != null);
	}

	public ICDITargetConfiguration getConfiguration() {
		return m_cdSession.getTargetConfiguration();
	}
	
	// handle text output from debuggee. 
	public void HandleDebuggeeOutput(String text) {
		m_cdSession.outputReceived(text);
	}

	public void terminate() throws CDIException {
		CrashDebugger.sessionEnded();
		super.terminate();
	}
}
