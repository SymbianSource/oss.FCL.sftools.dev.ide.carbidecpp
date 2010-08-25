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

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.ISymbianSDKModifier;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

class TestSymbianSDK implements ISymbianSDK, ISymbianSDKModifier {
	
	private String framework;
	private Version sdkVersion;
	private String family;

	public TestSymbianSDK(String frameWork, String sdkVersionString) {
		this.framework = frameWork;
		setSDKVersion(Version.parseVersion(sdkVersionString));
		setFamily(framework);
	}

	public void addSupportedFeature(Object feature) {
	}

	public ISDKBuildInfo getBuildInfo(String builderId) {
		return null;
	}

	public String getEPOCROOT() {
		return "C:\\";
	}

	public String getFamily() {
		return family;
	}

	public IPath getIncludePath() {
		return null;
	}

	public String getName() {
		return null;
	}

	public Version getOSVersion() {
		return null;
	}

	public File getPrefixFile(String builderId) {
		return null;
	}

	public IPath getReleaseRoot() {
		return null;
	}

	public Version getSDKVersion() {
		return sdkVersion;
	}

	public Set getSupportedFeatures() {
		return null;
	}

	public List<String> getSupportedTargetTypes() {
		return null;
	}

	public IPath getToolsPath() {
		return null;
	}

	public String getUniqueId() {
		return null;
	}

	public List<String> getVariantCFGMacros() {
		return null;
	}

	public String getVendor() {
		return null;
	}

	public void setEPOCROOT(String epocRoot) {
	}

	public boolean isEnabled() {
		return false;
	}

	public void setBuildInfo(ISDKBuildInfo buildInfo, String builderId) {
	}

	public void setEnabled(boolean enable) {
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public void setName(String name) {
	}

	public void setOSVersion(Version osVer) {
	}

	public void setPrefixFile(IPath prefixFile, String builderId) {
	}

	public void setSDKVersion(Version sdkVers) {
		sdkVersion = sdkVers;
	}

	public void setUniqueId(String id) {
	}

	public void scanSDK() {
	}

}