package com.nokia.carbide.cdt.builder.test.sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SBSv2QueryData implements ISBSv2QueryData {

	
	/** alias ==> build config data Map*/
	HashMap<String, ISBSv2ConfigData> sbsBuildConfigMap = new HashMap<String, ISBSv2ConfigData>();
	HashMap<ISymbianSDK, List<String>> sdkProductVariantList = new HashMap<ISymbianSDK, List<String>>();
	
	@Override
	public void addConfigurationData(ISBSv2ConfigData configData) {
		
		String buildAlias = configData.getBuildAlias();
		if (null == sbsBuildConfigMap.get(buildAlias)){
			sbsBuildConfigMap.put(buildAlias, configData);
		} else  {
			// build alias already exists, just add it as a supported SDK
			ISBSv2ConfigData updateConfig = sbsBuildConfigMap.get(buildAlias);
			for (ISymbianSDK sdk : configData.getSupportedSDKs()){
				// Add to the list of already supported SDKs, if any
				updateConfig.addSupportedSDK(sdk);
			}
			sbsBuildConfigMap.put(buildAlias, updateConfig);
		}
	}

	@Override
	public List<ISBSv2ConfigData> getBaseSBSConfigurations() {
		List<ISBSv2ConfigData> baseSBSConfigs = new ArrayList<ISBSv2ConfigData>();
		
		// get all the base configurations...
		for (String key : sbsBuildConfigMap.keySet()) {
			ISBSv2ConfigData configData = sbsBuildConfigMap.get(key);
			if (configData.isBaseConfig()) {
				baseSBSConfigs.add(configData);
			}
		}
		
		return baseSBSConfigs;
	}
	
	@Override
	public List<ISBSv2ConfigData> getSDKSpecificConfigData(ISymbianSDK sdk) {
		List<ISBSv2ConfigData> configsForSDK = new ArrayList<ISBSv2ConfigData>();
		
		// get all the base configurations...
		for (String key : sbsBuildConfigMap.keySet()) {
			ISBSv2ConfigData configData = sbsBuildConfigMap.get(key);
			if (configData.isBaseConfig()) {
				continue;
			}
			// Not a Raptor-defined config, see if the SDK defined it
			if (sdk != null){
				if (configData.getSupportedSDKs().contains(sdk)){
					configsForSDK.add(configData);
				}
			}
		}
		
		return configsForSDK;
	}
	
	@Override
	public List<ISBSv2ConfigData> getAllConfigurationsForSDK(ISymbianSDK sdk) {
		List<ISBSv2ConfigData> allConfigs = new ArrayList<ISBSv2ConfigData>();
		allConfigs.addAll(getBaseSBSConfigurations());
		allConfigs.addAll(getSDKSpecificConfigData(sdk));
		return allConfigs;
	}

	@Override
	public List<String> getProductsForSDK(ISymbianSDK sdk) {
		return sdkProductVariantList.get(sdk);
	}

	@Override
	public void addProductListForSDK(ISymbianSDK sdk, List<String> products) {
		if (null == sdkProductVariantList.get(sdk) || 
			sdkProductVariantList.size() == 0){
			
			sdkProductVariantList.put(sdk, products);
		}

	}

	@Override
	public ISBSv2ConfigData getSBSConfigByAlias(String alias) {
		return sbsBuildConfigMap.get(alias);
	}

	@Override
	public ISBSv2ConfigData getSBSConfigByMeaning(String string) {
		ISBSv2ConfigData configData = null;
		for (String key : sbsBuildConfigMap.keySet()){
			if (sbsBuildConfigMap.get(key).getMeaning().equals(string)){
				return sbsBuildConfigMap.get(key);
			}
		}
		
		return configData;
	}

}
