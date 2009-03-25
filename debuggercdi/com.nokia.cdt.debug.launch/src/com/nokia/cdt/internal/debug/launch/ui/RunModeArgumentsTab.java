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
package com.nokia.cdt.internal.debug.launch.ui;

import cwdbg.PreferenceConstants;

import org.eclipse.cdt.launch.internal.ui.LaunchImages;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.cdt.launch.internal.ui.LaunchUIPlugin;
import org.eclipse.cdt.launch.ui.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class RunModeArgumentsTab extends CLaunchConfigurationTab {

	protected Text argumentsText;

	public void createControl(Composite parent) {
	    Composite composite = new Composite(parent, SWT.NONE);
	    composite.setLayout(new GridLayout());
	    composite.setFont(parent.getFont());

	    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
	    setControl(composite);
	    
		LaunchUIPlugin.getDefault().getWorkbench().getHelpSystem().setHelp(getControl(), ICDTLaunchHelpContextIds.LAUNCH_CONFIGURATION_DIALOG_ARGUMNETS_TAB);
			
	    Group group = new Group(composite, SWT.NONE);
	    Font font = composite.getFont();
		group.setFont(font);
	    group.setLayout(new GridLayout());
	    group.setLayoutData(new GridData(GridData.FILL_BOTH));
	    
		group.setText(LaunchMessages.getString("CArgumentsTab.C/C++_Program_Arguments")); //$NON-NLS-1$
		argumentsText = new Text(group, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		argumentsText.getAccessible().addAccessibleListener(new AccessibleAdapter() {                       
			public void getName(AccessibleEvent e) {
				e.result = LaunchMessages.getString("CArgumentsTab.C/C++_Program_Arguments"); //$NON-NLS-1$
			}
        });
	    GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 40;
	    gd.widthHint = 100;
		argumentsText.setLayoutData(gd);
	    argumentsText.setFont(font);
		argumentsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			argumentsText.setText(configuration.getAttribute(PreferenceConstants.J_PN_ProgramArguments, "")); //$NON-NLS-1$
		}
		catch (CoreException e) {
			setErrorMessage(LaunchMessages.getFormattedString("Launch.common.Exception_occurred_reading_configuration_EXCEPTION", e.getStatus().getMessage())); //$NON-NLS-1$
			LaunchUIPlugin.log(e);
		}
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String attributeValue = 
			argumentsText.getText().trim().replaceAll("\r\n", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		configuration.setAttribute(PreferenceConstants.J_PN_ProgramArguments, attributeValue);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PreferenceConstants.J_PN_ProgramArguments, (String) null);
	}

	@Override
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_ARGUMENTS_TAB);
	}

	public String getName() {
		return LaunchMessages.getString("CArgumentsTab.Arguments"); //$NON-NLS-1$
	}

}
