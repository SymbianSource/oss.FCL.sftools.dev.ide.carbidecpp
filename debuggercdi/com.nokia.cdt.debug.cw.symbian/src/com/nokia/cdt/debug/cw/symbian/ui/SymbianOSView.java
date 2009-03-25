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
package com.nokia.cdt.debug.cw.symbian.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.freescale.cdt.debug.cw.core.ui.os.OSView;

public class SymbianOSView extends OSView {

	public static final String SYMBIAN_OS_VIEW = "com.nokia.cdt.debug.cw.symbian.symbian_os_view"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see com.freescale.cdt.debug.cw.core.ui.os.OSView#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, SYMBIAN_OS_VIEW);
	}

}
