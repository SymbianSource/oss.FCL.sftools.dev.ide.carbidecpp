package com.nokia.carbide.cdt.builder.test.sandbox;

import java.util.List;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/** Data that describes the meaning of a single build configuration */
public interface ISBSv2ConfigData {

	String getBuildAlias();
	
	String getMeaning();
	
	String getReleaseDirectory(ISymbianSDK sdk);
	
	ISBSv2ConfigPreprocessorInfo getBuildData(ISymbianSDK sdk);
	
	String getTraditionalTarget(ISymbianSDK sdk);
	
	String getTraditionalPlatform(ISymbianSDK sdk);
	
	List<ISymbianSDK> getSupportedSDKs();
	
	void addSupportedSDK(ISymbianSDK sdk);
	
	/**
	 * Is this configuration supported by the base Raptor install?
	 * @return 
	 */
	boolean isBaseConfig();
}
