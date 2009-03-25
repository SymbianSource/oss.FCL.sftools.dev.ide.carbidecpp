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

import org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration;
import org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration2;

import com.freescale.cdt.debug.cw.core.CWPlugin;
import com.freescale.cdt.debug.cw.core.cdi.model.Target;
import com.freescale.cdt.debug.cw.core.cdi.model.TargetConfiguration;

import cwdbg.PreferenceConstants;

public class TargetConfiguration4CrashDebugger extends TargetConfiguration implements
		ICDITargetConfiguration, ICDITargetConfiguration2 {
	public TargetConfiguration4CrashDebugger(Target target) {
		super(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsTerminate()
	 */
	public boolean supportsTerminate() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsDisconnect()
	 */
	public boolean supportsDisconnect() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsSuspend()
	 */
	public boolean supportsSuspend() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsResume()
	 */
	public boolean supportsResume() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsRestart()
	 */
	public boolean supportsRestart() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsStepping()
	 */
	public boolean supportsStepping() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsInstructionStepping()
	 */
	public boolean supportsInstructionStepping() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsBreakpoints()
	 */
	public boolean supportsBreakpoints() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsRegisters()
	 */
	public boolean supportsRegisters() {
		return true; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsRegisterModification()
	 */
	public boolean supportsRegisterModification() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsSharedLibrary()
	 */
	public boolean supportsSharedLibrary() {
		return true; // ??
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsMemoryRetrieval()
	 */
	public boolean supportsMemoryRetrieval() {
		return true; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsMemoryModification()
	 */
	public boolean supportsMemoryModification() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration#supportsExpressionEvaluation()
	 */
	public boolean supportsExpressionEvaluation() {
		return true;
	}

	public boolean supportsGlobalVariableRetrieval() {
		return true;
	}

	public boolean supportsThreadControl() {
		return false;
	}

	public boolean supportsRuntimeTypeIdentification() {
		return CWPlugin.getDefault().getPluginPreferences().getBoolean(
				PreferenceConstants.J_PN_ShowRuntimeType);
	}

	public boolean supportsAddressBreaksOnStartup() {
		return false;
	}

	public boolean supportsPassiveVariableUpdate() {
		return true;
	}
}
