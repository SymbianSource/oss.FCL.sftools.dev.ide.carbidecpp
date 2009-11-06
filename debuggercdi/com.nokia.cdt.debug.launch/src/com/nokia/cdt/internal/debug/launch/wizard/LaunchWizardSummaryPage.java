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
package com.nokia.cdt.internal.debug.launch.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class LaunchWizardSummaryPage extends WizardPage implements ISummaryTextItemContainer {

	private Text summaryText;
	private Text youCanChangeText;
	private Button showSettingsButton;
	private Map<String, String> summaryItems;
	/**
	 * Create the wizard
	 * @param configName 
	 */
	public LaunchWizardSummaryPage(String configName) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("LaunchWizardSummaryPage.1")); //$NON-NLS-1$
		String description = MessageFormat.format(Messages.getString("LaunchWizardSummaryPage.2"), (Object[])new String[] {configName}); //$NON-NLS-1$
		setDescription(description);
		summaryItems = new LinkedHashMap<String, String>();
	}
	
	public void putSummaryTextItem(String key, String summaryItem) {
		summaryItems.remove(key);
		if (summaryItem != null)
			summaryItems.put(key, summaryItem);
		
		if (getControl().isVisible()) {
			updateSummaryText();
		}
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		//
		setControl(container);
		
		summaryText = new Text(container, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
		summaryText.setEditable(false);
		summaryText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		youCanChangeText = new Text(container, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
		youCanChangeText.setEditable(false);
		youCanChangeText.setText(Messages.getString("LaunchWizardSummaryPage.3")); //$NON-NLS-1$
		youCanChangeText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		youCanChangeText.setData(".uid", "LaunchWizardSummaryPage.youCanChangeText");

		showSettingsButton = new Button(container, SWT.CHECK);
		showSettingsButton.setText(Messages.getString("LaunchWizardSummaryPage.4")); //$NON-NLS-1$
		showSettingsButton.setData(".uid", "LaunchWizardSummaryPage.showSettingsButton");

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchWizardHelpIds.WIZARD_SUMMARY_PAGE);
	}
	
	public void setVisible(boolean visible) {
    	super.setVisible(visible);
		if (visible) {
			updateSummaryText();
		}
	}

	private void updateSummaryText() {
		StringBuilder builder = new StringBuilder();
		builder.append(Messages.getString("LaunchWizardSummaryPage.SummaryHeading")); //$NON-NLS-1$
		for (String string : summaryItems.values()) {
			builder.append('\t');
			builder.append('\u2022'); // bullet
			builder.append(' ');
			builder.append(string);
			builder.append('\n');
		}
		summaryText.setText(builder.toString());
		summaryText.setFocus();
	}

	public boolean shouldOpenLaunchConfigurationDialog() {
		return showSettingsButton.getSelection();
	}

}
