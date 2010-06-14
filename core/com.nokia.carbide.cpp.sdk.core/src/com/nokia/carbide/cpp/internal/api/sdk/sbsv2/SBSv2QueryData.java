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
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cpp.internal.api.sdk.sbsv2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SBSv2QueryData implements ISBSv2QueryData {

	/** alias to ISBSv2ConfigData - These are the standard build configs defined by Raptor, and are treated as virtual by this API */
	HashMap<String, ISBSv2ConfigData> baseSBSConfigs = new HashMap<String, ISBSv2ConfigData>();
	/** SDK ==> ISBSv2ConfigData : Contains all the SBS configs particular to a single SDK */
	HashMap<ISymbianSDK, List<ISBSv2ConfigData>> sbsSDKBuildConfigMap = new HashMap<ISymbianSDK, List<ISBSv2ConfigData>>();
	HashMap<ISymbianSDK, List<String>> sdkProductVariantList = new HashMap<ISymbianSDK, List<String>>();
	
	public void addConfigurationData(ISymbianSDK sdk, ISBSv2ConfigData configData) {
		
		if (sdk == null){
			baseSBSConfigs.put(configData.getBuildAlias(), configData);
		}
		
		List<ISBSv2ConfigData> configDataForSDK = sbsSDKBuildConfigMap.get(sdk);
		
		if (configDataForSDK == null){
			// This SDK does not exist, create the first entry
			configDataForSDK = new ArrayList<ISBSv2ConfigData>();
			configDataForSDK.add(configData);
			sbsSDKBuildConfigMap.put(sdk, configDataForSDK);
			return;
		}
		
		configDataForSDK.add(configData);
	}

	public HashMap<String, ISBSv2ConfigData> getBaseSBSConfigurations() {
		return baseSBSConfigs;
	}
	
	public List<ISBSv2ConfigData> getSDKSpecificConfigData(ISymbianSDK sdk) {
		List<ISBSv2ConfigData> sdkDefinedConfigs =  new ArrayList<ISBSv2ConfigData>();
		
		for (ISBSv2ConfigData oneConfig : sbsSDKBuildConfigMap.get(sdk)){
			// check if the alias is already in the base list, if not add it
			boolean addConfigForReturn = true;
			for (String alias : baseSBSConfigs.keySet()){
				ISBSv2ConfigData baseConfig = baseSBSConfigs.get(alias);
				if (oneConfig.getBuildAlias().equals(baseConfig.getBuildAlias())){
					addConfigForReturn = false;
					break;
				}
			}
			if (addConfigForReturn){
				sdkDefinedConfigs.add(oneConfig);
			}
		}
			
		return sdkDefinedConfigs;
	}
	
	public List<ISBSv2ConfigData> getAllConfigurationsForSDK(ISymbianSDK sdk) {
		return sbsSDKBuildConfigMap.get(sdk);
	}

	public List<String> getProductsForSDK(ISymbianSDK sdk) {
		return sdkProductVariantList.get(sdk);
	}

	public void addProductListForSDK(ISymbianSDK sdk, List<String> products) {
		if (null == sdkProductVariantList.get(sdk) || 
			sdkProductVariantList.size() == 0){
			
			sdkProductVariantList.put(sdk, products);
		}
	}

	public ISBSv2ConfigData getSBSConfigByAlias(ISymbianSDK sdk, String alias) {
		
		if (sdk == null){
			return baseSBSConfigs.get(alias);
		} else {
			List<ISBSv2ConfigData> configListForSDK =  sbsSDKBuildConfigMap.get(sdk);
			
			for (ISBSv2ConfigData config : configListForSDK){
				if (config.getBuildAlias().equals(alias)){
					return config;
				}
			}
		}
		
		return null;
	}

	public ISBSv2ConfigData getSBSConfigByMeaning(ISymbianSDK sdk, String meaning) {
		if (sdk == null){
			for (String aliasKey : baseSBSConfigs.keySet()){
				ISBSv2ConfigData config = baseSBSConfigs.get(aliasKey);
				if (config.getMeaning().equals(meaning)){
					return config;
				}
			}
		} else {
			List<ISBSv2ConfigData> configListForSDK =  sbsSDKBuildConfigMap.get(sdk);
			
			for (ISBSv2ConfigData config : configListForSDK){
				if (config.getMeaning().equals(meaning)){
					return config;
				}
			}
		}
		
		return null;
	}

}
