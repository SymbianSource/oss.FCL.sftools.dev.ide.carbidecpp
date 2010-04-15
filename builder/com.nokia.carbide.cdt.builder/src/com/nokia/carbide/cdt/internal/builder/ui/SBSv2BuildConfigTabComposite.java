/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.CarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.ISBSv2BuildConfigInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SBSv2BuildConfigTabComposite extends Composite {
	
	private static final String configCmdLabelPrefixText = "Configuration Command: -c "; 
	private static final String releaseTreeLabelPrefixText = "Release Tree: "; 
	
	Text variantEdit;
	
	Label configCmdLabel;
	Label releaseTreeLabel;
	private ICarbideBuildConfiguration config;
	
	public SBSv2BuildConfigTabComposite(TabItem tabItem) {
		super(tabItem.getParent(), SWT.NONE);
	}

	public void createControls() {
		setLayout(new GridLayout(2, false));

		Label variantLabel = new Label(this, SWT.NONE);
		variantLabel.setText(Messages.getString("CarbideSBSv2ConfigTab.VariantLabel")); //$NON-NLS-1$
		variantLabel.setToolTipText(Messages.getString("CarbideSBSv2ConfigTab.VariantLabel_ToolTip")); //$NON-NLS-1$

		variantEdit = new Text(this, SWT.BORDER);
		variantEdit.setToolTipText(Messages.getString("CarbideSBSv2ConfigTab.VariantLabel_ToolTip")); //$NON-NLS-1$
		variantEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		SelectionListener listener;
		variantEdit.addKeyListener( new KeyListener() {
			
			public void keyReleased(KeyEvent e) {
				setVaraintDetailsText();
			}
			
			public void keyPressed(KeyEvent e) {
				// ignore
			}
		});
		
		Group emulatorBuildOptionsGroup;
		emulatorBuildOptionsGroup = new Group(this, SWT.NONE);
		emulatorBuildOptionsGroup.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		emulatorBuildOptionsGroup.setLayoutData(gd);
		emulatorBuildOptionsGroup.setText("Build Variant Details"); //$NON-NLS-1$

		configCmdLabel = new Label(emulatorBuildOptionsGroup, SWT.CHECK);
		configCmdLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		releaseTreeLabel = new Label(emulatorBuildOptionsGroup, SWT.CHECK);
		releaseTreeLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	public void initData(ICarbideBuildConfiguration buildConfig) {
		this.config = buildConfig;
		ISBSv2BuildConfigInfo sbsv2ConfigInfo = ((CarbideBuildConfiguration)buildConfig).getSBSv2ConfigInfo();
		if (sbsv2ConfigInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_VARIANT) != null){
			variantEdit.setText(sbsv2ConfigInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_VARIANT));
		}
		
		setVaraintDetailsText();
	}
	
	private void setVaraintDetailsText() {
		
		String configCmdText = configCmdLabelPrefixText;
		if (config != null){
			configCmdText += config.getSBSv2Alias() + variantEdit.getText();
		}
		configCmdLabel.setText(configCmdText);

		String variantText = SBSv2Utils.getVariantOutputDirModifier(variantEdit.getText());
		if (variantText == null) variantText = "";	
		String relTreeText = releaseTreeLabelPrefixText;
		if (config != null){
			relTreeText += " " + config.getSDK().getEPOCROOT() + "epoc32" + File.separator + "release" + File.separator + config.getPlatformString().toLowerCase() + variantText + File.separator + config.getTargetString().toLowerCase();
		}
		releaseTreeLabel.setText(relTreeText);
	}

	

	public boolean compareConfigurationSettings(ICarbideBuildConfiguration selectedConfig, boolean writeToConfig) {
		boolean settingsEqual = true;
		
		ISBSv2BuildConfigInfo currSBSv2Info = ((CarbideBuildConfiguration)selectedConfig).getSBSv2ConfigInfo();
		settingsEqual = currSBSv2Info.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_VARIANT).equals(variantEdit.getText());
		
		if (!settingsEqual && writeToConfig) {
			currSBSv2Info.setSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_VARIANT, variantEdit.getText());
		}
		
		return settingsEqual;
	}
	
	public void performDefaults(ISymbianSDK sdk) {
		variantEdit.setText("");
	}
}
