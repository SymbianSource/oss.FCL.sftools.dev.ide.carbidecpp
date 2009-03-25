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

package com.nokia.cdt.debug.cw.symbian;

import com.freescale.cdt.debug.cw.core.Debugger;
import com.freescale.cdt.debug.cw.core.cdi.model.Target;

public class SymbianDebugger extends Debugger {

	public static final String DEBUGGER_ID = "com.nokia.cdt.debug.cw.symbian.SymbianDebugger"; //$NON-NLS-1$
	
	public boolean shouldAutoDisableWatchpoints(Target target) {
		return false;
	}

}