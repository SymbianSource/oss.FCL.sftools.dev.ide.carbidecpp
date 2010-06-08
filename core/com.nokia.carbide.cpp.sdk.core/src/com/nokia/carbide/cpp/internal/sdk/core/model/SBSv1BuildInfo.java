/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
*/

package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.IBSFPlatform;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVPlatform;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

/**
 * SBSv1 specific build information.
 *
 */
public class SBSv1BuildInfo implements ISBSv1BuildInfo {

	private Date createDate;
	private File licenseFile;
	private File prefixFile;
	private Version sdkVersion;
	private String sdkOSBranch;
	private String sdkDescription;
	private String publisherName;
	private URL publisherURL;
	private IBSFCatalog bsfCatalog;
	private ISBVCatalog sbvCatalog;
	private List<ISymbianBuildContext> binaryVariantContextList = new ArrayList<ISymbianBuildContext>(0);
	private List<ISymbianBuildContext> bsfContextList = new ArrayList<ISymbianBuildContext>(0);

	public List<ISymbianBuildContext> getFilteredBuildConfigurations(ISymbianSDK sdk) {
		// This is probably a bug, but the filtering only uses SBSv1 preferences if SBSv1 is enabled...
		List<ISymbianBuildContext> filteredContexts;
		if (SBSv2Utils.enableSBSv1Support()) {
			filteredContexts = getSBSv1FilteredBuildConfigurations(sdk);
		} else {
			// be optimistic in this case... SBSv3? ;)
			filteredContexts = getAllBuildConfigurations(sdk);
		}
		return filteredContexts;
	}

	@SuppressWarnings("rawtypes")
	public List<ISymbianBuildContext> getAllBuildConfigurations(ISymbianSDK sdk) {
		Set sdkFeatures = sdk.getSupportedFeatures();
		List<ISymbianBuildContext> buildTargets = new ArrayList<ISymbianBuildContext>();
		
		// note that this gets variant platforms but not regular BSF's
		List <String>buildPlats =  getAvailablePlatforms(sdk);
		
		if (buildPlats.size() == 0){
			return Collections.emptyList();
		}
		// TODO: Hard code build context hack
		buildTargets.add(new BuildContextSBSv1(sdk, ISymbianBuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.DEBUG_TARGET));
		
		if (sdkFeatures.contains(ISymbianSDKFeatures.IS_WINSCW_UREL_SUPPORTED)){
			// TODO: Hard code build context hack
			buildTargets.add(new BuildContextSBSv1(sdk, ISymbianBuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.RELEASE_TARGET));
		}
		
		for (String currPlat : buildPlats){
			if (currPlat.equals(ISymbianBuildContext.EMULATOR_PLATFORM) ) { 
				// emulation targets already determined (some SDKs don't get WISNCW UREL
				continue;
			}
			// TODO: Hard code build context hack
			buildTargets.add(new BuildContextSBSv1(sdk, currPlat, ISymbianBuildContext.DEBUG_TARGET));
			
			// everything gets release except for WINSCW
			// TODO: Hard code build context hack
			buildTargets.add(new BuildContextSBSv1(sdk, currPlat, ISymbianBuildContext.RELEASE_TARGET));
		}
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr.getBSFScannerEnabled()){
			buildTargets.addAll(getBSFPlatformContexts(sdk));
			buildTargets.addAll(getBinaryVariationPlatformContexts(sdk)); // Symbian Binary Variation (.var)
		}
		
