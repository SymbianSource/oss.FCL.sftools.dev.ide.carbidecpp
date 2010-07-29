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
package com.nokia.carbide.internal.discovery.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

import com.nokia.carbide.internal.discovery.ui.extension.IPortalPageLayer;

public class SupportPage implements IPortalPageLayer {

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setLayout(new FillLayout());

		return composite;
	}

	@Override
	public void init() {
	}

	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		return null;
	}

	@Override
	public void dispose() {
	}
}
