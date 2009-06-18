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

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

public class StopModeMainTab extends CarbideMainTab {

	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		LaunchPlugin.getDefault().getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.STOP_MODE_MAIN);
		
		GridLayout topLayout = new GridLayout();
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 1);
		createProjectGroup(comp, 1);
		createExeFileGroup(comp, 1);
		
		fProgLabel.setText(Messages.getString("StopModeMainTab.0")); //$NON-NLS-1$
		fProgLabel.setToolTipText(Messages.getString("StopModeMainTab.1")); //$NON-NLS-1$
		fProgText.setToolTipText(Messages.getString("StopModeMainTab.1")); //$NON-NLS-1$

		fProjLabel.setToolTipText(Messages.getString("StopModeMainTab.2")); //$NON-NLS-1$
		fProjText.setToolTipText(Messages.getString("StopModeMainTab.2")); //$NON-NLS-1$

		createVerticalSpacer(comp, 1);
		if (wantsTerminalOption() /*&& ProcessFactory.supportesTerminal()*/) {
			createTerminalOption(comp, 1);
			createVerticalSpacer(comp, 1);
		}
		createBuildOptionGroup(comp, 1);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
	}

	public void initializeFrom(ILaunchConfiguration config) {
		super.initializeFrom(config);
	}

	public void performApply(ILaunchConfigurationWorkingCopy config) {
		super.performApply(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration config) {
		boolean result = super.isValid(config);
		return result;
	}

}
