package com.nokia.carbide.cdt.builder.test.sandbox;

import java.util.ArrayList;
import java.util.List;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SBSv2ConfigData implements ISBSv2ConfigData {

	String buildAlias;
	String meaning;
	/** A configuration that was discovered without using a valid EPOCROOT during an sbs query */
	private boolean isBaseConfig;
	List<ISymbianSDK> supportedSDKs = new ArrayList<ISymbianSDK>();
	
	public SBSv2ConfigData(String buildAlias, String meaning, ISymbianSDK sdk){
		this.buildAlias = buildAlias;
		this.meaning = meaning;
		if (sdk != null){
			supportedSDKs.add(sdk);
		} else {
			this.isBaseConfig = true; 
		}
	}

	@Override
	public String getBuildAlias() {
		return buildAlias;
	}

	@Override
	public String getMeaning() {
		return meaning;
	}


	@Override
	public String getReleaseDirectory() {
		// TODO Here's this may need to be dynamically determined...
		 //Maybe need ISymbianSDK param?
		return null;
	}

	@Override
	public ISBSv2ConfigPreprocessorInfo getBuildData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTraditionalTarget() {
		// TODO NOT SURE IF THIS WILL BE NEEDED
		return null;
	}

	@Override
	public String getTraditionalPlatform() {
		// TODO NOT SURE IF THIS WILL BE NEEDED
		return null;
	}

	@Override
	public List<ISymbianSDK> getSupportedSDKs() {
		return supportedSDKs;
	}

	@Override
	public void addSupportedSDK(ISymbianSDK sdk) {
		supportedSDKs.add(sdk);
	}

	@Override
	public boolean isBaseConfig() {
		return isBaseConfig;
	}
	
	public String toString(){
		return "Alias = " + buildAlias + " : Meaning = " + meaning;
	}

}
