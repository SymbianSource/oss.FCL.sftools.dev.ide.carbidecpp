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
package com.nokia.carbide.cpp.internal.api.sdk.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.sdk.ui.Messages;
import com.nokia.carbide.cpp.internal.sdk.ui.SDKUIPreferenceConstants;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.ui.SDKUIPlugin;

/**
 * @since 1.4
 */
public class SBSv1PlatformFilterComposite extends Composite {

	private Button platEKA2_WINSCW;
	private Button platEKA2_GCCE;
	private Button platEKA2_ARMV5;
	private Button platEKA2_ARMV5_ABIV2;
	private Button platEKA2_ARMV5_ABIV1;
	
	private Button platEKA1_WINSCW;
	private Button platEKA1_ARMI;
	private Button platEKA1_ARM4;
	private Button platEKA1_THUMB;
	
	private Button enableBSFScanner;

	
	SBSv1PlatformFilterComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	public void createControls() {
		GridLayout gridLayout = new GridLayout();
		setLayout(gridLayout);
		
		Label selectWhichPlatformsLabel = new Label(this, SWT.NONE);
		selectWhichPlatformsLabel.setLayoutData(new GridData());
		selectWhichPlatformsLabel.setText(Messages.getString("BuildPlatformFilterPage.Select_Platforms_Help")); //$NON-NLS-1$

		Group eka2PlatformsGroup = new Group(this, SWT.NONE);
		eka2PlatformsGroup.setToolTipText(Messages.getString("BuildPlatformFilterPage.Specify_Platforms_Help")); //$NON-NLS-1$
		eka2PlatformsGroup.setText(Messages.getString("BuildPlatformFilterPage.EKA2_Platforms")); //$NON-NLS-1$
		eka2PlatformsGroup.setLayoutData(new GridData(254, SWT.DEFAULT));
		eka2PlatformsGroup.setLayout(new GridLayout());
		
		platEKA2_WINSCW = new Button(eka2PlatformsGroup, SWT.CHECK);
		platEKA2_WINSCW.setLayoutData(new GridData(220, SWT.DEFAULT));
		platEKA2_WINSCW.setText("Emulation (WINSCW)"); //$NON-NLS-1$
		platEKA2_WINSCW.setToolTipText(""); //$NON-NLS-1$

		platEKA2_GCCE = new Button(eka2PlatformsGroup, SWT.CHECK);
		platEKA2_GCCE.setText("GCCE"); //$NON-NLS-1$

		platEKA2_ARMV5 = new Button(eka2PlatformsGroup, SWT.CHECK);
		platEKA2_ARMV5.setText("ARMV5"); //$NON-NLS-1$

		platEKA2_ARMV5_ABIV2 = new Button(eka2PlatformsGroup, SWT.CHECK);
		platEKA2_ARMV5_ABIV2.setText("ARMV5_ABIV2 (OS 9.1-9.3)"); //$NON-NLS-1$
		
		platEKA2_ARMV5_ABIV1 = new Button(eka2PlatformsGroup, SWT.CHECK);
		platEKA2_ARMV5_ABIV1.setText("ARMV5_ABIV1 (OS 9.4+)"); //$NON-NLS-1$

		Group eka1PlatformsGroup = new Group(this, SWT.NONE);
		eka1PlatformsGroup.setToolTipText(Messages.getString("BuildPlatformFilterPage.Specify_Platforms_Help2")); //$NON-NLS-1$
		eka1PlatformsGroup.setLayoutData(new GridData(258, SWT.DEFAULT));
		eka1PlatformsGroup.setText(Messages.getString("BuildPlatformFilterPage.EKA1_Platforms_Label")); //$NON-NLS-1$
		eka1PlatformsGroup.setLayout(new GridLayout());

		platEKA1_WINSCW = new Button(eka1PlatformsGroup, SWT.CHECK);
		platEKA1_WINSCW.setText("Emulation (WINSCW)"); //$NON-NLS-1$

		platEKA1_ARMI = new Button(eka1PlatformsGroup, SWT.CHECK);
		platEKA1_ARMI.setText("ARMI"); //$NON-NLS-1$

		platEKA1_ARM4 = new Button(eka1PlatformsGroup, SWT.CHECK);
		platEKA1_ARM4.setText("ARM4"); //$NON-NLS-1$

		platEKA1_THUMB = new Button(eka1PlatformsGroup, SWT.CHECK);
		platEKA1_THUMB.setText("THUMB"); //$NON-NLS-1$

		enableBSFScanner = new Button(this, SWT.CHECK);
		enableBSFScanner.setToolTipText(Messages.getString("BuildPlatformFilterPage.BSF_Help")); //$NON-NLS-1$
		enableBSFScanner.setText(Messages.getString("BuildPlatformFilterPage.BSF_Label")); //$NON-NLS-1$
				
		setCheckBoxSettings();
	}

