/*******************************************************************************  
 * Copyright ©2007 Nokia Corporation. All rights reserved.                        
 *******************************************************************************/

package com.nokia.carbide.cpp.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Creates an empty Carbide Extensions "category" to Preferences page
 */
public class ExtensionsPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage{
	/**
	 * Create the preference page
	 */
	public ExtensionsPreferencesPage() {
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
		
		selectWhichPlatformsLabel.setText("Carbide Extensions Preference Page\n\nAdditional 3rd party plugins and utilities that contribute global settings\nto the Carbide.c++ product may reside under this preference page."); //$NON-NLS-1$
	}
	
	@Override
	protected Control createContents(Composite parent) {
		return null;
	}
}
