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
package com.nokia.carbide.cdt.internal.builder.ui;

import java.io.File;

import org.eclipse.cdt.utils.ui.controls.ControlFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.IROMBuilderInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

/**
 * Arguments tab for the Carbide Build Configurations page
 *
 */
public class ROMBuilderTabComposite extends Composite {

	private Text cmdLineEdit;
	private Text workingDirEdit;
	private Button workingDirBrowse;

	
	public ROMBuilderTabComposite(TabItem tabItem) {
		super(tabItem.getParent(), SWT.NONE);
	}

	public void createControls() {
		setLayout(new GridLayout(3, false));

		Label cmdLineLabel = new Label(this, SWT.NONE);
		cmdLineLabel.setText(Messages.getString("CarbideRomBuilderTab.Cmd_Line")); //$NON-NLS-1$
		cmdLineLabel.setToolTipText(Messages.getString("CarbideRomBuilderTab.Cmd_Line_Tool_Tip")); //$NON-NLS-1$

		cmdLineEdit = new Text(this, SWT.BORDER);
		cmdLineEdit.setToolTipText(Messages.getString("CarbideRomBuilderTab.BldfilesArgumentsToolTip")); //$NON-NLS-1$
		cmdLineEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label workingDirLabel = new Label(this, SWT.NONE);
		workingDirLabel.setText(Messages.getString("CarbideRomBuilderTab.Working_Dir")); //$NON-NLS-1$
		workingDirLabel.setToolTipText(Messages.getString("CarbideRomBuilderTab.Working_Dir_Tool_Tip")); //$NON-NLS-1$

		workingDirEdit = new Text(this, SWT.BORDER);
		workingDirEdit.setToolTipText(Messages.getString("CarbideRomBuilderTab.Working_Dir_Tool_Tip")); //$NON-NLS-1$
		workingDirEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		workingDirBrowse = ControlFactory.createPushButton(this, Messages.getString("CarbideRomBuilderTab.Browse")); //$NON-NLS-1$
		workingDirBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.NONE);
				BrowseDialogUtils.initializeFrom(dialog, workingDirEdit);

				dialog.setText(Messages.getString("CarbideRomBuilderTab.Rom_Dir_Dialog_Title")); //$NON-NLS-1$
				String result = dialog.open();
				
				if (result != null) {
					workingDirEdit.setText(result);
				} 
			}
		});
	}
	
	public void initData(ICarbideBuildConfiguration buildConfig) {
		IROMBuilderInfo romInfo = buildConfig.getROMBuildInfo();
		cmdLineEdit.setText(romInfo.getCommandLine());
		workingDirEdit.setText(romInfo.getWorkingDirectory());
	}
	
	public boolean compareConfigurationSettings(ICarbideBuildConfiguration selectedConfig, boolean writeToConfig) {
		boolean settingsEqual = true;
		
		IROMBuilderInfo existingInfo = selectedConfig.getROMBuildInfo();
		settingsEqual = existingInfo.getCommandLine().equals(cmdLineEdit.getText()) &&
		existingInfo.getWorkingDirectory().equals(workingDirEdit.getText());
		
		if (!settingsEqual && writeToConfig) {
			existingInfo.setCommandLine(cmdLineEdit.getText());
			existingInfo.setWorkingDirectory(workingDirEdit.getText());
		}
		
		return settingsEqual;
	}
	
	public void performDefaults(ISymbianSDK sdk) {
		cmdLineEdit.setText(""); //$NON-NLS-1$
		workingDirEdit.setText("C:\\"); //$NON-NLS-1$

		// now set epoc32\rom folder in the sdk as the default working dir 
		// this is most common folder that rom images are built from for most symbian kits..
		String dir = sdk.getEPOCROOT() + "epoc32\\rom\\";  //$NON-NLS-1$
		if (new File(dir).exists())
			workingDirEdit.setText(dir);
	}
}
