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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.CarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.ISBSv2BuildConfigInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SBSv2BuildConfigTabComposite extends Composite {
	
	Text variantEdit;
	
	public SBSv2BuildConfigTabComposite(TabItem tabItem) {
		super(tabItem.getParent(), SWT.NONE);
	}

	public void createControls() {
		setLayout(new GridLayout(3, false));

		Label variantLabel = new Label(this, SWT.NONE);
		variantLabel.setText(Messages.getString("CarbideSBSv2ConfigTab.VariantLabel")); //$NON-NLS-1$
		variantLabel.setToolTipText(Messages.getString("CarbideSBSv2ConfigTab.VariantLabel_ToolTip")); //$NON-NLS-1$

		variantEdit = new Text(this, SWT.BORDER);
		variantEdit.setToolTipText(Messages.getString("CarbideSBSv2ConfigTab.VariantLabel_ToolTip")); //$NON-NLS-1$
		variantEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
	}
	
	public void initData(ICarbideBuildConfiguration buildConfig) {
		ISBSv2BuildConfigInfo sbsv2ConfigInfo = ((CarbideBuildConfiguration)buildConfig).getSBSv2ConfigInfo();
		if (sbsv2ConfigInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_VARIANT) != null){
			variantEdit.setText(sbsv2ConfigInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_VARIANT));
		}
		
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
