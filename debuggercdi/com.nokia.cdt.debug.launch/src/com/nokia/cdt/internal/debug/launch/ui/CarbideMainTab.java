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

import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.cdt.launch.ui.CMainTab;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

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
		fProgLabel.setData(".uid", "CarbideMainTab.ProgramLabel");
		gd = new GridData();
		gd.horizontalSpan = 3;
		fProgLabel.setLayoutData(gd);
		fProgText = new Text(mainComp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fProgText.setLayoutData(gd);
		fProgText.setData(".uid", "CarbideMainTab.ProgramText");
		fProgText.addModifyListener(new ModifyListener() {
	
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});
	
		Button fBrowseForBinaryButton;
		fBrowseForBinaryButton = createPushButton(mainComp, LaunchMessages.getString("Launch.common.Browse_2"), null); //$NON-NLS-1$
		fBrowseForBinaryButton.setData(".uid", "CarbideMainTab.BrowseForBinaryButton");
		fBrowseForBinaryButton.addSelectionListener(new SelectionAdapter() {
	
			public void widgetSelected(SelectionEvent evt) {
				handleBinaryBrowseButtonSelected();
				updateLaunchConfigurationDialog();
			}
		});
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		
		// forces page to get focus so that help works without having to select some control first.
		getControl().setFocus();
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		fEnableBuildButton.setData(".uid", "CMainTab.EnableBuildButton"); //$NON-NLS-1$ //$NON-NLS-2$
		fDisableBuildButton.setData(".uid", "CMainTab.DisableBuildButton"); //$NON-NLS-1$ //$NON-NLS-2$
		fWorkspaceSettingsButton.setData(".uid", "CMainTab.WorkspaceSettingsButton"); //$NON-NLS-1$ //$NON-NLS-2$ 
		fWorkpsaceSettingsLink.setData(".uid", "CMainTab.WorkspaceSettingsLink"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}