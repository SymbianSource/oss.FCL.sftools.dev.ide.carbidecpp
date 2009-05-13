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
import java.net.URL;
import java.util.*;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.sdk.core.*;

class TestSymbianSDK implements ISymbianSDK {
	
	
	public List<String> getVariantCFGMacros() {
		return null;
	}
	public List<String> getVendorSDKMacros() {
		return null;
	}
	public boolean isEKA1() {
		return false;
	}
	public boolean isEKA2() {
		return false;
	}
	public boolean isS60() {
		return false;
	}
	public void setEPOCROOT(String epocRoot) {
		
	}
	public void setName(String name) {
		
	}
	public void setUniqueID(String id) {
		
	}
	private String framework;
	private Version sdkVersion;
	public TestSymbianSDK(String frameWork, String sdkVersionString) {
		this.framework = frameWork;
		this.sdkVersion = Version.parseVersion(sdkVersionString);
	}
	public List<String> getAllMacros() {
		return null;
	}
	public List<String> getAvailablePlatforms() {
		return null;
	}
	public Date getCreationDate() {
		return null;
	}
	public String getEPOCROOT() {
		return "C:\\";
	}
	public boolean isEnabled() {
		return false;
	}
	public String getFamily() {
		return framework;
	}
	public List<ISymbianBuildContext> getFilteredBuildConfigurations() {
		return null;
	}
	public IPath getIncludePath() {
		return null;
	}
	public File getLicenseFile() {
		return null;
	}
	public String getName() {
		return null;
	}
	
	public Version getOSVersion() {
		return null;
	}
	public List<String> getPlatformMacros(String platform) {
		return null;
	}
	public File getPrefixFile() {
		return null;
	}
	public File getPublisherLogo() {
		return null;
	}
	public URL getPublisherURL() {
		return null;
	}
	public IPath getReleaseRoot() {
		return null;
	}
	public String getSDKDescription() {
		return null;
	}
	public String getSDKOSBranch() {
		return null;
	}
	public Version getSDKVersion() {
		return sdkVersion;
	}
	public IPath getToolsPath() {
		return null;
	}
	public List<ISymbianBuildContext> getUnfilteredBuildConfigurations() {
		return null;
	}
	public String getUniqueId() {
		return null;
	}
	public String getVendor() {
		return null;
	}
	
	public boolean isDefaultSDK() {
		return false;
	}
	public boolean isValid() {
		return false;
	}
	public void setEnabled(boolean enable) {
	}
	public List<String> validationErrors() {
		return null;
	}
	public void setCreateData(Date createDate) {
	}
	public void setIncludePath(IPath incPath) {
	}
	public void setIsDefaultSDK(boolean isDefault) {
	}
	public void setLicenseFile(File licenseFile) {
	}
	public void setOSSDKBranch(String branch) {
	}
	public void setOSVersion(Version osVer) {
	}
	public void setPrefixFile(IPath prefixFile) {
	}
	public void setPublisherLogo(File logoFile) {
	}
	public void setPublisherName(String pubName) {
	}
	public void setPublisherURL(URL pubURL) {
	}
	public void setReleaseRoot(IPath releaseRootPath) {
	}
	public void setSDKDescription(String descr) {
	}
	public void setSDKVersion(Version sdkVers) {
	}
	public void setSourcePath(IPath srcPath) {
	}
	public void setToolsPath(IPath toolPath) {
	}
	public void setAvailablePlatforms(List<String> platList) {
	}
	public List<String> getSupportedTargetTypes() {
		return null;
	}
	public void setOSMacros(List<String> osMacros) {
	}
	public void setPlatformMacros(String platform, List<String> platMacros) {
	}
	public List<String> getProjectVariantHRHMacros() {
		return null;
	}
	public List<String> getTargetTypeMacros(String targettype) {
		return null;
	}
	public boolean getRequiresRestart() {
		return false;
	}
	
	public String getPublisherName() {
		return null;
	}

	public void setCreateDate(Date createDate) {
	}
	public void setSupportsWINSCW_UREL(boolean isSupported) {
		
	}
	public boolean supportsWINSCW_UREL() {
		return false;
	}
	public void scanSDK() {
		
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISymbianSDK#getBSFCatalog()
	 */
	public IBSFCatalog getBSFCatalog() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISymbianSDK#getProjectVariantHRHDefines()
	 */
	public List<IDefine> getProjectVariantHRHDefines() {
		return Collections.EMPTY_LIST;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISymbianSDK#getSBVCatalog()
	 */
	public ISBVCatalog getSBVCatalog() {
		return null;
	}
	
	
}