		return buildTargets;
	}

	public List<String> getPlatformMacros(ISymbianSDK sdk, String platform) {
		if (sdk instanceof SymbianSDK) {
			return ((SymbianSDK)sdk).getPlatformMacros(platform);
		}
		return null;
	}

	public List<String> getVendorSDKMacros(ISymbianSDK sdk) {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		return sdkMgr.getSymbianMacroStore().getVendorMacros(getSDKVersion(sdk), getName(sdk));
	}

	public List<String> getAvailablePlatforms(ISymbianSDK sdk) {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		return sdkMgr.getSymbianMacroStore().getSupportedPlatforms(((SymbianSDK)sdk).getOSVersion(), getSDKOSBranch(sdk), getBSFCatalog(sdk));
	}

	public String getName(ISymbianSDK sdk) {
		if (sdk instanceof SymbianSDK) {
			DeviceType deviceEntry = ((SymbianSDK)sdk).getDeviceEntry();
			if (deviceEntry != null) {
				return deviceEntry.getName();
			}
		}
		return "";
	}

	public String getVendor(ISymbianSDK sdk) {
		String[] parts = getName(sdk).split("\\.");
		if (parts.length == 3)
			return parts[1];
		
		return "";
	}

	public String getFamily(ISymbianSDK sdk) {
		String[] parts = getName(sdk).split("\\.");
		if (parts.length == 3){
			if (getSDKVersion(sdk).getMajor() == 5 && getName(sdk).equalsIgnoreCase(NOKIA_SF_SDK_NAME)){
				// A vendor of "symbian" and SDK major version 5 is the same as prior naming for "com.nokia.s60" & 5th Edition.
				// Return "s60" so that project template generation continues to work as it's a S60 5th ed. SDK. 
				return ISBSv1BuildInfo.S60_FAMILY_ID;
			} else {
				return parts[2];
			}
		}
		
		return "";
	}

	public Version getSDKVersion(ISymbianSDK sdk) {
		if (sdkVersion == null){
			return new Version("0.0");
		}
		return sdkVersion;
	}

	public File getPrefixFile(ISymbianSDK sdk) {
		return prefixFile;
	}

	public IPath getToolsPath(ISymbianSDK sdk) {
		String epocRoot = sdk.getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32ToolsPath = new Path(epocRoot).append("epoc32/tools");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32ToolsPath = new Path(epoc32ToolsPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32ToolsPath;
		}
		return null;
	}

	public IPath getReleaseRoot(ISymbianSDK sdk) {
		String epocRoot = sdk.getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32RelPath = new Path(epocRoot).append("epoc32/release");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32RelPath = new Path(epoc32RelPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32RelPath;
		}
		return null;
	}

	public IPath getIncludePath(ISymbianSDK sdk) {
		String epocRoot = sdk.getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32IncPath = new Path(epocRoot).append("epoc32/include");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32IncPath = new Path(epoc32IncPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32IncPath;
		}
		return null;
	}

	public String getSDKDescription(ISymbianSDK sdk) {
		if (sdkDescription == null){
			return "";
		}
		return sdkDescription;
	}

	public Date getCreationDate(ISymbianSDK sdk) {
		return createDate;
	}

	public File getLicenseFile(ISymbianSDK sdk) {
		return licenseFile;
	}

	public String getSDKOSBranch(ISymbianSDK sdk) {
		if (sdkOSBranch == null){
			return "";
		}
		return sdkOSBranch;
	}

	public URL getPublisherURL(ISymbianSDK sdk) {
		return publisherURL;
	}

	public String getPublisherName(ISymbianSDK sdk) {
		return publisherName;
	}

	public List<String> getTargetTypeMacros(ISymbianSDK sdk, String targettype) {
		// this is based on \epoc32\tools\trgtype.pm which changes from
		// OS version to OS version, but largely remains constant with
		// regards to the basic type.
		List<String> macros = new ArrayList<String>();
		
		// if it's not one of these then it's a DLL
		if (targettype.compareToIgnoreCase("EPOCEXE") == 0) {
			macros.add("__EXEDLL__");
		} else if (targettype.compareToIgnoreCase("EXEDLL") == 0) {
			macros.add("__EXEDLL__");
		} else if (targettype.compareToIgnoreCase("EXE") == 0) {
			macros.add("__EXE__");
		} else if (targettype.compareToIgnoreCase("EXEXP") == 0) {
			macros.add("__EXE__");
		} else if (targettype.compareToIgnoreCase("IMPLIB") == 0) {
			macros.add("__IMPLIB__");
		} else if (targettype.compareToIgnoreCase("KLIB") == 0) {
			macros.add("__LIB__");
		} else if (targettype.compareToIgnoreCase("LIB") == 0) {
			macros.add("__LIB__");
		} else {
			macros.add("__DLL__");
		}
		return macros;
	}

	public IBSFCatalog getBSFCatalog(ISymbianSDK sdk) {
		synchronized (sdk) {
			if (bsfCatalog == null) {
				bsfCatalog = BSFCatalogFactory.createCatalog(sdk);
			}
		}
		return bsfCatalog;
	}

	public ISBVCatalog getSBVCatalog(ISymbianSDK sdk) {
		synchronized (sdk) {
			if (sbvCatalog == null) {
				sbvCatalog = SBVCatalogFactory.createCatalog(sdk);
			}
		}
		return sbvCatalog;
	}

	public boolean isDefaultSDK(ISymbianSDK sdk) {
		if (sdk instanceof SymbianSDK) {
			DeviceType deviceEntry = ((SymbianSDK)sdk).getDeviceEntry();
			if (deviceEntry == null) {
				return false;
			}
			if (deviceEntry.getDefault().equals(DefaultType.YES_LITERAL)){
				return true;
			}
		}
		return false;
	}

	public boolean isS60(ISymbianSDK sdk) {
		return getFamily(sdk).equals(ISBSv1BuildInfo.S60_FAMILY_ID)
			|| getFamily(sdk).equals(ISBSv1BuildInfo.SERIES60_FAMILY_ID);
	}

	public boolean isPreviouslyScanned(ISymbianSDK sdk) {
		if (sdk instanceof SymbianSDK) {
			return ((SymbianSDK)sdk).isPreviouslyScanned();
		}
		return true;
	}

	public void setLicenseFile(ISymbianSDK sdk, File licenseFile) {
		 this.licenseFile = licenseFile;
	}

	public void setPrefixFile(ISymbianSDK sdk, IPath prefixFile) {
		this.prefixFile = new File(prefixFile.toOSString());
	}

	public void setSDKVersion(ISymbianSDK sdk, Version sdkVers) {
		sdkVersion = sdkVers;
	}

	public void setPublisherURL(ISymbianSDK sdk, URL pubURL) {
		publisherURL = pubURL;
	}

	public void setCreateDate(ISymbianSDK sdk, Date createDate) {
		this.createDate = createDate;
	}

	public void setOSSDKBranch(ISymbianSDK sdk, String branch) {
		sdkOSBranch = branch;
	}

	public void setSDKDescription(ISymbianSDK sdk, String descr) {
		sdkDescription = descr;
	}

	public void setPublisherName(ISymbianSDK sdk, String pubName) {
		publisherName = pubName;
	}

	public void setName(ISymbianSDK sdk, String name) {
		if (sdk instanceof SymbianSDK) {
			DeviceType deviceEntry = ((SymbianSDK)sdk).getDeviceEntry();
			if (deviceEntry != null) {
				deviceEntry.setName(name);
			}
		}
	}

	public void setIsDefaultSDK(ISymbianSDK sdk, boolean isDefault) {
		if (sdk instanceof SymbianSDK) {
			DeviceType deviceEntry = ((SymbianSDK)sdk).getDeviceEntry();
			if (deviceEntry != null) {
				if (isDefault){
					deviceEntry.setDefault(DefaultType.YES_LITERAL);
				} else {
					deviceEntry.setDefault(DefaultType.NO_LITERAL);
				}
			}
		}
	}

	public void setPreviouslyScanned(ISymbianSDK sdk, boolean wasScanned) {
		if (sdk instanceof SymbianSDK) {
			((SymbianSDK)sdk).setPreviouslyScanned(wasScanned);
		}
	}

	protected List<ISymbianBuildContext> getBinaryVariationPlatformContexts(ISymbianSDK sdk) {
		synchronized (binaryVariantContextList) {
			if (!binaryVariantContextList.isEmpty()){
				return binaryVariantContextList;
			}
			
			ISBVCatalog catalog = getSBVCatalog(sdk);
			for (ISBVPlatform sbvPlatform : catalog.getPlatforms()) {
				// Currently only variation of ARMV5 is supported... So just hard code the variated platform
				// Only add the build platform if it's not virtual.
				if (!sbvPlatform.isVirtual()){
					// TODO: Hard code build context hack
					binaryVariantContextList.add(new BuildContextSBSv1(sdk, BuildContextSBSv1.ARMV5_PLATFORM + "." + sbvPlatform.getName(), ISymbianBuildContext.DEBUG_TARGET));
					// TODO: Hard code build context hack
					binaryVariantContextList.add(new BuildContextSBSv1(sdk, BuildContextSBSv1.ARMV5_PLATFORM + "." + sbvPlatform.getName(), ISymbianBuildContext.RELEASE_TARGET));
				}
			}
		}
		return binaryVariantContextList;
	}

	protected List<ISymbianBuildContext> getBSFPlatformContexts(ISymbianSDK sdk) {
		synchronized (bsfContextList) {
			if (!bsfContextList.isEmpty()){
				return bsfContextList;
			}
			
			IBSFCatalog catalog = getBSFCatalog(sdk);
			for (IBSFPlatform platform : catalog.getPlatforms()) {
				// only return non-variant style BSF's.  see boog #4533 for details.
				if (!platform.isVariant()) {
					// TODO: Hard code build context hack
					bsfContextList.add(new BuildContextSBSv1(sdk, platform.getName().toUpperCase(), ISymbianBuildContext.DEBUG_TARGET));
					// TODO: Hard code build context hack
					bsfContextList.add(new BuildContextSBSv1(sdk, platform.getName().toUpperCase(), ISymbianBuildContext.RELEASE_TARGET));
				}
			}
		}
		return bsfContextList;
	}

	@SuppressWarnings("rawtypes")
	protected List<ISymbianBuildContext> getSBSv1FilteredBuildConfigurations(ISymbianSDK sdk) {
		Set sdkFeatures = sdk.getSupportedFeatures();
		List<ISymbianBuildContext> buildContexts =  getAllBuildConfigurations(sdk);
		if (buildContexts.size() == 0){
			return Collections.emptyList();
		}
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		List<BuildPlat> platFilterList = sdkMgr.getPlatformList();
		Iterator<ISymbianBuildContext> li = buildContexts.iterator();
		while(li.hasNext()){
			ISymbianBuildContext currContext = li.next();
			for (BuildPlat currPlat : platFilterList){ // see which ones need to be filtered out.
				
				if (currPlat.getPlatName().equals(currContext.getPlatformString())){
					if (!currPlat.isEnabled()){
						if (sdkFeatures.contains(ISymbianSDKFeatures.IS_EKA2) && 
							currPlat.getOsIdentifier().equals(BuildPlat.EKA2_IDENTIFIER)){
							li.remove();  // filtered out in UI, don't show
							break;
						}
					}
				}
			}
		}
		return buildContexts;
	}

}
