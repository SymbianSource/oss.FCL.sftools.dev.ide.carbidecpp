/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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



package com.nokia.carbide.templatewizard.symbian.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

class TestSymbianSDK implements ISymbianSDK {
	
	private String framework;
	private TestBuildInfo testBuildInfo;
	private Map<String, ISDKBuildInfo> buildInfoMap = new HashMap<String, ISDKBuildInfo>();
	private Set sdkFeatures = new HashSet();

	public TestSymbianSDK(String frameWork, String sdkVersionString) {
		this.framework = frameWork;
		testBuildInfo = new TestBuildInfo();
		testBuildInfo.setSDKVersion(this, Version.parseVersion(sdkVersionString));
		testBuildInfo.setFamily(framework);
		buildInfoMap.put(ISymbianBuilderID.SBSV1_BUILDER, testBuildInfo);
	}

	public ISDKBuildInfo getBuildInfo(String builderId) {
		ISDKBuildInfo buildInfo = buildInfoMap.get(builderId);
		return buildInfo;
	}

	public Set getSupportedFeatures() {
		return sdkFeatures;
	}

	public boolean isValid() {
		return false;
	}

	public List<String> validationErrors() {
		return null;
	}

	public boolean isEnabled() {
		return false;
	}

	public List<String> getVariantCFGMacros() {
		return null;
	}

	public String getUniqueId() {
		return null;
	}

	public String getEPOCROOT() {
		return "C:\\";
	}

	public Version getOSVersion() {
		return null;
	}

	public List<String> getSupportedTargetTypes() {
		return null;
	}

	public void setEPOCROOT(String epocRoot) {
	}

	public void scanSDK() {
	}

}