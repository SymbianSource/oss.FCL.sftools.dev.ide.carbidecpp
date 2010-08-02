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
package com.nokia.carbide.cpp.internal.qt.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;


public class QtImporterBuildTargetsPage extends QtBuildTargetsPage {

	private static final String RECENTLY_SELECTED_BUILD_CONFIGS_STORE = "QtImporterBuildTargetsPage.RECENTLY_SELECTED_BUILD_CONFIGS_STORE"; //$NON-NLS-1$

    private QtProFileImportWizard theWizard;

	
	public QtImporterBuildTargetsPage(QtProFileImportWizard wizard) {
		super();
		theWizard = wizard;
	}

	protected boolean validatePage() {
		boolean valid = super.validatePage();
		if (valid) {
			checkPathWithSDKs(theWizard.getProFile());
		}
		return valid;
	}
	
	public void saveDialogSettings() {
		// If the SDK of the build configuration is a variant or
		// has BSFs enabled then add the configuration to the configuration store
		IDialogSettings settings = getDialogSettings();
		String savedConfigs[] = getPreviouslySelectedConfigs();  // get the current list of configs saved
		boolean settingsNeedUpdate = false; // only write data when configs are selected and BSFs are enabled and/or VARIANTVIRTUAL(s) are present
        if (settings != null) {
			final List<ISymbianBuildContext> selectedConfigs = getSelectedBuildConfigs();
			List<ISymbianBuildContext> selectedConfigsToSave = new ArrayList<ISymbianBuildContext>();
			ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
			for (ISymbianBuildContext currContext : selectedConfigs){
				ISymbianSDK sdk = currContext.getSDK();
				IBSFCatalog bsfCatalog = null;
				if (currContext instanceof ISBSv1BuildContext) {
					// SBSv1 only
					ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
					bsfCatalog = sbsv1BuildInfo.getBSFCatalog();
				} 
				if (bsfCatalog != null && bsfCatalog.getVirtualVariantPlatforms().length > 0 || sdkMgr.getBSFScannerEnabled()){
					// this setting needs to be persisted.
					settingsNeedUpdate = true;
					selectedConfigsToSave.add(currContext);
				}
			}
			if (settingsNeedUpdate) {
				// Iterate through all the persisted configs and don't add those that have the same
				// SDK ID as the ones that are currently selected, we'll replace those with the currently selected ones
				List<String> configsToSave = new ArrayList<String>();  // the config list we'll actually persist to settings
				for (String currPersitedConfig : savedConfigs){
					boolean addConfig = true;
					for (ISymbianBuildContext currContext : selectedConfigs){
						if (currPersitedConfig.contains(currContext.getSDK().getUniqueId())){
							// don't add this one, we'll add these later
							addConfig = false;
							break;
						}
					}
					if (addConfig){
						configsToSave.add(currPersitedConfig);
					}
					
				}
				
				for (ISymbianBuildContext currContext : selectedConfigs){
					configsToSave.add(currContext.getDisplayString());  // add the selected configs now
				}
				
				settings.put(RECENTLY_SELECTED_BUILD_CONFIGS_STORE, configsToSave.toArray(new String[configsToSave.size()]));
			}
        }
		
	}
	
	/**
	 * Get the list of configurations that have previous been selected for SDKs
	 * This only includes SDKs when BSFs are enabled and/or VIRTUALVARIANT configuration(s) are present
	 * Regular public SDKs typically won't save this info.
	 * @return A String array of configuration display names. An empty list of none have been saved before.
	 */
	public String [] getPreviouslySelectedConfigs(){
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String configSettings[] = settings.getArray(RECENTLY_SELECTED_BUILD_CONFIGS_STORE);
			if (configSettings != null){
				return configSettings;
			}
		}
		
		// default case, just empty array
		return new String[0]; 
	}

}
