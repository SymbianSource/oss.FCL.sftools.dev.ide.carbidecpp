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

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.cdt.launch.ui.CMainTab;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class CarbideMainTab extends CMainTab {

	public CarbideMainTab() {
		super();
	}

	public CarbideMainTab(int flags) {
		super(flags);
	}
	
	protected void createExeFileGroup(Composite parent, int colSpan) {
		Composite mainComp = new Composite(parent, SWT.NONE);
		GridLayout mainLayout = new GridLayout();
		mainLayout.numColumns = 2;
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainComp.setLayout(mainLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		mainComp.setLayoutData(gd);
		fProgLabel = new Label(mainComp, SWT.NONE);
		fProgLabel.setText(LaunchMessages.getString("CMainTab.C/C++_Application")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 3;
		fProgLabel.setLayoutData(gd);
		fProgText = new Text(mainComp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fProgText.setLayoutData(gd);
		fProgText.addModifyListener(new ModifyListener() {
	
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});
	
		Button fBrowseForBinaryButton;
		fBrowseForBinaryButton = createPushButton(mainComp, LaunchMessages.getString("Launch.common.Browse_2"), null); //$NON-NLS-1$
		fBrowseForBinaryButton.addSelectionListener(new SelectionAdapter() {
	
			public void widgetSelected(SelectionEvent evt) {
				handleBinaryBrowseButtonSelected();
				updateLaunchConfigurationDialog();
			}
		});
	}

	protected void updateProgramFromConfig(ILaunchConfiguration config) {
		if (fProgText != null)
			super.updateProgramFromConfig(config);
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		
		// forces page to get focus so that help works without having to select some control first.
		getControl().setFocus();
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		ICProject cProject = this.getCProject();
		if (cProject != null)
		{
			config.setMappedResources(new IResource[] { cProject.getProject() });
			try { // Only initialize the build config ID once.
				if (config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, "").length() == 0)//$NON-NLS-1$
				{
					ICProjectDescription projDes = CCorePlugin.getDefault().getProjectDescription(cProject.getProject());
					if (projDes != null)
					{
						String buildConfigID = projDes.getActiveConfiguration().getId();
						config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, buildConfigID);			
					}				
				}
			} catch (CoreException e) { 
				LaunchPlugin.log(e);
			}
		}
		config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, fProjText.getText());
		if (fProgText != null) {
			config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, fProgText.getText());
		}
		if (fTerminalButton != null) {
			config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_USE_TERMINAL, fTerminalButton.getSelection());
		}
	}
}