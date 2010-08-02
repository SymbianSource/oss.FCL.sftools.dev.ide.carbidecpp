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
package com.nokia.carbide.internal.discovery.ui.editor;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class StackComposite extends SharedBackgroundComposite {

	private StackLayout stackLayout;

	public StackComposite(Composite parent, Composite backgroundParent) {
		super(parent, backgroundParent);
		stackLayout = new StackLayout();
		setLayout(stackLayout);
	}

	public void showControl(Control control) {
		if (stackLayout.topControl == control)
			return;

		stackLayout.topControl = control;
		layout();
	}
}

