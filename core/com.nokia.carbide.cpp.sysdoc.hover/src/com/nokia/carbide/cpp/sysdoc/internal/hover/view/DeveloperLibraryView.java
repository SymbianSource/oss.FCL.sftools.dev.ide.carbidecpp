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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.view;

import java.net.URL;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * 
 * Developer Library view displays content of API in a view rather than pop-up
 * browser
 */

public class DeveloperLibraryView extends ViewPart {
	private static IBrowserInformationControlAgent browserInformationControlAgent = new BrowserInformationControlAgentImp(
			true);
	public static final String VIEW_ID = "com.nokia.carbide.cpp.sysdoc.internal.hover.view.DeveloperLibraryView";

	/**
	 * The constructor.
	 */
	public DeveloperLibraryView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		browserInformationControlAgent.setShell(parent.getShell());
		browserInformationControlAgent.createContent(parent);
	}

	public static void setURL(URL url) {
		browserInformationControlAgent.setURL(url);
	}

	@Override
	public void setFocus() {
	}
}