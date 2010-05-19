package com.nokia.carbide.cdt.builder.test.sandbox;

import java.util.List;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public interface ISBSv2QueryData {

	void addConfigurationData(ISBSv2ConfigData configData);
	
	List<ISBSv2ConfigData> getAllConfigurationsForSDK(ISymbianSDK sdk);
	
	List<ISBSv2ConfigData> getSDKSpecificConfigData(ISymbianSDK sdk);
	
	List<ISBSv2ConfigData> getBaseSBSConfigurations();
	
	List<String> getProductsForSDK(ISymbianSDK sdk);
	
	void addProductListForSDK(ISymbianSDK sdk, List<String> products);

	ISBSv2ConfigData getSBSConfigByAlias(String aliase);

	ISBSv2ConfigData getSBSConfigByMeaning(String string);
}
