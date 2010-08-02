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


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.internal.discovery.ui.extension.AbstractDiscoveryPortalPageLayer;

public class InstallExtensionsPortalExtension extends AbstractDiscoveryPortalPageLayer {

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Control control = super.createControl(parent, part);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, 
				"com.nokia.carbide.discovery.ui.view.DiscoveryView.catalogviewer"); //$NON-NLS-1$

		return control;
	}
}
