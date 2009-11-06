/* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.cdt.internal.debug.launch.wizard;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * A wizard page that handles build options.
 *
 */
public class BuildOptionsSelectionPage extends WizardPage {

	private Button fDisableBuildButton;
	private Button fEnableBuildButton;
	private Button fWorkspaceSettingsButton;
	private Link fWorkpsaceSettingsLink;

	/**
	 * Constructor.
	 */
	public BuildOptionsSelectionPage() {
		super("BuildOptionsSelectionPage"); //$NON-NLS-1$
		setTitle(Messages.getString("BuildOptionsSelectionPage.Title")); //$NON-NLS-1$
		setDescription(Messages.getString("BuildOptionsSelectionPage.Description")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(1, false));
        composite.setLayoutData(new GridData());
		setControl(composite);

		Group optionsGroup = new Group(composite, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;
		gridLayout.makeColumnsEqualWidth= true;
		optionsGroup.setLayoutData(gridData);
		optionsGroup.setLayout(gridLayout);
		optionsGroup.setText(Messages.getString("BuildOptionsSelectionPage.OptionsGroupLabel"));
		optionsGroup.setData(".uid", "BuildOptionsSelectionPage.optionsGroup");

		fDisableBuildButton = new Button(optionsGroup, SWT.RADIO);
		fDisableBuildButton.setText(Messages.getString("BuildOptionsSelectionPage.DisableButtonLabel")); //$NON-NLS-1$
		fDisableBuildButton.setToolTipText(Messages.getString("BuildOptionsSelectionPage.DisableButtonToolTip")); //$NON-NLS-1$
		fDisableBuildButton.setData(".uid", "BuildOptionsSelectionPage.disableBuildButton");
		
		new Label(optionsGroup, SWT.NONE);
		fEnableBuildButton = new Button(optionsGroup, SWT.RADIO);
		fEnableBuildButton.setText(Messages.getString("BuildOptionsSelectionPage.EnableButtonLabel")); //$NON-NLS-1$
		fEnableBuildButton.setToolTipText(Messages.getString("BuildOptionsSelectionPage.EnableButtonToolTip")); //$NON-NLS-1$
		fEnableBuildButton.setData(".uid", "BuildOptionsSelectionPage.enableBuildButon");
		
		new Label(optionsGroup, SWT.NONE);
		fWorkspaceSettingsButton = new Button(optionsGroup, SWT.RADIO);
		fWorkspaceSettingsButton.setText(Messages.getString("BuildOptionsSelectionPage.WorkspaceSettingsButtonLabel")); //$NON-NLS-1$
		fWorkspaceSettingsButton.setToolTipText(Messages.getString("BuildOptionsSelectionPage.WorkspaceSettingsButtonToolTip")); //$NON-NLS-1$
		fWorkspaceSettingsButton.setData(".uid", "BuildOptionsSelectionPage.workspaceSettingsButton");
		
		fWorkpsaceSettingsLink = new Link(optionsGroup, SWT.NONE);
		fWorkpsaceSettingsLink.setText(Messages.getString("BuildOptionsSelectionPage.WorkspaceSettingsLinkLabel")); //$NON-NLS-1$
		fWorkpsaceSettingsLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(
						composite.getShell(), 
						Messages.getString("BuildOptionsSelectionPage.WorkspaceSettingsPageID"), //$NON-NLS-1$
						null, 
						null).open();
			}
		});
		fWorkpsaceSettingsLink.setData(".uid", "BuildOptionsSelectionPage.workspaceSettingsLink");

		setDefaults();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#performHelp()
	 */
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.BUILD_OPTIONS_SELECTION_PAGE);
	}

    /**
     * Initialize the default values for this page
     */
    public void setDefaults() {
    	fWorkspaceSettingsButton.setSelection(true);
    }

	/**
	 * Copy build option value to the given launch configuration
	 * @param config - launch configuration provided by caller
	 */
    public void updateConfiguration(ILaunchConfigurationWorkingCopy config) {
		int buildBeforeLaunchValue = ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_USE_WORKSPACE_SETTING;
		if (fDisableBuildButton.getSelection()) {
			buildBeforeLaunchValue = ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_DISABLED;
		} else if (fEnableBuildButton.getSelection()) {
			buildBeforeLaunchValue = ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_ENABLED;
		}
		config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH, buildBeforeLaunchValue);
    }

}