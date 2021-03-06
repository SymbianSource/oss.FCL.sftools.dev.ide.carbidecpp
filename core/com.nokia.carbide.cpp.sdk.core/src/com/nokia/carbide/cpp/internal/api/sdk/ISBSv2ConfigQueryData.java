package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Interface to interact directly with results for Raptor configuration query data (--query=config{&lt;config&gt;])
 * @since 3.0
 */
public interface ISBSv2ConfigQueryData extends Serializable {
	public String getAlias();
	public String getBuildPrefix();
	public Map<String, String> getBuildMacros();
	public String getConfigurationErrorMessage();
	public Map<String, String> getMetaDataMacros();
	public List<String> getMetaDataIncludes();
	public String getMetaDataVariantHRH();
	public String getOutputPathString();
	public List<String> getTargettypes();
}