	private void setCheckBoxSettings() {
		List<BuildPlat> bldPlatList = new ArrayList<BuildPlat>();
		bldPlatList = getPlatFilterPrefsStore();
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		enableBSFScanner.setSelection(sdkMgr.getBSFScannerEnabled());
		
		for (BuildPlat currBldPlat : bldPlatList) {
			if (currBldPlat.getPlatName().equals("WINSCW")){ //$NON-NLS-1$
				if (currBldPlat.getOsIdentifier().equals(BuildPlat.EKA1_IDENTIFIER)){
					platEKA1_WINSCW.setSelection(currBldPlat.isEnabled());
				} else {
					platEKA2_WINSCW.setSelection(currBldPlat.isEnabled());
				}
			} else if (currBldPlat.getPlatName().equals("THUMB")){ //$NON-NLS-1$
				platEKA1_THUMB.setSelection(currBldPlat.isEnabled());
			} else if (currBldPlat.getPlatName().equals("ARMI")){ //$NON-NLS-1$
				platEKA1_ARMI.setSelection(currBldPlat.isEnabled());
			} else if (currBldPlat.getPlatName().equals("ARM4")){ //$NON-NLS-1$
				platEKA1_ARM4.setSelection(currBldPlat.isEnabled());
			} else if (currBldPlat.getPlatName().equals("GCCE")){ //$NON-NLS-1$
				platEKA2_GCCE.setSelection(currBldPlat.isEnabled());
			} else if (currBldPlat.getPlatName().equals("ARMV5")){ //$NON-NLS-1$
				platEKA2_ARMV5.setSelection(currBldPlat.isEnabled());
			} else if (currBldPlat.getPlatName().equals("ARMV5_ABIV2")){ //$NON-NLS-1$
				platEKA2_ARMV5_ABIV2.setSelection(currBldPlat.isEnabled());
			} else if (currBldPlat.getPlatName().equals("ARMV5_ABIV1")){ //$NON-NLS-1$
				platEKA2_ARMV5_ABIV1.setSelection(currBldPlat.isEnabled());
			}
		}
	}

	public static List<BuildPlat> getPlatFilterPrefsStore() {
		
		List<BuildPlat> buildPlatList = new ArrayList<BuildPlat>();
		
		IPreferenceStore prefsStore = SDKUIPlugin.getDefault().getPreferenceStore();
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		sdkMgr.enableBSFScanner(prefsStore.getBoolean(SDKUIPreferenceConstants.ENABLE_BSF_SCANNER));
		
		BuildPlat bldPlat = new BuildPlat("WINSCW", BuildPlat.EKA1_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA1_WINSCW));
		buildPlatList.add(bldPlat);
		buildPlatList.add(new BuildPlat("ARM4", BuildPlat.EKA1_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA1_ARM4)));
		buildPlatList.add(new BuildPlat("ARMI", BuildPlat.EKA1_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA1_ARMI)));
		buildPlatList.add(new BuildPlat("THUMB", BuildPlat.EKA1_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA1_THUMB)));
		
		buildPlatList.add(new BuildPlat("WINSCW", BuildPlat.EKA2_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA2_WINSCW)));
		buildPlatList.add(new BuildPlat("ARMV5",  BuildPlat.EKA2_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5)));
		buildPlatList.add(new BuildPlat("ARMV5_ABIV2", BuildPlat.EKA2_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV2)));
		buildPlatList.add(new BuildPlat("ARMV5_ABIV1", BuildPlat.EKA2_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV1)));

		buildPlatList.add(new BuildPlat("GCCE", BuildPlat.EKA2_IDENTIFIER, prefsStore.getBoolean(SDKUIPreferenceConstants.PLAT_EKA2_GCCE)));
		
		sdkMgr.setPlatformList(buildPlatList);
		
		return buildPlatList;
	}

	public void performOk() {
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.ENABLE_BSF_SCANNER, enableBSFScanner.getSelection());
		
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA2_WINSCW, platEKA2_WINSCW.getSelection());
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA2_GCCE, platEKA2_GCCE.getSelection());
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5, platEKA2_ARMV5.getSelection());
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV2, platEKA2_ARMV5_ABIV2.getSelection());
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV1, platEKA2_ARMV5_ABIV1.getSelection());
		
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA1_WINSCW, platEKA1_WINSCW.getSelection());
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA1_THUMB, platEKA1_THUMB.getSelection());
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA1_ARMI, platEKA1_ARMI.getSelection());
		writePlatFilterOptionSetting(SDKUIPreferenceConstants.PLAT_EKA1_ARM4, platEKA1_ARM4.getSelection());
		
		// update data structures that are passed back to the sdk.core
		getPlatFilterPrefsStore();
	}
	
	private void writePlatFilterOptionSetting(String platFilterIdName, boolean value){
		IPreferenceStore prefsStore = SDKUIPlugin.getDefault().getPreferenceStore();
		
		if (platFilterIdName.equals(SDKUIPreferenceConstants.ENABLE_BSF_SCANNER)){
			prefsStore.setValue(SDKUIPreferenceConstants.ENABLE_BSF_SCANNER, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA1_ARM4)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA1_ARM4, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA1_ARMI)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA1_ARMI, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA1_THUMB)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA1_THUMB, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA1_WINSCW)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA1_WINSCW, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV2)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV2, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV1)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA2_ARMV5_ABIV1, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA2_GCCE)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA2_GCCE, value);
		} else if (platFilterIdName.equals(SDKUIPreferenceConstants.PLAT_EKA2_WINSCW)){
			prefsStore.setValue(SDKUIPreferenceConstants.PLAT_EKA2_WINSCW, value);
		}
		
	}
}
