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
package com.nokia.cdt.debug.cw.symbian.breakpointactions;

import org.eclipse.cdt.debug.core.breakpointactions.IBreakpointAction;
import org.eclipse.cdt.debug.ui.breakpointactions.IBreakpointActionPage;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.swt.widgets.Composite;

public class SkipActionPage extends PlatformObject implements IBreakpointActionPage {

	private SkipActionComposite skipComposite;
	private SkipAction skipAction;

	public void actionDialogCanceled() {
	}

	public void actionDialogOK() {
	}

	public Composite createComposite(IBreakpointAction action, Composite composite, int style) {
		skipAction = (SkipAction)action;
		skipComposite = new SkipActionComposite(composite, style, this);
		return skipComposite;
	}

	public SkipAction getSkipAction() {
		return skipAction;
	}

}
