package com.nokia.carbide.cpp.sdk.core;

import java.util.List;
import java.util.Map;

public interface ISBSv2ConfigQueryData {
	public String getBuildPrefix();
	public Map<String, String> getBuildMacros();
	public String getConfigurationErrorMessage();
	public Map<String, String> getMetaDataMacros();
	public List<String> getMetaDataIncludes();
	public String getMetaDataVariantHRH();
	public String getOutputPathString();
}
