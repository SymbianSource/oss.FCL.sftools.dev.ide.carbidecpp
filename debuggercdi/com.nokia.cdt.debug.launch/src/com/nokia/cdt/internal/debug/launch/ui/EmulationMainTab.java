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

import com.freescale.cdt.debug.cw.core.settings.DebuggerCommonData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;

public class EmulationMainTab extends CarbideMainTab implements IResourceChangeListener {

	protected Label hostLabel;
	protected Text hostText;
	protected Button hostBrowse;
	
	public EmulationMainTab() {
		super(DONT_CHECK_PROGRAM);
	}
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		LaunchPlugin.getDefault().getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.EMULATION_MAIN);
		// Move the focus from the launch config view to this composite. Afterward the focus will move with the tabs.
		comp.setFocus(); 
		
		if (parent instanceof TabFolder)
		{
			TabFolder tabFolder = (TabFolder) parent;
			tabFolder.addSelectionListener(new SelectionListener(){

				public void widgetSelected(SelectionEvent e) {
					if (e.item instanceof TabItem)
						((TabItem)e.item).getControl().setFocus();
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					if (e.item instanceof TabItem)
						((TabItem)e.item).getControl().setFocus();
				}}
			);					
		}
		
		GridLayout topLayout = new GridLayout();
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 1);
		createProjectGroup(comp, 1);
		createHostAppGroup(comp, 1);

		fProjLabel.setToolTipText(Messages.getString("EmulationMainTab.7")); //$NON-NLS-1$
		fProjText.setToolTipText(Messages.getString("EmulationMainTab.7")); //$NON-NLS-1$

		createVerticalSpacer(comp, 1);
		if (wantsTerminalOption() /*&& ProcessFactory.supportesTerminal()*/) {
			createTerminalOption(comp, 1);
		}

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_BUILD);

	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	
	protected void createHostAppGroup(Composite parent, int colSpan) {
		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		projComp.setLayoutData(gd);

		hostLabel = new Label(projComp, SWT.NONE);
		hostLabel.setText(Messages.getString("EmulationMainTab.2")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 2;
		hostLabel.setLayoutData(gd);
		hostLabel.setToolTipText(Messages.getString("EmulationMainTab.3")); //$NON-NLS-1$

		hostText = new Text(projComp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		hostText.setLayoutData(gd);
		hostText.setToolTipText(Messages.getString("EmulationMainTab.3")); //$NON-NLS-1$
		hostText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});

		hostBrowse = createPushButton(projComp, Messages.getString("EmulationMainTab.4"), null); //$NON-NLS-1$
		hostBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("EmulationMainTab.5")); //$NON-NLS-1$
				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					hostText.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
	}

	public void initializeFrom(ILaunchConfiguration config) {
		super.initializeFrom(config);
		try {
			hostText.setText(config.getAttribute(DebuggerCommonData.Host_App_Path, "")); //$NON-NLS-1$
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}
	}

	public void performApply(ILaunchConfigurationWorkingCopy config) {
		super.performApply(config);
		config.setAttribute(DebuggerCommonData.Host_App_Path, hostText.getText());
	}

	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_BUILD) {

			final EmulationMainTab tab = this;
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					tab.getLaunchConfigurationDialog().updateButtons();
					tab.getLaunchConfigurationDialog().updateMessage();
				}
			});
		}
	}
}
