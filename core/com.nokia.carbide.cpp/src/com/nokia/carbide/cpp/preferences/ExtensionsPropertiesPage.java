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
package com.nokia.carbide.cpp.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * Creates an empty Carbide Extensions "category" to project properties page
 */
public class ExtensionsPropertiesPage extends PropertyPage {
	/**
	 * Create the property page
	 */
	public ExtensionsPropertiesPage() {
		super();
	}
		
	public void init(IWorkbench arg0) {
	}

	@Override
	public void createControl(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		setControl(content);
		final GridLayout gridLayout = new GridLayout();
		content.setLayout(gridLayout);
		
		final Label selectWhichPlatformsLabel = new Label(content, SWT.NONE);
		selectWhichPlatformsLabel.setLayoutData(new GridData());
		
		selectWhichPlatformsLabel.setText("Carbide Extensions Property Page\n\nAdditional 3rd party plugins and utilities that contribute project settings\nto the Carbide.c++ product may reside under this property page."); //$NON-NLS-1$
	}
	
	@Override
	protected Control createContents(Composite parent) {
		return null;
	}
}
