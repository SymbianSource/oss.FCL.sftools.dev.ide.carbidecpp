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
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.IBSFPlatform;
import com.nokia.carbide.cpp.sdk.core.ISBSv1BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVPlatform;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

/**
 * SBSv1 specific build information.
 *
 */
public class SBSv1BuildInfo implements ISBSv1BuildInfo {

	private ISymbianSDK sdk;
	private IBSFCatalog bsfCatalog;
	private ISBVCatalog sbvCatalog;
	private boolean wasScanned = false;
	private List<ISymbianBuildContext> binaryVariantContextList = new ArrayList<ISymbianBuildContext>(0);
	private List<ISymbianBuildContext> bsfContextList = new ArrayList<ISymbianBuildContext>(0);
	private Map<String, List<String>> cachedPlatformMacros = new HashMap<String, List<String>>();

	public SBSv1BuildInfo(ISymbianSDK sdk) {
		this.sdk = sdk;
	}

	public void clearPlatformMacros() {
		cachedPlatformMacros.clear();
	}

	public List<String> getAvailablePlatforms() {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		return sdkMgr.getSymbianMacroStore().getSupportedPlatforms(((SymbianSDK)sdk).getOSVersion(), "", getBSFCatalog());
	}

	@SuppressWarnings("rawtypes")
	public List<ISymbianBuildContext> getAllBuildConfigurations() {
		Set sdkFeatures = sdk.getSupportedFeatures();
		List<ISymbianBuildContext> buildTargets = new ArrayList<ISymbianBuildContext>();
		
		// note that this gets variant platforms but not regular BSF's
		List <String>buildPlats =  getAvailablePlatforms();
		
		if (buildPlats.size() == 0){
			return Collections.emptyList();
		}
		// TODO: Hard code build context hack
		buildTargets.add(new BuildContextSBSv1(sdk, ISBSv1BuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.DEBUG_TARGET));
		
		if (sdkFeatures.contains(ISymbianSDKFeatures.IS_WINSCW_UREL_SUPPORTED)){
			// TODO: Hard code build context hack
			buildTargets.add(new BuildContextSBSv1(sdk, ISBSv1BuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.RELEASE_TARGET));
		}
		
		for (String currPlat : buildPlats){
			if (currPlat.equals(ISBSv1BuildContext.EMULATOR_PLATFORM) ) { 
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
			buildTargets.addAll(getBSFPlatformContexts());
			buildTargets.addAll(getBinaryVariationPlatformContexts()); // Symbian Binary Variation (.var)
		}
		
		return buildTargets;
	}

	public IBSFCatalog getBSFCatalog() {
		synchronized (sdk) {
			if (bsfCatalog == null) {
				bsfCatalog = BSFCatalogFactory.createCatalog(sdk);
			}
		}
		return bsfCatalog;
	}

	public List<ISymbianBuildContext> getFilteredBuildConfigurations() {
		// This is probably a bug, but the filtering only uses SBSv1 preferences if SBSv1 is enabled...
		List<ISymbianBuildContext> filteredContexts;
		if (SBSv2Utils.enableSBSv1Support()) {
			filteredContexts = getSBSv1FilteredBuildConfigurations();
		} else {
			// be optimistic in this case... SBSv3? ;)
			filteredContexts = getAllBuildConfigurations();
		}
		return filteredContexts;
	}

	public List<String> getPlatformMacros(String platform) {
		List<String> platformMacros = cachedPlatformMacros.get(platform.toUpperCase());
		if (platformMacros == null) {
			synchronized (cachedPlatformMacros) {
				IBSFCatalog bsfCatalog = getBSFCatalog();
				ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
				platformMacros = sdkMgr.getSymbianMacroStore().getPlatformMacros(sdk.getOSVersion(), "", bsfCatalog, platform);
				cachedPlatformMacros.put(platform.toUpperCase(), platformMacros);
			}
		}
		return platformMacros;
	}

	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 */
	public IPath getPrefixFromVariantCfg(){
		File epocRoot = new File(sdk.getEPOCROOT());
		File variantCfg;
		variantCfg = new File(epocRoot, SymbianSDK.SPP_VARIANT_CFG_FILE);
		if (!variantCfg.exists()) {
			variantCfg = new File(epocRoot, SymbianSDK.VARIANT_CFG_FILE);
			if (!variantCfg.exists())
				return null;
		}
		
		String variantDir = null;
		String variantFile = null;
		try {
			char[] cbuf = new char[(int) variantCfg.length()];
			Reader reader = new FileReader(variantCfg);
			reader.read(cbuf);
			reader.close();
			String[] lines = new String(cbuf).split("\r\n|\r|\n");
			for (int i = 0; i < lines.length; i++) {
				// skip comments and blank lines
				String line = SymbianSDK.removeComments(lines[i]);
				if (line.matches("\\s*#.*") || line.trim().length() == 0) 
					continue;
				
				// parse the variant line, which is an EPOCROOT-relative
				// path to a bldvariant.hrh file
				Matcher matcher = SymbianSDK.VARIANT_HRH_LINE_PATTERN.matcher(line);
				if (matcher.matches()) {
					variantDir = matcher.group(1);
					variantFile = matcher.group(3); 
					File variantFullPathFile = new File(epocRoot, variantDir + File.separator + variantFile);
					IPath variantFilePath = new Path(PathUtils.convertPathToUnix(variantFullPathFile.getAbsolutePath()));
					return variantFilePath;
				}
			}
		} catch (IOException e) {
		}
		
		return null; // can't find the file...
	}

	public ISBVCatalog getSBVCatalog() {
		synchronized (sdk) {
			if (sbvCatalog == null) {
				sbvCatalog = SBVCatalogFactory.createCatalog(sdk);
			}
		}
		return sbvCatalog;
	}

	public List<String> getTargetTypeMacros(String targettype) {
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

	public List<String> getVendorSDKMacros() {
		return new ArrayList<String>();  // vendor macros no longer apply for Symbian^3 and up
	}

	public boolean isPreviouslyScanned() {
		return wasScanned;
	}

	public void setPreviouslyScanned(boolean wasScanned) {
		this.wasScanned = wasScanned;
	}

	protected List<ISymbianBuildContext> getBinaryVariationPlatformContexts() {
		synchronized (binaryVariantContextList) {
			if (!binaryVariantContextList.isEmpty()){
				return binaryVariantContextList;
			}
			
			ISBVCatalog catalog = getSBVCatalog();
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

	protected List<ISymbianBuildContext> getBSFPlatformContexts() {
		synchronized (bsfContextList) {
			if (!bsfContextList.isEmpty()){
				return bsfContextList;
			}
			
			IBSFCatalog catalog = getBSFCatalog();
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
	protected List<ISymbianBuildContext> getSBSv1FilteredBuildConfigurations() {
		Set sdkFeatures = sdk.getSupportedFeatures();
		List<ISymbianBuildContext> buildContexts =  getAllBuildConfigurations();
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
