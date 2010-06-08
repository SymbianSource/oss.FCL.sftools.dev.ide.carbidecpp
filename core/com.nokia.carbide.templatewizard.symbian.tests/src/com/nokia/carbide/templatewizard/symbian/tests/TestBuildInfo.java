package com.nokia.carbide.templatewizard.symbian.tests;

import com.nokia.carbide.cpp.internal.sdk.core.model.SBSv1BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class TestBuildInfo extends SBSv1BuildInfo {

	private String family;
	
	public String getFamily(ISymbianSDK sdk) {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}
}
