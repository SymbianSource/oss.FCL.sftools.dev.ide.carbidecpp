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

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class CrashDebuggerPerspectiveFactory implements IPerspectiveFactory {
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		layout.addView("com.nokia.carbide.cpp.debug.crashdebugger.ui.CrashDebuggerView", IPageLayout.TOP, IPageLayout.RATIO_MAX, editorArea); //$NON-NLS-1$

		layout.addShowViewShortcut("com.nokia.carbide.cpp.debug.crashdebugger.ui.CrashDebuggerView"); //$NON-NLS-1$
		layout.addShowViewShortcut("org.eclipse.help.ui.HelpView"); //$NON-NLS-1$

		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
	}
}
