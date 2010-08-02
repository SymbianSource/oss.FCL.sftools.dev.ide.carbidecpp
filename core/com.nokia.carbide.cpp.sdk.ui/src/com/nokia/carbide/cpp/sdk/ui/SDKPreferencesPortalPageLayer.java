/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.cpp.sdk.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.sdk.ui.SDKPreferencePage;
import com.nokia.carbide.internal.discovery.ui.extension.IActionBar;
import com.nokia.carbide.internal.discovery.ui.extension.IActionUIUpdater;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPageLayer;

@SuppressWarnings("restriction")
public class SDKPreferencesPortalPageLayer implements IPortalPageLayer {

	private SDKPreferencePage preferencePage;

	public Control createControl(Composite parent, IEditorPart part) {
		preferencePage = new SDKPreferencePage();
		preferencePage.createControl(parent);
		return preferencePage.getControl();
	}

	public void init() {
		preferencePage.init(PlatformUI.getWorkbench());
	}

	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		return null;
	}

	public void dispose() {
		preferencePage.dispose();
	}

}
